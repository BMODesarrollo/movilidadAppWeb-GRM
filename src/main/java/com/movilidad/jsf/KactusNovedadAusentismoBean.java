/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.empleados.gm.Datos;
import com.empleados.gm.MBWebService;
import com.movilidad.ejb.NominaServerParamDetFacadeLocal;
import com.movilidad.ejb.NominaServerParamEmpresaFacadeLocal;
import com.movilidad.ejb.NominaServerParamFacadeLocal;
import com.movilidad.ejb.NovedadIncapacidadFacadeLocal;
import com.movilidad.model.NominaServerParam;
import com.movilidad.model.NominaServerParamDet;
import com.movilidad.model.NominaServerParamEmpresa;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadAutorizacionAusentismos;
import com.movilidad.model.NovedadIncapacidad;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author solucionesit
 */
@Named(value = "kactusNovAusentismoBean")
@ViewScoped
public class KactusNovedadAusentismoBean implements Serializable {

    @EJB
    private NominaServerParamFacadeLocal nominaServerParamEjb;

    @EJB
    private NominaServerParamEmpresaFacadeLocal nominaServerParamEmpresaEjb;

    @EJB
    private NominaServerParamDetFacadeLocal nominaServerParamDetEjb;

    @EJB
    private NovedadIncapacidadFacadeLocal novedadIncapacidadEjb;

    private NominaServerParam nominaServerParam;
    private NominaServerParamEmpresa nominaServerParamEmpresa;

    private String serviceUrl;

    private void cargarNominaServerParam() {
        nominaServerParam = nominaServerParamEjb.find(ConstantsUtil.ID_NOMINA_SERVER_PARAM);
    }

    private void cargarNominaServerParamEmpresa(int idNominaServerParam, int idGopUnidadFuncional) {
        nominaServerParamEmpresa = nominaServerParamEmpresaEjb.findByIdNominaServerParamAndUf(idNominaServerParam, idGopUnidadFuncional);
    }

    private void cargarUrlServicio() {
        serviceUrl = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.URL_NOVEDAD_AUSENTISMO_KACTUS);
    }

    boolean sendToKactus(NovedadAutorizacionAusentismos autorizacionNovedad)
            throws DatatypeConfigurationException, MalformedURLException {
        cargarNominaServerParam();

        if (nominaServerParam == null) {
            MovilidadUtil.addErrorMessage("No se encontró parametrizacion de Nomina Server Param.");
            return false;
        }
        cargarNominaServerParamEmpresa(nominaServerParam.getIdNominaServerParam(),
                autorizacionNovedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        if (nominaServerParamEmpresa == null) {
            MovilidadUtil.addErrorMessage("No se encontró parametrizacion de Nomina Server Param Empresa.");
            return false;
        }

        cargarUrlServicio();

        if (serviceUrl == null) {
            MovilidadUtil.addErrorMessage("No se encontró URL de servicio para envío de novedades a KACTUS");
            return false;
        }

        NominaServerParamDet serverParamDet = nominaServerParamDetEjb.findByIdNovedadTipoDetalle(autorizacionNovedad.getIdNovedad().getIdNovedadTipoDetalle().getIdNovedadTipoDetalle());

        if (serverParamDet == null) {
            MovilidadUtil.addErrorMessage("No se encontró Param Server Detalle para el Detalle de la novedad a guardar");
            return false;
        }

        String msgValidacion = validarDatos(autorizacionNovedad, serverParamDet);

        if (msgValidacion != null) {
            MovilidadUtil.addErrorMessage(msgValidacion);
            return false;
        }

        if (serverParamDet.getClasificacion() == ConstantsUtil.CLASIFICACION_INCAPACIDAD) {
            // Se obtienen los datos requeridos para mandar la información al servicio

            // Se obtienen los seguimientos de incapacidad asociados a la novedad
            List<NovedadIncapacidad> lstSeguimientos = novedadIncapacidadEjb.findByNovedad(autorizacionNovedad.getIdNovedad().getIdNovedad());

            String consecutivoIncapacidad = lstSeguimientos.stream()
                    .limit(1)
                    .map(x -> {
                        return String.valueOf(x.getNumeroIncapacidad());
                    }
                    ).collect(Collectors.joining());

            String identificacion = autorizacionNovedad.getIdNovedad().getIdEmpleado().getIdentificacion();
            enviarIncapacidad(
                    identificacion, serverParamDet.getCodConc(),
                    autorizacionNovedad.getIdNovedad().getDesde(),
                    autorizacionNovedad.getIdNovedad().getHasta(),
                    serverParamDet.getTipoIncapacidad(), consecutivoIncapacidad);
        }

        if (serverParamDet.getClasificacion() == ConstantsUtil.CLASIFICACION_AUSENTISMO) {
            // Se obtienen los datos requeridos para mandar la información al servicio

            String identificacion = autorizacionNovedad.getIdNovedad().getIdEmpleado().getIdentificacion();
            enviarAusentismo(
                    identificacion, serverParamDet.getCodConc(),
                    autorizacionNovedad.getIdNovedad().getDesde(),
                    autorizacionNovedad.getIdNovedad().getHasta(),
                    serverParamDet.getCodMause());
        }
        
        return true;

    }

    /**
     * Método que se encarga de envíar las novedades de ausentismo a KACTUS
     *
     * @param identificacion
     * @param conceptoAusentismo
     * @param fechaDesde
     * @param fechaHasta
     * @param codigoAusentismo
     * @throws MalformedURLException
     */
    private void enviarAusentismo(String identificacion, String conceptoAusentismo,
            Date fechaDesde, Date fechaHasta, String codigoAusentismo)
            throws MalformedURLException {

        URL url = new URL(serviceUrl);
        Datos data = new Datos(url);

        MBWebService service = data.getMBWebServicePort();
        String response = service.ausentismosGreen(
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.USR_NOVEDAD_AUSENTISMO_KACTUS), // Usuario del servicio
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.PWD_NOVEDAD_AUSENTISMO_KACTUS), // Usuario del Contraseña
                nominaServerParamEmpresa.getCodSwNomina(), // Código Empresa
                identificacion, // Identificación
                conceptoAusentismo, //Concepto ausentismo
                codigoAusentismo, // Código ausentismo
                calcularDias(fechaDesde, fechaHasta), // Cantidad de dias
                Util.dateFormatFechaDDMMYYYY(fechaDesde), // Fecha desde
                Util.dateFormatFechaDDMMYYYY(fechaHasta) // Fecha hasta
        );
