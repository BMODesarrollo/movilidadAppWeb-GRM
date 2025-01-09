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
@Table(name = "pm_ing_egr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmIngEgr.findAll", query = "SELECT p FROM PmIngEgr p")
    , @NamedQuery(name = "PmIngEgr.findByIdPmIngEgr", query = "SELECT p FROM PmIngEgr p WHERE p.idPmIngEgr = :idPmIngEgr")
    , @NamedQuery(name = "PmIngEgr.findByFecha", query = "SELECT p FROM PmIngEgr p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "PmIngEgr.findByValor", query = "SELECT p FROM PmIngEgr p WHERE p.valor = :valor")
    , @NamedQuery(name = "PmIngEgr.findByLiquidado", query = "SELECT p FROM PmIngEgr p WHERE p.liquidado = :liquidado")
    , @NamedQuery(name = "PmIngEgr.findByUsername", query = "SELECT p FROM PmIngEgr p WHERE p.username = :username")
    , @NamedQuery(name = "PmIngEgr.findByCreado", query = "SELECT p FROM PmIngEgr p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmIngEgr.findByModificado", query = "SELECT p FROM PmIngEgr p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmIngEgr.findByEstadoReg", query = "SELECT p FROM PmIngEgr p WHERE p.estadoReg = :estadoReg")})
public class PmIngEgr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_ing_egr")
    private Integer idPmIngEgr;
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
    @JoinColumn(name = "id_pm_ie_conceptos", referencedColumnName = "id_pm_ie_conceptos")
    @ManyToOne(fetch = FetchType.LAZY)
    private PmIeConceptos idPmIeConceptos;

    public PmIngEgr() {
    }

    public PmIngEgr(Integer idPmIngEgr) {
        this.idPmIngEgr = idPmIngEgr;
    }

    public Integer getIdPmIngEgr() {
        return idPmIngEgr;
    }

    public void setIdPmIngEgr(Integer idPmIngEgr) {
        this.idPmIngEgr = idPmIngEgr;
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

    public PmIeConceptos getIdPmIeConceptos() {
        return idPmIeConceptos;
    }

    public void setIdPmIeConceptos(PmIeConceptos idPmIeConceptos) {
        this.idPmIeConceptos = idPmIeConceptos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmIngEgr != null ? idPmIngEgr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmIngEgr)) {
            return false;
        }
        PmIngEgr other = (PmIngEgr) object;
        if ((this.idPmIngEgr == null && other.idPmIngEgr != null) || (this.idPmIngEgr != null && !this.idPmIngEgr.equals(other.idPmIngEgr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmIngEgr[ idPmIngEgr=" + idPmIngEgr + " ]";
    }
    
}
