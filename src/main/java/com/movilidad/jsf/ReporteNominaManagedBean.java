package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ReporteHoras;
import com.movilidad.util.beans.ReporteHorasExcel;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "reporteNominaBean")
@ViewScoped
public class ReporteNominaManagedBean implements Serializable {

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;

    @EJB
    private GenericaFacadeLocal genericaEjb;

    private Locale locale = new Locale("es", "CO");
    private Calendar current = Calendar.getInstance(locale);
    private String area;

    private final String[] meses = {
        "enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    };

    private Date fecha_inicio;
    private Date fecha_fin;
    private StreamedContent file;
    private ParamAreaUsr paramAreaUsr;
    private Integer i_unidad_funcional;
    private List<ReporteHoras> lstReporteHoras;
    private List<ReporteHorasExcel> lstReporteHorasExcel;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        paramAreaUsr = genericaEjb.findByUsername(user.getUsername());
    }

    public void generarReporte() throws FileNotFoundException {

        file = null;
        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        if (paramAreaUsr != null) {
            area = paramAreaUsr.getIdParamArea().getArea().toUpperCase();
        }

        lstReporteHoras = paramReporteHorasEjb.obtenerDatosReporte(fecha_inicio, fecha_fin, i_unidad_funcional != null ? i_unidad_funcional : 0);

        if (lstReporteHoras == null) {
            MovilidadUtil.addErrorMessage("Error!!");
            return;
        }

        if (lstReporteHoras.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }

        cargarDatosAExcel();
        generarExcel();
    }

    private void cargarDatosAExcel() {
        try {
            lstReporteHorasExcel = new ArrayList<>();
            // num , obj
            HashMap<String, ParamReporteHoras> hReporte = new HashMap<>();
            List<ParamReporteHoras> lstParamReporteHoras = paramReporteHorasEjb.findAllActivos(0);

            for (ParamReporteHoras rph : lstParamReporteHoras) {
                hReporte.put(rph.getTipoHora(), rph);
            }

            for (ReporteHoras reporteHora : lstReporteHoras) {
                if (reporteHora.getNocturnas().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_NOCTURNAS);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getNocturnas());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getExtra_diurna().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_EXTRA_DIURNA);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getExtra_diurna());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getExtra_nocturna().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_EXTRA_NOCTURNA);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getExtra_nocturna());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getFestivo_diurno().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_DIURNO);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getFestivo_diurno());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getFestivo_nocturno().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_NOCTURNO);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getFestivo_nocturno());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getFestivo_extra_diurno().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_EXTRA_DIURNO);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getFestivo_extra_diurno());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getFestivo_extra_nocturno().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getFestivo_extra_nocturno());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getDominical_comp_diurnas().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getDominical_comp_diurnas());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getDominical_comp_nocturnas().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getDominical_comp_nocturnas());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getDominical_comp_diurna_extra().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getDominical_comp_diurna_extra());
                        lstReporteHorasExcel.add(r);
                    }
                }
                if (reporteHora.getDominical_comp_nocturna_extra().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO);
                    if (paramReporteHoras != null) {
                        ReporteHorasExcel r = new ReporteHorasExcel();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setConcepto(paramReporteHoras.getConcepto());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setFechaPago(fecha_fin);
                        r.setCantidad(reporteHora.getDominical_comp_nocturna_extra());
                        lstReporteHorasExcel.add(r);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void generarExcel() throws FileNotFoundException {
        Map parametros = new HashMap();
        String periodo = obtenerDia(fecha_inicio) + " al " + obtenerDia(fecha_fin) + " de " + obtenerMesParaInforme(fecha_inicio);
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Reporte Horas.xls";
        parametros.put("reporteHoras", lstReporteHorasExcel);
        parametros.put("periodo", periodo);
        destino = destino + "RESUMEN_FORTIUS.xls";

        GeneraXlsx.generar(plantilla, destino, parametros);
        try (FileInputStream fis = new FileInputStream(destino); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // Cargar el archivo Excel
            //para editar nombre de una hoja de calculo
//            Workbook workbook = WorkbookFactory.create(fis);
//            Sheet sheet = workbook.getSheet("RESUMEN");
//            if (sheet != null) {
//                int sheetIndex = workbook.getSheetIndex(sheet);
//                workbook.setSheetName(sheetIndex, i_unidad_funcional != 0 ? (i_unidad_funcional == 1 ? "RESUMEN ZMO III" : "RESUMEN ZMO V") : "");
//            }

            
            Workbook workbook = WorkbookFactory.create(fis);
            //se asigna nombre a la primer hoja de cálculo
            workbook.setSheetName(0, i_unidad_funcional != 0 ? (i_unidad_funcional == 1 ? "RESUMEN ZMO III" : "RESUMEN ZMO V") : "");

            // Escribir el workbook en el ByteArrayOutputStream
            workbook.write(baos);
            workbook.close();

            // Crear el StreamedContent a partir del ByteArrayOutputStream
            try (InputStream stream = new ByteArrayInputStream(baos.toByteArray())) {
                file = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "RESUMEN_FORTIUS_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xls");
            }

        } catch (IOException | InvalidFormatException | EncryptedDocumentException ex) {
            Logger.getLogger(ReporteNominaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int obtenerMes(Date fecha) {
        current.setTime(fecha);
        return current.get(Calendar.MONTH) + 1;
    }

    public String obtenerMesParaInforme(Date fecha) {
        current.setTime(fecha);
        return meses[current.get(Calendar.MONTH)] + " de " + current.get(Calendar.YEAR);
    }

    public int obtenerDia(Date fecha) {
        current.setTime(fecha);
        return current.get(Calendar.DAY_OF_MONTH);
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

    public List<ReporteHoras> getLstReporteHoras() {
        return lstReporteHoras;
    }

    public void setLstReporteHoras(List<ReporteHoras> lstReporteHoras) {
        this.lstReporteHoras = lstReporteHoras;
    }

    public List<ReporteHorasExcel> getLstReporteHorasExcel() {
        return lstReporteHorasExcel;
    }

    public void setLstReporteHorasExcel(List<ReporteHorasExcel> lstReporteHorasExcel) {
        this.lstReporteHorasExcel = lstReporteHorasExcel;
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

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public ParamAreaUsr getParamAreaUsr() {
        return paramAreaUsr;
    }

    public void setParamAreaUsr(ParamAreaUsr paramAreaUsr) {
        this.paramAreaUsr = paramAreaUsr;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getI_unidad_funcional() {
        return i_unidad_funcional;
    }

    public void setI_unidad_funcional(Integer i_unidad_funcional) {
        this.i_unidad_funcional = i_unidad_funcional;
    }
}
