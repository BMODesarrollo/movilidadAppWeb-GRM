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
 * @author HP
 */
@Entity
@Table(name = "accidente_lugar_demar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteLugarDemar.findAll", query = "SELECT a FROM AccidenteLugarDemar a")
    , @NamedQuery(name = "AccidenteLugarDemar.findByIdAccidenteLugarDemar", query = "SELECT a FROM AccidenteLugarDemar a WHERE a.idAccidenteLugarDemar = :idAccidenteLugarDemar")
    , @NamedQuery(name = "AccidenteLugarDemar.findByIdAccInformeOpe", query = "SELECT a FROM AccidenteLugarDemar a WHERE a.idAccInformeOpe = :idAccInformeOpe")})
public class AccidenteLugarDemar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_lugar_demar")
    private Integer idAccidenteLugarDemar;
    @JoinColumn(name = "id_accidente_lugar", referencedColumnName = "id_accidente_lugar")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccidenteLugar idAccidenteLugar;
    @JoinColumn(name = "id_acc_via_demarcacion", referencedColumnName = "id_acc_via_demarcacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaDemarcacion idAccViaDemarcacion;
    @Column(name = "id_acc_informe_ope")
    private Integer idAccInformeOpe;

    public AccidenteLugarDemar() {
    }

    public AccidenteLugarDemar(Integer idAccidenteLugarDemar) {
        this.idAccidenteLugarDemar = idAccidenteLugarDemar;
    }

    public Integer getIdAccidenteLugarDemar() {
        return idAccidenteLugarDemar;
    }

    public void setIdAccidenteLugarDemar(Integer idAccidenteLugarDemar) {
        this.idAccidenteLugarDemar = idAccidenteLugarDemar;
    }

    public AccidenteLugar getIdAccidenteLugar() {
        return idAccidenteLugar;
    }

    public void setIdAccidenteLugar(AccidenteLugar idAccidenteLugar) {
        this.idAccidenteLugar = idAccidenteLugar;
    }

    public AccViaDemarcacion getIdAccViaDemarcacion() {
        return idAccViaDemarcacion;
    }

    public void setIdAccViaDemarcacion(AccViaDemarcacion idAccViaDemarcacion) {
        this.idAccViaDemarcacion = idAccViaDemarcacion;
    }

    public Integer getIdAccInformeOpe() {
        return idAccInformeOpe;
    }

    public void setIdAccInformeOpe(Integer idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteLugarDemar != null ? idAccidenteLugarDemar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteLugarDemar)) {
            return false;
        }
        AccidenteLugarDemar other = (AccidenteLugarDemar) object;
        if ((this.idAccidenteLugarDemar == null && other.idAccidenteLugarDemar != null) || (this.idAccidenteLugarDemar != null && !this.idAccidenteLugarDemar.equals(other.idAccidenteLugarDemar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteLugarDemar[ idAccidenteLugarDemar=" + idAccidenteLugarDemar + " ]";
    }
    
}
