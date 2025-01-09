package com.movilidad.jsf;

import com.movilidad.ejb.TanqueoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Tanqueo;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("tanqueoJsf")
@ViewScoped
public class TanqueoController implements Serializable {

    @EJB
    private TanqueoFacadeLocal tanqueoFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;

    private Tanqueo tanqueo = null;
    private List<Tanqueo> listTanqueo = new ArrayList<>();

    private String c_coVehiculo = "";
    private boolean b_control = true;
    private Date fechaInicio;
    private Date fechaFin;
    private int i_kmIniUT;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public TanqueoController() {
    }

    @PostConstruct
    void init() {
        fechaInicio = new Date();
        fechaFin = new Date();
        listTanqueo = tanqueoFacadeLocal.findTanqueoDates(fechaInicio, fechaFin);
        b_control = true;
        c_coVehiculo = "";
        i_kmIniUT = 0;
    }

    public void reset() {
        tanqueo = new Tanqueo();
        c_coVehiculo = "";
        listTanqueo = tanqueoFacadeLocal.findTanqueoDates(fechaInicio, fechaFin);
        b_control = true;
        i_kmIniUT = 0;
    }

    public void preprareGuardar() {
        tanqueo = new Tanqueo();
    }

    public void guardar() {
        if (tanqueo != null) {
            if (tanqueo.getKmInicial() > tanqueo.getKmFinal()) {
                MovilidadUtil.addErrorMessage("Km Final no puede ser menor a Km Inicial");
                PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
                return;
            }
            if (tanqueo.getKmInicial() <= 0 || tanqueo.getKmFinal() <= 0) {
                MovilidadUtil.addErrorMessage("Km Inicial o Km Final deben ser mayores a 0");
                PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
                return;
            }
            if (tanqueo.getVolumen().compareTo(new BigDecimal(0)) == 0) {
                MovilidadUtil.addErrorMessage("Combustible agregado debe ser mayor a 0");
                PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
                return;
            }
            if ((tanqueo.getKmFinal() - tanqueo.getKmInicial()) > 650) {
                MovilidadUtil.addErrorMessage("La diferencia entre el kilometraje no es permitida");
                PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
                return;
            }
            tanqueo.setAlerta(1);
            tanqueo.setUsername(user.getUsername());
            tanqueo.setCreado(new Date());
            tanqueo.setEstadoReg(0);
            tanqueoFacadeLocal.create(tanqueo);
            MovilidadUtil.addSuccessMessage("Tanqueo realizado al vehículo " + tanqueo.getIdVehiculo().getCodigo() + " con exito");
            PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
            reset();
            return;
        }
        MovilidadUtil.addErrorMessage("Error del sistema");
        PrimeFaces.current().ajax().update("form-tanqueo:msg-tq");
        reset();
    }

    public void prepareEditar() {
        if (tanqueo != null) {
            c_coVehiculo = tanqueo.getIdVehiculo().getCodigo();
            return;
        }
        MovilidadUtil.addErrorMessage("Error al cargar registro de tanqueo");
        PrimeFaces.current().ajax().update("form-tanqueo:msg-tq");
    }

    public void actualizar() {
        if (tanqueo != null) {
            if (tanqueo.getKmInicial() > tanqueo.getKmFinal()) {
                MovilidadUtil.addErrorMessage("Km Final no puede ser menor a Km Inicial");
                PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
                return;
            }
            if (tanqueo.getKmInicial() <= 0 || tanqueo.getKmFinal() <= 0) {
                MovilidadUtil.addErrorMessage("Km Inicial o Km Final deben ser mayores a 0");
                PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
                return;
            }
            if (tanqueo.getVolumen().compareTo(new BigDecimal(0)) == 0) {
                MovilidadUtil.addErrorMessage("Combustible agregado debe ser mayor a 0");
                PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
                return;
            }
            if ((tanqueo.getKmFinal() - tanqueo.getKmInicial()) > 650) {
                MovilidadUtil.addErrorMessage("La diferencia entre el kilometraje no es permitida");
                PrimeFaces.current().ajax().update("tanqueoFormCreate:messages");
                return;
            }
            tanqueo.setAlerta(1);
            tanqueo.setUsername(user.getUsername());
            tanqueoFacadeLocal.edit(tanqueo);
            MovilidadUtil.addSuccessMessage("Tanqueo editado al vehículo " + tanqueo.getIdVehiculo().getCodigo() + " con exito");
            PrimeFaces.current().ajax().update("form-tanqueo:msg-tq");
            PrimeFaces.current().executeScript("PF('tanqueoEditDialog').hide()");
            reset();
        }
    }

