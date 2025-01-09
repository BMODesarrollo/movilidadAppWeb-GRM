package com.movilidad.jsf;

import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.PdEstadoProcesoFacadeLocal;
import com.movilidad.ejb.GenericaPdMaestroDetalleFacadeLocal;
import com.movilidad.ejb.GenericaPdMaestroFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.PdTipoSancionFacadeLocal;
import com.movilidad.model.Generica;
import com.movilidad.model.PdEstadoProceso;
import com.movilidad.model.GenericaPdMaestro;
import com.movilidad.model.GenericaPdMaestroDetalle;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.PdTipoSancion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ProcesoDisciplinarioGenUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "genericaPdMaestroBean")
@ViewScoped
public class GenericaPdMaestroBean implements Serializable {

    @EJB
    private GenericaPdMaestroFacadeLocal pdMaestroEjb;
    @EJB
    private PdEstadoProcesoFacadeLocal pdEstadoProcesoEjb;
    @EJB
    private PdTipoSancionFacadeLocal pdTipoSancionEjb;
    @EJB
    private GenericaPdMaestroDetalleFacadeLocal detalleEjb;
    @EJB
    private GenericaFacadeLocal novedadEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrEjb;

    private List<GenericaPdMaestro> lstProcesos;
    private List<PdEstadoProceso> lstEstadoProcesos;
    private List<PdTipoSancion> lstTipoSancion;
    private List<Generica> lstNovedades;
    private List<Generica> lstNovedadesSeleccionadas;
    private List<GenericaPdMaestroDetalle> lstDetalles;
    private List<GenericaPdMaestroDetalle> lstDetallesEliminados;

    private GenericaPdMaestro genericaPdMaestro;
    private GenericaPdMaestro selected;
    private ParamArea paramArea;
    private Integer idEstadoProceso;
    private Integer idTipoSancion;
    private Date fechaDesde;
    private Date fechaHasta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;
    private boolean flagBtnNuevo = false;

    @PostConstruct
    public void init() {
        if (validarArea()) {
            lstProcesos = pdMaestroEjb.findAllByArea(paramArea.getIdParamArea());
            flagBtnNuevo = true;
        }
    }

    public void nuevo() {
        isEditing = false;
        genericaPdMaestro = new GenericaPdMaestro();
        lstDetalles = new ArrayList<>();
        lstNovedadesSeleccionadas = new ArrayList<>();
        lstEstadoProcesos = pdEstadoProcesoEjb.findAllByEstadoReg();
        lstTipoSancion = pdTipoSancionEjb.findAllByEstadoReg();
        idEstadoProceso = null;
        idTipoSancion = null;
        selected = null;
    }

