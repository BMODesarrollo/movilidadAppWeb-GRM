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
@Table(name = "registro_estado_armamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroEstadoArmamento.findAll", query = "SELECT r FROM RegistroEstadoArmamento r"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByIdRegistroEstadoArmamento", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.idRegistroEstadoArmamento = :idRegistroEstadoArmamento"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByFechaHora", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.fechaHora = :fechaHora"),
    @NamedQuery(name = "RegistroEstadoArmamento.findBySerial", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.serial = :serial"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByMunicion", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.municion = :municion"),
    @NamedQuery(name = "RegistroEstadoArmamento.findBySalvoConducto", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.salvoConducto = :salvoConducto"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByEstado", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.estado = :estado"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByPathFotos", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.pathFotos = :pathFotos"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByUsername", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.username = :username"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByCreado", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.creado = :creado"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByModificado", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.modificado = :modificado"),
    @NamedQuery(name = "RegistroEstadoArmamento.findByEstadoReg", query = "SELECT r FROM RegistroEstadoArmamento r WHERE r.estadoReg = :estadoReg")})
public class RegistroEstadoArmamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_estado_armamento")
    private Integer idRegistroEstadoArmamento;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "serial")
    private Integer serial;
    @Column(name = "municion")
    private Integer municion;
    @Column(name = "salvo_conducto")
    private Integer salvoConducto;
    @Column(name = "estado")
    private Integer estado;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 100)
    @Column(name = "path_fotos")
    private String pathFotos;
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
    @JoinColumn(name = "id_cable_ubicacion", referencedColumnName = "id_cable_ubicacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableUbicacion idCableUbicacion;

    public RegistroEstadoArmamento() {
    }

    public RegistroEstadoArmamento(Integer idRegistroEstadoArmamento) {
        this.idRegistroEstadoArmamento = idRegistroEstadoArmamento;
    }

    public RegistroEstadoArmamento(Integer idRegistroEstadoArmamento, String username, Date creado, int estadoReg) {
        this.idRegistroEstadoArmamento = idRegistroEstadoArmamento;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdRegistroEstadoArmamento() {
        return idRegistroEstadoArmamento;
    }

    public void setIdRegistroEstadoArmamento(Integer idRegistroEstadoArmamento) {
        this.idRegistroEstadoArmamento = idRegistroEstadoArmamento;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getMunicion() {
        return municion;
    }

    public void setMunicion(Integer municion) {
        this.municion = municion;
    }

    public Integer getSalvoConducto() {
        return salvoConducto;
    }

    public void setSalvoConducto(Integer salvoConducto) {
        this.salvoConducto = salvoConducto;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
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

    public CableUbicacion getIdCableUbicacion() {
        return idCableUbicacion;
    }

    public void setIdCableUbicacion(CableUbicacion idCableUbicacion) {
        this.idCableUbicacion = idCableUbicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroEstadoArmamento != null ? idRegistroEstadoArmamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroEstadoArmamento)) {
            return false;
        }
        RegistroEstadoArmamento other = (RegistroEstadoArmamento) object;
        if ((this.idRegistroEstadoArmamento == null && other.idRegistroEstadoArmamento != null) || (this.idRegistroEstadoArmamento != null && !this.idRegistroEstadoArmamento.equals(other.idRegistroEstadoArmamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.RegistroEstadoArmamento[ idRegistroEstadoArmamento=" + idRegistroEstadoArmamento + " ]";
    }
    
}
