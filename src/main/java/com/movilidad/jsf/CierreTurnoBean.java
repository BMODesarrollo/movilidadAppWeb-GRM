/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.SumEliminadoResponsableDTO;
import com.movilidad.dto.SumEntradaSalidaDTO;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.GopCierreTurnoFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.GopCierreTurno;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ResEstadoActFlota;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ObjetoSigleton;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "cierreTurnoBean")
@ViewScoped
public class CierreTurnoBean implements Serializable {

    /**
     * Creates a new instance of CierreTurnoBean
     */
    public CierreTurnoBean() {
    }
    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @EJB
    private GopCierreTurnoFacadeLocal cierreTurnoEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEJB;
    @EJB
    private NovedadFacadeLocal novEJB;
    @EJB
    private AccidenteFacadeLocal accEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private NotificacionEmailBean notiEmailBean;

    @Inject
    private UsersListBean usersListBean;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();

    private GopCierreTurno gopCierreTurno;
    private List<GopCierreTurno> listGopCierreTurno;
    private Date fecha;
    private Date fechaConsulta;
    private String observacion;
    private String user_tecnico_control;
    private String user_tecnico_patio;
    private String user_tecnico_control_recibe;
    private String user_tecnico_patio_recibe;
    private Integer idGopCierreTurno;

    private List<Users> listUser;

