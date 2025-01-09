package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.PrgSolicitudNovedadParamFacadeLocal;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.PrgSolicitudNovedadParam;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SolicitudNovedadParamUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Named(value = "prgSolicitudNovedadParamBean")
@ViewScoped
public class PrgSolicitudNovedadParamBean implements Serializable {

    @EJB
    private PrgSolicitudNovedadParamFacadeLocal novedadParamEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetallesEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;

    private PrgSolicitudNovedadParam novedadParam;
    private PrgSolicitudNovedadParam selected;

    private NotificacionProcesos notificacionProcesos;
    private NovedadTipoDetalles novedad_cambio;
    private NovedadTipoDetalles novedad_permiso;
    private NovedadTipoDetalles novedad_licencia;
    private NovedadTipoDetalles novedad_no_firma;
    private String codigoProceso;

    private boolean isEditing;

    private List<PrgSolicitudNovedadParam> lstSolicitudNovedadParams;
    private List<NotificacionProcesos> lstNotificacionProcesos;
    private List<NovedadTipoDetalles> lstNovedadCambios;
    private List<NovedadTipoDetalles> lstNovedadPermisos;
    private List<NovedadTipoDetalles> lstNovedadLicencia;
    private List<NovedadTipoDetalles> lstNovedadNoFirma;

    private Map<String, NotificacionProcesos> hMProcesos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstSolicitudNovedadParams = novedadParamEjb.findByEstadoReg();

