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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_informe_master_apoyo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterApoyo.findAll", query = "SELECT a FROM AccInformeMasterApoyo a")
    , @NamedQuery(name = "AccInformeMasterApoyo.findByIdAccInformeMasterApoyo", query = "SELECT a FROM AccInformeMasterApoyo a WHERE a.idAccInformeMasterApoyo = :idAccInformeMasterApoyo")})
public class AccInformeMasterApoyo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_apoyo")
    private Integer idAccInformeMasterApoyo;
    @Lob
    @Size(max = 65535)
    @Column(name = "firma")
    private String firma;
    @JoinColumn(name = "id_acc_informe_master", referencedColumnName = "id_acc_informe_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeMaster idAccInformeMaster;
    @JoinColumn(name = "id_master", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idMaster;

    public AccInformeMasterApoyo() {
    }

    public AccInformeMasterApoyo(Integer idAccInformeMasterApoyo) {
        this.idAccInformeMasterApoyo = idAccInformeMasterApoyo;
    }

    public Integer getIdAccInformeMasterApoyo() {
        return idAccInformeMasterApoyo;
    }

    public void setIdAccInformeMasterApoyo(Integer idAccInformeMasterApoyo) {
        this.idAccInformeMasterApoyo = idAccInformeMasterApoyo;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public AccInformeMaster getIdAccInformeMaster() {
        return idAccInformeMaster;
    }

    public void setIdAccInformeMaster(AccInformeMaster idAccInformeMaster) {
        this.idAccInformeMaster = idAccInformeMaster;
    }

    public Empleado getIdMaster() {
        return idMaster;
    }

    public void setIdMaster(Empleado idMaster) {
        this.idMaster = idMaster;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInformeMasterApoyo != null ? idAccInformeMasterApoyo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterApoyo)) {
            return false;
        }
        AccInformeMasterApoyo other = (AccInformeMasterApoyo) object;
        if ((this.idAccInformeMasterApoyo == null && other.idAccInformeMasterApoyo != null) || (this.idAccInformeMasterApoyo != null && !this.idAccInformeMasterApoyo.equals(other.idAccInformeMasterApoyo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterApoyo[ idAccInformeMasterApoyo=" + idAccInformeMasterApoyo + " ]";
    }
    
}
