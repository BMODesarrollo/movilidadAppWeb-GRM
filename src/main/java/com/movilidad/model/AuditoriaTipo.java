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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author solucionesit
 */
@Entity
@Table(name = "auditoria_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaTipo.findAll", query = "SELECT a FROM AuditoriaTipo a")
    , @NamedQuery(name = "AuditoriaTipo.findByIdAuditoriaTipo", query = "SELECT a FROM AuditoriaTipo a WHERE a.idAuditoriaTipo = :idAuditoriaTipo")
    , @NamedQuery(name = "AuditoriaTipo.findByNombre", query = "SELECT a FROM AuditoriaTipo a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "AuditoriaTipo.findByDescripcion", query = "SELECT a FROM AuditoriaTipo a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "AuditoriaTipo.findByCreado", query = "SELECT a FROM AuditoriaTipo a WHERE a.creado = :creado")
    , @NamedQuery(name = "AuditoriaTipo.findByModificado", query = "SELECT a FROM AuditoriaTipo a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AuditoriaTipo.findByEstadoReg", query = "SELECT a FROM AuditoriaTipo a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_tipo")
    private Integer idAuditoriaTipo;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoriaTipo", fetch = FetchType.LAZY)
    private List<AuditoriaEncabezado> auditoriaEncabezadoList;

    public AuditoriaTipo() {
    }

    public AuditoriaTipo(Integer idAuditoriaTipo) {
        this.idAuditoriaTipo = idAuditoriaTipo;
    }

    public AuditoriaTipo(Integer idAuditoriaTipo, Date creado, int estadoReg) {
        this.idAuditoriaTipo = idAuditoriaTipo;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaTipo() {
        return idAuditoriaTipo;
    }

    public void setIdAuditoriaTipo(Integer idAuditoriaTipo) {
        this.idAuditoriaTipo = idAuditoriaTipo;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @XmlTransient
    public List<AuditoriaEncabezado> getAuditoriaEncabezadoList() {
        return auditoriaEncabezadoList;
    }

    public void setAuditoriaEncabezadoList(List<AuditoriaEncabezado> auditoriaEncabezadoList) {
        this.auditoriaEncabezadoList = auditoriaEncabezadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaTipo != null ? idAuditoriaTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaTipo)) {
            return false;
        }
        AuditoriaTipo other = (AuditoriaTipo) object;
        if ((this.idAuditoriaTipo == null && other.idAuditoriaTipo != null) || (this.idAuditoriaTipo != null && !this.idAuditoriaTipo.equals(other.idAuditoriaTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaTipo[ idAuditoriaTipo=" + idAuditoriaTipo + " ]";
    }
    
}
