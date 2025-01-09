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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "gop_alerta_presentacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GopAlertaPresentacion.findAll", query = "SELECT g FROM GopAlertaPresentacion g")
    , @NamedQuery(name = "GopAlertaPresentacion.findByIdGopAlertaPresentacion", query = "SELECT g FROM GopAlertaPresentacion g WHERE g.idGopAlertaPresentacion = :idGopAlertaPresentacion")
    , @NamedQuery(name = "GopAlertaPresentacion.findByMinutos", query = "SELECT g FROM GopAlertaPresentacion g WHERE g.minutos = :minutos")
    , @NamedQuery(name = "GopAlertaPresentacion.findByUsername", query = "SELECT g FROM GopAlertaPresentacion g WHERE g.username = :username")
    , @NamedQuery(name = "GopAlertaPresentacion.findByCreado", query = "SELECT g FROM GopAlertaPresentacion g WHERE g.creado = :creado")
    , @NamedQuery(name = "GopAlertaPresentacion.findByModificado", query = "SELECT g FROM GopAlertaPresentacion g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GopAlertaPresentacion.findByEstadoReg", query = "SELECT g FROM GopAlertaPresentacion g WHERE g.estadoReg = :estadoReg")})
public class GopAlertaPresentacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gop_alerta_presentacion")
    private Integer idGopAlertaPresentacion;
    @Column(name = "minutos")
    private Integer minutos;
    @Lob
    @Size(max = 65535)
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

    public GopAlertaPresentacion() {
    }

    public GopAlertaPresentacion(Integer idGopAlertaPresentacion) {
        this.idGopAlertaPresentacion = idGopAlertaPresentacion;
    }

    public Integer getIdGopAlertaPresentacion() {
        return idGopAlertaPresentacion;
    }

    public void setIdGopAlertaPresentacion(Integer idGopAlertaPresentacion) {
        this.idGopAlertaPresentacion = idGopAlertaPresentacion;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
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
        hash += (idGopAlertaPresentacion != null ? idGopAlertaPresentacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GopAlertaPresentacion)) {
            return false;
        }
        GopAlertaPresentacion other = (GopAlertaPresentacion) object;
        if ((this.idGopAlertaPresentacion == null && other.idGopAlertaPresentacion != null) || (this.idGopAlertaPresentacion != null && !this.idGopAlertaPresentacion.equals(other.idGopAlertaPresentacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GopAlertaPresentacion[ idGopAlertaPresentacion=" + idGopAlertaPresentacion + " ]";
    }
    
}
