/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.MttoAsig;
import com.movilidad.model.PrgVehicleStatus;
import java.io.Serializable;

/**
 *
 * @author solucionesit
 */
public class MttoAsigPrgVehicleStatus implements Serializable , Comparable<MttoAsigPrgVehicleStatus> {

    private MttoAsig mttoAsig;
    private PrgVehicleStatus prgVehicleStatus;

    public MttoAsigPrgVehicleStatus() {
    }

    public MttoAsigPrgVehicleStatus(MttoAsig mttoAsig, PrgVehicleStatus prgVehicleStatus) {
        this.mttoAsig = mttoAsig;
        this.prgVehicleStatus = prgVehicleStatus;
    }

    public MttoAsigPrgVehicleStatus(PrgVehicleStatus prgVehicleStatus) {
        this.prgVehicleStatus = prgVehicleStatus;
    }
    
    
    public MttoAsig getMttoAsig() {
        return mttoAsig;
    }

    public void setMttoAsig(MttoAsig mttoAsig) {
        this.mttoAsig = mttoAsig;
    }

    public PrgVehicleStatus getPrgVehicleStatus() {
        return prgVehicleStatus;
    }

    public void setPrgVehicleStatus(PrgVehicleStatus prgVehicleStatus) {
        this.prgVehicleStatus = prgVehicleStatus;
    }
    
    @Override
    public int compareTo(MttoAsigPrgVehicleStatus e) {
        return e.getPrgVehicleStatus().getProductionDistance().compareTo(prgVehicleStatus.getProductionDistance());
    }

}
