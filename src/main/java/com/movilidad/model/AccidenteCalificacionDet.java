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
 * @author cesar
 */
@Entity
@Table(name = "accidente_calificacion_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteCalificacionDet.findAll", query = "SELECT a FROM AccidenteCalificacionDet a")
    , @NamedQuery(name = "AccidenteCalificacionDet.findByAccidenteCalificacionDet", query = "SELECT a FROM AccidenteCalificacionDet a WHERE a.accidenteCalificacionDet = :accidenteCalificacionDet")
    , @NamedQuery(name = "AccidenteCalificacionDet.findByCalificacion", query = "SELECT a FROM AccidenteCalificacionDet a WHERE a.calificacion = :calificacion")
    , @NamedQuery(name = "AccidenteCalificacionDet.findByUuidInvitado", query = "SELECT a FROM AccidenteCalificacionDet a WHERE a.uuidInvitado = :uuidInvitado")
    , @NamedQuery(name = "AccidenteCalificacionDet.findByUsrLidera", query = "SELECT a FROM AccidenteCalificacionDet a WHERE a.usrLidera = :usrLidera")
    , @NamedQuery(name = "AccidenteCalificacionDet.findByCreado", query = "SELECT a FROM AccidenteCalificacionDet a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteCalificacionDet.findByModificado", query = "SELECT a FROM AccidenteCalificacionDet a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteCalificacionDet.findByEstadoReg", query = "SELECT a FROM AccidenteCalificacionDet a WHERE a.estadoReg = :estadoReg")})
public class AccidenteCalificacionDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "accidente_calificacion_det")
    private Integer accidenteCalificacionDet;
    @Column(name = "calificacion")
    private Integer calificacion;
    @Size(max = 36)
    @Column(name = "uuid_invitado")
    private String uuidInvitado;
    @Size(max = 15)
    @Column(name = "usr_lidera")
    private String usrLidera;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_accidente_analisis", referencedColumnName = "id_accidente_analisis")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccidenteAnalisis idAccidenteAnalisis;
    @JoinColumn(name = "id_accidente_calificacion", referencedColumnName = "id_accidente_calificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccidenteCalificacion idAccidenteCalificacion;

    public AccidenteCalificacionDet() {
    }

    public AccidenteCalificacionDet(Integer accidenteCalificacionDet) {
        this.accidenteCalificacionDet = accidenteCalificacionDet;
    }

    public Integer getAccidenteCalificacionDet() {
        return accidenteCalificacionDet;
    }

    public void setAccidenteCalificacionDet(Integer accidenteCalificacionDet) {
        this.accidenteCalificacionDet = accidenteCalificacionDet;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getUuidInvitado() {
        return uuidInvitado;
    }

    public void setUuidInvitado(String uuidInvitado) {
        this.uuidInvitado = uuidInvitado;
    }

    public String getUsrLidera() {
        return usrLidera;
    }

    public void setUsrLidera(String usrLidera) {
        this.usrLidera = usrLidera;
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

    public AccidenteAnalisis getIdAccidenteAnalisis() {
        return idAccidenteAnalisis;
    }

    public void setIdAccidenteAnalisis(AccidenteAnalisis idAccidenteAnalisis) {
        this.idAccidenteAnalisis = idAccidenteAnalisis;
    }

    public AccidenteCalificacion getIdAccidenteCalificacion() {
        return idAccidenteCalificacion;
    }

    public void setIdAccidenteCalificacion(AccidenteCalificacion idAccidenteCalificacion) {
        this.idAccidenteCalificacion = idAccidenteCalificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accidenteCalificacionDet != null ? accidenteCalificacionDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteCalificacionDet)) {
            return false;
        }
        AccidenteCalificacionDet other = (AccidenteCalificacionDet) object;
        if ((this.accidenteCalificacionDet == null && other.accidenteCalificacionDet != null) || (this.accidenteCalificacionDet != null && !this.accidenteCalificacionDet.equals(other.accidenteCalificacionDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteCalificacionDet[ accidenteCalificacionDet=" + accidenteCalificacionDet + " ]";
    }
    
}
