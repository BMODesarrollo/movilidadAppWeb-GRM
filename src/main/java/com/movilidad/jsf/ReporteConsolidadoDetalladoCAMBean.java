package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.util.beans.ConsolidadoDetalladoCAM;
import com.movilidad.util.beans.ConsolidadoLiquidacionCAM;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "reporteConsolidadoDetBean")
@ViewScoped
public class ReporteConsolidadoDetalladoCAMBean implements Serializable {

    @EJB
    private GenericaJornadaFacadeLocal genericaJornadaEjb;
    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;

    private Locale locale = new Locale("es", "CO");
    private Calendar current = Calendar.getInstance(locale);

    private Date fecha_inicio;
    private Date fecha_fin;
    private StreamedContent file;

    private List<ConsolidadoLiquidacionCAM> lstConsolidado;
    private List<ConsolidadoDetalladoCAM> lstConsolidadoDetallado;

    public void generarReporte() throws FileNotFoundException {

        Map<String, ParamReporteHoras> hParamHoras;
        List<ParamReporteHoras> lstParamReporteHoras;
        file = null;

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        lstParamReporteHoras = paramReporteHorasEjb.findAllActivos(0);
        lstConsolidado = genericaJornadaEjb.obtenerDatosConsolidadoCAM(fecha_inicio, fecha_fin);
        lstConsolidadoDetallado = genericaJornadaEjb.obtenerDatosConsolidadoDetalladoCAM(fecha_inicio, fecha_fin);

        if (lstParamReporteHoras == null || lstParamReporteHoras.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos (Parametrización Horas)");
            return;
        }
        if (lstConsolidado == null || lstConsolidado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado)");
            return;
        }
        if (lstConsolidadoDetallado == null || lstConsolidadoDetallado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado detallado)");
            return;
        }

        hParamHoras = retornarMapParamHoras(lstParamReporteHoras);

        for (ConsolidadoLiquidacionCAM consolidado : lstConsolidado) {

            if (consolidado.getNocturnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_NOCTURNAS);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getNocturnas().doubleValue() * recargo;
                    consolidado.setSalario_nocturno(valor);
                }
            }
            if (consolidado.getExtra_diurna().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_EXTRA_DIURNA);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getExtra_diurna().doubleValue() * recargo;
                    consolidado.setSalario_extra_diurno(valor);
                }
            }
            if (consolidado.getExtra_nocturna().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_EXTRA_NOCTURNA);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getExtra_nocturna().doubleValue() * recargo;
                    consolidado.setSalario_extra_nocturno(valor);
                }
            }
            if (consolidado.getFestivo_diurno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_DIURNO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getFestivo_diurno().doubleValue() * recargo;
                    consolidado.setSalario_festivo_diurno(valor);
                }
            }
            if (consolidado.getFestivo_nocturno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_NOCTURNO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getFestivo_nocturno().doubleValue() * recargo;
                    consolidado.setSalario_festivo_nocturno(valor);
                }
            }
            if (consolidado.getFestivo_extra_diurno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getFestivo_extra_diurno().doubleValue() * recargo;
                    consolidado.setSalario_festivo_extra_diurno(valor);
                }
            }
            if (consolidado.getFestivo_extra_nocturno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getFestivo_extra_nocturno().doubleValue() * recargo;
                    consolidado.setSalario_festivo_extra_nocturno(valor);
                }
            }
            if (consolidado.getDominical_comp_diurnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getDominical_comp_diurnas().doubleValue() * recargo;
                    consolidado.setSalario_dominical_diurno(valor);
                }
            }
            if (consolidado.getDominical_comp_nocturnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getDominical_comp_nocturnas().doubleValue() * recargo;
                    consolidado.setSalario_dominical_nocturno(valor);
                }
            }
            if (consolidado.getDominical_comp_diurna_extra().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getDominical_comp_diurna_extra().doubleValue() * recargo;
                    consolidado.setSalario_dominical_con_comp_extra_diurno(valor);
                }
            }
            if (consolidado.getDominical_comp_nocturna_extra().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = consolidado.getHora() * consolidado.getDominical_comp_nocturna_extra().doubleValue() * recargo;
                    consolidado.setSalario_dominical_con_comp_extra_nocturno(valor);
                }
            }

            double total = consolidado.getSalario_nocturno() + consolidado.getSalario_extra_diurno()
                    + consolidado.getSalario_extra_nocturno() + consolidado.getSalario_festivo_diurno()
                    + consolidado.getSalario_festivo_nocturno() + consolidado.getSalario_festivo_extra_diurno()
                    + consolidado.getSalario_festivo_extra_nocturno() + consolidado.getSalario_dominical_diurno()
                    + consolidado.getSalario_dominical_nocturno() + consolidado.getSalario_dominical_con_comp_extra_diurno()
                    + consolidado.getSalario_dominical_con_comp_extra_nocturno();

            consolidado.setTotal(total);

        }

        for (ConsolidadoDetalladoCAM detallado : lstConsolidadoDetallado) {

            if (detallado.getNocturnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_NOCTURNAS);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getNocturnas().doubleValue() * recargo;
                    detallado.setSalario_nocturno(valor);
                }
            }
            if (detallado.getExtra_diurna().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_EXTRA_DIURNA);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getExtra_diurna().doubleValue() * recargo;
                    detallado.setSalario_extra_diurno(valor);
                }
            }
            if (detallado.getExtra_nocturna().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_EXTRA_NOCTURNA);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getExtra_nocturna().doubleValue() * recargo;
                    detallado.setSalario_extra_nocturno(valor);
                }
            }
            if (detallado.getFestivo_diurno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_DIURNO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getFestivo_diurno().doubleValue() * recargo;
                    detallado.setSalario_festivo_diurno(valor);
                }
            }
            if (detallado.getFestivo_nocturno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_NOCTURNO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getFestivo_nocturno().doubleValue() * recargo;
                    detallado.setSalario_festivo_nocturno(valor);
                }
            }
            if (detallado.getFestivo_extra_diurno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getFestivo_extra_diurno().doubleValue() * recargo;
                    detallado.setSalario_festivo_extra_diurno(valor);
                }
            }
            if (detallado.getFestivo_extra_nocturno().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getFestivo_extra_nocturno().doubleValue() * recargo;
                    detallado.setSalario_festivo_extra_nocturno(valor);
                }
            }
            if (detallado.getDominical_comp_diurnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getDominical_comp_diurnas().doubleValue() * recargo;
                    detallado.setSalario_dominical_diurno(valor);
                }
            }
            if (detallado.getDominical_comp_nocturnas().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getDominical_comp_nocturnas().doubleValue() * recargo;
                    detallado.setSalario_dominical_nocturno(valor);
                }
            }
            if (detallado.getDominical_comp_diurna_extra().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getDominical_comp_diurna_extra().doubleValue() * recargo;
                    detallado.setSalario_dominical_con_comp_extra_diurno(valor);
                }
            }
            if (detallado.getDominical_comp_nocturna_extra().compareTo(Util.CERO) == 1) {
                ParamReporteHoras paramReporteHoras = hParamHoras.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO);
                if (paramReporteHoras != null) {
                    Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                    float recargo = (float) pRecargo / 100;
                    double valor = detallado.getHora() * detallado.getDominical_comp_nocturna_extra().doubleValue() * recargo;
                    detallado.setSalario_dominical_con_comp_extra_nocturno(valor);
                }
            }

            double total = detallado.getSalario_nocturno() + detallado.getSalario_extra_diurno()
                    + detallado.getSalario_extra_nocturno() + detallado.getSalario_festivo_diurno()
                    + detallado.getSalario_festivo_nocturno() + detallado.getSalario_festivo_extra_diurno()
                    + detallado.getSalario_festivo_extra_nocturno() + detallado.getSalario_dominical_diurno()
                    + detallado.getSalario_dominical_nocturno() + detallado.getSalario_dominical_con_comp_extra_diurno()
                    + detallado.getSalario_dominical_con_comp_extra_nocturno();

            detallado.setTotal(total);

        }

        generarExcel();
    }

    private void generarExcel() throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Consolidado Liquidacion CAM.xlsx";
        parametros.put("desde", Util.dateFormat(fecha_inicio));
        parametros.put("hasta", Util.dateFormat(fecha_fin));
        parametros.put("generado", Util.dateFormat(new Date()));
        parametros.put("liquidacion", lstConsolidado);
        parametros.put("liquidacionDet", lstConsolidadoDetallado);

        destino = destino + "REPORTE_GENERAL_NOMINA.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        MovilidadUtil.addSuccessMessage("Reporte generado éxitosamente");
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "REPORTE_GENERAL_NOMINA_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx");
    }

    private Map<String, ParamReporteHoras> retornarMapParamHoras(List<ParamReporteHoras> lstParamReporteHoras) {
        Map<String, ParamReporteHoras> hParamHoras = new HashMap<>();
        for (ParamReporteHoras rph : lstParamReporteHoras) {
            hParamHoras.put(rph.getTipoHora(), rph);
        }

        return hParamHoras;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Calendar getCurrent() {
        return current;
    }

    public void setCurrent(Calendar current) {
        this.current = current;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

}
