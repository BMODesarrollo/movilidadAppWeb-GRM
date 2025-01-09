package com.movilidad.jsf;

import com.movilidad.ejb.LavadoContratistaFacadeLocal;
import com.movilidad.ejb.LavadoCostoFacadeLocal;
import com.movilidad.ejb.LavadoTipoServicioFacadeLocal;
import com.movilidad.model.LavadoContratista;
import com.movilidad.model.LavadoCosto;
import com.movilidad.model.LavadoTipoServicio;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "lavadoCostoBean")
@ViewScoped
public class LavadoCostoBean implements Serializable {

    @EJB
    private LavadoCostoFacadeLocal lavadoCostoEjb;
    @EJB
    private LavadoTipoServicioFacadeLocal lavadoTipoServicioEjb;
    @EJB
    private LavadoContratistaFacadeLocal lavadoContratistaEjb;

    private LavadoCosto lavadoCosto;
    private LavadoCosto selected;
    private LavadoTipoServicio lavadoTipoServicio;
    private LavadoContratista lavadoContratista;

    private Date fechaDesde;
    private Date fechaHasta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    private List<LavadoCosto> lstLavadoCosto;

    private List<LavadoTipoServicio> lstLavadoTipoServicios;
    private List<LavadoContratista> lstContratistas;

    @PostConstruct
    public void init() {
        consultar();
    }

    /**
     * Prepara la lista de tipos de eventos antes de registrar/modificar un
     * registro.
     */
    public void prepareListLavadoTipoServicio() {
        lstLavadoTipoServicios = null;

        if (lstLavadoTipoServicios == null) {
            lstLavadoTipoServicios = lavadoTipoServicioEjb.findAllByEstadoReg();
            PrimeFaces.current().ajax().update(":frmTipoServicioList:dtLavadoTipoServicio");
            PrimeFaces.current().executeScript("PF('wlVdtLavadoTipoServicio').clearFilters();");
        }
    }

    /**
     * Evento que se dispara al seleccionar el tipo de servicio (Lavado) en el
     * modal que muestra listado de tipos
     *
     * @param event
     */
    public void onRowLavadoTipoServicioClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof LavadoTipoServicio) {
            setLavadoTipoServicio((LavadoTipoServicio) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVdtLavadoTipoServicio').clearFilters();");
    }

    /**
     * Prepara la lista de contratistas de lavado antes de registrar/modificar
     * un registro.
     */
    public void prepareListLavadoContratista() {
        lstContratistas = null;

        if (lstContratistas == null) {
            lstContratistas = lavadoContratistaEjb.findAllActivos();
            PrimeFaces.current().ajax().update(":frmContratistaList:dtLavadoContratista");
            PrimeFaces.current().executeScript("PF('wlVdtLavadoContratista').clearFilters();");
        }
    }

    /**
     * Evento que se dispara al seleccionar el contratista (Lavado) en el modal
     * que muestra listado
     *
     * @param event
     */
    public void onRowLavadoContratistaClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof LavadoContratista) {
            setLavadoContratista((LavadoContratista) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVdtLavadoContratista').clearFilters();");
    }

    /**
     * Carga los datos para un nuevo registro
     */
    public void nuevo() {
        isEditing = false;
        fechaDesde = null;
        fechaHasta = null;
        lavadoCosto = new LavadoCosto();
        lavadoTipoServicio = new LavadoTipoServicio();
        lavadoContratista = new LavadoContratista();
        selected = null;
    }

    /**
     * Realiza la carga del registro previo a la edición de éste.
     */
    public void editar() {
        isEditing = true;
        fechaDesde = selected.getDesde();
        fechaHasta = selected.getHasta();
        lavadoCosto = selected;
        lavadoTipoServicio = selected.getIdLavadoTipoServicio();
        lavadoContratista = selected.getIdLavadoContratista();
    }

    /**
     * Guarda/modifica los datos en la tabla.
     */
    public void guardar() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {

            lavadoCosto.setDesde(fechaDesde);
            lavadoCosto.setHasta(fechaHasta);
            lavadoCosto.setIdLavadoContratista(lavadoContratista);
            lavadoCosto.setIdLavadoTipoServicio(lavadoTipoServicio);
            lavadoCosto.setUsername(user.getUsername());

            if (isEditing) {

                lavadoCosto.setModificado(MovilidadUtil.fechaCompletaHoy());
                lavadoCostoEjb.edit(lavadoCosto);

                MovilidadUtil.hideModal("wlvLavadoCosto");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");

            } else {
                lavadoCosto.setEstadoReg(0);
                lavadoCosto.setCreado(MovilidadUtil.fechaCompletaHoy());
                lavadoCostoEjb.create(lavadoCosto);

                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(msgValidacion);
        }
    }

    private String validarDatos() {

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            return "La fecha desde DEBE ser menor que la fecha FIN";
        }

        if (lavadoTipoServicio.getIdLavadoTipoServicio() == null) {
            return "DEBE seleccionar un tipo de servicio";
        }

        if (lavadoContratista.getIdLavadoContratista() == null) {
            return "DEBE seleccionar un contratista";
        }

        if (isEditing) {
            if (lavadoCostoEjb.verificarRegistro(fechaDesde, fechaHasta, selected.getIdLavadoCosto(), lavadoTipoServicio.getIdLavadoTipoServicio(), lavadoContratista.getIdLavadoContratista()) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
        } else {
            if (!lstLavadoCosto.isEmpty()) {
                if (lavadoCostoEjb.verificarRegistro(fechaDesde, fechaHasta, 0, lavadoTipoServicio.getIdLavadoTipoServicio(), lavadoContratista.getIdLavadoContratista()) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
            }
        }

        return null;
    }

    private void consultar() {
        lstLavadoCosto = lavadoCostoEjb.findAllByEstadoReg();
    }

    public LavadoCosto getLavadoCosto() {
        return lavadoCosto;
    }

    public void setLavadoCosto(LavadoCosto pmVrbonoGrupal) {
        this.lavadoCosto = pmVrbonoGrupal;
    }

    public LavadoCosto getSelected() {
        return selected;
    }

    public void setSelected(LavadoCosto selected) {
        this.selected = selected;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<LavadoCosto> getLstLavadoCosto() {
        return lstLavadoCosto;
    }

    public void setLstLavadoCosto(List<LavadoCosto> lstLavadoCosto) {
        this.lstLavadoCosto = lstLavadoCosto;
    }

    public LavadoTipoServicio getLavadoTipoServicio() {
        return lavadoTipoServicio;
    }

    public void setLavadoTipoServicio(LavadoTipoServicio lavadoTipoServicio) {
        this.lavadoTipoServicio = lavadoTipoServicio;
    }

    public LavadoContratista getLavadoContratista() {
        return lavadoContratista;
    }

    public void setLavadoContratista(LavadoContratista lavadoContratista) {
        this.lavadoContratista = lavadoContratista;
    }

    public List<LavadoTipoServicio> getLstLavadoTipoServicios() {
        return lstLavadoTipoServicios;
    }

    public void setLstLavadoTipoServicios(List<LavadoTipoServicio> lstLavadoTipoServicios) {
        this.lstLavadoTipoServicios = lstLavadoTipoServicios;
    }

    public List<LavadoContratista> getLstContratistas() {
        return lstContratistas;
    }

    public void setLstContratistas(List<LavadoContratista> lstContratistas) {
        this.lstContratistas = lstContratistas;
    }

}
