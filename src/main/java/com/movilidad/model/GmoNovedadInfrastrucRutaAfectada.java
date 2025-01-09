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
@Table(name = "gmo_novedad_infrastruc_ruta_afectada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GmoNovedadInfrastrucRutaAfectada.findAll", query = "SELECT g FROM GmoNovedadInfrastrucRutaAfectada g"),
    @NamedQuery(name = "GmoNovedadInfrastrucRutaAfectada.findByIdGmoNovedadInfrastrucRutaAfectada", query = "SELECT g FROM GmoNovedadInfrastrucRutaAfectada g WHERE g.idGmoNovedadInfrastrucRutaAfectada = :idGmoNovedadInfrastrucRutaAfectada"),
    @NamedQuery(name = "GmoNovedadInfrastrucRutaAfectada.findByUsername", query = "SELECT g FROM GmoNovedadInfrastrucRutaAfectada g WHERE g.username = :username"),
    @NamedQuery(name = "GmoNovedadInfrastrucRutaAfectada.findByCreado", query = "SELECT g FROM GmoNovedadInfrastrucRutaAfectada g WHERE g.creado = :creado"),
    @NamedQuery(name = "GmoNovedadInfrastrucRutaAfectada.findByModificado", query = "SELECT g FROM GmoNovedadInfrastrucRutaAfectada g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GmoNovedadInfrastrucRutaAfectada.findByEstadoReg", query = "SELECT g FROM GmoNovedadInfrastrucRutaAfectada g WHERE g.estadoReg = :estadoReg")})
public class GmoNovedadInfrastrucRutaAfectada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gmo_novedad_infrastruc_ruta_afectada")
    private Integer idGmoNovedadInfrastrucRutaAfectada;
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
    @JoinColumn(name = "id_gmo_novedad_infrastruc", referencedColumnName = "id_gmo_novedad_infrastruc")
    @ManyToOne(optional = false)
    private GmoNovedadInfrastruc idGmoNovedadInfrastruc;
    @JoinColumn(name = "id_prg_route", referencedColumnName = "id_prg_route")
    @ManyToOne(optional = false)
    private PrgRoute idPrgRoute;

    public GmoNovedadInfrastrucRutaAfectada() {
    }

    public GmoNovedadInfrastrucRutaAfectada(Integer idGmoNovedadInfrastrucRutaAfectada) {
        this.idGmoNovedadInfrastrucRutaAfectada = idGmoNovedadInfrastrucRutaAfectada;
    }

    public GmoNovedadInfrastrucRutaAfectada(Integer idGmoNovedadInfrastrucRutaAfectada, String username, Date creado, int estadoReg) {
        this.idGmoNovedadInfrastrucRutaAfectada = idGmoNovedadInfrastrucRutaAfectada;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGmoNovedadInfrastrucRutaAfectada() {
        return idGmoNovedadInfrastrucRutaAfectada;
    }

    public void setIdGmoNovedadInfrastrucRutaAfectada(Integer idGmoNovedadInfrastrucRutaAfectada) {
        this.idGmoNovedadInfrastrucRutaAfectada = idGmoNovedadInfrastrucRutaAfectada;
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

    public GmoNovedadInfrastruc getIdGmoNovedadInfrastruc() {
        return idGmoNovedadInfrastruc;
    }

    public void setIdGmoNovedadInfrastruc(GmoNovedadInfrastruc idGmoNovedadInfrastruc) {
        this.idGmoNovedadInfrastruc = idGmoNovedadInfrastruc;
    }

    public PrgRoute getIdPrgRoute() {
        return idPrgRoute;
    }

    public void setIdPrgRoute(PrgRoute idPrgRoute) {
        this.idPrgRoute = idPrgRoute;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGmoNovedadInfrastrucRutaAfectada != null ? idGmoNovedadInfrastrucRutaAfectada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GmoNovedadInfrastrucRutaAfectada)) {
            return false;
        }
        GmoNovedadInfrastrucRutaAfectada other = (GmoNovedadInfrastrucRutaAfectada) object;
        if ((this.idGmoNovedadInfrastrucRutaAfectada == null && other.idGmoNovedadInfrastrucRutaAfectada != null) || (this.idGmoNovedadInfrastrucRutaAfectada != null && !this.idGmoNovedadInfrastrucRutaAfectada.equals(other.idGmoNovedadInfrastrucRutaAfectada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GmoNovedadInfrastrucRutaAfectada[ idGmoNovedadInfrastrucRutaAfectada=" + idGmoNovedadInfrastrucRutaAfectada + " ]";
    }
    
}
