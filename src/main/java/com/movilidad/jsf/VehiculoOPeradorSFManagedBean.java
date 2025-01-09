/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "vehiculoOPeradorSFMB")
@ViewScoped
public class VehiculoOPeradorSFManagedBean implements Serializable {

    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();
    private String codOperador = "";
    private String codVehiculo = "";
    private List<PrgTc> listInfo = new ArrayList<>();

    @EJB
    private PrgTcFacadeLocal prgTcEJB;

    /**
     * Creates a new instance of VehiculoOPeradorSFManagedBean
     */
    public VehiculoOPeradorSFManagedBean() {
    }

    public void consultar() {

        if (!codOperador.isEmpty()) {
            try {
                Integer.parseInt(codOperador);
            } catch (Exception e) {
                MovilidadUtil.addErrorMessage("Codigo empleado incorrecto");
                return;
            }
        }
        listInfo = prgTcEJB.reporteVehiculoOperador(desde, hasta, codVehiculo, codOperador);
        
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getCodOperador() {
        return codOperador;
    }

    public void setCodOperador(String codOperador) {
        this.codOperador = codOperador;
    }

    public String getCodVehiculo() {
        return codVehiculo;
    }

    public void setCodVehiculo(String codVehiculo) {
        this.codVehiculo = codVehiculo;
    }

    public List<PrgTc> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<PrgTc> listInfo) {
        this.listInfo = listInfo;
    }

}
