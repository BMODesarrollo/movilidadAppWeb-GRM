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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "snc_correcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SncCorrecion.findAll", query = "SELECT s FROM SncCorrecion s")
    , @NamedQuery(name = "SncCorrecion.findByIdSncCorrecion", query = "SELECT s FROM SncCorrecion s WHERE s.idSncCorrecion = :idSncCorrecion")
    , @NamedQuery(name = "SncCorrecion.findByCodigo", query = "SELECT s FROM SncCorrecion s WHERE s.codigo = :codigo")
    , @NamedQuery(name = "SncCorrecion.findByDescripcion", query = "SELECT s FROM SncCorrecion s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "SncCorrecion.findByUsername", query = "SELECT s FROM SncCorrecion s WHERE s.username = :username")
    , @NamedQuery(name = "SncCorrecion.findByCreado", query = "SELECT s FROM SncCorrecion s WHERE s.creado = :creado")
    , @NamedQuery(name = "SncCorrecion.findByModificado", query = "SELECT s FROM SncCorrecion s WHERE s.modificado = :modificado")
    , @NamedQuery(name = "SncCorrecion.findByEstadoReg", query = "SELECT s FROM SncCorrecion s WHERE s.estadoReg = :estadoReg")})
public class SncCorrecion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_snc_correcion")
    private Integer idSncCorrecion;
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
    @OneToMany(mappedBy = "idSncCorrecion", fetch = FetchType.LAZY)
    private List<SncDetalle> sncDetalleList;

    public SncCorrecion() {
    }

    public SncCorrecion(Integer idSncCorrecion) {
        this.idSncCorrecion = idSncCorrecion;
    }

    public SncCorrecion(Integer idSncCorrecion, String username, Date creado, int estadoReg) {
        this.idSncCorrecion = idSncCorrecion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSncCorrecion() {
        return idSncCorrecion;
    }

    public void setIdSncCorrecion(Integer idSncCorrecion) {
        this.idSncCorrecion = idSncCorrecion;
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
        hash += (idSncCorrecion != null ? idSncCorrecion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SncCorrecion)) {
            return false;
        }
        SncCorrecion other = (SncCorrecion) object;
        if ((this.idSncCorrecion == null && other.idSncCorrecion != null) || (this.idSncCorrecion != null && !this.idSncCorrecion.equals(other.idSncCorrecion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SncCorrecion[ idSncCorrecion=" + idSncCorrecion + " ]";
    }
    
}
