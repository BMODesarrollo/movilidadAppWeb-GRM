/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.AccDesplazaADTO;
import com.movilidad.dto.AccidentDTO;
import com.movilidad.dto.EmployeeDTO;
import com.movilidad.dto.MantenimentDTO;
import com.movilidad.dto.OthersIssueDTO;
import com.movilidad.dto.ParrillaDTO;
import com.movilidad.dto.ServicesParrillaDTO;
import com.movilidad.dto.StrandedDTO;
import com.movilidad.dto.TpConteoDTO;
import com.movilidad.dto.VehicleDTO;
import com.movilidad.ejb.AccDesplazaAFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.TecnicoPatioFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.AccDesplazaA;
import com.movilidad.model.Accidente;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;

/**
 *
 * @author solucionesit
 */
@Named(value = "parrillaEstadoVehiculoBean")
@ViewScoped
public class ParrillaEstadoVehiculoBean implements Serializable {

    /**
     * Creates a new instance of ParrillaEstadoVehiculoBean
     */
    public ParrillaEstadoVehiculoBean() {
    }

    @PostConstruct
    public void init() {
        barModel = new BarChartModel();
    }

    @EJB
    private VehiculoFacadeLocal vhclEJB;
    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @EJB
    private AccidenteFacadeLocal accidenteEJB;
    @EJB
    private AccDesplazaAFacadeLocal accDesplazaAEJB;
    @EJB
    private TecnicoPatioFacadeLocal tecnicoPatioEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private MapGeoBean mapGeoBean;
    @Inject
    private EmpleadoListJSFManagedBean empleadoListJSFManagedBean;

    private List<AccDesplazaA> lstAccDesplazamientos;
    private List<TpConteoDTO> lstTpConteoDTO;

    private ServicesParrillaDTO servicesParrillaDTO;
    private Integer idAccDesplaza;
    private TreeNode root;
    private PrgTc prgTc;
//    private MindmapNode rootMindmapNode;
    private boolean viewInfo = false;
    private boolean viewDetail = false;
    private boolean viewAsignado = false;
    private boolean viewEnPatio = false;
    private boolean viewVarados = false;
    private boolean viewAccidente = false;
    private boolean viewMttoPatio = false;
    private boolean viewOtro = false;
    private boolean flagDesplazamiento = false;
    private String tituloOpcion = "";

    public void onBack() {
        viewDetail = true;
        viewEnPatio = false;
        viewVarados = false;
        viewAccidente = false;
        viewMttoPatio = false;
        viewAsignado = false;
        viewOtro = false;
    }

    public void onViewEnPatio() {
        tituloOpcion = "Vehículos en Patio";
        viewDetail = false;
        viewEnPatio = true;
    }

    public void onViewVarados() {
        tituloOpcion = "Vehículos Inoperativos Varados";
        viewDetail = false;
        viewVarados = true;
    }

    public void onViewAccidente() {
        tituloOpcion = "Vehículos Inoperativos con Accidente";
        viewDetail = false;
        viewAccidente = true;
    }

    public void onViewMttoPatio() {
        tituloOpcion = "Vehículos Inoperativos en Patio";
        viewDetail = false;
        viewMttoPatio = true;
    }

    public void onViewOtro() {
        tituloOpcion = "Vehículos Inoperativos en URI";
        viewDetail = false;
        viewOtro = true;
    }

    public void onViewAsignado() {
        tituloOpcion = "Vehículos Asignados";
        viewDetail = false;
        viewAsignado = true;
    }

    public void onHandleFlagDesplazamiento(AccidentDTO accidenteDTO) {
        flagDesplazamiento = !flagDesplazamiento;
        idAccDesplaza = accidenteDTO.getAccDesplazaA() != null
                ? accidenteDTO.getAccDesplazaA().getIdAccDesplazaA() : null;
    }

    private BarChartModel barModel;

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries salidas = new ChartSeries();
        salidas.setLabel("Salidas");
        ChartSeries entradas = new ChartSeries();
        entradas.setLabel("Entradas");
        ChartSeries partidas = new ChartSeries();
        partidas.setLabel("Partidas");

