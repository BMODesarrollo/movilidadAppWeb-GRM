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
@Table(name = "gop_alerta_tiempo_detenido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GopAlertaTiempoDetenido.findAll", query = "SELECT g FROM GopAlertaTiempoDetenido g")
    , @NamedQuery(name = "GopAlertaTiempoDetenido.findByIdGopAlertaTiempoDetenido", query = "SELECT g FROM GopAlertaTiempoDetenido g WHERE g.idGopAlertaTiempoDetenido = :idGopAlertaTiempoDetenido")
    , @NamedQuery(name = "GopAlertaTiempoDetenido.findByMinutos", query = "SELECT g FROM GopAlertaTiempoDetenido g WHERE g.minutos = :minutos")
    , @NamedQuery(name = "GopAlertaTiempoDetenido.findByUsername", query = "SELECT g FROM GopAlertaTiempoDetenido g WHERE g.username = :username")
    , @NamedQuery(name = "GopAlertaTiempoDetenido.findByCreado", query = "SELECT g FROM GopAlertaTiempoDetenido g WHERE g.creado = :creado")
    , @NamedQuery(name = "GopAlertaTiempoDetenido.findByModificado", query = "SELECT g FROM GopAlertaTiempoDetenido g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GopAlertaTiempoDetenido.findByEstadoReg", query = "SELECT g FROM GopAlertaTiempoDetenido g WHERE g.estadoReg = :estadoReg")})
public class GopAlertaTiempoDetenido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gop_alerta_tiempo_detenido")
    private Integer idGopAlertaTiempoDetenido;
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

    public GopAlertaTiempoDetenido() {
    }

    public GopAlertaTiempoDetenido(Integer idGopAlertaTiempoDetenido) {
        this.idGopAlertaTiempoDetenido = idGopAlertaTiempoDetenido;
    }

    public Integer getIdGopAlertaTiempoDetenido() {
        return idGopAlertaTiempoDetenido;
    }

    public void setIdGopAlertaTiempoDetenido(Integer idGopAlertaTiempoDetenido) {
        this.idGopAlertaTiempoDetenido = idGopAlertaTiempoDetenido;
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
        hash += (idGopAlertaTiempoDetenido != null ? idGopAlertaTiempoDetenido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GopAlertaTiempoDetenido)) {
            return false;
        }
        GopAlertaTiempoDetenido other = (GopAlertaTiempoDetenido) object;
        if ((this.idGopAlertaTiempoDetenido == null && other.idGopAlertaTiempoDetenido != null) || (this.idGopAlertaTiempoDetenido != null && !this.idGopAlertaTiempoDetenido.equals(other.idGopAlertaTiempoDetenido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GopAlertaTiempoDetenido[ idGopAlertaTiempoDetenido=" + idGopAlertaTiempoDetenido + " ]";
    }
    
}
