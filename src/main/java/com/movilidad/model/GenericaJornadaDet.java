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
 * @author solucionesit
 */
@Entity
@Table(name = "generica_jornada_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaJornadaDet.findAll", query = "SELECT g FROM GenericaJornadaDet g")
    , @NamedQuery(name = "GenericaJornadaDet.findByIdGenericaJornadaDet", query = "SELECT g FROM GenericaJornadaDet g WHERE g.idGenericaJornadaDet = :idGenericaJornadaDet")
    , @NamedQuery(name = "GenericaJornadaDet.findByCantidad", query = "SELECT g FROM GenericaJornadaDet g WHERE g.cantidad = :cantidad")
    , @NamedQuery(name = "GenericaJornadaDet.findByTimeorigin", query = "SELECT g FROM GenericaJornadaDet g WHERE g.timeorigin = :timeorigin")
    , @NamedQuery(name = "GenericaJornadaDet.findByTimedestiny", query = "SELECT g FROM GenericaJornadaDet g WHERE g.timedestiny = :timedestiny")
    , @NamedQuery(name = "GenericaJornadaDet.findByUsername", query = "SELECT g FROM GenericaJornadaDet g WHERE g.username = :username")
    , @NamedQuery(name = "GenericaJornadaDet.findByCreado", query = "SELECT g FROM GenericaJornadaDet g WHERE g.creado = :creado")
    , @NamedQuery(name = "GenericaJornadaDet.findByModificado", query = "SELECT g FROM GenericaJornadaDet g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GenericaJornadaDet.findByEstadoReg", query = "SELECT g FROM GenericaJornadaDet g WHERE g.estadoReg = :estadoReg")})
public class GenericaJornadaDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_jornada_det")
    private Integer idGenericaJornadaDet;
    @Size(max = 8)
    @Column(name = "cantidad")
    private String cantidad;
    @Size(max = 8)
    @Column(name = "timeorigin")
    private String timeorigin;
    @Size(max = 8)
    @Column(name = "timedestiny")
    private String timedestiny;
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
    @JoinColumn(name = "id_generica_jornada", referencedColumnName = "id_generica_jornada")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenericaJornada idGenericaJornada;
    @JoinColumn(name = "id_param_reporte", referencedColumnName = "id_param_reporte_horas")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamReporteHoras idParamReporte;

    public GenericaJornadaDet() {
    }

    public GenericaJornadaDet(Integer idGenericaJornadaDet) {
        this.idGenericaJornadaDet = idGenericaJornadaDet;
    }

    public Integer getIdGenericaJornadaDet() {
        return idGenericaJornadaDet;
    }

    public void setIdGenericaJornadaDet(Integer idGenericaJornadaDet) {
        this.idGenericaJornadaDet = idGenericaJornadaDet;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTimeorigin() {
        return timeorigin;
    }

    public void setTimeorigin(String timeorigin) {
        this.timeorigin = timeorigin;
    }

    public String getTimedestiny() {
        return timedestiny;
    }

    public void setTimedestiny(String timedestiny) {
        this.timedestiny = timedestiny;
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

    public GenericaJornada getIdGenericaJornada() {
        return idGenericaJornada;
    }

    public void setIdGenericaJornada(GenericaJornada idGenericaJornada) {
        this.idGenericaJornada = idGenericaJornada;
    }

    public ParamReporteHoras getIdParamReporte() {
        return idParamReporte;
    }

    public void setIdParamReporte(ParamReporteHoras idParamReporte) {
        this.idParamReporte = idParamReporte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaJornadaDet != null ? idGenericaJornadaDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaJornadaDet)) {
            return false;
        }
        GenericaJornadaDet other = (GenericaJornadaDet) object;
        if ((this.idGenericaJornadaDet == null && other.idGenericaJornadaDet != null) || (this.idGenericaJornadaDet != null && !this.idGenericaJornadaDet.equals(other.idGenericaJornadaDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaJornadaDet[ idGenericaJornadaDet=" + idGenericaJornadaDet + " ]";
    }
    
}
