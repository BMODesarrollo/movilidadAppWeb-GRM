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
@Table(name = "pd_estado_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdEstadoProceso.findAll", query = "SELECT p FROM PdEstadoProceso p"),
    @NamedQuery(name = "PdEstadoProceso.findByIdPdEstadoProceso", query = "SELECT p FROM PdEstadoProceso p WHERE p.idPdEstadoProceso = :idPdEstadoProceso"),
    @NamedQuery(name = "PdEstadoProceso.findByNombre", query = "SELECT p FROM PdEstadoProceso p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PdEstadoProceso.findByCierraProceso", query = "SELECT p FROM PdEstadoProceso p WHERE p.cierraProceso = :cierraProceso"),
    @NamedQuery(name = "PdEstadoProceso.findByUsername", query = "SELECT p FROM PdEstadoProceso p WHERE p.username = :username"),
    @NamedQuery(name = "PdEstadoProceso.findByCreado", query = "SELECT p FROM PdEstadoProceso p WHERE p.creado = :creado"),
    @NamedQuery(name = "PdEstadoProceso.findByModificado", query = "SELECT p FROM PdEstadoProceso p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PdEstadoProceso.findByEstadoReg", query = "SELECT p FROM PdEstadoProceso p WHERE p.estadoReg = :estadoReg")})
public class PdEstadoProceso implements Serializable {

    @OneToMany(mappedBy = "idPdEstadoProceso", fetch = FetchType.LAZY)
    private List<GenericaPdMaestro> genericaPdMaestroList;

    @OneToMany(mappedBy = "idPdEstadoProceso", fetch = FetchType.LAZY)
    private List<PdMaestro> pdMaestroList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pd_estado_proceso")
    private Integer idPdEstadoProceso;
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
    @Column(name = "cierra_proceso")
    private int cierraProceso;
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

    public PdEstadoProceso() {
    }

    public PdEstadoProceso(Integer idPdEstadoProceso) {
        this.idPdEstadoProceso = idPdEstadoProceso;
    }

    public PdEstadoProceso(Integer idPdEstadoProceso, String nombre, String descripcion, int cierraProceso, String username, int estadoReg) {
        this.idPdEstadoProceso = idPdEstadoProceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cierraProceso = cierraProceso;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPdEstadoProceso() {
        return idPdEstadoProceso;
    }

    public void setIdPdEstadoProceso(Integer idPdEstadoProceso) {
        this.idPdEstadoProceso = idPdEstadoProceso;
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

    public int getCierraProceso() {
        return cierraProceso;
    }

    public void setCierraProceso(int cierraProceso) {
        this.cierraProceso = cierraProceso;
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
        hash += (idPdEstadoProceso != null ? idPdEstadoProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdEstadoProceso)) {
            return false;
        }
        PdEstadoProceso other = (PdEstadoProceso) object;
        if ((this.idPdEstadoProceso == null && other.idPdEstadoProceso != null) || (this.idPdEstadoProceso != null && !this.idPdEstadoProceso.equals(other.idPdEstadoProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PdEstadoProceso[ idPdEstadoProceso=" + idPdEstadoProceso + " ]";
    }

    @XmlTransient
    public List<PdMaestro> getPdMaestroList() {
        return pdMaestroList;
    }

    public void setPdMaestroList(List<PdMaestro> pdMaestroList) {
        this.pdMaestroList = pdMaestroList;
    }

    @XmlTransient
    public List<GenericaPdMaestro> getGenericaPdMaestroList() {
        return genericaPdMaestroList;
    }

    public void setGenericaPdMaestroList(List<GenericaPdMaestro> genericaPdMaestroList) {
        this.genericaPdMaestroList = genericaPdMaestroList;
    }
    
}