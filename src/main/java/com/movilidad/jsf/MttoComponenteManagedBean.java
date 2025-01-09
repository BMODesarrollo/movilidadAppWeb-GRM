package com.movilidad.jsf;

import com.movilidad.ejb.MttoComponenteFacadeLocal;
import com.movilidad.model.MttoComponente;
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
@Named(value = "mttoComponenteBean")
@ViewScoped
public class MttoComponenteManagedBean implements Serializable {

    @EJB
    private MttoComponenteFacadeLocal mttoComponenteEjb;

    private List<MttoComponente> lstMttoComponentes;

    private MttoComponente mttoComponente;
    private MttoComponente selected;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private final PrimeFaces current = PrimeFaces.current();
    
    public void nuevo() {
        mttoComponente = new MttoComponente();
        resetSelected();
    }

    public void guardar() {
        mttoComponente.setUsername(user.getUsername());
        mttoComponente.setCreado(new Date());
        mttoComponente.setEstadoReg(0);
        mttoComponenteEjb.create(mttoComponente);
        lstMttoComponentes.add(mttoComponente);
        nuevo();
        MovilidadUtil.addSuccessMessage("Componente registrado con éxito");
    }

    public void editar() {
        mttoComponente = selected;
        current.executeScript("PF('componenteMtto').show();");
    }

    public void actualizar() {
        mttoComponente.setUsername(user.getUsername());
        mttoComponente.setModificado(new Date());
        mttoComponente.setEstadoReg(0);
        mttoComponenteEjb.edit(mttoComponente);
        current.executeScript("PF('componenteMtto').hide();");
        MovilidadUtil.addSuccessMessage("Componente actualizado con éxito");
    }

    private void resetSelected() {
        selected = null;
    }

    public MttoComponenteFacadeLocal getMttoComponenteEjb() {
        return mttoComponenteEjb;
    }

    public void setMttoComponenteEjb(MttoComponenteFacadeLocal mttoComponenteEjb) {
        this.mttoComponenteEjb = mttoComponenteEjb;
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

    public MttoComponente getMttoComponente() {
        return mttoComponente;
    }

    public void setMttoComponente(MttoComponente mttoComponente) {
        this.mttoComponente = mttoComponente;
    }

    public MttoComponente getSelected() {
        return selected;
    }

    public void setSelected(MttoComponente selected) {
        this.selected = selected;
    }

}
