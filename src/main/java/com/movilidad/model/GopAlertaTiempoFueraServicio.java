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
 * @author solucionesit
 */
@Entity
@Table(name = "gop_alerta_tiempo_fuera_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GopAlertaTiempoFueraServicio.findAll", query = "SELECT g FROM GopAlertaTiempoFueraServicio g")
    , @NamedQuery(name = "GopAlertaTiempoFueraServicio.findByIdGopAlertaTiempoFueraServicio", query = "SELECT g FROM GopAlertaTiempoFueraServicio g WHERE g.idGopAlertaTiempoFueraServicio = :idGopAlertaTiempoFueraServicio")
    , @NamedQuery(name = "GopAlertaTiempoFueraServicio.findByDias", query = "SELECT g FROM GopAlertaTiempoFueraServicio g WHERE g.dias = :dias")
    , @NamedQuery(name = "GopAlertaTiempoFueraServicio.findByUsername", query = "SELECT g FROM GopAlertaTiempoFueraServicio g WHERE g.username = :username")
    , @NamedQuery(name = "GopAlertaTiempoFueraServicio.findByCreado", query = "SELECT g FROM GopAlertaTiempoFueraServicio g WHERE g.creado = :creado")
    , @NamedQuery(name = "GopAlertaTiempoFueraServicio.findByModificado", query = "SELECT g FROM GopAlertaTiempoFueraServicio g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GopAlertaTiempoFueraServicio.findByEstadoReg", query = "SELECT g FROM GopAlertaTiempoFueraServicio g WHERE g.estadoReg = :estadoReg")})
public class GopAlertaTiempoFueraServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gop_alerta_tiempo_fuera_servicio")
    private Integer idGopAlertaTiempoFueraServicio;
    @Column(name = "dias")
    private Integer dias;
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

    public GopAlertaTiempoFueraServicio() {
    }

    public GopAlertaTiempoFueraServicio(Integer idGopAlertaTiempoFueraServicio) {
        this.idGopAlertaTiempoFueraServicio = idGopAlertaTiempoFueraServicio;
    }

    public Integer getIdGopAlertaTiempoFueraServicio() {
        return idGopAlertaTiempoFueraServicio;
    }

    public void setIdGopAlertaTiempoFueraServicio(Integer idGopAlertaTiempoFueraServicio) {
        this.idGopAlertaTiempoFueraServicio = idGopAlertaTiempoFueraServicio;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
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
        hash += (idGopAlertaTiempoFueraServicio != null ? idGopAlertaTiempoFueraServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GopAlertaTiempoFueraServicio)) {
            return false;
        }
        GopAlertaTiempoFueraServicio other = (GopAlertaTiempoFueraServicio) object;
        if ((this.idGopAlertaTiempoFueraServicio == null && other.idGopAlertaTiempoFueraServicio != null) || (this.idGopAlertaTiempoFueraServicio != null && !this.idGopAlertaTiempoFueraServicio.equals(other.idGopAlertaTiempoFueraServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GopAlertaTiempoFueraServicio[ idGopAlertaTiempoFueraServicio=" + idGopAlertaTiempoFueraServicio + " ]";
    }
    
}
