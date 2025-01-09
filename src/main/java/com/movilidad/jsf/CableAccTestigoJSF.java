/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccTestigoFacadeLocal;
import com.movilidad.model.AccProfesion;
import com.movilidad.model.CableAccTestigo;
import com.movilidad.model.CableAccidentalidad;
import com.movilidad.model.EmpleadoTipoIdentificacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar la data del objeto CableAccTestigo, principal tabla
 * afectada cable_acc_testigo
 *
 * @author soluciones-it
 */
@Named(value = "cableAccTestigoJSF")
@ViewScoped
public class CableAccTestigoJSF implements Serializable {

    @EJB
    private CableAccTestigoFacadeLocal cableAccTestigoFacadeLocal;

    private CableAccTestigo cableAccTestigo;

    private List<CableAccTestigo> listCableAccTestigo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;
    private Integer idCableAccidentalidad;
    private Integer idEmpIdentificacion;
    private Integer idAccProfesion;

    @Inject
    private CableAccidentalidadJSF caJSF;

    /**
     * Creates a new instance of CableAccTestigoJSF
     */
    public CableAccTestigoJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
        idEmpIdentificacion = null;
        idAccProfesion = null;
        idCableAccidentalidad = caJSF.compartirIdAccidente();
    }

    /**
     * Permite persistir la data del objeto CableAccTestigo en la base de datos
     */
    public void guardar() {
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentaldad");
            return;
        }
        if (cableAccTestigo != null) {
            if (validarNombre()) {
                MovilidadUtil.addErrorMessage("Num de identificacion ya se encuentra registrado");
                return;
            }
            cargarObjetos();
            cableAccTestigo.setIdCableAccidentalidad(new CableAccidentalidad(idCableAccidentalidad));
            cableAccTestigo.setCreado(new Date());
            cableAccTestigo.setModificado(new Date());
            cableAccTestigo.setEstadoReg(0);
            cableAccTestigo.setUsername(user.getUsername());
            cableAccTestigoFacadeLocal.create(cableAccTestigo);
            MovilidadUtil.addSuccessMessage("Se a registrado Testigo correctamente");
            reset();
        }
    }

    /**
     * Permite realizar un update del objeto CableAccTestigo en la base de datos
     */
    public void actualizar() {
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentaldad");
            return;
        }
        if (cableAccTestigo != null) {
            if (!cNombreAux.equals(cableAccTestigo.getNumIdentificacion())) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Num de identificacion ya se encuentra registrado");
                    return;
                }
            }
            cargarObjetos();
            cableAccTestigo.setModificado(new Date());
            cableAccTestigoFacadeLocal.edit(cableAccTestigo);
            MovilidadUtil.addSuccessMessage("Se a actualizado Testigo correctamente");
            reset();
        }
    }

    public void eliminarTestigo(CableAccTestigo cas) {
        cas.setEstadoReg(1);
        cas.setModificado(new Date());
        cableAccTestigoFacadeLocal.edit(cas);
        MovilidadUtil.addSuccessMessage("Testigo eliminado con éxito");
    }

    /**
     * Permite crear la instancia del objeto CableAccTestigo
     */
    public void prepareGuardar() {
        cableAccTestigo = new CableAccTestigo();
    }

    public void reset() {
        cableAccTestigo = null;
        cNombreAux = "";
        idAccProfesion = null;
        idEmpIdentificacion = null;
    }

    /**
     * Permite capturar el objeto CableAccTestigo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto CableAccTestigo
     */
    public void onGetCableAccTestigo(CableAccTestigo event) {
        cableAccTestigo = event;
        cNombreAux = event.getNumIdentificacion();
        idEmpIdentificacion = event.getIdTipoDoc() != null ? event.getIdTipoDoc().getIdEmpleadoTipoIdentificacion() : null;
        idAccProfesion = event.getIdAccProfesion() != null ? event.getIdAccProfesion().getIdAccProfesion() : null;
    }

    void cargarObjetos() {
        if (idEmpIdentificacion != null) {
            cableAccTestigo.setIdTipoDoc(new EmpleadoTipoIdentificacion(idEmpIdentificacion));
        }
        if (idAccProfesion != null) {
            cableAccTestigo.setIdAccProfesion(new AccProfesion(idAccProfesion));
        }
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el numIdentificacion no se encuentra
     */
    boolean validarNombre() {
        List<CableAccTestigo> findAllEstadoReg = cableAccTestigoFacadeLocal.findAllEstadoReg(idCableAccidentalidad);
        for (CableAccTestigo sae : findAllEstadoReg) {
            if (sae.getNumIdentificacion().equals(cableAccTestigo.getNumIdentificacion())) {
                return true;
            }
        }
        return false;
    }

    public CableAccTestigo getCableAccTestigo() {
        return cableAccTestigo;
    }

    public void setCableAccTestigo(CableAccTestigo cableAccTestigo) {
        this.cableAccTestigo = cableAccTestigo;
    }

    public List<CableAccTestigo> getListCableAccTestigo() {
        if (idCableAccidentalidad != null) {
            listCableAccTestigo = cableAccTestigoFacadeLocal.findAllEstadoReg(idCableAccidentalidad);
        }
        return listCableAccTestigo;
    }

    public void setListCableAccTestigo(List<CableAccTestigo> listCableAccTestigo) {
        this.listCableAccTestigo = listCableAccTestigo;
    }

    public Integer getIdEmpIdentificacion() {
        return idEmpIdentificacion;
    }

    public void setIdEmpIdentificacion(Integer idEmpIdentificacion) {
        this.idEmpIdentificacion = idEmpIdentificacion;
    }

    public Integer getIdAccProfesion() {
        return idAccProfesion;
    }

    public void setIdAccProfesion(Integer idAccProfesion) {
        this.idAccProfesion = idAccProfesion;
    }

}
