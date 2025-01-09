package com.movilidad.jsf;

import com.movilidad.ejb.AccPreFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionTelegramDetFacadeLocal;
import com.movilidad.model.AccPre;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionTelegramDet;
import com.movilidad.model.Novedad;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.GeocodingDTO;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import com.movlidad.httpUtil.SenderNotificacionTelegram;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "accPreBean")
@ViewScoped
public class AccPreManagedBean implements Serializable {

    @EJB
    private AccPreFacadeLocal accPreEjb;
    @EJB
    private NotificacionTelegramDetFacadeLocal notificacionTelegramDetEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;

    @Inject
    private AccidenteJSF accidenteBean;

    private List<AccPre> lstAccPre;

    private AccPre accPre;
    private Date fechaInicio;
    private Date fechaFin;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
//        fechaInicio = new Date();
//        fechaFin = new Date();
    }

    /**
     * Permite guardar un accidente desde objeto novedad, retornando la
     * direccion del accidente Se procede a guardar en accidentalidad de manera
     * automatica. Se omite el proceso de preaccidentalidad
     *
     * @param novedad Novedad
     * @return String , null si no encontró direccion
     */
    public GeocodingDTO guardarAccidente(Novedad novedad) {
        return guardarAccidenteSinPreAccidentalidad(novedad);
//        accPre = new AccPre();
//        accPre.setIdPrgTc(novedad.getPrgTc());
//        accPre.setCreado(new Date());
//        accPre.setEstadoReg(0);
//        accPre.setEstado(0);
//        accPre.setUsername(user.getUsername());
//        accPre.setIdNovedad(novedad);
//
//        // se cancela preaccidentalidad
//        //   accPreEjb.create(accPre);
    }

    public GeocodingDTO guardarAccidenteSinPreAccidentalidad(Novedad n) {
        GeocodingDTO ubicacionVeh = accidenteBean.guardarAccidente(n);
        if (ubicacionVeh != null && ubicacionVeh.getDireccion().equals("X")) {
            return null;
        }
        enviarNotificacionTelegram(n, ubicacionVeh);
        return ubicacionVeh;
    }

    private void enviarNotificacionTelegram(Novedad novedad, GeocodingDTO ubicacionVeh) {
        if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
            NotificacionTelegramDet detalle = notificacionTelegramDetEjb.findByIdNotifProcesoAndUf(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getIdNotificacionProceso(), novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
            if (detalle != null) {
                String urlPost = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.URL_NOTIF_TELEGRAM_ACC);
                if (urlPost != null) {
                    Empleado emp = null;
                    if (novedad.getIdEmpleado() != null) {
                        emp = empleadoFacadeLocal.find(novedad.getIdEmpleado().getIdEmpleado());
                    }
                    JSONObject objeto = SenderNotificacionTelegram.getObjeto();
                    objeto.put("chatId", detalle.getChatId());
                    objeto.put("token", detalle.getIdNotificacionTelegram().getToken());
                    objeto.put("nombreBot", detalle.getIdNotificacionTelegram().getNombreBot());
                    objeto.put("tipoEvento", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
                    objeto.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
                    objeto.put("operador", emp != null ? emp.getNombresApellidos() : "");
                    objeto.put("fechaHora", Util.dateFormat(novedad.getFecha()));
                    objeto.put("direccion", ubicacionVeh != null ? ubicacionVeh.getDireccion() : "NA");
                    objeto.put("latitud", ubicacionVeh != null ? ubicacionVeh.getLatitud() : "");
                    objeto.put("longitud", ubicacionVeh != null ? ubicacionVeh.getLongitud() : "");
                    objeto.put("descripcion", novedad.getObservaciones());
                    boolean sent = SenderNotificacionTelegram.send(objeto, urlPost);
                    System.out.println("sent-" + sent);
                }
            }

        }
    }

    public void cambiarEstado(int estado, AccPre accPre) {
        if (Util.validarFechaCambioEstado(fechaInicio, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin.");
            return;
        }
        if (accPre != null) {
            accPre.setEstado(estado);
            accPre.setUsernameModifica(user.getUsername());
            accPre.setModificado(new Date());

            if (estado == 1) {
                if (accPre.getIdPrgTc() != null) {
                    accPre.getIdNovedad().setPrgTc(accPre.getIdPrgTc());
                }
                accidenteBean.guardarAccidente(accPre.getIdNovedad());
                MovilidadUtil.addSuccessMessage("Registro aprobado");
            } else {
                MovilidadUtil.addSuccessMessage("Registro rechazado");
            }

            accPreEjb.edit(accPre);
        }
    }

    public boolean verificarEstado(int estado) {
        if (estado == 1) {
            return true;
        }
        if (estado == 2) {
            return true;
        }
        return false;
    }

    public void consultar() {
        if (fechaInicio == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha de inicio");
            return;
        }
        if (fechaFin == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha fin");
            return;
        }

        lstAccPre = accPreEjb.findByFecha(fechaInicio, fechaFin);
        if (lstAccPre == null) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
        }
    }

    public AccPre getAccPre() {
        return accPre;
    }

    public void setAccPre(AccPre accPre) {
        this.accPre = accPre;
    }

    public List<AccPre> getLstAccPre() {
        return lstAccPre;
    }

    public void setLstAccPre(List<AccPre> lstAccPre) {
        this.lstAccPre = lstAccPre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
