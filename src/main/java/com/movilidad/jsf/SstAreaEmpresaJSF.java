/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstAreaEmpresaFacadeLocal;
import com.movilidad.model.SstAreaEmpresa;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar toda los datos para el objeto SstAreaEmpresa principal
 * tabla afectada sst_area_empresa
 *
 * @author cesar
 */
@Named(value = "sstAreaEmpresaJSF")
@ViewScoped
public class SstAreaEmpresaJSF implements Serializable {

    @EJB
    private SstAreaEmpresaFacadeLocal sstAreaEmpresaFacadeLocal;

    private SstAreaEmpresa sstAreaEmpresa;

    private List<SstAreaEmpresa> listSstAreaEmpresa;

    private String cNombreAreaAux;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public SstAreaEmpresaJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAreaAux = "";
    }

    /**
     * Permite persistir la data del objeto SstAreaEmpresa en la base de datos
     */
    public void guardar() {
        try {
            if (sstAreaEmpresa != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst área no se encuentra disponible");
                    return;
                }
                sstAreaEmpresa.setCreado(new Date());
                sstAreaEmpresa.setModificado(new Date());
                sstAreaEmpresa.setEstadoReg(0);
                sstAreaEmpresa.setUsername(user.getUsername());
                sstAreaEmpresaFacadeLocal.create(sstAreaEmpresa);
                MovilidadUtil.addSuccessMessage("Se a registrado sst área correctamente");
                sstAreaEmpresa = new SstAreaEmpresa();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst área");
        }
    }

    /**
     * Permite realizar un update del objeto SstAreaEmpresa en la base de datos
     */
    public void actualizar() {
        try {
            if (sstAreaEmpresa != null) {
                if (!cNombreAreaAux.equals(sstAreaEmpresa.getArea())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst área no se encuentra disponible");
                        return;
                    }
                }
                sstAreaEmpresa.setModificado(new Date());
                sstAreaEmpresaFacadeLocal.edit(sstAreaEmpresa);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst área correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst área");
        }
    }

    /**
     * Permite crear la instancia del objeto SstAreaEmpresa
     */
    public void prepareGuardar() {
        sstAreaEmpresa = new SstAreaEmpresa();
    }

    public void reset() {
        sstAreaEmpresa = null;
        cNombreAreaAux = "";
    }

    /**
     * Permite capturar el objeto SstAreaEmpresa seleccionado por el usuario
     *
     * @param event Evento que captura el objeto SstAreaEmpresa
     */
    public void onGetSstAreaEmpresa(SstAreaEmpresa event) {
        sstAreaEmpresa = event;
        cNombreAreaAux = event.getArea();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstAreaEmpresa> findAllEstadoReg = sstAreaEmpresaFacadeLocal.findAllEstadoReg();
        for (SstAreaEmpresa sae : findAllEstadoReg) {
            if (sae.getArea().equals(sstAreaEmpresa.getArea())) {
                return true;
            }
        }
        return false;
    }

    public SstAreaEmpresa getSstAreaEmpresa() {
        return sstAreaEmpresa;
    }

    public void setSstAreaEmpresa(SstAreaEmpresa sstAreaEmpresa) {
        this.sstAreaEmpresa = sstAreaEmpresa;
    }

    public List<SstAreaEmpresa> getListSstAreaEmpresa() {
        listSstAreaEmpresa = sstAreaEmpresaFacadeLocal.findAllEstadoReg();
        return listSstAreaEmpresa;
    }

    public void setListSstAreaEmpresa(List<SstAreaEmpresa> listSstAreaEmpresa) {
        this.listSstAreaEmpresa = listSstAreaEmpresa;
    }

}
