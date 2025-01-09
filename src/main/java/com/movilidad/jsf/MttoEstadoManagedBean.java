package com.movilidad.jsf;

import com.movilidad.ejb.MttoEstadoFacadeLocal;
import com.movilidad.model.MttoEstado;
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
@Named(value = "mttoEstadoBean")
@ViewScoped
public class MttoEstadoManagedBean implements Serializable {

    @EJB
    private MttoEstadoFacadeLocal mttoEstadoEjb;

    private List<MttoEstado> lstMttoEstados;

    private MttoEstado mttoEstado;
    private MttoEstado selected;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private final PrimeFaces current = PrimeFaces.current();
    
    public void nuevo() {
        mttoEstado = new MttoEstado();
        resetSelected();
    }

    public void guardar() {
        mttoEstado.setUsername(user.getUsername());
        mttoEstado.setCreado(new Date());
        mttoEstado.setEstadoReg(0);
        mttoEstadoEjb.create(mttoEstado);
        lstMttoEstados.add(mttoEstado);
        nuevo();
        MovilidadUtil.addSuccessMessage("Estado registrado con éxito");
    }

    public void editar() {
        mttoEstado = selected;
        current.executeScript("PF('estadoMtto').show();");
    }

    public void actualizar() {
        mttoEstado.setUsername(user.getUsername());
        mttoEstado.setModificado(new Date());
        mttoEstado.setEstadoReg(0);
        mttoEstadoEjb.edit(mttoEstado);
        current.executeScript("PF('estadoMtto').hide();");
        MovilidadUtil.addSuccessMessage("Estado actualizado con éxito");
    }

    private void resetSelected() {
        selected = null;
    }

    public MttoEstadoFacadeLocal getMttoEstadoEjb() {
        return mttoEstadoEjb;
    }

    public void setMttoEstadoEjb(MttoEstadoFacadeLocal mttoEstadoEjb) {
        this.mttoEstadoEjb = mttoEstadoEjb;
    }

    public List<MttoEstado> getLstMttoEstados() {
        if (lstMttoEstados == null) {
            lstMttoEstados = mttoEstadoEjb.findAll();
        }
        return lstMttoEstados;
    }

    public void setLstMttoEstados(List<MttoEstado> lstMttoEstados) {
        this.lstMttoEstados = lstMttoEstados;
    }

    public MttoEstado getMttoEstado() {
        return mttoEstado;
    }

    public void setMttoEstado(MttoEstado mttoEstado) {
        this.mttoEstado = mttoEstado;
    }

    public MttoEstado getSelected() {
        return selected;
    }

    public void setSelected(MttoEstado selected) {
        this.selected = selected;
    }

}
