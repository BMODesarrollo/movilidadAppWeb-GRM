package com.movilidad.jsf;

import com.movilidad.ejb.LavadoTipoFacadeLocal;
import com.movilidad.model.LavadoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "lavadoTipoBean")
@ViewScoped
public class LavadoTipoManagedBean implements Serializable {

    @EJB
    private LavadoTipoFacadeLocal lavadoTipoEjb;

    private LavadoTipo lavadoTipo;
    private LavadoTipo selected;

    private List<LavadoTipo> lstTipoLavados;

    private final PrimeFaces current = PrimeFaces.current();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void nuevo() {
        lavadoTipo = new LavadoTipo();
        selected = null;
        current.focus("frmParametro:tipo_lavado");
    }

    public void editar() {
        if (selected == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }

        lavadoTipo = selected;
        current.executeScript("PF('parametroDlg').show();");
        current.focus("frmParametro:tipo_lavado");
    }

    public void guardar() {
        if (!validarDatos()) {
            if (lavadoTipoEjb.findTipoLavado(lavadoTipo.getTipoLavado()) == null) {
                lavadoTipo.setCreado(new Date());
                lavadoTipo.setEstadoReg(0);
                lavadoTipo.setUsername(user.getUsername());
                lavadoTipoEjb.create(lavadoTipo);
                lstTipoLavados.add(lavadoTipo);
                nuevo();
                MovilidadUtil.addSuccessMessage("Tipo de lavado registrado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("El tipo de lavado que intenta registrar ya existe");
            }
        }

    }

    public void actualizar() {
        if (!validarDatos()) {
            lavadoTipo.setModificado(new Date());
            lavadoTipo.setUsername(user.getUsername());
            lavadoTipoEjb.edit(lavadoTipo);
            current.ajax().update(":frmParametroList:dtParametros");
            current.executeScript("PF('parametroDlg').hide();");
//            selected = null;
            MovilidadUtil.addSuccessMessage("Tipo de lavado actualizado éxitosamente");
        }
    }

    public boolean validarDatos() {
        if (lavadoTipo.getTipoLavado() == null || lavadoTipo.getTipoLavado().equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar tipo de lavado");
            return true;
        }
        return false;
    }

    public void handleClose(CloseEvent event) {
        selected = null;
    }

    public LavadoTipo getLavadoTipo() {
        return lavadoTipo;
    }

    public void setLavadoTipo(LavadoTipo lavadoTipo) {
        this.lavadoTipo = lavadoTipo;
    }

    public LavadoTipo getSelected() {
        return selected;
    }

    public void setSelected(LavadoTipo selected) {
        this.selected = selected;
    }

    public List<LavadoTipo> getLstTipoLavados() {
        if (lstTipoLavados == null) {
            lstTipoLavados = lavadoTipoEjb.findAll();
        }
        return lstTipoLavados;
    }

    public void setLstTipoLavados(List<LavadoTipo> lstTipoLavados) {
        this.lstTipoLavados = lstTipoLavados;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }
}
