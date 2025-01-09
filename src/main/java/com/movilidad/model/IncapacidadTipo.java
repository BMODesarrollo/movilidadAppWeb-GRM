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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "incapacidad_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncapacidadTipo.findAll", query = "SELECT i FROM IncapacidadTipo i"),
    @NamedQuery(name = "IncapacidadTipo.findByIdIncapacidadTipo", query = "SELECT i FROM IncapacidadTipo i WHERE i.idIncapacidadTipo = :idIncapacidadTipo"),
    @NamedQuery(name = "IncapacidadTipo.findByUsername", query = "SELECT i FROM IncapacidadTipo i WHERE i.username = :username"),
    @NamedQuery(name = "IncapacidadTipo.findByCreado", query = "SELECT i FROM IncapacidadTipo i WHERE i.creado = :creado"),
    @NamedQuery(name = "IncapacidadTipo.findByModificado", query = "SELECT i FROM IncapacidadTipo i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "IncapacidadTipo.findByEstadoReg", query = "SELECT i FROM IncapacidadTipo i WHERE i.estadoReg = :estadoReg")})
public class IncapacidadTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_incapacidad_tipo")
    private Integer idIncapacidadTipo;
    @Lob
    @Size(max = 65535)
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
    @OneToMany(mappedBy = "idIncapacidadTipo")
    private List<NovedadIncapacidad> novedadIncapacidadList;

    public IncapacidadTipo() {
    }

    public IncapacidadTipo(Integer idIncapacidadTipo) {
        this.idIncapacidadTipo = idIncapacidadTipo;
    }

    public Integer getIdIncapacidadTipo() {
        return idIncapacidadTipo;
    }

    public void setIdIncapacidadTipo(Integer idIncapacidadTipo) {
        this.idIncapacidadTipo = idIncapacidadTipo;
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
    public List<NovedadIncapacidad> getNovedadIncapacidadList() {
        return novedadIncapacidadList;
    }

    public void setNovedadIncapacidadList(List<NovedadIncapacidad> novedadIncapacidadList) {
        this.novedadIncapacidadList = novedadIncapacidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIncapacidadTipo != null ? idIncapacidadTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncapacidadTipo)) {
            return false;
        }
        IncapacidadTipo other = (IncapacidadTipo) object;
        if ((this.idIncapacidadTipo == null && other.idIncapacidadTipo != null) || (this.idIncapacidadTipo != null && !this.idIncapacidadTipo.equals(other.idIncapacidadTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.IncapacidadTipo[ idIncapacidadTipo=" + idIncapacidadTipo + " ]";
    }
    
}
