package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoTipoDocumentoFacadeLocal;
import com.movilidad.model.VehiculoTipoDocumentos;
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
@Named(value = "vehiculoTipoDocumentoBean")
@ViewScoped
public class VehiculoTipoDocumentoJSFManagedBean implements Serializable {
    
    @EJB
    private VehiculoTipoDocumentoFacadeLocal vehiculoTipoDocumentoEjb;
    private VehiculoTipoDocumentos vehiculoTipoDocumento;
    private List<VehiculoTipoDocumentos> lista;
    private VehiculoTipoDocumentos selected;
    private boolean obligatorio;
    private boolean vencimiento;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        this.lista = this.vehiculoTipoDocumentoEjb.findAll();
        this.vehiculoTipoDocumento = new VehiculoTipoDocumentos();
        this.selected = null;
        this.obligatorio = false;
        this.vencimiento = false;
    }
    
    public void guardar() {
        vehiculoTipoDocumento.setUsername(user.getUsername());
        vehiculoTipoDocumento.setObligatorio(this.obligatorio ? 1 : 0);
        vehiculoTipoDocumento.setVencimiento(this.vencimiento ? 1 : 0);
        vehiculoTipoDocumento.setCreado(new Date());
        this.vehiculoTipoDocumentoEjb.create(vehiculoTipoDocumento);
        init();
        MovilidadUtil.addSuccessMessage("Tipo de documento agregado.");
    }
    
    public void actualizar() {
        vehiculoTipoDocumento.setUsername(user.getUsername());
        vehiculoTipoDocumento.setObligatorio(this.obligatorio ? 1 : 0);
        vehiculoTipoDocumento.setVencimiento(this.vencimiento ? 1 : 0);
        this.vehiculoTipoDocumentoEjb.edit(vehiculoTipoDocumento);
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Tipo de documento actualizado.");
    }
    
    public void editar() {
        this.vehiculoTipoDocumento = this.selected;
        this.obligatorio = this.vehiculoTipoDocumento.getObligatorio() > 0;
        this.vencimiento = this.vehiculoTipoDocumento.getVencimiento() > 0;
    }
    
    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoTipoDocumentoEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de documento cambiado éxitosamente."));
    }
    
    public void nuevo() {
        this.vehiculoTipoDocumento = new VehiculoTipoDocumentos();
        this.selected = null;
        this.obligatorio = false;
        this.vencimiento = false;
    }
    
    public VehiculoTipoDocumentoFacadeLocal getVehiculoTipoDocumentoEjb() {
        return vehiculoTipoDocumentoEjb;
    }
    
    public void setVehiculoTipoDocumentoEjb(VehiculoTipoDocumentoFacadeLocal vehiculoTipoDocumentoEjb) {
        this.vehiculoTipoDocumentoEjb = vehiculoTipoDocumentoEjb;
    }
    
    public VehiculoTipoDocumentos getVehiculoTipoDocumento() {
        return vehiculoTipoDocumento;
    }
    
    public void setVehiculoTipoDocumento(VehiculoTipoDocumentos vehiculoTipoDocumento) {
        this.vehiculoTipoDocumento = vehiculoTipoDocumento;
    }
    
    public List<VehiculoTipoDocumentos> getLista() {
        return lista;
    }
    
    public void setLista(List<VehiculoTipoDocumentos> lista) {
        this.lista = lista;
    }
    
    public VehiculoTipoDocumentos getSelected() {
        return selected;
    }
    
    public void setSelected(VehiculoTipoDocumentos selected) {
        this.selected = selected;
    }
    
    public boolean isObligatorio() {
        return obligatorio;
    }
    
    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }
    
    public boolean isVencimiento() {
        return vencimiento;
    }
    
    public void setVencimiento(boolean vencimiento) {
        this.vencimiento = vencimiento;
    }
}