//        String response = service.ausentismosGreen(
//                "wsRigel", // Usuario del servicio
//                "6rUP0.mov1|.Ws", // Usuario del Contraseña
//                "7362", // Código Empresa
//                "3215433", // Identificación
//                "519", //Concepto ausentismo
//                "30", // Código ausentismo
//                "3", // Cantidad de dias
//                "28-03-2022", // Fecha desde
//                "30-03-2022" // Fecha hasta
//        );

        System.out.println(response);
    }

    /**
     * Método que se encarga de envíar las incapacidades a KACTUS
     *
     * @param identificacion
     * @param conceptoIncapacidad
     * @param fechaDesde
     * @param fechaHasta
     * @param tipoIncapacidad
     * @throws MalformedURLException
     */
    private void enviarIncapacidad(String identificacion, String conceptoIncapacidad,
            Date fechaDesde, Date fechaHasta, String tipoIncapacidad, String consecutivoIncapacidad)
            throws MalformedURLException {

        URL url = new URL(serviceUrl);
        Datos data = new Datos(url);

        MBWebService service = data.getMBWebServicePort();

        String response = service.incapacidadesGreen(
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.USR_NOVEDAD_AUSENTISMO_KACTUS), // Usuario del servicio
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.PWD_NOVEDAD_AUSENTISMO_KACTUS), // Usuario del Contraseña
                nominaServerParamEmpresa.getCodSwNomina(), // Código Empresa 
                identificacion, // Identificación
                conceptoIncapacidad, //Concepto Incapacidad 
                tipoIncapacidad, //Tipo de incapacidad
                consecutivoIncapacidad, //Consecutivo incapacidad
                calcularDias(fechaDesde, fechaHasta), // Cantidad de dias
                Util.dateFormatFechaDDMMYYYY(fechaDesde), // Fecha desde
                Util.dateFormatFechaDDMMYYYY(fechaHasta), // Fecha hasta
                "O800" //Código CIE10
        );

//        String response = service.incapacidadesGreen(
//                "wsRigel", // Usuario del servicio
//                "6rUP0.mov1|.Ws", // Usuario del Contraseña
//                "7362", // Código Empresa 
//                "79664264", // Identificación
//                "143", //Concepto Incapacidad 
//                "LMA", //Tipo de incapacidad
//                "#INC_VIVIANA", //Código incapacidad
//                "116", // Cantidad de dias
//                "16-03-2022", // Fecha desde
//                "11-07-2022", // Fecha hasta 
//                "O800" //Código CIE10
//        );
        System.out.println(response);
    }

    private String calcularDias(Date desde, Date hasta) {

        if (desde == null && hasta == null) {
            return "";
        }

        long startTime = desde.getTime();
        long endTime = hasta.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24) + 1;
        return String.valueOf((int) diffDays);
    }

    private String validarDatos(NovedadAutorizacionAusentismos autorizacionNovedad,
            NominaServerParamDet serverParamDet) {

        Novedad novedad = autorizacionNovedad.getIdNovedad();

        if (novedad.getDesde() == null) {
            return "La novedad a enviar NO cuenta con fecha desde";
        }

        if (novedad.getHasta() == null) {
            return "La novedad a enviar NO cuenta con fecha hasta";
        }

        if (serverParamDet.getClasificacion() == ConstantsUtil.CLASIFICACION_INCAPACIDAD) {
            // Se verifica si la incapacidad a enviar tiene algún seguimiento
            List<NovedadIncapacidad> lstSeguimientos = novedadIncapacidadEjb.findByNovedad(novedad.getIdNovedad());

            if (lstSeguimientos == null || lstSeguimientos.isEmpty()) {
                return "La novedad a envíar NO posee un seguimiento de incapacidad asociado";
            }
        }

        return null;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

}
