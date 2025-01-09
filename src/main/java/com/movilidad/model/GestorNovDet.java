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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "gestor_nov_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestorNovDet.findAll", query = "SELECT g FROM GestorNovDet g"),
    @NamedQuery(name = "GestorNovDet.findByIdGestorNovDet", query = "SELECT g FROM GestorNovDet g WHERE g.idGestorNovDet = :idGestorNovDet"),
    @NamedQuery(name = "GestorNovDet.findByValor", query = "SELECT g FROM GestorNovDet g WHERE g.valor = :valor"),
    @NamedQuery(name = "GestorNovDet.findByUsername", query = "SELECT g FROM GestorNovDet g WHERE g.username = :username"),
    @NamedQuery(name = "GestorNovDet.findByCreado", query = "SELECT g FROM GestorNovDet g WHERE g.creado = :creado"),
    @NamedQuery(name = "GestorNovDet.findByModificado", query = "SELECT g FROM GestorNovDet g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GestorNovDet.findByEstadoReg", query = "SELECT g FROM GestorNovDet g WHERE g.estadoReg = :estadoReg")})
public class GestorNovDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestor_nov_det")
    private Integer idGestorNovDet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private int valor;
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
    @JoinColumn(name = "id_gestor_novedad", referencedColumnName = "id_gestor_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private GestorNovedad idGestorNovedad;
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadTipoDetalle;

    public GestorNovDet() {
    }

    public GestorNovDet(Integer idGestorNovDet) {
        this.idGestorNovDet = idGestorNovDet;
    }

    public GestorNovDet(Integer idGestorNovDet, int valor) {
        this.idGestorNovDet = idGestorNovDet;
        this.valor = valor;
    }

    public Integer getIdGestorNovDet() {
        return idGestorNovDet;
    }

    public void setIdGestorNovDet(Integer idGestorNovDet) {
        this.idGestorNovDet = idGestorNovDet;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
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

    public GestorNovedad getIdGestorNovedad() {
        return idGestorNovedad;
    }

    public void setIdGestorNovedad(GestorNovedad idGestorNovedad) {
        this.idGestorNovedad = idGestorNovedad;
    }

    public NovedadTipoDetalles getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(NovedadTipoDetalles idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestorNovDet != null ? idGestorNovDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestorNovDet)) {
            return false;
        }
        GestorNovDet other = (GestorNovDet) object;
        if ((this.idGestorNovDet == null && other.idGestorNovDet != null) || (this.idGestorNovDet != null && !this.idGestorNovDet.equals(other.idGestorNovDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GestorNovDet[ idGestorNovDet=" + idGestorNovDet + " ]";
    }
    
}
