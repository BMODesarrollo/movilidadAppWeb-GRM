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
@Table(name = "generica_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaSeguimiento.findAll", query = "SELECT g FROM GenericaSeguimiento g"),
    @NamedQuery(name = "GenericaSeguimiento.findByIdGenericaSeguimiento", query = "SELECT g FROM GenericaSeguimiento g WHERE g.idGenericaSeguimiento = :idGenericaSeguimiento"),
    @NamedQuery(name = "GenericaSeguimiento.findByFecha", query = "SELECT g FROM GenericaSeguimiento g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "GenericaSeguimiento.findByUsername", query = "SELECT g FROM GenericaSeguimiento g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaSeguimiento.findByCreado", query = "SELECT g FROM GenericaSeguimiento g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaSeguimiento.findByModificado", query = "SELECT g FROM GenericaSeguimiento g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaSeguimiento.findByEstadoReg", query = "SELECT g FROM GenericaSeguimiento g WHERE g.estadoReg = :estadoReg")})
public class GenericaSeguimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_seguimiento")
    private Integer idGenericaSeguimiento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "seguimiento")
    private String seguimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
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
    @JoinColumn(name = "id_generica", referencedColumnName = "id_generica")
    @ManyToOne(optional = false)
    private Generica idGenerica;

    public GenericaSeguimiento() {
    }

    public GenericaSeguimiento(Integer idGenericaSeguimiento) {
        this.idGenericaSeguimiento = idGenericaSeguimiento;
    }

    public GenericaSeguimiento(Integer idGenericaSeguimiento, String seguimiento, String username, Date creado, int estadoReg) {
        this.idGenericaSeguimiento = idGenericaSeguimiento;
        this.seguimiento = seguimiento;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaSeguimiento() {
        return idGenericaSeguimiento;
    }

    public void setIdGenericaSeguimiento(Integer idGenericaSeguimiento) {
        this.idGenericaSeguimiento = idGenericaSeguimiento;
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

    public Generica getIdGenerica() {
        return idGenerica;
    }

    public void setIdGenerica(Generica idGenerica) {
        this.idGenerica = idGenerica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaSeguimiento != null ? idGenericaSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaSeguimiento)) {
            return false;
        }
        GenericaSeguimiento other = (GenericaSeguimiento) object;
        if ((this.idGenericaSeguimiento == null && other.idGenericaSeguimiento != null) || (this.idGenericaSeguimiento != null && !this.idGenericaSeguimiento.equals(other.idGenericaSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaSeguimiento[ idGenericaSeguimiento=" + idGenericaSeguimiento + " ]";
    }
    
}
