/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.freeway.LiquidationTimeReportLogicLiquidationRow;
import com.movilidad.utils.Util;
import java.io.Serializable;

/**
 *
 * @author Luis Vélez
 */
public class ErrorLiquidacion implements Serializable {

    String fecha;
    String driver_code;
    String driver_name;
    String production_time;
    String error;

    public ErrorLiquidacion(LiquidationTimeReportLogicLiquidationRow vs) {
        this.fecha = (vs.getDate().getValue());
        this.driver_code = vs.getCodeDriver().getValue();
        this.driver_name = vs.getDriver().getValue();
        this.production_time = Util.durationToStr(vs.getProductionTime());
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDriver_code() {
        return driver_code;
    }

    public void setDriver_code(String driver_code) {
        this.driver_code = driver_code;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getProduction_time() {
        return production_time;
    }

    public void setProduction_time(String production_time) {
        this.production_time = production_time;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
