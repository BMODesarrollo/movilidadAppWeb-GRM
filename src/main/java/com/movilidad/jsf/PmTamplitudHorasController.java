package com.movilidad.jsf;

import com.movilidad.ejb.PmTamplitudHorasFacadeLocal;
import com.movilidad.model.PmTamplitudHoras;
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

@Named("pmTamplitudHorasController")
@ViewScoped
public class PmTamplitudHorasController implements Serializable {

    @EJB
    private PmTamplitudHorasFacadeLocal pmTAmplHorasEJB;
    private List<PmTamplitudHoras> items = null;
    private PmTamplitudHoras selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PmTamplitudHorasController() {
    }

    public PmTamplitudHoras getSelected() {
        return selected;
    }

    public void setSelected(PmTamplitudHoras selected) {
        this.selected = selected;
    }

    public PmTamplitudHoras prepareCreate() {
        selected = new PmTamplitudHoras();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Programa master T amplitud horas se creó correctamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Error, Programa master T amplitud horas no se creó correctamente");
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Programa master T amplitud horas se actualizó correctamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Error, Programa master T amplitud horas no se actualizó correctamente");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Programa master T amplitud horas se eliminó correctamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Error, Programa master T amplitud horas no se eliminó correctamente");
        }
    }

    public List<PmTamplitudHoras> getItems() {
        items = pmTAmplHorasEJB.cargarEstadoRegistro();
        return items;
    }

    public void reset() {
        selected = new PmTamplitudHoras();
        items = pmTAmplHorasEJB.cargarEstadoRegistro();
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
                        pmTAmplHorasEJB.create(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        pmTAmplHorasEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case UPDATE:
                        getSelected().setUsername(user.getUsername());
                        getSelected().setModificado(new Date());
                        pmTAmplHorasEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('PmTamplitudHorasEditDialog').hide();");
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
