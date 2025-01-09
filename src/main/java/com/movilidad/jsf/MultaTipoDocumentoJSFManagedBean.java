package com.movilidad.jsf;

import com.movilidad.ejb.MultaTipoDocumentoFacadeLocal;
import com.movilidad.model.MultaTipoDocumentos;
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
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "multaTipoDocumentoBean")
@ViewScoped
public class MultaTipoDocumentoJSFManagedBean implements Serializable {

    @EJB
    private MultaTipoDocumentoFacadeLocal multaTipoDocumentoEjb;
    private MultaTipoDocumentos multaTipoDocumento;
    private List<MultaTipoDocumentos> lista;
    private MultaTipoDocumentos selected;
    private boolean obligatorio;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.multaTipoDocumentoEjb.findAll();
        this.multaTipoDocumento = new MultaTipoDocumentos();
        this.selected = null;
        this.obligatorio = false;
    }

    public void guardar() {
        multaTipoDocumento.setUsername(user.getUsername());
        multaTipoDocumento.setObligatorio(this.obligatorio ? 1 : 0);
        multaTipoDocumento.setCreado(new Date());
        this.multaTipoDocumentoEjb.create(multaTipoDocumento);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Tipo de documento agregado."));
    }

    public void actualizar() {
        multaTipoDocumento.setObligatorio(this.obligatorio ? 1 : 0);
        multaTipoDocumento.setUsername(user.getUsername());
        this.multaTipoDocumentoEjb.edit(multaTipoDocumento);
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Tipo de documento actualizado.");
    }

    public void editar() {
        this.multaTipoDocumento = this.selected;
        this.obligatorio = this.multaTipoDocumento.getObligatorio() > 0;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.multaTipoDocumentoEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de documento cambiado éxitosamente."));
    }

    public void nuevo() {
        this.multaTipoDocumento = new MultaTipoDocumentos();
        this.selected = null;
        this.obligatorio = false;
    }

    public MultaTipoDocumentoFacadeLocal getMultaTipoDocumentoEjb() {
        return multaTipoDocumentoEjb;
    }

    public void setMultaTipoDocumentoEjb(MultaTipoDocumentoFacadeLocal multaTipoDocumentoEjb) {
        this.multaTipoDocumentoEjb = multaTipoDocumentoEjb;
    }

    public MultaTipoDocumentos getMultaTipoDocumento() {
        return multaTipoDocumento;
    }

    public void setMultaTipoDocumento(MultaTipoDocumentos multaTipoDocumento) {
        this.multaTipoDocumento = multaTipoDocumento;
    }

    public MultaTipoDocumentos getSelected() {
        return selected;
    }

    public void setSelected(MultaTipoDocumentos selected) {
        this.selected = selected;
    }

    public List<MultaTipoDocumentos> getLista() {
        return lista;
    }

    public void setLista(List<MultaTipoDocumentos> lista) {
        this.lista = lista;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

}
