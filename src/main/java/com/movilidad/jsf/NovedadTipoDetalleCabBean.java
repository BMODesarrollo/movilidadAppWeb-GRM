package com.movilidad.jsf;

import com.movilidad.ejb.NovedadTipoDetallesCabFacadeLocal;
import com.movilidad.ejb.NovedadTipoCabFacadeLocal;
import com.movilidad.model.NovedadTipoDetallesCab;
import com.movilidad.model.NovedadTipoCab;
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
@Named(value = "novedadTipoDetalleCabBean")
@ViewScoped
public class NovedadTipoDetalleCabBean implements Serializable {

    @EJB
    private NovedadTipoDetallesCabFacadeLocal novedadTipoDetallesCabEjb;
    @EJB
    private NovedadTipoCabFacadeLocal novedadTipoCabEjb;

    private NovedadTipoDetallesCab novedadTipoDetalle;
    private NovedadTipoDetallesCab selected;
    private NovedadTipoCab novedadTipo;
    private String nombre;

    private boolean isEditing;
    private boolean b_notifica;

    private List<NovedadTipoDetallesCab> lstNovedadTipoDetallesCab;
    private List<NovedadTipoCab> lstNovedadTipoCab;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstNovedadTipoDetallesCab = novedadTipoDetallesCabEjb.findAllByEstadoReg();
    }

    /**
     * Prepara la lista de visitantes antes de registrar/modificar un registro
     */
    public void prepareListNovedadTipo() {
        lstNovedadTipoCab = null;

        lstNovedadTipoCab = novedadTipoCabEjb.findAllByEstadoReg();
        PrimeFaces.current().executeScript("PF('wVNovedadTipoCabDialog').clearFilters();");

        if (lstNovedadTipoCab == null) {
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
        if (event.getObject() instanceof NovedadTipoCab) {
            setNovedadTipo((NovedadTipoCab) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('NovedadTipoCabDialog').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNovedadTipoCab:dtNovedadTipoCab");
    }

    public void nuevo() {
        isEditing = false;
        b_notifica = false;
        nombre = "";
        novedadTipoDetalle = new NovedadTipoDetallesCab();
        novedadTipo = new NovedadTipoCab();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        b_notifica = (selected.getNotifica() == 1);
        nombre = selected.getNombre();
        novedadTipo = selected.getIdNovedadTipoCab();
        novedadTipoDetalle = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    public void cargarEmails() {
        if (b_notifica) {
            novedadTipoDetalle.setEmails("");
        }
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            if (isEditing) {
                novedadTipoDetalle.setNotifica(b_notifica ? 1 : 0);
                novedadTipoDetalle.setNombre(nombre);
                novedadTipoDetalle.setIdNovedadTipoCab(novedadTipo);
                novedadTipoDetalle.setUsername(user.getUsername());
                novedadTipoDetalle.setModificado(new Date());
                novedadTipoDetallesCabEjb.edit(novedadTipoDetalle);

                PrimeFaces.current().executeScript("PF('wlvNovedadTipoDetalleCab').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                novedadTipoDetalle.setNotifica(b_notifica ? 1 : 0);
                novedadTipoDetalle.setNombre(nombre);
                novedadTipoDetalle.setIdNovedadTipoCab(novedadTipo);
                novedadTipoDetalle.setUsername(user.getUsername());
                novedadTipoDetalle.setEstadoReg(0);
                novedadTipoDetalle.setCreado(new Date());
                novedadTipoDetallesCabEjb.create(novedadTipoDetalle);

                lstNovedadTipoDetallesCab.add(novedadTipoDetalle);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (novedadTipo.getIdNovedadTipoCab() == null) {
            return "DEBE seleccionar un tipo de novedad";
        }

        if (isEditing) {
            if (novedadTipoDetallesCabEjb.findByNombre(nombre.trim(), selected.getIdNovedadTipoDetCab()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstNovedadTipoDetallesCab.isEmpty()) {
                if (novedadTipoDetallesCabEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }
        return null;
    }

    public NovedadTipoDetallesCab getNovedadTipoDetalle() {
        return novedadTipoDetalle;
    }

    public void setNovedadTipoDetalle(NovedadTipoDetallesCab novedadTipoDetalle) {
        this.novedadTipoDetalle = novedadTipoDetalle;
    }

    public NovedadTipoDetallesCab getSelected() {
        return selected;
    }

    public void setSelected(NovedadTipoDetallesCab selected) {
        this.selected = selected;
    }

    public NovedadTipoCab getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(NovedadTipoCab novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<NovedadTipoDetallesCab> getLstNovedadTipoDetallesCab() {
        return lstNovedadTipoDetallesCab;
    }

    public void setLstNovedadTipoDetallesCab(List<NovedadTipoDetallesCab> lstNovedadTipoDetallesCab) {
        this.lstNovedadTipoDetallesCab = lstNovedadTipoDetallesCab;
    }

    public List<NovedadTipoCab> getLstNovedadTipoCab() {
        return lstNovedadTipoCab;
    }

    public void setLstNovedadTipoCab(List<NovedadTipoCab> lstNovedadTipoCab) {
        this.lstNovedadTipoCab = lstNovedadTipoCab;
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
