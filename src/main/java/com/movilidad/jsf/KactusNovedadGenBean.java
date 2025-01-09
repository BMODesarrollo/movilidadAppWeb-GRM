/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.kactus.ws.IKWsInovo;
import com.kactus.ws.KWsInovo;
import com.kactus.ws.RpNovedades;
import com.movilidad.ejb.GenericaNominaAutorizacionDetFacadeLocal;
import com.movilidad.ejb.GenericaNominaAutorizacionIndividualFacadeLocal;
import com.movilidad.ejb.NominaServerParamEmpresaFacadeLocal;
import com.movilidad.ejb.NominaServerParamFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.GenericaNominaAutorizacion;
import com.movilidad.model.GenericaNominaAutorizacionDet;
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
@Named(value = "kactusNovGenBean")
@ViewScoped
public class KactusNovedadGenBean implements Serializable {

    /**
     * Creates a new instance of KactusNovedadBean
     */
    public KactusNovedadGenBean() {
    }
    @EJB
    private NominaServerParamFacadeLocal nominaServerParamEjb;

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;

    @EJB
    private NominaServerParamEmpresaFacadeLocal nominaServerParamEmpresaEjb;
    @EJB
    private GenericaNominaAutorizacionDetFacadeLocal autorizacionDetEJB;
    @EJB
    private GenericaNominaAutorizacionIndividualFacadeLocal autorizacionIndvEJB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private HashMap<String, ParamReporteHoras> hReporte;
    private HashMap<String, GenericaNominaAutorizacionDet> mapNominaAutoDet;
    private HashMap<String, GenericaNominaAutorizacionDetIndividual> mapNominaAutoDetIndv;
    private List<ReporteHoras> lstReporteHoras;
    private NominaServerParam nominaServerParam;
    private NominaServerParamEmpresa nominaServerParamEmpresa;

    private String mensajeError;
    private String conceptosError;
    private static final String YA_EXISTE_NOVEDAD_KACTUS = "Ya se encuentra una novedad para el periodo";

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

    private void cargarMapDetalles(Integer idGenericaNominaAutorizacion) {
        mapNominaAutoDet = new HashMap<>();
        List<GenericaNominaAutorizacionDet> detalles
                = autorizacionDetEJB.findByIdNominaAutorizacion(idGenericaNominaAutorizacion);

        for (GenericaNominaAutorizacionDet obj : detalles) {
            mapNominaAutoDet.put(obj.getIdentificacion(), obj);
        }
    }

    private void cargarMapDetallesIndividual(Date desde, Date hasta, int idParamArea) {
        mapNominaAutoDetIndv = new HashMap<>();
        List<GenericaNominaAutorizacionIndividual> autorizaciones
                = autorizacionIndvEJB.findAllByRangoFechasAndAreaAndEmpleado(desde, hasta, idParamArea, 0);

        for (GenericaNominaAutorizacionIndividual obj : autorizaciones) {

            if (obj.getEnviadoNomina() == 1) {
                if (obj.getGenericaNominaAutorizacionDetIndividualList() != null) {

                    for (GenericaNominaAutorizacionDetIndividual item : obj.getGenericaNominaAutorizacionDetIndividualList()) {
                        mapNominaAutoDetIndv.put(item.getIdentificacion(), item);
                    }
                }
            }

        }
    }

    private boolean cargarReporteHoras(Date desde, Date hasta, int idParamArea) {
        lstReporteHoras = paramReporteHorasEjb.obtenerDatosReporteGenericas(desde, hasta, idParamArea, 0);
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

    void sendToKactus(GenericaNominaAutorizacion autorizacionNomina, String fechaPago)
            throws DatatypeConfigurationException, MalformedURLException {
        cargarNominaServerParam();
        cargarMapDetalles(autorizacionNomina.getIdGenericaNominaAutorizacion());
        cargarMapDetallesIndividual(autorizacionNomina.getDesde(), autorizacionNomina.getHasta(), autorizacionNomina.getIdParamArea().getIdParamArea());
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
                autorizacionNomina.getIdParamArea().getIdParamArea())) {
            return;
        }
        cargarMapParamReporteHoras();

