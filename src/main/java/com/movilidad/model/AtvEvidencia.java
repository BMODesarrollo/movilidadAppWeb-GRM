/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "atv_evidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvEvidencia.findAll", query = "SELECT a FROM AtvEvidencia a")
    , @NamedQuery(name = "AtvEvidencia.findByIdAtvEvidencia", query = "SELECT a FROM AtvEvidencia a WHERE a.idAtvEvidencia = :idAtvEvidencia")
    , @NamedQuery(name = "AtvEvidencia.findByFecha", query = "SELECT a FROM AtvEvidencia a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AtvEvidencia.findByUsername", query = "SELECT a FROM AtvEvidencia a WHERE a.username = :username")
    , @NamedQuery(name = "AtvEvidencia.findByCreado", query = "SELECT a FROM AtvEvidencia a WHERE a.creado = :creado")
    , @NamedQuery(name = "AtvEvidencia.findByModificado", query = "SELECT a FROM AtvEvidencia a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AtvEvidencia.findByEstadoReg", query = "SELECT a FROM AtvEvidencia a WHERE a.estadoReg = :estadoReg")})
public class AtvEvidencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_evidencia")
    private Integer idAtvEvidencia;
    @Lob
    @Size(max = 65535)
    @Column(name = "path")
    private String path;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_atv_tipo_estado", referencedColumnName = "id_atv_tipo_estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private AtvTipoEstado idAtvTipoEstado;
    @JoinColumn(name = "id_atv_tipo_evidencia", referencedColumnName = "id_atv_tipo_evidencia")
    @ManyToOne(fetch = FetchType.LAZY)
    private AtvTipoEvidencia idAtvTipoEvidencia;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Novedad idNovedad;
    
    @Column(name = "latitud")
    private Float latitud;
    @Column(name = "longitud")
    private Float longitud;

    public AtvEvidencia() {
    }

    public AtvEvidencia(Integer idAtvEvidencia) {
        this.idAtvEvidencia = idAtvEvidencia;
    }

    public Integer getIdAtvEvidencia() {
        return idAtvEvidencia;
    }

    public void setIdAtvEvidencia(Integer idAtvEvidencia) {
        this.idAtvEvidencia = idAtvEvidencia;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public AtvTipoEstado getIdAtvTipoEstado() {
        return idAtvTipoEstado;
    }

    public void setIdAtvTipoEstado(AtvTipoEstado idAtvTipoEstado) {
        this.idAtvTipoEstado = idAtvTipoEstado;
    }

    public AtvTipoEvidencia getIdAtvTipoEvidencia() {
        return idAtvTipoEvidencia;
    }

    public void setIdAtvTipoEvidencia(AtvTipoEvidencia idAtvTipoEvidencia) {
        this.idAtvTipoEvidencia = idAtvTipoEvidencia;
    }

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtvEvidencia != null ? idAtvEvidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvEvidencia)) {
            return false;
        }
        AtvEvidencia other = (AtvEvidencia) object;
        if ((this.idAtvEvidencia == null && other.idAtvEvidencia != null) || (this.idAtvEvidencia != null && !this.idAtvEvidencia.equals(other.idAtvEvidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvEvidencia[ idAtvEvidencia=" + idAtvEvidencia + " ]";
    }
    
}
