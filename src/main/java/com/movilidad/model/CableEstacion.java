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
import javax.persistence.Lob;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "cable_estacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableEstacion.findAll", query = "SELECT c FROM CableEstacion c"),
    @NamedQuery(name = "CableEstacion.findByIdCableEstacion", query = "SELECT c FROM CableEstacion c WHERE c.idCableEstacion = :idCableEstacion"),
    @NamedQuery(name = "CableEstacion.findByIdEstacion", query = "SELECT c FROM CableEstacion c WHERE c.idCableEstacion = :idCableEstacion"),
    @NamedQuery(name = "CableEstacion.findByCodigo", query = "SELECT c FROM CableEstacion c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CableEstacion.findByNombre", query = "SELECT c FROM CableEstacion c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CableEstacion.findByUsername", query = "SELECT c FROM CableEstacion c WHERE c.username = :username"),
    @NamedQuery(name = "CableEstacion.findByCreado", query = "SELECT c FROM CableEstacion c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableEstacion.findByModificado", query = "SELECT c FROM CableEstacion c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableEstacion.findByEstadoReg", query = "SELECT c FROM CableEstacion c WHERE c.estadoReg = :estadoReg")})
public class CableEstacion implements Serializable {

    @OneToMany(mappedBy = "idEstacion", fetch = FetchType.LAZY)
    private List<ActividadInfraDiaria> actividadInfraDiariaList;
    @OneToMany(mappedBy = "idCableEstacion", fetch = FetchType.LAZY)
    private List<NovedadMtto> novedadMttoList;
    @OneToMany(mappedBy = "idCableEstacion", fetch = FetchType.LAZY)
    private List<CableRevisionDia> cableRevisionDiaList;
    @OneToMany(mappedBy = "idCableEstacion", fetch = FetchType.LAZY)
    private List<CableRevisionEstacion> cableRevisionEstacionList;

    @OneToMany(mappedBy = "idEstacion", fetch = FetchType.LAZY)
    private List<NovedadMttoDiaria> novedadMttoDiariaList;
    @OneToMany(mappedBy = "idCableEstacion", fetch = FetchType.LAZY)
    private List<CableAccidentalidad> cableAccidentalidadList;
    private List<CableNovedadOp> cableNovedadOpList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCableEstacion", fetch = FetchType.LAZY)
    private List<AseoParamArea> aseoParamAreaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCableEstacion", fetch = FetchType.LAZY)
    private List<CableUbicacion> cableUbicacionList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_estacion")
    private Integer idCableEstacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 65535)
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

    public CableEstacion() {
    }

    public CableEstacion(Integer idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    public CableEstacion(Integer idEstacion, String codigo, String nombre, String username, Date creado, int estadoReg) {
        this.idCableEstacion = idEstacion;
        this.codigo = codigo;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableEstacion() {
        return idCableEstacion;
    }

    public void setIdCableEstacion(Integer idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    @XmlTransient
    public List<AseoParamArea> getAseoParamAreaList() {
        return aseoParamAreaList;
    }

    public void setAseoParamAreaList(List<AseoParamArea> aseoParamAreaList) {
        this.aseoParamAreaList = aseoParamAreaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableEstacion != null ? idCableEstacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableEstacion)) {
            return false;
        }
        CableEstacion other = (CableEstacion) object;
        if ((this.idCableEstacion == null && other.idCableEstacion != null) || (this.idCableEstacion != null && !this.idCableEstacion.equals(other.idCableEstacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableEstacion[ idCableEstacion=" + idCableEstacion + " ]";
    }

    @XmlTransient
    public List<CableUbicacion> getCableUbicacionList() {
        return cableUbicacionList;
    }

    public void setCableUbicacionList(List<CableUbicacion> cableUbicacionList) {
        this.cableUbicacionList = cableUbicacionList;
    }

    @XmlTransient
    public List<NovedadMttoDiaria> getNovedadMttoDiariaList() {
        return novedadMttoDiariaList;
    }

    public void setNovedadMttoDiariaList(List<NovedadMttoDiaria> novedadMttoDiariaList) {
        this.novedadMttoDiariaList = novedadMttoDiariaList;
    }

    @XmlTransient
    public List<CableRevisionEstacion> getCableRevisionEstacionList() {
        return cableRevisionEstacionList;
    }

    public void setCableRevisionEstacionList(List<CableRevisionEstacion> cableRevisionEstacionList) {
        this.cableRevisionEstacionList = cableRevisionEstacionList;
    }

    @XmlTransient
    public List<CableRevisionDia> getCableRevisionDiaList() {
        return cableRevisionDiaList;
    }

    public void setCableRevisionDiaList(List<CableRevisionDia> cableRevisionDiaList) {
        this.cableRevisionDiaList = cableRevisionDiaList;
    }
    
    @XmlTransient
    public List<CableAccidentalidad> getCableAccidentalidadList() {
        return cableAccidentalidadList;
    }

    public void setCableAccidentalidadList(List<CableAccidentalidad> cableAccidentalidadList) {
        this.cableAccidentalidadList = cableAccidentalidadList;
    }
    
    public List<CableNovedadOp> getCableNovedadOpList() {
        return cableNovedadOpList;
    }

    public void setCableNovedadOpList(List<CableNovedadOp> cableNovedadOpList) {
        this.cableNovedadOpList = cableNovedadOpList;
    }

    @XmlTransient
    public List<ActividadInfraDiaria> getActividadInfraDiariaList() {
        return actividadInfraDiariaList;
    }

    public void setActividadInfraDiariaList(List<ActividadInfraDiaria> actividadInfraDiariaList) {
        this.actividadInfraDiariaList = actividadInfraDiariaList; 
    }
    
    @XmlTransient
    public List<NovedadMtto> getNovedadMttoList() {
        return novedadMttoList;
    }

    public void setNovedadMttoList(List<NovedadMtto> novedadMttoList) {
        this.novedadMttoList = novedadMttoList;
    }

}
