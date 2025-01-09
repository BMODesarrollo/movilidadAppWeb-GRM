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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "hallazgos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hallazgo.findAll", query = "SELECT h FROM Hallazgo h"),
    @NamedQuery(name = "Hallazgo.findByIdHallazgo", query = "SELECT h FROM Hallazgo h WHERE h.idHallazgo = :idHallazgo"),
    @NamedQuery(name = "Hallazgo.findByConsecutivo", query = "SELECT h FROM Hallazgo h WHERE h.consecutivo = :consecutivo"),
    @NamedQuery(name = "Hallazgo.findByFechaIdentificacion", query = "SELECT h FROM Hallazgo h WHERE h.fechaIdentificacion = :fechaIdentificacion"),
    @NamedQuery(name = "Hallazgo.findByHora", query = "SELECT h FROM Hallazgo h WHERE h.hora = :hora"),
    @NamedQuery(name = "Hallazgo.findByZona", query = "SELECT h FROM Hallazgo h WHERE h.zona = :zona"),
    @NamedQuery(name = "Hallazgo.findByCodigoInfraccion", query = "SELECT h FROM Hallazgo h WHERE h.codigoInfraccion = :codigoInfraccion"),
    @NamedQuery(name = "Hallazgo.findByEstado", query = "SELECT h FROM Hallazgo h WHERE h.estado = :estado"),
    @NamedQuery(name = "Hallazgo.findByFechaContestacion", query = "SELECT h FROM Hallazgo h WHERE h.fechaContestacion = :fechaContestacion"),
    @NamedQuery(name = "Hallazgo.findByUsername", query = "SELECT h FROM Hallazgo h WHERE h.username = :username"),
    @NamedQuery(name = "Hallazgo.findByCreado", query = "SELECT h FROM Hallazgo h WHERE h.creado = :creado"),
    @NamedQuery(name = "Hallazgo.findByModificado", query = "SELECT h FROM Hallazgo h WHERE h.modificado = :modificado"),
    @NamedQuery(name = "Hallazgo.findByEstadoReg", query = "SELECT h FROM Hallazgo h WHERE h.estadoReg = :estadoReg")})
public class Hallazgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_hallazgo")
    private Integer idHallazgo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "consecutivo")
    private int consecutivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_identificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "hora")
    private String hora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "zona")
    private String zona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "codigo_infraccion")
    private String codigoInfraccion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_contestacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaContestacion;
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
    @ManyToOne
    private Empleado idEmpleado;
    @JoinColumn(name = "id_hallazgos_param_area", referencedColumnName = "id_hallazgos_param_area")
    @ManyToOne
    private HallazgosParamArea idHallazgoParamArea;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false)
    private Vehiculo idVehiculo;

    public Hallazgo() {
    }

    public Hallazgo(Integer idHallazgo) {
        this.idHallazgo = idHallazgo;
    }

    public Hallazgo(Integer idHallazgo, int consecutivo, Date fechaIdentificacion, String hora, String zona, String codigoInfraccion, String descripcion, String estado, Date fechaContestacion) {
        this.idHallazgo = idHallazgo;
        this.consecutivo = consecutivo;
        this.fechaIdentificacion = fechaIdentificacion;
        this.hora = hora;
        this.zona = zona;
        this.codigoInfraccion = codigoInfraccion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaContestacion = fechaContestacion;
    }

    public Integer getIdHallazgo() {
        return idHallazgo;
    }

    public void setIdHallazgo(Integer idHallazgo) {
        this.idHallazgo = idHallazgo;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Date getFechaIdentificacion() {
        return fechaIdentificacion;
    }

    public void setFechaIdentificacion(Date fechaIdentificacion) {
        this.fechaIdentificacion = fechaIdentificacion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCodigoInfraccion() {
        return codigoInfraccion;
    }

    public void setCodigoInfraccion(String codigoInfraccion) {
        this.codigoInfraccion = codigoInfraccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaContestacion() {
        return fechaContestacion;
    }

    public void setFechaContestacion(Date fechaContestacion) {
        this.fechaContestacion = fechaContestacion;
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

    public HallazgosParamArea getIdHallazgoParamArea() {
        return idHallazgoParamArea;
    }

    public void setIdHallazgoParamArea(HallazgosParamArea idHallazgoParamArea) {
        this.idHallazgoParamArea = idHallazgoParamArea;
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
        hash += (idHallazgo != null ? idHallazgo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hallazgo)) {
            return false;
        }
        Hallazgo other = (Hallazgo) object;
        if ((this.idHallazgo == null && other.idHallazgo != null) || (this.idHallazgo != null && !this.idHallazgo.equals(other.idHallazgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Hallazgo[ idHallazgo=" + idHallazgo + " ]";
    }
    
}
