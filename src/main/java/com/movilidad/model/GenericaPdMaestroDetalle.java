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
@Table(name = "generica_pd_maestro_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPdMaestroDetalle.findAll", query = "SELECT g FROM GenericaPdMaestroDetalle g"),
    @NamedQuery(name = "GenericaPdMaestroDetalle.findByIdGenericaPdMaestroDetalle", query = "SELECT g FROM GenericaPdMaestroDetalle g WHERE g.idGenericaPdMaestroDetalle = :idGenericaPdMaestroDetalle"),
    @NamedQuery(name = "GenericaPdMaestroDetalle.findByFechaProceso", query = "SELECT g FROM GenericaPdMaestroDetalle g WHERE g.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "GenericaPdMaestroDetalle.findByUsername", query = "SELECT g FROM GenericaPdMaestroDetalle g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaPdMaestroDetalle.findByCreado", query = "SELECT g FROM GenericaPdMaestroDetalle g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPdMaestroDetalle.findByModificado", query = "SELECT g FROM GenericaPdMaestroDetalle g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPdMaestroDetalle.findByEstadoReg", query = "SELECT g FROM GenericaPdMaestroDetalle g WHERE g.estadoReg = :estadoReg")})
public class GenericaPdMaestroDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pd_maestro_detalle")
    private Integer idGenericaPdMaestroDetalle;
    @Column(name = "fecha_proceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Generica idGenerica;
    @JoinColumn(name = "id_generica_pd_maestro", referencedColumnName = "id_generica_pd_maestro")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenericaPdMaestro idGenericaPdMaestro;

    public GenericaPdMaestroDetalle() {
    }

    public GenericaPdMaestroDetalle(Integer idGenericaPdMaestroDetalle) {
        this.idGenericaPdMaestroDetalle = idGenericaPdMaestroDetalle;
    }

    public GenericaPdMaestroDetalle(Integer idGenericaPdMaestroDetalle, String username, Date creado, int estadoReg) {
        this.idGenericaPdMaestroDetalle = idGenericaPdMaestroDetalle;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaPdMaestroDetalle() {
        return idGenericaPdMaestroDetalle;
    }

    public void setIdGenericaPdMaestroDetalle(Integer idGenericaPdMaestroDetalle) {
        this.idGenericaPdMaestroDetalle = idGenericaPdMaestroDetalle;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
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

    public GenericaPdMaestro getIdGenericaPdMaestro() {
        return idGenericaPdMaestro;
    }

    public void setIdGenericaPdMaestro(GenericaPdMaestro idGenericaPdMaestro) {
        this.idGenericaPdMaestro = idGenericaPdMaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaPdMaestroDetalle != null ? idGenericaPdMaestroDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPdMaestroDetalle)) {
            return false;
        }
        GenericaPdMaestroDetalle other = (GenericaPdMaestroDetalle) object;
        if ((this.idGenericaPdMaestroDetalle == null && other.idGenericaPdMaestroDetalle != null) || (this.idGenericaPdMaestroDetalle != null && !this.idGenericaPdMaestroDetalle.equals(other.idGenericaPdMaestroDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPdMaestroDetalle[ idGenericaPdMaestroDetalle=" + idGenericaPdMaestroDetalle + " ]";
    }
    
}
