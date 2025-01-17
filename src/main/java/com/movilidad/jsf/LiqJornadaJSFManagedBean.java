/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.aja.jornada.controller.JornadaFlexible;
import com.aja.jornada.controller.calculator;
import com.aja.jornada.dto.ErrorPrgSercon;
import com.aja.jornada.model.EmpleadoLiqUtil;
import com.aja.jornada.model.GopUnidadFuncionalLiqUtil;
import com.aja.jornada.model.PrgSerconLiqUtil;
import com.aja.jornada.util.JornadaUtil;
import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgSerconMotivoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.ConfigControlJornada;
import com.movilidad.model.Empleado;
import com.movilidad.model.ParamFeriado;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgSerconMotivo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ConsolidadoLiquidacion;
import com.movilidad.util.beans.ConsolidadoLiquidacionDetallado;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.UtilJornada;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigControlJornada;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.context.SecurityContextHolder;
import com.aja.jornada.util.ConfigJornada;
import com.movilidad.model.PrgSerconInicial;

/**
 *
 * @author solucionesit
 */
@Named(value = "liqJornadaJSFMB")
@ViewScoped
public class LiqJornadaJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of LiqJornadaJSFManagedBean
     */
    public LiqJornadaJSFManagedBean() {
    }

    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @EJB
    private ParamFeriadoFacadeLocal paraFeEJB;
    @EJB
    private PrgSerconMotivoFacadeLocal serconMotivoEJB;
    @Inject
    private ControlJornadaController controlJornadaMB;
    @Inject
    private LiquidarJornadaJSFMB liquidarJornadaBean;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Date fechaDesde = MovilidadUtil.fechaCompletaHoy();
    private Date fechaHasta = MovilidadUtil.fechaCompletaHoy();

    private PrgSercon prgSercon;
    private PrgSercon emplPrgSercon;
    private boolean flagCalculo = false;
    private boolean flagSabDom = false;
    private boolean flagEliminar = false;
    private boolean flagDesliminar = false;
    private boolean flagLiquidar = false;
    private boolean flagModificar = false;
    private boolean flagCalcular = false;
    private boolean flagCambioEmpleado = false;
    private boolean flagFeriado = false;
    
    private String codigoTransMi = "";
    private String nombreEmpleado = "";
    private int nSemana = 0;

    private String s_horaIniTurno1;
    private String s_horaFinTurno1;
    private String s_horaIniTurno2;
    private String s_horaFinTurno2;
    private String s_horaIniTurno3;
    private String s_horaFinTurno3;
    private String totalHorasAsignadas = calculator.hr_cero;
    private String totalHorasExtrasSmnal;
    private StreamedContent file;
    Empleado empleadoPrgSercon;
    Empleado empleadoConsultado;

    int i_prgSerconMotivo = 0;

    private List<PrgSercon> prglist = new ArrayList<>();
    private List<PrgSerconInicial> lstProgInicial;
    private List<PrgSerconMotivo> prgSerconMotivoList;
    private List<ConsolidadoLiquidacion> lstConsolidado;

    private List<ConsolidadoLiquidacionDetallado> lstConsolidadoDetallado;

    private JornadaFlexible js;

    private JornadaFlexible getINSTANCEJS() {
        if (js == null) {
            js = new JornadaFlexible();
        }
        return js;
    }

    @PostConstruct
    public void init() {
        if (MovilidadUtil.validarUrl("controlJornada/liqJornada")) {
            cargarDatos();
        }
    }

    /**
     * Responsable de cargar la data de jornadas para ser presentada en la vista
     * principal del modulo.
     *
     * La variables fechaDesde y fechaHasta, son seleccionadas por el usurio
     * desde la vista principla.
     *
     * Limpia los filtros del DataTable de la vista principal.
     *
     */
    public void cargarDatos() {
        if (MovilidadUtil.dateSinHora(fechaDesde).before(MovilidadUtil.dateSinHora(fechaHasta))
                || MovilidadUtil.dateSinHora(fechaDesde).equals(MovilidadUtil.dateSinHora(fechaHasta))) {
            calculator.reset();
            prglist = prgSerconEJB.getPrgSerconByDateAndUnidadFunc(
                    fechaDesde, fechaHasta,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        }
    }

    /**
     * Método Responsable de consultar las jornadas por rango de fechas y
     * incovar al método generarExcel, para construir el reporte de consolidado
     * detallado de jornadas.
     *
     * @throws FileNotFoundException
     */
    public void generarReporte() throws FileNotFoundException {

        file = null;

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        lstConsolidado = prgSerconEJB.obtenerDatosConsolidado(fechaDesde, fechaHasta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstConsolidadoDetallado = prgSerconEJB.obtenerDatosConsolidadoDetallado(
                fechaDesde, fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstConsolidado == null || lstConsolidado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado)");
            return;
        }
        if (lstConsolidadoDetallado == null || lstConsolidadoDetallado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado detallado)");
            return;
        }
        generarExcel(1);
    }

    public void descargarProgramacionInicial() throws FileNotFoundException {
        file = null;

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        lstConsolidado = prgSerconEJB.obtenerDatosCargados(fechaDesde, fechaHasta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstConsolidadoDetallado = prgSerconEJB.obtenerDatosCargadosDetallado(
                fechaDesde, fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstConsolidado == null || lstConsolidado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado)");
            return;
        }
        if (lstConsolidadoDetallado == null || lstConsolidadoDetallado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado detallado)");
            return;
        }
        generarExcel(0);
