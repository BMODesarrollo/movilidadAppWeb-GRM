package com.movilidad.jsf;

import com.movilidad.ejb.PrgTareaFacadeLocal;
import com.movilidad.model.PrgTarea;
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
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("prgTareaController")
@ViewScoped
public class PrgTareaController implements Serializable {

    @EJB
    private PrgTareaFacadeLocal prgTareEJB;

    @Inject
    private SelectConfigFreewayBean configFreewayBean;
    private List<PrgTarea> items = null;
    private PrgTarea selected;

    private boolean bsumaDis;
    private boolean bcomercial;
    private boolean bmantenimeinto;
    private boolean bdisponible;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PrgTareaFacadeLocal getPrgTareEJB() {
        return prgTareEJB;
    }

    public void setPrgTareEJB(PrgTareaFacadeLocal prgTareEJB) {
        this.prgTareEJB = prgTareEJB;
    }

    public boolean isBsumaDis() {
        return bsumaDis;
    }

    public void setBsumaDis(boolean bsumaDis) {
        this.bsumaDis = bsumaDis;
    }

    public boolean isBcomercial() {
        return bcomercial;
    }

    public void setBcomercial(boolean bcomercial) {
        this.bcomercial = bcomercial;
    }

    public boolean isBmantenimeinto() {
        return bmantenimeinto;
    }

    public void setBmantenimeinto(boolean bmantenimeinto) {
        this.bmantenimeinto = bmantenimeinto;
    }

    public PrgTareaController() {
    }

    public PrgTarea getSelected() {
        return selected;
    }

    public void setSelected(PrgTarea selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        bsumaDis = false;
        bcomercial = false;
        bmantenimeinto = false;
        bdisponible = false;
    }

    public void prepareCreate() {
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        selected = new PrgTarea();
        initializeEmbeddableKey();
        MovilidadUtil.openModal("PrgTareaCreateDialog");
    }

    public void guardar() {
        PrgTarea findByNombreTareaAndIdGopUnidadFuncional = prgTareEJB.findByNombreTareaAndIdGopUnidadFuncional(getSelected().getTarea(),
                configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional().getIdGopUnidadFuncional(), 0);

        if (findByNombreTareaAndIdGopUnidadFuncional != null) {
            MovilidadUtil.addErrorMessage("Ya existe una tarea con el nombre digitado.");
            return;
        }
        getSelected().setSumDistancia(bsumaDis ? 1 : 0);
        getSelected().setComercial(bcomercial ? 1 : 0);
        getSelected().setMantenimiento(bmantenimeinto ? 1 : 0);
        getSelected().setOpDisponible(bdisponible ? 1 : 0);
        getSelected().setCreado(MovilidadUtil.fechaCompletaHoy());
        getSelected().setEstadoReg(0);
        getSelected().setUsername(user.getUsername());
        getSelected().setIdPrgTarea(0);
        getSelected().setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
        prgTareEJB.create(selected);

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        reset();
        MovilidadUtil.addSuccessMessage("Tarea de Programación se ha registrado Exitosamente");
    }

    public void reset() {
        selected = new PrgTarea();
        items = prgTareEJB.findallEst();

    }

    public void create() {
        persist(PersistAction.CREATE);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void detalle() {

    }

    public void preModificar() {
        try {
            bcomercial = (getSelected().getComercial() == 1);
        } catch (Exception e) {
            bcomercial = false;
        }
        try {
            bsumaDis = (getSelected().getSumDistancia() == 1);
        } catch (Exception e) {
            bsumaDis = false;
        }
        try {
            bmantenimeinto = (getSelected().getMantenimiento() == 1);
        } catch (Exception e) {
            bmantenimeinto = false;
        }
        try {
            bdisponible = (getSelected().getOpDisponible() == 1);
        } catch (Exception e) {
            bdisponible = false;
        }
    }

    public void modificar() {
        PrgTarea findByNombreTareaAndIdGopUnidadFuncional = prgTareEJB.findByNombreTareaAndIdGopUnidadFuncional(getSelected().getTarea(),
                getSelected().getIdGopUnidadFuncional().getIdGopUnidadFuncional(), getSelected().getIdPrgTarea());

        if (findByNombreTareaAndIdGopUnidadFuncional != null) {
            MovilidadUtil.addErrorMessage("Ya existe una tarea con el nombre digitado.");
            return;
        }
        getSelected().setUsername(user.getUsername());
        getSelected().setSumDistancia(bsumaDis ? 1 : 0);
        getSelected().setComercial(bcomercial ? 1 : 0);
        getSelected().setMantenimiento(bmantenimeinto ? 1 : 0);
        getSelected().setOpDisponible(bdisponible ? 1 : 0);
        prgTareEJB.edit(selected);
        MovilidadUtil.addSuccessMessage("Tarea de Programación se ha Modificado Exitosamente");
        PrimeFaces.current().executeScript("PF('PrgTareaEditDialog').hide()");
    }

    public void destroy() {
        getSelected().setEstadoReg(1);
        getSelected().setModificado(new Date());
        prgTareEJB.edit(selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        MovilidadUtil.addSuccessMessage("Se ha eliminado Exitosamente");
    }

    public List<PrgTarea> getItems() {
        items = prgTareEJB.findAllByIdGopUnidadFuncional(configFreewayBean.getIdGopUF());
        return items;
    }

    public PrgTarea getPrgTarea(java.lang.Integer id) {
        return prgTareEJB.find(id);
    }

    public List<PrgTarea> getItemsAvailableSelectMany() {
        return prgTareEJB.findallEst();
    }

    public List<PrgTarea> getItemsAvailableSelectOne() {
        return prgTareEJB.findallEst();
    }

    public boolean isBdisponible() {
        return bdisponible;
    }

    public void setBdisponible(boolean bdisponible) {
        this.bdisponible = bdisponible;
    }

    private void persist(PersistAction persistAction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FacesConverter(forClass = PrgTarea.class)
    public static class PrgTareaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PrgTareaController controller = (PrgTareaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "prgTareaController");
            return controller.getPrgTarea(getKey(value));
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
            if (object instanceof PrgTarea) {
                PrgTarea o = (PrgTarea) object;
                return getStringKey(o.getIdPrgTarea());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PrgTarea.class.getName()});
                return null;
            }
        }

    }

}
