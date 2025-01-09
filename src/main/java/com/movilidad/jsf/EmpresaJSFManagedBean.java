/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpresaFacadeLocal;
import com.movilidad.model.Empresa;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "emprJSFMB")
@ViewScoped
public class EmpresaJSFManagedBean implements Serializable {

    private Empresa emprs;

    private List<Empresa> listaEmpresas;
    @EJB
    private EmpresaFacadeLocal empresaEJB;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of EmpresaJSFManagedBean
     */
    public EmpresaJSFManagedBean() {
        listaEmpresas = new ArrayList();
    }

    @PostConstruct
    public void init() {
        emprs = new Empresa();
        listarEmpresas();
    }

    public void openDialog() {
        emprs = new Empresa();
    }

    public void listarEmpresas() {
        try {
            listaEmpresas = empresaEJB.findAll();
        } catch (Exception e) {
        }
    }

    public void editar(Empresa emp) {
        this.emprs = emp;
    }

    public void guardarEdit() {
         if (empresaEJB.findByNit(emprs.getNit(), emprs.getIdEmpresa()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe Empresa con este Nit");
                return;
            }
        emprs.setUsername(user.getUsername());
        try {
            empresaEJB.edit(emprs);
            MovilidadUtil.addSuccessMessage("Exito. Se modificó el registro exitosamente");
            closeDialog();
        } catch (Exception e) {
        }
    }

    public void eliminar(Empresa emprL) {
        emprL.setEstadoReg(1);
        try {
            empresaEJB.edit(emprL);
          MovilidadUtil.addSuccessMessage("Eliminado. Se eliminó el registro exitosamente");
            init();
        } catch (Exception e) {
           MovilidadUtil.addErrorMessage(e.getMessage());
        }

    }

    public void guardar() {
        if (emprs != null) {
            emprs.setUsername(user.getUsername());
            emprs.setCreado(new Date());
            emprs.setEstadoReg(0);

            if (empresaEJB.findByNit(emprs.getNit(), 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe Empresa con este Nit");
                return;
            }
            try {
                empresaEJB.create(emprs);
               MovilidadUtil.addSuccessMessage("Exito. Registro exitoso Empresa");
                closeDialog();
            } catch (Exception e) {
            }

        }
    }

    public void closeDialog() {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogE').hide();");
        init();
    }

    public Empresa getEmprs() {
        return emprs;
    }

    public void setEmprs(Empresa emprs) {
        this.emprs = emprs;
    }

    public List<Empresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public void setListaEmpresas(List<Empresa> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

}
