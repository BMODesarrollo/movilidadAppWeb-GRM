/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccSiniestradoFacadeLocal;
import com.movilidad.model.CableAccEntregaPaciente;
import com.movilidad.model.CableAccSiniestrado;
import com.movilidad.model.CableAccTipoUsuario;
import com.movilidad.model.CableAccTpAsistencia;
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
 * Permite gestionar la data del objeto CableAccSiniestrado, principal tabla
 * afectada cable_acc_siniestrado
 *
 * @author soluciones-it
 */
@Named(value = "cableAccSiniestradoJSF")
@ViewScoped
public class CableAccSiniestradoJSF implements Serializable {

    @EJB
    private CableAccSiniestradoFacadeLocal cableAccSiniestradoFacadeLocal;

    private CableAccSiniestrado cableAccSiniestrado;

    private List<CableAccSiniestrado> listCableAccSiniestrado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;
    private Integer idCableAccidentalidad;
    private Integer idCableAccTpUsuario;
    private Integer idEmpIdentificacion;
    private Integer idCableAccEntregaPaciente;
    private Integer idCableAccTpAsistencia;

    @Inject
    private CableAccidentalidadJSF caJSF;

    /**
     * Creates a new instance of CableAccSiniestradoJSF
     */
    public CableAccSiniestradoJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
        idCableAccTpUsuario = null;
        idEmpIdentificacion = null;
        idCableAccEntregaPaciente = null;
        idCableAccidentalidad = caJSF.compartirIdAccidente();
    }

    /**
     * Permite persistir la data del objeto CableAccSiniestrado en la base de
     * datos
     */
    public void guardar() {
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un Accidente");
            return;
        }
        if (cableAccSiniestrado != null) {
            if (validarNombre()) {
                MovilidadUtil.addErrorMessage("Num de identificacion ya se encuentra registrado");
                return;
            }
            cargarObjetos();
            cableAccSiniestrado.setIdCableAccidentalidad(new CableAccidentalidad(idCableAccidentalidad));
            cableAccSiniestrado.setCreado(new Date());
            cableAccSiniestrado.setModificado(new Date());
            cableAccSiniestrado.setEstadoReg(0);
            cableAccSiniestrado.setUsername(user.getUsername());
            cableAccSiniestradoFacadeLocal.create(cableAccSiniestrado);
            MovilidadUtil.addSuccessMessage("Se a registrado Siniestrado correctamente");
            reset();
        }
    }

    /**
     * Permite realizar un update del objeto CableAccSiniestrado en la base de
     * datos
     */
    public void actualizar() {
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentalidad");
            return;
        }
        if (cableAccSiniestrado != null) {
            if (!cNombreAux.equals(cableAccSiniestrado.getNumIdentificacion())) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Num de identificacion ya se encuentra registrado");
                    return;
                }
            }
            cargarObjetos();
            cableAccSiniestrado.setModificado(new Date());
            cableAccSiniestradoFacadeLocal.edit(cableAccSiniestrado);
            MovilidadUtil.addSuccessMessage("Se a actualizado Siniestrado correctamente");
            reset();
        }
    }

    public void eliminarSiniestrado(CableAccSiniestrado cas) {
        cas.setEstadoReg(1);
        cas.setModificado(new Date());
        cableAccSiniestradoFacadeLocal.edit(cas);
        MovilidadUtil.addSuccessMessage("Siniestrado eliminado con éxito");
    }

