/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "generica_pm_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmGrupo.findAll", query = "SELECT g FROM GenericaPmGrupo g"),
    @NamedQuery(name = "GenericaPmGrupo.findByIdGenericaPmGrupo", query = "SELECT g FROM GenericaPmGrupo g WHERE g.idGenericaPmGrupo = :idGenericaPmGrupo"),
    @NamedQuery(name = "GenericaPmGrupo.findByNombreGrupo", query = "SELECT g FROM GenericaPmGrupo g WHERE g.nombreGrupo = :nombreGrupo"),
    @NamedQuery(name = "GenericaPmGrupo.findByDescripcion", query = "SELECT g FROM GenericaPmGrupo g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GenericaPmGrupo.findByUsername", query = "SELECT g FROM GenericaPmGrupo g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaPmGrupo.findByCreado", query = "SELECT g FROM GenericaPmGrupo g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPmGrupo.findByModificado", query = "SELECT g FROM GenericaPmGrupo g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPmGrupo.findByEstadoReg", query = "SELECT g FROM GenericaPmGrupo g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmGrupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pm_grupo")
    private Integer idGenericaPmGrupo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_grupo")
    private String nombreGrupo;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenericaPmGrupo", fetch = FetchType.LAZY)
    private List<GenericaPmGrupoDetalle> genericaPmGrupoDetalleList;

    public GenericaPmGrupo() {
    }

    public GenericaPmGrupo(Integer idGenericaPmGrupo) {
        this.idGenericaPmGrupo = idGenericaPmGrupo;
    }

    public GenericaPmGrupo(Integer idGenericaPmGrupo, String nombreGrupo, String username, Date creado, int estadoReg) {
        this.idGenericaPmGrupo = idGenericaPmGrupo;
        this.nombreGrupo = nombreGrupo;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaPmGrupo() {
        return idGenericaPmGrupo;
    }

    public void setIdGenericaPmGrupo(Integer idGenericaPmGrupo) {
        this.idGenericaPmGrupo = idGenericaPmGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @XmlTransient
    public List<GenericaPmGrupoDetalle> getGenericaPmGrupoDetalleList() {
        return genericaPmGrupoDetalleList;
    }

    public void setGenericaPmGrupoDetalleList(List<GenericaPmGrupoDetalle> genericaPmGrupoDetalleList) {
        this.genericaPmGrupoDetalleList = genericaPmGrupoDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaPmGrupo != null ? idGenericaPmGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmGrupo)) {
            return false;
        }
        GenericaPmGrupo other = (GenericaPmGrupo) object;
        if ((this.idGenericaPmGrupo == null && other.idGenericaPmGrupo != null) || (this.idGenericaPmGrupo != null && !this.idGenericaPmGrupo.equals(other.idGenericaPmGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmGrupo[ idGenericaPmGrupo=" + idGenericaPmGrupo + " ]";
    }
    
}
