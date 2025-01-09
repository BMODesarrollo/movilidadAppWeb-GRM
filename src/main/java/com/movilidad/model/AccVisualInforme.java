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
@Table(name = "acc_visual_informe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccVisualInforme.findAll", query = "SELECT a FROM AccVisualInforme a")
    , @NamedQuery(name = "AccVisualInforme.findByIdAccVisualInforme", query = "SELECT a FROM AccVisualInforme a WHERE a.idAccVisualInforme = :idAccVisualInforme")})
public class AccVisualInforme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_visual_informe")
    private Integer idAccVisualInforme;
    @JoinColumn(name = "id_acc_informe_ope", referencedColumnName = "id_acc_informe_ope")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeOpe idAccInformeOpe;
    @JoinColumn(name = "id_acc_via_visual", referencedColumnName = "id_acc_via_visual")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaVisual idAccViaVisual;

    public AccVisualInforme() {
    }

    public AccVisualInforme(Integer idAccVisualInforme) {
        this.idAccVisualInforme = idAccVisualInforme;
    }

    public Integer getIdAccVisualInforme() {
        return idAccVisualInforme;
    }

    public void setIdAccVisualInforme(Integer idAccVisualInforme) {
        this.idAccVisualInforme = idAccVisualInforme;
    }

    public AccInformeOpe getIdAccInformeOpe() {
        return idAccInformeOpe;
    }

    public void setIdAccInformeOpe(AccInformeOpe idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }

    public AccViaVisual getIdAccViaVisual() {
        return idAccViaVisual;
    }

    public void setIdAccViaVisual(AccViaVisual idAccViaVisual) {
        this.idAccViaVisual = idAccViaVisual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccVisualInforme != null ? idAccVisualInforme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccVisualInforme)) {
            return false;
        }
        AccVisualInforme other = (AccVisualInforme) object;
        if ((this.idAccVisualInforme == null && other.idAccVisualInforme != null) || (this.idAccVisualInforme != null && !this.idAccVisualInforme.equals(other.idAccVisualInforme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccVisualInforme[ idAccVisualInforme=" + idAccVisualInforme + " ]";
    }
    
}
