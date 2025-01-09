package com.movilidad.jsf;

import com.movilidad.ejb.SstEmpresaTipoFacadeLocal;
import com.movilidad.model.SstEmpresaTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "sstEmpresaTipoBean")
@ViewScoped
public class SstEmpresaTipoBean implements Serializable {

    @EJB
    private SstEmpresaTipoFacadeLocal empresaTipoEjb;

    private SstEmpresaTipo empresaTipo;
    private SstEmpresaTipo selected;
    private String nombre;

    private boolean isEditing;

    private List<SstEmpresaTipo> lstSstEmpresaTipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstSstEmpresaTipos = empresaTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        empresaTipo = new SstEmpresaTipo();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        empresaTipo = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {

                empresaTipo.setNombre(nombre);
                empresaTipo.setEstadoReg(0);
                empresaTipo.setCreado(new Date());
                empresaTipo.setUsername(user.getUsername());
                empresaTipoEjb.edit(empresaTipo);

                PrimeFaces.current().executeScript("PF('wlvEmpresaTipo').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                empresaTipo.setNombre(nombre);
                empresaTipo.setEstadoReg(0);
                empresaTipo.setCreado(new Date());
                empresaTipo.setUsername(user.getUsername());
                empresaTipoEjb.create(empresaTipo);
                lstSstEmpresaTipos.add(empresaTipo);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (empresaTipoEjb.findByNombre(nombre.trim(), selected.getIdSstEmpresaTipo()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstSstEmpresaTipos.isEmpty()) {
                if (empresaTipoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public SstEmpresaTipo getSstEmpresaTipo() {
        return empresaTipo;
    }

    public void setSstEmpresaTipo(SstEmpresaTipo empresaTipo) {
        this.empresaTipo = empresaTipo;
    }

    public SstEmpresaTipo getSelected() {
        return selected;
    }

    public void setSelected(SstEmpresaTipo selected) {
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

    public List<SstEmpresaTipo> getLstSstEmpresaTipos() {
        return lstSstEmpresaTipos;
    }

    public void setLstSstEmpresaTipos(List<SstEmpresaTipo> lstSstEmpresaTipos) {
        this.lstSstEmpresaTipos = lstSstEmpresaTipos;
    }

}
