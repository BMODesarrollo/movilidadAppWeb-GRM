/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.VehiculoLavadoStatusDTO;
import com.movilidad.ejb.LavadoGmFacadeLocal;
import com.movilidad.ejb.LavadoTipoServicioFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.LavadoCalificacion;
import com.movilidad.model.LavadoGm;
import com.movilidad.model.LavadoMotivo;
import com.movilidad.model.LavadoTipoServicio;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "lavadoGMJSF")
@ViewScoped
public class LavadoGMJSF implements Serializable {

    @EJB
    private LavadoGmFacadeLocal lavadoGmFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private LavadoTipoServicioFacadeLocal lavadoTipoServicioFacadeLocal;

    //
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    //
    private List<LavadoGm> listLavado;

    // var lavado
    private LavadoGm lavadoGm;
    private Integer idLavadoTipoServicio;
    private Integer idLavadoMotivo;
    private String vehiculo;
    private Integer idLavadoCalificacion;

    // costos
    List<VehiculoLavadoStatusDTO> vehiculoLavadoStatusList;

    private Date desde, hasta;
    private int idGopUF;
    private int i_Vehicle;
    private List<LavadoGm> listLavadosRange;
    List<Vehiculo> vehiculos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of LavadoGMJSF
     */
    public LavadoGMJSF() {
        desde = new Date();
        hasta = new Date();
    }

