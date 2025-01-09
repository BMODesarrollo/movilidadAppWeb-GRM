/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.MttoAsigFacadeLocal;
import com.movilidad.ejb.MttoTareaFacadeLocal;
import com.movilidad.ejb.PrgServbusesFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.ejb.PrgVehicleStatusFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoIdfFacadeLocal;
import com.movilidad.model.MttoAsig;
import com.movilidad.model.MttoTarea;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgVehicleStatus;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoIdf;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.MttoAsigPrgVehicleStatus;
import com.movilidad.util.beans.ReporteServbusesPatioFin;
import com.movilidad.util.beans.ResumenAsignadosPorPatio;
import com.movilidad.util.beans.VehiculosKmPatio;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "asignacionJSFMB")
@ViewScoped
public class AsignacionJSFManagedBean implements Serializable {

    private List<PrgVehicleStatus> listServicios = new ArrayList<>();
    private List<PrgVehicleStatus> listServiciosPasoIII = new ArrayList<>();
    private List<ReporteServbusesPatioFin> lstReporteServbusesPatioFin;
    private List<VehiculosKmPatio> listVehiculosKmPatio = new ArrayList<>();
    private List<VehiculosKmPatio> listVehiculosKmPatioPasoIII = new ArrayList<>();
    private List<Vehiculo> lstVehiculos;
    private List<PrgTc> lstSinAsignar;
    private Date fechaPost = MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), 1);
    private List<PrgStopPoint> listaPatiosPato_iv = new ArrayList<>();
    private List<ResumenAsignadosPorPatio> listaResumen = new ArrayList<>();

    int servAsignadosII = 0;
    int servTotalesII = 0;
    int vehiculosII = 0;
    int servAsignadosIV = 0;
    int servTotalesIV = 0;
    int vehiculosIV = 0;
    private int i_idPatio;
    private int i_idPatio_paso_II;
    private int id_tarea_iv;
    private int id_from_depot_iv;
    private int id_to_depot_iv;
    private String horaInicioString = "02:00:00";
    private String horaFinString = "24:00:00";
    private PrgTc prgTcsSinAsignar;
    private List<PrgVehicleStatus> listPatios = new ArrayList<>();
    private List<PrgVehicleStatus> listServiciosEnPatio;
    private List<PrgVehicleStatus> listServiciosNoEnPatio;
//    Servicios en patio para el paso 1
    private List<MttoAsigPrgVehicleStatus> listaPasoI = new ArrayList<>();