//        generarExcelCargaInicialTurnos();

    }

    /**
     * Método Responsable de construir el archivo Excel de consolidado detallado
     * de jornadas.
     *
     * @throws FileNotFoundException
     */
    private void generarExcelCargaInicialTurnos() throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Reporte Programado Operadores.xlsx";
        parametros.put("items", lstProgInicial);
        destino = destino + "REPORTE_PROGRAMADO_OPERADORES.xls";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "REPORTE_PROGRAMADO_OPERADORES_" + Util.dateFormat(getFechaDesde()) + "_al_" + Util.dateFormat(getFechaHasta()) + ".xlsx");
    }

    /**
     * Método Responsable de construir el archivo Excel de consolidado detallado
     * de jornadas.
     *
     * @throws FileNotFoundException
     */
    private void generarExcel(int opt) throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Consolidado Liquidacion Gen.xlsx";
        parametros.put("desde", Util.dateFormat(fechaDesde));
        parametros.put("hasta", Util.dateFormat(fechaHasta));
        parametros.put("generado", Util.dateFormat(new Date()));
        parametros.put("titulo", opt == 1 ? "CONSOLIDADO DE HORAS LABORADAS" : "DE HORAS PROGRAMADAS");
        parametros.put("liquidacion", lstConsolidado);
        parametros.put("liquidacionDet", lstConsolidadoDetallado);

        destino = destino + (opt == 1 ? "CONSOLIDADO_LIQUIDACION.xls" : "PROGRAMADO_LIQUIDACION.xls");
        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", (opt == 1 ? "CONSOLIDADO_LIQUIDACION_" : "PROGRAMADO_LIQUIDACION_") + Util.dateFormat(fechaDesde) + "_al_" + Util.dateFormat(fechaHasta) + ".xlsx");
    }

    /**
     * Preparar variables para la gestión de modificar una jornada.
     */
    public void prepareModificar() {
        if (prgSercon == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        flagModificarJornada();
        if (flagModificar) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        if (prgSercon.getIdPrgSerconMotivo() != null) {
            i_prgSerconMotivo = prgSercon.getIdPrgSerconMotivo()
                    .getIdPrgSerconMotivo();
        }

        prgSercon.setDiurnas(prgSercon.getDiurnas() == null ? calculator.hr_cero
                : prgSercon.getDiurnas());
        prgSercon.setNocturnas(prgSercon.getNocturnas() == null ? calculator.hr_cero
                : prgSercon.getNocturnas());
        prgSercon.setExtraDiurna(prgSercon.getExtraDiurna() == null ? calculator.hr_cero
                : prgSercon.getExtraDiurna());
        prgSercon.setExtraNocturna(prgSercon.getExtraNocturna() == null ? calculator.hr_cero
                : prgSercon.getExtraNocturna());
        prgSercon.setFestivoDiurno(prgSercon.getFestivoDiurno() == null ? calculator.hr_cero
                : prgSercon.getFestivoDiurno());
        prgSercon.setFestivoNocturno(prgSercon.getFestivoNocturno() == null ? calculator.hr_cero
                : prgSercon.getFestivoNocturno());
        prgSercon.setFestivoExtraDiurno(prgSercon.getFestivoExtraDiurno() == null ? calculator.hr_cero
                : prgSercon.getFestivoExtraDiurno());
        prgSercon.setFestivoExtraNocturno(prgSercon.getFestivoExtraNocturno() == null ? calculator.hr_cero
                : prgSercon.getFestivoExtraNocturno());
        cargarTotalHorasExtrasSmanal(prgSercon.getFecha(), prgSercon.getIdEmpleado().getIdEmpleado());

        prgSerconMotivoList = serconMotivoEJB.findAllEstadoRegistro();
        PrimeFaces.current().executeScript("PF('LiqJornadaDialog').show();");
    }

    /**
     * Método encargado de persistir en BD la gestion del borrado de una
     * jornada. Valida que la jornada ya halla sido liquidada o ya a sido
     * borrada.
     */
    @Transactional
    public void borradoNomina() {
        if (prgSercon == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (prgSercon.getLiquidado() != null && prgSercon.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (prgSercon.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        flagEliminarNomina();
        if (!flagEliminar) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        prgSercon.setNominaBorrada(1);
        prgSerconEJB.nominaBorrada(prgSercon.getIdPrgSercon(), 1, user.getUsername());
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
    }

    /**
     * Método responsable de deshacer la eliminación de una jornada y persistir
     * el cambio en la BD.
     */
    @Transactional
    public void dehacerBorradoNomina() {
        if (prgSercon == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (prgSercon.getNominaBorrada() == 0) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        flagDesEliminarNomina();
        if (!flagDesliminar) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        prgSercon.setNominaBorrada(0);
        prgSerconEJB.nominaBorrada(prgSercon.getIdPrgSercon(), 0, user.getUsername());
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
    }

    /**
     * Método encargado de validar que la hora inicio no sea mayor a la hora
     * fin.
     *
     * @return retorna un valor boolean.
     */
    boolean validarHoras() {
        return MovilidadUtil.toSecs(prgSercon.getRealTimeOrigin() == null ? calculator.hr_cero : prgSercon.getRealTimeOrigin())
                > MovilidadUtil.toSecs(prgSercon.getRealTimeDestiny() == null ? calculator.hr_cero : prgSercon.getRealTimeDestiny());
    }

    /**
     * Método encargado de persistir en BD la gestión de modificar una jornada.
     * Se utiliza en la vista 'EditLiqJornada' al hacer clic sobre el botón
     * 'Guardar', mencionado botón solo persiste la información tal cual se
     * diligencia, esto quiere decir que Rigel no hara un calculo automatico de
     * la jornada.
     */
    public void guardarEdit() {
        if (validarHoras()) {
            MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
            return;
        }
        if (i_prgSerconMotivo == 0) {
            MovilidadUtil.addErrorMessage("Seleccione el motivo.");
            return;
        }
        if (MovilidadUtil.toSecs(prgSercon.getRealTimeOrigin()) > MovilidadUtil.toSecs("23:59:59")) {
            MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59.");
            return;
        }

        if (prgSercon.getObservaciones() != null) {
            if (prgSercon.getObservaciones().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite observación");
                return;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite observación");
            return;
        }
        if (validarRangoHoras(prgSercon)) {
            MovilidadUtil.addErrorMessage("El turno NO permite respetar las 12 horas de descanso reglamentarias");
            return;
        }
        //Tipo de calculo manual
        prgSercon.setTipoCalculo(1);
        prgSercon.setUsername(user.getUsername());
        prgSercon.setModificado(MovilidadUtil.fechaCompletaHoy());
        prgSercon.setPrgModificada(2);
        prgSercon.setUserGenera(user.getUsername());
        prgSercon.setAutorizado(1);
        prgSercon.setUserAutorizado(user.getUsername());
        prgSercon.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        prgSercon.setIdPrgSerconMotivo(new PrgSerconMotivo(i_prgSerconMotivo));
        String productionTime = timeProduction();
        prgSercon.setProductionTimeReal(productionTime);
        prgSerconEJB.edit(prgSercon);
        cargarDatos();
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
        PrimeFaces.current().executeScript("PF('LiqJornadaDialog').hide()");
        prgSercon = null;
    }

    /**
     * Método encargado de calcular el tiempo de producción de una jornada y
     * retornarlo como un tipo de dato String.
     *
     * Suma las horas generadas por cada parte de trabajo.
     *
     * @return Tiempo de producción calculado.
     */
    public String timeProduction() {
        int turnoI1;
        int turnoF1;
        //Convertir a enteros las horas todos los turnos
        turnoI1 = MovilidadUtil.toSecs(prgSercon.getRealTimeOrigin());
        turnoF1 = MovilidadUtil.toSecs(prgSercon.getRealTimeDestiny());
        int turnoI2 = MovilidadUtil.toSecs(prgSercon.getRealHiniTurno2());
        int turnoF2 = MovilidadUtil.toSecs(prgSercon.getRealHfinTurno2());
        int turnoI3 = MovilidadUtil.toSecs(prgSercon.getRealHiniTurno3());
        int turnoF3 = MovilidadUtil.toSecs(prgSercon.getRealHfinTurno3());

        //Calcular el tiempo de produccion de todos los turnos
        int produccionTurno1 = turnoF1 - turnoI1;
        int produccionTurno2 = turnoF2 - turnoI2;
        int produccionTurno3 = turnoF3 - turnoI3;
        //Calcular el tiempo total de produccion
        int produccionReal = produccionTurno1 + produccionTurno2 + produccionTurno3;

        int productionTimeProg = MovilidadUtil.toSecs(prgSercon.getProductionTime());
        int productionTimeRealTotal = 0;

        if (produccionReal < productionTimeProg || produccionReal > productionTimeProg) {
            productionTimeRealTotal = produccionReal;
        }

        return MovilidadUtil.toTimeSec(productionTimeRealTotal);
    }

    /**
     * Método encargado de persisitir en BD la gestión de la modificación de una
     * jornada.
     *
     * Invoca al método cargarCalcularDato(), responsable de calcular la
     * jornada.
     *
     * Es accionado desde la vista EditLiqJornada, al preccionar el botón
     * 'Guardar Calular'.
     */
    public void guardarCalcular() throws ParseException {
        if (UtilJornada.esPorPartes(prgSercon)) {
            if (validarHoras(prgSercon.getRealHiniTurno2(), prgSercon.getRealHfinTurno2())) {
                cargarDatos();
                MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio. Turno 2");
                return;
            }
            if (validarHoras(prgSercon.getRealHiniTurno3(), prgSercon.getRealHfinTurno3())) {
                cargarDatos();
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
        if (validarHoras()) {
            MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
            return;
        }
        if (i_prgSerconMotivo == 0) {
            MovilidadUtil.addErrorMessage("Seleccione el motivo.");
            return;
        }
        if (MovilidadUtil.toSecs(prgSercon.getRealTimeOrigin()) > MovilidadUtil.toSecs("23:59:59")) {
            MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59.");
            return;
        }
        if (prgSercon.getObservaciones() == null || prgSercon.getObservaciones().isEmpty()) {
            MovilidadUtil.addErrorMessage("Digite observación");
            return;
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
            if (validarHorasExtras(prgSercon)) {
                return;
            }
            if (validarMaxHorasExtrasSmanales(prgSercon)) {
                return;
            }
        }

        //Tipo de calculo automatico "2" Calculado por Rigel, "1" Calculado por el usuario
        prgSercon.setTipoCalculo(2);
        prgSercon.setUsername(user.getUsername());
        prgSercon.setModificado(MovilidadUtil.fechaCompletaHoy());
        prgSercon.setPrgModificada(1);
        prgSercon.setUserGenera(user.getUsername());
        prgSercon.setAutorizado(1);
        prgSercon.setUserAutorizado(user.getUsername());
        prgSercon.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        prgSercon.setIdPrgSerconMotivo(new PrgSerconMotivo(i_prgSerconMotivo));
        String productionTime = timeProduction();
        prgSercon.setProductionTimeReal(productionTime);
        if (UtilJornada.tipoLiquidacion()) {
            if (MovilidadUtil.isSunday(prgSercon.getFecha())) {
                List<PrgSerconLiqUtil> preCagarHorasDominicales = getINSTANCEJS().preCagarHorasDominicales(prgSercon.getFecha(),
                        prgSercon.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                        prgSercon.getIdEmpleado().getIdEmpleado(), cargarObjetoParaJar(prgSercon));
                PrgSerconLiqUtil dom = preCagarHorasDominicales.stream()
                        .filter(x -> Util.dateFormat(prgSercon.getFecha()).equals(Util.dateFormat(x.getFecha()))).findFirst().orElse(null);
                setValueFromPrgSerconJar(dom, prgSercon);
                prgSerconEJB.edit(prgSercon);

            } else {
                PrgSerconLiqUtil param = cargarObjetoParaJar(prgSercon);
                param.setFecha(prgSercon.getFecha());
                param.setIdEmpleado(new EmpleadoLiqUtil(prgSercon.getIdEmpleado().getIdEmpleado()));
                param.setIdGopUnidadFuncional(new GopUnidadFuncionalLiqUtil(prgSercon.getIdGopUnidadFuncional().getIdGopUnidadFuncional()));
                param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(prgSercon.getSercon(), prgSercon.getWorkTime())));
                System.out.println("work time: " + param.getWorkTime());
                ErrorPrgSercon errorPrgSercon
                        = getINSTANCEJS().recalcularHrsExtrasSmnl(param);
                System.out.println("errorPrgSercon.getLista().size()" + errorPrgSercon.getLista().size());
                if (errorPrgSercon.isStatus()) {
                    String msg = "Se excedió el máximo de horas extras diarias" + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
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
                System.out.println("Jornada liquidada: " + get.toString());
                setValueFromPrgSerconJar(get, prgSercon);
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
                cargarCalcularDatoPorPartes(prgSercon);
            } else {
                cargarCalcularDato(prgSercon);
            }
            prgSerconEJB.edit(prgSercon);
        }

        cargarDatos();
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
        PrimeFaces.current().executeScript("PF('LiqJornadaDialog').hide()");
        prgSercon = null;
    }

    /**
     * Método encargado de calcular y persistir en BD una jornda por medio del
     * submenu por medio de un clic derecho del datatable en la vista principal.
     *
     */
    public void calcularPrgSercon() throws ParseException {
        if (prgSercon == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (prgSercon.getLiquidado() != null && prgSercon.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida, la jornada se encuentra en estado de liquidado.");
            return;
        }
        if (prgSercon.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida, la jornada se encuentra en estado de borrado.");
            return;
        }
//        if (prgSercon.getPrgModificada() != null && prgSercon.getPrgModificada() == 2) {
//            MovilidadUtil.addErrorMessage("Opción no válida");
//            return;
//        }
        if (flagCalcularJornada()) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        UtilJornada.cargarParametrosJar();
        boolean realTime = validarHorasParaCalcular(prgSercon.getAutorizado(),
                prgSercon.getPrgModificada(), prgSercon.getRealTimeOrigin());
        if (UtilJornada.tipoLiquidacion()) {
            if (validarHorasExtrasFlexible(prgSercon, realTime)) {
                return;
            }
            if (validarMaxHorasExtrasSmanalesFlexible(prgSercon, realTime)) {
                return;
            }
        } else {
            if (validarHorasExtras(prgSercon)) {
                return;
            }
            if (validarMaxHorasExtrasSmanales(prgSercon)) {
                return;
            }
        }
        if (UtilJornada.tipoLiquidacion()) {
            if (MovilidadUtil.isSunday(prgSercon.getFecha())) {
                List<PrgSerconLiqUtil> preCagarHorasDominicales = getINSTANCEJS().preCagarHorasDominicales(prgSercon.getFecha(),
                        prgSercon.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                        prgSercon.getIdEmpleado().getIdEmpleado(), cargarObjetoParaJar(prgSercon));
                PrgSerconLiqUtil get = preCagarHorasDominicales.get(0);
                setValueFromPrgSerconJar(get, prgSercon);
                prgSerconEJB.edit(prgSercon);
            } else {
                String timeOrigin1 = prgSercon.getTimeOrigin();
                String timeDestiny1 = prgSercon.getTimeDestiny();
                String timeOrigin2 = prgSercon.getHiniTurno2();
                String timeDestiny2 = prgSercon.getHfinTurno2();
                String timeOrigin3 = prgSercon.getHiniTurno3();
                String timeDestiny3 = prgSercon.getHfinTurno3();
                if (prgSercon.getAutorizado() != null && prgSercon.getAutorizado() == 1) {
                    timeOrigin1 = prgSercon.getRealTimeOrigin();
                    timeDestiny1 = prgSercon.getRealTimeDestiny();
                    timeOrigin2 = prgSercon.getRealHiniTurno2();
                    timeDestiny2 = prgSercon.getRealHfinTurno2();
                    timeOrigin3 = prgSercon.getRealHiniTurno3();
                    timeDestiny3 = prgSercon.getRealHfinTurno3();
                }
                PrgSerconLiqUtil param = new PrgSerconLiqUtil();
                param.setFecha(prgSercon.getFecha());
                param.setTimeOrigin(timeOrigin1);
                param.setTimeDestiny(timeDestiny1);
                param.setHiniTurno2(timeOrigin2);
                param.setHfinTurno2(timeDestiny2);
                param.setHiniTurno3(timeOrigin3);
                param.setHfinTurno3(timeDestiny3);
                param.setIdEmpleado(new EmpleadoLiqUtil(prgSercon.getIdEmpleado().getIdEmpleado()));
                param.setIdGopUnidadFuncional(new GopUnidadFuncionalLiqUtil(prgSercon.getIdGopUnidadFuncional().getIdGopUnidadFuncional()));
                param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(prgSercon.getSercon(), prgSercon.getWorkTime())));
                ErrorPrgSercon errorPrgSercon
                        = getINSTANCEJS().recalcularHrsExtrasSmnl(param);
                if (errorPrgSercon.isStatus()) {
                    String msg = "Se excedió el máximo de horas extras diarias" + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
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
                prgSerconEJB.edit(prgSercon);
                list = errorPrgSercon.getLista().stream()
                        .filter(x -> jornadaFechaDiferente(prgSercon.getFecha(), x))
                        .collect(Collectors.toList());
                prgSerconEJB.updatePrgSerconFromList(list, 0);

                Date friday = com.aja.jornada.util.Util.getDiaSemana(prgSercon.getFecha(), Calendar.FRIDAY);

                //La jornada a modificar es un Descanso
                if (UtilJornada.workTimeJornadaVirtual(prgSercon.getSercon())
                        && !Util.dateFormat(friday).equals(Util.dateFormat(prgSercon.getFecha()))) {
                    Date lunes = com.aja.jornada.util.Util.getDiaSemana(prgSercon.getFecha(), Calendar.MONDAY);
                    Date domingo = com.aja.jornada.util.Util.sumarDias(lunes, -1);

                    List<PrgSerconLiqUtil> preCagarHorasDominicales = getINSTANCEJS().preCagarHorasDominicales(domingo,
                            prgSercon.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                            prgSercon.getIdEmpleado().getIdEmpleado(), null);

                    prgSerconEJB.updatePrgSerconFromList(preCagarHorasDominicales, 0);

                };

            }
        } else {
            prgSercon.setDominicalCompDiurnas(null);
            prgSercon.setDominicalCompNocturnas(null);
            if (UtilJornada.esPorPartes(prgSercon)) {
                cargarCalcularDatoPorPartes(prgSercon);
            } else {
                cargarCalcularDato(prgSercon);
            }
            prgSerconEJB.edit(prgSercon);
        }
        MovilidadUtil.addSuccessMessage("Operación exitosa.");
    }

    public String fortmatFecha(int valor) {
        if (valor == 0) {
            return Util.dateFormat(fechaDesde);
        } else {
            return Util.dateFormat(fechaHasta);
        }
    }

    public PrgSercon caluleOnePart(String s_horaInicio, String s_horaFin, Date fechaParam, String totalHorasLaborales) {
        calculator.reset();
        PrgSercon parteJornada = new PrgSercon();
        int ultimaHoraDia = MovilidadUtil.toSecs(calculator.fin_dia);
        int i_horaIniSec;
        int i_horaFinSec;

        i_horaIniSec = MovilidadUtil.toSecs(s_horaInicio);
        i_horaFinSec = MovilidadUtil.toSecs(s_horaFin);

        if (i_horaIniSec <= 0 && i_horaFinSec <= 0) {
            parteJornada.setDiurnas(calculator.getDiurnas());
            parteJornada.setNocturnas(calculator.getNocturnas());
            parteJornada.setExtraDiurna(calculator.getExtra_diurna());
            parteJornada.setExtraNocturna(calculator.getExtra_nocturna());
            parteJornada.setFestivoDiurno(calculator.getFestivo_diurno());
            parteJornada.setFestivoNocturno(calculator.getFestivo_nocturno());
            parteJornada.setFestivoExtraDiurno(calculator.getFestivo_extra_diurno());
            parteJornada.setFestivoExtraNocturno(calculator.getFestivo_extra_nocturno());
            return parteJornada;
        }

        if (i_horaIniSec > ultimaHoraDia) {
            Date fecha = MovilidadUtil.sumarDias(fechaParam, 1);
            ParamFeriado pf = paraFeEJB.findByFecha(fecha);
            if (pf == null) {
                JornadaUtil.calcular("H", s_horaInicio, "H", s_horaFin, totalHorasLaborales);
            } else {
                JornadaUtil.calcular("F", s_horaInicio, "F", s_horaFin, totalHorasLaborales);
            }
        } else {

            ParamFeriado pffHI = paraFeEJB.findByFecha(fechaParam);
            ParamFeriado pffHF = pffHI;
            Date fecha = fechaParam;
            if (i_horaFinSec > ultimaHoraDia) {
                fecha = MovilidadUtil.sumarDias(fechaParam, 1);
                pffHF = paraFeEJB.findByFecha(fecha);
            }
            if (pffHI == null && pffHF == null) {
                JornadaUtil.calcular("H", s_horaInicio, "H", s_horaFin, totalHorasLaborales);
            }
            if (pffHI != null && pffHF == null) {
                JornadaUtil.calcular("F", s_horaInicio, "H", s_horaFin, totalHorasLaborales);
            }
            if (pffHI != null && pffHF != null) {
                JornadaUtil.calcular("F", s_horaInicio, "F", s_horaFin, totalHorasLaborales);
            }
            if (pffHI == null && pffHF != null) {
                JornadaUtil.calcular("H", s_horaInicio, "F", s_horaFin, totalHorasLaborales);
            }
        }
        parteJornada.setDiurnas(calculator.getDiurnas());
        parteJornada.setNocturnas(calculator.getNocturnas());
        parteJornada.setExtraDiurna(calculator.getExtra_diurna());
        parteJornada.setExtraNocturna(calculator.getExtra_nocturna());
        parteJornada.setFestivoDiurno(calculator.getFestivo_diurno());
        parteJornada.setFestivoNocturno(calculator.getFestivo_nocturno());
        parteJornada.setFestivoExtraDiurno(calculator.getFestivo_extra_diurno());
        parteJornada.setFestivoExtraNocturno(calculator.getFestivo_extra_nocturno());
        return parteJornada;
    }

    /**
     * Método responsable de invocar al método calculaJ para calcular las horas
     * a liquidar(diurnas,nocturnas,extra diurna, extra nocturna, festivo
     * diurno, festivo nocturno, festivo extra diurno, festivo extra nocturno)
     * de una jornada de acuerdo a una hora inicio y hora fin de turno.
     *
     * Persiste la información en BD.
     *
     * Las jornadas o turnos que pasan por este método son almacenadas con el
     * identificador 2 en el atributo 'tipoCalculo', lo cual significa que el
     * tipo de calculo es automatico, hecho por rigel.
     *
     * Invoca al método calculaJ, responsable de calcular la jornada.
     *
     * @param ps
     * @return
     */
    public PrgSercon cargarCalcularDato(PrgSercon ps) {
        calculator.reset();
        int ultimaHoraDia = MovilidadUtil.toSecs(calculator.fin_dia);
        int horaIniSec;
        int horaFinSec;
        String timeOrigin;
        String timeDestiny;
        String workTime = MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(ps.getSercon(), ps.getWorkTime()));
        if (ps.getRealTimeOrigin() != null && ps.getRealTimeDestiny() != null) {
            if (ps.getRealTimeOrigin().equals(calculator.hr_cero) && ps.getRealTimeDestiny().equals(calculator.hr_cero)) {
                ps.setDiurnas(calculator.getDiurnas());
                ps.setNocturnas(calculator.getNocturnas());
                ps.setExtraDiurna(calculator.getExtra_diurna());
                ps.setExtraNocturna(calculator.getExtra_nocturna());
                ps.setFestivoDiurno(calculator.getFestivo_diurno());
                ps.setFestivoNocturno(calculator.getFestivo_nocturno());
                ps.setFestivoExtraDiurno(calculator.getFestivo_extra_diurno());
                ps.setFestivoExtraNocturno(calculator.getFestivo_extra_nocturno());
                //Tipo de calculo automatico
                ps.setTipoCalculo(2);
                return ps;
            }
        } else {
            return ps;
        }

        if (ps.getAutorizado() != null) {
            if (ps.getRealTimeOrigin() != null && ps.getRealTimeDestiny() != null && ps.getAutorizado() == 1) {
                timeOrigin = ps.getRealTimeOrigin();
                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
                timeDestiny = ps.getRealTimeDestiny();
                horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
            } else {
                return ps;
            }
        } else {
            return ps;
        }
        if (horaIniSec > ultimaHoraDia) {
            Date fecha = MovilidadUtil.sumarDias(ps.getFecha(), 1);
            ParamFeriado pf = paraFeEJB.findByFecha(fecha);
            if (pf == null) {
                JornadaUtil.calcular("H", timeOrigin, "H", timeDestiny, workTime);

            } else {
                JornadaUtil.calcular("F", timeOrigin, "F", timeDestiny, workTime);

            }
        } else {

            ParamFeriado pffHI = paraFeEJB.findByFecha(ps.getFecha());
            ParamFeriado pffHF = pffHI;
            Date fecha = ps.getFecha();
            if (horaFinSec > ultimaHoraDia) {
                fecha = MovilidadUtil.sumarDias(ps.getFecha(), 1);
                pffHF = paraFeEJB.findByFecha(fecha);
            }
            if (pffHI == null && pffHF == null) {
                JornadaUtil.calcular("H", timeOrigin, "H", timeDestiny, workTime);
            }
            if (pffHI != null && pffHF == null) {
                JornadaUtil.calcular("F", timeOrigin, "H", timeDestiny, workTime);
            }
            if (pffHI != null && pffHF != null) {
                JornadaUtil.calcular("F", timeOrigin, "F", timeDestiny, workTime);
            }
            if (pffHI == null && pffHF != null) {
                JornadaUtil.calcular("H", timeOrigin, "F", timeDestiny, workTime);
            }
        }
        ps.setDiurnas(calculator.getDiurnas());
        ps.setNocturnas(calculator.getNocturnas());
        ps.setExtraDiurna(calculator.getExtra_diurna());
        ps.setExtraNocturna(calculator.getExtra_nocturna());
        ps.setFestivoDiurno(calculator.getFestivo_diurno());
        ps.setFestivoNocturno(calculator.getFestivo_nocturno());
        ps.setFestivoExtraDiurno(calculator.getFestivo_extra_diurno());
        ps.setFestivoExtraNocturno(calculator.getFestivo_extra_nocturno());
        //Tipo de calculo automatico
        ps.setTipoCalculo(2);

        return ps;
    }

    /**
     * Método encargado de liquidar una jornada, accionado la opción liquidar
     * del submenu contextual invocado con clic derecho sobre el registro en
     * cuestión, se le seteará un '1' en el campo liquidado.
     */
    @Transactional
    public void liquidarUno() {
        if (prgSercon == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (prgSercon.getLiquidado() != null && prgSercon.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        flagLiquidarJornada();
        if (flagLiquidar) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        try {
            int up = prgSerconEJB.liquidarPorId(prgSercon.getIdPrgSercon(), user.getUsername());
            if (up > 0) {
                PrgSercon find = prgSerconEJB.find(prgSercon.getIdPrgSercon());
                List<PrgSercon> lst = new ArrayList<>();
                lst.add(find);
                liquidarJornadaBean.prgSerconDetalleBuild(lst, user.getUsername());
                MovilidadUtil.addSuccessMessage("Jornada del operador " + prgSercon.getIdEmpleado().getCodigoTm() + " liquidada Exitosamente");
                prgSercon.setLiquidado(1);
            } else {
                MovilidadUtil.addAdvertenciaMessage("Jornada del operador " + prgSercon.getIdEmpleado().getCodigoTm() + " no liquidada");
                prgSercon.setLiquidado(0);
            }
            cargarDatos();

        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error inesperado al liquidar jornada, contacte al administrador");
            prgSercon.setLiquidado(0);
        }
    }

    public void flagCalculo() {
        if (prgSercon.getAutorizado() == null) {
            flagCalculo = false;
            return;
        }
        flagCalculo = (prgSercon.getAutorizado() == 1);
    }

    public void flagEliminarNomina() {
        flagEliminar = prgSercon.getNominaBorrada() == 0 && (prgSercon.getLiquidado() == null || prgSercon.getLiquidado() == 0);
    }

    public void flagDesEliminarNomina() {
        flagDesliminar = prgSercon.getNominaBorrada() == 1 && prgSercon.getLiquidado() == 0;
    }

    public void sabadoDomingo(PrgSercon p) {
        flagSabDom = MovilidadUtil.isDay(p.getFecha(), Calendar.SATURDAY)
                || MovilidadUtil.isDay(p.getFecha(), Calendar.SUNDAY);
    }

    public boolean flagCalcularJornada() {
        flagCalcular = (!flagCalculo || prgSercon.getNominaBorrada() == 1 || prgSercon.getLiquidado() == 1);
        return flagCalcular;
    }

    public void flagLiquidarJornada() {
        flagLiquidar = prgSercon.getLiquidado() == 1 || (prgSercon.getAutorizado() != null && prgSercon.getAutorizado() == -1);
    }

    public void flagModificarJornada() {
        flagModificar = prgSercon.getNominaBorrada() == 1 || prgSercon.getLiquidado() == 1;
    }

    public void flagCambioEmpleado() {
        flagCambioEmpleado = prgSercon.getLiquidado() == 1;
    }

    /**
     * Método responsable de cargar la variable prgSercon con el registro
     * seleccionado desde el datatable de la vista principal y acondicionar el
     * comportamiendo de las opciones del clic derecho de acuerdo al estado de
     * la jorndad seleccionada.
     *
     * @param event
     * @throws Exception
     */
    public void onRowlClckSelect(final SelectEvent event) throws Exception {
        setPrgSercon((PrgSercon) event.getObject());
        setPrgSercon(prgSerconEJB.find(prgSercon.getIdPrgSercon()));
        flagCalculo();
        flagEliminarNomina();
        flagDesEliminarNomina();
        sabadoDomingo(prgSercon);
        flagLiquidarJornada();
        flagCalcularJornada();
        flagModificarJornada();
        flagCambioEmpleado();
    }

    /**
     * Método encargado de convertiti una jornada cualquiera a jornada festiva
     * sin compensatorio.
     *
     * @param ps objeto jornada a que se utiliza para realizar el ajuste.
     * @param caso vairable int que lleva como bandera que tipo de jornada se va
     * a ajustar, ya sea, de día hábil a festivo o de festivo a hábil, de hábil
     * a hábil y de festivo a festivo.
     * @return devuelve el objeto de jornada previamente ajustado.
     */
    public PrgSercon convertirFestivoSinComp(PrgSercon ps, int caso) {

        int ultimaHoraDia = MovilidadUtil.toSecs(calculator.fin_dia);
        int horaIniSec;
        int horaFinSec;
        String timeOrigin;
        String timeDestiny;
        String workTime = MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(ps.getSercon(), ps.getWorkTime()));
        if (ps.getDominicalCompDiurnas() != null) {
            return null;
        }

        if (ps.getAutorizado() != null && ps.getAutorizado() == 1) {
            if (ps.getRealTimeOrigin() != null) {
                timeOrigin = ps.getRealTimeOrigin();
                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
                timeDestiny = ps.getRealTimeDestiny();
                horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
            } else {
                timeOrigin = ps.getTimeOrigin();
                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
                timeDestiny = ps.getTimeDestiny();
                horaFinSec = MovilidadUtil.toSecs(timeDestiny);
            }
        } else if (ps.getPrgModificada() != null && ps.getPrgModificada() == 1) {
            timeOrigin = ps.getRealTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = ps.getRealTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
        } else {
            timeOrigin = ps.getTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = ps.getTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        }
        int diurnas_dom = 0;
        int nocturnas_dom = 0;

        //La jornada conmienza un domingo y termina en festivo (HF)
        //La jornda fue el mismo día y NO es fetivo (HH)
        if (caso == 2 || caso == 3) {
//            System.out.println("Caso 2 Y1SUS");
            ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(
                    ps.getDiurnas()) + MovilidadUtil.toSecs(ps.getFestivoDiurno())));
            ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(
                    ps.getNocturnas()) + MovilidadUtil.toSecs(ps.getFestivoNocturno())));
            ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(
                    MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(ps.getExtraNocturna())));

            ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(
                    MovilidadUtil.toSecs(ps.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(ps.getExtraDiurna())));
            ps.setExtraDiurna(calculator.hr_cero);
            ps.setExtraNocturna(calculator.hr_cero);
            ps.setDiurnas(calculator.hr_cero);
            ps.setNocturnas(calculator.hr_cero);

            return ps;
        }

//        Si la jornada comienza en hábil y termina un dia domingo (HH)
//        Si la jornada comienza en domingo y termina un dia hábil (HH)
        if (caso == 4 || caso == 5) {
//            System.out.println("Caso 4 Y1SUS");
            if (MovilidadUtil.toSecs(timeOrigin) < MovilidadUtil.toSecs(UtilJornada.getIniNocturna())) {
                diurnas_dom = MovilidadUtil.toSecs(UtilJornada.getIniNocturna()) - MovilidadUtil.toSecs(timeOrigin);
                nocturnas_dom = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin) - diurnas_dom;

                if (MovilidadUtil.toSecs(ps.getExtraNocturna()) > 0) {

                    int horasDiaSeleccionado = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin);

                    if (horasDiaSeleccionado >= MovilidadUtil.toSecs(workTime)) {
                        String horasExtrasComp = MovilidadUtil.toTimeSec(horasDiaSeleccionado - MovilidadUtil.toSecs(workTime));
                        ps.setFestivoExtraNocturno(horasExtrasComp);
                        if (MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) >= MovilidadUtil.toSecs(ps.getExtraNocturna())) {
                            ps.setExtraDiurna(calculator.hr_cero);
                        } else {
                            ps.setExtraNocturna(MovilidadUtil.toTimeSec(
                                    MovilidadUtil.toSecs(ps.getExtraNocturna())
                                    - MovilidadUtil.toSecs(ps.getFestivoExtraNocturno())));
                        }
                        ps.setFestivoDiurno(ps.getDiurnas());
                        ps.setFestivoNocturno(ps.getNocturnas());
                        ps.setNocturnas(calculator.hr_cero);
                        ps.setDiurnas(calculator.hr_cero);
                    } else {
                        ps.setFestivoDiurno(MovilidadUtil.toTimeSec(diurnas_dom));
                        ps.setFestivoNocturno(MovilidadUtil.toTimeSec(nocturnas_dom));
                    }
                } else {
                    ps.setExtraNocturna(calculator.hr_cero);
                }

            } else {
//              System.out.println("Sin Diurnas");
                nocturnas_dom = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin);
                ps.setFestivoDiurno(calculator.hr_cero);
                ps.setFestivoNocturno(MovilidadUtil.toTimeSec(nocturnas_dom));
                ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) - nocturnas_dom));
            }
            return ps;
        }

        if (caso == 0) {
//                System.out.println("Caso 0 Y1SUS");

            ps.setDominicalCompDiurnaExtra(null);
            ps.setDominicalCompNocturnaExtra(null);
            ps.setDominicalCompDiurnas(null);
            ps.setDominicalCompNocturnas(null);
            return ps;
        }
        return null;
    }

    public PrgSercon convertirFestivoConComp(PrgSercon ps, int caso) {

        int ultimaHoraDia = MovilidadUtil.toSecs(calculator.fin_dia);
        int horaIniSec;
        int horaFinSec;
//        String timeOrigin;
//        String timeDestiny;
        if (ps.getDominicalCompDiurnas() != null) {
            return null;
        }
//
//        if (ps.getAutorizado() != null && ps.getAutorizado() == 1) {
//            if (ps.getRealTimeOrigin() != null) {
//                timeOrigin = ps.getRealTimeOrigin();
//                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
//                timeDestiny = ps.getRealTimeDestiny();
//                horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
//            } else {
//                timeOrigin = ps.getTimeOrigin();
//                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
//                timeDestiny = ps.getTimeDestiny();
//                horaFinSec = MovilidadUtil.toSecs(timeDestiny);
//            }
//        } else if (ps.getPrgModificada() != null && ps.getPrgModificada() == 1) {
//            timeOrigin = ps.getRealTimeOrigin();
//            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
//            timeDestiny = ps.getRealTimeDestiny();
//            horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
//        } else {
//            timeOrigin = ps.getTimeOrigin();
//            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
//            timeDestiny = ps.getTimeDestiny();
//            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
//        }
//        int num_para_trabajar = 0;
//        int diurnas_dom = 0;
//        int nocturnas_dom = 0;
//        int diurnas_dom_extra = 0;
//        int nocturnas_dom_extra = 0;

        //La jornada conmienza un festivo y termina en domingo (FH)
//        if (caso == 1) {
//            System.out.println("Caso 1 Y1SUS");
//
//            ps.setDominicalCompDiurnas(ps.getFestivoDiurno());
//            ps.setDominicalCompNocturnas(ps.getFestivoNocturno());
//            ps.setFestivoDiurno(JornadaUtil.hr_cero);
//            ps.setFestivoNocturno(JornadaUtil.hr_cero);
//
//            return ps;
//        }
        //La jornada conmienza un domingo y termina en festivo (HF)
//        if (caso == 2) {
//            System.out.println("Caso 2 Y1SUS");
        ps.setDominicalCompDiurnas(ps.getDiurnas());
        ps.setDominicalCompNocturnas(ps.getNocturnas());
        ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(
                MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(ps.getExtraNocturna())));

        ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(
                MovilidadUtil.toSecs(ps.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(ps.getExtraDiurna())));
        ps.setExtraDiurna(calculator.hr_cero);
        ps.setExtraNocturna(calculator.hr_cero);
        ps.setDiurnas(calculator.hr_cero);
        ps.setNocturnas(calculator.hr_cero);

        return ps;