//    public void onEmpleadoCargar(Empleado emp) {
//        if (cableAccSiniestrado != null) {
//            cableAccSiniestrado.setApellidos(emp.getApellidos());
//            cableAccSiniestrado.setDireccion(emp.getDireccion());
//            cableAccSiniestrado.setEdad(MovilidadUtil.obtenerEdadByFecha(emp.getFechaNcto()));
//            cableAccSiniestrado.setIdTipoDoc(emp.getIdEmpleadoTipoIdentificacion());
//            cableAccSiniestrado.setNombre(emp.getNombres());
//            cableAccSiniestrado.setTelefono(emp.getTelefonoMovil());
//            cableAccSiniestrado.setNumIdentificacion(emp.getIdentificacion());
//        }
//    }
    /**
     * Permite crear la instancia del objeto CableAccSiniestrado
     */
    public void prepareGuardar() {
        cableAccSiniestrado = new CableAccSiniestrado();
    }

    public void reset() {
        cableAccSiniestrado = null;
        cNombreAux = "";
        idCableAccTpUsuario = null;
        idEmpIdentificacion = null;
        idCableAccEntregaPaciente = null;
    }

    /**
     * Permite capturar el objeto CableAccSiniestrado seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto CableAccSiniestrado
     */
    public void onGetCableAccSiniestrado(CableAccSiniestrado event) {
        cableAccSiniestrado = event;
        cNombreAux = event.getNumIdentificacion();
        idCableAccTpUsuario = event.getIdCableAccTpUsuario() != null ? event.getIdCableAccTpUsuario().getIdCableAccTipoUsuario() : null;
        idEmpIdentificacion = event.getIdTipoDoc() != null ? event.getIdTipoDoc().getIdEmpleadoTipoIdentificacion() : null;
        idCableAccEntregaPaciente = event.getIdCableAccEntregaPaciente() != null ? event.getIdCableAccEntregaPaciente().getIdCableAccEntregaPaciente() : null;
        idCableAccTpAsistencia = event.getIdCableAccTpAsistencia() != null ? event.getIdCableAccTpAsistencia().getIdCableAccTpAsistencia() : null;
    }

    public void cambioLesion() {
        if (cableAccSiniestrado.getPresentaLesion() != null && cableAccSiniestrado.getPresentaLesion().equals(0)) {
            cableAccSiniestrado.setLesion(null);
        }
    }

    void cargarObjetos() {
        if (idCableAccEntregaPaciente != null) {
            cableAccSiniestrado.setIdCableAccEntregaPaciente(new CableAccEntregaPaciente(idCableAccEntregaPaciente));
        }
        if (idEmpIdentificacion != null) {
            cableAccSiniestrado.setIdTipoDoc(new EmpleadoTipoIdentificacion(idEmpIdentificacion));
        }
        if (idCableAccTpUsuario != null) {
            cableAccSiniestrado.setIdCableAccTpUsuario(new CableAccTipoUsuario(idCableAccTpUsuario));
        }
        if (idCableAccTpAsistencia != null) {
            cableAccSiniestrado.setIdCableAccTpAsistencia(new CableAccTpAsistencia(idCableAccTpAsistencia));
        }
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el numIdentificacion no se encuentra
     */
    boolean validarNombre() {
        List<CableAccSiniestrado> findAllEstadoReg = cableAccSiniestradoFacadeLocal.findAllEstadoReg(idCableAccidentalidad);
        for (CableAccSiniestrado sae : findAllEstadoReg) {
            if (sae.getNumIdentificacion().equals(cableAccSiniestrado.getNumIdentificacion())) {
                return true;
            }
        }
        return false;
    }

    public CableAccSiniestrado getCableAccSiniestrado() {
        return cableAccSiniestrado;
    }

    public void setCableAccSiniestrado(CableAccSiniestrado cableAccSiniestrado) {
        this.cableAccSiniestrado = cableAccSiniestrado;
    }

    public List<CableAccSiniestrado> getListCableAccSiniestrado() {
        if (idCableAccidentalidad != null) {
            listCableAccSiniestrado = cableAccSiniestradoFacadeLocal.findAllEstadoReg(idCableAccidentalidad);
        }
        return listCableAccSiniestrado;
    }

    public Integer getIdCableAccTpUsuario() {
        return idCableAccTpUsuario;
    }

    public void setIdCableAccTpUsuario(Integer idCableAccTpUsuario) {
        this.idCableAccTpUsuario = idCableAccTpUsuario;
    }

    public Integer getIdCableAccEntregaPaciente() {
        return idCableAccEntregaPaciente;
    }

    public void setIdCableAccEntregaPaciente(Integer idCableAccEntregaPaciente) {
        this.idCableAccEntregaPaciente = idCableAccEntregaPaciente;
    }

    public Integer getIdEmpIdentificacion() {
        return idEmpIdentificacion;
    }

    public void setIdEmpIdentificacion(Integer idEmpIdentificacion) {
        this.idEmpIdentificacion = idEmpIdentificacion;
    }

    public Integer getIdCableAccTpAsistencia() {
        return idCableAccTpAsistencia;
    }

    public void setIdCableAccTpAsistencia(Integer idCableAccTpAsistencia) {
        this.idCableAccTpAsistencia = idCableAccTpAsistencia;
    }

}
