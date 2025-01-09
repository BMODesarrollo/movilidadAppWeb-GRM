/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.aja.jornada.controller.GenericaJornadaFlexible;
import com.aja.jornada.dto.ErrorPrgSercon;
import com.aja.jornada.model.EmpleadoLiqUtil;
import com.aja.jornada.model.GenericaJornadaLiqUtil;
import com.aja.jornada.model.GenericaJornadaTipoLiqUtil;
import com.aja.jornada.model.ParamAreaLiqUtil;
import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GenericaJornadaExtraFacadeLocal;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.GenericaJornadaMotivoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaParamFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaCargoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.ConfigControlJornada;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaExtra;
import com.movilidad.model.GenericaJornadaMotivo;
import com.movilidad.model.GenericaJornadaParam;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.ParamAreaCargo;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ConsolidadoLiquidacion;
import com.movilidad.util.beans.ConsolidadoLiquidacionDetallado;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import static com.movilidad.utils.MovilidadUtil.fechaDiferente;
import com.movilidad.utils.SingletonConfigControlJornada;
import com.movilidad.utils.Util;
import com.movilidad.utils.UtilJornada;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.aja.jornada.util.ConfigJornada;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.model.GenericaJornadaInicial;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.ParamFeriado;
import com.movilidad.utils.SendMails;

/**
 *
 * @author solucionesit
 */
@Named(value = "genLiqJornadaMB")
@ViewScoped
public class GenericaLiquidarJornadaMB implements Serializable {

    /**
     * Creates a new instance of GenericaLiquidarJornadaMB
     */
    public GenericaLiquidarJornadaMB() {
    }

    @EJB
    private ParamFeriadoFacadeLocal paramFeriadoEjb;

    @EJB
    private GenericaJornadaMotivoFacadeLocal jornadaMotivoEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;

    @EJB
    private GenericaJornadaFacadeLocal genJornadaEJB;

    @EJB
    private ParamAreaCargoFacadeLocal areaCargoEJB;

    @EJB
    private GenericaJornadaTipoFacadeLocal jornadaTEJB;

    @EJB
    private GenericaJornadaParamFacadeLocal genJorParamEJB;

    @EJB
    private GenericaJornadaExtraFacadeLocal genJornadaExtraEjb;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;

    @Inject
    private LiquidarGenericaJornadaBean LiqGenJornadaBean;

    @Inject
    private CalcularMasivoJSFManagedBean calcularMasivoBean;

    private GenericaJornada genericaJornada;
    private GenericaJornada emplPrgSercon;
    private boolean flagCalculo = false;
    private boolean flagSabDom = false;
    private boolean flagEliminar = false;
    private boolean flagDesliminar = false;
    private boolean flagLiquidar = false;
    private boolean flagModificar = false;
    private boolean flagCalcular = false;
    private boolean flagCambioEmpleado = false;
    private boolean b_permiso = false;
    private boolean b_orden_tbj = false;
    private boolean flagFeriado = false;
    /**
     * Indica si se debe llevar el control del maximo de horas extras por semana
     * y mensual.
     *
     * Se carga en el método init.
     */
    private boolean b_maxHorasExtrasSemanaMes = false;

    private String codigoTransMi = "";
    private String nombreEmpleado = "";
    private String ordenTrabajo = null;

    private String s_diurnas = "00:00:00";
    private String s_nocturnas = "00:00:00";
    private String s_extra_diurna = "00:00:00";
    private String s_extra_nocturna = "00:00:00";
    private String s_festivo_diurno = "00:00:00";
    private String s_festivo_nocturno = "00:00:00";
    private String s_festivo_extra_diurno = "00:00:00";
    private String s_festivo_extra_nocturno = "00:00:00";
    private String s_dominical_comp_diurno = "00:00:00";
    private String s_dominical_comp_nocturno = "00:00:00";
    private String s_dominical_comp_diurno_extra = "00:00:00";
    private String s_dominical_comp_nocturno_extra = "00:00:00";
    private String MAX_HORAS_EXTRAS_DIARIAS;
    Empleado empleadoPrgSercon;
    Empleado empleadoConsultado;
    List<GenericaJornada> listaEmpleados = new ArrayList<>();

    private ParamAreaUsr pau;
    private GenericaJornadaParam genJornadaParam;
    private StreamedContent file;

    private boolean b_joranada_flexible = false;

    int i_genericaJornadaMotivo = 0;
    String rol_user = "";

    private double horasExtrasInicio;
    private double horasExtrasFin;

    private List<ConsolidadoLiquidacion> lstConsolidado;
    private List<ConsolidadoLiquidacion> lstCargado;
    private List<GenericaJornadaInicial> lstProgInicial;

    private List<ConsolidadoLiquidacionDetallado> lstConsolidadoDetallado;
    private List<ConsolidadoLiquidacionDetallado> lstCargadoDetallado;

    private List<GenericaJornadaMotivo> genericaJornadaMotivoList;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private GenericaJornadaFlexible flexible;

    public GenericaJornadaFlexible getJornadaFlexible() {
        if (flexible == null) {
            flexible = new GenericaJornadaFlexible();
        }
        return flexible;
    }

    @PostConstruct
    public void init() {
        MAX_HORAS_EXTRAS_DIARIAS = UtilJornada.getMaxHrsExtrasDia();
        for (GrantedAuthority auth : user.getAuthorities()) {
            rol_user = auth.getAuthority();
        }
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        if (pau != null) {
            genJornadaParam = genJorParamEJB.getByIdArea(pau.getIdParamArea().getIdParamArea());

            b_maxHorasExtrasSemanaMes = genJornadaParam != null
                    && genJornadaParam.getHorasExtrasSemanales() >= 0
                    && genJornadaParam.getHorasExtrasMensuales() >= 0;
            b_permiso = pau.getIdParamArea().getControlJornada().equals(1);
            b_joranada_flexible = jornadaFlexible();

            calcularMasivoBean.setGenJornadaParam(genJornadaParam);
        }
        calcularMasivoBean.setPau(pau);
        calcularMasivoBean.setRol_user(rol_user);
        LiqGenJornadaBean.setPau(pau);
        calcularMasivoBean.cargarDatos();
    }

