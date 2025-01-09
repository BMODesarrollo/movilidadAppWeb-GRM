package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.util.beans.InformeInterventoria;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "informeInterventoriaBean")
@ViewScoped
public class InformeInterventoriaManagedBean implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<InformeInterventoria> lstInforme;
    private List<GopUnidadFuncional> lstUnidadesFuncionales;

    private Date fechaMes;

    private String[] cargos = {"OPERADOR BUS ARTICULADO", "OPERADOR BUS BIARTICULADO", "OPERADOR MASTER"};

    @PostConstruct
    public void init() {
    }

    public void generarReporte() {
        Calendar c = Calendar.getInstance();
        c.setTime(fechaMes);
        int numberOfDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String fechaFinal = year + "-" + month + "-" + numberOfDays;

        // COUNT long y SUM bigdecimal
        if (compararFecha(c)) {
            MovilidadUtil.addErrorMessage("El año seleccionado es mayor al actual");
            lstInforme = null;
            return;
        }

        Date fechaFin = Util.toDate(fechaFinal);

        PrimeFaces.current().executeScript("PF('wlVInterventoria').clearFilters()");

        lstInforme = empleadoEjb.getInformeInterventoria(fechaMes, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstInforme == null || lstInforme.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        MovilidadUtil.addSuccessMessage("Reporte generado exitósamente");
    }

    private boolean compararFecha(Calendar calendar) {
        int anioAct = new Date().getYear();
        int anioFecha = calendar.get(Calendar.YEAR) - 1900;
        return anioFecha > anioAct;
    }

    public Date getFechaMes() {
        return fechaMes;
    }

    public void setFechaMes(Date fechaMes) {
        this.fechaMes = fechaMes;
    }

    public List<InformeInterventoria> getLstInforme() {
        return lstInforme;
    }

    public void setLstInforme(List<InformeInterventoria> lstInforme) {
        this.lstInforme = lstInforme;
    }

    public String[] getCargos() {
        return cargos;
    }

    public void setCargos(String[] cargos) {
        this.cargos = cargos;
    }

    public List<GopUnidadFuncional> getLstUnidadesFuncionales() {
        return lstUnidadesFuncionales;
    }

    public void setLstUnidadesFuncionales(List<GopUnidadFuncional> lstUnidadesFuncionales) {
        this.lstUnidadesFuncionales = lstUnidadesFuncionales;
    }

}
