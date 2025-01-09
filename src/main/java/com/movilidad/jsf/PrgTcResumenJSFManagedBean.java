package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.PrgPatternFacadeLocal;
import com.movilidad.ejb.PrgRouteFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.ejb.PrgTareaFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcResponsableFacadeLocal;
import com.movilidad.ejb.PrgTcResumenFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PrgPattern;
import com.movilidad.model.PrgRoute;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTarea;
import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgTcResponsable;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.KmsResumen;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.data.FilterEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "prgTcResumenBean")
@ViewScoped
public class PrgTcResumenJSFManagedBean implements Serializable {

    @EJB
    private PrgTcResumenFacadeLocal prgTcResumenEjb;
    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @EJB
    private PrgTareaFacadeLocal prgTareaEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private PrgRouteFacadeLocal prgRouteEjb;
    @EJB
    private PrgTcResponsableFacadeLocal prgTcResponsableEjb;

    private PrgStopPointFacadeLocal prgSPEJB;

    @EJB
    private PrgPatternFacadeLocal prgPatternEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private PrgTcResumen prgTcResumen;
    private PrgTc prgTc;
    private PrgTc selected;
    private Empleado empleado;
    private Vehiculo vehiculo;
    private PrgTarea servicio;
    private PrgTcResponsable responsable;
    private PrgRoute ruta;
    private Date fechaIzquierda;
    private Date fechaDerecha;
    private int tipoVehiculoIzq;
    private int tipoServicioIzq;
    private int codVehiculoIzq;
    private int tipoVehiculoDer;
    private int tipoServicioDer;
    private int codVehiculoDer;
    private int i_codResponsable;
    private int i_fromStop;
    private int componente;
    private int i_idRuta = 0;
    private Integer i_idTarea;
    private int i_toStop;
    private BigDecimal b_Adicionales;
    private BigDecimal b_Eliminados;
    private BigDecimal b_Total;
    private BigDecimal b_Total_Art;
    private BigDecimal b_Total_Bi;
    private BigDecimal b_Distancia;
    private BigDecimal b_DistanciaDerecha;
    private BigDecimal b_DistanciaTm;
    private BigDecimal b_DistanciaTmDer;
    private BigDecimal b_IPH_ComercialArt;
    private BigDecimal b_IPH_ComercialBi;
    private BigDecimal b_PRG_ComercialArt;
    private BigDecimal b_PRG_ComercialBi;
    private BigDecimal b_IPH_HlpArt;
    private BigDecimal b_IPH_HlpBi;
    private BigDecimal b_PRG_HlpArt;
    private BigDecimal b_PRG_HlpBi;
    private BigDecimal b_Vacio_IPH_Art;
    private BigDecimal b_Vacio_IPH_Bi;
    private BigDecimal b_Res_Art;
    private BigDecimal b_Res_Bi;
    private BigDecimal b_Fac_Art;
    private BigDecimal b_Fac_Bi;
    private BigDecimal b_Res_Art_NoOpt;
    private BigDecimal b_Res_Bi_NoOpt;
    private BigDecimal b_Preconcialiado;
    private BigDecimal b_TotalResSemana;
    private PrgPattern i_idPuntoIni;
    private PrgStopPoint i_idPuntoIniStopPoint;
    private PrgStopPoint i_idPuntoFinStopPoint;
    private PrgStopPoint prgStopPoint;
    private PrgTc prgTcVacOrVaccom;
    private PrgPattern i_idPuntoFin;
    private Integer i_idPuntoIniInt = 0;
    private Integer i_idPuntoFinInt = 0;
    private String codV = "";
    private String color = "";

    private String horaFinString = "";
    private String horaInicioString = "";
    private String punto_inicioString;
    private String punto_finString;
    private String destino = "";
    private String codVehiculo = "";
    private String codVDer = "";
    private String colorDer = "";
    private String c_operacionDistancia = "";
    private String b1 = "";
    private String b2 = "";
    private String b3 = "";
    private String b4 = "";
    private String b5 = "";
    private String b6 = "";
    private String b7 = "";

    private boolean flagRuta;
    private boolean flagPInicioPFin = false;
    private boolean flagConciliacion = false;

    private List<PrgTc> lstPrgTcIzquierda;
    private List<PrgTc> lstPrgTcDerecha;
    private List<PrgTcResponsable> lstResponsable;
    private List<Empleado> lstEmpleados;
    private List<PrgTarea> lstServicios;
    private List<PrgRoute> lstRutas;
    private List<PrgPattern> listPrgPattern;
    private List<PrgStopPoint> listPrgStopPoint;
    private List<PrgTc> lstDistance;
    private List<PrgTc> lstDistanceDerecha;
    private List<PrgTc> lstPreconcialiado;
    private List<PrgTcResumen> lstResumenSem;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {

        tipoVehiculoIzq = 0;
        tipoServicioIzq = 3;
        codVehiculoIzq = 0;
        tipoVehiculoDer = 0;
        tipoServicioDer = 5;
        codVehiculoDer = 0;
        i_codResponsable = 0;
        codVehiculo = "";
        b_Adicionales = Util.CERO;
        b_Eliminados = Util.CERO;
        b_Total = Util.CERO;
        b_Total_Art = Util.CERO;
        b_Total_Bi = Util.CERO;
        b_Distancia = Util.CERO;
        b_DistanciaDerecha = Util.CERO;
        b_DistanciaTm = Util.CERO;
        b_DistanciaTmDer = Util.CERO;
        b_IPH_ComercialArt = Util.CERO;
        b_IPH_ComercialBi = Util.CERO;
        b_PRG_ComercialArt = Util.CERO;
        b_PRG_ComercialBi = Util.CERO;
        b_IPH_HlpArt = Util.CERO;
        b_IPH_HlpBi = Util.CERO;
        b_PRG_HlpArt = Util.CERO;
        b_PRG_HlpBi = Util.CERO;
        b_Vacio_IPH_Art = Util.CERO;
        b_Res_Art_NoOpt = Util.CERO;
        b_Res_Bi_NoOpt = Util.CERO;
        b_Vacio_IPH_Bi = Util.CERO;
        b_Res_Art = Util.CERO;
        b_Res_Bi = Util.CERO;
        b_Fac_Art = Util.CERO;
        b_Fac_Bi = Util.CERO;
        b_Preconcialiado = Util.CERO;
        fechaIzquierda = new Date();
        lstPreconcialiado = new ArrayList<>();
        lstPrgTcIzquierda = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstPrgTcDerecha = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        llenarFechas();
        empleado = new Empleado();
        vehiculo = new Vehiculo();
        servicio = new PrgTarea();
        ruta = new PrgRoute();
        responsable = new PrgTcResponsable();
        prgTcResumen = new PrgTcResumen();
        selected = null;
    }

    /**
     * Asigna color al botón correspondiente a la fecha seleccionada
     *
     * @param valor
     * @return clase OrangeButton si la fecha corresponde a la seleccionada
     */
    public String asignarColor(String valor) {
        String fecha_C = Util.dateFormat(fechaIzquierda);
        if (fecha_C.equals(valor)) {
            return "OrangeButton";
        }
        return "";
    }

    /**
     *
     */
    public void prepareListEmpleados() {
        lstEmpleados = empleadoEjb.findAllByUnidadFuncacional(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        PrimeFaces.current().executeScript("PF('wVEmpleado').clearFilters();");
    }

    /**
     *
     */
    public void prepareListPrgTc() {
        this.prgTc = new PrgTc();
    }

    /**
     * Evento que se dispara luego de seleccionar al empleado que va a realizar
     * un servicio
     *
     * @param event
     */
    public void onEmpleadoChosen(SelectEvent event) {
        setEmpleado((Empleado) event.getObject());
    }

    /**
     * Evento que se dispara al momento de seleccionar un empleado de listado de
     * empleados
     *
     * @param event
     */
    public void onRowEmpleadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVEmpleado').clearFilters();");
        PrimeFaces.current().ajax().update(":frmPmEmpleadoList:dtEmpleados");
    }

    /**
     * Método que controla el cambio entre pestañas (Tabs)
     *
     * @param event
     */
    public void onChange(TabChangeEvent event) {
        PrimeFaces current = PrimeFaces.current();
        Tab activeTab = event.getTab();
        switch (activeTab.getId()) {
            case "tbResumenS":
                b_TotalResSemana = Util.CERO;
                getResumenSem();
                current.ajax().update("tabResumen:pGridResumenS");
                break;
            case "tbLiqHLP":
                b_Fac_Art = prgTcResumen.getFactorArt() != null ? prgTcResumen.getFactorArt() : Util.CERO;
                b_Fac_Bi = prgTcResumen.getFactorBi() != null ? prgTcResumen.getFactorBi() : Util.CERO;
                b_IPH_HlpArt = prgTcResumen.getIphHlpArt() != null ? prgTcResumen.getIphHlpArt() : Util.CERO;
                b_IPH_HlpBi = prgTcResumen.getIphHlpBi() != null ? prgTcResumen.getIphHlpBi() : Util.CERO;
                b_PRG_HlpArt = prgTcResumen.getPrgHlpArt() != null ? prgTcResumen.getPrgHlpArt() : Util.CERO;
                b_PRG_HlpBi = prgTcResumen.getPrgHlpBi() != null ? prgTcResumen.getPrgHlpBi() : Util.CERO;
                b_Vacio_IPH_Art = prgTcResumen.getHlpiphOptimizadoArt() != null ? prgTcResumen.getHlpiphOptimizadoArt() : Util.CERO;
                b_Vacio_IPH_Bi = prgTcResumen.getHlpiphOptimizadoBi() != null ? prgTcResumen.getHlpiphOptimizadoBi() : Util.CERO;
                b_IPH_ComercialArt = prgTcResumen.getIphComArt() != null ? prgTcResumen.getIphComArt() : Util.CERO;
                b_IPH_ComercialBi = prgTcResumen.getIphComBi() != null ? prgTcResumen.getIphComBi() : Util.CERO;
                b_PRG_ComercialArt = prgTcResumen.getPrgComArt() != null ? prgTcResumen.getPrgComArt() : Util.CERO;
                b_PRG_ComercialBi = prgTcResumen.getPrgComBi() != null ? prgTcResumen.getPrgComBi() : Util.CERO;

                if (prgTcResumen.getHlpOptArt().compareTo(Util.CERO) > 0) {
                    b_Res_Art = prgTcResumen.getHlpOptArt();
                }
                if (prgTcResumen.getHlpOptBi().compareTo(Util.CERO) > 0) {
                    b_Res_Bi = prgTcResumen.getHlpOptBi();
                } else {
                    b_Res_Art_NoOpt = Util.CERO;
                    b_Res_Bi_NoOpt = Util.CERO;
                }
                current.ajax().update("tabResumen:frmtbLiqHLP");
                break;
        }
    }

