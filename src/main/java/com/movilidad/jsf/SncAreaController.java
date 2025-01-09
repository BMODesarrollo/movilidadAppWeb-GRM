package com.movilidad.jsf;

import com.movilidad.ejb.SncAreaFacadeLocal;
import com.movilidad.model.SncArea;
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

@Named("sncAreaController")
@ViewScoped
public class SncAreaController implements Serializable {
    
    @EJB
    private SncAreaFacadeLocal sncAreaEJB;
    private List<SncArea> items = null;
    private SncArea selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    public SncAreaController() {
    }
    
    public SncArea getSelected() {
        return selected;
    }
    
    public void setSelected(SncArea selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    public SncArea prepareCreate() {
        selected = new SncArea();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void reset() {
        selected = new SncArea();
        items = sncAreaEJB.findallEst();
        
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("SncAreaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("SncAreaUpdated"));
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("SncAreaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public List<SncArea> getItems() {
        if (items == null) {
            items = sncAreaEJB.findallEst();
            //items.removeIf((SncArea o) ->  o.getEstadoReg() == 1);
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
                        sncAreaEJB.edit(selected);
                        MovilidadUtil.hideModal("SncAreaCreateDialog");
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        sncAreaEJB.edit(selected);
                        break;
                    
                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdSncArea(0);
                        sncAreaEJB.create(selected);
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
    
    public SncArea getSncArea(java.lang.Integer id) {
        return sncAreaEJB.find(id);
    }
    
    public List<SncArea> getItemsAvailableSelectMany() {
        return sncAreaEJB.findallEst();
    }
    
    public List<SncArea> getItemsAvailableSelectOne() {
        return sncAreaEJB.findallEst();
    }
    
    @FacesConverter(forClass = SncArea.class)
    public static class SncAreaControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SncAreaController controller = (SncAreaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sncAreaController");
            return controller.getSncArea(getKey(value));
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
            if (object instanceof SncArea) {
                SncArea o = (SncArea) object;
                return getStringKey(o.getIdSncArea());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SncArea.class.getName()});
                return null;
            }
        }
        
    }
    
}
