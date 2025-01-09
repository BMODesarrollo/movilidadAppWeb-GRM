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
import javax.persistence.Lob;
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
@Table(name = "cable_evento_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableEventoTipo.findAll", query = "SELECT c FROM CableEventoTipo c"),
    @NamedQuery(name = "CableEventoTipo.findByIdCableEventoTipo", query = "SELECT c FROM CableEventoTipo c WHERE c.idCableEventoTipo = :idCableEventoTipo"),
    @NamedQuery(name = "CableEventoTipo.findByCodigo", query = "SELECT c FROM CableEventoTipo c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CableEventoTipo.findByNombre", query = "SELECT c FROM CableEventoTipo c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CableEventoTipo.findByUsername", query = "SELECT c FROM CableEventoTipo c WHERE c.username = :username"),
    @NamedQuery(name = "CableEventoTipo.findByCreado", query = "SELECT c FROM CableEventoTipo c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableEventoTipo.findByModificado", query = "SELECT c FROM CableEventoTipo c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableEventoTipo.findByEstadoReg", query = "SELECT c FROM CableEventoTipo c WHERE c.estadoReg = :estadoReg")})
public class CableEventoTipo implements Serializable {
    @OneToMany(mappedBy = "idCableEventoTipo", fetch = FetchType.LAZY)
    private List<CableNovedadOp> cableNovedadOpList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_evento_tipo")
    private Integer idCableEventoTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @OneToMany(mappedBy = "idCableEventoTipo", fetch = FetchType.LAZY)
    private List<CableEventoTipoDet> cableEventoTipoDetList;

    public CableEventoTipo() {
    }

    public CableEventoTipo(Integer idCableEventoTipo) {
        this.idCableEventoTipo = idCableEventoTipo;
    }

    public CableEventoTipo(Integer idCableEventoTipo, String codigo, String nombre, String descripcion, int estadoReg) {
        this.idCableEventoTipo = idCableEventoTipo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableEventoTipo() {
        return idCableEventoTipo;
    }

    public void setIdCableEventoTipo(Integer idCableEventoTipo) {
        this.idCableEventoTipo = idCableEventoTipo;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<CableEventoTipoDet> getCableEventoTipoDetList() {
        return cableEventoTipoDetList;
    }

    public void setCableEventoTipoDetList(List<CableEventoTipoDet> cableEventoTipoDetList) {
        this.cableEventoTipoDetList = cableEventoTipoDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableEventoTipo != null ? idCableEventoTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableEventoTipo)) {
            return false;
        }
        CableEventoTipo other = (CableEventoTipo) object;
        if ((this.idCableEventoTipo == null && other.idCableEventoTipo != null) || (this.idCableEventoTipo != null && !this.idCableEventoTipo.equals(other.idCableEventoTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableEventoTipo[ idCableEventoTipo=" + idCableEventoTipo + " ]";
    }

    @XmlTransient
    public List<CableNovedadOp> getCableNovedadOpList() {
        return cableNovedadOpList;
    }

    public void setCableNovedadOpList(List<CableNovedadOp> cableNovedadOpList) {
        this.cableNovedadOpList = cableNovedadOpList;
    }
    
}
