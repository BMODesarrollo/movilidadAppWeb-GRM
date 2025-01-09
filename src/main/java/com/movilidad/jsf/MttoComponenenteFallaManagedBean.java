package com.movilidad.jsf;

import com.movilidad.ejb.MttoComponenteFacadeLocal;
import com.movilidad.ejb.MttoComponenteFallaFacadeLocal;
import com.movilidad.ejb.MttoCriticidadFacadeLocal;
import com.movilidad.model.MttoComponente;
import com.movilidad.model.MttoComponenteFalla;
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
@Named(value = "mttoComponenteFallaBean")
@ViewScoped
public class MttoComponenenteFallaManagedBean implements Serializable {

    @EJB
    private MttoComponenteFallaFacadeLocal mttoComponenteFallaEjb;
    @EJB
    private MttoComponenteFacadeLocal mttoComponenteEjb;
    @EJB
    private MttoCriticidadFacadeLocal mttoCriticidadEjb;

    private List<MttoComponenteFalla> lstMttoComponenteFallas;
    private List<MttoComponente> lstMttoComponentes;
    private List<MttoCriticidad> lstMttoCriticidads;

    private MttoComponenteFalla mttoComponenteFalla;
    private MttoComponenteFalla selected;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private int i_idComponente = 0;
    private int i_idCriticidad = 0;

    private final PrimeFaces current = PrimeFaces.current();

    public void nuevo() {
        mttoComponenteFalla = new MttoComponenteFalla();
        i_idComponente = 0;
        i_idCriticidad = 0;
        resetSelected();
    }

    public void guardar() {
        mttoComponenteFalla.setIdMttoComponente(mttoComponenteEjb.find(i_idComponente));
        mttoComponenteFalla.setIdMttoCriticidad(mttoCriticidadEjb.find(i_idCriticidad));
        mttoComponenteFalla.setUsername(user.getUsername());
        mttoComponenteFalla.setCreado(new Date());
        mttoComponenteFalla.setEstadoReg(0);
        mttoComponenteFallaEjb.create(mttoComponenteFalla);
        lstMttoComponenteFallas.add(mttoComponenteFalla);
        nuevo();
        MovilidadUtil.addSuccessMessage("Falla de componente registrada con éxito");
    }

    public void editar() {
        mttoComponenteFalla = selected;
        i_idComponente = mttoComponenteFalla.getIdMttoComponente().getIdMttoComponente();
        i_idCriticidad = mttoComponenteFalla.getIdMttoCriticidad().getIdMttoCriticidad();
        current.executeScript("PF('componenteFalla').show();");
    }

    public void actualizar() {
        mttoComponenteFalla.setIdMttoComponente(mttoComponenteEjb.find(i_idComponente));
        mttoComponenteFalla.setIdMttoCriticidad(mttoCriticidadEjb.find(i_idCriticidad));
        mttoComponenteFalla.setUsername(user.getUsername());
        mttoComponenteFalla.setModificado(new Date());
        mttoComponenteFalla.setEstadoReg(0);
        mttoComponenteFallaEjb.edit(mttoComponenteFalla);
        current.executeScript("PF('componenteFalla').hide();");
        MovilidadUtil.addSuccessMessage("Falla de componente actualizada con éxito");
    }

    private void resetSelected() {
        selected = null;
    }

    public MttoComponenteFallaFacadeLocal getMttoComponenteFallaEjb() {
        return mttoComponenteFallaEjb;
    }

    public void setMttoComponenteFallaEjb(MttoComponenteFallaFacadeLocal mttoComponenteFallaEjb) {
        this.mttoComponenteFallaEjb = mttoComponenteFallaEjb;
    }

    public List<MttoComponenteFalla> getLstMttoComponenteFallas() {
        if (lstMttoComponenteFallas == null) {
            lstMttoComponenteFallas = mttoComponenteFallaEjb.findAll();
        }
        return lstMttoComponenteFallas;
    }

    public void setLstMttoComponenteFallas(List<MttoComponenteFalla> lstMttoComponenteFallas) {
        this.lstMttoComponenteFallas = lstMttoComponenteFallas;
    }

    public List<MttoComponente> getLstMttoComponentes() {
        if (lstMttoComponentes == null) {
            lstMttoComponentes = mttoComponenteEjb.findAll();
        }
        return lstMttoComponentes;
    }

    public void setLstMttoComponentes(List<MttoComponente> lstMttoComponentes) {
        this.lstMttoComponentes = lstMttoComponentes;
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

    public MttoComponenteFalla getMttoComponenteFalla() {
        return mttoComponenteFalla;
    }

    public void setMttoComponenteFalla(MttoComponenteFalla mttoComponenteFalla) {
        this.mttoComponenteFalla = mttoComponenteFalla;
    }

    public MttoComponenteFalla getSelected() {
        return selected;
    }

    public void setSelected(MttoComponenteFalla selected) {
        this.selected = selected;
    }

    public int getI_idComponente() {
        return i_idComponente;
    }

    public void setI_idComponente(int i_idComponente) {
        this.i_idComponente = i_idComponente;
    }

    public int getI_idCriticidad() {
        return i_idCriticidad;
    }

    public void setI_idCriticidad(int i_idCriticidad) {
        this.i_idCriticidad = i_idCriticidad;
    }
}
