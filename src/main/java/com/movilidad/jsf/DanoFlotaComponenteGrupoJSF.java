/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.model.DanoFlotaComponenteGrupo;
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
import com.movilidad.ejb.DanoFlotaComponenteGrupoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.VehiculoTipo;

/**
 *
 * @author Julián Arévalo
 */
@Named(value = "danoFlotaComponenteGrupoJSF")
@ViewScoped
public class DanoFlotaComponenteGrupoJSF implements Serializable {

    @EJB
    private DanoFlotaComponenteGrupoFacadeLocal danoFlotaParamGrupoFacadeLocal;
    
    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoFacadeLocal;

    private List<DanoFlotaComponenteGrupo> listGrupo;
    
    private List<VehiculoTipo> listTipoVehiculo;

    private DanoFlotaComponenteGrupo danoFlotaComponenteGrupo;

    private Integer idGrupo;
    
    private Integer idTipologia;

    private final PrimeFaces current = PrimeFaces.current();
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public DanoFlotaComponenteGrupoJSF() {
    }

    @PostConstruct
    public void init() {
        idGrupo = 0;
        idTipologia=0;
        listGrupo = danoFlotaParamGrupoFacadeLocal.getAllActivo();
        listTipoVehiculo = vehiculoTipoFacadeLocal.findAllEstadoR();
        danoFlotaComponenteGrupo = new DanoFlotaComponenteGrupo();
    }

    public void activarDesactivarGrupo(DanoFlotaComponenteGrupo obj, int opc) {
        if (opc == 0) {
            obj.setEstadoReg(1);
        } else {
            obj.setEstadoReg(0);
        }
        danoFlotaParamGrupoFacadeLocal.edit(obj);
        init();
        MovilidadUtil.addSuccessMessage("Éxito.");        
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        if(idTipologia==0){
            MovilidadUtil.addErrorMessage("Seleccione tipo vehículo.");
            return;
        }
        danoFlotaComponenteGrupo.setVehiculoTipo(vehiculoTipoFacadeLocal.find(idTipologia));
        danoFlotaComponenteGrupo.setUsername(user.getUsername());
        danoFlotaComponenteGrupo.setEstadoReg(0);
        danoFlotaComponenteGrupo.setCreado(MovilidadUtil.fechaCompletaHoy());
        danoFlotaParamGrupoFacadeLocal.create(danoFlotaComponenteGrupo);
        MovilidadUtil.addSuccessMessage("Se guardó el registro correctamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
        init();
    }

    @Transactional
    public void editarTransactional(DanoFlotaComponenteGrupo obj) {
         if(idTipologia==0){
            MovilidadUtil.addErrorMessage("Seleccione tipo vehículo.");
            return;
        }
        danoFlotaComponenteGrupo.setCreado(obj.getCreado());
        danoFlotaComponenteGrupo.setVehiculoTipo(vehiculoTipoFacadeLocal.find(idTipologia));
        danoFlotaParamGrupoFacadeLocal.edit(danoFlotaComponenteGrupo);
        MovilidadUtil.addSuccessMessage("Se actualizó el registro correctamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
        init();
    }

    public void preEdit(DanoFlotaComponenteGrupo obj) {
        idGrupo = obj.getIdComponenteGrupo();
        idTipologia= obj.getVehiculoTipo().getIdVehiculoTipo();
        danoFlotaComponenteGrupo = obj;
    }

    public void limpiarForm() {
        danoFlotaComponenteGrupo = new DanoFlotaComponenteGrupo();
        idGrupo = 0;
        idTipologia =0;
    }
    
    public void cerrar() {
        limpiarForm();
        init();
        MovilidadUtil.addErrorMessage("Operación cancelada");
    }

    public DanoFlotaComponenteGrupoFacadeLocal getDanoFlotaParamGrupoFacadeLocal() {
        return danoFlotaParamGrupoFacadeLocal;
    }

    public void setDanoFlotaParamGrupoFacadeLocal(DanoFlotaComponenteGrupoFacadeLocal danoFlotaParamGrupoFacadeLocal) {
        this.danoFlotaParamGrupoFacadeLocal = danoFlotaParamGrupoFacadeLocal;
    }

    public List<DanoFlotaComponenteGrupo> getListGrupo() {
        return listGrupo;
    }

    public void setListGrupo(List<DanoFlotaComponenteGrupo> listGrupo) {
        this.listGrupo = listGrupo;
    }

    public DanoFlotaComponenteGrupo getDanoFlotaComponenteGrupo() {
        return danoFlotaComponenteGrupo;
    }

    public void setDanoFlotaComponenteGrupo(DanoFlotaComponenteGrupo danoFlotaComponenteGrupo) {
        this.danoFlotaComponenteGrupo = danoFlotaComponenteGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public Integer getIdTipologia() {
        return idTipologia;
    }

    public void setIdTipologia(Integer idTipologia) {
        this.idTipologia = idTipologia;
    }

    public VehiculoTipoFacadeLocal getVehiculoTipoFacadeLocal() {
        return vehiculoTipoFacadeLocal;
    }

    public void setVehiculoTipoFacadeLocal(VehiculoTipoFacadeLocal vehiculoTipoFacadeLocal) {
        this.vehiculoTipoFacadeLocal = vehiculoTipoFacadeLocal;
    }

    public List<VehiculoTipo> getListTipoVehiculo() {
        return listTipoVehiculo;
    }

    public void setListTipoVehiculo(List<VehiculoTipo> listTipoVehiculo) {
        this.listTipoVehiculo = listTipoVehiculo;
    }
       
}
