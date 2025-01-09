/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DanoFlotaComponenteFacadeLocal;
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
import com.movilidad.model.DanoFlotaComponente;
import java.util.Objects;

/**
 *
 * @author Julián Arévalo
 */
@Named(value = "danoFlotaComponenteJSF")
@ViewScoped
public class DanoFlotaComponenteJSF implements Serializable {

    @EJB
    private DanoFlotaComponenteFacadeLocal danoFlotaComponenteFacadeLocal;

    @EJB
    private DanoFlotaComponenteGrupoFacadeLocal danoFlotaComponenteGrupoFacadeLocal;

    private List<DanoFlotaComponente> listDanoFlotaComponente;

    private List<DanoFlotaComponenteGrupo> listGrupo;

    private DanoFlotaComponente danoFlotaComponente;

    private Integer idComponente;

    private Integer idGrupo;

    private final PrimeFaces current = PrimeFaces.current();
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public DanoFlotaComponenteJSF() {
    }

    @PostConstruct
    public void init() {
        idGrupo = 0;
        idComponente = 0;
        listGrupo = danoFlotaComponenteGrupoFacadeLocal.getAllActivo();
        listDanoFlotaComponente = danoFlotaComponenteFacadeLocal.getAllActivo();
        danoFlotaComponente = new DanoFlotaComponente();
    }

    public void activarDesactivarComponente(DanoFlotaComponente obj, int opc) {
        if (opc == 0) {
            obj.setEstadoReg(1);
        } else {
            obj.setEstadoReg(0);
        }
        danoFlotaComponenteFacadeLocal.edit(obj);
        init();
        MovilidadUtil.addSuccessMessage("Éxito.");
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        danoFlotaComponente.setNombre(quitarEspaciosYMayusculas(this.danoFlotaComponente.getNombre()));
        if (idGrupo == 0) {
            MovilidadUtil.addErrorMessage("Seleccione Grupo.");
            return;
        }
        DanoFlotaComponenteGrupo grupo = new DanoFlotaComponenteGrupo();
        grupo = danoFlotaComponenteGrupoFacadeLocal.find(idGrupo);

        if (validarExistencia(danoFlotaComponente, grupo)) {
            MovilidadUtil.addErrorMessage("El componente ya existe.");
            return;
        }
        danoFlotaComponente.setDanoFlotaGrupo(grupo);
        danoFlotaComponente.setUsername(user.getUsername());
        danoFlotaComponente.setEstadoReg(0);
        danoFlotaComponente.setAfectaPm(0);
        danoFlotaComponente.setCreado(MovilidadUtil.fechaCompletaHoy());
        danoFlotaComponenteFacadeLocal.create(danoFlotaComponente);
        MovilidadUtil.addSuccessMessage("Se guardó el registro correctamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
        init();
    }

    @Transactional
    public void editarTransactional(DanoFlotaComponente obj) {
        danoFlotaComponente.setNombre(quitarEspaciosYMayusculas(this.danoFlotaComponente.getNombre()));
        if (idGrupo == 0) {
            MovilidadUtil.addErrorMessage("Seleccione Grupo.");
            return;
        }
        DanoFlotaComponenteGrupo grupo = new DanoFlotaComponenteGrupo();
        grupo = danoFlotaComponenteGrupoFacadeLocal.find(idGrupo);
        if (validarExistencia(danoFlotaComponente, grupo)) {
            MovilidadUtil.addErrorMessage("El componente ya existe.");
            return;
        }
        danoFlotaComponente.setAfectaPm(0);
        danoFlotaComponente.setCreado(obj.getCreado());
        danoFlotaComponente.setDanoFlotaGrupo(danoFlotaComponenteGrupoFacadeLocal.find(idGrupo));
        try {
            danoFlotaComponenteFacadeLocal.edit(danoFlotaComponente);
        } catch (Exception e) {
        }
        MovilidadUtil.addSuccessMessage("Se actualizó el registro correctamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
        init();
    }

    public void preEdit(DanoFlotaComponente obj) {
        idComponente = obj.getIdDanoComponente();
        idGrupo = obj.getDanoFlotaGrupo().getIdComponenteGrupo();
        danoFlotaComponente = obj;
    }

    public void limpiarForm() {
        danoFlotaComponente = new DanoFlotaComponente();
        idGrupo = 0;
        idComponente = 0;
    }

    public void cerrar() {
        limpiarForm();
        init();
        MovilidadUtil.addErrorMessage("Operación cancelada");
    }

    public boolean validarExistencia(DanoFlotaComponente obj, DanoFlotaComponenteGrupo grupo) {
        try {
            List<DanoFlotaComponente> listComponenteValidar = danoFlotaComponenteFacadeLocal.
                    findPieces("'" + danoFlotaComponente.getNombre() + "'", grupo.getVehiculoTipo().getIdVehiculoTipo());
            if (listComponenteValidar.size()>0) {
                return !Objects.equals(listComponenteValidar.get(0).getIdDanoComponente(), obj.getIdDanoComponente());
            } else {
                return !listComponenteValidar.isEmpty();
            }
        } catch (Exception e) {
            return true;
        }
    }

    public String quitarEspaciosYMayusculas(String cadena) {
        String sinEspacios = cadena.replaceAll("\\s+", "");
        String enMayusculas = sinEspacios.toUpperCase();
        return enMayusculas;
    }

    public DanoFlotaComponenteFacadeLocal getDanoFlotaComponenteFacadeLocal() {
        return danoFlotaComponenteFacadeLocal;
    }

    public void setDanoFlotaComponenteFacadeLocal(DanoFlotaComponenteFacadeLocal danoFlotaComponenteFacadeLocal) {
        this.danoFlotaComponenteFacadeLocal = danoFlotaComponenteFacadeLocal;
    }

    public DanoFlotaComponenteGrupoFacadeLocal getDanoFlotaComponenteGrupoFacadeLocal() {
        return danoFlotaComponenteGrupoFacadeLocal;
    }

    public void setDanoFlotaComponenteGrupoFacadeLocal(DanoFlotaComponenteGrupoFacadeLocal danoFlotaComponenteGrupoFacadeLocal) {
        this.danoFlotaComponenteGrupoFacadeLocal = danoFlotaComponenteGrupoFacadeLocal;
    }

    public List<DanoFlotaComponente> getListDanoFlotaComponente() {
        return listDanoFlotaComponente;
    }

    public void setListDanoFlotaComponente(List<DanoFlotaComponente> listDanoFlotaComponente) {
        this.listDanoFlotaComponente = listDanoFlotaComponente;
    }

    public List<DanoFlotaComponenteGrupo> getListGrupo() {
        return listGrupo;
    }

    public void setListGrupo(List<DanoFlotaComponenteGrupo> listGrupo) {
        this.listGrupo = listGrupo;
    }

    public DanoFlotaComponente getDanoFlotaComponente() {
        return danoFlotaComponente;
    }

    public void setDanoFlotaComponente(DanoFlotaComponente danoFlotaComponente) {
        this.danoFlotaComponente = danoFlotaComponente;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
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

}
