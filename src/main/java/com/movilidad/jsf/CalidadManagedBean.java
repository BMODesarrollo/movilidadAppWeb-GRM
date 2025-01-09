package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "calidadBean")
@ViewScoped
public class CalidadManagedBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    private List<Novedad> lista;

    private Date fechaIni;
    private Date fechaFin;
    private StreamedContent file;

    @PostConstruct
    public void init() {
        fechaFin = new Date();
        current.setTime(fechaFin);
        current.add(Calendar.DATE, -1);
        fechaIni = current.getTime();
    }

    public void generarReporte() throws FileNotFoundException, ParseException {
        PrimeFaces current = PrimeFaces.current();
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        destino = destino + "Informe_Quejas.xlsx";
        plantilla = plantilla + "InformeQuejas.xlsx";
        List<Novedad> lstQuejas;

        if (fechaIni == null) {
            current.ajax().update("frmInformeAccidente:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha de inicio");
            return;
        }

        if (fechaFin == null) {
            current.ajax().update("frmInformeAccidente:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha fin");
            return;
        }

        if (Util.validarFechaCambioEstado(fechaIni, fechaFin)) {
            current.ajax().update("frmInformeAccidente:messages");
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha fin");
            return;
        }
        lstQuejas = novedadEjb.getQuejas(fechaIni, fechaFin);

        if (lstQuejas == null || lstQuejas.isEmpty()) {
            current.ajax().update("frmInformeAccidente:messages");
            MovilidadUtil.addErrorMessage("No se encuentran quejas registradas para ese rango de fechas.");
            return;
        }
        parametros.put("quejas", lstQuejas);
        parametros.put("fechaIni", Util.dateFormat(fechaIni));
        parametros.put("fechaFin", Util.dateFormat(fechaFin));
        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "InformeQuejas_" + Util.dateFormat(fechaIni) + "_al_" + Util.dateFormat(fechaFin) + ".xlsx");
        current.ajax().update("frmInformeAccidente");
        MovilidadUtil.addSuccessMessage("Reporte generado exitósamente");
    }

    public void getByDateRange() {

        if (fechaIni == null) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha de inicio");
            return;
        }

        if (fechaFin == null) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha fin");
            return;
        }
        
        if (Util.validarFechaCambioEstado(fechaIni, fechaFin)) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha fin");
            return;
        }

        PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters()");
        lista = novedadEjb.getNovedadesSNC(fechaIni, fechaFin);
        
        if (lista == null || lista.isEmpty()) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("No se encuentran novedades registradas para ese rango de fechas.");
        }
    }
    
    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public List<Novedad> getLista() {
        return lista;
    }

    public void setLista(List<Novedad> lista) {
        this.lista = lista;
    }
}
