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
 * @author HP
 */
@Entity
@Table(name = "pm_grupo_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmGrupoDetalle.findAll", query = "SELECT p FROM PmGrupoDetalle p")
    , @NamedQuery(name = "PmGrupoDetalle.findByIdPmGrupoDetalle", query = "SELECT p FROM PmGrupoDetalle p WHERE p.idPmGrupoDetalle = :idPmGrupoDetalle")
    , @NamedQuery(name = "PmGrupoDetalle.findByFechaIngreso", query = "SELECT p FROM PmGrupoDetalle p WHERE p.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "PmGrupoDetalle.findByFechaSalida", query = "SELECT p FROM PmGrupoDetalle p WHERE p.fechaSalida = :fechaSalida")
    , @NamedQuery(name = "PmGrupoDetalle.findByUsername", query = "SELECT p FROM PmGrupoDetalle p WHERE p.username = :username")
    , @NamedQuery(name = "PmGrupoDetalle.findByCreado", query = "SELECT p FROM PmGrupoDetalle p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmGrupoDetalle.findByModificado", query = "SELECT p FROM PmGrupoDetalle p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmGrupoDetalle.findByEstadoReg", query = "SELECT p FROM PmGrupoDetalle p WHERE p.estadoReg = :estadoReg")})
public class PmGrupoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_grupo_detalle")
    private Integer idPmGrupoDetalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "fecha_salida")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_pm_grupo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PmGrupo idGrupo;

    public PmGrupoDetalle() {
    }

    public PmGrupoDetalle(Integer idPmGrupoDetalle) {
        this.idPmGrupoDetalle = idPmGrupoDetalle;
    }

    public PmGrupoDetalle(Integer idPmGrupoDetalle, Date fechaIngreso, String username, Date creado, int estadoReg) {
        this.idPmGrupoDetalle = idPmGrupoDetalle;
        this.fechaIngreso = fechaIngreso;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPmGrupoDetalle() {
        return idPmGrupoDetalle;
    }

    public void setIdPmGrupoDetalle(Integer idPmGrupoDetalle) {
        this.idPmGrupoDetalle = idPmGrupoDetalle;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
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

    public PmGrupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(PmGrupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmGrupoDetalle != null ? idPmGrupoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmGrupoDetalle)) {
            return false;
        }
        PmGrupoDetalle other = (PmGrupoDetalle) object;
        if ((this.idPmGrupoDetalle == null && other.idPmGrupoDetalle != null) || (this.idPmGrupoDetalle != null && !this.idPmGrupoDetalle.equals(other.idPmGrupoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmGrupoDetalle[ idPmGrupoDetalle=" + idPmGrupoDetalle + " ]";
    }
    
}
