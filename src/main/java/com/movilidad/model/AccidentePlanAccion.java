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
@Table(name = "accidente_plan_accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidentePlanAccion.findAll", query = "SELECT a FROM AccidentePlanAccion a")
    , @NamedQuery(name = "AccidentePlanAccion.findByIdAccidentePlanAccion", query = "SELECT a FROM AccidentePlanAccion a WHERE a.idAccidentePlanAccion = :idAccidentePlanAccion")
    , @NamedQuery(name = "AccidentePlanAccion.findByUsername", query = "SELECT a FROM AccidentePlanAccion a WHERE a.username = :username")
    , @NamedQuery(name = "AccidentePlanAccion.findByCreado", query = "SELECT a FROM AccidentePlanAccion a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidentePlanAccion.findByModificado", query = "SELECT a FROM AccidentePlanAccion a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidentePlanAccion.findByEstadoReg", query = "SELECT a FROM AccidentePlanAccion a WHERE a.estadoReg = :estadoReg")})
public class AccidentePlanAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_plan_accion")
    private Integer idAccidentePlanAccion;
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
    @JoinColumn(name = "id_acc_plan_accion", referencedColumnName = "id_acc_plan_accion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AccPlanAccion idAccPlanAccion;
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Accidente idAccidente;

    public AccidentePlanAccion() {
    }

    public AccidentePlanAccion(Integer idAccidentePlanAccion) {
        this.idAccidentePlanAccion = idAccidentePlanAccion;
    }

    public Integer getIdAccidentePlanAccion() {
        return idAccidentePlanAccion;
    }

    public void setIdAccidentePlanAccion(Integer idAccidentePlanAccion) {
        this.idAccidentePlanAccion = idAccidentePlanAccion;
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

    public AccPlanAccion getIdAccPlanAccion() {
        return idAccPlanAccion;
    }

    public void setIdAccPlanAccion(AccPlanAccion idAccPlanAccion) {
        this.idAccPlanAccion = idAccPlanAccion;
    }

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidentePlanAccion != null ? idAccidentePlanAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidentePlanAccion)) {
            return false;
        }
        AccidentePlanAccion other = (AccidentePlanAccion) object;
        if ((this.idAccidentePlanAccion == null && other.idAccidentePlanAccion != null) || (this.idAccidentePlanAccion != null && !this.idAccidentePlanAccion.equals(other.idAccidentePlanAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidentePlanAccion[ idAccidentePlanAccion=" + idAccidentePlanAccion + " ]";
    }
    
}
