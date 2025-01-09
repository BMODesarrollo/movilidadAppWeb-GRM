package com.movilidad.jsf;

import com.movilidad.ejb.PmTamplitudVrbonoFacadeLocal;
import com.movilidad.model.PmTamplitudVrbono;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import com.movilidad.utils.MovilidadUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("pmTamplitudVrbonoController")
@ViewScoped
public class PmTamplitudVrbonoController implements Serializable {

    @EJB
    private PmTamplitudVrbonoFacadeLocal pmAmpliVBonoEJB;
    private List<PmTamplitudVrbono> items = null;
    private PmTamplitudVrbono selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PmTamplitudVrbonoController() {
    }

    public PmTamplitudVrbono getSelected() {
        return selected;
    }

    public void setSelected(PmTamplitudVrbono selected) {
        this.selected = selected;
    }

    public PmTamplitudVrbono prepareCreate() {
        selected = new PmTamplitudVrbono();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Programa master T amplitud Valor Bonos se creó correctamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Programa master T amplitud Valor Bonos no se creó correctamente");
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Programa master T amplitud Valor Bonos se actualizó correctamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Programa master T amplitud Valor Bonos no se actualizó correctamente");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Programa master T amplitud Valor Bonos se eliminó correctamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Programa master T amplitud Valor Bonos no se eliminó correctamente");
        }
    }

    public List<PmTamplitudVrbono> getItems() {
        items = pmAmpliVBonoEJB.cargarEstadoRegistro();
        return items;
    }

    public void reset() {
        selected = new PmTamplitudVrbono();
        items = pmAmpliVBonoEJB.cargarEstadoRegistro();
    }

    public int validarFecha() throws ParseException {
        SimpleDateFormat prueba = new SimpleDateFormat("yyyy/MM/dd");
        Date d = prueba.parse(prueba.format(new Date()));
        return selected.getDesde().compareTo(d);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getSelected().setUsername(user.getUsername());
                        getSelected().setEstadoReg(0);
                        getSelected().setCreado(new Date());
                        if (validarFecha() < 0) {
                            MovilidadUtil.addErrorMessage("Fecha Desde debe ser mayor a fecha de hoy");
                            return;
                        }

                        if (getSelected().getVrBono() < 0) {
                            MovilidadUtil.addErrorMessage("Valor Bono debe ser mayor a 0");
                            return;
                        }

                        pmAmpliVBonoEJB.create(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        pmAmpliVBonoEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case UPDATE:
                        getSelected().setUsername(user.getUsername());
                        getSelected().setModificado(new Date());
                        if (getSelected().getVrBono() < 0) {
                            MovilidadUtil.addErrorMessage("Valor Bono debe ser mayor a 0");
                            return;
                        }
                        pmAmpliVBonoEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('PmTamplitudVrbonoEditDialog').hide();");
                        reset();
                        break;
                    default:
                        break;
                }
            } catch (EJBException ex) {
                JsfUtil.addErrorMessage("Error del sistema");
            } catch (Exception ex) {
                JsfUtil.addErrorMessage("Error del sistema");
            }
        }
    }

}
