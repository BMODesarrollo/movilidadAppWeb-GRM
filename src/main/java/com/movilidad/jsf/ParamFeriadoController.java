package com.movilidad.jsf;

import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.model.ParamFeriado;
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

@Named("paramFeriadoController")
@ViewScoped
public class ParamFeriadoController implements Serializable {

    @EJB
    private ParamFeriadoFacadeLocal paraFeEJB;
    private List<ParamFeriado> items = null;
    private ParamFeriado selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public ParamFeriadoController() {
    }

    public ParamFeriado getSelected() {
        return selected;
    }

    public void setSelected(ParamFeriado selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Date getMinimoDate() {
        Date Minimo;
        Minimo = new Date();
        return Minimo;
    }

    public ParamFeriado prepareCreate() {
        selected = new ParamFeriado();
        initializeEmbeddableKey();
        return selected;
    }

    public void reset() {
        selected = new ParamFeriado();
        items = paraFeEJB.findallEst();
    }

    public void registar() {
        if (paraFeEJB.findByFechaAndIdParamFeriado(getSelected().getFecha(), 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe una registro para la fecha seleccionada.");
            return;
        }
        create();
    }

    public void modificar() {

        if (paraFeEJB.findByFechaAndIdParamFeriado(getSelected().getFecha(), getSelected().getIdParamFeriado()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe una registro para la fecha seleccionada.");
            return;
        }
        Date hoy;
        hoy = new Date();
        Date inicio;
        Date fin;
        inicio = getSelected().getFecha();

        int mayor;

        mayor = hoy.compareTo(inicio);

//        if(mayor  < 0 ) {
        update();
//        } else {
//           MovilidadUtil.addAdvertenciaMessage("La FECHA debe ser mayor a la de hoy"); 
//            
//        }
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("ParamFeriadoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.

        } else {

            selected = null;
        }

    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("ParamFeriadoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("ParamFeriadoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ParamFeriado> getItems() {
        if (items == null) {
            items = paraFeEJB.findallEst();
            //items.removeIf((ParamFeriado o) ->  o.getEstadoReg() == 1);
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
                        paraFeEJB.edit(selected);
                        PrimeFaces.current().executeScript("PF('ParamFeriadoEditDialog').hide()");
                        break;

                    case DELETE:
                        getSelected().setEstadoReg(1);
                        paraFeEJB.remove(selected);
                        break;

                    case CREATE:

                        getSelected().setCreado(new Date());
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdParamFeriado(Integer.MIN_VALUE);
                        paraFeEJB.create(selected);
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

    public ParamFeriado getParamFeriado(java.lang.Integer id) {
        return paraFeEJB.find(id);
    }

    public List<ParamFeriado> getItemsAvailableSelectMany() {
        return paraFeEJB.findallEst();
    }

    public List<ParamFeriado> getItemsAvailableSelectOne() {
        return paraFeEJB.findallEst();
    }

    @FacesConverter(forClass = ParamFeriado.class)
    public static class ParamFeriadoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParamFeriadoController controller = (ParamFeriadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "paramFeriadoController");
            return controller.getParamFeriado(getKey(value));
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
            if (object instanceof ParamFeriado) {
                ParamFeriado o = (ParamFeriado) object;
                return getStringKey(o.getIdParamFeriado());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ParamFeriado.class.getName()});
                return null;
            }
        }

    }

}
