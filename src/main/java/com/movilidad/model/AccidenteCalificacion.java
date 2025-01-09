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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "accidente_calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteCalificacion.findAll", query = "SELECT a FROM AccidenteCalificacion a")
    , @NamedQuery(name = "AccidenteCalificacion.findByIdAccidenteCalificacion", query = "SELECT a FROM AccidenteCalificacion a WHERE a.idAccidenteCalificacion = :idAccidenteCalificacion")
    , @NamedQuery(name = "AccidenteCalificacion.findByFechaCalificacion", query = "SELECT a FROM AccidenteCalificacion a WHERE a.fechaCalificacion = :fechaCalificacion")
    , @NamedQuery(name = "AccidenteCalificacion.findByPinReunion", query = "SELECT a FROM AccidenteCalificacion a WHERE a.pinReunion = :pinReunion")
    , @NamedQuery(name = "AccidenteCalificacion.findByCalificado", query = "SELECT a FROM AccidenteCalificacion a WHERE a.calificado = :calificado")
    , @NamedQuery(name = "AccidenteCalificacion.findByUsername", query = "SELECT a FROM AccidenteCalificacion a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteCalificacion.findByCreado", query = "SELECT a FROM AccidenteCalificacion a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteCalificacion.findByModificado", query = "SELECT a FROM AccidenteCalificacion a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteCalificacion.findByEstadoReg", query = "SELECT a FROM AccidenteCalificacion a WHERE a.estadoReg = :estadoReg")})
public class AccidenteCalificacion implements Serializable {

    @OneToMany(mappedBy = "idAccidenteCalificacion", fetch = FetchType.LAZY)
    private List<AccidentePreCalificacion> accidentePreCalificacionList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_calificacion")
    private Integer idAccidenteCalificacion;
    @Column(name = "fecha_calificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCalificacion;
    @Column(name = "pin_reunion")
    private Integer pinReunion;
    @Column(name = "calificado")
    private Integer calificado;
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
    @OneToMany(mappedBy = "idAccidenteCalificacion", fetch = FetchType.LAZY)
    private List<AccidenteCalificacionDet> accidenteCalificacionDetList;

    public AccidenteCalificacion() {
    }

    public AccidenteCalificacion(Integer idAccidenteCalificacion) {
        this.idAccidenteCalificacion = idAccidenteCalificacion;
    }

    public Integer getIdAccidenteCalificacion() {
        return idAccidenteCalificacion;
    }

    public void setIdAccidenteCalificacion(Integer idAccidenteCalificacion) {
        this.idAccidenteCalificacion = idAccidenteCalificacion;
    }

    public Date getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(Date fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public Integer getPinReunion() {
        return pinReunion;
    }

    public void setPinReunion(Integer pinReunion) {
        this.pinReunion = pinReunion;
    }

    public Integer getCalificado() {
        return calificado;
    }

    public void setCalificado(Integer calificado) {
        this.calificado = calificado;
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

    @XmlTransient
    public List<AccidenteCalificacionDet> getAccidenteCalificacionDetList() {
        return accidenteCalificacionDetList;
    }

    public void setAccidenteCalificacionDetList(List<AccidenteCalificacionDet> accidenteCalificacionDetList) {
        this.accidenteCalificacionDetList = accidenteCalificacionDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteCalificacion != null ? idAccidenteCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteCalificacion)) {
            return false;
        }
        AccidenteCalificacion other = (AccidenteCalificacion) object;
        if ((this.idAccidenteCalificacion == null && other.idAccidenteCalificacion != null) || (this.idAccidenteCalificacion != null && !this.idAccidenteCalificacion.equals(other.idAccidenteCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteCalificacion[ idAccidenteCalificacion=" + idAccidenteCalificacion + " ]";
    }

    @XmlTransient
    public List<AccidentePreCalificacion> getAccidentePreCalificacionList() {
        return accidentePreCalificacionList;
    }

    public void setAccidentePreCalificacionList(List<AccidentePreCalificacion> accidentePreCalificacionList) {
        this.accidentePreCalificacionList = accidentePreCalificacionList;
    }
    
}
