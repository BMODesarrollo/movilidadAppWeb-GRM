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
@Table(name = "pqr_medio_reporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PqrMedioReporte.findAll", query = "SELECT p FROM PqrMedioReporte p"),
    @NamedQuery(name = "PqrMedioReporte.findByIdPqrMedioReporte", query = "SELECT p FROM PqrMedioReporte p WHERE p.idPqrMedioReporte = :idPqrMedioReporte"),
    @NamedQuery(name = "PqrMedioReporte.findByNombre", query = "SELECT p FROM PqrMedioReporte p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PqrMedioReporte.findByUsername", query = "SELECT p FROM PqrMedioReporte p WHERE p.username = :username"),
    @NamedQuery(name = "PqrMedioReporte.findByCreado", query = "SELECT p FROM PqrMedioReporte p WHERE p.creado = :creado"),
    @NamedQuery(name = "PqrMedioReporte.findByModificado", query = "SELECT p FROM PqrMedioReporte p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PqrMedioReporte.findByEstadoReg", query = "SELECT p FROM PqrMedioReporte p WHERE p.estadoReg = :estadoReg")})
public class PqrMedioReporte implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPqrMedioReporte", fetch = FetchType.LAZY)
    private List<PqrMaestro> pqrMaestroList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqr_medio_reporte")
    private Integer idPqrMedioReporte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
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
    @Column(name = "estado_reg")
    private Integer estadoReg;

    public PqrMedioReporte() {
    }

    public PqrMedioReporte(Integer idPqrMedioReporte) {
        this.idPqrMedioReporte = idPqrMedioReporte;
    }

    public PqrMedioReporte(Integer idPqrMedioReporte, String nombre, String descripcion) {
        this.idPqrMedioReporte = idPqrMedioReporte;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdPqrMedioReporte() {
        return idPqrMedioReporte;
    }

    public void setIdPqrMedioReporte(Integer idPqrMedioReporte) {
        this.idPqrMedioReporte = idPqrMedioReporte;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPqrMedioReporte != null ? idPqrMedioReporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PqrMedioReporte)) {
            return false;
        }
        PqrMedioReporte other = (PqrMedioReporte) object;
        if ((this.idPqrMedioReporte == null && other.idPqrMedioReporte != null) || (this.idPqrMedioReporte != null && !this.idPqrMedioReporte.equals(other.idPqrMedioReporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PqrMedioReporte[ idPqrMedioReporte=" + idPqrMedioReporte + " ]";
    }

    @XmlTransient
    public List<PqrMaestro> getPqrMaestroList() {
        return pqrMaestroList;
    }

    public void setPqrMaestroList(List<PqrMaestro> pqrMaestroList) {
        this.pqrMaestroList = pqrMaestroList;
    }
    
}