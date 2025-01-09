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
 * @author solucionesit
 */
@Entity
@Table(name = "novedad_prg_tc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadPrgTc.findAll", query = "SELECT n FROM NovedadPrgTc n"),
    @NamedQuery(name = "NovedadPrgTc.findById", query = "SELECT n FROM NovedadPrgTc n WHERE n.id = :id"),
    @NamedQuery(name = "NovedadPrgTc.findByUsername", query = "SELECT n FROM NovedadPrgTc n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadPrgTc.findByCreado", query = "SELECT n FROM NovedadPrgTc n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadPrgTc.findByModificado", query = "SELECT n FROM NovedadPrgTc n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadPrgTc.findByEstadoReg", query = "SELECT n FROM NovedadPrgTc n WHERE n.estadoReg = :estadoReg")})
public class NovedadPrgTc implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distancia")
    private BigDecimal distancia;
    @Column(name = "estado_operacion")
    private Integer estadoOperacion;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 3)
    @Column(name = "control")
    private String control;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
    @JoinColumn(name = "from_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint fromStop;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_old_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idOldEmpleado;
    @JoinColumn(name = "id_old_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idOldVehiculo;
    @JoinColumn(name = "id_prg_tc_responsable", referencedColumnName = "id_prg_tc_responsable")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTcResponsable idPrgTcResponsable;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @JoinColumn(name = "to_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint toStop;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Novedad idNovedad;
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PrgTc idPrgTc;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public NovedadPrgTc() {
    }

    public NovedadPrgTc(Integer id) {
        this.id = id;
    }

    public NovedadPrgTc(Integer id, String username, Date creado, int estadoReg) {
        this.id = id;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public NovedadPrgTc(BigDecimal distancia, Integer estadoOperacion, String observaciones, String timeOrigin, String control, String timeDestiny, PrgStopPoint fromStop, Empleado idEmpleado, Empleado idOldEmpleado, Vehiculo idOldVehiculo, PrgTcResponsable idPrgTcResponsable, Vehiculo idVehiculo, PrgStopPoint toStop, Integer id, String username, Date creado, Date modificado, int estadoReg, Novedad idNovedad, PrgTc idPrgTc) {
        this.distancia = distancia;
        this.estadoOperacion = estadoOperacion;
        this.observaciones = observaciones;
        this.timeOrigin = timeOrigin;
        this.control = control;
        this.timeDestiny = timeDestiny;
        this.fromStop = fromStop;
        this.idEmpleado = idEmpleado;
        this.idOldEmpleado = idOldEmpleado;
        this.idOldVehiculo = idOldVehiculo;
        this.idPrgTcResponsable = idPrgTcResponsable;
        this.idVehiculo = idVehiculo;
        this.toStop = toStop;
        this.id = id;
        this.username = username;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.idNovedad = idNovedad;
        this.idPrgTc = idPrgTc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public PrgTc getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(PrgTc idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadPrgTc)) {
            return false;
        }
        NovedadPrgTc other = (NovedadPrgTc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadPrgTc[ id=" + id + " ]";
    }

    public BigDecimal getDistancia() {
        return distancia;
    }

    public void setDistancia(BigDecimal distancia) {
        this.distancia = distancia;
    }

    public Integer getEstadoOperacion() {
        return estadoOperacion;
    }

    public void setEstadoOperacion(Integer estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public PrgStopPoint getFromStop() {
        return fromStop;
    }

    public void setFromStop(PrgStopPoint fromStop) {
        this.fromStop = fromStop;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleado getIdOldEmpleado() {
        return idOldEmpleado;
    }

    public void setIdOldEmpleado(Empleado idOldEmpleado) {
        this.idOldEmpleado = idOldEmpleado;
    }

    public Vehiculo getIdOldVehiculo() {
        return idOldVehiculo;
    }

    public void setIdOldVehiculo(Vehiculo idOldVehiculo) {
        this.idOldVehiculo = idOldVehiculo;
    }

    public PrgTcResponsable getIdPrgTcResponsable() {
        return idPrgTcResponsable;
    }

    public void setIdPrgTcResponsable(PrgTcResponsable idPrgTcResponsable) {
        this.idPrgTcResponsable = idPrgTcResponsable;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public PrgStopPoint getToStop() {
        return toStop;
    }

    public void setToStop(PrgStopPoint toStop) {
        this.toStop = toStop;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

}
