/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.EmpleadoDTO;
import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.OperacionPatiosFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.Empleado;
import com.movilidad.model.OperacionPatios;
import com.movilidad.util.beans.InformeLocalidadEmpleado;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author soluciones-it
 */
@Named(value = "reporteCercaniaJSF")
@ViewScoped
public class ReporteCercaniaJSF implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private OperacionPatiosFacadeLocal opfl;
    @EJB
    private ConfigEmpresaFacadeLocal cefl;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<Empleado> listEmpleado;
    private List<Empleado> listEmpleadoFilter;
    private Integer idOperacionPatio;
    private OperacionPatios operacionPatios;

    //georeferencia
    private MapModel simpleModel;
    private Marker marker;
    private ConfigEmpresa configEmpresa;

    // informe empleados por localidad
    private List<InformeLocalidadEmpleado> infLoclEmpList;
    private List<InformeLocalidadEmpleado> infLoclPorcentaje;
    private Integer total;
    private Integer idEmpleadoCargo;

    //
    private int idGopUF;

    /**
     * Creates a new instance of ReporteCercaniaJSF
     */
    public ReporteCercaniaJSF() {
    }

    @PostConstruct
    public void intit() {
        idOperacionPatio = null;
        listEmpleado = null;
        configEmpresa = cefl.findByLlave(Util.CODE_MARKER_ICON);
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public void consultar() {
        cargarUF();
        if (idOperacionPatio == null) {
            operacionPatios = null;
            MovilidadUtil.addErrorMessage("Debe seleccionar un patio");
            return;
        }
        operacionPatios = opfl.find(idOperacionPatio);
        if (operacionPatios.getLatitud() == null && operacionPatios.getLongitud() == null) {
            MovilidadUtil.addErrorMessage("Patio seleccionado no cuenta con georeferencia");
            operacionPatios = null;
            return;
        }
        listEmpleado = empleadoFacadeLocal.findAllEmpleadosActivos(idGopUF);
    }

    public void ubicacionEmpleado(Empleado emp) {
        simpleModel = new DefaultMapModel();
        LatLng latlngEmp = new LatLng(Double.valueOf(emp.getLatitud()), Double.valueOf(emp.getLongitud()));
        EmpleadoDTO empDTO = new EmpleadoDTO();
        empDTO.setNombres(emp.getNombresApellidos());
        empDTO.setDireccion(emp.getDireccion() != null ? emp.getDireccion() : "");
        empDTO.setTelefono(emp.getTelefonoMovil() != null ? emp.getTelefonoMovil() : "");
        empDTO.setTipologia(emp.getIdEmpleadoCargo() != null ? emp.getIdEmpleadoCargo().getNombreCargo() : "");
        empDTO.setUnidadFuncional(emp.getIdGopUnidadFuncional() != null ? emp.getIdGopUnidadFuncional().getNombre() : "");
        empDTO.setDistancia(emp.getKmDistanciaPaitos(operacionPatios.getLatitud(), operacionPatios.getLongitud()));
        LatLng latlngPatio = new LatLng(Double.valueOf(operacionPatios.getLatitud()), Double.valueOf(operacionPatios.getLongitud()));
        simpleModel.addOverlay(new Marker(latlngEmp, emp.getNombresApellidos(), empDTO, configEmpresa != null ? configEmpresa.getValor() : "https://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
        simpleModel.addOverlay(new Marker(latlngPatio, operacionPatios.getNombrePatio()));

        Polyline polyline = new Polyline();
        polyline.getPaths().add(latlngEmp);
        polyline.getPaths().add(latlngPatio);

        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);

        simpleModel.addOverlay(polyline);
        MovilidadUtil.openModal("dlgMap");
    }

    public void mostrarTodos() {
        if (idOperacionPatio == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un patio");
            operacionPatios = null;
            return;
        }
        if (listEmpleadoFilter != null && !listEmpleadoFilter.isEmpty()) {
            cargarMapLayer(listEmpleadoFilter);
        } else if (listEmpleado != null && !listEmpleado.isEmpty()) {
            cargarMapLayer(listEmpleado);
        } else {
            operacionPatios = null;
            MovilidadUtil.addErrorMessage("No se ha encontrado empleados");
        }
    }

    void cargarMapLayer(List<Empleado> listEmp) {
        simpleModel = new DefaultMapModel();
        LatLng latlngPatio = new LatLng(Double.valueOf(operacionPatios.getLatitud()), Double.valueOf(operacionPatios.getLongitud()));
        listEmp.forEach(emp -> {
            if (emp.getLatitud() != null && emp.getLongitud() != null) {
                EmpleadoDTO empDTO = new EmpleadoDTO();
                empDTO.setNombres(emp.getNombresApellidos());
                empDTO.setDireccion(emp.getDireccion() != null ? emp.getDireccion() : "");
                empDTO.setTelefono(emp.getTelefonoMovil() != null ? emp.getTelefonoMovil() : "");
                empDTO.setTipologia(emp.getIdEmpleadoCargo() != null ? emp.getIdEmpleadoCargo().getNombreCargo() : "");
                empDTO.setUnidadFuncional(emp.getIdGopUnidadFuncional() != null ? emp.getIdGopUnidadFuncional().getNombre() : "");
                empDTO.setDistancia(emp.getKmDistanciaPaitos(operacionPatios.getLatitud(), operacionPatios.getLongitud()));
                LatLng latlngEmp = new LatLng(Double.valueOf(emp.getLatitud()), Double.valueOf(emp.getLongitud()));
                simpleModel.addOverlay(new Marker(latlngEmp,
                        emp.getNombresApellidos() + " " + emp.getCodigoTm(),
                        empDTO,
                        configEmpresa != null ? configEmpresa.getValor() : "https://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
            }
        });
        simpleModel.addOverlay(new Marker(latlngPatio, operacionPatios.getNombrePatio()));
        MovilidadUtil.openModal("dlgMap");
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        if (event.getOverlay() instanceof Polyline) {
            return;
        }
        marker = (Marker) event.getOverlay();
    }

    // reporte localidad empleados
    public void crearInformeLocalidad() {
        cargarUF();
//        if (idGopUF == 0) {
//            MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
//            return;
//        }
        infLoclEmpList = empleadoFacadeLocal.findInformeLocalidadEmpleado(idEmpleadoCargo, idGopUF);
        if (infLoclEmpList.isEmpty()) {
            MovilidadUtil.addSuccessMessage("No se ha encontrado registros");
            if (infLoclPorcentaje == null) {
                infLoclPorcentaje = new ArrayList<>();
            }
            infLoclPorcentaje.clear();
            return;
        }
        // porcentaje
        if (infLoclPorcentaje == null) {
            infLoclPorcentaje = new ArrayList<>();
        }
        infLoclPorcentaje.clear();
        Supplier<Stream<InformeLocalidadEmpleado>> stream = () -> infLoclEmpList.stream();
        total = stream.get().mapToInt(infoLoclEmp -> infoLoclEmp.getTotal().intValue()).sum();
        infLoclPorcentaje = stream.get().map(infoLoclEmp -> {
            InformeLocalidadEmpleado ilc = new InformeLocalidadEmpleado();
            ilc.setLocalidad(infoLoclEmp.getLocalidad());
            float f = infoLoclEmp.getTotal() * 100 / total;
            ilc.setTotal(f);
            return ilc;
        }).collect(Collectors.toList());
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public List<Empleado> getListEmpleado() {
        return listEmpleado;
    }

    public void setListEmpleado(List<Empleado> listEmpleado) {
        this.listEmpleado = listEmpleado;
    }

    public Integer getIdOperacionPatio() {
        return idOperacionPatio;
    }

    public void setIdOperacionPatio(Integer idOperacionPatio) {
        this.idOperacionPatio = idOperacionPatio;
    }

    public OperacionPatios getOperacionPatios() {
        return operacionPatios;
    }

    public void setOperacionPatios(OperacionPatios operacionPatios) {
        this.operacionPatios = operacionPatios;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public List<Empleado> getListEmpleadoFilter() {
        return listEmpleadoFilter;
    }

    public void setListEmpleadoFilter(List<Empleado> listEmpleadoFilter) {
        this.listEmpleadoFilter = listEmpleadoFilter;
    }

    public List<InformeLocalidadEmpleado> getInfLoclEmpList() {
        return infLoclEmpList;
    }

    public void setInfLoclEmpList(List<InformeLocalidadEmpleado> infLoclEmpList) {
        this.infLoclEmpList = infLoclEmpList;
    }

    public List<InformeLocalidadEmpleado> getInfLoclPorcentaje() {
        return infLoclPorcentaje;
    }

    public void setInfLoclPorcentaje(List<InformeLocalidadEmpleado> infLoclPorcentaje) {
        this.infLoclPorcentaje = infLoclPorcentaje;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getIdEmpleadoCargo() {
        return idEmpleadoCargo;
    }

    public void setIdEmpleadoCargo(Integer idEmpleadoCargo) {
        this.idEmpleadoCargo = idEmpleadoCargo;
    }

}
