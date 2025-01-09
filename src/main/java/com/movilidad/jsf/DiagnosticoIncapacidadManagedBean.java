package com.movilidad.jsf;

import com.movilidad.ejb.IncapacidadDxFacadeLocal;
import com.movilidad.model.IncapacidadDx;
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
@Named(value = "incapacidadDxBean")
@ViewScoped
public class DiagnosticoIncapacidadManagedBean implements Serializable {

    @EJB
    private IncapacidadDxFacadeLocal incapacidadDxEjb;

    private List<IncapacidadDx> lstIncapacidadDxs;

    private IncapacidadDx incapacidadDx;
    private IncapacidadDx selected;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private final PrimeFaces current = PrimeFaces.current();

    public void nuevo() {
        incapacidadDx = new IncapacidadDx();
        resetSelected();
    }

    public void guardar() {
        incapacidadDx.setUsername(user.getUsername());
        incapacidadDx.setCreado(new Date());
        incapacidadDx.setEstadoReg(0);
        incapacidadDxEjb.create(incapacidadDx);
        lstIncapacidadDxs.add(incapacidadDx);
        nuevo();
        MovilidadUtil.addSuccessMessage("Diagnóstico registrado con éxito");
    }

    public void editar() {
        incapacidadDx = selected;
        current.executeScript("PF('incapacidadDx').show();");
    }

    public void actualizar() {
        incapacidadDx.setUsername(user.getUsername());
        incapacidadDx.setModificado(new Date());
        incapacidadDx.setEstadoReg(0);
        incapacidadDxEjb.edit(incapacidadDx);
        current.executeScript("PF('incapacidadDx').hide();");
        MovilidadUtil.addSuccessMessage("Diagnóstico actualizado con éxito");
    }

    private void resetSelected() {
        selected = null;
    }

    public List<IncapacidadDx> getLstIncapacidadDxs() {
        if (lstIncapacidadDxs == null) {
            lstIncapacidadDxs = incapacidadDxEjb.findAll();
        }
        return lstIncapacidadDxs;
    }

    public void setLstIncapacidadDxs(List<IncapacidadDx> lstIncapacidadDxs) {
        this.lstIncapacidadDxs = lstIncapacidadDxs;
    }

    public IncapacidadDx getIncapacidadDx() {
        return incapacidadDx;
    }

    public void setIncapacidadDx(IncapacidadDx incapacidadDx) {
        this.incapacidadDx = incapacidadDx;
    }

    public IncapacidadDx getSelected() {
        return selected;
    }

    public void setSelected(IncapacidadDx selected) {
        this.selected = selected;
    }
}