        if (lstSolicitudNovedadParams != null) {
            cargarDatosMap();
        }
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una
     * autorización
     */
    public void prepareListNotificacionProcesos() {
        lstNotificacionProcesos = null;
        if (lstNotificacionProcesos == null) {
            lstNotificacionProcesos = notificacionProcesosEjb.findAll(0);
            PrimeFaces.current().ajax().update("frmNotificacionProcesosList:dtNotificacionProceso");
        }
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una
     * autorización
     */
    public void prepareListNovedadCambios() {
        lstNovedadCambios = null;
        if (lstNovedadCambios == null) {
            lstNovedadCambios = novedadTipoDetallesEjb.findAll();
            PrimeFaces.current().ajax().update("frmNovedadCambiosList:dtNovedadCambios");
        }
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una
     * autorización
     */
    public void prepareListNovedadPermisos() {
        lstNovedadPermisos = null;
        if (lstNovedadPermisos == null) {
            lstNovedadPermisos = novedadTipoDetallesEjb.findAll();
            PrimeFaces.current().ajax().update("frmNovedadPermisosList:dtNovedadPermisos");
        }
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una
     * autorización
     */
    public void prepareListNovedadLicencias() {
        lstNovedadLicencia = null;
        if (lstNovedadLicencia == null) {
            lstNovedadLicencia = novedadTipoDetallesEjb.findAll();
            PrimeFaces.current().ajax().update("frmNovedadLicenciasList:dtNovedadLicencias");
        }
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una
     * autorización
     */
    public void prepareListNovedadNoFirma() {
        lstNovedadNoFirma = null;
        if (lstNovedadNoFirma == null) {
            lstNovedadNoFirma = novedadTipoDetallesEjb.findAll();
            PrimeFaces.current().ajax().update("frmNovedadNoFirmaList:dtNovedadNoFirma");
        }
    }

    /**
     * Evento que se dispara al seleccionar codigo del proceso antes de
     * registrar/modificar un registro
     *
     * @param event
     */
    public void onRowNotificacionProcesoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NotificacionProcesos) {
            setNotificacionProcesos((NotificacionProcesos) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVDtNotificacionProceso').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNotificacionProcesosList:dtNotificacionProceso");
    }

    /**
     * Evento que se dispara al seleccionar novedad correspondiente al cambio de
     * turno antes de registrar/modificar un registro
     *
     * @param event
     */
    public void onRowNovedadCambioClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDetalles) {
            setNovedad_cambio((NovedadTipoDetalles) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVDtNovedadCambios').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNovedadCambiosList:dtNovedadCambios");
    }

    /**
     * Evento que se dispara al seleccionar novedad correspondiente a los
     * permisos antes de registrar/modificar un registro
     *
     * @param event
     */
    public void onRowNovedadPermisoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDetalles) {
            setNovedad_permiso((NovedadTipoDetalles) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVDtNovedadPermisos').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNovedadPermisosList:dtNovedadPermisos");
    }

    /**
     * Evento que se dispara al seleccionar novedad correspondiente a las
     * licencias antes de registrar/modificar un registro
     *
     * @param event
     */
    public void onRowNovedadLicenciaClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDetalles) {
            setNovedad_licencia((NovedadTipoDetalles) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVDtNovedadLicencias').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNovedadLicenciasList:dtNovedadLicencias");
    }

    /**
     * Evento que se dispara al seleccionar novedad correspondiente a la NO
     * firma de licencias antes de registrar/modificar un registro
     *
     * @param event
     */
    public void onRowNovedadNoFirmaClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDetalles) {
            setNovedad_no_firma((NovedadTipoDetalles) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVDtNovedadNoFirma').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNovedadNoFirmaList:dtNovedadNoFirma");
    }

    public void nuevo() {
        isEditing = false;
        novedadParam = new PrgSolicitudNovedadParam();
        notificacionProcesos = new NotificacionProcesos();
        novedad_cambio = new NovedadTipoDetalles();
        novedad_permiso = new NovedadTipoDetalles();
        novedad_licencia = new NovedadTipoDetalles();
        novedad_no_firma = new NovedadTipoDetalles();
        selected = null;
        codigoProceso = null;
    }

    public void editar() {
        isEditing = true;
        codigoProceso = selected.getIdNotificacionProceso().getCodigoProceso();
        novedadParam = selected;
        notificacionProcesos = novedadParam.getIdNotificacionProceso();
        novedad_cambio = novedadParam.getIdNovedadCambio();
        novedad_permiso = novedadParam.getIdNovedadPermiso();
        novedad_licencia = novedadParam.getIdNovedadLicencia();
        novedad_no_firma = novedadParam.getIdNovedadNoFirma();
    }

    public void guardar() {

        String validacion = SolicitudNovedadParamUtil.validarDatos(notificacionProcesos, novedad_cambio, novedad_permiso, novedad_licencia, novedad_no_firma);

        if (isEditing) {
            if (validacion == null) {

                if (!notificacionProcesos.getCodigoProceso().equals(codigoProceso)) {
                    if (SolicitudNovedadParamUtil.validarExisteRegistro(hMProcesos, notificacionProcesos.getCodigoProceso())) {
                        MovilidadUtil.addErrorMessage("YA EXISTE un registro con ése código de proceso");
                        return;
                    }
                }

                novedadParam.setIdNotificacionProceso(notificacionProcesos);
                novedadParam.setIdNovedadCambio(novedad_cambio);
                novedadParam.setIdNovedadPermiso(novedad_permiso);
                novedadParam.setIdNovedadLicencia(novedad_licencia);
                novedadParam.setIdNovedadNoFirma(novedad_no_firma);
                novedadParam.setUsername(user.getUsername());
                novedadParam.setModificado(new Date());

                novedadParamEjb.edit(novedadParam);
                PrimeFaces.current().executeScript("PF('wlVSolicitudNovedad').hide();");
                init();
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        } else {
            if (validacion == null) {

                if (SolicitudNovedadParamUtil.validarExisteRegistro(hMProcesos, notificacionProcesos.getCodigoProceso())) {
                    MovilidadUtil.addErrorMessage("YA EXISTE un registro con ése código de proceso");
                    return;
                }

                novedadParam.setIdNotificacionProceso(notificacionProcesos);
                novedadParam.setIdNovedadCambio(novedad_cambio);
                novedadParam.setIdNovedadPermiso(novedad_permiso);
                novedadParam.setIdNovedadLicencia(novedad_licencia);
                novedadParam.setIdNovedadNoFirma(novedad_no_firma);
                novedadParam.setCreado(new Date());
                novedadParam.setEstadoReg(0);
                novedadParam.setUsername(user.getUsername());

                novedadParamEjb.create(novedadParam);
                nuevo();
                init();
                MovilidadUtil.addSuccessMessage("Registro guardado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        }
    }

    private void cargarDatosMap() {
        hMProcesos = new HashMap<>();

        for (PrgSolicitudNovedadParam param : lstSolicitudNovedadParams) {
            hMProcesos.put(param.getIdNotificacionProceso().getCodigoProceso(), param.getIdNotificacionProceso());
        }

    }

    public PrgSolicitudNovedadParam getNovedadParam() {
        return novedadParam;
    }

    public void setNovedadParam(PrgSolicitudNovedadParam novedadParam) {
        this.novedadParam = novedadParam;
    }

    public PrgSolicitudNovedadParam getSelected() {
        return selected;
    }

    public void setSelected(PrgSolicitudNovedadParam selected) {
        this.selected = selected;
    }

    public NotificacionProcesos getNotificacionProcesos() {
        return notificacionProcesos;
    }

    public void setNotificacionProcesos(NotificacionProcesos notificacionProcesos) {
        this.notificacionProcesos = notificacionProcesos;
    }

    public NovedadTipoDetalles getNovedad_cambio() {
        return novedad_cambio;
    }

    public void setNovedad_cambio(NovedadTipoDetalles novedad_cambio) {
        this.novedad_cambio = novedad_cambio;
    }

    public NovedadTipoDetalles getNovedad_permiso() {
        return novedad_permiso;
    }

    public void setNovedad_permiso(NovedadTipoDetalles novedad_permiso) {
        this.novedad_permiso = novedad_permiso;
    }

    public NovedadTipoDetalles getNovedad_licencia() {
        return novedad_licencia;
    }

    public void setNovedad_licencia(NovedadTipoDetalles novedad_licencia) {
        this.novedad_licencia = novedad_licencia;
    }

    public NovedadTipoDetalles getNovedad_no_firma() {
        return novedad_no_firma;
    }

    public void setNovedad_no_firma(NovedadTipoDetalles novedad_no_firma) {
        this.novedad_no_firma = novedad_no_firma;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<PrgSolicitudNovedadParam> getLstSolicitudNovedadParams() {
        return lstSolicitudNovedadParams;
    }

    public void setLstSolicitudNovedadParams(List<PrgSolicitudNovedadParam> lstSolicitudNovedadParams) {
        this.lstSolicitudNovedadParams = lstSolicitudNovedadParams;
    }

    public List<NotificacionProcesos> getLstNotificacionProcesos() {
        return lstNotificacionProcesos;
    }

    public void setLstNotificacionProcesos(List<NotificacionProcesos> lstNotificacionProcesos) {
        this.lstNotificacionProcesos = lstNotificacionProcesos;
    }

    public List<NovedadTipoDetalles> getLstNovedadCambios() {
        return lstNovedadCambios;
    }

    public void setLstNovedadCambios(List<NovedadTipoDetalles> lstNovedadCambios) {
        this.lstNovedadCambios = lstNovedadCambios;
    }

    public List<NovedadTipoDetalles> getLstNovedadPermisos() {
        return lstNovedadPermisos;
    }

    public void setLstNovedadPermisos(List<NovedadTipoDetalles> lstNovedadPermisos) {
        this.lstNovedadPermisos = lstNovedadPermisos;
    }

    public List<NovedadTipoDetalles> getLstNovedadLicencia() {
        return lstNovedadLicencia;
    }

    public void setLstNovedadLicencia(List<NovedadTipoDetalles> lstNovedadLicencia) {
        this.lstNovedadLicencia = lstNovedadLicencia;
    }

    public List<NovedadTipoDetalles> getLstNovedadNoFirma() {
        return lstNovedadNoFirma;
    }

    public void setLstNovedadNoFirma(List<NovedadTipoDetalles> lstNovedadNoFirma) {
        this.lstNovedadNoFirma = lstNovedadNoFirma;
    }

}
