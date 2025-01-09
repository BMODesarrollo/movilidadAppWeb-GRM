/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author julian.arevalo
 */
@Entity
@Table(name = "prg_tc")
public class TecnicoPatioTipoTabla {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "servbus")
    private String servbus;

    @Column(name = "num_entry_depot")
    private int numEntryDepot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public int getNumEntryDepot() {
        return numEntryDepot;
    }

    public void setNumEntryDepot(int numEntryDepot) {
        this.numEntryDepot = numEntryDepot;
    }

}
