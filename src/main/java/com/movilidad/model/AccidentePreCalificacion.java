/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "accidente_pre_calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidentePreCalificacion.findAll", query = "SELECT a FROM AccidentePreCalificacion a")
    , @NamedQuery(name = "AccidentePreCalificacion.findByIdAccidentePreCalificacion", query = "SELECT a FROM AccidentePreCalificacion a WHERE a.idAccidentePreCalificacion = :idAccidentePreCalificacion")})
public class AccidentePreCalificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_pre_calificacion")
    private Integer idAccidentePreCalificacion;
    @JoinColumn(name = "id_causaraiz", referencedColumnName = "id_acc_causa_raiz")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccCausaRaiz idCausaraiz;
    @JoinColumn(name = "id_causasub", referencedColumnName = "id_acc_subcausa")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccCausaSub idCausasub;
    @JoinColumn(name = "id_accidente_calificacion", referencedColumnName = "id_accidente_calificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccidenteCalificacion idAccidenteCalificacion;

    public AccidentePreCalificacion() {
    }

    public AccidentePreCalificacion(Integer idAccidentePreCalificacion) {
        this.idAccidentePreCalificacion = idAccidentePreCalificacion;
    }

    public Integer getIdAccidentePreCalificacion() {
        return idAccidentePreCalificacion;
    }

    public void setIdAccidentePreCalificacion(Integer idAccidentePreCalificacion) {
        this.idAccidentePreCalificacion = idAccidentePreCalificacion;
    }

    public AccCausaRaiz getIdCausaraiz() {
        return idCausaraiz;
    }

    public void setIdCausaraiz(AccCausaRaiz idCausaraiz) {
        this.idCausaraiz = idCausaraiz;
    }

    public AccCausaSub getIdCausasub() {
        return idCausasub;
    }

    public void setIdCausasub(AccCausaSub idCausasub) {
        this.idCausasub = idCausasub;
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
        hash += (idAccidentePreCalificacion != null ? idAccidentePreCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidentePreCalificacion)) {
            return false;
        }
        AccidentePreCalificacion other = (AccidentePreCalificacion) object;
        if ((this.idAccidentePreCalificacion == null && other.idAccidentePreCalificacion != null) || (this.idAccidentePreCalificacion != null && !this.idAccidentePreCalificacion.equals(other.idAccidentePreCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidentePreCalificacion[ idAccidentePreCalificacion=" + idAccidentePreCalificacion + " ]";
    }
    
}
