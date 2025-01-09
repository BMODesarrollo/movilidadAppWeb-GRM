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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "generica_pm_ing_egr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmIngEgr.findAll", query = "SELECT g FROM GenericaPmIngEgr g")
    , @NamedQuery(name = "GenericaPmIngEgr.findByIdGenericaPmIngEgr", query = "SELECT g FROM GenericaPmIngEgr g WHERE g.idGenericaPmIngEgr = :idGenericaPmIngEgr")
    , @NamedQuery(name = "GenericaPmIngEgr.findByFecha", query = "SELECT g FROM GenericaPmIngEgr g WHERE g.fecha = :fecha")
    , @NamedQuery(name = "GenericaPmIngEgr.findByValor", query = "SELECT g FROM GenericaPmIngEgr g WHERE g.valor = :valor")
    , @NamedQuery(name = "GenericaPmIngEgr.findByLiquidado", query = "SELECT g FROM GenericaPmIngEgr g WHERE g.liquidado = :liquidado")
    , @NamedQuery(name = "GenericaPmIngEgr.findByUsername", query = "SELECT g FROM GenericaPmIngEgr g WHERE g.username = :username")
    , @NamedQuery(name = "GenericaPmIngEgr.findByCreado", query = "SELECT g FROM GenericaPmIngEgr g WHERE g.creado = :creado")
    , @NamedQuery(name = "GenericaPmIngEgr.findByModificado", query = "SELECT g FROM GenericaPmIngEgr g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GenericaPmIngEgr.findByEstadoReg", query = "SELECT g FROM GenericaPmIngEgr g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmIngEgr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pm_ing_egr")
    private Integer idGenericaPmIngEgr;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "valor")
    private Integer valor;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "liquidado")
    private Integer liquidado;
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
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_generica_pm_ie_conceptos", referencedColumnName = "id_generica_pm_ie_conceptos")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaPmIeConceptos idGenericaPmIeConceptos;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaPmIngEgr() {
    }

    public GenericaPmIngEgr(Integer idGenericaPmIngEgr) {
        this.idGenericaPmIngEgr = idGenericaPmIngEgr;
    }

    public Integer getIdGenericaPmIngEgr() {
        return idGenericaPmIngEgr;
    }

    public void setIdGenericaPmIngEgr(Integer idGenericaPmIngEgr) {
        this.idGenericaPmIngEgr = idGenericaPmIngEgr;
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

    public Integer getLiquidado() {
        return liquidado;
    }

    public void setLiquidado(Integer liquidado) {
        this.liquidado = liquidado;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public GenericaPmIeConceptos getIdGenericaPmIeConceptos() {
        return idGenericaPmIeConceptos;
    }

    public void setIdGenericaPmIeConceptos(GenericaPmIeConceptos idGenericaPmIeConceptos) {
        this.idGenericaPmIeConceptos = idGenericaPmIeConceptos;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaPmIngEgr != null ? idGenericaPmIngEgr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmIngEgr)) {
            return false;
        }
        GenericaPmIngEgr other = (GenericaPmIngEgr) object;
        if ((this.idGenericaPmIngEgr == null && other.idGenericaPmIngEgr != null) || (this.idGenericaPmIngEgr != null && !this.idGenericaPmIngEgr.equals(other.idGenericaPmIngEgr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmIngEgr[ idGenericaPmIngEgr=" + idGenericaPmIngEgr + " ]";
    }
    
}
