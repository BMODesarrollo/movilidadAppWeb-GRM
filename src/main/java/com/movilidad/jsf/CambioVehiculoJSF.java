/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DispClasificacionNovedadFacadeLocal;
import com.movilidad.ejb.DispEstadoPendActualFacadeLocal;
import com.movilidad.ejb.DispSistemaFacadeLocal;
import com.movilidad.ejb.MyAppNovedadParamFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadPrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoEstadoHistoricoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.DispClasificacionNovedad;
import com.movilidad.model.DispEstadoPendActual;
import com.movilidad.model.DispSistema;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.MyAppNovedadParam;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadPrgTc;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoEstadoHistorico;
import com.movilidad.model.VehiculoTipoEstadoDet;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.VehiculoChangeDTO;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "cambioVehiculoJSF")
@ViewScoped
public class CambioVehiculoJSF implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehFacadeLocal;
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private DispEstadoPendActualFacadeLocal estadoPendActualFacadeLocal;
    @EJB
    private VehiculoEstadoHistoricoFacadeLocal historicoFacadeLocal;
    @EJB
    private DispClasificacionNovedadFacadeLocal dispClasificacionNovedadFacadeLocal;
    @EJB
    private NovedadPrgTcFacadeLocal novedadPrgTcFacadeLocal;
    @EJB
    private NovedadFacadeLocal novedadFacadeLocal;
    @EJB
    private MyAppNovedadParamFacadeLocal myAppNovedadParamFacadeLocal;
    @EJB
    private DispSistemaFacadeLocal dispSistemaFacadeLocal;

    @Inject
    private PrgTcByVehiculoJSF prgTcByVehiculoJSF;
    @Inject
    private SelectDispSistemaBean selectDispSistemaBean;
    @Inject
    private ConfigMailParamJSF configMailParamJSF;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private SelectGopUnidadFuncionalBean selectGopUnidadFuncionalBean;

    private VehiculoChangeDTO vehChgDTO;
    private int idGopUF;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of CambioVehiculoJSF
     */
    public CambioVehiculoJSF() {
    }

    public void nuevoCambioVehiculo() {
        prgTcByVehiculoJSF.reset();
        vehChgDTO = new VehiculoChangeDTO();
    }

    @Transactional
    public void procesarCambioVehiculo() throws Exception {
        cargarUF();
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
            return;
        }
        PrgTc prgTc = prgTcByVehiculoJSF.getPrgTcSelect();
        Integer idDispSistema = selectDispSistemaBean.getId_dis_sistema();
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar el servicio para el cambio de vehículo");
            return;
        }
        if (idDispSistema == null) {
            MovilidadUtil.addErrorMessage("Sistema es requerido");
            return;
        }
        if (vehChgDTO.getNewVehiculo() == null) {
            return;
        }
        if (vehChgDTO.getObservacion() == null) {
            return;
        }
        MyAppNovedadParam manp = myAppNovedadParamFacadeLocal
                .findByCodigoProceso(ConstantsUtil.COD_NOVEDAD_CAMBIO_VEH);
        if (manp == null) {
            MovilidadUtil.addErrorMessage("No existe novedad parametrizada para MyApp");
            return;
        }
        Vehiculo vehNew = vehFacadeLocal.findByCodigo(vehChgDTO.getNewVehiculo());
        if (vehNew == null) {
            MovilidadUtil.addErrorMessage("Vehículo no disponible");
            return;
        }
        vehChgDTO.setNewVehiculo(vehNew.getCodigo());
        List<PrgTc> listPrgTcOld = prgTcFacadeLocal.getListPrgTcByIdVehiculoAndFechaAndHoraMayorIgual(
                prgTc.getIdVehiculo().getIdVehiculo(),
                Util.dateTimeFormat(Util.dateFormat(prgTc.getFecha()) + " " + prgTc.getTimeOrigin()),
                idGopUF);
        if (listPrgTcOld.isEmpty()) {
            // cuenta con registros
            MovilidadUtil.addErrorMessage("Vehiculo " + prgTc.getIdVehiculo().getCodigo() + " cuenta con servicios");
            return;
        }
        List<PrgTc> listPrgTcNew = prgTcFacadeLocal.getListPrgTcByIdVehiculoAndFechaAndHoraMayorIgual(
                vehNew.getIdVehiculo(),
                Util.dateTimeFormat(Util.dateFormat(prgTc.getFecha()) + " " + prgTc.getTimeOrigin()),
                idGopUF);
        if (!listPrgTcNew.isEmpty()) {
            // cuenta con registros
            MovilidadUtil.addErrorMessage("Vehiculo " + vehNew.getCodigo() + " cuenta con servicios");
            return;
        }

