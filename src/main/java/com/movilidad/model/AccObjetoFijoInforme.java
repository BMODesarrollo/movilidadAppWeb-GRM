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
@Table(name = "acc_objeto_fijo_informe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccObjetoFijoInforme.findAll", query = "SELECT a FROM AccObjetoFijoInforme a")
    , @NamedQuery(name = "AccObjetoFijoInforme.findByIdAccObjetoFijoInforme", query = "SELECT a FROM AccObjetoFijoInforme a WHERE a.idAccObjetoFijoInforme = :idAccObjetoFijoInforme")})
public class AccObjetoFijoInforme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_objeto_fijo_informe")
    private Integer idAccObjetoFijoInforme;
    @JoinColumn(name = "id_acc_informe_ope", referencedColumnName = "id_acc_informe_ope")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeOpe idAccInformeOpe;
    @JoinColumn(name = "id_acc_objeto_fijo", referencedColumnName = "id_acc_objeto_fijo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccObjetoFijo idAccObjetoFijo;

    public AccObjetoFijoInforme() {
    }

    public AccObjetoFijoInforme(Integer idAccObjetoFijoInforme) {
        this.idAccObjetoFijoInforme = idAccObjetoFijoInforme;
    }

    public Integer getIdAccObjetoFijoInforme() {
        return idAccObjetoFijoInforme;
    }

    public void setIdAccObjetoFijoInforme(Integer idAccObjetoFijoInforme) {
        this.idAccObjetoFijoInforme = idAccObjetoFijoInforme;
    }

    public AccInformeOpe getIdAccInformeOpe() {
        return idAccInformeOpe;
    }

    public void setIdAccInformeOpe(AccInformeOpe idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }

    public AccObjetoFijo getIdAccObjetoFijo() {
        return idAccObjetoFijo;
    }

    public void setIdAccObjetoFijo(AccObjetoFijo idAccObjetoFijo) {
        this.idAccObjetoFijo = idAccObjetoFijo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccObjetoFijoInforme != null ? idAccObjetoFijoInforme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccObjetoFijoInforme)) {
            return false;
        }
        AccObjetoFijoInforme other = (AccObjetoFijoInforme) object;
        if ((this.idAccObjetoFijoInforme == null && other.idAccObjetoFijoInforme != null) || (this.idAccObjetoFijoInforme != null && !this.idAccObjetoFijoInforme.equals(other.idAccObjetoFijoInforme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccObjetoFijoInforme[ idAccObjetoFijoInforme=" + idAccObjetoFijoInforme + " ]";
    }
    
}
