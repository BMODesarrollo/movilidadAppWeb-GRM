package com.movilidad.jsf;

import com.movilidad.ejb.PrgCompaniaFacadeLocal;
import com.movilidad.model.PrgCompania;
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

@Named("prgCompaniaController")
@ViewScoped
public class PrgCompaniaController implements Serializable {

    @EJB
    private PrgCompaniaFacadeLocal prgComEJB;
    private List<PrgCompania> items = null;
    private PrgCompania selected;

    boolean qualsIgnoreCase;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PrgCompaniaController() {
    }

    public PrgCompania getSelected() {
        return selected;
    }

    public void setSelected(PrgCompania selected) {
        this.selected = selected;
    }

    public void prepareCreate() {
        selected = new PrgCompania();
    }

    public void registrar() {
        if (selected.getNombreCompania().equals("") || selected.getNombreCompania().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombre de Compañía es requerido");
            return;
        }
        selected.setNombreCompania(selected.getNombreCompania().toUpperCase());
        if (!validar(selected.getNombreCompania(), 0)) {
            MovilidadUtil.addErrorMessage("Nombre de Compañía se encuentra registrado en el sistema");
            return;
        }
        getSelected().setCreado(new Date());
        getSelected().setModificado(new Date());
        getSelected().setEstadoReg(0);
        getSelected().setUsername(user.getUsername());
        getSelected().setIdPrgCompania(0);
        prgComEJB.create(selected);
        MovilidadUtil.addSuccessMessage("Compañía registrada exitosamente");
        reset();
    }

    public void modificar() {
        if (selected.getNombreCompania().equals("") || selected.getNombreCompania().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombre de Compañía es requerido");
            return;
        }
        selected.setNombreCompania(selected.getNombreCompania().toUpperCase());
        if (!validar(selected.getNombreCompania(), selected.getIdPrgCompania())) {
            MovilidadUtil.addErrorMessage("Nombre de Compañía se encuentra registrado en el sistema");
            return;
        }
        prgComEJB.edit(selected);
        MovilidadUtil.addSuccessMessage("Nombre de Compañía Actualizada exitosamente");
        reset();
        PrimeFaces.current().executeScript("PF('PrgCompaniaEditDialog').hide()");
    }

//validar nombre unico de empresa
    boolean validar(String nom_emp, int id) {
        List<PrgCompania> findCampo = prgComEJB.findCampo("nombre_compania", nom_emp, id);
        return findCampo.isEmpty();
    }

    public void reset() {
        selected = new PrgCompania();
        items = prgComEJB.findallEst();
    }

    public void destroy() {
        getSelected().setEstadoReg(1);
        getSelected().setModificado(new Date());
        prgComEJB.edit(selected);
        MovilidadUtil.addSuccessMessage("Compañía Eliminada exitosamente");
        reset();
    }

    public List<PrgCompania> getItems() {
        items = prgComEJB.findallEst();
        return items;
    }
}
