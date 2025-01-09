package com.movilidad.jsf;

import com.movilidad.ejb.PmPuntosEmpresaFacadeLocal;
import com.movilidad.model.PmPuntosEmpresa;
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

@Named("pmPuntosEmpresaController")
@ViewScoped
public class PmPuntosEmpresaController implements Serializable {

    @EJB
    private PmPuntosEmpresaFacadeLocal pmPunEmpEJB;
    private List<PmPuntosEmpresa> items = null;
    private PmPuntosEmpresa selected;
    private Date desdeMod;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PmPuntosEmpresaController() {
    }

    public PmPuntosEmpresa getSelected() {
        return selected;
    }

    public void setSelected(PmPuntosEmpresa selected) {
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

    public PmPuntosEmpresa prepareCreate() {
        selected = new PmPuntosEmpresa();
        initializeEmbeddableKey();
        return selected;
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
        premodificar();
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
        selected = new PmPuntosEmpresa();
        items = pmPunEmpEJB.findallEst();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("PmPuntosEmpresaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("PmPuntosEmpresaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("PmPuntosEmpresaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PmPuntosEmpresa> getItems() {
        if (items == null) {
            items = pmPunEmpEJB.findallEst();
            //items.removeIf((PmPuntosEmpresa o) ->  o.getEstadoReg() == 1);
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
                        if (selected.getVrPuntos() < 0) {
                            MovilidadUtil.addErrorMessage("Valor de puntos debe ser mayor o igual a 0");
                            return;
                        }
                        pmPunEmpEJB.edit(selected);
                        PrimeFaces.current().executeScript("PF('PmPuntosEmpresaEditDialog').hide()");
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        pmPunEmpEJB.edit(selected);
                        break;

                    case CREATE:
                        if (selected.getVrPuntos() < 0) {
                            MovilidadUtil.addErrorMessage("Valor de puntos debe ser mayor o igual a 0");
                            return;
                        }
                        getSelected().setCreado(new Date());
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setIdPmPuntosEmpresa(0);
                        pmPunEmpEJB.create(selected);
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

    public PmPuntosEmpresa getPmPuntosEmpresa(java.lang.Integer id) {
        return pmPunEmpEJB.find(id);
    }

    public List<PmPuntosEmpresa> getItemsAvailableSelectMany() {
        return pmPunEmpEJB.findallEst();
    }

    public List<PmPuntosEmpresa> getItemsAvailableSelectOne() {
        return pmPunEmpEJB.findallEst();
    }

    @FacesConverter(forClass = PmPuntosEmpresa.class)
    public static class PmPuntosEmpresaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PmPuntosEmpresaController controller = (PmPuntosEmpresaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pmPuntosEmpresaController");
            return controller.getPmPuntosEmpresa(getKey(value));
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
            if (object instanceof PmPuntosEmpresa) {
                PmPuntosEmpresa o = (PmPuntosEmpresa) object;
                return getStringKey(o.getIdPmPuntosEmpresa());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PmPuntosEmpresa.class.getName()});
                return null;
            }
        }

    }

}
