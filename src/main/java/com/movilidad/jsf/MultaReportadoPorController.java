package com.movilidad.jsf;

import com.movilidad.ejb.MultaReportadoPorFacade;
import com.movilidad.ejb.MultaReportadoPorFacadeLocal;
import com.movilidad.model.MultaClasificacion;
import com.movilidad.model.MultaReportadoPor;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("multaReportadoPorController")
@ViewScoped
public class MultaReportadoPorController implements Serializable {

    @EJB
    private MultaReportadoPorFacadeLocal multaRepoEJB;
    private List<MultaReportadoPor> items = null;
    private MultaReportadoPor selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public MultaReportadoPorController() {
    }

    public MultaReportadoPor getSelected() {
        return selected;
    }

    public void setSelected(MultaReportadoPor selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public MultaReportadoPor prepareCreate() {
        selected = new MultaReportadoPor();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("MultaReportadoPorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("MultaReportadoPorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("MultaReportadoPorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MultaReportadoPor> getItems() {
        if (items == null) {
            items = multaRepoEJB.findallEst();
            //items.removeIf((MultaReportadoPor o) ->  o.getEstadoReg() == 1);  
        }
        return items;
    }

    public void reset() {
        selected = new MultaReportadoPor();
        items = multaRepoEJB.findallEst();

    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case UPDATE:
                        selected.setUsername(user.getUsername());
                        multaRepoEJB.edit(selected);
                        PrimeFaces.current().executeScript("PF('MultaReportadoPorEditDialog').hide()");
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        multaRepoEJB.edit(selected);
                        break;

                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdMultaReportadoPor(Integer.MIN_VALUE);
                        multaRepoEJB.create(selected);
                        prepareCreate();
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public MultaReportadoPor getMultaReportadoPor(java.lang.Integer id) {
        return multaRepoEJB.find(id);
    }

    public List<MultaReportadoPor> getItemsAvailableSelectMany() {
        return multaRepoEJB.findallEst();
    }

    public List<MultaReportadoPor> getItemsAvailableSelectOne() {
        return multaRepoEJB.findallEst();
    }

    @FacesConverter(forClass = MultaReportadoPor.class)
    public static class MultaReportadoPorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MultaReportadoPorController controller = (MultaReportadoPorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "multaReportadoPorController");
            return controller.getMultaReportadoPor(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MultaReportadoPor) {
                MultaReportadoPor o = (MultaReportadoPor) object;
                return getStringKey(o.getIdMultaReportadoPor());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MultaReportadoPor.class.getName()});
                return null;
            }
        }

    }

}
