/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.aja.jornada.controller.JornadaFlexible;
import com.aja.jornada.model.PrgSerconLiqUtil;
import com.dbconnection.Common;
import com.freeway.ArrayOfControlReportLogicControlRow;
import com.freeway.ArrayOfPatternRow;
import com.freeway.ArrayOfstring;
import com.freeway.ControlReportLogicControlRow;
import com.freeway.ISae;
import com.freeway.RouteRow;
import com.freeway.Sae;
import com.freeway.StopPointRow;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PrgRouteFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.ejb.PrgTareaFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcResumenFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.PrgRoute;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTarea;
import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ErrorTc;
import com.movilidad.utils.UtilJornada;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author luis
 */
@Named(value = "prgControlTableMB")
@ViewScoped
public class PrgControlTableMB implements Serializable {

    @EJB
    private PrgRouteFacadeLocal prgRouteEjb;
    @EJB
    private PrgStopPointFacadeLocal prgStopEjb;
    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @EJB
    private PrgTareaFacadeLocal prgTareaEjb;
    @EJB
    private PrgTcResumenFacadeLocal prgTcResumenEjb;
    @EJB
    private VehiculoTipoFacadeLocal vehiTipoEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private PrgSerconFacadeLocal prgSerconFacadeLocal;
    @Inject
    private SelectConfigFreewayBean configFreewayBean;
    private List<PrgTc> listTc;
    private List<PrgTcResumen> listTcResumen;
    List<PrgTcResumen> tcRList;
    private List<PrgStopPoint> listStop;
    private HashMap<String, List<ControlReportLogicControlRow>> controlTableMap;
    private HashMap<String, List<PrgTc>> controlTableValidada;
    private List<List<ControlReportLogicControlRow>> listaTablaControl;
    private List<ErrorTc> listError;

    HashMap<String, Integer> routes;
    HashMap<String, Integer> stopPoints;
//    HashMap<Integer, Integer> employee;
    HashMap<String, Integer> employee;
    HashMap<String, Integer> tipoVehiculo;
    HashMap<String, Integer> tarea;

    private Date fromDate;
    private Date toDate;
    private Date hasta;
    private boolean obtener = true;
    private boolean validar = false;
    private boolean procesar = false;
    private boolean collapsed = false;
    private int activeIndex = 0;
    Sae sae;
    ISae iSae;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private JornadaFlexible jornadaFlexible;

    public JornadaFlexible getJornadaFlexible() {
        if (jornadaFlexible == null) {
            jornadaFlexible = new JornadaFlexible();
        }
        return jornadaFlexible;
    }

    @PostConstruct
    public void init() {
//        fromDate = Util.toDate("2019-05-16");
//        toDate = Util.toDate("2019-05-17");
//        solucion = "Prueba Tunal, Prueba Sur II";
        listError = null;
        listTcResumen = null;
    }

    /**
     * Creates a new instance of prgRouteMB
     */
    public PrgControlTableMB() {
    }

