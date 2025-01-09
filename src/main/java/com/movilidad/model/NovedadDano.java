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
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "novedad_dano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadDano.findAll", query = "SELECT n FROM NovedadDano n"),
    @NamedQuery(name = "NovedadDano.findByIdNovedadDano", query = "SELECT n FROM NovedadDano n WHERE n.idNovedadDano = :idNovedadDano"),
    @NamedQuery(name = "NovedadDano.findByFecha", query = "SELECT n FROM NovedadDano n WHERE n.fecha = :fecha"),
    @NamedQuery(name = "NovedadDano.findByPathFotos", query = "SELECT n FROM NovedadDano n WHERE n.pathFotos = :pathFotos"),
    @NamedQuery(name = "NovedadDano.findByUsername", query = "SELECT n FROM NovedadDano n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadDano.findByCreado", query = "SELECT n FROM NovedadDano n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadDano.findByModificado", query = "SELECT n FROM NovedadDano n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadDano.findByEstadoReg", query = "SELECT n FROM NovedadDano n WHERE n.estadoReg = :estadoReg")})
public class NovedadDano implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadDano", fetch = FetchType.LAZY)
    private List<NovedadDanoLiqDet> novedadDanoLiqDetList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_dano")
    private Integer idNovedadDano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 150)
    @Column(name = "path_fotos")
    private String pathFotos;
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
    @Column(name = "version")
    private Integer version;
    @OneToMany(mappedBy = "idNovedadDano", fetch = FetchType.LAZY)
    private List<Novedad> novedadList;
    @JoinColumn(name = "id_vehiculo_componente", referencedColumnName = "id_vehiculo_componente")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoComponente idVehiculoComponente;
    @JoinColumn(name = "id_vehiculo_componente_dano", referencedColumnName = "id_vehiculo_componente_dano")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoComponenteDano idVehiculoComponenteDano;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_vehiculo_dano_severidad", referencedColumnName = "id_vehiculo_dano_severidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoDanoSeveridad idVehiculoDanoSeveridad;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_ruta", referencedColumnName = "id_prg_route")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgRoute idPrgRoute;
    @Transient
    private String componentesAfectados;

    public NovedadDano() {
    }

    public NovedadDano(Integer idNovedadDano) {
        this.idNovedadDano = idNovedadDano;
    }

    public NovedadDano(Integer idNovedadDano, Date fecha, String username, Date creado, int estadoReg) {
        this.idNovedadDano = idNovedadDano;
        this.fecha = fecha;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadDano() {
        return idNovedadDano;
    }

    public void setIdNovedadDano(Integer idNovedadDano) {
        this.idNovedadDano = idNovedadDano;
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

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
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
    public List<Novedad> getNovedadList() {
        return novedadList;
    }

    public void setNovedadList(List<Novedad> novedadList) {
        this.novedadList = novedadList;
    }

    public VehiculoComponente getIdVehiculoComponente() {
        return idVehiculoComponente;
    }

    public void setIdVehiculoComponente(VehiculoComponente idVehiculoComponente) {
        this.idVehiculoComponente = idVehiculoComponente;
    }

    public VehiculoComponenteDano getIdVehiculoComponenteDano() {
        return idVehiculoComponenteDano;
    }

    public void setIdVehiculoComponenteDano(VehiculoComponenteDano idVehiculoComponenteDano) {
        this.idVehiculoComponenteDano = idVehiculoComponenteDano;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public VehiculoDanoSeveridad getIdVehiculoDanoSeveridad() {
        return idVehiculoDanoSeveridad;
    }

    public void setIdVehiculoDanoSeveridad(VehiculoDanoSeveridad idVehiculoDanoSeveridad) {
        this.idVehiculoDanoSeveridad = idVehiculoDanoSeveridad;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
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
        hash += (idNovedadDano != null ? idNovedadDano.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadDano)) {
            return false;
        }
        NovedadDano other = (NovedadDano) object;
        if ((this.idNovedadDano == null && other.idNovedadDano != null) || (this.idNovedadDano != null && !this.idNovedadDano.equals(other.idNovedadDano))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadDano[ idNovedadDano=" + idNovedadDano + " ]";
    }

    @XmlTransient
    public List<NovedadDanoLiqDet> getNovedadDanoLiqDetList() {
        return novedadDanoLiqDetList;
    }

    public void setNovedadDanoLiqDetList(List<NovedadDanoLiqDet> novedadDanoLiqDetList) {
        this.novedadDanoLiqDetList = novedadDanoLiqDetList;
    }

    public PrgRoute getIdPrgRoute() {
        return idPrgRoute;
    }

    public void setIdPrgRoute(PrgRoute idPrgRoute) {
        this.idPrgRoute = idPrgRoute;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getComponentesAfectados() {
        return componentesAfectados;
    }

    public void setComponentesAfectados(String componentesAfectados) {
        this.componentesAfectados = componentesAfectados;
    }
}