    public void editar() {
        isEditing = true;
        genericaPdMaestro = selected;
        idEstadoProceso = genericaPdMaestro.getIdPdEstadoProceso().getIdPdEstadoProceso();
        idTipoSancion = genericaPdMaestro.getIdPdTipoSancion() != null ? genericaPdMaestro.getIdPdTipoSancion().getIdPdTipoSancion() : null;
        lstDetalles = genericaPdMaestro.getGenericaPdMaestroDetalleList();
        lstNovedadesSeleccionadas = new ArrayList<>();
        lstEstadoProcesos = pdEstadoProcesoEjb.findAllByEstadoReg();
        lstTipoSancion = pdTipoSancionEjb.findAllByEstadoReg();
        lstDetalles = detalleEjb.findByIdProceso(selected.getIdGenericaPdMaestro());
        lstDetallesEliminados = new ArrayList<>();
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            PdEstadoProceso estado = pdEstadoProcesoEjb.find(idEstadoProceso);

            if (estado.getCierraProceso() == 1) {
                genericaPdMaestro.setUsuarioCierre(user.getUsername());
            }

            genericaPdMaestro.setIdParamArea(paramArea);
            genericaPdMaestro.setIdPdEstadoProceso(estado);
            genericaPdMaestro.setIdPdTipoSancion(idTipoSancion != null ? new PdTipoSancion(idTipoSancion) : null);
            genericaPdMaestro.setGenericaPdMaestroDetalleList(new ArrayList<>());

            for (GenericaPdMaestroDetalle det : lstDetalles) {
                if (det.getIdGenericaPdMaestroDetalle() == null) {
                    genericaPdMaestro.getGenericaPdMaestroDetalleList().add(det);
                }
            }

            if (isEditing) {

                if (lstDetallesEliminados != null) {
                    for (GenericaPdMaestroDetalle det : lstDetallesEliminados) {
                        detalleEjb.remove(det);
                    }
                }

                genericaPdMaestro.setModificado(MovilidadUtil.fechaCompletaHoy());
                pdMaestroEjb.edit(genericaPdMaestro);

                MovilidadUtil.hideModal("wlvGenericaPdMaestro");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                genericaPdMaestro.setUsuarioApertura(user.getUsername());
                genericaPdMaestro.setCreado(MovilidadUtil.fechaCompletaHoy());
                genericaPdMaestro.setEstadoReg(0);
                pdMaestroEjb.create(genericaPdMaestro);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            init();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    /**
     * Método que se encarga de limpiar las listas de la vista modal en la que
     * se muestran las novedades.
     */
    public void prepareListadoGenericaes() {
        lstNovedades = null;
        fechaDesde = null;
        fechaHasta = null;
        lstNovedadesSeleccionadas = null;
    }

    /**
     * Método que se encarga de realizar la búsqueda de novedades por rango de
     * fechas.
     */
    public void cargarListadoGenerica() {
        lstNovedades = novedadEjb.findByDateRange(fechaDesde, fechaHasta, 0);
    }

    /*
        Método que se encarga de crear la lista de detalles que se van a 
        almacenar en la base de datos
     */
    public void crearListaDetalles() {

        if (lstNovedadesSeleccionadas == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar al menos una novedad");
            return;
        }

        /**
         * Se verifica si alguno de los detalles existe una novedad ya
         * seleccionada.
         */
        String validacion = ProcesoDisciplinarioGenUtil.verificarExisteGenerica(lstDetalles, lstNovedadesSeleccionadas);

        if (validacion != null) {
            lstNovedadesSeleccionadas = null;
            MovilidadUtil.addErrorMessage(validacion);
            return;
        }

        lstDetalles = ProcesoDisciplinarioGenUtil.generarListaDetalle(lstNovedadesSeleccionadas, user.getUsername(), genericaPdMaestro);
        lstNovedades = null;
        lstNovedadesSeleccionadas = null;
        MovilidadUtil.hideModal("NovedadesListDialog");
    }

    /**
     * Método que realiza la eliminación de detalles en la base de datos y en la
     * vista.
     *
     * @param detalle
     */
    public void eliminarDetalle(GenericaPdMaestroDetalle detalle) {
        if (detalle.getIdGenericaPdMaestroDetalle() == null) {
            lstDetalles.remove(detalle);
        } else {
            lstDetallesEliminados.add(detalle);
            lstDetalles.remove(detalle);
        }
        MovilidadUtil.addSuccessMessage("Detalle eliminado con éxito");
    }

    private String validarDatos() {

        if (lstDetalles == null || lstDetalles.isEmpty()) {
            return "DEBE haber al menos un detalle en la lista";
        }

        if (idEstadoProceso == null) {
            return "DEBE seleccionar un estado de proceso";
        }

        if (genericaPdMaestro.getFechaCierre() != null) {
            if (Util.validarFechaCambioEstado(genericaPdMaestro.getFechaApertura(), genericaPdMaestro.getFechaCierre())) {
                return "La fecha de apertura NO debe ser mayor a la de cierre";
            }
        }

        if (isEditing) {
        } else {

        }

        return null;
    }

    private boolean validarArea() {
        ParamAreaUsr paramAreaUsr = paramAreaUsrEjb.getByIdUser(user.getUsername());

        if (paramAreaUsr != null) {
            if (paramAreaUsr.getIdParamArea() != null) {
                paramArea = paramAreaUsr.getIdParamArea();
                return true;
            }
        }

        return false;
    }

    public List<GenericaPdMaestro> getLstProcesos() {
        return lstProcesos;
    }

    public void setLstProcesos(List<GenericaPdMaestro> lstProcesos) {
        this.lstProcesos = lstProcesos;
    }

    public List<PdEstadoProceso> getLstEstadoProcesos() {
        return lstEstadoProcesos;
    }

    public void setLstEstadoProcesos(List<PdEstadoProceso> lstEstadoProcesos) {
        this.lstEstadoProcesos = lstEstadoProcesos;
    }

    public List<PdTipoSancion> getLstTipoSancion() {
        return lstTipoSancion;
    }

    public void setLstTipoSancion(List<PdTipoSancion> lstTipoSancion) {
        this.lstTipoSancion = lstTipoSancion;
    }

    public List<Generica> getLstNovedades() {
        return lstNovedades;
    }

    public void setLstNovedades(List<Generica> lstNovedades) {
        this.lstNovedades = lstNovedades;
    }

    public List<Generica> getLstNovedadesSeleccionadas() {
        return lstNovedadesSeleccionadas;
    }

    public void setLstNovedadesSeleccionadas(List<Generica> lstNovedadesSeleccionadas) {
        this.lstNovedadesSeleccionadas = lstNovedadesSeleccionadas;
    }

    public List<GenericaPdMaestroDetalle> getLstDetalles() {
        return lstDetalles;
    }

    public void setLstDetalles(List<GenericaPdMaestroDetalle> lstDetalles) {
        this.lstDetalles = lstDetalles;
    }

    public List<GenericaPdMaestroDetalle> getLstDetallesEliminados() {
        return lstDetallesEliminados;
    }

    public void setLstDetallesEliminados(List<GenericaPdMaestroDetalle> lstDetallesEliminados) {
        this.lstDetallesEliminados = lstDetallesEliminados;
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

    public GenericaPdMaestro getGenericaPdMaestro() {
        return genericaPdMaestro;
    }

    public void setGenericaPdMaestro(GenericaPdMaestro genericaPdMaestro) {
        this.genericaPdMaestro = genericaPdMaestro;
    }

    public GenericaPdMaestro getSelected() {
        return selected;
    }

    public void setSelected(GenericaPdMaestro selected) {
        this.selected = selected;
    }

    public Integer getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(Integer idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    public Integer getIdTipoSancion() {
        return idTipoSancion;
    }

    public void setIdTipoSancion(Integer idTipoSancion) {
        this.idTipoSancion = idTipoSancion;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isFlagBtnNuevo() {
        return flagBtnNuevo;
    }

    public void setFlagBtnNuevo(boolean flagBtnNuevo) {
        this.flagBtnNuevo = flagBtnNuevo;
    }

}
