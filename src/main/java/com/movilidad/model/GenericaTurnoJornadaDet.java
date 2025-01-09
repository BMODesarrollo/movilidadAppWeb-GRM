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
@Table(name = "generica_turno_jornada_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaTurnoJornadaDet.findAll", query = "SELECT g FROM GenericaTurnoJornadaDet g"),
    @NamedQuery(name = "GenericaTurnoJornadaDet.findByIdGenericaTurnoJornadaDet", query = "SELECT g FROM GenericaTurnoJornadaDet g WHERE g.idGenericaTurnoJornadaDet = :idGenericaTurnoJornadaDet"),
    @NamedQuery(name = "GenericaTurnoJornadaDet.findByCantidadHabil", query = "SELECT g FROM GenericaTurnoJornadaDet g WHERE g.cantidadHabil = :cantidadHabil"),
    @NamedQuery(name = "GenericaTurnoJornadaDet.findByCantidadFeriada", query = "SELECT g FROM GenericaTurnoJornadaDet g WHERE g.cantidadFeriada = :cantidadFeriada"),
    @NamedQuery(name = "GenericaTurnoJornadaDet.findByUsername", query = "SELECT g FROM GenericaTurnoJornadaDet g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaTurnoJornadaDet.findByCreado", query = "SELECT g FROM GenericaTurnoJornadaDet g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaTurnoJornadaDet.findByModificado", query = "SELECT g FROM GenericaTurnoJornadaDet g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaTurnoJornadaDet.findByEstadoReg", query = "SELECT g FROM GenericaTurnoJornadaDet g WHERE g.estadoReg = :estadoReg")})
public class GenericaTurnoJornadaDet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_turno_jornada_det")
    private Integer idGenericaTurnoJornadaDet;
    @Column(name = "cantidad_habil")
    private Integer cantidadHabil;
    @Column(name = "cantidad_feriada")
    private Integer cantidadFeriada;
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
    @JoinColumn(name = "id_generica_turno_jornada", referencedColumnName = "id_generica_turno_jornada")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaTurnoJornada idGenericaTurnoJornada;

    public GenericaTurnoJornadaDet() {
    }

    public GenericaTurnoJornadaDet(Integer idGenericaTurnoJornadaDet) {
        this.idGenericaTurnoJornadaDet = idGenericaTurnoJornadaDet;
    }

    public Integer getIdGenericaTurnoJornadaDet() {
        return idGenericaTurnoJornadaDet;
    }

    public void setIdGenericaTurnoJornadaDet(Integer idGenericaTurnoJornadaDet) {
        this.idGenericaTurnoJornadaDet = idGenericaTurnoJornadaDet;
    }

    public Integer getCantidadHabil() {
        return cantidadHabil;
    }

    public void setCantidadHabil(Integer cantidadHabil) {
        this.cantidadHabil = cantidadHabil;
    }

    public Integer getCantidadFeriada() {
        return cantidadFeriada;
    }

    public void setCantidadFeriada(Integer cantidadFeriada) {
        this.cantidadFeriada = cantidadFeriada;
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

    public GenericaTurnoJornada getIdGenericaTurnoJornada() {
        return idGenericaTurnoJornada;
    }

    public void setIdGenericaTurnoJornada(GenericaTurnoJornada idGenericaTurnoJornada) {
        this.idGenericaTurnoJornada = idGenericaTurnoJornada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaTurnoJornadaDet != null ? idGenericaTurnoJornadaDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaTurnoJornadaDet)) {
            return false;
        }
        GenericaTurnoJornadaDet other = (GenericaTurnoJornadaDet) object;
        if ((this.idGenericaTurnoJornadaDet == null && other.idGenericaTurnoJornadaDet != null) || (this.idGenericaTurnoJornadaDet != null && !this.idGenericaTurnoJornadaDet.equals(other.idGenericaTurnoJornadaDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaTurnoJornadaDet[ idGenericaTurnoJornadaDet=" + idGenericaTurnoJornadaDet + " ]";
    }
    
}
