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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "gmo_novedad_infrastruc_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GmoNovedadInfrastrucSeguimiento.findAll", query = "SELECT g FROM GmoNovedadInfrastrucSeguimiento g"),
    @NamedQuery(name = "GmoNovedadInfrastrucSeguimiento.findByIdGmoNovedadInfrastrucSeguimiento", query = "SELECT g FROM GmoNovedadInfrastrucSeguimiento g WHERE g.idGmoNovedadInfrastrucSeguimiento = :idGmoNovedadInfrastrucSeguimiento"),
    @NamedQuery(name = "GmoNovedadInfrastrucSeguimiento.findByFecha", query = "SELECT g FROM GmoNovedadInfrastrucSeguimiento g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "GmoNovedadInfrastrucSeguimiento.findByUsername", query = "SELECT g FROM GmoNovedadInfrastrucSeguimiento g WHERE g.username = :username"),
    @NamedQuery(name = "GmoNovedadInfrastrucSeguimiento.findByCreado", query = "SELECT g FROM GmoNovedadInfrastrucSeguimiento g WHERE g.creado = :creado"),
    @NamedQuery(name = "GmoNovedadInfrastrucSeguimiento.findByModificado", query = "SELECT g FROM GmoNovedadInfrastrucSeguimiento g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GmoNovedadInfrastrucSeguimiento.findByEstadoReg", query = "SELECT g FROM GmoNovedadInfrastrucSeguimiento g WHERE g.estadoReg = :estadoReg")})
public class GmoNovedadInfrastrucSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gmo_novedad_infrastruc_seguimiento")
    private Integer idGmoNovedadInfrastrucSeguimiento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "observacion")
    private String observacion;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GmoNovedadInfrastruc idGmoNovedadInfrastruc;

    public GmoNovedadInfrastrucSeguimiento() {
    }

    public GmoNovedadInfrastrucSeguimiento(Integer idGmoNovedadInfrastrucSeguimiento) {
        this.idGmoNovedadInfrastrucSeguimiento = idGmoNovedadInfrastrucSeguimiento;
    }

    public GmoNovedadInfrastrucSeguimiento(Integer idGmoNovedadInfrastrucSeguimiento, String observacion, String username, Date creado, int estadoReg) {
        this.idGmoNovedadInfrastrucSeguimiento = idGmoNovedadInfrastrucSeguimiento;
        this.observacion = observacion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGmoNovedadInfrastrucSeguimiento() {
        return idGmoNovedadInfrastrucSeguimiento;
    }

    public void setIdGmoNovedadInfrastrucSeguimiento(Integer idGmoNovedadInfrastrucSeguimiento) {
        this.idGmoNovedadInfrastrucSeguimiento = idGmoNovedadInfrastrucSeguimiento;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGmoNovedadInfrastrucSeguimiento != null ? idGmoNovedadInfrastrucSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GmoNovedadInfrastrucSeguimiento)) {
            return false;
        }
        GmoNovedadInfrastrucSeguimiento other = (GmoNovedadInfrastrucSeguimiento) object;
        if ((this.idGmoNovedadInfrastrucSeguimiento == null && other.idGmoNovedadInfrastrucSeguimiento != null) || (this.idGmoNovedadInfrastrucSeguimiento != null && !this.idGmoNovedadInfrastrucSeguimiento.equals(other.idGmoNovedadInfrastrucSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GmoNovedadInfrastrucSeguimiento[ idGmoNovedadInfrastrucSeguimiento=" + idGmoNovedadInfrastrucSeguimiento + " ]";
    }
    
}