    @PostConstruct
    public void init() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        listLavado = new ArrayList<>();
        consultarLavados();
    }

    public void consultarLavados() {
        listLavado.clear();
        cargarUF();
        listLavado = lavadoGmFacadeLocal.findLavadoGMByFechaAndGopUF(idGopUF, desde, hasta);
        vehiculos = vehiculoFacadeLocal.findByidGopUnidadFuncAndTipo(idGopUF, 0);
        listLavadosRange = null;

        if (isCurrentTimeInRange()) {
            ZonedDateTime[] dateRange = getDateRange(true);
            Date startDate = Date.from(dateRange[0].toInstant());
            Date endDate = Date.from(dateRange[1].toInstant());
            listLavadosRange = lavadoGmFacadeLocal.findLavadoByRange(startDate, endDate, idGopUF);
        } else {
            ZonedDateTime[] dateRange = getDateRange(false);
            Date startDate = Date.from(dateRange[0].toInstant());
            Date endDate = Date.from(dateRange[1].toInstant());
            listLavadosRange = lavadoGmFacadeLocal.findLavadoByRange(startDate, endDate, idGopUF);
        }

        Map<Integer, Integer> lavadoCounter = new HashMap<>();
        for (LavadoGm lavado : listLavadosRange) {
            Integer vehiculoId = lavado.getIdVehiculo().getIdVehiculo();
            lavadoCounter.put(vehiculoId, lavadoCounter.getOrDefault(vehiculoId, 0) + 1);
        }

        vehiculoLavadoStatusList = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            Integer vehiculoId = vehiculo.getIdVehiculo();
            int lavados = lavadoCounter.getOrDefault(vehiculoId, 0);
            String codigo = vehiculo.getCodigo();
            boolean estaLavado = lavados > 0;
            vehiculoLavadoStatusList.add(new VehiculoLavadoStatusDTO(vehiculoId, codigo, lavados, estaLavado));
        }

    }

    public void nuevoLavado() {
        reset();
        vehiculos = vehiculoFacadeLocal.findByidGopUnidadFuncAndTipo(idGopUF, 0);
        lavadoGm = new LavadoGm();
    }

    public void procesarLavado() {
        if (lavadoGm == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }

        boolean isCreate = lavadoGm.getIdLavadoGm() == null;
        if (idLavadoTipoServicio == null) {
            MovilidadUtil.addErrorMessage("Lavado Tipo Servicio es requerido");
            return;
        }
        cargarUF();
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            return;
        }
        LavadoTipoServicio lavadoTpServ = lavadoTipoServicioFacadeLocal.find(idLavadoTipoServicio);
        if (lavadoTpServ == null) {
            MovilidadUtil.addErrorMessage("Lavado Tipo Servicio es requerido");
            return;
        }

        if (lavadoTpServ.getEspecial() == 1) {
            if (idLavadoMotivo == null) {
                MovilidadUtil.addErrorMessage("Lavado es de tipo especial, motivo es requerido");
                return;
            }
            if (Util.isStringNullOrEmpty(lavadoGm.getObsMotivo())) {
                MovilidadUtil.addErrorMessage("Observación motivo es requerido");
                return;
            }
        } else {
            if (idLavadoMotivo == null) {
                lavadoGm.setIdLavadoMotivo(null);
                lavadoGm.setObsMotivo(null);
            }
        }

        if (i_Vehicle != 0) {
            lavadoGm.setIdVehiculo(vehiculoFacadeLocal.find(i_Vehicle));
        } else {
            MovilidadUtil.addErrorMessage("Vehículo es requerido");
            return;
        }
        Date d = new Date();
        if (isCreate) {
            lavadoGm.setCreado(d);
            lavadoGm.setFechaHora(d);
            lavadoGm.setEstadoReg(0);
            lavadoGm.setEstado(0);
        }
        lavadoGm.setModificado(d);
        lavadoGm.setIdGopUnidadFuncional(lavadoGm.getIdVehiculo().getIdGopUnidadFuncional());
        if (lavadoTpServ.getEspecial() == 1) {
            lavadoGm.setIdLavadoMotivo(new LavadoMotivo(idLavadoMotivo));
        }
        lavadoGm.setIdLavadoTipoServicio(lavadoTpServ);
        lavadoGm.setUsername(user.getUsername());
        if (isCreate) {
            lavadoGmFacadeLocal.create(lavadoGm);
            reset();
            nuevoLavado();
            MovilidadUtil.addSuccessMessage("Registro creado con éxito");
        } else {
            lavadoGmFacadeLocal.edit(lavadoGm);
            MovilidadUtil.addSuccessMessage("Registro actualizado con éxito");
            reset();
            MovilidadUtil.hideModal("modalDlg");
        }
        consultarLavados();
    }

    public void onLavadoGm(LavadoGm event) {
        lavadoGm = event;
        idLavadoMotivo = event.getIdLavadoMotivo() != null ? event.getIdLavadoMotivo().getIdLavadoMotivo() : null;
        idLavadoTipoServicio = event.getIdLavadoTipoServicio() != null ? event.getIdLavadoTipoServicio().getIdLavadoTipoServicio() : null;
        vehiculo = event.getIdVehiculo() != null ? event.getIdVehiculo().getCodigo() : null;
    }

    public void prepareCalificar() {
        if (lavadoGm == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        idLavadoMotivo = lavadoGm.getIdLavadoMotivo() != null ? lavadoGm.getIdLavadoMotivo().getIdLavadoMotivo() : null;
        idLavadoTipoServicio = lavadoGm.getIdLavadoTipoServicio() != null ? lavadoGm.getIdLavadoTipoServicio().getIdLavadoTipoServicio() : null;
        MovilidadUtil.openModal("modalDlg");
    }

    public void calificarLavado() {
        if (lavadoGm == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        if (idLavadoCalificacion == null) {
            MovilidadUtil.addErrorMessage("Calificación es requerido");
            return;
        }
        lavadoGm = lavadoGmFacadeLocal.find(lavadoGm.getIdLavadoGm());
        if (lavadoGm.getEstado().equals(ConstantsUtil.LAVADO_ABIERTO)) {
            MovilidadUtil.addErrorMessage("Lavado no puede ser calificado, por que aún no ha sido cerrado");
            return;
        }
        if (lavadoGm.getFechaCalifica() != null) {
            MovilidadUtil.addErrorMessage("Lavado ya ha sido calificado el día " + Util.dateTimeFormat(lavadoGm.getFechaCalifica()));
            return;
        }
        Date d = new Date();
        lavadoGm.setIdLavadoCalificacion(new LavadoCalificacion(idLavadoCalificacion));
        lavadoGm.setModificado(d);
        lavadoGm.setFechaCalifica(d);
        lavadoGm.setUsrCalifica(user.getUsername());
        lavadoGmFacadeLocal.edit(lavadoGm);
        reset();
        consultarLavados();
        MovilidadUtil.addSuccessMessage("Lavado calificado con éxito");
        MovilidadUtil.hideModal("modalDlg");
    }

    public void cerrarLavado() {
        if (lavadoGm == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        Integer aux = lavadoGm.getCosto();
        lavadoGm = lavadoGmFacadeLocal.find(lavadoGm.getIdLavadoGm());
        if (lavadoGm.getEstado().equals(ConstantsUtil.LAVADO_CERRADO)) {
            MovilidadUtil.addErrorMessage("Lavado ha sido cerrado el día " + Util.dateTimeFormat(lavadoGm.getFechaCierre()));
            return;
        }
        if (lavadoGm.getFechaCalifica() != null) {
            MovilidadUtil.addErrorMessage("Lavado no permitido");
            return;
        }
        Date d = new Date();
        lavadoGm.setEstado(1);
        lavadoGm.setModificado(d);
        lavadoGm.setFechaCierre(d);
        lavadoGm.setCosto(aux);
        lavadoGm.setUsrCierra(user.getUsername());
        lavadoGmFacadeLocal.edit(lavadoGm);
        reset();
        consultarLavados();
        MovilidadUtil.addSuccessMessage("Lavado cerrado con éxito");
    }

    public void aprobarLavado() {
        if (lavadoGm == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        lavadoGm = lavadoGmFacadeLocal.find(lavadoGm.getIdLavadoGm());
        if (lavadoGm.getEstado().equals(ConstantsUtil.LAVADO_ABIERTO)) {
            MovilidadUtil.addErrorMessage("Lavado no ha sido cerrado");
            return;
        }
        if (lavadoGm.getAprobado() == ConstantsUtil.LAVADO_APROBADO) {
            MovilidadUtil.addErrorMessage("Lavado se encuentra aprobado");
            return;
        }
        Date d = new Date();
        lavadoGm.setAprobado(ConstantsUtil.LAVADO_APROBADO);
        lavadoGm.setModificado(d);
        lavadoGm.setFechaAprueba(d);
        lavadoGm.setUsrAprueba(user.getUsername());
        lavadoGmFacadeLocal.edit(lavadoGm);
        reset();
        consultarLavados();
        MovilidadUtil.addSuccessMessage("Lavado aprobado con éxito");
    }

    public void noAprobarLavado() {
        if (lavadoGm == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        lavadoGm = lavadoGmFacadeLocal.find(lavadoGm.getIdLavadoGm());
        if (lavadoGm.getEstado().equals(ConstantsUtil.LAVADO_ABIERTO)) {
            MovilidadUtil.addErrorMessage("Lavado no ha sido cerrado");
            return;
        }
        if (lavadoGm.getAprobado() == ConstantsUtil.LAVADO_NO_APROBADO) {
            MovilidadUtil.addErrorMessage("Lavado ha sido no aprobado el día " + Util.dateTimeFormat(lavadoGm.getFechaAprueba()));
            return;
        }
        Date d = new Date();
        lavadoGm.setAprobado(ConstantsUtil.LAVADO_NO_APROBADO);
        lavadoGm.setModificado(d);
        lavadoGm.setFechaAprueba(d);
        lavadoGm.setUsrAprueba(user.getUsername());
        lavadoGmFacadeLocal.edit(lavadoGm);
        reset();
        consultarLavados();
        MovilidadUtil.addSuccessMessage("Lavado no aprobado con éxito");
    }

    public boolean isCurrentTimeInRange() {
        // Obtiene la fecha y hora actual
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        // Obtiene la fecha y hora de hoy a las 07:00 PM
        ZonedDateTime start = now.with(LocalTime.of(19, 0));

        // Obtiene la fecha y hora de mañana a las 07:00 PM
        ZonedDateTime end = start.plusDays(1);

        // Valida si la fecha y hora actual está en el rango
        return now.isAfter(start) && now.isBefore(end);
    }

    public static ZonedDateTime[] getDateRange(boolean flag) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime start, end;

        if (flag) {
            // Si el parámetro es true
            start = now.with(LocalTime.of(17, 0)); // Hoy a las 17:00
            end = start.plusDays(1); // Mañana a las 17:00
        } else {
            // Si el parámetro es false
            start = now.with(LocalTime.of(17, 0)).minusDays(1); // Ayer a las 17:00
            end = start.plusDays(1); // Hoy a las 17:00
        }

        return new ZonedDateTime[]{start, end};
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    private void reset() {
        i_Vehicle = 0;
        lavadoGm = null;
        idLavadoTipoServicio = null;
        idLavadoMotivo = null;
        vehiculo = null;
        idLavadoCalificacion = null;
    }

    public List<LavadoGm> getListLavado() {
        return listLavado;
    }

    public void setListLavado(List<LavadoGm> listLavado) {
        this.listLavado = listLavado;
    }

    public LavadoGm getLavadoGm() {
        return lavadoGm;
    }

    public void setLavadoGm(LavadoGm lavadoGm) {
        this.lavadoGm = lavadoGm;
    }

    public Integer getIdLavadoTipoServicio() {
        return idLavadoTipoServicio;
    }

    public void setIdLavadoTipoServicio(Integer idLavadoTipoServicio) {
        this.idLavadoTipoServicio = idLavadoTipoServicio;
    }

    public Integer getIdLavadoMotivo() {
        return idLavadoMotivo;
    }

    public void setIdLavadoMotivo(Integer idLavadoMotivo) {
        this.idLavadoMotivo = idLavadoMotivo;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Integer getIdLavadoCalificacion() {
        return idLavadoCalificacion;
    }

    public void setIdLavadoCalificacion(Integer idLavadoCalificacion) {
        this.idLavadoCalificacion = idLavadoCalificacion;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public List<LavadoGm> getListLavadosRange() {
        return listLavadosRange;
    }

    public void setListLavadosRange(List<LavadoGm> listLavadosRange) {
        this.listLavadosRange = listLavadosRange;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public LavadoTipoServicioFacadeLocal getLavadoTipoServicioFacadeLocal() {
        return lavadoTipoServicioFacadeLocal;
    }

    public void setLavadoTipoServicioFacadeLocal(LavadoTipoServicioFacadeLocal lavadoTipoServicioFacadeLocal) {
        this.lavadoTipoServicioFacadeLocal = lavadoTipoServicioFacadeLocal;
    }

    public List<VehiculoLavadoStatusDTO> getVehiculoLavadoStatusList() {
        return vehiculoLavadoStatusList;
    }

    public void setVehiculoLavadoStatusList(List<VehiculoLavadoStatusDTO> vehiculoLavadoStatusList) {
        this.vehiculoLavadoStatusList = vehiculoLavadoStatusList;
    }

    public int getI_Vehicle() {
        return i_Vehicle;
    }

    public void setI_Vehicle(int i_Vehicle) {
        this.i_Vehicle = i_Vehicle;
    }

}
