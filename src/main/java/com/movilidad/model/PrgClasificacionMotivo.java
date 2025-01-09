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
@Table(name = "prg_clasificacion_motivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgClasificacionMotivo.findAll", query = "SELECT p FROM PrgClasificacionMotivo p"),
    @NamedQuery(name = "PrgClasificacionMotivo.findByIdPrgClasificacionMotivo", query = "SELECT p FROM PrgClasificacionMotivo p WHERE p.idPrgClasificacionMotivo = :idPrgClasificacionMotivo"),
    @NamedQuery(name = "PrgClasificacionMotivo.findByNombre", query = "SELECT p FROM PrgClasificacionMotivo p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PrgClasificacionMotivo.findByDescripcion", query = "SELECT p FROM PrgClasificacionMotivo p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "PrgClasificacionMotivo.findByUsername", query = "SELECT p FROM PrgClasificacionMotivo p WHERE p.username = :username"),
    @NamedQuery(name = "PrgClasificacionMotivo.findByCreado", query = "SELECT p FROM PrgClasificacionMotivo p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgClasificacionMotivo.findByModificado", query = "SELECT p FROM PrgClasificacionMotivo p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgClasificacionMotivo.findByEstadoReg", query = "SELECT p FROM PrgClasificacionMotivo p WHERE p.estadoReg = :estadoReg")})
public class PrgClasificacionMotivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_clasificacion_motivo")
    private Integer idPrgClasificacionMotivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_prg_tc_responsable", referencedColumnName = "id_prg_tc_responsable")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PrgTcResponsable idPrgTcResponsable;

    @OneToMany(mappedBy = "idPrgClasificacionMotivo", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList;
    @OneToMany(mappedBy = "idPrgClasificacionMotivo", fetch = FetchType.LAZY)
    private List<NovedadTipoDetalles> novedadTipoDetallesList;

    public PrgClasificacionMotivo() {
    }

    public PrgClasificacionMotivo(Integer idPrgClasificacionMotivo) {
        this.idPrgClasificacionMotivo = idPrgClasificacionMotivo;
    }

    public PrgClasificacionMotivo(Integer idPrgClasificacionMotivo, String nombre) {
        this.idPrgClasificacionMotivo = idPrgClasificacionMotivo;
        this.nombre = nombre;
    }

    public Integer getIdPrgClasificacionMotivo() {
        return idPrgClasificacionMotivo;
    }

    public void setIdPrgClasificacionMotivo(Integer idPrgClasificacionMotivo) {
        this.idPrgClasificacionMotivo = idPrgClasificacionMotivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public PrgTcResponsable getIdPrgTcResponsable() {
        return idPrgTcResponsable;
    }

    public void setIdPrgTcResponsable(PrgTcResponsable idPrgTcResponsable) {
        this.idPrgTcResponsable = idPrgTcResponsable;
    }

    @XmlTransient
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    @XmlTransient
    public List<NovedadTipoDetalles> getNovedadTipoDetallesList() {
        return novedadTipoDetallesList;
    }

    public void setNovedadTipoDetallesList(List<NovedadTipoDetalles> novedadTipoDetallesList) {
        this.novedadTipoDetallesList = novedadTipoDetallesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgClasificacionMotivo != null ? idPrgClasificacionMotivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgClasificacionMotivo)) {
            return false;
        }
        PrgClasificacionMotivo other = (PrgClasificacionMotivo) object;
        if ((this.idPrgClasificacionMotivo == null && other.idPrgClasificacionMotivo != null) || (this.idPrgClasificacionMotivo != null && !this.idPrgClasificacionMotivo.equals(other.idPrgClasificacionMotivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgClasificacionMotivo[ idPrgClasificacionMotivo=" + idPrgClasificacionMotivo + " ]";
    }

}
