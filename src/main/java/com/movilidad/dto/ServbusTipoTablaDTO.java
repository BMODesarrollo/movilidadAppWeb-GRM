/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class ServbusTipoTablaDTO {

    @Column(name = "servbus")
    private String servbus;
    @Column(name = "num_entry_depot")
    private short numEntryDepot;

    public ServbusTipoTablaDTO(String servbus, short numEntryDepot) {
        this.servbus = servbus;
        this.numEntryDepot = numEntryDepot;
    }

    public ServbusTipoTablaDTO() {
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public short getNumEntryDepot() {
        return numEntryDepot;
    }

    public void setNumEntryDepot(short numEntryDepot) {
        this.numEntryDepot = numEntryDepot;
    }

}
