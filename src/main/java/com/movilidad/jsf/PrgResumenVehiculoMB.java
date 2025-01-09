/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.freeway.ArrayOfVehicleStatusReportLogicVehicleStatusRow;
import com.freeway.ArrayOfstring;
import com.freeway.ISae;
import com.freeway.Sae;
import com.freeway.VehicleStatusReportLogicVehicleStatusRow;
import com.movilidad.ejb.PrgActividadFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.ejb.PrgVehicleStatusFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.PrgActividad;
import com.movilidad.model.PrgRoute;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgVehicleStatus;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ErrorVehicleStatus;
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
@Named(value = "prgResumenVehiculoMB")
@ViewScoped
public class PrgResumenVehiculoMB implements Serializable {

    @EJB
    private PrgStopPointFacadeLocal prgStopEjb;
    @EJB
    private PrgActividadFacadeLocal prgActividadEjb;
    @EJB
    private PrgVehicleStatusFacadeLocal prgVehicleEjb;
    @EJB
    private VehiculoTipoFacadeLocal vehiTipoEjb;
    @Inject
    private SelectConfigFreewayBean configFreewayBean;
    private List<PrgRoute> listRoutes;
    private HashMap<String, List<PrgVehicleStatus>> cumpleDiarioValidado;
    private List<ErrorVehicleStatus> listError;

    HashMap<String, Integer> stopPoints;
    HashMap<String, Integer> tipoVehiculo;
    HashMap<String, Integer> actividad;

    private HashMap<String, List<VehicleStatusReportLogicVehicleStatusRow>> cumpleDiarioMap;

    private Date fromDate;
    private Date toDate;
    private boolean obtener = true;
    private boolean validar = false;
    private boolean procesar = false;
    private boolean collapsed = false;
    private int activeIndex = 0;

