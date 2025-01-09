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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "novedad_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadCab.findAll", query = "SELECT n FROM NovedadCab n"),
    @NamedQuery(name = "NovedadCab.findByIdNovedadCab", query = "SELECT n FROM NovedadCab n WHERE n.idNovedadCab = :idNovedadCab"),
    @NamedQuery(name = "NovedadCab.findByFechaHora", query = "SELECT n FROM NovedadCab n WHERE n.fechaHora = :fechaHora"),
    @NamedQuery(name = "NovedadCab.findByEstadoNov", query = "SELECT n FROM NovedadCab n WHERE n.estadoNov = :estadoNov"),
    @NamedQuery(name = "NovedadCab.findByObservacion", query = "SELECT n FROM NovedadCab n WHERE n.observacion = :observacion"),
    @NamedQuery(name = "NovedadCab.findByUsername", query = "SELECT n FROM NovedadCab n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadCab.findByCreado", query = "SELECT n FROM NovedadCab n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadCab.findByModificado", query = "SELECT n FROM NovedadCab n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadCab.findByEstadoReg", query = "SELECT n FROM NovedadCab n WHERE n.estadoReg = :estadoReg")})
public class NovedadCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_cab")
    private Integer idNovedadCab;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "estado_nov")
    private Integer estadoNov;
    @Size(min = 1, max = 150)
    @Column(name = "path_foto")
    private String pathFoto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Size(max = 15)
    @Column(name = "username_estado")
    private String usernameEstado;
    @Column(name = "fecha_estado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @OneToMany(mappedBy = "novedadCab", fetch = FetchType.LAZY)
    private List<AseoCabinaNovedad> aseoCabinaNovedadList;
    @JoinColumn(name = "id_novedad_tipo_cab", referencedColumnName = "id_novedad_tipo_cab")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoCab idNovedadTipoCab;
    @JoinColumn(name = "id_novedad_tipo_det_cab", referencedColumnName = "id_novedad_tipo_det_cab")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetallesCab idNovedadTipoDetCab;
    @JoinColumn(name = "id_cable_cabina", referencedColumnName = "id_cable_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableCabina idCableCabina;

    public NovedadCab() {
    }

    public NovedadCab(Integer idNovedadCab) {
        this.idNovedadCab = idNovedadCab;
    }

    public NovedadCab(Integer idNovedadCab, String observacion) {
        this.idNovedadCab = idNovedadCab;
        this.observacion = observacion;
    }

    public Integer getIdNovedadCab() {
        return idNovedadCab;
    }

    public void setIdNovedadCab(Integer idNovedadCab) {
        this.idNovedadCab = idNovedadCab;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getEstadoNov() {
        return estadoNov;
    }

    public void setEstadoNov(Integer estadoNov) {
        this.estadoNov = estadoNov;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    @XmlTransient
    public List<AseoCabinaNovedad> getAseoCabinaNovedadList() {
        return aseoCabinaNovedadList;
    }

    public void setAseoCabinaNovedadList(List<AseoCabinaNovedad> aseoCabinaNovedadList) {
        this.aseoCabinaNovedadList = aseoCabinaNovedadList;
    }

    public NovedadTipoCab getIdNovedadTipoCab() {
        return idNovedadTipoCab;
    }

    public void setIdNovedadTipoCab(NovedadTipoCab idNovedadTipoCab) {
        this.idNovedadTipoCab = idNovedadTipoCab;
    }

    public NovedadTipoDetallesCab getIdNovedadTipoDetCab() {
        return idNovedadTipoDetCab;
    }

    public void setIdNovedadTipoDetCab(NovedadTipoDetallesCab idNovedadTipoDetCab) {
        this.idNovedadTipoDetCab = idNovedadTipoDetCab;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public CableCabina getIdCableCabina() {
        return idCableCabina;
    }

    public void setIdCableCabina(CableCabina idCableCabina) {
        this.idCableCabina = idCableCabina;
    }

    public String getUsernameEstado() {
        return usernameEstado;
    }

    public void setUsernameEstado(String usernameEstado) {
        this.usernameEstado = usernameEstado;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadCab != null ? idNovedadCab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadCab)) {
            return false;
        }
        NovedadCab other = (NovedadCab) object;
        if ((this.idNovedadCab == null && other.idNovedadCab != null) || (this.idNovedadCab != null && !this.idNovedadCab.equals(other.idNovedadCab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadCab[ idNovedadCab=" + idNovedadCab + " ]";
    }
}
