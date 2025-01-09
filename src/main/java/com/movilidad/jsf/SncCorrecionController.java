package com.movilidad.jsf;

import com.movilidad.ejb.SncCorrecionFacadeLocal;
import com.movilidad.model.MultaClasificacion;
import com.movilidad.model.SncCorrecion;
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
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("sncCorrecionController")
@ViewScoped
public class SncCorrecionController implements Serializable {
    
    @EJB
    private SncCorrecionFacadeLocal sncCorreEJB;
    private List<SncCorrecion> items = null;
    private SncCorrecion selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    public SncCorrecionController() {
    }
    
    public SncCorrecion getSelected() {
        return selected;
    }
    
    public void setSelected(SncCorrecion selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    public SncCorrecion prepareCreate() {
        selected = new SncCorrecion();
        initializeEmbeddableKey();
        return selected;
    }

    public void reset() {
        selected = new SncCorrecion();
        items = sncCorreEJB.findallEst();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("SncCorrecionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("SncCorrecionUpdated"));
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("SncCorrecionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public List<SncCorrecion> getItems() {
        if (items == null) {
            items = sncCorreEJB.findallEst();
            // items.removeIf((SncCorrecion o) ->  o.getEstadoReg() == 1);
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
                        sncCorreEJB.edit(selected);
                        MovilidadUtil.hideModal("SncCorrecionCreateDialog");
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        sncCorreEJB.edit(selected);
                        break;
                    
                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdSncCorrecion(0);
                        sncCorreEJB.create(selected);
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
    
    public SncCorrecion getSncCorrecion(java.lang.Integer id) {
        return sncCorreEJB.find(id);
    }
    
    public List<SncCorrecion> getItemsAvailableSelectMany() {
        return sncCorreEJB.findallEst();
    }
    
    public List<SncCorrecion> getItemsAvailableSelectOne() {
        return sncCorreEJB.findallEst();
    }
    
    @FacesConverter(forClass = SncCorrecion.class)
    public static class SncCorrecionControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SncCorrecionController controller = (SncCorrecionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sncCorrecionController");
            return controller.getSncCorrecion(getKey(value));
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
            if (object instanceof SncCorrecion) {
                SncCorrecion o = (SncCorrecion) object;
                return getStringKey(o.getIdSncCorrecion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SncCorrecion.class.getName()});
                return null;
            }
        }
        
    }
    
}
