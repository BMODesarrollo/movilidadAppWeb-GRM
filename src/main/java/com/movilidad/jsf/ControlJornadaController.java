package com.movilidad.jsf;

import com.aja.jornada.controller.JornadaFlexible;
import com.aja.jornada.dto.ErrorPrgSercon;
import com.aja.jornada.model.EmpleadoLiqUtil;
import com.aja.jornada.model.GopUnidadFuncionalLiqUtil;
import com.aja.jornada.model.PrgSerconLiqUtil;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgSerconMotivoFacadeLocal;
import com.movilidad.model.ConfigControlJornada;
import com.movilidad.model.Empleado;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgSerconDet;
import com.movilidad.model.PrgSerconMotivo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.LiquidacionSercon;
import com.movilidad.util.beans.PrgSerconObj;
import com.movilidad.utils.CagarJornadaGenericaException;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.UtilJornada;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigControlJornada;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import liquidadorjornada.Jornada;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author César Mercado
 */
@Named(value = "controlJornada")
@ViewScoped
public class ControlJornadaController implements Serializable {

    public ControlJornadaController() {
    }

    @EJB
    private PrgSerconMotivoFacadeLocal serconMotivoFacadeLocal;

    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;

    @EJB
    private EmpleadoFacadeLocal emplEJB;

    @Inject
    private LiqJornadaJSFManagedBean LiqJornadaJSFMB;

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    @Inject
    private CalcularMasivoJSFManagedBean calcularMasivoBean;

    private List<PrgSercon> prglist = new ArrayList<>();
    private List<PrgSercon> prglistSelected = new ArrayList<>();
    private List<PrgSerconMotivo> prgSerconMotivoList;

    List<PrgSerconDet> listPrgSerconDet;
    private List<Empleado> listaEmpleados = new ArrayList<>();
    private PrgSercon prgSercon = null;
    private PrgSercon prgSerconNew = null;

    private Empleado empl;
    private Date fechaDesde = MovilidadUtil.fechaHoy();
    private Date fechaHasta = MovilidadUtil.fechaHoy();
    private Date fechaDesdeBM = MovilidadUtil.fechaHoy();
    private Date fechaHastaBM = MovilidadUtil.fechaHoy();
    private boolean b_autoriza = false;
    private boolean b_genera = false;
    private boolean b_generaDelete = false;
    private boolean b_liquida = false;
    private boolean b_controlLiquida = false;
    private boolean b_controlAutoriza = false;
    private boolean b_controlSubirArchivo = false;
    private boolean b_novFromFile = false;
    private Integer i_prgSerconMotivo = 0;
    private String rol = "";
    private String observacionesBM = "";
    private String totalHorasExtrasSmnal;

    private String realTimeOrigin;
    private String realTimeDestiny;

    private String codigoTransMi;
    private String nombreEmpleado;
    private UploadedFile uploadedFile;
    private UploadedFile archivoNovedades;
    private JornadaFlexible js;

