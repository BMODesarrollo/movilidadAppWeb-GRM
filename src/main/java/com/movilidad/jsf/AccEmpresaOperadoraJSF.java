/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccEmpresaOperadoraFacadeLocal;
import com.movilidad.model.AccEmpresaOperadora;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite parametrizar la data relacionada con los objetos AccEmpresaOperadora
 * Principal tabla afectada acc_empresa_operadora
 *
 * @author HP
 */
@Named(value = "accEmpresaOperadoraJSF")
@ViewScoped
public class AccEmpresaOperadoraJSF implements Serializable {

    @EJB
    private AccEmpresaOperadoraFacadeLocal accEmpresaOperadoraFacadeLocal;

    private AccEmpresaOperadora accEmpresaOperadora;

    private List<AccEmpresaOperadora> listAccEmpresaOperadora;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccEmpresaOperadoraJSF() {
    }

    /**
     * Permite persistir la data del objeto AccEmpresaOperadora en la base de
     * datos
     */
    public void guardar() {
        try {
            if (accEmpresaOperadora != null) {
                accEmpresaOperadora.setEmpresaOperadora(accEmpresaOperadora.getEmpresaOperadora().toUpperCase());
                accEmpresaOperadora.setCreado(new Date());
                accEmpresaOperadora.setModificado(new Date());
                accEmpresaOperadora.setEstadoReg(0);
                accEmpresaOperadora.setUsername(user.getUsername());
                accEmpresaOperadoraFacadeLocal.create(accEmpresaOperadora);
                MovilidadUtil.addSuccessMessage("Se a registrado el acc empresa operadora realizada correctamente");
                accEmpresaOperadora = new AccEmpresaOperadora();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc empresa operadora realizada");
        }
    }

    /**
     * Permite realizar un update del objeto AccEmpresaOperadora en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (accEmpresaOperadora != null) {
                accEmpresaOperadora.setEmpresaOperadora(accEmpresaOperadora.getEmpresaOperadora().toUpperCase());
                accEmpresaOperadoraFacadeLocal.edit(accEmpresaOperadora);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc empresa operadora realizada correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('emp-ope-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc empresa operadora realizada");
        }
    }

    /**
     * Permite crear la instancia del objeto AccEmpresaOperadora
     */
    public void prepareGuardar() {
        accEmpresaOperadora = new AccEmpresaOperadora();
    }

    public void reset() {
        accEmpresaOperadora = null;
    }

    /**
     *
     * @param event Objeto AccEmpresaOperadora seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accEmpresaOperadora = (AccEmpresaOperadora) event.getObject();
    }

    public AccEmpresaOperadora getAccEmpresaOperadora() {
        return accEmpresaOperadora;
    }

    public void setAccEmpresaOperadora(AccEmpresaOperadora accEmpresaOperadora) {
        this.accEmpresaOperadora = accEmpresaOperadora;
    }

    public List<AccEmpresaOperadora> getListAccEmpresaOperadora() {
        listAccEmpresaOperadora = accEmpresaOperadoraFacadeLocal.estadoReg();
        return listAccEmpresaOperadora;
    }

}
