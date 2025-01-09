package com.movilidad.jsf;

import com.movilidad.ejb.MultaClasificacionFacadeLocal;
import com.movilidad.model.MultaClasificacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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

@Named("multaClasificacionController")
@ViewScoped
public class MultaClasificacionController implements Serializable {
    
    private MultaClasificacion multac;
    @EJB
    private MultaClasificacionFacadeLocal multaClaEJB;
    private List<MultaClasificacion> items = null;
    private MultaClasificacion selected;
    private List<MultaClasificacion> multa;
    private int i_idTipo = 0;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    public MultaClasificacionController() {
    }

    @PostConstruct
    public void init() {

    }

    public MultaClasificacion getSelected() {
        return selected;
    }

    public void setSelected(MultaClasificacion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public MultaClasificacion prepareCreate() {
        selected = new MultaClasificacion();
        initializeEmbeddableKey();
        return selected;
    }

    public void reset() {
        selected = new MultaClasificacion();
        items = multaClaEJB.findallEst();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("MultaClasificacionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.

        }

    }

    public void listarMulta() {
        try {
            multa = multaClaEJB.findallEst();
        } catch (Exception e) {
        }
    }

    public void cargarListas() {
        listarMulta();

    }




    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("MultaClasificacionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("MultaClasificacionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MultaClasificacion> getItems() {
        if (items == null) {
            items = multaClaEJB.findallEst();
            //  items.removeIf((MultaClasificacion o) ->  o.getEstadoReg() == 1);
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
                        multaClaEJB.edit(selected);
                        PrimeFaces.current().executeScript("PF('MultaClasificacionEditDialog').hide()");

                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        multaClaEJB.edit(selected);
                        break;

                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdMultaClasificacion(0);
                        multaClaEJB.create(selected);
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

    public MultaClasificacion getMultaClasificacion(java.lang.Integer id) {
        return multaClaEJB.find(id);
    }

    public List<MultaClasificacion> getItemsAvailableSelectMany() {
        return multaClaEJB.findallEst();
    }

    public List<MultaClasificacion> getItemsAvailableSelectOne() {
        return multaClaEJB.findallEst();
    }

    @FacesConverter(forClass = MultaClasificacion.class)
    public static class MultaClasificacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MultaClasificacionController controller = (MultaClasificacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "multaClasificacionController");
            return controller.getMultaClasificacion(getKey(value));
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
            if (object instanceof MultaClasificacion) {
                MultaClasificacion o = (MultaClasificacion) object;
                return getStringKey(o.getIdMultaClasificacion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MultaClasificacion.class.getName()});
                return null;
            }
        }

    }

}
