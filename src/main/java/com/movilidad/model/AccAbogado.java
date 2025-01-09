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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "acc_abogado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccAbogado.findAll", query = "SELECT a FROM AccAbogado a"),
    @NamedQuery(name = "AccAbogado.findByIdAccAbogado", query = "SELECT a FROM AccAbogado a WHERE a.idAccAbogado = :idAccAbogado"),
    @NamedQuery(name = "AccAbogado.findByCedula", query = "SELECT a FROM AccAbogado a WHERE a.cedula = :cedula"),
    @NamedQuery(name = "AccAbogado.findByNombreCompleto", query = "SELECT a FROM AccAbogado a WHERE a.nombreCompleto = :nombreCompleto"),
    @NamedQuery(name = "AccAbogado.findByTarjetaProfesional", query = "SELECT a FROM AccAbogado a WHERE a.tarjetaProfesional = :tarjetaProfesional"),
    @NamedQuery(name = "AccAbogado.findByUsername", query = "SELECT a FROM AccAbogado a WHERE a.username = :username"),
    @NamedQuery(name = "AccAbogado.findByCreado", query = "SELECT a FROM AccAbogado a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccAbogado.findByModificado", query = "SELECT a FROM AccAbogado a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccAbogado.findByEstadoReg", query = "SELECT a FROM AccAbogado a WHERE a.estadoReg = :estadoReg"),
    @NamedQuery(name = "AccAbogado.findByCedulaExpedicion", query = "SELECT a FROM AccAbogado a WHERE a.cedulaExpedicion = :cedulaExpedicion"),
    @NamedQuery(name = "AccAbogado.findByTarjetaProfesionalExpedicion", query = "SELECT a FROM AccAbogado a WHERE a.tarjetaProfesionalExpedicion = :tarjetaProfesionalExpedicion"),
    @NamedQuery(name = "AccAbogado.findByMembrete", query = "SELECT a FROM AccAbogado a WHERE a.membrete = :membrete")})
public class AccAbogado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_abogado")
    private Integer idAccAbogado;
    @Size(max = 15)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 40)
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Size(max = 10)
    @Column(name = "tarjeta_profesional")
    private String tarjetaProfesional;
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
    @Size(max = 50)
    @Column(name = "cedula_expedicion")
    private String cedulaExpedicion;
    @Size(max = 70)
    @Column(name = "tarjeta_profesional_expedicion")
    private String tarjetaProfesionalExpedicion;
    @Size(max = 20)
    @Column(name = "membrete")
    private String membrete;

    public AccAbogado() {
    }

    public AccAbogado(Integer idAccAbogado) {
        this.idAccAbogado = idAccAbogado;
    }

    public Integer getIdAccAbogado() {
        return idAccAbogado;
    }

    public void setIdAccAbogado(Integer idAccAbogado) {
        this.idAccAbogado = idAccAbogado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTarjetaProfesional() {
        return tarjetaProfesional;
    }

    public void setTarjetaProfesional(String tarjetaProfesional) {
        this.tarjetaProfesional = tarjetaProfesional;
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

    public String getCedulaExpedicion() {
        return cedulaExpedicion;
    }

    public void setCedulaExpedicion(String cedulaExpedicion) {
        this.cedulaExpedicion = cedulaExpedicion;
    }

    public String getTarjetaProfesionalExpedicion() {
        return tarjetaProfesionalExpedicion;
    }

    public void setTarjetaProfesionalExpedicion(String tarjetaProfesionalExpedicion) {
        this.tarjetaProfesionalExpedicion = tarjetaProfesionalExpedicion;
    }

    public String getMembrete() {
        return membrete;
    }

    public void setMembrete(String membrete) {
        this.membrete = membrete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccAbogado != null ? idAccAbogado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccAbogado)) {
            return false;
        }
        AccAbogado other = (AccAbogado) object;
        if ((this.idAccAbogado == null && other.idAccAbogado != null) || (this.idAccAbogado != null && !this.idAccAbogado.equals(other.idAccAbogado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccAbogado[ idAccAbogado=" + idAccAbogado + " ]";
    }
    
}
