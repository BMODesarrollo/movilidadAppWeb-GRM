/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.AccidenteAnalisisFacadeLocal;
import com.movilidad.ejb.AccidenteConductorFacadeLocal;
import com.movilidad.ejb.AccidenteDocumentoFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteLugarFacadeLocal;
import com.movilidad.ejb.AccidentePlanAccionFacadeLocal;
import com.movilidad.ejb.AccidenteTestigoFacadeLocal;
import com.movilidad.ejb.AccidenteVehiculoFacadeLocal;
import com.movilidad.ejb.AccidenteVictimaFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteAnalisis;
import com.movilidad.model.AccidenteLugar;
import com.movilidad.model.AccidenteLugarDemar;
import com.movilidad.model.AccidentePlanAccion;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author soluciones-it
 */
@Named(value = "accReporteGMJSF")
@ViewScoped
public class AccReporteGMJSF implements Serializable {

    @EJB
    private AccidenteLugarFacadeLocal accLugarFacadeLocal;
    @EJB
    private AccidenteConductorFacadeLocal accConductorFacadeLocal;
    @EJB
    private AccidenteVehiculoFacadeLocal accVehiculoFacadeLocal;
    @EJB
    private AccidenteDocumentoFacadeLocal accDocumentoFacadeLocal;
    @EJB
    private AccidenteVictimaFacadeLocal accVictimaFacadeLocal;
    @EJB
    private AccidentePlanAccionFacadeLocal accPlanAccionFacadeLocal;
    @EJB
    private AccidenteTestigoFacadeLocal accTestigoFacadeLocal;
    @EJB
    private AccidenteAnalisisFacadeLocal accAnalisisFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private AccidenteFacadeLocal accidenteEjb;
    //Descargar el documento
    private StreamedContent file;
    private Accidente accidente;

    /**
     * Creates a new instance of AccReporteGMJSF
     */
    public AccReporteGMJSF() {
    }

    public StreamedContent generarReporteAccidente(Integer idAccidente) throws FileNotFoundException{
        accidente = accidenteEjb.find(idAccidente);
        generarReporte(accidente);
        return file;
    }
    
