package com.movilidad.jsf;



import com.movilidad.ejb.SncDetalleFacadeLocal;
import com.movilidad.model.SncDetalle;
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

@Named("sncDetalleController")
@ViewScoped
public class SncDetalleController implements Serializable {

    @EJB
    private SncDetalleFacadeLocal sncDetaEJB;
    private List<SncDetalle> items = null;
    private SncDetalle selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public SncDetalleController() {
    }

    public SncDetalle getSelected() {
        return selected;
    }

    public void setSelected(SncDetalle selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }



    public SncDetalle prepareCreate() {
        selected = new SncDetalle();
        initializeEmbeddableKey();
        return selected;
    }
  public void reset() {
        selected = new SncDetalle();
        items = sncDetaEJB.findallEst();
    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("SncDetalleCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("SncDetalleUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("SncDetalleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SncDetalle> getItems() {
        if (items == null) {
            items = sncDetaEJB.findallEst();
            //items.removeIf((SncDetalle o) ->  o.getEstadoReg() == 1);
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
                        sncDetaEJB.edit(selected);
                        MovilidadUtil.hideModal("SncDetalleCreateDialog");
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        sncDetaEJB.edit(selected);
                        break;

                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdSncDetalle(0);
                        sncDetaEJB.create(selected);
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

    public SncDetalle getSncDetalle(java.lang.Integer id) {
        return sncDetaEJB.find(id);
    }

    public List<SncDetalle> getItemsAvailableSelectMany() {
        return sncDetaEJB.findallEst();
    }

    public List<SncDetalle> getItemsAvailableSelectOne() {
        return sncDetaEJB.findallEst();
    }

    @FacesConverter(forClass = SncDetalle.class)
    public static class SncDetalleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SncDetalleController controller = (SncDetalleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sncDetalleController");
            return controller.getSncDetalle(getKey(value));
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
            if (object instanceof SncDetalle) {
                SncDetalle o = (SncDetalle) object;
                return getStringKey(o.getIdSncDetalle());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SncDetalle.class.getName()});
                return null;
            }
        }

    }

}
