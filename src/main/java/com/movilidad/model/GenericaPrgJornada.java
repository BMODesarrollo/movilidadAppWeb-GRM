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
 * @author cesar
 */
@Entity
@Table(name = "generica_prg_jornada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPrgJornada.findAll", query = "SELECT g FROM GenericaPrgJornada g")
    , @NamedQuery(name = "GenericaPrgJornada.findByIdGenericaPrgJornada", query = "SELECT g FROM GenericaPrgJornada g WHERE g.idGenericaPrgJornada = :idGenericaPrgJornada")
    , @NamedQuery(name = "GenericaPrgJornada.findByFecha", query = "SELECT g FROM GenericaPrgJornada g WHERE g.fecha = :fecha")
    , @NamedQuery(name = "GenericaPrgJornada.findByTimeOrigin", query = "SELECT g FROM GenericaPrgJornada g WHERE g.timeOrigin = :timeOrigin")
    , @NamedQuery(name = "GenericaPrgJornada.findByTimeDestiny", query = "SELECT g FROM GenericaPrgJornada g WHERE g.timeDestiny = :timeDestiny")
    , @NamedQuery(name = "GenericaPrgJornada.findByUsername", query = "SELECT g FROM GenericaPrgJornada g WHERE g.username = :username")
    , @NamedQuery(name = "GenericaPrgJornada.findByCreado", query = "SELECT g FROM GenericaPrgJornada g WHERE g.creado = :creado")
    , @NamedQuery(name = "GenericaPrgJornada.findByModificado", query = "SELECT g FROM GenericaPrgJornada g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GenericaPrgJornada.findByEstadoReg", query = "SELECT g FROM GenericaPrgJornada g WHERE g.estadoReg = :estadoReg")})
public class GenericaPrgJornada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_prg_jornada")
    private Integer idGenericaPrgJornada;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
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
    @JoinColumn(name = "id_generica_jornada_tipo", referencedColumnName = "id_generica_jornada_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaJornadaTipo idGenericaJornadaTipo;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaPrgJornada() {
    }

    public GenericaPrgJornada(Integer idGenericaPrgJornada) {
        this.idGenericaPrgJornada = idGenericaPrgJornada;
    }

    public Integer getIdGenericaPrgJornada() {
        return idGenericaPrgJornada;
    }

    public void setIdGenericaPrgJornada(Integer idGenericaPrgJornada) {
        this.idGenericaPrgJornada = idGenericaPrgJornada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
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

    public GenericaJornadaTipo getIdGenericaJornadaTipo() {
        return idGenericaJornadaTipo;
    }

    public void setIdGenericaJornadaTipo(GenericaJornadaTipo idGenericaJornadaTipo) {
        this.idGenericaJornadaTipo = idGenericaJornadaTipo;
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
        hash += (idGenericaPrgJornada != null ? idGenericaPrgJornada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPrgJornada)) {
            return false;
        }
        GenericaPrgJornada other = (GenericaPrgJornada) object;
        if ((this.idGenericaPrgJornada == null && other.idGenericaPrgJornada != null) || (this.idGenericaPrgJornada != null && !this.idGenericaPrgJornada.equals(other.idGenericaPrgJornada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPrgJornada[ idGenericaPrgJornada=" + idGenericaPrgJornada + " ]";
    }
    
}
