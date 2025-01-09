/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstVehiculoMarcaFacadeLocal;
import com.movilidad.model.SstVehiculoMarca;
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
 * Permite gestionar toda los datos para el objeto SstVehiculoMarca principal
 * tabla afectada sst_vehiculo_marca
 *
 * @author cesar
 */
@Named(value = "sstVehiculoMarcaJSF")
@ViewScoped
public class SstVehiculoMarcaJSF implements Serializable {

    @EJB
    private SstVehiculoMarcaFacadeLocal sstVehiculoMarcaFacadeLocal;

    private SstVehiculoMarca sstVehiculoMarca;

    private List<SstVehiculoMarca> listSstVehiculoMarca;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreVehiculoMarcaAux;

    /**
     * Creates a new instance of SstVehiculoMarcaJSF
     */
    public SstVehiculoMarcaJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreVehiculoMarcaAux = "";
    }

    /**
     * Permite persistir la data del objeto SstVehiculoMarca en la base de datos
     */
    public void guardar() {
        try {
            if (sstVehiculoMarca != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst vehículo marca no se encuentra disponible");
                    return;
                }
                sstVehiculoMarca.setCreado(new Date());
                sstVehiculoMarca.setModificado(new Date());
                sstVehiculoMarca.setEstadoReg(0);
                sstVehiculoMarca.setUsername(user.getUsername());
                sstVehiculoMarcaFacadeLocal.create(sstVehiculoMarca);
                MovilidadUtil.addSuccessMessage("Se a registrado sst vehículo marca correctamente");
                sstVehiculoMarca = new SstVehiculoMarca();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst vehículo marca");
        }
    }

    /**
     * Permite realizar un update del objeto SstVehiculoMarca en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (sstVehiculoMarca != null) {
                if (!cNombreVehiculoMarcaAux.equals(sstVehiculoMarca.getMarca())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst vehículo marca no se encuentra disponible");
                        return;
                    }
                }
                sstVehiculoMarca.setModificado(new Date());
                sstVehiculoMarcaFacadeLocal.edit(sstVehiculoMarca);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst vehículo marca correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst vehículo marca");
        }
    }

    /**
     * Permite crear la instancia del objeto SstVehiculoMarca
     */
    public void prepareGuardar() {
        sstVehiculoMarca = new SstVehiculoMarca();
    }

    public void reset() {
        sstVehiculoMarca = null;
        cNombreVehiculoMarcaAux = "";
    }

    /**
     * Permite capturar el objeto SstVehiculoMarca seleccionado por el usuario
     *
     * @param event Evento que captura el objeto SstVehiculoMarca
     */
    public void onGetSstVehiculoMarca(SstVehiculoMarca event) {
        sstVehiculoMarca = event;
        cNombreVehiculoMarcaAux = event.getMarca();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstVehiculoMarca> findAllEstadoReg = sstVehiculoMarcaFacadeLocal.findAllEstadoReg();
        for (SstVehiculoMarca sae : findAllEstadoReg) {
            if (sae.getMarca().equals(sstVehiculoMarca.getMarca())) {
                return true;
            }
        }
        return false;
    }

    public SstVehiculoMarca getSstVehiculoMarca() {
        return sstVehiculoMarca;
    }

    public void setSstVehiculoMarca(SstVehiculoMarca sstVehiculoMarca) {
        this.sstVehiculoMarca = sstVehiculoMarca;
    }

    public List<SstVehiculoMarca> getListSstVehiculoMarca() {
        listSstVehiculoMarca = sstVehiculoMarcaFacadeLocal.findAllEstadoReg();
        return listSstVehiculoMarca;
    }

    public void setListSstVehiculoMarca(List<SstVehiculoMarca> listSstVehiculoMarca) {
        this.listSstVehiculoMarca = listSstVehiculoMarca;
    }

}