    /**
     *
     * @param n1
     * @param n2
     * @return
     */
    public BigDecimal operar(BigDecimal n1, BigDecimal n2) {
        return n1.divide(n2, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Obtiene lista de paradas por idRuta
     */
    public void findByIdRoute() {
        listPrgPattern = prgPatternEJB.findAllOrderedByIdRoute(i_idRuta);
        if (listPrgPattern.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("Sin paradas");
        }
    }

    /**
     * Calcula la distancia total de los servicios seleccionados en la tabla
     * izquierda
     */
    public void calcularDistancia() {
        long total = 0;
        if (lstDistance != null) {
            if (!lstDistance.isEmpty()) {
                this.selected = lstDistance.get(0);
            } else {
                this.selected = null;
            }
            for (PrgTc lstDistance1 : lstDistance) {
                total += lstDistance1.getDistance() != null
                        ? lstDistance1.getDistance().longValue() : 0;
            }
        }
        BigDecimal totalAux = new BigDecimal(total);
        this.b_Distancia = totalAux;
    }

    /**
     * Calcula la distancia total de los servicios seleccionados en la tabla
     * derecha
     */
    public void calcularDistanciaDerecha() {
        long total = 0;
        if (lstDistanceDerecha != null) {
            if (!lstDistanceDerecha.isEmpty()) {
                this.selected = lstDistanceDerecha.get(0);
            } else {
                this.selected = null;
            }
            for (PrgTc lstDistance1 : lstDistanceDerecha) {
                total += lstDistance1.getDistance() != null
                        ? lstDistance1.getDistance().longValue() : 0;
            }
        }
        BigDecimal totalAux = new BigDecimal(total);
        this.b_DistanciaDerecha = totalAux;
    }

    /**
     * Carga los datos antes de registrar un nuevo servicio
     */
    public void nuevo() {
        prgTc = new PrgTc();
        empleado = new Empleado();
        vehiculo = new Vehiculo();
        servicio = new PrgTarea();
        responsable = new PrgTcResponsable();
        ruta = new PrgRoute();
        codVehiculo = "";
        codVehiculoIzq = 0;
        codVehiculoDer = 0;
        i_codResponsable = 0;
        i_fromStop = 0;
        i_toStop = 0;
        i_idTarea = 0;
        i_idRuta = 0;
        i_idPuntoIniInt = 0;
        i_idPuntoFinInt = 0;
        punto_finString = "";
        punto_inicioString = "";
        horaInicioString = "";
        horaFinString = "";
        flagRuta = true;
        lstServicios = prgTareaEjb.findAllTareasSumDistancia(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstRutas = prgRouteEjb.getRutas();
        selected = null;
    }

    /**
     * Devolver el valor del servicio seleccionado a su valor por defecto
     */
    public void resetSelected() {
        if (selected != null) {
            selected = null;
            PrimeFaces.current().ajax().update("frmAddServicio");
        }
    }

    /**
     * Carga los datos del servicio a editar, en la vista de modificación de
     * servicios
     */
    public void editar() {
        PrimeFaces current = PrimeFaces.current();

        if (validarEstadoConciliacion()) {
            current.ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }
        if (selected == null) {
            current.ajax().update(":messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un servicio");
            return;
        }
        this.prgTc = null;
        i_codResponsable = 0;
        codVehiculo = "";
        i_idRuta = 0;
        i_idTarea = 0;
        i_fromStop = 0;
        i_toStop = 0;
        i_idPuntoIniInt = 0;
        i_idPuntoFinInt = 0;
        i_idPuntoIni = new PrgPattern();
        i_idPuntoFin = new PrgPattern();
        punto_inicioString = "";
        punto_finString = "";
        lstServicios = prgTareaEjb.findAllTareasSumDistancia(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstRutas = prgRouteEjb.getRutas();
        this.prgTc = this.selected;
        this.empleado = this.prgTc.getIdEmpleado();
        this.horaInicioString = prgTc.getTimeOrigin();
        this.horaFinString = prgTc.getTimeDestiny();
        if (prgTc.getIdRuta() != null) {
            i_idRuta = this.prgTc.getIdRuta().getIdPrgRoute();
            findByIdRoute();
        }
        if (prgTc.getIdPrgTcResponsable() != null) {
            this.responsable = this.prgTc.getIdPrgTcResponsable();
            i_codResponsable = responsable.getIdPrgTcResponsable();
        } else {
            responsable = null;
        }
        if (prgTc.getFromStop() != null) {
            i_idPuntoIniStopPoint = prgTc.getFromStop();
            punto_inicioString = i_idPuntoIniStopPoint.getName();
            if (prgTc.getIdRuta() != null) {
                i_idPuntoIniInt = prgTc.getFromStop().getIdPrgStoppoint();
                setObjeto(0);
            } else {
                i_idPuntoIni.setIdPrgStoppoint(prgTc.getFromStop());
            }
        }
        if (prgTc.getToStop() != null) {
            i_idPuntoFinInt = prgTc.getToStop().getIdPrgStoppoint();
            i_idPuntoFinStopPoint = prgTc.getToStop();
            punto_finString = i_idPuntoFinStopPoint.getName();
            if (prgTc.getIdRuta() != null) {
                setObjeto(1);
            } else {
                i_idPuntoFin.setIdPrgStoppoint(prgTc.getToStop());
            }
        }
        if (prgTc.getIdVehiculo() != null) {
            this.vehiculo = this.prgTc.getIdVehiculo();
            codVehiculo = vehiculo.getCodigo();
        }
        if (prgTc.getIdTaskType() != null) {
            servicio = prgTc.getIdTaskType();
            i_idTarea = servicio.getIdPrgTarea();
        }
        if (i_idTarea > 0) {
            onOffRuta();
        }
    }

    /**
     * Persiste un nuevo servicio en base de datos, y luego lo agrega en la
     * lista de servicios adicionales
     */
    public void guardar() {

        PrgTcResumen resumen = prgTcResumenEjb.findByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }

        if (vehiculo == null) {
            MovilidadUtil.addErrorMessage("Debe ingresar un código vehículo válido");
            PrimeFaces.current().ajax().update("frmAddServicio:messages");
            return;
        }
        if (prgTc.getTabla() <= 0) {
            MovilidadUtil.addErrorMessage("Debe ingresar una tabla");
            PrimeFaces.current().ajax().update("frmAddServicio:messages");
            return;
        }
        if (prgTc.getServbus() == null) {
            MovilidadUtil.addErrorMessage("Debe cargar un vehículo válido para poder generar el servbus.");
            PrimeFaces.current().ajax().update("frmAddServicio:messages");
            return;
        }
        if (vehiculo.getIdVehiculoTipo().getIdVehiculoTipo() == 2
                && empleado.getIdEmpleadoCargo().getIdEmpleadoTipoCargo() == 28) {
            MovilidadUtil.addAdvertenciaMessage("El operador no está certificado para operar Biarticulado");
            PrimeFaces.current().ajax().update("frmAddServicio:messages");
            return;
        }
        if (empleado.getIdentificacion() != null) {
            prgTc.setIdEmpleado(empleado);
            prgTc.setIdGopUnidadFuncional(empleado.getIdGopUnidadFuncional());
        }
        if (i_codResponsable > 0) {
            responsable = prgTcResponsableEjb.find(i_codResponsable);
            prgTc.setIdPrgTcResponsable(responsable);
        }

        if (i_idPuntoIniInt > 0) {
            prgTc.setFromStop(i_idPuntoIni.getIdPrgStoppoint());
        }
        if (!punto_inicioString.isEmpty() && !punto_inicioString.equals("") && i_idRuta == 0) {
            prgTc.setFromStop(i_idPuntoIniStopPoint);
        }
        if (i_idPuntoFinInt > 0) {
            prgTc.setToStop(i_idPuntoFin.getIdPrgStoppoint());
        }
        if (!punto_finString.isEmpty() && !punto_finString.equals("") && i_idRuta == 0) {
            prgTc.setToStop(i_idPuntoFinStopPoint);
        }

        if (servicio.getIdPrgTarea() != null) {
            if (!servicio.getTarea().isEmpty() && !servicio.getTarea().equals("")) {
                prgTc.setIdTaskType(servicio);
            }
        }

        if (i_idRuta > 0) {
            prgTc.setIdRuta(prgRouteEjb.find(i_idRuta));
        }
        prgTc.setFecha(fechaIzquierda);
        prgTc.setTimeOrigin(horaInicioString);
        prgTc.setTimeDestiny(horaFinString);
        prgTc.setUsername(user.getUsername());
        prgTc.setIdVehiculo(vehiculo);
        prgTc.setIdVehiculoTipo(vehiculo.getIdVehiculoTipo());
        prgTc.setIdPrgTcResumen(resumen != null ? resumen : null);
        prgTc.setDistance(prgTcEjb.findDistandeByFromStopAndToStop(prgTc.getFromStop().getIdPrgStoppoint(), prgTc.getToStop().getIdPrgStoppoint()));
        prgTc.setCreado(new Date());

        if (prgTc.getIdTaskType().getComercial() != 0) {
            prgTc.setEstadoOperacion(3);
        }

        if (prgTc.getIdTaskType().getIdPrgTarea() == 3) {
            prgTc.setEstadoOperacion(6);
        }

        if (prgTc.getIdTaskType().getComercial() == 0) {
            prgTc.setEstadoOperacion(7);
        }

        prgTc.setEstadoReg(0);
        this.prgTcEjb.create(prgTc);

        if (tipoServicioIzq == 3) {
            lstPrgTcIzquierda = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            PrimeFaces.current().ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
            PrimeFaces.current().executeScript("PF('cellIzq').clearFilters()");
        } else if (tipoServicioDer == 3) {
            lstPrgTcDerecha = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            PrimeFaces.current().ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
        } else {
            lstPrgTcIzquierda = prgTcEjb.findByFecha(fechaIzquierda,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            PrimeFaces.current().ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
        }
        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Servicio creado correctamente."));
    }

    /**
     * Realiza cambio a formato numérico del campo distancia antes de exportar
     * excel de servicios
     *
     * @param document
     */
    public void postProcessXLS(Object document) {

        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow header = sheet.getRow(0);

        Iterator<Row> rowIterator = sheet.iterator();

        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getColumnIndex() > 1) {

                    if (!cell.getStringCellValue().isEmpty()
                            && cell.getColumnIndex() == 8) {
                        cell.setCellValue(new BigDecimal(cell.getStringCellValue().replace("'", "")).doubleValue());
                    }
                }
            }
        }
    }

    /**
     * Realiza la modificación de los datos de un servicio existente
     */
    public void actualizar() {

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }

        if (vehiculo == null) {
            MovilidadUtil.addErrorMessage("Debe ingresar un código de vehículo válido");
            PrimeFaces.current().ajax().update("frmAddServicio:messages");
            return;
        }

        if (prgTc.getTabla() <= 0) {
            MovilidadUtil.addErrorMessage("Debe ingresar una tabla");
            PrimeFaces.current().ajax().update("frmAddServicio:messages");
            return;
        }

        if (prgTc.getServbus() == null) {
            MovilidadUtil.addErrorMessage("Debe cargar un vehículo válido para poder generar el servbus.");
            PrimeFaces.current().ajax().update("frmAddServicio:messages");
            return;
        }

        if (vehiculo.getIdVehiculoTipo().getIdVehiculoTipo() == 2
                && empleado.getIdEmpleadoCargo().getIdEmpleadoTipoCargo() == 28) {
            MovilidadUtil.addAdvertenciaMessage("El operador no está certificado para operar Biarticulado");
            PrimeFaces.current().ajax().update("frmAddServicio:messages");
            return;
        }
        if (empleado.getIdentificacion() != null) {
            prgTc.setIdEmpleado(empleado);
        }
        if (i_codResponsable > 0) {
            responsable = prgTcResponsableEjb.find(i_codResponsable);
            prgTc.setIdPrgTcResponsable(responsable);
        }

        if (i_idPuntoIniInt > 0) {
            prgTc.setFromStop(i_idPuntoIni.getIdPrgStoppoint());
        }
        if (!punto_inicioString.isEmpty() && !punto_inicioString.equals("") && i_idRuta == 0) {
            prgTc.setFromStop(i_idPuntoIniStopPoint);
        }
        if (i_idPuntoFinInt > 0) {
            prgTc.setToStop(i_idPuntoFin.getIdPrgStoppoint());
        }
        if (!punto_finString.isEmpty() && !punto_finString.equals("") && i_idRuta == 0) {
            prgTc.setToStop(i_idPuntoFinStopPoint);
        }
        if (servicio.getIdPrgTarea() > 0) {
            if (!servicio.getTarea().isEmpty() && !servicio.getTarea().equals("")) {
                prgTc.setIdTaskType(servicio);
            }
        }
        if (i_idRuta > 0) {
            prgTc.setIdRuta(prgRouteEjb.find(i_idRuta));
        }
        if (!horaInicioString.isEmpty() && !horaInicioString.equals("")) {
            prgTc.setTimeOrigin(horaInicioString);
        }
        if (!horaFinString.isEmpty() && !horaFinString.equals("")) {
            prgTc.setTimeDestiny(horaFinString);
        }

        prgTc.setDistance(prgTcEjb.findDistandeByFromStopAndToStop(prgTc.getFromStop().getIdPrgStoppoint(), prgTc.getToStop().getIdPrgStoppoint()));
        prgTc.setUsername(user.getUsername());
        prgTc.setIdVehiculo(vehiculo);
        prgTc.setIdVehiculoTipo(vehiculo.getIdVehiculoTipo());
        prgTc.setModificado(new Date());

        if (prgTc.getIdTaskType().getComercial() != 0) {
            prgTc.setEstadoOperacion(3);
        }

        if (prgTc.getIdTaskType().getIdPrgTarea() == 3) {
            prgTc.setEstadoOperacion(6);
        }

        if (prgTc.getIdTaskType().getComercial() == 0) {
            prgTc.setEstadoOperacion(7);
        }

        this.prgTcEjb.edit(prgTc);

        if (lstDistance != null) {
            b_Distancia = Util.CERO;
            lstDistance.clear();
        }
        if (lstDistanceDerecha != null) {
            b_DistanciaDerecha = Util.CERO;
            lstDistanceDerecha.clear();
        }

        PrimeFaces.current().executeScript("PF('cellIzq').unselectAllRows()");
        PrimeFaces.current().ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
        PrimeFaces.current().ajax().update("tabResumen:frmIzquierda:paramIzquierda");
        PrimeFaces.current().ajax().update("tabResumen:frmIzquierda:context_menu_izq");
        PrimeFaces.current().ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
        PrimeFaces.current().ajax().update("tabResumen:frmDerecha:paramDerecha");
        PrimeFaces.current().ajax().update("tabResumen:frmDerecha:context_menu_der");
        PrimeFaces.current().executeScript("PF('addServicio').hide();");
        PrimeFaces.current().ajax().update(":messages");
        PrimeFaces.current().ajax().update(":frmConciliacion:pGridResumen");
        PrimeFaces.current().ajax().update(":frmConciliacion:pGridData");
        resetSelected();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Servicio actualizado correctamente."));
    }

    /**
     * Evento que se dispara al filtrar en la tabla del lado izquierdo
     *
     * @param filterEvent
     */
    public void filterListenerIzq(FilterEvent filterEvent) {
        lstDistance = null;
        this.b_Distancia = Util.CERO;
        resetSelected();
        PrimeFaces.current().executeScript("PF('cellIzq').unselectAllRows()");
        PrimeFaces.current().ajax().update("tabResumen:frmIzquierda:paramIzquierda");
        PrimeFaces.current().ajax().update("tabResumen:frmIzquierda:pGridBotones");
        PrimeFaces.current().ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
    }

    /**
     * Evento que se dispara al filtrar en la tabla del lado derecho
     *
     * @param filterEvent
     */
    public void filterListenerDer(FilterEvent filterEvent) {
        lstDistanceDerecha = null;
        this.b_DistanciaDerecha = Util.CERO;
        resetSelected();
        PrimeFaces.current().executeScript("PF('cellDer').unselectAllRows()");
        PrimeFaces.current().ajax().update("tabResumen:frmDerecha:paramDerecha");
        PrimeFaces.current().ajax().update("tabResumen:frmDerecha:pGridBotonesDer");
        PrimeFaces.current().ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
    }

    /**
     * Método que se dispara al seleccionar las paradas al adicionar/modificar
     * servicio, y coloca la información de los puntos de inicio y fin en la
     * vista
     *
     * @param event
     * @throws Exception
     */
    public void onRowDblClckSelectStopPoint(final SelectEvent event) throws Exception {
        PrimeFaces current = PrimeFaces.current();
        setPrgStopPoint((PrgStopPoint) event.getObject());
        if (prgTcVacOrVaccom == null) {
            prgTcVacOrVaccom = new PrgTc();
        }
        if (componente == 0) {
            prgTcVacOrVaccom.setToStop(prgStopPoint);
            destino = prgStopPoint.getName();
            current.ajax().update("frmAddServicio:txtDestino");
            current.ajax().update(":frmPrincipalStopPointList:tablaStopPoint");
            current.executeScript("PF('wvStopPointListDialog').hide()");
        } else if (componente == 1) {
            flagPInicioPFin = true;
            setI_idPuntoIniStopPoint((PrgStopPoint) event.getObject());
            punto_inicioString = i_idPuntoIniStopPoint.getName();
            current.ajax().update(":frmAddServicio:addServ_pIniByLinea");
            current.ajax().update(":frmPrincipalStopPointList:tablaStopPoint");
            current.executeScript("PF('wvStopPointListDialog').hide()");
        } else {
            setI_idPuntoFinStopPoint((PrgStopPoint) event.getObject());
            punto_finString = i_idPuntoFinStopPoint.getName();
            current.ajax().update(":frmAddServicio:addServ_pFinByLinea");
            current.ajax().update(":frmPrincipalStopPointList:tablaStopPoint");
            current.executeScript("PF('wvStopPointListDialog').hide()");
        }
    }

