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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "acc_novedad_tipo_infrastruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccNovedadTipoInfrastruc.findAll", query = "SELECT a FROM AccNovedadTipoInfrastruc a"),
    @NamedQuery(name = "AccNovedadTipoInfrastruc.findByIdAccNovedadTipoInfrastruc", query = "SELECT a FROM AccNovedadTipoInfrastruc a WHERE a.idAccNovedadTipoInfrastruc = :idAccNovedadTipoInfrastruc"),
    @NamedQuery(name = "AccNovedadTipoInfrastruc.findByNombre", query = "SELECT a FROM AccNovedadTipoInfrastruc a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AccNovedadTipoInfrastruc.findByDescripcion", query = "SELECT a FROM AccNovedadTipoInfrastruc a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "AccNovedadTipoInfrastruc.findByUsername", query = "SELECT a FROM AccNovedadTipoInfrastruc a WHERE a.username = :username"),
    @NamedQuery(name = "AccNovedadTipoInfrastruc.findByCreado", query = "SELECT a FROM AccNovedadTipoInfrastruc a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccNovedadTipoInfrastruc.findByModificado", query = "SELECT a FROM AccNovedadTipoInfrastruc a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccNovedadTipoInfrastruc.findByEstadoReg", query = "SELECT a FROM AccNovedadTipoInfrastruc a WHERE a.estadoReg = :estadoReg")})
public class AccNovedadTipoInfrastruc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_novedad_tipo_infrastruc")
    private Integer idAccNovedadTipoInfrastruc;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccNovedadTipoInfrastruc", fetch = FetchType.LAZY)
    private List<AccNovedadInfraestruc> accNovedadInfraestrucList;
    @OneToMany(mappedBy = "idAccNovedadTipoInfrastruc", fetch = FetchType.LAZY)
    private List<AccNovedadTipoDetallesInfrastruc> accNovedadTipoDetallesInfrastrucList;

    public AccNovedadTipoInfrastruc() {
    }

    public AccNovedadTipoInfrastruc(Integer idAccNovedadTipoInfrastruc) {
        this.idAccNovedadTipoInfrastruc = idAccNovedadTipoInfrastruc;
    }

    public AccNovedadTipoInfrastruc(Integer idAccNovedadTipoInfrastruc, String nombre, String descripcion) {
        this.idAccNovedadTipoInfrastruc = idAccNovedadTipoInfrastruc;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdAccNovedadTipoInfrastruc() {
        return idAccNovedadTipoInfrastruc;
    }

    public void setIdAccNovedadTipoInfrastruc(Integer idAccNovedadTipoInfrastruc) {
        this.idAccNovedadTipoInfrastruc = idAccNovedadTipoInfrastruc;
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
    public List<AccNovedadInfraestruc> getAccNovedadInfraestrucList() {
        return accNovedadInfraestrucList;
    }

    public void setAccNovedadInfraestrucList(List<AccNovedadInfraestruc> accNovedadInfraestrucList) {
        this.accNovedadInfraestrucList = accNovedadInfraestrucList;
    }

    @XmlTransient
    public List<AccNovedadTipoDetallesInfrastruc> getAccNovedadTipoDetallesInfrastrucList() {
        return accNovedadTipoDetallesInfrastrucList;
    }

    public void setAccNovedadTipoDetallesInfrastrucList(List<AccNovedadTipoDetallesInfrastruc> accNovedadTipoDetallesInfrastrucList) {
        this.accNovedadTipoDetallesInfrastrucList = accNovedadTipoDetallesInfrastrucList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccNovedadTipoInfrastruc != null ? idAccNovedadTipoInfrastruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccNovedadTipoInfrastruc)) {
            return false;
        }
        AccNovedadTipoInfrastruc other = (AccNovedadTipoInfrastruc) object;
        if ((this.idAccNovedadTipoInfrastruc == null && other.idAccNovedadTipoInfrastruc != null) || (this.idAccNovedadTipoInfrastruc != null && !this.idAccNovedadTipoInfrastruc.equals(other.idAccNovedadTipoInfrastruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccNovedadTipoInfrastruc[ idAccNovedadTipoInfrastruc=" + idAccNovedadTipoInfrastruc + " ]";
    }
    
}
