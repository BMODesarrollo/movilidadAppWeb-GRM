/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccEntregaPacienteFacadeLocal;
import com.movilidad.model.CableAccEntregaPaciente;
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
 * Permite gestionar toda los datos para el objeto CableAccEntregaPaciente
 * principal tabla afectada cable_acc_entrega_paciente
 *
 * @author soluciones-it
 */
@Named(value = "cableAccEntregaPacienteJSF")
@ViewScoped
public class CableAccEntregaPacienteJSF implements Serializable {

    @EJB
    private CableAccEntregaPacienteFacadeLocal cableAccEntregaPacienteFacadeLocal;

    private CableAccEntregaPaciente cableAccEntregaPaciente;

    private List<CableAccEntregaPaciente> listCableAccEntregaPaciente;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of CableAccEntregaPacienteJSF
     */
    public CableAccEntregaPacienteJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto CableAccEntregaPaciente en la base
     * de datos
     */
    public void guardar() {
        try {
            if (cableAccEntregaPaciente != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para acc entrega paciente no se encuentra disponible");
                    return;
                }
                cableAccEntregaPaciente.setCreado(new Date());
                cableAccEntregaPaciente.setModificado(new Date());
                cableAccEntregaPaciente.setEstadoReg(0);
                cableAccEntregaPaciente.setUsername(user.getUsername());
                cableAccEntregaPacienteFacadeLocal.create(cableAccEntregaPaciente);
                MovilidadUtil.addSuccessMessage("Se a registrado acc entrega paciente correctamente");
                cableAccEntregaPaciente = new CableAccEntregaPaciente();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc entrega paciente");
        }
    }

    /**
     * Permite realizar un update del objeto CableAccEntregaPaciente en la base
     * de datos
     */
    public void actualizar() {
        try {
            if (cableAccEntregaPaciente != null) {
                if (!cNombreAux.equals(cableAccEntregaPaciente.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para acc entrega paciente no se encuentra disponible");
                        return;
                    }
                }
                cableAccEntregaPaciente.setModificado(new Date());
                cableAccEntregaPacienteFacadeLocal.edit(cableAccEntregaPaciente);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc entrega paciente correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc entrega paciente");
        }
    }

    /**
     * Permite crear la instancia del objeto CableAccEntregaPaciente
     */
    public void prepareGuardar() {
        cableAccEntregaPaciente = new CableAccEntregaPaciente();
    }

    public void reset() {
        cableAccEntregaPaciente = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto CableAccEntregaPaciente seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto CableAccEntregaPaciente
     */
    public void onGetCableAccEntregaPaciente(CableAccEntregaPaciente event) {
        cableAccEntregaPaciente = event;
        cNombreAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<CableAccEntregaPaciente> findAllEstadoReg = cableAccEntregaPacienteFacadeLocal.findAllEstadoReg();
        for (CableAccEntregaPaciente sae : findAllEstadoReg) {
            if (sae.getNombre().equals(cableAccEntregaPaciente.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public CableAccEntregaPaciente getCableAccEntregaPaciente() {
        return cableAccEntregaPaciente;
    }

    public void setCableAccEntregaPaciente(CableAccEntregaPaciente cableAccEntregaPaciente) {
        this.cableAccEntregaPaciente = cableAccEntregaPaciente;
    }

    public List<CableAccEntregaPaciente> getListCableAccEntregaPaciente() {
        listCableAccEntregaPaciente = cableAccEntregaPacienteFacadeLocal.findAllEstadoReg();
        return listCableAccEntregaPaciente;
    }

    public void setListCableAccEntregaPaciente(List<CableAccEntregaPaciente> listCableAccEntregaPaciente) {
        this.listCableAccEntregaPaciente = listCableAccEntregaPaciente;
    }

}
