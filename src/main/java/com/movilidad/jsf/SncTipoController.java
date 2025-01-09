package com.movilidad.jsf;

import com.movilidad.ejb.SncTipoFacadeLocal;
import com.movilidad.model.MultaClasificacion;
import com.movilidad.model.SncTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import com.movilidad.utils.MovilidadUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("sncTipoController")
@ViewScoped
public class SncTipoController implements Serializable {

    @EJB
    private SncTipoFacadeLocal sncTiEJB;
    private List<SncTipo> items = null;
    private SncTipo selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public SncTipoController() {
    }

    public SncTipo getSelected() {
        return selected;
    }

    public void setSelected(SncTipo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public SncTipo prepareCreate() {
        selected = new SncTipo();
        initializeEmbeddableKey();
        return selected;
    }

//    public void prepareEdit() {
//        PrimeFaces current = PrimeFaces.current();
//        current.executeScript("PF('SncTipoEditDialog').show();");
//    }

    public void reset() {
        selected = new SncTipo();
        items = sncTiEJB.findallEst();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("SncTipoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("SncTipoUpdated"));
     
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("SncTipoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SncTipo> getItems() {
        if (items == null) {
            items = sncTiEJB.findallEst();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case UPDATE:
                        getSelected().setUsername(user.getUsername());
                        getSelected().setModificado(new Date());
                        sncTiEJB.edit(selected);
                        MovilidadUtil.hideModal("SncTipoCreateDialog");

                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        sncTiEJB.edit(selected);
                        break;

                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdSncTipo(0);
                        sncTiEJB.create(selected);
                        reset();
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

    public SncTipo getSncTipo(java.lang.Integer id) {
        return sncTiEJB.find(id);
    }

    public List<SncTipo> getItemsAvailableSelectMany() {
        return sncTiEJB.findallEst();
    }

    public List<SncTipo> getItemsAvailableSelectOne() {
        return sncTiEJB.findallEst();
    }

    @FacesConverter(forClass = SncTipo.class)
    public static class SncTipoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SncTipoController controller = (SncTipoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sncTipoController");
            return controller.getSncTipo(getKey(value));
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
            if (object instanceof SncTipo) {
                SncTipo o = (SncTipo) object;
                return getStringKey(o.getIdSncTipo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SncTipo.class.getName()});
                return null;
            }
        }

    }

}
