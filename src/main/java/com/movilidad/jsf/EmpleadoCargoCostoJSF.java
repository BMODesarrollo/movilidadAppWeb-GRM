/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoCargoCostoFacadeLocal;
import com.movilidad.ejb.ParamAreaCargoFacadeLocal;
import com.movilidad.model.EmpleadoCargoCosto;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaCargo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "empleadoCargoCostoJSF")
@ViewScoped
public class EmpleadoCargoCostoJSF implements Serializable {

    @EJB
    private EmpleadoCargoCostoFacadeLocal empleadoCargoCostoFacadeLocal;
    @EJB
    private ParamAreaCargoFacadeLocal paramAreaCargoFacadeLocal;

    private EmpleadoCargoCosto empleadoCargoCosto;
    private List<EmpleadoCargoCosto> listEmpleadoCargoCosto;
    private List<EmpleadoTipoCargo> listEmpleadoTipo;

    private ParamArea paramArea;
    private Integer i_idParamCargo;
    private Date fechaDesde;
    private Date fechaHasta;
    private String c_usernameLogin;
    private boolean bUserAuth;

    UserExtended user;

    public EmpleadoCargoCostoJSF() {
    }

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bUserAuth = getUserAuthAll();
        paramArea = null;
        empleadoCargoCosto = null;
        listEmpleadoCargoCosto = new ArrayList<>();
        listEmpleadoTipo = new ArrayList<>();
        i_idParamCargo = null;
        fechaDesde = new Date();
        fechaHasta = new Date();
        c_usernameLogin = user.getUsername();
        cargarCargos();
        if (bUserAuth) {
            listEmpleadoCargoCosto = empleadoCargoCostoFacadeLocal.findAllEstadoReg();
        } else {
            if (paramArea != null) {
                listEmpleadoCargoCosto = empleadoCargoCostoFacadeLocal.findAllByAreaEstadoReg(paramArea.getIdParamArea());
            }
        }
    }

    public void guardar() {
        try {
            if (empleadoCargoCosto != null) {
                if (validarDatos()) {
                    return;
                }
                if (controlCambios(i_idParamCargo)) {
                    MovilidadUtil.addErrorMessage("Procedimiento no valido");
                    return;
                }
                if (verificarProceso(empleadoCargoCosto.getDesde(), i_idParamCargo) != null) {
                    return;
                }
                if (verificarProceso(empleadoCargoCosto.getHasta(), i_idParamCargo) != null) {
                    return;
                }
                cargarObjetos();
                empleadoCargoCosto.setUsername(user.getUsername());
                empleadoCargoCosto.setCreado(new Date());
                empleadoCargoCosto.setModificado(new Date());
                empleadoCargoCosto.setEstadoReg(0);
                empleadoCargoCostoFacadeLocal.create(empleadoCargoCosto);
                reset();
                empleadoCargoCosto = new EmpleadoCargoCosto();
                cargarCargos();
                if (bUserAuth) {
                    listEmpleadoCargoCosto = empleadoCargoCostoFacadeLocal.findAllEstadoReg();
                } else {
                    if (paramArea != null) {
                        listEmpleadoCargoCosto = empleadoCargoCostoFacadeLocal.findAllByAreaEstadoReg(paramArea.getIdParamArea());
                    }
                }
                MovilidadUtil.addSuccessMessage("Procedimineto registrado con éxito");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void prepareGuardar() {
        empleadoCargoCosto = new EmpleadoCargoCosto();
        cargarCargos();
    }

    public void editar() {
        try {
            EmpleadoCargoCosto e;
            if (validarDatos()) {
                return;
            }
            if (controlCambios(i_idParamCargo)) {
                MovilidadUtil.addErrorMessage("Procedimiento no valido");
                return;
            }
            e = verificarProceso(empleadoCargoCosto.getDesde(), i_idParamCargo);
            if (e != null) {
                if (!empleadoCargoCosto.getIdEmpleadoCargoCosto().equals(e.getIdEmpleadoCargoCosto())) {
                    return;
                }
            }
            e = verificarProceso(empleadoCargoCosto.getHasta(), i_idParamCargo);
            if (e != null) {
                if (!empleadoCargoCosto.getIdEmpleadoCargoCosto().equals(e.getIdEmpleadoCargoCosto())) {
                    return;
                }
            }
            eliminarMensages();
            cargarObjetos();
            empleadoCargoCosto.setModificado(new Date());
            empleadoCargoCostoFacadeLocal.edit(empleadoCargoCosto);
            cargarCargos();
            if (bUserAuth) {
                listEmpleadoCargoCosto = empleadoCargoCostoFacadeLocal.findAllEstadoReg();
            } else {
                if (paramArea != null) {
                    listEmpleadoCargoCosto = empleadoCargoCostoFacadeLocal.findAllByAreaEstadoReg(paramArea.getIdParamArea());
                }
            }
            reset();
            MovilidadUtil.addSuccessMessage("Procedimineto actualizado con éxito");
            PrimeFaces.current().executeScript("PF('empCargoDlg').hide()");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void onEmpleadoCargoCostoEvent(EmpleadoCargoCosto event) {
        empleadoCargoCosto = event;
        i_idParamCargo = empleadoCargoCosto.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo();
        cargarCargos();
    }

    public void reset() {
        empleadoCargoCosto = null;
        i_idParamCargo = null;
        // buscarRegistros();
    }

    public void buscarRegistros() {
        try {
            if (MovilidadUtil.fechasIgualMenorMayor(fechaHasta, fechaDesde, false) == 0) {
                MovilidadUtil.addErrorMessage("Fecha Hasta no puede ser inferior a Fecha Desde");
                return;
            }
            listEmpleadoCargoCosto = empleadoCargoCostoFacadeLocal.findAllRangoFechaEstadoReg(fechaDesde, fechaHasta);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    EmpleadoCargoCosto verificarProceso(Date d, Integer idCargo) {
        try {
            EmpleadoCargoCosto e;
            List<EmpleadoCargoCosto> listEmpleadoCargo = empleadoCargoCostoFacadeLocal.findForDateAndCargo(d, idCargo);
            if (listEmpleadoCargo != null) {
                if (!listEmpleadoCargo.isEmpty()) {
                    e = listEmpleadoCargo.get(0);
                    if (e != null) {
                        MovilidadUtil.addErrorMessage("El cargo "
                                + e.getIdEmpleadoTipoCargo().getNombreCargo() + " "
                                + "cuenta con Costo entre las fechas " + Util.dateFormat(e.getDesde()) + " y "
                                + Util.dateFormat(e.getHasta()) + ". "
                                + "No es posible el registro");
                        return e;
                    }
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public void recomendarFecha() {
        EmpleadoCargoCosto e;
        List<EmpleadoCargoCosto> listEmpleadoCargo = empleadoCargoCostoFacadeLocal.findMaxDateHasta(i_idParamCargo);
        if (listEmpleadoCargo != null) {
            if (!listEmpleadoCargo.isEmpty()) {
                e = listEmpleadoCargo.get(0);
                Date d = Util.DiasAFecha(e.getHasta(), 1);
                empleadoCargoCosto.setDesde(d);
            }
        }
    }

    boolean validarDatos() {
        try {
            if (MovilidadUtil.fechasIgualMenorMayor(empleadoCargoCosto.getHasta(), empleadoCargoCosto.getDesde(), false) == 0) {
                MovilidadUtil.addErrorMessage("Fecha Hasta no puede ser inferior a Fecha Desde");
                return true;
            }
            if (i_idParamCargo == null) {
                MovilidadUtil.addErrorMessage("Cargo es requerido");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        }
    }

    boolean controlCambios(Integer idCargo) {
        cargarCargos();
        for (EmpleadoTipoCargo etc : listEmpleadoTipo) {
            if (etc.getIdEmpleadoTipoCargo().equals(idCargo)) {
                return false;
            }
        }
        return true;
    }

    void cargarObjetos() {
        empleadoCargoCosto.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_idParamCargo));
    }

    void cargarCargos() {
        try {
            listEmpleadoTipo.clear();
            List<ParamAreaCargo> listPAC;
            if (bUserAuth) {
                listPAC = paramAreaCargoFacadeLocal.findAllEstadoReg();
                if (listPAC != null && !listPAC.isEmpty()) {
                    for (ParamAreaCargo pac : listPAC) {
                        listEmpleadoTipo.add(pac.getIdEmpleadoTipoCargo());
                    }
                }
            } else {
                listPAC = paramAreaCargoFacadeLocal.findByUsernameAllCargos(user.getUsername());
                if (listPAC != null && !listPAC.isEmpty()) {
                    paramArea = listPAC.get(0).getIdParamArea();
                    for (ParamAreaCargo pac : listPAC) {
                        listEmpleadoTipo.add(pac.getIdEmpleadoTipoCargo());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    boolean getUserAuthAll() {
        for (GrantedAuthority ua : user.getAuthorities()) {
            if (ua.getAuthority().equals(Util.USER_AUT_ROLE)) {
                return true;
            }
        }
        return false;
    }

    void eliminarMensages() {
        FacesContext context = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> it = context.getMessages();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public EmpleadoCargoCosto getEmpleadoCargoCosto() {
        return empleadoCargoCosto;
    }

    public void setEmpleadoCargoCosto(EmpleadoCargoCosto empleadoCargoCosto) {
        this.empleadoCargoCosto = empleadoCargoCosto;
    }

    public List<EmpleadoCargoCosto> getListEmpleadoCargoCosto() {
        return listEmpleadoCargoCosto;
    }

    public void setListEmpleadoCargoCosto(List<EmpleadoCargoCosto> listEmpleadoCargoCosto) {
        this.listEmpleadoCargoCosto = listEmpleadoCargoCosto;
    }

    public List<EmpleadoTipoCargo> getListEmpleadoTipo() {
        return listEmpleadoTipo;
    }

    public void setListEmpleadoTipo(List<EmpleadoTipoCargo> listEmpleadoTipo) {
        this.listEmpleadoTipo = listEmpleadoTipo;
    }

    public Integer getI_idParamCargo() {
        return i_idParamCargo;
    }

    public void setI_idParamCargo(Integer i_idParamCargo) {
        this.i_idParamCargo = i_idParamCargo;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getC_usernameLogin() {
        return c_usernameLogin;
    }

    public boolean isbUserAuth() {
        return bUserAuth;
    }

    public void setbUserAuth(boolean bUserAuth) {
        this.bUserAuth = bUserAuth;
    }

}
