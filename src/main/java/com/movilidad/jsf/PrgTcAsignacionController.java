/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.MttoTareaFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.PrgAsignacion;
import com.movilidad.ejb.PrgAsignacionFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoDocumentoFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.Errores;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.MttoTarea;
import com.movilidad.util.beans.PreCarga;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoDocumentos;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ServbusIdTipoVehiculo;
import com.movilidad.utils.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.event.FileUploadEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Cesar
 */
@Named(value = "prgTcAsigna")
@ViewScoped
public class PrgTcAsignacionController implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private PrgAsignacionFacadeLocal asignacionFacadeLocal;
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private MttoTareaFacadeLocal mttoTareaFacadeLocal;
    @EJB
    private VehiculoDocumentoFacadeLocal vehiculoDocumentoEJB;
    @EJB
    private ConfigEmpresaFacadeLocal empresaFacadeLocal;

    //
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private HashMap<String, Vehiculo> mapVehiculo;
    private HashMap<String, MttoTarea> mapMttoTarea;
    private HashMap<String, ServbusIdTipoVehiculo> mapServbus;
    private List<PreCarga> listPreCarga;
    private List<Errores> listErrores;
    private List<String> listPlacas;
    private List<String> listServbus;
    private Date fecha = null;
    private final String separador = ",";
    private boolean isValidAsignament = false;
    private ConfigEmpresa omitirDocVehiculo;
    private List<PrgAsignacion> listAsignacion;
    private int idGopUF;
    // control del tab que se está visualizando
    private Integer activeIndex;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PrgTcAsignacionController() {
    }

    @PostConstruct
    public void init() {
        omitirDocVehiculo = empresaFacadeLocal.findByLlave(ConstantsUtil.PERMITIR_VALIDACION_DOC_VEH);
        mapServbus = new HashMap<>();
        mapVehiculo = new HashMap<>();
        listPlacas = new ArrayList<>();
        listServbus = new ArrayList<>();
        mapMttoTarea = new HashMap<>();
        listPreCarga = new ArrayList<>();
        listErrores = new ArrayList<>();
        activeIndex = 0;
        cargarUF();
    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        cargarUF();
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            return;
        }
        reset();
        cargarMapTarea();
        cargarVehiculos();
        boolean isValidPrograming = true;
