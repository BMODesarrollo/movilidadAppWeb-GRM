package com.movilidad.jsf;

import com.movilidad.ejb.IncapacidadOrdenaFacadeLocal;
import com.movilidad.model.IncapacidadOrdena;
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
@Named(value = "ordenaIncapacidadBean")
@ViewScoped
public class OrdenaIncapacidadManagedBean implements Serializable {

    @EJB
    private IncapacidadOrdenaFacadeLocal incapacidadOrdenaEjb;

    private List<IncapacidadOrdena> lstIncapacidadOrdenas;

    private IncapacidadOrdena incapacidadOrdena;
    private IncapacidadOrdena selected;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private final PrimeFaces current = PrimeFaces.current();

    public void nuevo() {
        incapacidadOrdena = new IncapacidadOrdena();
        resetSelected();
    }

    public void guardar() {
        incapacidadOrdena.setUsername(user.getUsername());
        incapacidadOrdena.setCreado(new Date());
        incapacidadOrdena.setEstadoReg(0);
        incapacidadOrdenaEjb.create(incapacidadOrdena);
        lstIncapacidadOrdenas.add(incapacidadOrdena);
        nuevo();
        MovilidadUtil.addSuccessMessage("Registro agregado con éxito");
    }

    public void editar() {
        incapacidadOrdena = selected;
        current.executeScript("PF('ordenaIncapacidad').show();");
    }

    public void actualizar() {
        incapacidadOrdena.setUsername(user.getUsername());
        incapacidadOrdena.setModificado(new Date());
        incapacidadOrdena.setEstadoReg(0);
        incapacidadOrdenaEjb.edit(incapacidadOrdena);
        current.executeScript("PF('ordenaIncapacidad').hide();");
        MovilidadUtil.addSuccessMessage("Registro actualizado con éxito");
    }

    private void resetSelected() {
        selected = null;
    }

    public List<IncapacidadOrdena> getLstIncapacidadOrdenas() {
        if (lstIncapacidadOrdenas == null) {
            lstIncapacidadOrdenas = incapacidadOrdenaEjb.findAll();
        }
        return lstIncapacidadOrdenas;
    }

    public void setLstIncapacidadOrdenas(List<IncapacidadOrdena> lstIncapacidadOrdenas) {
        this.lstIncapacidadOrdenas = lstIncapacidadOrdenas;
    }

    public IncapacidadOrdena getIncapacidadOrdena() {
        return incapacidadOrdena;
    }

    public void setIncapacidadOrdena(IncapacidadOrdena incapacidadOrdena) {
        this.incapacidadOrdena = incapacidadOrdena;
    }

    public IncapacidadOrdena getSelected() {
        return selected;
    }

    public void setSelected(IncapacidadOrdena selected) {
        this.selected = selected;
    }
}
