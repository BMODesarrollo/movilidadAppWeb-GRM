package com.movilidad.jsf;

import com.movilidad.ejb.AccNovedadTipoInfrastrucFacadeLocal;
import com.movilidad.model.AccNovedadTipoInfrastruc;
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
@Named(value = "accNovedadTipoInfrastucBean")
@ViewScoped
public class AccNovedadTipoInfrastucBean implements Serializable {

    @EJB
    private AccNovedadTipoInfrastrucFacadeLocal accNovedadTipoInfrastrucEjb;

    private AccNovedadTipoInfrastruc accNovedadTipoInfrastruc;
    private AccNovedadTipoInfrastruc selected;
    private String nombre;

    private boolean isEditing;

    private List<AccNovedadTipoInfrastruc> lstAccNovedadTipoInfrastrucs;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAccNovedadTipoInfrastrucs = accNovedadTipoInfrastrucEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        accNovedadTipoInfrastruc = new AccNovedadTipoInfrastruc();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        nombre = selected.getNombre();
        accNovedadTipoInfrastruc = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {

                accNovedadTipoInfrastruc.setNombre(nombre);
                accNovedadTipoInfrastruc.setModificado(new Date());
                accNovedadTipoInfrastruc.setUsername(user.getUsername());
                accNovedadTipoInfrastrucEjb.edit(accNovedadTipoInfrastruc);

                PrimeFaces.current().executeScript("PF('wlvCableEventoTipo').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                accNovedadTipoInfrastruc.setNombre(nombre);
                accNovedadTipoInfrastruc.setEstadoReg(0);
                accNovedadTipoInfrastruc.setCreado(new Date());
                accNovedadTipoInfrastruc.setUsername(user.getUsername());
                accNovedadTipoInfrastrucEjb.create(accNovedadTipoInfrastruc);
                lstAccNovedadTipoInfrastrucs.add(accNovedadTipoInfrastruc);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (accNovedadTipoInfrastrucEjb.findByNombre(nombre.trim(), selected.getIdAccNovedadTipoInfrastruc()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstAccNovedadTipoInfrastrucs.isEmpty()) {
                if (accNovedadTipoInfrastrucEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public AccNovedadTipoInfrastruc getAccNovedadTipoInfrastruc() {
        return accNovedadTipoInfrastruc;
    }

    public void setAccNovedadTipoInfrastruc(AccNovedadTipoInfrastruc accNovedadTipoInfrastruc) {
        this.accNovedadTipoInfrastruc = accNovedadTipoInfrastruc;
    }

    public AccNovedadTipoInfrastruc getSelected() {
        return selected;
    }

    public void setSelected(AccNovedadTipoInfrastruc selected) {
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

    public List<AccNovedadTipoInfrastruc> getLstAccNovedadTipoInfrastrucs() {
        return lstAccNovedadTipoInfrastrucs;
    }

    public void setLstAccNovedadTipoInfrastrucs(List<AccNovedadTipoInfrastruc> lstAccNovedadTipoInfrastrucs) {
        this.lstAccNovedadTipoInfrastrucs = lstAccNovedadTipoInfrastrucs;
    }

}
