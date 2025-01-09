/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.PrgSolicitudLicenciaFacadeLocal;
import com.movilidad.ejb.PrgSolicitudNovedadParamFacadeLocal;
import com.movilidad.ejb.PrgTokenFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.PrgSolicitudLicencia;
import com.movilidad.model.PrgSolicitudMotivo;
import com.movilidad.model.PrgSolicitudNovedadParam;
import com.movilidad.model.PrgToken;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "solicitudLicenciNoRemuneradaJSF")
@ViewScoped
public class SolicitudLicenciNoRemuneradaJSF implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private PrgTokenFacadeLocal prgTokenFacadeLocal;
    @EJB
    private PrgSolicitudLicenciaFacadeLocal prgSolicitudLicenciaFacadeLocal;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private PrgSolicitudNovedadParamFacadeLocal novedadParamEjb;
    @EJB
    private NovedadFacadeLocal novedadEjb;

    private PrgToken prgToken;
    private PrgSolicitudLicencia prgSolicitudLicencia;
    private List<PrgSolicitudLicencia> listPrgSolicitudLicencia;

    private String cToken;
    private Date dDesde;
    private Date dHasta;
    private int iOp;
    private Integer idMotivo;
    private Integer consec_licencia;
    private boolean bPermiso;
    private String loadGif = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO);

    UserExtended user;

    public SolicitudLicenciNoRemuneradaJSF() {
    }

    @PostConstruct
    public void init() {
        cToken = null;
        prgSolicitudLicencia = null;
        prgToken = null;
        dDesde = new Date();
        dHasta = new Date();
        listPrgSolicitudLicencia = new ArrayList<>();
        validarParam();
        iOp = 0;
        idMotivo = null;
        bPermiso = false;
    }

    //------------ Ususario solicitante
    void validarParam() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        cToken = params.get("pin");
        String idEmp = params.get("id");
        if (cToken != null && idEmp != null) {
            try {
                Integer idEmpleado = new Integer(idEmp);
                validarCodigo(idEmpleado);
            } catch (NumberFormatException e) {
            }
        }
    }

    void validarCodigo(Integer idEmpleado) {
        Empleado empleado = empleadoFacadeLocal.find(idEmpleado);
        if (empleado == null) {
            MovilidadUtil.addErrorMessage("No se puede realizar el procedimiento");
            return;
        }
        if (cToken == null) {
            MovilidadUtil.addErrorMessage("Código de Seguridad de acceso es requerido");
            return;
        }
        prgToken = prgTokenFacadeLocal.login(cToken, empleado.getIdEmpleado());
        if (prgToken != null) {
            prgSolicitudLicencia = new PrgSolicitudLicencia();
            MovilidadUtil.addSuccessMessage("Bienvenido, recuerde diligenciar los datos correctamente.");
            return;
        }
        MovilidadUtil.addErrorMessage("Código de Seguridad no valido");
    }

    public void guardarSolicitud() {
        try {
            if (prgSolicitudLicencia != null) {
                if (validarDatos(prgSolicitudLicencia.getDesde(), prgSolicitudLicencia.getHasta())) {
                    return;
                }
                if (idMotivo == null) {
                    MovilidadUtil.addErrorMessage("Motivo es requerido");
                    return;
                }
                prgSolicitudLicencia.setIdPrgSolicitudMotivo(new PrgSolicitudMotivo(idMotivo));
                validarFechas();
                Date d = new Date();
                prgToken.setUsado(d);
                prgToken.setActivo(Util.ID_TOKEN_USADO);
                prgToken.setModificado(d);
                prgToken.setTipo(Util.ID_SOLICITUD_LICENCIA_NO_REMUNERADA);
                prgTokenFacadeLocal.edit(prgToken);
                prgSolicitudLicencia.setCreado(d);
                prgSolicitudLicencia.setModificado(d);
                prgSolicitudLicencia.setIdPrgToken(prgToken);
                prgSolicitudLicencia.setEstadoReg(0);
                prgSolicitudLicenciaFacadeLocal.create(prgSolicitudLicencia);
                MovilidadUtil.addSuccessMessage("Solicitud registrada en el sistema con éxito.");
                enviarCorreo(prgSolicitudLicencia, Util.ID_ESTADO_SOLICITUD_LICENCIA_PENDIENTE, false, null);
                PrimeFaces.current().executeScript("location.href='solicitud.jsf'");
                return;
            }
            MovilidadUtil.addErrorMessage("Error en el sistema");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean validarDatos(Date desde, Date hasta) {
        try {
            if (MovilidadUtil.fechasIgualMenorMayor(hasta, desde, false) == 0) {
                MovilidadUtil.addErrorMessage("Fecha Hasta no puede ser inferior a Fecha Desde");
                return true;
            }
            return false;
        } catch (ParseException e) {
            return false;
        }
    }

    //---------------- Profesional----------
    public void onSelectPrgSolicitudLicencia(PrgSolicitudLicencia psl, int op) {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        prgSolicitudLicencia = psl;

        consec_licencia = prgSolicitudLicencia.getConsecutivo();

        if (op == 1) {
            PrimeFaces.current().executeScript("PF('licenciaWV').show()");
        }
    }

    public void buscarRegistros() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        role();
        listPrgSolicitudLicencia = prgSolicitudLicenciaFacadeLocal.getTodoPorFecha(dDesde, dHasta);
    }

    public void onAprobarSolicitud() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PrgSolicitudNovedadParam novedadParam = novedadParamEjb.find(1);
        if (novedadParam == null) {
            MovilidadUtil.addErrorMessage("NO se encontró proceso relacionado al envío de novedades");
            return;
        }

        if (prgSolicitudLicencia != null) {
            if (prgSolicitudLicencia.getAprobadoDesde() == null) {
                MovilidadUtil.addErrorMessage("Fecha A Partir de es requerido");
                return;
            }
            if (prgSolicitudLicencia.getAprobadoHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha Hasta es requerido");
                return;
            }
            if (validarDatos(prgSolicitudLicencia.getAprobadoDesde(), prgSolicitudLicencia.getAprobadoHasta())) {
                return;
            }

            prgSolicitudLicencia.setConsecutivo(novedadParam.getConsecLicencia());
            novedadParamEjb.aumentarConsecutivoLicencia(novedadParam.getIdPrgSolicitudNovedadParam());

            PrgToken pt = prgSolicitudLicencia.getIdPrgToken();

            Novedad novedad = new Novedad();
            novedad.setIdEmpleado(pt.getIdEmpleado());
            novedad.setIdNovedadTipo(novedadParam.getIdNovedadLicencia().getIdNovedadTipo());
            novedad.setIdNovedadTipoDetalle(novedadParam.getIdNovedadLicencia());
            novedad.setPuntosPm(novedadParam.getIdNovedadLicencia().getPuntosPm());
            novedad.setFecha(new Date());
            novedad.setCreado(new Date());
            novedad.setDesde(prgSolicitudLicencia.getAprobadoDesde());
            novedad.setHasta(prgSolicitudLicencia.getAprobadoHasta());
            novedad.setObservaciones("Licencia NO remunerada");
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

            prgSolicitudLicencia.setModificado(new Date());
            prgSolicitudLicencia.setUserAprueba(user.getUsername());
            int dif = MovilidadUtil.getDiferenciaDia(prgSolicitudLicencia.getAprobadoDesde(), prgSolicitudLicencia.getAprobadoHasta());
            prgSolicitudLicencia.setNumeroDiasAprobado(dif);
            prgSolicitudLicenciaFacadeLocal.edit(prgSolicitudLicencia);
            pt.setActivo(Util.ID_TOKEN_APROBADO);
            prgTokenFacadeLocal.edit(pt);
            MovilidadUtil.addSuccessMessage("Solicitud de licencia no remunerada aprobada con éxito");
            enviarCorreo(prgSolicitudLicencia, Util.ID_ESTADO_SOLICITUD_LICENCIA_APROBADO, true, novedadParam.getIdNotificacionProceso().getEmails());
            onClose();
            buscarRegistros();
            PrimeFaces.current().executeScript("PF('licenciaWV').hide()");
        }
    }

    public void onRechazarSolicitud() {
        PrgToken pt = prgSolicitudLicencia.getIdPrgToken();
        pt.setActivo(Util.ID_TOKEN_RECHAZADO);
        prgTokenFacadeLocal.edit(pt);
        prgSolicitudLicencia.setAprobadoDesde(null);
        prgSolicitudLicencia.setAprobadoHasta(null);
        prgSolicitudLicencia.setIdPrgToken(pt);
        prgSolicitudLicencia.setUserAprueba(user.getUsername());
        prgSolicitudLicenciaFacadeLocal.edit(prgSolicitudLicencia);
        enviarCorreo(prgSolicitudLicencia, Util.ID_ESTADO_SOLICITUD_LICENCIA_RECHAZADO, false, null);
        MovilidadUtil.addSuccessMessage("Solicitud rechazada con éxito");
        onClose();
        buscarRegistros();
        PrimeFaces.current().executeScript("PF('licenciaWV').hide()");
    }

    public void onClose() {
        prgSolicitudLicencia = null;
        iOp = 0;
    }

    void validarFechas() {
        //prgSolicitudLicencia.getDesde(), prgSolicitudLicencia.getHasta()
        int day;
        day = Util.isDay(prgSolicitudLicencia.getDesde());
        if (day == 1) {
            prgSolicitudLicencia.setDesde(Util.DiasAFecha(prgSolicitudLicencia.getDesde(), -1));
        }
        day = Util.isDay(prgSolicitudLicencia.getHasta());
        if (day == 2) {
            prgSolicitudLicencia.setHasta(Util.DiasAFecha(prgSolicitudLicencia.getHasta(), 1));
        }
    }

    public String minDate() {
        return Util.dateFormat(Util.DiasAFecha(new Date(), -3));
    }

    void role() {
        for (GrantedAuthority ga : user.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_PROFOP")) {
                bPermiso = true;
                break;
            }
        }
    }

    //-------------Enviar los correos
    private Map getMailParams(boolean op) {
        String templ;
        if (op) {
            templ = Util.ID_SOLICITUD_TEMPLATE_LICENCIA;
        } else {
            templ = Util.TEMPLATE_SOLICITUDES;
        }
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(templ);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    void enviarCorreo(PrgSolicitudLicencia psl, String estado, boolean op, String emailsControl) {
        Map mapa = getMailParams(op);
        String destinatarios;
        Map mailProperties = new HashMap();
        mailProperties.put("tipo", Util.ID_ESTADO_SOLICITUD_LICENCIA_NO_REMUNERADA);
        mailProperties.put("titulo", "NOTIFICACION SOLICITUDES Y/O PERMISOS");
        mailProperties.put("fecha", Util.dateFormat(new Date()));
        mailProperties.put("codigo", psl.getIdPrgToken().getIdEmpleado().getCodigoTm());
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("operador", psl.getIdPrgToken().getIdEmpleado().getApellidos().toUpperCase()
                + " "
                + psl.getIdPrgToken().getIdEmpleado().getNombres().toUpperCase());
        mailProperties.put("estado", estado);

        String subject = Util.ID_ESTADO_SOLICITUD_LICENCIA_NO_REMUNERADA + " " + estado;
        destinatarios = psl.getIdPrgToken().getIdEmpleado().getEmailCorporativo();

        if (op) {
            mailProperties.put("dias", String.valueOf(psl.getNumeroDiasAprobado()));
            mailProperties.put("apartir", Util.dateFormat(psl.getAprobadoDesde()));
            mailProperties.put("hasta", Util.dateFormat(psl.getAprobadoHasta()));
            destinatarios = destinatarios + "," + emailsControl;
        }
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    public void generarNovedadNoFirma() {
        PrgSolicitudNovedadParam novedadParam = novedadParamEjb.find(1);
        if (novedadParam == null) {
            MovilidadUtil.addErrorMessage("NO se encontró proceso relacionado al envío de novedades");
            return;
        }
        Novedad novedad = new Novedad();
        novedad.setFecha(new Date());
        novedad.setIdEmpleado(prgToken.getIdEmpleado());
        novedad.setIdNovedadTipo(novedadParam.getIdNovedadNoFirma().getIdNovedadTipo());
        novedad.setIdNovedadTipoDetalle(novedadParam.getIdNovedadNoFirma());
        novedad.setCreado(new Date());
        novedad.setUsername(user.getUsername());
        novedad.setEstadoReg(0);
        novedad.setObservaciones("El operador NO diligenció el formato de licencias dentro 3 días hábiles");
        novedad.setPuntosPm(novedadParam.getIdNovedadNoFirma().getPuntosPm());

        if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == 1) {
            novedad.setProcede(1);
            novedad.setPuntosPmConciliados(novedadParam.getIdNovedadNoFirma().getPuntosPm());
        } else {
            novedad.setProcede(0);
            novedad.setPuntosPmConciliados(0);
        }

        novedadEjb.create(novedad);
        notificarCorreoNovedad(novedad);
        MovilidadUtil.addSuccessMessage("Novedad generada éxitosamente");
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

    public PrgToken getPrgToken() {
        return prgToken;
    }

    public void setPrgToken(PrgToken prgToken) {
        this.prgToken = prgToken;
    }

    public PrgSolicitudLicencia getPrgSolicitudLicencia() {
        return prgSolicitudLicencia;
    }

    public void setPrgSolicitudLicencia(PrgSolicitudLicencia prgSolicitudLicencia) {
        this.prgSolicitudLicencia = prgSolicitudLicencia;
    }

    public String getcToken() {
        return cToken;
    }

    public void setcToken(String cToken) {
        this.cToken = cToken;
    }

    public List<PrgSolicitudLicencia> getListPrgSolicitudLicencia() {
        return listPrgSolicitudLicencia;
    }

    public void setListPrgSolicitudLicencia(List<PrgSolicitudLicencia> listPrgSolicitudLicencia) {
        this.listPrgSolicitudLicencia = listPrgSolicitudLicencia;
    }

    public Date getdDesde() {
        return dDesde;
    }

    public void setdDesde(Date dDesde) {
        this.dDesde = dDesde;
    }

    public Date getdHasta() {
        return dHasta;
    }

    public void setdHasta(Date dHasta) {
        this.dHasta = dHasta;
    }

    public int getiOp() {
        return iOp;
    }

    public void setiOp(int iOp) {
        this.iOp = iOp;
    }

    public Integer getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }

    public boolean isbPermiso() {
        return bPermiso;
    }

    public Integer getConsec_licencia() {
        return consec_licencia;
    }

    public void setConsec_licencia(Integer consec_licencia) {
        this.consec_licencia = consec_licencia;
    }

    public String getLoadGif() {
        return loadGif;
    }

    public void setLoadGif(String loadGif) {
        this.loadGif = loadGif;
    }

}
