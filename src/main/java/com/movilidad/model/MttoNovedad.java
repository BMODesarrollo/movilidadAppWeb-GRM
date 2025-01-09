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
import javax.persistence.Lob;
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
 * @author solucionesit
 */
@Entity
@Table(name = "mtto_novedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MttoNovedad.findAll", query = "SELECT m FROM MttoNovedad m"),
    @NamedQuery(name = "MttoNovedad.findByIdMttoNovedad", query = "SELECT m FROM MttoNovedad m WHERE m.idMttoNovedad = :idMttoNovedad"),
    @NamedQuery(name = "MttoNovedad.findByFecha", query = "SELECT m FROM MttoNovedad m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "MttoNovedad.findByCerrado", query = "SELECT m FROM MttoNovedad m WHERE m.cerrado = :cerrado"),
    @NamedQuery(name = "MttoNovedad.findByFechaCierre", query = "SELECT m FROM MttoNovedad m WHERE m.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "MttoNovedad.findByUsername", query = "SELECT m FROM MttoNovedad m WHERE m.username = :username"),
    @NamedQuery(name = "MttoNovedad.findByUserCierra", query = "SELECT m FROM MttoNovedad m WHERE m.userCierra = :userCierra"),
    @NamedQuery(name = "MttoNovedad.findByCreado", query = "SELECT m FROM MttoNovedad m WHERE m.creado = :creado"),
    @NamedQuery(name = "MttoNovedad.findByModificado", query = "SELECT m FROM MttoNovedad m WHERE m.modificado = :modificado"),
    @NamedQuery(name = "MttoNovedad.findByEstadoRegistro", query = "SELECT m FROM MttoNovedad m WHERE m.estadoRegistro = :estadoRegistro")})
public class MttoNovedad implements Serializable {
    @JoinColumn(name = "id_empleado_responsable", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleadoResponsable;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mtto_novedad")
    private Integer idMttoNovedad;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones_cierre")
    private String observacionesCierre;
    @Column(name = "cerrado")
    private Integer cerrado;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Size(max = 15)
    @Column(name = "user_cierra")
    private String userCierra;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_registro")
    private Integer estadoRegistro;
    @JoinColumn(name = "id_mtto_componente_falla", referencedColumnName = "id_mtto_componente_falla")
    @ManyToOne(fetch = FetchType.LAZY)
    private MttoComponenteFalla idMttoComponenteFalla;
    @JoinColumn(name = "id_mtto_componente", referencedColumnName = "id_mtto_componente")
    @ManyToOne(fetch = FetchType.LAZY)
    private MttoComponente idMttoComponente;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_mtto_estado", referencedColumnName = "id_mtto_estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private MttoEstado idMttoEstado;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @Size(max = 15)
    @Column(name = "orden_trabajo")
    private String ordenTrabajo;

    public MttoNovedad() {
    }

    public MttoNovedad(Integer idMttoNovedad) {
        this.idMttoNovedad = idMttoNovedad;
    }

    public Integer getIdMttoNovedad() {
        return idMttoNovedad;
    }

    public void setIdMttoNovedad(Integer idMttoNovedad) {
        this.idMttoNovedad = idMttoNovedad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getCerrado() {
        return cerrado;
    }

    public void setCerrado(Integer cerrado) {
        this.cerrado = cerrado;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserCierra() {
        return userCierra;
    }

    public void setUserCierra(String userCierra) {
        this.userCierra = userCierra;
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

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public MttoComponenteFalla getIdMttoComponenteFalla() {
        return idMttoComponenteFalla;
    }

    public void setIdMttoComponenteFalla(MttoComponenteFalla idMttoComponenteFalla) {
        this.idMttoComponenteFalla = idMttoComponenteFalla;
    }

    public MttoComponente getIdMttoComponente() {
        return idMttoComponente;
    }

    public void setIdMttoComponente(MttoComponente idMttoComponente) {
        this.idMttoComponente = idMttoComponente;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public MttoEstado getIdMttoEstado() {
        return idMttoEstado;
    }

    public void setIdMttoEstado(MttoEstado idMttoEstado) {
        this.idMttoEstado = idMttoEstado;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMttoNovedad != null ? idMttoNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MttoNovedad)) {
            return false;
        }
        MttoNovedad other = (MttoNovedad) object;
        if ((this.idMttoNovedad == null && other.idMttoNovedad != null) || (this.idMttoNovedad != null && !this.idMttoNovedad.equals(other.idMttoNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MttoNovedad[ idMttoNovedad=" + idMttoNovedad + " ]";
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public String getObservacionesCierre() {
        return observacionesCierre;
    }

    public void setObservacionesCierre(String observacionesCierre) {
        this.observacionesCierre = observacionesCierre;
    }

    public Empleado getIdEmpleadoResponsable() {
        return idEmpleadoResponsable;
    }

    public void setIdEmpleadoResponsable(Empleado idEmpleadoResponsable) {
        this.idEmpleadoResponsable = idEmpleadoResponsable;
    }
    
}