    /**
     * Consume la Tabla de Control de Freeway a través del método getControlData
     * del SAE Service
     */
    public void consumeControlTable() {
        if (toDate.compareTo(fromDate) < 0) {
            MovilidadUtil.addErrorMessage("Fecha Fin no puede ser menor a Fecha Inicio");
            return;
        }
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        if (consultaPrgTc()) {
            MovilidadUtil.addErrorMessage("Existe programación cargada para las fechas indicadas");
            return;
        }
        obtener = false;
        List<String> lista = new LinkedList<>();
//        System.out.println("consumeControlTable");
        ArrayOfPatternRow patterns;
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
//        controlTableMap = new HashMap<>();
//        System.out.println("Solución : " + configFreeway.getCodigoSolucion());
        lista.add(configFreewayBean.getConfigFreeway().getCodigoSolucion());
        ArrayOfstring codeOrganizationList = new ArrayOfstring();
        codeOrganizationList.setString(lista);
        PrgTcResumen tcResumen;
        ArrayOfControlReportLogicControlRow controlTable;
        List<ControlReportLogicControlRow> controlReportLogicControlRowList;
        XMLGregorianCalendar desde;
        try {
//            listaTablaControl = new LinkedList<>();
            tcRList = new LinkedList<>();
            controlTableMap = new HashMap<>();
            while (!current.getTime().after(toDate)) {
                controlTable = new ArrayOfControlReportLogicControlRow();
                controlReportLogicControlRowList = new LinkedList<>();
                desde = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(Util.xmlGregorianFormat(current.getTime()));
                tcResumen = new PrgTcResumen();
                tcResumen.setFecha(current.getTime());
                tcResumen.setUsername(user.getUsername());
//                tcResumen.setUsername("freeway");
                tcResumen.setCreado(new Date());
                for (String organizacion : configFreewayBean.getConfigFreeway().getCodigoSolucion().split(",")) {
                    lista = new LinkedList<>();
                    lista.add(organizacion.trim());
                    codeOrganizationList = new ArrayOfstring();
                    codeOrganizationList.setString(lista);
                    controlTable = getiSae(new URL(
                            configFreewayBean.getConfigFreeway().getUrlServicio())).getControlData(desde, desde, codeOrganizationList);
//                    System.out.println("Fecha : " + Util.dateFormat(current.getTime())+" Solucion : " + organizacion + " Tamaño Solucion : " + controlTable.getControlReportLogicControlRow().size());
                    controlReportLogicControlRowList.addAll(controlTable.getControlReportLogicControlRow());
                }
                System.out.println("Fecha : " + Util.dateFormat(current.getTime()));
                System.out.println("Tamaño TablaControl :" + controlTable.getControlReportLogicControlRow().size());
                System.out.println("Tamaño TablaControl :" + controlReportLogicControlRowList.size());
//                if (controlTable.getControlReportLogicControlRow().size() > 0) {
                if (controlReportLogicControlRowList.size() > 0) {
//                    controlTableMap.put(Util.dateFormat(current.getTime()), controlTable.getControlReportLogicControlRow());
                    controlTableMap.put(Util.dateFormat(current.getTime()), controlReportLogicControlRowList);
//                    processResumen(controlTable.getControlReportLogicControlRow(), tcResumen);
                    processResumen(controlReportLogicControlRowList, tcResumen);
                }
                tcRList.add(tcResumen);
                current.add(Calendar.DATE, 1);
//                listTcResumen = tcRList;
            }
//            System.out.println("tcRList : " + tcRList.size());
            if (controlTableMap.isEmpty()) {
                obtener = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontraron datos"));
            } else {
                validar = true;
                listTcResumen = tcRList;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Datos Obtenidos con Éxito!"));
            }
//            System.out.println("ControlTableMap : " + controlTableMap.size());
//            System.out.println("TCResumen List : " + tcRList.size());
        } catch (Exception e) {
            Logger.getGlobal().log(Level.OFF, e.getMessage());
            System.out.println("e.getMessage() : " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage()));
        }
    }

    /**
     * Valida la Tabla de Control de Freeway obtenida previamente a través del
     * método getControlData del SAE Service
     */
    public void validateControlTable() {
//        System.out.println("validateControlTable");
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        List<PrgTc> listaTC;
        PrgTc tcTest;
        List<ControlReportLogicControlRow> list_ControlRow;
        controlTableValidada = new HashMap<>();
        listError = new LinkedList<>();
        setEmployees();//......Cargar mapa empleados
        setRoutes();//.........Cargar mapa Routes
        setStopPoints();//.....Cargar mapa StopoPoints
        setTipoVehiculos();//..Cargar mapa tipoVehiculo
        setTarea();//..........Cargar mapa PrgTarea
        int firstRecord = 0;
        PrgTcResumen tcResumen;
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        int i = 1;
        while (!current.getTime().after(toDate)) {
            tcResumen = new PrgTcResumen();
            tcResumen.setUsername(user.getUsername());
//            tcResumen.setUsername("freeway");
            tcResumen.setCreado(new Date());
            list_ControlRow = controlTableMap.get(Util.dateFormat(current.getTime()));
            listaTC = new LinkedList<>();
            tcResumen.setFecha(Util.toDate(Util.dateFormat(current.getTime())));
//            prgTcResumenEjb.create(tcResumen);
            for (ControlReportLogicControlRow c : list_ControlRow) {
                if (firstRecord == 0) {
                    firstRecord = 1;
                }
                if (c.getDate().getValue() != null) {
                    tcTest = xmlToTc(c);
//                    System.out.println("TC : " + c.toString());
//                    listaTC.add(xmlToTc(c));
                    listaTC.add(tcTest);
//                    System.out.println(tcTest.toString());
//                    prgTcEjb.create(xmlToTc(c));
                    i++;
                }
//                if (i == 100) {
//                    break;
//                }
            }
            if (!listaTC.isEmpty()) {
                controlTableValidada.put(Util.dateFormat(current.getTime()), listaTC);
            }
            firstRecord = 0;
            current.add(Calendar.DATE, 1);
        }
        if (listError.isEmpty()) {
            validar = false;
            procesar = true;
            collapsed = false;
            activeIndex = 0;
            MovilidadUtil.addSuccessMessage("Datos validados con Éxito!");
            configFreewayBean.setDisabled(true);
        } else {
//            obtener = true;
//            validar = false;
            collapsed = true;
            procesar = false;
            configFreewayBean.setDisabled(false);
            activeIndex = 1;
            MovilidadUtil.addErrorMessage("Se presentaron errores durante la validación");
        }
    }

    /**
     * Aplica la Tabla de Control de Freeway si fue validada
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void applyControlTable() {
        List<PrgTc> listaTC;
        List<ControlReportLogicControlRow> list_ControlRow;
        int firstRecord = 0;
        PrgTcResumen tcResumen;
        PrgTc tablaC;
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        int i = 1;
        for (PrgTcResumen t : listTcResumen) {
            listaTC = controlTableValidada.get(Util.dateFormat(t.getFecha()));
            prgTcResumenEjb.create(t);
            for (PrgTc c : listaTC) {
                c.setIdPrgTcResumen(t);
//                System.out.println(c.toString());
                prgTcEjb.create(c);
                if (i == 100) {
                    break;
                }
            }
            i++;
        }

//        while (!current.getTime().after(toDate)) {
//            tcResumen = new PrgTcResumen();
//            tcResumen.setUsername("freeway");
//            tcResumen.setCreado(new Date());
//            list_ControlRow = controlTableMap.get(Util.dateFormat(current.getTime()));
//            listaTC = new LinkedList<>();
//            tcResumen.setFecha(Util.toDate(Util.dateFormat(current.getTime())));
//            prgTcResumenEjb.create(tcResumen);
//            for (ControlReportLogicControlRow c : list_ControlRow) {
//                if (firstRecord == 0) {
//                    firstRecord = 1;
//                }
//                if (c.getDate().getValue() != null) {
//                    //                    System.out.println("Nro :" + i);
//                    //                    System.out.println("TC : " + c.toString());
//                    //                    listaTC.add(xmlToTc(c));
//                    prgTcEjb.create(xmlToTc(c));
//                    i++;
//
//                }
//                if (i == 100) {
//                    break;
//                }
//            }
////            System.out.println("IDTCResumen : " + tcResumen.getIdPrgTcResumen());
////            prgTcResumenEjb.createTransaction(tcResumen, listaTC);
//            firstRecord = 0;
//            current.add(Calendar.DATE, 1);
//        }
        obtener = true;
        validar = false;
        procesar = false;
        collapsed = false;
        activeIndex = 0;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Datos procesados con Éxito!"));
    }

    private boolean consultaPrgTc() {
        // se agrega el cero para no hacer filtro por unidad funcional
        return prgTcEjb.countByFechas(fromDate, toDate, configFreewayBean.getIdGopUF()) > 0;
    }

    public void processResumen(List<ControlReportLogicControlRow> l, PrgTcResumen tcR) {
        int operadores = 0;
        double comerciales = 0, comArt = 0, comBi = 0;
        double hlp = 0, hlpArt = 0, hlpBi = 0;
        Set<String> servicios = new HashSet<>();
        Set<String> sercones = new HashSet<>();
        for (ControlReportLogicControlRow c : l) {
            if (c.getCrewService().getValue() != null) {
                sercones.add(c.getCrewService().getValue());
            }
//            if (c.getCrewService().getValue() != null && (!driverCode.equals(c.getCrewService().getValue()))) {
//                operadores++;
//                driverCode = c.getCrewService().getValue();
//            }
            if (c.getVehicleService().getValue() != null) {
                servicios.add(c.getVehicleService().getValue());
            }
//            if (c.getVehicleService().getValue() != null && (!vehicleservice.equals(c.getVehicleService().getValue()))) {
//                servbus++;
//                vehicleservice = c.getVehicleService().getValue();
//            }
            if (c.getTaskType().getValue() != null && c.getTaskType().getValue().equals("Vacío")) {
                hlp += c.getDistance();
                hlpArt += (c.getVehicleType().getValue().equals("ARTICULADO") ? c.getDistance() : 0);
                hlpBi += (c.getVehicleType().getValue().equals("BIARTICULADO") ? c.getDistance() : 0);
            } else if (c.getTaskType().getValue() != null && c.getVehicleService().getValue() != null) {
                comerciales += c.getDistance();
                comArt += (c.getVehicleType().getValue().equals("ARTICULADO") ? c.getDistance() : 0);
                comBi += (c.getVehicleType().getValue().equals("BIARTICULADO") ? c.getDistance() : 0);
            }
        }
        tcR.setMcomPrg(new BigDecimal(comerciales));
        tcR.setMcomArtPrg(new BigDecimal(comArt));
        tcR.setMcomBiPrg(new BigDecimal(comBi));
        tcR.setMhlpProg(new BigDecimal(hlp));
        tcR.setMhlpArtPrg(new BigDecimal(hlpArt));
        tcR.setMhlpBiPrg(new BigDecimal(hlpBi));
        tcR.setOperadoresPrg(sercones.size());
        tcR.setServiciosPrg(servicios.size());
    }

    public void load2Db() {
        List<PrgTc> listaTC;
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO prg_tc(id_prg_tc_resumen,"
                + "fecha,"
                + "sercon,"
                + "id_empleado,"
                + "amplitude,"
                + "work_time,"
                + "production_distance,"
                + "work_piece,"
                + "id_task_type,"
                + "from_stop,"
                + "to_stop,"
                + "time_origin,"
                + "time_destiny,"
                + "task_duration,"
                + "distance,"
                + "servbus,"
                + "servicio_base,"
                + "id_vehiculo_tipo,"
                + "id_ruta,"
                + "tabla,"
                + "viajes,"
                + "trayecto,"
                + "username,"
                + "id_gop_unidad_funcional,"
                + "creado) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            con = Common.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps = con.prepareStatement(sql);
            int i = 0;
            long start = System.currentTimeMillis();
            for (PrgTcResumen t : tcRList) {
//                System.out.println("Primer for");
                listaTC = controlTableValidada.get(Util.dateFormat(t.getFecha()));
                t.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
                prgTcResumenEjb.create(t);
                for (PrgTc c : listaTC) {
//                    System.out.println("Segundo for");
                    c.setIdPrgTcResumen(t);
//                    System.out.println(c.toString());
                    ps.setInt(1, t.getIdPrgTcResumen());
                    ps.setString(2, Util.dateFormat(c.getFecha()));
                    ps.setString(3, c.getSercon());
                    ps.setString(4, (c.getIdEmpleado().getIdEmpleado() != null ? c.getIdEmpleado().getIdEmpleado() + "" : null));
                    ps.setString(5, c.getAmplitude());
                    ps.setString(6, c.getWorkTime());
                    ps.setString(7, (c.getProductionDistance() != null ? c.getProductionDistance().doubleValue() + "" : null));
                    ps.setString(8, c.getWorkPiece() != null ? c.getWorkPiece() + "" : null);
                    ps.setString(9, (c.getIdTaskType() != null ? c.getIdTaskType().getIdPrgTarea() + "" : null));
                    ps.setString(10, (c.getFromStop() != null ? c.getFromStop().getIdPrgStoppoint() + "" : null));
                    ps.setString(11, (c.getToStop() != null ? c.getToStop().getIdPrgStoppoint() + "" : null));
                    ps.setString(12, c.getTimeOrigin());
                    ps.setString(13, c.getTimeDestiny());
                    ps.setString(14, c.getTaskDuration());
                    ps.setString(15, c.getDistance() != null ? c.getDistance().doubleValue() + "" : null);
                    ps.setString(16, c.getServbus() != null ? c.getServbus() + "" : null);
                    ps.setString(17, c.getServicioBase());
                    ps.setString(18, (c.getIdVehiculoTipo() != null
                            ? c.getIdVehiculoTipo().getIdVehiculoTipo() + ""
                            : null));
                    ps.setString(19, (c.getIdRuta() != null ? c.getIdRuta().getIdPrgRoute() + "" : null));
                    ps.setString(20, c.getTabla() != null ? c.getTabla() + "" : null);
                    ps.setString(21, c.getViajes() != null ? c.getViajes() + "" : null);
                    ps.setString(22, c.getTrayecto());
                    ps.setString(23, c.getUsername());
                    ps.setInt(24, configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                    ps.setString(25, Util.dateFormat(c.getCreado()));
                    ps.addBatch();
//                    i++;
//                    if (i == 500) {
//                        break;
//                    }
                }
//                if (i == 1000) {
                ps.executeBatch();
                con.commit();
//                    i = 0;
//                }
//                i++;
            }
            if (i > 0) {
                int[] results = ps.executeBatch();
                con.commit();
            }
            con.setAutoCommit(true);
//            System.out.println("Time Taken=" + (System.currentTimeMillis() - start));
            prgTcEjb.buildShifts(fromDate, toDate, configFreewayBean.getIdGopUF());
            prgTcEjb.spDesasignarOp(fromDate, toDate, configFreewayBean.getIdGopUF(), user.getUsername());
            obtener = true;
            validar = false;
            procesar = false;
            collapsed = false;
            activeIndex = 0;
            if (UtilJornada.tipoLiquidacion()) {//flexible
                UtilJornada.cargarParametrosJar();
                List<PrgSerconLiqUtil> calculoOrdinarioJornadas = getJornadaFlexible().calculoOrdinarioJornadas(fromDate, toDate, 1, configFreewayBean.getIdGopUF());
                System.out.println("calculoOrdinarioJornadas---------->" + calculoOrdinarioJornadas.size());
                prgSerconFacadeLocal.updatePrgSerconFromList(calculoOrdinarioJornadas, 1);
                List<PrgSerconLiqUtil> calculoJornadaFlexible = getJornadaFlexible().calculoJornadaFlexible(fromDate, toDate, 1, configFreewayBean.getIdGopUF());
                System.out.println("calculoJornadaFlexible------------>" + calculoJornadaFlexible.size());
                prgSerconFacadeLocal.updatePrgSerconFromList(calculoJornadaFlexible, 1);
                List<PrgSerconLiqUtil> distribuirDomicalSinCompensatorio = getJornadaFlexible().distribuirDomicalSinCompensatorio(fromDate, toDate, 
                        configFreewayBean.getIdGopUF());
                System.out.println("distribuirDomicalSinCompensatorio->" + distribuirDomicalSinCompensatorio.size());
                prgSerconFacadeLocal.updatePrgSerconFromList(distribuirDomicalSinCompensatorio, 1);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Datos procesados con Éxito!"));
            configFreewayBean.setDisabled(false);
        } catch (Exception ex) {
            collapsed = true;
            procesar = false;
            activeIndex = 1;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se presentaron errores durante el procesamiento\n" + ex.getMessage()));
            System.out.println(ex.getMessage());
            Logger.getLogger(PrgControlTableMB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrgControlTableMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrgControlTableMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private PrgTc xmlToTc(ControlReportLogicControlRow c) {
        PrgTc tc = new PrgTc();
        PrgStopPoint stop;
        VehiculoTipo vehiTipo = null;
        Empleado e;
        PrgTarea task;
        ErrorTc error;
        error = new ErrorTc(c);
        tc.setFecha(Util.toDateWS(c.getDate().getValue()));
        tc.setSercon(c.getCrewService().getValue());

        if (c.getCodeDriver().getValue() != null && employee.get(c.getCodeDriver().getValue()) != null) {
            e = new Empleado(employee.get(c.getCodeDriver().getValue()));
            tc.setIdEmpleado(e);
        } else if (c.getCodeDriver().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\nOperador no encontrado" : "Operador no encontrado");
        }

//        tc.setAmplitude(c.getAmplitude() != null ? String.format("%02d:%02d:%02d", c.getAmplitude().getHours(), c.getAmplitude().getMinutes(), c.getAmplitude().getSeconds()) : null);
        tc.setAmplitude(c.getAmplitude() != null ? Util.durationToStr(c.getAmplitude()) : null);
//        tc.setWorkTime(c.getWorkTime() != null ? String.format("%02d:%02d:%02d", c.getWorkTime().getHours(), c.getWorkTime().getMinutes(), c.getWorkTime().getSeconds()) : null);
        tc.setWorkTime(c.getWorkTime() != null ? Util.durationToStr(c.getWorkTime()) : null);
        tc.setProductionDistance(c.getProductionDistance().getValue() != null ? new BigDecimal(c.getProductionDistance().getValue().replace(",", ".")) : null);
        tc.setWorkPiece(Integer.valueOf(c.getPieceOfWork().getValue()));

        if (c.getTaskType().getValue() != null && tarea.get(c.getTaskType().getValue().trim().toUpperCase()) != null) {
            task = (new PrgTarea(tarea.get(c.getTaskType().getValue().trim().toUpperCase())));
            tc.setIdTaskType(task);
        } else if (c.getTaskType().getValue() != null) {
            error.setTask_type(c.getTaskType().getValue());
            error.setError(error.getError() != null ? error.getError() + "\nTarea no encontrada" : "Tarea no encontrada");
        }

        if (c.getFromStopPoint().getValue() != null && stopPoints.get(c.getFromStopPoint().getValue()) != null) {
            stop = new PrgStopPoint(stopPoints.get(c.getFromStopPoint().getValue()));
            tc.setFromStop(stop);
        } else if (c.getFromStopPoint().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\nfromStop no encontrado" : "fromStop no encontrado");
        }

        if (c.getToStopPoint().getValue() != null && stopPoints.get(c.getToStopPoint().getValue()) != null) {
            stop = new PrgStopPoint(stopPoints.get(c.getToStopPoint().getValue()));
            tc.setToStop(stop);
        } else if (c.getToStopPoint().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\ntoStop no encontrado" : "toStop no encontrado");
        }

//        tc.setTimeOrigin(c.getStartTime() != null ? String.format("%02d:%02d:%02d", c.getStartTime().getHours(), c.getStartTime().getMinutes(), c.getStartTime().getSeconds()) : null);
        tc.setTimeOrigin(c.getStartTime() != null ? Util.durationToStr(c.getStartTime()) : null);
//        tc.setTimeDestiny(c.getEndTime() != null ? String.format("%02d:%02d:%02d", c.getEndTime().getHours(), c.getEndTime().getMinutes(), c.getEndTime().getSeconds()) : null);
        tc.setTimeDestiny(c.getEndTime() != null ? Util.durationToStr(c.getEndTime()) : null);
//        tc.setTaskDuration(c.getTaskDuration() != null ? String.format("%02d:%02d:%02d", c.getTaskDuration().getHours(), c.getTaskDuration().getMinutes(), c.getTaskDuration().getSeconds()) : null);
        tc.setTaskDuration(c.getTaskDuration() != null ? Util.durationToStr(c.getTaskDuration()) : null);
        tc.setDistance(c.getDistance() != null ? new BigDecimal(c.getDistance()) : null);
        tc.setServbus(c.getVehicleService().getValue());
        if (c.getVehicleType().getValue() != null && tipoVehiculo.get(c.getVehicleType().getValue()) != null) {
//            System.out.println("Vehiculo Tipo getHash :"+tipoVehiculo.get(c.getVehicleType().getValue()));
            vehiTipo = new VehiculoTipo(tipoVehiculo.get(c.getVehicleType().getValue()));
            tc.setIdVehiculoTipo(vehiTipo);
        } else if (c.getVehicleType().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\nTipo Vehículo no encontrado" : "Tipo Vehículo no encontrado");
        } else {
            tc.setIdVehiculoTipo(null);
        }

//        tc.setIdVehiculoTipo(vehiTipo != null ? (new VehiculoTipo(tipoVehiculo.get(c.getVehicleType().getValue()))) : null);
        if (c.getRoute().getValue() != null && routes.get(c.getRoute().getValue()) != null) {
            tc.setIdRuta(c.getRoute().getValue() != null ? new PrgRoute(routes.get(c.getRoute().getValue())) : null);
        } else if (c.getRoute().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\nRuta no encontrado" : "Ruta no encontrado");
        }

        tc.setTabla(c.getVehicleInRoute().getValue() != null ? Integer.valueOf(c.getVehicleInRoute().getValue()) : null);
        tc.setViajes(c.getTrip().getValue() != null ? Integer.valueOf(c.getTrip().getValue()) : null);
        tc.setTrayecto(c.getPattern().getValue());
        tc.setUsername(user.getUsername());
//        tc.setUsername("freeway");
        tc.setCreado(new Date());
        if (error.getError() != null) {
            listError.add(error);
        }
        return tc;
    }

    private PrgRoute xmlToRoute(RouteRow route) {
        PrgRoute ruta = new PrgRoute();
        PrgStopPoint stop;
        ruta.setIdRoute(route.getRouteId().getValue());
        ruta.setName(route.getName().getValue());
        ruta.setCode(route.getCode().getValue());
        ruta.setDescription(route.getDescription().getValue());
        ruta.setIdLine(route.getLineId().getValue());
        ruta.setLine(route.getLine().getValue());
        stop = prgStopEjb.find("id_stop_point", route.getFromStopPointID().getValue());
        ruta.setIdPrgFromStoppoint(stop);
        ruta.setIdFromStopPoint(route.getFromStopPointID().getValue());
        ruta.setFromStopPoint(route.getFromStopPoint().getValue());
        ruta.setIdToStopPoint(route.getToStopPointID().getValue());
        stop = prgStopEjb.find("id_stop_point", route.getFromStopPointID().getValue());
        ruta.setIdPrgToStoppoint(stop);
        ruta.setToStopPoint(route.getToStopPoint().getValue());
        ruta.setIsActive(route.getIsActive().intValue());
        ruta.setIsCircular(route.getIsCircular().intValue());
        ruta.setIsCommercial(route.getIsCommercial().intValue());
        ruta.setUsername(user.getUsername());
//        ruta.setUsername("freeway");
        ruta.setCreado(new Date());
        return ruta;
    }

    public PrgStopPoint xmlToStopPoint(StopPointRow sp) {
        PrgStopPoint stopPoint = new PrgStopPoint();
        stopPoint.setIdStopPoint(sp.getStopPointId().getValue());
        stopPoint.setName(sp.getName().getValue());
        stopPoint.setCode(sp.getCode().getValue());
        stopPoint.setDescription(sp.getDescription().getValue());
        stopPoint.setIsActive(sp.getIsActive().intValue());
        stopPoint.setIsDepot(sp.getIsDepot().intValue());
        stopPoint.setIsFuelAvaible(sp.getIsFuelAvailable().intValue());
        stopPoint.setIsRelayPosible(sp.getIsRelayPossible().intValue());
        stopPoint.setVehicleCapacity(sp.getVehicleCapacity());
        stopPoint.setUsername(user.getUsername());
//        stopPoint.setUsername("freeway");
        stopPoint.setCreado(new Date());
        return stopPoint;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public boolean isObtener() {
        return obtener;
    }

    public void setObtener(boolean obtener) {
        this.obtener = obtener;
    }

    public boolean isProcesar() {
        return procesar;
    }

    public void setProcesar(boolean procesar) {
        this.procesar = procesar;
    }

    public boolean isValidar() {
        return validar;
    }

    public void setValidar(boolean validar) {
        this.validar = validar;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public Sae getSae(URL url) {
        sae = new Sae(url);
        return sae;
    }

    public ISae getiSae(URL url) {
        iSae = getSae(url).getHttpSaeService();
        return iSae;
    }

    public List<PrgTc> getListTc() {
        listTc = prgTcEjb.findAllIdGopUnidadFuncional(configFreewayBean.getIdGopUF());
        return listTc;
    }

    public void setListTc(List<PrgTc> listTc) {
        this.listTc = listTc;
    }

    public List<PrgTcResumen> getListTcResumen() {
//        if (listTcResumen == null) {
        listTcResumen = prgTcResumenEjb.getResumenSem(Util.dateFormat(fromDate), Util.dateFormat(toDate), configFreewayBean.getIdGopUF());
//        }
        return listTcResumen;
    }

    public List<ErrorTc> getListError() {
        return listError;
    }

    public void setListError(List<ErrorTc> listError) {
        this.listError = listError;
    }

    public void setListTcResumen(List<PrgTcResumen> listTcResumen) {
        this.listTcResumen = listTcResumen;
    }

    /**
     * Carga Routes o rutas por unidad funcional seleccionada en el mapa routes
     */
    private void setRoutes() {
//        if (this.routes == null) {
        routes = new HashMap<>();
        for (PrgRoute r : prgRouteEjb.findByUnidadFuncional(configFreewayBean.getIdGopUF())) {
            routes.put(r.getCode(), r.getIdPrgRoute());
        }
//        }
    }

    /**
     * Carga StopoPoints o paradas por unidad funcional seleccionada en el mapa
     * stopPoints
     */
    private void setStopPoints() {
//        if (this.stopPoints == null) {
        stopPoints = new HashMap<>();
        for (PrgStopPoint s : prgStopEjb.findAllByUnidadFuncional(configFreewayBean.getIdGopUF())) {
            stopPoints.put(s.getName(), s.getIdPrgStoppoint());
        }
//        }
    }

    /**
     * Carga empleados por unidad funcional en el mapa employee
     */
    private void setEmployees() {
//        if (this.employee == null) {
        employee = new HashMap<>();
        for (Empleado emp : empleadoEjb.findAllConRetiradosByUnidadFuncional(configFreewayBean.getIdGopUF())) {
            employee.put((emp.getCodigoTm() + ""), emp.getIdEmpleado());
        }
//        }
    }

    private void setTipoVehiculos() {
        if (this.tipoVehiculo == null) {
            tipoVehiculo = new HashMap<>();
            for (VehiculoTipo vt : vehiTipoEjb.findAll()) {
                tipoVehiculo.put(vt.getNombreTipoVehiculo().toUpperCase(), vt.getIdVehiculoTipo());
            }
        }
    }

    /**
     * Carga los PrgTarea o tareas por unidad funcional en el mapa tarea
     */
    private void setTarea() {
//        if (this.tarea == null) {
        tarea = new HashMap<>();
        for (PrgTarea t : prgTareaEjb.findAllByIdGopUnidadFuncional(configFreewayBean.getIdGopUF())) {
            tarea.put(t.getTarea().trim().toUpperCase(), t.getIdPrgTarea());
        }
//        }
    }
}
