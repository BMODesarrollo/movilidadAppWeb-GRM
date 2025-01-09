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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "cable_acc_clasificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableAccClasificacion.findAll", query = "SELECT c FROM CableAccClasificacion c")
    , @NamedQuery(name = "CableAccClasificacion.findByIdCableAccClasificacion", query = "SELECT c FROM CableAccClasificacion c WHERE c.idCableAccClasificacion = :idCableAccClasificacion")
    , @NamedQuery(name = "CableAccClasificacion.findByTipo", query = "SELECT c FROM CableAccClasificacion c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "CableAccClasificacion.findByDescripcion", query = "SELECT c FROM CableAccClasificacion c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "CableAccClasificacion.findByUsername", query = "SELECT c FROM CableAccClasificacion c WHERE c.username = :username")
    , @NamedQuery(name = "CableAccClasificacion.findByCreado", query = "SELECT c FROM CableAccClasificacion c WHERE c.creado = :creado")
    , @NamedQuery(name = "CableAccClasificacion.findByModificado", query = "SELECT c FROM CableAccClasificacion c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "CableAccClasificacion.findByEstadoReg", query = "SELECT c FROM CableAccClasificacion c WHERE c.estadoReg = :estadoReg")})
public class CableAccClasificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_acc_clasificacion")
    private Integer idCableAccClasificacion;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(mappedBy = "idCableAccClasificacion", fetch = FetchType.LAZY)
    private List<CableAccidentalidad> cableAccidentalidadList;

    public CableAccClasificacion() {
    }

    public CableAccClasificacion(Integer idCableAccClasificacion) {
        this.idCableAccClasificacion = idCableAccClasificacion;
    }

    public Integer getIdCableAccClasificacion() {
        return idCableAccClasificacion;
    }

    public void setIdCableAccClasificacion(Integer idCableAccClasificacion) {
        this.idCableAccClasificacion = idCableAccClasificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @XmlTransient
    public List<CableAccidentalidad> getCableAccidentalidadList() {
        return cableAccidentalidadList;
    }

    public void setCableAccidentalidadList(List<CableAccidentalidad> cableAccidentalidadList) {
        this.cableAccidentalidadList = cableAccidentalidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccClasificacion != null ? idCableAccClasificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccClasificacion)) {
            return false;
        }
        CableAccClasificacion other = (CableAccClasificacion) object;
        if ((this.idCableAccClasificacion == null && other.idCableAccClasificacion != null) || (this.idCableAccClasificacion != null && !this.idCableAccClasificacion.equals(other.idCableAccClasificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccClasificacion[ idCableAccClasificacion=" + idCableAccClasificacion + " ]";
    }
    
}
