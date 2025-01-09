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
 * @author cesar
 */
@Entity
@Table(name = "sst_eps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstEps.findAll", query = "SELECT s FROM SstEps s"),
    @NamedQuery(name = "SstEps.findByIdSstEps", query = "SELECT s FROM SstEps s WHERE s.idSstEps = :idSstEps"),
    @NamedQuery(name = "SstEps.findByNombre", query = "SELECT s FROM SstEps s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SstEps.findByDescripcion", query = "SELECT s FROM SstEps s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SstEps.findByUsername", query = "SELECT s FROM SstEps s WHERE s.username = :username"),
    @NamedQuery(name = "SstEps.findByCreado", query = "SELECT s FROM SstEps s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstEps.findByModificado", query = "SELECT s FROM SstEps s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstEps.findByEstadoReg", query = "SELECT s FROM SstEps s WHERE s.estadoReg = :estadoReg")})
public class SstEps implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_eps")
    private Integer idSstEps;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSstEps", fetch = FetchType.LAZY)
    private List<SstEmpresaVisitante> sstEmpresaVisitanteList;

    public SstEps() {
    }

    public SstEps(Integer idSstEps) {
        this.idSstEps = idSstEps;
    }

    public Integer getIdSstEps() {
        return idSstEps;
    }

    public void setIdSstEps(Integer idSstEps) {
        this.idSstEps = idSstEps;
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
    public List<SstEmpresaVisitante> getSstEmpresaVisitanteList() {
        return sstEmpresaVisitanteList;
    }

    public void setSstEmpresaVisitanteList(List<SstEmpresaVisitante> sstEmpresaVisitanteList) {
        this.sstEmpresaVisitanteList = sstEmpresaVisitanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstEps != null ? idSstEps.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstEps)) {
            return false;
        }
        SstEps other = (SstEps) object;
        if ((this.idSstEps == null && other.idSstEps != null) || (this.idSstEps != null && !this.idSstEps.equals(other.idSstEps))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstEps[ idSstEps=" + idSstEps + " ]";
    }
    
}
