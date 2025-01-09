package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.model.ParamArea;
import com.movilidad.util.beans.ReporteHoras;
import com.movilidad.util.beans.ReporteHorasExcel;
import com.movilidad.util.beans.ReporteInterventoria;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
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
@Named(value = "reporteInterventoriaBean")
@ViewScoped
public class ReporteInterventoriaManagedBean implements Serializable {

    @EJB
    private GenericaJornadaFacadeLocal genericaJornadaEjb;

    @EJB
    private ParamAreaFacadeLocal paramAreaEjb;

    private Locale locale = new Locale("es", "CO");
    private Calendar current = Calendar.getInstance(locale);

    private final String[] meses = {
        "enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    };

    private Date fecha_inicio;
    private Date fecha_fin;
    private StreamedContent file;

    private Integer[] areasSeleccionadas;

    private List<ReporteInterventoria> lstReporteInterventoria;
    private List<ParamArea> lstAreas;

    public void generarReporte() throws FileNotFoundException {

        file = null;
        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }
        if (areasSeleccionadas.length > 0) {
            lstReporteInterventoria = genericaJornadaEjb.obtenerDatosInfoInterventoriaConArea(fecha_inicio, fecha_fin, areasSeleccionadas);
        } else {
            lstReporteInterventoria = genericaJornadaEjb.obtenerDatosInfoInterventoriaSinArea(fecha_inicio, fecha_fin);
        }

        if (lstReporteInterventoria.isEmpty() || lstReporteInterventoria == null) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }

        generarExcel();
    }

    private void generarExcel() throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Reporte Interventoria.xlsx";
        parametros.put("reporteInterventoria", lstReporteInterventoria);
        parametros.put("dIni", obtenerDia(fecha_inicio));
        parametros.put("dFin", obtenerDia(fecha_fin));
        parametros.put("mes", obtenerMesParaInforme(fecha_inicio));

        destino = destino + "REPORTE_INTERVENTORIA.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        MovilidadUtil.addSuccessMessage("Reporte generado éxitosamente");
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "REPORTE_INTERVENTORIA_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx");
    }

    private int obtenerMes(Date fecha) {
        current.setTime(fecha);
        return current.get(Calendar.MONTH) + 1;
    }

    private String obtenerMesParaInforme(Date fecha) {
        current.setTime(fecha);
        return meses[current.get(Calendar.MONTH)].toUpperCase() + " DEL " + current.get(Calendar.YEAR);
    }

    public int obtenerDia(Date fecha) {
        current.setTime(fecha);
        return current.get(Calendar.DAY_OF_MONTH);
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

    public Integer[] getAreasSeleccionadas() {
        return areasSeleccionadas;
    }

    public void setAreasSeleccionadas(Integer[] areasSeleccionadas) {
        this.areasSeleccionadas = areasSeleccionadas;
    }

    public List<ReporteInterventoria> getLstReporteInterventoria() {
        return lstReporteInterventoria;
    }

    public void setLstReporteInterventoria(List<ReporteInterventoria> lstReporteInterventoria) {
        this.lstReporteInterventoria = lstReporteInterventoria;
    }

    public List<ParamArea> getLstAreas() {
        return paramAreaEjb.findAllEstadoReg();
    }

    public void setLstAreas(List<ParamArea> lstAreas) {
        this.lstAreas = lstAreas;
    }

}
