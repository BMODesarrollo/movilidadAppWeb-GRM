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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julián Arévalo
 */
@Entity
@Table(name = "pd_responsables")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdResponsables.findAll", query = "SELECT p FROM PdResponsables p"),
    @NamedQuery(name = "PdResponsables.findByIdPmNovedadIncluir", query = "SELECT p FROM PdResponsables p WHERE p.idResponsable = :idResponsable"),
    @NamedQuery(name = "PdResponsables.findByActivo", query = "SELECT p FROM PdResponsables p WHERE p.activo = :activo"),
    @NamedQuery(name = "PdResponsables.findByUsername", query = "SELECT p FROM PdResponsables p WHERE p.username = :username"),
    @NamedQuery(name = "PdResponsables.findByCreado", query = "SELECT p FROM PdResponsables p WHERE p.creado = :creado"),
    @NamedQuery(name = "PdResponsables.findByModificado", query = "SELECT p FROM PdResponsables p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PdResponsables.findByEstadoReg", query = "SELECT p FROM PdResponsables p WHERE p.estadoReg = :estadoReg")})
public class PdResponsables implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_responsable") 
    private int idResponsable;
        
    @Column(name = "activo")
    private Integer activo;
    
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date creado;
    
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    
    @Column(name = "estado_reg")
    private Integer estadoReg;
    
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;


    public PdResponsables() {
    }

    public PdResponsables(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public PdResponsables(int idResponsable, Integer activo, String username, Date creado, Date modificado, Integer estadoReg, Users user) {
        this.idResponsable = idResponsable;
        this.activo = activo;
        this.username = username;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.user = user;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    
    
}
