package com.movilidad.jsf;

import com.movilidad.ejb.PrgVehiculoInactivoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoFacadeLocal;
import com.movilidad.model.PrgVehiculoInactivo;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cambioVehiculoBean")
@ViewScoped
public class CambioVehiculoJSFManagedBean implements Serializable {

    @EJB
    private PrgVehiculoInactivoFacadeLocal vehiculoInactivoEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private VehiculoTipoEstadoFacadeLocal vehiculoEstadoEjb;

    private List<PrgVehiculoInactivo> lstVehiculosInactivos;
    private List<VehiculoTipoEstado> lstEstadoVehiculos;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private PrgVehiculoInactivo vehiculoInactivo;
    private PrgVehiculoInactivo selected;

    private String s_codVehiculo;
    private int i_EstadoVehiculo;

    public void nuevo() {
        vehiculoInactivo = new PrgVehiculoInactivo();
        i_EstadoVehiculo = 0;
        s_codVehiculo = "";
        selected = null;
    }

    public void editar() {
        vehiculoInactivo = selected;
        s_codVehiculo = selected.getIdVehiculo().getCodigo();
        i_EstadoVehiculo = selected.getIdVehiculoEstado().getIdVehiculoTipoEstado();
    }

    public void guardar() {
        if (vehiculoInactivo.getIdVehiculo() == null) {
            PrimeFaces.current().ajax().update("frmCambioVehiculo:messages");
            MovilidadUtil.addErrorMessage("Debe ingresar un vehículo");
            return;
        }
        if (Util.validarFechaCambioEstado(vehiculoInactivo.getFromDate(), vehiculoInactivo.getToDate())) {
            PrimeFaces.current().ajax().update("frmCambioVehiculo:messages");
            MovilidadUtil.addErrorMessage("La fecha inicio debe ser menor a la fecha fin");
            return;
        }

        cambiarEstadoVehiculo(vehiculoInactivo.getIdVehiculo());
        vehiculoInactivo.setIdVehiculoEstado(new VehiculoTipoEstado(i_EstadoVehiculo));
        vehiculoInactivo.setUsername(user.getUsername());
        vehiculoInactivo.setCreado(new Date());
        vehiculoInactivo.setActivo(0);
        vehiculoInactivoEjb.create(vehiculoInactivo);
        nuevo();
        MovilidadUtil.addSuccessMessage("Estado de vehículo cambiado éxitosamente");
    }

    public void actualizar() {
        if (vehiculoInactivo.getIdVehiculo() == null) {
            PrimeFaces.current().ajax().update("frmCambioVehiculo:messages");
            MovilidadUtil.addErrorMessage("Debe ingresar un vehículo");
            return;
        }
        if (Util.validarFechaCambioEstado(vehiculoInactivo.getFromDate(), vehiculoInactivo.getToDate())) {
            PrimeFaces.current().ajax().update("frmCambioVehiculo:messages");
            MovilidadUtil.addErrorMessage("La fecha inicio debe ser menor a la fecha fin");
            return;
        }
        if (i_EstadoVehiculo == 1) {
            PrimeFaces.current().ajax().update("frmCambioVehiculo:messages");
            MovilidadUtil.addErrorMessage("Para activar el vehículo debe seleccionar el botón en la columna de acciones");
            return;
        }

        VehiculoTipoEstado vehiculoTipoEstado = vehiculoEstadoEjb.find(i_EstadoVehiculo);

        cambiarEstadoVehiculo(vehiculoInactivo.getIdVehiculo());
        vehiculoInactivo.setActivo(0);
        vehiculoInactivo.setIdVehiculoEstado(vehiculoTipoEstado);
        vehiculoInactivo.setUsername(user.getUsername());
        vehiculoInactivo.setModificado(new Date());
        vehiculoInactivoEjb.edit(vehiculoInactivo);
        MovilidadUtil.addSuccessMessage("Estado de vehículo cambiado éxitosamente");
    }

    public void cambiarEstadoVehiculo(Vehiculo vehiculo) {
        vehiculo.setIdVehiculoTipoEstado(new VehiculoTipoEstado(i_EstadoVehiculo));
        vehiculoEjb.edit(vehiculo);
    }

