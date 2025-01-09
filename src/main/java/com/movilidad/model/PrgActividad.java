/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "prg_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgActividad.findAll", query = "SELECT p FROM PrgActividad p")
    , @NamedQuery(name = "PrgActividad.findByIdPrgActividad", query = "SELECT p FROM PrgActividad p WHERE p.idPrgActividad = :idPrgActividad")
    , @NamedQuery(name = "PrgActividad.findByActividad", query = "SELECT p FROM PrgActividad p WHERE p.actividad = :actividad")
    , @NamedQuery(name = "PrgActividad.findByUsername", query = "SELECT p FROM PrgActividad p WHERE p.username = :username")
    , @NamedQuery(name = "PrgActividad.findByCreado", query = "SELECT p FROM PrgActividad p WHERE p.creado = :creado")
    , @NamedQuery(name = "PrgActividad.findByModificado", query = "SELECT p FROM PrgActividad p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgActividad.findByEstadoReg", query = "SELECT p FROM PrgActividad p WHERE p.estadoReg = :estadoReg")})
public class PrgActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_actividad")
    private Integer idPrgActividad;
    @Size(max = 45)
    @Column(name = "actividad")
    private String actividad;
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
    @OneToMany(mappedBy = "idPrgActividad", fetch = FetchType.LAZY)
    private List<PrgVehicleStatus> prgVehicleStatusList;

    public PrgActividad() {
    }

    public PrgActividad(Integer idPrgActividad) {
        this.idPrgActividad = idPrgActividad;
    }

    public PrgActividad(Integer idPrgActividad, String username, Date creado, int estadoReg) {
        this.idPrgActividad = idPrgActividad;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgActividad() {
        return idPrgActividad;
    }

    public void setIdPrgActividad(Integer idPrgActividad) {
        this.idPrgActividad = idPrgActividad;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
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

    @XmlTransient
    public List<PrgVehicleStatus> getPrgVehicleStatusList() {
        return prgVehicleStatusList;
    }

    public void setPrgVehicleStatusList(List<PrgVehicleStatus> prgVehicleStatusList) {
        this.prgVehicleStatusList = prgVehicleStatusList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgActividad != null ? idPrgActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgActividad)) {
            return false;
        }
        PrgActividad other = (PrgActividad) object;
        if ((this.idPrgActividad == null && other.idPrgActividad != null) || (this.idPrgActividad != null && !this.idPrgActividad.equals(other.idPrgActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgActividad[ idPrgActividad=" + idPrgActividad + " ]";
    }
    
}
