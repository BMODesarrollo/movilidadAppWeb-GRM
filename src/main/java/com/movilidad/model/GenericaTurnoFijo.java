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
@Table(name = "generica_turno_fijo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaTurnoFijo.findAll", query = "SELECT g FROM GenericaTurnoFijo g"),
    @NamedQuery(name = "GenericaTurnoFijo.findByIdGenericaTurnoFijo", query = "SELECT g FROM GenericaTurnoFijo g WHERE g.idGenericaTurnoFijo = :idGenericaTurnoFijo"),
    @NamedQuery(name = "GenericaTurnoFijo.findByDescripcion", query = "SELECT g FROM GenericaTurnoFijo g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GenericaTurnoFijo.findByActivo", query = "SELECT g FROM GenericaTurnoFijo g WHERE g.activo = :activo"),
    @NamedQuery(name = "GenericaTurnoFijo.findByCreado", query = "SELECT g FROM GenericaTurnoFijo g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaTurnoFijo.findByModificado", query = "SELECT g FROM GenericaTurnoFijo g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaTurnoFijo.findByEstadoReg", query = "SELECT g FROM GenericaTurnoFijo g WHERE g.estadoReg = :estadoReg")})
public class GenericaTurnoFijo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_turno_fijo")
    private Integer idGenericaTurnoFijo;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Integer activo;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_generica_jornada_tipo", referencedColumnName = "id_generica_jornada_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaJornadaTipo idGenericaJornadaTipo;

    public GenericaTurnoFijo() {
    }

    public GenericaTurnoFijo(Integer idGenericaTurnoFijo) {
        this.idGenericaTurnoFijo = idGenericaTurnoFijo;
    }

    public Integer getIdGenericaTurnoFijo() {
        return idGenericaTurnoFijo;
    }

    public void setIdGenericaTurnoFijo(Integer idGenericaTurnoFijo) {
        this.idGenericaTurnoFijo = idGenericaTurnoFijo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaTurnoFijo != null ? idGenericaTurnoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaTurnoFijo)) {
            return false;
        }
        GenericaTurnoFijo other = (GenericaTurnoFijo) object;
        if ((this.idGenericaTurnoFijo == null && other.idGenericaTurnoFijo != null) || (this.idGenericaTurnoFijo != null && !this.idGenericaTurnoFijo.equals(other.idGenericaTurnoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaTurnoFijo[ idGenericaTurnoFijo=" + idGenericaTurnoFijo + " ]";
    }

}
