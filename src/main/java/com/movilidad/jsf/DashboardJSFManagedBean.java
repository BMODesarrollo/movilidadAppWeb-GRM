/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.util.beans.ConsolidadServicios;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;

/**
 *
 * @author solucionesit
 */
@Named(value = "dashboardJSFMB")
@ViewScoped
public class DashboardJSFManagedBean implements Serializable {

    private BarChartModel barModel;
    private LineChartModel diagramaHora;
    private LineChartModel diagramaDia;
    private LineChartModel diagramaTrimestre;
    private Date fecha1 = MovilidadUtil.fechaHoy();
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();
    private List<ConsolidadServicios> listaConsolidadoKm;
    private BigDecimal f = BigDecimal.ZERO;
    private boolean flag_hora = false;
    private boolean flag_dia = false;
    private boolean flag_ultimo_trimestre = false;

    @EJB
    private PrgTcFacadeLocal prgTcEJB;

    /**
     * Creates a new instance of DashboardJSFManagedBean
     */
    public DashboardJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        createBarModel();
    }

    public void getByhour() {
        flag_hora = true;
        flag_dia = false;
        flag_ultimo_trimestre = false;
        createLineHora();
    }

    public void getByDay() {
        flag_hora = false;
        flag_dia = true;
        flag_ultimo_trimestre = false;
        createLineDia();
    }

    public void getByTastTrimester() {
        flag_hora = false;
        flag_dia = false;
        flag_ultimo_trimestre = true;
    }

    public void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Consolidado Servicios");

        List<Number> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(101);
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(75, 192, 192, 1)");
        bgColor.add("rgba(0, 128, 0, 1)");
        bgColor.add("rgba(255, 205, 86, 1)");
        bgColor.add("rgba(255, 0, 0, 1)");
        barDataSet.setBackgroundColor(bgColor);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("Programado");
        labels.add("Ejecutado");
        labels.add("Ganados");
        labels.add("Perdido");
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        barModel.setOptions(options);
    }

    private void createLineHora() {
        diagramaHora = initCategoryModelHora();
        Axis yAxis = diagramaHora.getAxis(AxisType.Y);
        diagramaHora.setTitle("Consolidado");
        diagramaHora.setLegendPosition("e");
        diagramaHora.setShowPointLabels(true);
        diagramaHora.getAxes().put(AxisType.X, new CategoryAxis("Días"));
        diagramaHora.setZoom(true);
        yAxis = diagramaHora.getAxis(AxisType.Y);
        yAxis.setLabel("KM");
        yAxis.setMin(0);
        yAxis.setMax(f);

    }

    private void createLineDia() {
        diagramaDia = initCategoryModelDia();
        Axis yAxis = diagramaDia.getAxis(AxisType.Y);
        diagramaDia.setTitle("Consolidado");
        diagramaDia.setLegendPosition("e");
        diagramaDia.setShowPointLabels(true);
        diagramaDia.getAxes().put(AxisType.X, new CategoryAxis("Días"));
        diagramaDia.setZoom(true);
        yAxis = diagramaDia.getAxis(AxisType.Y);
        yAxis.setLabel("KM");
        yAxis.setMin(0);
        yAxis.setMax(f);

    }

    private LineChartModel initCategoryModelHora() {
        listaConsolidadoKm = prgTcEJB.getConsolidadoPorHora(fecha1);

        LineChartModel model = new LineChartModel();
        model.setAnimate(true);
        model.setShadow(true);
        ChartSeries programado = new ChartSeries();
        programado.setLabel("Programado");
        ChartSeries eliminado = new ChartSeries();
        eliminado.setLabel("Eliminado");
        ChartSeries adicional = new ChartSeries();
        adicional.setLabel("Adicional");
        for (ConsolidadServicios c : listaConsolidadoKm) {
            programado.set(c.getId(), c.getProgramado());
            eliminado.set(c.getId(), c.getEliminado());
            adicional.set(c.getId(), c.getAdicional());

            if (c.getProgramado() != null) {
                if (f.compareTo(c.getProgramado()) == -1) {
                    f = c.getProgramado();
                }
            }
            if (c.getEliminado() != null) {
                if (f.compareTo(c.getEliminado()) == -1) {
                    f = c.getEliminado();
                }
            }
            if (c.getAdicional() != null) {
                if (f.compareTo(c.getAdicional()) == -1) {
                    f = c.getAdicional();
                }
            }

        }
        model.addSeries(programado);
        model.addSeries(eliminado);
        model.addSeries(adicional);

        return model;
    }

    private LineChartModel initCategoryModelDia() {
        listaConsolidadoKm = prgTcEJB.getConsolidadoPorDia(desde, hasta);

        LineChartModel model = new LineChartModel();
        model.setAnimate(true);
        model.setShadow(true);
        ChartSeries programado = new ChartSeries();
        programado.setLabel("Programado");
        ChartSeries eliminado = new ChartSeries();
        eliminado.setLabel("Eliminado");
        ChartSeries adicional = new ChartSeries();
        adicional.setLabel("Adicional");
        for (ConsolidadServicios c : listaConsolidadoKm) {
            programado.set(c.getId(), c.getProgramado());
            eliminado.set(c.getId(), c.getEliminado());
            adicional.set(c.getId(), c.getAdicional());

            if (c.getProgramado() != null) {
                if (f.compareTo(c.getProgramado()) == -1) {
                    f = c.getProgramado();
                }
            }
            if (c.getEliminado() != null) {
                if (f.compareTo(c.getEliminado()) == -1) {
                    f = c.getEliminado();
                }
            }
            if (c.getAdicional() != null) {
                if (f.compareTo(c.getAdicional()) == -1) {
                    f = c.getAdicional();
                }
            }

        }
        model.addSeries(programado);
        model.addSeries(eliminado);
        model.addSeries(adicional);

        return model;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public LineChartModel getDiagramaHora() {
        return diagramaHora;
    }

    public void setDiagramaHora(LineChartModel diagramaHora) {
        this.diagramaHora = diagramaHora;
    }

    public LineChartModel getDiagramaDia() {
        return diagramaDia;
    }

    public void setDiagramaDia(LineChartModel diagramaDia) {
        this.diagramaDia = diagramaDia;
    }

    public LineChartModel getDiagramaTrimestre() {
        return diagramaTrimestre;
    }

    public void setDiagramaTrimestre(LineChartModel diagramaTrimestre) {
        this.diagramaTrimestre = diagramaTrimestre;
    }

    public boolean isFlag_hora() {
        return flag_hora;
    }

    public void setFlag_hora(boolean flag_hora) {
        this.flag_hora = flag_hora;
    }

    public boolean isFlag_dia() {
        return flag_dia;
    }

    public void setFlag_dia(boolean flag_dia) {
        this.flag_dia = flag_dia;
    }

    public boolean isFlag_ultimo_trimestre() {
        return flag_ultimo_trimestre;
    }

    public void setFlag_ultimo_trimestre(boolean flag_ultimo_trimestre) {
        this.flag_ultimo_trimestre = flag_ultimo_trimestre;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
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

}
