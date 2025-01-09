/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.kactus.ws.IKWsInovo;
import com.kactus.ws.KWsInovo;
import com.kactus.ws.RpNovedades;
import com.movilidad.ejb.NominaAutorizacionDetIndividualFacadeLocal;
import com.movilidad.ejb.NominaServerParamEmpresaFacadeLocal;
import com.movilidad.ejb.NominaServerParamFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NominaAutorizacionDetIndividual;
import com.movilidad.model.NominaAutorizacionIndividual;
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
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "kactusNovIndvBean")
@ViewScoped
public class KactusNovedadIndBean implements Serializable {

    /**
     * Creates a new instance of KactusNovedadBean
     */
    public KactusNovedadIndBean() {
    }
    @EJB
    private NominaServerParamFacadeLocal nominaServerParamEjb;

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;

    @EJB
    private NominaServerParamEmpresaFacadeLocal nominaServerParamEmpresaEjb;
    @EJB
    private NominaAutorizacionDetIndividualFacadeLocal autorizacionDetEJB;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private HashMap<String, ParamReporteHoras> hReporte;
    private HashMap<String, NominaAutorizacionDetIndividual> mapNominaAutoDet;
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

    private void cargarMapDetalles(Integer idNominaAutorizacion) {
        mapNominaAutoDet = new HashMap<>();
        List<NominaAutorizacionDetIndividual> detalles
                = autorizacionDetEJB.findByIdNominaAutorizacion(idNominaAutorizacion);

        for (NominaAutorizacionDetIndividual obj : detalles) {
            mapNominaAutoDet.put(obj.getIdentificacion(), obj);
        }
    }

    private boolean cargarReporteHoras(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado) {
        lstReporteHoras = paramReporteHorasEjb.obtenerDatosReporteByFechasAndUfAndIdEmpleado(desde, hasta, idGopUnidadFuncional, idEmpleado);
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

    boolean sendToKactus(NominaAutorizacionIndividual autorizacionNomina, String fechaPago)
            throws DatatypeConfigurationException, MalformedURLException {
        cargarNominaServerParam();
        cargarMapDetalles(autorizacionNomina.getIdNominaAutorizacionIndividual());
        if (nominaServerParam == null) {
            MovilidadUtil.addErrorMessage("No se encontró parametrizacion de Nomina Server Param.");
            return false;
        }
        cargarNominaServerParamEmpresa(nominaServerParam.getIdNominaServerParam(),
                autorizacionNomina.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        if (nominaServerParamEmpresa == null) {
            MovilidadUtil.addErrorMessage("No se encontró parametrizacion de Nomina Server Param Empresa.");
            return false;
        }
        if (cargarReporteHoras(autorizacionNomina.getDesde(), autorizacionNomina.getHasta(),
                autorizacionNomina.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                autorizacionNomina.getIdEmpleado().getIdEmpleado())) {
            return false;
        }
        cargarMapParamReporteHoras();

        KWsInovo kws = new KWsInovo(new URL(nominaServerParam.getUrlWs()));
        IKWsInovo iKws = kws.getBasicHttpBindingIKWsInovo();

        for (ReporteHoras reporteHora : lstReporteHoras) {
            mensajeError = "";
            conceptosError = "";

            NominaAutorizacionDetIndividual item = mapNominaAutoDet.get(reporteHora.getIdentificacion());

            if (item == null) {
                enviarNovedades(reporteHora, iKws, fechaPago, autorizacionNomina, true, item);
            } else {
                if (item.getMensajeError() != null) {
                    item.setMensajeError("");
                    conceptosError = item.getConceptos();
                    enviarNovedades(reporteHora, iKws, fechaPago, autorizacionNomina, false, item);
                }
            }

        }

        return true;

    }

    void enviarNovedades(ReporteHoras reporteHora, IKWsInovo iKws,
            String fechaPago, NominaAutorizacionIndividual autorizacionNomina,
            boolean esPrimerEnvio, NominaAutorizacionDetIndividual item) throws NumberFormatException {
        Map<Integer, String> serviceResponse;
        if (reporteHora.getNocturnas().compareTo(Util.CERO) == 1) {
            serviceResponse = new HashMap();
            ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_NOCTURNAS);
            if (paramReporteHoras != null) {

                if (item == null || (estaConceptoEnListaErrores(item.getConceptos(), paramReporteHoras.getCodigo()))) {
                    enviarNovedadKactus(iKws, fechaPago, reporteHora.getNocturnas().doubleValue(),
                            Integer.parseInt(paramReporteHoras.getCodigo()),
                            Long.parseLong(reporteHora.getIdentificacion()));

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);

                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getNocturnas() == null) {
                        detalle.setNocturnas(reporteHora.getNocturnas());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getExtraDiurna() == null) {
                        detalle.setExtraDiurna(reporteHora.getExtra_diurna());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getExtraNocturna() == null) {
                        detalle.setExtraNocturna(reporteHora.getExtra_nocturna());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getFestivoDiurno() == null) {
                        detalle.setFestivoDiurno(reporteHora.getFestivo_diurno());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getFestivoNocturno() == null) {
                        detalle.setFestivoNocturno(reporteHora.getFestivo_nocturno());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getFestivoExtraDiurno() == null) {
                        detalle.setFestivoExtraDiurno(reporteHora.getFestivo_extra_diurno());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getFestivoExtraNocturno() == null) {
                        detalle.setFestivoExtraNocturno(reporteHora.getFestivo_extra_nocturno());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getDominicalCompDiurnas() == null) {
                        detalle.setDominicalCompDiurnas(reporteHora.getDominical_comp_diurnas());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getDominicalCompNocturnas() == null) {
                        detalle.setDominicalCompNocturnas(reporteHora.getDominical_comp_nocturnas());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getDominicalCompDiurnaExtra() == null) {
                        detalle.setDominicalCompDiurnaExtra(reporteHora.getDominical_comp_diurna_extra());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
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

                    updateConceptosError(serviceResponse, paramReporteHoras.getCodigo(), esPrimerEnvio);
                    //Detalle de la autorizacion
                    NominaAutorizacionDetIndividual detalle = validarDetalleSaved(reporteHora, autorizacionNomina);
                    if (detalle.getDominicalCompNocturnaExtra() == null) {
                        detalle.setDominicalCompNocturnaExtra(reporteHora.getDominical_comp_nocturna_extra());
                        saveNominaAutorizacionDetIndividual(detalle);
                    } else {
                        saveNominaAutorizacionDetIndividual(detalle);
                    }
                }
            }
        }
    }

