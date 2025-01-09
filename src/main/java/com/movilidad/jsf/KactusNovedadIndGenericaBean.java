/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.kactus.ws.IKWsInovo;
import com.kactus.ws.KWsInovo;
import com.kactus.ws.RpNovedades;
import com.movilidad.ejb.GenericaNominaAutorizacionDetIndividualFacadeLocal;
import com.movilidad.ejb.NominaServerParamEmpresaFacadeLocal;
import com.movilidad.ejb.NominaServerParamFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.GenericaNominaAutorizacionDetIndividual;
import com.movilidad.model.GenericaNominaAutorizacionIndividual;
import com.movilidad.model.NominaServerParam;
import com.movilidad.model.NominaServerParamEmpresa;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ReporteHoras;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "kactusNovIndvGenBean")
@ViewScoped
public class KactusNovedadIndGenericaBean implements Serializable {

    /**
     * Creates a new instance of KactusNovedadBean
     */
    public KactusNovedadIndGenericaBean() {
    }
    @EJB
    private NominaServerParamFacadeLocal nominaServerParamEjb;

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;

    @EJB
    private NominaServerParamEmpresaFacadeLocal nominaServerParamEmpresaEjb;
    @EJB
    private GenericaNominaAutorizacionDetIndividualFacadeLocal autorizacionDetEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private HashMap<String, ParamReporteHoras> hReporte;
    private HashMap<String, GenericaNominaAutorizacionDetIndividual> mapNominaAutoDet;
    private List<ReporteHoras> lstReporteHoras;
    private NominaServerParam nominaServerParam;
    private NominaServerParamEmpresa nominaServerParamEmpresa;

    private String mensajeError;

    private void cargarNominaServerParam() {
        nominaServerParam = nominaServerParamEjb.find(ConstantsUtil.ID_NOMINA_SERVER_PARAM);
    }

    private void cargarNominaServerParamEmpresa(int idNominaServerParam, int idGopUnidadFuncional) {
        nominaServerParamEmpresa = nominaServerParamEmpresaEjb.findByIdNominaServerParamAndUf(idNominaServerParam, idGopUnidadFuncional);
    }

    private void cargarMapParamReporteHoras() {
        hReporte = new HashMap<>();
        List<ParamReporteHoras> lstParamReporteHoras = paramReporteHorasEjb.findAllActivos(0);

        for (ParamReporteHoras rph : lstParamReporteHoras) {
            hReporte.put(rph.getTipoHora(), rph);
        }
    }

    private void cargarMapDetalles(Integer idNominaAutorizacion) {
        mapNominaAutoDet = new HashMap<>();
        List<GenericaNominaAutorizacionDetIndividual> detalles
                = autorizacionDetEJB.findByIdNominaAutorizacion(idNominaAutorizacion);

        for (GenericaNominaAutorizacionDetIndividual obj : detalles) {
            mapNominaAutoDet.put(obj.getIdentificacion(), obj);
        }
    }

    private boolean cargarReporteHoras(Date desde, Date hasta, int idParamArea, int idEmpleado) {
        lstReporteHoras = paramReporteHorasEjb.obtenerDatosReporteGenericaByAreaAndEmpleado(desde, hasta, idParamArea, idEmpleado);
        if (lstReporteHoras == null) {
            MovilidadUtil.addErrorMessage("Error!!");
            return true;
        }
        if (lstReporteHoras.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return true;
        }
        return false;
    }