    /**
     * Método que ayuda a encontrar la parada de destino correspondiente a un
     * servicio
     *
     * @param comp
     */
    public void findDestino(int comp) {
        componente = comp;
        if (componente == 1) {
            destino = punto_inicioString;
        } else if (componente == 2) {
            destino = punto_finString;
        }
        if (listPrgStopPoint != null) {
            listPrgStopPoint.clear();
        }

        if (destino.isEmpty() || destino.equals("")) {
            MovilidadUtil.addAdvertenciaMessage("Digite el nombre de la parada");
            return;
        }
        listPrgStopPoint = prgSPEJB.findStopPointByName(destino,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (listPrgStopPoint == null) {
            MovilidadUtil.addAdvertenciaMessage("No hay Paradas con este nombre");
            return;
        }
        PrimeFaces.current().executeScript("PF('wvStopPointListDialog').show()");
        PrimeFaces.current().executeScript("PF('tablaStopPointWV').clearFilters();");
        PrimeFaces.current().ajax().update(":frmPrincipalStopPointList:tablaStopPoint");

    }

    /**
     * Método que se encarga de asignar en la vista de adición/modificación de
     * servicios las paradas de INICIO y FIN para un servicio comercial
     *
     * @param x
     */
    public void setObjeto(int x) {
        for (PrgPattern p : listPrgPattern) {
            if (x == 0) {
                if (p.getIdPrgStoppoint().getIdPrgStoppoint().equals(i_idPuntoIniInt)) {
                    setI_idPuntoIni(p);
                    break;
                }
            } else {
                if (p.getIdPrgStoppoint().getIdPrgStoppoint().equals(i_idPuntoFinInt)) {
                    setI_idPuntoFin(p);
                    break;
                }
            }
        }
        if (!i_idPuntoFinInt.equals(0) && !i_idPuntoIniInt.equals(0)) {
            if (i_idPuntoIni.getDistance() > i_idPuntoFin.getDistance()) {
                MovilidadUtil.addErrorMessage("Error de sentido Norte/sur");
                i_idPuntoFinInt = 0;
                i_idPuntoIniInt = 0;
            }
        }
    }

    /**
     * Método que se encarga de asignar tarea a un servicio, y especificar si
     * las paradas de éste son comerciales o NO
     */
    public void onOffRuta() {
        for (PrgTarea p : lstServicios) {
            if (i_idTarea.equals(p.getIdPrgTarea())) {
                if (p.getComercial() == 1 && !(p.getIdPrgTarea().equals(3)
                        || p.getIdPrgTarea().equals(4))) {
                    setServicio(p);
                    flagRuta = false;
                    flagPInicioPFin = false;
                    return;
                } else {
                    setServicio(p);
                    flagRuta = true;
                    flagPInicioPFin = true;
                    return;
                }
            }
        }
    }

    /**
     * Verifica si el día seleccionado se encuentra CONCILIADO
     *
     * @return true si la fecha seleccionada se encuentra CONCILIADA
     */
    private boolean validarEstadoConciliacion() {
        PrgTcResumen res = prgTcResumenEjb.findByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (res != null) {
            if (res.getConciliado() == null) {
                return false;
            }
            if (res.getConciliado() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Carga los servicios de la tabla izquierda
     */
    public void selectDateIzquierda() {
        prgTcResumen = new PrgTcResumen();
        PrimeFaces current = PrimeFaces.current();
        b_Adicionales = Util.CERO;
        b_Eliminados = Util.CERO;
        b_Total = Util.CERO;
        b_Total_Art = Util.CERO;
        b_Total_Bi = Util.CERO;
        b_DistanciaTm = Util.CERO;
        b_DistanciaTmDer = Util.CERO;
        b_TotalResSemana = Util.CERO;
        tipoServicioIzq = 3;
        tipoVehiculoIzq = 0;
        tipoServicioDer = 5;
        tipoVehiculoDer = 0;
        b_Distancia = Util.CERO;

        lstPrgTcIzquierda = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstPrgTcDerecha = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        llenarFechas();

        if ((lstPrgTcIzquierda.isEmpty() && lstPrgTcDerecha.isEmpty())
                || (lstPrgTcIzquierda == null && lstPrgTcDerecha == null)) {
            current.ajax().update(":frmConciliacion:pGridResumen");
            lstPrgTcIzquierda = null;
            lstPrgTcDerecha = null;
            current.executeScript("PF('cellIzq').clearFilters()");
            current.executeScript("PF('cellIzq').unselectAllRows()");
            current.executeScript("PF('cellDer').clearFilters()");
            current.executeScript("PF('cellDer').unselectAllRows()");
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron servicios Adicionales/Eliminados para la fecha: " + Util.dateFormat(fechaIzquierda) + ".");
            return;
        }

        current.executeScript("PF('cellIzq').clearFilters()");
        current.executeScript("PF('cellIzq').unselectAllRows()");
        current.executeScript("PF('cellDer').clearFilters()");
        current.executeScript("PF('cellDer').unselectAllRows()");
        current.ajax().update(":frmConciliacion:pGridResumen");
    }

    /**
     * Obtiene el resumen de kilómetros semanales
     */
    public void getResumenSem() {
        if ((b1 != null && !b1.isEmpty()) && (b7 != null && !b7.isEmpty())) {
            List<PrgTcResumen> lstResumenSemAux = prgTcResumenEjb.getResumenSem(b1, b7,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            lstResumenSem = new ArrayList<>();
            for (PrgTcResumen r : lstResumenSemAux) {
                if (r.getMadicArt() == null && r.getMadicBi() == null
                        && r.getMelimArt() == null && r.getMelimBi() == null) {

                    r.setMadicArt(Util.CERO);
                    r.setMadicBi(Util.CERO);
                    r.setMelimArt(Util.CERO);
                    r.setMelimBi(Util.CERO);
                    r.setHlpNoptArt(Util.CERO);
                    r.setHlpNoptBi(Util.CERO);
                    r.setHlpOptArt(Util.CERO);
                    r.setHlpOptBi(Util.CERO);
                    r.setFactorArt(Util.CERO);
                    r.setFactorBi(Util.CERO);
                }
                lstResumenSem.add(r);
            }
        }
    }

    /**
     * Evento que se dispara al presionar los botones que tienen las fechas de
     * la semana actual
     *
     * @param fecha
     */
    public void onClickBtn(String fecha) {
        fechaIzquierda = Util.toDate(fecha);
        selectDateIzquierda();
    }

    /**
     * Carga los botones (días de la semana) con las fechas de la semana actual
     */
    public void llenarFechas() {
        Date fromDate = fechaIzquierda;
        int c = 0;
        Locale locale = new Locale("es", "CO");
        Calendar current = Calendar.getInstance(locale);
        current.setTime(fromDate);
        current.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        while (c <= 6) {
            switch (c) {
                case 0:
                    b1 = Util.dateFormat(current.getTime());
                    break;
                case 1:
                    b2 = Util.dateFormat(current.getTime());
                    break;
                case 2:
                    b3 = Util.dateFormat(current.getTime());
                    break;
                case 3:
                    b4 = Util.dateFormat(current.getTime());
                    break;
                case 4:
                    b5 = Util.dateFormat(current.getTime());
                    break;
                case 5:
                    b6 = Util.dateFormat(current.getTime());
                    break;
                case 6:
                    b7 = Util.dateFormat(current.getTime());
                    break;
            }
//            System.out.println(Util.dateFormat(current.getTime()));
            current.add(Calendar.DATE, 1);
            c++;
        }
    }

    /**
     * Formatea fecha a formato YYYY-MM-DD
     *
     * @return
     */
    public String formatearFechaActual() {
        return Util.dateFormat(fechaIzquierda);
    }

    /**
     * Obtiene el total de kilómetros semanales
     *
     * @param total
     */
    public void obtenerTotalSemana(BigDecimal total) {
        List<BigDecimal> lstTotalSemana = new ArrayList<>();
        lstTotalSemana.add(total);

        for (BigDecimal x : lstTotalSemana) {
            b_TotalResSemana = b_TotalResSemana.add(x);
        }

        lstTotalSemana.clear();
    }

    /**
     * Obtiene total de kilómetros para una fecha
     *
     * @param resumen
     * @return
     */
    public BigDecimal obtenerTotal(PrgTcResumen resumen) {
        BigDecimal art;
        BigDecimal bi;
        BigDecimal res = Util.CERO;

        if (resumen.getFactorArt() == null) {
            resumen.setFactorArt(Util.CERO);
        }
        if (resumen.getFactorBi() == null) {
            resumen.setFactorBi(Util.CERO);
        }

        if (resumen.getFactorArt().compareTo(Util.CERO) == 0 || resumen.getFactorBi().compareTo(Util.CERO) == 0) {
            art = resumen.getMcomArtCon();
            bi = resumen.getMcomBiCon();

            if (art == null) {
                art = Util.CERO;
            }
            if (bi == null) {
                bi = Util.CERO;
            }
            res = art.add(bi);
        }

        if (resumen.getHlpOptArt().compareTo(Util.CERO) > 0 && resumen.getHlpOptBi().compareTo(Util.CERO) > 0) {
            art = resumen.getMcomArtCon().add(resumen.getHlpOptArt().multiply(resumen.getFactorArt()));
            bi = resumen.getMcomBiCon().add(resumen.getHlpOptBi().multiply(resumen.getFactorBi()));
            res = art.add(bi).stripTrailingZeros();
        }
        if (resumen.getHlpNoptArt().compareTo(Util.CERO) > 0 && resumen.getHlpNoptBi().compareTo(Util.CERO) > 0) {
            art = resumen.getMcomArtCon().add(resumen.getHlpNoptArt().multiply(resumen.getFactorArt()));
            bi = resumen.getMcomBiCon().add(resumen.getHlpNoptBi().multiply(resumen.getFactorBi()));
            res = art.add(bi).stripTrailingZeros();
        }
        if (resumen.getHlpOptArt().compareTo(Util.CERO) > 0 && resumen.getHlpNoptBi().compareTo(Util.CERO) > 0) {
            art = resumen.getMcomArtCon().add(resumen.getHlpOptArt().multiply(resumen.getFactorArt()));
            bi = resumen.getMcomBiCon().add(resumen.getHlpNoptBi().multiply(resumen.getFactorBi()));
            res = art.add(bi).stripTrailingZeros();
        }
        if (resumen.getHlpNoptArt().compareTo(Util.CERO) > 0 && resumen.getHlpOptBi().compareTo(Util.CERO) > 0) {
            art = resumen.getMcomArtCon().add(resumen.getHlpNoptArt().multiply(resumen.getFactorArt()));
            bi = resumen.getMcomBiCon().add(resumen.getHlpOptBi().multiply(resumen.getFactorBi()));
            res = art.add(bi).stripTrailingZeros();
        }

        obtenerTotalSemana(res);

        return res;
    }

    /**
     * Carga los botones (días de la semana) con las fechas de la semana
     * anterior
     */
    public void llenarFechasAnterior() {
        Date fromDate = Util.toDate(b1);
        int c = 0;
        Locale locale = new Locale("es", "CO");
        Calendar current = Calendar.getInstance(locale);
        current.setTime(fromDate);
        current.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        while (c <= 6) {
            current.add(Calendar.DATE, -1);
            switch (c) {
                case 0:
                    b7 = Util.dateFormat(current.getTime());
                    break;
                case 1:
                    b6 = Util.dateFormat(current.getTime());
                    break;
                case 2:
                    b5 = Util.dateFormat(current.getTime());
                    break;
                case 3:
                    b4 = Util.dateFormat(current.getTime());
                    break;
                case 4:
                    b3 = Util.dateFormat(current.getTime());
                    break;
                case 5:
                    b2 = Util.dateFormat(current.getTime());
                    break;
                case 6:
                    b1 = Util.dateFormat(current.getTime());
                    break;
            }
            c++;
        }
    }

    /**
     * Carga los botones (días de la semana) con las fechas de la semana
     * siguiente
     */
    public void llenarFechasSiguiente() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Util.toDate(b7));
        cal.add(Calendar.DATE, 1);
        Date fromDate = cal.getTime();

        int c = 0;
        Locale locale = new Locale("es", "CO");
        Calendar current = Calendar.getInstance(locale);
        current.setTime(fromDate);
        current.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        while (c <= 6) {
            switch (c) {
                case 0:
                    b1 = Util.dateFormat(current.getTime());
                    break;
                case 1:
                    b2 = Util.dateFormat(current.getTime());
                    break;
                case 2:
                    b3 = Util.dateFormat(current.getTime());
                    break;
                case 3:
                    b4 = Util.dateFormat(current.getTime());
                    break;
                case 4:
                    b5 = Util.dateFormat(current.getTime());
                    break;
                case 5:
                    b6 = Util.dateFormat(current.getTime());
                    break;
                case 6:
                    b7 = Util.dateFormat(current.getTime());
                    break;
            }
            current.add(Calendar.DATE, 1);
            c++;
        }
    }

    /**
     * Valida que los botones (días de la semana) no sean mayor a la semana de
     * la fecha actual
     *
     * @return true si la ultima fecha de la semana corresponda a una semana
     * mayor a la actual
     */
    public boolean validarFechaSiguiente() {
        if (b7 != null) {
            Date f = Util.toDate(b7);
            if (f != null) {
                return f.compareTo(new Date()) >= 0;
            }

        }
        return false;
    }

    /**
     * Formatea Bigdecimal a números con 3 decimales
     *
     * @param n
     * @return
     */
    public String formatearDecimal(BigDecimal n) {
        if (n != null) {
            return Util.formatDecimal(n);
        } else {
            return "0";
        }
    }

    /**
     * Método para calcular el porcentaje de IPH (Liquidación HLP)
     *
     * @param n1
     * @param n2
     * @param flag
     * @return
     */
    public BigDecimal calcularOpDist(BigDecimal n1, BigDecimal n2, boolean flag) {
        BigDecimal res = Util.CERO;
        if (n2.compareTo(Util.CERO) > 0) {
            if (flag) {
                res = n2.divide(n1, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            } else {
                res = n2.divide(n1, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            }
        }
        return res;
    }

    /**
     * Método para calcular el porcentaje de cumplimiento Articulado
     * (Liquidación HLP)
     *
     * @return
     */
    public BigDecimal calcularCumplimientoArt() {
        BigDecimal res = Util.CERO;
        BigDecimal op;
        op = prgTcResumen.getMcomArtPrg().add(prgTcResumen.getMadicArt());
        if (op.compareTo(Util.CERO) > 0) {
            res = b_Total_Art.divide(op, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        return res;
    }

    /**
     * Método para calcular el porcentaje de cumplimiento Biarticulado
     * (Liquidación HLP)
     *
     * @return
     */
    public BigDecimal calcularCumplimientoBi() {
        BigDecimal res = Util.CERO;
        BigDecimal op;
        op = prgTcResumen.getMcomBiPrg().add(prgTcResumen.getMadicBi());
        if (op.compareTo(Util.CERO) > 0) {
            res = b_Total_Bi.divide(op, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        return res;
    }

    /**
     * Método para calcular el porcentaje de distribución (Liquidación HLP)
     *
     * @param n1
     * @param n2
     * @return
     */
    public BigDecimal calcularOpPrg(BigDecimal n1, BigDecimal n2) {
        BigDecimal res = Util.CERO;
        if (n2.compareTo(Util.CERO) > 0) {
            res = n2.divide(n1, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        return res;
    }

    /**
     * Calcula el vacío NO pago para Articulado (Liquidación HLP)
     */
    public void calcularVacArt() {
        final BigDecimal CIEN = new BigDecimal(100);
        BigDecimal n1 = prgTcResumen.getMcomArtPrg();
        BigDecimal n2 = calcularCumplimientoArt().divide(CIEN);
        BigDecimal n3 = calcularOpDist(b_IPH_ComercialArt, b_IPH_HlpArt, true).divide(CIEN);

        BigDecimal res;
        res = n1.multiply(n2).multiply(n3);
        prgTcResumen.setHlpNoptArt(res);
    }

    /**
     * Calcula el vacío NO pago para Biarticulado (Liquidación HLP)
     */
    public void calcularVacBi() {
        final BigDecimal CIEN = new BigDecimal(100);
        BigDecimal n1 = prgTcResumen.getMcomBiPrg();
        BigDecimal n2 = calcularCumplimientoBi().divide(CIEN);
        BigDecimal n3 = calcularOpDist(b_IPH_ComercialBi, b_IPH_HlpBi, true).divide(CIEN);

        BigDecimal res;
        res = n1.multiply(n2).multiply(n3);
        prgTcResumen.setHlpNoptBi(res);
    }

    /**
     * Calcula Kilómetros a pagar para Articulados (Liquidación HLP)
     *
     * @return
     */
    public BigDecimal calcularKmPagarArt() {
        final BigDecimal CIEN = new BigDecimal(100);
        BigDecimal n1 = b_PRG_HlpArt;
        BigDecimal n2 = calcularCumplimientoArt().divide(CIEN);

        BigDecimal res = Util.CERO;
        if (n2.compareTo(Util.CERO) > 0) {
            res = n1.divide(n2, 4, BigDecimal.ROUND_HALF_UP);
        }
        return res;
    }

    /**
     * Calcula Kilómetros a pagar para Biarticulados (Liquidación HLP)
     *
     * @return
     */
    public BigDecimal calcularKmPagarBi() {
        final BigDecimal CIEN = new BigDecimal(100);
        BigDecimal n1 = b_PRG_HlpBi;
        BigDecimal n2 = calcularCumplimientoBi().divide(CIEN);

        BigDecimal res = Util.CERO;
        if (n2.compareTo(Util.CERO) > 0) {
            res = n1.divide(n2, 4, BigDecimal.ROUND_HALF_UP);
        }
        return res;
    }

    /**
     * Calcula Factor (FAK) para Articulado (Liquidación HLP)
     */
    public void calcularFacArt() {
        BigDecimal n1 = b_IPH_HlpArt;
        BigDecimal n2 = b_PRG_HlpArt;
        BigDecimal n3 = n2.multiply(new BigDecimal(2));

        BigDecimal res = Util.CERO;
        if (n3.compareTo(Util.CERO) > 0) {
            res = n1.add(n2).divide(n3, 4, BigDecimal.ROUND_HALF_UP);
        }
        b_Fac_Art = res;
    }

    /**
     * Calcula Factor (FAK) para Biarticulado (Liquidación HLP)
     */
    public void calcularFacBi() {
        BigDecimal n1 = b_IPH_HlpBi;
        BigDecimal n2 = b_PRG_HlpBi;
        BigDecimal n3 = n2.multiply(new BigDecimal(2));

        BigDecimal res = Util.CERO;
        if (n2.compareTo(Util.CERO) > 0) {
            res = n1.add(n2).divide(n3, 4, BigDecimal.ROUND_HALF_UP);
        }
        b_Fac_Bi = res;
    }

    /**
     * Calcula vacío Articulado cuando hay optimización
     */
    public void calcularResArt() {
        final BigDecimal CIEN = new BigDecimal(100);
        b_Res_Art = b_Vacio_IPH_Art.multiply(calcularCumplimientoArt().divide(CIEN));
    }

    /**
     * Calcula vacío Biarticulado cuando hay optimización
     */
    public void calcularResBi() {
        final BigDecimal CIEN = new BigDecimal(100);
        b_Res_Bi = b_Vacio_IPH_Bi.multiply(calcularCumplimientoBi().divide(CIEN));
    }

    /**
     * Carga los servicios eliminados en tabla izquierda
     */
    public void obtenerEliminadosIzquierda() {
        PrimeFaces current = PrimeFaces.current();
        lstPrgTcIzquierda = null;
        lstPrgTcIzquierda = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (lstDistance != null) {
            lstDistance.clear();
            this.b_Distancia = Util.CERO;
        }
        resetSelected();
        current.executeScript("PF('cellIzq').clearFilters()");
        current.executeScript("PF('cellIzq').unselectAllRows()");
        current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
    }

    /**
     * Carga los servicios adicionales en tabla izquierda
     */
    public void obtenerAdicionalesIzquierda() {
        PrimeFaces current = PrimeFaces.current();
        lstPrgTcIzquierda = null;
        lstPrgTcIzquierda = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (lstDistance != null) {
            lstDistance.clear();
//            calcularDistancia();
            this.b_Distancia = Util.CERO;
        }
        resetSelected();
        current.executeScript("PF('cellIzq').clearFilters()");
        current.executeScript("PF('cellIzq').unselectAllRows()");
        current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
    }

    /**
     * Evento que carga los servicios en la tabla izquierda, al seleccionar tipo
     * de vehículo
     *
     * @param event
     */
    public void obtenerTvIzquierda(AjaxBehaviorEvent event) {
        PrimeFaces current = PrimeFaces.current();
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        switch (tipoVehiculoIzq) {
            case 0:
                if (tipoServicioIzq == 0) {
                    lstPrgTcIzquierda = null;
                    lstPrgTcIzquierda = prgTcEjb.findByFecha(fechaIzquierda, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoServicioIzq == 3) {
                    obtenerAdicionalesIzquierda();
                    return;
                }
                if (tipoServicioIzq == 5) {
                    obtenerEliminadosIzquierda();
                }
                break;
            case 1:
                if (tipoServicioIzq == 0) {
                    lstPrgTcIzquierda = prgTcEjb.findByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoServicioIzq == 3) {
                    lstPrgTcIzquierda = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoServicioIzq == 5) {
                    lstPrgTcIzquierda = prgTcEjb.findEliminadosByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                }
                break;
            case 2:
                if (tipoServicioIzq == 0) {
                    lstPrgTcIzquierda = prgTcEjb.findByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoServicioIzq == 3) {
                    lstPrgTcIzquierda = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoServicioIzq == 5) {
                    lstPrgTcIzquierda = prgTcEjb.findEliminadosByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                }
                break;
        }
    }

    /**
     * Evento que carga los servicios en la tabla izquierda, al seleccionar tipo
     * de vehículo
     *
     * @param event
     */
    public void obtenerTsIzquierda(AjaxBehaviorEvent event) {
        PrimeFaces current = PrimeFaces.current();
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        switch (tipoServicioIzq) {
            case 0:
                if (tipoVehiculoIzq == 0) {
                    lstPrgTcIzquierda = null;
                    lstPrgTcIzquierda = prgTcEjb.findByFecha(fechaIzquierda, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoVehiculoIzq == 1) {
                    lstPrgTcIzquierda = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoVehiculoIzq == 2) {
                    lstPrgTcIzquierda = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                }
                break;
            case 3:
                if (tipoVehiculoIzq == 0) {
                    obtenerAdicionalesIzquierda();
                    return;
                }
                if (tipoVehiculoIzq == 1) {
                    lstPrgTcIzquierda = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoVehiculoIzq == 2) {
                    lstPrgTcIzquierda = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                        this.b_Distancia = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                }
                break;
            case 5:
                if (tipoVehiculoIzq == 0) {
                    obtenerEliminadosIzquierda();
                    return;
                }
                if (tipoVehiculoIzq == 1) {
                    lstPrgTcIzquierda = prgTcEjb.findEliminadosByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                    return;
                }
                if (tipoVehiculoIzq == 2) {
                    lstPrgTcIzquierda = prgTcEjb.findEliminadosByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistance != null) {
                        lstDistance.clear();
//                        calcularDistancia();
                    }
                    resetSelected();
                    current.executeScript("PF('cellIzq').clearFilters()");
                    current.executeScript("PF('cellIzq').unselectAllRows()");
                    current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                }
                break;
        }
    }

    /**
     * Evento que carga los servicios en la tabla derecha, al seleccionar tipo
     * de vehículo
     *
     * @param event
     */
    public void obtenerTvDerecha(AjaxBehaviorEvent event) {

        PrimeFaces current = PrimeFaces.current();
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        switch (tipoVehiculoDer) {
            case 0:
                if (tipoServicioDer == 0) {
                    lstPrgTcDerecha = null;
                    lstPrgTcDerecha = prgTcEjb.findByFecha(fechaIzquierda, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoServicioDer == 3) {
                    obtenerAdicionalesDerecha();
                    return;
                }
                if (tipoServicioDer == 5) {
                    obtenerEliminadosDerecha();
                }
                break;
            case 1:
                if (tipoServicioDer == 0) {
                    lstPrgTcDerecha = prgTcEjb.findByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoServicioDer == 3) {
                    lstPrgTcDerecha = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoServicioDer == 5) {
                    lstPrgTcDerecha = prgTcEjb.findEliminadosByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                }
                break;
            case 2:
                if (tipoServicioDer == 0) {
                    lstPrgTcDerecha = prgTcEjb.findByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoServicioDer == 3) {
                    lstPrgTcDerecha = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoServicioDer == 5) {
                    lstPrgTcDerecha = prgTcEjb.findEliminadosByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                }
                break;
        }
    }

    /**
     * Evento que carga los servicios en la tabla derecha, al seleccionar tipo
     * de vehículo
     *
     * @param event
     */
    public void obtenerTsDerecha(AjaxBehaviorEvent event) {
        PrimeFaces current = PrimeFaces.current();
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        switch (tipoServicioDer) {
            case 0:
                if (tipoVehiculoDer == 0) {
                    lstPrgTcDerecha = null;
                    lstPrgTcDerecha = prgTcEjb.findByFecha(fechaIzquierda, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoVehiculoDer == 1) {
                    lstPrgTcDerecha = prgTcEjb.findByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoVehiculoDer == 2) {
                    lstPrgTcDerecha = prgTcEjb.findByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                }
                break;
            case 3:
                if (tipoVehiculoDer == 0) {
                    obtenerAdicionalesDerecha();
                    return;
                }
                if (tipoVehiculoDer == 1) {
                    lstPrgTcDerecha = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoVehiculoDer == 2) {
                    lstPrgTcDerecha = prgTcEjb.findAdicionalesByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                }
                break;
            case 5:
                if (tipoVehiculoDer == 0) {
                    obtenerEliminadosDerecha();
                    return;
                }
                if (tipoVehiculoDer == 1) {
                    lstPrgTcDerecha = prgTcEjb.findEliminadosByTipoVehiculo(fechaIzquierda, 1, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                    return;
                }
                if (tipoVehiculoDer == 2) {
                    lstPrgTcDerecha = prgTcEjb.findEliminadosByTipoVehiculo(fechaIzquierda, 2, idGopUnidadFuncional);
                    if (lstDistanceDerecha != null) {
                        lstDistanceDerecha.clear();
//                        calcularDistanciaDerecha();
                        this.b_DistanciaDerecha = Util.CERO;
                    }
                    resetSelected();
                    current.executeScript("PF('cellDer').clearFilters()");
                    current.executeScript("PF('cellDer').unselectAllRows()");
                    current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
                }
                break;
        }
    }

    /**
     * Valida las horas de inicio y fin al agregar/modificar un servicio
     */
    public void validarHora() {
        if (!horaInicioString.equals("") && !horaFinString.equals("")) {
            int dif = MovilidadUtil.diferencia(horaInicioString, horaFinString);
            if (dif < 0) {
                MovilidadUtil.addErrorMessage("La hora fin no puede ser meno a la hora inicio");
                horaFinString = "";
            }
        }
    }

    /**
     * Carga los servicios eliminados en tabla derecha
     */
    public void obtenerEliminadosDerecha() {
        PrimeFaces current = PrimeFaces.current();
        lstPrgTcDerecha = new ArrayList<>();
        lstPrgTcDerecha = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (lstDistanceDerecha != null) {
            lstDistanceDerecha.clear();
//            calcularDistanciaDerecha();
            this.b_DistanciaDerecha = Util.CERO;
        }
        resetSelected();
        current.executeScript("PF('cellDer').clearFilters()");
        current.executeScript("PF('cellDer').unselectAllRows()");
        current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
    }

    /**
     * Carga los servicios adicionales en tabla derecha
     */
    public void obtenerAdicionalesDerecha() {
        PrimeFaces current = PrimeFaces.current();
        lstPrgTcDerecha = new ArrayList<>();
        lstPrgTcDerecha = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (lstDistanceDerecha != null) {
            lstDistanceDerecha.clear();
//            calcularDistanciaDerecha();
            this.b_DistanciaDerecha = Util.CERO;
        }
        resetSelected();
        current.executeScript("PF('cellDer').clearFilters()");
        current.executeScript("PF('cellDer').unselectAllRows()");
        current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
    }

    /**
     * Obtiene el resumen detallado de Kilómetros para una fecha específica
     */
    public void getResumenKm() {
        prgTcResumen = prgTcResumenEjb.findByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (prgTcResumen == null) {
            b_Total_Art = Util.CERO;
            b_Total_Bi = Util.CERO;
            b_Total = Util.CERO;
            b_Adicionales = Util.CERO;
            b_Eliminados = Util.CERO;
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("No existe programación cargada para el día: " + Util.dateFormat(fechaIzquierda));
            return;
        }
        KmsResumen kmsResumen = prgTcResumenEjb.getResumen(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (prgTcResumen.getConciliado() == null) {
            flagConciliacion = true;
            prgTcResumen.setConciliado(0);
        } else {
            flagConciliacion = prgTcResumen.getConciliado() == 0 || prgTcResumen.getConciliado() == 2;
        }

        BigDecimal m_comArtPrg_Aux = prgTcResumen.getMcomArtPrg();
        BigDecimal m_comArtPrg = m_comArtPrg_Aux != null ? m_comArtPrg_Aux : Util.CERO;

        BigDecimal m_comBiPrg_Aux = prgTcResumen.getMcomBiPrg();
        BigDecimal m_comBiPrg = m_comBiPrg_Aux != null ? m_comBiPrg_Aux : Util.CERO;

        BigDecimal b_elimArt_Aux = kmsResumen.getElimArt();
        BigDecimal b_elimArt = b_elimArt_Aux != null ? b_elimArt_Aux : Util.CERO;

        BigDecimal b_elimBi_Aux = kmsResumen.getElimBi();
        BigDecimal b_elimBi = b_elimBi_Aux != null ? b_elimBi_Aux : Util.CERO;

        BigDecimal b_adicArt_Aux = kmsResumen.getAdcArt();
        BigDecimal b_adicArt = b_adicArt_Aux != null ? b_adicArt_Aux : Util.CERO;

        BigDecimal b_adicBi_Aux = kmsResumen.getAdcBi();
        BigDecimal b_adicBi = b_adicBi_Aux != null ? b_adicBi_Aux : Util.CERO;

        BigDecimal b_ComEjeConArt_Aux = prgTcResumen.getMcomArtCon();
        BigDecimal b_ComEjeConArt = b_ComEjeConArt_Aux != null ? b_ComEjeConArt_Aux : Util.CERO;

        BigDecimal b_ComEjeConBi_Aux = prgTcResumen.getMcomBiCon();
        BigDecimal b_ComEjeConBi = b_ComEjeConBi_Aux != null ? b_ComEjeConBi_Aux : Util.CERO;

        prgTcResumen.setMelimArt(b_elimArt);
        prgTcResumen.setMelimBi(b_elimBi);
        prgTcResumen.setMadicArt(b_adicArt);
        prgTcResumen.setMadicBi(b_adicBi);
        prgTcResumen.setMcomArtCon(b_ComEjeConArt);
        prgTcResumen.setMcomBiCon(b_ComEjeConBi);
        b_Adicionales = prgTcResumen.getMadicArt().add(prgTcResumen.getMadicBi()) != null ? prgTcResumen.getMadicArt().add(prgTcResumen.getMadicBi()) : Util.CERO;
        b_Eliminados = b_elimArt.add(b_elimBi);
        b_Total = prgTcResumen.getMcomPrg().add(b_Adicionales).subtract(b_Eliminados);
        b_Total_Art = m_comArtPrg.add(prgTcResumen.getMadicArt()).subtract(prgTcResumen.getMelimArt());
        b_Total_Bi = m_comBiPrg.add(prgTcResumen.getMadicBi()).subtract(prgTcResumen.getMelimBi());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Resúmen cargado éxitosamente"));
    }

    /**
     * Asigna colores a los servicios que se encuentran en la tabla izquierda
     *
     * @param actual
     * @return nombre de clase CSS que aplica el color a las filas de la tabla
     */
    public String colores(PrgTc actual) {

        if (actual == null) {
            return "";
        }
        if (codV.isEmpty()) {
            codV = actual.getIdVehiculo().getCodigo();
            color = "blueColor";
            return color;
        }

        if (codV.equals(actual.getIdVehiculo().getCodigo()) && color.equals("blueColor")) {
            color = "blueColor";
            return color;
        }
        if (!(codV.equals(actual.getIdVehiculo().getCodigo())) && color.equals("blueColor")) {
            codV = actual.getIdVehiculo().getCodigo();
            color = "adicionales";
            return color;
        }
        if (codV.equals(actual.getIdVehiculo().getCodigo()) && color.equals("adicionales")) {
            color = "adicionales";
            return color;

        }
        if (!(codV.equals(actual.getIdVehiculo().getCodigo())) && color.equals("adicionales")) {
            codV = actual.getIdVehiculo().getCodigo();
            color = "blueColor";
            return color;
        }
        return color;
    }

    /**
     * Asigna colores a los servicios que se encuentran en la tabla derecha
     *
     * @param actual
     * @return nombre de clase CSS que aplica el color a las filas de la tabla
     */
    public String coloresDerecha(PrgTc actual) {

        if (actual == null) {
            return "";
        }
        if (codVDer.isEmpty()) {
            codVDer = actual.getIdVehiculo().getCodigo();
            colorDer = "grayColor";
            return colorDer;
        }

        if (codVDer.equals(actual.getIdVehiculo().getCodigo()) && colorDer.equals("grayColor")) {
            colorDer = "grayColor";
            return colorDer;
        }
        if (!(codVDer.equals(actual.getIdVehiculo().getCodigo())) && colorDer.equals("grayColor")) {
            codVDer = actual.getIdVehiculo().getCodigo();
            colorDer = "eliminados";
            return colorDer;
        }
        if (codVDer.equals(actual.getIdVehiculo().getCodigo()) && colorDer.equals("eliminados")) {
            colorDer = "eliminados";
            return colorDer;

        }
        if (!(codVDer.equals(actual.getIdVehiculo().getCodigo())) && colorDer.equals("eliminados")) {
            codVDer = actual.getIdVehiculo().getCodigo();
            colorDer = "grayColor";
            return colorDer;
        }
        return colorDer;
    }

    /**
     * Guarda los datos de la concialiación en la tabla resumen
     */
    @Transactional
    public void guardarConciliacion() {
        if (prgTcResumen.getIdPrgTcResumen() > 0) {
            prgTcEjb.updateTmDistance(fechaIzquierda);
            prgTcResumen.setMcomArtCon(b_Total_Art);
            prgTcResumen.setMcomBiCon(b_Total_Bi);
            prgTcResumen.setConciliado(1);
            prgTcResumen.setUsername(user.getUsername());
            prgTcResumen.setIphComArt(Util.CERO);
            prgTcResumen.setIphComBi(Util.CERO);
            prgTcResumen.setPrgComArt(Util.CERO);
            prgTcResumen.setPrgComBi(Util.CERO);
            prgTcResumen.setIphHlpArt(Util.CERO);
            prgTcResumen.setIphHlpBi(Util.CERO);
            prgTcResumen.setPrgHlpArt(Util.CERO);
            prgTcResumen.setPrgHlpBi(Util.CERO);
            prgTcResumen.setHlpNoptArt(Util.CERO);
            prgTcResumen.setFactorArt(Util.CERO);
            prgTcResumen.setHlpiphOptimizadoArt(Util.CERO);
            prgTcResumen.setHlpOptArt(Util.CERO);
            prgTcResumen.setFactorArt(Util.CERO);
            prgTcResumen.setHlpNoptBi(Util.CERO);
            prgTcResumen.setFactorBi(Util.CERO);
            prgTcResumen.setHlpiphOptimizadoBi(Util.CERO);
            prgTcResumen.setHlpOptBi(Util.CERO);
            prgTcResumen.setFactorBi(Util.CERO);

            if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
                prgTcResumen.setIdGopUnidadFuncional(null);
            } else {
                prgTcResumen.setIdGopUnidadFuncional(new GopUnidadFuncional(
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
            }

            prgTcResumenEjb.edit(prgTcResumen);
            flagConciliacion = false;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Conciliación guardada con éxito"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe cargar el consolidado de kilómetros"));
        }
    }

    /**
     * Guarda los datos de la liquidación de vacíos en la tabla resumen
     */
    @Transactional
    public void guardarLiquidacionHlp() {
        if (prgTcResumen.getIdPrgTcResumen() > 0) {
            prgTcResumen.setUsername(user.getUsername());
            prgTcResumen.setIphComArt(b_IPH_ComercialArt);
            prgTcResumen.setIphComBi(b_IPH_ComercialBi);
            prgTcResumen.setPrgComArt(b_PRG_ComercialArt);
            prgTcResumen.setPrgComBi(b_PRG_ComercialBi);
            prgTcResumen.setIphHlpArt(b_IPH_HlpArt);
            prgTcResumen.setIphHlpBi(b_IPH_HlpBi);
            prgTcResumen.setPrgHlpArt(b_PRG_HlpArt);
            prgTcResumen.setPrgHlpBi(b_PRG_HlpBi);

            if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
                prgTcResumen.setIdGopUnidadFuncional(null);
            } else {
                prgTcResumen.setIdGopUnidadFuncional(new GopUnidadFuncional(
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
            }

            if (b_PRG_HlpArt.compareTo(b_IPH_HlpArt) == 1) {
                if (b_Res_Art_NoOpt.compareTo(Util.CERO) == 1) {
                    prgTcResumen.setHlpNoptArt(b_Res_Art_NoOpt);
                    prgTcResumen.setHlpOptArt(Util.CERO);
                    prgTcResumen.setFactorArt(new BigDecimal(1));
                } else {
                    prgTcResumen.setHlpOptArt(Util.CERO);
                    prgTcResumen.setFactorArt(new BigDecimal(1));
                }
            } else {
                if (b_Res_Art_NoOpt.compareTo(Util.CERO) == 1) {
                    prgTcResumen.setHlpOptArt(b_Res_Art_NoOpt);
                    prgTcResumen.setHlpNoptArt(Util.CERO);
                    prgTcResumen.setFactorArt(b_Fac_Art);
                    prgTcResumen.setHlpiphOptimizadoArt(b_Vacio_IPH_Art);
                } else {
                    prgTcResumen.setHlpiphOptimizadoArt(b_Vacio_IPH_Art);
                    prgTcResumen.setHlpOptArt(b_Res_Art);
                    prgTcResumen.setHlpNoptArt(Util.CERO);
                    prgTcResumen.setFactorArt(b_Fac_Art);
                }
            }

            if (b_PRG_HlpBi.compareTo(b_IPH_HlpBi) == 1) {
                if (b_Res_Bi_NoOpt.compareTo(Util.CERO) == 1) {
                    prgTcResumen.setHlpNoptBi(b_Res_Bi_NoOpt);
                    prgTcResumen.setFactorBi(new BigDecimal(1));
                    prgTcResumen.setHlpOptBi(Util.CERO);
                } else {
                    prgTcResumen.setHlpOptBi(Util.CERO);
                    prgTcResumen.setFactorBi(new BigDecimal(1));
                }
            } else {
                if (b_Res_Bi_NoOpt.compareTo(Util.CERO) == 1) {
                    prgTcResumen.setHlpiphOptimizadoBi(b_Vacio_IPH_Bi);
                    prgTcResumen.setHlpNoptBi(Util.CERO);
                    prgTcResumen.setHlpOptBi(b_Res_Bi_NoOpt);
                    prgTcResumen.setFactorBi(b_Fac_Bi);
                } else {
                    prgTcResumen.setHlpiphOptimizadoBi(b_Vacio_IPH_Bi);
                    prgTcResumen.setHlpNoptBi(Util.CERO);
                    prgTcResumen.setHlpOptBi(b_Res_Bi);
                    prgTcResumen.setFactorBi(b_Fac_Bi);
                }
            }

            prgTcResumenEjb.edit(prgTcResumen);
            if (b_Res_Art_NoOpt.compareTo(Util.CERO) > 0) {
                b_Res_Art_NoOpt = Util.CERO;
            }
            if (b_Res_Bi_NoOpt.compareTo(Util.CERO) > 0) {
                b_Res_Bi_NoOpt = Util.CERO;
            }
            flagConciliacion = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Liquidación guardada con éxito"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Error al guardar liquidación"));
        }
    }

    /**
     * Deshace los datos de un día que se halla conciliado
     */
    @Transactional
    public void deshacerConciliacion() {
        PrgTcResumen prgTcResumenAux = prgTcResumenEjb.findByFecha(fechaIzquierda,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (prgTcResumenAux.getIdPrgTcResumen() > 0) {
            prgTcResumenAux.setMelimArt(Util.CERO);
            prgTcResumenAux.setMelimBi(Util.CERO);
            prgTcResumenAux.setMadicArt(Util.CERO);
            prgTcResumenAux.setMadicBi(Util.CERO);
            prgTcResumenAux.setMcomArtCon(Util.CERO);
            prgTcResumenAux.setMcomBiCon(Util.CERO);
            prgTcResumenAux.setIphComArt(Util.CERO);
            prgTcResumenAux.setIphComBi(Util.CERO);
            prgTcResumenAux.setPrgComArt(Util.CERO);
            prgTcResumenAux.setPrgComBi(Util.CERO);
            prgTcResumenAux.setIphHlpArt(Util.CERO);
            prgTcResumenAux.setIphHlpBi(Util.CERO);
            prgTcResumenAux.setPrgHlpArt(Util.CERO);
            prgTcResumenAux.setPrgHlpBi(Util.CERO);
            prgTcResumenAux.setHlpiphOptimizadoArt(Util.CERO);
            prgTcResumenAux.setHlpiphOptimizadoBi(Util.CERO);
            prgTcResumenAux.setHlpOptArt(Util.CERO);
            prgTcResumenAux.setHlpOptBi(Util.CERO);
            prgTcResumenAux.setHlpNoptArt(Util.CERO);
            prgTcResumenAux.setHlpNoptBi(Util.CERO);
            prgTcResumenAux.setFactorArt(Util.CERO);
            prgTcResumenAux.setFactorBi(Util.CERO);
            prgTcResumenAux.setConciliado(0);
            prgTcResumen.setConciliado(0);
            prgTcResumenEjb.edit(prgTcResumenAux);
            flagConciliacion = true;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Conciliación revertida con éxito"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe cargar el consolidado de kilómetros"));
        }
    }

    /**
     * Genera nuevo servbus para adición/modificación de servicio
     */
    public void getServbusString() {
        String tipo = "TA";
        if (vehiculo != null) {
            if (vehiculo.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                tipo = "TB";
            }
        }
        prgTc.setServbus(MovilidadUtil.servbus(tipo));
    }

    /**
     * Elimina servicio adicional
     */
    @Transactional
    public void eliminarAdicional() {
        PrimeFaces current = PrimeFaces.current();

        if (selected == null) {
            current.ajax().update(":messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un servicio");
            return;
        }

        if (i_codResponsable == 0) {
            current.ajax().update(":messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un responsable");
            return;
        }

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }

        this.selected.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
        this.selected.setEstadoOperacion(99);
        if (selected.getObservaciones() == null) {
            this.selected.setObservaciones("Servicio adicional eliminado por " + user.getUsername());
        } else {
            this.selected.setObservaciones(selected.getObservaciones() + " (Servicio adicional eliminado por " + user.getUsername() + ")");
        }
        this.selected.setUsername(user.getUsername());
        this.selected.setModificado(new Date());
        prgTcEjb.edit(selected);

        if (tipoServicioIzq == 3) {
            for (PrgTc p1 : lstPrgTcIzquierda) {
                if (p1.getIdPrgTc().equals(selected.getIdPrgTc())) {
                    lstPrgTcIzquierda.remove(p1);
                    break;
                }
            }
            current.executeScript("PF('cellIzq').clearFilters()");
            current.executeScript("PF('cellIzq').unselectAllRows()");
            current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
        }
        if (tipoServicioDer == 3) {
            for (PrgTc p2 : lstPrgTcDerecha) {
                if (p2.getIdPrgTc().equals(selected.getIdPrgTc())) {
                    lstPrgTcDerecha.remove(p2);
                    break;
                }
            }
            current.executeScript("PF('cellDer').clearFilters()");
            current.executeScript("PF('cellDer').unselectAllRows()");
            current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
        }

        current.executeScript("PF('eliminarAdicional').hide()");
        resetSelected();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Adicional eliminado con éxito"));
    }

    /**
     * Método global que elimina servicio programado y carga listado de
     * servicios eliminados
     */
    public void eliminarPrg() {
        eliminarProgramado();
        actListasEliminados();
    }

    /**
     * Elimina servicios programados
     */
    @Transactional
    public void eliminarProgramado() {
        PrimeFaces current = PrimeFaces.current();
        this.prgTc = this.selected;

        if (selected == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un servicio");
            return;
        }

        if (i_codResponsable == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un responsable");
            return;
        }

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }

        this.prgTc.setIdPrgTcResponsable(prgTcResponsableEjb.find(i_codResponsable));
        this.prgTc.setEstadoOperacion(5);
        if (prgTc.getObservaciones() == null) {
            this.prgTc.setObservaciones("Servicio programado eliminado por " + user.getUsername());
        } else {
            this.prgTc.setObservaciones(prgTc.getObservaciones() + " (Servicio programado eliminado por " + user.getUsername() + ")");
        }
        this.prgTc.setUsername(user.getUsername());
        this.prgTc.setModificado(new Date());
        prgTcEjb.edit(prgTc);

        current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
        current.executeScript("PF('eliminarProgramado').hide()");
        resetSelected();
    }

    /**
     * Carga listado servicios eliminados al eliminar un servicio programado
     */
    private void actListasEliminados() {
        PrimeFaces current = PrimeFaces.current();

        if (tipoServicioIzq == 0) {
            if (tipoServicioDer == 5) {
                lstPrgTcDerecha = new ArrayList<>();
                lstPrgTcDerecha = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                current.executeScript("PF('cellDer').clearFilters()");
                current.executeScript("PF('cellDer').unselectAllRows()");
                current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
            }
            lstDistance.clear();
            this.b_Distancia = Util.CERO;
            current.ajax().update("tabResumen:frmIzquierda:paramIzquierda");
        }
        if (tipoServicioDer == 0) {
            if (tipoServicioIzq == 5) {
                lstPrgTcIzquierda = new ArrayList<>();
                lstPrgTcIzquierda = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                current.executeScript("PF('cellIzq').clearFilters()");
                current.executeScript("PF('cellIzq').unselectAllRows()");
                current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
            }
            this.b_DistanciaDerecha = Util.CERO;
            lstDistanceDerecha.clear();
            current.ajax().update("tabResumen:frmDerecha:paramDerecha");
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Servicio programado eliminado con éxito"));
    }

    /**
     * Método global que deshace servicio eliminado y carga listado de servicios
     * programados
     */
    public void deshacerElim() {
        deshacerEliminado();
        actListaPrg();
    }

    private void actListaPrg() {
        PrimeFaces current = PrimeFaces.current();
        if (tipoServicioIzq == 0) {
            lstPrgTcIzquierda = new ArrayList<>();
            lstPrgTcIzquierda = prgTcEjb.findByFecha(fechaIzquierda,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            current.executeScript("PF('cellIzq').clearFilters()");
            current.executeScript("PF('cellIzq').unselectAllRows()");
            current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
        }
        if (tipoServicioDer == 0) {
            lstPrgTcDerecha = new ArrayList<>();
            lstPrgTcDerecha = prgTcEjb.findByFecha(fechaIzquierda,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            current.executeScript("PF('cellDer').clearFilters()");
            current.executeScript("PF('cellDer').unselectAllRows()");
            current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Servicio revertido con éxito"));
    }

    /**
     * Deshace un servicio eliminado, es decir, pasa un servicio eliminado a
     * programado
     */
    @Transactional
    public void deshacerEliminado() {
        PrimeFaces current = PrimeFaces.current();

        if (selected == null) {
            current.ajax().update(":messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un servicio");
            return;
        }

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }

        this.prgTc = this.selected;

        if (this.prgTc.getServbus().endsWith("AD")) {
            this.prgTc.setEstadoOperacion(4);
        } else {
            this.prgTc.setEstadoOperacion(0);
        }
        if (prgTc.getObservaciones() == null) {
            this.prgTc.setObservaciones("Se revierte servicio eliminado por " + user.getUsername());
        } else {
            this.prgTc.setObservaciones(prgTc.getObservaciones() + " (Se revierte servicio eliminado por " + user.getUsername() + ")");
        }
        this.prgTc.setUsername(user.getUsername());
        this.prgTc.setModificado(new Date());
        prgTcEjb.edit(prgTc);

        if (tipoServicioIzq == 5) {
            for (PrgTc p1 : lstPrgTcIzquierda) {
                if (p1.getIdPrgTc().equals(prgTc.getIdPrgTc())) {
                    lstPrgTcIzquierda.remove(p1);
                    break;
                }
            }
            if (lstDistance != null) {
                lstDistance.clear();
            }
            this.b_Distancia = Util.CERO;
            current.ajax().update("tabResumen:frmIzquierda:paramIzquierda");
        }
        if (tipoServicioDer == 5) {
            for (PrgTc p2 : lstPrgTcDerecha) {
                if (p2.getIdPrgTc().equals(prgTc.getIdPrgTc())) {
                    lstPrgTcDerecha.remove(p2);
                    break;
                }
            }
            this.b_DistanciaDerecha = Util.CERO;
            if (lstDistanceDerecha != null) {
                lstDistanceDerecha.clear();
            }
            current.ajax().update("tabResumen:frmDerecha:paramDerecha");
        }

        current.executeScript("PF('cellDer').clearFilters()");
        current.executeScript("PF('cellDer').unselectAllRows()");
        current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
        resetSelected();
    }

    /**
     * Realiza búsqueda de vehículo por código
     */
    public void buscarVehiculo() {
        vehiculo = new Vehiculo();
        vehiculo = vehiculoEjb.getVehiculoCodigo(codVehiculo.toUpperCase());
        if (vehiculo != null) {
            MovilidadUtil.addSuccessMessage("Vehículo encontrado");
            return;
        }
        MovilidadUtil.addErrorMessage("Vehículo no encontrado");
    }

    /**
     * Reasigna servicios (RESGISTROS prg_tc) de un vehículo a otro en la tabla
     * izquierda
     */
    @Transactional
    public void reasignarTarea() {
        Vehiculo newVehiculo = vehiculo;
        if (newVehiculo == null) {
            MovilidadUtil.addErrorMessage("Debe especificar un vehículo válido");
            return;
        }

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }

        for (PrgTc t : lstDistance) {
            if (t.getIdVehiculoTipo().getIdVehiculoTipo() == 1 && newVehiculo.getIdVehiculoTipo().getIdVehiculoTipo() == 2) {
                MovilidadUtil.addErrorMessage("No se puede cargar un Biarticulado en un servicio de Art.");
                PrimeFaces.current().ajax().update("frmReasignarTarea:messages");
                return;
            }
            t.setIdVehiculo(newVehiculo);
            t.setIdVehiculoTipo(newVehiculo.getIdVehiculoTipo());
            prgTcEjb.edit(t);
        }
        PrimeFaces.current().executeScript("PF('ReasignarTarea').hide()");
        PrimeFaces.current().ajax().update(":messages");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Servicio(s) reasignado(s) éxitosamente"));
    }

    /**
     * Cambia responsable de un servicio eliminado
     */
    public void cambiarResponsable() {
        PrimeFaces current = PrimeFaces.current();
        if (i_codResponsable > 0) {
            if (validarEstadoConciliacion()) {
                current.ajax().update(":messages");
                MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
                return;
            }
            selected.setIdPrgTcResponsable(prgTcResponsableEjb.find(i_codResponsable));
            prgTcEjb.edit(selected);
            i_codResponsable = 0;
            if (tipoServicioIzq == 5) {
                tipoServicioIzq = 5;
                tipoVehiculoIzq = 0;
                lstDistance.clear();
                this.b_Distancia = Util.CERO;
                current.ajax().update("tabResumen:frmIzquierda:paramIzquierda");
                lstPrgTcIzquierda = null;
                lstPrgTcIzquierda = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                current.executeScript("PF('cellIzq').unselectAllRows()");
                current.executeScript("PF('cellIzq').clearFilters()");
                current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
            } else if (tipoServicioDer == 5) {
                tipoServicioDer = 5;
                tipoVehiculoDer = 0;
                this.b_DistanciaDerecha = Util.CERO;
                lstDistanceDerecha.clear();
                current.ajax().update("tabResumen:frmDerecha:paramDerecha");
                lstPrgTcDerecha = null;
                lstPrgTcDerecha = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                current.executeScript("PF('cellDer').unselectAllRows()");
                current.executeScript("PF('cellDer').clearFilters()");
                current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
            } else {
                current.executeScript("PF('cellIzq').unselectAllRows()");
                current.executeScript("PF('cellIzq').clearFilters()");
                current.ajax().update("tabResumen:frmIzquierda:dtPrgtcIzquierda");
                current.executeScript("PF('cellDer').unselectAllRows()");
                current.executeScript("PF('cellDer').clearFilters()");
                current.ajax().update("tabResumen:frmDerecha:dtPrgtcDerecha");
            }
            resetSelected();
            current.executeScript("PF('cambiarResponsable').hide()");
            MovilidadUtil.addSuccessMessage("Responsable actualizado éxitosamente");
        } else {
            MovilidadUtil.addErrorMessage("Debe seleccionar un responsable");
        }
    }

    /**
     * Reasigna servicios (RESGISTROS prg_tc) de un vehículo a otro en la tabla
     * derecha
     */
    @Transactional
    public void reasignarTareaDerecha() {
        Vehiculo newVehiculo = vehiculo;
        if (newVehiculo == null) {
            MovilidadUtil.addErrorMessage("Debe especificar un vehículo válido");
            return;
        }

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update("frmReasignarTareaDer:messagesDer");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }

        for (PrgTc t : lstDistanceDerecha) {
            if (t.getIdVehiculoTipo().getIdVehiculoTipo() == 1 && newVehiculo.getIdVehiculoTipo().getIdVehiculoTipo() == 2) {
                MovilidadUtil.addErrorMessage("No se puede cargar un Biarticulado en un servicio de Art.");
                PrimeFaces.current().ajax().update("frmReasignarTareaDer:messagesDer");
                return;
            }
            t.setIdVehiculo(newVehiculo);
            t.setIdVehiculoTipo(newVehiculo.getIdVehiculoTipo());
            prgTcEjb.edit(t);
        }
        PrimeFaces.current().executeScript("PF('ReasignarTareaDer').hide()");
        PrimeFaces.current().ajax().update(":messages");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Servicio(s) reasignado(s) éxitosamente"));
    }

    /**
     * Evento que captura la modificación de una distancia en la tabla izquierda
     *
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {
        try {
            PrimeFaces current = PrimeFaces.current();
            Integer id = Integer.valueOf(event.getRowKey());
            PrgTc prgTcfind = prgTcEjb.find(id);
            PrgTc nvoPrg = null;
            String regex = "^[+-=-][0-9]+";
            if (prgTcfind != null) {

                if (validarEstadoConciliacion()) {
                    PrimeFaces.current().ajax().update(":messages");
                    MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
                    return;
                }

                BigDecimal oldValue = prgTcfind.getDistance();
                String newValue = (String) event.getNewValue();
                if (newValue.isEmpty() || newValue.equals("")) {
                    return;
                }
                if (Pattern.matches(regex, newValue)) {
                    if (newValue.contains("+")) {
                        String[] split = newValue.split("\\+");
                        BigDecimal bg_aux = new BigDecimal(split[1]);
                        BigDecimal add = bg_aux.add(oldValue);
                        if (prgTcfind.getEstadoOperacion() == 5) {
                            if (add.compareTo(prgTcfind.getDistance()) == 1) {
                                MovilidadUtil.addErrorMessage("No se puede asignar un valor mayor al permitido");
                                return;
                            }
                        }

                        prgTcfind.setDistance(add);
                        prgTcEjb.edit(prgTcfind);

                        if (prgTcfind.getEstadoOperacion() == 0 || prgTcfind.getEstadoOperacion() == 1 || prgTcfind.getEstadoOperacion() == 2) {
                            tipoServicioIzq = 0;
                            tipoVehiculoIzq = 0;
                            lstPrgTcIzquierda = null;
                            lstPrgTcIzquierda = prgTcEjb.findByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellIzq').clearFilters()");
//                            System.out.println("prgTcEjb.findByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 3 || prgTcfind.getEstadoOperacion() == 4 || prgTcfind.getEstadoOperacion() == 6) {
                            tipoServicioIzq = 3;
                            tipoVehiculoIzq = 0;
                            lstPrgTcIzquierda = null;
                            lstPrgTcIzquierda = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellIzq').clearFilters()");
//                            System.out.println("prgTcEjb.findAdicionalesByFecha(fechaIzquierda)");
                        }

                        MovilidadUtil.addSuccessMessage("Se ha adicionado " + bg_aux + " al registro indicado, nuevo valor " + add + " ");
                        c_operacionDistancia = "";
                        resetSelected();
                        return;
                    }
                    if (newValue.contains("-")) {
                        String[] split = newValue.split("\\-");
                        BigDecimal bg_aux = new BigDecimal(split[1]);
                        if (bg_aux.compareTo(prgTcfind.getDistance()) == 1) {
                            MovilidadUtil.addErrorMessage("Está intentando restar más de lo permitido");
                            return;
                        }
                        BigDecimal subt = oldValue.subtract(bg_aux);
                        prgTcfind.setDistance(subt);
                        prgTcEjb.edit(prgTcfind);

                        if (prgTcfind.getEstadoOperacion() == 0 || prgTcfind.getEstadoOperacion() == 1 || prgTcfind.getEstadoOperacion() == 2) {
                            tipoServicioIzq = 0;
                            tipoVehiculoIzq = 0;
                            lstPrgTcIzquierda = null;
                            lstPrgTcIzquierda = prgTcEjb.findByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellIzq').clearFilters()");
//                            System.out.println("prgTcEjb.findByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 3 || prgTcfind.getEstadoOperacion() == 4 || prgTcfind.getEstadoOperacion() == 6) {
                            tipoServicioIzq = 3;
                            tipoVehiculoIzq = 0;
                            lstPrgTcIzquierda = null;
                            lstPrgTcIzquierda = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellIzq').clearFilters()");
//                            System.out.println("prgTcEjb.findAdicionalesByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 5) {

                            nvoPrg = prgTcfind;
                            nvoPrg.setIdPrgTc(null);
                            nvoPrg.setEstadoOperacion(2);
                            nvoPrg.setObservaciones("Servicio recuperado por menor valor eliminado");
                            nvoPrg.setDistance(bg_aux);
                            nvoPrg.setCreado(new Date());
                            prgTcEjb.create(nvoPrg);

                            tipoServicioIzq = 5;
                            tipoVehiculoIzq = 0;
                            lstPrgTcIzquierda = null;
                            lstPrgTcIzquierda = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellIzq').clearFilters()");
//                            System.out.println("prgTcEjb.findEliminadosByFecha(fechaIzquierda);");
                        }
                        MovilidadUtil.addSuccessMessage("Se ha restado " + bg_aux + " al registro indicado, nuevo valor " + subt + " ");
                        c_operacionDistancia = "";
                        resetSelected();
                        return;
                    }
                    if (newValue.contains("=")) {
                        String[] split = newValue.split("\\=");
                        BigDecimal bg_aux = new BigDecimal(split[1]);
                        if (prgTcfind.getEstadoOperacion() == 5) {
                            if (bg_aux.compareTo(prgTcfind.getDistance()) == 1) {
                                MovilidadUtil.addErrorMessage("Está intentando asignar un valor mayor a lo permitido");
                                return;
                            }
                        }

                        prgTcfind.setDistance(bg_aux);
                        prgTcfind.setModificado(new Date());
                        prgTcEjb.edit(prgTcfind);

                        if (prgTcfind.getEstadoOperacion() == 0 || prgTcfind.getEstadoOperacion() == 1 || prgTcfind.getEstadoOperacion() == 2) {
                            tipoServicioIzq = 0;
                            tipoVehiculoIzq = 0;
                            lstPrgTcIzquierda = null;
                            lstPrgTcIzquierda = prgTcEjb.findByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellIzq').clearFilters()");
//                            System.out.println("prgTcEjb.findByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 3 || prgTcfind.getEstadoOperacion() == 4 || prgTcfind.getEstadoOperacion() == 6) {
                            tipoServicioIzq = 3;
                            tipoVehiculoIzq = 0;
                            lstPrgTcIzquierda = null;
                            lstPrgTcIzquierda = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellIzq').clearFilters()");
//                            System.out.println("prgTcEjb.findAdicionalesByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 5) {

                            nvoPrg = prgTcfind;
                            nvoPrg.setIdPrgTc(null);
                            nvoPrg.setEstadoOperacion(2);
                            nvoPrg.setObservaciones("Servicio recuperado por menor valor eliminado");
                            nvoPrg.setDistance(oldValue.subtract(bg_aux));
                            nvoPrg.setCreado(new Date());
                            prgTcEjb.create(nvoPrg);

                            tipoServicioIzq = 5;
                            tipoVehiculoIzq = 0;
                            lstPrgTcIzquierda = null;
                            lstPrgTcIzquierda = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellIzq').clearFilters()");
//                            System.out.println("prgTcEjb.findEliminadosByFecha(fechaIzquierda);");
                        }
                        MovilidadUtil.addSuccessMessage("Se ha cambiado el registro por: " + prgTcfind.getDistance());
                        c_operacionDistancia = "";
                        resetSelected();
                        return;
                    }
                    if (!newValue.contains("+") && !newValue.contains("-") && !newValue.contains("=")) {
                        MovilidadUtil.addErrorMessage("Falta operador aritmético (+,- ó =)");
                    }
                } else {
                    MovilidadUtil.addErrorMessage("Dato no valido");
                }
            } else {
                MovilidadUtil.addErrorMessage("Error al seleccionar campo");
            }
        } catch (Exception e) {
            System.out.println("Error en EditCell");
            System.out.println(e.getMessage());
            MovilidadUtil.addErrorMessage("Dato no valido");
        }
    }

    /**
     * Evento que captura la modificación de una distancia en la tabla derecha
     *
     * @param event
     */
    public void onCellEditDerecha(CellEditEvent event) {
        try {
            PrimeFaces current = PrimeFaces.current();
            Integer id = Integer.valueOf(event.getRowKey());
            PrgTc prgTcfind = prgTcEjb.find(id);
            PrgTc nvoPrg = null;
            String regex = "^[+-=-][0-9]+";
            if (prgTcfind != null) {

                if (validarEstadoConciliacion()) {
                    PrimeFaces.current().ajax().update(":messages");
                    MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
                    return;
                }

                BigDecimal oldValue = prgTcfind.getDistance();
                String newValue = (String) event.getNewValue();
                if (newValue.isEmpty() || newValue.equals("")) {
                    return;
                }
                if (Pattern.matches(regex, newValue)) {
                    if (newValue.contains("+")) {
                        String[] split = newValue.split("\\+");
                        BigDecimal bg_aux = new BigDecimal(split[1]);
                        BigDecimal add = bg_aux.add(oldValue);
                        if (prgTcfind.getEstadoOperacion() == 5) {
                            if (add.compareTo(prgTcfind.getDistance()) == 1) {
                                MovilidadUtil.addErrorMessage("No se puede asignar un valor mayor al permitido");
                                return;
                            }
                        }

                        prgTcfind.setDistance(add);
                        prgTcfind.setModificado(new Date());
                        prgTcEjb.edit(prgTcfind);

                        if (prgTcfind.getEstadoOperacion() == 0 || prgTcfind.getEstadoOperacion() == 1 || prgTcfind.getEstadoOperacion() == 2) {
                            tipoServicioDer = 0;
                            tipoVehiculoDer = 0;
                            lstPrgTcDerecha = null;
                            lstPrgTcDerecha = prgTcEjb.findByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellDer').clearFilters()");
//                            System.out.println("prgTcEjb.findByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 3 || prgTcfind.getEstadoOperacion() == 4 || prgTcfind.getEstadoOperacion() == 6) {
                            tipoServicioDer = 3;
                            tipoVehiculoDer = 0;
                            lstPrgTcDerecha = null;
                            lstPrgTcDerecha = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellDer').clearFilters()");
//                            System.out.println("prgTcEjb.findAdicionalesByFecha(fechaIzquierda)");
                        }

                        MovilidadUtil.addSuccessMessage("Se ha adicionado " + bg_aux + " al registro indicado, nuevo valor " + add + " ");
                        c_operacionDistancia = "";
                        resetSelected();
                        return;
                    }
                    if (newValue.contains("-")) {
                        String[] split = newValue.split("\\-");
                        BigDecimal bg_aux = new BigDecimal(split[1]);
                        if (bg_aux.compareTo(prgTcfind.getDistance()) == 1) {
                            MovilidadUtil.addErrorMessage("Está intentando restar más de lo permitido");
                            return;
                        }
                        BigDecimal subt = oldValue.subtract(bg_aux);
                        prgTcfind.setDistance(subt);
                        prgTcfind.setModificado(new Date());
                        prgTcEjb.edit(prgTcfind);

                        if (prgTcfind.getEstadoOperacion() == 0 || prgTcfind.getEstadoOperacion() == 1 || prgTcfind.getEstadoOperacion() == 2) {
                            tipoServicioDer = 0;
                            tipoVehiculoDer = 0;
                            lstPrgTcDerecha = null;
                            lstPrgTcDerecha = prgTcEjb.findByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellDer').clearFilters()");
//                            System.out.println("prgTcEjb.findByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 3 || prgTcfind.getEstadoOperacion() == 4 || prgTcfind.getEstadoOperacion() == 6) {
                            tipoServicioDer = 3;
                            tipoVehiculoDer = 0;
                            lstPrgTcDerecha = null;
                            lstPrgTcDerecha = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellDer').clearFilters()");
//                            System.out.println("prgTcEjb.findAdicionalesByFecha(fechaIzquierda)");
                        }
                        if (prgTcfind.getEstadoOperacion() == 5) {

                            nvoPrg = prgTcfind;
                            nvoPrg.setIdPrgTc(null);
                            nvoPrg.setEstadoOperacion(2);
                            nvoPrg.setObservaciones("Servicio recuperado por menor valor eliminado");
                            nvoPrg.setDistance(bg_aux);
                            nvoPrg.setCreado(new Date());
                            prgTcEjb.create(nvoPrg);

                            tipoServicioDer = 5;
                            tipoVehiculoDer = 0;
                            lstPrgTcDerecha = null;
                            lstPrgTcDerecha = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellDer').clearFilters()");
//                            System.out.println("prgTcEjb.findEliminadosByFecha(fechaIzquierda);");
                        }
                        MovilidadUtil.addSuccessMessage("Se ha restado " + bg_aux + " al registro indicado, nuevo valor " + subt + " ");
                        c_operacionDistancia = "";
                        resetSelected();
                        return;
                    }
                    if (newValue.contains("=")) {
                        String[] split = newValue.split("\\=");
                        BigDecimal bg_aux = new BigDecimal(split[1]);
                        if (prgTcfind.getEstadoOperacion() == 5) {
                            if (bg_aux.compareTo(prgTcfind.getDistance()) == 1) {
                                MovilidadUtil.addErrorMessage("Está intentando asignar un valor mayor a lo permitido");
                                return;
                            }
                        }
                        prgTcfind.setDistance(bg_aux);
                        prgTcfind.setModificado(new Date());
                        prgTcEjb.edit(prgTcfind);

                        if (prgTcfind.getEstadoOperacion() == 0 || prgTcfind.getEstadoOperacion() == 1 || prgTcfind.getEstadoOperacion() == 2) {
                            tipoServicioDer = 0;
                            tipoVehiculoDer = 0;
                            lstPrgTcDerecha = null;
                            lstPrgTcDerecha = prgTcEjb.findByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellDer').clearFilters()");
//                            System.out.println("prgTcEjb.findByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 3 || prgTcfind.getEstadoOperacion() == 4 || prgTcfind.getEstadoOperacion() == 6) {
                            tipoServicioDer = 3;
                            tipoVehiculoDer = 0;
                            lstPrgTcDerecha = null;
                            lstPrgTcDerecha = prgTcEjb.findAdicionalesByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellDer').clearFilters()");
//                            System.out.println("prgTcEjb.findAdicionalesByFecha(fechaIzquierda)");
                        }

                        if (prgTcfind.getEstadoOperacion() == 5) {

                            nvoPrg = prgTcfind;
                            nvoPrg.setIdPrgTc(null);
                            nvoPrg.setEstadoOperacion(2);
                            nvoPrg.setObservaciones("Servicio recuperado por menor valor eliminado");
                            nvoPrg.setDistance(oldValue.subtract(bg_aux));
                            nvoPrg.setCreado(new Date());
                            prgTcEjb.create(nvoPrg);

                            tipoServicioDer = 5;
                            tipoVehiculoDer = 0;
                            lstPrgTcDerecha = null;
                            lstPrgTcDerecha = prgTcEjb.findEliminadosByFecha(fechaIzquierda,
                                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                            current.executeScript("PF('cellDer').clearFilters()");
//                            System.out.println("prgTcEjb.findEliminadosByFecha(fechaIzquierda);");
                        }
                        MovilidadUtil.addSuccessMessage("Se ha cambiado el registro por: " + prgTcfind.getDistance());
                        c_operacionDistancia = "";
                        resetSelected();
                        return;
                    }
                    if (!newValue.contains("+") && !newValue.contains("-") && !newValue.contains("=")) {
                        MovilidadUtil.addErrorMessage("Falta operador aritmético (+,- ó =)");
                    }
                } else {
                    MovilidadUtil.addErrorMessage("Dato no valido");
                }
            } else {
                MovilidadUtil.addErrorMessage("Error al seleccionar campo");
            }
        } catch (Exception e) {
            System.out.println("Error en EditCell");
            System.out.println(e.getMessage());
            MovilidadUtil.addErrorMessage("Dato no valido");
        }
    }

    /**
     * Asigna distancia total recorrida de un BUS a un solo servicio en la tabla
     * izquierda
     */
    @Transactional
    public void asignarDistanciaIzquierda() {

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }
        double regRandom = Math.random();
        regRandom = regRandom * lstDistance.size() + 1;
        int res = (int) regRandom;

        if (b_Distancia == null) {
            MovilidadUtil.addErrorMessage("Ingrese distancia a agregar");
            return;
        }

        if (b_Distancia.compareTo(Util.CERO) == -1) {
            MovilidadUtil.addErrorMessage("No se puede ingresar valores negativos");
            return;
        }
        if (lstDistance.isEmpty()) {
            MovilidadUtil.addErrorMessage("Debe seleccionar los servicios a cambiar");
            return;
        }
        boolean b_aux;
        String c_Vehiculo = lstDistance.get(0).getIdVehiculo().getCodigo();
        for (PrgTc c : lstDistance) {
            b_aux = c.getIdVehiculo().getCodigo().equals(c_Vehiculo);
            if (!b_aux) {
                MovilidadUtil.addErrorMessage("La lista cuenta con vehículos distintos, por lo tanto no se puede realizar la acción");
                return;
            }
        }
        for (PrgTc t : lstDistance) {
            if (t.getIdPrgTc().equals(lstDistance.get(res - 1).getIdPrgTc())) {
                t.setDistance(b_Distancia);
                prgTcEjb.edit(t);
            } else {
                t.setDistance(Util.CERO);
                prgTcEjb.edit(t);
            }
        }

        b_Distancia = Util.CERO;
        b_DistanciaTm = Util.CERO;
        lstDistance.clear();
        getResumenKm();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "La(s) distancia(s) del bus: " + c_Vehiculo + " han sido actualizada(s) éxitosamente"));
    }

    /**
     * Asigna distancia total recorrida de un BUS a un solo servicio en la tabla
     * derecha
     */
    @Transactional
    public void asignarDistanciaDerecha() {

        if (validarEstadoConciliacion()) {
            PrimeFaces.current().ajax().update(":messages");
            MovilidadUtil.addErrorMessage("El día " + Util.dateFormat(fechaIzquierda) + " ya ha sido conciliado.");
            return;
        }

        double regRandom = Math.random();
        regRandom = regRandom * lstDistanceDerecha.size() + 1;
        int res = (int) regRandom;

        if (b_DistanciaDerecha == null) {
            MovilidadUtil.addErrorMessage("Ingrese distancia a agregar");
            return;
        }

        if (lstDistanceDerecha.isEmpty()) {
            MovilidadUtil.addErrorMessage("Debe seleccionar los servicios a cambiar");
            return;
        }

        if (b_DistanciaDerecha.compareTo(Util.CERO) == -1) {
            MovilidadUtil.addErrorMessage("No se puede ingresar valores negativos");
            return;
        }
        boolean b_aux;
        String c_Vehiculo = lstDistanceDerecha.get(0).getIdVehiculo().getCodigo();
        for (PrgTc c : lstDistanceDerecha) {
            b_aux = c.getIdVehiculo().getCodigo().equals(c_Vehiculo);
            if (!b_aux) {
                MovilidadUtil.addErrorMessage("La lista cuenta con vehículos distintos, por lo tanto no se puede realizar la acción");
                return;
            }
        }
        for (PrgTc t : lstDistanceDerecha) {
            if (t.getIdPrgTc().equals(lstDistanceDerecha.get(res - 1).getIdPrgTc())) {
                t.setDistance(b_DistanciaDerecha);
                prgTcEjb.edit(t);
            } else {
                t.setDistance(Util.CERO);
                prgTcEjb.edit(t);
            }
        }
        b_DistanciaDerecha = Util.CERO;
        b_DistanciaTmDer = Util.CERO;
        lstDistanceDerecha.clear();
        getResumenKm();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "La(s) distancia(s) del bus: " + c_Vehiculo + " han sido actualizada(s) éxitosamente"));
    }

    /**
     * Realiza la búsqueda de un vehículo POR código al adicionar/editar un
     * servicio
     */
    public void cargarVehiculo() {
        try {
            if (!(codVehiculo.equals("") && codVehiculo.isEmpty())) {
                Vehiculo vehiculoAux = vehiculoEjb.getVehiculoCodigo(codVehiculo.toUpperCase());
                if (vehiculoAux != null) {
                    if (selected != null) {
                        if (vehiculoAux.getIdVehiculoTipo().getIdVehiculoTipo() == 2
                                && empleado.getIdEmpleadoCargo().getIdEmpleadoTipoCargo() == 28) {
                            MovilidadUtil.addAdvertenciaMessage("El operador no está certificado para operar Biarticulado");
                            return;
                        }
                    }
                    this.vehiculo = vehiculoAux;
                    getServbusString();
                    PrimeFaces.current().ajax().update("frmAddServicio:servbus");
                    MovilidadUtil.addSuccessMessage("Vehículo válido");
                    PrimeFaces.current().ajax().update("btnGuardar");
                } else {
                    PrimeFaces.current().ajax().update("frmAddServicio:servbus");
                    MovilidadUtil.addErrorMessage("Vehículo NO válido");
                    PrimeFaces.current().ajax().update("btnGuardar");
                    this.vehiculo = null;
                    prgTc.setServbus(null);
                }
            }
            if (codVehiculo.equals("")) {
                MovilidadUtil.addErrorMessage("Debe ingresar código de vehículo");
                this.vehiculo = null;
                prgTc.setServbus(null);
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error de sistema");
        }
    }

    public PrgTcResumen getPrgTcResumen() {
        return prgTcResumen;
    }

    public void setPrgTcResumen(PrgTcResumen prgTcResumen) {
        this.prgTcResumen = prgTcResumen;
    }

    public PrgTcResumenFacadeLocal getPrgTcResumenEjb() {
        return prgTcResumenEjb;
    }

    public void setPrgTcResumenEjb(PrgTcResumenFacadeLocal prgTcResumenEjb) {
        this.prgTcResumenEjb = prgTcResumenEjb;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public List<PrgTc> getLstPrgTcIzquierda() {
        return lstPrgTcIzquierda;
    }

    public void setLstPrgTcIzquierda(List<PrgTc> lstPrgTcIzquierda) {
        this.lstPrgTcIzquierda = lstPrgTcIzquierda;
    }

    public List<PrgTc> getLstPrgTcDerecha() {
        return lstPrgTcDerecha;
    }

    public void setLstPrgTcDerecha(List<PrgTc> lstPrgTcDerecha) {
        this.lstPrgTcDerecha = lstPrgTcDerecha;
    }

    public Date getFechaIzquierda() {
        return fechaIzquierda;
    }

    public void setFechaIzquierda(Date fechaIzquierda) {
        this.fechaIzquierda = fechaIzquierda;
    }

    public Date getFechaDerecha() {
        return fechaDerecha;
    }

    public void setFechaDerecha(Date fechaDerecha) {
        this.fechaDerecha = fechaDerecha;
    }

    public int getTipoVehiculoIzq() {
        return tipoVehiculoIzq;
    }

    public void setTipoVehiculoIzq(int tipoVehiculoIzq) {
        this.tipoVehiculoIzq = tipoVehiculoIzq;
    }

    public int getTipoServicioIzq() {
        return tipoServicioIzq;
    }

    public void setTipoServicioIzq(int tipoServicioIzq) {
        this.tipoServicioIzq = tipoServicioIzq;
    }

    public int getCodVehiculoIzq() {
        return codVehiculoIzq;
    }

    public void setCodVehiculoIzq(int codVehiculoIzq) {
        this.codVehiculoIzq = codVehiculoIzq;
    }

    public int getTipoVehiculoDer() {
        return tipoVehiculoDer;
    }

    public void setTipoVehiculoDer(int tipoVehiculoDer) {
        this.tipoVehiculoDer = tipoVehiculoDer;
    }

    public int getTipoServicioDer() {
        return tipoServicioDer;
    }

    public void setTipoServicioDer(int tipoServicioDer) {
        this.tipoServicioDer = tipoServicioDer;
    }

    public int getCodVehiculoDer() {
        return codVehiculoDer;
    }

    public void setCodVehiculoDer(int codVehiculoDer) {
        this.codVehiculoDer = codVehiculoDer;
    }

    public PrgTc getSelected() {
        return selected;
    }

    public void setSelected(PrgTc selected) {
        this.selected = selected;
    }

    public String getHoraFinString() {
        return horaFinString;
    }

    public void setHoraFinString(String horaFinString) {
        this.horaFinString = horaFinString;
    }

    public String getHoraInicioString() {
        return horaInicioString;
    }

    public void setHoraInicioString(String horaInicioString) {
        this.horaInicioString = horaInicioString;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public PrgTarea getServicio() {
        return servicio;
    }

    public void setServicio(PrgTarea servicio) {
        this.servicio = servicio;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public List<PrgTarea> getLstServicios() {
        return lstServicios;
    }

    public void setLstServicios(List<PrgTarea> lstServicios) {
        this.lstServicios = lstServicios;
    }

    public PrgRoute getRuta() {
        return ruta;
    }

    public void setRuta(PrgRoute ruta) {
        this.ruta = ruta;
    }

    public List<PrgRoute> getLstRutas() {
        return lstRutas;
    }

    public void setLstRutas(List<PrgRoute> lstRutas) {
        this.lstRutas = lstRutas;
    }

    public PrgTcResponsable getResponsable() {
        return responsable;
    }

    public void setResponsable(PrgTcResponsable responsable) {
        this.responsable = responsable;
    }

    public List<PrgTcResponsable> getLstResponsable() {
        if (lstResponsable == null) {
            lstResponsable = new ArrayList<>();
            lstResponsable = prgTcResponsableEjb.getPrgResponsables();
        }
        return lstResponsable;
    }

    public void setLstResponsable(List<PrgTcResponsable> lstResponsable) {
        this.lstResponsable = lstResponsable;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public int getI_codResponsable() {
        return i_codResponsable;
    }

    public void setI_codResponsable(int i_codResponsable) {
        this.i_codResponsable = i_codResponsable;
    }

    public BigDecimal getB_Adicionales() {
        return b_Adicionales;
    }

    public void setB_Adicionales(BigDecimal b_Adicionales) {
        this.b_Adicionales = b_Adicionales;
    }

    public BigDecimal getB_Eliminados() {
        return b_Eliminados;
    }

    public void setB_Eliminados(BigDecimal b_Eliminados) {
        this.b_Eliminados = b_Eliminados;
    }

    public BigDecimal getB_Total() {
        return b_Total;
    }

    public void setB_Total(BigDecimal b_Total) {
        this.b_Total = b_Total;
    }

    public int getI_fromStop() {
        return i_fromStop;
    }

    public void setI_fromStop(int i_fromStop) {
        this.i_fromStop = i_fromStop;
    }

    public int getI_toStop() {
        return i_toStop;
    }

    public void setI_toStop(int i_toStop) {
        this.i_toStop = i_toStop;
    }

    public Integer getI_idTarea() {
        return i_idTarea;
    }

    public void setI_idTarea(Integer i_idTarea) {
        this.i_idTarea = i_idTarea;
    }

    public boolean isFlagRuta() {
        return flagRuta;
    }

    public void setFlagRuta(boolean flagRuta) {
        this.flagRuta = flagRuta;
    }

    public boolean isFlagPInicioPFin() {
        return flagPInicioPFin;
    }

    public void setFlagPInicioPFin(boolean flagPInicioPFin) {
        this.flagPInicioPFin = flagPInicioPFin;
    }

    public int getI_idRuta() {
        return i_idRuta;
    }

    public void setI_idRuta(int i_idRuta) {
        this.i_idRuta = i_idRuta;
    }

    public PrgPattern getI_idPuntoIni() {
        return i_idPuntoIni;
    }

    public void setI_idPuntoIni(PrgPattern i_idPuntoIni) {
        this.i_idPuntoIni = i_idPuntoIni;
    }

    public PrgStopPoint getI_idPuntoIniStopPoint() {
        return i_idPuntoIniStopPoint;
    }

    public void setI_idPuntoIniStopPoint(PrgStopPoint i_idPuntoIniStopPoint) {
        this.i_idPuntoIniStopPoint = i_idPuntoIniStopPoint;
    }

    public PrgStopPoint getI_idPuntoFinStopPoint() {
        return i_idPuntoFinStopPoint;
    }

    public void setI_idPuntoFinStopPoint(PrgStopPoint i_idPuntoFinStopPoint) {
        this.i_idPuntoFinStopPoint = i_idPuntoFinStopPoint;
    }

    public PrgPattern getI_idPuntoFin() {
        return i_idPuntoFin;
    }

    public void setI_idPuntoFin(PrgPattern i_idPuntoFin) {
        this.i_idPuntoFin = i_idPuntoFin;
    }

    public Integer getI_idPuntoIniInt() {
        return i_idPuntoIniInt;
    }

    public void setI_idPuntoIniInt(Integer i_idPuntoIniInt) {
        this.i_idPuntoIniInt = i_idPuntoIniInt;
    }

    public Integer getI_idPuntoFinInt() {
        return i_idPuntoFinInt;
    }

    public void setI_idPuntoFinInt(Integer i_idPuntoFinInt) {
        this.i_idPuntoFinInt = i_idPuntoFinInt;
    }

    public String getPunto_inicioString() {
        return punto_inicioString;
    }

    public void setPunto_inicioString(String punto_inicioString) {
        this.punto_inicioString = punto_inicioString;
    }

    public String getPunto_finString() {
        return punto_finString;
    }

    public void setPunto_finString(String punto_finString) {
        this.punto_finString = punto_finString;
    }

    public int getComponente() {
        return componente;
    }

    public void setComponente(int componente) {
        this.componente = componente;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<PrgPattern> getListPrgPattern() {
        return listPrgPattern;
    }

    public void setListPrgPattern(List<PrgPattern> listPrgPattern) {
        this.listPrgPattern = listPrgPattern;
    }

    public List<PrgStopPoint> getListPrgStopPoint() {
        return listPrgStopPoint;
    }

    public void setListPrgStopPoint(List<PrgStopPoint> listPrgStopPoint) {
        this.listPrgStopPoint = listPrgStopPoint;
    }

    public PrgStopPoint getPrgStopPoint() {
        return prgStopPoint;
    }

    public void setPrgStopPoint(PrgStopPoint prgStopPoint) {
        this.prgStopPoint = prgStopPoint;
    }

    public PrgTc getPrgTcVacOrVaccom() {
        return prgTcVacOrVaccom;
    }

    public void setPrgTcVacOrVaccom(PrgTc prgTcVacOrVaccom) {
        this.prgTcVacOrVaccom = prgTcVacOrVaccom;
    }

    public List<PrgTc> getLstDistance() {
        return lstDistance;
    }

    public void setLstDistance(List<PrgTc> lstDistance) {
        this.lstDistance = lstDistance;
    }

    public BigDecimal getB_Total_Art() {
        return b_Total_Art;
    }

    public void setB_Total_Art(BigDecimal b_Total_Art) {
        this.b_Total_Art = b_Total_Art;
    }

    public BigDecimal getB_Total_Bi() {
        return b_Total_Bi;
    }

    public void setB_Total_Bi(BigDecimal b_Total_Bi) {
        this.b_Total_Bi = b_Total_Bi;
    }

    public BigDecimal getB_Distancia() {
        return b_Distancia;
    }

    public void setB_Distancia(BigDecimal b_Distancia) {
        this.b_Distancia = b_Distancia;
    }

    public List<PrgTc> getLstDistanceDerecha() {
        return lstDistanceDerecha;
    }

    public void setLstDistanceDerecha(List<PrgTc> lstDistanceDerecha) {
        this.lstDistanceDerecha = lstDistanceDerecha;
    }

    public BigDecimal getB_DistanciaDerecha() {
        return b_DistanciaDerecha;
    }

    public void setB_DistanciaDerecha(BigDecimal b_DistanciaDerecha) {
        this.b_DistanciaDerecha = b_DistanciaDerecha;
    }

    public boolean isFlagConciliacion() {
        return flagConciliacion;
    }

    public void setFlagConciliacion(boolean flagConciliacion) {
        this.flagConciliacion = flagConciliacion;
    }

    public String getCodVehiculo() {
        return codVehiculo;
    }

    public void setCodVehiculo(String codVehiculo) {
        this.codVehiculo = codVehiculo;
    }

    public String getC_operacionDistancia() {
        return c_operacionDistancia;
    }

    public void setC_operacionDistancia(String c_operacionDistancia) {
        this.c_operacionDistancia = c_operacionDistancia;
    }

    public BigDecimal getB_DistanciaTm() {
        return b_DistanciaTm;
    }

    public void setB_DistanciaTm(BigDecimal b_DistanciaTm) {
        this.b_DistanciaTm = b_DistanciaTm;
    }

    public BigDecimal getB_DistanciaTmDer() {
        return b_DistanciaTmDer;
    }

    public void setB_DistanciaTmDer(BigDecimal b_DistanciaTmDer) {
        this.b_DistanciaTmDer = b_DistanciaTmDer;
    }

    public List<PrgTcResumen> getLstResumenSem() {
        return lstResumenSem;
    }

    public void setLstResumenSem(List<PrgTcResumen> lstResumenSem) {
        this.lstResumenSem = lstResumenSem;
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getB2() {
        return b2;
    }

    public void setB2(String b2) {
        this.b2 = b2;
    }

    public String getB3() {
        return b3;
    }

    public void setB3(String b3) {
        this.b3 = b3;
    }

    public String getB4() {
        return b4;
    }

    public void setB4(String b4) {
        this.b4 = b4;
    }

    public String getB5() {
        return b5;
    }

    public void setB5(String b5) {
        this.b5 = b5;
    }

    public String getB6() {
        return b6;
    }

    public void setB6(String b6) {
        this.b6 = b6;
    }

    public String getB7() {
        return b7;
    }

    public void setB7(String b7) {
        this.b7 = b7;
    }

    public BigDecimal getB_IPH_ComercialArt() {
        return b_IPH_ComercialArt;
    }

    public void setB_IPH_ComercialArt(BigDecimal b_IPH_ComercialArt) {
        this.b_IPH_ComercialArt = b_IPH_ComercialArt;
    }

    public BigDecimal getB_IPH_ComercialBi() {
        return b_IPH_ComercialBi;
    }

    public void setB_IPH_ComercialBi(BigDecimal b_IPH_ComercialBi) {
        this.b_IPH_ComercialBi = b_IPH_ComercialBi;
    }

    public BigDecimal getB_PRG_ComercialArt() {
        return b_PRG_ComercialArt;
    }

    public void setB_PRG_ComercialArt(BigDecimal b_PRG_ComercialArt) {
        this.b_PRG_ComercialArt = b_PRG_ComercialArt;
    }

    public BigDecimal getB_PRG_ComercialBi() {
        return b_PRG_ComercialBi;
    }

    public void setB_PRG_ComercialBi(BigDecimal b_PRG_ComercialBi) {
        this.b_PRG_ComercialBi = b_PRG_ComercialBi;
    }

    public BigDecimal getB_IPH_HlpArt() {
        return b_IPH_HlpArt;
    }

    public void setB_IPH_HlpArt(BigDecimal b_IPH_HlpArt) {
        this.b_IPH_HlpArt = b_IPH_HlpArt;
    }

    public BigDecimal getB_IPH_HlpBi() {
        return b_IPH_HlpBi;
    }

    public void setB_IPH_HlpBi(BigDecimal b_IPH_HlpBi) {
        this.b_IPH_HlpBi = b_IPH_HlpBi;
    }

    public BigDecimal getB_PRG_HlpArt() {
        return b_PRG_HlpArt;
    }

    public void setB_PRG_HlpArt(BigDecimal b_PRG_HlpArt) {
        this.b_PRG_HlpArt = b_PRG_HlpArt;
    }

    public BigDecimal getB_PRG_HlpBi() {
        return b_PRG_HlpBi;
    }

    public void setB_PRG_HlpBi(BigDecimal b_PRG_HlpBi) {
        this.b_PRG_HlpBi = b_PRG_HlpBi;
    }

    public BigDecimal getB_Vacio_IPH_Art() {
        return b_Vacio_IPH_Art;
    }

    public void setB_Vacio_IPH_Art(BigDecimal b_Vacio_IPH_Art) {
        this.b_Vacio_IPH_Art = b_Vacio_IPH_Art;
    }

    public BigDecimal getB_Vacio_IPH_Bi() {
        return b_Vacio_IPH_Bi;
    }

    public void setB_Vacio_IPH_Bi(BigDecimal b_Vacio_IPH_Bi) {
        this.b_Vacio_IPH_Bi = b_Vacio_IPH_Bi;
    }

    public BigDecimal getB_Res_Art() {
        return b_Res_Art;
    }

    public void setB_Res_Art(BigDecimal b_Res_Art) {
        this.b_Res_Art = b_Res_Art;
    }

    public BigDecimal getB_Res_Bi() {
        return b_Res_Bi;
    }

    public void setB_Res_Bi(BigDecimal b_Res_Bi) {
        this.b_Res_Bi = b_Res_Bi;
    }

    public BigDecimal getB_Preconcialiado() {
        return b_Preconcialiado;
    }

    public void setB_Preconcialiado(BigDecimal b_Preconcialiado) {
        this.b_Preconcialiado = b_Preconcialiado;
    }

    public List<PrgTc> getLstPreconcialiado() {
        return lstPreconcialiado;
    }

    public void setLstPreconcialiado(List<PrgTc> lstPreconcialiado) {
        this.lstPreconcialiado = lstPreconcialiado;
    }

    public BigDecimal getB_Res_Art_NoOpt() {
        return b_Res_Art_NoOpt;
    }

    public void setB_Res_Art_NoOpt(BigDecimal b_Res_Art_NoOpt) {
        this.b_Res_Art_NoOpt = b_Res_Art_NoOpt;
    }

    public BigDecimal getB_Res_Bi_NoOpt() {
        return b_Res_Bi_NoOpt;
    }

    public void setB_Res_Bi_NoOpt(BigDecimal b_Res_Bi_NoOpt) {
        this.b_Res_Bi_NoOpt = b_Res_Bi_NoOpt;
    }

    public BigDecimal getB_TotalResSemana() {
        return b_TotalResSemana;
    }

    public void setB_TotalResSemana(BigDecimal b_TotalResSemana) {
        this.b_TotalResSemana = b_TotalResSemana;
    }

    public BigDecimal getB_Fac_Art() {
        return b_Fac_Art;
    }

    public void setB_Fac_Art(BigDecimal b_Fac_Art) {
        this.b_Fac_Art = b_Fac_Art;
    }

    public BigDecimal getB_Fac_Bi() {
        return b_Fac_Bi;
    }

    public void setB_Fac_Bi(BigDecimal b_Fac_Bi) {
        this.b_Fac_Bi = b_Fac_Bi;
    }

}
