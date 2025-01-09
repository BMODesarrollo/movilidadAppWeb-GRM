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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "cable_tramo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableTramo.findAll", query = "SELECT c FROM CableTramo c")
    , @NamedQuery(name = "CableTramo.findByIdCableTramo", query = "SELECT c FROM CableTramo c WHERE c.idCableTramo = :idCableTramo")
    , @NamedQuery(name = "CableTramo.findByCodigo", query = "SELECT c FROM CableTramo c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "CableTramo.findByNombre", query = "SELECT c FROM CableTramo c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "CableTramo.findByDescripcion", query = "SELECT c FROM CableTramo c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "CableTramo.findByUsername", query = "SELECT c FROM CableTramo c WHERE c.username = :username")
    , @NamedQuery(name = "CableTramo.findByCreado", query = "SELECT c FROM CableTramo c WHERE c.creado = :creado")
    , @NamedQuery(name = "CableTramo.findByModificado", query = "SELECT c FROM CableTramo c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "CableTramo.findByEstadoReg", query = "SELECT c FROM CableTramo c WHERE c.estadoReg = :estadoReg")})
public class CableTramo implements Serializable {
    
    @OneToMany(mappedBy = "idCableTramo", fetch = FetchType.LAZY)
    private List<CableNovedadOp> cableNovedadOpList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_tramo")
    private Integer idCableTramo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
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

    public CableTramo() {
    }

    public CableTramo(Integer idCableTramo) {
        this.idCableTramo = idCableTramo;
    }

    public CableTramo(Integer idCableTramo, String codigo, String nombre, String descripcion) {
        this.idCableTramo = idCableTramo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdCableTramo() {
        return idCableTramo;
    }

    public void setIdCableTramo(Integer idCableTramo) {
        this.idCableTramo = idCableTramo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    public List<CableNovedadOp> getCableNovedadOpList() {
        return cableNovedadOpList;
    }

    public void setCableNovedadOpList(List<CableNovedadOp> cableNovedadOpList) {
        this.cableNovedadOpList = cableNovedadOpList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableTramo != null ? idCableTramo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableTramo)) {
            return false;
        }
        CableTramo other = (CableTramo) object;
        if ((this.idCableTramo == null && other.idCableTramo != null) || (this.idCableTramo != null && !this.idCableTramo.equals(other.idCableTramo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableTramo[ idCableTramo=" + idCableTramo + " ]";
    }
    
}
