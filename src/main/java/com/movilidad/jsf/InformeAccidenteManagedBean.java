package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Novedad;
import com.movilidad.util.beans.InformeAccidente;
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
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "informeAccidenteBean")
@ViewScoped
public class InformeAccidenteManagedBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    private Date fechaIni;
    private Date fechaFin;
    private StreamedContent file;

    private List<GopUnidadFuncional> lstUnidadesFuncionales;

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
        destino = destino + "Informe_Accidente.xlsx";
        plantilla = plantilla + "InformeAccidente_BOCETO_PRUEBAS.xlsx";
        List<Novedad> lstAccidentes;

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
        lstAccidentes = novedadEjb.getAccidentes(fechaIni, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        InformeAccidente informeAccidente = novedadEjb.obtenerDetalleAccidente(
                fechaIni, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (informeAccidente == null) {
            informeAccidente = new InformeAccidente(0, 0, 0, 0);
        }
        if (lstAccidentes == null) {
            current.ajax().update("frmInformeAccidente:messages");
            MovilidadUtil.addErrorMessage("No se encuentran accidentes registrados para ese rango de fechas.");
            return;
        }
        parametros.put("accidentes", lstAccidentes);
        parametros.put("detalleAccidentes", informeAccidente);
        parametros.put("total_Accidentes", lstAccidentes.size());
        parametros.put("fechaIni", Util.dateFormat(fechaIni));
        parametros.put("fechaFin", Util.dateFormat(fechaFin));
        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "Informe_Accidente.xlsx");
        current.ajax().update("frmInformeAccidente");
        MovilidadUtil.addSuccessMessage("Reporte generado exitósamente");
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

    public List<GopUnidadFuncional> getLstUnidadesFuncionales() {
        return lstUnidadesFuncionales;
    }

    public void setLstUnidadesFuncionales(List<GopUnidadFuncional> lstUnidadesFuncionales) {
        this.lstUnidadesFuncionales = lstUnidadesFuncionales;
    }

}
