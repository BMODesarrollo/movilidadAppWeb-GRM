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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "atv_token_prestador")
@XmlRootElement
public class AtvTokenPrestador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_token_prestador")
    private Integer idAtvTokenPrestador;
    @Column(name = "token")
    private String token;
    @Column(name = "activo")
    private Integer activo;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    
    @JoinColumn(name = "id_atv_prestador", referencedColumnName = "id_atv_prestador")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AtvPrestador idAtvPrestador;

    public AtvTokenPrestador() {
    }

    public Integer getIdAtvTokenPrestador() {
        return idAtvTokenPrestador;
    }

    public void setIdAtvTokenPrestador(Integer idAtvTokenPrestador) {
        this.idAtvTokenPrestador = idAtvTokenPrestador;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public AtvPrestador getIdAtvPrestador() {
        return idAtvPrestador;
    }

    public void setIdAtvPrestador(AtvPrestador idAtvPrestador) {
        this.idAtvPrestador = idAtvPrestador;
    }

    
}