    @PostConstruct
    public void init() {
        fechaConsulta = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void consultar() {
        gopCierreTurno = getUltimoCierre();
        if (gopCierreTurno != null) {
            if (unidadFuncionalSessionBean.isFiltradoUF()) {
                unidadFuncionalSessionBean.setI_unidad_funcional(gopCierreTurno.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
            }
            idGopCierreTurno = gopCierreTurno.getIdGopCierreTurno();
        }
        consultarCierresTurnosByFecha();

    }

    public void consultarCierresTurnosByFecha() {
        listGopCierreTurno = cierreTurnoEjb.findByFechaAndIdGopUnidadFunc(
                fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    GopCierreTurno getUltimoCierre() {
        return cierreTurnoEjb.findLastGopCierreTurno(fechaConsulta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void selectCierreTurno() {
        for (GopCierreTurno obj : listGopCierreTurno) {
            if (obj.getIdGopCierreTurno().equals(idGopCierreTurno)) {
                gopCierreTurno = obj;
            }
        }
    }

    public void nuevo() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una Unidad Funcional.");
            return;
        }
        user_tecnico_control = user.getUsername();
        user_tecnico_patio = "";
        user_tecnico_control_recibe = "";
        user_tecnico_patio_recibe = "";
        observacion = "";
        listUser = usersListBean.consultarUsersHabilitadoByUF(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        MovilidadUtil.openModal("dlg_cierre_turno_wv");
        fecha = MovilidadUtil.fechaCompletaHoy();
    }

    public void guardar() {
        GopCierreTurno ultimoCierre = getUltimoCierre();
        if (valiar(ultimoCierre)) {
            return;
        }
        guardarTransactional(ultimoCierre);
        notificarCierreTurno();
        consultar();
    }

    public String dateFormat(Date fecha) {
        return Util.dateTimeFormat(fecha);
    }

    public boolean valiar(GopCierreTurno ultimoCierre) {
        if (user_tecnico_control.equals(user_tecnico_control_recibe) || user_tecnico_control.equals(user_tecnico_patio_recibe)) {
            MovilidadUtil.addErrorMessage("No se permite la duplicidad de usuarios entre turnos.");
            return true;
        }
        if (user_tecnico_patio.equals(user_tecnico_control_recibe) || user_tecnico_patio.equals(user_tecnico_patio_recibe)) {
            MovilidadUtil.addErrorMessage("No se permite la duplicidad de usuarios entre turnos.");
            return true;
        }
        if (ultimoCierre == null) {
            return false;
        }
        if (ultimoCierre.getUserTecControl().equals(user_tecnico_control)) {
            MovilidadUtil.addErrorMessage("No es posible realizar un cierre de turno. El cierre inmediatamente anterior pertenece al mismo usuario.");
            return true;
        }
        return false;
    }

    @Transactional
    public void guardarTransactional(GopCierreTurno ultimoCierre) {
        gopCierreTurno = new GopCierreTurno();
        /**
         * set Descripción con el detalle del cierre de turno.
         */
        String buildDescriptionCierreTurno = buildDescriptionCierreTurno(ultimoCierre);
        if (buildDescriptionCierreTurno == null) {
            MovilidadUtil.addErrorMessage("Error al geenrar la descripción del cirre de turno.");
            return;
        }
        gopCierreTurno.setDescripcion(buildDescriptionCierreTurno);
        gopCierreTurno.setCreado(MovilidadUtil.fechaCompletaHoy());
        gopCierreTurno.setUsername(user.getUsername());
        gopCierreTurno.setUserTecControl(user_tecnico_control);
        gopCierreTurno.setUserTecControlRecibe(user_tecnico_control_recibe);
        gopCierreTurno.setUserTecPatio(user_tecnico_patio);
        gopCierreTurno.setUserTecPatioRecibe(user_tecnico_patio_recibe);
        gopCierreTurno.setEstadoReg(0);
        gopCierreTurno.setFechaHora(this.fecha);
        gopCierreTurno.setObservacion(observacion);
        gopCierreTurno.setIdGopUnidadFuncional(new GopUnidadFuncional(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
        cierreTurnoEjb.create(gopCierreTurno);
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        MovilidadUtil.hideModal("dlg_cierre_turno_wv");

    }

    public String buildDescriptionCierreTurno(GopCierreTurno ultimoCierre) {
        String description = null;
        try {

            int idUf = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
            /**
             * Total de servicios eliminados por reponsables.
             */
            List<SumEliminadoResponsableDTO> listEliminadoPorResponsable
                    = prgTcEjb.findReportSumEliminadoResponsable(this.fecha, idUf,
                            ultimoCierre == null ? null : ultimoCierre.getFechaHora());
            /**
             * Estado Actual de la flota.
             */
            List<ResEstadoActFlota> resumenEstadoActualFlota
                    = vehiculoEJB.getResumenEstadoActualFlota(idUf);
            /**
             * Total de novedades abiertas de atencion en vía para el turno
             */
            Long totalNovedadesAtvAbiertas = novEJB.findTotalNovedadesAtvByEstado(fecha,
                    ConstantsUtil.NOV_ESTADO_ABIERTO, idUf, ultimoCierre == null ? null : ultimoCierre.getFechaHora());
            /**
             * Toral de accidentes sin atender para el turno
             */
            Long totalAccidentesEnAtencion = accEJB.findTotalAccidentesEnAtencion(
                    fecha, idUf, ultimoCierre == null ? null : ultimoCierre.getFechaHora());
            Long sumServiciosSinOp = prgTcEjb.findReportSumServiciosSinOp(fecha, idUf);
            Long sumServiciosSinVeh = prgTcEjb.findReportSumServiciosSinVehiculo(fecha, idUf);
            Long totalOpDisponibles = prgTcEjb.totalDisponibleByFechaAndUf(fecha, idUf);
            Long totalVehDisponibles = vehiculoEJB.totalDisponibles(fecha, idUf);
            int idNovedadTipoAusentismo = Integer.parseInt(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO));
            Long totalAusentismos = novEJB.totalAusentismos(fecha, idUf, idNovedadTipoAusentismo, ultimoCierre == null ? null : ultimoCierre.getFechaHora());

            String s_sum_serv_op = "<br /><br />Total Servicio sin Operador: " + sumServiciosSinOp;
            String s_sum_serv_veh = "<br />Total Servicio sin Vehículo: " + sumServiciosSinVeh;

            String s_sum_nov_atv_abiertas = "<br /><br />Total Novedades ATV Abiertas: " + totalNovedadesAtvAbiertas;
            String s_sum_acc_atencion = "<br />Total Accidentes en atención: " + totalAccidentesEnAtencion;

            String s_msgs_eliminado_responsable = "<br /><br />Total de servicios eliminados por responsable: ";
            String s_eliminado_responsable = "<br />";
            for (SumEliminadoResponsableDTO item : listEliminadoPorResponsable) {
                s_eliminado_responsable += "&nbsp;-" + item.getResponsable() + ": " + item.getTotal() + "<br />";
            }

            String s_msgs_estado_actual_flota = "<br />Vehículos operativos vs Inoperativos";
            String s_estado_actual_flota = "<br />";
            /**
             * Creación del texto para el estado actual de flota.
             */
            for (ResEstadoActFlota item : resumenEstadoActualFlota) {
                s_estado_actual_flota += "&nbsp;-Total Vehículos Inoperativos: "
                        + item.getTotal_vehi_nov() + "<br />"
                        + "&nbsp;-Total Vehículos Operativos: "
                        + (item.getTotal_vehi_reg().subtract(item.getTotal_vehi_nov()));
            }
            String s_total_op_disponibles = "<br />Total Operadores disponibles: " + totalOpDisponibles;
            String s_total_nov_ausentismo = "<br />Total Novedades de Ausentismo: " + totalAusentismos;
            String s_total_veh_disponibles = "<br />&nbsp;-Total Vehículos disponibles: " + totalVehDisponibles;
            description = s_total_op_disponibles + s_sum_serv_op + s_sum_serv_veh + s_sum_nov_atv_abiertas
                    + s_sum_acc_atencion + s_total_nov_ausentismo + s_msgs_eliminado_responsable + s_eliminado_responsable
                    + s_msgs_estado_actual_flota + s_estado_actual_flota + s_total_veh_disponibles;

        } catch (Exception e) {
            return null;
        }
        return description;
    }

    /**
     * Realiza el envío de correo del cierre de turno registrado
     */
    private void notificarCierreTurno() {
        Map mapa = notiEmailBean.getMailParams(ConstantsUtil.ID_NOTIFICACION_CONF,
                ConstantsUtil.TEMPLATE_CIERRE_TURNO);

        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fechaHora", Util.dateTimeFormat(gopCierreTurno.getFechaHora()));
        mailProperties.put("descripcion", gopCierreTurno.getDescripcion());
        mailProperties.put("observacion", gopCierreTurno.getObservacion());
        mailProperties.put("userTecnicoControl", gopCierreTurno.getUserTecControl());
        mailProperties.put("userTecnicoPatio", gopCierreTurno.getUserTecPatio());
        String subject;
        String code = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.CODE_NOTI_PROC_CIERRE_TURNO);
        String destinatarios = notiEmailBean.obtenerDestinatarios(code,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (destinatarios != null) {
            subject = "Cierre turno " + Util.dateTimeFormat(gopCierreTurno.getFechaHora());

            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", null);
        }
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public GopCierreTurno getGopCierreTurno() {
        return gopCierreTurno;
    }

    public void setGopCierreTurno(GopCierreTurno gopCierreTurno) {
        this.gopCierreTurno = gopCierreTurno;
    }

    public String getUser_tecnico_control() {
        return user_tecnico_control;
    }

    public void setUser_tecnico_control(String user_tecnico_control) {
        this.user_tecnico_control = user_tecnico_control;
    }

    public String getUser_tecnico_patio() {
        return user_tecnico_patio;
    }

    public void setUser_tecnico_patio(String user_tecnico_patio) {
        this.user_tecnico_patio = user_tecnico_patio;
    }

    public List<Users> getListUser() {
        return listUser;
    }

    public void setListUser(List<Users> listUser) {
        this.listUser = listUser;
    }

    public List<GopCierreTurno> getListGopCierreTurno() {
        return listGopCierreTurno;
    }

    public void setListGopCierreTurno(List<GopCierreTurno> listGopCierreTurno) {
        this.listGopCierreTurno = listGopCierreTurno;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public Integer getIdGopCierreTurno() {
        return idGopCierreTurno;
    }

    public void setIdGopCierreTurno(Integer idGopCierreTurno) {
        this.idGopCierreTurno = idGopCierreTurno;
    }

    public String getUser_tecnico_control_recibe() {
        return user_tecnico_control_recibe;
    }

    public void setUser_tecnico_control_recibe(String user_tecnico_control_recibe) {
        this.user_tecnico_control_recibe = user_tecnico_control_recibe;
    }

    public String getUser_tecnico_patio_recibe() {
        return user_tecnico_patio_recibe;
    }

    public void setUser_tecnico_patio_recibe(String user_tecnico_patio_recibe) {
        this.user_tecnico_patio_recibe = user_tecnico_patio_recibe;
    }

}
