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
 * @author HP
 */
@Entity
@Table(name = "accidente_costos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteCostos.findAll", query = "SELECT a FROM AccidenteCostos a")
    , @NamedQuery(name = "AccidenteCostos.findByIdAccidenteCostos", query = "SELECT a FROM AccidenteCostos a WHERE a.idAccidenteCostos = :idAccidenteCostos")
    , @NamedQuery(name = "AccidenteCostos.findByFecha", query = "SELECT a FROM AccidenteCostos a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AccidenteCostos.findByValor", query = "SELECT a FROM AccidenteCostos a WHERE a.valor = :valor")
    , @NamedQuery(name = "AccidenteCostos.findByUsername", query = "SELECT a FROM AccidenteCostos a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteCostos.findByCreado", query = "SELECT a FROM AccidenteCostos a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteCostos.findByModificado", query = "SELECT a FROM AccidenteCostos a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteCostos.findByEstadoReg", query = "SELECT a FROM AccidenteCostos a WHERE a.estadoReg = :estadoReg")})
public class AccidenteCostos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_costos")
    private Integer idAccidenteCostos;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "valor")
    private Integer valor;
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
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;
    @JoinColumn(name = "id_acc_tipo_costos", referencedColumnName = "id_acc_tipo_costos")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoCostos idAccTipoCostos;

    public AccidenteCostos() {
        fecha = new Date();
    }

    public AccidenteCostos(Integer idAccidenteCostos) {
        this.idAccidenteCostos = idAccidenteCostos;
    }

    public Integer getIdAccidenteCostos() {
        return idAccidenteCostos;
    }

    public void setIdAccidenteCostos(Integer idAccidenteCostos) {
        this.idAccidenteCostos = idAccidenteCostos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    public AccTipoCostos getIdAccTipoCostos() {
        return idAccTipoCostos;
    }

    public void setIdAccTipoCostos(AccTipoCostos idAccTipoCostos) {
        this.idAccTipoCostos = idAccTipoCostos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteCostos != null ? idAccidenteCostos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteCostos)) {
            return false;
        }
        AccidenteCostos other = (AccidenteCostos) object;
        if ((this.idAccidenteCostos == null && other.idAccidenteCostos != null) || (this.idAccidenteCostos != null && !this.idAccidenteCostos.equals(other.idAccidenteCostos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteCostos[ idAccidenteCostos=" + idAccidenteCostos + " ]";
    }
    
}
