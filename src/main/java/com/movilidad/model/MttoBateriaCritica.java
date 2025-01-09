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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "mtto_bateria_critica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MttoBateriaCritica.findAll", query = "SELECT m FROM MttoBateriaCritica m")
    , @NamedQuery(name = "MttoBateriaCritica.findByIdMttoBateriaCritica", query = "SELECT m FROM MttoBateriaCritica m WHERE m.idMttoBateriaCritica = :idMttoBateriaCritica")
    , @NamedQuery(name = "MttoBateriaCritica.findByCarga", query = "SELECT m FROM MttoBateriaCritica m WHERE m.carga = :carga")
    , @NamedQuery(name = "MttoBateriaCritica.findByUsername", query = "SELECT m FROM MttoBateriaCritica m WHERE m.username = :username")
    , @NamedQuery(name = "MttoBateriaCritica.findByCreado", query = "SELECT m FROM MttoBateriaCritica m WHERE m.creado = :creado")
    , @NamedQuery(name = "MttoBateriaCritica.findByModificado", query = "SELECT m FROM MttoBateriaCritica m WHERE m.modificado = :modificado")
    , @NamedQuery(name = "MttoBateriaCritica.findByEstadoReg", query = "SELECT m FROM MttoBateriaCritica m WHERE m.estadoReg = :estadoReg")})
public class MttoBateriaCritica implements Serializable {

    @JoinColumn(name = "id_notificacion_procesos", referencedColumnName = "id_notificacion_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotificacionProcesos idNotificacionProcesos;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mtto_bateria_critica")
    private Integer idMttoBateriaCritica;
    @Column(name = "carga")
    private Integer carga;
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
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;

    public MttoBateriaCritica() {
    }

    public MttoBateriaCritica(Integer idMttoBateriaCritica) {
        this.idMttoBateriaCritica = idMttoBateriaCritica;
    }

    public Integer getIdMttoBateriaCritica() {
        return idMttoBateriaCritica;
    }

    public void setIdMttoBateriaCritica(Integer idMttoBateriaCritica) {
        this.idMttoBateriaCritica = idMttoBateriaCritica;
    }

    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
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

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMttoBateriaCritica != null ? idMttoBateriaCritica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MttoBateriaCritica)) {
            return false;
        }
        MttoBateriaCritica other = (MttoBateriaCritica) object;
        if ((this.idMttoBateriaCritica == null && other.idMttoBateriaCritica != null) || (this.idMttoBateriaCritica != null && !this.idMttoBateriaCritica.equals(other.idMttoBateriaCritica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MttoBateriaCritica[ idMttoBateriaCritica=" + idMttoBateriaCritica + " ]";
    }

    public NotificacionProcesos getIdNotificacionProcesos() {
        return idNotificacionProcesos;
    }

    public void setIdNotificacionProcesos(NotificacionProcesos idNotificacionProcesos) {
        this.idNotificacionProcesos = idNotificacionProcesos;
    }

}
