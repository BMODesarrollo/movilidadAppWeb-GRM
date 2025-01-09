package com.movilidad.jsf;

import com.movilidad.ejb.MultaSeguimientoFacadeLocal;
import com.movilidad.model.Multa;
import com.movilidad.model.MultaSeguimiento;
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

@Named("multaSeguimientoController")
@ViewScoped
public class MultaSeguimientoController implements Serializable {

    @EJB
    private MultaSeguimientoFacadeLocal MultaSEJB;
    private List<MultaSeguimiento> items;
    private MultaSeguimiento selected;
    private int i_idMulta;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public MultaSeguimientoController() {
    }

    public MultaSeguimiento prepareCreate() {
        selected = new MultaSeguimiento();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Segimiento de la Multa creado correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Segimiento de la Multa no se creó correctamente");
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Segimiento de la Multa actualizado correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Segimiento de la Multa no se actualizó correctamente");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Segimiento de la Multa eliminado correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Segimiento de la Multa no se eliminó correctamente");
        }
    }

    public void cargar(int i_aux) {
        i_idMulta = i_aux;
        items = MultaSEJB.obtenerMS(i_idMulta);
    }

    public void reset() {
        selected = new MultaSeguimiento();
        items = MultaSEJB.obtenerMS(i_idMulta);
    }

    void cargarMulta() {
        Multa multa = new Multa();
        multa.setIdMulta(i_idMulta);
        selected.setIdMulta(multa);
    }

    public int validarFecha() throws ParseException {
        SimpleDateFormat prueba = new SimpleDateFormat("yyyy/MM/dd");
        Date d = prueba.parse(prueba.format(new Date()));
        return selected.getFecha().compareTo(d);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        cargarMulta();
                        getSelected().setUsername(user.getUsername());
                        getSelected().setEstadoReg(0);
                        getSelected().setCreado(new Date());
                        if (validarFecha() < 0) {
                            MovilidadUtil.addErrorMessage("Fecha Desde debe ser mayor a fecha de hoy");
                            return;
                        }
                        MultaSEJB.create(selected);
                        reset();
                        JsfUtil.addSuccessMessage(successMessage);
                        break;
                    case DELETE:
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(1);
                        MultaSEJB.edit(selected);
                        reset();
                        break;
                    case UPDATE:
                        getSelected().setUsername(user.getUsername());
                        if (validarFecha() < 0) {
                            MovilidadUtil.addErrorMessage("Fecha Desde debe ser mayor a fecha de hoy");
                            return;
                        }
                        MultaSEJB.edit(selected);
                        reset();
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('MultaSeguimientoEditDialog').hide();");
                        JsfUtil.addSuccessMessage(successMessage);
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

    public MultaSeguimiento getSelected() {
        return selected;
    }

    public void setSelected(MultaSeguimiento selected) {
        this.selected = selected;
    }

    public int getI_idMulta() {
        return i_idMulta;
    }

    public void setI_idMulta(int i_idMulta) {
        this.i_idMulta = i_idMulta;
    }

    public List<MultaSeguimiento> getItems() {
        return items;
    }
}
