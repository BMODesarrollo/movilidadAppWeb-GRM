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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 */
@Entity
@Table(name = "sst_arl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstArl.findAll", query = "SELECT s FROM SstArl s")
    , @NamedQuery(name = "SstArl.findByIdSstArl", query = "SELECT s FROM SstArl s WHERE s.idSstArl = :idSstArl")
    , @NamedQuery(name = "SstArl.findByNombre", query = "SELECT s FROM SstArl s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "SstArl.findByDescripcion", query = "SELECT s FROM SstArl s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "SstArl.findByUsername", query = "SELECT s FROM SstArl s WHERE s.username = :username")
    , @NamedQuery(name = "SstArl.findByCreado", query = "SELECT s FROM SstArl s WHERE s.creado = :creado")
    , @NamedQuery(name = "SstArl.findByModificado", query = "SELECT s FROM SstArl s WHERE s.modificado = :modificado")
    , @NamedQuery(name = "SstArl.findByEstadoReg", query = "SELECT s FROM SstArl s WHERE s.estadoReg = :estadoReg")})
public class SstArl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_arl")
    private Integer idSstArl;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSstArl", fetch = FetchType.LAZY)
    private List<SstEmpresa> sstEmpresaList;

    public SstArl() {
    }

    public SstArl(Integer idSstArl) {
        this.idSstArl = idSstArl;
    }

    public Integer getIdSstArl() {
        return idSstArl;
    }

    public void setIdSstArl(Integer idSstArl) {
        this.idSstArl = idSstArl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public List<SstEmpresa> getSstEmpresaList() {
        return sstEmpresaList;
    }

    public void setSstEmpresaList(List<SstEmpresa> sstEmpresaList) {
        this.sstEmpresaList = sstEmpresaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstArl != null ? idSstArl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstArl)) {
            return false;
        }
        SstArl other = (SstArl) object;
        if ((this.idSstArl == null && other.idSstArl != null) || (this.idSstArl != null && !this.idSstArl.equals(other.idSstArl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstArl[ idSstArl=" + idSstArl + " ]";
    }
    
}