    public void cargarVehiculo() {
        if (c_coVehiculo.equals("")) {
            MovilidadUtil.addErrorMessage("Vehículo no valido");
            tanqueo.setIdVehiculo(null);
            b_control = true;
            return;
        }
        Vehiculo vehiculo = vehiculoFacadeLocal.getVehiculo(c_coVehiculo,0);
        if (vehiculo != null) {
            Date aux_date = tanqueo.getFecha();
            int id_aux = vehiculo.getIdVehiculo();
            List<Tanqueo> aux_list = tanqueoFacadeLocal.findTanqueoIngresado(aux_date, id_aux);
            if (!aux_list.isEmpty()) {
                MovilidadUtil.addErrorMessage("El vehículo seleccionado para la fecha ingresada ya cuenta con registro de tanqueo");
                b_control = true;
                tanqueo.setIdVehiculo(null);
                return;
            }
        }
        if (vehiculo != null) {
            tanqueo.setIdVehiculo(vehiculo);
            c_coVehiculo = vehiculo.getCodigo();
            MovilidadUtil.addSuccessMessage("Vehículo cargado correctamente");
            ultimoTanqueo(vehiculo);
            b_control = false;
            return;
        }
        MovilidadUtil.addErrorMessage("Vehículo no existe");
        tanqueo.setIdVehiculo(null);
        b_control = true;
    }

    void ultimoTanqueo(Vehiculo v) {
        int i_aux = v.getIdVehiculo();
        Tanqueo aux_tanqueo = tanqueoFacadeLocal.findUltimoTanqueo(i_aux);
        if (aux_tanqueo != null) {
            tanqueo.setKmInicial(aux_tanqueo.getKmFinal());
            i_kmIniUT = aux_tanqueo.getKmFinal();
            return;
        }
        i_kmIniUT = 0;
    }

    public void getByDateRange() {
        if (fechaFin.compareTo(fechaInicio) < 0) {
            MovilidadUtil.addErrorMessage("Fecha Fin no puede ser menor a Fecha Inicio");
            fechaInicio = new Date();
            fechaFin = new Date();
            listTanqueo = tanqueoFacadeLocal.findTanqueoDates(fechaInicio, fechaFin);
            return;
        }
        listTanqueo = tanqueoFacadeLocal.findTanqueoDates(fechaInicio, fechaFin);
    }

    public void controlCambioFecha() {
        c_coVehiculo = "";
        b_control = true;
        tanqueo.setKmFinal(0);
    }

    public boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_TC")) {
//                System.out.println(auth.getAuthority());
                return true;
            }
        }
        return false;
    }

    public Tanqueo getTanqueo() {
        return tanqueo;
    }

    public void setTanqueo(Tanqueo tanqueo) {
        this.tanqueo = tanqueo;
    }

    public List<Tanqueo> getListTanqueo() {
        return listTanqueo;
    }

    public String getC_coVehiculo() {
        return c_coVehiculo;
    }

    public void setC_coVehiculo(String c_coVehiculo) {
        this.c_coVehiculo = c_coVehiculo;
    }

    public boolean isB_control() {
        return b_control;
    }

    public void setB_control(boolean b_control) {
        this.b_control = b_control;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getI_kmIniUT() {
        return i_kmIniUT;
    }

    public void setI_kmIniUT(int i_kmIniUT) {
        this.i_kmIniUT = i_kmIniUT;
    }

}
