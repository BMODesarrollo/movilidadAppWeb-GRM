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
@Table(name = "generica_pd_maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPdMaestro.findAll", query = "SELECT g FROM GenericaPdMaestro g"),
    @NamedQuery(name = "GenericaPdMaestro.findByIdGenericaPdMaestro", query = "SELECT g FROM GenericaPdMaestro g WHERE g.idGenericaPdMaestro = :idGenericaPdMaestro"),
    @NamedQuery(name = "GenericaPdMaestro.findByFechaApertura", query = "SELECT g FROM GenericaPdMaestro g WHERE g.fechaApertura = :fechaApertura"),
    @NamedQuery(name = "GenericaPdMaestro.findByFechaCierre", query = "SELECT g FROM GenericaPdMaestro g WHERE g.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "GenericaPdMaestro.findByUsuarioApertura", query = "SELECT g FROM GenericaPdMaestro g WHERE g.usuarioApertura = :usuarioApertura"),
    @NamedQuery(name = "GenericaPdMaestro.findByUsuarioCierre", query = "SELECT g FROM GenericaPdMaestro g WHERE g.usuarioCierre = :usuarioCierre"),
    @NamedQuery(name = "GenericaPdMaestro.findByCreado", query = "SELECT g FROM GenericaPdMaestro g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPdMaestro.findByModificado", query = "SELECT g FROM GenericaPdMaestro g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPdMaestro.findByEstadoReg", query = "SELECT g FROM GenericaPdMaestro g WHERE g.estadoReg = :estadoReg")})
public class GenericaPdMaestro implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenericaPdMaestro", fetch = FetchType.LAZY)
    private List<GenericaPdMaestroDetalle> genericaPdMaestroDetalleList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pd_maestro")
    private Integer idGenericaPdMaestro;
    @Column(name = "fecha_apertura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "comentarios")
    private String comentarios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario_apertura")
    private String usuarioApertura;
    @Size(max = 15)
    @Column(name = "usuario_cierre")
    private String usuarioCierre;
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
    @JoinColumn(name = "id_pd_estado_proceso", referencedColumnName = "id_pd_estado_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private PdEstadoProceso idPdEstadoProceso;
    @JoinColumn(name = "id_pd_tipo_sancion", referencedColumnName = "id_pd_tipo_sancion")
    @ManyToOne(fetch = FetchType.LAZY)
    private PdTipoSancion idPdTipoSancion;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenericaPdMaestro", fetch = FetchType.LAZY)
    private List<GenericaPdMaestroSeguimiento> genericaPdMaestroSeguimientoList;

    public GenericaPdMaestro() {
    }

    public GenericaPdMaestro(Integer idGenericaPdMaestro) {
        this.idGenericaPdMaestro = idGenericaPdMaestro;
    }

    public GenericaPdMaestro(Integer idGenericaPdMaestro, String comentarios, String usuarioApertura, Date creado, int estadoReg) {
        this.idGenericaPdMaestro = idGenericaPdMaestro;
        this.comentarios = comentarios;
        this.usuarioApertura = usuarioApertura;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaPdMaestro() {
        return idGenericaPdMaestro;
    }

    public void setIdGenericaPdMaestro(Integer idGenericaPdMaestro) {
        this.idGenericaPdMaestro = idGenericaPdMaestro;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getUsuarioApertura() {
        return usuarioApertura;
    }

    public void setUsuarioApertura(String usuarioApertura) {
        this.usuarioApertura = usuarioApertura;
    }

    public String getUsuarioCierre() {
        return usuarioCierre;
    }

    public void setUsuarioCierre(String usuarioCierre) {
        this.usuarioCierre = usuarioCierre;
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

    public PdEstadoProceso getIdPdEstadoProceso() {
        return idPdEstadoProceso;
    }

    public void setIdPdEstadoProceso(PdEstadoProceso idPdEstadoProceso) {
        this.idPdEstadoProceso = idPdEstadoProceso;
    }

    public PdTipoSancion getIdPdTipoSancion() {
        return idPdTipoSancion;
    }

    public void setIdPdTipoSancion(PdTipoSancion idPdTipoSancion) {
        this.idPdTipoSancion = idPdTipoSancion;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @XmlTransient
    public List<GenericaPdMaestroSeguimiento> getGenericaPdMaestroSeguimientoList() {
        return genericaPdMaestroSeguimientoList;
    }

    public void setGenericaPdMaestroSeguimientoList(List<GenericaPdMaestroSeguimiento> genericaPdMaestroSeguimientoList) {
        this.genericaPdMaestroSeguimientoList = genericaPdMaestroSeguimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaPdMaestro != null ? idGenericaPdMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPdMaestro)) {
            return false;
        }
        GenericaPdMaestro other = (GenericaPdMaestro) object;
        if ((this.idGenericaPdMaestro == null && other.idGenericaPdMaestro != null) || (this.idGenericaPdMaestro != null && !this.idGenericaPdMaestro.equals(other.idGenericaPdMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPdMaestro[ idGenericaPdMaestro=" + idGenericaPdMaestro + " ]";
    }

    @XmlTransient
    public List<GenericaPdMaestroDetalle> getGenericaPdMaestroDetalleList() {
        return genericaPdMaestroDetalleList;
    }

    public void setGenericaPdMaestroDetalleList(List<GenericaPdMaestroDetalle> genericaPdMaestroDetalleList) {
        this.genericaPdMaestroDetalleList = genericaPdMaestroDetalleList;
    }

}
