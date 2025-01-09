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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julián Arévalo
 */
@Entity
@Table(name = "pd_maestro_asistente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdMaestroAsistente.findAll", query = "SELECT p FROM PdMaestroDetalle p"),
    @NamedQuery(name = "PdMaestroAsistente.findByIdPdMaestroDetalle", query = "SELECT p FROM PdMaestroDetalle p WHERE p.idPdMaestroDetalle = :idPdMaestroDetalle"),
    @NamedQuery(name = "PdMaestroAsistente.findByFechaProceso", query = "SELECT p FROM PdMaestroDetalle p WHERE p.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "PdMaestroAsistente.findByUsername", query = "SELECT p FROM PdMaestroDetalle p WHERE p.username = :username"),
    @NamedQuery(name = "PdMaestroAsistente.findByCreado", query = "SELECT p FROM PdMaestroDetalle p WHERE p.creado = :creado"),
    @NamedQuery(name = "PdMaestroAsistente.findByModificado", query = "SELECT p FROM PdMaestroDetalle p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PdMaestroAsistente.findByEstadoReg", query = "SELECT p FROM PdMaestroDetalle p WHERE p.estadoReg = :estadoReg")})
public class PdMaestroAsistente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pd_maestro_asistente")
    private Integer idPdMaestroAsistente;
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
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_pd_maestro", referencedColumnName = "id_pd_maestro")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PdMaestro idPdMaestro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "asiste")
    private Integer asiste;

    public PdMaestroAsistente() {
    }

    public PdMaestroAsistente(Integer idPdMaestroAsistente) {
        this.idPdMaestroAsistente = idPdMaestroAsistente;
    }

    public PdMaestroAsistente(Integer idPdMaestroAsistente, String username, Date creado, Date modificado, int estadoReg, Empleado idEmpleado, PdMaestro idPdMaestro, Integer asiste) {
        this.idPdMaestroAsistente = idPdMaestroAsistente;
        this.username = username;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.idEmpleado = idEmpleado;
        this.idPdMaestro = idPdMaestro;
        this.asiste = asiste;
    }

    public Integer getIdPdMaestroAsistente() {
        return idPdMaestroAsistente;
    }

    public void setIdPdMaestroAsistente(Integer idPdMaestroAsistente) {
        this.idPdMaestroAsistente = idPdMaestroAsistente;
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



    public PdMaestro getIdPdMaestro() {
        return idPdMaestro;
    }

    public void setIdPdMaestro(PdMaestro idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }

    public Integer getAsiste() {
        return asiste;
    }

    public void setAsiste(Integer asiste) {
        this.asiste = asiste;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    
}
