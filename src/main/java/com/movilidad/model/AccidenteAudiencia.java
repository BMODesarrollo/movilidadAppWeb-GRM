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
 * @author HP
 */
@Entity
@Table(name = "accidente_audiencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteAudiencia.findAll", query = "SELECT a FROM AccidenteAudiencia a")
    , @NamedQuery(name = "AccidenteAudiencias.findByIdAccidenteAudiencia", query = "SELECT a FROM AccidenteAudiencia a WHERE a.idAccidenteAudiencia = :idAccidenteAudiencia")
    , @NamedQuery(name = "AccidenteAudiencias.findByFecha", query = "SELECT a FROM AccidenteAudiencia a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AccidenteAudiencias.findByLugar", query = "SELECT a FROM AccidenteAudiencia a WHERE a.lugar = :lugar")})
public class AccidenteAudiencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accidente_audiencia")
    private Integer idAccidenteAudiencia;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "valor")
    private int valor;
    @Column(name = "lugar")
    private String lugar;
    @Column(name = "fecha_conciliacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConciliacion;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
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

    public AccidenteAudiencia() {
    }

    public AccidenteAudiencia(Integer idAccidenteAudiencia) {
        this.idAccidenteAudiencia = idAccidenteAudiencia;
    }

    public Integer getIdAccidenteAudiencia() {
        return idAccidenteAudiencia;
    }

    public void setIdAccidenteAudiencia(Integer idAccidenteAudiencia) {
        this.idAccidenteAudiencia = idAccidenteAudiencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    
    

    public Date getFechaConciliacion() {
        return fechaConciliacion;
    }

    public void setFechaConciliacion(Date fechaConciliacion) {
        this.fechaConciliacion = fechaConciliacion;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteAudiencia != null ? idAccidenteAudiencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteAudiencia)) {
            return false;
        }
        AccidenteAudiencia other = (AccidenteAudiencia) object;
        if ((this.idAccidenteAudiencia == null && other.idAccidenteAudiencia != null) || (this.idAccidenteAudiencia != null && !this.idAccidenteAudiencia.equals(other.idAccidenteAudiencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteAudiencia[ idAccidenteAudiencia=" + idAccidenteAudiencia + " ]";
    }

    
    
}
