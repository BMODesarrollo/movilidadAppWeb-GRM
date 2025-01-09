/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstMatEquiMarcaFacadeLocal;
import com.movilidad.model.SstEmpresa;
import com.movilidad.model.SstMatEquiMarca;
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

/**
 * Permite gestionar toda los datos para el objeto SstMatEquiMarca principal
 * tabla afectada sst_mat_equi_marca
 *
 * @author cesar
 */
@Named(value = "sstMatEquiMarcaJSF")
@ViewScoped
public class SstMatEquiMarcaJSF implements Serializable {

    @EJB
    private SstMatEquiMarcaFacadeLocal sstMatEquiMarcaFacadeLocal;

    private SstMatEquiMarca sstMatEquiMarca;

    private List<SstMatEquiMarca> listSstMatEquiMarca;

    private String cNombreMatEquiMarcaAux;

    @Inject
    private SstTokenJSF aPIJSF;

    /**
     * Creates a new instance of SstMatEquiMarcaJSF
     */
    public SstMatEquiMarcaJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreMatEquiMarcaAux = "";
    }

    /**
     * Permite persistir la data del objeto SstMatEquiMarca en la base de datos
     */
    public void guardar() {
        try {
            if (sstMatEquiMarca != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst materiales equipo marca no se encuentra disponible");
                    return;
                }
                SstEmpresa sstEmpresa = aPIJSF.getSstEmpresa();
                sstMatEquiMarca.setIdSstEmpresa(sstEmpresa);
                sstMatEquiMarca.setCreado(new Date());
                sstMatEquiMarca.setModificado(new Date());
                sstMatEquiMarca.setEstadoReg(0);
                sstMatEquiMarca.setUsername(sstEmpresa.getUsrNombre());
                sstMatEquiMarcaFacadeLocal.create(sstMatEquiMarca);
                MovilidadUtil.addSuccessMessage("Se a registrado sst materiales equipo marca correctamente");
                sstMatEquiMarca = new SstMatEquiMarca();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst materiales equipo marca");
        }
    }

    /**
     * Permite realizar un update del objeto SstMatEquiMarca en la base de datos
     */
    public void actualizar() {
        try {
            if (sstMatEquiMarca != null) {
                if (!cNombreMatEquiMarcaAux.equals(sstMatEquiMarca.getMarca())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst materiales equipo marca no se encuentra disponible");
                        return;
                    }
                }
                sstMatEquiMarca.setModificado(new Date());
                sstMatEquiMarcaFacadeLocal.edit(sstMatEquiMarca);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst materiales equipo marca correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst materiales equipo marca");
        }
    }

    /**
     * Permite crear la instancia del objeto SstMatEquiMarca
     */
    public void prepareGuardar() {
        sstMatEquiMarca = new SstMatEquiMarca();
    }

    public void reset() {
        sstMatEquiMarca = null;
        cNombreMatEquiMarcaAux = "";
    }

    /**
     * Permite capturar el objeto SstMatEquiMarca seleccionado por el usuario
     *
     * @param event Evento que captura el objeto SstMatEquiMarca
     */
    public void onGetSstMatEquiMarca(SstMatEquiMarca event) {
        sstMatEquiMarca = event;
        cNombreMatEquiMarcaAux = event.getMarca();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstMatEquiMarca> findAllEstadoReg = sstMatEquiMarcaFacadeLocal.findAllEstadoReg(aPIJSF.getSstEmpresa().getIdSstEmpresa());
        for (SstMatEquiMarca sae : findAllEstadoReg) {
            if (sae.getMarca().equals(sstMatEquiMarca.getMarca())) {
                return true;
            }
        }
        return false;
    }

    public SstMatEquiMarca getSstMatEquiMarca() {
        return sstMatEquiMarca;
    }

    public void setSstMatEquiMarca(SstMatEquiMarca sstMatEquiMarca) {
        this.sstMatEquiMarca = sstMatEquiMarca;
    }

    public List<SstMatEquiMarca> getListSstMatEquiMarca() {
        listSstMatEquiMarca = sstMatEquiMarcaFacadeLocal.findAllEstadoReg(aPIJSF.getSstEmpresa().getIdSstEmpresa());
        return listSstMatEquiMarca;
    }

    public void setListSstMatEquiMarca(List<SstMatEquiMarca> listSstMatEquiMarca) {
        this.listSstMatEquiMarca = listSstMatEquiMarca;
    }

}
