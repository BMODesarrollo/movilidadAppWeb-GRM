package com.movilidad.jsf;

import com.movilidad.ejb.GmoNovedadTipoDetallesInfrastrucFacadeLocal;
import com.movilidad.ejb.GmoNovedadTipoInfrastrucFacadeLocal;
import com.movilidad.model.GmoNovedadTipoDetallesInfrastruc;
import com.movilidad.model.GmoNovedadTipoInfrastruc;
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
@Named(value = "gmoTipoDetalleInfrastrctBean")
@ViewScoped
public class GmoNovedadTipoDetalleInfrastrctJSF implements Serializable {

    @EJB
    private GmoNovedadTipoDetallesInfrastrucFacadeLocal novTipoDetallesInfrastrucEjb;
    @EJB
    private GmoNovedadTipoInfrastrucFacadeLocal novTipoInfrastrucEjb;

    private GmoNovedadTipoDetallesInfrastruc novedadTipoDetalle;
    private GmoNovedadTipoDetallesInfrastruc selected;
    private GmoNovedadTipoInfrastruc novedadTipo;
    private String nombre;

    private boolean isEditing;
    private boolean b_notifica;

    private List<GmoNovedadTipoDetallesInfrastruc> lstGmoNovedadTipoDetallesInfrastruc;
    private List<GmoNovedadTipoInfrastruc> lstGmoNovedadTipoInfrastruc;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstGmoNovedadTipoDetallesInfrastruc = novTipoDetallesInfrastrucEjb.findAllByEstadoReg();
    }

    /**
     * Prepara la lista de visitantes antes de registrar/modificar un registro
     */
    public void prepareListNovedadTipo() {
        lstGmoNovedadTipoInfrastruc = null;

        lstGmoNovedadTipoInfrastruc = novTipoInfrastrucEjb.findAllByEstadoReg();
        PrimeFaces.current().executeScript("PF('novedadTipoDialog_wv').clearFilters();");

        if (lstGmoNovedadTipoInfrastruc == null) {
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
        if (event.getObject() instanceof GmoNovedadTipoInfrastruc) {
            setNovedadTipo((GmoNovedadTipoInfrastruc) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVnovedadTipoDialog_wv').clearFilters();");
        PrimeFaces.current().ajax().update("frmNovedadTipo:dtNovedadTipo_id");
    }

    public void nuevo() {
        isEditing = false;
        b_notifica = false;
        nombre = "";
        novedadTipoDetalle = new GmoNovedadTipoDetallesInfrastruc();
        novedadTipo = new GmoNovedadTipoInfrastruc();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        b_notifica = (selected.getNotifica() == 1);
        nombre = selected.getNombre();
        novedadTipo = selected.getIdGmoNovedadTipoInfrastruc();
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
                novedadTipoDetalle.setNotifica(b_notifica ? 1 : 0);
                novedadTipoDetalle.setNombre(nombre);
                novedadTipoDetalle.setIdGmoNovedadTipoInfrastruc(novedadTipo);
                novedadTipoDetalle.setUsername(user.getUsername());
            if (isEditing) {
                novedadTipoDetalle.setModificado(MovilidadUtil.fechaCompletaHoy());
                novTipoDetallesInfrastrucEjb.edit(novedadTipoDetalle);

                PrimeFaces.current().executeScript("PF('wlvNovedadTipoDetalleInfrastruc').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                novedadTipoDetalle.setEstadoReg(0);
                novedadTipoDetalle.setCreado(MovilidadUtil.fechaCompletaHoy());
                novTipoDetallesInfrastrucEjb.create(novedadTipoDetalle);

                lstGmoNovedadTipoDetallesInfrastruc.add(novedadTipoDetalle);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (novedadTipo.getIdGmoNovedadTipoInfrastruc() == null) {
            return "DEBE seleccionar un tipo de novedad";
        }

        if (isEditing) {
            if (novTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), selected.getIdGmoNovedadTipoDetInfrastruc()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstGmoNovedadTipoDetallesInfrastruc.isEmpty()) {
                if (novTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }
        return null;
    }

    public GmoNovedadTipoDetallesInfrastruc getNovedadTipoDetalle() {
        return novedadTipoDetalle;
    }

    public void setNovedadTipoDetalle(GmoNovedadTipoDetallesInfrastruc novedadTipoDetalle) {
        this.novedadTipoDetalle = novedadTipoDetalle;
    }

    public GmoNovedadTipoDetallesInfrastruc getSelected() {
        return selected;
    }

    public void setSelected(GmoNovedadTipoDetallesInfrastruc selected) {
        this.selected = selected;
    }

    public GmoNovedadTipoInfrastruc getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(GmoNovedadTipoInfrastruc novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<GmoNovedadTipoDetallesInfrastruc> getLstGmoNovedadTipoDetallesInfrastruc() {
        return lstGmoNovedadTipoDetallesInfrastruc;
    }

    public void setLstGmoNovedadTipoDetallesInfrastruc(List<GmoNovedadTipoDetallesInfrastruc> lstGmoNovedadTipoDetallesInfrastruc) {
        this.lstGmoNovedadTipoDetallesInfrastruc = lstGmoNovedadTipoDetallesInfrastruc;
    }

    public List<GmoNovedadTipoInfrastruc> getLstGmoNovedadTipoInfrastruc() {
        return lstGmoNovedadTipoInfrastruc;
    }

    public void setLstGmoNovedadTipoInfrastruc(List<GmoNovedadTipoInfrastruc> lstGmoNovedadTipoInfrastruc) {
        this.lstGmoNovedadTipoInfrastruc = lstGmoNovedadTipoInfrastruc;
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
