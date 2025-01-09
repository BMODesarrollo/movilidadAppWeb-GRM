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
@Table(name = "acc_novedad_infraestruc_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccNovedadInfraestrucSeguimiento.findAll", query = "SELECT a FROM AccNovedadInfraestrucSeguimiento a"),
    @NamedQuery(name = "AccNovedadInfraestrucSeguimiento.findByIdAccNovedadInfraestrucSeguimiento", query = "SELECT a FROM AccNovedadInfraestrucSeguimiento a WHERE a.idAccNovedadInfraestrucSeguimiento = :idAccNovedadInfraestrucSeguimiento"),
    @NamedQuery(name = "AccNovedadInfraestrucSeguimiento.findByFecha", query = "SELECT a FROM AccNovedadInfraestrucSeguimiento a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "AccNovedadInfraestrucSeguimiento.findByUsername", query = "SELECT a FROM AccNovedadInfraestrucSeguimiento a WHERE a.username = :username"),
    @NamedQuery(name = "AccNovedadInfraestrucSeguimiento.findByCreado", query = "SELECT a FROM AccNovedadInfraestrucSeguimiento a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccNovedadInfraestrucSeguimiento.findByModificado", query = "SELECT a FROM AccNovedadInfraestrucSeguimiento a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccNovedadInfraestrucSeguimiento.findByEstadoReg", query = "SELECT a FROM AccNovedadInfraestrucSeguimiento a WHERE a.estadoReg = :estadoReg")})
public class AccNovedadInfraestrucSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_novedad_infraestruc_seguimiento")
    private Integer idAccNovedadInfraestrucSeguimiento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "seguimiento")
    private String seguimiento;
    @Size(max = 255)
    @Column(name = "path")
    private String path;
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
    @JoinColumn(name = "id_acc_novedad_infraestruc", referencedColumnName = "id_acc_novedad_infraestruc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AccNovedadInfraestruc idAccNovedadInfraestruc;

    public AccNovedadInfraestrucSeguimiento() {
    }

    public AccNovedadInfraestrucSeguimiento(Integer idAccNovedadInfraestrucSeguimiento) {
        this.idAccNovedadInfraestrucSeguimiento = idAccNovedadInfraestrucSeguimiento;
    }

    public AccNovedadInfraestrucSeguimiento(Integer idAccNovedadInfraestrucSeguimiento, String seguimiento) {
        this.idAccNovedadInfraestrucSeguimiento = idAccNovedadInfraestrucSeguimiento;
        this.seguimiento = seguimiento;
    }

    public Integer getIdAccNovedadInfraestrucSeguimiento() {
        return idAccNovedadInfraestrucSeguimiento;
    }

    public void setIdAccNovedadInfraestrucSeguimiento(Integer idAccNovedadInfraestrucSeguimiento) {
        this.idAccNovedadInfraestrucSeguimiento = idAccNovedadInfraestrucSeguimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public AccNovedadInfraestruc getIdAccNovedadInfraestruc() {
        return idAccNovedadInfraestruc;
    }

    public void setIdAccNovedadInfraestruc(AccNovedadInfraestruc idAccNovedadInfraestruc) {
        this.idAccNovedadInfraestruc = idAccNovedadInfraestruc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccNovedadInfraestrucSeguimiento != null ? idAccNovedadInfraestrucSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccNovedadInfraestrucSeguimiento)) {
            return false;
        }
        AccNovedadInfraestrucSeguimiento other = (AccNovedadInfraestrucSeguimiento) object;
        if ((this.idAccNovedadInfraestrucSeguimiento == null && other.idAccNovedadInfraestrucSeguimiento != null) || (this.idAccNovedadInfraestrucSeguimiento != null && !this.idAccNovedadInfraestrucSeguimiento.equals(other.idAccNovedadInfraestrucSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccNovedadInfraestrucSeguimiento[ idAccNovedadInfraestrucSeguimiento=" + idAccNovedadInfraestrucSeguimiento + " ]";
    }

}
