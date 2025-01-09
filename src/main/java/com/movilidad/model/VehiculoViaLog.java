/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * @author soluciones-it
 */
@Entity
@Table(name = "vehiculo_via_log")
public class VehiculoViaLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_via_log")
    private Integer idVehiculoViaLog;

    @Column(name = "username")
    private String username;

    @Column(name = "on_road")
    private boolean onRoad;
    
    @Column(name = "applied")
    private boolean applied;

    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Column(name = "origen")
    private String origen;
    
    @Column(name = "description")
    private String description;

    @JoinColumn(name = "id_vehiculo_via", referencedColumnName = "id_vehiculo_via")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoVia idVehiculoVia;

    public VehiculoViaLog() {
    }

    public Integer getIdVehiculoViaLog() {
        return idVehiculoViaLog;
    }

    public void setIdVehiculoViaLog(Integer idVehiculoViaLog) {
        this.idVehiculoViaLog = idVehiculoViaLog;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnRoad() {
        return onRoad;
    }

    public void setOnRoad(boolean onRoad) {
        this.onRoad = onRoad;
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VehiculoVia getIdVehiculoVia() {
        return idVehiculoVia;
    }

    public void setIdVehiculoVia(VehiculoVia idVehiculoVia) {
        this.idVehiculoVia = idVehiculoVia;
    }
    
    

}
