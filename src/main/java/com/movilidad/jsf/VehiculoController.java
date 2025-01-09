/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoPropietarioFacadeLocal;
import com.movilidad.ejb.VehiculoTipoCarroceriaFacadeLocal;
import com.movilidad.ejb.VehiculoTipoChasisFacadeLocal;
import com.movilidad.ejb.VehiculoTipoCombustibleFacadeLocal;
import com.movilidad.ejb.VehiculoTipoDireccionFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoTransmisionFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoPropietarios;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.model.VehiculoTipoCarroceria;
import com.movilidad.model.VehiculoTipoChasis;
import com.movilidad.model.VehiculoTipoCombustible;
import com.movilidad.model.VehiculoTipoDireccion;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.model.VehiculoTipoTransmision;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author César
 */
@Named(value = "vehiculoController")
@ViewScoped
public class VehiculoController implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoEJB;
    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoFacadeLocal;
    @EJB
    private VehiculoTipoCarroceriaFacadeLocal vehiculoTipoCarroceriaFacadeLocal;
    @EJB
    private VehiculoTipoDireccionFacadeLocal vehiculoTipoDireccionFacadeLocal;
    @EJB
    private VehiculoTipoTransmisionFacadeLocal vehiculoTipoTransmisionFacadeLocal;
    @EJB
    private VehiculoTipoCombustibleFacadeLocal vehiculoTipoCombustibleFacadeLocal;
    @EJB
    private VehiculoPropietarioFacadeLocal vehiculoPropietarioFacadeLocal;
    @EJB
    private VehiculoTipoEstadoFacadeLocal vehiculoTipoEstadoFacadeLocal;
    @EJB
    private VehiculoTipoChasisFacadeLocal vehiculoTipoChasisFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private Vehiculo vehiculo;

    private List<Vehiculo> listvehiculos;
    private List<VehiculoTipo> listvehiculoTipos;
    private List<VehiculoTipoChasis> listvehiculoTipoChasises;
    private List<VehiculoTipoCarroceria> listvehiculoTipoCarroceria;
    private List<VehiculoTipoDireccion> listvehiculoTipopoDireccion;
    private List<VehiculoTipoTransmision> listvehiculoTipoTransmision;
    private List<VehiculoTipoCombustible> listvehiculoTipoCombustible;
    private List<VehiculoPropietarios> listvehiculoPropietario;
    private List<VehiculoTipoEstado> listvehiculoTipoEstado;

    private Integer i_vehiculoTipo;
    private Integer i_vehiculoTipoChasis;
    private Integer i_vehiculoTipoCarroceria;
    private Integer i_vehiculoTipoDireccion;
    private Integer i_vehiculoTipoTrasmision;
    private Integer i_vehiculoTipoCombustible;
    private Integer i_vehiculoPropietario;
    private Integer i_vehiculoTipoEstado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public VehiculoController() {
    }

    @PostConstruct
    public void init() {
        consultarVehiculos();
    }

    public void consultarVehiculos() {
        listvehiculos = vehiculoEJB.findAllVehiculosByidGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

    }

    public void prepareNuevoVehiculo() {
        cargarTipos();
        reset();
        vehiculo = new Vehiculo();
    }

    public void prepareEditarVehiculo() {
        if (vehiculo.getIdVehiculo() != 0) {

            i_vehiculoPropietario = vehiculo.getIdVehiculoPropietario() == null
                    ? 0 : vehiculo.getIdVehiculoPropietario().getIdVehiculoPropietario();
            i_vehiculoTipo = vehiculo.getIdVehiculoTipo() == null
                    ? 0 : vehiculo.getIdVehiculoTipo().getIdVehiculoTipo();
            i_vehiculoTipoCarroceria = vehiculo.getIdVehiculoCarroceria() == null
                    ? 0 : vehiculo.getIdVehiculoCarroceria().getIdVehiculoTipoCarroceria();
            i_vehiculoTipoChasis = vehiculo.getIdVehiculoChasis() == null
                    ? 0 : vehiculo.getIdVehiculoChasis().getIdVehiculoTipoChasis();
            i_vehiculoTipoCombustible = vehiculo.getIdVehiculoCombustible() == null
                    ? 0 : vehiculo.getIdVehiculoCombustible().getIdVehiculoTipoCombustible();
            i_vehiculoTipoDireccion = vehiculo.getIdVehiculoDireccion() == null
                    ? 0 : vehiculo.getIdVehiculoDireccion().getIdVehiculoTipoDireccion();
            i_vehiculoTipoTrasmision = vehiculo.getIdVehiculoTransmision() == null
                    ? 0 : vehiculo.getIdVehiculoTransmision().getIdVehiculoTipoTransmision();
            i_vehiculoTipoEstado = vehiculo.getIdVehiculoTipoEstado() == null
                    ? 0 : vehiculo.getIdVehiculoTipoEstado().getIdVehiculoTipoEstado();
            unidadFuncionalSessionBean.setI_unidad_funcional(vehiculo.getIdGopUnidadFuncional() == null ? 0
                    : vehiculo.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        }
        cargarTipos();
        MovilidadUtil.openModal("nuevoVehiculoDialog");
    }

    @Transactional
    public void guardarVehiculo() {
        try {
            if (validar()) {
                return;
            }
            cargarObjetos();
            converMayus();
            vehiculo.setCreado(new Date());
            vehiculo.setModificado(new Date());
            vehiculo.setUsername(user.getUsername());
            vehiculo.setEstadoReg(0);
            vehiculoEJB.create(vehiculo);
            MovilidadUtil.addSuccessMessage("Vehículo Registrado Correctamente");
            consultarVehiculos();
            vehiculo = new Vehiculo();
            reset();
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error del sistemas");
            System.out.println("error en gardarVehiculo");
        }
    }

    boolean validar() {
        if (i_vehiculoTipo == 0) {
            MovilidadUtil.addErrorMessage("error en vehículo tipo.");
            return true;
        }
        if (i_vehiculoTipoCarroceria == 0) {
            MovilidadUtil.addErrorMessage("error vehículo tipo carrocería");
            return true;
        }
        if (i_vehiculoPropietario == 0) {
            MovilidadUtil.addErrorMessage("error en propietario.");
            return true;
        }
        if (i_vehiculoTipoChasis == 0) {
            MovilidadUtil.addErrorMessage("error vehículo chasis.");
            return true;
        }
        if (i_vehiculoTipoCombustible == 0) {
            MovilidadUtil.addErrorMessage("error en tipo combustible.");
            return true;
        }
        if (i_vehiculoTipoDireccion == 0) {
            MovilidadUtil.addErrorMessage("error en tipo dirección");
            return true;
        }
        if (i_vehiculoTipoEstado == 0) {
            MovilidadUtil.addErrorMessage("error en tipo estado.");
            return true;
        }
        if (i_vehiculoTipoTrasmision == 0) {
            MovilidadUtil.addErrorMessage("error en tipo trasmisión");
            return true;
        }
        Integer idVehiculo = vehiculo.getIdVehiculo() == null ? 0 : vehiculo.getIdVehiculo();
        if (validarDatos("codigo", vehiculo.getCodigo(), idVehiculo)) {
            MovilidadUtil.addErrorMessage("Código no disponible, el código se encuentra registrado en el sistema");
            return true;
        }
        if (validarDatos("placa", vehiculo.getPlaca(), idVehiculo)) {
            MovilidadUtil.addErrorMessage("Placa no disponible, la placa se encuentra registrado en el sistema");
            return true;
        }
        if (validarDatos("numero_motor", vehiculo.getNumeroMotor(), idVehiculo)) {
            MovilidadUtil.addErrorMessage("Número de Motor ingresado se encuentra registrado en el sistema");
            return true;
        }
        if (validarDatos("numero_chasis", vehiculo.getNumeroChasis(), idVehiculo)) {
            MovilidadUtil.addErrorMessage("Númeri de Chasis ingresado se encuentra registrado en el sistema");
            return true;
        }
        if (validarDatos("numero_carroceria", vehiculo.getNumeroCarroceria(), idVehiculo)) {
            MovilidadUtil.addErrorMessage("Número de Carrocería ingresado se encuentra registrado en el sistema");
            return true;
        }
        if (validarDatos("numero_serie", vehiculo.getNumeroSerie(), idVehiculo)) {
            MovilidadUtil.addErrorMessage("Número de Serie ingresado se encuentra registrado en el sistema");
            return true;
        }
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Unidad Funcional es requerido.");
            return true;
        }

        return false;
    }

    public void editarVehiculo() {
        try {
            if (validar()) {
                return;
            }
            cargarObjetos();
            converMayus();

            vehiculo.setUsername(user.getUsername());
            vehiculo.setModificado(new Date());
            vehiculoEJB.edit(vehiculo);
            MovilidadUtil.addSuccessMessage("Vehículo actualizado correctamente");
            consultarVehiculos();
            reset();
            MovilidadUtil.hideModal("nuevoVehiculoDialog");
        } catch (Exception e) {
            System.out.println("Error al editar");
            MovilidadUtil.addErrorMessage("Error del sistema");
        }
    }

    void cargarObjetos() {
        vehiculo.setIdVehiculoCarroceria(new VehiculoTipoCarroceria(i_vehiculoTipoCarroceria));
        vehiculo.setIdVehiculoChasis(new VehiculoTipoChasis(i_vehiculoTipoChasis));
        vehiculo.setIdVehiculoCombustible(new VehiculoTipoCombustible(i_vehiculoTipoCombustible));
        vehiculo.setIdVehiculoDireccion(new VehiculoTipoDireccion(i_vehiculoTipoDireccion));
        vehiculo.setIdVehiculoPropietario(new VehiculoPropietarios(i_vehiculoPropietario));
        vehiculo.setIdVehiculoTipo(new VehiculoTipo(i_vehiculoTipo));
        vehiculo.setIdVehiculoTipoEstado(new VehiculoTipoEstado(i_vehiculoTipoEstado));
        vehiculo.setIdVehiculoTransmision(new VehiculoTipoTransmision(i_vehiculoTipoTrasmision));
        vehiculo.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
    }

    void converMayus() {
        try {
            vehiculo.setCodigo(vehiculo.getCodigo().toUpperCase());
            vehiculo.setColor((vehiculo.getColor().equals("") || vehiculo.getColor() == null ? "" : vehiculo.getColor().toUpperCase()));
            vehiculo.setNumeroCarroceria((vehiculo.getNumeroCarroceria().equals("") || vehiculo.getNumeroCarroceria() == null ? "" : vehiculo.getNumeroCarroceria().toUpperCase()));
            vehiculo.setNumeroMotor((vehiculo.getNumeroMotor().equals("") || vehiculo.getNumeroMotor() == null ? "" : vehiculo.getNumeroMotor().toUpperCase()));
            vehiculo.setNumeroSerie((vehiculo.getNumeroSerie().equals("") || vehiculo.getNumeroSerie() == null ? "" : vehiculo.getNumeroSerie().toUpperCase()));
            vehiculo.setNumeroChasis((vehiculo.getNumeroChasis().equals("") || vehiculo.getNumeroChasis() == null ? "" : vehiculo.getNumeroChasis().toUpperCase()));
            vehiculo.setPlaca((vehiculo.getPlaca().equals("") || vehiculo.getPlaca() == null ? "" : vehiculo.getPlaca().toUpperCase()));
        } catch (Exception e) {
            System.out.println("Error en Convertir mayusculas");
        }
    }

    boolean validarDatos(String campo, String valor, Integer id) {
        List<Vehiculo> parametros = vehiculoEJB.getParametros(campo, valor, id);
//        System.out.println(parametros);
        return parametros.size() > 0;
    }

    public void reset() {
        vehiculo = null;
        i_vehiculoTipo = 0;
        i_vehiculoTipoChasis = 0;
        i_vehiculoTipoCarroceria = 0;
        i_vehiculoTipoDireccion = 0;
        i_vehiculoTipoTrasmision = 0;
        i_vehiculoTipoCombustible = 0;
        i_vehiculoPropietario = 0;
        i_vehiculoTipoEstado = 0;
    }

    private void cargarTipos() {
        listvehiculoTipos = vehiculoTipoFacadeLocal.findAllEstadoR();
        listvehiculoTipoChasises = vehiculoTipoChasisFacadeLocal.findAll();
        listvehiculoTipoCombustible = vehiculoTipoCombustibleFacadeLocal.findAll();
        listvehiculoTipopoDireccion = vehiculoTipoDireccionFacadeLocal.findAll();
        listvehiculoTipoCarroceria = vehiculoTipoCarroceriaFacadeLocal.findAll();
        listvehiculoTipoTransmision = vehiculoTipoTransmisionFacadeLocal.findAll();
        listvehiculoPropietario = vehiculoPropietarioFacadeLocal.findAll();
        listvehiculoTipoEstado = vehiculoTipoEstadoFacadeLocal.findAll();
    }

    public void onRowSelect(SelectEvent event) {
        vehiculo = (Vehiculo) event.getObject();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Integer getI_vehiculoTipo() {
        return i_vehiculoTipo;
    }

    public void setI_vehiculoTipo(Integer i_vehiculoTipo) {
        this.i_vehiculoTipo = i_vehiculoTipo;
    }

    public Integer getI_vehiculoTipoChasis() {
        return i_vehiculoTipoChasis;
    }

    public void setI_vehiculoTipoChasis(Integer i_vehiculoTipoChasis) {
        this.i_vehiculoTipoChasis = i_vehiculoTipoChasis;
    }

    public Integer getI_vehiculoTipoCarroceria() {
        return i_vehiculoTipoCarroceria;
    }

    public void setI_vehiculoTipoCarroceria(Integer i_vehiculoTipoCarroceria) {
        this.i_vehiculoTipoCarroceria = i_vehiculoTipoCarroceria;
    }

    public Integer getI_vehiculoTipoDireccion() {
        return i_vehiculoTipoDireccion;
    }

    public void setI_vehiculoTipoDireccion(Integer i_vehiculoTipoDireccion) {
        this.i_vehiculoTipoDireccion = i_vehiculoTipoDireccion;
    }

    public Integer getI_vehiculoTipoTrasmision() {
        return i_vehiculoTipoTrasmision;
    }

    public void setI_vehiculoTipoTrasmision(Integer i_vehiculoTipoTrasmision) {
        this.i_vehiculoTipoTrasmision = i_vehiculoTipoTrasmision;
    }

    public Integer getI_vehiculoTipoCombustible() {
        return i_vehiculoTipoCombustible;
    }

    public void setI_vehiculoTipoCombustible(Integer i_vehiculoTipoCombustible) {
        this.i_vehiculoTipoCombustible = i_vehiculoTipoCombustible;
    }

    public Integer getI_vehiculoPropietario() {
        return i_vehiculoPropietario;
    }

    public void setI_vehiculoPropietario(Integer i_vehiculoPropietario) {
        this.i_vehiculoPropietario = i_vehiculoPropietario;
    }

    public Integer getI_vehiculoTipoEstado() {
        return i_vehiculoTipoEstado;
    }

    public void setI_vehiculoTipoEstado(Integer i_vehiculoTipoEstado) {
        this.i_vehiculoTipoEstado = i_vehiculoTipoEstado;
    }

    public List<VehiculoTipo> getListvehiculoTipos() {
        return listvehiculoTipos;
    }

    public List<VehiculoTipoChasis> getListvehiculoTipoChasises() {
        return listvehiculoTipoChasises;
    }

    public List<VehiculoTipoCarroceria> getListvehiculoTipoCarroceria() {
        return listvehiculoTipoCarroceria;
    }

    public List<VehiculoTipoDireccion> getListvehiculoTipopoDireccion() {
        return listvehiculoTipopoDireccion;
    }

    public List<VehiculoTipoTransmision> getListvehiculoTipoTransmision() {
        return listvehiculoTipoTransmision;
    }

    public List<VehiculoTipoCombustible> getListvehiculoTipoCombustible() {
        return listvehiculoTipoCombustible;
    }

    public List<VehiculoPropietarios> getListvehiculoPropietario() {
        return listvehiculoPropietario;
    }

    public List<VehiculoTipoEstado> getListvehiculoTipoEstado() {
        return listvehiculoTipoEstado;
    }

    public List<Vehiculo> getListvehiculos() {
        return listvehiculos;
    }

    public void setListvehiculos(List<Vehiculo> listvehiculos) {
        this.listvehiculos = listvehiculos;
    }

}
