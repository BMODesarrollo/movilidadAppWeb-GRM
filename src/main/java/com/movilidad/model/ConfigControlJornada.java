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
 * @author solucionesit
 */
@Entity
@Table(name = "config_control_jornada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigControlJornada.findAll", query = "SELECT c FROM ConfigControlJornada c")
    , @NamedQuery(name = "ConfigControlJornada.findByIdConfigControlJornada", query = "SELECT c FROM ConfigControlJornada c WHERE c.idConfigControlJornada = :idConfigControlJornada")
    , @NamedQuery(name = "ConfigControlJornada.findByNombreConfig", query = "SELECT c FROM ConfigControlJornada c WHERE c.nombreConfig = :nombreConfig")
    , @NamedQuery(name = "ConfigControlJornada.findByTiempo", query = "SELECT c FROM ConfigControlJornada c WHERE c.tiempo = :tiempo")
    , @NamedQuery(name = "ConfigControlJornada.findByEstado", query = "SELECT c FROM ConfigControlJornada c WHERE c.estado = :estado")
    , @NamedQuery(name = "ConfigControlJornada.findByRestringe", query = "SELECT c FROM ConfigControlJornada c WHERE c.restringe = :restringe")
    , @NamedQuery(name = "ConfigControlJornada.findByObservacion", query = "SELECT c FROM ConfigControlJornada c WHERE c.observacion = :observacion")
    , @NamedQuery(name = "ConfigControlJornada.findByUsername", query = "SELECT c FROM ConfigControlJornada c WHERE c.username = :username")
    , @NamedQuery(name = "ConfigControlJornada.findByCreado", query = "SELECT c FROM ConfigControlJornada c WHERE c.creado = :creado")
    , @NamedQuery(name = "ConfigControlJornada.findByModificado", query = "SELECT c FROM ConfigControlJornada c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "ConfigControlJornada.findByEstadoReg", query = "SELECT c FROM ConfigControlJornada c WHERE c.estadoReg = :estadoReg")})
public class ConfigControlJornada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_config_control_jornada")
    private Integer idConfigControlJornada;
    @Size(max = 45)
    @Column(name = "nombre_config")
    private String nombreConfig;
    @Size(max = 8)
    @Column(name = "tiempo")
    private String tiempo;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "restringe")
    private Integer restringe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "observacion")
    private String observacion;
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

    public ConfigControlJornada() {
    }

    public ConfigControlJornada(Integer idConfigControlJornada) {
        this.idConfigControlJornada = idConfigControlJornada;
    }

    public ConfigControlJornada(Integer idConfigControlJornada, String observacion) {
        this.idConfigControlJornada = idConfigControlJornada;
        this.observacion = observacion;
    }

    public Integer getIdConfigControlJornada() {
        return idConfigControlJornada;
    }

    public void setIdConfigControlJornada(Integer idConfigControlJornada) {
        this.idConfigControlJornada = idConfigControlJornada;
    }

    public String getNombreConfig() {
        return nombreConfig;
    }

    public void setNombreConfig(String nombreConfig) {
        this.nombreConfig = nombreConfig;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getRestringe() {
        return restringe;
    }

    public void setRestringe(Integer restringe) {
        this.restringe = restringe;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
        hash += (idConfigControlJornada != null ? idConfigControlJornada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigControlJornada)) {
            return false;
        }
        ConfigControlJornada other = (ConfigControlJornada) object;
        if ((this.idConfigControlJornada == null && other.idConfigControlJornada != null) || (this.idConfigControlJornada != null && !this.idConfigControlJornada.equals(other.idConfigControlJornada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("idConfigControlJornada: ");
        sb.append(idConfigControlJornada);
        sb.append("\n");
        sb.append("nombreConfig: ");
        sb.append(nombreConfig);
        sb.append("\n");
        sb.append("tiempo: ");
        sb.append(tiempo);
        sb.append("\n");
        sb.append("estado: ");
        sb.append(estado);
        sb.append("\n");
        sb.append("restringe: ");
        sb.append(restringe);
        sb.append("\n");
        sb.append("observacion: ");
        sb.append(observacion);
        return sb.toString();
    }
    
}
