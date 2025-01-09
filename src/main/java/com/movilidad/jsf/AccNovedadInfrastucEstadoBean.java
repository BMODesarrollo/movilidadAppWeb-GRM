package com.movilidad.jsf;

import com.movilidad.ejb.AccNovedadInfrastucEstadoFacadeLocal;
import com.movilidad.model.AccNovedadInfrastucEstado;
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
@Named(value = "accNovedadInfrastucEstadoBean")
@ViewScoped
public class AccNovedadInfrastucEstadoBean implements Serializable {

    @EJB
    private AccNovedadInfrastucEstadoFacadeLocal accNovedadInfrastucEstadoEjb;

    private AccNovedadInfrastucEstado accNovedadInfrastucEstado;
    private AccNovedadInfrastucEstado selected;
    private String nombre;

    private boolean isEditing;

    private List<AccNovedadInfrastucEstado> lstAccNovedadInfrastucEstados;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAccNovedadInfrastucEstados = accNovedadInfrastucEstadoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        accNovedadInfrastucEstado = new AccNovedadInfrastucEstado();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        nombre = selected.getNombre();
        accNovedadInfrastucEstado = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {

                accNovedadInfrastucEstado.setNombre(nombre);
                accNovedadInfrastucEstado.setModificado(new Date());
                accNovedadInfrastucEstado.setUsername(user.getUsername());
                accNovedadInfrastucEstadoEjb.edit(accNovedadInfrastucEstado);

                PrimeFaces.current().executeScript("PF('wlvCableEventoTipo').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                accNovedadInfrastucEstado.setNombre(nombre);
                accNovedadInfrastucEstado.setEstadoReg(0);
                accNovedadInfrastucEstado.setCreado(new Date());
                accNovedadInfrastucEstado.setUsername(user.getUsername());
                accNovedadInfrastucEstadoEjb.create(accNovedadInfrastucEstado);
                lstAccNovedadInfrastucEstados.add(accNovedadInfrastucEstado);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (accNovedadInfrastucEstadoEjb.findByNombre(nombre.trim(), selected.getIdAccNovedadInfrastucEstado()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstAccNovedadInfrastucEstados.isEmpty()) {
                if (accNovedadInfrastucEstadoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public AccNovedadInfrastucEstado getAccNovedadInfrastucEstado() {
        return accNovedadInfrastucEstado;
    }

    public void setAccNovedadInfrastucEstado(AccNovedadInfrastucEstado accNovedadInfrastucEstado) {
        this.accNovedadInfrastucEstado = accNovedadInfrastucEstado;
    }

    public AccNovedadInfrastucEstado getSelected() {
        return selected;
    }

    public void setSelected(AccNovedadInfrastucEstado selected) {
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

    public List<AccNovedadInfrastucEstado> getLstAccNovedadInfrastucEstados() {
        return lstAccNovedadInfrastucEstados;
    }

    public void setLstAccNovedadInfrastucEstados(List<AccNovedadInfrastucEstado> lstAccNovedadInfrastucEstados) {
        this.lstAccNovedadInfrastucEstados = lstAccNovedadInfrastucEstados;
    }

}
