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
@Table(name = "auditoria_lugar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaLugar.findAll", query = "SELECT a FROM AuditoriaLugar a")
    ,
    @NamedQuery(name = "AuditoriaLugar.findByIdAuditoriaLugar", query = "SELECT a FROM AuditoriaLugar a WHERE a.idAuditoriaLugar = :idAuditoriaLugar")
    ,
    @NamedQuery(name = "AuditoriaLugar.findByIdParamArea", query = "SELECT a FROM AuditoriaLugar a WHERE a.idParamArea = :idParamArea")
    ,
    @NamedQuery(name = "AuditoriaLugar.findByBus", query = "SELECT a FROM AuditoriaLugar a WHERE a.bus = :bus")
    ,
    @NamedQuery(name = "AuditoriaLugar.findByEstacion", query = "SELECT a FROM AuditoriaLugar a WHERE a.estacion = :estacion")
    ,
    @NamedQuery(name = "AuditoriaLugar.findByAreaComun", query = "SELECT a FROM AuditoriaLugar a WHERE a.areaComun = :areaComun")
    ,
    @NamedQuery(name = "AuditoriaLugar.findByCreado", query = "SELECT a FROM AuditoriaLugar a WHERE a.creado = :creado")
    ,
    @NamedQuery(name = "AuditoriaLugar.findByModificado", query = "SELECT a FROM AuditoriaLugar a WHERE a.modificado = :modificado")
    ,
    @NamedQuery(name = "AuditoriaLugar.findByEstadoReg", query = "SELECT a FROM AuditoriaLugar a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaLugar implements Serializable {

    @Column(name = "bus")
    private boolean bus;
    @Column(name = "estacion")
    private boolean estacion;
    @Column(name = "area_comun")
    private boolean areaComun;
    @Column(name = "empleado")
    private boolean empleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoriaLugar", fetch = FetchType.LAZY)
    private List<AuditoriaEncabezado> auditoriaEncabezadoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_lugar")
    private Integer idAuditoriaLugar;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;

    public AuditoriaLugar() {
    }

    public AuditoriaLugar(Integer idAuditoriaLugar) {
        this.idAuditoriaLugar = idAuditoriaLugar;
    }

    public AuditoriaLugar(Integer idAuditoriaLugar, ParamArea idParamArea, Date creado, int estadoReg) {
        this.idAuditoriaLugar = idAuditoriaLugar;
        this.idParamArea = idParamArea;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaLugar() {
        return idAuditoriaLugar;
    }

    public void setIdAuditoriaLugar(Integer idAuditoriaLugar) {
        this.idAuditoriaLugar = idAuditoriaLugar;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaLugar != null ? idAuditoriaLugar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaLugar)) {
            return false;
        }
        AuditoriaLugar other = (AuditoriaLugar) object;
        if ((this.idAuditoriaLugar == null && other.idAuditoriaLugar != null) || (this.idAuditoriaLugar != null && !this.idAuditoriaLugar.equals(other.idAuditoriaLugar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaLugar[ idAuditoriaLugar=" + idAuditoriaLugar + " ]";
    }

    @XmlTransient
    public List<AuditoriaEncabezado> getAuditoriaEncabezadoList() {
        return auditoriaEncabezadoList;
    }

    public void setAuditoriaEncabezadoList(List<AuditoriaEncabezado> auditoriaEncabezadoList) {
        this.auditoriaEncabezadoList = auditoriaEncabezadoList;
    }

    public boolean isBus() {
        return bus;
    }

    public void setBus(boolean bus) {
        this.bus = bus;
    }

    public boolean isEstacion() {
        return estacion;
    }

    public void setEstacion(boolean estacion) {
        this.estacion = estacion;
    }

    public boolean isAreaComun() {
        return areaComun;
    }

    public void setAreaComun(boolean areaComun) {
        this.areaComun = areaComun;
    }

    public boolean isEmpleado() {
        return empleado;
    }

    public void setEmpleado(boolean empleado) {
        this.empleado = empleado;
    }

}