//        MovilidadUtil.addSuccessMessage("Todo ok");
        guardarNovedad(vehChgDTO, idDispSistema, prgTc, vehNew, listPrgTcOld, manp);
        reset();
        MovilidadUtil.hideModal("serv-veh-wv");
        MovilidadUtil.addSuccessMessage("Cambio de vehículo realizado con exito. "
                + listPrgTcOld.size()
                + " Servicios afectados");
    }

    public void reset() {
        vehChgDTO = null;
    }

    private void guardarNovedad(VehiculoChangeDTO dto, Integer idDispSistema, PrgTc prgTc, Vehiculo vehNew,
            List<PrgTc> listPrgTc, MyAppNovedadParam manp) throws Exception {
        Vehiculo vehOld = prgTc.getIdVehiculo();
        Novedad nov = new Novedad();
        nov.setIdVehiculo(vehOld);
        nov.setCreado(MovilidadUtil.fechaCompletaHoy());
        nov.setModificado(MovilidadUtil.fechaCompletaHoy());
        nov.setFecha(MovilidadUtil.fechaHoy());
        nov.setIdEmpleado(prgTc.getIdEmpleado());
        nov.setEstadoReg(0);
        nov.setIdGopUnidadFuncional(new GopUnidadFuncional(idGopUF));
        nov.setIdNovedadTipoDetalle(manp.getIdNovedadTipoDetalle());
        nov.setIdNovedadTipo(manp.getIdNovedadTipoDetalle().getIdNovedadTipo());
        nov.setObservaciones(dto.getObservacion());
        nov.setUsername(user.getUsername());
        nov.setIdGopUnidadFuncional(prgTc.getIdGopUnidadFuncional());
        nov.setPuntosPm(0);
        nov.setPuntosPmConciliados(0);
        nov.setLiquidada(0);
        List<NovedadPrgTc> listNovPrgTc = new ArrayList<>();
        listPrgTc.forEach(prg -> {
            prg.setIdVehiculo(vehNew);
            prg.setOldVehiculo(vehOld.getIdVehiculo());
            prg.setObservaciones(dto.getObservacion());
            NovedadPrgTc np = new NovedadPrgTc();
            np.setIdNovedad(nov);
            np.setIdPrgTc(prg);
            np.setIdGopUnidadFuncional(new GopUnidadFuncional(idGopUF));
            np.setIdEmpleado(prg.getIdEmpleado());
            np.setIdVehiculo(vehOld);

            np.setToStop(prg.getToStop());
            np.setFromStop(prg.getFromStop());
            np.setIdPrgTcResponsable(prg.getIdPrgTcResponsable());
            np.setObservaciones(dto.getObservacion());
            np.setTimeOrigin(prg.getTimeOrigin());
            np.setTimeDestiny(prg.getTimeDestiny());
            np.setEstadoOperacion(prg.getEstadoOperacion());
            np.setDistancia(prgTcFacadeLocal
                    .findDistandeByFromStopAndToStop(prg.getFromStop().getIdPrgStoppoint(),
                            prg.getToStop().getIdPrgStoppoint()));
            np.setUsername(user.getUsername());
            np.setCreado(MovilidadUtil.fechaCompletaHoy());
            np.setEstadoReg(0);
            listNovPrgTc.add(np);
        });
        DispEstadoPendActual depa = null;
        if (nov.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == 1) {
            vehOld.setIdVehiculoTipoEstado(
                    nov.getIdNovedadTipoDetalle()
                            .getIdVehiculoTipoEstadoDet()
                            .getIdVehiculoTipoEstado());
            // vehService.modificar(); probar si guardando la nov se actualiza el vehiculo.
            VehiculoTipoEstadoDet idVehiculoTipoEstadoDet = nov.
                    getIdNovedadTipoDetalle().
                    getIdVehiculoTipoEstadoDet();
            if (idVehiculoTipoEstadoDet != null) {
                List<DispEstadoPendActual> listEstPendActl = estadoPendActualFacadeLocal
                        .findFirtStatusByidVehiculoTipoEstadoDetOrAll(idVehiculoTipoEstadoDet
                                .getIdVehiculoTipoEstadoDet(),
                                false);
                if (!listEstPendActl.isEmpty()) {
                    depa = listEstPendActl.get(0);
                }
            }
            DispClasificacionNovedad dcn = new DispClasificacionNovedad();
            dcn.setCreado(MovilidadUtil.fechaCompletaHoy());
            dcn.setModificado(MovilidadUtil.fechaCompletaHoy());
            dcn.setEstadoReg(0);
            dcn.setFechaHabilitacion(null);
//            dcn.setIdDispCausaEntrada(dce);
            dcn.setIdDispEstadoPendActual(depa);
            dcn.setIdDispSistema(new DispSistema(idDispSistema));
            dcn.setObservacion(dto.getObservacion());
            dcn.setUsername(user.getUsername());
            nov.setIdDispClasificacionNovedad(dcn);
            dispClasificacionNovedadFacadeLocal.create(dcn);

            VehiculoEstadoHistorico vehEstHis = new VehiculoEstadoHistorico();
            vehEstHis.setIdVehiculo(vehOld);
            vehEstHis.setIdDispActividad(null);
            vehEstHis.setFechaHora(MovilidadUtil.fechaCompletaHoy());
            vehEstHis.setUsuarioReporta(user.getUsername());
            vehEstHis.setCreado(MovilidadUtil.fechaCompletaHoy());
            vehEstHis.setModificado(MovilidadUtil.fechaCompletaHoy());
            vehEstHis.setUsername(user.getUsername());
            vehEstHis.setObservacion(dto.getObservacion());
            vehEstHis.setEstadoReg(0);
            vehEstHis.setIdDispEstadoPendActual(depa);
            vehEstHis.setIdVehiculoTipoEstado(vehOld.getIdVehiculoTipoEstado());
            vehEstHis.setIdVehiculoTipoEstadoDet(nov.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet());
            historicoFacadeLocal.create(vehEstHis);
//            vehOld.setIdVehiculoTipoEstado(nov.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstado());
            vehFacadeLocal.edit(vehOld);
        }
        if (nov.getIdNovedadTipoDetalle().getNotificacion() == 1) {
            DispSistema dispSistema = dispSistemaFacadeLocal.find(idDispSistema);
            Map map = new HashMap<>();
            map.put("tipoNovedad", nov.getIdNovedadTipo().getNombreTipoNovedad());
            map.put("tipoDetalle", nov.getIdNovedadTipoDetalle().getTituloTipoNovedad());
            map.put("fechaHora", Util.dateTimeFormat(MovilidadUtil.fechaCompletaHoy()));
            map.put("reportadoPor", user.getUsername());
            map.put("empleado", nov.getIdEmpleado().getNombresApellidos());
            map.put("vehiculo", nov.getIdVehiculo().getCodigo());
            map.put("vehiculoCambio", vehNew.getCodigo());
            map.put("causa", dispSistema != null ? dispSistema.getNombre() : "NO APLICA");
            map.put("servicios", listPrgTc.size());
            map.put("estado", depa != null ? depa.getNombre() : "NO APLICA");
            map.put("observacion", dto.getObservacion());
            notificar(map, manp);
        }
        novedadFacadeLocal.create(nov);
        prgTcFacadeLocal.editList(listPrgTc);
        novedadPrgTcFacadeLocal.createList(listNovPrgTc);
    }

    private void notificar(Map map, MyAppNovedadParam manp) {
        SendMails.sendEmail(configMailParamJSF.getMailParamByTemplate(Util.TEMPLATE_CAMBIO_VEHICULO),
                map,
                manp.getAsunto(),
                "",
                manp.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails(),
                "Notificaciones RIGEL", null);
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.getIdGopUnidadFuncional();
        if (idGopUF == 0) {
            Integer i = selectGopUnidadFuncionalBean.getI_unidad_funcional();
            idGopUF = i == null ? 0 : i;
        }
//        System.out.println("idGopUF " + idGopUF);
    }

    public VehiculoChangeDTO getVehChgDTO() {
        return vehChgDTO;
    }

    public void setVehChgDTO(VehiculoChangeDTO vehChgDTO) {
        this.vehChgDTO = vehChgDTO;
    }

}
