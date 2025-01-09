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
@Table(name = "generica_pm_grupo_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmGrupoDetalle.findAll", query = "SELECT g FROM GenericaPmGrupoDetalle g"),
    @NamedQuery(name = "GenericaPmGrupoDetalle.findByIdGenericaPmGrupoDetalle", query = "SELECT g FROM GenericaPmGrupoDetalle g WHERE g.idGenericaPmGrupoDetalle = :idGenericaPmGrupoDetalle"),
    @NamedQuery(name = "GenericaPmGrupoDetalle.findByFechaIngreso", query = "SELECT g FROM GenericaPmGrupoDetalle g WHERE g.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "GenericaPmGrupoDetalle.findByFechaSalida", query = "SELECT g FROM GenericaPmGrupoDetalle g WHERE g.fechaSalida = :fechaSalida"),
    @NamedQuery(name = "GenericaPmGrupoDetalle.findByUsername", query = "SELECT g FROM GenericaPmGrupoDetalle g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaPmGrupoDetalle.findByCreado", query = "SELECT g FROM GenericaPmGrupoDetalle g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPmGrupoDetalle.findByModificado", query = "SELECT g FROM GenericaPmGrupoDetalle g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPmGrupoDetalle.findByEstadoReg", query = "SELECT g FROM GenericaPmGrupoDetalle g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmGrupoDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pm_grupo_detalle")
    private Integer idGenericaPmGrupoDetalle;
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
    @JoinColumn(name = "id_generica_pm_grupo", referencedColumnName = "id_generica_pm_grupo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenericaPmGrupo idGenericaPmGrupo;

    public GenericaPmGrupoDetalle() {
    }

    public GenericaPmGrupoDetalle(Integer idGenericaPmGrupoDetalle) {
        this.idGenericaPmGrupoDetalle = idGenericaPmGrupoDetalle;
    }

    public GenericaPmGrupoDetalle(Integer idGenericaPmGrupoDetalle, Date fechaIngreso, String username, Date creado, int estadoReg) {
        this.idGenericaPmGrupoDetalle = idGenericaPmGrupoDetalle;
        this.fechaIngreso = fechaIngreso;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaPmGrupoDetalle() {
        return idGenericaPmGrupoDetalle;
    }

    public void setIdGenericaPmGrupoDetalle(Integer idGenericaPmGrupoDetalle) {
        this.idGenericaPmGrupoDetalle = idGenericaPmGrupoDetalle;
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

    public GenericaPmGrupo getIdGenericaPmGrupo() {
        return idGenericaPmGrupo;
    }

    public void setIdGenericaPmGrupo(GenericaPmGrupo idGenericaPmGrupo) {
        this.idGenericaPmGrupo = idGenericaPmGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaPmGrupoDetalle != null ? idGenericaPmGrupoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmGrupoDetalle)) {
            return false;
        }
        GenericaPmGrupoDetalle other = (GenericaPmGrupoDetalle) object;
        if ((this.idGenericaPmGrupoDetalle == null && other.idGenericaPmGrupoDetalle != null) || (this.idGenericaPmGrupoDetalle != null && !this.idGenericaPmGrupoDetalle.equals(other.idGenericaPmGrupoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmGrupoDetalle[ idGenericaPmGrupoDetalle=" + idGenericaPmGrupoDetalle + " ]";
    }
    
}
