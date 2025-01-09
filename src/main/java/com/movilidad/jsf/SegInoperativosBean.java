package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.SegActividadHabilitacionFacadeLocal;
import com.movilidad.ejb.SegGestionaHabilitacionFacadeLocal;
import com.movilidad.ejb.SegInoperativosFacadeLocal;
import com.movilidad.ejb.SegMedioReportaFacadeLocal;
import com.movilidad.ejb.SegTipoConductaFacadeLocal;
import com.movilidad.ejb.SegTipoIncumplimientoFacadeLocal;
import com.movilidad.ejb.SegTipoRespuestaEnteFacadeLocal;
import com.movilidad.ejb.SegTipoSancionFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Accidente;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoEstado;
import com.movilidad.model.SegActividadHabilitacion;
import com.movilidad.model.SegActividadInoperativos;
import com.movilidad.model.SegGestionaHabilitacion;
import com.movilidad.model.SegInoperativos;
import com.movilidad.model.SegMedioReporta;
import com.movilidad.model.SegTipoConducta;
import com.movilidad.model.SegTipoIncumplimiento;
import com.movilidad.model.SegTipoRespuestaEnte;
import com.movilidad.model.SegTipoSancion;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Jesús Jiménez
 */
@Named(value = "segInoperativosBean")
@ViewScoped
public class SegInoperativosBean implements Serializable {

    @EJB
    private SegInoperativosFacadeLocal segInoperativosEjb;
    @EJB
    private EmpleadoFacadeLocal emplEjb;
    @EJB
    private SegTipoSancionFacadeLocal tipoSancionjb;
    @EJB
    private SegTipoIncumplimientoFacadeLocal tipoIncumpliEjb;
    @EJB
    private SegTipoConductaFacadeLocal tipoConductaEjb;
    @EJB
    private SegTipoRespuestaEnteFacadeLocal tipoRsptEnteEjb;
    @EJB
    private SegMedioReportaFacadeLocal medioReportaEjb;
    @EJB
    private SegActividadHabilitacionFacadeLocal actividadesEjb;
    @EJB
    private SegGestionaHabilitacionFacadeLocal gestionaHabilitaEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private ConfigEmpresaFacadeLocal confiEmprEJB;

    private Map<String, SegActividadInoperativos> mapaActividadInoperativos;
    private Map<String, String> mapaActividadInoperativosSeleccionados;
    private SegInoperativos segInoperativos;
    private SegInoperativos selected;
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();
    private int idSegTipoSancion;
    private int idSegTipoIncumplimiento;
    private int idSegTipoConducta;
    private int idSegMedioReporta;
    private int idSegTipoRespuestaEnte;
    private int idSegGestionaHabilitacion;
    private int idEmpleado;
    private int idVehiculo;
    private Date fechaEvento;
    private Date fechaNotificacion;
    private Date fechaSancionIndefinida;
    private Date fechaSancion;
    private Date fechaHabilitacion;
    private int diasInoperativos;
    private String quienReporta;
    private String quienSeNotifico;
    private String calificaionSegVial;
    private Date primerafechaEnvioSoporteEnte;
    private Date fechaCitaEnte;
    private Date fechaRespuestaEnte;
    private Date segundafechaEnvioSoporteEnte;
    private Date mes;
    private Integer codigoTransMi;
    private String codigoVehiculo;
    private boolean isEditing;
    private boolean flagRsptEnte;
    private Integer estadoEmpleado;

    private Empleado empleado;
    private Vehiculo vehiculo;

    private List<SegInoperativos> lstSegInoperativoses;
    private List<SegTipoSancion> lstTiposSancion;
    private List<SegTipoIncumplimiento> lstTiposIncumplimiento;
    private List<SegTipoConducta> lstTiposConducta;
    private List<SegTipoRespuestaEnte> lstTiposRsptEnte;
    private List<SegMedioReporta> lstMediosReporta;
    private List<SegActividadHabilitacion> lstActividades;
    private List<SegGestionaHabilitacion> lstGestionaHabilitacion;

