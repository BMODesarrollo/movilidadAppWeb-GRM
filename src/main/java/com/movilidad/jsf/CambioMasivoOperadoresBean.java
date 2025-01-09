package com.movilidad.jsf;

import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgSerconMotivoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgSerconMotivo;
import com.movilidad.model.PrgTc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cambioMasivoOperadoresBean")
@ViewScoped
public class CambioMasivoOperadoresBean implements Serializable {

    @EJB
    private PrgSerconMotivoFacadeLocal serconMotivoEJB;
    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEJB;
    @EJB
    private NovedadFacadeLocal novedadEJB;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetalleEJB;
    @EJB
    private ConfigFacadeLocal configEJB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Date fechaDesde;
    private Date fechaHasta;
    private Integer i_prgSerconMotivo;
    private String codOperador1;
    private String codOperador2;
    private String observacion;

    private Empleado empleado1;
    private Empleado empleado2;

    private List<PrgSerconMotivo> prgSerconMotivoList;

    private List<PrgSercon> prgSerconOperador1List;
    private List<PrgSercon> prgSerconOperador2List;

    private List<PrgTc> prgTcOperador1List;
    private List<PrgTc> prgTcOperador2List;

    /**
     *
     * Verifica si una novedad_detalle procede de forma automática al
     * agregar/modificar una novedad
     *
     * @return true si el valor es IGUAL a 1, de lo contrario false
     */
    public boolean procedeNovedad() {
        try {
            return configEJB.findByKey("nov").getValue() == ConstantsUtil.ON_INT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cargarDatosParaAjustar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            MovilidadUtil.updateComponent(":msgs");
            return;
        }

        fechaDesde = null;
        fechaHasta = null;
        i_prgSerconMotivo = null;
        codOperador1 = null;
        codOperador2 = null;
        empleado1 = null;
        empleado2 = null;

        prgSerconMotivoList = serconMotivoEJB.findAllEstadoRegistro();

        prgSerconOperador1List = null;
        prgSerconOperador2List = null;

        prgTcOperador1List = null;
        prgTcOperador2List = null;

        MovilidadUtil.openModal("cambioEmplWVMasivo");
    }

    public void buscarOperador1() {

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            MovilidadUtil.addErrorMessage("La fecha desde NO debe ser mayor "
                    + "a la fecha hasta");
            return;
        }

        empleado1 = empleadoEJB.findbyCodigoTmAndIdGopUnidadFuncActivo(
                Integer.parseInt(codOperador1),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (empleado1 == null) {
            MovilidadUtil.addErrorMessage("El operador consultado NO existe ó "
                    + "NO se encuentra en la unidad funcional seleccionada");
            return;
        }

        if (validarUF()) {
            return;
        }

        MovilidadUtil.addSuccessMessage("Operador 1 cargado con éxito");

        cargarDatosOperador1();

    }

    public void buscarOperador2() {

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            MovilidadUtil.addErrorMessage("La fecha desde NO debe ser mayor "
                    + "a la fecha hasta");
            return;
        }

        empleado2 = empleadoEJB.findbyCodigoTmAndIdGopUnidadFuncActivo(
                Integer.parseInt(codOperador2),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (empleado2 == null) {
            MovilidadUtil.addErrorMessage("El operador consultado NO existe ó "
                    + "NO se encuentra en la unidad funcional seleccionada");
            return;
        }

        if (validarUF()) {
            return;
        }

        MovilidadUtil.addSuccessMessage("Operador 2 cargado con éxito");

        cargarDatosOperador2();

    }

    private void cargarDatosOperador1() {

        prgSerconOperador1List = prgSerconEJB.
                obtenerSerconesPorRangoFechasYEmpleado(
                        fechaDesde, fechaHasta,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                        empleado1.getIdEmpleado());

        prgTcOperador1List = prgTcEJB.obtenerTareasPorFechasYOperador(
                fechaDesde, fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                empleado1.getIdEmpleado());
    }

