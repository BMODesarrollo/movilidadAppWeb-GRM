/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "cable_novedad_op")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableNovedadOp.findAll", query = "SELECT c FROM CableNovedadOp c")
    ,
    @NamedQuery(name = "CableNovedadOp.findByIdCableNovedadOp", query = "SELECT c FROM CableNovedadOp c WHERE c.idCableNovedadOp = :idCableNovedadOp")
    ,
    @NamedQuery(name = "CableNovedadOp.findByFecha", query = "SELECT c FROM CableNovedadOp c WHERE c.fecha = :fecha")
    ,
    @NamedQuery(name = "CableNovedadOp.findByDescripcion", query = "SELECT c FROM CableNovedadOp c WHERE c.descripcion = :descripcion")
    ,
    @NamedQuery(name = "CableNovedadOp.findByTimeIniParada", query = "SELECT c FROM CableNovedadOp c WHERE c.timeIniParada = :timeIniParada")
    ,
    @NamedQuery(name = "CableNovedadOp.findByTimeFinParada", query = "SELECT c FROM CableNovedadOp c WHERE c.timeFinParada = :timeFinParada")
    ,
    @NamedQuery(name = "CableNovedadOp.findByTimeTotalParada", query = "SELECT c FROM CableNovedadOp c WHERE c.timeTotalParada = :timeTotalParada")
    ,
    @NamedQuery(name = "CableNovedadOp.findByHorometroTotal", query = "SELECT c FROM CableNovedadOp c WHERE c.horometroTotal = :horometroTotal")
    ,
    @NamedQuery(name = "CableNovedadOp.findByTimeOperacionCom", query = "SELECT c FROM CableNovedadOp c WHERE c.timeOperacionCom = :timeOperacionCom")
    ,
    @NamedQuery(name = "CableNovedadOp.findByTimeOperacionSistema", query = "SELECT c FROM CableNovedadOp c WHERE c.timeOperacionSistema = :timeOperacionSistema")
    ,
    @NamedQuery(name = "CableNovedadOp.findByUsername", query = "SELECT c FROM CableNovedadOp c WHERE c.username = :username")
    ,
    @NamedQuery(name = "CableNovedadOp.findByCreado", query = "SELECT c FROM CableNovedadOp c WHERE c.creado = :creado")
    ,
    @NamedQuery(name = "CableNovedadOp.findByModificado", query = "SELECT c FROM CableNovedadOp c WHERE c.modificado = :modificado")
    ,
    @NamedQuery(name = "CableNovedadOp.findByEstadoReg", query = "SELECT c FROM CableNovedadOp c WHERE c.estadoReg = :estadoReg")})
public class CableNovedadOp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_novedad_op")
    private Integer idCableNovedadOp;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "time_ini_parada")
    private String timeIniParada;
    @Size(max = 15)
    @Column(name = "time_fin_parada")
    private String timeFinParada;
    @Size(max = 15)
    @Column(name = "time_total_parada")
    private String timeTotalParada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "horometro_total")
    private BigDecimal horometroTotal;
    @Size(max = 15)
    @Column(name = "time_operacion_com")
    private String timeOperacionCom;
    @Size(max = 15)
    @Column(name = "time_operacion_sistema")
    private String timeOperacionSistema;
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
    @JoinColumn(name = "id_cable_cabina", referencedColumnName = "id_cable_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableCabina idCableCabina;
    @JoinColumn(name = "id_cable_cabina_dos", referencedColumnName = "id_cable_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableCabina idCableCabinaDos;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_cable_evento_tipo", referencedColumnName = "id_cable_evento_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEventoTipo idCableEventoTipo;
    @JoinColumn(name = "id_cable_evento_tipo_det", referencedColumnName = "id_cable_evento_tipo_det")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEventoTipoDet idCableEventoTipoDet;
    @JoinColumn(name = "id_cable_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEstacion idCableEstacion;
    @JoinColumn(name = "id_cable_tramo", referencedColumnName = "id_cable_tramo")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableTramo idCableTramo;

    public CableNovedadOp() {
    }

    public CableNovedadOp(Integer idCableNovedadOp) {
        this.idCableNovedadOp = idCableNovedadOp;
    }

    public CableNovedadOp(Integer idCableNovedadOp, String username, String descripcion, Date creado, int estadoReg) {
        this.idCableNovedadOp = idCableNovedadOp;
        this.descripcion = descripcion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableNovedadOp() {
        return idCableNovedadOp;
    }

    public void setIdCableNovedadOp(Integer idCableNovedadOp) {
        this.idCableNovedadOp = idCableNovedadOp;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTimeIniParada() {
        return timeIniParada;
    }

    public void setTimeIniParada(String timeIniParada) {
        this.timeIniParada = timeIniParada;
    }

    public String getTimeFinParada() {
        return timeFinParada;
    }

    public void setTimeFinParada(String timeFinParada) {
        this.timeFinParada = timeFinParada;
    }

    public String getTimeTotalParada() {
        return timeTotalParada;
    }

    public void setTimeTotalParada(String timeTotalParada) {
        this.timeTotalParada = timeTotalParada;
    }

    public BigDecimal getHorometroTotal() {
        return horometroTotal;
    }

    public void setHorometroTotal(BigDecimal horometroTotal) {
        this.horometroTotal = horometroTotal;
    }

    public String getTimeOperacionCom() {
        return timeOperacionCom;
    }

    public void setTimeOperacionCom(String timeOperacionCom) {
        this.timeOperacionCom = timeOperacionCom;
    }

    public String getTimeOperacionSistema() {
        return timeOperacionSistema;
    }

    public void setTimeOperacionSistema(String timeOperacionSistema) {
        this.timeOperacionSistema = timeOperacionSistema;
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

    public CableCabina getIdCableCabina() {
        return idCableCabina;
    }

    public void setIdCableCabina(CableCabina idCableCabina) {
        this.idCableCabina = idCableCabina;
    }

    public CableCabina getIdCableCabinaDos() {
        return idCableCabinaDos;
    }

    public void setIdCableCabinaDos(CableCabina idCableCabinaDos) {
        this.idCableCabinaDos = idCableCabinaDos;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public CableEventoTipo getIdCableEventoTipo() {
        return idCableEventoTipo;
    }

    public void setIdCableEventoTipo(CableEventoTipo idCableEventoTipo) {
        this.idCableEventoTipo = idCableEventoTipo;
    }

    public CableEventoTipoDet getIdCableEventoTipoDet() {
        return idCableEventoTipoDet;
    }

    public void setIdCableEventoTipoDet(CableEventoTipoDet idCableEventoTipoDet) {
        this.idCableEventoTipoDet = idCableEventoTipoDet;
    }

    public CableEstacion getIdCableEstacion() {
        return idCableEstacion;
    }

    public void setIdCableEstacion(CableEstacion idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    public CableTramo getIdCableTramo() {
        return idCableTramo;
    }

    public void setIdCableTramo(CableTramo idCableTramo) {
        this.idCableTramo = idCableTramo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableNovedadOp != null ? idCableNovedadOp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableNovedadOp)) {
            return false;
        }
        CableNovedadOp other = (CableNovedadOp) object;
        if ((this.idCableNovedadOp == null && other.idCableNovedadOp != null) || (this.idCableNovedadOp != null && !this.idCableNovedadOp.equals(other.idCableNovedadOp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableNovedadOp[ idCableNovedadOp=" + idCableNovedadOp + " ]";
    }

}
