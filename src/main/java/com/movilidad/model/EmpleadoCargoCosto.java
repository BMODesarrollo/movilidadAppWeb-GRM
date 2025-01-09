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
 * @author HP
 */
@Entity
@Table(name = "empleado_cargo_costo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoCargoCosto.findAll", query = "SELECT e FROM EmpleadoCargoCosto e")
    , @NamedQuery(name = "EmpleadoCargoCosto.findByIdEmpleadoCargoCosto", query = "SELECT e FROM EmpleadoCargoCosto e WHERE e.idEmpleadoCargoCosto = :idEmpleadoCargoCosto")
    , @NamedQuery(name = "EmpleadoCargoCosto.findByDesde", query = "SELECT e FROM EmpleadoCargoCosto e WHERE e.desde = :desde")
    , @NamedQuery(name = "EmpleadoCargoCosto.findByHasta", query = "SELECT e FROM EmpleadoCargoCosto e WHERE e.hasta = :hasta")
    , @NamedQuery(name = "EmpleadoCargoCosto.findByCosto", query = "SELECT e FROM EmpleadoCargoCosto e WHERE e.costo = :costo")
    , @NamedQuery(name = "EmpleadoCargoCosto.findByUsername", query = "SELECT e FROM EmpleadoCargoCosto e WHERE e.username = :username")
    , @NamedQuery(name = "EmpleadoCargoCosto.findByCreado", query = "SELECT e FROM EmpleadoCargoCosto e WHERE e.creado = :creado")
    , @NamedQuery(name = "EmpleadoCargoCosto.findByModificado", query = "SELECT e FROM EmpleadoCargoCosto e WHERE e.modificado = :modificado")
    , @NamedQuery(name = "EmpleadoCargoCosto.findByEstadoReg", query = "SELECT e FROM EmpleadoCargoCosto e WHERE e.estadoReg = :estadoReg")})
public class EmpleadoCargoCosto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_cargo_costo")
    private Integer idEmpleadoCargoCosto;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Column(name = "costo")
    private Integer costo;
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
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;

    public EmpleadoCargoCosto() {
    }

    public EmpleadoCargoCosto(Integer idEmpleadoCargoCosto) {
        this.idEmpleadoCargoCosto = idEmpleadoCargoCosto;
    }

    public Integer getIdEmpleadoCargoCosto() {
        return idEmpleadoCargoCosto;
    }

    public void setIdEmpleadoCargoCosto(Integer idEmpleadoCargoCosto) {
        this.idEmpleadoCargoCosto = idEmpleadoCargoCosto;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
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

    public EmpleadoTipoCargo getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(EmpleadoTipoCargo idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleadoCargoCosto != null ? idEmpleadoCargoCosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoCargoCosto)) {
            return false;
        }
        EmpleadoCargoCosto other = (EmpleadoCargoCosto) object;
        if ((this.idEmpleadoCargoCosto == null && other.idEmpleadoCargoCosto != null) || (this.idEmpleadoCargoCosto != null && !this.idEmpleadoCargoCosto.equals(other.idEmpleadoCargoCosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.EmpleadoCargoCosto[ idEmpleadoCargoCosto=" + idEmpleadoCargoCosto + " ]";
    }
    
}