    Sae sae;
    ISae iSae;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of prgRouteMB
     */
    public PrgResumenVehiculoMB() {

//        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
//            if (!auth.getAuthority().equals("ROLE_PRG")) {
//                FacesContext context = FacesContext.getCurrentInstance();
//                HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
//                System.out.println(origRequest.getContextPath());
//
//                try {
//
//                    FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.jsf");
////                    FacesContext.getCurrentInstance().getExternalContext()
////                            .redirect(origRequest.getContextPath() + "/index.jsf");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    /**
     * Consume las Routes de Freeway a través del método getRoutes del SAE
     * Service
     */
//    @Transactional
    public void consumeVehicleStatus() {
        if (toDate.compareTo(fromDate) < 0) {
            MovilidadUtil.addErrorMessage("Fecha Fin no puede ser menor a Fecha Inicio");
            return;
        }
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar la unidad funcional");
            return;
        }
        if (consultaPrgVehicleStatus()) {
            MovilidadUtil.addErrorMessage("Existen registros cargados para las fechas indicadas");
            return;
        }
        obtener = false;
        List<String> lista = new LinkedList<>();
//        System.out.println("consumeVehicleStatus");
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
//        controlTableMap = new HashMap<>();
//        System.out.println("Solución : " + solucion);
        lista.add(configFreewayBean.getConfigFreeway().getCodigoSolucion());
        ArrayOfstring codeOrganizationList = new ArrayOfstring();
        codeOrganizationList.setString(lista);
//        PrgTcResumen tcResumen;
        ArrayOfVehicleStatusReportLogicVehicleStatusRow cumplediario;
        List<VehicleStatusReportLogicVehicleStatusRow> vehiculoStatus;
        XMLGregorianCalendar desde;
        try {
            cumpleDiarioMap = new HashMap<>();
            while (!current.getTime().after(toDate)) {
                vehiculoStatus = new LinkedList<>();
                cumplediario = new ArrayOfVehicleStatusReportLogicVehicleStatusRow();
                desde = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(Util.xmlGregorianFormat(current.getTime()));
//                tcResumen = new PrgTcResumen();
//                tcResumen.setFecha(current.getTime());
//                tcResumen.setUsername("freeway");
//                tcResumen.setCreado(new Date());
//                System.out.println("Fecha : " + Util.dateFormat(current.getTime()));
                for (String organizacion : configFreewayBean.getConfigFreeway().getCodigoSolucion().split(",")) {
                    lista = new LinkedList<>();
                    lista.add(organizacion.trim());
                    codeOrganizationList = new ArrayOfstring();
                    codeOrganizationList.setString(lista);
                    cumplediario = getiSae(new URL(
                            configFreewayBean.getConfigFreeway().getUrlServicio())).getVehicleStatusData(desde, desde, codeOrganizationList);
                    vehiculoStatus.addAll(cumplediario.getVehicleStatusReportLogicVehicleStatusRow());
//                    System.out.println("Solucion : " + organizacion + " Tamaño Solucion : " + cumplediario.getVehicleStatusReportLogicVehicleStatusRow().size());

                }
//                System.out.println("Tamaño CumpleDiario :" + cumplediario.getVehicleStatusReportLogicVehicleStatusRow().size());
//                listaTablaControl.add(controlTable.getControlReportLogicControlRow());
//                if (cumplediario.getVehicleStatusReportLogicVehicleStatusRow().size() > 0) {
                if (vehiculoStatus.size() > 0) {
//                    cumpleDiarioMap.put(Util.dateFormat(current.getTime()), cumplediario.getVehicleStatusReportLogicVehicleStatusRow());
                    cumpleDiarioMap.put(Util.dateFormat(current.getTime()), vehiculoStatus);
//                    processResumen(cumplediario.getControlReportLogicControlRow(), tcResumen);
                }
//                tcRList.add(tcResumen);
                current.add(Calendar.DATE, 1);
//                listTcResumen = tcRList;
            }
            if (cumpleDiarioMap.isEmpty()) {
                obtener = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontraron datos"));
            } else {
                validar = true;
//                listTcResumen = tcRList;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Datos Obtenidos con Éxito!"));
            }
//            System.out.println("ControlTableMap : " + cumpleDiarioMap.size());
//            System.out.println("TCResumen List : " + tcRList.size());
        } catch (Exception e) {
            Logger.getGlobal().log(Level.OFF, e.getMessage());
//            System.out.println("e.getMessage() : " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage()));
            e.printStackTrace();
        }
    }

    public void validate() {
//        System.out.println("validateControlTable");
        List<PrgVehicleStatus> listaTC;
        PrgVehicleStatus vehicleStatus;
        List<VehicleStatusReportLogicVehicleStatusRow> list_CumpleDiarioRow;
        cumpleDiarioValidado = new HashMap<>();
        listError = new LinkedList<>();
        setStopPoints();
        setTipoVehiculos();
        setActividad();
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        int i = 1;
        while (!current.getTime().after(toDate)) {
            list_CumpleDiarioRow = cumpleDiarioMap.get(Util.dateFormat(current.getTime()));
            listaTC = new LinkedList<>();
            for (VehicleStatusReportLogicVehicleStatusRow c : list_CumpleDiarioRow) {
                if (c.getDate().getValue() != null) {
                    vehicleStatus = xmlToVehicleStatus(c);
                    listaTC.add(vehicleStatus);
//                    System.out.println(vehicleStatus.toString());
                }
            }
            if (!listaTC.isEmpty()) {
                cumpleDiarioValidado.put(Util.dateFormat(current.getTime()), listaTC);
            }
            current.add(Calendar.DATE, 1);
        }
        if (listError.isEmpty()) {
            configFreewayBean.setDisabled(true);
            validar = false;
            procesar = true;
            collapsed = false;
            activeIndex = 0;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Datos validados con Éxito!"));
        } else {
//            obtener = true;
//            validar = false;
            configFreewayBean.setDisabled(false);
            collapsed = true;
            procesar = false;
            activeIndex = 1;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se presentaron errores durante la validación"));
        }
    }

