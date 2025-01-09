/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "generica_pd_maestro_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPdMaestroSeguimiento.findAll", query = "SELECT g FROM GenericaPdMaestroSeguimiento g"),
    @NamedQuery(name = "GenericaPdMaestroSeguimiento.findByIdGenericaPdMaestroSeguimiento", query = "SELECT g FROM GenericaPdMaestroSeguimiento g WHERE g.idGenericaPdMaestroSeguimiento = :idGenericaPdMaestroSeguimiento"),
    @NamedQuery(name = "GenericaPdMaestroSeguimiento.findByFecha", query = "SELECT g FROM GenericaPdMaestroSeguimiento g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "GenericaPdMaestroSeguimiento.findByPath", query = "SELECT g FROM GenericaPdMaestroSeguimiento g WHERE g.path = :path"),
    @NamedQuery(name = "GenericaPdMaestroSeguimiento.findByUsername", query = "SELECT g FROM GenericaPdMaestroSeguimiento g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaPdMaestroSeguimiento.findByCreado", query = "SELECT g FROM GenericaPdMaestroSeguimiento g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPdMaestroSeguimiento.findByModificado", query = "SELECT g FROM GenericaPdMaestroSeguimiento g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPdMaestroSeguimiento.findByEstadoReg", query = "SELECT g FROM GenericaPdMaestroSeguimiento g WHERE g.estadoReg = :estadoReg")})
public class GenericaPdMaestroSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pd_maestro_seguimiento")
    private Integer idGenericaPdMaestroSeguimiento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "seguimiento")
    private String seguimiento;
    @Size(max = 255)
    @Column(name = "path")
    private String path;
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
    @JoinColumn(name = "id_generica_pd_maestro", referencedColumnName = "id_generica_pd_maestro")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenericaPdMaestro idGenericaPdMaestro;

    public GenericaPdMaestroSeguimiento() {
    }

    public GenericaPdMaestroSeguimiento(Integer idGenericaPdMaestroSeguimiento) {
        this.idGenericaPdMaestroSeguimiento = idGenericaPdMaestroSeguimiento;
    }

    public GenericaPdMaestroSeguimiento(Integer idGenericaPdMaestroSeguimiento, String seguimiento) {
        this.idGenericaPdMaestroSeguimiento = idGenericaPdMaestroSeguimiento;
        this.seguimiento = seguimiento;
    }

    public Integer getIdGenericaPdMaestroSeguimiento() {
        return idGenericaPdMaestroSeguimiento;
    }

    public void setIdGenericaPdMaestroSeguimiento(Integer idGenericaPdMaestroSeguimiento) {
        this.idGenericaPdMaestroSeguimiento = idGenericaPdMaestroSeguimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public GenericaPdMaestro getIdGenericaPdMaestro() {
        return idGenericaPdMaestro;
    }

    public void setIdGenericaPdMaestro(GenericaPdMaestro idGenericaPdMaestro) {
        this.idGenericaPdMaestro = idGenericaPdMaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaPdMaestroSeguimiento != null ? idGenericaPdMaestroSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPdMaestroSeguimiento)) {
            return false;
        }
        GenericaPdMaestroSeguimiento other = (GenericaPdMaestroSeguimiento) object;
        if ((this.idGenericaPdMaestroSeguimiento == null && other.idGenericaPdMaestroSeguimiento != null) || (this.idGenericaPdMaestroSeguimiento != null && !this.idGenericaPdMaestroSeguimiento.equals(other.idGenericaPdMaestroSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPdMaestroSeguimiento[ idGenericaPdMaestroSeguimiento=" + idGenericaPdMaestroSeguimiento + " ]";
    }
    
}