        for (int i = 0; i < 24; i++) {
            String key = String.format("%d - %d", i, i + 1);
            String startTime = String.format("%02d:00:00", i);
            String endTime = String.format("%02d:59:59", i);

            List<TpConteoDTO> conteos = tecnicoPatioEJB.findCounterTpTime(
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), startTime, endTime);

            for (TpConteoDTO conteo : conteos) {
                switch (conteo.getDepotType()) {
                    case 0: // Entradas
                        entradas.set(key, conteo.getConfirmado());
                        break;
                    case 1: // Salidas
                        salidas.set(key, conteo.getConfirmado());
                        break;
                    case 2: // Partidas
                        partidas.set(key, conteo.getConfirmado());
                        break;
                }
            }
        }

        model.addSeries(entradas);
        model.addSeries(salidas);
        model.addSeries(partidas);

        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Franja Horaria");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Buses");
        yAxis.setMin(0);
        yAxis.setMax(200);
        yAxis.setTickFormat("%d");

        // Configura el formato del tooltip para mostrar solo el valor
        barModel.setDatatipFormat("%2$d");
    }

    // Getters para barModel y horizontalBarModel
    public BarChartModel getBarModel() {
        return barModel;
    }

    public void cargarParrilla() {
        onBack();
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addAdvertenciaMessage("Seleccione unidad funcional");
            return;
        }
        cargarDatosParrilla();
