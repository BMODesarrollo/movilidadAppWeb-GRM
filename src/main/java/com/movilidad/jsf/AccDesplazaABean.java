package com.movilidad.jsf;

import com.movilidad.ejb.AccDesplazaAFacadeLocal;
import com.movilidad.model.AccDesplazaA;
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
 * @author Carlos Ballestas
 */
@Named(value = "accDesplazaABean")
@ViewScoped
public class AccDesplazaABean implements Serializable {

    @EJB
    private AccDesplazaAFacadeLocal accDesplazaAEjb;

    private AccDesplazaA accDesplazaA;
    private AccDesplazaA selected;
    private String nombre;

    private boolean isEditing;

    private List<AccDesplazaA> lstAccDesplazaA;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void nuevo() {
        nombre = "";
        accDesplazaA = new AccDesplazaA();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        accDesplazaA = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            accDesplazaA.setNombre(nombre);
            accDesplazaA.setEstadoReg(0);
            accDesplazaA.setUsername(user.getUsername());
            if (isEditing) {
                accDesplazaA.setModificado(MovilidadUtil.fechaCompletaHoy());
                accDesplazaAEjb.edit(accDesplazaA);
                MovilidadUtil.hideModal("wlVAccDesplazaA");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                accDesplazaA.setCreado(MovilidadUtil.fechaCompletaHoy());
                accDesplazaAEjb.create(accDesplazaA);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (accDesplazaAEjb.findByNombre(nombre.trim(), selected.getIdAccDesplazaA()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (accDesplazaAEjb.findByNombre(nombre.trim(), 0) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        }

        return null;
    }

    private void consultar() {
        lstAccDesplazaA = accDesplazaAEjb.findByEstadoReg();
    }

    public AccDesplazaA getAccDesplazaA() {
        return accDesplazaA;
    }

    public void setAccDesplazaA(AccDesplazaA accDesplazaA) {
        this.accDesplazaA = accDesplazaA;
    }

    public AccDesplazaA getSelected() {
        return selected;
    }

    public void setSelected(AccDesplazaA selected) {
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

    public List<AccDesplazaA> getLstAccDesplazaA() {
        return lstAccDesplazaA;
    }

    public void setLstAccDesplazaA(List<AccDesplazaA> lstAccDesplazaA) {
        this.lstAccDesplazaA = lstAccDesplazaA;
    }

}