//        String path = Util.saveFile(event.getFile(), 0, "asignacion");
        Scanner scar = new Scanner(event.getFile().getInputstream());
        while (scar.hasNextLine()) {
            String nextLine = scar.nextLine();
            String[] split = nextLine.split(separador);
            String servbus = null, vehiculo = null, timeOrigin = null, timeDestiny = null, tareaMtto = null;
            if (split.length > 0) {
                if (isValidPrograming) {
                    fecha = Util.toDate(split[0]);
                    cargarServbus();
                    // validar si se puede cargar programacion para la fecha
//                    isValidPrograming = !isValidProgrammingFromDay();
                    if (!isValidProgrammingFromDay()) {
                        MovilidadUtil.addErrorMessage("No se puede sobreescribir la asignación");
                        return;
                    }
                    isValidPrograming = false;
                }
            }
            if (split.length > 1) {
                servbus = split[1];
            }
            if (split.length > 2) {
                vehiculo = split[2];
            }
            if (split.length > 3) {
                timeOrigin = split[3].isEmpty() ? null : split[3];
            }
            if (split.length > 4) {
                timeDestiny = split[4].isEmpty() ? null : split[4];
            }
            if (split.length > 5) {
                tareaMtto = split[5];
            }
            validar(servbus, vehiculo, timeOrigin, timeDestiny, tareaMtto);
        }
        scar.close();
        long countVehiculo = listPlacas.stream().distinct().count();
        long countServbus = listServbus.stream().distinct().count();
        if (countVehiculo != listPlacas.size()) {
            MovilidadUtil.addErrorMessage("El archivo presenta vehículos repetidos");
            reset();
            MovilidadUtil.hideModal("upCsv");
            return;
        }
        if (countServbus != listServbus.size()) {
            MovilidadUtil.addErrorMessage("El archivo presenta servbus repetidos");
            reset();
            MovilidadUtil.hideModal("upCsv");
            return;
        }
        if (!listErrores.isEmpty()) {
            MovilidadUtil.addErrorMessage("La asignación ha presentado errores, revisar.");
            MovilidadUtil.hideModal("upCsv");
            activeIndex = 1;
            return;
        }
        isValidAsignament = true;
        activeIndex = 0;
        MovilidadUtil.addSuccessMessage("Se ha procesado con éxito el archivo, puede proceder a asignar");
        MovilidadUtil.hideModal("upCsv");
    }

    public void reset() {
        mapVehiculo.clear();
        listErrores.clear();
        listPlacas.clear();
        listServbus.clear();
        listPreCarga.clear();
        fecha = null;
        isValidAsignament = false;
    }

    void validar(String servbus, String vehiculo, String timeOrigin, String timeDestiny, String tarea) {
        Errores error;
        String fecha = Util.dateFormat(this.fecha);
        boolean isValid = true;
        boolean existVehiculo = mapVehiculo.containsKey(vehiculo);
        boolean existServbus = mapServbus.containsKey(servbus);
        boolean existTareaMtto = mapMttoTarea.containsKey(tarea);

        boolean isNullTimeOrigin = Util.isStringNullOrEmpty(timeOrigin);
        boolean isNullTimeDestiny = Util.isStringNullOrEmpty(timeDestiny);
        boolean isNullTarea = Util.isStringNullOrEmpty(tarea);

        // si la tarea no es nula, validar que los tiempos sean correctos
        if (!isNullTarea) {
            if (!existTareaMtto) {
                error = new Errores();
                error.setFecha(fecha);
                error.setServbus(servbus);
                error.setBus(vehiculo);
                error.setError(tarea + " Esta tarea no se encuentra parametrizada");
                listErrores.add(error);
                isValid = false;
            }
            if (isNullTimeOrigin || isNullTimeDestiny) {
                error = new Errores();
                error.setFecha(fecha);
                error.setServbus(servbus);
                error.setBus(vehiculo);
                error.setError(tarea + " No está permitido asignar una tarea sin rango de tiempos.");
                listErrores.add(error);
                isValid = false;
            }

            // validar si el time_origin que viene cargado es valido
            if (!isNullTimeOrigin) {
                if (MovilidadUtil.toSecs(timeOrigin) > MovilidadUtil.toSecs(ConstantsUtil.HORA_FINAL_DIA)) {
                    error = new Errores();
                    error.setFecha(fecha);
                    error.setServbus(servbus);
                    error.setBus(vehiculo);
                    error.setError(timeOrigin + " Tiempo incorrecto, no puede ser mayor a 23:59:59");
                    listErrores.add(error);
                    isValid = false;
                }
            }
            // validar si el time_destiny que viene cargado es valido
            if (!isNullTimeDestiny) {
                if (MovilidadUtil.toSecs(timeDestiny) > MovilidadUtil.toSecs(ConstantsUtil.HORA_FINAL_DIA)) {
                    error = new Errores();
                    error.setFecha(fecha);
                    error.setServbus(servbus);
                    error.setBus(vehiculo);
                    error.setError(timeDestiny + " Tiempo incorrecto, no puede ser mayor a 23:59:59");
                    listErrores.add(error);
                    isValid = false;
                }
            }
            // validar que time_origin no sea menor a time_destiny
            if (!isNullTimeOrigin && !isNullTimeDestiny) {
                if (MovilidadUtil.toSecs(timeOrigin) > MovilidadUtil.toSecs(timeDestiny)) {
                    error = new Errores();
                    error.setFecha(fecha);
                    error.setServbus(servbus);
                    error.setBus(vehiculo);
                    error.setError("TimeOrigin " + timeOrigin + " No puede ser mayor a TimeDestiny " + timeDestiny);
                    listErrores.add(error);
                    isValid = false;
                }
            }
        }
        if (!existVehiculo) {
            error = new Errores();
            error.setFecha(fecha);
            error.setServbus(servbus);
            error.setBus(vehiculo);
            error.setError("Vehículo no existe");
            listErrores.add(error);
            isValid = false;
        }
        if (!existServbus) {
            error = new Errores();
            error.setFecha(fecha);
            error.setServbus(servbus);
            error.setBus(vehiculo);
            error.setError("ServBus no existe en Programación");
            listErrores.add(error);
            isValid = false;
        }
        if (existVehiculo && existServbus) {
            if (!isEqualsTipoVehiculo(mapVehiculo.get(vehiculo), mapServbus.get(servbus))) {
                error = new Errores();
                error.setFecha(fecha);
                error.setServbus(servbus);
                error.setBus(vehiculo);
                error.setError("Vehículo no valido para cubrir este servicio");
                listErrores.add(error);
                isValid = false;
            }
        }
        if (!existVehiculo) {
            if (omitirDocVehiculo != null && omitirDocVehiculo.getValor().equals(ConstantsUtil.ON_INT)) {
                List<VehiculoDocumentos> findDocVencidos = vehiculoDocumentoEJB
                        .findDocVencidos(mapVehiculo.get(vehiculo).getIdVehiculo(), this.fecha);
                if (!findDocVencidos.isEmpty()) {
                    for (VehiculoDocumentos vd : findDocVencidos) {
                        error = new Errores();
                        error.setFecha(fecha);
                        error.setServbus(servbus);
                        error.setBus(vehiculo);
                        error.setError("Documento Vencido: " + vd.getIdVehiculoTipoDocumento().getNombreTipoDocumento());
                        listErrores.add(error);
                    }
                    isValid = false;
                }
            }
        }
        if (isValid) {
            PreCarga carga = new PreCarga();
            carga.setFecha(this.fecha);
            carga.setServbus(servbus);
            carga.setVehiculo(mapVehiculo.get(vehiculo));
            if (!isNullTarea) {
                carga.setMttoTarea(mapMttoTarea.get(tarea));
                carga.setTimeDestiny(timeDestiny);
                carga.setTimeOrigin(timeOrigin);
            }
            listPreCarga.add(carga);
        }
        listPlacas.add(vehiculo);
        listServbus.add(servbus);
    }

    void cargarVehiculos() {
        mapVehiculo.clear();
        vehiculoFacadeLocal.findAllVehiculosByidGopUnidadFuncional(idGopUF).forEach((v) -> {
            mapVehiculo.put(v.getCodigo(), v);
        });
    }

    void cargarServbus() {
        mapServbus.clear();
        prgTcFacadeLocal.getServbusAndIdTipoVehiculo(Util.dateFormat(fecha), idGopUF).forEach((o) -> {
            mapServbus.put(o.getServbus(), o);
        });
    }

    @Transactional
    public void programar() {
        if (listPreCarga.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay programación para cargar el la fecha suministrada");
            return;
        }
        String sFecha = Util.dateFormat(fecha);
        Date d = new Date();
        GopUnidadFuncional gUf = new GopUnidadFuncional(idGopUF);
        listPreCarga.stream().map(pc -> {
            prgTcFacadeLocal.asignarBusToServbus(pc.getVehiculo().getIdVehiculo(),
                    pc.getServbus(),
                    sFecha,
                    user.getUsername(),
                    idGopUF);
            return pc;
        }).map(pc -> {
            PrgAsignacion pa = new PrgAsignacion();
            pa.setFecha(fecha);
            pa.setServbus(pc.getServbus());
            pa.setIdVehiculo(pc.getVehiculo());
            pa.setCreado(d);
            pa.setModificado(d);
            pa.setIdGopUnidadFuncional(gUf);
            pa.setUsername(user.getUsername());
            if (pc.getMttoTarea() != null) {
                pa.setIdMttoTarea(pc.getMttoTarea());
                pa.setTimeDestiny(pc.getTimeDestiny());
                pa.setTimeOrigin(pc.getTimeOrigin());
            }
            return pa;
        }).forEachOrdered(pa -> {
            asignacionFacadeLocal.create(pa);
        });
        reset();
        activeIndex = 2;
        MovilidadUtil.addSuccessMessage("Asignación aplicada con éxito");
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    /* Obtener la data de todos los objetos MttoTarea
     * mantenerlos en el objeto Map para realizar una busqueda de ellos mas optima 
     */
    void cargarMapTarea() {
        mapMttoTarea.clear();
        mttoTareaFacadeLocal.findAll().forEach((mttoTarea) -> {
            mapMttoTarea.put(mttoTarea.getNombreMttoTarea(), mttoTarea);
        });
    }

    boolean isEqualsTipoVehiculo(Vehiculo vehiculo, ServbusIdTipoVehiculo stv) {
        return vehiculo.getIdVehiculoTipo().getIdVehiculoTipo().equals(stv.getIdTipoVehiculo());
    }

    boolean isValidProgrammingFromDay() {
        return asignacionFacadeLocal.countAsignacionDiaByFechaAndIdGopUF(fecha, idGopUF) == 0;
    }

    public List<PreCarga> getListPreCarga() {
        return listPreCarga;
    }

    public void setListPreCarga(List<PreCarga> listPreCarga) {
        this.listPreCarga = listPreCarga;
    }

    public List<Errores> getListErrores() {
        return listErrores;
    }

    public void setListErrores(List<Errores> listErrores) {
        this.listErrores = listErrores;
    }

    public boolean isIsValidAsignament() {
        return isValidAsignament;
    }

    public void setIsValidAsignament(boolean isValidAsignament) {
        this.isValidAsignament = isValidAsignament;
    }

    public List<PrgAsignacion> getListAsignacion() {
        cargarUF();
        listAsignacion = asignacionFacadeLocal.findAsignacionDiaByFechaAndIdGopUF(new Date(), idGopUF);
        return listAsignacion;
    }

    public void setListAsignacion(List<PrgAsignacion> listAsignacion) {
        this.listAsignacion = listAsignacion;
    }

    public Integer getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(Integer activeIndex) {
        this.activeIndex = activeIndex;
    }

}