    private JornadaFlexible getINSTANCEJS() {
        if (js == null) {
            js = new JornadaFlexible();
        }
        return js;
    }
    /**
     * Usuario en sesión
     */
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        if (MovilidadUtil.validarUrl("/controlJornada/ControlJornada")) {
            consultar();
        }
        validarRol();
    }

    public void consultarMotivos() {
        prgSerconMotivoList = serconMotivoFacadeLocal.findAllEstadoRegistro();
    }

    /**
     * Responsable de cargar la data de jornadas para ser presentada en la vista
     * principal del modulo.
     *
     * La variables fechaDesde y fechaHasta, son seleccionadas por el usurio
     * desde la vista principla.
     *
     * Se invoca desde ControlJornada.
     *
     * @throws java.text.ParseException
     */
    public void consultar() {
        if (MovilidadUtil.dateSinHora(fechaDesde).before(MovilidadUtil.dateSinHora(fechaHasta))
                || MovilidadUtil.dateSinHora(fechaDesde).equals(MovilidadUtil.dateSinHora(fechaHasta))) {
            prglist = prgSerconEJB.getPrgSerconByDateAndUnidadFunc(fechaDesde,
                    fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        }
    }

    /**
     * Preparar variables para la gestion de borrar jornada por rango de fecha o
     * masivo.
     *
     * Se invoca desde la vista ControlJornada.
     */
    public void preBorrarMasivo() {
        empl = null;
        i_prgSerconMotivo = 0;
        fechaDesdeBM = MovilidadUtil.fechaHoy();
        fechaHastaBM = MovilidadUtil.fechaHoy();
        consultar();
        consultarMotivos();
    }

    /**
     * Preparar variables para la gestión de ajustar jornada.
     *
     * Se invoca desde la vista ControlJornada.
     */
    public void preAjustar() {
        setPrgSercon(prgSerconEJB.find(prgSercon.getIdPrgSercon()));
        if (prgSercon.getNominaBorrada() == 1) {
            prgSercon = null;
            return;
        }
        if (prgSercon.getLiquidado() != null && prgSercon.getLiquidado() == 1) {
            prgSercon = null;
            return;
        }
        if (prgSercon.getHiniTurno2() == null) {
            prgSercon.setHiniTurno2(Jornada.hr_cero);
        }
        if (prgSercon.getHfinTurno2() == null) {
            prgSercon.setHfinTurno2(Jornada.hr_cero);
        }
        if (prgSercon.getHiniTurno3() == null) {
            prgSercon.setHiniTurno3(Jornada.hr_cero);
        }
        if (prgSercon.getHfinTurno3() == null) {
            prgSercon.setHfinTurno3(Jornada.hr_cero);
        }
//        if (rol.equals("ROLE_TC")) {
//            if (JornadaUtil.esPorPartes(prgSercon)) {
//                MovilidadUtil.addErrorMessage("Esta jornada es por partes, Usted no puede ajustarla");
//                return;
//            }
//        }
        if (prgSercon.getIdPrgSerconMotivo() != null) {
            i_prgSerconMotivo = prgSercon.getIdPrgSerconMotivo().getIdPrgSerconMotivo();
        }
        if (prgSercon.getRealTimeOrigin() == null || prgSercon.getAutorizado() == null
                || (prgSercon.getAutorizado() != null && prgSercon.getAutorizado() == 0)) {
            prgSercon.setRealTimeOrigin(prgSercon.getTimeOrigin());
            prgSercon.setRealTimeDestiny(prgSercon.getTimeDestiny());
            prgSercon.setRealHiniTurno2(prgSercon.getHiniTurno2());
            prgSercon.setRealHfinTurno2(prgSercon.getHfinTurno2());
            prgSercon.setRealHiniTurno3(prgSercon.getHiniTurno3());
            prgSercon.setRealHfinTurno3(prgSercon.getHfinTurno3());
        }
        cargarTotalHorasExtrasSmanal(prgSercon.getFecha(), prgSercon.getIdEmpleado().getIdEmpleado());
        consultarMotivos();
        /**
         * Abrir ventana modal donde se hace la gestión de ajuste de jornada.
         */
        MovilidadUtil.openModal("controlJornadaDialog");
    }

    /**
     * Preparar variables para la gestion de crear turnos.
     *
     * Se invoca desde la vista ControlJornada.
     */
    public void preCrearTurno() {
        i_prgSerconMotivo = 0;
        codigoTransMi = "";
        nombreEmpleado = "";
        empl = null;
        prgSerconNew = new PrgSercon();
        prgSerconNew.setFecha(MovilidadUtil.fechaCompletaHoy());
        prgSerconNew.setHiniTurno2(Jornada.hr_cero);
        prgSerconNew.setHfinTurno2(Jornada.hr_cero);
        prgSerconNew.setHiniTurno3(Jornada.hr_cero);
        prgSerconNew.setHfinTurno3(Jornada.hr_cero);
        prgSerconNew.setRealHiniTurno2(Jornada.hr_cero);
        prgSerconNew.setRealHfinTurno2(Jornada.hr_cero);
        prgSerconNew.setRealHiniTurno3(Jornada.hr_cero);
        prgSerconNew.setRealHfinTurno3(Jornada.hr_cero);
        consultarMotivos();
    }

    /**
     * Resposable de perisitir en BD una jornada, despues de hacer varias
     * validaciones.
     *
     * Se invoca desde la ventana modal crearJornada.
     *
     * @throws ParseException
     */
    public void guardarTurno() throws ParseException {
        if (disableBoton()) {
            MovilidadUtil.addErrorMessage("La fecha no puede ser anterior a la del día de hoy");
            return;
        }
        if (validarHoras(prgSerconNew.getRealTimeOrigin(), prgSerconNew.getRealTimeDestiny())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 1 no puede ser mayor a la hora fin turno 1");
            return;
        }
        if (MovilidadUtil.toSecs(prgSerconNew.getRealTimeOrigin()) > MovilidadUtil.toSecs("23:59:59")) {
            MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59.");
            return;
        }

        if (validarHoras(prgSerconNew.getRealHfinTurno2(), prgSerconNew.getRealHfinTurno2())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 2 no puede ser mayor a la hora fin turno 2");
            return;
        }
        if (validarHoras(prgSerconNew.getRealHfinTurno3(), prgSerconNew.getRealHfinTurno3())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 3 no puede ser mayor a la hora fin turno 3");
            return;
        }
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado el empleado");
            return;
        }
        if (i_prgSerconMotivo == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el motivo");
            return;
        }

        if (prgSerconNew.getFecha() == null) {
            MovilidadUtil.addErrorMessage("Seleccionar una la fecha");
            return;
        }

        if (prgSerconNew.getObservaciones() == null || prgSerconNew.getObservaciones().isEmpty()) {
            MovilidadUtil.addErrorMessage("Digite la observación");
            return;
        }
        String productionTime = timeProductionNew();
        /**
         * Set unidad funcional para la jornada.
         */
        prgSerconNew.setIdGopUnidadFuncional(empl.getIdGopUnidadFuncional());
        prgSerconNew.setIdEmpleado(empl);
        prgSerconNew.setWorkTime(productionTime);
        prgSerconNew.setProductionTime(productionTime);
        prgSerconNew.setTimeOrigin(prgSerconNew.getRealTimeOrigin());
        prgSerconNew.setTimeDestiny(prgSerconNew.getRealTimeDestiny());
        System.out.println("JornadaUtil.esPorPartes(prgSerconNew)->"
                + UtilJornada.esPorPartes(prgSerconNew));
        if (UtilJornada.esPorPartes(prgSerconNew)) {
            prgSerconNew.setRealHiniTurno2(prgSerconNew.getRealHiniTurno2());
            prgSerconNew.setRealHfinTurno2(prgSerconNew.getRealHfinTurno2());
            prgSerconNew.setRealHiniTurno3(prgSerconNew.getRealHiniTurno3());
            prgSerconNew.setRealHfinTurno3(prgSerconNew.getRealHfinTurno3());
        } else {
            prgSerconNew.setRealHiniTurno2(null);
            prgSerconNew.setRealHfinTurno2(null);
            prgSerconNew.setRealHiniTurno3(null);
            prgSerconNew.setRealHfinTurno3(null);
        }
        if (validarTrasnochoMadrugada(prgSerconNew)) {
            MovilidadUtil.addErrorMessage("El turno NO permite respetar las 12 horas de descanso reglamentarias");
            return;
        }
        prgSerconNew.setAutorizado(-1);
        if (UtilJornada.esPorPartes(prgSerconNew)) {

            if (validarHoras(prgSerconNew.getRealHiniTurno2(), prgSerconNew.getRealHfinTurno2())) {
                consultar();
                MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio. Turno 2");
                return;
            }
            if (validarHoras(prgSerconNew.getRealHiniTurno3(), prgSerconNew.getRealHfinTurno3())) {
                consultar();
                MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio. Turno 3");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno2()) > MovilidadUtil.toSecs("23:59:59")) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59. Turno 2");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno3()) > MovilidadUtil.toSecs("23:59:59")) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59. Turno 3");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealTimeDestiny())
                    >= MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno2())
                    && (MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno2()) != 0
                    && MovilidadUtil.toSecs(prgSerconNew.getRealHfinTurno2()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 2 deben ser mayores a las del turno 1");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealHfinTurno2())
                    >= MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno3())
                    && (MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno3()) != 0
                    && MovilidadUtil.toSecs(prgSerconNew.getRealHfinTurno3()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 3 deben ser mayores a las del turno 2");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealTimeDestiny())
                    >= MovilidadUtil.toSecs(prgSerconNew.getHiniTurno3())
                    && (MovilidadUtil.toSecs(prgSerconNew.getHiniTurno3()) != 0
                    && MovilidadUtil.toSecs(prgSerconNew.getHfinTurno3()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 3 deben ser mayores a las del turno 1");
                return;
            }
        }
        /**
         * Rigel GMO fase II
         */
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        /**
         * Validar tipo de liquidación
         */
        if (UtilJornada.tipoLiquidacion()) {//Flexible
            if (MovilidadUtil.toSecs(productionTime) < MovilidadUtil.toSecs(UtilJornada.getMinHrsLaboralesDia())) {
                String msg = "No la jornada no cumple con el minimo de horas laborales, " + UtilJornada.getMinHrsLaboralesDia() + ". Encontrado, " + productionTime;
                if (UtilJornada.getMinHrsLaboralesDiaObj().getRestringe().equals(ConstantsUtil.ON_INT)) {
                    MovilidadUtil.addErrorMessage(msg);
                    return;
                } else {
                    MovilidadUtil.addAdvertenciaMessage(msg);
                }
            }
            if (MovilidadUtil.toSecs(productionTime) > MovilidadUtil.toSecs(UtilJornada.getMaxHrsLaboralesDia())) {
                String mssg = "Se excedió el máximo de horas laborales por día, " + UtilJornada.getMaxHrsLaboralesDia() + ". Encontrado "
                        + productionTime;
                if (UtilJornada.getMaxHrsLaboralesDiaObj().getRestringe().equals(ConstantsUtil.ON_INT)) {
                    MovilidadUtil.addErrorMessage(mssg);
                    return;
                } else {
                    MovilidadUtil.addAdvertenciaMessage(mssg);
                }
            }
            if (validarHorasExtrasFlexible(prgSerconNew, true)) {
                return;
            }
            if (validarMaxHorasExtrasSmanalesFlexible(prgSerconNew, true)) {
                return;
            }
        } else {

            /**
             * Validar que la jornada que se intenta persistir cumpla con el
             * criterio de aceptación de maximo horas extras parametrizadas en
             * configEmpresa.
             */
            if (validarHorasExtras(prgSerconNew)) {
                return;
            }

            if (validarMaxHorasExtrasSmanales(prgSerconNew)) {
                return;
            }
        }

        prgSerconNew.setPrgModificada(1);
        prgSerconNew.setUsername(user.getUsername());
        prgSerconNew.setCreado(MovilidadUtil.fechaCompletaHoy());
        prgSerconNew.setUserGenera(user.getUsername());
        prgSerconNew.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        prgSerconNew.setIdPrgSerconMotivo(new PrgSerconMotivo(i_prgSerconMotivo));

//        prgSerconNew.setIdToStop(new PrgStopPoint(MovilidadUtil.patio()));
//        prgSerconNew.setIdFromStop(new PrgStopPoint(MovilidadUtil.patio()));
        prgSerconNew.setNominaBorrada(0);
        prgSerconNew.setSercon("DP_COLABORADOR");
        prgSerconNew.setLiquidado(0);

        if (prgSerconNew.getProductionTimeReal() == null) {
            prgSerconNew.setProductionTimeReal(prgSerconNew.getProductionTime());
        }

        /**
         * Validar que tipo de rol tiene el usuario que desde persistir la
         * información.
         *
         * Sí el usuario tiene rol ROLE_PROFOP ó ROLE_LIQ, la jornada se
         * persistirá con los atributos de autorización seteados.
         *
         * Sí lo anterior no se cumple, la jornada se persistira con una valor
         * -1 en el atributo autoriza, esto significa que quedará como "Por
         * autorizar"
         *
         */
        if ((rol.equals("ROLE_PROFOP") || rol.equals("ROLE_LIQ"))
                || !UtilJornada.validarCriterioMinJornada(prgSerconNew)) {

            prgSerconNew.setUserAutorizado(user.getUsername());
            prgSerconNew.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
            // (1) uno para autorizar, (0) cero para no autorizar
            prgSerconNew.setAutorizado(1);

            prgSerconNew.setDominicalCompDiurnas(null);
            prgSerconNew.setDominicalCompNocturnas(null);
            if (UtilJornada.tipoLiquidacion()) {
                if (MovilidadUtil.isSunday(prgSerconNew.getFecha())) {
                    List<PrgSerconLiqUtil> preCagarHorasDominicales = getINSTANCEJS().preCagarHorasDominicales(prgSerconNew.getFecha(),
                            prgSerconNew.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                            prgSerconNew.getIdEmpleado().getIdEmpleado(), cargarObjetoParaJar(prgSerconNew));
                    PrgSerconLiqUtil get = preCagarHorasDominicales.get(0);
                    setValueFromPrgSerconJar(get, prgSerconNew);
                } else {
                    PrgSerconLiqUtil param = cargarObjetoParaJar(prgSerconNew);
                    param.setFecha(prgSerconNew.getFecha());
                    param.setIdEmpleado(new EmpleadoLiqUtil(prgSerconNew.getIdEmpleado().getIdEmpleado()));
                    param.setIdGopUnidadFuncional(new GopUnidadFuncionalLiqUtil(prgSerconNew.getIdGopUnidadFuncional().getIdGopUnidadFuncional()));
                    param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(prgSerconNew.getSercon(), prgSerconNew.getWorkTime())));
                    ErrorPrgSercon errorPrgSercon
                            = getINSTANCEJS().recalcularHrsExtrasSmnl(param);
                    if (errorPrgSercon.isStatus()) {
                        String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                                + MovilidadUtil.toTimeSec(errorPrgSercon.getHora());
                        ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
                        if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                            MovilidadUtil.addErrorMessage(msg);
                        } else {
                            MovilidadUtil.addAdvertenciaMessage(msg);
                        }
                        return;
                    }
                    List<PrgSerconLiqUtil> list = errorPrgSercon.getLista().stream()
                            .filter(x -> !jornadaFechaDiferente(prgSerconNew.getFecha(), x))
                            .collect(Collectors.toList());
                    PrgSerconLiqUtil get = list.get(0);
                    setValueFromPrgSerconJar(get, prgSerconNew);
                    prgSerconEJB.edit(prgSerconNew);
                    list = errorPrgSercon.getLista().stream()
                            .filter(x -> jornadaFechaDiferente(prgSerconNew.getFecha(), x))
                            .collect(Collectors.toList());
                    prgSerconEJB.updatePrgSerconFromList(list, 0);

                }
            } else {
                if (UtilJornada.esPorPartes(prgSerconNew)) {
                    LiqJornadaJSFMB.cargarCalcularDatoPorPartes(prgSerconNew);
                } else {
                    LiqJornadaJSFMB.cargarCalcularDato(prgSerconNew);
                }
                prgSerconEJB.edit(prgSerconNew);
            }
        } else {
            /**
             * estado Por autorizar
             */
            prgSerconNew.setAutorizado(-1);
            prgSerconNew.setUserAutorizado("");
            prgSerconNew.setFechaAutoriza(null);
            prgSerconEJB.create(prgSerconNew);
        }
        MovilidadUtil.addSuccessMessage("Se crea jornada exitosamente");
        MovilidadUtil.hideModal(rol);
        PrimeFaces.current()
                .executeScript("PF('crear_turno_wv_dialog').hide()");
        consultar();
    }

    private boolean validarTrasnochoMadrugada(PrgSercon solicitanteAct) throws ParseException {
        PrgSercon prgSerconSolicitanteAnt;
        PrgSercon prgSerconSolicitanteSig;
        String hIniTurnoACambiar;
        int dif;
        Date fechaAnterior = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), -1);
        Date fechaSiguiente = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), 1);

        prgSerconSolicitanteAnt = prgSerconEJB.validarEmplSinJornadaByUnindadFuncional(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaAnterior, 0);
        boolean despuesDeEjecutado = UtilJornada.registroDespuesDeEjecutado(solicitanteAct.getFecha(),
                UtilJornada.ultimaHoraRealJornada(solicitanteAct));
        String msg = "El turno NO permite respetar las 12 horas de descanso reglamentarias";
        if (prgSerconSolicitanteAnt != null) {
            /**
             * Caso 2: Verificar si el operador cumple 12 horas de descanso con
             * respecto al turno del día anterior.
             */
            String sTimeOriginRemp = solicitanteAct.getRealTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginRemp) == 0) {
                sTimeOriginRemp = "24:00:00";
            }

            String timeDestinyDiaAnterior = prgSerconSolicitanteAnt.getTimeDestiny();
            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, "24:00:00");
            if (prgSerconSolicitanteAnt.getAutorizado() != null
                    && prgSerconSolicitanteAnt.getAutorizado() == 1) {
                timeDestinyDiaAnterior = prgSerconSolicitanteAnt.getRealTimeDestiny();
            }
            dif = MovilidadUtil.diferencia(timeDestinyDiaAnterior, hIniTurnoACambiar);
            ConfigControlJornada horasDescanso = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(horasDescanso.getTiempo())) {
                if (despuesDeEjecutado && UtilJornada.tipoLiquidacion()) {
                    MovilidadUtil.addAdvertenciaMessage(msg);
                    return false;
                } else {
                    MovilidadUtil.addErrorMessage(msg);
                    return true;
                }
            }
        }

        prgSerconSolicitanteSig = prgSerconEJB.validarEmplSinJornadaByUnindadFuncional(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaSiguiente, 0);

        if (prgSerconSolicitanteSig != null) {
            /**
             * Caso 2: Verificar si el operador solicitante cumple 12 horas de
             * descanso con respecto a la jornada del día siguiente.
             */
            String sTimeOriginRempSig = prgSerconSolicitanteSig.getTimeOrigin();
            if (prgSerconSolicitanteSig.getAutorizado() != null
                    && prgSerconSolicitanteSig.getAutorizado() == 1) {
                sTimeOriginRempSig = prgSerconSolicitanteSig.getRealTimeOrigin();
            }

            if (MovilidadUtil.toSecs(sTimeOriginRempSig) == 0) {
                sTimeOriginRempSig = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRempSig, "24:00:00");
            dif = MovilidadUtil.diferencia(solicitanteAct.getRealTimeDestiny(), hIniTurnoACambiar);
            ConfigControlJornada get = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(get.getTiempo())) {
                if (despuesDeEjecutado && UtilJornada.tipoLiquidacion()) {
                    MovilidadUtil.addAdvertenciaMessage(msg);
                    return false;
                } else {
                    MovilidadUtil.addErrorMessage(msg);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Calcular tiempo de produción para una nueva joranda.
     *
     * Se ayuda de la variable prgSerconNew, utlizada en la persistencia de una
     * nueva jornada, para hacer el calculo.
     *
     * @return un valor string con el total de horas de producción.
     */
    public String timeProductionNew() {
        /**
         * Convertir a enteros las horas todos los turnos.
         */

        int turnoI1 = MovilidadUtil.toSecs(prgSerconNew.getRealTimeOrigin());
        int turnoF1 = MovilidadUtil.toSecs(prgSerconNew.getRealTimeDestiny());
        int turnoI2 = MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno2());
        int turnoF2 = MovilidadUtil.toSecs(prgSerconNew.getRealHfinTurno2());
        int turnoI3 = MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno3());
        int turnoF3 = MovilidadUtil.toSecs(prgSerconNew.getRealHfinTurno3());

        /**
         * Calcular el tiempo de produccion de todos los turnos.
         */
        int produccionTurno1 = turnoF1 - turnoI1;
        int produccionTurno2 = turnoF2 - turnoI2;
        int produccionTurno3 = turnoF3 - turnoI3;
        /**
         * Calcular el tiempo total de produccion
         */
        int produccionTotal = produccionTurno1 + produccionTurno2 + produccionTurno3;

        /**
         * Retornar el tiempo todal de produccion en String.
         */
        if (produccionTotal == 0) {
            return Jornada.hr_cero;
        }
        return MovilidadUtil.toTimeSec(produccionTotal);
    }

    /**
     * Calcular tiempo de produción al ajustar una joranda.
     *
     * Se ayuda de la variable prgSercon, utlizada en la persistencia de ajustar
     * una jornada, para hacer el calculo.
     *
     * @return un valor string con el total de horas de producción.
     */
    public String timeProduction() {
        int turnoI1 = 0;
        int turnoF1 = 0;
        int turnoI2 = 0;
        int turnoF2 = 0;
        int turnoI3 = 0;
        int turnoF3 = 0;
        //Convertir a enteros las horas todos los turnos
        if (prgSercon.getRealTimeOrigin() != null) {
            turnoI1 = MovilidadUtil.toSecs(prgSercon.getRealTimeOrigin());
        } else {
            turnoI1 = MovilidadUtil.toSecs(prgSercon.getTimeOrigin());
        }

        if (prgSercon.getRealTimeDestiny() != null) {
            turnoF1 = MovilidadUtil.toSecs(prgSercon.getRealTimeDestiny());
            turnoI2 = MovilidadUtil.toSecs(prgSercon.getRealHiniTurno2());
            turnoF2 = MovilidadUtil.toSecs(prgSercon.getRealHfinTurno2());
            turnoI3 = MovilidadUtil.toSecs(prgSercon.getRealHiniTurno3());
            turnoF3 = MovilidadUtil.toSecs(prgSercon.getRealHfinTurno3());
        } else {
            turnoF1 = MovilidadUtil.toSecs(prgSercon.getTimeDestiny());
            turnoI2 = MovilidadUtil.toSecs(prgSercon.getHiniTurno2());
            turnoF2 = MovilidadUtil.toSecs(prgSercon.getHfinTurno2());
            turnoI3 = MovilidadUtil.toSecs(prgSercon.getHiniTurno3());
            turnoF3 = MovilidadUtil.toSecs(prgSercon.getHfinTurno3());
        }

        //Calcular el tiempo de produccion de todos los turnos
        int produccionTurno1 = turnoF1 - turnoI1;
        int produccionTurno2 = turnoF2 - turnoI2;
        int produccionTurno3 = turnoF3 - turnoI3;
        //Calcular el tiempo total de produccion
        int produccionReal = produccionTurno1 + produccionTurno2 + produccionTurno3;

        int productionTimeProg = MovilidadUtil.toSecs(prgSercon.getProductionTime());
        int productionTimeRealTotal;

        if (produccionReal < productionTimeProg) {
            int diferencia = productionTimeProg - produccionReal;
            productionTimeRealTotal = productionTimeProg - diferencia;
        } else {
            int diferencia = produccionReal - productionTimeProg;
            productionTimeRealTotal = productionTimeProg + diferencia;
        }

        //Retornar el tiempo total de produccion en String
        if (productionTimeRealTotal == 0) {
            return Jornada.hr_cero;
        }
        return MovilidadUtil.toTimeSec(productionTimeRealTotal);
    }

    boolean validarHoras(String hora1, String hora2) {
        return MovilidadUtil.toSecs(hora1) > MovilidadUtil.toSecs(hora2);
    }

    /**
     * Responsable de cargar un empleado en la variable empl, para la gestión de
     * crear turno.
     *
     * Se invoca en la vista crearJornada.
     *
     * La variable codigoTransMi es diligenciada desde la vista por el usuario.
     *
     * @throws Exception
     */
    public void emplFindByCodigoT() throws Exception {
        if (codigoTransMi != null) {
            if ("".equals(codigoTransMi) && (codigoTransMi.isEmpty())) {
                MovilidadUtil.addAdvertenciaMessage("Digite el codigo del operador");
                empl = null;
                nombreEmpleado = "";
                return;
            }
            int cod = MovilidadUtil.convertStringToInt(codigoTransMi);
            if (cod == 0) {
                MovilidadUtil.addErrorMessage("Digite un código válido");
                return;
            }

            empl = emplEJB.findbyCodigoTmAndIdGopUnidadFuncActivo(cod,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (empl == null) {
                MovilidadUtil.addErrorMessage("No existe operador con el código digitado para la Unidad Funcional.");
                empl = null;
                nombreEmpleado = "";
                return;
            }
            PrgSercon ps = prgSerconEJB.validarEmplSinJornadaByUnindadFuncional(
                    empl.getIdEmpleado(), prgSerconNew.getFecha(),
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (ps != null) {
                MovilidadUtil.addErrorMessage("Operador ya existe con jornada.");
                codigoTransMi = "";
                nombreEmpleado = "";
                empl = null;
                return;
            }
            nombreEmpleado = empl.getCodigoTm() + " - " + empl.getNombres() + " " + empl.getApellidos();
            MovilidadUtil.addSuccessMessage("Operador cargado");

            cargarTotalHorasExtrasSmanal(prgSerconNew.getFecha(), empl.getIdEmpleado());

        }
    }

    /**
     * Responsable de cargar un empleado en la variable empl, para la gestión de
     * eliminar jornadas por rango de fecha o masivo.
     *
     * Se invoca en la vista borrarMasivo.
     *
     * La variable codigoTransMi es diligenciada desde la vista por el usuario.
     *
     * @throws Exception
     */
    public void emplFindByCodigoTBM() throws Exception {
        if (codigoTransMi == null) {
            MovilidadUtil.addErrorMessage("Digite un código válido.");
            return;
        }
        if ("".equals(codigoTransMi) && (codigoTransMi.isEmpty())) {
            MovilidadUtil.addAdvertenciaMessage("Digite un código válido.");
            empl = null;
            nombreEmpleado = "";
            return;
        }
        int cod = MovilidadUtil.convertStringToInt(codigoTransMi);
        if (cod == 0) {
            MovilidadUtil.addErrorMessage("Digite un código válido.");
            return;
        }
        empl = emplEJB.findbyCodigoTmAndIdGopUnidadFuncActivo(cod,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No existe operador con el codigo digitado para la Unidad Funcional.");
            empl = null;
            nombreEmpleado = "";
            return;
        }
        nombreEmpleado = empl.getCodigoTm() + " - " + empl.getNombres() + " " + empl.getApellidos();
        MovilidadUtil.addSuccessMessage("Operador cargado.");
    }

    /**
     * Responsable de precargar una jornada como modificada, y pendiente por
     * autorizar.
     *
     * Se invoca en el método: generarHorasReales,
     */
    void programacionModificada() {
//        if (prgSercon.getTimeOrigin().equals(prgSercon.getRealTimeOrigin())
//                && prgSercon.getTimeDestiny().equals(prgSercon.getRealTimeDestiny())) {
//            prgSercon.setPrgModificada(0);
//        } else {
        prgSercon.setPrgModificada(1);
        prgSercon.setAutorizado(-1);
        prgSercon.setUserAutorizado("");
        prgSercon.setFechaAutoriza(null);
//        }
    }

    /**
     * Se usa para descargar el pdf de ayuda para crear y ajustar jornadas.
     *
     * Se invoca en la vista ControlJornada.
     *
     * @throws Exception
     */
    public void prepDownloadLocal() throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(""));
    }

    /**
     * Se usa para validar si el tiempo inicio es posterior al tiempo final.
     *
     * Se usa en timeProduction, guardarTurno y generarHorasReales.
     *
     * @return retorna valor boolean.
     */
    boolean validarHoras() {
        return MovilidadUtil.toSecs(prgSercon.getRealTimeOrigin()) > MovilidadUtil.toSecs(prgSercon.getRealTimeDestiny());
    }

