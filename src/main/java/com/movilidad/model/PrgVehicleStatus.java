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
@Table(name = "prg_vehicle_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgVehicleStatus.findAll", query = "SELECT p FROM PrgVehicleStatus p")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByIdPrgVehicleStatus", query = "SELECT p FROM PrgVehicleStatus p WHERE p.idPrgVehicleStatus = :idPrgVehicleStatus")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByFecha", query = "SELECT p FROM PrgVehicleStatus p WHERE p.fecha = :fecha")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByServbus", query = "SELECT p FROM PrgVehicleStatus p WHERE p.servbus = :servbus")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByExpedicion", query = "SELECT p FROM PrgVehicleStatus p WHERE p.expedicion = :expedicion")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByTimeOrigin", query = "SELECT p FROM PrgVehicleStatus p WHERE p.timeOrigin = :timeOrigin")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByTimeDestiny", query = "SELECT p FROM PrgVehicleStatus p WHERE p.timeDestiny = :timeDestiny")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByComercialTime", query = "SELECT p FROM PrgVehicleStatus p WHERE p.comercialTime = :comercialTime")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByHlpTime", query = "SELECT p FROM PrgVehicleStatus p WHERE p.hlpTime = :hlpTime")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByDeadTime", query = "SELECT p FROM PrgVehicleStatus p WHERE p.deadTime = :deadTime")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByProductionTime", query = "SELECT p FROM PrgVehicleStatus p WHERE p.productionTime = :productionTime")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByComercialDistance", query = "SELECT p FROM PrgVehicleStatus p WHERE p.comercialDistance = :comercialDistance")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByHlpDistance", query = "SELECT p FROM PrgVehicleStatus p WHERE p.hlpDistance = :hlpDistance")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByProductionDistance", query = "SELECT p FROM PrgVehicleStatus p WHERE p.productionDistance = :productionDistance")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByLineas", query = "SELECT p FROM PrgVehicleStatus p WHERE p.lineas = :lineas")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByUsername", query = "SELECT p FROM PrgVehicleStatus p WHERE p.username = :username")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByCreado", query = "SELECT p FROM PrgVehicleStatus p WHERE p.creado = :creado")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByModificado", query = "SELECT p FROM PrgVehicleStatus p WHERE p.modificado = :modificado")
    ,
    @NamedQuery(name = "PrgVehicleStatus.findByEstadoReg", query = "SELECT p FROM PrgVehicleStatus p WHERE p.estadoReg = :estadoReg")})
