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
@Table(name = "acc_choque_informe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccChoqueInforme.findAll", query = "SELECT a FROM AccChoqueInforme a")
    , @NamedQuery(name = "AccChoqueInforme.findByIdAccChoqueInforme", query = "SELECT a FROM AccChoqueInforme a WHERE a.idAccChoqueInforme = :idAccChoqueInforme")})
public class AccChoqueInforme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_choque_informe")
    private Integer idAccChoqueInforme;
    @JoinColumn(name = "id_acc_informe_ope", referencedColumnName = "id_acc_informe_ope")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeOpe idAccInformeOpe;
    @JoinColumn(name = "id_acc_tipo_vehiculo", referencedColumnName = "id_acc_tipo_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoVehiculo idAccTipoVehiculo;

    public AccChoqueInforme() {
    }

    public AccChoqueInforme(Integer idAccChoqueInforme) {
        this.idAccChoqueInforme = idAccChoqueInforme;
    }

    public Integer getIdAccChoqueInforme() {
        return idAccChoqueInforme;
    }

    public void setIdAccChoqueInforme(Integer idAccChoqueInforme) {
        this.idAccChoqueInforme = idAccChoqueInforme;
    }

    public AccInformeOpe getIdAccInformeOpe() {
        return idAccInformeOpe;
    }

    public void setIdAccInformeOpe(AccInformeOpe idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }

    public AccTipoVehiculo getIdAccTipoVehiculo() {
        return idAccTipoVehiculo;
    }

    public void setIdAccTipoVehiculo(AccTipoVehiculo idAccTipoVehiculo) {
        this.idAccTipoVehiculo = idAccTipoVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccChoqueInforme != null ? idAccChoqueInforme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccChoqueInforme)) {
            return false;
        }
        AccChoqueInforme other = (AccChoqueInforme) object;
        if ((this.idAccChoqueInforme == null && other.idAccChoqueInforme != null) || (this.idAccChoqueInforme != null && !this.idAccChoqueInforme.equals(other.idAccChoqueInforme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccChoqueInforme[ idAccChoqueInforme=" + idAccChoqueInforme + " ]";
    }
    
}
