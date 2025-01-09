package com.movilidad.jsf;

import com.movilidad.ejb.ActividadInfraTipoDetFacadeLocal;
import com.movilidad.ejb.ActividadInfraTipoFacadeLocal;
import com.movilidad.model.ActividadInfraTipoDet;
import com.movilidad.model.ActividadInfraTipo;
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
@Named(value = "actividadInfraTipoDetBean")
@ViewScoped
public class ActividadInfraTipoDetJSF implements Serializable {

    @EJB
    private ActividadInfraTipoDetFacadeLocal novTipoDetallesInfrastrucEjb;
    @EJB
    private ActividadInfraTipoFacadeLocal novTipoInfrastrucEjb;

    private ActividadInfraTipoDet tipoDetalle;
    private ActividadInfraTipoDet selected;
    private ActividadInfraTipo novedadTipo;
    private String nombre;

    private boolean isEditing;
    private boolean b_notifica;

    private List<ActividadInfraTipoDet> lstActividadInfraTipoDet;
    private List<ActividadInfraTipo> lstActividadInfraTipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstActividadInfraTipoDet = novTipoDetallesInfrastrucEjb.findAllByEstadoReg();
    }

    /**
     * Prepara la lista de visitantes antes de registrar/modificar un registro
     */
    public void prepareListNovedadTipo() {
        lstActividadInfraTipo = null;

        lstActividadInfraTipo = novTipoInfrastrucEjb.findAllByEstadoReg();
        PrimeFaces.current().executeScript("PF('novedadTipoDialog_wv').clearFilters();");

        if (lstActividadInfraTipo == null) {
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
        if (event.getObject() instanceof ActividadInfraTipo) {
            setNovedadTipo((ActividadInfraTipo) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVnovedadTipoDialog_wv').clearFilters();");
        PrimeFaces.current().ajax().update("frmNovedadTipo:dtNovedadTipo_id");
    }

    public void nuevo() {
        isEditing = false;
        b_notifica = false;
        nombre = "";
        tipoDetalle = new ActividadInfraTipoDet();
        novedadTipo = new ActividadInfraTipo();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        b_notifica = (selected.getNotifica() == 1);
        nombre = selected.getNombre();
        novedadTipo = selected.getIdActividadInfraTipo();
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
            tipoDetalle.setIdActividadInfraTipo(novedadTipo);
            tipoDetalle.setUsername(user.getUsername());
            if (isEditing) {
                tipoDetalle.setModificado(MovilidadUtil.fechaCompletaHoy());
                novTipoDetallesInfrastrucEjb.edit(tipoDetalle);

                PrimeFaces.current().executeScript("PF('wlvActividadInfraTipoDet').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                tipoDetalle.setEstadoReg(0);
                tipoDetalle.setCreado(MovilidadUtil.fechaCompletaHoy());
                novTipoDetallesInfrastrucEjb.create(tipoDetalle);

                lstActividadInfraTipoDet.add(tipoDetalle);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (novedadTipo.getIdActividadInfraTipo() == null) {
            return "DEBE seleccionar un tipo de actividad";
        }

        if (isEditing) {
            if (novTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), selected.getIdActividadInfraTipoDet(), novedadTipo.getIdActividadInfraTipo()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstActividadInfraTipoDet.isEmpty()) {
                if (novTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), 0, novedadTipo.getIdActividadInfraTipo()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }
        return null;
    }

    public ActividadInfraTipoDet getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(ActividadInfraTipoDet tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
    }

    public ActividadInfraTipoDet getSelected() {
        return selected;
    }

    public void setSelected(ActividadInfraTipoDet selected) {
        this.selected = selected;
    }

    public ActividadInfraTipo getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(ActividadInfraTipo novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<ActividadInfraTipoDet> getLstActividadInfraTipoDet() {
        return lstActividadInfraTipoDet;
    }

    public void setLstActividadInfraTipoDet(List<ActividadInfraTipoDet> lstActividadInfraTipoDet) {
        this.lstActividadInfraTipoDet = lstActividadInfraTipoDet;
    }

    public List<ActividadInfraTipo> getLstActividadInfraTipo() {
        return lstActividadInfraTipo;
    }

    public void setLstActividadInfraTipo(List<ActividadInfraTipo> lstActividadInfraTipo) {
        this.lstActividadInfraTipo = lstActividadInfraTipo;
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
