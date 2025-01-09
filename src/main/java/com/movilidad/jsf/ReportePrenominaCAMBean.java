package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.model.ParamArea;
import com.movilidad.util.beans.PrenominaCAM;
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
@Named(value = "reportePrenominaCAMBean")
@ViewScoped
public class ReportePrenominaCAMBean implements Serializable {

    @EJB
    private GenericaJornadaFacadeLocal genericaJornadaEjb;

    @EJB
    private ParamAreaFacadeLocal paramAreaEjb;

    private Locale locale = new Locale("es", "CO");
    private Calendar current = Calendar.getInstance(locale);

    private Date fecha_inicio;
    private Date fecha_fin;
    private StreamedContent file;

    private Integer[] areasSeleccionadas;

    private List<PrenominaCAM> lstReporte;
    private List<ParamArea> lstAreas;

    public void generarReporte() throws FileNotFoundException {

        file = null;

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }
        if (areasSeleccionadas.length > 0) {
            lstReporte = genericaJornadaEjb.obtenerDatosInformePrenominaCAMPorArea(fecha_inicio, fecha_fin, areasSeleccionadas);
        } else {
            MovilidadUtil.addErrorMessage("Debe seleccionar al menos 1 área");
        }

        if (lstReporte == null || lstReporte.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        generarExcel();
    }

    private void generarExcel() throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "PRENOMINA CONSORCIO CABLEMOVIL.xlsx";
        parametros.put("prenomina", lstReporte);

        destino = destino + "PRENOMINA_CAM.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        MovilidadUtil.addSuccessMessage("Reporte generado éxitosamente");
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "PRENOMINA_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx");
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

    public List<PrenominaCAM> getLstReporte() {
        return lstReporte;
    }

    public void setLstReporte(List<PrenominaCAM> lstReporte) {
        this.lstReporte = lstReporte;
    }

    public void setAreasSeleccionadas(Integer[] areasSeleccionadas) {
        this.areasSeleccionadas = areasSeleccionadas;
    }

    public List<ParamArea> getLstAreas() {
        return paramAreaEjb.findAllEstadoReg();
    }

    public void setLstAreas(List<ParamArea> lstAreas) {
        this.lstAreas = lstAreas;
    }

}
