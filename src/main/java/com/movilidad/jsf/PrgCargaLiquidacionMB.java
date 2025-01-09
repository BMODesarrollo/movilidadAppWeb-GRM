/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.freeway.ArrayOfLiquidationTimeReportLogicLiquidationRow;
import com.freeway.ArrayOfstring;
import com.freeway.ISae;
import com.freeway.LiquidationTimeReportLogicLiquidationRow;
import com.freeway.Sae;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgVehicleStatusFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.model.PrgVehicleStatus;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ErrorLiquidacion;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author luis
 */
@Named(value = "prgCargaLiquidacionMB")
@ViewScoped
public class PrgCargaLiquidacionMB implements Serializable {

    @EJB
    private PrgVehicleStatusFacadeLocal prgVehicleEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    @EJB
    private PrgSerconFacadeLocal prgSerconFacade;

    @Inject
    private SelectConfigFreewayBean configFreewayBean;

    private HashMap<String, List<PrgSercon>> liquidacionMapValidado;

    private List<ErrorLiquidacion> listError;

    HashMap<String, Integer> employee;

    private HashMap<String, List<LiquidationTimeReportLogicLiquidationRow>> liquidacionMap;

    private Date fromDate;
    private Date toDate;
    private boolean obtener = true;
    private boolean validar = false;
    private boolean procesar = false;
    private boolean collapsed = false;
    private boolean update = false;
    private int activeIndex = 0;

    Sae sae;
    ISae iSae;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of prgRouteMB
     */
    public PrgCargaLiquidacionMB() {

    }

