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
@Table(name = "sst_area_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstAreaEmpresa.findAll", query = "SELECT s FROM SstAreaEmpresa s"),
    @NamedQuery(name = "SstAreaEmpresa.findByIdSstAreaEmpresa", query = "SELECT s FROM SstAreaEmpresa s WHERE s.idSstAreaEmpresa = :idSstAreaEmpresa"),
    @NamedQuery(name = "SstAreaEmpresa.findByArea", query = "SELECT s FROM SstAreaEmpresa s WHERE s.area = :area"),
    @NamedQuery(name = "SstAreaEmpresa.findByDescripcion", query = "SELECT s FROM SstAreaEmpresa s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SstAreaEmpresa.findByUsername", query = "SELECT s FROM SstAreaEmpresa s WHERE s.username = :username"),
    @NamedQuery(name = "SstAreaEmpresa.findByCreado", query = "SELECT s FROM SstAreaEmpresa s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstAreaEmpresa.findByModificado", query = "SELECT s FROM SstAreaEmpresa s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstAreaEmpresa.findByEstadoReg", query = "SELECT s FROM SstAreaEmpresa s WHERE s.estadoReg = :estadoReg")})
public class SstAreaEmpresa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_area_empresa")
    private Integer idSstAreaEmpresa;
    @Size(max = 45)
    @Column(name = "area")
    private String area;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSstAreaEmpresa", fetch = FetchType.LAZY)
    private List<SstAutorizacion> sstAutorizacionList;

    public SstAreaEmpresa() {
    }

    public SstAreaEmpresa(Integer idSstAreaEmpresa) {
        this.idSstAreaEmpresa = idSstAreaEmpresa;
    }

    public Integer getIdSstAreaEmpresa() {
        return idSstAreaEmpresa;
    }

    public void setIdSstAreaEmpresa(Integer idSstAreaEmpresa) {
        this.idSstAreaEmpresa = idSstAreaEmpresa;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
    public List<SstAutorizacion> getSstAutorizacionList() {
        return sstAutorizacionList;
    }

    public void setSstAutorizacionList(List<SstAutorizacion> sstAutorizacionList) {
        this.sstAutorizacionList = sstAutorizacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstAreaEmpresa != null ? idSstAreaEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstAreaEmpresa)) {
            return false;
        }
        SstAreaEmpresa other = (SstAreaEmpresa) object;
        if ((this.idSstAreaEmpresa == null && other.idSstAreaEmpresa != null) || (this.idSstAreaEmpresa != null && !this.idSstAreaEmpresa.equals(other.idSstAreaEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstAreaEmpresa[ idSstAreaEmpresa=" + idSstAreaEmpresa + " ]";
    }
    
}
