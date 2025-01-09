package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.KmConciliadoFacadeLocal;
import com.movilidad.ejb.PrgTcResumenFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.KmConciliado;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.util.beans.InformeContabilidad;
import com.movilidad.util.beans.InformeContabilidad235;
import com.movilidad.util.beans.InformeContabilidadNo235;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "informeContabilidadBean")
@ViewScoped
public class InformeContabilidadJSFManagedBean implements Serializable {

    @EJB
    private KmConciliadoFacadeLocal kmConciliadoEjb;
    @EJB
    private PrgTcResumenFacadeLocal prgTcResumenEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    private Date fecha_inicio;
    private Date fecha_fin;
    private Date fecha_inicio_CX;
    private Date fecha_fin_CX;
    private Date fecha;
    private StreamedContent file;

    private List<KmConciliado> lstKmConciliado;
    private List<InformeContabilidad> lstKmContablidad;
    private List<InformeContabilidad235> lstKmContablidad235;
    private List<InformeContabilidadNo235> lstKmContablidadNo235;

    @PostConstruct
    public void init() {
        llenarFechas();
    }

    public boolean validarUrlBMO() {
        String urlComparar = Util.URL_BMO;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        return url.contains(urlComparar);
    }

    public void onChange(TabChangeEvent event) {
        PrimeFaces primefaces = PrimeFaces.current();
        Tab activeTab = event.getTab();
        switch (activeTab.getId()) {
            case "tbLiqDiaria":
                lstKmConciliado = null;
                fecha = null;
                primefaces.ajax().update("frmCargaKmConciliado:tabView:PGridDiaria");
                primefaces.ajax().update("frmCargaKmConciliado:tabView:pGridLiquidacion");
                break;
            case "tbInforme":
                fecha_inicio = MovilidadUtil.fechaHoy();
                fecha_fin = MovilidadUtil.fechaHoy();
                lstKmContablidad = null;
                primefaces.ajax().update("frmCargaKmConciliado:tabView:pGridReporte");
                primefaces.ajax().update("frmCargaKmConciliado:tabView:pGridInforme");
                break;
            case "tbInforme235":
                fecha_inicio = MovilidadUtil.fechaHoy();
                fecha_fin = MovilidadUtil.fechaHoy();
                lstKmContablidad235 = null;
                primefaces.ajax().update("frmCargaKmConciliado:tabView:pGridInforme235");
                break;
        }
    }