    private boolean consultaPrgVehicleStatus() {
        return prgVehicleEjb.countByFechasByIdGopUnidadFuncional(fromDate, toDate,
                configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional().getIdGopUnidadFuncional()) != 0;
    }

    @Transactional
    public void load2Db() {
        List<PrgVehicleStatus> listaTC;
        int firstRecord = 0;
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        int i = 1;
        while (!current.getTime().after(toDate)) {
            listaTC = cumpleDiarioValidado.get(Util.dateFormat(current.getTime()));
            for (PrgVehicleStatus c : listaTC) {
//                System.out.println(c.toString());
                c.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
                prgVehicleEjb.create(c);
//                if (i == 100) {
//                    break;
//                }
            }
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

    @PostConstruct
    public void init() {
//        fromDate = Util.toDate("2019-07-06");
//        toDate = Util.toDate("2019-05-17");
//        solucion = "Prueba Tunal";
        listError = null;
    }

    private PrgVehicleStatus xmlToVehicleStatus(VehicleStatusReportLogicVehicleStatusRow vs) {
        PrgVehicleStatus p = new PrgVehicleStatus();
        PrgStopPoint stop;
        PrgActividad activity;
        VehiculoTipo vehiTipo = null;
        ErrorVehicleStatus error;
        error = new ErrorVehicleStatus(vs);
        p.setFecha(Util.toDateWS(vs.getDate().getValue()));
        p.setServbus(vs.getVehicleService().getValue());

        //Valida si tipo vehiculo existe
        if (vs.getVehicleType().getValue() != null && tipoVehiculo.get(vs.getVehicleType().getValue()) != null) {
            vehiTipo = new VehiculoTipo(tipoVehiculo.get(vs.getVehicleType().getValue()));
            p.setIdVehiculoTipo(vehiTipo);
        } else if (vs.getVehicleType().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\nTipo Vehículo no encontrado" : "Tipo Vehículo no encontrado");
            error.setVehicle_type(vs.getVehicleType().getValue());
        } else {
            p.setIdVehiculoTipo(null);
        }

        p.setExpedicion(vs.getExpedition().getValue() != null ? Integer.parseInt(vs.getExpedition().getValue()) : null);

        //Valida si tipo Actividad Existe
        if (vs.getActivity().getValue() != null && actividad.get(vs.getActivity().getValue().toUpperCase()) != null) {
            activity = new PrgActividad(actividad.get(vs.getActivity().getValue().toUpperCase()));
            p.setIdPrgActividad(activity);
        } else if (vs.getActivity().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\nTipo Actividad no encontrado" : "Tipo Actividad no encontrado");
            error.setActividad(vs.getActivity().getValue());
        } else {
            p.setIdPrgActividad(null);
        }

        if (vs.getFromStopPoint().getValue() != null && stopPoints.get(vs.getFromStopPoint().getValue().toUpperCase()) != null) {
            stop = new PrgStopPoint(stopPoints.get(vs.getFromStopPoint().getValue().toUpperCase()));
            p.setIdFromDepot(stop);
        } else if (vs.getFromStopPoint().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\nfromStop no encontrado" : "fromStop no encontrado");
            error.setFromStop(vs.getFromStopPoint().getValue());
        }

        if (vs.getToStopPoint().getValue() != null && stopPoints.get(vs.getToStopPoint().getValue().toUpperCase()) != null) {
            stop = new PrgStopPoint(stopPoints.get(vs.getToStopPoint().getValue().toUpperCase()));
            p.setIdToDepot(stop);
        } else if (vs.getToStopPoint().getValue() != null) {
            error.setError(error.getError() != null ? error.getError() + "\ntoStop no encontrado" : "toStop no encontrado");
            error.setToStop(vs.getToStopPoint().getValue());
        }
//        p.setTimeOrigin(vs.getStartTime() != null ? String.format("%02d:%02d:%02d", vs.getStartTime().getHours(), vs.getStartTime().getMinutes(), vs.getStartTime().getSeconds()) : null);
        p.setTimeOrigin(vs.getStartTime() != null ? Util.durationToStr(vs.getStartTime()) : null);
//        p.setTimeDestiny(vs.getEndTime() != null ? String.format("%02d:%02d:%02d", vs.getEndTime().getHours(), vs.getEndTime().getMinutes(), vs.getEndTime().getSeconds()) : null);
        p.setTimeDestiny(vs.getEndTime() != null ? Util.durationToStr(vs.getEndTime()) : null);
//        p.setComercialTime(vs.getBusinessTime() != null ? String.format("%02d:%02d:%02d", vs.getBusinessTime().getHours(), vs.getBusinessTime().getMinutes(), vs.getBusinessTime().getSeconds()) : null);
        p.setComercialTime(vs.getBusinessTime() != null ? Util.durationToStr(vs.getBusinessTime()) : null);
//        p.setHlpTime(vs.getEmptyTime() != null ? String.format("%02d:%02d:%02d", vs.getEmptyTime().getHours(), vs.getEmptyTime().getMinutes(), vs.getEmptyTime().getSeconds()) : null);
        p.setHlpTime(vs.getEmptyTime() != null ? Util.durationToStr(vs.getEmptyTime()) : null);
//        p.setDeadTime(vs.getDeadTime() != null ? String.format("%02d:%02d:%02d", vs.getDeadTime().getHours(), vs.getDeadTime().getMinutes(), vs.getDeadTime().getSeconds()) : null);
        p.setDeadTime(vs.getDeadTime() != null ? Util.durationToStr(vs.getDeadTime()) : null);
//        p.setProductionTime(vs.getProductionTime() != null ? String.format("%02d:%02d:%02d", vs.getProductionTime().getHours(), vs.getProductionTime().getMinutes(), vs.getProductionTime().getSeconds()) : null);
        p.setProductionTime(vs.getProductionTime() != null ? Util.durationToStr(vs.getProductionTime()) : null);
        p.setComercialDistance(vs.getBusinessDistance().getValue() != null ? new Double(vs.getBusinessDistance().getValue().replace(",", ".")) : null);
        p.setHlpDistance(vs.getEmptyDistance().getValue() != null ? new Double(vs.getEmptyDistance().getValue().replace(",", ".")) : null);
        p.setProductionDistance(vs.getProductionDistance().getValue() != null ? new Double(vs.getProductionDistance().getValue().replace(",", ".")) : null);
        p.setLineas(vs.getLines().getValue() != null ? vs.getLines().getValue() : null);
        p.setUsername(user.getUsername());
        p.setCreado(new Date());
        if (error.getError() != null) {
            listError.add(error);
        }
        return p;
    }

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    private void setStopPoints() {
        if (this.stopPoints == null) {
            stopPoints = new HashMap<>();
            for (PrgStopPoint s : prgStopEjb.findAllByUnidadFuncional(configFreewayBean.getIdGopUF())) {
                stopPoints.put(s.getName().toUpperCase(), s.getIdPrgStoppoint());
            }

        }
    }

    private void setTipoVehiculos() {
        if (this.tipoVehiculo == null) {
            tipoVehiculo = new HashMap<>();
            for (VehiculoTipo vt : vehiTipoEjb.findAll()) {
                tipoVehiculo.put(vt.getNombreTipoVehiculo().toUpperCase(), vt.getIdVehiculoTipo());
            }
        }
    }

    private void setActividad() {
        if (this.actividad == null) {
            actividad = new HashMap<>();
            for (PrgActividad vt : prgActividadEjb.findAll()) {
                actividad.put(vt.getActividad().toUpperCase(), vt.getIdPrgActividad());
            }
        }
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

    public List<ErrorVehicleStatus> getListError() {
        return listError;
    }

    public void setListError(List<ErrorVehicleStatus> listError) {
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
}
