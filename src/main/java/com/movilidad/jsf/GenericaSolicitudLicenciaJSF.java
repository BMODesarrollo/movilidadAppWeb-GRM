/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaSolicitudLicenciaFacadeLocal;
import com.movilidad.ejb.GenericaTokenFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.ParamAreaCargoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.GenericaSolicitudLicencia;
import com.movilidad.model.GenericaSolicitudMotivo;
import com.movilidad.model.GenericaToken;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaCargo;
import com.movilidad.model.ParamAreaUsr;
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
@Named(value = "genericaSolicitudLicenciaJSF")
@ViewScoped
public class GenericaSolicitudLicenciaJSF implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private GenericaTokenFacadeLocal genericaTokenFacadeLocal;
    @EJB
    private GenericaSolicitudLicenciaFacadeLocal licenciaFacadeLocal;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserFacadeLocal;
    @EJB
    private ParamAreaCargoFacadeLocal areaCargoFacadeLocal;

    private GenericaToken genericaToken;
    private GenericaSolicitudLicencia genericaSolicitudLicencia;
    private List<GenericaSolicitudLicencia> listGenericaSolicitudLicencia;

    private String cToken;
    private Date dDesde;
    private Date dHasta;
    private int iOp;
    private Integer idMotivo;
    private boolean bPermiso;

    UserExtended user;

    public GenericaSolicitudLicenciaJSF() {
    }

    @PostConstruct
    public void init() {
        cToken = null;
        genericaSolicitudLicencia = null;
        genericaToken = null;
        dDesde = new Date();
        dHasta = new Date();
        listGenericaSolicitudLicencia = new ArrayList<>();
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
        genericaToken = genericaTokenFacadeLocal.login(cToken, empleado.getIdEmpleado());
        if (genericaToken != null) {
            genericaSolicitudLicencia = new GenericaSolicitudLicencia();
            MovilidadUtil.addSuccessMessage("Bienvenido, recuerde diligenciar los datos correctamente.");
            return;
        }
        MovilidadUtil.addErrorMessage("Código de Seguridad no valido");
    }

    public void guardarSolicitud() {
        try {
            if (genericaSolicitudLicencia != null) {
                ParamArea pa = null;
                if (validarDatos(genericaSolicitudLicencia.getDesde(), genericaSolicitudLicencia.getHasta())) {
                    return;
                }
                if (idMotivo == null) {
                    MovilidadUtil.addErrorMessage("Motivo es requerido");
                    return;
                }
                Date d = new Date();
                Empleado idEmpleado = genericaToken.getIdEmpleado();
                if (idEmpleado != null && idEmpleado.getIdEmpleadoCargo() != null) {
                    EmpleadoTipoCargo idEmpleadoCargo = idEmpleado.getIdEmpleadoCargo();
                    ParamAreaCargo cargoAreaByCargo = areaCargoFacadeLocal.getCargoAreaByCargo(idEmpleadoCargo.getIdEmpleadoTipoCargo());
                    if (cargoAreaByCargo != null && cargoAreaByCargo.getIdParamArea() != null) {
                        pa = cargoAreaByCargo.getIdParamArea();
                    }
                }
                if (pa == null) {
                    MovilidadUtil.addErrorMessage("Su cargo no cuenta con Área asignada.");
                    return;
                }
                validarFechas();
                genericaToken.setUsado(d);
                genericaToken.setActivo(Util.ID_TOKEN_USADO);
                genericaToken.setModificado(d);
                genericaToken.setTipo(Util.ID_SOLICITUD_LICENCIA_NO_REMUNERADA);
                genericaTokenFacadeLocal.edit(genericaToken);
                genericaSolicitudLicencia.setIdGenericaSolicitudMotivo(new GenericaSolicitudMotivo(idMotivo));
                genericaSolicitudLicencia.setIdParamArea(pa);
                genericaSolicitudLicencia.setCreado(d);
                genericaSolicitudLicencia.setModificado(d);
                genericaSolicitudLicencia.setIdGenericaToken(genericaToken);
                genericaSolicitudLicencia.setEstadoReg(0);
                licenciaFacadeLocal.create(genericaSolicitudLicencia);
                MovilidadUtil.addSuccessMessage("Solicitud registrada en el sistema con éxito.");
                enviarCorreo(genericaSolicitudLicencia, Util.ID_ESTADO_SOLICITUD_LICENCIA_PENDIENTE, false);
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
    public void onSelectGenericaSolicitudLicencia(GenericaSolicitudLicencia psl, int op) {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        genericaSolicitudLicencia = psl;
        if (op == 1) {
            PrimeFaces.current().executeScript("PF('licenciaWV').show()");
        }
    }

    public void buscarRegistros() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        role();
        if (user != null) {
            ParamAreaUsr byIdUser = paramAreaUserFacadeLocal.getByIdUser(user.getUsername());
            if (byIdUser != null && byIdUser.getIdParamArea() != null) {
                listGenericaSolicitudLicencia = licenciaFacadeLocal.getTodoPorFecha(dDesde, dHasta, byIdUser.getIdParamArea().getIdParamArea());
            }
        }
    }

    public void onAprobarSolicitud() {
        if (genericaSolicitudLicencia != null) {
            if (genericaSolicitudLicencia.getAprobadoDesde() == null) {
                MovilidadUtil.addErrorMessage("Fecha A Partir de es requerido");
                return;
            }
            if (genericaSolicitudLicencia.getAprobadoHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha Hasta es requerido");
                return;
            }
            if (validarDatos(genericaSolicitudLicencia.getAprobadoDesde(), genericaSolicitudLicencia.getAprobadoHasta())) {
                return;
            }
            GenericaToken gt = genericaSolicitudLicencia.getIdGenericaToken();
            genericaSolicitudLicencia.setModificado(new Date());
            genericaSolicitudLicencia.setUserAprueba(user.getUsername());
            int dif = MovilidadUtil.getDiferenciaDia(genericaSolicitudLicencia.getAprobadoDesde(), genericaSolicitudLicencia.getAprobadoHasta());
            genericaSolicitudLicencia.setNumeroDiasAprobado(dif);
            licenciaFacadeLocal.edit(genericaSolicitudLicencia);
            gt.setActivo(Util.ID_TOKEN_APROBADO);
            genericaTokenFacadeLocal.edit(gt);
            MovilidadUtil.addSuccessMessage("Solicitud de licencia no remunerada aprobada con éxito");
            enviarCorreo(genericaSolicitudLicencia, Util.ID_ESTADO_SOLICITUD_LICENCIA_APROBADO, true);
            onClose();
            buscarRegistros();
            PrimeFaces.current().executeScript("PF('licenciaWV').hide()");
        }
    }

    public void onRechazarSolicitud() {
        GenericaToken idGenericaToken = genericaSolicitudLicencia.getIdGenericaToken();
        idGenericaToken.setActivo(Util.ID_TOKEN_RECHAZADO);
        genericaTokenFacadeLocal.edit(idGenericaToken);
        genericaSolicitudLicencia.setAprobadoDesde(null);
        genericaSolicitudLicencia.setAprobadoHasta(null);
        genericaSolicitudLicencia.setIdGenericaToken(idGenericaToken);
        genericaSolicitudLicencia.setUserAprueba(user.getUsername());
        licenciaFacadeLocal.edit(genericaSolicitudLicencia);
        enviarCorreo(genericaSolicitudLicencia, Util.ID_ESTADO_SOLICITUD_LICENCIA_RECHAZADO, false);
        MovilidadUtil.addSuccessMessage("Solicitud rechazada con éxito");
        onClose();
        buscarRegistros();
        PrimeFaces.current().executeScript("PF('licenciaWV').hide()");
    }

    public void onClose() {
        genericaSolicitudLicencia = null;
        iOp = 0;
    }

    void validarFechas() {
        //prgSolicitudLicencia.getDesde(), prgSolicitudLicencia.getHasta()
        int day;
        day = Util.isDay(genericaSolicitudLicencia.getDesde());
        if (day == 1) {
            genericaSolicitudLicencia.setDesde(Util.DiasAFecha(genericaSolicitudLicencia.getDesde(), -1));
        }
        day = Util.isDay(genericaSolicitudLicencia.getHasta());
        if (day == 2) {
            genericaSolicitudLicencia.setHasta(Util.DiasAFecha(genericaSolicitudLicencia.getHasta(), 1));
        }
    }

    public String minDate() {
        return Util.dateFormat(Util.DiasAFecha(new Date(), -3));
    }

    void role() {
        for (GrantedAuthority ga : user.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_PROFGEN")) {
                bPermiso = true;
                break;
            }
        }
    }

    //-------------Enviar los correos
    private Map getMailParams(boolean op) {
        String templ;
        if (op) {
            templ = Util.ID_SOLICITUD_TEMPLATE_LICENCIA_GEN;
        } else {
            templ = Util.TEMPLATE_SOLICITUDES_GEN;
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

    void enviarCorreo(GenericaSolicitudLicencia gsl, String estado, boolean op) {
        Map mapa = getMailParams(op);
        Map mailProperties = new HashMap();
        mailProperties.put("tipo", Util.ID_ESTADO_SOLICITUD_LICENCIA_NO_REMUNERADA);
        mailProperties.put("fecha", Util.dateFormat(new Date()));
        mailProperties.put("titulo", "NOTIFICACION SOLICITUDES Y/O PERMISOS");
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("cedula", gsl.getIdGenericaToken().getIdEmpleado().getIdentificacion());
        mailProperties.put("operador", gsl.getIdGenericaToken().getIdEmpleado().getApellidos().toUpperCase()
                + " "
                + gsl.getIdGenericaToken().getIdEmpleado().getNombres().toUpperCase());
        mailProperties.put("estado", estado);
        if (op) {
            mailProperties.put("dias", String.valueOf(gsl.getNumeroDiasAprobado()));
            mailProperties.put("apartir", Util.dateFormat(gsl.getAprobadoDesde()));
            mailProperties.put("hasta", Util.dateFormat(gsl.getAprobadoHasta()));
        }
        String subject = Util.ID_ESTADO_SOLICITUD_LICENCIA_NO_REMUNERADA;
        String destinatarios = gsl.getIdGenericaToken().getIdEmpleado().getEmailCorporativo();
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    public GenericaToken getGenericaToken() {
        return genericaToken;
    }

    public void setGenericaToken(GenericaToken genericaToken) {
        this.genericaToken = genericaToken;
    }

    public GenericaSolicitudLicencia getGenericaSolicitudLicencia() {
        return genericaSolicitudLicencia;
    }

    public void setGenericaSolicitudLicencia(GenericaSolicitudLicencia genericaSolicitudLicencia) {
        this.genericaSolicitudLicencia = genericaSolicitudLicencia;
    }

    public List<GenericaSolicitudLicencia> getListGenericaSolicitudLicencia() {
        return listGenericaSolicitudLicencia;
    }

    public void setListGenericaSolicitudLicencia(List<GenericaSolicitudLicencia> listGenericaSolicitudLicencia) {
        this.listGenericaSolicitudLicencia = listGenericaSolicitudLicencia;
    }

    public String getcToken() {
        return cToken;
    }

    public void setcToken(String cToken) {
        this.cToken = cToken;
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

}
