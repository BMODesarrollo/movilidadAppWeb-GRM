package com.movilidad.jsf;


import com.movilidad.ejb.NovedadTipoDocumentosFacade;
import com.movilidad.ejb.NovedadTipoDocumentosFacadeLocal;
import com.movilidad.model.MultaClasificacion;
import com.movilidad.model.NovedadTipoDocumentos;
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
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("novedadTipoDocumentosController")
@ViewScoped
public class NovedadTipoDocumentosController implements Serializable {

    @EJB
    private NovedadTipoDocumentosFacadeLocal novTiDocEJB;
    private List<NovedadTipoDocumentos> items = null;
    private NovedadTipoDocumentos selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public NovedadTipoDocumentosController() {
    }

    public NovedadTipoDocumentos getSelected() {
        return selected;
    }

    public void setSelected(NovedadTipoDocumentos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }


    public NovedadTipoDocumentos prepareCreate() {
        selected = new NovedadTipoDocumentos();
        initializeEmbeddableKey();
        return selected;
    }
 public void reset() {
        selected = new NovedadTipoDocumentos();
        items = novTiDocEJB.findAll();
    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("NovedadTipoDocumentosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("NovedadTipoDocumentosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("NovedadTipoDocumentosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<NovedadTipoDocumentos> getItems() {
        if (items == null) {
            items = novTiDocEJB.findAll();
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
                        novTiDocEJB.edit(selected);
                        PrimeFaces.current().executeScript("PF('NovedadTipoDocumentosEditDialog').hide()");
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        novTiDocEJB.remove(selected);
                        break;

                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setObligatorio(1);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdNovedadTipoDocumento(Integer.MIN_VALUE);
                        novTiDocEJB.create(selected);
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

    public NovedadTipoDocumentos getNovedadTipoDocumentos(java.lang.Integer id) {
        return novTiDocEJB.find(id);
    }

    public List<NovedadTipoDocumentos> getItemsAvailableSelectMany() {
        return novTiDocEJB.findAll();
    }

    public List<NovedadTipoDocumentos> getItemsAvailableSelectOne() {
        return novTiDocEJB.findAll();
    }

    @FacesConverter(forClass = NovedadTipoDocumentos.class)
    public static class NovedadTipoDocumentosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NovedadTipoDocumentosController controller = (NovedadTipoDocumentosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "novedadTipoDocumentosController");
            return controller.getNovedadTipoDocumentos(getKey(value));
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
            if (object instanceof NovedadTipoDocumentos) {
                NovedadTipoDocumentos o = (NovedadTipoDocumentos) object;
                return getStringKey(o.getIdNovedadTipoDocumento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NovedadTipoDocumentos.class.getName()});
                return null;
            }
        }

    }

}