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
 * @author HP
 */
@Entity
@Table(name = "accidente_borrador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteBorrador.findAll", query = "SELECT a FROM AccidenteBorrador a")
    , @NamedQuery(name = "AccidenteBorrador.findByIdAccidenteBorrador", query = "SELECT a FROM AccidenteBorrador a WHERE a.idAccidenteBorrador = :idAccidenteBorrador")
    , @NamedQuery(name = "AccidenteBorrador.findByUserSolicitado", query = "SELECT a FROM AccidenteBorrador a WHERE a.userSolicitado = :userSolicitado")
    , @NamedQuery(name = "AccidenteBorrador.findByFechaSolicitado", query = "SELECT a FROM AccidenteBorrador a WHERE a.fechaSolicitado = :fechaSolicitado")
    , @NamedQuery(name = "AccidenteBorrador.findByUserAprobado", query = "SELECT a FROM AccidenteBorrador a WHERE a.userAprobado = :userAprobado")
    , @NamedQuery(name = "AccidenteBorrador.findByFechaAprobado", query = "SELECT a FROM AccidenteBorrador a WHERE a.fechaAprobado = :fechaAprobado")
    , @NamedQuery(name = "AccidenteBorrador.findByUsername", query = "SELECT a FROM AccidenteBorrador a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteBorrador.findByCreado", query = "SELECT a FROM AccidenteBorrador a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteBorrador.findByModificado", query = "SELECT a FROM AccidenteBorrador a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteBorrador.findByEstadoReg", query = "SELECT a FROM AccidenteBorrador a WHERE a.estadoReg = :estadoReg")})
public class AccidenteBorrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_borrador")
    private Integer idAccidenteBorrador;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion_solicitado")
    private String descripcionSolicitado;
    @Size(max = 15)
    @Column(name = "user_solicitado")
    private String userSolicitado;
    @Column(name = "fecha_solicitado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitado;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion_aprobado")
    private String descripcionAprobado;
    @Size(max = 15)
    @Column(name = "user_aprobado")
    private String userAprobado;
    @Column(name = "fecha_aprobado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprobado;
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
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;

    public AccidenteBorrador() {
    }

    public AccidenteBorrador(Integer idAccidenteBorrador) {
        this.idAccidenteBorrador = idAccidenteBorrador;
    }

    public Integer getIdAccidenteBorrador() {
        return idAccidenteBorrador;
    }

    public void setIdAccidenteBorrador(Integer idAccidenteBorrador) {
        this.idAccidenteBorrador = idAccidenteBorrador;
    }

    public String getDescripcionSolicitado() {
        return descripcionSolicitado;
    }

    public void setDescripcionSolicitado(String descripcionSolicitado) {
        this.descripcionSolicitado = descripcionSolicitado;
    }

    public String getUserSolicitado() {
        return userSolicitado;
    }

    public void setUserSolicitado(String userSolicitado) {
        this.userSolicitado = userSolicitado;
    }

    public Date getFechaSolicitado() {
        return fechaSolicitado;
    }

    public void setFechaSolicitado(Date fechaSolicitado) {
        this.fechaSolicitado = fechaSolicitado;
    }

    public String getDescripcionAprobado() {
        return descripcionAprobado;
    }

    public void setDescripcionAprobado(String descripcionAprobado) {
        this.descripcionAprobado = descripcionAprobado;
    }

    public String getUserAprobado() {
        return userAprobado;
    }

    public void setUserAprobado(String userAprobado) {
        this.userAprobado = userAprobado;
    }

    public Date getFechaAprobado() {
        return fechaAprobado;
    }

    public void setFechaAprobado(Date fechaAprobado) {
        this.fechaAprobado = fechaAprobado;
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

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteBorrador != null ? idAccidenteBorrador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteBorrador)) {
            return false;
        }
        AccidenteBorrador other = (AccidenteBorrador) object;
        if ((this.idAccidenteBorrador == null && other.idAccidenteBorrador != null) || (this.idAccidenteBorrador != null && !this.idAccidenteBorrador.equals(other.idAccidenteBorrador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteBorrador[ idAccidenteBorrador=" + idAccidenteBorrador + " ]";
    }
    
}
