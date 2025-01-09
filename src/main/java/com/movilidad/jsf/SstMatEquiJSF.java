/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstMatEquiFacadeLocal;
import com.movilidad.ejb.SstMatEquiTipoFacadeLocal;
import com.movilidad.model.SstEmpresa;
import com.movilidad.model.SstMatEqui;
import com.movilidad.model.SstMatEquiMarca;
import com.movilidad.model.SstMatEquiTipo;
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
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar toda los datos para el objeto SstMatEqui principal tabla
 * afectada sst_mat_equi
 *
 * @author cesar
 */
@Named(value = "sstMatEquiJSF")
@ViewScoped
public class SstMatEquiJSF implements Serializable {

    @EJB
    private SstMatEquiFacadeLocal sstMatEquiFacadeLocal;
    @EJB
    private SstMatEquiTipoFacadeLocal sstMatEquiTipoFacadeLocal;

    private SstMatEqui sstMatEqui;

    private List<SstMatEqui> listSstMatEqui;
    private List<SstMatEquiTipo> listSstMatEquiTipo;

    private String cNombreMatEquiAux;
    private Integer idSstMatEquiTipo;
    private Integer idSstMatEquiMarca;

    @Inject
    private SstTokenJSF aPIJSF;

    /**
     * Creates a new instance of SstMatEquiJSF
     */
    public SstMatEquiJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreMatEquiAux = "";
        idSstMatEquiTipo = null;
        idSstMatEquiMarca = null;
    }

    /**
     * Permite persistir la data del objeto SstMatEqui en la base de datos
     */
    public void guardar() {
        try {
            if (sstMatEqui != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst materiales equipo no se encuentra disponible");
                    return;
                }
                if (validarIds()) {
                    return;
                }
                cargarObjetos();
                SstEmpresa sstEmpresa = aPIJSF.getSstEmpresa();
                sstMatEqui.setIdSstEmpresa(sstEmpresa);
                sstMatEqui.setCreado(new Date());
                sstMatEqui.setModificado(new Date());
                sstMatEqui.setEstadoReg(0);
                sstMatEqui.setUsername(sstEmpresa.getUsrNombre());
                sstMatEquiFacadeLocal.create(sstMatEqui);
                MovilidadUtil.addSuccessMessage("Se a registrado sst materiales equipo correctamente");
                sstMatEqui = new SstMatEqui();
                init();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst materiales equipo");
        }
    }

    /**
     * Permite realizar un update del objeto SstMatEqui en la base de datos
     */
    public void actualizar() {
        try {
            if (sstMatEqui != null) {
                if (!cNombreMatEquiAux.equals(sstMatEqui.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst materiales equipo no se encuentra disponible");
                        return;
                    }
                }
                if (validarIds()) {
                    return;
                }
                cargarObjetos();
                sstMatEqui.setModificado(new Date());
                sstMatEquiFacadeLocal.edit(sstMatEqui);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst materiales equipo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst materiales equipo");
        }
    }

    /**
     * Permite crear la instancia del objeto SstMatEqui
     */
    public void prepareGuardar() {
        sstMatEqui = new SstMatEqui();
        init();
    }

    public void reset() {
        sstMatEqui = null;
        cNombreMatEquiAux = "";
        idSstMatEquiTipo = null;
        idSstMatEquiMarca = null;
    }

    /**
     * Permite capturar el objeto SstMatEqui seleccionado por el usuario
     *
     * @param event Evento que captura el objeto SstMatEqui
     */
    public void onGetSstMatEqui(SstMatEqui event) {
        sstMatEqui = event;
        cNombreMatEquiAux = event.getNombre();
        idSstMatEquiTipo = event.getIdSstMatEquiTipo() != null ? event.getIdSstMatEquiTipo().getIdSstMatEquiTipo() : null;
        idSstMatEquiMarca = event.getIdSstMatEquiMarca() != null ? event.getIdSstMatEquiMarca().getIdSstMatEquiMarca() : null;
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstMatEqui> findAllEstadoReg = sstMatEquiFacadeLocal.findAllEstadoReg(aPIJSF.getSstEmpresa().getIdSstEmpresa());
        for (SstMatEqui sae : findAllEstadoReg) {
            if (sae.getNombre().equals(sstMatEqui.getNombre())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permite validar si los valores para los atributos idSstMatEquiTipo y
     * idSstMatEquiMarca no sean nulos una vez el usuario haya procesado el
     * formulario
     *
     * @return false si los ids no son nulos
     */
    boolean validarIds() {
        if (idSstMatEquiMarca == null) {
            MovilidadUtil.addErrorMessage("sst materiales equipo marca es requerido");
            return true;
        }
        if (idSstMatEquiTipo == null) {
            MovilidadUtil.addErrorMessage("sst materiales equipo tipo es requerido");
            return true;
        }
        return false;
    }

    // crea las intancias de los objetos SstMatEquiMarca y SstMatEquiTipo, 
    // con los identificadores seleccionados, y se setean en el objeto
    // SstMatEqui
    void cargarObjetos() {
        sstMatEqui.setIdSstMatEquiMarca(new SstMatEquiMarca(idSstMatEquiMarca));
        sstMatEqui.setIdSstMatEquiTipo(new SstMatEquiTipo(idSstMatEquiTipo));
    }

    public SstMatEqui getSstMatEqui() {
        return sstMatEqui;
    }

    public void setSstMatEqui(SstMatEqui sstMatEqui) {
        this.sstMatEqui = sstMatEqui;
    }

    public List<SstMatEqui> getListSstMatEqui() {
        listSstMatEqui = sstMatEquiFacadeLocal.findAllEstadoReg(aPIJSF.getSstEmpresa().getIdSstEmpresa());
        return listSstMatEqui;
    }

    public void setListSstMatEqui(List<SstMatEqui> listSstMatEqui) {
        this.listSstMatEqui = listSstMatEqui;
    }

    public Integer getIdSstMatEquiTipo() {
        return idSstMatEquiTipo;
    }

    public void setIdSstMatEquiTipo(Integer idSstMatEquiTipo) {
        this.idSstMatEquiTipo = idSstMatEquiTipo;
    }

    public Integer getIdSstMatEquiMarca() {
        return idSstMatEquiMarca;
    }

    public void setIdSstMatEquiMarca(Integer idSstMatEquiMarca) {
        this.idSstMatEquiMarca = idSstMatEquiMarca;
    }

    public List<SstMatEquiTipo> getListSstMatEquiTipo() {
        listSstMatEquiTipo = sstMatEquiTipoFacadeLocal.findAllByEstadoReg();
        return listSstMatEquiTipo;
    }

    public void setListSstMatEquiTipo(List<SstMatEquiTipo> listSstMatEquiTipo) {
        this.listSstMatEquiTipo = listSstMatEquiTipo;
    }

}