    /**
     * Consume las Routes de Freeway a través del método getRoutes del SAE
     * Service
     */
    @Transactional
    public void consumeCargaLiquidacion() {
        //Aqui
        if (fromDate == null) {
            MovilidadUtil.addErrorMessage("Falta FromDate");
            return;
        }
        if (toDate == null) {
            MovilidadUtil.addErrorMessage("Falta ToDate");
            return;
        }
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        if (toDate.compareTo(fromDate) < 0) {
            MovilidadUtil.addErrorMessage("Fecha Fin no puede ser menor a Fecha Inicio");
            return;
        }
        if (consultaCargaLiquidacion()) {
            System.out.println("update true");
            update = true;
            validar = false;
        } else {
            System.out.println("update false");
            update = false;
        }
        obtener = false;
        List<String> lista = new LinkedList<>();
        System.out.println("LogicLiquidationRow");
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        System.out.println("Solución : " + configFreewayBean.getConfigFreeway().getCodigoSolucion());
        lista.add(configFreewayBean.getConfigFreeway().getCodigoSolucion());
        ArrayOfstring codeOrganizationList = new ArrayOfstring();
        codeOrganizationList.setString(lista);
        PrgTcResumen tcResumen;
        ArrayOfLiquidationTimeReportLogicLiquidationRow liquidacion;
        List<LiquidationTimeReportLogicLiquidationRow> listLiquidacion;
        XMLGregorianCalendar desde;
        try {
            liquidacionMap = new HashMap<>();
            while (!current.getTime().after(toDate)) {
                listLiquidacion = new LinkedList<>();
                liquidacion = new ArrayOfLiquidationTimeReportLogicLiquidationRow();
                desde = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(Util.xmlGregorianFormat(current.getTime()));

                System.out.println("Fecha : " + Util.dateFormat(current.getTime()));
                for (String organizacion : configFreewayBean.getConfigFreeway().getCodigoSolucion().split(",")) {
                    lista = new LinkedList<>();
                    lista.add(organizacion.trim());
                    codeOrganizationList = new ArrayOfstring();
                    codeOrganizationList.setString(lista);
//                    cumplediario = getiSae().getVehicleStatusData(desde, desde, codeOrganizationList);
                    liquidacion = getiSae(new URL(
                            configFreewayBean.getConfigFreeway().getUrlServicio())).getLiquidationTimeData(desde, desde, codeOrganizationList, true);
                    listLiquidacion.addAll(liquidacion.getLiquidationTimeReportLogicLiquidationRow());
                    System.out.println("Solucion : " + organizacion + " Tamaño Solucion : " + liquidacion.getLiquidationTimeReportLogicLiquidationRow().size());
                }
                if (listLiquidacion.size() > 0) {
//                    cumpleDiarioMap.put(Util.dateFormat(current.getTime()), cumplediario.getVehicleStatusReportLogicVehicleStatusRow());
                    liquidacionMap.put(Util.dateFormat(current.getTime()), listLiquidacion);
//                    processResumen(cumplediario.getControlReportLogicControlRow(), tcResumen);
                }
//                tcRList.add(tcResumen);
                current.add(Calendar.DATE, 1);
//                listTcResumen = tcRList;
            }
            if (liquidacionMap.isEmpty()) {
                obtener = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontraron datos"));
            } else {
                if (update) {
                    validar = false;
                    System.out.println("Validar false");
                } else {
                    validar = true;
                    System.out.println("Validar true");
                }
//                listTcResumen = tcRList;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Datos Obtenidos con Éxito!"));
            }
            System.out.println("liquidacionMap : " + liquidacionMap.size());
//            System.out.println("TCResumen List : " + tcRList.size());
        } catch (Exception e) {
            Logger.getGlobal().log(Level.OFF, e.getMessage());
            System.out.println("e.getMessage() : " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage()));
        }
    }

    public boolean validate() {
        System.out.println("prgCargaLiquidacionMB");
        List<PrgVehicleStatus> listaTC;
        List<PrgSercon> listaSercon;
        PrgVehicleStatus vehicleStatus;
        PrgSercon sercon;
//        List<VehicleStatusReportLogicVehicleStatusRow> list_CumpleDiarioRow;
        List<LiquidationTimeReportLogicLiquidationRow> list_LiquidacionRow;
        liquidacionMapValidado = new HashMap<>();
        listError = new LinkedList<>();
        list_LiquidacionRow = new LinkedList<>();
        setEmployees();
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        int i = 1;
        while (!current.getTime().after(toDate)) {
            listaSercon = new LinkedList<>();
            list_LiquidacionRow = liquidacionMap.get(Util.dateFormat(current.getTime()));
//            for (VehicleStatusReportLogicVehicleStatusRow c : list_CumpleDiarioRow) {
            for (LiquidationTimeReportLogicLiquidationRow c : list_LiquidacionRow) {
                if (c.getDate().getValue() != null && c.getCodeDriver().getValue() != null) {
                    sercon = xmlToPrgSercon(c);
//                    vehicleStatus = xmlToVehicleStatus(c);
                    listaSercon.add(sercon);
//                    System.out.println(listaSercon.toString());
                }
            }
            if (!listaSercon.isEmpty()) {
//                System.out.println("Fecha : "+Util.dateFormat(current.getTime())+" registros "+listaSercon.size());
                liquidacionMapValidado.put(Util.dateFormat(current.getTime()), listaSercon);
            }
            current.add(Calendar.DATE, 1);
        }
        if (listError.isEmpty()) {
            validar = false;
            procesar = true;
            collapsed = false;
            activeIndex = 0;
            configFreewayBean.setDisabled(true);
            MovilidadUtil.addSuccessMessage("Datos validados con Éxito!");
            return true;
        } else {
//            obtener = true;
//            validar = false;
            collapsed = true;
            procesar = false;
            activeIndex = 1;
            configFreewayBean.setDisabled(false);
            MovilidadUtil.addErrorMessage("Se presentaron errores durante la validación.");
            return false;
        }

    }

    public void validarActualizar() {
        boolean ok = validate();

        if (!ok) {
            return;
        }
        load2Db(1);
        obtener = true;
        validar = false;
        procesar = false;
        update = false;
    }

    private PrgSercon xmlToPrgSercon(LiquidationTimeReportLogicLiquidationRow l) {
        PrgSercon sercon = new PrgSercon();
        Empleado e;
        ErrorLiquidacion error;
        error = new ErrorLiquidacion(l);
        sercon.setFecha(Util.toDateWS(l.getDate().getValue()));

        if (l.getCodeDriver().getValue() != null && employee.get(l.getCodeDriver().getValue()) != null) {
            e = new Empleado(employee.get(l.getCodeDriver().getValue()));
            sercon.setIdEmpleado(e);
        } else if (l.getCodeDriver().getValue() != null) {
            System.out.println(l.getCodeDriver().getValue());
            error.setError(error.getError() != null ? error.getError() + "\nOperador no encontrado" : "Operador no encontrado");
        }
        sercon.setProductionTime(Util.durationToStr(l.getProductionTime()));
        sercon.setDiurnas(Util.durationToStr(l.getDayTime()));
        sercon.setNocturnas(Util.durationToStr(l.getNightTime()));
        sercon.setExtraDiurna(Util.durationToStr(l.getExtraDayTime()));
        sercon.setExtraNocturna(Util.durationToStr(l.getExtraNightTime()));
        sercon.setFestivoDiurno(Util.durationToStr(l.getHolyDayTime()));
        sercon.setFestivoExtraDiurno(Util.durationToStr(l.getHolyExtraDayTime()));
        sercon.setFestivoNocturno(Util.durationToStr(l.getHolyNightTime()));
        sercon.setFestivoExtraNocturno(Util.durationToStr(l.getHolyExtraNightTime()));
        sercon.setCompensatorio(Util.durationToStr(l.getCompensatoryTime()));
//        System.out.println("," + l.getCodeDriver().getValue() + "," + 
//                l.getDriver().getValue() + "," + 
//                sercon.toString());
        if (error.getError() != null) {
            listError.add(error);
        }
        return sercon;
    }

    @Transactional
    public void load2Db(int opc) {
        List<PrgSercon> listaLiq;
        int firstRecord = 0;
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        int i = 1;
        while (!current.getTime().after(toDate)) {
            listaLiq = liquidacionMapValidado.get(Util.dateFormat(current.getTime()));
            prgSerconFacade.updateSerconFromList(listaLiq, opc);
//            for (PrgSercon c : listaLiq) {
//                System.out.println(c.toString());
//                
//                prgSerconFacade.updatePrgSercon(c);
//            }
            i++;
            current.add(Calendar.DATE, 1);
        }
        obtener = true;
        validar = false;
        procesar = false;
        collapsed = false;
        activeIndex = 0;
        configFreewayBean.setDisabled(false);
        MovilidadUtil.addSuccessMessage("Datos procesados con Éxito!");
    }

    private boolean consultaCargaLiquidacion() {
        return prgSerconFacade.countByFechas(fromDate, toDate, configFreewayBean.getIdGopUF()) > 0;
    }

    @PostConstruct
    public void init() {
//        fromDate = Util.toDate("2019-07-06");
//        toDate = Util.toDate("2019-05-17");
//        solucion = "Prueba Tunal";
        listError = null;
    }

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    private void setEmployees() {
//        if (this.employee == null) {
        employee = new HashMap<>();
        for (Empleado emp : empleadoEjb.findAll()) {
            employee.put((emp.getCodigoTm() + ""), emp.getIdEmpleado());
        }
//        }
    }

    public Sae getSae(URL url) {
        sae = new Sae(url);
        return sae;
    }

    public ISae getiSae(URL url) {
        iSae = getSae(url).getHttpSaeService();
        return iSae;
    }

    public void setSae(Sae sae) {
        this.sae = sae;
    }

    public void setiSae(ISae iSae) {
        this.iSae = iSae;
    }

    public List<ErrorLiquidacion> getListError() {
        return listError;
    }

    public void setListError(List<ErrorLiquidacion> listError) {
        this.listError = listError;
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

    public boolean isObtener() {
        return obtener;
    }

    public void setObtener(boolean obtener) {
        this.obtener = obtener;
    }

    public boolean isValidar() {
        return validar;
    }

    public void setValidar(boolean validar) {
        this.validar = validar;
    }

    public boolean isProcesar() {
        return procesar;
    }

    public void setProcesar(boolean procesar) {
        this.procesar = procesar;
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

//</editor-fold>
    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