    /**
     *
     * @param iKws
     * @param fechaPago
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

    boolean estaConceptoEnListaErrores(String conceptos, String concepto) {
        if (conceptos == null || concepto == null) {
            return false;
        }
        return conceptos.contains(concepto);
    }

    NominaAutorizacionDetIndividual builNominaAutorizacionDetIndividual(String identificacion,
            GopUnidadFuncional uf,
            NominaAutorizacionIndividual autorizacionNomina) {
        NominaAutorizacionDetIndividual autorizacionDet = new NominaAutorizacionDetIndividual();
        autorizacionDet.setIdentificacion(identificacion);
        autorizacionDet.setCreado(MovilidadUtil.fechaCompletaHoy());
        autorizacionDet.setEstadoReg(0);
        autorizacionDet.setUsername(user.getUsername());
        autorizacionDet.setIdGopUnidadFuncional(uf);
        autorizacionDet.setIdNominaAutorizacionIndividual(autorizacionNomina);
        return autorizacionDet;
    }

    NominaAutorizacionDetIndividual validarDetalleSaved(ReporteHoras reporteHora, NominaAutorizacionIndividual autorizacionNomina) {
        NominaAutorizacionDetIndividual get = mapNominaAutoDet.get(reporteHora.getIdentificacion());
        if (get == null) {
            get = builNominaAutorizacionDetIndividual(reporteHora.getIdentificacion(),
                    autorizacionNomina.getIdGopUnidadFuncional(),
                    autorizacionNomina);
            mapNominaAutoDet.put(reporteHora.getIdentificacion(), get);
        }
        return get;
    }

    void saveNominaAutorizacionDetIndividual(NominaAutorizacionDetIndividual autorizacionDet) {

        if (mensajeError != null && !mensajeError.isEmpty()) {
            autorizacionDet.setCodigoError(ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS);
            autorizacionDet.setMensajeError(mensajeError);
            autorizacionDet.setConceptos(conceptosError);
        } else {
            autorizacionDet.setCodigoError(null);
            autorizacionDet.setMensajeError(null);
            autorizacionDet.setConceptos(null);
        }

        if (autorizacionDet.getIdNominaAutorizacionIndividual() == null) {
            autorizacionDetEJB.create(autorizacionDet);
            mapNominaAutoDet.put(autorizacionDet.getIdentificacion(), autorizacionDet);
        } else {
            autorizacionDet.setModificado(MovilidadUtil.fechaCompletaHoy());
            autorizacionDet.setUsername(user.getUsername());
            autorizacionDetEJB.edit(autorizacionDet);
        }
    }

    void updateConceptosError(Map<Integer, String> serviceResponse,
            String codigoConcepto, boolean esPrimerEnvio) {
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
