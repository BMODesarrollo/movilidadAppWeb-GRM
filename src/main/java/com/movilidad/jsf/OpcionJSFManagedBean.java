/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.OpcionFacadeLocal;
import com.movilidad.model.Opcion;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author solucionesit
 */
@Named(value = "opcionJSFMB")
@ViewScoped
public class OpcionJSFManagedBean implements Serializable {

    private boolean flagEditarOpcion;

    private List<Opcion> listOpcion;

    private Opcion opcion;

    @EJB
    private OpcionFacadeLocal opcionEJB;

    /**
     * Creates a new instance of OpcionJSFManagedBean
     */
    public OpcionJSFManagedBean() {

    }

    @PostConstruct
    public void init() {
        consultar();
    }

    void consultar() {
        listOpcion = opcionEJB.findAll();
    }

    public void guardarOpcion() {
        if (opcion.getDescripcion().equals("")) {
            MovilidadUtil.addErrorMessage("Digite la Descripción");
        }
        if (opcion.getIconChild().equals("")) {
            MovilidadUtil.addErrorMessage("Digite Icon Child");
        }
        if (opcion.getIconParent().equals("")) {
            MovilidadUtil.addErrorMessage("Digite Icon Parent");
        }
        if (opcion.getNameop().equals("")) {
            MovilidadUtil.addErrorMessage("Digite Nombre de la opción");
        }
        if (opcion.getRecurso().equals("")) {
            MovilidadUtil.addErrorMessage("Digite Recurso");
        }
        opcionEJB.create(opcion);
        listOpcion.add(opcion);
        MovilidadUtil.addSuccessMessage("Registro exitoso Opción");
        prepareNewOpcion();

    }

    public void prepareNewOpcion() {
        flagEditarOpcion = false;
        opcion = new Opcion();
        opcion.setIconChild("fa fa-");
        opcion.setIconParent("fa fa-");
        opcion.setDescripcion("");
        opcion.setNameop("");
        opcion.setRecurso("/");
    }

    public void editarOpcion() {
        if (opcion.getDescripcion().equals("")) {
            MovilidadUtil.addErrorMessage("Digite la Descripción");
        }
        if (opcion.getIconChild().equals("")) {
            MovilidadUtil.addErrorMessage("Digite Icon Child");
        }
        if (opcion.getIconParent().equals("")) {
            MovilidadUtil.addErrorMessage("Digite Icon Parent");
        }
        if (opcion.getNameop().equals("")) {
            MovilidadUtil.addErrorMessage("Digite Nombre de la opción");
        }
        if (opcion.getRecurso().equals("")) {
            MovilidadUtil.addErrorMessage("Digite Recurso");
        }
        opcionEJB.edit(opcion);
        MovilidadUtil.hideModal("wvOpcionDialog");
        consultar();
        MovilidadUtil.addSuccessMessage("Registro exitoso Opción");
    }

    public void prepareEditOpcion(Opcion o) {
        opcion = o;
        flagEditarOpcion = true;
    }

    public boolean isFlagEditarOpcion() {
        return flagEditarOpcion;
    }

    public void setFlagEditarOpcion(boolean flagEditarOpcion) {
        this.flagEditarOpcion = flagEditarOpcion;
    }

    public List<Opcion> getListOpcion() {
        return listOpcion;
    }

    public void setListOpcion(List<Opcion> listOpcion) {
        this.listOpcion = listOpcion;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

}
