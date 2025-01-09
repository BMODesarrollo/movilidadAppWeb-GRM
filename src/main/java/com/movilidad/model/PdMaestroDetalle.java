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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pd_maestro_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdMaestroDetalle.findAll", query = "SELECT p FROM PdMaestroDetalle p"),
    @NamedQuery(name = "PdMaestroDetalle.findByIdPdMaestroDetalle", query = "SELECT p FROM PdMaestroDetalle p WHERE p.idPdMaestroDetalle = :idPdMaestroDetalle"),
    @NamedQuery(name = "PdMaestroDetalle.findByFechaProceso", query = "SELECT p FROM PdMaestroDetalle p WHERE p.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "PdMaestroDetalle.findByUsername", query = "SELECT p FROM PdMaestroDetalle p WHERE p.username = :username"),
    @NamedQuery(name = "PdMaestroDetalle.findByCreado", query = "SELECT p FROM PdMaestroDetalle p WHERE p.creado = :creado"),
    @NamedQuery(name = "PdMaestroDetalle.findByModificado", query = "SELECT p FROM PdMaestroDetalle p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PdMaestroDetalle.findByEstadoReg", query = "SELECT p FROM PdMaestroDetalle p WHERE p.estadoReg = :estadoReg")})
public class PdMaestroDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pd_maestro_detalle")
    private Integer idPdMaestroDetalle;
    @Column(name = "fecha_proceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
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
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Novedad idNovedad;
    @JoinColumn(name = "id_pd_maestro", referencedColumnName = "id_pd_maestro")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PdMaestro idPdMaestro;


    public PdMaestroDetalle() {
    }

    public PdMaestroDetalle(Integer idPdMaestroDetalle) {
        this.idPdMaestroDetalle = idPdMaestroDetalle;
    }

    public PdMaestroDetalle(Integer idPdMaestroDetalle, String username, Date creado, int estadoReg) {
        this.idPdMaestroDetalle = idPdMaestroDetalle;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPdMaestroDetalle() {
        return idPdMaestroDetalle;
    }

    public void setIdPdMaestroDetalle(Integer idPdMaestroDetalle) {
        this.idPdMaestroDetalle = idPdMaestroDetalle;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
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

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public PdMaestro getIdPdMaestro() {
        return idPdMaestro;
    }

    public void setIdPdMaestro(PdMaestro idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPdMaestroDetalle != null ? idPdMaestroDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdMaestroDetalle)) {
            return false;
        }
        PdMaestroDetalle other = (PdMaestroDetalle) object;
        if ((this.idPdMaestroDetalle == null && other.idPdMaestroDetalle != null) || (this.idPdMaestroDetalle != null && !this.idPdMaestroDetalle.equals(other.idPdMaestroDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PdMaestroDetalle[ idPdMaestroDetalle=" + idPdMaestroDetalle + " ]";
    }

}
