package com.movilidad.jsf;

import com.movilidad.ejb.SstTipoLaborFacadeLocal;
import com.movilidad.model.SstTipoLabor;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "sstTipoLaboresBean")
@ViewScoped
public class SstTipoLaboresBean implements Serializable {

    @EJB
    private SstTipoLaborFacadeLocal tipoLaborEjb;

    private SstTipoLabor labor;
    private SstTipoLabor selected;
    private String tipo_labor;

    private boolean flagEdit;

    private List<SstTipoLabor> lstTipoLabores;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstTipoLabores = tipoLaborEjb.findAllEstadoReg();
    }

    public void nuevo() {
        labor = new SstTipoLabor();
        tipo_labor = "";
        selected = null;
        flagEdit = false;
    }

    public void editar() {
        flagEdit = true;
        tipo_labor = selected.getTipoLabor();
        labor = selected;
    }

    public void guardar() {
        if (flagEdit) {
            if (!verificarDatosAlActualizar()) {
                labor.setTipoLabor(tipo_labor);
                labor.setUsername(user.getUsername());
                labor.setModificado(new Date());
                tipoLaborEjb.edit(labor);
                PrimeFaces.current().executeScript("PF('wvLabores').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("El tipo de labor a actualizar YA EXISTE");
            }
        } else {
            if (tipoLaborEjb.findByTipoLabor(tipo_labor) != null) {
                MovilidadUtil.addErrorMessage("El tipo de labor a guardar YA EXISTE");
                return;
            }
            labor.setTipoLabor(tipo_labor);
            labor.setUsername(user.getUsername());
            labor.setCreado(new Date());
            labor.setEstadoReg(0);
            tipoLaborEjb.create(labor);
            lstTipoLabores.add(labor);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    private boolean verificarDatosAlActualizar() {
        if (!labor.getTipoLabor().trim().equals(tipo_labor)) {
            if (tipoLaborEjb.findByTipoLabor(tipo_labor) != null) {
                return true;
            }
        }

        return false;
    }

    public SstTipoLabor getLabor() {
        return labor;
    }

    public void setLabor(SstTipoLabor labor) {
        this.labor = labor;
    }

    public SstTipoLabor getSelected() {
        return selected;
    }

    public void setSelected(SstTipoLabor selected) {
        this.selected = selected;
    }

    public boolean isFlagEdit() {
        return flagEdit;
    }

    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }

    public List<SstTipoLabor> getLstTipoLabores() {
        return lstTipoLabores;
    }

    public void setLstTipoLabores(List<SstTipoLabor> lstTipoLabores) {
        this.lstTipoLabores = lstTipoLabores;
    }

    public String getTipo_labor() {
        return tipo_labor;
    }

    public void setTipo_labor(String tipo_labor) {
        this.tipo_labor = tipo_labor;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

}
