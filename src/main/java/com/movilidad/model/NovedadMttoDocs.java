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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "novedad_mtto_docs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadMttoDocs.findAll", query = "SELECT n FROM NovedadMttoDocs n"),
    @NamedQuery(name = "NovedadMttoDocs.findByIdNovedadMttoDocs", query = "SELECT n FROM NovedadMttoDocs n WHERE n.idNovedadMttoDocs = :idNovedadMttoDocs"),
    @NamedQuery(name = "NovedadMttoDocs.findByPath", query = "SELECT n FROM NovedadMttoDocs n WHERE n.path = :path"),
    @NamedQuery(name = "NovedadMttoDocs.findByUsername", query = "SELECT n FROM NovedadMttoDocs n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadMttoDocs.findByCreado", query = "SELECT n FROM NovedadMttoDocs n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadMttoDocs.findByModificado", query = "SELECT n FROM NovedadMttoDocs n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadMttoDocs.findByEstadoReg", query = "SELECT n FROM NovedadMttoDocs n WHERE n.estadoReg = :estadoReg")})
public class NovedadMttoDocs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_mtto_docs")
    private Integer idNovedadMttoDocs;
    @Size(max = 255)
    @Column(name = "path")
    private String path;
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
    @JoinColumn(name = "id_novedad_mtto", referencedColumnName = "id_novedad_mtto")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadMtto idNovedadMtto;

    public NovedadMttoDocs() {
    }

    public NovedadMttoDocs(Integer idNovedadMttoDocs) {
        this.idNovedadMttoDocs = idNovedadMttoDocs;
    }

    public Integer getIdNovedadMttoDocs() {
        return idNovedadMttoDocs;
    }

    public void setIdNovedadMttoDocs(Integer idNovedadMttoDocs) {
        this.idNovedadMttoDocs = idNovedadMttoDocs;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public NovedadMtto getIdNovedadMtto() {
        return idNovedadMtto;
    }

    public void setIdNovedadMtto(NovedadMtto idNovedadMtto) {
        this.idNovedadMtto = idNovedadMtto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadMttoDocs != null ? idNovedadMttoDocs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadMttoDocs)) {
            return false;
        }
        NovedadMttoDocs other = (NovedadMttoDocs) object;
        if ((this.idNovedadMttoDocs == null && other.idNovedadMttoDocs != null) || (this.idNovedadMttoDocs != null && !this.idNovedadMttoDocs.equals(other.idNovedadMttoDocs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadMttoDocs[ idNovedadMttoDocs=" + idNovedadMttoDocs + " ]";
    }
    
}
