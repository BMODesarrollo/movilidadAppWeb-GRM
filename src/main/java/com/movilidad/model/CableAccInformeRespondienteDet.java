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
 * @author soluciones-it
 */
@Entity
@Table(name = "cable_acc_informe_respondiente_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableAccInformeRespondienteDet.findAll", query = "SELECT c FROM CableAccInformeRespondienteDet c")
    , @NamedQuery(name = "CableAccInformeRespondienteDet.findByIdCableAccInformeRespondienteDet", query = "SELECT c FROM CableAccInformeRespondienteDet c WHERE c.idCableAccInformeRespondienteDet = :idCableAccInformeRespondienteDet")
    , @NamedQuery(name = "CableAccInformeRespondienteDet.findByRespuesta", query = "SELECT c FROM CableAccInformeRespondienteDet c WHERE c.respuesta = :respuesta")})
public class CableAccInformeRespondienteDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_acc_informe_respondiente_det")
    private Integer idCableAccInformeRespondienteDet;
    @Lob
    @Size(max = 65535)
    @Column(name = "respuesta")
    private String respuesta;
    @JoinColumn(name = "id_cable_acc_informe_pregunta", referencedColumnName = "id_cable_acc_informe_pregunta")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccInformePregunta idCableAccInformePregunta;
    @JoinColumn(name = "id_cable_acc_informe_respondiente", referencedColumnName = "id_cable_acc_informe_respondiente")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccInformeRespondiente idCableAccInformeRespondiente;

    public CableAccInformeRespondienteDet() {
    }

    public CableAccInformeRespondienteDet(Integer idCableAccInformeRespondienteDet) {
        this.idCableAccInformeRespondienteDet = idCableAccInformeRespondienteDet;
    }

    public Integer getIdCableAccInformeRespondienteDet() {
        return idCableAccInformeRespondienteDet;
    }

    public void setIdCableAccInformeRespondienteDet(Integer idCableAccInformeRespondienteDet) {
        this.idCableAccInformeRespondienteDet = idCableAccInformeRespondienteDet;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public CableAccInformePregunta getIdCableAccInformePregunta() {
        return idCableAccInformePregunta;
    }

    public void setIdCableAccInformePregunta(CableAccInformePregunta idCableAccInformePregunta) {
        this.idCableAccInformePregunta = idCableAccInformePregunta;
    }

    public CableAccInformeRespondiente getIdCableAccInformeRespondiente() {
        return idCableAccInformeRespondiente;
    }

    public void setIdCableAccInformeRespondiente(CableAccInformeRespondiente idCableAccInformeRespondiente) {
        this.idCableAccInformeRespondiente = idCableAccInformeRespondiente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccInformeRespondienteDet != null ? idCableAccInformeRespondienteDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccInformeRespondienteDet)) {
            return false;
        }
        CableAccInformeRespondienteDet other = (CableAccInformeRespondienteDet) object;
        if ((this.idCableAccInformeRespondienteDet == null && other.idCableAccInformeRespondienteDet != null) || (this.idCableAccInformeRespondienteDet != null && !this.idCableAccInformeRespondienteDet.equals(other.idCableAccInformeRespondienteDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccInformeRespondienteDet[ idCableAccInformeRespondienteDet=" + idCableAccInformeRespondienteDet + " ]";
    }

}
