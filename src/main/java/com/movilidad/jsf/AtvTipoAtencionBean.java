/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvTipoAtencionFacadeLocal;
import com.movilidad.model.AtvTipoAtencion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "atvTipoAtencionBean")
@ViewScoped
public class AtvTipoAtencionBean implements Serializable{

    
    @EJB
    private AtvTipoAtencionFacadeLocal atvTipoAtencionEjb;

    private AtvTipoAtencion atvTipoAtencion;
    private AtvTipoAtencion selected;
    private String nombre;
    private String descripcion;

    private boolean isEditing;

    private List<AtvTipoAtencion> lstAtvTipoAtencion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstAtvTipoAtencion = atvTipoAtencionEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        atvTipoAtencion = new AtvTipoAtencion();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        atvTipoAtencion = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        if (validarDatos()) {
            return;
        }
        atvTipoAtencion.setNombre(nombre);
        atvTipoAtencion.setDescripcion(descripcion);
        atvTipoAtencion.setUsername(user.getUsername());
        if (isEditing) {
            atvTipoAtencion.setModificado(MovilidadUtil.fechaCompletaHoy());
            atvTipoAtencionEjb.edit(atvTipoAtencion);
            MovilidadUtil.hideModal("wv_tipo_atencion");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            atvTipoAtencion.setEstadoReg(0);
            atvTipoAtencion.setCreado(MovilidadUtil.fechaCompletaHoy());
            atvTipoAtencionEjb.create(atvTipoAtencion);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (atvTipoAtencionEjb.findByNombre(nombre.trim(), selected.getIdAtvTipoAtencion()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        } else {
            if (atvTipoAtencionEjb.findByNombre(nombre.trim(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado");
                return true;
            }
        }

        return false;
    }

    public AtvTipoAtencion getAtvTipoAtencion() {
        return atvTipoAtencion;
    }

    public void setAtvTipoAtencion(AtvTipoAtencion atvTipoAtencion) {
        this.atvTipoAtencion = atvTipoAtencion;
    }

    public AtvTipoAtencion getSelected() {
        return selected;
    }

    public void setSelected(AtvTipoAtencion selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public List<AtvTipoAtencion> getLstAtvTipoAtencion() {
        return lstAtvTipoAtencion;
    }

    public void setLstAtvTipoAtencion(List<AtvTipoAtencion> lstAtvTipoAtencion) {
        this.lstAtvTipoAtencion = lstAtvTipoAtencion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
