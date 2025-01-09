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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "novedad_vacaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadVacaciones.findAll", query = "SELECT n FROM NovedadVacaciones n"),
    @NamedQuery(name = "NovedadVacaciones.findByIdNovedadVacaciones", query = "SELECT n FROM NovedadVacaciones n WHERE n.idNovedadVacaciones = :idNovedadVacaciones"),
    @NamedQuery(name = "NovedadVacaciones.findByCcColaborador", query = "SELECT n FROM NovedadVacaciones n WHERE n.ccColaborador = :ccColaborador"),
    @NamedQuery(name = "NovedadVacaciones.findByFechaInicio", query = "SELECT n FROM NovedadVacaciones n WHERE n.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "NovedadVacaciones.findByFechaFin", query = "SELECT n FROM NovedadVacaciones n WHERE n.fechaFin = :fechaFin"),
    @NamedQuery(name = "NovedadVacaciones.findByUsername", query = "SELECT n FROM NovedadVacaciones n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadVacaciones.findByCreado", query = "SELECT n FROM NovedadVacaciones n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadVacaciones.findByModificado", query = "SELECT n FROM NovedadVacaciones n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadVacaciones.findByEstadoReg", query = "SELECT n FROM NovedadVacaciones n WHERE n.estadoReg = :estadoReg")})
public class NovedadVacaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_vacaciones")
    private Integer idNovedadVacaciones;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "cc_colaborador")
    private String ccColaborador;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;

    public NovedadVacaciones() {
    }

    public NovedadVacaciones(Integer idNovedadVacaciones) {
        this.idNovedadVacaciones = idNovedadVacaciones;
    }

    public NovedadVacaciones(Integer idNovedadVacaciones, String ccColaborador, String username, Date creado, int estadoReg) {
        this.idNovedadVacaciones = idNovedadVacaciones;
        this.ccColaborador = ccColaborador;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadVacaciones() {
        return idNovedadVacaciones;
    }

    public void setIdNovedadVacaciones(Integer idNovedadVacaciones) {
        this.idNovedadVacaciones = idNovedadVacaciones;
    }

    public String getCcColaborador() {
        return ccColaborador;
    }

    public void setCcColaborador(String ccColaborador) {
        this.ccColaborador = ccColaborador;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
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
        hash += (idNovedadVacaciones != null ? idNovedadVacaciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadVacaciones)) {
            return false;
        }
        NovedadVacaciones other = (NovedadVacaciones) object;
        if ((this.idNovedadVacaciones == null && other.idNovedadVacaciones != null) || (this.idNovedadVacaciones != null && !this.idNovedadVacaciones.equals(other.idNovedadVacaciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadVacaciones[ idNovedadVacaciones=" + idNovedadVacaciones + " ]";
    }
    
}
