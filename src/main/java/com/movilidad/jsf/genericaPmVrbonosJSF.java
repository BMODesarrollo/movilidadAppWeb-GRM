/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.ejb.GenericaPmVrbonosFacadeLocal;
import com.movilidad.ejb.ParamAreaCargoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.GenericaPmVrbonos;
import com.movilidad.model.ParamAreaCargo;
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
@Named(value = "genPmVrbonosJSF")
@ViewScoped
public class genericaPmVrbonosJSF implements Serializable {
    
    private List<GenericaPmVrbonos> items = null;
    private GenericaPmVrbonos selected;
    private int i_idEmpeladoTipoCargo;
    private EmpleadoTipoCargo empleadoTipoC;
    private List<ParamAreaCargo> listEmpleadoTC;
    private boolean flag;
    
    private ParamAreaUsr pau;
    
    @EJB
    private EmpleadoTipoCargoFacadeLocal empleadoTipoCargoFacadeLocal;
    
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    
    @EJB
    private ParamAreaCargoFacadeLocal paramAreaCargoEBJ;
    
    @EJB
    private GenericaPmVrbonosFacadeLocal genPmVrBonoEJB;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of genericaPmVrbonosJSF
     */
    public genericaPmVrbonosJSF() {
    }
    
    @PostConstruct
    public void init() {
        consultar();
    }
    
    public void consultar() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        if (pau != null) {
            items = genPmVrBonoEJB.getByIdArea(pau.getIdParamArea().getIdParamArea());
        } else {
            items = new ArrayList<>();
        }
    }
    
    public void prepareCreate() {
        flag = false;
        i_idEmpeladoTipoCargo = 0;
        listEmpleadoTC = paramAreaCargoEBJ.findAllArea(pau.getIdParamArea().getIdParamArea());
        selected = new GenericaPmVrbonos();
    }
    
    public void prepareEdit() {
        flag = true;
        listEmpleadoTC = paramAreaCargoEBJ.findAllArea(pau.getIdParamArea().getIdParamArea());
        i_idEmpeladoTipoCargo = selected.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo();
        
    }
    
    public void reset() {
        flag = false;
        i_idEmpeladoTipoCargo = 0;
        selected = new GenericaPmVrbonos();
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
        if (selected.getVrBonoAlimentos() < 0) {
            MovilidadUtil.addErrorMessage("Bono Alimentos no puede ser Negativo");
            return;
        }
        if (selected.getVrBonoAlimentos() < 0) {
            MovilidadUtil.addErrorMessage("Bono Alimentos no puede ser Negativo");
            return;
        }
        if (i_idEmpeladoTipoCargo == 0) {
            MovilidadUtil.addErrorMessage("Seleccionar tipo de cargo.");
            return;
        }
        if (selected.getVrBonoSalarial() < 0) {
            MovilidadUtil.addErrorMessage("Bono Salarial no puede ser Negativo");
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
        GenericaPmVrbonos resp = genPmVrBonoEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getDesde(), flag ? selected.getIdGenericaPmVrbonos() : 0, i_idEmpeladoTipoCargo);
        if (resp == null) {
            resp = genPmVrBonoEJB.findByIdAreaAndFecha(pau.getIdParamArea().getIdParamArea(), selected.getHasta(), flag ? selected.getIdGenericaPmVrbonos() : 0, i_idEmpeladoTipoCargo);
            if (resp != null) {
                MovilidadUtil.addErrorMessage("Ya hay un registro con fechas y tipo de cargo similar para el área");
                return;
            }
        } else {
            MovilidadUtil.addErrorMessage("Ya hay un registro con fechas y tipo de cargo similar para el área");
            return;
        }
        selected.setUsername(user.getUsername());
        selected.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_idEmpeladoTipoCargo));
        if (flag) {
            selected.setModificado(MovilidadUtil.fechaCompletaHoy());
            genPmVrBonoEJB.edit(selected);
            PrimeFaces.current().executeScript("PF('PmVrbonosEditDialog').hide()");
            MovilidadUtil.addSuccessMessage("Modificado con exito.");
        } else {
            selected.setIdParamArea(pau.getIdParamArea());
            selected.setEstadoReg(0);
            selected.setCreado(MovilidadUtil.fechaCompletaHoy());
            genPmVrBonoEJB.create(selected);
            i_idEmpeladoTipoCargo = 0;
            selected = new GenericaPmVrbonos();
            MovilidadUtil.addSuccessMessage("Guardado con exito.");
        }
        consultar();
    }
    
    public List<GenericaPmVrbonos> getItems() {
        return items;
    }
    
    public void setItems(List<GenericaPmVrbonos> items) {
        this.items = items;
    }
    
    public GenericaPmVrbonos getSelected() {
        return selected;
    }
    
    public void setSelected(GenericaPmVrbonos selected) {
        this.selected = selected;
    }
    
    public EmpleadoTipoCargo getEmpleadoTipoC() {
        return empleadoTipoC;
    }
    
    public void setEmpleadoTipoC(EmpleadoTipoCargo empleadoTipoC) {
        this.empleadoTipoC = empleadoTipoC;
    }
    
    public List<ParamAreaCargo> getListEmpleadoTC() {
        return listEmpleadoTC;
    }
    
    public void setListEmpleadoTC(List<ParamAreaCargo> listEmpleadoTC) {
        this.listEmpleadoTC = listEmpleadoTC;
    }
    
    public int getI_idEmpeladoTipoCargo() {
        return i_idEmpeladoTipoCargo;
    }
    
    public void setI_idEmpeladoTipoCargo(int i_idEmpeladoTipoCargo) {
        this.i_idEmpeladoTipoCargo = i_idEmpeladoTipoCargo;
    }
    
}