        KWsInovo kws = new KWsInovo(new URL(nominaServerParam.getUrlWs()));
        IKWsInovo iKws = kws.getBasicHttpBindingIKWsInovo();

        Map<Integer, String> serviceResponse = new HashMap();

        for (ReporteHoras reporteHora : lstReporteHoras) {

            mensajeError = "";
            conceptosError = "";

            GenericaNominaAutorizacionDetIndividual itemIndividual = mapNominaAutoDetIndv.get(reporteHora.getIdentificacion());
            GenericaNominaAutorizacionDet item = mapNominaAutoDet.get(reporteHora.getIdentificacion());

            if ((itemIndividual == null && item == null) || (item != null && item.getMensajeError() != null)) {

                if (item != null) {
                    item.setMensajeError("");
                    conceptosError = item.getConceptos();
                }

                if (reporteHora.getNocturnas().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_NOCTURNAS);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getNocturnas().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getNocturnas() == null) {
                                detalle.setNocturnas(reporteHora.getNocturnas());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getExtra_diurna().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_EXTRA_DIURNA);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getExtra_diurna().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getExtraDiurna() == null) {
                                detalle.setExtraDiurna(reporteHora.getExtra_diurna());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getExtra_nocturna().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_EXTRA_NOCTURNA);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getExtra_nocturna().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getExtraNocturna() == null) {
                                detalle.setExtraNocturna(reporteHora.getExtra_nocturna());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getFestivo_diurno().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_DIURNO);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getFestivo_diurno().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getFestivoDiurno() == null) {
                                detalle.setFestivoDiurno(reporteHora.getFestivo_diurno());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }

                        }
                    }
                }
                if (reporteHora.getFestivo_nocturno().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_NOCTURNO);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getFestivo_nocturno().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getFestivoNocturno() == null) {
                                detalle.setFestivoNocturno(reporteHora.getFestivo_nocturno());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getFestivo_extra_diurno().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_EXTRA_DIURNO);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getFestivo_extra_diurno().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getFestivoExtraDiurno() == null) {
                                detalle.setFestivoExtraDiurno(reporteHora.getFestivo_extra_diurno());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getFestivo_extra_nocturno().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getFestivo_extra_nocturno().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getFestivoExtraNocturno() == null) {
                                detalle.setFestivoExtraNocturno(reporteHora.getFestivo_extra_nocturno());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getDominical_comp_diurnas().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getDominical_comp_diurnas().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getDominicalCompDiurnas() == null) {
                                detalle.setDominicalCompDiurnas(reporteHora.getDominical_comp_diurnas());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getDominical_comp_nocturnas().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getDominical_comp_nocturnas().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getDominicalCompNocturnas() == null) {
                                detalle.setDominicalCompNocturnas(reporteHora.getDominical_comp_nocturnas());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getDominical_comp_diurna_extra().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getDominical_comp_diurna_extra().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getDominicalCompDiurnaExtra() == null) {
                                detalle.setDominicalCompDiurnaExtra(reporteHora.getDominical_comp_diurna_extra());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
                    }
                }
                if (reporteHora.getDominical_comp_nocturna_extra().compareTo(Util.CERO) == 1) {
                    serviceResponse = new HashMap();
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO);
                    if (paramReporteHoras != null) {
                        if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                            serviceResponse = enviarNovedadKactus(iKws, fechaPago, reporteHora.getDominical_comp_nocturna_extra().doubleValue(),
                                    Integer.parseInt(paramReporteHoras.getCodigo()),
                                    Long.parseLong(reporteHora.getIdentificacion()));

                            updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), (item == null));

                            //Detalle de la autorizacion
                            GenericaNominaAutorizacionDet detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                            if (detalle.getDominicalCompNocturnaExtra() == null) {
                                detalle.setDominicalCompNocturnaExtra(reporteHora.getDominical_comp_nocturna_extra());
                                saveGenericaNominaAutorizacionDet(detalle);
                            } else {
                                saveGenericaNominaAutorizacionDet(detalle);
                            }
                        }
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
    private Map<Integer, String> enviarNovedadKactus(IKWsInovo iKws, String fechaPago,
            double cantidadHoras, int codigoConcepto, long codigoEmpl) {
        Map<Integer, String> serviceResponse = new HashMap();
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
        serviceResponse.put(rep.getPInSecConc().intValue(), rep.getPStDesErro().getValue());
        System.out.println(rep.getPInSecConc());
        System.out.println(rep.getPStDesErro().getValue());
        //return rep.getPInSecConc();
        return serviceResponse;

    }

    public GenericaNominaAutorizacionDet builGenericaNominaAutorizacionDet(String identificacion,
            GenericaNominaAutorizacion autorizacionNomina) {
        GenericaNominaAutorizacionDet autorizacionDet = new GenericaNominaAutorizacionDet();
        autorizacionDet.setIdentificacion(identificacion);
        autorizacionDet.setIdParamArea(autorizacionNomina.getIdParamArea());
        autorizacionDet.setCreado(MovilidadUtil.fechaCompletaHoy());
        autorizacionDet.setEstadoReg(0);
        autorizacionDet.setUsername(user.getUsername());
        autorizacionDet.setIdGenericaNominaAutorizacion(autorizacionNomina);
        return autorizacionDet;
    }

    GenericaNominaAutorizacionDet validarDetalleSaved(ReporteHoras reporteHora, GenericaNominaAutorizacion autorizacionNomina) {
        GenericaNominaAutorizacionDet get = mapNominaAutoDet.get(reporteHora.getIdentificacion());
        if (get == null) {
            get = builGenericaNominaAutorizacionDet(reporteHora.getIdentificacion(),
                    autorizacionNomina);
            mapNominaAutoDet.put(reporteHora.getIdentificacion(), get);
        }
        return get;
    }

    boolean estaConceptoEnListaErrores(String conceptos, String concepto) {
        return conceptos.contains(concepto);
    }

    void saveGenericaNominaAutorizacionDet(GenericaNominaAutorizacionDet autorizacionDet) {

        if (mensajeError != null && !mensajeError.isEmpty()) {
            autorizacionDet.setCodigoError(ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS);
            autorizacionDet.setMensajeError(mensajeError);
            autorizacionDet.setConceptos(conceptosError);
        } else {
            autorizacionDet.setCodigoError(null);
            autorizacionDet.setMensajeError(null);
            autorizacionDet.setConceptos(null);
        }

        if (autorizacionDet.getIdGenericaNominaAutorizacionDet() == null) {
            autorizacionDetEJB.create(autorizacionDet);
            mapNominaAutoDet.put(autorizacionDet.getIdentificacion(), autorizacionDet);
        } else {
            autorizacionDet.setModificado(MovilidadUtil.fechaCompletaHoy());
            autorizacionDet.setUsername(user.getUsername());
            autorizacionDetEJB.edit(autorizacionDet);
        }
    }

    void updateConceptosError(Map<Integer, String> serviceResponse, String codigoConcepto, boolean esPrimerEnvio) {
        if (esPrimerEnvio) {

            System.out.println("primerEnvio");

            if (serviceResponse.get(ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS) != null) {
                mensajeError += serviceResponse.get(ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS) + " : " + codigoConcepto + "\n";
                conceptosError += codigoConcepto + ",";
            }
        } else {
            System.out.println("segundoEnvio");
            if (serviceResponse.get(
                    ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS) != null) {

                System.out.println("esError");

                if (!(serviceResponse.get(
                        ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS))
                        .contains(YA_EXISTE_NOVEDAD_KACTUS)) {

                    System.out.println("Novedad no existe");

                    mensajeError += serviceResponse.get(
                            ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS) + " : " + codigoConcepto + "\n";
                    if (!conceptosError.contains(codigoConcepto)) {
                        conceptosError += codigoConcepto + ",";
                    }
                } else {

                    System.out.println("Ya existe novedad");

                    if (conceptosError.contains(codigoConcepto)) {
                        conceptosError = conceptosError.replaceAll(codigoConcepto + ",", "");
                    }
                }

            } else {

                System.out.println("No es error");

                if (conceptosError.contains(codigoConcepto)) {
                    conceptosError = conceptosError.replaceAll(codigoConcepto + ",", "");
                }
            }
        }
    }

}
