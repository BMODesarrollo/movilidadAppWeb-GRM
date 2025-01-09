package com.movilidad.jsf;

import com.movilidad.ejb.MttoCriticidadFacadeLocal;
import com.movilidad.model.MttoCriticidad;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "mttoCriticidadBean")
@ViewScoped
public class MttoCriticidadManagedBean implements Serializable {

    @EJB
    private MttoCriticidadFacadeLocal mttoCriticidadEjb;

    private List<MttoCriticidad> lstMttoCriticidads;

    private MttoCriticidad mttoCriticidad;
    private MttoCriticidad selected;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private final PrimeFaces current = PrimeFaces.current();
    
    public void nuevo() {
        mttoCriticidad = new MttoCriticidad();
        resetSelected();
    }

    public void guardar() {
        mttoCriticidad.setUsername(user.getUsername());
        mttoCriticidad.setCreado(new Date());
        mttoCriticidad.setEstadoReg(0);
        mttoCriticidadEjb.create(mttoCriticidad);
        lstMttoCriticidads.add(mttoCriticidad);
        nuevo();
        MovilidadUtil.addSuccessMessage("Criticidad registrada con éxito");
    }

    public void editar() {
        mttoCriticidad = selected;
        current.executeScript("PF('criticidadMtto').show();");
    }

    public void actualizar() {
        mttoCriticidad.setUsername(user.getUsername());
        mttoCriticidad.setModificado(new Date());
        mttoCriticidad.setEstadoReg(0);
        mttoCriticidadEjb.edit(mttoCriticidad);
        MovilidadUtil.hideModal("criticidadMtto");
        MovilidadUtil.addSuccessMessage("Criticidad actualizada con éxito");
    }

    private void resetSelected() {
        selected = null;
    }

    public MttoCriticidadFacadeLocal getMttoCriticidadEjb() {
        return mttoCriticidadEjb;
    }

    public void setMttoCriticidadEjb(MttoCriticidadFacadeLocal mttoCriticidadEjb) {
        this.mttoCriticidadEjb = mttoCriticidadEjb;
    }

    public List<MttoCriticidad> getLstMttoCriticidads() {
        if (lstMttoCriticidads == null) {
            lstMttoCriticidads = mttoCriticidadEjb.findAll();
        }
        return lstMttoCriticidads;
    }

    public void setLstMttoCriticidads(List<MttoCriticidad> lstMttoCriticidads) {
        this.lstMttoCriticidads = lstMttoCriticidads;
    }

    public MttoCriticidad getMttoCriticidad() {
        return mttoCriticidad;
    }

    public void setMttoCriticidad(MttoCriticidad mttoCriticidad) {
        this.mttoCriticidad = mttoCriticidad;
    }

    public MttoCriticidad getSelected() {
        return selected;
    }

    public void setSelected(MttoCriticidad selected) {
        this.selected = selected;
    }

}
