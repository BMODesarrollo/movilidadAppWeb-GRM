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
 * @author HP
 */
@Entity
@Table(name = "snc_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SncTipo.findAll", query = "SELECT s FROM SncTipo s")
    , @NamedQuery(name = "SncTipo.findByIdSncTipo", query = "SELECT s FROM SncTipo s WHERE s.idSncTipo = :idSncTipo")
    , @NamedQuery(name = "SncTipo.findByCodigo", query = "SELECT s FROM SncTipo s WHERE s.codigo = :codigo")
    , @NamedQuery(name = "SncTipo.findByDescripcion", query = "SELECT s FROM SncTipo s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "SncTipo.findByUsername", query = "SELECT s FROM SncTipo s WHERE s.username = :username")
    , @NamedQuery(name = "SncTipo.findByCreado", query = "SELECT s FROM SncTipo s WHERE s.creado = :creado")
    , @NamedQuery(name = "SncTipo.findByModificado", query = "SELECT s FROM SncTipo s WHERE s.modificado = :modificado")
    , @NamedQuery(name = "SncTipo.findByEstadoReg", query = "SELECT s FROM SncTipo s WHERE s.estadoReg = :estadoReg")})
public class SncTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_snc_tipo")
    private Integer idSncTipo;
    @Size(max = 6)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSncTipo", fetch = FetchType.LAZY)
    private List<SncDetalle> sncDetalleList;

    public SncTipo() {
    }

    public SncTipo(Integer idSncTipo) {
        this.idSncTipo = idSncTipo;
    }

    public SncTipo(Integer idSncTipo, String username, Date creado, int estadoReg) {
        this.idSncTipo = idSncTipo;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSncTipo() {
        return idSncTipo;
    }

    public void setIdSncTipo(Integer idSncTipo) {
        this.idSncTipo = idSncTipo;
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
    public List<SncDetalle> getSncDetalleList() {
        return sncDetalleList;
    }

    public void setSncDetalleList(List<SncDetalle> sncDetalleList) {
        this.sncDetalleList = sncDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSncTipo != null ? idSncTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SncTipo)) {
            return false;
        }
        SncTipo other = (SncTipo) object;
        if ((this.idSncTipo == null && other.idSncTipo != null) || (this.idSncTipo != null && !this.idSncTipo.equals(other.idSncTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SncTipo[ idSncTipo=" + idSncTipo + " ]";
    }
    
}