//        }
        //La jornda fue el mismo día y NO es fetivo (HH)
//        if (caso == 3) {
//            System.out.println("Caso 3 Y1SUS");
//            ps.setDominicalCompDiurnas(ps.getDiurnas());
//            ps.setDominicalCompNocturnas(ps.getNocturnas());
//            ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(
//                    ps.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(ps.getExtraNocturna())));
//
//            ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(
//                    ps.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(ps.getExtraDiurna())));
//
//            ps.setExtraDiurna(JornadaUtil.hr_cero);
//            ps.setExtraNocturna(JornadaUtil.hr_cero);
//            ps.setDiurnas(JornadaUtil.hr_cero);
//            ps.setNocturnas(JornadaUtil.hr_cero);
//            return ps;
//        }
//        Si la jornada comienza en hábil y termina un dia domingo (HH)
//        Si la jornada comienza en domingo y termina un dia hábil (HH)
//        if (caso == 4 || caso == 5) {
//            System.out.println("Caso 4 Y1SUS");
//            if (MovilidadUtil.toSecs(timeOrigin) < MovilidadUtil.toSecs(JornadaUtil.getIniNocturna())) {
//                diurnas_dom = MovilidadUtil.toSecs(JornadaUtil.getIniNocturna()) - MovilidadUtil.toSecs(timeOrigin);
//                nocturnas_dom = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin) - diurnas_dom;
//
//                if (MovilidadUtil.toSecs(ps.getExtraNocturna()) > 0) {
//
//                    int horasDominical = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin);
//
//                    if (horasDominical >= MovilidadUtil.toSecs("08:00:00")) {
//                        String horasExtrasComp = MovilidadUtil.toTimeSec(horasDominical - MovilidadUtil.toSecs("08:00:00"));
//                        ps.setFestivoExtraNocturno(horasExtrasComp);
//                        if (MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) >= MovilidadUtil.toSecs(ps.getExtraNocturna())) {
//                            ps.setExtraDiurna(JornadaUtil.hr_cero);
//                        } else {
//                            ps.setExtraNocturna(MovilidadUtil.toTimeSec(
//                                    MovilidadUtil.toSecs(ps.getExtraNocturna())
//                                    - MovilidadUtil.toSecs(ps.getFestivoExtraNocturno())));
//                        }
//                        ps.setDominicalCompDiurnas(ps.getDiurnas());
//                        ps.setDominicalCompNocturnas(ps.getNocturnas());
//                        ps.setNocturnas(JornadaUtil.hr_cero);
//                        ps.setDiurnas(JornadaUtil.hr_cero);
//                    } else {
//                        ps.setDominicalCompDiurnas(MovilidadUtil.toTimeSec(diurnas_dom));
//                        ps.setDominicalCompNocturnas(MovilidadUtil.toTimeSec(nocturnas_dom));
//                    }
//                } else {
//                    ps.setExtraNocturna(JornadaUtil.hr_cero);
//                }
//
//            } else {
////              System.out.println("Sin Diurnas");
//                nocturnas_dom = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin);
//                ps.setDominicalCompDiurnas(JornadaUtil.hr_cero);
//                ps.setDominicalCompNocturnas(MovilidadUtil.toTimeSec(nocturnas_dom));
//                ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) - nocturnas_dom));
//            }
//            return ps;
//        }

        //La jornda fue el mismo día y es fetivo (FF)
