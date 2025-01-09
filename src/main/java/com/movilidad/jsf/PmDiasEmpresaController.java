package com.movilidad.jsf;

import com.movilidad.ejb.PmDiasEmpresaFacadeLocal;
import com.movilidad.model.PmDiasEmpresa;
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

@Named("pmDiasEmpresaController")
@ViewScoped
public class PmDiasEmpresaController implements Serializable {

    @EJB
    private PmDiasEmpresaFacadeLocal pmDiasEmpEJB;
    private List<PmDiasEmpresa> items = null;
    private PmDiasEmpresa selected;
    private Date desdeMod;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PmDiasEmpresaController() {
    }

    public PmDiasEmpresa getSelected() {
        return selected;
    }

    public void setSelected(PmDiasEmpresa selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Date getMinimoDate() {
        Date Minimo;
        Minimo = getSelected().getDesde();
        return Minimo;
    }

    public PmDiasEmpresa prepareCreate() {
        selected = new PmDiasEmpresa();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("PmDiasEmpresaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("PmDiasEmpresaUpdated"));
    }

    public void registar() {
        Date hoy;
        hoy = new Date();
        Date inicio;
        Date fin;
        inicio = getSelected().getDesde();
        fin = getSelected().getHasta();
        int mayor;
        int entre;
        mayor = hoy.compareTo(inicio);
        entre = inicio.compareTo(fin);
        if (mayor < 0) {
            if (entre == -1) {
                create();

            } else {
                MovilidadUtil.addAdvertenciaMessage("La fecha HASTA debe ser mayor a la de DESDE");

            }
        } else {
            MovilidadUtil.addAdvertenciaMessage("La FECHA debe ser mayor a la de hoy");

        }
    }

    public void premodificar() {

        desdeMod = getSelected().getDesde();

    }

    public void modificar() {
        Date hoy;
        hoy = desdeMod;
        Date inicio;
        Date fin;
        inicio = getSelected().getDesde();
        fin = getSelected().getHasta();
        int mayor;
        int entre;
        mayor = hoy.compareTo(inicio);
        entre = inicio.compareTo(fin);
        if (mayor <= 0) {
            if (entre == -1) {
                update();

            } else {
                MovilidadUtil.addAdvertenciaMessage("La fecha HASTA debe ser mayor a la de DESDE");

            }
        } else {
            MovilidadUtil.addAdvertenciaMessage("La fecha DESDE debe ser mayor a la del dia REGISTRADO");

        }
    }

    public void reset() {
        selected = new PmDiasEmpresa();
        items = pmDiasEmpEJB.findallEst();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("PmDiasEmpresaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PmDiasEmpresa> getItems() {
        if (items == null) {
            items = pmDiasEmpEJB.findallEst();
            //items.removeIf((PmDiasEmpresa o) ->  o.getEstadoReg() == 1);
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case UPDATE:
                        if (selected.getNroDias() < 0) {
                            MovilidadUtil.addErrorMessage("El Número de Días debe ser mayor o igual a 0");
                            return;
                        }
                        selected.setUsername(user.getUsername());
                        pmDiasEmpEJB.edit(selected);
                        PrimeFaces.current().executeScript("PF('PmDiasEmpresaEditDialog').hide()");
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        pmDiasEmpEJB.edit(selected);
                        break;

                    case CREATE:
                        if (selected.getNroDias() < 0) {
                            MovilidadUtil.addErrorMessage("El Número de Días debe ser mayor o igual a 0");
                            return;
                        }
                        getSelected().setCreado(new Date());
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdPmDiasEmpresa(Integer.MIN_VALUE);
                        pmDiasEmpEJB.create(selected);
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

    public PmDiasEmpresa getPmDiasEmpresa(java.lang.Integer id) {
        return pmDiasEmpEJB.find(id);
    }

    public List<PmDiasEmpresa> getItemsAvailableSelectMany() {
        return pmDiasEmpEJB.findallEst();
    }

    public List<PmDiasEmpresa> getItemsAvailableSelectOne() {
        return pmDiasEmpEJB.findallEst();
    }

    @FacesConverter(forClass = PmDiasEmpresa.class)
    public static class PmDiasEmpresaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PmDiasEmpresaController controller = (PmDiasEmpresaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pmDiasEmpresaController");
            return controller.getPmDiasEmpresa(getKey(value));
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
            if (object instanceof PmDiasEmpresa) {
                PmDiasEmpresa o = (PmDiasEmpresa) object;
                return getStringKey(o.getIdPmDiasEmpresa());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PmDiasEmpresa.class.getName()});
                return null;
            }
        }

    }

}
