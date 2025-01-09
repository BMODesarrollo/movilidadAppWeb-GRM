/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoDocumentosFacadeLocal;
import com.movilidad.ejb.VehiculoDocumentoFacadeLocal;
import com.movilidad.model.EmpleadoDocumentos;
import com.movilidad.model.VehiculoDocumentos;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author solucionesit
 */
@Named(value = "validarDocumentacionBean")
@ViewScoped
public class ValidarDocumentacionBean implements Serializable {

    @EJB
    private VehiculoDocumentoFacadeLocal vehiculoDocumentoEJB;
    @EJB
    private EmpleadoDocumentosFacadeLocal empleadocumentoEJB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<VehiculoDocumentos> listDocVencidosVehiculo;
    private List<VehiculoDocumentos> listAllDocVencidosVehiculo;
    private List<EmpleadoDocumentos> listDocVencidosEmpleado;
    private List<EmpleadoDocumentos> listAllDocVencidosEmpleado;

    /**
     * Creates a new instance of ValidarDocumentacionBean
     */
    public ValidarDocumentacionBean() {
    }


    public void consultarDocVencidosOpe() {
        listAllDocVencidosEmpleado = empleadocumentoEJB.findAllDocVencidos(
                MovilidadUtil.fechaHoy(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void consultarDocVencidosVehi() {
        listAllDocVencidosVehiculo = vehiculoDocumentoEJB.findAllDocVencidos(
                MovilidadUtil.fechaHoy(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

    }

    /**
     * Método encargado de validar el estado de la documentación de vehiculos.
     * Carga lista de tipo Vehiculo Documento con los registros de los
     * documentos vencidos.
     *
     * @param fecha
     * @param idVehiculo
     * @return false si el vehículo no tiene documentos vencido, true si el
     * vehículo tiene documentos vencidos
     */
    public boolean validarDocuVhiculo(Date fecha, int idVehiculo) {
        listDocVencidosVehiculo = vehiculoDocumentoEJB.findDocVencidos(idVehiculo, fecha);
        return !listDocVencidosVehiculo.isEmpty();
    }

    /**
     * Método encargado de validar el estado de la documentación de Operadores.
     * Carga lista de tipo EmpleadoDocumento con los registros de los documentos
     * vencidos.
     *
     * @param fecha
     * @param idEmppleado
     * @return false si el Operador no tiene documentos vencidos, true si el
     * Operador tiene documentos vencidos
     */
    public boolean validarDocuOperador(Date fecha, int idEmppleado) {
        listDocVencidosEmpleado = empleadocumentoEJB.findDocVencidos(idEmppleado, fecha);

        return !listDocVencidosEmpleado.isEmpty();
    }

    public List<VehiculoDocumentos> getListDocVencidosVehiculo() {
        return listDocVencidosVehiculo;
    }

    public void setListDocVencidosVehiculo(List<VehiculoDocumentos> listDocVencidosVehiculo) {
        this.listDocVencidosVehiculo = listDocVencidosVehiculo;
    }

    public List<EmpleadoDocumentos> getListDocVencidosEmpleado() {
        return listDocVencidosEmpleado;
    }

    public void setListDocVencidosEmpleado(List<EmpleadoDocumentos> listDocVencidosEmpleado) {
        this.listDocVencidosEmpleado = listDocVencidosEmpleado;
    }

    public List<VehiculoDocumentos> getListAllDocVencidosVehiculo() {
        return listAllDocVencidosVehiculo;
    }

    public void setListAllDocVencidosVehiculo(List<VehiculoDocumentos> listAllDocVencidosVehiculo) {
        this.listAllDocVencidosVehiculo = listAllDocVencidosVehiculo;
    }

    public List<EmpleadoDocumentos> getListAllDocVencidosEmpleado() {
        return listAllDocVencidosEmpleado;
    }

    public void setListAllDocVencidosEmpleado(List<EmpleadoDocumentos> listAllDocVencidosEmpleado) {
        this.listAllDocVencidosEmpleado = listAllDocVencidosEmpleado;
    }

}
