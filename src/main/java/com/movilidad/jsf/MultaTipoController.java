package com.movilidad.jsf;



import com.movilidad.ejb.MultaTipoFacadeLocal;
import com.movilidad.model.MultaTipo;
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
import org.springframework.security.core.context.SecurityContextHolder;

@Named("multaTipoController")
@ViewScoped
public class MultaTipoController implements Serializable {

    @EJB
    private MultaTipoFacadeLocal mulTiEJB;
    private List<MultaTipo> items = null;
    private MultaTipo selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public MultaTipoController() {
    }

    public MultaTipo getSelected() {
        return selected;
    }

    public void setSelected(MultaTipo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }



    public MultaTipo prepareCreate() {
        selected = new MultaTipo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("MultaTipoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("MultaTipoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("MultaTipoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MultaTipo> getItems() {
        if (items == null) {
            items = mulTiEJB.findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case UPDATE:
                        selected.setUsername(user.getUsername());
                        mulTiEJB.edit(selected);
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        mulTiEJB.remove(selected);
                        break;

                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdMultaTipo(Integer.MIN_VALUE);
                        mulTiEJB.create(selected);
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

    public MultaTipo getMultaTipo(java.lang.Integer id) {
        return mulTiEJB.find(id);
    }

    public List<MultaTipo> getItemsAvailableSelectMany() {
        return mulTiEJB.findAll();
    }

    public List<MultaTipo> getItemsAvailableSelectOne() {
        return mulTiEJB.findAll();
    }

    @FacesConverter(forClass = MultaTipo.class)
    public static class MultaTipoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MultaTipoController controller = (MultaTipoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "multaTipoController");
            return controller.getMultaTipo(getKey(value));
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
            if (object instanceof MultaTipo) {
                MultaTipo o = (MultaTipo) object;
                return getStringKey(o.getIdMultaTipo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MultaTipo.class.getName()});
                return null;
            }
        }

    }

}