//    Servicios no en patio para le paso 2
    private List<MttoAsigPrgVehicleStatus> listaPasoII = new ArrayList<>();
    private List<MttoAsigPrgVehicleStatus> listaPasoIII = new ArrayList<>();
    private List<MttoAsig> listaPasoIV = new ArrayList<>();
    private MttoAsig mttoAsig_obj = new MttoAsig();
    private List<Vehiculo> listVehiculos = new ArrayList<>();
    private List<MttoTarea> listTareas = new ArrayList<>();
    private String codigoVehiculo;
    private boolean skip;
    private VehiculosKmPatio selectedVehiculo;

    @PostConstruct
    public void init() {
//      listServicios = prgVehStatus.findPrgNextDay(fechaPost);
        listPatios = prgVehStatus.findPrgPatiosNextDay(fechaPost);
        i_idPatio_paso_II = i_idPatio = configEJB.findByKey("patio").getValue();
        consultarServiciosPasoI();
        resumen();
        pasoIII();

    }

    @EJB
    private PrgVehicleStatusFacadeLocal prgVehStatus;
    @EJB
    private PrgServbusesFacadeLocal prgServbusesEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private VehiculoIdfFacadeLocal vehiculoIdfEjb;
    @EJB
    private MttoTareaFacadeLocal mttoTareaEJB;

    @EJB
    private MttoAsigFacadeLocal mttoAsigEJB;
    @EJB
    private ConfigFacadeLocal configEJB;
    @EJB
    private PrgStopPointFacadeLocal prgStopPointEJB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of AsignacionJSFManagedBean
     */
    public AsignacionJSFManagedBean() {
    }

    public String esPar(int numero) {
        if (numero % 2 == 0) {
            return "background-color: #F3F6F9;";
        } else {
            return "background-color: gainsboro;";
        }
    }

    /**
     * En este metodo se lleva a cabo la persistencia de los datos ingresados en
     * el tab 1, que corresponde a tareas en patio.
     */
    public void savePasoI() {
        for (MttoAsigPrgVehicleStatus m : listaPasoI) {
            MttoAsig obj = new MttoAsig();
            obj.setIdMttoAsig(m.getMttoAsig().getIdMttoAsig());
            obj.setEstadoReg(0);
            obj.setFecha(fechaPost);
            obj.setIdMttoTarea(m.getMttoAsig().getIdMttoTarea());
            obj.setTimeOrigin(m.getPrgVehicleStatus().getTimeOrigin());
            obj.setTimeDestiny(m.getPrgVehicleStatus().getTimeDestiny());
            if (obj.getCreado() == null) {
                obj.setCreado(MovilidadUtil.fechaCompletaHoy());
            }
            if (m.getMttoAsig().getIdMttoAsig() != null) {
                obj.setModificado(MovilidadUtil.fechaCompletaHoy());
            } else {
                obj.setCreado(MovilidadUtil.fechaCompletaHoy());
            }
            obj.setServbus(m.getMttoAsig().getServbus());
            obj.setUsername(user.getUsername());
            String codigoV = m.getMttoAsig().getIdVehiculo().getCodigo();

            if (codigoV == null) {
                m.getMttoAsig().getIdVehiculo().setCodigo(null);
            } else if (m.getMttoAsig().getIdVehiculo().getCodigo().replaceAll("\\s", "").isEmpty()) {
                m.getMttoAsig().getIdVehiculo().setCodigo(null);
            } else {
                Vehiculo v = vehiculoEjb.getVehiculoCodigo(m.getMttoAsig().getIdVehiculo().getCodigo().replaceAll("\\s", ""));
                if (m.getPrgVehicleStatus().getServbus().contains("TA")
                        && v.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                    MovilidadUtil.addErrorMessage("No se puede asignar Vehículo Biarticulado en servicio de Articulado");
                    return;
                }
                if (v == null) {
                    m.getMttoAsig().getIdVehiculo().setCodigo(null);
                } else {
                    MttoAsig remotoMttoAsigI = mttoAsigEJB.findByIdVehiculo(v.getIdVehiculo(), fechaPost, m.getMttoAsig().getIdMttoAsig() == null ? 0 : m.getMttoAsig().getIdMttoAsig());
                    if (remotoMttoAsigI != null) {
                        MovilidadUtil.addErrorMessage("Vehículo: " + v.getCodigo() + " ya esta asignado el Servbus: " + remotoMttoAsigI.getServbus());
                        m.getMttoAsig().getIdVehiculo().setCodigo(null);
                        return;
                    }
                    if (m.getPrgVehicleStatus().getServbus().contains("TA")
                            && v.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                        MovilidadUtil.addErrorMessage("No se puede asignar Vehículo Biarticulado en servicio de Articulado");
                        return;
                    }
                    obj.setIdVehiculo(v);
                }
            }

            if (m.getMttoAsig().getIdVehiculo().getCodigo() != null
                    && m.getMttoAsig().getIdMttoTarea().getIdMttoTarea() != null) {
                PrgVehicleStatus pvsFromD = prgVehStatus.getFromDepotOrToDepotByServbus(fechaPost, obj.getServbus(), 0);
                PrgVehicleStatus pvsToD = prgVehStatus.getFromDepotOrToDepotByServbus(fechaPost, obj.getServbus(), 1);
                obj.setIdFromDepot(pvsFromD.getIdFromDepot());
                obj.setIdToDepot(pvsToD.getIdToDepot());
                mttoAsigEJB.edit(obj);
            }
        }
        MovilidadUtil.addSuccessMessage("Acción con exito.");
        resumen();
        consultarServiciosPasoI();
    }

    /**
     * Carga la lista listaPasoII con los MttoAsigPrgVehicleStatus que tenga
     * id_prg_actividad=1 cargados con las tareas del paso 1.
     */
    public void consultarServiciosPasoII() {
        servAsignadosII = 0;
        listServiciosNoEnPatio = prgVehStatus.findPrgNextDayPorDistanciaPasoII(fechaPost, i_idPatio_paso_II);
        Collections.sort(listServiciosNoEnPatio);
        listaPasoII.clear();
        for (PrgVehicleStatus p : listServiciosNoEnPatio) {
            MttoAsig m = mttoAsigEJB.findByServbus(fechaPost, p.getServbus());
            if (m == null) {
                listaPasoII.add(new MttoAsigPrgVehicleStatus(new MttoAsig(p.getServbus(), new MttoTarea(), new Vehiculo()), p));
            } else {
                if (m.getIdMttoTarea() == null) {
                    m.setIdMttoTarea(new MttoTarea());
                }
                if (m.getIdVehiculo() == null) {
                    m.setIdVehiculo(new Vehiculo());
                } else {
                    servAsignadosII++;
                }
                listaPasoII.add(new MttoAsigPrgVehicleStatus(m, p));
            }
        }
        servTotalesII = listaPasoII.size();
        pasoII();
        PrimeFaces.current().executeScript("PF('cellIzq').clearFilters();");
        PrimeFaces.current().ajax().update("tabView_id:frm_tab_ii:dt_paso_II");
        PrimeFaces.current().ajax().update("tabView_id:frm_tab_ii:id_resumen");

    }

    /**
     * Carga la lista listaPasoIII con los MttoAsigPrgVehicleStatus que tenga
     * id_prg_actividad=1, ordenados por distancia desc y cargados con las
     * tareas del paso 1.
     */
    public void consultarServiciosPasoIII() {
        servAsignadosII = 0;
        servAsignadosIV = 0;
        listServiciosNoEnPatio = prgVehStatus.findPrgNextDayPorDistanciaPasoII(fechaPost, 0);
        Collections.sort(listServiciosNoEnPatio);
        listaPasoIII.clear();
        for (PrgVehicleStatus p : listServiciosNoEnPatio) {
            MttoAsig m = mttoAsigEJB.findByServbus(fechaPost, p.getServbus());
            if (m == null) {
                listaPasoIII.add(new MttoAsigPrgVehicleStatus(new MttoAsig(p.getServbus(), new MttoTarea(), new Vehiculo()), p));
            } else {
                if (m.getIdMttoTarea() == null) {
                    m.setIdMttoTarea(new MttoTarea());
                }
                if (m.getIdVehiculo() == null) {
                    m.setIdVehiculo(new Vehiculo());
                } else {
                    servAsignadosIV++;
                    servAsignadosII++;
                }
                listaPasoIII.add(new MttoAsigPrgVehicleStatus(m, p));
            }
        }
        listaPasoIV = mttoAsigEJB.findAsignacionSinServbus(fechaPost);
        servTotalesIV = listaPasoIII.size();
        for (MttoAsig ma : listaPasoIV) {
            PrgVehicleStatus pvs = new PrgVehicleStatus();
            pvs.setIdFromDepot(ma.getIdFromDepot());
            pvs.setIdToDepot(ma.getIdToDepot());
            listaPasoIII.add(new MttoAsigPrgVehicleStatus(ma, pvs));
        }
    }

    public void cargarPatio(int op) {
        for (PrgStopPoint p : listaPatiosPato_iv) {
            if (op == 0) {
                if (id_from_depot_iv == 0) {
                    mttoAsig_obj.setIdFromDepot(null);
                    break;
                }
                if (p.getIdPrgStoppoint().equals(id_from_depot_iv)) {
                    mttoAsig_obj.setIdFromDepot(p);
                    break;
                }
            }
            if (op == 1) {
                if (id_to_depot_iv == 0) {
                    mttoAsig_obj.setIdFromDepot(null);
                    break;
                }
                if (p.getIdPrgStoppoint().equals(id_to_depot_iv)) {
                    mttoAsig_obj.setIdFromDepot(p);
                    break;
                }
            }
        }
    }

    public void onTabChange(TabChangeEvent event) {
        PrimeFaces pf = PrimeFaces.current();
        if ("paso_x_i".equals(event.getTab().getId())) {
            listPatios = prgVehStatus.findPrgPatiosNextDay(fechaPost);
            i_idPatio_paso_II = i_idPatio = configEJB.findByKey("patio").getValue();
            consultarServiciosPasoI();
            pf.ajax().update("tabView_id:frm_tab_i:mtto_a_id");

        }
        if ("paso_x_ii".equals(event.getTab().getId())) {
            consultarServiciosPasoII();
        }
        if ("paso_x_iii".equals(event.getTab().getId())) {
            consultarServiciosPasoIII();
            pasoIII();
            pf.executeScript("PF('wvpaso_iii_v').clearFilters();");
            pf.ajax().update("tabView_id:frm_tab_iii:dtpaso_iii_v");
            pf.executeScript("PF('wvpaso_iii').clearFilters();");
            pf.ajax().update("tabView_id:frm_tab_iii:dtpaso_iii");
        }
        if ("paso_x_iv".equals(event.getTab().getId())) {
            pasoIII();
            listaPatiosPato_iv = prgStopPointEJB.getPatios();
            listaPasoIV = mttoAsigEJB.findAsignacionSinServbus(fechaPost);
            pf.executeScript("PF('wvpaso_iv_v').clearFilters();");
            pf.ajax().update("tabView_id:frm_tab_iv:dtpaso_iv_v");
            pf.ajax().update("tabView_id:frm_tab_iv");

        }
    }

    public void resumen() {
        listaResumen = mttoAsigEJB.getResumenAsignados(fechaPost);

        //Servbuses asignados
        servAsignadosIV = 0;
        vehiculosIV = 0;
        listServiciosNoEnPatio = prgVehStatus.findPrgNextDayPorDistanciaPasoII(fechaPost, 0);
        listaPasoIII.clear();
        for (PrgVehicleStatus p : listServiciosNoEnPatio) {
            MttoAsig m = mttoAsigEJB.findByServbus(fechaPost, p.getServbus());
            if (m != null) {
                if (m.getIdVehiculo() != null) {
                    servAsignadosIV++;
                }
            }
        }
        //servbuses totales
        servTotalesIV = listServiciosNoEnPatio.size();

        //vehiculos no asignados
        lstVehiculos = vehiculoEjb.getVehiclosActivo();
        VehiculosKmPatio rspf = null;
        lstSinAsignar = new ArrayList<>();
        for (Vehiculo v : lstVehiculos) {
            prgTcsSinAsignar = new PrgTc();
            MttoAsig remoto = mttoAsigEJB.findByIdVehiculo(v.getIdVehiculo(), fechaPost, 0);
            if (remoto == null) {
                vehiculosIV++;
            }
        }
    }

    public void onRowSelect(SelectEvent event) {
        setSelectedVehiculo((VehiculosKmPatio) event.getObject());
        codigoVehiculo = selectedVehiculo.getIdVehiculo().getCodigo();
        if (selectedVehiculo.getPatio() != null) {
            id_from_depot_iv = selectedVehiculo.getPatio().getIdPrgStoppoint();
            mttoAsig_obj.setIdFromDepot(selectedVehiculo.getPatio());
        }
        if (selectedVehiculo.getPatio() != null) {
            id_to_depot_iv = selectedVehiculo.getPatio().getIdPrgStoppoint();
            mttoAsig_obj.setIdToDepot(selectedVehiculo.getPatio());
        }
        mttoAsig_obj.setIdVehiculo(selectedVehiculo.getIdVehiculo());
    }

    public MttoTarea getTareaById(int id) {
        for (MttoTarea t : listTareas) {
            if (t.getIdMttoTarea().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public void removeById(int id) {
        for (VehiculosKmPatio v : listVehiculosKmPatioPasoIII) {
            if (v.getIdVehiculo().getIdVehiculo().equals(id)) {
                listVehiculosKmPatioPasoIII.remove(v);
                break;
            }
        }
    }

    public void eliminarAsigTarea(MttoAsig ma) {
        mttoAsigEJB.remove(ma);
        listaPasoIV.remove(ma);

        VehiculosKmPatio rspf = null;
        prgTcsSinAsignar = new PrgTc();
        prgTcsSinAsignar = prgServbusesEjb.getPrgtcSinAsignarByFecha(ma.getIdVehiculo().getIdVehiculo(), MovilidadUtil.fechaHoy(), 0);
        if (prgTcsSinAsignar != null) {
            VehiculoIdf v_idf = vehiculoIdfEjb.findByFechaAndIdVehiculo(MovilidadUtil.fechaHoy(), prgTcsSinAsignar.getIdVehiculo().getIdVehiculo());
            BigDecimal km = new BigDecimal(0);
            if (v_idf != null) {
                km = km.add(v_idf.getKm());
                km = km.subtract(new BigDecimal(prgTcsSinAsignar.getIdVehiculo().getOdometro()));
                km = km.divide(new BigDecimal(1000));
            }
            rspf = new VehiculosKmPatio(prgTcsSinAsignar.getFecha(),
                    prgTcsSinAsignar.getIdVehiculo(),
                    km,
                    prgTcsSinAsignar.getToStop());
            listVehiculosKmPatioPasoIII.add(rspf);
        }
        MovilidadUtil.addSuccessMessage("Se eliminó correctamente");
    }

    public void guardarAsigTarea() {
        if (mttoAsig_obj.getIdVehiculo() == null) {
            MovilidadUtil.addErrorMessage("Vehículo no cargado");
            return;
        }
        if (mttoAsig_obj.getIdFromDepot() == null) {
            MovilidadUtil.addErrorMessage("Patio inicio no cargado");
            return;
        }
        if (mttoAsig_obj.getIdToDepot() == null) {
            MovilidadUtil.addErrorMessage("Patio fin no cargado_");
            return;
        }
        if (id_tarea_iv == 0) {
            MovilidadUtil.addErrorMessage("Tarea no cargada");
            return;
        }
        if (id_from_depot_iv == 0) {
            MovilidadUtil.addErrorMessage("Patio inicio no cargado");
            return;
        }
        if (id_to_depot_iv == 0) {
            MovilidadUtil.addErrorMessage("Patio fin no cargado");
            return;
        }
        mttoAsig_obj.setIdMttoTarea(getTareaById(id_tarea_iv));
        mttoAsig_obj.setUsername(user.getUsername());
        mttoAsig_obj.setCreado(MovilidadUtil.fechaCompletaHoy());
        mttoAsig_obj.setEstadoReg(0);
        mttoAsig_obj.setFecha(fechaPost);
        mttoAsigEJB.create(mttoAsig_obj);
        MovilidadUtil.addSuccessMessage("Tarea Agregada");
        listaPasoIV.add(mttoAsig_obj);
        removeById(mttoAsig_obj.getIdVehiculo().getIdVehiculo());
        codigoVehiculo = "";
        id_from_depot_iv = 0;
        id_to_depot_iv = 0;
        mttoAsig_obj = new MttoAsig();
        vehiculosIV--;
    }

    public void enlazarGenerico(List<MttoAsigPrgVehicleStatus> listaAsig, List<VehiculosKmPatio> listavVehiculos) {
        for (MttoAsigPrgVehicleStatus m : listaAsig) {
            if (m.getMttoAsig().getIdVehiculo().getIdVehiculo() == null) {
                if (listavVehiculos.size() > 0) {
                    VehiculosKmPatio vkp = listavVehiculos.get(0);
                    System.out.println("Servbus--> " + m.getMttoAsig().getServbus() + " distancia--> " + m.getPrgVehicleStatus().getProductionDistance());
                    System.out.println("Vehiculo--> " + vkp.getIdVehiculo().getCodigo() + " distancia--> " + vkp.getKmPorRecorrer());

                    MttoAsig obj = new MttoAsig();
                    if (obj.getCreado() == null) {
                        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
                    }
                    obj.setIdMttoAsig(m.getMttoAsig().getIdMttoAsig());
                    if (m.getMttoAsig().getIdMttoAsig() != null) {
                        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
                    } else {
                        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
                    }
                    obj.setFecha(fechaPost);
                    obj.setServbus(m.getMttoAsig().getServbus());
                    obj.setIdVehiculo(vkp.getIdVehiculo());
                    obj.setUsername(user.getUsername());
                    obj.setEstadoReg(0);
                    obj.setIdFromDepot(m.getPrgVehicleStatus().getIdFromDepot());
                    obj.setIdToDepot(m.getPrgVehicleStatus().getIdToDepot());

                    mttoAsigEJB.edit(obj);
                    listavVehiculos.remove(vkp);
                } else {
                    break;
                }
            }
        }
    }

    public void enlazar() {
        try {
            eliminarVehiculo();
            List<MttoAsigPrgVehicleStatus> listaServbusBiArt = new ArrayList<>();
            List<MttoAsigPrgVehicleStatus> listaServbusArt = new ArrayList<>();
            List<VehiculosKmPatio> listaVehiculosBiArt = new ArrayList<>();
            List<VehiculosKmPatio> listaVehiculosArt = new ArrayList<>();
            for (MttoAsigPrgVehicleStatus m : listaPasoII) {
                if (m.getPrgVehicleStatus().getServbus().contains("TB")) {
                    listaServbusBiArt.add(m);
                } else {
                    listaServbusArt.add(m);
                }
            }
            for (VehiculosKmPatio v : listVehiculosKmPatio) {
                if (v.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                    listaVehiculosBiArt.add(v);
                } else {
                    listaVehiculosArt.add(v);

                }
            }
            Collections.sort(listaVehiculosBiArt);
            Collections.sort(listaVehiculosArt);

            Collections.sort(listaServbusBiArt);
            Collections.sort(listaServbusArt);
            enlazarGenerico(listaServbusBiArt, listaVehiculosBiArt);
            enlazarGenerico(listaServbusArt, listaVehiculosArt);

            consultarServiciosPasoII();
            resumen();
            MovilidadUtil.addSuccessMessage("Acción finalizada");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarVehiculo() {
        List<VehiculosKmPatio> listaAux = new ArrayList<>();
        for (VehiculosKmPatio v : listVehiculosKmPatio) {
            MttoAsig remotoMttoAsigI = mttoAsigEJB.findByIdVehiculo(v.getIdVehiculo().getIdVehiculo(), fechaPost, 0);
            if (remotoMttoAsigI != null) {
                listaAux.add(v);
            }
        }
        for (VehiculosKmPatio vv : listaAux) {
            listVehiculosKmPatio.remove(vv);
        }
    }

    public void onCellEdit(CellEditEvent event) {
        try {

            int id = event.getRowIndex();
            String servbus = event.getRowKey();
            Object codigoVehiculo_old = event.getOldValue();
            Object codigoVehiculo_new = event.getNewValue();

            for (MttoAsigPrgVehicleStatus m : listaPasoII) {
                if (servbus.equals(m.getMttoAsig().getServbus())) {
                    MttoAsig obj = new MttoAsig();
                    obj.setIdMttoAsig(m.getMttoAsig().getIdMttoAsig());
                    if (obj.getCreado() == null) {
                        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
                    }
                    obj.setEstadoReg(0);
                    obj.setFecha(fechaPost);
                    if (m.getMttoAsig().getIdMttoAsig() != null) {
                        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
                    } else {
                        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
                    }
                    obj.setServbus(m.getMttoAsig().getServbus());
                    obj.setUsername(user.getUsername());
                    String codigoV = m.getMttoAsig().getIdVehiculo().getCodigo();
                    Vehiculo v;
                    boolean ok = false;
                    if (codigoV == null) {
                        m.getMttoAsig().getIdVehiculo().setCodigo("");
                    } else if (m.getMttoAsig().getIdVehiculo().getCodigo().replaceAll("\\s", "").isEmpty()) {
                        m.getMttoAsig().getIdVehiculo().setCodigo("");
                    } else {
                        if (codigoV.equalsIgnoreCase("null")) {
                            m.getMttoAsig().getIdVehiculo().setCodigo("");
                            obj.setIdVehiculo(null);
                            servAsignadosII--;
                            servAsignadosIV--;
                            ok = true;
                        } else {
                            v = vehiculoEjb.getVehiculoCodigo(m.getMttoAsig().getIdVehiculo().getCodigo().replaceAll("\\s", ""));
                            if (v == null) {
                                m.getMttoAsig().getIdVehiculo().setCodigo("");
                            } else {
                                if (m.getPrgVehicleStatus().getServbus().contains("TA") && v.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                                    MovilidadUtil.addErrorMessage("No se puede asignar Vehículo Biarticulado en servicio de Articulado");
                                    return;
                                }
                                MttoAsig remotoMttoAsigI = mttoAsigEJB.findByIdVehiculo(v.getIdVehiculo(), fechaPost, m.getMttoAsig().getIdMttoAsig() == null ? 0 : m.getMttoAsig().getIdMttoAsig());
                                if (remotoMttoAsigI != null) {
                                    MovilidadUtil.addErrorMessage("Vehículo: " + v.getCodigo() + " ya esta asignado el Servbus: " + remotoMttoAsigI.getServbus());
                                    if (codigoVehiculo_old != null) {
                                        m.getMttoAsig().getIdVehiculo().setCodigo(codigoVehiculo_old.toString());
                                    } else {
                                        m.getMttoAsig().getIdVehiculo().setCodigo("");
                                    }
                                    return;
                                }
                                obj.setIdVehiculo(v);
                                servAsignadosII++;
                                ok = true;
                            }
                        }
                    }

                    if (ok) {
                        obj.setIdFromDepot(m.getPrgVehicleStatus().getIdFromDepot());
                        obj.setIdToDepot(m.getPrgVehicleStatus().getIdToDepot());
                        mttoAsigEJB.edit(obj);
                        resumen();
                        MttoAsig remoto = mttoAsigEJB.findByServbus(fechaPost, obj.getServbus());
                        if (remoto.getIdVehiculo() == null) {
                            remoto.setIdVehiculo(new Vehiculo());
                        }
                        m.setMttoAsig(remoto);
                        MovilidadUtil.addSuccessMessage("Acción con exito. \nVehiculo: "
                                + m.getMttoAsig().getIdVehiculo().getCodigo() + "\n Servbus: "
                                + m.getMttoAsig().getServbus());
                    } else {
                        MovilidadUtil.addErrorMessage("No existe vehículo con este código");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error interno");
        }
    }

    public void onCellEditPasoIII(CellEditEvent event) {
        try {

            int id = event.getRowIndex();
            String servbus = event.getRowKey();
            Object codigoVehiculo_old = event.getOldValue();
            Object codigoVehiculo_new = event.getNewValue();
            for (MttoAsigPrgVehicleStatus m : listaPasoIII) {
                if (servbus.equals(m.getMttoAsig().getServbus())) {
                    MttoAsig obj = new MttoAsig();
                    obj.setIdMttoAsig(m.getMttoAsig().getIdMttoAsig());
                    if (obj.getCreado() == null) {
                        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
                    }
                    obj.setEstadoReg(0);
                    obj.setFecha(fechaPost);
                    if (m.getMttoAsig().getIdMttoAsig() != null) {
                        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
                    } else {
                        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
                    }
                    obj.setServbus(m.getMttoAsig().getServbus());
                    obj.setUsername(user.getUsername());
                    String codigoV = m.getMttoAsig().getIdVehiculo().getCodigo();
                    Vehiculo v;
                    boolean ok = false;
                    if (codigoV == null) {
                        m.getMttoAsig().getIdVehiculo().setCodigo("");
                    } else if (m.getMttoAsig().getIdVehiculo().getCodigo().replaceAll("\\s", "").isEmpty()) {
                        m.getMttoAsig().getIdVehiculo().setCodigo("");
                    } else {
                        if (codigoV.equalsIgnoreCase("null")) {
                            m.getMttoAsig().getIdVehiculo().setCodigo("");
                            obj.setIdVehiculo(null);
                            servAsignadosIV--;
                            servAsignadosII--;
                            ok = true;
                        } else {
                            v = vehiculoEjb.getVehiculoCodigo(m.getMttoAsig().getIdVehiculo().getCodigo().replaceAll("\\s", ""));

                            if (v == null) {
                                m.getMttoAsig().getIdVehiculo().setCodigo("");
                            } else {
                                if (m.getPrgVehicleStatus().getServbus().contains("TA") && v.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                                    MovilidadUtil.addErrorMessage("No se puede asignar Vehículo Biarticulado en servicio de Articulado");
                                    return;
                                }
                                MttoAsig remotoMttoAsigI = mttoAsigEJB.findByIdVehiculo(v.getIdVehiculo(), fechaPost, m.getMttoAsig().getIdMttoAsig() == null ? 0 : m.getMttoAsig().getIdMttoAsig());
                                if (remotoMttoAsigI != null) {
                                    MovilidadUtil.addErrorMessage("Vehículo: " + v.getCodigo() + " ya esta asignado el Servbus: " + remotoMttoAsigI.getServbus());
                                    if (codigoVehiculo_old != null) {
                                        m.getMttoAsig().getIdVehiculo().setCodigo(codigoVehiculo_old.toString());
                                    } else {
                                        m.getMttoAsig().getIdVehiculo().setCodigo("");
                                    }
                                    return;
                                }
                                obj.setIdVehiculo(v);
                                servAsignadosII++;
                                ok = true;
                            }
                        }
                    }

                    if (ok) {
                        obj.setIdFromDepot(m.getPrgVehicleStatus().getIdFromDepot());
                        obj.setIdToDepot(m.getPrgVehicleStatus().getIdToDepot());
                        mttoAsigEJB.edit(obj);
                        MttoAsig remoto = mttoAsigEJB.findByServbus(fechaPost, obj.getServbus());
                        resumen();
                        if (remoto.getIdVehiculo() == null) {
                            remoto.setIdVehiculo(new Vehiculo());
                            pasoIII();
                        } else {
                            for (VehiculosKmPatio v_i : listVehiculosKmPatioPasoIII) {
                                if (remoto.getIdVehiculo().getIdVehiculo()
                                        .equals(v_i.getIdVehiculo().getIdVehiculo())) {
                                    boolean resp = listVehiculosKmPatioPasoIII.remove(v_i);
                                    break;
                                }
                            }
                        }
                        m.setMttoAsig(remoto);
                        MovilidadUtil.addSuccessMessage("Acción con exito. \nVehiculo: "
                                + m.getMttoAsig().getIdVehiculo().getCodigo() + "\n Servbus: "
                                + m.getMttoAsig().getServbus());
                        break;
                    } else {
                        MovilidadUtil.addErrorMessage("No existe vehículo con este código");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error interno");
        }
    }

    public int toEntero(double data) {
        double value = data / 1000;
        return (int) value;
    }

    public void consultarServiciosPasoI() {
        listServiciosEnPatio = prgVehStatus.findPrgEnPatioNextDay(fechaPost, i_idPatio, horaInicioString, horaFinString);
        listTareas = mttoTareaEJB.findAll();
        listaPasoI.clear();
        for (PrgVehicleStatus p : listServiciosEnPatio) {
            MttoAsig m = mttoAsigEJB.findByServbus(fechaPost, p.getServbus());
            MttoAsig mHoy = mttoAsigEJB.findByServbus(MovilidadUtil.fechaHoy(), p.getServbus());
            if (m == null) {
                MttoTarea mt;
                if (mHoy == null) {
                    mt = new MttoTarea();
                } else if (mHoy.getIdMttoTarea() != null) {
                    mt = mHoy.getIdMttoTarea();
                } else {
                    mt = new MttoTarea();
                }
                listaPasoI.add(
                        new MttoAsigPrgVehicleStatus(
                                new MttoAsig(p.getServbus(), mt, new Vehiculo()), p));
            } else {
                if (m.getIdMttoTarea() == null) {
                    m.setIdMttoTarea(new MttoTarea());
                }
                if (m.getIdVehiculo() == null) {
                    m.setIdVehiculo(new Vehiculo());
                }
                listaPasoI.add(new MttoAsigPrgVehicleStatus(m, p));
            }
        }
    }

    public String patio(PrgStopPoint patio) {
        if (patio == null) {
            return "N/A";
        }
        String name = "";
        try {
            name = patio.getName();
        } catch (Exception e) {
            return "N/A";
        }

        return name;
    }

    public List<Object> patios() {
        List<Object> aux_list = new ArrayList<>();
        for (VehiculosKmPatio d : listVehiculosKmPatio) {
            aux_list.add(patio(d.getPatio()));
        }
        aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        return aux_list;
    }

    public List<Object> patiosFromDepot_iii() {
        List<Object> aux_list = new ArrayList<>();
        for (MttoAsigPrgVehicleStatus d : listaPasoIII) {
            if (d.getPrgVehicleStatus() != null) {
                aux_list.add(patio(d.getPrgVehicleStatus().getIdFromDepot()));
            } else if (d.getMttoAsig().getIdFromDepot() != null) {
                aux_list.add(patio(d.getMttoAsig().getIdFromDepot()));
            }
        }
        aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        return aux_list;
    }

    public List<Object> patiosToDepot_iii() {
        List<Object> aux_list = new ArrayList<>();
        for (MttoAsigPrgVehicleStatus d : listaPasoIII) {
            if (d.getPrgVehicleStatus() != null) {
                aux_list.add(patio(d.getPrgVehicleStatus().getIdToDepot()));
            } else if (d.getMttoAsig().getIdToDepot() != null) {
                aux_list.add(patio(d.getMttoAsig().getIdToDepot()));
            }
        }
        aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        return aux_list;
    }

    public void pasoII() {
        listVehiculosKmPatio = new ArrayList<>();
        lstVehiculos = vehiculoEjb.getVehiclosActivo();
        VehiculosKmPatio rspf = null;
        lstSinAsignar = new ArrayList<>();
        for (Vehiculo v : lstVehiculos) {
            prgTcsSinAsignar = new PrgTc();
            prgTcsSinAsignar = prgServbusesEjb.getPrgtcSinAsignarByFecha(v.getIdVehiculo(), MovilidadUtil.fechaHoy(), i_idPatio_paso_II);
            MttoAsig remoto = mttoAsigEJB.findByIdVehiculo(v.getIdVehiculo(), fechaPost, 0);
            if (remoto == null) {
                if (prgTcsSinAsignar != null) {
                    VehiculoIdf v_idf = vehiculoIdfEjb.findByFechaAndIdVehiculo(MovilidadUtil.fechaHoy(), v.getIdVehiculo());
                    BigDecimal km = new BigDecimal(0);
                    if (v_idf != null) {
                        km = km.add(v_idf.getKm());
                        km = km.subtract(new BigDecimal(v.getOdometro()));
                        km = km.divide(new BigDecimal(1000));
                    }
                    rspf = new VehiculosKmPatio(prgTcsSinAsignar.getFecha(),
                            prgTcsSinAsignar.getIdVehiculo(),
                            km,
                            prgTcsSinAsignar.getToStop());
                    listVehiculosKmPatio.add(rspf);
                }
            }
        }
        vehiculosII = listVehiculosKmPatio.size();
        Collections.sort(listVehiculosKmPatio);
        PrimeFaces.current().executeScript("PF('cellDer').clearFilters();");
        PrimeFaces.current().ajax().update("tabView_id:frm_tab_ii:dt_paso_II_v");
    }

    public void pasoIII() {
        listVehiculosKmPatioPasoIII = new ArrayList<>();
        lstVehiculos = vehiculoEjb.getVehiclosActivo();
        VehiculosKmPatio rspf = null;
        lstSinAsignar = new ArrayList<>();
        for (Vehiculo v : lstVehiculos) {
            prgTcsSinAsignar = new PrgTc();
            prgTcsSinAsignar = prgServbusesEjb.getPrgtcSinAsignarByFecha(v.getIdVehiculo(), MovilidadUtil.fechaHoy(), 0);
            MttoAsig remoto = mttoAsigEJB.findByIdVehiculo(v.getIdVehiculo(), fechaPost, 0);
            if (remoto == null) {
                VehiculoIdf v_idf = vehiculoIdfEjb.findByFechaAndIdVehiculo(MovilidadUtil.fechaHoy(), v.getIdVehiculo());
                BigDecimal km = new BigDecimal(0);
                if (v_idf != null) {
                    km = km.add(v_idf.getKm());
                    km = km.subtract(new BigDecimal(v.getOdometro()));
                    km = km.divide(new BigDecimal(1000));
                }
                if (prgTcsSinAsignar != null) {

                    rspf = new VehiculosKmPatio(prgTcsSinAsignar.getFecha(),
                            prgTcsSinAsignar.getIdVehiculo(),
                            km,
                            prgTcsSinAsignar.getToStop());
                    listVehiculosKmPatioPasoIII.add(rspf);
                } else {
                    rspf = new VehiculosKmPatio(null, v, km, null);
                    listVehiculosKmPatioPasoIII.add(rspf);
                }
            }
        }
        vehiculosII = listVehiculosKmPatioPasoIII.size();
        vehiculosIV = listVehiculosKmPatioPasoIII.size();
        Collections.sort(listVehiculosKmPatioPasoIII);
    }

    public void consultarVehiculo() {
        VehiculosKmPatio rspf = null;
        lstSinAsignar = new ArrayList<>();
        prgTcsSinAsignar = new PrgTc();
        int codigoV = 0;
        if (codigoVehiculo == null) {
            MovilidadUtil.addErrorMessage("Digite código de vehículo");
            return;
        }
        if (codigoVehiculo.replaceAll("\\s", "").isEmpty()) {
            MovilidadUtil.addErrorMessage("Digite código de vehículo");
            return;
        }
        Vehiculo v = vehiculoEjb.getVehiculoCodigo(codigoVehiculo.replaceAll("\\s", ""));
        if (v == null) {
            MovilidadUtil.addErrorMessage("No existe vehículo con este código");
            return;
        }
        prgTcsSinAsignar = prgServbusesEjb.getPrgtcSinAsignarByFecha(v.getIdVehiculo(), MovilidadUtil.fechaHoy(), 0);
        MttoAsig remoto = mttoAsigEJB.findByIdVehiculo(v.getIdVehiculo(), fechaPost, 0);
        if (remoto == null) {
            if (prgTcsSinAsignar != null) {
                VehiculoIdf v_idf = vehiculoIdfEjb.findByFechaAndIdVehiculo(MovilidadUtil.fechaHoy(), v.getIdVehiculo());
                BigDecimal km = new BigDecimal(0);
                if (v_idf != null) {
                    km = km.add(v_idf.getKm());
                    km = km.subtract(new BigDecimal(v.getOdometro()));
                    km = km.divide(new BigDecimal(1000));

                }
                rspf = new VehiculosKmPatio(prgTcsSinAsignar.getFecha(),
                        prgTcsSinAsignar.getIdVehiculo(),
                        km,
                        prgTcsSinAsignar.getToStop());
                mttoAsig_obj.setIdFromDepot(rspf.getPatio());
                mttoAsig_obj.setIdToDepot(rspf.getPatio());
                mttoAsig_obj.setIdVehiculo(rspf.getIdVehiculo());
                codigoVehiculo = mttoAsig_obj.getIdVehiculo().getCodigo();
                id_from_depot_iv = mttoAsig_obj.getIdFromDepot().getIdPrgStoppoint();
                id_to_depot_iv = mttoAsig_obj.getIdToDepot().getIdPrgStoppoint();
                MovilidadUtil.addSuccessMessage("Vehículo cargado");
            }
        } else {
            MovilidadUtil.addErrorMessage("Vehículo ya tiene tarea asignada");
        }
    }

    public List<PrgVehicleStatus> getListServicios() {
        return listServicios;
    }

    public void setListServicios(List<PrgVehicleStatus> listServicios) {
        this.listServicios = listServicios;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public List<ReporteServbusesPatioFin> getLstReporteServbusesPatioFin() {
        return lstReporteServbusesPatioFin;
    }

    public void setLstReporteServbusesPatioFin(List<ReporteServbusesPatioFin> lstReporteServbusesPatioFin) {
        this.lstReporteServbusesPatioFin = lstReporteServbusesPatioFin;
    }

    public List<Vehiculo> getLstVehiculos() {
        return lstVehiculos;
    }

    public void setLstVehiculos(List<Vehiculo> lstVehiculos) {
        this.lstVehiculos = lstVehiculos;
    }

    public List<PrgTc> getLstSinAsignar() {
        return lstSinAsignar;
    }

    public void setLstSinAsignar(List<PrgTc> lstSinAsignar) {
        this.lstSinAsignar = lstSinAsignar;
    }

    public PrgTc getPrgTcsSinAsignar() {
        return prgTcsSinAsignar;
    }

    public void setPrgTcsSinAsignar(PrgTc prgTcsSinAsignar) {
        this.prgTcsSinAsignar = prgTcsSinAsignar;
    }

    public List<PrgVehicleStatus> getListServiciosPasoIII() {
        return listServiciosPasoIII;
    }

    public void setListServiciosPasoIII(List<PrgVehicleStatus> listServiciosPasoIII) {
        this.listServiciosPasoIII = listServiciosPasoIII;
    }

    public List<VehiculosKmPatio> getListVehiculosKmPatio() {
        return listVehiculosKmPatio;
    }

    public void setListVehiculosKmPatio(List<VehiculosKmPatio> listVehiculosKmPatio) {
        this.listVehiculosKmPatio = listVehiculosKmPatio;
    }

    public List<PrgVehicleStatus> getListPatios() {
        return listPatios;
    }

    public void setListPatios(List<PrgVehicleStatus> listPatios) {
        this.listPatios = listPatios;
    }

    public int getI_idPatio() {
        return i_idPatio;
    }

    public void setI_idPatio(int i_idPatio) {
        this.i_idPatio = i_idPatio;
    }

    public String getHoraInicioString() {
        return horaInicioString;
    }

    public void setHoraInicioString(String horaInicioString) {
        this.horaInicioString = horaInicioString;
    }

    public String getHoraFinString() {
        return horaFinString;
    }

    public void setHoraFinString(String horaFinString) {
        this.horaFinString = horaFinString;
    }

    public List<PrgVehicleStatus> getListServiciosEnPatio() {
        return listServiciosEnPatio;
    }

    public void setListServiciosEnPatio(List<PrgVehicleStatus> listServiciosEnPatio) {
        this.listServiciosEnPatio = listServiciosEnPatio;
    }

    public List<MttoAsigPrgVehicleStatus> getListaPasoI() {
        return listaPasoI;
    }

    public void setListaPasoI(List<MttoAsigPrgVehicleStatus> listaPasoI) {
        this.listaPasoI = listaPasoI;
    }

    public List<MttoAsigPrgVehicleStatus> getListaPasoII() {
        return listaPasoII;
    }

    public void setListaPasoII(List<MttoAsigPrgVehicleStatus> listaPasoII) {
        this.listaPasoII = listaPasoII;
    }

    public List<Vehiculo> getListVehiculos() {
        return listVehiculos;
    }

    public void setListVehiculos(List<Vehiculo> listVehiculos) {
        this.listVehiculos = listVehiculos;
    }

    public List<MttoTarea> getListTareas() {
        return listTareas;
    }

    public void setListTareas(List<MttoTarea> listTareas) {
        this.listTareas = listTareas;
    }

    public int getI_idPatio_paso_II() {
        return i_idPatio_paso_II;
    }

    public void setI_idPatio_paso_II(int i_idPatio_paso_II) {
        this.i_idPatio_paso_II = i_idPatio_paso_II;
    }

    public int getServTotalesII() {
        return servTotalesII;
    }

    public void setServTotalesII(int servTotalesII) {
        this.servTotalesII = servTotalesII;
    }

    public int getServAsignadosII() {
        return servAsignadosII;
    }

    public void setServAsignadosII(int servAsignadosII) {
        this.servAsignadosII = servAsignadosII;
    }

    public int getVehiculosII() {
        return vehiculosII;
    }

    public void setVehiculosII(int vehiculosII) {
        this.vehiculosII = vehiculosII;
    }

    public Date getFechaPost() {
        return fechaPost;
    }

    public void setFechaPost(Date fechaPost) {
        this.fechaPost = fechaPost;
    }

    public List<MttoAsigPrgVehicleStatus> getListaPasoIII() {
        return listaPasoIII;
    }

    public void setListaPasoIII(List<MttoAsigPrgVehicleStatus> listaPasoIII) {
        this.listaPasoIII = listaPasoIII;
    }

    public List<VehiculosKmPatio> getListVehiculosKmPatioPasoIII() {
        return listVehiculosKmPatioPasoIII;
    }

    public void setListVehiculosKmPatioPasoIII(List<VehiculosKmPatio> listVehiculosKmPatioPasoIII) {
        this.listVehiculosKmPatioPasoIII = listVehiculosKmPatioPasoIII;
    }

    public List<MttoAsig> getListaPasoIV() {
        return listaPasoIV;
    }

    public void setListaPasoIV(List<MttoAsig> listaPasoIV) {
        this.listaPasoIV = listaPasoIV;
    }

    public MttoAsig getMttoAsig_obj() {
        return mttoAsig_obj;
    }

    public void setMttoAsig_obj(MttoAsig mttoAsig_obj) {
        this.mttoAsig_obj = mttoAsig_obj;
    }

    public String getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(String codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public int getId_tarea_iv() {
        return id_tarea_iv;
    }

    public void setId_tarea_iv(int id_tarea_iv) {
        this.id_tarea_iv = id_tarea_iv;
    }

    public List<PrgStopPoint> getListaPatiosPato_iv() {
        return listaPatiosPato_iv;
    }

    public void setListaPatiosPato_iv(List<PrgStopPoint> listaPatiosPato_iv) {
        this.listaPatiosPato_iv = listaPatiosPato_iv;
    }

    public int getId_from_depot_iv() {
        return id_from_depot_iv;
    }

    public void setId_from_depot_iv(int id_from_depot_iv) {
        this.id_from_depot_iv = id_from_depot_iv;
    }

    public int getId_to_depot_iv() {
        return id_to_depot_iv;
    }

    public void setId_to_depot_iv(int id_to_depot_iv) {
        this.id_to_depot_iv = id_to_depot_iv;
    }

    public VehiculosKmPatio getSelectedVehiculo() {
        return selectedVehiculo;
    }

    public void setSelectedVehiculo(VehiculosKmPatio selectedVehiculo) {
        this.selectedVehiculo = selectedVehiculo;
    }

    public int getServAsignadosIV() {
        return servAsignadosIV;
    }

    public void setServAsignadosIV(int servAsignadosIV) {
        this.servAsignadosIV = servAsignadosIV;
    }

    public int getServTotalesIV() {
        return servTotalesIV;
    }

    public void setServTotalesIV(int servTotalesIV) {
        this.servTotalesIV = servTotalesIV;
    }

    public int getVehiculosIV() {
        return vehiculosIV;
    }

    public void setVehiculosIV(int vehiculosIV) {
        this.vehiculosIV = vehiculosIV;
    }

    public List<ResumenAsignadosPorPatio> getListaResumen() {
        return listaResumen;
    }

    public void setListaResumen(List<ResumenAsignadosPorPatio> listaResumen) {
        this.listaResumen = listaResumen;
    }

}