    private boolean jornadaFlexible() {
        return pau.getIdParamArea().getJornadaFlexible() == null ? false
                : pau.getIdParamArea().getJornadaFlexible().equals(1);
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

        if (Util.validarFechaCambioEstado(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta())) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        if (pau != null) {
            if (pau.getIdParamArea() != null) {
                lstConsolidado = genJornadaEJB.obtenerDatosConsolidado(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta(), pau.getIdParamArea().getIdParamArea());
                lstConsolidadoDetallado = genJornadaEJB.obtenerDatosConsolidadoDetallado(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta(), pau.getIdParamArea().getIdParamArea());

                if (lstConsolidado == null || lstConsolidado.isEmpty()) {
                    MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado)");
                    return;
                }
                if (lstConsolidadoDetallado == null || lstConsolidadoDetallado.isEmpty()) {
                    MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado detallado)");
                    return;
                }
                generarExcel(lstConsolidado, lstConsolidadoDetallado, 1);
            }

        } else {
            MovilidadUtil.addErrorMessage("usted NO tiene un área asociada.");
        }

    }

    public void descargarProgramacionInicial() throws FileNotFoundException {

//        file = null;
//
//        if (Util.validarFechaCambioEstado(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta())) {
//            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
//            return;
//        }
//
//        if (pau != null) {
//            if (pau.getIdParamArea() != null) {
//                lstProgInicial = genJornadaEJB.findJornadasCargadas(pau.getIdParamArea().getIdParamArea(), calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta());
//       
//                if (lstProgInicial == null || lstProgInicial.isEmpty()) {
//                    MovilidadUtil.addErrorMessage("No se encontraron datos para las fechas indicadas");
//                    return;
//                }
//                generarExcelCargaInicialTurnos();
//            }
//
//        } else {
//            MovilidadUtil.addErrorMessage("usted NO tiene un área asociada.");
//        }
        file = null;

        if (Util.validarFechaCambioEstado(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta())) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        if (pau != null) {
            if (pau.getIdParamArea() != null) {
                lstCargado = genJornadaEJB.obtenerDatosCargados(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta(), pau.getIdParamArea().getIdParamArea());
                lstCargadoDetallado = genJornadaEJB.obtenerDatosCargadosDetallado(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta(), pau.getIdParamArea().getIdParamArea());

                if (lstCargado == null || lstCargado.isEmpty()) {
                    MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado)");
                    return;
                }
                if (lstCargadoDetallado == null || lstCargadoDetallado.isEmpty()) {
                    MovilidadUtil.addErrorMessage("No se encontraron datos (Consolidado detallado)");
                    return;
                }
                generarExcel(lstCargado,lstCargadoDetallado, 0);
            }

        } else {
            MovilidadUtil.addErrorMessage("usted NO tiene un área asociada.");
        }

    }

    /**
     * Método Responsable de construir el archivo Excel de consolidado detallado
     * de jornadas.
     *
     * @throws FileNotFoundException
     */
    private void generarExcel(List<ConsolidadoLiquidacion> lstC, List<ConsolidadoLiquidacionDetallado> lstCDet, int opt) throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Consolidado Liquidacion Gen.xlsx";
        parametros.put("desde", Util.dateFormat(calcularMasivoBean.getFechaDesde()));
        parametros.put("hasta", Util.dateFormat(calcularMasivoBean.getFechaHasta()));
        parametros.put("generado", Util.dateFormat(new Date()));
        parametros.put("titulo", opt == 1 ? "CONSOLIDADO DE HORAS LABORADAS" : "DE HORAS PROGRAMADAS");
        parametros.put("liquidacion", lstC);
        parametros.put("liquidacionDet", lstCDet);

        destino = destino + (opt == 1 ? "CONSOLIDADO_LIQUIDACION.xls" : "PROGRAMADO_LIQUIDACION.xls");

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", (opt == 1 ? "CONSOLIDADO_LIQUIDACION_" : "PROGRAMADO_LIQUIDACION_") + Util.dateFormat(calcularMasivoBean.getFechaDesde()) + "_al_" + Util.dateFormat(calcularMasivoBean.getFechaHasta()) + ".xlsx");
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
        plantilla = plantilla + "Reporte Programado.xlsx";
        parametros.put("items", lstProgInicial);
        destino = destino + "REPORTE_PROGRAMADO.xls";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "REPORTE_PROGRAMADO_" + Util.dateFormat(calcularMasivoBean.getFechaDesde()) + "_al_" + Util.dateFormat(calcularMasivoBean.getFechaHasta()) + ".xlsx");
    }

    /**
     * Carga la lista genericaJornadaMotivoList con los motivos por id de area
     * del usuario.
     */
    public void cargarMotivos() {
        genericaJornadaMotivoList = jornadaMotivoEJB.findByArea(pau.getIdParamArea().getIdParamArea());
    }

    /**
     * Preparar variables para la gestión de modificar una jornada.
     */
    public void prepareModificar() throws ParseException {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        flagModificarJornada();
        if (flagModificar) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        if (genericaJornada.getIdGenericaJornadaMotivo() != null) {
            i_genericaJornadaMotivo = genericaJornada.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo();
        }
        if (genericaJornada.getAutorizado() != null && genericaJornada.getAutorizado() < 1) {
            genericaJornada.setDiurnas("00:00:00");
            genericaJornada.setNocturnas("00:00:00");
            genericaJornada.setExtraDiurna("00:00:00");
            genericaJornada.setExtraNocturna("00:00:00");
            genericaJornada.setFestivoDiurno("00:00:00");
            genericaJornada.setFestivoNocturno("00:00:00");
            genericaJornada.setFestivoExtraDiurno("00:00:00");
            genericaJornada.setFestivoExtraNocturno("00:00:00");
        }
        s_diurnas = genericaJornada.getDiurnas();
        s_nocturnas = genericaJornada.getNocturnas();
        s_extra_diurna = genericaJornada.getExtraDiurna();
        s_extra_nocturna = genericaJornada.getExtraNocturna();
        s_festivo_diurno = genericaJornada.getFestivoDiurno();
        s_festivo_nocturno = genericaJornada.getFestivoNocturno();
        s_festivo_extra_diurno = genericaJornada.getFestivoExtraDiurno();
        s_festivo_extra_nocturno = genericaJornada.getFestivoExtraNocturno();
        if (genericaJornada.getOrdenTrabajo() != null) {
            ordenTrabajo = genericaJornada.getOrdenTrabajo();
            b_orden_tbj = true;
        } else {
            ordenTrabajo = null;
            b_orden_tbj = false;
        }
        horasExtrasInicio = horasExtrasByJornada(genericaJornada);
        cargarMotivos();
        MovilidadUtil.openModal("LiqJornadaDialog");

    }

    /**
     * Carga la lista listaEmpleados con todos los empleado activos que
     * pertenescan al area del usuario que lo solicita.
     */
    public void findEmplActivos() {
        listaEmpleados.clear();
        if (pau == null) {
            MovilidadUtil.addErrorMessage("Aun no está asignado a un área");
            return;
        }
        listaEmpleados = genJornadaEJB.getJornadasByFechaAndArea(genericaJornada.getFecha(), pau.getIdParamArea().getIdParamArea());
        if (listaEmpleados.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay jornadas para hacer cambio");
            return;
        } else {
            MovilidadUtil.openModal("empleado_list_liq_wv");
            MovilidadUtil.updateComponent("formEmpleados_liq:tabla");
        }

    }

    /**
     * Responsable de invocar a los metodos borradoNomina: para borrar una
     * jornada. recalcularJornada: Recalcular las jornadas del empleado.
     *
     * @throws ParseException
     */
    public void borradoNominaAndConsultar() throws ParseException {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (genericaJornada.getLiquidado() != null && genericaJornada.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (genericaJornada.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        flagEliminarNomina();
        if (!flagEliminar) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        borradoNomina();
        if (b_maxHorasExtrasSemanaMes) {
            double horasExtras = horasExtrasByJornada(genericaJornada);
            calcularGenJorExtraBorrar(genericaJornada, horasExtras);
        }
        calcularMasivoBean.recalcularJornada(genericaJornada);
        calcularMasivoBean.cargarDatos();
    }

    /**
     * Método encargado de persistir en BD la gestion del borrado de una
     * jornada. Valida que la jornada ya halla sido liquidada o ya a sido
     * borrada.
     */
    @Transactional
    public void borradoNomina() {
//        genericaJornada.setDominicalCompDiurnaExtra("00:00:00");
//        genericaJornada.setDominicalCompNocturnaExtra("00:00:00");
//        genericaJornada.setDominicalCompDiurnas("00:00:00");
//        genericaJornada.setDominicalCompNocturnas("00:00:00");
        genericaJornada.setNominaBorrada(1);
        genJornadaEJB.nominaBorrada(genericaJornada.getIdGenericaJornada(), 1, user.getUsername());
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
    }

    /**
     * Método responsable de colcular cuanto de hora extra tiene un turno.
     *
     * @param hIni Hora inicio del turno HH:mm:ss
     * @param hFin Hora fin del turno HH:mm:ss
     * @param gjt Objeto GeneticaJornadaTipo el cual contiene la configuracion
     * del turno, esto para saber cuantas horas de descanso tiene el turno.
     * @return retorna las horas extras caluladas. String.
     */
    public String horaExtras(String hIni, String hFin, GenericaJornadaTipo gjt) {
        int extras = (MovilidadUtil.toSecs(hFin) - MovilidadUtil.toSecs(hIni)) - MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales());
        if (extras > 0) {
            if (gjt != null) {
                if (extras > MovilidadUtil.toSecs(gjt.getDescanso())) {
                    return MovilidadUtil.toTimeSec(extras - MovilidadUtil.toSecs(gjt.getDescanso()));
                } else {
                    return "00:00:00";
                }
            } else {
                return MovilidadUtil.toTimeSec(extras);
            }
        } else {
//            System.out.println("EXTRAS 5-->> " + "00:00:00");
            return "00:00:00";
        }
    }

    public void deshacerBorradoNominaAndConsultar() throws ParseException {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        if (genericaJornada.getNominaBorrada() == 0) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        flagDesEliminarNomina();
        if (!flagDesliminar) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        if (b_maxHorasExtrasSemanaMes) {
            double horasExtras = horasExtrasByJornada(genericaJornada);
            if (horasExtras > 0) {
                GenericaJornadaExtra genJornadaExtra = calcularGenJorExtraDeshacerEliminado(genericaJornada, horasExtras);
                if (genJornadaExtra == null) {
                    return;
                }
                if (genJornadaExtra.getIdGenericaJornadaExtra() == null) {
                    genJornadaExtraEjb.create(genJornadaExtra);
                } else {
                    genJornadaExtraEjb.edit(genJornadaExtra);
                }
            }
        }
        dehacerBorradoNomina();
        calcularMasivoBean.recalcularJornada(genericaJornada);
        calcularMasivoBean.cargarDatos();
    }

    /**
     * Actualiza la tabla resumen de que lleva el control del maximo de horas
     * extras semanales y mensuales, cada vez vez que se elimine una jornada.
     *
     * @param gj Objeto GenericaJornada que contiene la jornada eliminada.
     * @param horasExtras Valor double con las horas extras.
     */
    public void calcularGenJorExtraBorrar(GenericaJornada gj, double horasExtras) {
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        horasExtras = horasExtras / 3600;
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
            genJornadaExtraEjb.create(genJornadaExtra);
        } else {
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(gj.getFecha());
            int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            genJornadaExtraEjb.edit(genJornadaExtra);
        }
    }

    /**
     * Actualiza la tabla resumen de que lleva el control del maximo de horas
     * extras semanales y mensuales, cada vez vez que se deshaga la eliminación
     * de una jornada.
     *
     * @param gj Objeto GenericaJornada que contiene la jornada a la que se le
     * ha deshecho la eliminación.
     * @param horasExtras Valor double con las horas extras.
     */
    public GenericaJornadaExtra calcularGenJorExtraDeshacerEliminado(GenericaJornada gj, double horasExtras) {
        double maxHorasExtrasSemana = genJornadaParam.getHorasExtrasSemanales();
        double maxHorasExtrasMes = genJornadaParam.getHorasExtrasMensuales();
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        horasExtras = horasExtras / 3600;
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
        }
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(gj.getFecha());
        int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
        if (numeroSemana == 1) {
            genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana1() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana1() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
        }
        if (numeroSemana == 2) {
            genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana2() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana2() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
        }
        if (numeroSemana == 3) {
            genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana3() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana3() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
        }
        if (numeroSemana == 4) {
            genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana4() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana4() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
        }
        if (numeroSemana == 5) {
            genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana5() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana5() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
        }
        return genJornadaExtra;

    }

    /**
     * Método responsable de deshacer la eliminación de una jornada y persistir
     * el cambio en la BD.
     */
    @Transactional
    public void dehacerBorradoNomina() {

        genericaJornada = genJornadaEJB.find(genericaJornada.getIdGenericaJornada());
//        genericaJornada.setDominicalCompDiurnaExtra("00:00:00");
//        genericaJornada.setDominicalCompNocturnaExtra("00:00:00");
//        genericaJornada.setDominicalCompDiurnas("00:00:00");
//        genericaJornada.setDominicalCompNocturnas("00:00:00");
        genericaJornada.setNominaBorrada(0);
        genJornadaEJB.nominaBorrada(genericaJornada.getIdGenericaJornada(), 0, user.getUsername());
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
    }

    /**
     * Método encargado de validar que la hora inicio no sea mayor a la hora
     * fin.
     *
     * @return retorna un valor boolean.
     */
    boolean validarHoras() {
        return MovilidadUtil.toSecs(genericaJornada.getRealTimeOrigin() == null ? "00:00:00" : genericaJornada.getRealTimeOrigin())
                > MovilidadUtil.toSecs(genericaJornada.getRealTimeDestiny() == null ? "00:00:00" : genericaJornada.getRealTimeDestiny());
    }

    /**
     * Calcula y devuelve valor de horas extras por jornada. Suma las horas
     * ExtraDiurna, ExtraNocturna, FestivoExtraDiurno, FestivoExtraNocturno,
     * DominicalCompNocturnaExtra, DominicalCompDiurnaExtra
     *
     * @param gj
     * @return
     */
    public int horasExtrasByJornada(GenericaJornada gj) {
        int extrasDiurna = MovilidadUtil.toSecs(gj.getExtraDiurna());
        int extrasNocturna = MovilidadUtil.toSecs(gj.getExtraNocturna());
        int festivoExtrasDiurna = MovilidadUtil.toSecs(gj.getFestivoExtraDiurno());
        int festivoExtrasNocturna = MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
        int dominicalExtrasNocturna = MovilidadUtil.toSecs(gj.getDominicalCompNocturnaExtra());
        int dominicalExtrasDiurna = MovilidadUtil.toSecs(gj.getDominicalCompDiurnaExtra());
        return extrasDiurna + extrasNocturna + festivoExtrasDiurna + festivoExtrasNocturna
                + dominicalExtrasNocturna + dominicalExtrasDiurna;
    }

    public void validarOrdenTrabajoAlModificarI(int opc) {
        int extrasViejas = horasExtrasByJornada(genericaJornada);

        int totalHorasExtrasNuevas = MovilidadUtil.toSecs(s_extra_diurna)
                + MovilidadUtil.toSecs(s_extra_nocturna)
                + MovilidadUtil.toSecs(s_festivo_extra_diurno)
                + MovilidadUtil.toSecs(s_festivo_extra_nocturno)
                + MovilidadUtil.toSecs(s_dominical_comp_diurno_extra)
                + MovilidadUtil.toSecs(s_dominical_comp_nocturno_extra);
        if (totalHorasExtrasNuevas > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS)) {
            MovilidadUtil.addErrorMessage("Solo se permiten hasta " + MAX_HORAS_EXTRAS_DIARIAS + " horas extras diarias.");
            return;
        }
        b_orden_tbj = totalHorasExtrasNuevas > extrasViejas;
        if (!b_orden_tbj) {
            b_orden_tbj = (!(genericaJornada.getOrdenTrabajo() == null || (genericaJornada.getOrdenTrabajo() != null
                    && genericaJornada.getOrdenTrabajo().replaceAll("\\s", "").isEmpty())));

        }
    }

    public void validarOrdenTrabajoAlModificarII(int opc) {

        if (genericaJornada == null) {
            return;
        }
        if (genericaJornada.getRealTimeOrigin() == null) {
            return;
        }
        if (genJornadaParam == null) {
            b_orden_tbj = false;
            return;
        }
        if (MovilidadUtil.toSecs(genericaJornada.getRealTimeOrigin()) > MovilidadUtil.toSecs("23:59:59")) {
            MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59.");
            genericaJornada.setRealTimeOrigin("23:59:59");
            return;
        }
        if (genericaJornada.getRealTimeDestiny() == null) {
            return;
        }
        GenericaJornadaTipo gjt = jornadaTEJB.findByHIniAndHFin(genericaJornada.getRealTimeOrigin(), genericaJornada.getRealTimeDestiny(),
                pau.getIdParamArea().getIdParamArea());
        String horasExtrasNuevas = horaExtras(genericaJornada.getRealTimeOrigin(), genericaJornada.getRealTimeDestiny(), gjt);
        int totalExtrasViejas = horasExtrasByJornada(genericaJornada);
        if (MovilidadUtil.toSecs(horasExtrasNuevas) > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS)) {
            MovilidadUtil.addErrorMessage("Solo se permiten hasta " + MAX_HORAS_EXTRAS_DIARIAS + " horas extras diarias.");
            return;
        }
        if (MovilidadUtil.toSecs(horasExtrasNuevas) > totalExtrasViejas
                && genJornadaParam.getRequerirOrdenTrabajo() == 1) {
            b_orden_tbj = true;
        } else {
            if (!(genericaJornada.getOrdenTrabajo() == null || (genericaJornada.getOrdenTrabajo() != null
                    && genericaJornada.getOrdenTrabajo().replaceAll("\\s", "").isEmpty()))) {
                b_orden_tbj = true;
            } else {
                b_orden_tbj = false;
                ordenTrabajo = null;

            }
        }
    }

    /**
     * Método encargado de persistir en BD la gestión de modificar una jornada.
     * Se utiliza en la vista 'EditLiqJornada' al hacer clic sobre el botón
     * 'Guardar', mencionado botón solo persiste la información tal cual se
     * diligencia, esto quiere decir que Rigel no hara un calculo automatico de
     * la jornada.
     */
    public void guardarEdit() throws ParseException {
        if (validarHoras()) {
            MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
            return;
        }
        if (i_genericaJornadaMotivo == 0) {
            MovilidadUtil.addErrorMessage("Seleccione el motivo.");
            return;
        }

        if (genericaJornada.getObservaciones() != null) {
            if (genericaJornada.getObservaciones().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite observación");
                return;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite observación");
            return;
        }
        calcularMasivoBean.cargarMapParamFeriado();
        int extrasViejas = horasExtrasByJornada(genericaJornada);

        int totalHorasExtrasNuevas = MovilidadUtil.toSecs(s_extra_diurna)
                + MovilidadUtil.toSecs(s_extra_nocturna)
                + MovilidadUtil.toSecs(s_festivo_extra_diurno)
                + MovilidadUtil.toSecs(s_festivo_extra_nocturno)
                + MovilidadUtil.toSecs(s_dominical_comp_diurno_extra)
                + MovilidadUtil.toSecs(s_dominical_comp_nocturno_extra);

        GenericaJornadaTipo gjt = jornadaTEJB.findByHIniAndHFin(genericaJornada.getRealTimeOrigin(), genericaJornada.getRealTimeDestiny(),
                pau.getIdParamArea().getIdParamArea());

        String horasExtrasNuevas = horaExtras(genericaJornada.getRealTimeOrigin(), genericaJornada.getRealTimeDestiny(), gjt);
        if (MovilidadUtil.toSecs(horasExtrasNuevas) > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS) || totalHorasExtrasNuevas > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS)) {
            MovilidadUtil.addErrorMessage("Solo se permiten hasta " + MAX_HORAS_EXTRAS_DIARIAS + " horas extras diarias.");
            return;
        }
        if (genJornadaParam != null && genJornadaParam.getRequerirOrdenTrabajo() == 1) {

            if (totalHorasExtrasNuevas > extrasViejas) {
                if (ordenTrabajo == null || (ordenTrabajo != null
                        && ordenTrabajo.replaceAll("\\s", "").isEmpty())) {
                    MovilidadUtil.addErrorMessage("Falta orden de trabajo.");
                    return;
                } else {
                    genericaJornada.setOrdenTrabajo(ordenTrabajo);
                }
            } else {
                if (!(genericaJornada.getOrdenTrabajo() == null || (genericaJornada.getOrdenTrabajo() != null
                        && genericaJornada.getOrdenTrabajo().replaceAll("\\s", "").isEmpty()))) {
                    genericaJornada.setOrdenTrabajo(ordenTrabajo);
                } else {
                    ordenTrabajo = null;
                }
            }
        }
        GenericaJornadaExtra genJorExtra = null;
        if (b_maxHorasExtrasSemanaMes) {
            horasExtrasInicio = extrasViejas;
            horasExtrasFin = totalHorasExtrasNuevas;
            if (horasExtrasInicio != horasExtrasFin) {
                genJorExtra = calcularGenericaJornadaExtra(genericaJornada);
                if (genJorExtra == null) {
                    return;
                }
            }
        }
        //Tipo de calculo manual
        genericaJornada.setDiurnas(s_diurnas);
        genericaJornada.setNocturnas(s_nocturnas);
        genericaJornada.setExtraDiurna(s_extra_diurna);
        genericaJornada.setExtraNocturna(s_extra_nocturna);
        genericaJornada.setFestivoDiurno(s_festivo_diurno);
        genericaJornada.setFestivoNocturno(s_festivo_nocturno);
        genericaJornada.setFestivoExtraDiurno(s_festivo_extra_diurno);
        genericaJornada.setFestivoExtraNocturno(s_festivo_extra_nocturno);
        genericaJornada.setDominicalCompDiurnas(s_dominical_comp_diurno);
        genericaJornada.setDominicalCompNocturnas(s_dominical_comp_nocturno);
        genericaJornada.setDominicalCompDiurnaExtra(s_dominical_comp_diurno_extra);
        genericaJornada.setDominicalCompNocturnaExtra(s_dominical_comp_nocturno_extra);
        genericaJornada.setTipoCalculo(1);
        genericaJornada.setUsername(user.getUsername());
        genericaJornada.setModificado(new Date());
        genericaJornada.setPrgModificada(2);
        genericaJornada.setUserGenera(user.getUsername());
        genericaJornada.setAutorizado(1);
        genericaJornada.setUserAutorizado(user.getUsername());
        genericaJornada.setFechaAutoriza(new Date());
        genericaJornada.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
        String productionTime = timeProduction();
        genericaJornada.setProductionTimeReal(productionTime);
        if (!rol_user.equals("ROLE_DIRGEN")) {
            if (calcularMasivoBean.aprobarHorasFeriadas(genericaJornada)) {
                int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
                if (genJornadaParam != null
                        && genJornadaParam.getCtrlAprobarExtrasFeriadas() == 0
                        && validacionDia == 0) {
                    genJorExtra = null;
                    genericaJornada.setAutorizado(-1);
                    genericaJornada.setFechaAutoriza(null);
                    genericaJornada.setUserAutorizado("");
                    if (genJornadaParam.getNotifica() == 1) {
                        if (genJornadaParam.getEmails() != null) {
                            calcularMasivoBean.notificar(genericaJornada);
                        }
                    }
                }
            }
        }
        genJornadaEJB.edit(genericaJornada);
        if (genJorExtra != null) {
            if (genJorExtra.getIdGenericaJornadaExtra() != null) {
                genJornadaExtraEjb.edit(genJorExtra);
            } else {
                genJornadaExtraEjb.create(genJorExtra);
            }
        }
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
        MovilidadUtil.hideModal("LiqJornadaDialog");
        genericaJornada = null;
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
        if (genericaJornada.getRealTimeOrigin() != null) {
            turnoI1 = MovilidadUtil.toSecs(genericaJornada.getRealTimeOrigin());
        } else {
            turnoI1 = MovilidadUtil.toSecs(genericaJornada.getTimeOrigin());
        }
        if (genericaJornada.getRealTimeDestiny() != null) {
            turnoF1 = MovilidadUtil.toSecs(genericaJornada.getRealTimeDestiny());
        } else {
            turnoF1 = MovilidadUtil.toSecs(genericaJornada.getTimeDestiny());
        }
        int turnoI2 = MovilidadUtil.toSecs(genericaJornada.getHiniTurno2());
        int turnoF2 = MovilidadUtil.toSecs(genericaJornada.getHfinTurno2());
        int turnoI3 = MovilidadUtil.toSecs(genericaJornada.getHiniTurno3());
        int turnoF3 = MovilidadUtil.toSecs(genericaJornada.getHfinTurno3());

        //Calcular el tiempo de produccion de todos los turnos
        int produccionTurno1 = turnoF1 - turnoI1;
        int produccionTurno2 = turnoF2 - turnoI2;
        int produccionTurno3 = turnoF3 - turnoI3;
        //Calcular el tiempo total de produccion
        int produccionReal = produccionTurno1 + produccionTurno2 + produccionTurno3;

        int productionTimeProg = MovilidadUtil.toSecs(genericaJornada.getProductionTime());
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
        if (validarHoras()) {
            MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
            return;
        }
        if (i_genericaJornadaMotivo == 0) {
            MovilidadUtil.addErrorMessage("Seleccione el motivo.");
            return;
        }

        if (genericaJornada.getObservaciones() != null) {
            if (genericaJornada.getObservaciones().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite observación");
                return;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite observación");
            return;
        }

        calcularMasivoBean.cargarMapParamFeriado();
        GenericaJornadaTipo gjt = jornadaTEJB.findByHIniAndHFin(genericaJornada.getRealTimeOrigin(), genericaJornada.getRealTimeDestiny(),
                pau.getIdParamArea().getIdParamArea());
        genericaJornada.setIdGenericaJornadaTipo(gjt);
        if (genJornadaParam != null && genJornadaParam.getRequerirOrdenTrabajo() == 1) {
            String horasExtras = horaExtras(genericaJornada.getRealTimeOrigin(),
                    genericaJornada.getRealTimeDestiny(), genericaJornada.getIdGenericaJornadaTipo());
            int totalExtrasViejas = horasExtrasByJornada(genericaJornada);
            if (MovilidadUtil.toSecs(horasExtras) > totalExtrasViejas) {
                if (ordenTrabajo == null || (ordenTrabajo != null
                        && ordenTrabajo.replaceAll("\\s", "").isEmpty())) {
                    MovilidadUtil.addErrorMessage("Falta orden de trabajo.");
                    return;
                } else {
                    genericaJornada.setOrdenTrabajo(ordenTrabajo);
                }
            } else {
                if (!(genericaJornada.getOrdenTrabajo() == null || (genericaJornada.getOrdenTrabajo() != null
                        && genericaJornada.getOrdenTrabajo().replaceAll("\\s", "").isEmpty()))) {
                    genericaJornada.setOrdenTrabajo(ordenTrabajo);
                } else {
                    ordenTrabajo = null;
                }
            }
        }

        boolean b_jornadaExtra = false;
        if (b_joranada_flexible) {
            if (validarTrasnochoMadrugada(genericaJornada)) {
                return;
            }
            if (validarHorasExtrasFlexible(genericaJornada, true)) {
                return;
            }
            if (validarMaxHorasExtrasSmanalesFlexible(genericaJornada, true)) {
                return;
            }

        } else {
            if ((MovilidadUtil.toSecs(genericaJornada.getRealTimeDestiny()) - MovilidadUtil.toSecs(genericaJornada.getRealTimeOrigin()))
                    > MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())) {

                if (genericaJornada.getIdGenericaJornadaTipo() != null) {
                    if (!((MovilidadUtil.toSecs(genericaJornada.getRealTimeDestiny())
                            - MovilidadUtil.toSecs(genericaJornada.getRealTimeOrigin())
                            - MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())
                            - MovilidadUtil.toSecs(genericaJornada.getIdGenericaJornadaTipo().getDescanso()))
                            <= MovilidadUtil.toSecs(ConfigJornada.getMax_hrs_extra_dia()))) {
                        MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                                + genericaJornada.getIdEmpleado().getIdentificacion()
                                + "-"
                                + genericaJornada.getIdEmpleado().getNombres()
                                + " "
                                + genericaJornada.getIdEmpleado().getApellidos());
                        return;

                    } else {
                        b_jornadaExtra = true;
                    }
                } else {
                    if (!((MovilidadUtil.toSecs(genericaJornada.getRealTimeDestiny())
                            - MovilidadUtil.toSecs(genericaJornada.getRealTimeOrigin())
                            - MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())) <= MovilidadUtil.toSecs(ConfigJornada.getMax_hrs_extra_dia()))) {
                        MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                                + genericaJornada.getIdEmpleado().getIdentificacion()
                                + "-"
                                + genericaJornada.getIdEmpleado().getNombres()
                                + " "
                                + genericaJornada.getIdEmpleado().getApellidos());
                        return;
                    } else {
                        b_jornadaExtra = true;
                    }
                }
            }
        }

        GenericaJornadaExtra genJorExtra = null;
        if (!b_joranada_flexible && b_maxHorasExtrasSemanaMes) {
            horasExtrasFin = MovilidadUtil.toSecs(horaExtras(genericaJornada.getRealTimeOrigin(), genericaJornada.getRealTimeDestiny(), gjt));
            genJorExtra = calcularGenericaJornadaExtra(genericaJornada);
            if (genJorExtra == null) {
                return;
            }
        }
        genericaJornada.setAutorizado(1);
        genericaJornada.setUserAutorizado(user.getUsername());
        genericaJornada.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        //Tipo de calculo automatico
        genericaJornada.setTipoCalculo(2);
        genericaJornada.setUsername(user.getUsername());
        genericaJornada.setModificado(MovilidadUtil.fechaCompletaHoy());
        genericaJornada.setPrgModificada(1);
        genericaJornada.setUserGenera(user.getUsername());

        genericaJornada.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
        String productionTime = timeProduction();
        genericaJornada.setProductionTimeReal(productionTime);

        boolean b_descanso = false;
        if (genericaJornada.getIdGenericaJornadaTipo() != null) {
            if (MovilidadUtil.toSecs(genericaJornada.getIdGenericaJornadaTipo().getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                int productionTime_ = MovilidadUtil.toSecs(genericaJornada.getProductionTimeReal());
                if (productionTime_ > MovilidadUtil.toSecs(genericaJornada.getIdGenericaJornadaTipo().getDescanso())) {
                    productionTime_ = productionTime_ - MovilidadUtil.toSecs(genericaJornada.getIdGenericaJornadaTipo().getDescanso());
                    genericaJornada.setProductionTimeReal(MovilidadUtil.toTimeSec(productionTime_));
                }
                b_descanso = true;
            }
        }
        boolean flagTipoCalculo = genericaJornada.getIdGenericaJornadaTipo() != null && genericaJornada.getIdGenericaJornadaTipo().getTipoCalculo() == 1;
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        if (b_joranada_flexible) {
            GenericaJornadaLiqUtil param = cargarObjetoParaJar(genericaJornada);
            if (genericaJornada.getIdGenericaJornadaTipo() != null) {
                param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
                param.getIdGenericaJornadaTipo().setDescanso(genericaJornada.getIdGenericaJornadaTipo().getDescanso());
                param.getIdGenericaJornadaTipo().setTipoCalculo(genericaJornada.getIdGenericaJornadaTipo().getTipoCalculo());
            }
            if (MovilidadUtil.isSunday(genericaJornada.getFecha())) {
                List<GenericaJornadaLiqUtil> preCagarHorasDominicales = getJornadaFlexible().preCagarHorasDominicales(genericaJornada.getFecha(),
                        genericaJornada.getIdParamArea().getIdParamArea(),
                        genericaJornada.getIdEmpleado().getIdEmpleado(), param);
                GenericaJornadaLiqUtil get = preCagarHorasDominicales.get(0);
                setValueFromPrgSerconJar(get, genericaJornada);
                genJornadaEJB.edit(genericaJornada);

            } else {
                param.setFecha(genericaJornada.getFecha());
                param.setIdEmpleado(new EmpleadoLiqUtil(genericaJornada.getIdEmpleado().getIdEmpleado()));
                param.setIdParamArea(new ParamAreaLiqUtil(genericaJornada.getIdParamArea().getIdParamArea()));
                param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(genericaJornada.getSercon(), genericaJornada.getWorkTime())));
                ErrorPrgSercon errorPrgSercon
                        = getJornadaFlexible().recalcularHrsExtrasSmnl(param);
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
                List<GenericaJornadaLiqUtil> list = errorPrgSercon.getListaGen().stream()
                        .filter(x -> !fechaDiferente(genericaJornada.getFecha(), x.getFecha()))
                        .collect(Collectors.toList());
                GenericaJornadaLiqUtil get = list.get(0);
                setValueFromPrgSerconJar(get, genericaJornada);
                genJornadaEJB.edit(genericaJornada);
                list = errorPrgSercon.getListaGen().stream()
                        .filter(x -> fechaDiferente(genericaJornada.getFecha(), x.getFecha()))
                        .collect(Collectors.toList());
                genJornadaEJB.updatePrgSerconFromList(list, 0);

            }
        } else {
            if (flagTipoCalculo) {
                calcularMasivoBean.cargarCalcularDatoPorPartes(genericaJornada);
            } else {
                calcularMasivoBean.cargarCalcularDato(genericaJornada, 2);
            }
            if (calcularMasivoBean.validarHorasPositivas(genericaJornada)) {
                MovilidadUtil.addErrorMessage("Error al calcular jornada"
                        + genericaJornada.getIdEmpleado().getIdentificacion()
                        + " - " + genericaJornada.getIdEmpleado().getNombres()
                        + " " + genericaJornada.getIdEmpleado().getApellidos());
                return;
            }
            if (!flagTipoCalculo && (b_descanso && b_jornadaExtra)) {
                calcularExtrasJornadaOrdinaria(genericaJornada);
            }
            if (!rol_user.equals("ROLE_DIRGEN")) {
                if (calcularMasivoBean.aprobarHorasFeriadas(genericaJornada)) {
                    int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
                    if (genJornadaParam != null
                            && genJornadaParam.getCtrlAprobarExtrasFeriadas() == 0
                            && validacionDia == 0) {
                        genJorExtra = null;
                        genericaJornada.setAutorizado(-1);
                        genericaJornada.setFechaAutoriza(null);
                        genericaJornada.setUserAutorizado("");
                        if (genJornadaParam.getNotifica() == 1) {
                            if (genJornadaParam.getEmails() != null) {
                                calcularMasivoBean.notificar(genericaJornada);
                            }
                        }
                    }
                }
            }
            genericaJornada.setDominicalCompDiurnas(null);
            genericaJornada.setDominicalCompNocturnas(null);
            genericaJornada.setDominicalCompDiurnaExtra(null);
            genericaJornada.setDominicalCompNocturnaExtra(null);
            genJornadaEJB.edit(genericaJornada);
            if (genJorExtra != null) {
                if (genJorExtra.getIdGenericaJornadaExtra() == null) {
                    genJornadaExtraEjb.create(genJorExtra);
                } else {
                    genJornadaExtraEjb.edit(genJorExtra);
                }
            }
            calcularMasivoBean.recalcularJornada(genericaJornada);
        }
        calcularMasivoBean.cargarDatos();
        notificarOperador(genericaJornada, "Aprobación Ajuste Jornada");
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
        MovilidadUtil.hideModal("LiqJornadaDialog");
        genericaJornada = null;

    }

    private void calcularExtrasJornadaOrdinaria(GenericaJornada jornada) {
        int festivaExtraNocturna = MovilidadUtil.toSecs(jornada.getFestivoExtraNocturno());
        int festivaExtraDiurna = MovilidadUtil.toSecs(jornada.getFestivoExtraDiurno());
        int extraNocturna = MovilidadUtil.toSecs(jornada.getExtraNocturna());
        int extraDiurna = MovilidadUtil.toSecs(jornada.getExtraDiurna());
        int descanso_ = MovilidadUtil.toSecs(jornada.getIdGenericaJornadaTipo().getDescanso());

        if (festivaExtraNocturna
                > 0) {
            if (festivaExtraNocturna > descanso_) {
                festivaExtraNocturna = festivaExtraNocturna - descanso_;
                descanso_ = 0;
            } else if (descanso_ > festivaExtraNocturna) {
                descanso_ = descanso_ - festivaExtraNocturna;
                festivaExtraNocturna = 0;
            } else if (festivaExtraNocturna == descanso_) {
                festivaExtraNocturna = festivaExtraNocturna - descanso_;
                descanso_ = 0;
            }
        }
        if (descanso_
                > 0) {
            if (festivaExtraDiurna > 0) {
                if (festivaExtraDiurna > descanso_) {
                    festivaExtraDiurna = festivaExtraDiurna - descanso_;
                    descanso_ = 0;
                } else if (descanso_ > festivaExtraDiurna) {
                    descanso_ = descanso_ - festivaExtraDiurna;
                    festivaExtraDiurna = 0;
                } else if (festivaExtraDiurna == descanso_) {
                    festivaExtraDiurna = festivaExtraDiurna - descanso_;
                    descanso_ = 0;
                }
            }
        }
        if (descanso_
                > 0) {
            if (extraNocturna > 0) {
                if (extraNocturna > descanso_) {
                    extraNocturna = extraNocturna - descanso_;
                    descanso_ = 0;
                } else if (descanso_ > extraNocturna) {
                    descanso_ = descanso_ - extraNocturna;
                    extraNocturna = 0;
                } else if (extraNocturna == descanso_) {
                    extraNocturna = extraNocturna - descanso_;
                    descanso_ = 0;
                }
            }
        }
        if (descanso_
                > 0) {
            if (extraDiurna > 0) {
                if (extraDiurna > descanso_) {
                    extraDiurna = extraDiurna - descanso_;
                    descanso_ = 0;
                } else if (descanso_ > extraDiurna) {
                    descanso_ = descanso_ - extraDiurna;
                    extraDiurna = 0;
                } else if (extraDiurna == descanso_) {
                    extraDiurna = extraDiurna - descanso_;
                    descanso_ = 0;
                }
            }
        }

        jornada.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(festivaExtraNocturna));
        jornada.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(festivaExtraDiurna));
        jornada.setExtraNocturna(MovilidadUtil.toTimeSec(extraNocturna));
        jornada.setExtraDiurna(MovilidadUtil.toTimeSec(extraDiurna));
    }

    public GenericaJornadaExtra calcularGenericaJornadaExtra(GenericaJornada gj) {
        double maxHorasExtrasSemana = genJornadaParam.getHorasExtrasSemanales();
        double maxHorasExtrasMes = genJornadaParam.getHorasExtrasMensuales();
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
        }
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(gj.getFecha());
        int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
        horasExtrasInicio = horasExtrasInicio / 3600;
        horasExtrasFin = horasExtrasFin / 3600;
        if (horasExtrasInicio > horasExtrasFin) {
            double dif = horasExtrasInicio - horasExtrasFin;
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
        } else if (horasExtrasInicio < horasExtrasFin) {
            double dif = horasExtrasFin - horasExtrasInicio;
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana1() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana1() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana2() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana2() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana3() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana3() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana4() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana4() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana5() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana5() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
        }
        return genJornadaExtra;
    }

    /**
     * Método encargado de calcular y persistir en BD una jornda por medio del
     * submenu por medio de un clic derecho del datatable en la vista principal.
     *
     */
    public void calcularPrgSercon() throws ParseException {
        String timeOrigin;
        String timeDestiny;
        calcularMasivoBean.cargarMapParamFeriado();
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        if (genericaJornada.getLiquidado() != null && genericaJornada.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (genericaJornada.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (genericaJornada.getPrgModificada() != null && genericaJornada.getPrgModificada() == 2) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        flagCalcularJornada();
        if (flagCalcular) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
//        System.out.println("Valor->>" + validarHorasParaCalcular(genericaJornada));
        boolean realTime = calcularMasivoBean.isRealTime(genericaJornada.getAutorizado(),
                genericaJornada.getPrgModificada(), genericaJornada.getRealTimeOrigin());
        if (realTime) {
            timeOrigin = genericaJornada.getRealTimeOrigin();
            timeDestiny = genericaJornada.getRealTimeDestiny();
        } else {
            timeOrigin = genericaJornada.getTimeOrigin();
            timeDestiny = genericaJornada.getTimeDestiny();
        }
        horasExtrasInicio = horasExtrasByJornada(genericaJornada);
        GenericaJornadaTipo gjt = jornadaTEJB.findByHIniAndHFin(timeOrigin, timeDestiny, pau.getIdParamArea().getIdParamArea());
        genericaJornada.setIdGenericaJornadaTipo(gjt);
        GenericaJornadaExtra genJorExtra = null;
        if (b_maxHorasExtrasSemanaMes) {
            horasExtrasFin = MovilidadUtil.toSecs(horaExtras(timeOrigin, timeDestiny, gjt));
            genJorExtra = calcularGenericaJornadaExtra(genericaJornada);
            if (genJorExtra == null) {
                return;
            }
        }

        boolean b_jornadaExtra = false;
        if (b_joranada_flexible) {
            if (validarTrasnochoMadrugada(genericaJornada)) {
                return;
            }
            if (validarHorasExtrasFlexible(genericaJornada, realTime)) {
                return;
            }
            if (validarMaxHorasExtrasSmanalesFlexible(genericaJornada, realTime)) {
                return;
            }

        } else {
            if ((MovilidadUtil.toSecs(timeDestiny) - MovilidadUtil.toSecs(timeOrigin))
                    > MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())) {

                if (genericaJornada.getIdGenericaJornadaTipo() != null) {
                    if (!((MovilidadUtil.toSecs(timeDestiny)
                            - MovilidadUtil.toSecs(timeOrigin)
                            - MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())
                            - MovilidadUtil.toSecs(genericaJornada.getIdGenericaJornadaTipo().getDescanso()))
                            <= MovilidadUtil.toSecs(ConfigJornada.getMax_hrs_extra_dia()))) {
                        MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                                + genericaJornada.getIdEmpleado().getIdentificacion()
                                + "-"
                                + genericaJornada.getIdEmpleado().getNombres()
                                + " "
                                + genericaJornada.getIdEmpleado().getApellidos());
                        return;

                    } else {
                        b_jornadaExtra = true;
                    }
                } else {
                    System.out.println("Sin tipo jornada" + timeOrigin + "-" + timeDestiny);
                    if (!((MovilidadUtil.toSecs(timeDestiny)
                            - MovilidadUtil.toSecs(timeOrigin)
                            - MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales())) <= MovilidadUtil.toSecs(ConfigJornada.getMax_hrs_extra_dia()))) {
                        MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                                + genericaJornada.getIdEmpleado().getIdentificacion()
                                + "-"
                                + genericaJornada.getIdEmpleado().getNombres()
                                + " "
                                + genericaJornada.getIdEmpleado().getApellidos());
                        return;
                    } else {
                        b_jornadaExtra = true;
                    }
                }
            }
        }
        boolean b_descanso = false;

        if (genericaJornada.getIdGenericaJornadaTipo()
                != null) {
            if (MovilidadUtil.toSecs(genericaJornada.getIdGenericaJornadaTipo().getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                int productionTime_ = 0;
                if (genericaJornada.getProductionTimeReal() != null) {
                    productionTime_ = MovilidadUtil.toSecs(genericaJornada.getProductionTimeReal());
                } else {
                    productionTime_ = MovilidadUtil.toSecs(genericaJornada.getProductionTime());
                }
                if (productionTime_ > MovilidadUtil.toSecs(genericaJornada.getIdGenericaJornadaTipo().getDescanso())) {
                    productionTime_ = productionTime_ - MovilidadUtil.toSecs(genericaJornada.getIdGenericaJornadaTipo().getDescanso());
                    genericaJornada.setProductionTimeReal(MovilidadUtil.toTimeSec(productionTime_));
                }
                b_descanso = true;
            }
        }

        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        if (b_joranada_flexible) {
            Integer AutorizadoBK = genericaJornada.getAutorizado();
            genericaJornada.setAutorizado(realTime ? 1 : null);
            GenericaJornadaLiqUtil param = cargarObjetoParaJar(genericaJornada);
            if (genericaJornada.getIdGenericaJornadaTipo() != null) {
                param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
                param.getIdGenericaJornadaTipo().setDescanso(genericaJornada.getIdGenericaJornadaTipo().getDescanso());
                param.getIdGenericaJornadaTipo().setTipoCalculo(genericaJornada.getIdGenericaJornadaTipo().getTipoCalculo());
            }
            if (MovilidadUtil.isSunday(genericaJornada.getFecha())) {
                List<GenericaJornadaLiqUtil> preCagarHorasDominicales = getJornadaFlexible().preCagarHorasDominicales(genericaJornada.getFecha(),
                        genericaJornada.getIdParamArea().getIdParamArea(),
                        genericaJornada.getIdEmpleado().getIdEmpleado(), param);
                GenericaJornadaLiqUtil get = preCagarHorasDominicales.get(0);
                setValueFromPrgSerconJar(get, genericaJornada);
            } else {
                param.setFecha(genericaJornada.getFecha());
                param.setIdEmpleado(new EmpleadoLiqUtil(genericaJornada.getIdEmpleado().getIdEmpleado()));
                param.setIdParamArea(new ParamAreaLiqUtil(genericaJornada.getIdParamArea().getIdParamArea()));
                param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(genericaJornada.getSercon(), genericaJornada.getWorkTime())));
                ErrorPrgSercon errorPrgSercon
                        = getJornadaFlexible().recalcularHrsExtrasSmnl(param);
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
                List<GenericaJornadaLiqUtil> list = errorPrgSercon.getListaGen().stream()
                        .filter(x -> !fechaDiferente(genericaJornada.getFecha(), x.getFecha()))
                        .collect(Collectors.toList());
                GenericaJornadaLiqUtil get = list.get(0);
                setValueFromPrgSerconJar(get, genericaJornada);
                genericaJornada.setAutorizado(AutorizadoBK);
                genJornadaEJB.edit(genericaJornada);
                list = errorPrgSercon.getListaGen().stream()
                        .filter(x -> fechaDiferente(genericaJornada.getFecha(), x.getFecha()))
                        .collect(Collectors.toList());
                genJornadaEJB.updatePrgSerconFromList(list, 0);

            }
        } else {
            boolean flagTipoCalculo = genericaJornada.getIdGenericaJornadaTipo() != null && genericaJornada.getIdGenericaJornadaTipo().getTipoCalculo() == 1;
            System.out.println("flagTipoCalculo->" + flagTipoCalculo);
            if (flagTipoCalculo) {
                calcularMasivoBean.cargarCalcularDatoPorPartes(genericaJornada);
            } else {
                calcularMasivoBean.cargarCalcularDato(genericaJornada, 2);
            }

            if (calcularMasivoBean.validarHorasPositivas(genericaJornada)) {
                MovilidadUtil.addErrorMessage("Error al calcular jornada"
                        + genericaJornada.getIdEmpleado().getIdentificacion()
                        + " - " + genericaJornada.getIdEmpleado().getNombres()
                        + " " + genericaJornada.getIdEmpleado().getApellidos());
                return;
            }
            if (!flagTipoCalculo && (b_descanso && b_jornadaExtra)) {
                calcularExtrasJornadaOrdinaria(genericaJornada);
            }

            if (!rol_user.equals(
                    "ROLE_DIRGEN")) {
                if (calcularMasivoBean.aprobarHorasFeriadas(genericaJornada)) {
                    int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
                    if (genJornadaParam != null
                            && genJornadaParam.getCtrlAprobarExtrasFeriadas() == 0
                            && validacionDia == 0) {
                        genJorExtra = null;
                        genericaJornada.setAutorizado(-1);
                        genericaJornada.setFechaAutoriza(null);
                        genericaJornada.setUserAutorizado("");
                        if (genJornadaParam.getNotifica() == 1) {
                            if (genJornadaParam.getEmails() != null) {
                                calcularMasivoBean.notificar(genericaJornada);
                            }
                        }
                    }
                }
            }

            genericaJornada.setDominicalCompDiurnas(
                    null);
            genericaJornada.setDominicalCompNocturnas(
                    null);
            genericaJornada.setDominicalCompDiurnaExtra(
                    null);
            genericaJornada.setDominicalCompNocturnaExtra(
                    null);
            genJornadaEJB.edit(genericaJornada);
            if (genJorExtra
                    != null) {
                if (genJorExtra.getIdGenericaJornadaExtra() == null) {
                    genJornadaExtraEjb.create(genJorExtra);
                } else {
                    genJornadaExtraEjb.edit(genJorExtra);
                }
            }

            calcularMasivoBean.recalcularJornada(genericaJornada);
        }
        calcularMasivoBean.cargarDatos();

        MovilidadUtil.addSuccessMessage(
                "Jornada calculada con exito");
    }

    public String fortmatFecha(int valor) {
        if (valor == 0) {
            return Util.dateFormat(calcularMasivoBean.getFechaDesde());
        } else {
            return Util.dateFormat(calcularMasivoBean.getFechaHasta());
        }
    }

    /**
     * Método encargado de liquidar una jornada, accionado la opción liquidar
     * del submenu contextual invocado con clic dereco sobre el registro en
     * cuestión, se le setteará un '1' en el campo liquidado.
     */
    @Transactional
    public void liquidarUno() {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (genericaJornada.getLiquidado() != null && genericaJornada.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        flagLiquidarJornada();
        if (flagLiquidar) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        try {
            int up = genJornadaEJB.liquidarPorId(genericaJornada.getIdGenericaJornada(), user.getUsername());
            if (up > 0) {
                MovilidadUtil.addSuccessMessage("Jornada del operador " + genericaJornada.getIdEmpleado().getCodigoTm() + " liquidada Exitosamente");
                genericaJornada.setLiquidado(1);
            } else {
                MovilidadUtil.addAdvertenciaMessage("Jornada del operador " + genericaJornada.getIdEmpleado().getCodigoTm() + " no liquidada");
                genericaJornada.setLiquidado(0);
            }
            calcularMasivoBean.cargarDatos();

        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error inesperado al liquidar jornada, contacte al administrador");
            genericaJornada.setLiquidado(0);
        }
    }

    public void flagCalculo() {
        if (genericaJornada.getAutorizado() == null) {
            flagCalculo = false;
            return;
        }
        flagCalculo = (genericaJornada.getAutorizado() == 1);
    }

    public void flagEliminarNomina() {
        flagEliminar = genericaJornada.getNominaBorrada() == 0 && (genericaJornada.getLiquidado() == null || genericaJornada.getLiquidado() == 0);
    }

    public void flagDesEliminarNomina() {
        flagDesliminar = genericaJornada.getNominaBorrada() == 1 && genericaJornada.getLiquidado() == 0;
    }

    public void sabadoDomingo(GenericaJornada p) {
        flagSabDom = MovilidadUtil.isDay(p.getFecha(), Calendar.SATURDAY)
                || MovilidadUtil.isDay(p.getFecha(), Calendar.SUNDAY);
    }

    public void flagCalcularJornada() {
        flagCalcular = (!flagCalculo || genericaJornada.getNominaBorrada() == 1 || genericaJornada.getLiquidado() == 1 || genericaJornada.getPrgModificada() == 2);
    }

    public void flagCambioEmpleado() {
        flagCambioEmpleado = genericaJornada.getLiquidado() == 1;
    }

    public void flagLiquidarJornada() {
        flagLiquidar = genericaJornada.getLiquidado() == 1
                || genericaJornada.getPrgModificada() == 0
                || genericaJornada.getPrgModificada() == 2
                || (genericaJornada.getAutorizado() != null && genericaJornada.getAutorizado() < 1);
    }

    public void flagModificarJornada() {
        flagModificar = genericaJornada.getNominaBorrada() == 1 || genericaJornada.getLiquidado() == 1;
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
        setGenericaJornada((GenericaJornada) event.getObject());
        genericaJornada = genJornadaEJB.find(genericaJornada.getIdGenericaJornada());
        setGenericaJornada(genericaJornada);
        flagCalculo();
        flagEliminarNomina();
        flagDesEliminarNomina();
        sabadoDomingo(genericaJornada);
        flagLiquidarJornada();
        flagCalcularJornada();
        flagModificarJornada();
        flagCambioEmpleado();
    }

    public void resetObj() {
        setGenericaJornada(null);

    }

    /**
     * Método responsable de convertir una jornada de día hábil a una jornda con
     * horas Festivas y persistir la información en BD.
     *
     * Invoca al método sabadoDomingo, responsable de identificar si la jornada
     * en cuestión aplica para un día sabado o domingo.
     *
     * @throws java.text.ParseException
     */
    public void convertirHoras() throws ParseException {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        if (genericaJornada.getLiquidado() != null && genericaJornada.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (genericaJornada.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        sabadoDomingo(genericaJornada);
        if (!flagSabDom) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }
        try {
            int festivoDiurno = MovilidadUtil.toSecs(genericaJornada.getDiurnas()) + MovilidadUtil.toSecs(genericaJornada.getFestivoDiurno());
            int festivoNocturno = MovilidadUtil.toSecs(genericaJornada.getNocturnas()) + MovilidadUtil.toSecs(genericaJornada.getFestivoNocturno());
            int festivoExtraDiurno = MovilidadUtil.toSecs(genericaJornada.getExtraDiurna()) + MovilidadUtil.toSecs(genericaJornada.getFestivoExtraDiurno());
            int festivoExtraNocturna = MovilidadUtil.toSecs(genericaJornada.getExtraNocturna()) + MovilidadUtil.toSecs(genericaJornada.getFestivoExtraNocturno());

            genericaJornada.setFestivoDiurno(MovilidadUtil.toTimeSec(festivoDiurno));
            genericaJornada.setFestivoNocturno(MovilidadUtil.toTimeSec(festivoNocturno));
            genericaJornada.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(festivoExtraDiurno));
            genericaJornada.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(festivoExtraNocturna));

            genericaJornada.setDiurnas("00:00:00");
            genericaJornada.setNocturnas("00:00:00");
            genericaJornada.setExtraDiurna("00:00:00");
            genericaJornada.setExtraNocturna("00:00:00");

            genJornadaEJB.edit(genericaJornada);
            calcularMasivoBean.recalcularJornada(genericaJornada);
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
    public void convertirHorasMasivo() throws ParseException {
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(calcularMasivoBean.getFechaDesde(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        for (GenericaJornada ps : calcularMasivoBean.getGenericaJornadaList()) {
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

                    ps.setDiurnas("00:00:00");
                    ps.setNocturnas("00:00:00");
                    ps.setExtraDiurna("00:00:00");
                    ps.setExtraNocturna("00:00:00");
                    if (ps.getLiquidado() != 1 && ps.getNominaBorrada() != 1) {
                        genJornadaEJB.edit(ps);
                    }
                }
            } catch (Exception e) {
                MovilidadUtil.addErrorMessage("Error al convertir horas");
            }
        }

        MovilidadUtil.addSuccessMessage("Se cambiaron las horas exitosamente");

    }

    /**
     * Método responsable de preparar la variables para la gestión de cambio de
     * empleado. Carga la lista de motivos que se presentaran en el formulario.
     *
     * visualiza el formulario donde se trabajará.
     */
    public void cambioEmpleado() throws ParseException {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la jornada");
            return;
        }
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        if (genericaJornada.getLiquidado() != null && genericaJornada.getLiquidado() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        if (genericaJornada.getNominaBorrada() == 1) {
            MovilidadUtil.addErrorMessage("Opción no válida");
            return;
        }
        emplPrgSercon = null;
        codigoTransMi = "";
        nombreEmpleado = "";
        empleadoPrgSercon = new Empleado(genericaJornada.getIdEmpleado().getIdEmpleado());
        if (genericaJornada.getIdGenericaJornadaMotivo() != null) {
            i_genericaJornadaMotivo = genericaJornada.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo();
        }
        cargarMotivos();
        MovilidadUtil.openModal("cambioEmplWV");

    }

    /**
     * Método responsable de invocar los metodos guardarcambioEmplTransactional:
     * Sirve para persisitir en BD la gestión de cambio de empleado.
     * cargarDatos, cargar la tabla con la data nueva.
     */
    public void guardarcambioEmplAndConsultar() {
        guardarcambioEmpl();
        calcularMasivoBean.cargarDatos();
    }

    /**
     * Método reponsable de persistir en BD la gestion del proceso de cambio de
     * empleado.
     *
     * Cierra la ventana modal, al finalizar la persistencia.
     */
    @Transactional
    public void guardarcambioEmpl() {
        if (i_genericaJornadaMotivo == 0) {
            MovilidadUtil.addErrorMessage("No se a seleccionado el motivo");
        }
        if (emplPrgSercon == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado empleado para cambio");
        }
        if (genericaJornada.getObservaciones() != null) {
            if (genericaJornada.getObservaciones().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite observación");
                return;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite observación");
            return;
        }

        GenericaJornadaExtra genJorExtraEmplPrgSercon = null;
        GenericaJornadaExtra genJorExtraGenericaJornada = null;
        if (b_maxHorasExtrasSemanaMes) {
            horasExtrasInicio = horasExtrasByJornada(genericaJornada);
            horasExtrasFin = horasExtrasByJornada(emplPrgSercon);

            if (horasExtrasInicio != horasExtrasFin) {
                genJorExtraEmplPrgSercon = calcularGenericaJornadaExtra(genericaJornada);
                if (genJorExtraEmplPrgSercon == null) {
                    return;
                }
            }
            horasExtrasFin = horasExtrasByJornada(genericaJornada);
            horasExtrasInicio = horasExtrasByJornada(emplPrgSercon);

            if (horasExtrasInicio != horasExtrasFin) {
                genJorExtraGenericaJornada = calcularGenericaJornadaExtra(emplPrgSercon);
                if (genJorExtraGenericaJornada == null) {
                    return;
                }
            }
        }
        /**
         * Objeto jornada seleccionada por el usuario.
         */
        genericaJornada.setUserGenera(user.getUsername());
        genericaJornada.setUserAutorizado(user.getUsername());
        genericaJornada.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        genericaJornada.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        genericaJornada.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
        genericaJornada.setIdEmpleado(emplPrgSercon.getIdEmpleado());
        genericaJornada.setUsername(user.getUsername());
        /**
         * Objeto jornada del empleado consultado en la gestión.
         */
        emplPrgSercon.setUserGenera(user.getUsername());
        emplPrgSercon.setUserAutorizado(user.getUsername());
        emplPrgSercon.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        emplPrgSercon.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        emplPrgSercon.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
        emplPrgSercon.setIdEmpleado(empleadoPrgSercon);
        emplPrgSercon.setObservaciones(genericaJornada.getObservaciones());
        emplPrgSercon.setUsername(user.getUsername());

        genJornadaEJB.cambioEmpleado(genericaJornada, emplPrgSercon);
        calcularMasivoBean.recalcularJornada(genericaJornada);
        calcularMasivoBean.recalcularJornada(emplPrgSercon);
        if (genJorExtraEmplPrgSercon != null) {
            if (genJorExtraEmplPrgSercon.getIdGenericaJornadaExtra() != null) {
                genJornadaExtraEjb.edit(genJorExtraEmplPrgSercon);
            } else {
                genJornadaExtraEjb.create(genJorExtraEmplPrgSercon);
            }
        }
        if (genJorExtraGenericaJornada != null) {
            if (genJorExtraGenericaJornada.getIdGenericaJornadaExtra() != null) {
                genJornadaExtraEjb.edit(genJorExtraGenericaJornada);
            } else {
                genJornadaExtraEjb.create(genJorExtraGenericaJornada);
            }
        }
        MovilidadUtil.addSuccessMessage("Se hizo el cambio uno a uno de empleados exitosamente");
        MovilidadUtil.hideModal("cambioEmplWV");
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
                emplPrgSercon = genJornadaEJB.getPrgSerconByCodigoTM(codigoTransMi, genericaJornada.getFecha());
                if (emplPrgSercon != null) {
                    ParamAreaCargo pac = areaCargoEJB.getByCargoArea(emplPrgSercon.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo(), pau.getIdParamArea().getIdParamArea());
                    if (pac == null) {
                        MovilidadUtil.addErrorMessage("El empleado no esta en la misma área");
                        emplPrgSercon = null;
                        nombreEmpleado = "";
                        return;
                    }
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

    public void onRowDblClckSelect(final SelectEvent event) throws Exception {
        if (emplPrgSercon == null) {
            emplPrgSercon = new GenericaJornada();
        }
        setEmplPrgSercon((GenericaJornada) event.getObject());
        nombreEmpleado = emplPrgSercon.getIdEmpleado().getIdentificacion()
                + " " + emplPrgSercon.getIdEmpleado().getNombres()
                + " " + emplPrgSercon.getIdEmpleado().getApellidos();
        if (emplPrgSercon.getIdGenericaJornada() == genericaJornada.getIdGenericaJornada()) {
            MovilidadUtil.addErrorMessage("Ha seleccionado al mismo empleado");
            MovilidadUtil.updateComponent("msgs");
            return;
        }
        MovilidadUtil.addSuccessMessage("Empleado Cargado.");
        MovilidadUtil.updateComponent("form_cambioEmpl:msgs_cambio_empl");
        MovilidadUtil.updateComponent("form_cambioEmpl:info_empl");
        MovilidadUtil.updateComponent("form_cambioEmpl:codigo_operador");
        MovilidadUtil.hideModal("empleado_list_liq_wv");

    }

    private GenericaJornadaLiqUtil cargarObjetoParaJar(GenericaJornada jornada) {
        String timeOrigin1 = jornada.getTimeOrigin();
        String timeDestiny1 = jornada.getTimeDestiny();
        if (jornada.getAutorizado() != null && jornada.getAutorizado() == 1) {
            timeOrigin1 = jornada.getRealTimeOrigin();
            timeDestiny1 = jornada.getRealTimeDestiny();
        }
        return new GenericaJornadaLiqUtil(timeOrigin1,
                timeDestiny1);
    }

    private void setValueFromPrgSerconJar(GenericaJornadaLiqUtil param1, GenericaJornada param2) {
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

    boolean validarHorasExtrasFlexible(GenericaJornada jornada, boolean realTime) throws ParseException {
        GenericaJornadaLiqUtil param = new GenericaJornadaLiqUtil();
        if (jornada.getIdGenericaJornadaTipo() != null) {
            param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
            param.getIdGenericaJornadaTipo().setDescanso(jornada.getIdGenericaJornadaTipo().getDescanso());
            param.getIdGenericaJornadaTipo().setTipoCalculo(jornada.getIdGenericaJornadaTipo().getTipoCalculo());
        }
        String iniTurno1 = jornada.getTimeOrigin();
        String finTurno1 = jornada.getTimeDestiny();
        if (realTime) {
            iniTurno1 = jornada.getRealTimeOrigin();
            finTurno1 = jornada.getRealTimeDestiny();
        }
        param.setFecha(jornada.getFecha());
        param.setTimeOrigin(iniTurno1);
        param.setTimeDestiny(finTurno1);
        param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(jornada.getSercon(), jornada.getWorkTime())));
        ErrorPrgSercon validateMaxHrsDia = getJornadaFlexible().validateMaxHrsDia(param);
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

    boolean validarMaxHorasExtrasSmanalesFlexible(GenericaJornada jornada, boolean realTime) throws ParseException {
        String iniTurno1 = jornada.getTimeOrigin();
        String finTurno1 = jornada.getTimeDestiny();
        if (realTime) {
            iniTurno1 = jornada.getRealTimeOrigin();
            finTurno1 = jornada.getRealTimeDestiny();
        }
        GenericaJornadaLiqUtil param = new GenericaJornadaLiqUtil();
        if (jornada.getIdGenericaJornadaTipo() != null) {
            param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
            param.getIdGenericaJornadaTipo().setDescanso(jornada.getIdGenericaJornadaTipo().getDescanso());
            param.getIdGenericaJornadaTipo().setTipoCalculo(jornada.getIdGenericaJornadaTipo().getTipoCalculo());
        }
        param.setTimeOrigin(iniTurno1);
        param.setTimeDestiny(finTurno1);
        param.setFecha(jornada.getFecha());
        param.setIdEmpleado(new EmpleadoLiqUtil(jornada.getIdEmpleado().getIdEmpleado()));
        param.setIdParamArea(new ParamAreaLiqUtil(jornada.getIdParamArea().getIdParamArea()));
        param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(jornada.getSercon(), jornada.getWorkTime())));
        ErrorPrgSercon validateMaxHrsSmnl = getJornadaFlexible().validateMaxHrsSmnl(param);
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

    private boolean validarTrasnochoMadrugada(GenericaJornada solicitanteAct) throws ParseException {
        GenericaJornada jornadaSolicitanteAnt;
        GenericaJornada jornadaSolicitanteSig;
        String hIniTurnoACambiar;
        int dif;
        Date fechaAnterior = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), -1);
        Date fechaSiguiente = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), 1);

        jornadaSolicitanteAnt = genJornadaEJB.validarEmplSinJornadaByParamAreaFechaEmpleado(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaAnterior, 0);
        boolean despuesDeEjecutado = UtilJornada.registroDespuesDeEjecutado(solicitanteAct.getFecha(),
                UtilJornada.ultimaHoraRealJornada(solicitanteAct));
        String msg = "El turno NO permite respetar las 12 horas de descanso reglamentarias";
        if (jornadaSolicitanteAnt != null) {
            /**
             * Caso 2: Verificar si el operador cumple 12 horas de descanso con
             * respecto al turno del día anterior.
             */
            String sTimeOriginRemp = solicitanteAct.getRealTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginRemp) == 0) {
                sTimeOriginRemp = "24:00:00";
            }

            String timeDestinyDiaAnterior = jornadaSolicitanteAnt.getTimeDestiny();
            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, "24:00:00");
            if (calcularMasivoBean.isRealTime(jornadaSolicitanteAnt.getAutorizado(),
                    jornadaSolicitanteAnt.getPrgModificada(), jornadaSolicitanteAnt.getRealTimeOrigin())) {
                timeDestinyDiaAnterior = jornadaSolicitanteAnt.getRealTimeDestiny();
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

        jornadaSolicitanteSig = genJornadaEJB.validarEmplSinJornadaByParamAreaFechaEmpleado(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaSiguiente, 0);

        if (jornadaSolicitanteSig != null) {
            /**
             * Caso 2: Verificar si el operador solicitante cumple 12 horas de
             * descanso con respecto a la jornada del día siguiente.
             */
            String sTimeOriginRempSig = jornadaSolicitanteSig.getTimeOrigin();
            if (calcularMasivoBean.isRealTime(jornadaSolicitanteSig.getAutorizado(),
                    jornadaSolicitanteSig.getPrgModificada(), jornadaSolicitanteSig.getRealTimeOrigin())) {
                sTimeOriginRempSig = jornadaSolicitanteSig.getRealTimeOrigin();
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
        flagFeriado = paramFeriadoEjb.findByFecha(fecha) != null;
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
        ParamFeriado paramFeriado = paramFeriadoEjb.findByFecha(fecha);
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

    private void notificarOperador(GenericaJornada gj, String asunto) {
        try {
            NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOTIFICACION_AJUSTE_JORNADA);
            if (template == null) {
                return;
            }
            Map mapa = getMailParams();
            mapa.replace("template", template.getPath());
            Map mailProperties = new HashMap();
            Empleado empl = gj.getIdEmpleado();
            GenericaJornadaMotivo motivo = jornadaMotivoEJB.find(gj.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo());
            mailProperties.put("identificacion", empl.getIdentificacion());
            mailProperties.put("nombre", empl.getNombres() + " " + empl.getApellidos());
            mailProperties.put("fecha", Util.dateFormat(gj.getFecha()));
            mailProperties.put("hora_ini_prg", gj.getTimeOrigin());
            mailProperties.put("hora_fin_prg", gj.getTimeDestiny());
            mailProperties.put("hora_ini_real", gj.getRealTimeOrigin());
            mailProperties.put("hora_fin_real", gj.getRealTimeDestiny());
            mailProperties.put("user_name", user.getUsername());
            mailProperties.put("motivo", motivo.getDescripcion());
            mailProperties.put("observacion", gj.getObservaciones());
            SendMails.sendEmail(mapa, mailProperties, asunto, "", empl.getEmailCorporativo(), "Notificaciones RIGEL", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(1);
        NotificacionTemplate template = notificacionTemplateEjb.find(ConstantsUtil.TEMPLATEGENERICAJORNADA);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    public GenericaJornada getGenericaJornada() {
        return genericaJornada;
    }

    public void setGenericaJornada(GenericaJornada genericaJornada) {
        this.genericaJornada = genericaJornada;
    }

    public List<GenericaJornadaMotivo> getGenericaJornadaMotivoList() {
        return genericaJornadaMotivoList;
    }

    public void setGenericaJornadaMotivoList(List<GenericaJornadaMotivo> genericaJornadaMotivoList) {
        this.genericaJornadaMotivoList = genericaJornadaMotivoList;
    }

    public int getI_genericaJornadaMotivo() {
        return i_genericaJornadaMotivo;
    }

    public void setI_genericaJornadaMotivo(int i_genericaJornadaMotivo) {
        this.i_genericaJornadaMotivo = i_genericaJornadaMotivo;
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

    public GenericaJornada getEmplPrgSercon() {
        return emplPrgSercon;
    }

    public void setEmplPrgSercon(GenericaJornada emplPrgSercon) {
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

    public boolean isB_permiso() {
        return b_permiso;
    }

    public void setB_permiso(boolean b_permiso) {
        this.b_permiso = b_permiso;
    }

    public List<GenericaJornada> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<GenericaJornada> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public boolean isFlagCambioEmpleado() {
        return flagCambioEmpleado;
    }

    public void setFlagCambioEmpleado(boolean flagCambioEmpleado) {
        this.flagCambioEmpleado = flagCambioEmpleado;
    }

    public String getS_diurnas() {
        return s_diurnas;
    }

    public void setS_diurnas(String s_diurnas) {
        this.s_diurnas = s_diurnas;
    }

    public String getS_nocturnas() {
        return s_nocturnas;
    }

    public void setS_nocturnas(String s_nocturnas) {
        this.s_nocturnas = s_nocturnas;
    }

    public String getS_extra_diurna() {
        return s_extra_diurna;
    }

    public void setS_extra_diurna(String s_extra_diurna) {
        this.s_extra_diurna = s_extra_diurna;
    }

    public String getS_extra_nocturna() {
        return s_extra_nocturna;
    }

    public void setS_extra_nocturna(String s_extra_nocturna) {
        this.s_extra_nocturna = s_extra_nocturna;
    }

    public String getS_festivo_diurno() {
        return s_festivo_diurno;
    }

    public void setS_festivo_diurno(String s_festivo_diurno) {
        this.s_festivo_diurno = s_festivo_diurno;
    }

    public String getS_festivo_nocturno() {
        return s_festivo_nocturno;
    }

    public void setS_festivo_nocturno(String s_festivo_nocturno) {
        this.s_festivo_nocturno = s_festivo_nocturno;
    }

    public String getS_festivo_extra_diurno() {
        return s_festivo_extra_diurno;
    }

    public void setS_festivo_extra_diurno(String s_festivo_extra_diurno) {
        this.s_festivo_extra_diurno = s_festivo_extra_diurno;
    }

    public String getS_festivo_extra_nocturno() {
        return s_festivo_extra_nocturno;
    }

    public void setS_festivo_extra_nocturno(String s_festivo_extra_nocturno) {
        this.s_festivo_extra_nocturno = s_festivo_extra_nocturno;
    }

    public boolean isB_orden_tbj() {
        return b_orden_tbj;
    }

    public void setB_orden_tbj(boolean b_orden_tbj) {
        this.b_orden_tbj = b_orden_tbj;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public List<ConsolidadoLiquidacion> getLstConsolidado() {
        return lstConsolidado;
    }

    public void setLstConsolidado(List<ConsolidadoLiquidacion> lstConsolidado) {
        this.lstConsolidado = lstConsolidado;
    }

    public List<ConsolidadoLiquidacionDetallado> getLstConsolidadoDetallado() {
        return lstConsolidadoDetallado;
    }

    public void setLstConsolidadoDetallado(List<ConsolidadoLiquidacionDetallado> lstConsolidadoDetallado) {
        this.lstConsolidadoDetallado = lstConsolidadoDetallado;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String getS_dominical_comp_diurno() {
        return s_dominical_comp_diurno;
    }

    public void setS_dominical_comp_diurno(String s_dominical_comp_diurno) {
        this.s_dominical_comp_diurno = s_dominical_comp_diurno;
    }

    public String getS_dominical_comp_nocturno() {
        return s_dominical_comp_nocturno;
    }

    public void setS_dominical_comp_nocturno(String s_dominical_comp_nocturno) {
        this.s_dominical_comp_nocturno = s_dominical_comp_nocturno;
    }

    public String getS_dominical_comp_diurno_extra() {
        return s_dominical_comp_diurno_extra;
    }

    public void setS_dominical_comp_diurno_extra(String s_dominical_comp_diurno_extra) {
        this.s_dominical_comp_diurno_extra = s_dominical_comp_diurno_extra;
    }

    public String getS_dominical_comp_nocturno_extra() {
        return s_dominical_comp_nocturno_extra;
    }

    public void setS_dominical_comp_nocturno_extra(String s_dominical_comp_nocturno_extra) {
        this.s_dominical_comp_nocturno_extra = s_dominical_comp_nocturno_extra;
    }

    public boolean isFlagFeriado() {
        return flagFeriado;
    }

    public void setFlagFeriado(boolean flagFeriado) {
        this.flagFeriado = flagFeriado;
    }

}