//        crearTreeNode();
        MovilidadUtil.openModal("parrilla_vehiculo_wv");
    }

    void cargarDatosParrilla() {
        cargarListaDesplazamientos();
        List<ParrillaDTO> listParrillaDTO = vhclEJB.findStatesVehicleServices(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        Supplier<Stream<ParrillaDTO>> supplierDTO = () -> listParrillaDTO.stream();
        // vehiculos operativos
        Predicate<ParrillaDTO> isOperativo = pdto -> pdto.getEstado().equals(ConstantsUtil.ON_INT);
        // vehiculos en patio
        Predicate<ParrillaDTO> inPatio = pdto -> pdto.getUbicacion() != null
                && pdto.getUbicacion().equals(ConstantsUtil.OFF_INT);
        // varados
        Predicate<ParrillaDTO> isVarado = pdto -> pdto.getIsVarado().equals(ConstantsUtil.ON_INT);
        // accidente
        Predicate<ParrillaDTO> isAccidente = pdto -> pdto.getIsAccidente().equals(ConstantsUtil.ON_INT);
        // mtto patio
        Predicate<ParrillaDTO> isMttoPatio = pdto -> pdto.getIsMttoPatio().equals(ConstantsUtil.ON_INT);

        Predicate<ParrillaDTO> isUri = pdto -> pdto.getIdUri().equals(ConstantsUtil.ON_INT);
        // --------------------------------------------------------------------//
        // OBJETO FINAL
        // OPERATIVO
        // vehiculos asignados operativos
        List<VehicleDTO> vehiculosAsignados = supplierDTO.get().filter(isOperativo.and(inPatio.negate())).map(va  -> {
            return new VehicleDTO(va.getCodigo(), va.getTipologia());
        }).collect(Collectors.toList());

        List<VehicleDTO> vehiculosPatios = supplierDTO.get().filter(isOperativo.and(inPatio)).map(vp -> {
            return new VehicleDTO(vp.getCodigo(), vp.getTipologia());
        }).collect(Collectors.toList());
        // INOPERATIVO
        // vehiculos varados
        List<StrandedDTO> vehiculosVarados = supplierDTO.get().filter(isVarado.and(isOperativo.negate())).map(vv -> {
            StrandedDTO strandedDTO = new StrandedDTO();
            strandedDTO.setEmpleado(MovilidadUtil.stringIsEmpty(vv.getCodigoTm()) ? null
                    : new EmployeeDTO(vv.getNombreEmpleado(), vv.getTelefono(), vv.getCodigoTm()));
            strandedDTO.setVehiculo(new VehicleDTO(vv.getCodigo(), vv.getTipologia()));
            strandedDTO.setFechaHoraEvento(
                    MovilidadUtil.stringIsEmpty(
                            vv.getFechaHoraEventoVarado()) ? null
                    : vv.getFechaHoraEventoVarado());
            strandedDTO.setNombreSistema(vv.getCausaFalla());
            return strandedDTO;
        }).collect(Collectors.toList());
        // accidentes
        List<AccidentDTO> vehiculosAccidente = supplierDTO.get().filter(isAccidente.and(isOperativo.negate()))
                .map(va  -> {
                    AccidentDTO accidentDTO = new AccidentDTO();
                    accidentDTO.setIdAccidente(va.getIdAccidente());
                    accidentDTO.setEmpleado(MovilidadUtil.stringIsEmpty(va.getCodigoTm()) ? null
                            : new EmployeeDTO(va.getNombreEmpleado(), va.getTelefono(), va.getCodigoTm()));
                    accidentDTO.setFechaHoraEvento(
                            MovilidadUtil.stringIsEmpty(
                                    va.getFechaHoraEventoAccidente()) ? null
                            : va.getFechaHoraEventoAccidente());
                    accidentDTO.setVehiculo(new VehicleDTO(va.getCodigo(), va.getTipologia()));
                    accidentDTO.setAccDesplazaA(new AccDesplazaADTO(va.getIdAccDesplazaA(), va.getDesplazaA()));
                    accidentDTO.setAsistido(va.getIsAccAsistido().equals(ConstantsUtil.ON_INT));
                    accidentDTO.setRuta(va.getRuta());
                    accidentDTO.setTipoEvento(va.getTipoNovedad());
                    return accidentDTO;
                }).collect(Collectors.toList());
        // otros, no son ni varados, ni accidentes
        List<OthersIssueDTO> vehiculoOtros = supplierDTO.get()
                .filter(isUri).map(vo -> {
            OthersIssueDTO othersIssueDTO = new OthersIssueDTO();
            othersIssueDTO.setEmpleado(MovilidadUtil.stringIsEmpty(vo.getCodigoTm()) ? null
                    : new EmployeeDTO(vo.getNombreEmpleado(), vo.getTelefono(), vo.getCodigoTm()));
            othersIssueDTO.setVehiculo(new VehicleDTO(vo.getCodigo(), vo.getTipologia()));
            othersIssueDTO.setDiasInoperativos(vo.getDiasInopetativos());
            othersIssueDTO.setFechaHoraEvento(
                    MovilidadUtil.stringIsEmpty(
                            vo.getFechaHoraEventoUri()) ? null
                    : vo.getFechaHoraEventoUri());
            othersIssueDTO.setTipoEvento(vo.getTipoNovedad());
            return othersIssueDTO;
        }).collect(Collectors.toList());
        // mantenimiento patio
        List<MantenimentDTO> vehiculosMttoPatio = supplierDTO.get().filter(isMttoPatio).map(vm -> {
            MantenimentDTO mantenimentDTO = new MantenimentDTO();
            mantenimentDTO.setVehiculo(new VehicleDTO(vm.getCodigo(), vm.getTipologia()));
            mantenimentDTO.setNombreSistema(vm.getCausaFalla());
            mantenimentDTO.setDiasInoperativos(vm.getDiasInopetativos());
            return mantenimentDTO;
        }).collect(Collectors.toList());

        // objeto final
        servicesParrillaDTO = new ServicesParrillaDTO();
        servicesParrillaDTO.setTotalOperativos(vehiculosAsignados.size() + vehiculosPatios.size());
        servicesParrillaDTO.setTotalAsignado(vehiculosAsignados.size());
        servicesParrillaDTO.setTotalEnPatio(vehiculosPatios.size());
        servicesParrillaDTO.setVehiculosAsignados(vehiculosAsignados);
        servicesParrillaDTO.setVehiculosEnPatios(vehiculosPatios);
        servicesParrillaDTO
                .setTotalInoperativos(vehiculosVarados.size() + vehiculosAccidente.size() + vehiculosMttoPatio.size());
        servicesParrillaDTO.setTotalVarado(vehiculosVarados.size());
        servicesParrillaDTO.setVehiculosVarados(vehiculosVarados);
        servicesParrillaDTO.setTotalAccidente(vehiculosAccidente.size());
        servicesParrillaDTO.setVehiculosAccidente(vehiculosAccidente);
        servicesParrillaDTO.setTotalMttoPatio(vehiculosMttoPatio.size());
        servicesParrillaDTO.setVehiculosMttoPatio(vehiculosMttoPatio);
        servicesParrillaDTO.setTotalOtros(vehiculoOtros.size());
        servicesParrillaDTO.setVehiculoOtros(vehiculoOtros);
        viewInfo = true;
    }

    void crearTreeNode() {

        root = new DefaultTreeNode("ESTADO", null);
        root.setExpanded(true);
        TreeNode nodeOperativos = new DefaultTreeNode("OPERATIVOS-> " + servicesParrillaDTO.getTotalOperativos(), root);
        nodeOperativos.setExpanded(true);
        TreeNode nodeAsignados = new DefaultTreeNode("ASIGNADO-> " + servicesParrillaDTO.getTotalAsignado(), nodeOperativos);
        nodeAsignados.setExpanded(true);
        TreeNode nodePatio = new DefaultTreeNode("PATIO-> " + servicesParrillaDTO.getTotalEnPatio(), nodeOperativos);
        nodePatio.setExpanded(true);

        TreeNode nodeInoperativos = new DefaultTreeNode("INOPERATIVOS-> " + servicesParrillaDTO.getTotalInoperativos(), root);
        nodeInoperativos.setExpanded(true);

        TreeNode nodeVarado = new DefaultTreeNode("VARADO-> " + servicesParrillaDTO.getTotalVarado(), nodeInoperativos);
        nodeVarado.setExpanded(true);
        TreeNode nodeAccidente = new DefaultTreeNode("ACCIDENTE-> " + servicesParrillaDTO.getTotalAccidente(), nodeInoperativos);
        nodeAccidente.setExpanded(true);
        TreeNode nodeMttoPatio = new DefaultTreeNode("MTTO PATIO-> " + servicesParrillaDTO.getTotalMttoPatio(), nodeInoperativos);
        nodeMttoPatio.setExpanded(true);
        TreeNode nodeOtros = new DefaultTreeNode("OTROS-> " + servicesParrillaDTO.getTotalOtros(), nodeInoperativos);
        nodeOtros.setExpanded(true);
    }

    public void agregarDesplazamientoAccidente(AccidentDTO accidentDTO) {
        Accidente accidente = accidenteEJB.find(
                accidentDTO.getIdAccidente()
        );

        if (accidente == null) {
            MovilidadUtil.addErrorMessage("No se encontró un accidente con los datos proporcionados");
            return;
        }

        //Se actualiza accidente con el desplazamiento seleccionado
        accidente.setIdAccDesplazaA(
                idAccDesplaza != null ? accDesplazaAEJB.find(idAccDesplaza)
                        : null
        );
        accidenteEJB.edit(accidente);
        flagDesplazamiento = false;
        cargarDatosParrilla();
        MovilidadUtil.addSuccessMessage("Desplazamiento agregado con éxito");
    }

    public void verInfoOperador(String codVehiculo) {
        prgTc = prgTcEJB.currentServiceByCodeVehicle(codVehiculo);
        if (prgTc != null) {
            empleadoListJSFManagedBean.setEmpl(prgTc.getIdEmpleado());
            MovilidadUtil.openModal("parrilla_operador_wv");
        }
    }

    public void modalEstadoFlota() {
        cargarEstadoFlota();
        MovilidadUtil.openModal("estadotpv");
    }

    public void cargarEstadoFlota() {
        //lstTpConteoDTO = tecnicoPatioEJB.findCounterTpTime(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        createBarModels();
        MovilidadUtil.updateComponent("frmEstadoFlota");
    }

    public void verUbicacion(String codVehiculo) {
        if (codVehiculo == null) {
            MovilidadUtil.addErrorMessage("Debe selecionar un servicio de la tabla.");
            return;
        }
        if (codVehiculo.isEmpty()) {
            MovilidadUtil.addErrorMessage("El servicio nmo tiene vehículo asignado.");
            return;
        }
        System.out.println("codVehiculo->" + codVehiculo);
        mapGeoBean.openMapGeo(codVehiculo);
    }

    void cargarListaDesplazamientos() {
        idAccDesplaza = null;
        lstAccDesplazamientos = accDesplazaAEJB.findByEstadoReg();
    }

    public boolean isViewInfo() {
        return viewInfo;
    }

    public void setViewInfo(boolean viewInfo) {
        this.viewInfo = viewInfo;
    }

    public ServicesParrillaDTO getServicesParrillaDTO() {
        return servicesParrillaDTO;
    }

    public void setServicesParrillaDTO(ServicesParrillaDTO servicesParrillaDTO) {
        this.servicesParrillaDTO = servicesParrillaDTO;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

//    public MindmapNode getRootMindmapNode() {
//        return rootMindmapNode;
//    }
//
//    public void setRootMindmapNode(MindmapNode rootMindmapNode) {
//        this.rootMindmapNode = rootMindmapNode;
//    }
    public boolean isViewDetail() {
        return viewDetail;
    }

    public void setViewDetail(boolean viewDetail) {
        this.viewDetail = viewDetail;
    }

    public boolean isViewEnPatio() {
        return viewEnPatio;
    }

    public void setViewEnPatio(boolean viewEnPatio) {
        this.viewEnPatio = viewEnPatio;
    }

    public boolean isViewVarados() {
        return viewVarados;
    }

    public void setViewVarados(boolean viewVarados) {
        this.viewVarados = viewVarados;
    }

    public boolean isViewAccidente() {
        return viewAccidente;
    }

    public void setViewAccidente(boolean viewAccidente) {
        this.viewAccidente = viewAccidente;
    }

    public boolean isViewMttoPatio() {
        return viewMttoPatio;
    }

    public void setViewMttoPatio(boolean viewMttoPatio) {
        this.viewMttoPatio = viewMttoPatio;
    }

    public boolean isViewOtro() {
        return viewOtro;
    }

    public void setViewOtro(boolean viewOtro) {
        this.viewOtro = viewOtro;
    }

    public boolean isViewAsignado() {
        return viewAsignado;
    }

    public void setViewAsignado(boolean viewAsignado) {
        this.viewAsignado = viewAsignado;
    }

    public String getTituloOpcion() {
        return tituloOpcion;
    }

    public void setTituloOpcion(String tituloOpcion) {
        this.tituloOpcion = tituloOpcion;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public boolean isFlagDesplazamiento() {
        return flagDesplazamiento;
    }

    public void setFlagDesplazamiento(boolean flagDesplazamiento) {
        this.flagDesplazamiento = flagDesplazamiento;
    }

    public List<AccDesplazaA> getLstAccDesplazamientos() {
        return lstAccDesplazamientos;
    }

    public void setLstAccDesplazamientos(List<AccDesplazaA> lstAccDesplazamientos) {
        this.lstAccDesplazamientos = lstAccDesplazamientos;
    }

    public Integer getIdAccDesplaza() {
        return idAccDesplaza;
    }

    public void setIdAccDesplaza(Integer idAccDesplaza) {
        this.idAccDesplaza = idAccDesplaza;
    }

    public List<TpConteoDTO> getLstTpConteoDTO() {
        return lstTpConteoDTO;
    }

    public void setLstTpConteoDTO(List<TpConteoDTO> lstTpConteoDTO) {
        this.lstTpConteoDTO = lstTpConteoDTO;
    }

}