    private void cargarDatosOperador2() {

        prgSerconOperador2List = prgSerconEJB.
                obtenerSerconesPorRangoFechasYEmpleado(
                        fechaDesde, fechaHasta,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                        empleado2.getIdEmpleado());

        prgTcOperador2List = prgTcEJB.obtenerTareasPorFechasYOperador(
                fechaDesde, fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                empleado2.getIdEmpleado());

    }

    @Transactional
    public void guardarCambioOperadores() {
        try {

            String mensajeValidacion = validarDatos();

            if (mensajeValidacion == null) {

                /**
                 * Se realiza el cambio de jornadas y servicios entre los
                 * operadores
                 */
                prgSerconOperador1List.forEach(item -> {

                    item.setAutorizado(1);
                    item.setPrgModificada(1);
                    item.setUserGenera(user.getUsername());
                    item.setUserAutorizado(user.getUsername());
                    item.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
                    item.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                    item.setIdPrgSerconMotivo(new PrgSerconMotivo(i_prgSerconMotivo));
                    item.setIdEmpleado(empleado2);
                    item.setObservaciones(observacion);
                    item.setUsername(user.getUsername());
                    prgSerconEJB.edit(item);
                });
                prgSerconOperador2List.forEach(item -> {

                    item.setAutorizado(1);
                    item.setPrgModificada(1);
                    item.setUserGenera(user.getUsername());
                    item.setUserAutorizado(user.getUsername());
                    item.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
                    item.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                    item.setIdPrgSerconMotivo(new PrgSerconMotivo(i_prgSerconMotivo));
                    item.setIdEmpleado(empleado1);
                    item.setObservaciones(observacion);
                    item.setUsername(user.getUsername());
                    prgSerconEJB.edit(item);

                });

                prgTcOperador1List.forEach(item -> {
                    item.setOldEmpleado(item.getIdEmpleado().getIdEmpleado());
                    item.setIdEmpleado(empleado2);

                    prgTcEJB.edit(item);
                });

                prgTcOperador2List.forEach(item -> {
                    item.setOldEmpleado(item.getIdEmpleado().getIdEmpleado());
                    item.setIdEmpleado(empleado1);

                    prgTcEJB.edit(item);
                });

                //Se crea novedad para cambio de turno
                Novedad novedad = new Novedad();
                novedad.setFecha(MovilidadUtil.fechaHoy());
                novedad.setIdEmpleado(empleado1);
                novedad.setOldEmpleado(empleado2);
                novedad.setObservaciones("Cambio Masivo de turno desde "
                        + Util.dateFormat(fechaDesde) + " hasta "
                        + Util.dateFormat(fechaHasta) + "entre los operadores "
                        + empleado1.getCodigoTm() + " y " + empleado2.getCodigoTm() + ". Realizado por " + user.getUsername());

                Integer idCambioTurno = Integer.parseInt(
                        SingletonConfigEmpresa.getMapConfiMapEmpresa().
                                get(ConstantsUtil.ID_DETALLE_CAMBIO_TURNO));

                if (idCambioTurno == null) {
                    MovilidadUtil.addErrorMessage("El parámetro detalle cambio turno NO ha sido registrado en el módulo de configuración");
                    throw new Exception("El parámetro detalle cambio turno NO ha sido registrado en el módulo de config empresa");
                }

                NovedadTipoDetalles novedadTipoDetalle = novedadTipoDetalleEJB.find(
                        idCambioTurno
                );

                novedad.setIdNovedadTipo(novedadTipoDetalle.getIdNovedadTipo());
                novedad.setIdNovedadTipoDetalle(novedadTipoDetalle);
                novedad.setUsername(user.getUsername());
                novedad.setEstadoReg(0);
                novedad.setCreado(MovilidadUtil.fechaCompletaHoy());

                if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == ConstantsUtil.ON_INT) {
                    if (procedeNovedad()) {
                        novedad.setProcede(1);
                        novedad.setPuntosPm(novedadTipoDetalle.getPuntosPm());
                        novedad.setPuntosPmConciliados(0);
                    }
                }

                novedadEJB.create(novedad);

                MovilidadUtil.addSuccessMessage("Cambio de turnos realizado con éxito");
                MovilidadUtil.runScript("actualizarSercones()");
                MovilidadUtil.hideModal("cambioEmplWVMasivo");

            } else {
                MovilidadUtil.addErrorMessage(mensajeValidacion);
            }

        } catch (Exception e) {
            System.out.println("Error al realizar la operación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    boolean validarUF() {
        if (empleado1 != null && empleado2 != null) {
            if (empleado1.getIdGopUnidadFuncional() != null && empleado2.getIdGopUnidadFuncional() != null) {
                if (!empleado1.getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                        .equals(empleado2.getIdGopUnidadFuncional().getIdGopUnidadFuncional())) {
                    MovilidadUtil.addErrorMessage("Los Operadores a cambiar no comparten la misma unidad funcional.");
                    MovilidadUtil.updateComponent("msgs");
                    MovilidadUtil.updateComponent("form_cambioEmpl:msgs_cambio_empl");
                    return true;
                }
            }
        }
        return false;
    }

    private String validarDatos() {

        if (empleado1 == null) {
            return "Operador 1 es Obligatorio";
        }

        if (empleado2 == null) {
            return "Operador 2 es Obligatorio";
        }

        if (prgSerconOperador1List == null || prgSerconOperador1List.isEmpty()) {
            return "El operador 1 NO tiene jornadas registradas para el rango de fechas seleccionado";
        }
        if (prgSerconOperador2List == null || prgSerconOperador2List.isEmpty()) {
            return "El operador 2 NO tiene jornadas registradas para el rango de fechas seleccionado";
        }
        if (prgTcOperador1List == null || prgTcOperador1List.isEmpty()) {
            return "El operador 1 NO tiene servicios asignados para el rango de fechas seleccionado";
        }
        if (prgTcOperador2List == null || prgTcOperador2List.isEmpty()) {
            return "El operador 2 NO tiene servicios asignados para el rango de fechas seleccionado";
        }
        return null;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getI_prgSerconMotivo() {
        return i_prgSerconMotivo;
    }

    public void setI_prgSerconMotivo(Integer i_prgSerconMotivo) {
        this.i_prgSerconMotivo = i_prgSerconMotivo;
    }

    public String getCodOperador1() {
        return codOperador1;
    }

    public void setCodOperador1(String codOperador1) {
        this.codOperador1 = codOperador1;
    }

    public String getCodOperador2() {
        return codOperador2;
    }

    public void setCodOperador2(String codOperador2) {
        this.codOperador2 = codOperador2;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<PrgSerconMotivo> getPrgSerconMotivoList() {
        return prgSerconMotivoList;
    }

    public void setPrgSerconMotivoList(List<PrgSerconMotivo> prgSerconMotivoList) {
        this.prgSerconMotivoList = prgSerconMotivoList;
    }

    public List<PrgSercon> getPrgSerconOperador1List() {
        return prgSerconOperador1List;
    }

    public void setPrgSerconOperador1List(List<PrgSercon> prgSerconOperador1List) {
        this.prgSerconOperador1List = prgSerconOperador1List;
    }

    public List<PrgSercon> getPrgSerconOperador2List() {
        return prgSerconOperador2List;
    }

    public void setPrgSerconOperador2List(List<PrgSercon> prgSerconOperador2List) {
        this.prgSerconOperador2List = prgSerconOperador2List;
    }

    public List<PrgTc> getPrgTcOperador1List() {
        return prgTcOperador1List;
    }

    public void setPrgTcOperador1List(List<PrgTc> prgTcOperador1List) {
        this.prgTcOperador1List = prgTcOperador1List;
    }

    public List<PrgTc> getPrgTcOperador2List() {
        return prgTcOperador2List;
    }

    public void setPrgTcOperador2List(List<PrgTc> prgTcOperador2List) {
        this.prgTcOperador2List = prgTcOperador2List;
    }

    public Empleado getEmpleado1() {
        return empleado1;
    }

    public void setEmpleado1(Empleado empleado1) {
        this.empleado1 = empleado1;
    }

    public Empleado getEmpleado2() {
        return empleado2;
    }

    public void setEmpleado2(Empleado empleado2) {
        this.empleado2 = empleado2;
    }

}
