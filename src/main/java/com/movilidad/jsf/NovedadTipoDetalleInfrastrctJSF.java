package com.movilidad.jsf;

import com.movilidad.ejb.NovedadTipoDetallesInfrastrucFacadeLocal;
import com.movilidad.ejb.NovedadTipoInfrastrucFacadeLocal;
import com.movilidad.model.NovedadTipoDetallesInfrastruc;
import com.movilidad.model.NovedadTipoInfrastruc;
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
@Named(value = "novTipoDetalleInfrastrctJSF")
@ViewScoped
public class NovedadTipoDetalleInfrastrctJSF implements Serializable {

    @EJB
    private NovedadTipoDetallesInfrastrucFacadeLocal novTipoDetallesInfrastrucEjb;
    @EJB
    private NovedadTipoInfrastrucFacadeLocal novTipoInfrastrucEjb;

    private NovedadTipoDetallesInfrastruc novedadTipoDetalle;
    private NovedadTipoDetallesInfrastruc selected;
    private NovedadTipoInfrastruc novedadTipo;
    private String nombre;

    private boolean isEditing;
    private boolean b_notifica;

    private List<NovedadTipoDetallesInfrastruc> lstNovedadTipoDetallesInfrastruc;
    private List<NovedadTipoInfrastruc> lstNovedadTipoInfrastruc;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstNovedadTipoDetallesInfrastruc = novTipoDetallesInfrastrucEjb.findAllByEstadoReg();
    }

    /**
     * Prepara la lista de visitantes antes de registrar/modificar un registro
     */
    public void prepareListNovedadTipo() {
        lstNovedadTipoInfrastruc = null;

        lstNovedadTipoInfrastruc = novTipoInfrastrucEjb.findAllByEstadoReg();
        PrimeFaces.current().executeScript("PF('novedadTipoDialog_wv').clearFilters();");

        if (lstNovedadTipoInfrastruc == null) {
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
        if (event.getObject() instanceof NovedadTipoInfrastruc) {
            setNovedadTipo((NovedadTipoInfrastruc) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVnovedadTipoDialog_wv').clearFilters();");
        PrimeFaces.current().ajax().update("frmNovedadTipo:dtNovedadTipo_id");
    }

    public void nuevo() {
        isEditing = false;
        b_notifica = false;
        nombre = "";
        novedadTipoDetalle = new NovedadTipoDetallesInfrastruc();
        novedadTipo = new NovedadTipoInfrastruc();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        b_notifica = (selected.getNotifica() == 1);
        nombre = selected.getNombre();
        novedadTipo = selected.getNovedadTipoInfrastruc();
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
                novedadTipoDetalle.setNovedadTipoInfrastruc(novedadTipo);
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

                lstNovedadTipoDetallesInfrastruc.add(novedadTipoDetalle);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (novedadTipo.getIdNovedadTipoInfrastruc() == null) {
            return "DEBE seleccionar un tipo de novedad";
        }

        if (isEditing) {
            if (novTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), selected.getIdNovedadTipoDetInfrastruc()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstNovedadTipoDetallesInfrastruc.isEmpty()) {
                if (novTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }
        return null;
    }

    public NovedadTipoDetallesInfrastruc getNovedadTipoDetalle() {
        return novedadTipoDetalle;
    }

    public void setNovedadTipoDetalle(NovedadTipoDetallesInfrastruc novedadTipoDetalle) {
        this.novedadTipoDetalle = novedadTipoDetalle;
    }

    public NovedadTipoDetallesInfrastruc getSelected() {
        return selected;
    }

    public void setSelected(NovedadTipoDetallesInfrastruc selected) {
        this.selected = selected;
    }

    public NovedadTipoInfrastruc getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(NovedadTipoInfrastruc novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<NovedadTipoDetallesInfrastruc> getLstNovedadTipoDetallesInfrastruc() {
        return lstNovedadTipoDetallesInfrastruc;
    }

    public void setLstNovedadTipoDetallesInfrastruc(List<NovedadTipoDetallesInfrastruc> lstNovedadTipoDetallesInfrastruc) {
        this.lstNovedadTipoDetallesInfrastruc = lstNovedadTipoDetallesInfrastruc;
    }

    public List<NovedadTipoInfrastruc> getLstNovedadTipoInfrastruc() {
        return lstNovedadTipoInfrastruc;
    }

    public void setLstNovedadTipoInfrastruc(List<NovedadTipoInfrastruc> lstNovedadTipoInfrastruc) {
        this.lstNovedadTipoInfrastruc = lstNovedadTipoInfrastruc;
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
