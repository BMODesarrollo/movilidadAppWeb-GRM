/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaPmPuntosEmpresaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.GenericaPmPuntosEmpresa;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.PmPuntosEmpresa;
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
@Named(value = "genPmPuntosEmpJSF")
@ViewScoped
public class GenericaPmPuntosEmpresaJSF implements Serializable {
    
    private List<GenericaPmPuntosEmpresa> items = null;
    private GenericaPmPuntosEmpresa selected;
    private Date desdeMod;
    
    private ParamAreaUsr pau;
    
    @EJB
    private GenericaPmPuntosEmpresaFacadeLocal genPmPuntosEmprEJB;
    
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GenericaPmPuntosEmpresaJSF
     */
    public GenericaPmPuntosEmpresaJSF() {
    }
    
    @PostConstruct
    public void init() {
        consultar();
    }
    
    public void prepareCreate() {
        selected = new GenericaPmPuntosEmpresa();
    }
    
    public void reset() {
        selected = new GenericaPmPuntosEmpresa();
        consultar();
    }
    
    public void consultar() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        if (pau != null) {
            items = genPmPuntosEmprEJB.getByIdArea(pau.getIdParamArea().getIdParamArea());
        } else {
            items = new ArrayList<>();
        }
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
        if (selected.getVrPuntos() < 0) {
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
        
        GenericaPmPuntosEmpresa resp = genPmPuntosEmprEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getDesde(), 0);
        if (resp == null) {
            resp = genPmPuntosEmprEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getHasta(), 0);
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
        genPmPuntosEmprEJB.create(selected);
        MovilidadUtil.addSuccessMessage("Guardado con exito.");
        consultar();
        selected = new GenericaPmPuntosEmpresa();
    }
    
    public void editar() throws ParseException {
        if (selected.getDesde() == null || selected.getHasta() == null) {
            MovilidadUtil.addErrorMessage("Cargar fechas");
            return;
        }
        if (selected.getVrPuntos() < 0) {
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
        GenericaPmPuntosEmpresa resp = genPmPuntosEmprEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getDesde(), selected.getIdGenericaPmPuntosEmpresa());
        if (resp == null) {
            resp = genPmPuntosEmprEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getDesde(), selected.getIdGenericaPmPuntosEmpresa());
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
        genPmPuntosEmprEJB.edit(selected);
        MovilidadUtil.addSuccessMessage("Modificado con exito.");
        PrimeFaces.current().executeScript("PF('genPmPuntosEmpresaEditDialog').hide()");
        consultar();
    }
    
    public List<GenericaPmPuntosEmpresa> getItems() {
        return items;
    }
    
    public void setItems(List<GenericaPmPuntosEmpresa> items) {
        this.items = items;
    }
    
    public GenericaPmPuntosEmpresa getSelected() {
        return selected;
    }
    
    public void setSelected(GenericaPmPuntosEmpresa selected) {
        this.selected = selected;
    }
    
    public Date getDesdeMod() {
        return desdeMod;
    }
    
    public void setDesdeMod(Date desdeMod) {
        this.desdeMod = desdeMod;
    }
    
}
