/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author cesar
 */
@Named(value = "prgTcServbusBuscarJSF")
@ViewScoped
public class PrgTcServbusBuscarJSF implements Serializable {

    @EJB
    private PrgTcFacadeLocal tcFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;

    private List<PrgTc> listPrgTc;
    private String identificacion;
    private Date d;

    public PrgTcServbusBuscarJSF() {
    }

    @PostConstruct
    public void init() {
        listPrgTc = new ArrayList<>();
        identificacion = null;
        d = new Date();
    }

    public void buscar() {
        if (identificacion == null) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            listPrgTc.clear();
            return;
        }
        Empleado emp = empleadoFacadeLocal.findByIdentificacion(identificacion);
        if (emp == null) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            listPrgTc.clear();
            return;
        }
        listPrgTc = tcFacadeLocal.findServiciosByFechaAndIdEmpleado(d, emp.getIdEmpleado());
        if (listPrgTc.isEmpty()) {
            System.out.println("NO SE ENCONTRÓ DATOS EN LISTA PRGTC");
            MovilidadUtil.addErrorMessage("No se encontraron registros.");
        }
    }

    public boolean enEjecucion(PrgTc p) throws ParseException {
        if (p == null) {
            return false;
        }
        if (MovilidadUtil.fechaBetween(d, p.getTimeOrigin(), p.getTimeDestiny()) && p.getEstadoOperacion() != 5) {
            return true;
        }
        return false;
    }

    public String alarStyleClass(PrgTc p) throws ParseException {
        if (p == null) {
            return null;
        }
        if (MovilidadUtil.fechaBetween(d, p.getTimeOrigin(), p.getTimeDestiny())
                && p.getEstadoOperacion() != 5 && p.getEstadoOperacion() != 8) {
            return "rowGrenStyle";
        }
        if (p.getEstadoOperacion() == 2) {
            return "rowOrangeStyle";
        }
        if (p.getEstadoOperacion() == 5 || p.getEstadoOperacion() == 8) {
            return "rowRedStyle";
        }
        if (p.getEstadoOperacion() == 7) {
            return "rowGrisOscuroStyle";
        }
        if (p.getEstadoOperacion() == 6) {
            return "rowTurquesaStyle";
        }
        if (p.getEstadoOperacion() == 3 || p.getEstadoOperacion() == 4) {
            return "rowBlueStyle";
        }
        return null;
    }

    public List<PrgTc> getListPrgTc() {
        return listPrgTc;
    }

    public void setListPrgTc(List<PrgTc> listPrgTc) {
        this.listPrgTc = listPrgTc;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

}
