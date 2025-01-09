package com.movilidad.jsf;

import com.movilidad.ejb.DispSistemaFacadeLocal;
import com.movilidad.model.DispSistema;
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
@Named(value = "dispSistemaBean")
@ViewScoped
public class DispSistemaBean implements Serializable {

    @EJB
    private DispSistemaFacadeLocal dispSistemaEjb;

    private DispSistema dispSistema;
    private DispSistema selected;
    private String nombre;

    private boolean isEditing;

    private List<DispSistema> lstDispSistemas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    void consultar() {
        lstDispSistemas = dispSistemaEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        dispSistema = new DispSistema();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        dispSistema = selected;
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

                dispSistema.setNombre(nombre);
                dispSistema.setEstadoReg(0);
                dispSistema.setCreado(new Date());
                dispSistema.setUsername(user.getUsername());
                dispSistemaEjb.edit(dispSistema);

                PrimeFaces.current().executeScript("PF('wlvdispSistema').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                dispSistema.setNombre(nombre);
                dispSistema.setEstadoReg(0);
                dispSistema.setCreado(new Date());
                dispSistema.setUsername(user.getUsername());
                dispSistemaEjb.create(dispSistema);
                lstDispSistemas.add(dispSistema);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
            consultar();
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        int idDispSistema = dispSistema.getIdDispSistema() == null ? 0 : dispSistema.getIdDispSistema();
        if (dispSistemaEjb.findByNombre(nombre.trim(), idDispSistema) != null) {
            return "YA existe un registro con el nombre a ingresar";
        }
        return null;
    }

    public DispSistema getDispSistema() {
        return dispSistema;
    }

    public void setDispSistema(DispSistema dispSistema) {
        this.dispSistema = dispSistema;
    }

    public DispSistema getSelected() {
        return selected;
    }

    public void setSelected(DispSistema selected) {
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

    public List<DispSistema> getLstDispSistemas() {
        return lstDispSistemas;
    }

    public void setLstDispSistemas(List<DispSistema> lstDispSistemas) {
        this.lstDispSistemas = lstDispSistemas;
    }

}