    public void liquidar() {
        BigDecimal b_Total_Art;
        BigDecimal b_Total_Bi;
        PrgTcResumen prgTcResumen = prgTcResumenEjb.findByFecha(fecha,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (prgTcResumen == null) {
            MovilidadUtil.addErrorMessage("No hay programación para el día " + Util.dateFormat(fecha));
            return;
        }
        if (prgTcResumen.getConciliado() == null || prgTcResumen.getConciliado() == 0) {
            MovilidadUtil.addErrorMessage("El día a liquidar no ha sido conciliado");
            return;
        }

        b_Total_Art = obtenerVacioArt(prgTcResumen);
        b_Total_Bi = obtenerVacioBi(prgTcResumen);

        lstKmConciliado = kmConciliadoEjb.findDate(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstKmContablidad = null;
        int totalArt = kmConciliadoEjb.totalVehiculos(fecha, 1, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        int totalBi = kmConciliadoEjb.totalVehiculos(fecha, 2, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        BigDecimal vRComercialArt;
        BigDecimal vRComercialBi;

        for (KmConciliado k : lstKmConciliado) {
            if (k.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo() == 1) {
                vRComercialArt = b_Total_Art.divide(new BigDecimal(totalArt), 2, RoundingMode.HALF_UP);
//                System.out.println("Km comercial ART: " + k.getKmComercial());
//                System.out.println("SUM. ART: " + b_Total_Art);
//                System.out.println("VRComercial ART: " + vRComercialArt);
//                System.out.println("TOTAL ART: " + totalArt);
                k.setKmContabilidad(vRComercialArt.add(new BigDecimal(k.getKmComercial())));
//                System.out.println("Km Contabilidad ART: " + k.getKmContabilidad());
                kmConciliadoEjb.edit(k);
            } else {
                vRComercialBi = b_Total_Bi.divide(new BigDecimal(totalBi), 2, RoundingMode.HALF_UP);
//                System.out.println("Km comercial BI: " + k.getKmComercial());
//                System.out.println("SUM. BI: " + b_Total_Bi);
//                System.out.println("VRComercial BI: " + vRComercialBi);
//                System.out.println("TOTAL BI: " + totalBi);
                k.setKmContabilidad(vRComercialBi.add(new BigDecimal(k.getKmComercial())));
//                System.out.println("Km Contabilidad BI: " + k.getKmContabilidad());
                kmConciliadoEjb.edit(k);
            }
        }
        MovilidadUtil.addSuccessMessage("Liquidación contable realizada con éxito");
    }

    private BigDecimal obtenerVacioArt(PrgTcResumen resumen) {
        if (resumen.getHlpOptArt().compareTo(Util.CERO) == 1) {
            return resumen.getHlpOptArt().multiply(resumen.getFactorArt());
        } else if (resumen.getHlpNoptArt().compareTo(Util.CERO) == 1) {
            return resumen.getHlpNoptArt().multiply(resumen.getFactorArt());
        }
        return Util.CERO;
    }

    private BigDecimal obtenerVacioBi(PrgTcResumen resumen) {
        if (resumen.getHlpOptBi().compareTo(Util.CERO) == 1) {
            return resumen.getHlpOptBi().multiply(resumen.getFactorBi());
        } else if (resumen.getHlpNoptBi().compareTo(Util.CERO) == 1) {
            return resumen.getHlpNoptBi().multiply(resumen.getFactorBi());
        }
        return Util.CERO;

    }

    public void cargarReporte() {
        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        if (validarUrlBMO()) {
            lstKmConciliado = null;
            lstKmContablidad = kmConciliadoEjb.obtenerRangoFechas(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

            if (lstKmContablidad == null) {
                MovilidadUtil.addErrorMessage("Error!!");
                return;
            }

            if (lstKmContablidad.isEmpty()) {
                MovilidadUtil.addErrorMessage("No se encontraron días liquidados");
                return;
            }

            for (InformeContabilidad ic : lstKmContablidad) {
                if (ic.getTotal() == null) {
                    ic.setTotal(BigDecimal.ZERO);
                }
            }
            MovilidadUtil.addSuccessMessage("Reporte generado con éxito");
        } else {
            lstKmContablidadNo235 = null;
            lstKmContablidadNo235 = kmConciliadoEjb.obtenerRangoFechasNo235(fecha_inicio_CX, fecha_fin_CX);
        }
    }

    public void generarReporte() throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        String validacion = null;
        file = null;
        if (validarUrlBMO()) {
            plantilla = plantilla + "Contabilidad.xlsx";
            parametros.put("kmsContabilidad", lstKmContablidad);
        } else {
            cargarReporte();
            validacion = this.validarCargaReporte();

            if (validacion != null) {
                MovilidadUtil.addErrorMessage(validacion);
                return;
            }

            plantilla = plantilla + "ContabilidadNo235.xlsx";
            parametros.put("kmsContabilidad", lstKmContablidadNo235);
        }
        destino = destino + "Kilómetros_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        if (validarUrlBMO()) {
            file = new DefaultStreamedContent(stream, "text/plain", "Kilómetros_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx");
        } else {
            file = new DefaultStreamedContent(stream, "text/plain", "Kilómetros_" + Util.dateFormat(fecha_inicio_CX) + "_al_" + Util.dateFormat(fecha_fin_CX) + ".xlsx");
        }
    }

    public void generarReporte235() throws FileNotFoundException {

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        lstKmContablidad235 = null;
        lstKmContablidad235 = kmConciliadoEjb.obtenerRangoFechas235(fecha_inicio, fecha_fin);

        if (lstKmContablidad235 == null) {
            MovilidadUtil.addErrorMessage("Error!!");
            return;
        }

        if (lstKmContablidad235.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron días liquidados");
            return;
        }

        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        file = null;
        plantilla = plantilla + "Contabilidad235.xlsx";
        parametros.put("kmsContabilidad", lstKmContablidad235);
        destino = destino + "Kilómetros_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "Kilómetros_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx");
    }

    public String formatearFecha(Date fecha) {
        return Util.dateFormat(fecha);
    }

    private String validarCargaReporte() {

        if (Util.validarFechaCambioEstado(fecha_inicio_CX, fecha_fin_CX)) {
            return "La fecha de inicio no puede ser mayor a la fecha fin";
        }

        if (lstKmContablidadNo235 == null || lstKmContablidadNo235.isEmpty()) {
            return "No se encontraron datos";
        }

        return null;
    }

    private void llenarFechas() {
        fecha_fin = MovilidadUtil.fechaHoy();
        fecha_inicio = MovilidadUtil.fechaHoy();
        fecha_inicio_CX = MovilidadUtil.fechaHoy();
        fecha_fin_CX = MovilidadUtil.fechaHoy();
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

    public List<KmConciliado> getLstKmConciliado() {
        return lstKmConciliado;
    }

    public void setLstKmConciliado(List<KmConciliado> lstKmConciliado) {
        this.lstKmConciliado = lstKmConciliado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<InformeContabilidad> getLstKmContablidad() {
        return lstKmContablidad;
    }

    public void setLstKmContablidad(List<InformeContabilidad> lstKmContablidad) {
        this.lstKmContablidad = lstKmContablidad;
    }

    public List<InformeContabilidad235> getLstKmContablidad235() {
        return lstKmContablidad235;
    }

    public void setLstKmContablidad235(List<InformeContabilidad235> lstKmContablidad235) {
        this.lstKmContablidad235 = lstKmContablidad235;
    }

    public List<InformeContabilidadNo235> getLstKmContablidadNo235() {
        return lstKmContablidadNo235;
    }

    public void setLstKmContablidadNo235(List<InformeContabilidadNo235> lstKmContablidadNo235) {
        this.lstKmContablidadNo235 = lstKmContablidadNo235;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public Date getFecha_inicio_CX() {
        return fecha_inicio_CX;
    }

    public void setFecha_inicio_CX(Date fecha_inicio_CX) {
        this.fecha_inicio_CX = fecha_inicio_CX;
    }

    public Date getFecha_fin_CX() {
        return fecha_fin_CX;
    }

    public void setFecha_fin_CX(Date fecha_fin_CX) {
        this.fecha_fin_CX = fecha_fin_CX;
    }
}
