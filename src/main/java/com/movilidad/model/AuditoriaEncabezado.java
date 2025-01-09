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
@Table(name = "auditoria_encabezado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaEncabezado.findAll", query = "SELECT a FROM AuditoriaEncabezado a")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByIdAuditoriaEncabezado", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.idAuditoriaEncabezado = :idAuditoriaEncabezado")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByTitulo", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.titulo = :titulo")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByDescripcion", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.descripcion = :descripcion")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByFechaDesde", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.fechaDesde = :fechaDesde")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByFechaHasta", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.fechaHasta = :fechaHasta")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByActiva", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.activa = :activa")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByUsername", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.username = :username")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByCreado", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.creado = :creado")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByModificado", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.modificado = :modificado")
    ,
    @NamedQuery(name = "AuditoriaEncabezado.findByEstadoReg", query = "SELECT a FROM AuditoriaEncabezado a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaEncabezado implements Serializable {

    @OneToMany(mappedBy = "idAuditoriaEncabezado", fetch = FetchType.LAZY)
    private List<Auditoria> auditoriaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_encabezado")
    private Integer idAuditoriaEncabezado;
    @Size(max = 100)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_desde")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesde;
    @Column(name = "fecha_hasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHasta;
    @Column(name = "activa")
    private Integer activa;
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
    @JoinColumn(name = "id_auditoria_lugar", referencedColumnName = "id_auditoria_lugar")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AuditoriaLugar idAuditoriaLugar;
    @JoinColumn(name = "id_auditoria_tipo", referencedColumnName = "id_auditoria_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AuditoriaTipo idAuditoriaTipo;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadTipoDetalles;
    @JoinColumn(name = "id_generica_tipo_detalle", referencedColumnName = "id_generica_tipo_detalle")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenericaTipoDetalles idGenericaTipoDetalles;

    public AuditoriaEncabezado() {
    }

    public AuditoriaEncabezado(Integer idAuditoriaEncabezado) {
        this.idAuditoriaEncabezado = idAuditoriaEncabezado;
    }

    public AuditoriaEncabezado(Integer idAuditoriaEncabezado, String username, Date creado, int estadoReg) {
        this.idAuditoriaEncabezado = idAuditoriaEncabezado;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaEncabezado() {
        return idAuditoriaEncabezado;
    }

    public void setIdAuditoriaEncabezado(Integer idAuditoriaEncabezado) {
        this.idAuditoriaEncabezado = idAuditoriaEncabezado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
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

    public AuditoriaLugar getIdAuditoriaLugar() {
        return idAuditoriaLugar;
    }

    public void setIdAuditoriaLugar(AuditoriaLugar idAuditoriaLugar) {
        this.idAuditoriaLugar = idAuditoriaLugar;
    }

    public AuditoriaTipo getIdAuditoriaTipo() {
        return idAuditoriaTipo;
    }

    public void setIdAuditoriaTipo(AuditoriaTipo idAuditoriaTipo) {
        this.idAuditoriaTipo = idAuditoriaTipo;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public NovedadTipoDetalles getIdNovedadTipoDetalles() {
        return idNovedadTipoDetalles;
    }

    public void setIdNovedadTipoDetalles(NovedadTipoDetalles idNovedadTipoDetalles) {
        this.idNovedadTipoDetalles = idNovedadTipoDetalles;
    }

    public GenericaTipoDetalles getIdGenericaTipoDetalles() {
        return idGenericaTipoDetalles;
    }

    public void setIdGenericaTipoDetalles(GenericaTipoDetalles idGenericaTipoDetalles) {
        this.idGenericaTipoDetalles = idGenericaTipoDetalles;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaEncabezado != null ? idAuditoriaEncabezado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaEncabezado)) {
            return false;
        }
        AuditoriaEncabezado other = (AuditoriaEncabezado) object;
        if ((this.idAuditoriaEncabezado == null && other.idAuditoriaEncabezado != null) || (this.idAuditoriaEncabezado != null && !this.idAuditoriaEncabezado.equals(other.idAuditoriaEncabezado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaEncabezado[ idAuditoriaEncabezado=" + idAuditoriaEncabezado + " ]";
    }

    @XmlTransient
    public List<Auditoria> getAuditoriaList() {
        return auditoriaList;
    }

    public void setAuditoriaList(List<Auditoria> auditoriaList) {
        this.auditoriaList = auditoriaList;
    }

}
