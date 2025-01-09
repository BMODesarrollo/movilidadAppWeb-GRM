/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.PrgSerconDTO;
import com.movilidad.ejb.GopAlertaPresentacionFacadeLocal;
import com.movilidad.ejb.MyAppSerconConfirmFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.GopAlertaPresentacion;
import com.movilidad.model.MyAppSerconConfirm;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgTc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.ObjetoSigleton;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "alertaAsistenciaBean")
@ViewScoped
public class AlertaAsistenciaBean implements Serializable {

    private String mensaje;
    private List<PrgSerconDTO> prgSerconDTOs;
    private List<PrgSercon> prgSerconSonConfirmacion;
    @EJB
    private PrgSerconFacadeLocal prgSerconFacadeLocal;
    @EJB
    private GopAlertaPresentacionFacadeLocal alertaPresentacionFacadeLocal;
    @EJB
    private MyAppSerconConfirmFacadeLocal MyAppSerconConfirmEJB;
    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of AlertaAsistenciaBean
     */
    public AlertaAsistenciaBean() {
    }

    public void consultarAllSinConfirmacion() {
        prgSerconSonConfirmacion = prgSerconFacadeLocal.findAllSerconSinPresentacionByUnidadFuncional(
                MovilidadUtil.fechaHoy(), (getGopAlertaPresentacion().getMinutos() * 60),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                Integer.parseInt(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO)));
    }

    public List<PrgSerconDTO> consultarSinConfirmacion() {
        if (getGopAlertaPresentacion() != null) {
            return prgSerconFacadeLocal.findSerconSinPresentacionByUnidadFunc(MovilidadUtil.fechaHoy(),
                    (getGopAlertaPresentacion().getMinutos() * 60), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        }
        return new ArrayList<>();
    }

    public void alertaSinPresentacion() {
        mensaje = "";
        prgSerconDTOs = consultarSinConfirmacion();
        for (PrgSerconDTO ps : prgSerconDTOs) {
            mensaje = mensaje + ps.getCodigo_tm() + " Operador sin presentación\n";
        }
        if (!prgSerconDTOs.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage(mensaje);

            PrimeFaces.current().executeScript("addListenerMsgs()");
//        MovilidadUtil.onAbrirModal("notifi_bar");
        }
    }

    public void onSetVisto() {
        for (PrgSerconDTO obj : prgSerconDTOs) {
            prgSerconFacadeLocal.updateVistoInPrgSercon(obj.getId_prg_sercon());
        }
    }

    public void registrarInicioJornada(PrgSercon prgSercon) {
        if (validarPresentacion(prgSercon)) {
            return;
        }
        PrgTc servicio = prgTcEJB.firtServiceByIdEmpleado(
                prgSercon.getIdEmpleado().getIdEmpleado(),
                prgSercon.getFecha());

        MyAppSerconConfirm masc = new MyAppSerconConfirm();
        masc.setCreado(MovilidadUtil.fechaCompletaHoy());
        masc.setEstadoReg(0);
        masc.setIdentificacion(prgSercon.getIdEmpleado().getIdentificacion());
        masc.setIdPrgStopPoint(servicio == null ? null : servicio.getFromStop());
        masc.setVerbo(user.getUsername());

        masc.setIdTask(servicio == null ? null : servicio.getIdTaskType().getIdPrgTarea());
        masc.setIsStop(servicio == null ? null : servicio.getFromStop().getIdPrgStoppoint());
        masc.setIdPrgTc(servicio);
        masc.setIdEmpleado(prgSercon.getIdEmpleado());
        Date fecha = Util.dateTimeFormat(Util.dateFormat(prgSercon.getFecha()) + " " + prgSercon.getTimeOrigin());
        masc.setFecha(fecha);
        masc.setProcesado(0);
        MyAppSerconConfirmEJB.create(masc);
        prgSerconFacadeLocal.updatePrgSerconMySerconConfirm(
                prgSercon.getIdPrgSercon(),
                masc.getIdMyAppSerconConfirm(),
                user.getUsername());
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        consultarAllSinConfirmacion();
    }

    public boolean validarPresentacion(PrgSercon prgSercon) {
        MyAppSerconConfirm findByIdEmpledao
                = MyAppSerconConfirmEJB.findByIdEmpledao(
                        prgSercon.getIdEmpleado().getIdEmpleado(),
                        prgSercon.getFecha());
        if (findByIdEmpledao != null) {
            prgSerconSonConfirmacion.remove(prgSercon);
            MovilidadUtil.addAdvertenciaMessage("Ya existe registro de presentacion para el empleado.");
            return true;
        }
        return false;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<PrgSercon> getPrgSerconSonConfirmacion() {
        return prgSerconSonConfirmacion;
    }

    public GopAlertaPresentacion getGopAlertaPresentacion() {
        if (ObjetoSigleton.getInstanceGopAlertaPresentacion() == null) {
            ObjetoSigleton.setGopAlertaPresentacion(alertaPresentacionFacadeLocal.find(1));
        }
        return ObjetoSigleton.getInstanceGopAlertaPresentacion();
    }

}
