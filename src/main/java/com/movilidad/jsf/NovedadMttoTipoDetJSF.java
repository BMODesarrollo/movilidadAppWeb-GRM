package com.movilidad.jsf;

import com.movilidad.ejb.NovedadMttoTipoDetFacadeLocal;
import com.movilidad.ejb.NovedadMttoTipoFacadeLocal;
import com.movilidad.model.NovedadMttoTipoDet;
import com.movilidad.model.NovedadMttoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "novedadMttoTipoDetBean")
@ViewScoped
public class NovedadMttoTipoDetJSF implements Serializable {

    @EJB
    private NovedadMttoTipoDetFacadeLocal novTipoDetallesInfrastrucEjb;
    @EJB
    private NovedadMttoTipoFacadeLocal novTipoInfrastrucEjb;

    private NovedadMttoTipoDet tipoDetalle;
    private NovedadMttoTipoDet selected;
    private NovedadMttoTipo novedadTipo;
    private String nombre;

    private boolean isEditing;
    private boolean b_notifica;

    private List<NovedadMttoTipoDet> lstNovedadMttoTipoDet;
    private List<NovedadMttoTipo> lstNovedadMttoTipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstNovedadMttoTipoDet = novTipoDetallesInfrastrucEjb.findAllByEstadoReg();
    }

    /**
     * Prepara la lista de visitantes antes de registrar/modificar un registro
     */
    public void prepareListNovedadTipo() {
        lstNovedadMttoTipo = null;

        lstNovedadMttoTipo = novTipoInfrastrucEjb.findAllByEstadoReg();
        PrimeFaces.current().executeScript("PF('novedadTipoDialog_wv').clearFilters();");

        if (lstNovedadMttoTipo == null) {
            MovilidadUtil.addErrorMessage("NO se encontraron tipos registrados");
        }
    }

    /**
     * Evento que se dispara al seleccionar el tipo de novedad en el listado de
     * tipos
     *
     * @param event
     */
    public void onRowNovedadTipoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadMttoTipo) {
            setNovedadTipo((NovedadMttoTipo) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVnovedadTipoDialog_wv').clearFilters();");
        PrimeFaces.current().ajax().update("frmNovedadTipo:dtNovedadTipo_id");
    }

    public void nuevo() {
        isEditing = false;
        b_notifica = false;
        nombre = "";
        tipoDetalle = new NovedadMttoTipoDet();
        novedadTipo = new NovedadMttoTipo();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        b_notifica = (selected.getNotifica() == 1);
        nombre = selected.getNombre();
        novedadTipo = selected.getIdNovedadMttoTipo();
        tipoDetalle = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    public void cargarEmails() {
        if (b_notifica) {
            tipoDetalle.setEmails("");
        }
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            tipoDetalle.setNotifica(b_notifica ? 1 : 0);
            tipoDetalle.setNombre(nombre);
            tipoDetalle.setIdNovedadMttoTipo(novedadTipo);
            tipoDetalle.setUsername(user.getUsername());
            if (isEditing) {
                tipoDetalle.setModificado(MovilidadUtil.fechaCompletaHoy());
                novTipoDetallesInfrastrucEjb.edit(tipoDetalle);

                PrimeFaces.current().executeScript("PF('wlvNovedadMttoTipoDet').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                tipoDetalle.setEstadoReg(0);
                tipoDetalle.setCreado(MovilidadUtil.fechaCompletaHoy());
                novTipoDetallesInfrastrucEjb.create(tipoDetalle);

                lstNovedadMttoTipoDet.add(tipoDetalle);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (novedadTipo.getIdNovedadMttoTipo() == null) {
            return "DEBE seleccionar un tipo de novedad";
        }

        if (isEditing) {
            if (novTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), selected.getIdNovedadMttoTipoDet(), novedadTipo.getIdNovedadMttoTipo()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstNovedadMttoTipoDet.isEmpty()) {
                if (novTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), 0, novedadTipo.getIdNovedadMttoTipo()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }
        return null;
    }

    public NovedadMttoTipoDet getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(NovedadMttoTipoDet tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
    }

    public NovedadMttoTipoDet getSelected() {
        return selected;
    }

    public void setSelected(NovedadMttoTipoDet selected) {
        this.selected = selected;
    }

    public NovedadMttoTipo getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(NovedadMttoTipo novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<NovedadMttoTipoDet> getLstNovedadMttoTipoDet() {
        return lstNovedadMttoTipoDet;
    }

    public void setLstNovedadMttoTipoDet(List<NovedadMttoTipoDet> lstNovedadMttoTipoDet) {
        this.lstNovedadMttoTipoDet = lstNovedadMttoTipoDet;
    }

    public List<NovedadMttoTipo> getLstNovedadMttoTipo() {
        return lstNovedadMttoTipo;
    }

    public void setLstNovedadMttoTipo(List<NovedadMttoTipo> lstNovedadMttoTipo) {
        this.lstNovedadMttoTipo = lstNovedadMttoTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isB_notifica() {
        return b_notifica;
    }

    public void setB_notifica(boolean b_notifica) {
        this.b_notifica = b_notifica;
    }

}
