package com.movilidad.jsf;

import com.movilidad.ejb.AccPlanAccionFacadeLocal;
import com.movilidad.model.AccPlanAccion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "accPlanAccionBean")
@ViewScoped
public class AccPlanAccionManagedBean implements Serializable {

    @EJB
    private AccPlanAccionFacadeLocal accPlanAccionEjb;

    private List<AccPlanAccion> lstAccPlanAccion;

    private AccPlanAccion accPlanAccion;
    private AccPlanAccion selected;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    private final PrimeFaces PRIMEFACES = PrimeFaces.current();

    public void nuevo() {
        accPlanAccion = new AccPlanAccion();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        accPlanAccion = selected;
        isEditing = true;
    }

    public void guardar() {
        if (!validarDatos()) {
            accPlanAccion.setUsername(user.getUsername());

            if (!isEditing) {
                if (accPlanAccionEjb.findByPlan(accPlanAccion.getPlan().trim()) != null) {
                    MovilidadUtil.addErrorMessage("El plan que está intentando registrar YA EXISTE");
                    return;
                }
                accPlanAccion.setCreado(new Date());
                accPlanAccion.setEstadoReg(0);
                accPlanAccionEjb.create(accPlanAccion);
                lstAccPlanAccion.add(accPlanAccion);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            } else {
                accPlanAccion.setModificado(new Date());
                accPlanAccionEjb.edit(accPlanAccion);
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
                PRIMEFACES.executeScript("PF('AccPlanAccionCreateDialog').hide();");
                isEditing = false;
            }

        }
    }

    private boolean validarDatos() {
        if (accPlanAccion.getPlan() == null) {
            MovilidadUtil.addErrorMessage("El campo plan es OBLIGATORIO");
            return true;
        }
        if (accPlanAccion.getDescripcion() == null) {
            MovilidadUtil.addErrorMessage("Debe ingresar una descripción");
            return true;
        }
        return false;
    }

    public List<AccPlanAccion> getLstAccPlanAccion() {
        if (lstAccPlanAccion == null) {
            lstAccPlanAccion = accPlanAccionEjb.findAll();
        }
        return lstAccPlanAccion;
    }

    public void setLstAccPlanAccion(List<AccPlanAccion> lstAccPlanAccion) {
        this.lstAccPlanAccion = lstAccPlanAccion;
    }

    public AccPlanAccion getAccPlanAccion() {
        return accPlanAccion;
    }

    public void setAccPlanAccion(AccPlanAccion accPlanAccion) {
        this.accPlanAccion = accPlanAccion;
    }

    public AccPlanAccion getSelected() {
        return selected;
    }

    public void setSelected(AccPlanAccion selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

}
