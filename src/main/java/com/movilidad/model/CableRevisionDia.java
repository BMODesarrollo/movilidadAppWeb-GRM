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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "cable_revision_dia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableRevisionDia.findAll", query = "SELECT c FROM CableRevisionDia c"),
    @NamedQuery(name = "CableRevisionDia.findByIdCableRevisionDia", query = "SELECT c FROM CableRevisionDia c WHERE c.idCableRevisionDia = :idCableRevisionDia"),
    @NamedQuery(name = "CableRevisionDia.findByFecha", query = "SELECT c FROM CableRevisionDia c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CableRevisionDia.findByUsername", query = "SELECT c FROM CableRevisionDia c WHERE c.username = :username"),
    @NamedQuery(name = "CableRevisionDia.findByCreado", query = "SELECT c FROM CableRevisionDia c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableRevisionDia.findByModificado", query = "SELECT c FROM CableRevisionDia c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableRevisionDia.findByEstadoReg", query = "SELECT c FROM CableRevisionDia c WHERE c.estadoReg = :estadoReg")})
public class CableRevisionDia implements Serializable {
    @OneToMany(mappedBy = "idCableRevisionDia", fetch = FetchType.LAZY)
    private List<CableRevisionDiaRta> cableRevisionDiaRtaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_revision_dia")
    private Integer idCableRevisionDia;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_cable_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEstacion idCableEstacion;

    public CableRevisionDia() {
    }

    public CableRevisionDia(Integer idCableRevisionDia) {
        this.idCableRevisionDia = idCableRevisionDia;
    }

    public Integer getIdCableRevisionDia() {
        return idCableRevisionDia;
    }

    public void setIdCableRevisionDia(Integer idCableRevisionDia) {
        this.idCableRevisionDia = idCableRevisionDia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public CableEstacion getIdCableEstacion() {
        return idCableEstacion;
    }

    public void setIdCableEstacion(CableEstacion idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableRevisionDia != null ? idCableRevisionDia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableRevisionDia)) {
            return false;
        }
        CableRevisionDia other = (CableRevisionDia) object;
        if ((this.idCableRevisionDia == null && other.idCableRevisionDia != null) || (this.idCableRevisionDia != null && !this.idCableRevisionDia.equals(other.idCableRevisionDia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableRevisionDia[ idCableRevisionDia=" + idCableRevisionDia + " ]";
    }

    @XmlTransient
    public List<CableRevisionDiaRta> getCableRevisionDiaRtaList() {
        return cableRevisionDiaRtaList;
    }

    public void setCableRevisionDiaRtaList(List<CableRevisionDiaRta> cableRevisionDiaRtaList) {
        this.cableRevisionDiaRtaList = cableRevisionDiaRtaList;
    }
    
}