    private List<String> actividadesSeleccionadas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        lstSegInoperativoses = segInoperativosEjb.findByRangoAndEstadoReg(desde, hasta);
    }

    public void nuevo() {
        idSegTipoSancion = 0;
        idSegTipoIncumplimiento = 0;
        idSegTipoConducta = 0;
        idSegMedioReporta = 0;
        idSegGestionaHabilitacion = 0;
        idSegTipoRespuestaEnte = 0;
        idEmpleado = 0;
        idVehiculo = 0;
        fechaEvento = MovilidadUtil.fechaCompletaHoy();
        fechaNotificacion = null;
        fechaSancionIndefinida = null;
        fechaSancion = null;
        fechaHabilitacion = null;
        diasInoperativos = 0;
        quienReporta = "";
        quienSeNotifico = "";
        calificaionSegVial = "";
        primerafechaEnvioSoporteEnte = null;
        fechaCitaEnte = null;
        fechaRespuestaEnte = null;
        segundafechaEnvioSoporteEnte = null;
        codigoTransMi = null;
        mes = null;
        codigoVehiculo = "";
        empleado = null;
        vehiculo = null;
        segInoperativos = new SegInoperativos();
        cargarTiposSancion(false);
        cargarTiposIncumplimiento(false);
        cargarTiposConducta(false);
        cargarMediosReporta(false);
        cargarTiposRsptEnte(false);
        cargarActividades(false);
        cargarGestionaHabilitacion(false);
        getActividadesSeleccionadasSingleton();
        cargarMapa();
        selected = null;
        isEditing = false;
        flagRsptEnte = true;
        estadoEmpleado = null;
    }

    public void onRowSelect(SelectEvent param) {
        setSelected((SegInoperativos) param.getObject());
    }

    public void rowUnselect() {
        selected = null;
    }

    public void editar() {
        if (selected == null) {
            MovilidadUtil.addErrorMessage("No se ha selecionado un registro en la tabla.");
            return;
        }
        idSegTipoSancion = selected.getIdSegTipoSancion() != null
                ? selected.getIdSegTipoSancion().getIdSegTipoSancion() : 0;
        idSegTipoIncumplimiento = selected.getIdSegTipoIncumplimiento() != null
                ? selected.getIdSegTipoIncumplimiento().getIdSegTipoIncumplimiento() : 0;
        idSegTipoConducta = selected.getIdSegTipoConducta() != null
                ? selected.getIdSegTipoConducta().getIdSegTipoConducta() : 0;
        idSegMedioReporta = selected.getIdSegMedioReporta() != null
                ? selected.getIdSegMedioReporta().getIdSegMedioReporta() : 0;
        idSegGestionaHabilitacion = selected.getIdSegGestionaHabilitacion() != null
                ? selected.getIdSegGestionaHabilitacion().getIdSegGestionaHabilitacion() : 0;
        idEmpleado = selected.getIdEmpleado() != null
                ? selected.getIdEmpleado().getIdEmpleado() : 0;
        idVehiculo = selected.getIdVehiculo() != null
                ? selected.getIdVehiculo().getIdVehiculo() : 0;
        idSegTipoRespuestaEnte = selected.getIdSegTipoRespuestaEnte() != null
                ? selected.getIdSegTipoRespuestaEnte().getIdSegTipoRespuestaEnte() : 0;
        fechaEvento = selected.getFechaEvento() != null
                ? selected.getFechaEvento() : null;
        fechaNotificacion = selected.getFechaNotificacion() != null
                ? selected.getFechaNotificacion() : null;
        fechaSancionIndefinida = selected.getFechaSancionIndefinida() != null
                ? selected.getFechaSancionIndefinida() : null;
        fechaSancion = selected.getFechaSancion() != null
                ? selected.getFechaSancion() : null;
        fechaHabilitacion = selected.getFechaHabilitacion() != null
                ? selected.getFechaHabilitacion() : null;
        diasInoperativos = selected.getDiasInoperativos();
        quienReporta = selected.getQuienReporta() != null
                ? selected.getQuienReporta() : "";
        quienSeNotifico = selected.getQuienSeNotifico() != null
                ? selected.getQuienSeNotifico() : "";
        calificaionSegVial = selected.getCalificaionSegVial() != null
                ? selected.getCalificaionSegVial() : "";
        primerafechaEnvioSoporteEnte = selected.getPrimerafechaEnvioSoporteEnte() != null
                ? selected.getPrimerafechaEnvioSoporteEnte() : null;
        fechaCitaEnte = selected.getFechaCitaEnte() != null
                ? selected.getFechaCitaEnte() : null;
        fechaRespuestaEnte = selected.getFechaRespuestaEnte() != null
                ? selected.getFechaRespuestaEnte() : null;
        segundafechaEnvioSoporteEnte = selected.getSegundafechaEnvioSoporteEnte() != null
                ? selected.getSegundafechaEnvioSoporteEnte() : null;
        codigoTransMi = selected.getIdEmpleado() != null
                ? selected.getIdEmpleado().getCodigoTm() : 0;
        if (codigoTransMi > 0) {
            empleado = selected.getIdEmpleado();
        }
        codigoVehiculo = selected.getIdVehiculo() != null
                ? selected.getIdVehiculo().getCodigo() : "";
        if (!codigoVehiculo.isEmpty()) {
            vehiculo = selected.getIdVehiculo();
        }
        segInoperativos = selected;
        flagRsptEnte = selected.getIdSegTipoRespuestaEnte() != null
                ? (selected.getIdSegTipoRespuestaEnte().getReqFecha() == 0) : true;
        mes = selected.getMes() != null
                ? selected.getMes() : null;
        cargarTiposSancion(false);
        cargarTiposIncumplimiento(false);
        cargarTiposConducta(false);
        cargarMediosReporta(false);
        cargarTiposRsptEnte(false);
        cargarActividades(false);
        cargarGestionaHabilitacion(false);
        getActividadesSeleccionadasSingleton();
        cargarMapa();
        isEditing = true;
        // estado empleado
        estadoEmpleado = selected.getIdEmpleado() != null
                ? selected.getIdEmpleado().getIdEmpleadoEstado().getIdEmpleadoEstado() : null;
    }

    public void evaluarValor() {
        lstTiposRsptEnte.stream().filter((obj)
                -> (obj.getIdSegTipoRespuestaEnte()
                        .equals(idSegTipoRespuestaEnte)))
                .forEachOrdered((obj) -> {
                    flagRsptEnte = obj.getReqFecha() == 0;
                });
    }

    private List<String> getActividadesSeleccionadasSingleton() {
        if (actividadesSeleccionadas == null) {
            actividadesSeleccionadas = new ArrayList<>();
        }
        actividadesSeleccionadas.clear();
        return actividadesSeleccionadas;
    }

    public void cargarVehiculo() {
        if (codigoVehiculo.isEmpty()) {
            MovilidadUtil.addErrorMessage("Digite un código valido");
            return;
        }
        vehiculo = vehiculoEjb.findByCodigo(codigoVehiculo);
        if (vehiculo != null) {
            MovilidadUtil.addSuccessMessage("Vehículo Cargado: " + vehiculo.getCodigo() + " " + vehiculo.getPlaca());
        } else {
            MovilidadUtil.addErrorMessage("No existe vehiculo con el código ingresado.");
        }
    }

    public void cargarTiposSancion(boolean goToDb) {
        if (lstTiposSancion == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstTiposSancion = tipoSancionjb.findAllByEstadoReg();
        }
    }

    public void cargarTiposIncumplimiento(boolean goToDb) {
        if (lstTiposIncumplimiento == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstTiposIncumplimiento = tipoIncumpliEjb.findAllByEstadoReg();
        }
    }

    public void cargarTiposConducta(boolean goToDb) {
        if (lstTiposConducta == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstTiposConducta = tipoConductaEjb.findAllByEstadoReg();
        }
    }

    public void cargarTiposRsptEnte(boolean goToDb) {
        if (lstTiposRsptEnte == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstTiposRsptEnte = tipoRsptEnteEjb.findAllByEstadoReg();
        }
    }

    public void cargarMediosReporta(boolean goToDb) {
        if (lstMediosReporta == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstMediosReporta = medioReportaEjb.findAllByEstadoReg();
        }
    }

    public void cargarActividades(boolean goToDb) {
        if (lstActividades == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstActividades = actividadesEjb.findAllByEstadoReg();
        }
    }

    public void cargarGestionaHabilitacion(boolean goToDb) {
        if (lstGestionaHabilitacion == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstGestionaHabilitacion = gestionaHabilitaEjb.findAllByEstadoReg();
        }
    }

    public void cargarEmpleado() {
        if (codigoTransMi == null) {
            MovilidadUtil.addErrorMessage("Digite un codigo válido");
            return;
        }
        empleado = emplEjb.findByCodigoTM(codigoTransMi);
        if (empleado != null) {
            MovilidadUtil.addSuccessMessage("Empleado Cargado: " + empleado.getNombres() + " " + empleado.getApellidos());
        } else {
            MovilidadUtil.addErrorMessage("No existe empleado con el código ingresado.");
        }
    }

    public String toString(Integer id) {
        return Integer.toString(id);
    }

    public void guardar() throws ParseException {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() throws ParseException {
        if (validarDatos()) {
            return;
        }
        loadDataToObject();
        loadActividadesToObject();
        loadMapaActividadInoperativosSeleccionados();
        loadTrueSelected();
        cambiarEstadoOperativo();
        if (isEditing) {
            segInoperativos.setModificado(MovilidadUtil.fechaCompletaHoy());
            segInoperativosEjb.edit(segInoperativos);
            MovilidadUtil.hideModal("wv_inoperativos");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            segInoperativos.setEstadoReg(0);
            segInoperativos.setCreado(MovilidadUtil.fechaCompletaHoy());
            segInoperativosEjb.create(segInoperativos);
            cambiarEstadoEmpleado();
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            MovilidadUtil.hideModal("wv_inoperativos");
        }
        consultar();
    }

    private void cambiarEstadoEmpleado() {
        ConfigEmpresa config = confiEmprEJB.findByLlave("stateEmpl");
        if (config != null) {
            int resp = emplEjb.actualizarEstadoEmpleado(empleado.getIdEmpleado(), Integer.parseInt(config.getValor()));
            System.out.println("Respuesta->" + resp);
        }
    }

    public void cargarMapa() {
        mapaActividadInoperativos = new HashMap<>();
        if (segInoperativos.getSegActividadInoperativosList() != null) {
            for (SegActividadInoperativos obj : segInoperativos.getSegActividadInoperativosList()) {
                mapaActividadInoperativos.put(String.valueOf(obj.getIdActividadHabilitacion().getIdSegActividadHabilitacion()), obj);
                actividadesSeleccionadas.add(String.valueOf(obj.getIdActividadHabilitacion().getIdSegActividadHabilitacion()));
            }
        }
    }

    public void loadActividadesToObject() {
        if (segInoperativos.getSegActividadInoperativosList() == null) {
            segInoperativos.setSegActividadInoperativosList(new ArrayList());
        }
        for (String id : actividadesSeleccionadas) {
            if (mapaActividadInoperativos.get(id) == null) {
                segInoperativos.getSegActividadInoperativosList()
                        .add(builObject(Integer.parseInt(id)));
            }
        }
    }

    private void loadMapaActividadInoperativosSeleccionados() {
        mapaActividadInoperativosSeleccionados = new HashMap<>();
        actividadesSeleccionadas.forEach((valor) -> {
            mapaActividadInoperativosSeleccionados.put(valor, valor);
        });
    }

    /**
     * Carga las verdaderas actividades seleccionadas por el usuario durante una
     * edición
     */
    private void loadTrueSelected() {
        List<SegActividadInoperativos> listAux = new ArrayList<>();
        listAux.addAll(segInoperativos.getSegActividadInoperativosList());
        listAux.stream().filter((obj) -> (mapaActividadInoperativosSeleccionados.get(
                String.valueOf(obj.getIdActividadHabilitacion()
                        .getIdSegActividadHabilitacion())) == null)).forEachOrdered((obj) -> {
            segInoperativos.getSegActividadInoperativosList().remove(obj);
        });
    }

    public String retornarStringActividades(SegInoperativos obj) {
        String actividades = "";
        if (obj.getSegActividadInoperativosList() != null) {
            for (SegActividadInoperativos item : obj.getSegActividadInoperativosList()) {
                actividades = actividades + item.getIdActividadHabilitacion().getNombre() + " / ";
            }
        }
        return actividades;
    }

    private SegActividadInoperativos builObject(int idActividad) {
        SegActividadInoperativos obj = new SegActividadInoperativos();
        obj.setIdSegInoperativos(segInoperativos);
        obj.setIdActividadHabilitacion(new SegActividadHabilitacion(idActividad));
        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
        obj.setUsername(user.getUsername());
        obj.setEstadoReg(0);
        return obj;
    }

    public void loadDataToObject() {
        segInoperativos.setUsername(user.getUsername());
        segInoperativos.setFechaEvento(fechaEvento);
        segInoperativos.setIdEmpleado(empleado);
        segInoperativos.setIdVehiculo(vehiculo);
        segInoperativos.setFechaNotificacion(fechaNotificacion);
        segInoperativos.setFechaSancion(fechaSancion);
        segInoperativos.setFechaSancionIndefinida(fechaSancionIndefinida);
        segInoperativos.setFechaHabilitacion(fechaHabilitacion);
        segInoperativos.setPrimerafechaEnvioSoporteEnte(primerafechaEnvioSoporteEnte);
        segInoperativos.setSegundafechaEnvioSoporteEnte(segundafechaEnvioSoporteEnte);
        segInoperativos.setDiasInoperativos(diasInoperativos);
        segInoperativos.setIdSegTipoSancion(idSegTipoSancion == 0
                ? null : new SegTipoSancion(idSegTipoSancion));
        segInoperativos.setIdSegTipoIncumplimiento(idSegTipoIncumplimiento == 0
                ? null : new SegTipoIncumplimiento(idSegTipoIncumplimiento));
        segInoperativos.setIdSegTipoConducta(idSegTipoConducta == 0
                ? null : new SegTipoConducta(idSegTipoConducta));
        segInoperativos.setIdSegTipoRespuestaEnte(idSegTipoRespuestaEnte == 0
                ? null : new SegTipoRespuestaEnte(idSegTipoRespuestaEnte));
        segInoperativos.setIdSegMedioReporta(idSegMedioReporta == 0
                ? null : new SegMedioReporta(idSegMedioReporta));
        segInoperativos.setFechaRespuestaEnte(fechaRespuestaEnte);
        segInoperativos.setFechaCitaEnte(fechaCitaEnte);
        segInoperativos.setQuienSeNotifico(quienSeNotifico);
        segInoperativos.setQuienReporta(quienReporta);
        segInoperativos.setCalificaionSegVial(calificaionSegVial);
        segInoperativos.setIdSegGestionaHabilitacion(idSegGestionaHabilitacion == 0
                ? null : new SegGestionaHabilitacion(idSegGestionaHabilitacion));
        segInoperativos.setMes(mes);
    }

    public boolean validarDatos() throws ParseException {
        if (MovilidadUtil.fechasIgualMenorMayor(fechaEvento, MovilidadUtil.fechaHoy(), false) > 1) {
            MovilidadUtil.addErrorMessage("La Fecha Evento no puede ser posterior al día de hoy,");
            return true;
        }
        if (empleado == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado un empleado");
            return true;
        }
        if (isEditing) {
            if (segInoperativosEjb.findByFechaEventoAndIdEmpleado(idEmpleado, fechaEvento, selected.getIdSegInoperativos()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un evento para la fecha con el empleado selecionado");
                return true;
            }
        } else {
            if (segInoperativosEjb.findByFechaEventoAndIdEmpleado(empleado.getIdEmpleado(), fechaEvento, 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un evento para la fecha con el empleado selecionado");
                return true;
            }
        }
        if (estadoEmpleado != null && estadoEmpleado.equals(1)) {
            if (Util.isStringNullOrEmpty(calificaionSegVial)) {
                MovilidadUtil.addErrorMessage("Calificación de Seguridad vial es requerida para activar al operador");
                return true;
            }
        }
        return false;
    }

    public void gestionarInoperatividadByAccidente(Accidente acc) {
        if (acc.getIdEmpleado() == null) {
            MovilidadUtil.addSuccessMessage("Error");
            return;
        }
        selected = segInoperativosEjb.findByFechaEventoAndIdEmpleado(acc.getIdEmpleado().getIdEmpleado(), acc.getFechaAcc(), 0);
        if (selected != null) {
            editar();
            calcularDiasInoperativos();
        } else {
            selected = segInoperativosEjb
                    .findByFechaSancionBetweeHabilitacionnAndIdEmpleado(acc.getIdEmpleado().getIdEmpleado(),
                            acc.getFechaAcc());
            if (selected != null) {
                editar();
                calcularDiasInoperativos();
            } else {
                nuevo();
                fechaEvento = acc.getFechaAcc();
                fechaNotificacion = Util.intanceDateFromDate(acc.getFechaAcc());
                fechaSancion = Util.intanceDateFromDate(acc.getFechaAcc());
                codigoVehiculo = acc.getIdVehiculo() != null ? acc.getIdVehiculo().getCodigo() : null;
                codigoTransMi = acc.getIdEmpleado().getCodigoTm();
                empleado = acc.getIdEmpleado();
                vehiculo = acc.getIdVehiculo();
                calcularDiasInoperativos();
            }
        }
    }

    private void calcularDiasInoperativos() {
        if (fechaSancion != null && fechaHabilitacion != null) {
            diasInoperativos = MovilidadUtil.getDiferenciaDia(fechaSancion, fechaHabilitacion);
        }
    }

    private void cambiarEstadoOperativo() {
        if (selected != null && selected.getIdEmpleado() != null && estadoEmpleado != null) {
            // empleado_estado 1 es activo
            selected.getIdEmpleado().setIdEmpleadoEstado(new EmpleadoEstado(estadoEmpleado));
            emplEjb.edit(selected.getIdEmpleado());
        }
    }

    public SegInoperativos getSegInoperativos() {
        return segInoperativos;
    }

    public void setSegInoperativos(SegInoperativos segInoperativos) {
        this.segInoperativos = segInoperativos;
    }

    public SegInoperativos getSelected() {
        return selected;
    }

    public void setSelected(SegInoperativos selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<SegInoperativos> getLstSegInoperativoses() {
        return lstSegInoperativoses;
    }

    public void setLstSegInoperativoses(List<SegInoperativos> lstSegInoperativoses) {
        this.lstSegInoperativoses = lstSegInoperativoses;
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

    public int getIdSegTipoSancion() {
        return idSegTipoSancion;
    }

    public void setIdSegTipoSancion(int idSegTipoSancion) {
        this.idSegTipoSancion = idSegTipoSancion;
    }

    public int getIdSegTipoIncumplimiento() {
        return idSegTipoIncumplimiento;
    }

    public void setIdSegTipoIncumplimiento(int idSegTipoIncumplimiento) {
        this.idSegTipoIncumplimiento = idSegTipoIncumplimiento;
    }

    public int getIdSegTipoConducta() {
        return idSegTipoConducta;
    }

    public void setIdSegTipoConducta(int idSegTipoConducta) {
        this.idSegTipoConducta = idSegTipoConducta;
    }

    public int getIdSegMedioReporta() {
        return idSegMedioReporta;
    }

    public void setIdSegMedioReporta(int idSegMedioReporta) {
        this.idSegMedioReporta = idSegMedioReporta;
    }

    public int getIdSegGestionaHabilitacion() {
        return idSegGestionaHabilitacion;
    }

    public void setIdSegGestionaHabilitacion(int idSegGestionaHabilitacion) {
        this.idSegGestionaHabilitacion = idSegGestionaHabilitacion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public Date getFechaSancionIndefinida() {
        return fechaSancionIndefinida;
    }

    public void setFechaSancionIndefinida(Date fechaSancionIndefinida) {
        this.fechaSancionIndefinida = fechaSancionIndefinida;
    }

    public Date getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(Date fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    public int getDiasInoperativos() {
        return diasInoperativos;
    }

    public void setDiasInoperativos(int diasInoperativos) {
        this.diasInoperativos = diasInoperativos;
    }

    public String getQuienReporta() {
        return quienReporta;
    }

    public void setQuienReporta(String quienReporta) {
        this.quienReporta = quienReporta;
    }

    public String getQuienSeNotifico() {
        return quienSeNotifico;
    }

    public void setQuienSeNotifico(String quienSeNotifico) {
        this.quienSeNotifico = quienSeNotifico;
    }

    public String getCalificaionSegVial() {
        return calificaionSegVial;
    }

    public void setCalificaionSegVial(String calificaionSegVial) {
        this.calificaionSegVial = calificaionSegVial;
    }

    public Date getFechaCitaEnte() {
        return fechaCitaEnte;
    }

    public void setFechaCitaEnte(Date fechaCitaEnte) {
        this.fechaCitaEnte = fechaCitaEnte;
    }

    public Date getFechaRespuestaEnte() {
        return fechaRespuestaEnte;
    }

    public void setFechaRespuestaEnte(Date fechaRespuestaEnte) {
        this.fechaRespuestaEnte = fechaRespuestaEnte;
    }

    public Date getPrimerafechaEnvioSoporteEnte() {
        return primerafechaEnvioSoporteEnte;
    }

    public void setPrimerafechaEnvioSoporteEnte(Date primerafechaEnvioSoporteEnte) {
        this.primerafechaEnvioSoporteEnte = primerafechaEnvioSoporteEnte;
    }

    public Date getSegundafechaEnvioSoporteEnte() {
        return segundafechaEnvioSoporteEnte;
    }

    public void setSegundafechaEnvioSoporteEnte(Date segundafechaEnvioSoporteEnte) {
        this.segundafechaEnvioSoporteEnte = segundafechaEnvioSoporteEnte;
    }

    public Date getFechaSancion() {
        return fechaSancion;
    }

    public void setFechaSancion(Date fechaSancion) {
        this.fechaSancion = fechaSancion;
    }

    public Integer getCodigoTransMi() {
        return codigoTransMi;
    }

    public void setCodigoTransMi(Integer codigoTransMi) {
        this.codigoTransMi = codigoTransMi;
    }

    public String getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(String codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public List<SegTipoSancion> getLstTiposSancion() {
        return lstTiposSancion;
    }

    public void setLstTiposSancion(List<SegTipoSancion> lstTiposSancion) {
        this.lstTiposSancion = lstTiposSancion;
    }

    public List<SegTipoIncumplimiento> getLstTiposIncumplimiento() {
        return lstTiposIncumplimiento;
    }

    public void setLstTiposIncumplimiento(List<SegTipoIncumplimiento> lstTiposIncumplimiento) {
        this.lstTiposIncumplimiento = lstTiposIncumplimiento;
    }

    public List<SegTipoConducta> getLstTiposConducta() {
        return lstTiposConducta;
    }

    public void setLstTiposConducta(List<SegTipoConducta> lstTiposConducta) {
        this.lstTiposConducta = lstTiposConducta;
    }

    public int getIdSegTipoRespuestaEnte() {
        return idSegTipoRespuestaEnte;
    }

    public void setIdSegTipoRespuestaEnte(int idSegTipoRespuestaEnte) {
        this.idSegTipoRespuestaEnte = idSegTipoRespuestaEnte;
    }

    public List<SegTipoRespuestaEnte> getLstTiposRsptEnte() {
        return lstTiposRsptEnte;
    }

    public void setLstTiposRsptEnte(List<SegTipoRespuestaEnte> lstTiposRsptEnte) {
        this.lstTiposRsptEnte = lstTiposRsptEnte;
    }

    public boolean isFlagRsptEnte() {
        return flagRsptEnte;
    }

    public void setFlagRsptEnte(boolean flagRsptEnte) {
        this.flagRsptEnte = flagRsptEnte;
    }

    public List<SegMedioReporta> getLstMediosReporta() {
        return lstMediosReporta;
    }

    public void setLstMediosReporta(List<SegMedioReporta> lstMediosReporta) {
        this.lstMediosReporta = lstMediosReporta;
    }

    public List<SegActividadHabilitacion> getLstActividades() {
        return lstActividades;
    }

    public void setLstActividades(List<SegActividadHabilitacion> lstActividades) {
        this.lstActividades = lstActividades;
    }

    public List<String> getActividadesSeleccionadas() {
        return actividadesSeleccionadas;
    }

    public void setActividadesSeleccionadas(List<String> actividadesSeleccionadas) {
        this.actividadesSeleccionadas = actividadesSeleccionadas;
    }

    public List<SegGestionaHabilitacion> getLstGestionaHabilitacion() {
        return lstGestionaHabilitacion;
    }

    public void setLstGestionaHabilitacion(List<SegGestionaHabilitacion> lstGestionaHabilitacion) {
        this.lstGestionaHabilitacion = lstGestionaHabilitacion;
    }

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public Integer getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(Integer estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }

}
