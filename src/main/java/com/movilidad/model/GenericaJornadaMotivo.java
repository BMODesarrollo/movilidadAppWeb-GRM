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
@Table(name = "generica_jornada_motivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaJornadaMotivo.findAll", query = "SELECT g FROM GenericaJornadaMotivo g"),
    @NamedQuery(name = "GenericaJornadaMotivo.findByIdGenericaJornadaMotivo", query = "SELECT g FROM GenericaJornadaMotivo g WHERE g.idGenericaJornadaMotivo = :idGenericaJornadaMotivo"),
    @NamedQuery(name = "GenericaJornadaMotivo.findByDescripcion", query = "SELECT g FROM GenericaJornadaMotivo g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GenericaJornadaMotivo.findByUsername", query = "SELECT g FROM GenericaJornadaMotivo g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaJornadaMotivo.findByCreado", query = "SELECT g FROM GenericaJornadaMotivo g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaJornadaMotivo.findByModificado", query = "SELECT g FROM GenericaJornadaMotivo g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaJornadaMotivo.findByEstadoReg", query = "SELECT g FROM GenericaJornadaMotivo g WHERE g.estadoReg = :estadoReg")})
public class GenericaJornadaMotivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_jornada_motivo")
    private Integer idGenericaJornadaMotivo;
    @Size(max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @OneToMany(mappedBy = "idGenericaJornadaMotivo", fetch = FetchType.LAZY)
    private List<GenericaJornada> genericaJornadaList;

    public GenericaJornadaMotivo() {
    }

    public GenericaJornadaMotivo(Integer idGenericaJornadaMotivo) {
        this.idGenericaJornadaMotivo = idGenericaJornadaMotivo;
    }

    public GenericaJornadaMotivo(Integer idGenericaJornadaMotivo, Date modificado) {
        this.idGenericaJornadaMotivo = idGenericaJornadaMotivo;
        this.modificado = modificado;
    }

    public Integer getIdGenericaJornadaMotivo() {
        return idGenericaJornadaMotivo;
    }

    public void setIdGenericaJornadaMotivo(Integer idGenericaJornadaMotivo) {
        this.idGenericaJornadaMotivo = idGenericaJornadaMotivo;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @XmlTransient
    public List<GenericaJornada> getGenericaJornadaList() {
        return genericaJornadaList;
    }

    public void setGenericaJornadaList(List<GenericaJornada> genericaJornadaList) {
        this.genericaJornadaList = genericaJornadaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaJornadaMotivo != null ? idGenericaJornadaMotivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaJornadaMotivo)) {
            return false;
        }
        GenericaJornadaMotivo other = (GenericaJornadaMotivo) object;
        if ((this.idGenericaJornadaMotivo == null && other.idGenericaJornadaMotivo != null) || (this.idGenericaJornadaMotivo != null && !this.idGenericaJornadaMotivo.equals(other.idGenericaJornadaMotivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaJornadaMotivo[ idGenericaJornadaMotivo=" + idGenericaJornadaMotivo + " ]";
    }
    
}
