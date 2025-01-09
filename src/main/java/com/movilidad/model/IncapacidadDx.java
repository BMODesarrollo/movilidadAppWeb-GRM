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
@Table(name = "incapacidad_dx")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncapacidadDx.findAll", query = "SELECT i FROM IncapacidadDx i"),
    @NamedQuery(name = "IncapacidadDx.findByIdIncapacidadDx", query = "SELECT i FROM IncapacidadDx i WHERE i.idIncapacidadDx = :idIncapacidadDx"),
    @NamedQuery(name = "IncapacidadDx.findByCodigo", query = "SELECT i FROM IncapacidadDx i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "IncapacidadDx.findBySexo", query = "SELECT i FROM IncapacidadDx i WHERE i.sexo = :sexo"),
    @NamedQuery(name = "IncapacidadDx.findByUsername", query = "SELECT i FROM IncapacidadDx i WHERE i.username = :username"),
    @NamedQuery(name = "IncapacidadDx.findByCreado", query = "SELECT i FROM IncapacidadDx i WHERE i.creado = :creado"),
    @NamedQuery(name = "IncapacidadDx.findByModificado", query = "SELECT i FROM IncapacidadDx i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "IncapacidadDx.findByEstadoReg", query = "SELECT i FROM IncapacidadDx i WHERE i.estadoReg = :estadoReg")})
public class IncapacidadDx implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_incapacidad_dx")
    private Integer idIncapacidadDx;
    @Size(max = 5)
    @Column(name = "codigo")
    private String codigo;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "sexo")
    private Character sexo;
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
    @OneToMany(mappedBy = "idIncapacidadDx")
    private List<NovedadIncapacidad> novedadIncapacidadList;

    public IncapacidadDx() {
    }

    public IncapacidadDx(Integer idIncapacidadDx) {
        this.idIncapacidadDx = idIncapacidadDx;
    }

    public Integer getIdIncapacidadDx() {
        return idIncapacidadDx;
    }

    public void setIdIncapacidadDx(Integer idIncapacidadDx) {
        this.idIncapacidadDx = idIncapacidadDx;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
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
        hash += (idIncapacidadDx != null ? idIncapacidadDx.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncapacidadDx)) {
            return false;
        }
        IncapacidadDx other = (IncapacidadDx) object;
        if ((this.idIncapacidadDx == null && other.idIncapacidadDx != null) || (this.idIncapacidadDx != null && !this.idIncapacidadDx.equals(other.idIncapacidadDx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.IncapacidadDx[ idIncapacidadDx=" + idIncapacidadDx + " ]";
    }
    
}
