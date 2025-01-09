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
@Table(name = "pd_tipo_sancion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdTipoSancion.findAll", query = "SELECT p FROM PdTipoSancion p"),
    @NamedQuery(name = "PdTipoSancion.findByIdPdTipoSancion", query = "SELECT p FROM PdTipoSancion p WHERE p.idPdTipoSancion = :idPdTipoSancion"),
    @NamedQuery(name = "PdTipoSancion.findByNombre", query = "SELECT p FROM PdTipoSancion p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PdTipoSancion.findByUsername", query = "SELECT p FROM PdTipoSancion p WHERE p.username = :username"),
    @NamedQuery(name = "PdTipoSancion.findByCreado", query = "SELECT p FROM PdTipoSancion p WHERE p.creado = :creado"),
    @NamedQuery(name = "PdTipoSancion.findByModificado", query = "SELECT p FROM PdTipoSancion p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PdTipoSancion.findByEstadoReg", query = "SELECT p FROM PdTipoSancion p WHERE p.estadoReg = :estadoReg")})
public class PdTipoSancion implements Serializable {

    @OneToMany(mappedBy = "idPdTipoSancion", fetch = FetchType.LAZY)
    private List<GenericaPdMaestro> genericaPdMaestroList;

    @OneToMany(mappedBy = "idPdTipoSancion", fetch = FetchType.LAZY)
    private List<PdMaestro> pdMaestroList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pd_tipo_sancion")
    private Integer idPdTipoSancion;
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

    public PdTipoSancion() {
    }

    public PdTipoSancion(Integer idPdTipoSancion) {
        this.idPdTipoSancion = idPdTipoSancion;
    }

    public PdTipoSancion(Integer idPdTipoSancion, String nombre, String descripcion, String username, int estadoReg) {
        this.idPdTipoSancion = idPdTipoSancion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPdTipoSancion() {
        return idPdTipoSancion;
    }

    public void setIdPdTipoSancion(Integer idPdTipoSancion) {
        this.idPdTipoSancion = idPdTipoSancion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPdTipoSancion != null ? idPdTipoSancion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdTipoSancion)) {
            return false;
        }
        PdTipoSancion other = (PdTipoSancion) object;
        if ((this.idPdTipoSancion == null && other.idPdTipoSancion != null) || (this.idPdTipoSancion != null && !this.idPdTipoSancion.equals(other.idPdTipoSancion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PdTipoSancion[ idPdTipoSancion=" + idPdTipoSancion + " ]";
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
