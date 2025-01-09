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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "generica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Generica.findAll", query = "SELECT g FROM Generica g"),
    @NamedQuery(name = "Generica.findByIdGenerica", query = "SELECT g FROM Generica g WHERE g.idGenerica = :idGenerica"),
    @NamedQuery(name = "Generica.findByFecha", query = "SELECT g FROM Generica g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "Generica.findByDesde", query = "SELECT g FROM Generica g WHERE g.desde = :desde"),
    @NamedQuery(name = "Generica.findByHasta", query = "SELECT g FROM Generica g WHERE g.hasta = :hasta"),
    @NamedQuery(name = "Generica.findByHora", query = "SELECT g FROM Generica g WHERE g.hora = :hora"),
    @NamedQuery(name = "Generica.findByPuntosPm", query = "SELECT g FROM Generica g WHERE g.puntosPm = :puntosPm"),
    @NamedQuery(name = "Generica.findByPuntosPmConciliados", query = "SELECT g FROM Generica g WHERE g.puntosPmConciliados = :puntosPmConciliados"),
    @NamedQuery(name = "Generica.findByProcede", query = "SELECT g FROM Generica g WHERE g.procede = :procede"),
    @NamedQuery(name = "Generica.findByLiquidada", query = "SELECT g FROM Generica g WHERE g.liquidada = :liquidada"),
    @NamedQuery(name = "Generica.findByUsername", query = "SELECT g FROM Generica g WHERE g.username = :username"),
    @NamedQuery(name = "Generica.findByCreado", query = "SELECT g FROM Generica g WHERE g.creado = :creado"),
    @NamedQuery(name = "Generica.findByModificado", query = "SELECT g FROM Generica g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "Generica.findByEstadoReg", query = "SELECT g FROM Generica g WHERE g.estadoReg = :estadoReg")})
public class Generica implements Serializable, Comparable<Generica> {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenerica", fetch = FetchType.LAZY)
    private List<GenericaPdMaestroDetalle> genericaPdMaestroDetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenerica")
    private List<GenericaSeguimiento> genericaSeguimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenerica")
    private List<GenericaDocumentos> genericaDocumentosList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica")
    private Integer idGenerica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "puntos_pm")
    private Integer puntosPm;
    @Column(name = "puntos_pm_conciliados")
    private Integer puntosPmConciliados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "procede")
    private int procede;
    @Basic(optional = false)
    @NotNull
    @Column(name = "liquidada")
    private int liquidada;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_generica_tipo_detalle", referencedColumnName = "id_generica_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaTipoDetalles idGenericaTipoDetalle;
    @JoinColumn(name = "id_generica_tipo", referencedColumnName = "id_generica_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenericaTipo idGenericaTipo;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public Generica() {
    }

    public Generica(Integer idGenerica) {
        this.idGenerica = idGenerica;
    }

    public Generica(Integer idGenerica, Date fecha, String observaciones, int procede, int liquidada, String username, Date creado, int estadoReg) {
        this.idGenerica = idGenerica;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.procede = procede;
        this.liquidada = liquidada;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenerica() {
        return idGenerica;
    }

    public void setIdGenerica(Integer idGenerica) {
        this.idGenerica = idGenerica;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getPuntosPm() {
        return puntosPm;
    }

    public void setPuntosPm(Integer puntosPm) {
        this.puntosPm = puntosPm;
    }

    public Integer getPuntosPmConciliados() {
        return puntosPmConciliados;
    }

    public void setPuntosPmConciliados(Integer puntosPmConciliados) {
        this.puntosPmConciliados = puntosPmConciliados;
    }

    public int getProcede() {
        return procede;
    }

    public void setProcede(int procede) {
        this.procede = procede;
    }

    public int getLiquidada() {
        return liquidada;
    }

    public void setLiquidada(int liquidada) {
        this.liquidada = liquidada;
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

    public GenericaTipoDetalles getIdGenericaTipoDetalle() {
        return idGenericaTipoDetalle;
    }

    public void setIdGenericaTipoDetalle(GenericaTipoDetalles idGenericaTipoDetalle) {
        this.idGenericaTipoDetalle = idGenericaTipoDetalle;
    }

    public GenericaTipo getIdGenericaTipo() {
        return idGenericaTipo;
    }

    public void setIdGenericaTipo(GenericaTipo idGenericaTipo) {
        this.idGenericaTipo = idGenericaTipo;
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
        hash += (idGenerica != null ? idGenerica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Generica)) {
            return false;
        }
        Generica other = (Generica) object;
        if ((this.idGenerica == null && other.idGenerica != null) || (this.idGenerica != null && !this.idGenerica.equals(other.idGenerica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Generica[ idGenerica=" + idGenerica + " ]";
    }

    @XmlTransient
    public List<GenericaDocumentos> getGenericaDocumentosList() {
        return genericaDocumentosList;
    }

    public void setGenericaDocumentosList(List<GenericaDocumentos> genericaDocumentosList) {
        this.genericaDocumentosList = genericaDocumentosList;
    }

    @XmlTransient
    public List<GenericaSeguimiento> getGenericaSeguimientoList() {
        return genericaSeguimientoList;
    }

    public void setGenericaSeguimientoList(List<GenericaSeguimiento> genericaSeguimientoList) {
        this.genericaSeguimientoList = genericaSeguimientoList;
    }

    @Override
    public int compareTo(Generica e) {
        return e.getFecha().compareTo(fecha);
    }

    @XmlTransient
    public List<GenericaPdMaestroDetalle> getGenericaPdMaestroDetalleList() {
        return genericaPdMaestroDetalleList;
    }

    public void setGenericaPdMaestroDetalleList(List<GenericaPdMaestroDetalle> genericaPdMaestroDetalleList) {
        this.genericaPdMaestroDetalleList = genericaPdMaestroDetalleList;
    }

}
