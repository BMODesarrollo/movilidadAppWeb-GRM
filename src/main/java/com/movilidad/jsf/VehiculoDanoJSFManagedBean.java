package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoDanoFacadeLocal;
import com.movilidad.model.VehiculoDano;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "vehiculoDanoBean")
@ViewScoped
public class VehiculoDanoJSFManagedBean implements Serializable {

    @EJB
    private VehiculoDanoFacadeLocal vehiculoDanoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private VehiculoDano vehiculoDano;
    private VehiculoDano selected;

    private List<VehiculoDano> lista;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoDanoEjb.findAll();
        this.vehiculoDano = new VehiculoDano();
        this.selected = null;
    }

    public void consultar() {
        this.lista = this.vehiculoDanoEjb.findAll();
    }

    public void guardar() {
        vehiculoDano.setUsername(user.getUsername());
        vehiculoDano.setCreado(new Date());
        this.vehiculoDanoEjb.create(vehiculoDano);
        this.lista.add(vehiculoDano);
        nuevo();
        init();
        MovilidadUtil.addSuccessMessage("Tipo de daño registrado éxitosamente.");
    }

    public void actualizar() {
        vehiculoDano.setUsername(user.getUsername());
        this.vehiculoDanoEjb.edit(vehiculoDano);
        init();
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Tipo de daño actualizado éxitosamente.");
    }

    public void editar() {
        this.vehiculoDano = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoDanoEjb.edit(selected);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado tipo de daño cambiado con éxito."));
    }

    public void nuevo() {
        this.vehiculoDano = new VehiculoDano();
        this.selected = null;
    }

    public VehiculoDanoFacadeLocal getVehiculoDanoEjb() {
        return vehiculoDanoEjb;
    }

    public void setVehiculoDanoEjb(VehiculoDanoFacadeLocal vehiculoDanoEjb) {
        this.vehiculoDanoEjb = vehiculoDanoEjb;
    }

    public VehiculoDano getVehiculoDano() {
        return vehiculoDano;
    }

    public void setVehiculoDano(VehiculoDano vehiculoDano) {
        this.vehiculoDano = vehiculoDano;
    }

    public VehiculoDano getSelected() {
        return selected;
    }

    public void setSelected(VehiculoDano selected) {
        this.selected = selected;
    }

    public List<VehiculoDano> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoDano> lista) {
        this.lista = lista;
    }

}
