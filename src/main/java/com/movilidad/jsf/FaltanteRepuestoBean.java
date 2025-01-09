/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DispFaltanteRepuestoFacadeLocal;
import com.movilidad.model.DispActividad;
import com.movilidad.model.DispFaltanteRepuesto;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;

/**
 *
 * @author solucionesit
 */
@Named(value = "faltanteRepuestBean")
@ViewScoped
public class FaltanteRepuestoBean implements Serializable {

    /**
     * Creates a new instance of FaltanteRepuestoBean
     */
    public FaltanteRepuestoBean() {
    }
    @EJB
    private DispFaltanteRepuestoFacadeLocal faltanteRepuestoEJB;
    private String nombre = "";
    private int cant = 0;
    private String observacion = "";
    private String headerModal = "";
    private List<DispFaltanteRepuesto> list;
    
    public void prepareAgregar() {
        nombre = "";
        observacion = "";
        cant = 0;
    }
    
    public void agregar() {
        if (list == null) {
            list = new ArrayList<>();
        }
        DispFaltanteRepuesto dfr = new DispFaltanteRepuesto();
        dfr.setNombre(nombre);
        dfr.setCantidad(cant);
        dfr.setObservacion(observacion);
        dfr.setCreado(MovilidadUtil.fechaCompletaHoy());
        dfr.setEstadoReg(0);
        list.add(dfr);
        MovilidadUtil.addSuccessMessage("Agregado con exito.");
        prepareAgregar();
    }
    
    @Transactional
    public void guardar(String username, int idDispActividad) {
        if (list != null) {
            for (DispFaltanteRepuesto dispFaltanteRepuesto : list) {
                dispFaltanteRepuesto.setUsername(username);
                dispFaltanteRepuesto.setIdDispActividad(new DispActividad(idDispActividad));
                faltanteRepuestoEJB.create(dispFaltanteRepuesto);
            }
        }
    }
    
    public void viewRepuestoFaltantes(DispActividad param) {
        list = param.getDispFaltanteRepuestoList();
        headerModal = param.getIdNovedad().getIdVehiculo().getPlaca() + " - "
                + param.getIdNovedad().getIdVehiculo().getCodigo();
    }
    
    public void delete(DispFaltanteRepuesto param) {
        list.remove(param);
        MovilidadUtil.addSuccessMessage("Item removido.");
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getCant() {
        return cant;
    }
    
    public void setCant(int cant) {
        this.cant = cant;
    }
    
    public String getObservacion() {
        return observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    public List<DispFaltanteRepuesto> getList() {
        return list;
    }
    
    public void setList(List<DispFaltanteRepuesto> list) {
        this.list = list;
    }
    
    public String getHeaderModal() {
        return headerModal;
    }
    
    public void setHeaderModal(String headerModal) {
        this.headerModal = headerModal;
    }
    
}
