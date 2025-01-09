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
@Table(name = "prg_desasignar_param")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgDesasignarParam.findAll", query = "SELECT p FROM PrgDesasignarParam p")
    , @NamedQuery(name = "PrgDesasignarParam.findByIdPrgDesasignarParam", query = "SELECT p FROM PrgDesasignarParam p WHERE p.idPrgDesasignarParam = :idPrgDesasignarParam")
    , @NamedQuery(name = "PrgDesasignarParam.findByUsername", query = "SELECT p FROM PrgDesasignarParam p WHERE p.username = :username")
    , @NamedQuery(name = "PrgDesasignarParam.findByCreado", query = "SELECT p FROM PrgDesasignarParam p WHERE p.creado = :creado")
    , @NamedQuery(name = "PrgDesasignarParam.findByModificado", query = "SELECT p FROM PrgDesasignarParam p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgDesasignarParam.findByEstadoReg", query = "SELECT p FROM PrgDesasignarParam p WHERE p.estadoReg = :estadoReg")})
public class PrgDesasignarParam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_desasignar_param")
    private Integer idPrgDesasignarParam;
    @Lob
    @Size(max = 65535)
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
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadTipoDetalle;
    @JoinColumn(name = "id_prg_sercon_motivo", referencedColumnName = "id_prg_sercon_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgSerconMotivo idPrgSerconMotivo;

    public PrgDesasignarParam() {
    }

    public PrgDesasignarParam(Integer idPrgDesasignarParam) {
        this.idPrgDesasignarParam = idPrgDesasignarParam;
    }

    public Integer getIdPrgDesasignarParam() {
        return idPrgDesasignarParam;
    }

    public void setIdPrgDesasignarParam(Integer idPrgDesasignarParam) {
        this.idPrgDesasignarParam = idPrgDesasignarParam;
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

    public NovedadTipoDetalles getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(NovedadTipoDetalles idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    public PrgSerconMotivo getIdPrgSerconMotivo() {
        return idPrgSerconMotivo;
    }

    public void setIdPrgSerconMotivo(PrgSerconMotivo idPrgSerconMotivo) {
        this.idPrgSerconMotivo = idPrgSerconMotivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgDesasignarParam != null ? idPrgDesasignarParam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgDesasignarParam)) {
            return false;
        }
        PrgDesasignarParam other = (PrgDesasignarParam) object;
        if ((this.idPrgDesasignarParam == null && other.idPrgDesasignarParam != null) || (this.idPrgDesasignarParam != null && !this.idPrgDesasignarParam.equals(other.idPrgDesasignarParam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgDesasignarParam[ idPrgDesasignarParam=" + idPrgDesasignarParam + " ]";
    }
    
}