    public void prepareActivarVehiculo() {
        selected.setObservaciones("");
        s_codVehiculo = selected.getIdVehiculo().getCodigo();
        i_EstadoVehiculo = selected.getIdVehiculo().getIdVehiculoTipoEstado().getIdVehiculoTipoEstado();
    }

    public void activarVehiculo() {
        Vehiculo vehiculo = selected.getIdVehiculo();
        vehiculo.setIdVehiculoTipoEstado(new VehiculoTipoEstado(1));
        vehiculoEjb.edit(vehiculo);

        selected.setActivo(1);
        selected.setIdVehiculoEstado(new VehiculoTipoEstado(1));
        selected.setUsrHabilita(user.getUsername());
        selected.setModificado(new Date());
        vehiculoInactivoEjb.edit(selected);
        PrimeFaces.current().executeScript("PF('activarVehiculo').hide();");
        MovilidadUtil.addSuccessMessage("Vehículo activado éxitosamente");
        selected = null;
    }

    public boolean verificarEstado(Date f1) {
        Date f2 = new Date();
        return f2.compareTo(f1) > 0;
    }

    public void buscarVehiculo() {
        PrimeFaces current = PrimeFaces.current();
        if (s_codVehiculo != null && !s_codVehiculo.isEmpty()) {
            Vehiculo v = vehiculoEjb.getVehiculoCodigo(s_codVehiculo.toUpperCase());
            if (v != null) {
                vehiculoInactivo.setIdVehiculo(v);
                vehiculoInactivo.setIdVehiculoEstado(v.getIdVehiculoTipoEstado());
                current.ajax().update("frmCambioVehiculo:messages");
                MovilidadUtil.addSuccessMessage("Vehículo encontrado");
            } else {
                vehiculoInactivo.setIdVehiculo(null);
                current.ajax().update("frmCambioVehiculo:messages");
                MovilidadUtil.addErrorMessage("Vehículo no encontrado");
            }
        } else {
            vehiculoInactivo.setIdVehiculo(null);
            current.ajax().update("frmCambioVehiculo:messages");
            MovilidadUtil.addErrorMessage("Ingrese el código del vehículo");
        }
    }

    public List<PrgVehiculoInactivo> getLstVehiculosInactivos() {
        lstVehiculosInactivos = vehiculoInactivoEjb.findAll();
        return lstVehiculosInactivos;
    }

    public void setLstVehiculosInactivos(List<PrgVehiculoInactivo> lstVehiculosInactivos) {
        this.lstVehiculosInactivos = lstVehiculosInactivos;
    }

    public List<VehiculoTipoEstado> getLstEstadoVehiculos() {
        lstEstadoVehiculos = vehiculoEstadoEjb.findAll();
        return lstEstadoVehiculos;
    }

    public void setLstEstadoVehiculos(List<VehiculoTipoEstado> lstEstadoVehiculos) {
        this.lstEstadoVehiculos = lstEstadoVehiculos;
    }

    public PrgVehiculoInactivo getVehiculoInactivo() {
        return vehiculoInactivo;
    }

    public void setVehiculoInactivo(PrgVehiculoInactivo vehiculoInactivo) {
        this.vehiculoInactivo = vehiculoInactivo;
    }

    public PrgVehiculoInactivo getSelected() {
        return selected;
    }

    public void setSelected(PrgVehiculoInactivo selected) {
        this.selected = selected;
    }

    public String getS_codVehiculo() {
        return s_codVehiculo;
    }

    public void setS_codVehiculo(String s_codVehiculo) {
        this.s_codVehiculo = s_codVehiculo;
    }

    public int getI_EstadoVehiculo() {
        return i_EstadoVehiculo;
    }

    public void setI_EstadoVehiculo(int i_EstadoVehiculo) {
        this.i_EstadoVehiculo = i_EstadoVehiculo;
    }

    public PrgVehiculoInactivoFacadeLocal getVehiculoInactivoEjb() {
        return vehiculoInactivoEjb;
    }

    public void setVehiculoInactivoEjb(PrgVehiculoInactivoFacadeLocal vehiculoInactivoEjb) {
        this.vehiculoInactivoEjb = vehiculoInactivoEjb;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }
}
