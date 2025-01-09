/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ContableCtaFacadeLocal;
import com.movilidad.model.ContableCta;
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
 * Permite gestionar toda los datos para el objeto ContableCta principal tabla
 * afectada contable_cta
 *
 * @author cesar
 */
@Named(value = "contableCtaJSF")
@ViewScoped
public class ContableCtaJSF implements Serializable {

    @EJB
    private ContableCtaFacadeLocal contableCtaFacadeLocal;
    private ContableCta contableCta;
    private List<ContableCta> listContableCta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNumCtaAux;
    private Integer idContableCuentaTipo;
    private boolean bEsMulta;

    /**
     * Creates a new instance of ContableCta
     */
    public ContableCtaJSF() {
    }

    @PostConstruct
    public void init() {
        cNumCtaAux = "";
        idContableCuentaTipo = null;
        bEsMulta = false;
    }

    /**
     * Permite persistir la data del objeto ContableCta en la base de datos
     */
    public void guardar() {
        try {
            if (contableCta != null) {
                if (idContableCuentaTipo == null) {
                    MovilidadUtil.addErrorMessage("Tipo de cuenta es requerido");
                    return;
                }
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Número de cuenta no se encuentra disponible");
                    return;
                }
                contableCta.setEsMulta(bEsMulta ? 1 : 0);
                contableCta.setIdContableCtaTipo(new ContableCtaTipo(idContableCuentaTipo));
                contableCta.setCreado(new Date());
                contableCta.setModificado(new Date());
                contableCta.setEstadoReg(0);
                contableCta.setUsername(user.getUsername());
                contableCtaFacadeLocal.create(contableCta);
                MovilidadUtil.addSuccessMessage("Se a registrado correctamente");
                contableCta = new ContableCta();
                idContableCuentaTipo = null;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar");
        }
    }

    /**
     * Permite realizar un update del objeto ContableCta en la base de datos
     */
    public void actualizar() {
        try {
            if (contableCta != null) {
                if (idContableCuentaTipo == null) {
                    MovilidadUtil.addErrorMessage("Tipo de cuenta es requerido");
                    return;
                }
                if (!cNumCtaAux.equals(contableCta.getNroCuenta())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Número de cuenta no se encuentra disponible");
                        return;
                    }
                }
                contableCta.setEsMulta(bEsMulta ? 1 : 0);
                contableCta.setIdContableCtaTipo(new ContableCtaTipo(idContableCuentaTipo));
                contableCta.setModificado(new Date());
                contableCtaFacadeLocal.edit(contableCta);
                MovilidadUtil.addSuccessMessage("Se a actualizado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar");
        }
    }

    /**
     * Permite crear la instancia del objeto ContableCta
     */
    public void prepareGuardar() {
        reset();
        contableCta = new ContableCta();
    }

    public void reset() {
        contableCta = null;
        cNumCtaAux = "";
        idContableCuentaTipo = null;
        bEsMulta = false;
    }

    /**
     * Permite capturar el objeto ContableCta seleccionado por el usuario
     *
     * @param event Evento que captura el objeto ContableCta
     */
    public void onContableCta(ContableCta event) {
        contableCta = event;
        cNumCtaAux = event.getNroCuenta();
        idContableCuentaTipo = event.getIdContableCtaTipo().getIdContableCtaTipo();
        bEsMulta = event.getEsMulta().equals(1);
    }

    /**
     * Permite validar si el valor asignado al atributo NroCuenta se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<ContableCta> findAllEstadoReg = contableCtaFacadeLocal.findAllEstadoReg();
        for (ContableCta cta : findAllEstadoReg) {
            if (cta.getNroCuenta().equals(contableCta.getNroCuenta())) {
                return true;
            }
        }
        return false;
    }

    public ContableCta getContableCta() {
        return contableCta;
    }

    public void setContableCta(ContableCta contableCta) {
        this.contableCta = contableCta;
    }

    public List<ContableCta> getListContableCta() {
        listContableCta = contableCtaFacadeLocal.findAllEstadoReg();
        return listContableCta;
    }

    public void setListContableCta(List<ContableCta> listContableCta) {
        this.listContableCta = listContableCta;
    }

    public Integer getIdContableCuentaTipo() {
        return idContableCuentaTipo;
    }

    public void setIdContableCuentaTipo(Integer idContableCuentaTipo) {
        this.idContableCuentaTipo = idContableCuentaTipo;
    }

    public boolean isbEsMulta() {
        return bEsMulta;
    }

    public void setbEsMulta(boolean bEsMulta) {
        this.bEsMulta = bEsMulta;
    }

}
