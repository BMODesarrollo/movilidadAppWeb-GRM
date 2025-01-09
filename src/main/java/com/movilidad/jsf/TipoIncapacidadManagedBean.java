package com.movilidad.jsf;

import com.movilidad.ejb.IncapacidadTipoFacadeLocal;
import com.movilidad.model.IncapacidadTipo;
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
@Named(value = "tipoIncapacidadBean")
@ViewScoped
public class TipoIncapacidadManagedBean implements Serializable {

    @EJB
    private IncapacidadTipoFacadeLocal incapacidadTipoEjb;

    private List<IncapacidadTipo> lstIncapacidadTipos;

    private IncapacidadTipo incapacidadTipo;
    private IncapacidadTipo selected;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private final PrimeFaces current = PrimeFaces.current();

    public void nuevo() {
        incapacidadTipo = new IncapacidadTipo();
        resetSelected();
    }

    public void guardar() {
        incapacidadTipo.setUsername(user.getUsername());
        incapacidadTipo.setCreado(new Date());
        incapacidadTipo.setEstadoReg(0);
        incapacidadTipoEjb.create(incapacidadTipo);
        lstIncapacidadTipos.add(incapacidadTipo);
        nuevo();
        MovilidadUtil.addSuccessMessage("Tipo de incapacidad registrado con éxito");
    }

    public void editar() {
        incapacidadTipo = selected;
        current.executeScript("PF('tipoIncapacidad').show();");
    }

    public void actualizar() {
        incapacidadTipo.setUsername(user.getUsername());
        incapacidadTipo.setModificado(new Date());
        incapacidadTipo.setEstadoReg(0);
        incapacidadTipoEjb.edit(incapacidadTipo);
        current.executeScript("PF('tipoIncapacidad').hide();");
        MovilidadUtil.addSuccessMessage("Tipo de incapacidad actualizado con éxito");
    }

    private void resetSelected() {
        selected = null;
    }

    public List<IncapacidadTipo> getLstIncapacidadTipos() {
        if (lstIncapacidadTipos == null) {
            lstIncapacidadTipos = incapacidadTipoEjb.findAll();
        }
        return lstIncapacidadTipos;
    }

    public void setLstIncapacidadTipos(List<IncapacidadTipo> lstIncapacidadTipos) {
        this.lstIncapacidadTipos = lstIncapacidadTipos;
    }

    public IncapacidadTipo getIncapacidadTipo() {
        return incapacidadTipo;
    }

    public void setIncapacidadTipo(IncapacidadTipo incapacidadTipo) {
        this.incapacidadTipo = incapacidadTipo;
    }

    public IncapacidadTipo getSelected() {
        return selected;
    }

    public void setSelected(IncapacidadTipo selected) {
        this.selected = selected;
    }
}
