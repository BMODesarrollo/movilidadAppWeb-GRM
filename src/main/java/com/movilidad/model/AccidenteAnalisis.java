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
 * @author cesar
 */
@Entity
@Table(name = "accidente_analisis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteAnalisis.findAll", query = "SELECT a FROM AccidenteAnalisis a")
    , @NamedQuery(name = "AccidenteAnalisis.findByIdAccidenteAnalisis", query = "SELECT a FROM AccidenteAnalisis a WHERE a.idAccidenteAnalisis = :idAccidenteAnalisis")
    , @NamedQuery(name = "AccidenteAnalisis.findByFecha", query = "SELECT a FROM AccidenteAnalisis a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AccidenteAnalisis.findByValoracion", query = "SELECT a FROM AccidenteAnalisis a WHERE a.valoracion = :valoracion")
    , @NamedQuery(name = "AccidenteAnalisis.findByUsername", query = "SELECT a FROM AccidenteAnalisis a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteAnalisis.findByCreado", query = "SELECT a FROM AccidenteAnalisis a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteAnalisis.findByModificado", query = "SELECT a FROM AccidenteAnalisis a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteAnalisis.findByEstadoReg", query = "SELECT a FROM AccidenteAnalisis a WHERE a.estadoReg = :estadoReg")})
public class AccidenteAnalisis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_analisis")
    private Integer idAccidenteAnalisis;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "valoracion")
    private Integer valoracion;
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
    @JoinColumn(name = "id_acc_arbol", referencedColumnName = "id_acc_arbol")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccArbol idAccArbol;
    @JoinColumn(name = "id_acc_causa", referencedColumnName = "id_acc_causa")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccCausa idAccCausa;
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;

    public AccidenteAnalisis() {
    }

    public AccidenteAnalisis(Integer idAccidenteAnalisis) {
        this.idAccidenteAnalisis = idAccidenteAnalisis;
    }

    public Integer getIdAccidenteAnalisis() {
        return idAccidenteAnalisis;
    }

    public void setIdAccidenteAnalisis(Integer idAccidenteAnalisis) {
        this.idAccidenteAnalisis = idAccidenteAnalisis;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
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

    public AccArbol getIdAccArbol() {
        return idAccArbol;
    }

    public void setIdAccArbol(AccArbol idAccArbol) {
        this.idAccArbol = idAccArbol;
    }

    public AccCausa getIdAccCausa() {
        return idAccCausa;
    }

    public void setIdAccCausa(AccCausa idAccCausa) {
        this.idAccCausa = idAccCausa;
    }

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteAnalisis != null ? idAccidenteAnalisis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteAnalisis)) {
            return false;
        }
        AccidenteAnalisis other = (AccidenteAnalisis) object;
        if ((this.idAccidenteAnalisis == null && other.idAccidenteAnalisis != null) || (this.idAccidenteAnalisis != null && !this.idAccidenteAnalisis.equals(other.idAccidenteAnalisis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteAnalisis[ idAccidenteAnalisis=" + idAccidenteAnalisis + " ]";
    }
    
}
