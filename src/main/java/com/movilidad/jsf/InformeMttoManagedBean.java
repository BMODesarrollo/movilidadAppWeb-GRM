package com.movilidad.jsf;

import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "informeMttoBean")
@ViewScoped
public class InformeMttoManagedBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;

    private List<Novedad> lista;

    private String opcion;
    private Date fechaInicio;
    private Date fechaFin;

    @PostConstruct
    public void init() {
        opcion = "";
    }

    public void buscarDatos() {
        if (opcion.isEmpty() || opcion.equals("")) {
            MovilidadUtil.addErrorMessage("Debe seleccionar criterio de búsqueda");
            return;
        }
        switch (opcion) {
            case "CV":
                lista = novedadEjb.obtenerCambiosVehiculo(fechaInicio, fechaFin);
                break;
            case "TQ04":
                lista = novedadEjb.obtenerTq04(fechaInicio, fechaFin);
                break;
        }
    }

    public void postProcessXLS(Object document) {

        XSSFWorkbook wb = (XSSFWorkbook) document;
        switch (opcion) {
            case "CV":
                wb.setSheetName(0, "CAMBIOS DE VEHICULO");
                break;
            case "TQ04":
                wb.setSheetName(0, "TQ04");
                break;
        }

    }

    public List<Novedad> getLista() {
        return lista;
    }

    public void setLista(List<Novedad> lista) {
        this.lista = lista;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
