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
 * @author HP
 */
@Entity
@Table(name = "vehiculo_tipo_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoTipoEstado.findAll", query = "SELECT v FROM VehiculoTipoEstado v")
    , @NamedQuery(name = "VehiculoTipoEstado.findByIdVehiculoTipoEstado", query = "SELECT v FROM VehiculoTipoEstado v WHERE v.idVehiculoTipoEstado = :idVehiculoTipoEstado")
    , @NamedQuery(name = "VehiculoTipoEstado.findByNombreTipoEstado", query = "SELECT v FROM VehiculoTipoEstado v WHERE v.nombreTipoEstado = :nombreTipoEstado")
    , @NamedQuery(name = "VehiculoTipoEstado.findByDescripcionTipoEstado", query = "SELECT v FROM VehiculoTipoEstado v WHERE v.descripcionTipoEstado = :descripcionTipoEstado")
    , @NamedQuery(name = "VehiculoTipoEstado.findByRestriccionOperacion", query = "SELECT v FROM VehiculoTipoEstado v WHERE v.restriccionOperacion = :restriccionOperacion")
    , @NamedQuery(name = "VehiculoTipoEstado.findByUsername", query = "SELECT v FROM VehiculoTipoEstado v WHERE v.username = :username")
    , @NamedQuery(name = "VehiculoTipoEstado.findByCreado", query = "SELECT v FROM VehiculoTipoEstado v WHERE v.creado = :creado")
    , @NamedQuery(name = "VehiculoTipoEstado.findByModificado", query = "SELECT v FROM VehiculoTipoEstado v WHERE v.modificado = :modificado")
    , @NamedQuery(name = "VehiculoTipoEstado.findByEstadoReg", query = "SELECT v FROM VehiculoTipoEstado v WHERE v.estadoReg = :estadoReg")})
public class VehiculoTipoEstado implements Serializable {

    @Column(name = "cierra_novedad")
    private int cierraNovedad;

    @OneToMany(mappedBy = "idVehiculoTipoEstado", fetch = FetchType.LAZY)
    private List<DispConciliacionDet> dispConciliacionDetList;

    @OneToMany(mappedBy = "idVehiculoTipoEstado", fetch = FetchType.LAZY)
    private List<VehiculoTipoEstadoDet> vehiculoTipoEstadoDetList;
    @OneToMany(mappedBy = "idVehiculoEstado")
    private List<PrgVehiculoInactivo> prgVehiculoInactivoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_tipo_estado")
    private Integer idVehiculoTipoEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_estado")
    private String nombreTipoEstado;
    @Size(max = 100)
    @Column(name = "descripcion_tipo_estado")
    private String descripcionTipoEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "restriccion_operacion")
    private int restriccionOperacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculoTipoEstado", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList;
    @OneToMany(mappedBy = "idVehiculoTipoEstado", fetch = FetchType.LAZY)
    private List<VehiculoEstadoHistorico> vehiculoEstadoHistoricoList;

    public VehiculoTipoEstado() {
    }

    public VehiculoTipoEstado(Integer idVehiculoTipoEstado) {
        this.idVehiculoTipoEstado = idVehiculoTipoEstado;
    }

    public VehiculoTipoEstado(Integer idVehiculoTipoEstado, String nombreTipoEstado, int restriccionOperacion, String username, int estadoReg) {
        this.idVehiculoTipoEstado = idVehiculoTipoEstado;
        this.nombreTipoEstado = nombreTipoEstado;
        this.restriccionOperacion = restriccionOperacion;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoTipoEstado() {
        return idVehiculoTipoEstado;
    }

    public void setIdVehiculoTipoEstado(Integer idVehiculoTipoEstado) {
        this.idVehiculoTipoEstado = idVehiculoTipoEstado;
    }

    public String getNombreTipoEstado() {
        return nombreTipoEstado;
    }

    public void setNombreTipoEstado(String nombreTipoEstado) {
        this.nombreTipoEstado = nombreTipoEstado;
    }

    public String getDescripcionTipoEstado() {
        return descripcionTipoEstado;
    }

    public void setDescripcionTipoEstado(String descripcionTipoEstado) {
        this.descripcionTipoEstado = descripcionTipoEstado;
    }

    public int getRestriccionOperacion() {
        return restriccionOperacion;
    }

    public void setRestriccionOperacion(int restriccionOperacion) {
        this.restriccionOperacion = restriccionOperacion;
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
    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }

    public int getCierraNovedad() {
        return cierraNovedad;
    }

    public void setCierraNovedad(int cierraNovedad) {
        this.cierraNovedad = cierraNovedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoTipoEstado != null ? idVehiculoTipoEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoTipoEstado)) {
            return false;
        }
        VehiculoTipoEstado other = (VehiculoTipoEstado) object;
        if ((this.idVehiculoTipoEstado == null && other.idVehiculoTipoEstado != null) || (this.idVehiculoTipoEstado != null && !this.idVehiculoTipoEstado.equals(other.idVehiculoTipoEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoTipoEstado[ idVehiculoTipoEstado=" + idVehiculoTipoEstado + " ]";
    }

    @XmlTransient
    public List<PrgVehiculoInactivo> getPrgVehiculoInactivoList() {
        return prgVehiculoInactivoList;
    }

    public void setPrgVehiculoInactivoList(List<PrgVehiculoInactivo> prgVehiculoInactivoList) {
        this.prgVehiculoInactivoList = prgVehiculoInactivoList;
    }

    @XmlTransient
    public List<VehiculoTipoEstadoDet> getVehiculoTipoEstadoDetList() {
        return vehiculoTipoEstadoDetList;
    }

    public void setVehiculoTipoEstadoDetList(List<VehiculoTipoEstadoDet> vehiculoTipoEstadoDetList) {
        this.vehiculoTipoEstadoDetList = vehiculoTipoEstadoDetList;
    }

    @XmlTransient
    public List<DispConciliacionDet> getDispConciliacionDetList() {
        return dispConciliacionDetList;
    }

    public void setDispConciliacionDetList(List<DispConciliacionDet> dispConciliacionDetList) {
        this.dispConciliacionDetList = dispConciliacionDetList;
    }

}
