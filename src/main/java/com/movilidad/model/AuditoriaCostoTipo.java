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
@Table(name = "auditoria_costo_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaCostoTipo.findAll", query = "SELECT a FROM AuditoriaCostoTipo a"),
    @NamedQuery(name = "AuditoriaCostoTipo.findByIdAuditoriaCostoTipo", query = "SELECT a FROM AuditoriaCostoTipo a WHERE a.idAuditoriaCostoTipo = :idAuditoriaCostoTipo"),
    @NamedQuery(name = "AuditoriaCostoTipo.findByNombre", query = "SELECT a FROM AuditoriaCostoTipo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AuditoriaCostoTipo.findByRequiereTipoVehiculo", query = "SELECT a FROM AuditoriaCostoTipo a WHERE a.requiereTipoVehiculo = :requiereTipoVehiculo"),
    @NamedQuery(name = "AuditoriaCostoTipo.findByUsername", query = "SELECT a FROM AuditoriaCostoTipo a WHERE a.username = :username"),
    @NamedQuery(name = "AuditoriaCostoTipo.findByCreado", query = "SELECT a FROM AuditoriaCostoTipo a WHERE a.creado = :creado"),
    @NamedQuery(name = "AuditoriaCostoTipo.findByModificado", query = "SELECT a FROM AuditoriaCostoTipo a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AuditoriaCostoTipo.findByEstadoReg", query = "SELECT a FROM AuditoriaCostoTipo a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaCostoTipo implements Serializable {

    @OneToMany(mappedBy = "idAuditoriaCostoTipo", fetch = FetchType.LAZY)
    private List<AuditoriaCosto> auditoriaCostoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_costo_tipo")
    private Integer idAuditoriaCostoTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requiere_tipo_vehiculo")
    private int requiereTipoVehiculo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
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

    public AuditoriaCostoTipo() {
    }

    public AuditoriaCostoTipo(Integer idAuditoriaCostoTipo) {
        this.idAuditoriaCostoTipo = idAuditoriaCostoTipo;
    }

    public AuditoriaCostoTipo(Integer idAuditoriaCostoTipo, String nombre, String descripcion, int requiereTipoVehiculo, String username, int estadoReg) {
        this.idAuditoriaCostoTipo = idAuditoriaCostoTipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requiereTipoVehiculo = requiereTipoVehiculo;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaCostoTipo() {
        return idAuditoriaCostoTipo;
    }

    public void setIdAuditoriaCostoTipo(Integer idAuditoriaCostoTipo) {
        this.idAuditoriaCostoTipo = idAuditoriaCostoTipo;
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

    public int getRequiereTipoVehiculo() {
        return requiereTipoVehiculo;
    }

    public void setRequiereTipoVehiculo(int requiereTipoVehiculo) {
        this.requiereTipoVehiculo = requiereTipoVehiculo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaCostoTipo != null ? idAuditoriaCostoTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaCostoTipo)) {
            return false;
        }
        AuditoriaCostoTipo other = (AuditoriaCostoTipo) object;
        if ((this.idAuditoriaCostoTipo == null && other.idAuditoriaCostoTipo != null) || (this.idAuditoriaCostoTipo != null && !this.idAuditoriaCostoTipo.equals(other.idAuditoriaCostoTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaCostoTipo[ idAuditoriaCostoTipo=" + idAuditoriaCostoTipo + " ]";
    }

    @XmlTransient
    public List<AuditoriaCosto> getAuditoriaCostoList() {
        return auditoriaCostoList;
    }

    public void setAuditoriaCostoList(List<AuditoriaCosto> auditoriaCostoList) {
        this.auditoriaCostoList = auditoriaCostoList;
    }
    
}