    public void generarReporte(Accidente acc) throws FileNotFoundException {
        Integer idAcc = acc.getIdAccidente();
        Map p = new HashMap();
        p.put("fechaAcc", Util.dateFormat(acc.getFechaAcc()));
        p.put("horaAcc", Util.dateToTimeHHMMSS(acc.getFechaAcc()));

        // si existe accidente lugar
        AccidenteLugar accLugar = accLugarFacadeLocal.buscarPorAccidente(idAcc);
        boolean existAccLugar = accLugar != null;
        p.put("direccion", existAccLugar ? accLugar.getDireccion() : "");
        p.put("sentido", (existAccLugar && accLugar.getIdAccSentido() != null) ? accLugar.getIdAccSentido().getSentido() : "");
        p.put("carril", (existAccLugar && accLugar.getIdAccViaCarriles() != null) ? accLugar.getIdAccViaCarriles().getViaCarriles() : "");
        p.put("estadoVia", (existAccLugar && accLugar.getIdAccViaEstado() != null) ? accLugar.getIdAccViaEstado().getViaEstado() : "");
        p.put("iluminacionVia", (existAccLugar && accLugar.getIdAccViaIluminacion() != null)
                ? accLugar.getIdAccViaIluminacion().getViaIluminacion()
                : "");
        p.put("climaVia", (existAccLugar && accLugar.getIdAccViaClima() != null) ? accLugar.getIdAccViaClima().getViaClima() : "");
        List<AccidenteLugarDemar> accidenteLugarDemarList = existAccLugar ? accLugar.getAccidenteLugarDemarList() : new ArrayList<>();
        p.put("senalizaciones", accidenteLugarDemarList);
        //acc
        p.put("evento", acc.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        p.put("clasificacion", acc.getIdClase() != null ? acc.getIdClase().getClase() : "");
        p.put("fechaEvento", acc.getFechaAcc() != null ? acc.getFechaAcc() : "");
//        p.put("fechaCierre", acc.getIdAccAtencionVia() != null ? acc.getIdAccAtencionVia().getNombre() : "");
        // vehiculo
        p.put("codVeh", acc.getIdVehiculo() != null ? acc.getIdVehiculo().getCodigo() : "");
        p.put("vehiculoModelo", vehiculoFacadeLocal.getVehiculoCodigo(accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getCodigoVehiculo()).getModelo());//modelo del móvil
        p.put("vehTabla", acc.getIdPrgTc() != null ? acc.getIdPrgTc().getTabla() : "");//Tabla del móvil
        p.put("vehRuta", acc.getIdPrgTc() != null ? acc.getIdPrgTc().getIdRuta() != null ? acc.getIdPrgTc().getIdRuta().getName() : "" : "");//Ruta del móvil
        p.put("vehInmovil", accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado() != null ? accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getIdAccInmovilizado() != null
                ? accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getIdAccInmovilizado() != 0
                ? accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getIdAccInmovilizado() != 2 ? "SI" : "NO" : "NO" : "NO" : "NO");
        p.put("vehURI", accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado() != null
                ? accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getIdAccInmovilizado() != null
                ? accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getIdAccInmovilizado() == 4 ? "SI" : "NO" : "NO" : "NO");
        p.put("vehPatio", accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado() != null
                ? accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getIdAccInmovilizado() != null
                ? (accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getIdAccInmovilizado() != 0
                ? (accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getIdAccInmovilizado() != 4
                ? accVehiculoFacadeLocal.estadoReg(idAcc).get(0).getIdAccInmovilizado().getInmovilizado() : "") : "") : "" : "");
        // operador
        p.put("codOpe", acc.getIdEmpleado() != null ? acc.getIdEmpleado().getCodigoTm() : "");
        p.put("UF", acc.getIdEmpleado() != null ? obtenerUF(String.valueOf(acc.getIdVehiculo().getCodigo())) : "");
        p.put("edadOpe", acc.getIdEmpleado() != null ? String.valueOf(calcularEdad(acc.getIdEmpleado().getFechaNcto())) : "");
        p.put("fechaIngreso", acc.getIdEmpleado() != null ? acc.getIdEmpleado().getFechaIngreso() : "");
//        p.put("estadoOperador", idAcc != null ? accVictimaFacadeLocal.estadoReg(idAcc).get(0). : "");

        // prgTc
        p.put("servicio", acc.getIdPrgTc() != null
                ? (acc.getIdPrgTc().getIdTaskType() != null ? acc.getIdPrgTc().getIdTaskType().getTarea() : "")
                : "");
        // abogado
        p.put("abogado", acc.getIdAccAbogado() != null ? acc.getIdAccAbogado().getNombreCompleto() : "");
        // conductores involucrados
        p.put("conductores", accConductorFacadeLocal.estadoReg(idAcc));
        // vehiculos involucrados
        p.put("vehiculos", accVehiculoFacadeLocal.estadoReg(idAcc));
        // victimas
        p.put("victimas", accVictimaFacadeLocal.estadoReg(idAcc));
        //testigos
        p.put("testigos", accTestigoFacadeLocal.estadoReg(idAcc));

        // análisis
        p.put("ipat", !Util.isStringNullOrEmpty(acc.getNroIpat()) ? "SI" : "NO");
        p.put("ipatN", !Util.isStringNullOrEmpty(acc.getNroIpat()) ? acc.getNroIpat() : "0");
        p.put("analisis", getAnalisis(accAnalisisFacadeLocal.estadoReg(idAcc)));
        //acc
        // documentos
        p.put("documentos", accDocumentoFacadeLocal.estadoReg(idAcc));
        //plan accion
        p.put("acciones", getPlanAccion(accPlanAccionFacadeLocal.findAllEstadoReg(idAcc)));
        
        String destino = "/tmp/" + Util.generarUUID() + ".xlsx";
        String plantilla = Util.getProperty(ConstantsUtil.KEY_PLANTILLA_ACC_GM);
        GeneraXlsx.generar(plantilla, destino, p);
        InputStream stream = new FileInputStream(new File(destino));
        file = new DefaultStreamedContent(stream,
                "text/plain",
                "reporte accidente "
                + acc.getIdNovedadTipoDetalle().getTituloTipoNovedad()
                + "-"
                + Util.dateFormat(acc.getFechaAcc()) + ".xlsx");
    }

    private List<String> getPlanAccion(List<AccidentePlanAccion> lista) {
        List<String> listPlanAccion = new ArrayList<>();
        if(lista != null && !lista.isEmpty()) {
            for (AccidentePlanAccion obj : lista) {
                listPlanAccion.add(obj.getIdAccPlanAccion().getDescripcion());
            }
        }
        return listPlanAccion;
    }
    
    private int calcularEdad(Date date) {
        return Util.getAge(date);
    }

    private String obtenerUF(String codTM) {
        // Verifica si la cadena coincide con los patrones
        if (codTM.matches("^Z63.*")) {
            return "ZMO III";
        } else if (codTM.matches("^Z67.*")) {
            return "ZMO V";
        } else {
            return "N.A";
        }
    }

    private String getAnalisis(List<AccidenteAnalisis> lista) {
        String cadena = "";
        for (AccidenteAnalisis obj : lista) {
            cadena += obj.getObservaciones()+"\n";
        }
        return cadena;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
}
