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
@Table(name = "acc_informe_ope_causalidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeOpeCausalidad.findAll", query = "SELECT a FROM AccInformeOpeCausalidad a")
    , @NamedQuery(name = "AccInformeOpeCausalidad.findByIdAccInformeOpeCausalidad", query = "SELECT a FROM AccInformeOpeCausalidad a WHERE a.idAccInformeOpeCausalidad = :idAccInformeOpeCausalidad")})
public class AccInformeOpeCausalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_ope_causalidad")
    private Integer idAccInformeOpeCausalidad;
    @Lob
    @Size(max = 65535)
    @Column(name = "respuesta")
    private String respuesta;
    @JoinColumn(name = "id_causa_raiz", referencedColumnName = "id_acc_causa_raiz")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccCausaRaiz idCausaRaiz;
    @JoinColumn(name = "id_causa_sub", referencedColumnName = "id_acc_subcausa")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccCausaSub idCausaSub;
    @JoinColumn(name = "id_acc_informe_ope", referencedColumnName = "id_acc_informe_ope")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeOpe idAccInformeOpe;

    public AccInformeOpeCausalidad() {
    }

    public AccInformeOpeCausalidad(Integer idAccInformeOpeCausalidad) {
        this.idAccInformeOpeCausalidad = idAccInformeOpeCausalidad;
    }

    public Integer getIdAccInformeOpeCausalidad() {
        return idAccInformeOpeCausalidad;
    }

    public void setIdAccInformeOpeCausalidad(Integer idAccInformeOpeCausalidad) {
        this.idAccInformeOpeCausalidad = idAccInformeOpeCausalidad;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public AccCausaRaiz getIdCausaRaiz() {
        return idCausaRaiz;
    }

    public void setIdCausaRaiz(AccCausaRaiz idCausaRaiz) {
        this.idCausaRaiz = idCausaRaiz;
    }

    public AccCausaSub getIdCausaSub() {
        return idCausaSub;
    }

    public void setIdCausaSub(AccCausaSub idCausaSub) {
        this.idCausaSub = idCausaSub;
    }

    public AccInformeOpe getIdAccInformeOpe() {
        return idAccInformeOpe;
    }

    public void setIdAccInformeOpe(AccInformeOpe idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInformeOpeCausalidad != null ? idAccInformeOpeCausalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeOpeCausalidad)) {
            return false;
        }
        AccInformeOpeCausalidad other = (AccInformeOpeCausalidad) object;
        if ((this.idAccInformeOpeCausalidad == null && other.idAccInformeOpeCausalidad != null) || (this.idAccInformeOpeCausalidad != null && !this.idAccInformeOpeCausalidad.equals(other.idAccInformeOpeCausalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeOpeCausalidad[ idAccInformeOpeCausalidad=" + idAccInformeOpeCausalidad + " ]";
    }
    
}
