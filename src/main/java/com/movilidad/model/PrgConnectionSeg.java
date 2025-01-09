/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "prg_connection_seg")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgConnectionSeg.findAll", query = "SELECT p FROM PrgConnectionSeg p")
    , @NamedQuery(name = "PrgConnectionSeg.findByIdPrgConnectionSeg", query = "SELECT p FROM PrgConnectionSeg p WHERE p.idPrgConnectionSeg = :idPrgConnectionSeg")
    , @NamedQuery(name = "PrgConnectionSeg.findByIdConnectionSeg", query = "SELECT p FROM PrgConnectionSeg p WHERE p.idConnectionSeg = :idConnectionSeg")
    , @NamedQuery(name = "PrgConnectionSeg.findByName", query = "SELECT p FROM PrgConnectionSeg p WHERE p.name = :name")
    , @NamedQuery(name = "PrgConnectionSeg.findByDescription", query = "SELECT p FROM PrgConnectionSeg p WHERE p.description = :description")
    , @NamedQuery(name = "PrgConnectionSeg.findByIdFromStopPoint", query = "SELECT p FROM PrgConnectionSeg p WHERE p.idFromStopPoint = :idFromStopPoint")
    , @NamedQuery(name = "PrgConnectionSeg.findByFromStopPoint", query = "SELECT p FROM PrgConnectionSeg p WHERE p.fromStopPoint = :fromStopPoint")
    , @NamedQuery(name = "PrgConnectionSeg.findByIdToStopPoint", query = "SELECT p FROM PrgConnectionSeg p WHERE p.idToStopPoint = :idToStopPoint")
    , @NamedQuery(name = "PrgConnectionSeg.findByToStopPoint", query = "SELECT p FROM PrgConnectionSeg p WHERE p.toStopPoint = :toStopPoint")
    , @NamedQuery(name = "PrgConnectionSeg.findByDistance", query = "SELECT p FROM PrgConnectionSeg p WHERE p.distance = :distance")
    , @NamedQuery(name = "PrgConnectionSeg.findByIsActive", query = "SELECT p FROM PrgConnectionSeg p WHERE p.isActive = :isActive")
    , @NamedQuery(name = "PrgConnectionSeg.findByOptTime", query = "SELECT p FROM PrgConnectionSeg p WHERE p.optTime = :optTime")
    , @NamedQuery(name = "PrgConnectionSeg.findBySpeedLimit", query = "SELECT p FROM PrgConnectionSeg p WHERE p.speedLimit = :speedLimit")
    , @NamedQuery(name = "PrgConnectionSeg.findByUsername", query = "SELECT p FROM PrgConnectionSeg p WHERE p.username = :username")
    , @NamedQuery(name = "PrgConnectionSeg.findByCreado", query = "SELECT p FROM PrgConnectionSeg p WHERE p.creado = :creado")
    , @NamedQuery(name = "PrgConnectionSeg.findByModificado", query = "SELECT p FROM PrgConnectionSeg p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgConnectionSeg.findByEstadoReg", query = "SELECT p FROM PrgConnectionSeg p WHERE p.estadoReg = :estadoReg")})
public class PrgConnectionSeg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_connection_seg")
    private Integer idPrgConnectionSeg;
    @Size(max = 40)
    @Column(name = "id_connection_seg")
    private String idConnectionSeg;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 150)
    @Column(name = "description")
    private String description;
    @Size(max = 40)
    @Column(name = "id_from_stop_point")
    private String idFromStopPoint;
    @Size(max = 45)
    @Column(name = "from_stop_point")
    private String fromStopPoint;
    @Size(max = 40)
    @Column(name = "id_to_stop_point")
    private String idToStopPoint;
    @Size(max = 45)
    @Column(name = "to_stop_point")
    private String toStopPoint;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distance")
    private Double distance;
    @Column(name = "is_Active")
    private Integer isActive;
    @Size(max = 8)
    @Column(name = "opt_time")
    private String optTime;
    @Column(name = "speed_limit")
    private Integer speedLimit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;

    public PrgConnectionSeg() {
    }

    public PrgConnectionSeg(Integer idPrgConnectionSeg) {
        this.idPrgConnectionSeg = idPrgConnectionSeg;
    }

    public PrgConnectionSeg(Integer idPrgConnectionSeg, String username, Date creado, int estadoReg) {
        this.idPrgConnectionSeg = idPrgConnectionSeg;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgConnectionSeg() {
        return idPrgConnectionSeg;
    }

    public void setIdPrgConnectionSeg(Integer idPrgConnectionSeg) {
        this.idPrgConnectionSeg = idPrgConnectionSeg;
    }

    public String getIdConnectionSeg() {
        return idConnectionSeg;
    }

    public void setIdConnectionSeg(String idConnectionSeg) {
        this.idConnectionSeg = idConnectionSeg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdFromStopPoint() {
        return idFromStopPoint;
    }

    public void setIdFromStopPoint(String idFromStopPoint) {
        this.idFromStopPoint = idFromStopPoint;
    }

    public String getFromStopPoint() {
        return fromStopPoint;
    }

    public void setFromStopPoint(String fromStopPoint) {
        this.fromStopPoint = fromStopPoint;
    }

    public String getIdToStopPoint() {
        return idToStopPoint;
    }

    public void setIdToStopPoint(String idToStopPoint) {
        this.idToStopPoint = idToStopPoint;
    }

    public String getToStopPoint() {
        return toStopPoint;
    }

    public void setToStopPoint(String toStopPoint) {
        this.toStopPoint = toStopPoint;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgConnectionSeg != null ? idPrgConnectionSeg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgConnectionSeg)) {
            return false;
        }
        PrgConnectionSeg other = (PrgConnectionSeg) object;
        if ((this.idPrgConnectionSeg == null && other.idPrgConnectionSeg != null) || (this.idPrgConnectionSeg != null && !this.idPrgConnectionSeg.equals(other.idPrgConnectionSeg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgConnectionSeg[ idPrgConnectionSeg=" + idPrgConnectionSeg + " ]";
    }
    
}
