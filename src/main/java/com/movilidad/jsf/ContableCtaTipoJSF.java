/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ContableCtaTipoFacadeLocal;
import com.movilidad.model.ContableCtaTipo;
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
 * Permite gestionar toda los datos para el objeto ContableCtaTipo principal
 * tabla afectada contable_cta_tipo
 *
 * @author cesar
 */
@Named(value = "contableCtaTipoJSF")
@ViewScoped
public class ContableCtaTipoJSF implements Serializable {

    @EJB
    private ContableCtaTipoFacadeLocal contableCtaTipoFacadeLocal;

    private ContableCtaTipo contableCtaTipo;

    private List<ContableCtaTipo> listContableCtaTipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreCtaAux;

    /**
     * Creates a new instance of ContableCtaTipoJSF
     */
    public ContableCtaTipoJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreCtaAux = "";
    }

    /**
     * Permite persistir la data del objeto ContableCtaTipo en la base de datos
     */
    public void guardar() {
        try {
            if (contableCtaTipo != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para cuenta tipo no se encuentra disponible");
                    return;
                }
                contableCtaTipo.setCreado(new Date());
                contableCtaTipo.setModificado(new Date());
                contableCtaTipo.setEstadoReg(0);
                contableCtaTipo.setUsername(user.getUsername());
                contableCtaTipoFacadeLocal.create(contableCtaTipo);
                MovilidadUtil.addSuccessMessage("Se a registrado correctamente");
                contableCtaTipo = new ContableCtaTipo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar");
        }
    }

    /**
     * Permite realizar un update del objeto ContableCtaTipo en la base de datos
     */
    public void actualizar() {
        try {
            if (contableCtaTipo != null) {
                if (!cNombreCtaAux.equals(contableCtaTipo.getTipoCuenta())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para cuenta tipo no se encuentra disponible");
                        return;
                    }
                }
                contableCtaTipo.setModificado(new Date());
                contableCtaTipoFacadeLocal.edit(contableCtaTipo);
                MovilidadUtil.addSuccessMessage("Se a actualizado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar");
        }
    }

    /**
     * Permite crear la instancia del objeto ContableCtaTipo
     */
    public void prepareGuardar() {
        contableCtaTipo = new ContableCtaTipo();
    }

    public void reset() {
        contableCtaTipo = null;
        cNombreCtaAux = "";
    }

    /**
     * Permite capturar el objeto ContableCtaTipo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto ContableCtaTipo
     */
    public void onContableCtaTipo(ContableCtaTipo event) {
        contableCtaTipo = event;
        cNombreCtaAux = event.getTipoCuenta();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<ContableCtaTipo> findAllEstadoReg = contableCtaTipoFacadeLocal.findAllEstadoReg();
        for (ContableCtaTipo cta : findAllEstadoReg) {
            if (cta.getTipoCuenta().equals(contableCtaTipo.getTipoCuenta())) {
                return true;
            }
        }
        return false;
    }

    public ContableCtaTipo getContableCtaTipo() {
        return contableCtaTipo;
    }

    public void setContableCtaTipo(ContableCtaTipo contableCtaTipo) {
        this.contableCtaTipo = contableCtaTipo;
    }

    public List<ContableCtaTipo> getListContableCtaTipo() {
        listContableCtaTipo = contableCtaTipoFacadeLocal.findAllEstadoReg();
        return listContableCtaTipo;
    }

    public void setListContableCtaTipo(List<ContableCtaTipo> listContableCtaTipo) {
        this.listContableCtaTipo = listContableCtaTipo;
    }

}
