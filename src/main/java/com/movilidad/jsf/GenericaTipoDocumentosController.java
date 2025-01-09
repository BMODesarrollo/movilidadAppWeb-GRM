package com.movilidad.jsf;

import com.movilidad.ejb.GenericaTipoDocumentosFacade;
import com.movilidad.ejb.GenericaTipoDocumentosFacadeLocal;
import com.movilidad.model.MultaClasificacion;
import com.movilidad.model.GenericaTipoDocumentos;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import com.movilidad.utils.Util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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

@Named("genericaTipoDocumentosController")
@ViewScoped
public class GenericaTipoDocumentosController implements Serializable {

    @EJB
    private GenericaTipoDocumentosFacadeLocal novTiDocEJB;
    private List<GenericaTipoDocumentos> items = null;
    private GenericaTipoDocumentos selected;
    private ParamAreaUsr paramAreaUsr;
    private boolean b_BtNuevo = false;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public GenericaTipoDocumentosController() {
    }

    @PostConstruct
    public void init() {
        paramAreaUsr = novTiDocEJB.findByUsername(user.getUsername());
        if (paramAreaUsr == null) {
            b_BtNuevo = true;
        }
    }

    public GenericaTipoDocumentos getSelected() {
        return selected;
    }

    public void setSelected(GenericaTipoDocumentos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public GenericaTipoDocumentos prepareCreate() {
        selected = new GenericaTipoDocumentos();
        initializeEmbeddableKey();
        return selected;
    }

    public void reset() {
        selected = new GenericaTipoDocumentos();
        items = novTiDocEJB.findAll();
    }

    public void create() {
        persist(PersistAction.CREATE, "Registro guardado éxitosamente");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Registro actualizado éxitosamente");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Registro eliminado éxitosamente");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<GenericaTipoDocumentos> getItems() {
        if (items == null) {
            if (paramAreaUsr != null) {
                items = novTiDocEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
            }
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case UPDATE:
                        if (paramAreaUsr != null) {
                            selected.setIdParamArea(paramAreaUsr.getIdParamArea());
                        }
                        selected.setUsername(user.getUsername());
                        novTiDocEJB.edit(selected);
                        PrimeFaces.current().executeScript("PF('GenericaTipoDocumentosEditDialog').hide()");
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        novTiDocEJB.remove(selected);
                        break;

                    case CREATE:
                        if (paramAreaUsr != null) {
                            selected.setIdParamArea(paramAreaUsr.getIdParamArea());
                        }
                        getSelected().setCreado(new Date());
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setObligatorio(1);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdGenericaTipoDocumento(Integer.MIN_VALUE);
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

    public GenericaTipoDocumentos getGenericaTipoDocumentos(java.lang.Integer id) {
        return novTiDocEJB.find(id);
    }

    public List<GenericaTipoDocumentos> getItemsAvailableSelectMany() {
        if (paramAreaUsr != null) {
            return novTiDocEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
        }
        return null;
    }

    public List<GenericaTipoDocumentos> getItemsAvailableSelectOne() {
        if (paramAreaUsr != null) {
            return novTiDocEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
        }
        return null;
    }

    public ParamAreaUsr getParamAreaUsr() {
        return paramAreaUsr;
    }

    public void setParamAreaUsr(ParamAreaUsr paramAreaUsr) {
        this.paramAreaUsr = paramAreaUsr;
    }

    public boolean isB_BtNuevo() {
        return b_BtNuevo;
    }

    public void setB_BtNuevo(boolean b_BtNuevo) {
        this.b_BtNuevo = b_BtNuevo;
    }

    @FacesConverter(forClass = GenericaTipoDocumentos.class)
    public static class GenericaTipoDocumentosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GenericaTipoDocumentosController controller = (GenericaTipoDocumentosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "novedadTipoDocumentosController");
            return controller.getGenericaTipoDocumentos(getKey(value));
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
            if (object instanceof GenericaTipoDocumentos) {
                GenericaTipoDocumentos o = (GenericaTipoDocumentos) object;
                return getStringKey(o.getIdGenericaTipoDocumento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), GenericaTipoDocumentos.class.getName()});
                return null;
            }
        }

    }

}
