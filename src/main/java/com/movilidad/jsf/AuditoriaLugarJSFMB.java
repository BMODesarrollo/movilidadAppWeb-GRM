/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaAreaComunFacadeLocal;
import com.movilidad.ejb.AuditoriaEstacionFacadeLocal;
import com.movilidad.ejb.AuditoriaLugarFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.AuditoriaLugar;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "audiLugarJSFMB")
@ViewScoped
public class AuditoriaLugarJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaLugarJSFMB
     */
    public AuditoriaLugarJSFMB() {
    }

    @EJB
    private AuditoriaLugarFacadeLocal audiLugarEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;

    @EJB
    private VehiculoTipoFacadeLocal VehiculoTipoEJB;

    private List<AuditoriaLugar> list;
    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;
    private AuditoriaLugar auditoriaLugar;
    private boolean bus;
    private int idVehiculoTipo;
    private boolean estacion;
    private boolean areaComun;
    private boolean empleado;

    private List<VehiculoTipo> lstVehiculoTipo;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        consultar();
    }

    public void consultar() {
        list = audiLugarEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
    }

    public void edit() {
        if (validarCampos()) {
            return;
        }
        editTransactional();
        consultar();
    }

    @Transactional
    public void editTransactional() {
        auditoriaLugar.setIdVehiculoTipo(idVehiculoTipo == 0 ? null : new VehiculoTipo(idVehiculoTipo));
        auditoriaLugar.setBus(bus);
        auditoriaLugar.setEstacion(estacion);
        auditoriaLugar.setAreaComun(areaComun);
        auditoriaLugar.setEmpleado(empleado);
        auditoriaLugar.setModificado(MovilidadUtil.fechaCompletaHoy());
        auditoriaLugar.setIdVehiculoTipo((idVehiculoTipo == 0 || !bus) ? null : new VehiculoTipo(idVehiculoTipo));
        audiLugarEJB.edit(auditoriaLugar);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        PrimeFaces.current().executeScript("PF('crear_audi_lugar_dialog_wv').hide()");
    }

    public void cargarVehiculoTipo(boolean goToDb) {
        if (lstVehiculoTipo == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstVehiculoTipo = VehiculoTipoEJB.findAllEstadoR();
        }
    }

    public void guardar() {
        if (validarCampos()) {
            return;
        }
        guardarTransactional();
        consultar();
    }

    boolean validarCampos() {
        if (auditoriaLugar.getNombre() == null || auditoriaLugar.getNombre().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un nombre.");
            return true;
        }
        if (auditoriaLugar.getDescripcion() == null || auditoriaLugar.getDescripcion().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un descripción.");
            return true;
        }
        if (!(bus || estacion || areaComun || empleado)) {
            MovilidadUtil.addErrorMessage("Debe estar una opción en sí");
            return true;
        }
        return false;
    }

    @Transactional
    public void guardarTransactional() {
        auditoriaLugar.setBus(bus);
        auditoriaLugar.setEstacion(estacion);
        auditoriaLugar.setAreaComun(areaComun);
        auditoriaLugar.setEmpleado(empleado);
        auditoriaLugar.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoriaLugar.setIdParamArea(paramAreaUsr.getIdParamArea());
        auditoriaLugar.setIdVehiculoTipo((idVehiculoTipo == 0 || !bus) ? null : new VehiculoTipo(idVehiculoTipo));
        auditoriaLugar.setEstadoReg(0);
        audiLugarEJB.create(auditoriaLugar);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        auditoriaLugar = new AuditoriaLugar();
        bus = false;
        estacion = false;
        areaComun = false;
        empleado = false;

    }

    public void updateComponent(int opc) {
        if (opc == 1 && bus) {
            empleado = estacion = areaComun = false;
        }
        if (opc == 2 && estacion) {
            empleado = bus = areaComun = false;
        }
        if (opc == 3 && areaComun) {
            empleado = bus = estacion = false;
        }
        if (opc == 4 && empleado) {
            bus = estacion = areaComun = false;
        }
    }

    public void preEdit(AuditoriaLugar obj) {
        if (audiLugarEJB.findByIdAuditoriaLugar(obj.getIdAuditoriaLugar()) != null) {
            MovilidadUtil.addErrorMessage("No se puede Modificar este lugar, ya se utilizó en una auditoría.");
            return;
        }
        cargarVehiculoTipo(false);
        idVehiculoTipo = obj.getIdVehiculoTipo() != null ? obj.getIdVehiculoTipo().getIdVehiculoTipo() : 0;
        bus = estacion = areaComun = empleado = false;
        if (obj.isBus()) {
            bus = true;
        }
        if (obj.isEstacion()) {
            estacion = true;
        }
        if (obj.isAreaComun()) {
            areaComun = true;
        }
        if (obj.isEmpleado()) {
            empleado = true;
        }
        auditoriaLugar = obj;
        MovilidadUtil.openModal("crear_audi_lugar_dialog_wv");
    }

    public void preGuardar() {
        empleado = bus = estacion = areaComun = false;
        auditoriaLugar = new AuditoriaLugar();
        cargarVehiculoTipo(false);
    }

    public List<AuditoriaLugar> getList() {
        return list;
    }

    public void setList(List<AuditoriaLugar> list) {
        this.list = list;
    }

    public AuditoriaLugar getAuditoriaLugar() {
        return auditoriaLugar;
    }

    public void setAuditoriaLugar(AuditoriaLugar auditoriaLugar) {
        this.auditoriaLugar = auditoriaLugar;
    }

    public boolean isBus() {
        return bus;
    }

    public void setBus(boolean bus) {
        this.bus = bus;
    }

    public boolean isEstacion() {
        return estacion;
    }

    public void setEstacion(boolean estacion) {
        this.estacion = estacion;
    }

    public boolean isAreaComun() {
        return areaComun;
    }

    public void setAreaComun(boolean areaComun) {
        this.areaComun = areaComun;
    }

    public boolean isEmpleado() {
        return empleado;
    }

    public void setEmpleado(boolean empleado) {
        this.empleado = empleado;
    }

    public int getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(int idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public List<VehiculoTipo> getLstVehiculoTipo() {
        return lstVehiculoTipo;
    }

    public void setLstVehiculoTipo(List<VehiculoTipo> lstVehiculoTipo) {
        this.lstVehiculoTipo = lstVehiculoTipo;
    }

}