    void sendToKactus(GenericaNominaAutorizacionIndividual autorizacionNomina, String fechaPago)
            throws DatatypeConfigurationException, MalformedURLException {
        cargarNominaServerParam();
        cargarMapDetalles(autorizacionNomina.getIdGenericaNominaAutorizacionIndividual());
        if (nominaServerParam == null) {
            MovilidadUtil.addErrorMessage("No se encontró parametrizacion de Nomina Server Param.");
            return;
        }
        cargarNominaServerParamEmpresa(nominaServerParam.getIdNominaServerParam(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (nominaServerParamEmpresa == null) {
            MovilidadUtil.addErrorMessage("No se encontró parametrizacion de Nomina Server Param Empresa.");
            return;
        }
        if (cargarReporteHoras(autorizacionNomina.getDesde(), autorizacionNomina.getHasta(),
                autorizacionNomina.getIdParamArea().getIdParamArea(),
                autorizacionNomina.getIdEmpleado().getIdEmpleado())) {
            return;
        }
        cargarMapParamReporteHoras();

        KWsInovo kws = new KWsInovo(new URL(nominaServerParam.getUrlWs()));
        IKWsInovo iKws = kws.getBasicHttpBindingIKWsInovo();

        Map<Long, String> serviceResponse = new HashMap();

        for (ReporteHoras reporteHora : lstReporteHoras) {

            mensajeError = "";

            if (reporteHora.getNocturnas().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_NOCTURNAS);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getNocturnas().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getNocturnas() == null) {
                        detalle.setNocturnas(reporteHora.getNocturnas());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getExtra_diurna().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_EXTRA_DIURNA);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getExtra_diurna().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getExtraDiurna() == null) {
                        detalle.setExtraDiurna(reporteHora.getExtra_diurna());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getExtra_nocturna().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_EXTRA_NOCTURNA);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getExtra_nocturna().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getExtraNocturna() == null) {
                        detalle.setExtraNocturna(reporteHora.getExtra_nocturna());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getFestivo_diurno().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_DIURNO);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getFestivo_diurno().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getFestivoDiurno() == null) {
                        detalle.setFestivoDiurno(reporteHora.getFestivo_diurno());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }

                }
            }
            if (reporteHora.getFestivo_nocturno().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_NOCTURNO);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getFestivo_nocturno().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getFestivoNocturno() == null) {
                        detalle.setFestivoNocturno(reporteHora.getFestivo_nocturno());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getFestivo_extra_diurno().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_EXTRA_DIURNO);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getFestivo_extra_diurno().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getFestivoExtraDiurno() == null) {
                        detalle.setFestivoExtraDiurno(reporteHora.getFestivo_extra_diurno());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getFestivo_extra_nocturno().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getFestivo_extra_nocturno().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getFestivoExtraNocturno() == null) {
                        detalle.setFestivoExtraNocturno(reporteHora.getFestivo_extra_nocturno());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getDominical_comp_diurnas().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getDominical_comp_diurnas().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getDominicalCompDiurnas() == null) {
                        detalle.setDominicalCompDiurnas(reporteHora.getDominical_comp_diurnas());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getDominical_comp_nocturnas().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getDominical_comp_nocturnas().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getDominicalCompNocturnas() == null) {
                        detalle.setDominicalCompNocturnas(reporteHora.getDominical_comp_nocturnas());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getDominical_comp_diurna_extra().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getDominical_comp_diurna_extra().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getDominicalCompDiurnaExtra() == null) {
                        detalle.setDominicalCompDiurnaExtra(reporteHora.getDominical_comp_diurna_extra());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
            if (reporteHora.getDominical_comp_nocturna_extra().compareTo(Util.CERO) == 1) {
                serviceResponse = new HashMap();
                ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getDominical_comp_nocturna_extra().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo());

                    //Detalle de la autorizacion
                    GenericaNominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getDominicalCompNocturnaExtra() == null) {
                        detalle.setDominicalCompNocturnaExtra(reporteHora.getDominical_comp_nocturna_extra());
                        saveGenericaNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
        }
    }

    /**
     *
     * @param iKws
     * @param xmlCalendar
     * @param cantidadHoras
     * @param codigoConcepto
     * @param codigoEmpl
     */
    private Map<Long, String> enviarNovedadKactus(IKWsInovo iKws, String fechaPago,
            double cantidadHoras, int codigoConcepto, long codigoEmpl) {
        Map<Long, String> serviceResponse = new HashMap();
        System.out.println("--xmlCalendar->" + fechaPago + "--cantidadHoras->"
                + cantidadHoras + "--codigoConcepto->" + codigoConcepto + "--codigoEmpl->" + codigoEmpl);
        RpNovedades rep = iKws.setNovedades(
                //                (short) 682, // pSmCodEmpr, tomado del ejemplo compartido
                (short) Short.valueOf(nominaServerParamEmpresa.getCodSwNomina()), // pSmCodEmpr, Tomado de la parametrización nomina_server_param
                //                (long) 457421, // pFlCodEmpl
                (long) codigoEmpl, // pFlCodEmpl 79714048 ZMO Op Bus
                "1", // pStNroCont
                (short) codigoConcepto, // pSmCodConc - Ser toma de Parametrización Horas
                cantidadHoras, // pFlCanNove - Cantidad de horas y minutos de novedad
                null, // pFlValCuot
                //                "wsRigel", // pStUser
                //                "testkactus", // pStUser nomina_server_param
                nominaServerParam.getUsr(),
                //                "W3bs3rv!c3", // pStPass
                //                "T3sTkk4ct.02", // pStPass nomina_server_param
                nominaServerParam.getPwd(),
                "N", // pStTipValpe
                fechaPago, // pDtFecPago
                (short) 0, // pSmNumCuot
                (float) 0, // pFlValTota
                (float) 0, // pFlSalNove
                "V"); // pStTipNove
        serviceResponse.put(rep.getPInSecConc(), rep.getPStDesErro().getValue());
        System.out.println(rep.getPInSecConc());
        System.out.println(rep.getPStDesErro().getValue());
        //return rep.getPInSecConc();
        return serviceResponse;

    }

    public GenericaNominaAutorizacionDetIndividual builGenericaNominaAutorizacionDetIndividual(String identificacion,
            GenericaNominaAutorizacionIndividual autorizacionNomina) {
        GenericaNominaAutorizacionDetIndividual autorizacionDet = new GenericaNominaAutorizacionDetIndividual();
        autorizacionDet.setIdentificacion(identificacion);
        autorizacionDet.setCreado(MovilidadUtil.fechaCompletaHoy());
        autorizacionDet.setEstadoReg(0);
        autorizacionDet.setUsername(user.getUsername());
        autorizacionDet.setIdParamArea(autorizacionNomina.getIdParamArea());
        autorizacionDet.setIdGenericaNominaAutorizacionIndividual(autorizacionNomina);
        return autorizacionDet;
    }

    GenericaNominaAutorizacionDetIndividual validarDetalleSaved(ReporteHoras reporteHora, GenericaNominaAutorizacionIndividual autorizacionNomina) {
        GenericaNominaAutorizacionDetIndividual get = mapNominaAutoDet.get(reporteHora.getIdentificacion());
        if (get == null) {
            get = builGenericaNominaAutorizacionDetIndividual(reporteHora.getIdentificacion(),
                    autorizacionNomina);
            mapNominaAutoDet.put(reporteHora.getIdentificacion(), get);
        }
        return get;
    }

    void saveGenericaNominaAutorizacionDetIndividual(GenericaNominaAutorizacionDetIndividual autorizacionDet) {
        if (mensajeError != null && !mensajeError.isEmpty()) {
            autorizacionDet.setCodigoError(ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS);
            autorizacionDet.setMensajeError(mensajeError);
        }

        if (autorizacionDet.getIdGenericaNominaAutorizacionDetIndividual() == null) {
            autorizacionDetEJB.create(autorizacionDet);
        } else {
            autorizacionDet.setModificado(MovilidadUtil.fechaCompletaHoy());
            autorizacionDet.setUsername(user.getUsername());
            autorizacionDetEJB.edit(autorizacionDet);
        }
        mapNominaAutoDet.put(autorizacionDet.getIdentificacion(), autorizacionDet);
    }

    void updateConceptosError(Map<Long, String> serviceResponse, String codigoConcepto) {
        if (serviceResponse.get(Long.valueOf(ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS)) != null) {
            mensajeError += serviceResponse.get(Long.valueOf(ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS)) + " : " + codigoConcepto + " \n ";
        }
    }

}
