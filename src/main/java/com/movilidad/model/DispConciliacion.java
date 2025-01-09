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
import javax.persistence.CascadeType;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "disp_conciliacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispConciliacion.findAll", query = "SELECT d FROM DispConciliacion d"),
    @NamedQuery(name = "DispConciliacion.findByIdDispConciliacion", query = "SELECT d FROM DispConciliacion d WHERE d.idDispConciliacion = :idDispConciliacion"),
    @NamedQuery(name = "DispConciliacion.findByFechaHora", query = "SELECT d FROM DispConciliacion d WHERE d.fechaHora = :fechaHora"),
    @NamedQuery(name = "DispConciliacion.findByUsername", query = "SELECT d FROM DispConciliacion d WHERE d.username = :username"),
    @NamedQuery(name = "DispConciliacion.findByCreado", query = "SELECT d FROM DispConciliacion d WHERE d.creado = :creado"),
    @NamedQuery(name = "DispConciliacion.findByModificado", query = "SELECT d FROM DispConciliacion d WHERE d.modificado = :modificado"),
    @NamedQuery(name = "DispConciliacion.findByEstadoReg", query = "SELECT d FROM DispConciliacion d WHERE d.estadoReg = :estadoReg")})
public class DispConciliacion implements Serializable {

    @OneToMany(mappedBy = "idDispConciliacion", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<DispConciliacionResumen> dispConciliacionResumenList;

    @OneToMany(mappedBy = "idDispConciliacion", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<DispConciliacionDet> dispConciliacionDetList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_conciliacion")
    private Integer idDispConciliacion;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
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

    public DispConciliacion() {
    }

    public DispConciliacion(Integer idDispConciliacion) {
        this.idDispConciliacion = idDispConciliacion;
    }

    public DispConciliacion(Integer idDispConciliacion, String username, Date creado, int estadoReg) {
        this.idDispConciliacion = idDispConciliacion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDispConciliacion() {
        return idDispConciliacion;
    }

    public void setIdDispConciliacion(Integer idDispConciliacion) {
        this.idDispConciliacion = idDispConciliacion;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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
        hash += (idDispConciliacion != null ? idDispConciliacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispConciliacion)) {
            return false;
        }
        DispConciliacion other = (DispConciliacion) object;
        if ((this.idDispConciliacion == null && other.idDispConciliacion != null) || (this.idDispConciliacion != null && !this.idDispConciliacion.equals(other.idDispConciliacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispConciliacion[ idDispConciliacion=" + idDispConciliacion + " ]";
    }

    @XmlTransient
    public List<DispConciliacionDet> getDispConciliacionDetList() {
        return dispConciliacionDetList;
    }

    public void setDispConciliacionDetList(List<DispConciliacionDet> dispConciliacionDetList) {
        this.dispConciliacionDetList = dispConciliacionDetList;
    }

    @XmlTransient
    public List<DispConciliacionResumen> getDispConciliacionResumenList() {
        return dispConciliacionResumenList;
    }

    public void setDispConciliacionResumenList(List<DispConciliacionResumen> dispConciliacionResumenList) {
        this.dispConciliacionResumenList = dispConciliacionResumenList;
    }

}
