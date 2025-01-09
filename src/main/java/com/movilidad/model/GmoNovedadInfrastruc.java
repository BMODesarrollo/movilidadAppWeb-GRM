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
@Table(name = "gmo_novedad_infrastruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GmoNovedadInfrastruc.findAll", query = "SELECT g FROM GmoNovedadInfrastruc g"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByIdGmoNovedadInfrastruc", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.idGmoNovedadInfrastruc = :idGmoNovedadInfrastruc"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByFechaHoraReg", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.fechaHoraReg = :fechaHoraReg"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByFechaHoraNov", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.fechaHoraNov = :fechaHoraNov"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByDireccion", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.direccion = :direccion"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByLatitud", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.latitud = :latitud"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByLongitud", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.longitud = :longitud"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByEstado", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.estado = :estado"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByUserReporta", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.userReporta = :userReporta"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByObsCierre", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.obsCierre = :obsCierre"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByUsrCierre", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.usrCierre = :usrCierre"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByFechaHoraCierre", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.fechaHoraCierre = :fechaHoraCierre"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByUsername", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.username = :username"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByCreado", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.creado = :creado"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByModificado", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GmoNovedadInfrastruc.findByEstadoReg", query = "SELECT g FROM GmoNovedadInfrastruc g WHERE g.estadoReg = :estadoReg")})
public class GmoNovedadInfrastruc implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGmoNovedadInfrastruc", fetch = FetchType.LAZY)
    private List<GmoNovedadInfrastrucSeguimiento> gmoNovedadInfrastrucSeguimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGmoNovedadInfrastruc", fetch = FetchType.LAZY)
    private List<GmoNovedadDocumentos> gmoNovedadDocumentosList;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "idGmoNovedadInfrastruc", fetch = FetchType.LAZY)
    private List<GmoNovedadInfrastrucRutaAfectada> gmoNovedadInfrastrucRutaAfectadaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gmo_novedad_infrastruc")
    private Integer idGmoNovedadInfrastruc;
    @Column(name = "fecha_hora_reg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraReg;
    @Column(name = "fecha_hora_nov")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraNov;
    @Size(max = 150)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 20)
    @Column(name = "latitud")
    private String latitud;
    @Size(max = 20)
    @Column(name = "longitud")
    private String longitud;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "estado")
    private Integer estado;
    @Size(max = 15)
    @Column(name = "user_reporta")
    private String userReporta;
    @Size(max = 250)
    @Column(name = "obs_cierre")
    private String obsCierre;
    @Size(max = 15)
    @Column(name = "usr_cierre")
    private String usrCierre;
    @Column(name = "fecha_hora_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCierre;
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
    @JoinColumn(name = "id_gmo_novedad_tipo_det_infrastruc", referencedColumnName = "id_gmo_novedad_tipo_det_infrastruc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GmoNovedadTipoDetallesInfrastruc idGmoNovedadTipoDetInfrastruc;
    @JoinColumn(name = "id_gmo_novedad_tipo_infrastruc", referencedColumnName = "id_gmo_novedad_tipo_infrastruc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GmoNovedadTipoInfrastruc idGmoNovedadTipoInfrastruc;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public GmoNovedadInfrastruc() {
    }

    public GmoNovedadInfrastruc(Integer idGmoNovedadInfrastruc) {
        this.idGmoNovedadInfrastruc = idGmoNovedadInfrastruc;
    }

    public GmoNovedadInfrastruc(Integer idGmoNovedadInfrastruc, String username, Date creado, int estadoReg) {
        this.idGmoNovedadInfrastruc = idGmoNovedadInfrastruc;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGmoNovedadInfrastruc() {
        return idGmoNovedadInfrastruc;
    }

    public void setIdGmoNovedadInfrastruc(Integer idGmoNovedadInfrastruc) {
        this.idGmoNovedadInfrastruc = idGmoNovedadInfrastruc;
    }

    public Date getFechaHoraReg() {
        return fechaHoraReg;
    }

    public void setFechaHoraReg(Date fechaHoraReg) {
        this.fechaHoraReg = fechaHoraReg;
    }

    public Date getFechaHoraNov() {
        return fechaHoraNov;
    }

    public void setFechaHoraNov(Date fechaHoraNov) {
        this.fechaHoraNov = fechaHoraNov;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getUserReporta() {
        return userReporta;
    }

    public void setUserReporta(String userReporta) {
        this.userReporta = userReporta;
    }

    public String getObsCierre() {
        return obsCierre;
    }

    public void setObsCierre(String obsCierre) {
        this.obsCierre = obsCierre;
    }

    public String getUsrCierre() {
        return usrCierre;
    }

    public void setUsrCierre(String usrCierre) {
        this.usrCierre = usrCierre;
    }

    public Date getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(Date fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
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

    public GmoNovedadTipoDetallesInfrastruc getIdGmoNovedadTipoDetInfrastruc() {
        return idGmoNovedadTipoDetInfrastruc;
    }

    public void setIdGmoNovedadTipoDetInfrastruc(GmoNovedadTipoDetallesInfrastruc idGmoNovedadTipoDetInfrastruc) {
        this.idGmoNovedadTipoDetInfrastruc = idGmoNovedadTipoDetInfrastruc;
    }

    public GmoNovedadTipoInfrastruc getIdGmoNovedadTipoInfrastruc() {
        return idGmoNovedadTipoInfrastruc;
    }

    public void setIdGmoNovedadTipoInfrastruc(GmoNovedadTipoInfrastruc idGmoNovedadTipoInfrastruc) {
        this.idGmoNovedadTipoInfrastruc = idGmoNovedadTipoInfrastruc;
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
        hash += (idGmoNovedadInfrastruc != null ? idGmoNovedadInfrastruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GmoNovedadInfrastruc)) {
            return false;
        }
        GmoNovedadInfrastruc other = (GmoNovedadInfrastruc) object;
        if ((this.idGmoNovedadInfrastruc == null && other.idGmoNovedadInfrastruc != null) || (this.idGmoNovedadInfrastruc != null && !this.idGmoNovedadInfrastruc.equals(other.idGmoNovedadInfrastruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GmoNovedadInfrastruc[ idGmoNovedadInfrastruc=" + idGmoNovedadInfrastruc + " ]";
    }

    @XmlTransient
    public List<GmoNovedadInfrastrucSeguimiento> getGmoNovedadInfrastrucSeguimientoList() {
        return gmoNovedadInfrastrucSeguimientoList;
    }

    public void setGmoNovedadInfrastrucSeguimientoList(List<GmoNovedadInfrastrucSeguimiento> gmoNovedadInfrastrucSeguimientoList) {
        this.gmoNovedadInfrastrucSeguimientoList = gmoNovedadInfrastrucSeguimientoList;
    }

    @XmlTransient
    public List<GmoNovedadDocumentos> getGmoNovedadDocumentosList() {
        return gmoNovedadDocumentosList;
    }

    public void setGmoNovedadDocumentosList(List<GmoNovedadDocumentos> gmoNovedadDocumentosList) {
        this.gmoNovedadDocumentosList = gmoNovedadDocumentosList;
    }

    @XmlTransient
    public List<GmoNovedadInfrastrucRutaAfectada> getGmoNovedadInfrastrucRutaAfectadaList() {
        return gmoNovedadInfrastrucRutaAfectadaList;
    }

    public void setGmoNovedadInfrastrucRutaAfectadaList(List<GmoNovedadInfrastrucRutaAfectada> gmoNovedadInfrastrucRutaAfectadaList) {
        this.gmoNovedadInfrastrucRutaAfectadaList = gmoNovedadInfrastrucRutaAfectadaList;
    }

}
