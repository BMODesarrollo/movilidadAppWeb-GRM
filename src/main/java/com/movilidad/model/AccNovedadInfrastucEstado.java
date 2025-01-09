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
@Table(name = "acc_novedad_infrastuc_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccNovedadInfrastucEstado.findAll", query = "SELECT a FROM AccNovedadInfrastucEstado a"),
    @NamedQuery(name = "AccNovedadInfrastucEstado.findByIdAccNovedadInfrastucEstado", query = "SELECT a FROM AccNovedadInfrastucEstado a WHERE a.idAccNovedadInfrastucEstado = :idAccNovedadInfrastucEstado"),
    @NamedQuery(name = "AccNovedadInfrastucEstado.findByNombre", query = "SELECT a FROM AccNovedadInfrastucEstado a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AccNovedadInfrastucEstado.findByDescripcion", query = "SELECT a FROM AccNovedadInfrastucEstado a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "AccNovedadInfrastucEstado.findByUsername", query = "SELECT a FROM AccNovedadInfrastucEstado a WHERE a.username = :username"),
    @NamedQuery(name = "AccNovedadInfrastucEstado.findByCreado", query = "SELECT a FROM AccNovedadInfrastucEstado a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccNovedadInfrastucEstado.findByModificado", query = "SELECT a FROM AccNovedadInfrastucEstado a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccNovedadInfrastucEstado.findByEstadoReg", query = "SELECT a FROM AccNovedadInfrastucEstado a WHERE a.estadoReg = :estadoReg")})
public class AccNovedadInfrastucEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_novedad_infrastuc_estado")
    private Integer idAccNovedadInfrastucEstado;
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
    @OneToMany(mappedBy = "idAccNovedadInfrastucEstado", fetch = FetchType.LAZY)
    private List<AccNovedadInfraestruc> accNovedadInfraestrucList;

    public AccNovedadInfrastucEstado() {
    }

    public AccNovedadInfrastucEstado(Integer idAccNovedadInfrastucEstado) {
        this.idAccNovedadInfrastucEstado = idAccNovedadInfrastucEstado;
    }

    public AccNovedadInfrastucEstado(Integer idAccNovedadInfrastucEstado, String nombre, String descripcion) {
        this.idAccNovedadInfrastucEstado = idAccNovedadInfrastucEstado;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdAccNovedadInfrastucEstado() {
        return idAccNovedadInfrastucEstado;
    }

    public void setIdAccNovedadInfrastucEstado(Integer idAccNovedadInfrastucEstado) {
        this.idAccNovedadInfrastucEstado = idAccNovedadInfrastucEstado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccNovedadInfrastucEstado != null ? idAccNovedadInfrastucEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccNovedadInfrastucEstado)) {
            return false;
        }
        AccNovedadInfrastucEstado other = (AccNovedadInfrastucEstado) object;
        if ((this.idAccNovedadInfrastucEstado == null && other.idAccNovedadInfrastucEstado != null) || (this.idAccNovedadInfrastucEstado != null && !this.idAccNovedadInfrastucEstado.equals(other.idAccNovedadInfrastucEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccNovedadInfrastucEstado[ idAccNovedadInfrastucEstado=" + idAccNovedadInfrastucEstado + " ]";
    }
    
}