//    public boolean validarMaxHorasPorPartes(PrgSercon ps) {
//        int parte1 = MovilidadUtil.toSecs(ps.getRealTimeDestiny()) - MovilidadUtil.toSecs(ps.getRealTimeOrigin());
//        int parte2 = MovilidadUtil.toSecs(ps.getRealHfinTurno2()) - MovilidadUtil.toSecs(ps.getRealHiniTurno2());
//        int parte3 = MovilidadUtil.toSecs(ps.getRealHfinTurno3()) - MovilidadUtil.toSecs(ps.getRealHiniTurno3());
//        int total = parte1 + parte2 + parte3;
//        if (total > MovilidadUtil.toSecs(horasJornada)) {
//            if (!(total - MovilidadUtil.toSecs(horasJornada) <= MovilidadUtil.toSecs("02:00:00"))) {
//                return true;
//            }
//        }
//        return false;
//    }
    /**
     * Responsable de persistir en BD la data resultante del proceso de ajuste
     * de jornada.
     *
     * Utiliza los métodos cargarCalcularDatoPorPartes y cargarCalcularDato,
     * para calular la jornada en cuestión.
     *
     * @throws java.text.ParseException
     */
    public void generarHorasReales() throws ParseException {
        if (validarHoras()) {
            consultar();
            MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
            return;
        }
        if (validarHorasExtras(prgSercon)) {
            MovilidadUtil.addErrorMessage("Error al validar horas extras para " + 
                    prgSercon.getIdEmpleado().getNombresApellidos() + "el día "+ 
                    MovilidadUtil.formatDate(prgSercon.getFecha(),"yyyy-mm-dd"));
            return;
        }
        if (MovilidadUtil.toSecs(prgSercon.getRealTimeOrigin()) > MovilidadUtil.toSecs("23:59:59")) {
            MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59.");
            return;
        }
        if (validarTrasnochoMadrugada(prgSercon)) {
            return;
        }
        prgSercon.setAutorizado(-1);
        if (UtilJornada.esPorPartes(prgSercon)) {
            if (validarHoras(prgSercon.getRealHiniTurno2(), prgSercon.getRealHfinTurno2())) {
                consultar();
                MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio. Turno 2");
                return;
            }
            if (validarHoras(prgSercon.getRealHiniTurno3(), prgSercon.getRealHfinTurno3())) {
                consultar();
                MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio. Turno 3");
                return;
            }
            if (MovilidadUtil.toSecs(prgSercon.getRealHiniTurno2()) > MovilidadUtil.toSecs("23:59:59")) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59. Turno 2");
                return;
            }
            if (MovilidadUtil.toSecs(prgSercon.getRealHiniTurno3()) > MovilidadUtil.toSecs("23:59:59")) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59. Turno 3");
                return;
            }
            if (MovilidadUtil.toSecs(prgSercon.getRealTimeDestiny())
                    >= MovilidadUtil.toSecs(prgSercon.getRealHiniTurno2())
                    && (MovilidadUtil.toSecs(prgSercon.getRealHiniTurno2()) != 0
                    && MovilidadUtil.toSecs(prgSercon.getRealHfinTurno2()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 2 deben ser mayores a las del turno 1");
                return;
            }
            if (MovilidadUtil.toSecs(prgSercon.getRealHfinTurno2())
                    >= MovilidadUtil.toSecs(prgSercon.getRealHiniTurno3())
                    && (MovilidadUtil.toSecs(prgSercon.getRealHiniTurno3()) != 0
                    && MovilidadUtil.toSecs(prgSercon.getRealHfinTurno3()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 3 deben ser mayores a las del turno 2");
                return;
            }
            if (MovilidadUtil.toSecs(prgSercon.getRealTimeDestiny())
                    >= MovilidadUtil.toSecs(prgSercon.getRealHiniTurno3())
                    && (MovilidadUtil.toSecs(prgSercon.getRealHiniTurno3()) != 0
                    && MovilidadUtil.toSecs(prgSercon.getRealHfinTurno3()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 3 deben ser mayores a las del turno 1");
                return;
            }

        }
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        if (UtilJornada.tipoLiquidacion()) {
            if (validarHorasExtrasFlexible(prgSercon, true)) {
                return;
            }
            if (validarMaxHorasExtrasSmanalesFlexible(prgSercon, true)) {
                return;
            }
        } else {
            if (validarMaxHorasExtrasSmanales(prgSercon)) {
                return;
            }
        }
        try {
            String productionTime = timeProduction();
            prgSercon.setProductionTimeReal(productionTime);
            if (!b_novFromFile) {
                prgSercon.setIdPrgSerconMotivo(new PrgSerconMotivo(i_prgSerconMotivo));
            }
            prgSercon.setUserGenera(user.getUsername());
            prgSercon.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
            programacionModificada();
            if (rol.equals("ROLE_PROFOP") || rol.equals("ROLE_LIQ")
                    || !UtilJornada.validarCriterioMinJornada(prgSercon)) {
//                    if (prgSercon.getPrgModificada() == 1) {
                prgSercon.setUserAutorizado(user.getUsername());
                prgSercon.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                // (1) uno para autorizar, (0) cero para no autorizar
                prgSercon.setAutorizado(1);
                if (UtilJornada.tipoLiquidacion()) {
                    if (MovilidadUtil.isSunday(prgSercon.getFecha())) {
                        List<PrgSerconLiqUtil> preCagarHorasDominicales = getINSTANCEJS().preCagarHorasDominicales(prgSercon.getFecha(),
                                prgSercon.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                                prgSercon.getIdEmpleado().getIdEmpleado(), cargarObjetoParaJar(prgSercon));
                        PrgSerconLiqUtil get = preCagarHorasDominicales.get(0);
                        setValueFromPrgSerconJar(get, prgSercon);
                    } else {
                        PrgSerconLiqUtil param = cargarObjetoParaJar(prgSercon);
                        param.setFecha(prgSercon.getFecha());
                        param.setIdEmpleado(new EmpleadoLiqUtil(prgSercon.getIdEmpleado().getIdEmpleado()));
                        param.setIdGopUnidadFuncional(new GopUnidadFuncionalLiqUtil(prgSercon.getIdGopUnidadFuncional().getIdGopUnidadFuncional()));
                        param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(prgSercon.getSercon(), prgSercon.getWorkTime())));
                        ErrorPrgSercon errorPrgSercon
                                = getINSTANCEJS().recalcularHrsExtrasSmnl(param);
                        if (errorPrgSercon.isStatus()) {
                            String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                                    + MovilidadUtil.toTimeSec(errorPrgSercon.getHora());
                            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
                            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                                MovilidadUtil.addErrorMessage(msg);
                            } else {
                                MovilidadUtil.addAdvertenciaMessage(msg);
                            }
                            return;
                        }
                        List<PrgSerconLiqUtil> list = errorPrgSercon.getLista().stream()
                                .filter(x -> !jornadaFechaDiferente(prgSercon.getFecha(), x))
                                .collect(Collectors.toList());
                        PrgSerconLiqUtil get = list.get(0);
                        setValueFromPrgSerconJar(get, prgSercon);
                        prgSercon.setProductionTimeReal(calcularRealTime(prgSercon));
                        prgSerconEJB.edit(prgSercon);
                        list = errorPrgSercon.getLista().stream()
                                .filter(x -> jornadaFechaDiferente(prgSercon.getFecha(), x))
                                .collect(Collectors.toList());
                        prgSerconEJB.updatePrgSerconFromList(list, 0);
                    }
                } else {
                    prgSercon.setDominicalCompDiurnas(null);
                    prgSercon.setDominicalCompNocturnas(null);
                    /**
                     * Validar si la jornada es por parte
                     */
                    if (UtilJornada.esPorPartes(prgSercon)) {
                        LiqJornadaJSFMB.cargarCalcularDatoPorPartes(prgSercon);
                        prgSercon.setProductionTimeReal(calcularRealTime(prgSercon));
                    } else {
                        LiqJornadaJSFMB.cargarCalcularDato(prgSercon);
                        prgSercon.setProductionTimeReal(calcularRealTime(prgSercon));
                        prgSerconEJB.edit(prgSercon);
                    }
                }
            } else if (rol.equals("ROLE_TC")) {
                if (prgSercon.getUserGenera() != null) {
                    if (!prgSercon.getUserGenera().equals(user.getUsername())) {
                        MovilidadUtil.addAdvertenciaMessage("Acción no permitida, solo puede realizar cambio al registro el usuario que lo registró");
                        return;
                    }
                    if (prgSercon.getAutorizado() != -1) {
                        consultar();
                        MovilidadUtil.addAdvertenciaMessage("No se puede realizar la acción, el registro ya fue autorizado por un profesional");
                        return;
                    }
                }
                prgSerconEJB.edit(prgSercon);
            }
            consultar();
            MovilidadUtil.addSuccessMessage("Horas reales registradas correctamente");
            prgSercon = null;
            i_prgSerconMotivo = 0;
            PrimeFaces.current().executeScript("PF('controlJornadaDialog').hide();");
        } catch (Exception e) {
            System.out.println("Error en Generar Horas Reales");
        }
    }

    /**
     * Responsable de persistir en BD el proceso de Borrar por rango de fechas,
     * las jornadas de un empleado.
     *
     * Utiliza las variable fechaDesdeBM y empl, las cuales son diligenciadas
     * por el usuario desde la vista.
     */
    @Transactional
    public void guardarBM() {
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado el operador");
            return;
        }
        if (fechaDesdeBM == null || fechaHastaBM == null) {
            MovilidadUtil.addErrorMessage("Cargar fechas");
            return;
        }
        if (i_prgSerconMotivo == 0) {
            MovilidadUtil.addErrorMessage("Seleccionar el motivo");
            return;
        }
        long valor = prgSerconEJB.validarPeriodoLiquidadoEmpleado(fechaDesde, fechaDesde, empl.getIdEmpleado());
        if (valor < 0) {
            MovilidadUtil.addErrorMessage("El empleado ya tiene jornadar liquidadas para este periodo de tiempo");
            return;
        }
        prgSerconEJB.borradoMasivo(empl.getIdEmpleado(), i_prgSerconMotivo, fechaDesdeBM, fechaHastaBM, observacionesBM, user.getUsername(), 1);
        MovilidadUtil.addSuccessMessage("Nomina borrada para el periodo de tiempo");
        PrimeFaces.current().executeScript("PF('borrar_wv_dialog').hide()");
        empl = null;
        i_prgSerconMotivo = 0;
        fechaDesdeBM = MovilidadUtil.fechaHoy();
        fechaHastaBM = MovilidadUtil.fechaHoy();
        consultar();
    }

    //Autorizar registro rol Profesional
    /**
     * Responsable de persistir en BD el proceso de Autoriza ó No Autorizar.
     * Esta opción solo esta habilitada para usuarios con rol ROLE_LIQ.
     *
     * Utiliza los métodos cargarCalcularDatoPorPartes y cargarCalcularDato,
     * para calular la jornada en cuestión.
     *
     * @param ps Es el objeto de la jornada. Cargado desde la vista al accionar
     * un boton en la vista.
     * @param op es un indicador para saber si la gestión que se desea realizar
     * es Autoriza ó No Autorizar. Tiene dos valores (0 y 1).
     */
    public void autorizar(PrgSercon ps, int op) {
        try {
            if (ps == null) {
                MovilidadUtil.addErrorMessage("Error al seleccionar la tarea");
                return;
            }
            if (ps.getPrgModificada() != 1) {
                MovilidadUtil.addErrorMessage("No puede realizar esta acción debido a que no está modificada la programación");
                return;
            }
            ps.setUserAutorizado(user.getUsername());
            ps.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
            ps.setDominicalCompDiurnas(null);
            ps.setDominicalCompNocturnas(null);
            UtilJornada.cargarParametrosJar();
            if (UtilJornada.tipoLiquidacion()) {
                ps.setAutorizado(op);
                if (MovilidadUtil.isSunday(ps.getFecha())) {
                    List<PrgSerconLiqUtil> preCagarHorasDominicales = getINSTANCEJS().preCagarHorasDominicales(ps.getFecha(),
                            ps.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                            ps.getIdEmpleado().getIdEmpleado(), cargarObjetoParaJar(ps));
                    PrgSerconLiqUtil get = preCagarHorasDominicales.get(0);
                    setValueFromPrgSerconJar(get, ps);
                    ps.setProductionTimeReal(calcularRealTime(ps));
                    prgSerconEJB.edit(ps);

                } else {
                    //
                    PrgSerconLiqUtil param = cargarObjetoParaJar(ps);
                    param.setFecha(ps.getFecha());
                    param.setIdEmpleado(new EmpleadoLiqUtil(ps.getIdEmpleado().getIdEmpleado()));
                    param.setIdGopUnidadFuncional(new GopUnidadFuncionalLiqUtil(ps.getIdGopUnidadFuncional().getIdGopUnidadFuncional()));
                    param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(ps.getSercon(), ps.getWorkTime())));
                    ErrorPrgSercon errorPrgSercon
                            = getINSTANCEJS().recalcularHrsExtrasSmnl(param);
                    if (errorPrgSercon.isStatus()) {
                        String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                                + MovilidadUtil.toTimeSec(errorPrgSercon.getHora());
                        ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
                        if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                            MovilidadUtil.addErrorMessage(msg);
                        } else {
                            MovilidadUtil.addAdvertenciaMessage(msg);
                        }
                        return;
                    }
                    List<PrgSerconLiqUtil> list = errorPrgSercon.getLista().stream()
                            .filter(x -> !jornadaFechaDiferente(ps.getFecha(), x))
                            .collect(Collectors.toList());
                    PrgSerconLiqUtil get = list.get(0);
                    setValueFromPrgSerconJar(get, ps);
                    ps.setProductionTimeReal(calcularRealTime(ps));
                    prgSerconEJB.edit(ps);
                    list = errorPrgSercon.getLista().stream()
                            .filter(x -> jornadaFechaDiferente(ps.getFecha(), x))
                            .collect(Collectors.toList());
                    prgSerconEJB.updatePrgSerconFromList(list, 0);
                    if (op == 1) { // (1) uno para autorizar, (0) cero para no autorizar
                        MovilidadUtil.addSuccessMessage("Autorización realizada");
                    } else {
                        MovilidadUtil.addSuccessMessage("Desautorización realizada");
                    }

                }
            } else {

                if (op == 1) { // (1) uno para autorizar, (0) cero para no autorizar
                    ps.setAutorizado(1);
                    if (validarHorasExtras(ps)) {
                        ps.setAutorizado(0);
                        return;
                    }
                    if (UtilJornada.esPorPartes(ps)) {
                        LiqJornadaJSFMB.cargarCalcularDatoPorPartes(ps);
                        ps.setProductionTimeReal(calcularRealTime(ps));
                    } else {
                        LiqJornadaJSFMB.cargarCalcularDato(ps);
                        ps.setProductionTimeReal(calcularRealTime(ps));
                    }
                    MovilidadUtil.addSuccessMessage("Autorización realizada");

                } else if (op == 0) {
                    ps.setAutorizado(0);
                    MovilidadUtil.addSuccessMessage("Desautorización realizada");
                }
                prgSerconEJB.edit(ps);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String calcularRealTime(PrgSercon ps) {
        int time = 0;
        if (ps.getRealTimeOrigin() != null && !ps.getRealTimeOrigin().isEmpty()) {
            if (ps.getRealTimeDestiny() != null && !ps.getRealTimeDestiny().isEmpty()) {
                time = MovilidadUtil.toSecs(ps.getRealTimeDestiny()) - MovilidadUtil.toSecs(ps.getRealTimeOrigin());
            }
        }
        if (ps.getRealHiniTurno2() != null && !ps.getRealHiniTurno2().isEmpty()) {
            if (ps.getRealHfinTurno2() != null && !ps.getRealHfinTurno2().isEmpty()) {
                time += MovilidadUtil.toSecs(ps.getRealHfinTurno2()) - MovilidadUtil.toSecs(ps.getRealHiniTurno2());
            }
        }
        if (ps.getRealHiniTurno3() != null && !ps.getRealHiniTurno3().isEmpty()) {
            if (ps.getRealHfinTurno3() != null && !ps.getRealHfinTurno3().isEmpty()) {
                time += MovilidadUtil.toSecs(ps.getRealHfinTurno3()) - MovilidadUtil.toSecs(ps.getRealHiniTurno3());
            }
        }
        return MovilidadUtil.toTimeSec(time);
    }

    /**
     * Método responsable de persistir en BD el proceso de liquidación o no, de
     * una jornada.
     *
     * @param ps Es el objeto de la jornada. Cargado desde la vista al accionar
     * un boton en la vista.
     * @param op es un indicador para saber si la gestión que se desea realizar
     * es Liquidar ó No Liquidar. Tiene dos valores (0 y 1).
     */
    public void liquidar(PrgSercon ps, int op) {
        try {
            if (ps != null) {
                if (op == 1) { // (1) uno para liquidar, (0) para no loquidar
                    ps.setLiquidado(1);
                    MovilidadUtil.addSuccessMessage("Registro liquidado correctamente");
                }
                if (op == 0) {
                    ps.setLiquidado(0);
                    MovilidadUtil.addSuccessMessage("Registro NO liquidado correctamente");
                }
                ps.setFechaLiquida(MovilidadUtil.fechaCompletaHoy());
                ps.setUserLiquida(user.getUsername());  //generado por es para usuario que liquida
                prgSerconEJB.edit(ps);
                return;
            }
            MovilidadUtil.addErrorMessage("Error al seleccionar la tarea");
        } catch (Exception e) {
            System.out.println("Error en liquidar");
        }
    }

    /**
     * Método responsable de control del modulo, de acuerdo al rol del usuario
     * que está en sesión.
     */
    void validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_PROFOP")) {
                rol = "ROLE_PROFOP";
                b_liquida = true;
                b_autoriza = true;
                b_controlAutoriza = true;
                b_controlSubirArchivo = true;
                b_genera = true;
                b_generaDelete = true;
                b_controlLiquida = true;
            }
            if (auth.getAuthority().equals("ROLE_TC")) {
                rol = "ROLE_TC";
                b_genera = true;
                b_generaDelete = false;
            }
            if (auth.getAuthority().equals("ROLE_LIQ")) {
                rol = "ROLE_LIQ";
                b_liquida = true;
                b_autoriza = true;
                b_controlAutoriza = true;
                b_controlSubirArchivo = true;
                b_genera = true;
                b_generaDelete = true;
                b_controlLiquida = true;
            }
        }
    }

    /**
     * Método responsable de cargar desde la vista la variable prgSercon, la
     * cual se se utiliza para ajustar una jornada.
     *
     * Se acciona al seleccionar un registro de la tabla de la vista
     * ControlJornada.
     *
     * @param event Contiene el objeto del registro seleccionado desde la vista
     * ControlJornada.
     * @throws ParseException
     */
    public void onRowSelect(SelectEvent event) throws ParseException {
        setPrgSercon((PrgSercon) event.getObject());
        int i = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(prgSercon.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
        if (prgSercon.getNominaBorrada() == 1 || (prgSercon.getLiquidado() != null && prgSercon.getLiquidado() == 1)) {
            prgSercon = null;
            return;
        }
        if (rol.equals("ROLE_TC")) {
            if (i == 0) {
                prgSercon = null;
            }
        }
    }

    /**
     * Método responsable de validar si la jornada en cuestión tiene mas de
     * 24Hrs de haberse ejecutado. Valida que la jornada no sea de antes de ayer
     * y ademas que el usuario no tenga rol ROLE_TC.
     *
     * @return retorna valor booleano FALSE si la jornada es de una fecha menor
     * a la actual o mayor y que el rol del usuario en sesión es no es ROLE_TC,
     * de lo contrario retorna TRUE.
     * @throws ParseException
     */
    public boolean disableBoton() throws ParseException {
        int i = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(prgSerconNew.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
        if (rol.equals("ROLE_TC")) {
            if (i == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método resposnable de hacer null la variable prgSercon luego de
     * desseleccionar la fila previamente seleccionada en la tabla de la vista
     * ControlJornada.
     *
     * Para desseleccionar un registro de la table se debe presionar la tecla
     * Ctrl+Clic sobre le registro.
     */
    public void onRowUnselect() {
        prgSercon = null;
    }

    public void onToggleSelect(ToggleSelectEvent event) throws ParseException {
        if (!event.isSelected()) {
            prglistSelected.clear();
        }
    }
    
    /**
     * Permite autorizar las novedades que se han pre-seleccionado en el reporte
     * de control jornada (módulo de operadores).
     * El proceso toma en lista todos los registros seleccionados en el reporte 
     * y evalúa si el estado del registro es "por Autorizar", de ser así 
     * procede a ejecutar el proceso de autorización.
     *
     * @throws java.text.ParseException
     */
    public void autorizarNovedades() throws ParseException {
        if (!prglistSelected.isEmpty()) {
            for (PrgSercon obj : prglistSelected) {
                if (obj.getAutorizado() != null && obj.getAutorizado() == -1) {//solamente se autorizan los registros que estén por autorizar
                    autorizar(obj, 1);
                }
            }
        }
    }
    
    /**
     * Metodo de resposnable de cargar el archivo Excel indicado desde la vista
     * ControlJornada en la variable uploadedFile.
     *
     * Invoca al método cargarReporteLiquidacion.
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            cargarReporteLiquidacion();
            MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
        } catch (Exception ex) {
            System.out.println("Error en la carga de archivo");
        }
    }

    /**
     * Método responsable de extraer las jornadas del archivo excel para la
     * carga de jornadas.
     */
    @Transactional
    public void cargarReporteLiquidacion() {
        try {
            if (uploadedFile != null) {
                List<LiquidacionSercon> list_LiquidacionSercon = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "asignacion");
                FileInputStream file = new FileInputStream(new File(path));
                XSSFWorkbook wb = new XSSFWorkbook(file);
                XSSFSheet sheet = wb.getSheetAt(0);
                int numFilas = sheet.getLastRowNum();
                for (int a = 4; a < numFilas; a++) {
                    LiquidacionSercon liquidacionSercon = new LiquidacionSercon();
                    Row fila = sheet.getRow(a);
                    int numCols = fila.getLastCellNum();
                    for (int b = 0; b < numCols; b++) {
                        Cell celda = fila.getCell(b);
                        switch (b) {
                            case 0:
                                Date parse = new SimpleDateFormat("dd/MM/yyyy").parse(celda.toString());
                                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                                liquidacionSercon.setFecha(fecha);
                                break;
                            case 1:
                                liquidacionSercon.setCompania(celda.toString());
                                break;
                            case 2:
                                liquidacionSercon.setCodigo(celda.toString());
                                break;
                            case 3:
                                liquidacionSercon.setConductor(celda.toString());
                                break;
                            case 4:
                                liquidacionSercon.setDocumento(celda.toString());
                                break;
                            case 5:
                                liquidacionSercon.setTiempoProduccion(celda.toString());
                                break;
                            case 6:
                                liquidacionSercon.setDiurnas(celda.toString());
                                break;
                            case 7:
                                liquidacionSercon.setNocturnas(celda.toString());
                                break;
                            case 8:
                                liquidacionSercon.setExtraDiurna(celda.toString());
                                break;
                            case 9:
                                liquidacionSercon.setExtraNocturna(celda.toString());
                                break;
                            case 10:
                                liquidacionSercon.setFestivoDiurno(celda.toString());
                                break;
                            case 11:
                                liquidacionSercon.setFestivoNocturno(celda.toString());
                                break;
                            case 12:
                                liquidacionSercon.setFestivoExtraDiurno(celda.toString());
                                break;
                            case 13:
                                liquidacionSercon.setFestivoExtraNocturno(celda.toString());
                                break;
                            case 14:
                                liquidacionSercon.setCompensatorio(celda.toString());
                                break;
                        }
                    }
                    list_LiquidacionSercon.add(liquidacionSercon);
                }
//                System.out.println("Tamaño : " + list_LiquidacionSercon.size());
//                System.out.println("Primer Objeto: " + list_LiquidacionSercon.get(0).toString());
//                System.out.println("Ultimo Objeto: " + list_LiquidacionSercon.get(list_LiquidacionSercon.size() - 1).toString());
                persistir(list_LiquidacionSercon);
                wb.close();
            }
        } catch (Exception e) {
            System.out.println("Error al procesar Excel");
        }
    }

    /**
     * Metodo encargado de persisitir en BD la información de jorndas cargadas
     * por el metodo cargarReporteLiquidacion().
     */
    void persistir(List<LiquidacionSercon> list) {
        try {
            for (LiquidacionSercon liq : list) {
                prgSerconEJB.update(liq);
            }
        } catch (Exception e) {
            System.out.println("Error en persistir Control Jornada");
        }
    }

    boolean validarHorasExtras(PrgSercon psLocal) throws ParseException {
        int difPart1 = MovilidadUtil.toSecs(psLocal.getRealTimeDestiny())
                - MovilidadUtil.toSecs(psLocal.getRealTimeOrigin());
        int difPart2 = MovilidadUtil.toSecs(psLocal.getRealHfinTurno2())
                - MovilidadUtil.toSecs(psLocal.getRealHiniTurno2());
        int difPart3 = MovilidadUtil.toSecs(psLocal.getRealHfinTurno3())
                - MovilidadUtil.toSecs(psLocal.getRealHiniTurno3());
        int totalProduccion = difPart1 + difPart2 + difPart3;
        String workTime = MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(psLocal.getSercon(), psLocal.getWorkTime()));
        if ((totalProduccion
                - MovilidadUtil.toSecs(workTime))
                > MovilidadUtil.toSecs(UtilJornada.getMaxHrsExtrasDia())) {
            String mssg = "Se excedió el máximo de horas extras " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                    + MovilidadUtil.toTimeSec((totalProduccion - MovilidadUtil.toSecs(workTime)));

            boolean despuesDeEjecutado = UtilJornada.registroDespuesDeEjecutado(psLocal.getFecha(),
                    UtilJornada.ultimaHoraRealJornada(psLocal));

            if (UtilJornada.respetarMaxHorasExtrasDiarias()) {
                if (despuesDeEjecutado && UtilJornada.tipoLiquidacion()) {
                    MovilidadUtil.addAdvertenciaMessage(mssg);
                } else {
                    MovilidadUtil.addErrorMessage(mssg);
                    return true;
                }
            } else {
                MovilidadUtil.addAdvertenciaMessage(mssg);
            }
        }
        return false;
    }

    boolean validarMaxHorasExtrasSmanales(PrgSercon psLocal) throws ParseException {
        int difPart1 = MovilidadUtil.toSecs(psLocal.getRealTimeDestiny())
                - MovilidadUtil.toSecs(psLocal.getRealTimeOrigin());
        int difPart2 = MovilidadUtil.toSecs(psLocal.getRealHfinTurno2())
                - MovilidadUtil.toSecs(psLocal.getRealHiniTurno2());
        int difPart3 = MovilidadUtil.toSecs(psLocal.getRealHfinTurno3())
                - MovilidadUtil.toSecs(psLocal.getRealHiniTurno3());
        int totalProduccion = difPart1 + difPart2 + difPart3;
        String workTime = MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(psLocal.getSercon(), psLocal.getWorkTime()));

        int totalHorasExtras = (totalProduccion
                - MovilidadUtil.toSecs(workTime));
        String ultimaHoraRealJornada = UtilJornada.ultimaHoraRealJornada(psLocal);
        boolean despuesDeEjecutado = UtilJornada.registroDespuesDeEjecutado(psLocal.getFecha(), ultimaHoraRealJornada);

//        if (totalHorasExtras >= MovilidadUtil.toSecs(JornadaUtil.getCriterioMinHrsExtra())) {
        if ((MovilidadUtil.toSecs(totalHorasExtrasSmnal) + totalHorasExtras)
                > MovilidadUtil.toSecs(UtilJornada.getMaxHrsExtrasSmnl())) {
            String msg = "Se excedió el máximo de horas extras semanales" + UtilJornada.getMaxHrsExtrasSmnl() + ". Encontrado "
                    + MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(totalHorasExtrasSmnal) + totalHorasExtras));
            if (despuesDeEjecutado && UtilJornada.tipoLiquidacion()) {
                MovilidadUtil.addAdvertenciaMessage(msg);
            } else {
                MovilidadUtil.addErrorMessage(msg);
                return true;
            }
        }
//        }
        return false;
    }

    public void cargarTotalHorasExtrasSmanal(Date fechaParam, int idEmpleadoFound) {
        Calendar primerDiaSmana = Calendar.getInstance(MovilidadUtil.localeCO());
        Calendar ultimoDiaSmana = Calendar.getInstance(MovilidadUtil.localeCO());
        primerDiaSmana.setTime(fechaParam);
        ultimoDiaSmana.setTime(fechaParam);
        primerDiaSmana.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ultimoDiaSmana.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date resp = prgSerconEJB.totalHorasExtrasByRangoFechaAndIdEmpleado(
                idEmpleadoFound, fechaParam, primerDiaSmana.getTime(), ultimoDiaSmana.getTime());
        totalHorasExtrasSmnal = resp == null ? Jornada.hr_cero : Util.dateToTimeHHMMSS(resp);
//        System.out.println("totalHorasExtrasSmnal->" + totalHorasExtrasSmnal);
    }

    boolean validarMaxHorasExtrasSmanalesFlexible(PrgSercon psLocal, boolean realTime) throws ParseException {
        String ini1 = psLocal.getTimeOrigin();
        String ini2 = psLocal.getHiniTurno2();
        String ini3 = psLocal.getHiniTurno3();
        String fin1 = psLocal.getTimeDestiny();
        String fin2 = psLocal.getHfinTurno2();
        String fin3 = psLocal.getHiniTurno3();

        if (realTime) {
            ini1 = psLocal.getRealTimeOrigin();
            ini2 = psLocal.getRealHiniTurno2();
            ini3 = psLocal.getRealHiniTurno3();
            fin1 = psLocal.getRealTimeDestiny();
            fin2 = psLocal.getRealHfinTurno2();
            fin3 = psLocal.getRealHiniTurno3();
        }
        ErrorPrgSercon validateMaxHrsSmnl = getINSTANCEJS().validateMaxHrsSmnl(psLocal.getFecha(),
                ini1, fin1, ini2, fin2, ini3, fin3,
                MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(psLocal.getSercon(), psLocal.getWorkTime())),
                psLocal.getIdEmpleado().getIdEmpleado(), psLocal.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        if (validateMaxHrsSmnl.isStatus()) {
            String msg = "Se excedió el máximo de horas extras semanales" + UtilJornada.getMaxHrsExtrasSmnl() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsSmnl.getHora());

            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasSmnlObj();
            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage(msg);
            } else {
                MovilidadUtil.addAdvertenciaMessage(msg);
            }
            return true;
        }
        return false;
    }

    boolean validarMaxHrsExtrasSmnlsFlexibleRangoFecha(List<PrgSercon> list, Date desde, Date hasta) throws ParseException {
        int idEmpleado = list.get(0).getIdEmpleado() == null ? 0 : list.get(0).getIdEmpleado().getIdEmpleado();
        int idGopUnidadFuncional = list.get(0).getIdGopUnidadFuncional() == null ? 0 : list.get(0).getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        List<PrgSerconLiqUtil> listaParam = new ArrayList<>();
        for (PrgSercon item : list) {
            PrgSerconLiqUtil liqUtil = new PrgSerconLiqUtil();
            liqUtil.setAutorizado(1);
            liqUtil.setFecha(item.getFecha());
            liqUtil.setRealTimeOrigin(prgSerconNew.getRealTimeOrigin());
            liqUtil.setRealTimeDestiny(prgSerconNew.getRealTimeDestiny());
            liqUtil.setRealHiniTurno2(prgSerconNew.getRealHiniTurno2());
            liqUtil.setRealHfinTurno2(prgSerconNew.getRealHfinTurno2());
            liqUtil.setRealHiniTurno3(prgSerconNew.getRealHiniTurno3());
            liqUtil.setRealHfinTurno3(prgSerconNew.getRealHfinTurno3());
            liqUtil.setSercon(prgSerconNew.getSercon());
            liqUtil.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(item.getSercon(), item.getWorkTime())));
            liqUtil.setIdEmpleado(new EmpleadoLiqUtil(idEmpleado));
            liqUtil.setIdGopUnidadFuncional(new GopUnidadFuncionalLiqUtil(idGopUnidadFuncional));
            listaParam.add(liqUtil);
        }
        ErrorPrgSercon validateMaxHrsSmnl = getINSTANCEJS().validateMaxHrsSmnlFromList(listaParam, desde, hasta, idEmpleado, idGopUnidadFuncional);
        if (validateMaxHrsSmnl.isStatus()) {
            String msg = "Se excedió el máximo de horas extras semanales" + UtilJornada.getMaxHrsExtrasSmnl() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsSmnl.getHora());

            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasSmnlObj();
            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage(msg);
            } else {
                MovilidadUtil.addAdvertenciaMessage(msg);
            }
            return true;
        }
        return false;
    }

    boolean validarHorasExtrasFlexible(PrgSercon psLocal, boolean realTime) throws ParseException {
        PrgSerconLiqUtil param = new PrgSerconLiqUtil();
        param.setFecha(psLocal.getFecha());
        param.setTimeOrigin(psLocal.getTimeOrigin());
        param.setTimeDestiny(psLocal.getTimeDestiny());
        param.setHiniTurno2(psLocal.getHiniTurno2());
        param.setHfinTurno2(psLocal.getHfinTurno2());
        param.setHiniTurno3(psLocal.getHiniTurno3());
        param.setHfinTurno3(psLocal.getHfinTurno3());
        if (realTime) {
            param.setTimeOrigin(psLocal.getRealTimeOrigin());
            param.setTimeDestiny(psLocal.getRealTimeDestiny());
            param.setHiniTurno2(psLocal.getRealHiniTurno2());
            param.setHfinTurno2(psLocal.getRealHfinTurno2());
            param.setHiniTurno3(psLocal.getRealHiniTurno3());
            param.setHfinTurno3(psLocal.getRealHfinTurno3());
        }
        param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(psLocal.getSercon(), psLocal.getWorkTime())));
        ErrorPrgSercon validateMaxHrsDia = getINSTANCEJS().validateMaxHrsDia(param);
        if (validateMaxHrsDia.isStatus()) {
            String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsDia.getHora()) + " " + Util.dateFormat(param.getFecha());
            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage(msg);
            } else {
                MovilidadUtil.addAdvertenciaMessage(msg);
            }
            return true;
        }
        return false;
    }

    private void setValueFromPrgSerconJar(PrgSerconLiqUtil param1, PrgSercon param2) {
        param2.setDiurnas(param1.getDiurnas());
        param2.setNocturnas(param1.getNocturnas());
        param2.setExtraDiurna(param1.getExtraDiurna());
        param2.setExtraNocturna(param1.getExtraNocturna());
        param2.setFestivoDiurno(param1.getFestivoDiurno());
        param2.setFestivoNocturno(param1.getFestivoNocturno());
        param2.setFestivoExtraDiurno(param1.getFestivoExtraDiurno());
        param2.setFestivoExtraNocturno(param1.getFestivoExtraNocturno());
        param2.setDominicalCompDiurnas(param1.getDominicalCompDiurnas());
        param2.setDominicalCompNocturnas(param1.getDominicalCompNocturnas());
    }

    private boolean jornadaFechaDiferente(Date fecha, PrgSerconLiqUtil param) {
        return !com.aja.jornada.util.Util.dateFormat(fecha).equals(com.aja.jornada.util.Util.dateFormat(param.getFecha()));
    }

    private PrgSerconLiqUtil cargarObjetoParaJar(PrgSercon ps) {
        String timeOrigin1 = ps.getTimeOrigin();
        String timeDestiny1 = ps.getTimeDestiny();
        String timeOrigin2 = ps.getHiniTurno2();
        String timeDestiny2 = ps.getHfinTurno2();
        String timeOrigin3 = ps.getHiniTurno3();
        String timeDestiny3 = ps.getHfinTurno3();
        if (ps.getAutorizado() != null && ps.getAutorizado() == 1) {
            timeOrigin1 = ps.getRealTimeOrigin();
            timeDestiny1 = ps.getRealTimeDestiny();
            timeOrigin2 = ps.getRealHiniTurno2();
            timeDestiny2 = ps.getRealHfinTurno2();
            timeOrigin3 = ps.getRealHiniTurno3();
            timeDestiny3 = ps.getRealHfinTurno3();
        }
        return new PrgSerconLiqUtil(timeOrigin1,
                timeDestiny1,
                timeOrigin2,
                timeDestiny2,
                timeOrigin3,
                timeDestiny3);
    }

    /**
     * Preparar las varibles para la gestion de ajuste de jorndas por rango de
     * fechas.
     */
    public void preAjustarPorFechas() {
        empl = null;
        nombreEmpleado = "";
        if (prgSercon != null) {
            empl = prgSercon.getIdEmpleado();
            nombreEmpleado = empl.getCodigoTm() + " - " + empl.getNombres() + " " + empl.getApellidos();
        }

        prgSerconNew = new PrgSercon();
        prgSerconNew.setHiniTurno2(Jornada.hr_cero);
        prgSerconNew.setHfinTurno2(Jornada.hr_cero);
        prgSerconNew.setHiniTurno3(Jornada.hr_cero);
        prgSerconNew.setHfinTurno3(Jornada.hr_cero);
        prgSerconNew.setRealTimeOrigin(Jornada.hr_cero);
        prgSerconNew.setRealTimeDestiny(Jornada.hr_cero);
        prgSerconNew.setRealHiniTurno2(Jornada.hr_cero);
        prgSerconNew.setRealHfinTurno2(Jornada.hr_cero);
        prgSerconNew.setRealHiniTurno3(Jornada.hr_cero);
        prgSerconNew.setRealHfinTurno3(Jornada.hr_cero);

        observacionesBM = "";
        i_prgSerconMotivo = 0;
        fechaDesdeBM = MovilidadUtil.fechaHoy();
        fechaHastaBM = MovilidadUtil.fechaHoy();
        consultarMotivos();
        PrimeFaces.current().executeScript("PF('ajuste_jornada_fecha_wv').show();");
    }

    /**
     * Método que se encarga de cargar la lista de operadores activos por unidad
     * funcional.
     */
    public void findEmplActivos() {
        listaEmpleados.clear();
        listaEmpleados = emplEJB.findEmpleadosOperadores(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (listaEmpleados.isEmpty()) {
            MovilidadUtil.addErrorMessage("NO se encontraron empleados registrados");
        } else {
            PrimeFaces.current().executeScript("PF('empleado_list_wv').show()");
            PrimeFaces.current().ajax().update("formEmpleados:tabla");
        }

    }

    /**
     * Seleccionar empleado de la lista al ajustar jornada masivo
     *
     * @param event
     * @throws Exception
     */
    public void onRowDblClckSelect(final SelectEvent event) throws Exception {
        if (empl == null) {
            empl = new Empleado();
        }
        setEmpl((Empleado) event.getObject());
        nombreEmpleado = empl.getCodigoTm() + " - " + empl.getNombres() + " " + empl.getApellidos();
        MovilidadUtil.addSuccessMessage("Empleado Cargado.");
        MovilidadUtil.updateComponent("form_ajuste_jornada_fecha:codigo_operador");
        MovilidadUtil.updateComponent("form_ajuste_jornada_fecha:msgs_turno");
        MovilidadUtil.hideModal("empleado_list_wv");
    }

    /**
     * Método que se encarga de realizar el ajuste masivo de jornadas por rango
     * de fechas.
     */
    public void guardarAjusteJornadafechas() throws ParseException {
        if (validarHoras(prgSerconNew.getRealTimeOrigin(), prgSerconNew.getRealTimeDestiny())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 1 no puede ser mayor a la hora fin turno 1");
            return;
        }
        if (MovilidadUtil.toSecs(prgSerconNew.getRealTimeOrigin()) > MovilidadUtil.toSecs("23:59:59")) {
            MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59.");
            return;
        }

        if (validarHoras(prgSerconNew.getRealHiniTurno2(), prgSerconNew.getRealHfinTurno2())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 2 no puede ser mayor a la hora fin turno 2");
            return;
        }
        if (validarHoras(prgSerconNew.getRealHiniTurno3(), prgSerconNew.getRealHfinTurno3())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 3 no puede ser mayor a la hora fin turno 3");
            return;
        }

        prgSerconNew.setAutorizado(1);

        if (UtilJornada.esPorPartes(prgSerconNew)) {

            if (validarHoras(prgSerconNew.getRealHiniTurno2(), prgSerconNew.getRealHfinTurno2())) {
                consultar();
                MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio. Turno 2");
                return;
            }
            if (validarHoras(prgSerconNew.getRealHiniTurno3(), prgSerconNew.getRealHfinTurno3())) {
                consultar();
                MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio. Turno 3");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno2()) > MovilidadUtil.toSecs("23:59:59")) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59. Turno 2");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno3()) > MovilidadUtil.toSecs("23:59:59")) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59. Turno 3");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealTimeDestiny())
                    >= MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno2())
                    && (MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno2()) != 0
                    && MovilidadUtil.toSecs(prgSerconNew.getRealHfinTurno2()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 2 deben ser mayores a las del turno 1");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealHfinTurno2())
                    >= MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno3())
                    && (MovilidadUtil.toSecs(prgSerconNew.getRealHiniTurno3()) != 0
                    && MovilidadUtil.toSecs(prgSerconNew.getRealHfinTurno3()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 3 deben ser mayores a las del turno 2");
                return;
            }
            if (MovilidadUtil.toSecs(prgSerconNew.getRealTimeDestiny())
                    >= MovilidadUtil.toSecs(prgSerconNew.getHiniTurno3())
                    && (MovilidadUtil.toSecs(prgSerconNew.getHiniTurno3()) != 0
                    && MovilidadUtil.toSecs(prgSerconNew.getHfinTurno3()) != 0)) {
                MovilidadUtil.addErrorMessage("Las horas del turno 3 deben ser mayores a las del turno 1");
                return;
            }
        }

        if (empl == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado el empleado");
            return;
        }
        if (i_prgSerconMotivo == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el motivo");
            return;
        }

        if (fechaDesdeBM == null) {
            MovilidadUtil.addErrorMessage("Seleccione fecha desde");
            return;
        }
        if (fechaHastaBM == null) {
            MovilidadUtil.addErrorMessage("Seleccione fecha hasta");
            return;
        }

        if (Util.validarFechaCambioEstado(fechaDesdeBM, fechaHastaBM)) {
            MovilidadUtil.addErrorMessage("La fecha desde NO puede ser mayor a la fecha hasta");
            return;
        }
        Integer uf = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0 ? empl.getIdGopUnidadFuncional().getIdGopUnidadFuncional() : unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();

        // Se valida si existen fechas liquidadas 
        List<Date> fechasLiquidadas = prgSerconEJB.obtenerDiasLiquidadosByFechasAndUnidadFuncional(fechaDesdeBM, fechaHastaBM, uf);

        if (fechasLiquidadas != null && fechasLiquidadas.size() > 0) {
            MovilidadUtil.addErrorMessage("Se han encontrado fechas liquidadas en el rango seleccionado");
            return;
        }

        /**
         * Se realiza la búsqueda de jornadas para el empleado seleccionado, y
         * se realiza el ajuste de las horas para cada fecha que se encuentre
         * dentro del rango.
         */
        List<PrgSercon> jornadas = prgSerconEJB
                .obtenerRegistrosByFechasAndUnidadFuncional(
                        fechaDesdeBM, fechaHastaBM,
                        uf,
                        empl.getIdEmpleado());

        if (jornadas == null) {
            MovilidadUtil.addErrorMessage("NO se encontraron jornadas para el rango de fechas seleccionado");
            return;
        }
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        if (UtilJornada.tipoLiquidacion()) {
            PrgSercon ps = new PrgSercon();
            for (PrgSercon jornada : jornadas) {
                ps.setSercon(jornada.getSercon());
                ps.setFecha(jornada.getFecha());
                ps.setTimeOrigin(prgSerconNew.getRealTimeOrigin());
                ps.setTimeDestiny(prgSerconNew.getRealTimeDestiny());
                ps.setHiniTurno2(prgSerconNew.getRealHiniTurno2());
                ps.setHfinTurno2(prgSerconNew.getRealHfinTurno2());
                ps.setHiniTurno3(prgSerconNew.getRealHiniTurno3());
                ps.setHfinTurno3(prgSerconNew.getRealHfinTurno3());
                ps.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(jornada.getSercon(), jornada.getWorkTime())));

                if (validarHorasExtrasFlexible(ps, false)) {
                    return;
                }
            }
            if (validarMaxHrsExtrasSmnlsFlexibleRangoFecha(jornadas, fechaDesdeBM, fechaHastaBM)) {
                return;
            }
        } else {
            if (validarMaxHorasExtrasSmanales(prgSerconNew)) {
                return;
            }
        }

        // Liquidación Normal
        if ((rol.equals("ROLE_PROFOP") || rol.equals("ROLE_LIQ"))
                || !UtilJornada.validarCriterioMinJornada(prgSerconNew)) {
            for (PrgSercon jornada : jornadas) {

                // (1) uno para autorizar, (0) cero para no autorizar
                jornada.setAutorizado(1);
                jornada.setUserAutorizado(user.getUsername());
                jornada.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                jornada.setModificado(MovilidadUtil.fechaCompletaHoy());
                jornada.setRealTimeOrigin(prgSerconNew.getRealTimeOrigin());
                jornada.setRealTimeDestiny(prgSerconNew.getRealTimeDestiny());
                jornada.setRealHiniTurno2(prgSerconNew.getRealHiniTurno2());
                jornada.setRealHfinTurno2(prgSerconNew.getRealHfinTurno2());
                jornada.setRealHiniTurno3(prgSerconNew.getRealHiniTurno3());
                jornada.setRealHfinTurno3(prgSerconNew.getRealHfinTurno3());
                jornada.setDominicalCompDiurnas(null);
                jornada.setDominicalCompNocturnas(null);

                // TODO: Preguntar si estos datos se actualizan
                jornada.setIdPrgSerconMotivo(
                        new PrgSerconMotivo(i_prgSerconMotivo));
                jornada.setObservaciones(observacionesBM);

                if (UtilJornada.tipoLiquidacion()) {
                    if (MovilidadUtil.isSunday(jornada.getFecha())) {
                        List<PrgSerconLiqUtil> preCagarHorasDominicales = getINSTANCEJS().preCagarHorasDominicales(jornada.getFecha(),
                                jornada.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                                jornada.getIdEmpleado().getIdEmpleado(), cargarObjetoParaJar(jornada));
                        PrgSerconLiqUtil get = preCagarHorasDominicales.get(0);
                        setValueFromPrgSerconJar(get, jornada);
                        prgSerconEJB.edit(jornada);

                    } else {
                        PrgSerconLiqUtil param = new PrgSerconLiqUtil();
                        param.setFecha(jornada.getFecha());
                        param.setTimeOrigin(jornada.getRealTimeOrigin());
                        param.setTimeDestiny(jornada.getRealTimeDestiny());
                        param.setHiniTurno2(jornada.getRealHiniTurno2());
                        param.setHfinTurno2(jornada.getRealHfinTurno2());
                        param.setHiniTurno3(jornada.getRealHiniTurno3());
                        param.setHfinTurno3(jornada.getRealHfinTurno3());
                        param.setIdEmpleado(new EmpleadoLiqUtil(jornada.getIdEmpleado().getIdEmpleado()));

                        param.setIdGopUnidadFuncional(new GopUnidadFuncionalLiqUtil(jornada.getIdGopUnidadFuncional().getIdGopUnidadFuncional()));

                        param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(jornada.getSercon(), jornada.getWorkTime())));

                        ErrorPrgSercon errorPrgSercon
                                = getINSTANCEJS().recalcularHrsExtrasSmnl(param);
                        if (errorPrgSercon.isStatus()) {
                            String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                                    + MovilidadUtil.toTimeSec(errorPrgSercon.getHora());
                            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
                            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                                MovilidadUtil.addErrorMessage(msg);
                            } else {
                                MovilidadUtil.addAdvertenciaMessage(msg);
                            }
                            return;
                        }
                        List<PrgSerconLiqUtil> list = errorPrgSercon.getLista().stream()
                                .filter(x -> !jornadaFechaDiferente(jornada.getFecha(), x))
                                .collect(Collectors.toList());
                        PrgSerconLiqUtil get = list.get(0);
                        setValueFromPrgSerconJar(get, jornada);
                        prgSerconEJB.edit(jornada);
                        list = errorPrgSercon.getLista().stream()
                                .filter(x -> jornadaFechaDiferente(jornada.getFecha(), x))
                                .collect(Collectors.toList());
                        prgSerconEJB.updatePrgSerconFromList(list, 0);

                    }
                } else {
                    if (UtilJornada.esPorPartes(jornada)) {
                        LiqJornadaJSFMB.cargarCalcularDatoPorPartes(jornada);
                    } else {
                        LiqJornadaJSFMB.cargarCalcularDato(jornada);
                        prgSerconEJB.edit(jornada);
                    }
                }
            }

        }

        consultar();

        MovilidadUtil.addSuccessMessage("Ajuste realizado con éxito");
        MovilidadUtil.hideModal("ajuste_jornada_fecha_wv");

    }

    public void cargarNovedades(FileUploadEvent event) throws Exception {
        try {
            this.archivoNovedades = event.getFile();
            calcularMasivoBean.cargarMapParamFeriado();
            if (archivoNovedades != null) {
                b_novFromFile = true;
                List<PrgSerconObj> list_novedades = new ArrayList<>();
                String path = Util.saveFile(archivoNovedades, 0, "carga_novedades_operadores");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcelAndCargarNovedades(fileInputStream, list_novedades);
                b_novFromFile = false;
            }
            consultar();
        } catch (CagarJornadaGenericaException e) {
            MovilidadUtil.addErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    void recorrerExcelAndCargarNovedades(FileInputStream inputStream, List<PrgSerconObj> list_novedades)
            throws Exception, IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);

        int numFilas = sheet.getLastRowNum();
        for (int a = 1; a <= numFilas; a++) {
            PrgSerconObj prgSercon = new PrgSerconObj();
            Row fila = sheet.getRow(a);
            int numCols = fila.getLastCellNum();
            Empleado empleado;
            for (int b = 0; b < numCols; b++) {
                Cell celda = fila.getCell(b);
                if (celda != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    switch (b) {
                        case 0://fecha
                            Date parse;
                            try {
                                parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                            } catch (Exception e) {
                                parse = Util.toDate(celda.toString());
                            }
                            if (parse == null) {
                                throw new CagarJornadaGenericaException("Formato erroneo evaluando el valor " + celda.getDateCellValue() + ". \nColumna: 'Fecha', Fila: " + Integer.valueOf(a + 1));
                            }
                            prgSercon.setFecha(parse);
                            break;
//                        case 1://Semana
//                        case 2://Día
//                        case 3://Tipo día
//                        case 4://Compañía
                        case 5://código TM
                            //validar que exista el código TM
                            if (Util.isOnlyDigits(celda.toString())) {
                                empleado = emplEJB.findByCodigoTM(Integer.parseInt(celda.toString()));
                                if (empleado != null) {
                                    prgSercon.setCodigoTM(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Empleado con código TM " + celda.toString() + " no existe en la BD. Columna: 'Código TM', Fila: " + Integer.valueOf(a + 1));
                                }
                            } else {
                                throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Código TM', Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
                        case 6://nombre
                            if (!celda.toString().isEmpty()) {
                                empleado = emplEJB.findByCodigoTM(Integer.parseInt(prgSercon.getCodigoTM()));
                                if (empleado != null) {
                                    if (!celda.toString().toUpperCase().equals(empleado.getNombresApellidos())) {
                                        throw new CagarJornadaGenericaException("Nombre " + celda.toString() + " no coincide con el código TM " + prgSercon.getCodigoTM() + ". Columna: 'Nombre', Fila: " + Integer.valueOf(a + 1));
                                    } else {
                                        prgSercon.setNombre(empleado.getNombresApellidos());
                                    }
                                } else {
                                    throw new CagarJornadaGenericaException("Empleado con nombre " + celda.toString() + " no existe en la BD. Columna: 'Nombre', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 7://identificación
                            if (!celda.toString().isEmpty()) {
                                if (Util.isOnlyDigits(celda.toString())) {
                                    empleado = emplEJB.findByIdentificacion(celda.toString());
                                    if (empleado != null) {
                                        if (Integer.parseInt(prgSercon.getCodigoTM()) != (empleado.getCodigoTm())) {
                                            throw new CagarJornadaGenericaException("Identificación " + celda.toString() + " no coincide con el código TM " + prgSercon.getCodigoTM() + ". Columna: 'Identificación', Fila: " + Integer.valueOf(a + 1));
                                        } else {
                                            prgSercon.setIdentificacion(empleado.getIdentificacion());
                                        }
                                    } else {
                                        throw new CagarJornadaGenericaException("Empleado con identificación " + celda.toString() + " no existe en la BD. Columna: 'Identificación', Fila: " + Integer.valueOf(a + 1));
                                    }
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Código TM', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 8://Tarea
                            if (celda.toString() != null && !celda.toString().isEmpty()) {
                                prgSercon.setSercon(celda.toString());
                            } else {
                                throw new CagarJornadaGenericaException("Error (" + celda.toString() + "). \nColumna: 'Tarea', Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
//                        case 9://Tiempo de producción
//                        case 10://Tiempo de producción Real
                        case 11://Hr. Inicio Programada
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Inicio Programada', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isValidTimeFormat(celda.toString())) {
                                    prgSercon.setHiniPrgTurno1(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Inicio Programada', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 12://Hr. Fin Programada
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Fin Programada', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHfinPrgTurno1(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Fin Programada', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 13://Hr. Inicio Real
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Inicio Real', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isValidTimeFormat(celda.toString())) {
                                    prgSercon.setHiniRealTurno1(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Inicio Real', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 14://Hr. Fin Real
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Fin Real', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHfinRealTurno1(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Fin Real', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 15://Hr. Inicio Turno 2
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'hora_ini_prg_T2', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHiniPrgTurno2(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'hora_ini_prg_T2', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 16://Hr. Fin Turno 2
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'hora_fin_prg_T2', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHfinPrgTurno2(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'hora_fin_prg_T2', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 17://Hr. Inicio Turno 2 Real
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Inicio Turno 2 Real', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHiniRealTurno2(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Inicio Turno 2 Real', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 18://Hr. Fin Turno 2 Real
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Fin Turno 2 Real', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHfinRealTurno2(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Fin Turno 2 Real', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 19://Hr. Inicio Turno 3
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Inicio Turno 3', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHiniPrgTurno3(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Inicio Turno 3', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 20://Hr. Fin Turno 3
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Fin Turno 3', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHfinPrgTurno3(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Fin Turno 3', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 21://Hr. Inicio Turno 3 Real
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Inicio Turno 3 Real', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHiniRealTurno3(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Inicio Turno 3 Real', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 22://Hr. Fin Turno 3 Real
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error valor (" + celda.toString() + "). \nColumna: 'Hr. Fin Turno 3 Real', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                if (Util.isFormatHHMMSSGreen(celda.toString())) {
                                    prgSercon.setHfinRealTurno3(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'Hr. Fin Turno 3 Real', Fila: " + Integer.valueOf(a + 1));
                                }
                            }
                            break;
                        case 23://motivo
                            if (celda.toString() != null && !celda.toString().isEmpty()) {
                                PrgSerconMotivo serconMotivo = serconMotivoFacadeLocal.findByName(celda.toString().toUpperCase());
                                if (serconMotivo != null) {
                                    prgSercon.setMotivoJornada(celda.toString());
                                } else {
                                    throw new CagarJornadaGenericaException("Motivo no existe (" + celda.toString() + "). Columna: 'motivo', Fila: " + Integer.valueOf(a + 1));
                                }
                            } else {
                                throw new CagarJornadaGenericaException("Revise valor de la columna: 'motivo', Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
                        case 24://Observaciones
                            if (celda.toString() != null && !celda.toString().isEmpty()) {
                                prgSercon.setObservacion(celda.toString());
                            } else {
                                throw new CagarJornadaGenericaException("Revise valor de la columna: 'Observaciones', Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
                    }
                }
            }
            list_novedades.add(prgSercon);
        }//fin del bucle que lee las filas del archivo
        List<PrgSercon> listSercon = castSercon(list_novedades);
        if (listSercon != null && !listSercon.isEmpty()) {
            cargarNovedadesExcel(listSercon);
        }
    }

    private List<PrgSercon> castSercon(List<PrgSerconObj> list) throws Exception {
        List<PrgSercon> listGJ = new ArrayList<>();
        PrgSercon objSercon;
        //no se valida la existencia del empleado dado que la lista recibida debe 
        //validarse con anterioridad
        Empleado emplObj;
        for (PrgSerconObj obj : list) {
            emplObj = emplEJB.findByIdentificacion(obj.getIdentificacion());
            //evaluar si hay servicio programado con los valores deseados y si no está liquidado
            objSercon = prgSerconEJB.findSerconProgramado(obj.getFecha(), emplObj.getIdEmpleado(), obj.getHiniPrgTurno1(),
                    obj.getHfinPrgTurno1(), obj.getHiniPrgTurno2(), obj.getHfinPrgTurno2(), obj.getHiniPrgTurno3(), obj.getHfinPrgTurno3());
            if (objSercon != null) {
                objSercon.setRealTimeOrigin(obj.getHiniRealTurno1());
                objSercon.setRealTimeDestiny(obj.getHfinRealTurno1());
//                objSercon.setHiniTurno2(obj.getHiniRealTurno2() != null ? obj.getHiniRealTurno2() : "00:00:00");
                objSercon.setRealHiniTurno2(obj.getHiniRealTurno2());
                objSercon.setRealHfinTurno2(obj.getHfinRealTurno2());
                objSercon.setRealHiniTurno3(obj.getHiniRealTurno3());
                objSercon.setRealHfinTurno3(obj.getHfinRealTurno3());
                objSercon.setObservaciones(obj.getObservacion());
                objSercon.setModificado(new Date());
                objSercon.setIdPrgSerconMotivo(serconMotivoFacadeLocal.findByName(obj.getMotivoJornada()));
                listGJ.add(objSercon);
            } else {
                throw new CagarJornadaGenericaException("No existe Jornada para el colaborador " + obj.getIdentificacion() + " en la fecha " + obj.getFecha());
            }
        }
        return listGJ;
    }

    /**
     * Permite autorizar un conjunto de novedades ingresadas en una colección de
     * tipo PrgSerconObj
     *
     * @param list contiene las novedades que se desean autorizar
     */
    private void cargarNovedadesExcel(List<PrgSercon> list) {
        //objeto en el que se salva la información que contenga genericaJornada, 
        //esto se debe hacer dado que el método generarHorasReales emplea el objeto 
        //global genericaJornada 
        PrgSercon objTemp = prgSercon;
        for (PrgSercon obj : list) {
            try {
                prgSercon = obj;
                generarHorasReales();
            } catch (ParseException ex) {
                Logger.getLogger(ControlJornadaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //asignar el objeto que se tenia al momento de lanzar el proceso
        prgSercon = objTemp;
    }

    public List<PrgSercon> getPrglist() {
        return prglist;
    }

    public boolean isB_autoriza() {
        return b_autoriza;
    }

    public void setB_autoriza(boolean b_autoriza) {
        this.b_autoriza = b_autoriza;
    }

    public boolean isB_genera() {
        return b_genera;
    }

    public void setB_genera(boolean b_genera) {
        this.b_genera = b_genera;
    }

    public boolean isB_liquida() {
        return b_liquida;
    }

    public void setB_liquida(boolean b_liquida) {
        this.b_liquida = b_liquida;
    }

    public boolean isB_controlLiquida() {
        return b_controlLiquida;
    }

    public void setB_controlLiquida(boolean b_controlLiquida) {
        this.b_controlLiquida = b_controlLiquida;
    }

    public boolean isB_controlAutoriza() {
        return b_controlAutoriza;
    }

    public void setB_controlAutoriza(boolean b_controlAutoriza) {
        this.b_controlAutoriza = b_controlAutoriza;
    }

    public PrgSercon getPrgSercon() {
        return prgSercon;
    }

    public void setPrgSercon(PrgSercon prgSercon) {
        this.prgSercon = prgSercon;
    }

    public List<PrgSerconMotivo> getPrgSerconMotivoList() {
        return prgSerconMotivoList;
    }

    public Integer getI_prgSerconMotivo() {
        return i_prgSerconMotivo;
    }

    public void setI_prgSerconMotivo(Integer i_prgSerconMotivo) {
        this.i_prgSerconMotivo = i_prgSerconMotivo;
    }

    public boolean isB_controlSubirArchivo() {
        return b_controlSubirArchivo;
    }

    public void setB_controlSubirArchivo(boolean b_controlSubirArchivo) {
        this.b_controlSubirArchivo = b_controlSubirArchivo;
    }

    public LiqJornadaJSFManagedBean getLiqJornadaJSFMB() {
        return LiqJornadaJSFMB;
    }

    public void setLiqJornadaJSFMB(LiqJornadaJSFManagedBean LiqJornadaJSFMB) {
        this.LiqJornadaJSFMB = LiqJornadaJSFMB;
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

    public PrgSercon getPrgSerconNew() {
        return prgSerconNew;
    }

    public void setPrgSerconNew(PrgSercon prgSerconNew) {
        this.prgSerconNew = prgSerconNew;
    }

    public String getCodigoTransMi() {
        return codigoTransMi;
    }

    public void setCodigoTransMi(String codigoTransMi) {
        this.codigoTransMi = codigoTransMi;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Date getFechaDesdeBM() {
        return fechaDesdeBM;
    }

    public void setFechaDesdeBM(Date fechaDesdeBM) {
        this.fechaDesdeBM = fechaDesdeBM;
    }

    public Date getFechaHastaBM() {
        return fechaHastaBM;
    }

    public void setFechaHastaBM(Date fechaHastaBM) {
        this.fechaHastaBM = fechaHastaBM;
    }

    public String getObservacionesBM() {
        return observacionesBM;
    }

    public void setObservacionesBM(String observacionesBM) {
        this.observacionesBM = observacionesBM;
    }

    public boolean isB_generaDelete() {
        return b_generaDelete;
    }

    public void setB_generaDelete(boolean b_generaDelete) {
        this.b_generaDelete = b_generaDelete;
    }

    public Empleado getEmpl() {
        return empl;
    }

    public void setEmpl(Empleado empl) {
        this.empl = empl;
    }

    public String getRealTimeOrigin() {
        return realTimeOrigin;
    }

    public void setRealTimeOrigin(String realTimeOrigin) {
        this.realTimeOrigin = realTimeOrigin;
    }

    public String getRealTimeDestiny() {
        return realTimeDestiny;
    }

    public void setRealTimeDestiny(String realTimeDestiny) {
        this.realTimeDestiny = realTimeDestiny;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public boolean isB_novFromFile() {
        return b_novFromFile;
    }

    public void setB_novFromFile(boolean b_novFromFile) {
        this.b_novFromFile = b_novFromFile;
    }

    public List<PrgSercon> getPrglistSelected() {
        return prglistSelected;
    }

    public void setPrglistSelected(List<PrgSercon> prglistSelected) {
        this.prglistSelected = prglistSelected;
    }

}
