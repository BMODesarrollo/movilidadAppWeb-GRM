/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DanoFlotaComponenteFacadeLocal;
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
import com.movilidad.ejb.DanoFlotaSolucionValorFacadeLocal;
import com.movilidad.model.DanoFlotaComponente;
import com.movilidad.model.DanoFlotaSolucionValor;

/**
 *
 * @author Julián Arévalo
 */
@Named(value = "danoFlotaSolucionValorJSF")
@ViewScoped
public class DanoFlotaSolucionValorJSF implements Serializable {

    @EJB
    private DanoFlotaSolucionValorFacadeLocal danoFlotaSolucionValorFacadeLocal;

    @EJB
    private DanoFlotaComponenteFacadeLocal danoFlotaComponenteFacadeLocal;

    private List<DanoFlotaSolucionValor> listDanoFlotaSolucionValor;

    private List<DanoFlotaComponente> listDanoFlotaComponente;

    private DanoFlotaSolucionValor danoFlotaSolucionValor;

    private Integer idComponente;

    private Integer idSolucionValor;

    private final PrimeFaces current = PrimeFaces.current();
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public DanoFlotaSolucionValorJSF() {
    }

    @PostConstruct
    public void init() {
        idSolucionValor = 0;
        idComponente = 0;
        listDanoFlotaSolucionValor = danoFlotaSolucionValorFacadeLocal.getAllActivo();
        listDanoFlotaComponente = danoFlotaComponenteFacadeLocal.getAllActivo();
        danoFlotaSolucionValor = new DanoFlotaSolucionValor();
    }

    public void activarDesactivarSolucionValor(DanoFlotaSolucionValor obj, int opc) {
        if (opc == 0) {
            obj.setEstadoReg(1);
        } else {
            obj.setEstadoReg(0);
        }
        danoFlotaSolucionValorFacadeLocal.edit(obj);
        PrimeFaces.current().executeScript("PF('dt_param_severidad').clearFilters();");
        init();
        MovilidadUtil.addSuccessMessage("Éxito.");
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {

        if (idComponente == 0) {
            MovilidadUtil.addErrorMessage("Seleccione Grupo.");
            return;
        }
        DanoFlotaComponente componente = new DanoFlotaComponente();
        componente = danoFlotaComponenteFacadeLocal.find(idComponente);

//        if (validarExistencia(danoFlotaComponente, grupo)) {
//            MovilidadUtil.addErrorMessage("El componente ya existe.");
//            return;
//        }
        danoFlotaSolucionValor.setDanoFlotaComponente(componente);
        danoFlotaSolucionValor.setUsername(user.getUsername());
        danoFlotaSolucionValor.setEstadoReg(0);
        danoFlotaSolucionValor.setCreado(MovilidadUtil.fechaCompletaHoy());
        danoFlotaSolucionValorFacadeLocal.create(danoFlotaSolucionValor);
        MovilidadUtil.addSuccessMessage("Se guardó el registro correctamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
        init();
    }

    @Transactional
    public void editarTransactional(DanoFlotaSolucionValor obj) {
        if (idComponente == 0) {
            MovilidadUtil.addErrorMessage("Seleccione un componentes.");
            return;
        }
        DanoFlotaComponente componente = new DanoFlotaComponente();
        componente = danoFlotaComponenteFacadeLocal.find(idComponente);

        danoFlotaSolucionValor.setDanoFlotaComponente(componente);
        danoFlotaSolucionValor.setUsername(user.getUsername());
        danoFlotaSolucionValor.setEstadoReg(0);
        danoFlotaSolucionValor.setCreado(MovilidadUtil.fechaCompletaHoy());

        try {
            danoFlotaSolucionValorFacadeLocal.edit(danoFlotaSolucionValor);
        } catch (Exception e) {
        }
        MovilidadUtil.addSuccessMessage("Se actualizó el registro correctamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
        init();
    }

    public void preEdit(DanoFlotaSolucionValor obj) {
        idSolucionValor = obj.getIdSolucionValor();
        idComponente = obj.getDanoFlotaComponente().getIdDanoComponente();
        danoFlotaSolucionValor = obj;
    }

    public void limpiarForm() {
        danoFlotaSolucionValor = new DanoFlotaSolucionValor();
        idSolucionValor = 0;
        idComponente = 0;
    }

    public void cerrar() {
        limpiarForm();
        init();
        MovilidadUtil.addErrorMessage("Operación cancelada");
    }

    public String quitarEspaciosYMayusculas(String cadena) {
        String sinEspacios = cadena.replaceAll("\\s+", "");
        String enMayusculas = sinEspacios.toUpperCase();
        return enMayusculas;
    }

    public DanoFlotaSolucionValorFacadeLocal getDanoFlotaSolucionValorFacadeLocal() {
        return danoFlotaSolucionValorFacadeLocal;
    }

    public void setDanoFlotaSolucionValorFacadeLocal(DanoFlotaSolucionValorFacadeLocal danoFlotaSolucionValorFacadeLocal) {
        this.danoFlotaSolucionValorFacadeLocal = danoFlotaSolucionValorFacadeLocal;
    }

    public DanoFlotaComponenteFacadeLocal getDanoFlotaComponenteFacadeLocal() {
        return danoFlotaComponenteFacadeLocal;
    }

    public void setDanoFlotaComponenteFacadeLocal(DanoFlotaComponenteFacadeLocal danoFlotaComponenteFacadeLocal) {
        this.danoFlotaComponenteFacadeLocal = danoFlotaComponenteFacadeLocal;
    }

    public List<DanoFlotaSolucionValor> getListDanoFlotaSolucionValor() {
        return listDanoFlotaSolucionValor;
    }

    public void setListDanoFlotaSolucionValor(List<DanoFlotaSolucionValor> listDanoFlotaSolucionValor) {
        this.listDanoFlotaSolucionValor = listDanoFlotaSolucionValor;
    }

    public List<DanoFlotaComponente> getListDanoFlotaComponente() {
        return listDanoFlotaComponente;
    }

    public void setListDanoFlotaComponente(List<DanoFlotaComponente> listDanoFlotaComponente) {
        this.listDanoFlotaComponente = listDanoFlotaComponente;
    }

    public DanoFlotaSolucionValor getDanoFlotaSolucionValor() {
        return danoFlotaSolucionValor;
    }

    public void setDanoFlotaSolucionValor(DanoFlotaSolucionValor danoFlotaSolucionValor) {
        this.danoFlotaSolucionValor = danoFlotaSolucionValor;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public Integer getIdSolucionValor() {
        return idSolucionValor;
    }

    public void setIdSolucionValor(Integer idSolucionValor) {
        this.idSolucionValor = idSolucionValor;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

}
