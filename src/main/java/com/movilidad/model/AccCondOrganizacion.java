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
import javax.persistence.Lob;
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
 * @author HP
 */
@Entity
@Table(name = "acc_cond_organizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccCondOrganizacion.findAll", query = "SELECT a FROM AccCondOrganizacion a")
    , @NamedQuery(name = "AccCondOrganizacion.findByIdAccCondOrganizacion", query = "SELECT a FROM AccCondOrganizacion a WHERE a.idAccCondOrganizacion = :idAccCondOrganizacion")
    , @NamedQuery(name = "AccCondOrganizacion.findByCondOrganizacion", query = "SELECT a FROM AccCondOrganizacion a WHERE a.condOrganizacion = :condOrganizacion")
    , @NamedQuery(name = "AccCondOrganizacion.findByUsername", query = "SELECT a FROM AccCondOrganizacion a WHERE a.username = :username")
    , @NamedQuery(name = "AccCondOrganizacion.findByCreado", query = "SELECT a FROM AccCondOrganizacion a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccCondOrganizacion.findByModificado", query = "SELECT a FROM AccCondOrganizacion a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccCondOrganizacion.findByEstadoReg", query = "SELECT a FROM AccCondOrganizacion a WHERE a.estadoReg = :estadoReg")})
public class AccCondOrganizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_cond_organizacion")
    private Integer idAccCondOrganizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "cond_organizacion")
    private String condOrganizacion;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.DATE)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;

    public AccCondOrganizacion() {
    }

    public AccCondOrganizacion(Integer idAccCondOrganizacion) {
        this.idAccCondOrganizacion = idAccCondOrganizacion;
    }

    public AccCondOrganizacion(Integer idAccCondOrganizacion, String condOrganizacion) {
        this.idAccCondOrganizacion = idAccCondOrganizacion;
        this.condOrganizacion = condOrganizacion;
    }

    public Integer getIdAccCondOrganizacion() {
        return idAccCondOrganizacion;
    }

    public void setIdAccCondOrganizacion(Integer idAccCondOrganizacion) {
        this.idAccCondOrganizacion = idAccCondOrganizacion;
    }

    public String getCondOrganizacion() {
        return condOrganizacion;
    }

    public void setCondOrganizacion(String condOrganizacion) {
        this.condOrganizacion = condOrganizacion;
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
        hash += (idAccCondOrganizacion != null ? idAccCondOrganizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccCondOrganizacion)) {
            return false;
        }
        AccCondOrganizacion other = (AccCondOrganizacion) object;
        if ((this.idAccCondOrganizacion == null && other.idAccCondOrganizacion != null) || (this.idAccCondOrganizacion != null && !this.idAccCondOrganizacion.equals(other.idAccCondOrganizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccCondOrganizacion[ idAccCondOrganizacion=" + idAccCondOrganizacion + " ]";
    }
    
}
