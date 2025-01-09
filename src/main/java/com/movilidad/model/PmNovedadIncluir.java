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
 * @author solucionesit
 */
@Entity
@Table(name = "pm_novedad_incluir")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmNovedadIncluir.findAll", query = "SELECT p FROM PmNovedadIncluir p"),
    @NamedQuery(name = "PmNovedadIncluir.findByIdPmNovedadIncluir", query = "SELECT p FROM PmNovedadIncluir p WHERE p.idPmNovedadIncluir = :idPmNovedadIncluir"),
    @NamedQuery(name = "PmNovedadIncluir.findByActivo", query = "SELECT p FROM PmNovedadIncluir p WHERE p.activo = :activo"),
    @NamedQuery(name = "PmNovedadIncluir.findByUsername", query = "SELECT p FROM PmNovedadIncluir p WHERE p.username = :username"),
    @NamedQuery(name = "PmNovedadIncluir.findByCreado", query = "SELECT p FROM PmNovedadIncluir p WHERE p.creado = :creado"),
    @NamedQuery(name = "PmNovedadIncluir.findByModificado", query = "SELECT p FROM PmNovedadIncluir p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PmNovedadIncluir.findByEstadoReg", query = "SELECT p FROM PmNovedadIncluir p WHERE p.estadoReg = :estadoReg")})
public class PmNovedadIncluir implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_novedad_incluir")
    private Integer idPmNovedadIncluir;
    @Column(name = "activo")
    private Integer activo;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadTipoDetalle;

    public PmNovedadIncluir() {
    }

    public PmNovedadIncluir(Integer idPmNovedadIncluir) {
        this.idPmNovedadIncluir = idPmNovedadIncluir;
    }

    public Integer getIdPmNovedadIncluir() {
        return idPmNovedadIncluir;
    }

    public void setIdPmNovedadIncluir(Integer idPmNovedadIncluir) {
        this.idPmNovedadIncluir = idPmNovedadIncluir;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmNovedadIncluir != null ? idPmNovedadIncluir.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmNovedadIncluir)) {
            return false;
        }
        PmNovedadIncluir other = (PmNovedadIncluir) object;
        if ((this.idPmNovedadIncluir == null && other.idPmNovedadIncluir != null) || (this.idPmNovedadIncluir != null && !this.idPmNovedadIncluir.equals(other.idPmNovedadIncluir))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmNovedadIncluir[ idPmNovedadIncluir=" + idPmNovedadIncluir + " ]";
    }
    
}
