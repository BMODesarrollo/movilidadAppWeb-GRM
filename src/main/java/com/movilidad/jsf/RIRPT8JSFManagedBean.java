/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.util.beans.ConsolidadoLiquidacionGMO;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author solucionesit
 *
 * Costo de mano de obra, detalle de conceptos, variación mensual.
 *
 * Tiempo suplementario ejecutado
 */
@Named(value = "rIRPT8Bean")
@ViewScoped
public class RIRPT8JSFManagedBean implements Serializable {

    /**
     * Creates a new instance of RIRPT8JSFManagedBean
     */
    public RIRPT8JSFManagedBean() {
    }
    private Date desde;
    private Date hasta;
    private List<ConsolidadoLiquidacionGMO> lstConsolidadoQuincenal;
    private List<ConsolidadoLiquidacionGMO> lstConsolidadoDetallado;
    private Map<String, ParamReporteHoras> hParamHoras;
    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private StreamedContent file;

    private void retornarMapParamHoras(List<ParamReporteHoras> lstParamReporteHoras) {
        hParamHoras = new HashMap<>();
        for (ParamReporteHoras rph : lstParamReporteHoras) {
            hParamHoras.put(rph.getTipoHora(), rph);
        }
    }

    private String nombreExcel() {
        return "costo_mano_obra_detalle_concepto_variación_mensual " + Util.dateFormat(desde) + "-"
                + Util.dateFormat(hasta);
    }

    public void generarReporte() throws ParseException {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar la Unidad Funcional.");
            return;
        }
        cardarData();
        generarExcel();
    }

    public void generarExcel() {
        try {
            Map parametros = new HashMap();
            parametros.put("quincenas", lstConsolidadoQuincenal);
            parametros.put("df", Util.DATE_FORMAT);
            String destino = "/tmp/ri-rpt-08.xlsx";
            String plantilla = "/rigel/reportes/ri-rpt-08.xlsx";
            GeneraXlsx.generar(plantilla, destino, parametros);
            File excel = new File(destino);
            InputStream stream = new FileInputStream(excel);
            file = new DefaultStreamedContent(stream, "text/plain", nombreExcel() + ".xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cardarData() throws ParseException {
        List<ParamReporteHoras> lstParamReporteHoras = paramReporteHorasEjb.findAllActivos(0);
        lstConsolidadoQuincenal = prgSerconEJB.obtenerDatosConsolidadoQuincenal(
                desde, hasta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        lstConsolidadoDetallado = prgSerconEJB.obtenerDatosConsolidadoDetalle(
                desde, hasta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        retornarMapParamHoras(lstParamReporteHoras);
        Map<String, ConsolidadoLiquidacionGMO> mapQuincenas = new HashMap<>();
        for (ConsolidadoLiquidacionGMO quincena : lstConsolidadoQuincenal) {
            mapQuincenas.put(quincena.getQuincena(), quincena);
        }

        for (ConsolidadoLiquidacionGMO detalle : lstConsolidadoDetallado) {

            if (detalle.getNocturnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_NOCTURNAS);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoNocturno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getNocturnas().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoNocturno(valor);
                }
            }
            if (detalle.getExtraDiurna().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_EXTRA_DIURNA);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoExtraDiurno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getExtraDiurna().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoExtraDiurno(valor);
                }
            }
            if (detalle.getExtraNocturna().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_EXTRA_NOCTURNA);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoExtraNocturno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getExtraNocturna().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoExtraNocturno(valor);
                }
            }
            if (detalle.getFestivoDiurno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_DIURNO);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoFestivoDiurno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getFestivoDiurno().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoFestivoDiurno(valor);
                }
            }
            if (detalle.getFestivoNocturno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_NOCTURNO);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoFestivoNocturno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getFestivoNocturno().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoFestivoNocturno(valor);
                }
            }
            if (detalle.getFestivoExtraDiurno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoFestivoExtraDiurno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getFestivoExtraDiurno().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoFestivoExtraDiurno(valor);
                }
            }
            if (detalle.getFestivoExtraNocturno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoFestivoExtraNocturno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getFestivoExtraNocturno().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoFestivoExtraNocturno(valor);
                }
            }
            if (detalle.getDominicalCompDiurnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoDominicalDiurno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getDominicalCompDiurnas().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoDominicalDiurno(valor);
                }
            }
            if (detalle.getDominicalCompNocturnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoDominicalNocturno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getDominicalCompNocturnas().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoDominicalNocturno(valor);
                }
            }
            if (detalle.getDominicalCompDiurnaExtra().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoDominicalConCompExtraDiurno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getDominicalCompDiurnaExtra().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoDominicalConCompExtraDiurno(valor);
                }
            }
            if (detalle.getDominicalCompNocturnaExtra().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    double valor = mapQuincenas.get(detalle.getQuincena()).getCostoDominicalConcompExtraNocturno()
                            + calcularCosto(paramReporteHoras,
                                    detalle.getDominicalCompNocturnaExtra().doubleValue(),
                                    detalle.getCostoHora());
                    mapQuincenas.get(detalle.getQuincena()).setCostoDominicalConcompExtraNocturno(valor);
                }
            }
        }
        lstConsolidadoQuincenal = new ArrayList<ConsolidadoLiquidacionGMO>(mapQuincenas.values());
        Collections.sort(lstConsolidadoQuincenal, Comparator.comparing(item -> item.getQuincena()));
    }

    /**
     *
     * @param paramReporteHoras
     * @param totalHoras
     * @param costoHora
     * @return
     */
    private double calcularCosto(ParamReporteHoras paramReporteHoras, double totalHoras, double costoHora) {
        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

        float recargo = (float) pRecargo / 100;
        double valor = costoHora * totalHoras * recargo;
        return valor;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

}