public class PrgVehicleStatus implements Serializable, Comparable<PrgVehicleStatus> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_vehicle_status")
    private Integer idPrgVehicleStatus;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "servbus")
    private String servbus;
    @Column(name = "expedicion")
    private Integer expedicion;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
    @Size(max = 8)
    @Column(name = "comercial_time")
    private String comercialTime;
    @Size(max = 8)
    @Column(name = "hlp_time")
    private String hlpTime;
    @Size(max = 8)
    @Column(name = "dead_time")
    private String deadTime;
    @Size(max = 8)
    @Column(name = "production_time")
    private String productionTime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "comercial_distance")
    private Double comercialDistance;
    @Column(name = "hlp_distance")
    private Double hlpDistance;
    @Column(name = "production_distance")
    private Double productionDistance;
    @Size(max = 45)
    @Column(name = "lineas")
    private String lineas;
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
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;
    @JoinColumn(name = "id_prg_actividad", referencedColumnName = "id_prg_actividad")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgActividad idPrgActividad;
    @JoinColumn(name = "id_from_depot", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idFromDepot;
    @JoinColumn(name = "id_to_depot", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idToDepot;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgVehicleStatus() {
    }

    public PrgVehicleStatus(Integer idPrgVehicleStatus) {
        this.idPrgVehicleStatus = idPrgVehicleStatus;
    }

    public PrgVehicleStatus(Integer idPrgVehicleStatus, String username, Date creado, int estadoReg) {
        this.idPrgVehicleStatus = idPrgVehicleStatus;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public PrgVehicleStatus(Integer idPrgVehicleStatus, Date fecha, String servbus, String timeOrigin, String timeDestiny, Double productionDistance) {
        this.idPrgVehicleStatus = idPrgVehicleStatus;
        this.fecha = fecha;
        this.servbus = servbus;
        this.timeOrigin = timeOrigin;
        this.timeDestiny = timeDestiny;
        this.productionDistance = productionDistance;
    }

    public PrgVehicleStatus(Integer idPrgVehicleStatus, Date fecha, String servbus, String timeOrigin, String timeDestiny, String productionTime, PrgStopPoint idFromDepot) {
        this.idPrgVehicleStatus = idPrgVehicleStatus;
        this.fecha = fecha;
        this.servbus = servbus;
        this.timeOrigin = timeOrigin;
        this.timeDestiny = timeDestiny;
        this.productionTime = productionTime;
        this.idFromDepot = idFromDepot;
    }

    public PrgVehicleStatus(Integer idPrgVehicleStatus, Date fecha, String servbus, String timeOrigin, String timeDestiny, String productionTime, PrgStopPoint idFromDepot, PrgStopPoint idToDepot) {
        this.idPrgVehicleStatus = idPrgVehicleStatus;
        this.fecha = fecha;
        this.servbus = servbus;
        this.timeOrigin = timeOrigin;
        this.timeDestiny = timeDestiny;
        this.productionTime = productionTime;
        this.idFromDepot = idFromDepot;
        this.idToDepot = idToDepot;
    }

    public Integer getIdPrgVehicleStatus() {
        return idPrgVehicleStatus;
    }

    public void setIdPrgVehicleStatus(Integer idPrgVehicleStatus) {
        this.idPrgVehicleStatus = idPrgVehicleStatus;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public Integer getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(Integer expedicion) {
        this.expedicion = expedicion;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
    }

    public String getComercialTime() {
        return comercialTime;
    }

    public void setComercialTime(String comercialTime) {
        this.comercialTime = comercialTime;
    }

    public String getHlpTime() {
        return hlpTime;
    }

    public void setHlpTime(String hlpTime) {
        this.hlpTime = hlpTime;
    }

    public String getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }

    public String getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(String productionTime) {
        this.productionTime = productionTime;
    }

    public Double getComercialDistance() {
        return comercialDistance;
    }

    public void setComercialDistance(Double comercialDistance) {
        this.comercialDistance = comercialDistance;
    }

    public Double getHlpDistance() {
        return hlpDistance;
    }

    public void setHlpDistance(Double hlpDistance) {
        this.hlpDistance = hlpDistance;
    }

    public Double getProductionDistance() {
        return productionDistance;
    }

    public void setProductionDistance(Double productionDistance) {
        this.productionDistance = productionDistance;
    }

    public String getLineas() {
        return lineas;
    }

    public void setLineas(String lineas) {
        this.lineas = lineas;
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

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public PrgActividad getIdPrgActividad() {
        return idPrgActividad;
    }

    public void setIdPrgActividad(PrgActividad idPrgActividad) {
        this.idPrgActividad = idPrgActividad;
    }

    public PrgStopPoint getIdFromDepot() {
        return idFromDepot;
    }

    public void setIdFromDepot(PrgStopPoint idFromDepot) {
        this.idFromDepot = idFromDepot;
    }

    public PrgStopPoint getIdToDepot() {
        return idToDepot;
    }

    public void setIdToDepot(PrgStopPoint idToDepot) {
        this.idToDepot = idToDepot;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgVehicleStatus != null ? idPrgVehicleStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgVehicleStatus)) {
            return false;
        }
        PrgVehicleStatus other = (PrgVehicleStatus) object;
        if ((this.idPrgVehicleStatus == null && other.idPrgVehicleStatus != null) || (this.idPrgVehicleStatus != null && !this.idPrgVehicleStatus.equals(other.idPrgVehicleStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgVehicleStatus[ idPrgVehicleStatus=" + idPrgVehicleStatus + " ]";
    }

    @Override
    public int compareTo(PrgVehicleStatus e) {
        return e.getProductionDistance().compareTo(productionDistance);
    }
}
