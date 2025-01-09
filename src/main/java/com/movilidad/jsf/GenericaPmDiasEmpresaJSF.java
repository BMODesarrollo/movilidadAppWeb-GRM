/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaPmDiasEmpresaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.GenericaPmDiasEmpresa;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.ParseException;
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
 * @author solucionesit
 */
@Named(value = "genPmDiasEmpresaJSF")
@ViewScoped
public class GenericaPmDiasEmpresaJSF implements Serializable {

    private List<GenericaPmDiasEmpresa> items = null;
    private GenericaPmDiasEmpresa selected;
    private Date desdeMod;
    private ParamAreaUsr pau;

    /**
     * Creates a new instance of GenericaPmDiasEmpresaJSF
     */
    public GenericaPmDiasEmpresaJSF() {
    }

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;

    @EJB
    private GenericaPmDiasEmpresaFacadeLocal genPmDiasEmprEJB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void prepareCreate() {
        selected = new GenericaPmDiasEmpresa();
    }

    public void consultar() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        if (pau != null) {
            items = genPmDiasEmprEJB.getByIdArea(pau.getIdParamArea().getIdParamArea());
        } else {
            items = new ArrayList<>();
        }
    }

    public void premodificar() {
        desdeMod = getSelected().getDesde();
    }

    public void guardar() throws ParseException {

        if (pau == null) {
            MovilidadUtil.addErrorMessage("El usuario no está configurado en algun área.");
            return;
        }
        if (selected.getDesde() == null || selected.getHasta() == null) {
            MovilidadUtil.addErrorMessage("Cargar fechas");
            return;
        }
        if (selected.getNroDias() < 0) {
            MovilidadUtil.addErrorMessage("Valor de puntos no pueden ser negativos");
            return;
        }
        if (MovilidadUtil.dateSinHora(selected.getDesde()).after(MovilidadUtil.dateSinHora(selected.getHasta()))) {
            MovilidadUtil.addErrorMessage("La fecha desde no debe ser menos a la fecha hastas");
            return;
        }
        if (MovilidadUtil.dateSinHora(selected.getDesde()).before(MovilidadUtil.dateSinHora(MovilidadUtil.fechaHoy()))) {
            MovilidadUtil.addErrorMessage("La fecha desde debe ser posterior a la fecha de hoy");
            return;
        }

        GenericaPmDiasEmpresa resp = genPmDiasEmprEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getDesde(), 0);
        if (resp == null) {
            resp = genPmDiasEmprEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getHasta(), 0);
            if (resp != null) {
                MovilidadUtil.addErrorMessage("Ya hay un registro con fechas similares para el área");
                return;
            }
        } else {
            MovilidadUtil.addErrorMessage("Ya hay un registro con fechas similares para el área");
            return;
        }
        selected.setIdParamArea(pau.getIdParamArea());
        selected.setCreado(MovilidadUtil.fechaCompletaHoy());
        selected.setEstadoReg(0);
        selected.setUsername(user.getUsername());
        genPmDiasEmprEJB.create(selected);
        MovilidadUtil.addSuccessMessage("Guardado con exito.");
        consultar();
        selected = new GenericaPmDiasEmpresa();
    }

    public void editar() throws ParseException {
        if (selected.getDesde() == null || selected.getHasta() == null) {
            MovilidadUtil.addErrorMessage("Cargar fechas");
            return;
        }
        if (selected.getNroDias() < 0) {
            MovilidadUtil.addErrorMessage("Valor de puntos no pueden ser negativos");
            return;
        }
        if (MovilidadUtil.dateSinHora(selected.getDesde()).after(MovilidadUtil.dateSinHora(selected.getHasta()))) {
            MovilidadUtil.addErrorMessage("La fecha desde no debe ser menos a la fecha hastas");
            return;
        }
        if (MovilidadUtil.dateSinHora(selected.getDesde()).before(MovilidadUtil.dateSinHora(desdeMod))) {
            MovilidadUtil.addErrorMessage("La fecha desde debe ser posterior a la fecha de hoy");
            return;
        }
        GenericaPmDiasEmpresa resp = genPmDiasEmprEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getDesde(), selected.getIdGenericaPmDiasEmpresa());
        if (resp == null) {
            resp = genPmDiasEmprEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getDesde(), selected.getIdGenericaPmDiasEmpresa());
            if (resp != null) {
                MovilidadUtil.addErrorMessage("Ya hay un registro con fechas similares para el área");
                return;
            }
        } else {
            MovilidadUtil.addErrorMessage("Ya hay un registro con fechas similares para el área");
            return;
        }
        selected.setUsername(user.getUsername());
        selected.setModificado(MovilidadUtil.fechaCompletaHoy());
        genPmDiasEmprEJB.edit(selected);
        MovilidadUtil.addSuccessMessage("Modificado con exito.");
        PrimeFaces.current().executeScript("PF('genPmDiasEmpresaEditDialog').hide()");
        consultar();
    }

    public void reset() {
        selected = null;
        consultar();
    }

    public List<GenericaPmDiasEmpresa> getItems() {
        return items;
    }

    public void setItems(List<GenericaPmDiasEmpresa> items) {
        this.items = items;
    }

    public GenericaPmDiasEmpresa getSelected() {
        return selected;
    }

    public void setSelected(GenericaPmDiasEmpresa selected) {
        this.selected = selected;
    }

}
