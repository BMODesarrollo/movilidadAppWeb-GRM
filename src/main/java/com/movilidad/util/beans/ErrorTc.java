/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.freeway.ControlReportLogicControlRow;
import java.io.Serializable;

/**
 *
 * @author Luis Vélez
 */
public class ErrorTc implements Serializable {

    String fecha;
    String sercon;
    String driverCode;
    String driver;
    String task_type;
    String fromStop;
    String toStop;
    String servbus;
    String line;
    String route;
    String error;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSercon() {
        return sercon;
    }

    public void setSercon(String sercon) {
        this.sercon = sercon;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getFromStop() {
        return fromStop;
    }

    public void setFromStop(String fromStop) {
        this.fromStop = fromStop;
    }

    public String getToStop() {
        return toStop;
    }

    public void setToStop(String toStop) {
        this.toStop = toStop;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public ErrorTc(ControlReportLogicControlRow tc) {
        this.fecha = tc.getDate().getValue();
        this.sercon = tc.getCrewService().getValue();
        this.driverCode= tc.getCodeDriver().getValue();
        this.driver = tc.getDriver().getValue();
        this.task_type = tc.getTaskType().getValue();
        this.fromStop = tc.getFromStopPoint().getValue();
        this.toStop = tc.getToStopPoint().getValue();
        this.servbus = tc.getVehicleService().getValue();
        this.line = tc.getLine().getValue();
        this.route = tc.getRoute().getValue();
    }
}
