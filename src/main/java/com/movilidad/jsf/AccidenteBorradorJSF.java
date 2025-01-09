/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteBorradorFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteBorrador;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteBorradorJSF")
@ViewScoped
public class AccidenteBorradorJSF implements Serializable {

    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private AccidenteBorradorFacadeLocal accidenteBorradorFacadeLocal;

    private List<AccidenteBorrador> listAccidenteBorrador;
    private AccidenteBorrador accidenteBorrador;

    private Date fechaIni;
    private Date fechaFin;

    public AccidenteBorradorJSF() {
    }

    @PostConstruct
    public void init() {
        listAccidenteBorrador = new ArrayList<>();
        accidenteBorrador = null;
        fechaIni = new Date();
        fechaFin = new Date();
    }

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void accidenteSolicitado(Accidente a) {
        accidenteBorrador = new AccidenteBorrador();
        accidenteBorrador.setIdAccidente(a);
        PrimeFaces.current().executeScript("PF('soliDlg').show()");
    }

    public void guardarAccSolicitado() {
        try {
            if (accidenteBorrador != null) {
                accidenteBorrador.setFechaSolicitado(new Date());
                accidenteBorrador.setUserSolicitado(user.getUsername());
                if (accidenteBorrador.getDescripcionSolicitado() == null) {
                    MovilidadUtil.addErrorMessage("Descripción es requerido");
                    return;
                }
                if (accidenteBorrador.getDescripcionSolicitado().isEmpty()) {
                    MovilidadUtil.addErrorMessage("Descripción es requerido");
                    return;
                }
                accidenteBorrador.setUsername(user.getUsername());
                accidenteBorrador.setCreado(new Date());
                accidenteBorrador.setModificado(new Date());
                accidenteBorrador.setEstadoReg(0);
                accidenteBorradorFacadeLocal.create(accidenteBorrador);
                PrimeFaces.current().executeScript("PF('soliDlg').hide()");
                MovilidadUtil.addSuccessMessage("Solicitud registrada con éxito");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public void accidenteAprobado(AccidenteBorrador ab) {
        accidenteBorrador = ab;
        PrimeFaces.current().executeScript("PF('aproDlg').show()");
    }

    public void guardarAccAprobado() {
        try {
            accidenteBorrador.setFechaAprobado(new Date());
            accidenteBorrador.setUserAprobado(user.getUsername());
            accidenteBorrador.setModificado(new Date());
            Accidente acc = accidenteBorrador.getIdAccidente();
            acc.setEstadoReg(3); // (3) código para eliminar los casos de accidnetalidad desde este módulo
            accidenteFacadeLocal.edit(acc);
            accidenteBorradorFacadeLocal.edit(accidenteBorrador);
            busquedaList();
            PrimeFaces.current().executeScript("PF('aproDlg').hide()");
            MovilidadUtil.addSuccessMessage("Solicitud aprobada con éxito");
            reset();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public boolean renderAccidenteSolicitud(Accidente a) {
        try {
            //Fecha Marzo 11
            Date fecha = Util.toDate("2020-03-11");
            if (a.getFechaAcc() != null) {
                if (MovilidadUtil.fechasIgualMenorMayor(a.getFechaAcc(), fecha, false) == 0) {
                    return true;
                }
            }
            return false;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void busquedaList() {
        try {
            if (MovilidadUtil.fechasIgualMenorMayor(fechaFin, fechaIni, false) == 0) {
                MovilidadUtil.addErrorMessage("Fecha Fin no puede ser inferior a Fecha Inicio");
                return;
            }
            fechaIni = MovilidadUtil.converterToHour(fechaIni, "00:00:00");
            fechaFin = MovilidadUtil.converterToHour(fechaFin, "23:59:59");
            listAccidenteBorrador = accidenteBorradorFacadeLocal.findAllEstadoReg(fechaIni, fechaFin);
        } catch (ParseException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public void reset() {
        accidenteBorrador = null;
        fechaIni = new Date();
        fechaFin = new Date();
    }

    public List<AccidenteBorrador> getListAccidenteBorrador() {
        return listAccidenteBorrador;
    }

    public void setListAccidenteBorrador(List<AccidenteBorrador> listAccidenteBorrador) {
        this.listAccidenteBorrador = listAccidenteBorrador;
    }

    public AccidenteBorrador getAccidenteBorrador() {
        return accidenteBorrador;
    }

    public void setAccidenteBorrador(AccidenteBorrador accidenteBorrador) {
        this.accidenteBorrador = accidenteBorrador;
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

}
