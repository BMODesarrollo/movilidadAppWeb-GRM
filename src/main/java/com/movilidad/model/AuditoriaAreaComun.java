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
@Table(name = "auditoria_area_comun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaAreaComun.findAll", query = "SELECT a FROM AuditoriaAreaComun a"),
    @NamedQuery(name = "AuditoriaAreaComun.findByIdAuditoriaAreaComun", query = "SELECT a FROM AuditoriaAreaComun a WHERE a.idAuditoriaAreaComun = :idAuditoriaAreaComun"),
    @NamedQuery(name = "AuditoriaAreaComun.findByNombre", query = "SELECT a FROM AuditoriaAreaComun a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AuditoriaAreaComun.findByDescripcion", query = "SELECT a FROM AuditoriaAreaComun a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "AuditoriaAreaComun.findByCreado", query = "SELECT a FROM AuditoriaAreaComun a WHERE a.creado = :creado"),
    @NamedQuery(name = "AuditoriaAreaComun.findByModificado", query = "SELECT a FROM AuditoriaAreaComun a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AuditoriaAreaComun.findByEstadoReg", query = "SELECT a FROM AuditoriaAreaComun a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaAreaComun implements Serializable {

    @OneToMany(mappedBy = "idAuditoriaAreaComun", fetch = FetchType.LAZY)
    private List<AuditoriaRealizadoPor> auditoriaRealizadoPorList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_area_comun")
    private Integer idAuditoriaAreaComun;
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

    public AuditoriaAreaComun() {
    }

    public AuditoriaAreaComun(Integer idAuditoriaAreaComun) {
        this.idAuditoriaAreaComun = idAuditoriaAreaComun;
    }

    public AuditoriaAreaComun(Integer idAuditoriaAreaComun, Date creado, int estadoReg) {
        this.idAuditoriaAreaComun = idAuditoriaAreaComun;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaAreaComun() {
        return idAuditoriaAreaComun;
    }

    public void setIdAuditoriaAreaComun(Integer idAuditoriaAreaComun) {
        this.idAuditoriaAreaComun = idAuditoriaAreaComun;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaAreaComun != null ? idAuditoriaAreaComun.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaAreaComun)) {
            return false;
        }
        AuditoriaAreaComun other = (AuditoriaAreaComun) object;
        if ((this.idAuditoriaAreaComun == null && other.idAuditoriaAreaComun != null) || (this.idAuditoriaAreaComun != null && !this.idAuditoriaAreaComun.equals(other.idAuditoriaAreaComun))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaAreaComun[ idAuditoriaAreaComun=" + idAuditoriaAreaComun + " ]";
    }

    @XmlTransient
    public List<AuditoriaRealizadoPor> getAuditoriaRealizadoPorList() {
        return auditoriaRealizadoPorList;
    }

    public void setAuditoriaRealizadoPorList(List<AuditoriaRealizadoPor> auditoriaRealizadoPorList) {
        this.auditoriaRealizadoPorList = auditoriaRealizadoPorList;
    }
    
}