//        if (caso == 6) {
//            System.out.println("Caso 6 Y1SUS");
//            ps.setDominicalCompDiurnas(ps.getFestivoDiurno());
//            ps.setDominicalCompNocturnas(ps.getFestivoNocturno());
//            ps.setFestivoDiurno(JornadaUtil.hr_cero);
//            ps.setFestivoNocturno(JornadaUtil.hr_cero);
//            return ps;
//        }
//        if (caso == 0) {
//                System.out.println("Caso 0 Y1SUS");
//            ps.setDominicalCompDiurnaExtra(null);
//            ps.setDominicalCompNocturnaExtra(null);
//            ps.setDominicalCompDiurnas(null);
//            ps.setDominicalCompNocturnas(null);
//            return ps;
//        }
//        return null;
    }

    public void recalcularJornada(PrgSercon ps) {

//        int caso = tieneFestivo(prgSercon);
        PrgSercon objPersistir = convertirFestivoConComp(prgSercon, 0);
//        PrgSercon objPersistir = convertirFestivoConComp(prgSercon, caso);
        if (objPersistir != null) {
            prgSerconEJB.edit(objPersistir);
        }
        MovilidadUtil.addSuccessMessage("Acción completada.");
    }

    /**
     * Método responsable de invocar los métodos tieneFestivo y
     * convertirFestivoSinComp para calcular y posteriormente persisitir la
     * infomacion en BD
     *
     * @param ps objeto jornada que sera intervenido.
     */
    public void recalcularJornadaDSC(PrgSercon ps) {

        int caso = tieneFestivo(prgSercon);
        PrgSercon objPersistir = convertirFestivoSinComp(prgSercon, caso);
        if (objPersistir != null) {
            prgSerconEJB.edit(objPersistir);
        }
        MovilidadUtil.addSuccessMessage("Acción completada.");
    }

    /**
     * método encargado de identificar que tipo de jornada se va a ajustar, ya
     * sea, de día hábil a festivo o de festivo a hábil, de hábil a hábil y de
     * festivo a festivo.
     *
     * @param ps objeto jornada a verificar.
     * @return devuelve valor int con el numero asociado al caso que presenta la
     * jornada.
     */
    public int tieneFestivo(PrgSercon ps) {
        int ultimaHoraDia = MovilidadUtil.toSecs(calculator.fin_dia);
        int horaIniSec;
        int horaFinSec;
        String timeOrigin;
        String timeDestiny;

        if (ps.getAutorizado() != null && ps.getAutorizado() == 1) {
            if (ps.getRealTimeOrigin() != null) {
                timeOrigin = ps.getRealTimeOrigin();
                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
                timeDestiny = ps.getRealTimeDestiny();
                horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
            } else {
                timeOrigin = ps.getTimeOrigin();
                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
                timeDestiny = ps.getTimeDestiny();
                horaFinSec = MovilidadUtil.toSecs(timeDestiny);
            }
        } else if (ps.getPrgModificada() != null && ps.getPrgModificada() == 1) {
            timeOrigin = ps.getRealTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = ps.getRealTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
        } else {
            timeOrigin = ps.getTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = ps.getTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        }
        ParamFeriado pffHI = paraFeEJB.findByFecha(ps.getFecha());
        ParamFeriado pffHF = pffHI;
        Date fecha = ps.getFecha();
        if (horaFinSec > ultimaHoraDia) {
            fecha = MovilidadUtil.sumarDias(ps.getFecha(), 1);
            pffHF = paraFeEJB.findByFecha(fecha);
        }

//        Si ja jornada es el mismo día y es festivo (FF)
        if (pffHI != null && fecha.equals(ps.getFecha())) {
            return 6;
        }
//        Si ja jornada es el mismo día y NO es festivo (HH)
        if (pffHI == null && fecha.equals(ps.getFecha())) {
            return 3;
        }
//        Si ja jornada conmienza un dia festivo y termina el domingo (FH)
        if (pffHI != null && MovilidadUtil.isSunday(fecha)) {
            return 1;
        }
//        Si la jornada comineza un domingo y termina en un festivo (HF)
        if ((pffHF != null && MovilidadUtil.isSunday(ps.getFecha()))) {
            return 2;
        }
//        Si la jornada comienza en hábil y termina un dia domingo (HH)
        if (MovilidadUtil.isSunday(fecha) && !MovilidadUtil.isSunday(ps.getFecha())) {
            return 4;
        }
//        Si la jornada comienza en domingo y termina un dia hábil (HH)
        if (!MovilidadUtil.isSunday(fecha) && MovilidadUtil.isSunday(ps.getFecha())) {
            return 5;
        }
        return 0;
    }

    /**
     * Método encargado de convertir una jornada a festivo dominical con
     * compensatorio.
     *
     * Invoca al método recalcularJornada, el cual es el responsable de
     * recalcular la jornada y persistir la información de BD.
     *
     * Invoca al método sabadoDomingo, responsable de identificar si la jornada
     * en cuestión aplica para un día sabado o domingo.
     */
    public void convertirHorasDCC() {
        if (prgSercon == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (prgSercon.getLiquidado() != null && prgSercon.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (prgSercon.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        sabadoDomingo(prgSercon);
        if (!flagSabDom) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        recalcularJornada(prgSercon);
    }

    /**
     * Método responsable de convertir una jornada de día hábil a una jornda con
     * horas Festivas y persistir la información en BD.
     *
     * Invoca al método sabadoDomingo, responsable de identificar si la jornada
     * en cuestión aplica para un día sabado o domingo.
     */
    public void convertirHoras() {
        if (prgSercon == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (prgSercon.getLiquidado() != null && prgSercon.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (prgSercon.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        sabadoDomingo(prgSercon);
        if (!flagSabDom) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        try {
            int festivoDiurno = 0;
            int festivoNocturno = 0;
            int festivoExtraDiurno = 0;
            int festivoExtraNocturna = 0;
            if (prgSercon.getDominicalCompDiurnas() == null) {
                festivoDiurno = MovilidadUtil.toSecs(prgSercon.getDiurnas())
                        + MovilidadUtil.toSecs(prgSercon.getFestivoDiurno());
                festivoNocturno = MovilidadUtil.toSecs(prgSercon.getNocturnas())
                        + MovilidadUtil.toSecs(prgSercon.getFestivoNocturno());
                festivoExtraDiurno = MovilidadUtil.toSecs(prgSercon.getExtraDiurna())
                        + MovilidadUtil.toSecs(prgSercon.getFestivoExtraDiurno());
                festivoExtraNocturna = MovilidadUtil.toSecs(prgSercon.getExtraNocturna())
                        + MovilidadUtil.toSecs(prgSercon.getFestivoExtraNocturno());
            } else {
                festivoDiurno = MovilidadUtil.toSecs(prgSercon.getFestivoDiurno())
                        + MovilidadUtil.toSecs(prgSercon.getDominicalCompDiurnas());
                festivoNocturno = MovilidadUtil.toSecs(prgSercon.getFestivoNocturno())
                        + MovilidadUtil.toSecs(prgSercon.getDominicalCompNocturnas());
                festivoExtraDiurno = MovilidadUtil.toSecs(prgSercon.getExtraDiurna())
                        + MovilidadUtil.toSecs(prgSercon.getFestivoExtraDiurno());
                festivoExtraNocturna = MovilidadUtil.toSecs(prgSercon.getExtraNocturna())
                        + MovilidadUtil.toSecs(prgSercon.getFestivoExtraNocturno());

            }
            prgSercon.setDiurnas(calculator.hr_cero);
            prgSercon.setNocturnas(calculator.hr_cero);
            prgSercon.setExtraDiurna(calculator.hr_cero);
            prgSercon.setExtraNocturna(calculator.hr_cero);
            prgSercon.setDominicalCompDiurnas(calculator.hr_cero);
            prgSercon.setDominicalCompNocturnas(calculator.hr_cero);
            prgSercon.setFestivoDiurno(MovilidadUtil.toTimeSec(festivoDiurno));
            prgSercon.setFestivoNocturno(MovilidadUtil.toTimeSec(festivoNocturno));
            prgSercon.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(festivoExtraDiurno));
            prgSercon.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(festivoExtraNocturna));
            prgSerconEJB.edit(prgSercon);
            MovilidadUtil.addSuccessMessage("Se cambiaron las horas exitosamente");

        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al convertir horas");
        }
    }

    /**
     * Método responsable de convertir una jornadas de día hábil a una jorndas
     * con horas Festivas y persistir la información en BD.
     *
     * Invoca al método sabadoDomingo, responsable de identificar si la jornada
     * en cuestión aplica para un día sabado o domingo.
     */
    public void convertirHorasMasivo() {
        cargarDatos();
        for (PrgSercon ps : prglist) {
            sabadoDomingo(ps);
            try {
                if (flagSabDom) {
                    int festivoDiurno = MovilidadUtil.toSecs(ps.getDiurnas()) + MovilidadUtil.toSecs(ps.getFestivoDiurno());
                    int festivoNocturno = MovilidadUtil.toSecs(ps.getNocturnas()) + MovilidadUtil.toSecs(ps.getFestivoNocturno());
                    int festivoExtraDiurno = MovilidadUtil.toSecs(ps.getExtraDiurna()) + MovilidadUtil.toSecs(ps.getFestivoExtraDiurno());
                    int festivoExtraNocturna = MovilidadUtil.toSecs(ps.getExtraNocturna()) + MovilidadUtil.toSecs(ps.getFestivoExtraNocturno());

                    ps.setFestivoDiurno(MovilidadUtil.toTimeSec(festivoDiurno));
                    ps.setFestivoNocturno(MovilidadUtil.toTimeSec(festivoNocturno));
                    ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(festivoExtraDiurno));
                    ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(festivoExtraNocturna));

                    ps.setDiurnas(calculator.hr_cero);
                    ps.setNocturnas(calculator.hr_cero);
                    ps.setExtraDiurna(calculator.hr_cero);
                    ps.setExtraNocturna(calculator.hr_cero);
                    if (ps.getLiquidado() != 1) {
                        prgSerconEJB.edit(ps);
                    }
                }
            } catch (Exception e) {
                MovilidadUtil.addErrorMessage("Error al convertir horas");
            }
        }
        cargarDatos();
        MovilidadUtil.addSuccessMessage("Se cambiaron las horas exitosamente");

    }

    /**
     * Método responsable de preparar la variables para la gestión de cambio de
     * empleado. Carga la lista de motivos que se presentaran en el formulario.
     *
     * visualiza el formulario donde se trabajará.
     */
    public void prepareCambioEmpleado() {
        if (prgSercon == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (prgSercon.getLiquidado() != null && prgSercon.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (prgSercon.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        emplPrgSercon = null;
        codigoTransMi = "";
        nombreEmpleado = "";
        empleadoPrgSercon = new Empleado(prgSercon.getIdEmpleado().getIdEmpleado());
        if (prgSercon.getIdPrgSerconMotivo() != null) {
            i_prgSerconMotivo = prgSercon.getIdPrgSerconMotivo().getIdPrgSerconMotivo();
        }
        prgSerconMotivoList = serconMotivoEJB.findAllEstadoRegistro();
        MovilidadUtil.openModal("cambioEmplWV");
    }

    /**
     * Método responsable de invocar los metodos guardarcambioEmplTransactional:
     * Sirve para persisitir en BD la gestión de cambio de empleado.
     * cargarDatos, cargar la tabla con la data nueva.
     */
    public void guardarcambioEmpl() throws ParseException {
        if (i_prgSerconMotivo == 0) {
            MovilidadUtil.addErrorMessage("No se a seleccionado el motivo");
            return;
        }
        if (emplPrgSercon == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado empleado para cambio");
            return;
        }
        if (prgSercon.getObservaciones() == null || prgSercon.getObservaciones().isEmpty()) {
            MovilidadUtil.addErrorMessage("Digite observación");
            return;
        }
        if (validarRangoHorasCambioUnoAUno(prgSercon, emplPrgSercon)) {
            MovilidadUtil.addErrorMessage("El turno NO permite respetar las 12 horas de descanso reglamentarias");
            return;
        }
        if (validarMaxHorasExtrasSmanal(prgSercon, emplPrgSercon)) {
            return;
        }
        guardarcambioEmplTransactional();
        cargarDatos();
    }

    /**
     * Método reponsable de persistir en BD la gestion del proceso de cambio de
     * empleado.
     *
     * Cierra la ventana modal, al finalizar la persistencia.
     */
    @Transactional
    public void guardarcambioEmplTransactional() {

        /**
         * Objeto jornada seleccionada por el usuario.
         */
        prgSercon.setUserGenera(user.getUsername());
        prgSercon.setUserAutorizado(user.getUsername());
        prgSercon.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        prgSercon.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        prgSercon.setIdPrgSerconMotivo(new PrgSerconMotivo(i_prgSerconMotivo));
        prgSercon.setIdEmpleado(empleadoConsultado);
        prgSercon.setUsername(user.getUsername());

        /**
         * Objeto jornada del empleado consultado en la gestión.
         */
        emplPrgSercon.setUserGenera(user.getUsername());
        emplPrgSercon.setUserAutorizado(user.getUsername());
        emplPrgSercon.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        emplPrgSercon.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        emplPrgSercon.setIdPrgSerconMotivo(new PrgSerconMotivo(i_prgSerconMotivo));
        emplPrgSercon.setIdEmpleado(empleadoPrgSercon);
        emplPrgSercon.setObservaciones(prgSercon.getObservaciones());
        emplPrgSercon.setUsername(user.getUsername());

        prgSerconEJB.cambioEmpleado(prgSercon, emplPrgSercon);
        if (SingletonConfigControlJornada.getMapConfigControlJornada()
                .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).getEstado().equals(ConstantsUtil.ON_INT)) {
            if (!Util.dateFormat(prgSercon.getFecha()).equals(Util.dateFormat(MovilidadUtil.fechaHoy()))) {
                if (prgSercon.getFecha().after(MovilidadUtil.fechaHoy())) {
                    prgTcEJB.cambioUnoAUnoServicosBySercon(prgSercon, emplPrgSercon);
                }
            }
        }
        MovilidadUtil.addSuccessMessage("Se hizo el cambio uno a uno de empleados exitosamente");
        PrimeFaces.current().executeScript("PF('cambioEmplWV').hide()");
    }

    /**
     * Responsable de cargar la jornada del empleado a consultar, en la variable
     * emplPrgSercon para le cambio uno a uno de turno entre operadores.
     *
     * Se invoca en la vista cambioEmpleado.
     *
     * La variable codigoTransMi es diligenciada desde la vista por el uuario.
     *
     * @throws Exception
     */
    public void emplFindByCodigoT() throws Exception {
        if (codigoTransMi != null) {
            if (!"".equals(codigoTransMi) && (!codigoTransMi.isEmpty())) {
                emplPrgSercon = prgSerconEJB.getPrgSerconByCodigoTMAndUnidadFuncional(codigoTransMi, prgSercon.getFecha(), 0);
                if (emplPrgSercon != null) {
                    nombreEmpleado = emplPrgSercon.getIdEmpleado().getCodigoTm() + " - " + emplPrgSercon.getIdEmpleado().getNombres() + " " + emplPrgSercon.getIdEmpleado().getApellidos();
                    empleadoConsultado = new Empleado(emplPrgSercon.getIdEmpleado().getIdEmpleado());
                    empleadoConsultado.setNombres(emplPrgSercon.getIdEmpleado().getNombres());
                    empleadoConsultado.setApellidos(emplPrgSercon.getIdEmpleado().getApellidos());
                    empleadoConsultado.setIdentificacion(emplPrgSercon.getIdEmpleado().getIdentificacion());
                    empleadoConsultado.setCodigoTm(emplPrgSercon.getIdEmpleado().getCodigoTm());
                    MovilidadUtil.addSuccessMessage("Operador cargado");
                } else {
                    MovilidadUtil.addErrorMessage("No existe jornada para este operador");
                    emplPrgSercon = null;
                    nombreEmpleado = "";
                }
            } else {
                MovilidadUtil.addAdvertenciaMessage("Digite el codigo del operador");
                emplPrgSercon = null;
                nombreEmpleado = "";
            }
        }
    }

    public PrgSercon cargarCalcularDatoPorPartes(PrgSercon ps) {
        PrgSercon onePart = null;
        PrgSercon twoPart = null;
        PrgSercon threePart = null;
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
        String workTime = MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(ps.getSercon(), ps.getWorkTime()));

        onePart = caluleOnePart(timeOrigin1, timeDestiny1, ps.getFecha(), workTime);
        twoPart = caluleOnePart(timeOrigin2, timeDestiny2, ps.getFecha(), workTime);
        threePart = caluleOnePart(timeOrigin3, timeDestiny3, ps.getFecha(), workTime);

        totalHorasAsignadas = calculator.hr_cero;
        ps.setDiurnas(calculator.hr_cero);
        ps.setNocturnas(calculator.hr_cero);
        ps.setExtraDiurna(calculator.hr_cero);
        ps.setExtraNocturna(calculator.hr_cero);
        ps.setFestivoDiurno(calculator.hr_cero);
        ps.setFestivoNocturno(calculator.hr_cero);
        ps.setFestivoExtraDiurno(calculator.hr_cero);
        ps.setFestivoExtraNocturno(calculator.hr_cero);
        sumarPorPartes(ps, onePart, workTime);
        sumarPorPartes(ps, twoPart, workTime);
        sumarPorPartes(ps, threePart, workTime);

        ps.setTipoCalculo(3);
        return ps;
    }

    /**
     *
     * @param ps Objeto de jornada seleccionado desde la vista.
     * @param psOriginal Objeto con los tiempos indicados desde la vista por el
     * usuario.
     * @return
     */
    public PrgSercon caso1AjustePorPartes(PrgSercon ps, PrgSercon psOriginal) {
        ps.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getDiurnas()) - MovilidadUtil.toSecs(psOriginal.getDiurnas())));

        ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) - MovilidadUtil.toSecs(psOriginal.getNocturnas())));

        ps.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraDiurna()) - MovilidadUtil.toSecs(psOriginal.getExtraDiurna())));

        ps.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraNocturna()) - MovilidadUtil.toSecs(psOriginal.getExtraNocturna())));

        ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoDiurno()) - MovilidadUtil.toSecs(psOriginal.getFestivoDiurno())));

        ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoNocturno()) - MovilidadUtil.toSecs(psOriginal.getFestivoNocturno())));

        ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraDiurno()) - MovilidadUtil.toSecs(psOriginal.getFestivoExtraDiurno())));

        ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) - MovilidadUtil.toSecs(psOriginal.getFestivoExtraNocturno())));

        return ps;
    }

    public PrgSercon sumarPorPartes(PrgSercon ps, PrgSercon psModificado, String totalHorasLaborales) {
        int totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas)
                + MovilidadUtil.toSecs(psModificado.getDiurnas());
        int maxHorasLaborales = MovilidadUtil.toSecs(totalHorasLaborales);
        int parteHorasLaborales;
        int parteHorasExtra;

        if (totalAsignar > maxHorasLaborales) {
            parteHorasExtra = totalAsignar - maxHorasLaborales;
            ps.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraDiurna()) + parteHorasExtra));
            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                ps.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getDiurnas()) + parteHorasLaborales));
                totalHorasAsignadas = totalHorasLaborales;
            }
        } else {
            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getDiurnas()));
            ps.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getDiurnas()) + MovilidadUtil.toSecs(psModificado.getDiurnas())));
        }
        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getNocturnas());

        if (totalAsignar > maxHorasLaborales) {
            parteHorasExtra = totalAsignar - maxHorasLaborales;
            ps.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraNocturna()) + parteHorasExtra));
            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) + parteHorasLaborales));
                totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
            }
        } else {
            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getNocturnas()));
            ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) + MovilidadUtil.toSecs(psModificado.getNocturnas())));
        }

        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoDiurno());

        if (totalAsignar > maxHorasLaborales) {
            parteHorasExtra = totalAsignar - maxHorasLaborales;
            ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraDiurno()) + parteHorasExtra));
            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoDiurno()) + parteHorasLaborales));
                totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
            }
        } else {
            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoDiurno()));
            ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoDiurno()) + MovilidadUtil.toSecs(psModificado.getFestivoDiurno())));
        }

        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoNocturno());

        if (totalAsignar > maxHorasLaborales) {
            parteHorasExtra = totalAsignar
                    - maxHorasLaborales;
            ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) + parteHorasExtra));
            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoNocturno()) + parteHorasLaborales));
                totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
            }
        } else {
            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoNocturno()));
            ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoNocturno()) + MovilidadUtil.toSecs(psModificado.getFestivoNocturno())));
        }

        ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(psModificado.getFestivoExtraDiurno())));

        ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(psModificado.getFestivoExtraNocturno())));

        ps.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraDiurna()) + MovilidadUtil.toSecs(psModificado.getExtraDiurna())));

        ps.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraNocturna()) + MovilidadUtil.toSecs(psModificado.getExtraNocturna())));
        return ps;
    }

    /**
     *
     * @param ps Objeto de jornada seleccionado desde la vista.
     * @param psOriginal Objeto con solo los valores originales de tiempos.
     * @param psModificado Objeto modificado con los tiempos indicados desde la
     * vista por el usuario.
     * @return
     */
    public PrgSercon caso2AjustePorPartes(PrgSercon ps, PrgSercon psOriginal, PrgSercon psModificado) {
        int i_diurna = MovilidadUtil.toSecs(psOriginal.getDiurnas()) - MovilidadUtil.toSecs(psModificado.getDiurnas());
        ps.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getDiurnas()) - i_diurna));

        int i_nocturnas = MovilidadUtil.toSecs(psOriginal.getNocturnas()) - MovilidadUtil.toSecs(psModificado.getNocturnas());
        ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) - i_nocturnas));

        int i_extraDiurna = MovilidadUtil.toSecs(psOriginal.getExtraDiurna()) - MovilidadUtil.toSecs(psModificado.getExtraDiurna());
        ps.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraDiurna()) - i_extraDiurna));

        int i_extraNocturna = MovilidadUtil.toSecs(psOriginal.getExtraNocturna()) - MovilidadUtil.toSecs(psModificado.getExtraNocturna());
        ps.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraNocturna()) - i_extraNocturna));

        int i_festivoDiurno = MovilidadUtil.toSecs(psOriginal.getFestivoDiurno()) - MovilidadUtil.toSecs(psModificado.getFestivoDiurno());
        ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoDiurno()) - i_festivoDiurno));

        int i_festivoNocturno = MovilidadUtil.toSecs(psOriginal.getFestivoNocturno()) - MovilidadUtil.toSecs(psModificado.getFestivoNocturno());
        ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoNocturno()) - i_festivoNocturno));

        int i_festivoExtraDiurno = MovilidadUtil.toSecs(psOriginal.getFestivoExtraDiurno()) - MovilidadUtil.toSecs(psModificado.getFestivoExtraDiurno());
        ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraDiurno()) - i_festivoExtraDiurno));

        int i_festivoExtraNocturno = MovilidadUtil.toSecs(psOriginal.getFestivoExtraNocturno()) - MovilidadUtil.toSecs(psModificado.getFestivoExtraNocturno());
        ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) - i_festivoExtraNocturno));
        return ps;
    }

    /**
     * Método responsable de calcular jornadas por rango de fechas al invocar el
     * método cargarCalcularDato.
     *
     * Valida que las jornadas no esten liquidadas.
     */
    public void cargarCalcularDatos() throws ParseException {
        if (UtilJornada.tipoLiquidacion()) {//flexible
            if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
                MovilidadUtil.addErrorMessage("Seleccionar una unidad funcional.");
                return;
            }
            UtilJornada.cargarParametrosJar();
            List<PrgSerconLiqUtil> calculoOrdinarioJornadas = getINSTANCEJS().calculoOrdinarioJornadas(fechaDesde, fechaHasta, 1, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            System.out.println("calculoOrdinarioJornadas---------->" + calculoOrdinarioJornadas.size());
            prgSerconEJB.updatePrgSerconFromListWithoutProductionTime(calculoOrdinarioJornadas);
            List<PrgSerconLiqUtil> calculoJornadaFlexible = getINSTANCEJS().calculoJornadaFlexible(fechaDesde, fechaHasta, 1, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            System.out.println("calculoJornadaFlexible------------>" + calculoJornadaFlexible.size());
            prgSerconEJB.updatePrgSerconFromListWithoutProductionTime(calculoJornadaFlexible);
            List<PrgSerconLiqUtil> distribuirDomicalSinCompensatorio = getINSTANCEJS().distribuirDomicalSinCompensatorio(fechaDesde, fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            System.out.println("distribuirDomicalSinCompensatorio->" + distribuirDomicalSinCompensatorio.size());
            prgSerconEJB.updatePrgSerconFromListWithoutProductionTime(distribuirDomicalSinCompensatorio);
        } else {
            prglist = new ArrayList<>();
            prglist = prgSerconEJB.getPrgSerconByDateAndUnidadFunc(
                    fechaDesde, fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            for (PrgSercon ps : prglist) {
                if ((ps.getLiquidado() == null || (ps.getLiquidado() != null && ps.getLiquidado() == 0))) {
                    if (UtilJornada.esPorPartes(ps)) {
                        cargarCalcularDatoPorPartes(ps);
                    } else {
                        cargarCalcularDato(ps);
                    }
                    prgSerconEJB.edit(ps);
                }
            }
        }
        cargarDatos();
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente");
    }

    private boolean validarRangoHoras(PrgSercon solicitanteAct) {
        PrgSercon prgSerconSolicitanteAnt;
        PrgSercon prgSerconSolicitanteSig;
        String hIniTurnoACambiar;
        int dif;
        Date fechaAnterior = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), -1);
        Date fechaSiguiente = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), 1);

        prgSerconSolicitanteAnt = prgSerconEJB.validarEmplSinJornadaByUnindadFuncional(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaAnterior, 0);

        if (prgSerconSolicitanteAnt != null) {
            /**
             * Caso 2: Verificar si el operador cumple 12 horas de descanso con
             * respecto al turno del día anterior.
             */
            String sTimeOriginRemp = solicitanteAct.getRealTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginRemp) == 0) {
                sTimeOriginRemp = calculator.fin_dia;
            }

            String timeDestinyDiaAnterior = getTimeDestiny(prgSerconSolicitanteAnt);
            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, calculator.fin_dia);
            dif = MovilidadUtil.diferencia(timeDestinyDiaAnterior, hIniTurnoACambiar);
            ConfigControlJornada get = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(get.getTiempo())) {
                return true;
            }
        }

        prgSerconSolicitanteSig = prgSerconEJB.validarEmplSinJornadaByUnindadFuncional(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaSiguiente, 0);

        if (prgSerconSolicitanteSig != null) {
            /**
             * Caso 2: Verificar si el operador solicitante cumple 12 horas de
             * descanso con respecto a la jornada del día siguiente.
             */
            String sTimeOriginRempSig = getTimeOrigin(prgSerconSolicitanteSig);
            if (prgSerconSolicitanteSig.getAutorizado() != null
                    && prgSerconSolicitanteSig.getAutorizado() == 1) {
                sTimeOriginRempSig = prgSerconSolicitanteSig.getRealTimeOrigin();
            }

            if (MovilidadUtil.toSecs(sTimeOriginRempSig) == 0) {
                sTimeOriginRempSig = calculator.fin_dia;
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRempSig, calculator.fin_dia);
            dif = MovilidadUtil.diferencia(solicitanteAct.getRealTimeDestiny(), hIniTurnoACambiar);
            ConfigControlJornada get = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(get.getTiempo())) {
                return true;
            }
        }

        return false;
    }

    private boolean validarRangoHorasCambioUnoAUno(PrgSercon solicitanteAct, PrgSercon reemplazoAct) throws ParseException {
        PrgSercon prgSerconSolicitanteAnt;
        PrgSercon prgSerconReemplazoAnt;
        PrgSercon prgSerconSolicitanteSig;
        PrgSercon prgSerconReemplazoSig;
        int dif;
        Date fechaAnterior = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), -1);
        Date fechaSiguiente = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), 1);

        prgSerconSolicitanteAnt = prgSerconEJB.validarEmplSinJornadaByUnindadFuncional(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaAnterior, 0);
        prgSerconReemplazoAnt = prgSerconEJB.getPrgSerconByCodigoTMAndUnidadFuncional(codigoTransMi, fechaAnterior, 0);
        boolean despuesDeEjecutado = UtilJornada.registroDespuesDeEjecutado(solicitanteAct.getFecha(), UtilJornada.ultimaHoraRealJornada(solicitanteAct));
        String msg = "El turno NO permite respetar las 12 horas de descanso reglamentarias";

        if (prgSerconReemplazoAnt != null) {
            // Caso 1: Verificar si el operador de reemplazo cumple 12 horas de descanso

            String sTimeOrigin = getTimeOrigin(solicitanteAct);

            if (MovilidadUtil.toSecs(sTimeOrigin) == 0) {
                sTimeOrigin = calculator.fin_dia;
            }

            dif = MovilidadUtil.diferencia(getTimeDestiny(prgSerconReemplazoAnt),
                    MovilidadUtil.sumarHoraSrting(sTimeOrigin, calculator.fin_dia));
            ConfigControlJornada get = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(get.getTiempo())) {
                return true;
            }
        }

        if (prgSerconSolicitanteAnt != null) {
            // Caso 2: Verificar si el operador solicitante cumple 12 horas de descanso
            String sTimeOriginRemp = getTimeOrigin(reemplazoAct);

            if (MovilidadUtil.toSecs(sTimeOriginRemp) == 0) {
                sTimeOriginRemp = calculator.fin_dia;
            }

            dif = MovilidadUtil.diferencia(getTimeDestiny(prgSerconSolicitanteAnt),
                    MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, calculator.fin_dia));

            ConfigControlJornada get = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(get.getTiempo())) {
                return true;
            }
        }

        prgSerconSolicitanteSig = prgSerconEJB.validarEmplSinJornadaByUnindadFuncional(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaSiguiente, 0);
        prgSerconReemplazoSig = prgSerconEJB.getPrgSerconByCodigoTMAndUnidadFuncional(codigoTransMi, fechaSiguiente, 0);

        if (prgSerconSolicitanteSig != null) {

            // Caso 1: Verificar si el operador de reemplazo cumple 12 horas de descanso
            String sTimeOriginSolSig = getTimeOrigin(prgSerconSolicitanteSig);

            if (MovilidadUtil.toSecs(sTimeOriginSolSig) == 0) {
                sTimeOriginSolSig = calculator.fin_dia;
            }

            dif = MovilidadUtil.diferencia(getTimeDestiny(reemplazoAct),
                    MovilidadUtil.sumarHoraSrting(sTimeOriginSolSig, calculator.fin_dia));
            ConfigControlJornada get = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(get.getTiempo())) {
                return true;
            }

        }

        if (prgSerconReemplazoSig != null) {
            // Caso 2: Verificar si el operador solicitante cumple 12 horas de descanso
            String sTimeOriginRempSig = getTimeOrigin(prgSerconReemplazoSig);

            if (MovilidadUtil.toSecs(sTimeOriginRempSig) == 0) {
                sTimeOriginRempSig = calculator.fin_dia;
            }

            dif = MovilidadUtil.diferencia(getTimeDestiny(solicitanteAct),
                    MovilidadUtil.sumarHoraSrting(sTimeOriginRempSig, calculator.fin_dia));
            ConfigControlJornada get = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(get.getTiempo())) {
                return true;
            }
        }

        return false;
    }

    private String getTimeDestiny(PrgSercon param) {
        if (param.getAutorizado() != null
                && param.getAutorizado() == 1) {
            return param.getRealTimeDestiny();
        } else {
            return param.getTimeDestiny();
        }
    }

    private String getTimeOrigin(PrgSercon param) {
        if (param.getAutorizado() != null
                && param.getAutorizado() == 1) {
            return param.getRealTimeDestiny();
        } else {
            return param.getTimeDestiny();
        }
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

    boolean validarHorasExtrasFlexible(PrgSercon psLocal, boolean realTime) throws ParseException {
        PrgSerconLiqUtil param = new PrgSerconLiqUtil();
        param.setFecha(psLocal.getFecha());
        param.setTimeOrigin(psLocal.getRealTimeOrigin());
        param.setTimeDestiny(psLocal.getRealTimeDestiny());
        param.setHiniTurno2(psLocal.getRealHiniTurno2());
        param.setHfinTurno2(psLocal.getRealHfinTurno2());
        param.setHiniTurno3(psLocal.getRealHiniTurno3());
        param.setHfinTurno3(psLocal.getRealHfinTurno3());
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
            String msg = "Se excedió el máximo de horas extras diarias" + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsDia.getHora());
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

    boolean validarHorasExtras(PrgSercon psLocal) throws ParseException {
        int difPart1 = MovilidadUtil.toSecs(psLocal.getRealTimeDestiny())
                - MovilidadUtil.toSecs(psLocal.getRealTimeOrigin());
        int difPart2 = MovilidadUtil.toSecs(psLocal.getRealHfinTurno2())
                - MovilidadUtil.toSecs(psLocal.getRealHiniTurno2());
        int difPart3 = MovilidadUtil.toSecs(psLocal.getRealHfinTurno3())
                - MovilidadUtil.toSecs(psLocal.getRealHiniTurno3());
        int totalProduccion = difPart1 + difPart2 + difPart3;
        String workTime = MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(psLocal.getSercon(), psLocal.getWorkTime()));
        int totalHorasExtras = totalProduccion - MovilidadUtil.toSecs(workTime);

        boolean despuesDeEjecutado = UtilJornada.registroDespuesDeEjecutado(psLocal.getFecha(), UtilJornada.ultimaHoraRealJornada(psLocal));

        /**
         * Validar si el total de horas extras en mayor que el criterio minimo
         * para considerar horas extras.
         */
//        if (totalHorasExtras >= MovilidadUtil.toSecs(JornadaUtil.getCriterioMinHrsExtra())) {
        if (totalHorasExtras > MovilidadUtil.toSecs(UtilJornada.getMaxHrsExtrasDia())) {
            String mssg = "Se excedió el máximo de horas extras " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                    + MovilidadUtil.toTimeSec((totalProduccion - MovilidadUtil.toSecs(workTime)));
            if (UtilJornada.respetarMaxHorasExtrasDiarias()) {
//                if (despuesDeEjecutado && JornadaUtil.tipoLiquidacion()) {
//                    MovilidadUtil.addAdvertenciaMessage(mssg);
//                    return false;
//                } else {
                MovilidadUtil.addErrorMessage(mssg);
                return true;
//                }
            } else {
                MovilidadUtil.addAdvertenciaMessage(mssg);
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
        totalHorasExtrasSmnal = resp == null ? calculator.hr_cero : Util.dateToTimeHHMMSS(resp);
//        System.out.println("totalHorasExtrasSmnal->" + totalHorasExtrasSmnal);
    }

    public boolean validarLiquidacionProgramada(PrgSercon psLocal) {
        if (psLocal.getPrgModificada() == null) {
            return true;
        }
        if (psLocal.getPrgModificada().equals(0)) {
            return true;
        }
        if (psLocal.getPrgModificada().equals(1) && psLocal.getAutorizado().equals(0)) {
            return true;
        }
        return false;
    }

    boolean validarMaxHorasExtrasSmanales(PrgSercon psLocal) throws ParseException {
        int difPart1 = validarLiquidacionProgramada(psLocal)
                ? (MovilidadUtil.toSecs(psLocal.getTimeDestiny())
                - MovilidadUtil.toSecs(psLocal.getTimeOrigin()))
                : (MovilidadUtil.toSecs(psLocal.getRealTimeDestiny())
                - MovilidadUtil.toSecs(psLocal.getRealTimeOrigin()));

        int difPart2 = validarLiquidacionProgramada(psLocal)
                ? (MovilidadUtil.toSecs(psLocal.getHfinTurno2())
                - MovilidadUtil.toSecs(psLocal.getHiniTurno2()))
                : (MovilidadUtil.toSecs(psLocal.getRealHfinTurno2())
                - MovilidadUtil.toSecs(psLocal.getRealHiniTurno2()));

        int difPart3 = validarLiquidacionProgramada(psLocal)
                ? (MovilidadUtil.toSecs(psLocal.getHfinTurno3())
                - MovilidadUtil.toSecs(psLocal.getHiniTurno3()))
                : (MovilidadUtil.toSecs(psLocal.getRealHfinTurno3())
                - MovilidadUtil.toSecs(psLocal.getRealHiniTurno3()));

        int totalProduccion = difPart1 + difPart2 + difPart3;
        int totalHorasExtras = (totalProduccion
                - UtilJornada.getWorkTimeJornada(psLocal.getSercon(), psLocal.getWorkTime()));

//        boolean despuesDeEjecutado = JornadaUtil.registroDespuesDeEjecutado(psLocal.getFecha(), JornadaUtil.ultimaHoraRealJornada(psLocal));
        /**
         * Validar si las horas extras generadas son mayores que el criterio
         * minimo para horas extras.
         */
//        if (validarLiquidacionProgramada(psLocal)
//                || totalHorasExtras >= MovilidadUtil.toSecs(JornadaUtil.getCriterioMinHrsExtra())) {
        if ((MovilidadUtil.toSecs(totalHorasExtrasSmnal) + totalHorasExtras)
                > MovilidadUtil.toSecs(UtilJornada.getMaxHrsExtrasSmnl())) {
            String msg = "Se excedió el máximo de horas extras semanales"
                    + UtilJornada.getMaxHrsExtrasSmnl()
                    + ". Encontrado "
                    + MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(totalHorasExtrasSmnal) + totalHorasExtras));
//            if (despuesDeEjecutado && JornadaUtil.tipoLiquidacion()) {
//                MovilidadUtil.addAdvertenciaMessage(msg);
//                return false;
//            } else {
            MovilidadUtil.addErrorMessage(msg);
            return true;
//            }
        }
//        }
        return false;
    }

    boolean validarMaxHorasExtrasSmanal(PrgSercon solicitanteAct, PrgSercon reemplazoAct) throws ParseException {
        cargarTotalHorasExtrasSmanal(solicitanteAct.getFecha(), solicitanteAct.getIdEmpleado().getIdEmpleado());
        if (validarMaxHorasExtrasSmanales(solicitanteAct)) {
            MovilidadUtil.addErrorMessage("Solicitante.");
            return true;
        }
        cargarTotalHorasExtrasSmanal(reemplazoAct.getFecha(), reemplazoAct.getIdEmpleado().getIdEmpleado());
        if (validarMaxHorasExtrasSmanales(reemplazoAct)) {
            MovilidadUtil.addErrorMessage("Remplazo.");
            return true;
        }
        return false;
    }

    void cambioServiciosPrgTc(PrgSercon sercon1, PrgSercon sercon2) {
        prgTcEJB.cambioUnoAUnoServicosBySercon(sercon1, sercon2);
    }

    boolean validarHoras(String hora1, String hora2) {
        return MovilidadUtil.toSecs(hora1) > MovilidadUtil.toSecs(hora2);
    }

    public boolean validarHorasParaCalcular(Integer autizado, Integer prgModificada, String realTime) {
        if (autizado != null && autizado == 1) {
            return realTime != null;
        } else {
            return prgModificada != null && prgModificada == 1;
        }
    }

    private void setValueFromPrgSerconJar(PrgSerconLiqUtil param1, PrgSercon param2) {
        param2.setDiurnas(param1.getDiurnas());
        System.out.println("Diurnas.................." + param1.getDiurnas());
        param2.setNocturnas(param1.getNocturnas());
        System.out.println("Nocturnas................" + param1.getNocturnas());
        param2.setExtraDiurna(param1.getExtraDiurna());
        System.out.println("Extra Diurnas..........." + param1.getExtraDiurna());
        param2.setExtraNocturna(param1.getExtraNocturna());
        System.out.println("Extra Nocturnas........." + param1.getExtraNocturna());
        param2.setFestivoDiurno(param1.getFestivoDiurno());
        System.out.println("Festivo Diurno.........." + param1.getFestivoDiurno());
        param2.setFestivoNocturno(param1.getFestivoNocturno());
        System.out.println("Festivo Nocturno........" + param1.getFestivoNocturno());
        param2.setFestivoExtraDiurno(param1.getFestivoExtraDiurno());
        System.out.println("Festivo extra diurno...." + param1.getFestivoExtraDiurno());
        param2.setFestivoExtraNocturno(param1.getFestivoExtraNocturno());
        System.out.println("Festivo extra Nocturno.." + param1.getFestivoExtraNocturno());
        param2.setDominicalCompDiurnas(param1.getDominicalCompDiurnas());
        System.out.println("Dom Comp Diurno........." + param1.getDominicalCompDiurnas());
        param2.setDominicalCompNocturnas(param1.getDominicalCompNocturnas());
        System.out.println("Dom Comp nocturno......." + param1.getDominicalCompNocturnas());
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
        System.out.println("timeOrigin1.." + timeOrigin1);
        System.out.println("timeDestiny1." + timeDestiny1);
        System.out.println("timeOrigin2.." + timeOrigin2);
        System.out.println("timeDestiny2." + timeDestiny2);
        System.out.println("timeOrigin3.." + timeOrigin3);
        System.out.println("timeDestiny3." + timeDestiny3);
        PrgSerconLiqUtil prgSerconLiqUtil = new PrgSerconLiqUtil(timeOrigin1,
                timeDestiny1,
                timeOrigin2,
                timeDestiny2,
                timeOrigin3,
                timeDestiny3);
        prgSerconLiqUtil.setSercon(ps.getSercon());
        return prgSerconLiqUtil;
    }

    private boolean jornadaFechaDiferente(Date fecha, PrgSerconLiqUtil param) {
        return !com.aja.jornada.util.Util.dateFormat(fecha).equals(com.aja.jornada.util.Util.dateFormat(param.getFecha()));
    }
    
    public String obtenerUF(int value) {
        String val = String.valueOf(value).matches("^(63|67).*") ? "ZMO " + (String.valueOf(value).startsWith("63") ? "III" : "V") : "";
        return val;
    }

    /**
     * Permite obtener el nombre del día al que corresponde la fecha ingresada
     *
     * @param fecha de tipo Date que especifica la fecha que se desea evaluar
     * @return String que contiene el nombre del día LUNES, MARTES...DOMINGO
     */
    public String obtenerNombreDia(Date fecha) {
        flagFeriado = paraFeEJB.findByFecha(fecha) != null;
        return fecha != null ? Util.getDayName(fecha).toUpperCase() : "";
    }
    
    /**
     * permite obtener el número de la semana del año de la fecha que se ingresa
     * como parámetro. La semana inicia en LUNES y termina en DOMINGO
     *
     * @param fecha de tipo Date que contiene la fecha a evaluar
     * @return número entero que indica la semana del año
     */
    public int obtenerSemana(Date fecha) {
        return fecha != null ? Util.getNumberWeekOfYear(fecha) : 0;
    }

    /**
     * Permite obtener el tipo de día contenido en la fecha. El acrónimo del
     * tipo de día corresponde a: - hab -> días hábiles (lunes, martes,
     * miércoles, jueves, viernes) - sab -> sábado - dom -> dominical o domingo
     * - fes -> días hábiles festivos
     *
     * @param fecha de tipo Date que contiene la fecha a procesar
     * @return String con el acrónimo correspondiente
     */
    public String obtenerTipoDia(Date fecha) {
        ParamFeriado paramFeriado = paraFeEJB.findByFecha(fecha);
        if (paramFeriado != null) {
            return "fes";
        }
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        int diaSemana = c.get(Calendar.DAY_OF_WEEK);
        if (diaSemana >= 2 && diaSemana <= 6) {
            return "hab";
        } else if (diaSemana == 7) {
            return "sab";
        } else {
            return "dom";
        }
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

    public List<PrgSercon> getPrglist() {
        return prglist;
    }

    public void setPrglist(List<PrgSercon> prglist) {
        this.prglist = prglist;
    }

    public PrgSercon getPrgSercon() {
        return prgSercon;
    }

    public void setPrgSercon(PrgSercon prgSercon) {
        this.prgSercon = prgSercon;
    }

    public ControlJornadaController getControlJornadaMB() {
        return controlJornadaMB;
    }

    public void setControlJornadaMB(ControlJornadaController controlJornadaMB) {
        this.controlJornadaMB = controlJornadaMB;
    }

    public List<PrgSerconMotivo> getPrgSerconMotivoList() {
        return prgSerconMotivoList;
    }

    public void setPrgSerconMotivoList(List<PrgSerconMotivo> prgSerconMotivoList) {
        this.prgSerconMotivoList = prgSerconMotivoList;
    }

    public int getI_prgSerconMotivo() {
        return i_prgSerconMotivo;
    }

    public void setI_prgSerconMotivo(int i_prgSerconMotivo) {
        this.i_prgSerconMotivo = i_prgSerconMotivo;
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

    public PrgSercon getEmplPrgSercon() {
        return emplPrgSercon;
    }

    public void setEmplPrgSercon(PrgSercon emplPrgSercon) {
        this.emplPrgSercon = emplPrgSercon;
    }

    public boolean isFlagCalculo() {
        return flagCalculo;
    }

    public void setFlagCalculo(boolean flagCalculo) {
        this.flagCalculo = flagCalculo;
    }

    public boolean isFlagSabDom() {
        return flagSabDom;
    }

    public void setFlagSabDom(boolean flagSabDom) {
        this.flagSabDom = flagSabDom;
    }

    public boolean isFlagEliminar() {
        return flagEliminar;
    }

    public void setFlagEliminar(boolean flagEliminar) {
        this.flagEliminar = flagEliminar;
    }

    public boolean isFlagDesliminar() {
        return flagDesliminar;
    }

    public void setFlagDesliminar(boolean flagDesliminar) {
        this.flagDesliminar = flagDesliminar;
    }

    public boolean isFlagLiquidar() {
        return flagLiquidar;
    }

    public void setFlagLiquidar(boolean flagLiquidar) {
        this.flagLiquidar = flagLiquidar;
    }

    public boolean isFlagModificar() {
        return flagModificar;
    }

    public void setFlagModificar(boolean flagModificar) {
        this.flagModificar = flagModificar;
    }

    public boolean isFlagCalcular() {
        return flagCalcular;
    }

    public void setFlagCalcular(boolean flagCalcular) {
        this.flagCalcular = flagCalcular;
    }

    public boolean isFlagCambioEmpleado() {
        return flagCambioEmpleado;
    }

    public void setFlagCambioEmpleado(boolean flagCambioEmpleado) {
        this.flagCambioEmpleado = flagCambioEmpleado;
    }

    public String getS_horaIniTurno1() {
        return s_horaIniTurno1;
    }

    public void setS_horaIniTurno1(String s_horaIniTurno1) {
        this.s_horaIniTurno1 = s_horaIniTurno1;
    }

    public String getS_horaFinTurno1() {
        return s_horaFinTurno1;
    }

    public void setS_horaFinTurno1(String s_horaFinTurno1) {
        this.s_horaFinTurno1 = s_horaFinTurno1;
    }

    public String getS_horaIniTurno2() {
        return s_horaIniTurno2;
    }

    public void setS_horaIniTurno2(String s_horaIniTurno2) {
        this.s_horaIniTurno2 = s_horaIniTurno2;
    }

    public String getS_horaFinTurno2() {
        return s_horaFinTurno2;
    }

    public void setS_horaFinTurno2(String s_horaFinTurno2) {
        this.s_horaFinTurno2 = s_horaFinTurno2;
    }

    public String getS_horaIniTurno3() {
        return s_horaIniTurno3;
    }

    public void setS_horaIniTurno3(String s_horaIniTurno3) {
        this.s_horaIniTurno3 = s_horaIniTurno3;
    }

    public String getS_horaFinTurno3() {
        return s_horaFinTurno3;
    }

    public void setS_horaFinTurno3(String s_horaFinTurno3) {
        this.s_horaFinTurno3 = s_horaFinTurno3;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public boolean isFlagFeriado() {
        return flagFeriado;
    }

    public void setFlagFeriado(boolean flagFeriado) {
        this.flagFeriado = flagFeriado;
    }

    public int getnSemana() {
        return nSemana;
    }

    public void setnSemana(int nSemana) {
        this.nSemana = nSemana;
    }

    public String getUsername() {
        return user.getUsername();
    }
}
