/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "acc_novedad_infraestruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccNovedadInfraestruc.findAll", query = "SELECT a FROM AccNovedadInfraestruc a"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByIdAccNovedadInfraestruc", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.idAccNovedadInfraestruc = :idAccNovedadInfraestruc"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByFechaHoraReporte", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.fechaHoraReporte = :fechaHoraReporte"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByFechaHoraCierre", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.fechaHoraCierre = :fechaHoraCierre"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByLatitud", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.latitud = :latitud"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByLongitud", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.longitud = :longitud"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByUsername", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.username = :username"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByCreado", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByModificado", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccNovedadInfraestruc.findByEstadoReg", query = "SELECT a FROM AccNovedadInfraestruc a WHERE a.estadoReg = :estadoReg")})
public class AccNovedadInfraestruc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_novedad_infraestruc")
    private Integer idAccNovedadInfraestruc;
    @Column(name = "fecha_hora_reporte")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraReporte;
    @Column(name = "fecha_hora_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCierre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitud")
    private BigDecimal latitud;
    @Column(name = "longitud")
    private BigDecimal longitud;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccNovedadInfraestruc", fetch = FetchType.LAZY)
    private List<AccNovedadInfraestrucDocumentos> accNovedadInfraestrucDocumentosList;
    @JoinColumn(name = "id_acc_novedad_infrastuc_estado", referencedColumnName = "id_acc_novedad_infrastuc_estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccNovedadInfrastucEstado idAccNovedadInfrastucEstado;
    @JoinColumn(name = "id_acc_novedad_tipo_detalle_infrastruc", referencedColumnName = "id_acc_novedad_tipo_detalle_infrastruc")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccNovedadTipoDetallesInfrastruc idAccNovedadTipoDetalleInfrastruc;
    @JoinColumn(name = "id_acc_novedad_tipo_infrastruc", referencedColumnName = "id_acc_novedad_tipo_infrastruc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AccNovedadTipoInfrastruc idAccNovedadTipoInfrastruc;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccNovedadInfraestruc", fetch = FetchType.LAZY)
    private List<AccNovedadInfraestrucSeguimiento> accNovedadInfraestrucSeguimientoList;

    public AccNovedadInfraestruc() {
    }

    public AccNovedadInfraestruc(Integer idAccNovedadInfraestruc) {
        this.idAccNovedadInfraestruc = idAccNovedadInfraestruc;
    }

    public AccNovedadInfraestruc(Integer idAccNovedadInfraestruc, String descripcion, String username, Date creado, int estadoReg) {
        this.idAccNovedadInfraestruc = idAccNovedadInfraestruc;
        this.descripcion = descripcion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAccNovedadInfraestruc() {
        return idAccNovedadInfraestruc;
    }

    public void setIdAccNovedadInfraestruc(Integer idAccNovedadInfraestruc) {
        this.idAccNovedadInfraestruc = idAccNovedadInfraestruc;
    }

    public Date getFechaHoraReporte() {
        return fechaHoraReporte;
    }

    public void setFechaHoraReporte(Date fechaHoraReporte) {
        this.fechaHoraReporte = fechaHoraReporte;
    }

    public Date getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(Date fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
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
    public List<AccNovedadInfraestrucDocumentos> getAccNovedadInfraestrucDocumentosList() {
        return accNovedadInfraestrucDocumentosList;
    }

    public void setAccNovedadInfraestrucDocumentosList(List<AccNovedadInfraestrucDocumentos> accNovedadInfraestrucDocumentosList) {
        this.accNovedadInfraestrucDocumentosList = accNovedadInfraestrucDocumentosList;
    }

    public AccNovedadInfrastucEstado getIdAccNovedadInfrastucEstado() {
        return idAccNovedadInfrastucEstado;
    }

    public void setIdAccNovedadInfrastucEstado(AccNovedadInfrastucEstado idAccNovedadInfrastucEstado) {
        this.idAccNovedadInfrastucEstado = idAccNovedadInfrastucEstado;
    }

    public AccNovedadTipoDetallesInfrastruc getIdAccNovedadTipoDetalleInfrastruc() {
        return idAccNovedadTipoDetalleInfrastruc;
    }

    public void setIdAccNovedadTipoDetalleInfrastruc(AccNovedadTipoDetallesInfrastruc idAccNovedadTipoDetalleInfrastruc) {
        this.idAccNovedadTipoDetalleInfrastruc = idAccNovedadTipoDetalleInfrastruc;
    }

    public AccNovedadTipoInfrastruc getIdAccNovedadTipoInfrastruc() {
        return idAccNovedadTipoInfrastruc;
    }

    public void setIdAccNovedadTipoInfrastruc(AccNovedadTipoInfrastruc idAccNovedadTipoInfrastruc) {
        this.idAccNovedadTipoInfrastruc = idAccNovedadTipoInfrastruc;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @XmlTransient
    public List<AccNovedadInfraestrucSeguimiento> getAccNovedadInfraestrucSeguimientoList() {
        return accNovedadInfraestrucSeguimientoList;
    }

    public void setAccNovedadInfraestrucSeguimientoList(List<AccNovedadInfraestrucSeguimiento> accNovedadInfraestrucSeguimientoList) {
        this.accNovedadInfraestrucSeguimientoList = accNovedadInfraestrucSeguimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccNovedadInfraestruc != null ? idAccNovedadInfraestruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccNovedadInfraestruc)) {
            return false;
        }
        AccNovedadInfraestruc other = (AccNovedadInfraestruc) object;
        if ((this.idAccNovedadInfraestruc == null && other.idAccNovedadInfraestruc != null) || (this.idAccNovedadInfraestruc != null && !this.idAccNovedadInfraestruc.equals(other.idAccNovedadInfraestruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccNovedadInfraestruc[ idAccNovedadInfraestruc=" + idAccNovedadInfraestruc + " ]";
    }

}
