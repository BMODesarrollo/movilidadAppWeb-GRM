package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgSolicitudCambioFacadeLocal;
import com.movilidad.ejb.PrgSolicitudFacadeLocal;
import com.movilidad.ejb.PrgSolicitudMotivoFacadeLocal;
import com.movilidad.ejb.PrgSolicitudNovedadParamFacadeLocal;
import com.movilidad.ejb.PrgSolicitudPermisoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTokenFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgSolicitud;
import com.movilidad.model.PrgSolicitudCambio;
import com.movilidad.model.PrgSolicitudMotivo;
import com.movilidad.model.PrgSolicitudNovedadParam;
import com.movilidad.model.PrgSolicitudPermiso;
import com.movilidad.model.PrgToken;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.TokenGeneratorUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "prgSolicitudesMasterBean")
@ViewScoped
public class PrgSolicitudesMasterBean implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    @EJB
    private PrgSolicitudFacadeLocal prgSolicitudEjb;

    @EJB
    private PrgSolicitudMotivoFacadeLocal prgSolicitudMotivoEjb;

    @EJB
    private PrgSolicitudPermisoFacadeLocal prgSolicitudPermisoEjb;

    @EJB
    private PrgSolicitudCambioFacadeLocal prgSolicitudCambioEjb;

    @EJB
    private PrgSerconFacadeLocal prgSerconEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @EJB
    private PrgTokenFacadeLocal prgTokenEjb;

    @EJB
    private NovedadFacadeLocal novedadEjb;

    @EJB
    private PrgSolicitudNovedadParamFacadeLocal novedadParamEjb;

    @EJB
    private PrgTcFacadeLocal prgTcEjb;

    @Inject
    private PrgSolicitudesManagedBean solicitudesJSF;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private String cod_operador;
    private String tipo_solicitud;

    private Empleado empSolicitante;

    private PrgSolicitud prgSolicitud;
    private PrgSolicitudCambio prgSolicitudCambio;
    private PrgToken prgToken;
    private PrgSercon prgSerconSolicitante;
    private PrgSercon prgSerconReemplazo;
    private PrgSolicitudPermiso prgSolicitudPermiso;
    private String codigo_operador_reemplazo;
    private Integer i_idSolicitudMotivo;

    private List<PrgSolicitudMotivo> lstSolicitudMotivos;

    private final PrimeFaces current = PrimeFaces.current();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void nuevo() {
        cod_operador = "";
        codigo_operador_reemplazo = "";
        tipo_solicitud = "";
        i_idSolicitudMotivo = null;
        empSolicitante = null;
        lstSolicitudMotivos = prgSolicitudMotivoEjb.findAll();
        prgSolicitud = new PrgSolicitud();
        prgSolicitud.setFechaSolicitud(new Date());
        prgSerconSolicitante = null;
        prgSerconReemplazo = null;

    }

    private boolean validarDatos() {

        if (i_idSolicitudMotivo == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un motivo");
            return true;
        }
        if (prgSerconSolicitante == null) {
            MovilidadUtil.addErrorMessage("Debe cargar los datos operador solicitante");
            return true;
        }

        if (tipo_solicitud.equals("permiso")) {
            int dif = MovilidadUtil.diferencia(prgSolicitudPermiso.getTimeOrigin(), prgSolicitudPermiso.getTimeDestiny());
            if (dif < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin");
                return true;
            }
        }

        if (tipo_solicitud.equals("cambio")) {
            if (prgSerconReemplazo == null) {
                MovilidadUtil.addErrorMessage("Debe cargar los datos del operador reemplazante");
                return true;
            }

            int dif_S = MovilidadUtil.diferencia(prgSolicitudCambio.getTimeOriginSolicitante(), prgSolicitudCambio.getTimeDestinySolicitante());
            if (dif_S < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin (OPERADOR SOLICITANTE)");
                return true;
            }

            int dif_R = MovilidadUtil.diferencia(prgSolicitudCambio.getTimeOriginReemplazo(), prgSolicitudCambio.getTimeDestinyReemplazo());
            if (dif_R < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin (OPERADOR REEMPLAZANTE)");
                return true;
            }

            if (validarRangoHoras(prgSerconSolicitante, prgSerconReemplazo, prgSolicitudCambio.getFecha())) {
                MovilidadUtil.addErrorMessage("El turno NO permite respetar las 12 horas de descanso reglamentarias");
                return true;
            }
        }

        return false;
    }

    public void buscarEmpleado() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            empSolicitante = empleadoEjb.getEmpleadoCodigoTM(Integer.parseInt(cod_operador));

            if (empSolicitante == null) {
                cod_operador = "";
                MovilidadUtil.addErrorMessage("El operador consultado NO se encuentra registrado");
            }

            unidadFuncionalSessionBean.setI_unidad_funcional(empSolicitante.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

        } else {
            empSolicitante = empleadoEjb.getEmpleadoCodigoTmAndUnidadFuncional(Integer.parseInt(cod_operador), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

            if (empSolicitante == null) {
                cod_operador = "";
                MovilidadUtil.addErrorMessage("El operador consultado NO se encuentra registrado ó NO corresponde a la unidad funcional del usuario");
            }
        }

    }

    public Date obtenerFecha() {
        return MovilidadUtil.sumarDias(Util.toDate(Util.dateFormat(prgSolicitud.getFechaSolicitud())), -3);
    }

    public void guardar() {
        guardarTodo();
        solicitudesJSF.getByDateRange();
    }

    @Transactional
    public void guardarTodo() {

        if (validarDatos()) {
            return;
        }

        PrgSolicitudNovedadParam novedadParam = novedadParamEjb.find(1);
        if (novedadParam == null) {
            MovilidadUtil.addErrorMessage("NO se encontró registro de parametrización de novedades para solicitudes, relacionado al envío de novedades");
            return;
        }

        prgToken = new PrgToken();
        prgToken.setTipo(1); // Tipo 1: cambios y permisos
        prgToken.setActivo(2);
        prgToken.setIdEmpleado(prgSerconSolicitante.getIdEmpleado());
        prgToken.setToken(TokenGeneratorUtil.nextToken());
        prgToken.setUsado(new Date());
        prgToken.setSolicitado(new Date());
        prgToken.setModificado(new Date());
        prgToken.setEstadoReg(0);
        prgTokenEjb.create(prgToken);

        /*
         * Se genera novedad para notificar a las partes interesadas ( Control,
         Reemplazo operador,..,etc)
         */
        Novedad novedad = new Novedad();
        novedad.setIdEmpleado(prgToken.getIdEmpleado());
        novedad.setIdGopUnidadFuncional(prgToken.getIdEmpleado().getIdGopUnidadFuncional());

        prgSolicitud.setIdPrgToken(prgToken);
        prgSolicitud.setIdGopUnidadFuncional(prgToken.getIdEmpleado().getIdGopUnidadFuncional());
        prgSolicitud.setIdPrgSercon(prgSerconSolicitante);
        prgSolicitud.setCreado(new Date());
        prgSolicitud.setIdPrgSolicitudMotivo(prgSolicitudMotivoEjb.find(i_idSolicitudMotivo));
        prgSolicitud.setEstadoReg(0);
        prgSolicitud.setReponeTiempo(0);
        prgSolicitud.setRequiereSoporte(0);
        prgSolicitud.setUserAprueba(user.getUsername());
        prgSolicitudEjb.create(prgSolicitud);

        switch (tipo_solicitud) {
            case "permiso":

                novedad.setIdNovedadTipo(novedadParam.getIdNovedadPermiso().getIdNovedadTipo());
                novedad.setIdNovedadTipoDetalle(novedadParam.getIdNovedadPermiso());
                novedad.setObservaciones("Se cubre turno");
                novedad.setPuntosPm(novedadParam.getIdNovedadPermiso().getPuntosPm());
                novedad.setFecha(prgSolicitudPermiso.getFecha());

                prgSolicitud.setConsecutivo(novedadParam.getConsecPermisos());
                prgSolicitudEjb.edit(prgSolicitud);
                novedadParamEjb.aumentarConsecutivoPermiso(novedadParam.getIdPrgSolicitudNovedadParam());

                prgSolicitudPermiso.setCreado(new Date());
                prgSolicitudPermiso.setEstadoReg(0);
                prgSolicitudPermiso.setIdPrgSolicitud(prgSolicitud);
                prgSolicitudPermisoEjb.create(prgSolicitudPermiso);
                MovilidadUtil.addSuccessMessage("Solicitud de permiso registrada con éxito");
                break;
            case "cambio":

                novedad.setIdNovedadTipo(novedadParam.getIdNovedadCambio().getIdNovedadTipo());
                novedad.setIdNovedadTipoDetalle(novedadParam.getIdNovedadCambio());
                novedad.setPuntosPm(novedadParam.getIdNovedadCambio().getPuntosPm());
                novedad.setFecha(prgSolicitudCambio.getFecha());
                novedad.setObservaciones("Se cambia turno");

                prgSolicitud.setConsecutivo(novedadParam.getConsecCambioTurno());
                prgSolicitudEjb.edit(prgSolicitud);

                novedadParamEjb.aumentarConsecutivoCambio(novedadParam.getIdPrgSolicitudNovedadParam());

                prgTcEjb.realizarCambioOperacion(prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getIdEmpleado(), prgSolicitud.getIdPrgSercon().getIdEmpleado().getIdEmpleado(), prgSolicitud.getIdPrgSercon().getSercon(), prgSolicitud.getPrgSolicitudCambioList().get(0).getFecha());
                prgTcEjb.realizarCambioOperacion(prgSolicitud.getIdPrgSercon().getIdEmpleado().getIdEmpleado(), prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getIdEmpleado(), prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getSercon(), prgSolicitud.getPrgSolicitudCambioList().get(0).getFecha());

                prgSerconEjb.cambiarNomina(prgToken.getIdEmpleado().getIdEmpleado(), prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdPrgSercon());
                prgSerconEjb.cambiarNomina(prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getIdEmpleado(), prgSolicitud.getIdPrgSercon().getIdPrgSercon());

                prgSolicitudCambio.setCreado(new Date());
                prgSolicitudCambio.setEstadoReg(0);
                prgSolicitudCambio.setIdPrgSerconReemplazo(prgSerconReemplazo);
                prgSolicitudCambio.setIdPrgSolicitud(prgSolicitud);
                prgSolicitudCambioEjb.create(prgSolicitudCambio);
                MovilidadUtil.addSuccessMessage("Solicitud de cambio registrada con éxito");
                break;
        }

        novedad.setCreado(new Date());
        novedad.setUsername(user.getUsername());
        novedad.setEstadoReg(0);

        if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == 1) {
            novedad.setProcede(1);
            novedad.setPuntosPmConciliados(novedad.getIdNovedadTipoDetalle().getPuntosPm());
        } else {
            novedad.setProcede(0);
            novedad.setPuntosPmConciliados(0);
        }

        novedadEjb.create(novedad);
        notificarCorreoNovedad(novedad);
        MovilidadUtil.addSuccessMessage("Novedad generada éxitosamente");

        notificar(novedadParam.getIdNotificacionProceso().getEmails());
        current.executeScript("PF('modalDlg').hide();");
        current.executeScript("PF('solicitudDlg').hide();");

    }

    public void mostrarTipoSolicitud() {

        if (empSolicitante == null) {
            MovilidadUtil.addErrorMessage("Debe realizar la búsqueda del operador solicitante");
            return;
        }

        switch (tipo_solicitud) {
            case "permiso":
                prgSolicitudPermiso = new PrgSolicitudPermiso();
                prgSolicitudCambio = null;
                prgSerconSolicitante = null;
                current.executeScript("PF('modalDlg').show();");
                break;
            case "cambio":
                prgSolicitudCambio = new PrgSolicitudCambio();
                prgSolicitudPermiso = null;
                prgSerconReemplazo = null;
                prgSerconSolicitante = null;
                codigo_operador_reemplazo = "";
                current.executeScript("PF('modalDlg').show();");
                break;
        }
    }

    public void cargarSerconReemplazo() {

        if (empleadoEjb.
                getEmpleadoCodigoTmAndUnidadFuncional(Integer.parseInt(codigo_operador_reemplazo),
                        empSolicitante.getIdGopUnidadFuncional()
                                .getIdGopUnidadFuncional()) == null) {
            prgSolicitudCambio.setLugarInicioReemplazo(null);
            prgSolicitudCambio.setLugarFinReemplazo(null);
            prgSolicitudCambio.setTimeOriginReemplazo(null);
            prgSolicitudCambio.setTimeDestinyReemplazo(null);
            MovilidadUtil.addErrorMessage("Operador REEMPLAZO NO pertenece a la unidad funcional del SOLICITANTE");
            return;
        }

        prgSerconReemplazo = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(codigo_operador_reemplazo, prgSolicitudCambio.getFecha(), empSolicitante.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

        if (prgSerconReemplazo == null) {
            prgSolicitudCambio.setLugarInicioReemplazo(null);
            prgSolicitudCambio.setLugarFinReemplazo(null);
            prgSolicitudCambio.setTimeOriginReemplazo(null);
            prgSolicitudCambio.setTimeDestinyReemplazo(null);
            MovilidadUtil.addErrorMessage("NO se encontró sercon para el operador REEMPLAZANTE en fecha seleccionada");
            return;
        }

        prgSolicitudCambio.setLugarInicioReemplazo(prgSerconReemplazo.getIdFromStop() != null ? prgSerconReemplazo.getIdFromStop().getName() : "N/A");
        prgSolicitudCambio.setLugarFinReemplazo(prgSerconReemplazo.getIdFromStop() != null ? prgSerconReemplazo.getIdToStop().getName() : "N/A");
        prgSolicitudCambio.setTimeOriginReemplazo(prgSerconReemplazo.getTimeOrigin());
        prgSolicitudCambio.setTimeDestinyReemplazo(prgSerconReemplazo.getTimeDestiny());

    }

    public void obtenerSercon() {
        switch (tipo_solicitud) {
            case "permiso":
                prgSerconSolicitante = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(cod_operador, prgSolicitudPermiso.getFecha(), empSolicitante.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                if (prgSerconSolicitante == null) {

                    prgSolicitudPermiso.setTimeOrigin(null);
                    prgSolicitudPermiso.setTimeDestiny(null);

                    prgSolicitudPermiso.setLugarInicio(null);
                    prgSolicitudPermiso.setLugarFin(null);

                    MovilidadUtil.addErrorMessage("NO se encontró sercon para la fecha seleccionada");
                    return;
                }

                prgSolicitudPermiso.setLugarInicio(prgSerconSolicitante.getIdFromStop() != null ? prgSerconSolicitante.getIdFromStop().getName() : "N/A");
                prgSolicitudPermiso.setLugarFin(prgSerconSolicitante.getIdFromStop() != null ? prgSerconSolicitante.getIdToStop().getName() : "N/A");
                prgSolicitudPermiso.setTimeOrigin(prgSerconSolicitante.getTimeOrigin());
                prgSolicitudPermiso.setTimeDestiny(prgSerconSolicitante.getTimeDestiny());
                break;
            case "cambio":
                prgSerconSolicitante = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(empSolicitante.getCodigoTm().toString(), prgSolicitudCambio.getFecha(), empSolicitante.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                if (prgSerconSolicitante == null) {
                    prgSolicitudCambio.setLugarInicioSolicitante(null);
                    prgSolicitudCambio.setLugarFinSolicitante(null);
                    prgSolicitudCambio.setTimeOriginSolicitante(null);
                    prgSolicitudCambio.setTimeDestinySolicitante(null);
                    MovilidadUtil.addErrorMessage("NO se encontró sercon para el operador SOLICITANTE en la fecha seleccionada");
                    return;
                }

                prgSolicitudCambio.setLugarInicioSolicitante(prgSerconSolicitante.getIdFromStop() != null ? prgSerconSolicitante.getIdFromStop().getName() : "N/A");
                prgSolicitudCambio.setLugarFinSolicitante(prgSerconSolicitante.getIdFromStop() != null ? prgSerconSolicitante.getIdToStop().getName() : "N/A");
                prgSolicitudCambio.setTimeOriginSolicitante(prgSerconSolicitante.getTimeOrigin());
                prgSolicitudCambio.setTimeDestinySolicitante(prgSerconSolicitante.getTimeDestiny());

                break;
        }
    }

    private boolean validarRangoHoras(PrgSercon solicitanteAct, PrgSercon reemplazoAct, Date fecha) {
        PrgSercon prgSerconSolicitanteAnt;
        PrgSercon prgSerconReemplazoAnt;
        PrgSercon prgSerconSolicitanteSig;
        PrgSercon prgSerconReemplazoSig;
        String hIniTurnoACambiar;
        int dif;
        Date fechaAnterior = MovilidadUtil.sumarDias(fecha, -1);
        Date fechaSiguiente = MovilidadUtil.sumarDias(fecha, 1);

        prgSerconSolicitanteAnt = prgSerconEjb.validarEmplSinJornadaByUnindadFuncional(empSolicitante.getIdEmpleado(), fechaAnterior, empSolicitante.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        prgSerconReemplazoAnt = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(codigo_operador_reemplazo, fechaAnterior, empSolicitante.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

        if (prgSerconReemplazoAnt != null) {
            // Caso 1: Verificar si el operador de reemplazo cumple 12 horas de descanso
            String sTimeOrigin = solicitanteAct.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOrigin) == 0) {
                sTimeOrigin = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOrigin, "24:00:00");
            dif = MovilidadUtil.diferencia(prgSerconReemplazoAnt.getTimeDestiny(), hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                return true;
            }
        }

        if (prgSerconSolicitanteAnt != null) {
            // Caso 2: Verificar si el operador solicitante cumple 12 horas de descanso
            String sTimeOriginRemp = reemplazoAct.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginRemp) == 0) {
                sTimeOriginRemp = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, "24:00:00");
            dif = MovilidadUtil.diferencia(prgSerconSolicitanteAnt.getTimeDestiny(), hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                return true;
            }
        }

        prgSerconSolicitanteSig = prgSerconEjb.validarEmplSinJornadaByUnindadFuncional(empSolicitante.getIdEmpleado(), fechaSiguiente, empSolicitante.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        prgSerconReemplazoSig = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(codigo_operador_reemplazo, fechaSiguiente, empSolicitante.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

        if (prgSerconSolicitanteSig != null) {

            // Caso 1: Verificar si el operador de reemplazo cumple 12 horas de descanso
            String sTimeOriginSolSig = prgSerconSolicitanteSig.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginSolSig) == 0) {
                sTimeOriginSolSig = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginSolSig, "24:00:00");
            dif = MovilidadUtil.diferencia(reemplazoAct.getTimeDestiny(), hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                return true;
            }

        }

        if (prgSerconReemplazoSig != null) {
            // Caso 2: Verificar si el operador solicitante cumple 12 horas de descanso
            String sTimeOriginRempSig = prgSerconReemplazoSig.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginRempSig) == 0) {
                sTimeOriginRempSig = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRempSig, "24:00:00");
            dif = MovilidadUtil.diferencia(solicitanteAct.getTimeDestiny(), hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                return true;
            }
        }

        return false;
    }

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    private Map getMailParamsNovedad() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_PM_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /**
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificarCorreoNovedad(Novedad novedad) {
        Map mapa = getMailParamsNovedad();
        Map mailProperties = new HashMap();
        mailProperties.put("fecha", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("tipo", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("detalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechas", novedad.getDesde() != null && novedad.getHasta() != null ? Util.dateFormat(novedad.getDesde()) + " hasta " + Util.dateFormat(novedad.getHasta()) : "");
        mailProperties.put("operador", novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getCodigoTm() + " - " + novedad.getIdEmpleado().getNombres() + " " + novedad.getIdEmpleado().getApellidos() : "");
        mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observaciones", novedad.getObservaciones());
        String subject = "Novedad " + novedad.getIdNovedadTipo().getNombreTipoNovedad();
        String destinatarios = "";

        //Busqueda Operador Máster
        if (novedad.getIdEmpleado() != null) {
            String correoMaster = "";
            if (novedad.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
                correoMaster = novedad.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
            }
            destinatarios = novedad.getIdEmpleado() != null ? correoMaster + "," + novedad.getIdEmpleado().getEmailCorporativo() : "";
        }
        if (novedad.getIdNovedadTipoDetalle().getNotificacion() == 1) {
            if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
                if (destinatarios != null) {
                    destinatarios = destinatarios + "," + novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                } else {
                    destinatarios = novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                }
            }
            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", null);
        }
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_SOLICITUDES);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    private void notificar(String emailsCtrl) {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();

        String estado = prgSolicitud.getIdPrgToken().getActivo().equals(2) ? "aprobada" : "rechazada";
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("titulo", "NOTIFICACION SOLICITUDES DE CAMBIOS Y/O PERMISOS");
        mailProperties.put("tipo", tipo_solicitud.equals("permiso") ? "PERMISO" : "CAMBIO");
        mailProperties.put("fecha", Util.dateFormat(prgSolicitud.getFechaSolicitud()));
        mailProperties.put("codigo", prgToken.getIdEmpleado().getCodigoTm());
        mailProperties.put("operador", prgToken.getIdEmpleado().getNombres().concat(" ").concat(prgToken.getIdEmpleado().getApellidos()));
        mailProperties.put("estado", prgToken.getActivo().equals(1) ? "ESTUDIO" : prgToken.getActivo().equals(2) ? "APROBADO" : "");
        String subject = "Su solicitud de " + tipo_solicitud + " ha sido " + estado;
        String destinatarios;

        if (tipo_solicitud.equals("permiso")) {
            destinatarios = prgSolicitud.getIdPrgToken().getIdEmpleado().getEmailCorporativo();
        } else {
            destinatarios = prgSolicitud.getIdPrgToken().getIdEmpleado().getEmailCorporativo() + ","
                    + prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getEmailCorporativo();
        }

        if (prgSolicitud.getIdPrgToken().getActivo().equals(2)) {
            destinatarios = destinatarios + "," + emailsCtrl;
        }

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);

    }

    public String getCod_operador() {
        return cod_operador;
    }

    public void setCod_operador(String cod_operador) {
        this.cod_operador = cod_operador;
    }

    public PrgSolicitud getPrgSolicitud() {
        return prgSolicitud;
    }

    public void setPrgSolicitud(PrgSolicitud prgSolicitud) {
        this.prgSolicitud = prgSolicitud;
    }

    public PrgSolicitudCambio getPrgSolicitudCambio() {
        return prgSolicitudCambio;
    }

    public void setPrgSolicitudCambio(PrgSolicitudCambio prgSolicitudCambio) {
        this.prgSolicitudCambio = prgSolicitudCambio;
    }

    public PrgSercon getPrgSerconSolicitante() {
        return prgSerconSolicitante;
    }

    public void setPrgSerconSolicitante(PrgSercon prgSerconSolicitante) {
        this.prgSerconSolicitante = prgSerconSolicitante;
    }

    public PrgSercon getPrgSerconReemplazo() {
        return prgSerconReemplazo;
    }

    public void setPrgSerconReemplazo(PrgSercon prgSerconReemplazo) {
        this.prgSerconReemplazo = prgSerconReemplazo;
    }

    public PrgSolicitudPermiso getPrgSolicitudPermiso() {
        return prgSolicitudPermiso;
    }

    public void setPrgSolicitudPermiso(PrgSolicitudPermiso prgSolicitudPermiso) {
        this.prgSolicitudPermiso = prgSolicitudPermiso;
    }

    public String getCodigo_operador_reemplazo() {
        return codigo_operador_reemplazo;
    }

    public void setCodigo_operador_reemplazo(String codigo_operador_reemplazo) {
        this.codigo_operador_reemplazo = codigo_operador_reemplazo;
    }

    public String getTipo_solicitud() {
        return tipo_solicitud;
    }

    public void setTipo_solicitud(String tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public PrgToken getPrgToken() {
        return prgToken;
    }

    public void setPrgToken(PrgToken prgToken) {
        this.prgToken = prgToken;
    }

    public Empleado getEmpSolicitante() {
        return empSolicitante;
    }

    public void setEmpSolicitante(Empleado empSolicitante) {
        this.empSolicitante = empSolicitante;
    }

    public Integer getI_idSolicitudMotivo() {
        return i_idSolicitudMotivo;
    }

    public void setI_idSolicitudMotivo(Integer i_idSolicitudMotivo) {
        this.i_idSolicitudMotivo = i_idSolicitudMotivo;
    }

    public List<PrgSolicitudMotivo> getLstSolicitudMotivos() {
        return lstSolicitudMotivos;
    }

    public void setLstSolicitudMotivos(List<PrgSolicitudMotivo> lstSolicitudMotivos) {
        this.lstSolicitudMotivos = lstSolicitudMotivos;
    }
}
