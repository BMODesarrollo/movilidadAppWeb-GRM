package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.DispConciliacionDetFacadeLocal;
import com.movilidad.ejb.DispConciliacionFacadeLocal;
import com.movilidad.ejb.DispConciliacionResumenFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoFacadeLocal;
import com.movilidad.model.DispConciliacion;
import com.movilidad.model.DispConciliacionDet;
import com.movilidad.model.DispConciliacionResumen;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "rConciliacionBean")
@ViewScoped
public class ReporteConciliacionBean implements Serializable {
    
    @EJB
    private DispConciliacionFacadeLocal conciliacionEjb;
    @EJB
    private DispConciliacionDetFacadeLocal dispConciliacionDetEjb;
    @EJB
    private DispConciliacionResumenFacadeLocal dispConciliacionResumenEjb;
    @EJB
    private VehiculoTipoEstadoFacadeLocal vehiculoTipoEstadoEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    
    private DispConciliacion conciliacion;
    
    private boolean b_input_causa_estrada;
    
    private List<DispConciliacionDet> lstDetalles;
    private List<DispConciliacionResumen> lstResumen;
    private List<VehiculoTipoEstado> lstTipoEstado;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        obtenerUltimaConciliacion();
    }
    
    private void obtenerUltimaConciliacion() {
        conciliacion = conciliacionEjb.obtenerUltimaConciliacion();
        b_input_causa_estrada = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOV_MTTO_INPUT_CAUSA_ENTRADA).equals("1");
        
        if (conciliacion != null) {
            lstDetalles = dispConciliacionDetEjb.obtenerDetallesByIdDispConciliacion(conciliacion.getIdDispConciliacion(),
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            lstResumen = dispConciliacionResumenEjb.obtenerResumenPorUfAndIdConciliacion(conciliacion.getIdDispConciliacion(),
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), null);
            lstTipoEstado = vehiculoTipoEstadoEjb.findByEstadoReg();
        }
        
    }
    
    public void obtenerDatos() {
        obtenerDatosTransactional();
        obtenerUltimaConciliacion();
    }
    
    @Transactional
    public void obtenerDatosTransactional() {
        List<DispConciliacionResumen> resumen = dispConciliacionResumenEjb.obtenerResumen(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        List<DispConciliacionDet> detalles = dispConciliacionDetEjb.obtenerDetalles(MovilidadUtil.fechaCompletaHoy(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        
        if (resumen != null) {
            
            DispConciliacion conciliacionAux = new DispConciliacion();
            conciliacionAux.setFechaHora(MovilidadUtil.fechaCompletaHoy());
            conciliacionAux.setUsername(user.getUsername());
            conciliacionAux.setCreado(MovilidadUtil.fechaCompletaHoy());
            conciliacionAux.setEstadoReg(0);
            
            resumen.forEach(item -> {
                item.setIdDispConciliacionResumen(null);
                item.setIdDispConciliacion(conciliacionAux);
                item.setGeneradoPor(user.getUsername());
                item.setAprobado(0);
                item.setCreado(MovilidadUtil.fechaCompletaHoy());
                item.setEstadoReg(0);
            });
            
            detalles.forEach(item -> {
                item.setIdDispConciliacionDet(null);
                item.setIdDispConciliacion(conciliacionAux);
                item.setUsername(user.getUsername());
                item.setCreado(MovilidadUtil.fechaCompletaHoy());
                item.setEstadoReg(0);
            });
            
            conciliacionAux.setDispConciliacionResumenList(resumen);
            conciliacionAux.setDispConciliacionDetList(detalles);
            
            conciliacionEjb.create(conciliacionAux);
            MovilidadUtil.addSuccessMessage("Datos cargados con éxito");
            
        }
        
    }
    
    @Transactional
    public void aprobarConciliacion(DispConciliacionResumen resumen) {
        aprobarConciliacionTransactional(resumen);
        
        NotificacionProcesos proceso = notificacionProcesosEjb.findByCodigo(
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CONCIFLOTA));
        
        if (proceso != null) {
            notificar(resumen, proceso);
        } else {
            MovilidadUtil.addAdvertenciaMessage("NO se encontró lista de distribución con código ("
                    + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CONCIFLOTA)
                    + " ), para realizar el envío por correo de la conciliación. ");
        }
    }
    
    private void aprobarConciliacionTransactional(DispConciliacionResumen resumen) {
        resumen.setAprobado(1);
        resumen.setFechaHoraAprobacion(MovilidadUtil.fechaCompletaHoy());
        resumen.setUsrOperaciones(user.getUsername());
        dispConciliacionResumenEjb.edit(resumen);
        MovilidadUtil.addSuccessMessage("Conciliación aprobada con éxito");
        MovilidadUtil.updateComponent(":msgs");
    }
    
    @Transactional
    public void rechazarConciliacion(DispConciliacionResumen resumen) {
        rechazarConciliacionTransactional(resumen);
    }
    
    private void rechazarConciliacionTransactional(DispConciliacionResumen resumen) {
        resumen.setAprobado(2);
        resumen.setFechaHoraAprobacion(null);
        resumen.setUsrOperaciones(user.getUsername());
        dispConciliacionResumenEjb.edit(resumen);
        MovilidadUtil.addSuccessMessage("Conciliación rechazada con éxito");
        MovilidadUtil.updateComponent(":msgs");
    }

    /**
     * Verifica si el usuario logueado corresponde al área de operaciones
     *
     * @return true si el usuario logueado corresponde al área de operaciones
     */
    public boolean validarRolOperaciones() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().contains("OP")
                    || auth.getAuthority().equals("ROLE_TC")) {
                return true;
            }
            
        }
        return false;
    }

    /**
     * Verifica si el usuario logueado corresponde al área de operaciones, y si
     * su unidad funcional es igual a la del resumen
     *
     * @param resumen registro tabla resumen
     * @return true si el usuario logueado corresponde al área de operaciones ,y
     * si su unidad funcional es igual a la del resumen
     */
    public boolean validarAprobacionOperaciones(DispConciliacionResumen resumen) {
        for (GrantedAuthority auth : user.getAuthorities()) {
            
            if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
                return true;
            }
            
            if (auth.getAuthority().contains("OP")
                    || auth.getAuthority().equals("ROLE_TC")) {
                if (resumen.getIdGopUnidadFuncional().getIdGopUnidadFuncional().equals(
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
                    return true;
                }
            }
            
        }
        return false;
    }

    /**
     * Valida si el usuario logueado corresponde al área de Mantenimiento
     *
     * @return true si el usuario corresponde al área de Mantenimiento
     */
    public boolean validarRolMtto() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().contains("MTTO")) {
                return true;
            }
        }
        return false;
    }

    /*
     * Parámetros para el envío de correos de aprobación de conciliaciones
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_APROBACION_CONCILIACION);
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
     * Realiza el envío de correo de las fallas registradas a las partes
     * interesadas
     */
    private void notificar(DispConciliacionResumen resumen, NotificacionProcesos proceso) {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
//        mailProperties.put("logo", aPIJSF.getcLogo());
        mailProperties.put("unidadFuncional", resumen.getIdGopUnidadFuncional() != null ? resumen.getIdGopUnidadFuncional().getNombre() : "");
        mailProperties.put("fechaConciliacion", resumen.getFechaHoraAprobacion() != null ? Util.dateTimeFormat(resumen.getFechaHoraAprobacion()) : "");
        mailProperties.put("turno", Util.dateToAmPmFormat(resumen.getFechaHoraAprobacion()));
        mailProperties.put("operativos", resumen.getTotalVehiculosOperativos() != null ? resumen.getTotalVehiculosOperativos() : "");
        mailProperties.put("inoperativos", resumen.getTotalVehiculosInoperativos() != null ? resumen.getTotalVehiculosInoperativos() : "");
        mailProperties.put("usrOperaciones", resumen.getUsrOperaciones() != null ? resumen.getUsrOperaciones() : "");
        String subject;
        String destinatarios;
        
        destinatarios = proceso.getEmails();
        subject = proceso.getMensaje() + " para la unidad funcional " + resumen.getIdGopUnidadFuncional().getNombre();

        // Generación de archivo excel con los datos de la conciliación
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        Map parametros = new HashMap();
        
        List<DispConciliacionDet> lstDetallesExcel = null;
        
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            lstDetallesExcel = dispConciliacionDetEjb.obtenerDetallesByIdDispConciliacion(
                    resumen.getIdDispConciliacion().getIdDispConciliacion(), resumen.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        }
        
        plantilla = plantilla + "Informe Conciliacion.xlsx";
        parametros.put("resumen", resumen);
        parametros.put("fecha", Util.dateTimeFormat(resumen.getIdDispConciliacion().getFechaHora()));
        parametros.put("detalles", unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() != 0 ? lstDetalles : lstDetallesExcel);
        parametros.put("dateFormat", Util.DATE_FORMAT);
        parametros.put("dateTimeFormat", Util.DATE_TIME_FORMAT);
        parametros.put("dateAmPm", Util.DATE_TO_AM_PM_FORMAT);
        destino = destino + "Informe_Conciliacion.xlsx";
        
        GeneraXlsx.generar(plantilla, destino, parametros);
        List<String> adjuntos = new ArrayList<>();
        adjuntos.add(destino);
        
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos);
    }
    
    public List<DispConciliacionDet> getLstDetalles() {
        return lstDetalles;
    }
    
    public void setLstDetalles(List<DispConciliacionDet> lstDetalles) {
        this.lstDetalles = lstDetalles;
    }
    
    public List<DispConciliacionResumen> getLstResumen() {
        return lstResumen;
    }
    
    public void setLstResumen(List<DispConciliacionResumen> lstResumen) {
        this.lstResumen = lstResumen;
    }
    
    public DispConciliacion getConciliacion() {
        return conciliacion;
    }
    
    public void setConciliacion(DispConciliacion conciliacion) {
        this.conciliacion = conciliacion;
    }
    
    public List<VehiculoTipoEstado> getLstTipoEstado() {
        return lstTipoEstado;
    }
    
    public void setLstTipoEstado(List<VehiculoTipoEstado> lstTipoEstado) {
        this.lstTipoEstado = lstTipoEstado;
    }
    
    public boolean isB_input_causa_estrada() {
        return b_input_causa_estrada;
    }
    
    public void setB_input_causa_estrada(boolean b_input_causa_estrada) {
        this.b_input_causa_estrada = b_input_causa_estrada;
    }
    
}
