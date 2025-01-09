/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgVehicleStatusFacadeLocal;
import com.movilidad.model.PrgVehicleStatus;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author luis
 */
@Named(value = "mttoCumpleDiarioMB")
@ViewScoped
public class MttoCumpleDiarioMB implements Serializable {

    @EJB
    private PrgVehicleStatusFacadeLocal prgVehicleStatusEjb;
    private Date fromDate;
    private Date toDate;
    private List<PrgVehicleStatus> listVehicleStatus;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of prgRouteMB
     */
    public MttoCumpleDiarioMB() {
    }

    public void consumeDistance() {

    }

    @PostConstruct
    public void init() {
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public List<PrgVehicleStatus> getListVehicleStatus() {
        return listVehicleStatus;
    }

    public void setListVehicleStatus(List<PrgVehicleStatus> listVehicleStatus) {
        this.listVehicleStatus = listVehicleStatus;
    }

    public void getByDate() {
        if (fromDate != null && toDate != null) {
            listVehicleStatus = prgVehicleStatusEjb.findByDate(fromDate, toDate);
            if (listVehicleStatus.isEmpty()) {
                MovilidadUtil.addErrorMessage("No se encontraron datos para la fecha indicada");
            }
        }
    }

    public String fechaIni() {
        return Util.dateFormat(fromDate);
    }

    public String fechaFin() {
        return Util.dateFormat(toDate);
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}
