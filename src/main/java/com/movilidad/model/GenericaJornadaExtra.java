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
 * @author solucionesit
 */
@Entity
@Table(name = "generica_jornada_extra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaJornadaExtra.findAll", query = "SELECT g FROM GenericaJornadaExtra g"),
    @NamedQuery(name = "GenericaJornadaExtra.findByIdGenericaJornadaExtra", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.idGenericaJornadaExtra = :idGenericaJornadaExtra"),
    @NamedQuery(name = "GenericaJornadaExtra.findByDesde", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.desde = :desde"),
    @NamedQuery(name = "GenericaJornadaExtra.findByHasta", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.hasta = :hasta"),
    @NamedQuery(name = "GenericaJornadaExtra.findBySemana1", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.semana1 = :semana1"),
    @NamedQuery(name = "GenericaJornadaExtra.findBySemana2", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.semana2 = :semana2"),
    @NamedQuery(name = "GenericaJornadaExtra.findBySemana3", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.semana3 = :semana3"),
    @NamedQuery(name = "GenericaJornadaExtra.findBySemana4", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.semana4 = :semana4"),
    @NamedQuery(name = "GenericaJornadaExtra.findBySemana5", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.semana5 = :semana5"),
    @NamedQuery(name = "GenericaJornadaExtra.findByTotal", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.total = :total"),
    @NamedQuery(name = "GenericaJornadaExtra.findByUsername", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaJornadaExtra.findByCreado", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaJornadaExtra.findByModificado", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaJornadaExtra.findByEstadoReg", query = "SELECT g FROM GenericaJornadaExtra g WHERE g.estadoReg = :estadoReg")})
public class GenericaJornadaExtra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_jornada_extra")
    private Integer idGenericaJornadaExtra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Column(name = "semana1")
    private double semana1;
    @Column(name = "semana2")
    private double semana2;
    @Column(name = "semana3")
    private double semana3;
    @Column(name = "semana4")
    private double semana4;
    @Column(name = "semana5")
    private double semana5;
    @Column(name = "total")
    private double total;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleado;

    public GenericaJornadaExtra() {
    }

    public GenericaJornadaExtra(Integer idGenericaJornadaExtra) {
        this.idGenericaJornadaExtra = idGenericaJornadaExtra;
    }

    public GenericaJornadaExtra(Integer idGenericaJornadaExtra, Date desde, Date hasta, Date creado, Date modificado) {
        this.idGenericaJornadaExtra = idGenericaJornadaExtra;
        this.desde = desde;
        this.hasta = hasta;
        this.creado = creado;
        this.modificado = modificado;
    }

    public Integer getIdGenericaJornadaExtra() {
        return idGenericaJornadaExtra;
    }

    public void setIdGenericaJornadaExtra(Integer idGenericaJornadaExtra) {
        this.idGenericaJornadaExtra = idGenericaJornadaExtra;
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

    public double getSemana1() {
        return semana1;
    }

    public void setSemana1(double semana1) {
        this.semana1 = semana1;
    }

    public double getSemana2() {
        return semana2;
    }

    public void setSemana2(double semana2) {
        this.semana2 = semana2;
    }

    public double getSemana3() {
        return semana3;
    }

    public void setSemana3(double semana3) {
        this.semana3 = semana3;
    }

    public double getSemana4() {
        return semana4;
    }

    public void setSemana4(double semana4) {
        this.semana4 = semana4;
    }

    public double getSemana5() {
        return semana5;
    }

    public void setSemana5(double semana5) {
        this.semana5 = semana5;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaJornadaExtra != null ? idGenericaJornadaExtra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaJornadaExtra)) {
            return false;
        }
        GenericaJornadaExtra other = (GenericaJornadaExtra) object;
        if ((this.idGenericaJornadaExtra == null && other.idGenericaJornadaExtra != null) || (this.idGenericaJornadaExtra != null && !this.idGenericaJornadaExtra.equals(other.idGenericaJornadaExtra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaJornadaExtra[ idGenericaJornadaExtra=" + idGenericaJornadaExtra + " ]";
    }
    
}
