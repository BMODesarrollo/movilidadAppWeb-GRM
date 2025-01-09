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
 * @author soluciones-it
 */
@Entity
@Table(name = "contable_rpt_fiducia_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContableRptFiduciaDet.findAll", query = "SELECT c FROM ContableRptFiduciaDet c")
    , @NamedQuery(name = "ContableRptFiduciaDet.findByIdContableRptFiduciaDet", query = "SELECT c FROM ContableRptFiduciaDet c WHERE c.idContableRptFiduciaDet = :idContableRptFiduciaDet")
    , @NamedQuery(name = "ContableRptFiduciaDet.findByValor", query = "SELECT c FROM ContableRptFiduciaDet c WHERE c.valor = :valor")
    , @NamedQuery(name = "ContableRptFiduciaDet.findByObservacion", query = "SELECT c FROM ContableRptFiduciaDet c WHERE c.observacion = :observacion")
    , @NamedQuery(name = "ContableRptFiduciaDet.findByUsername", query = "SELECT c FROM ContableRptFiduciaDet c WHERE c.username = :username")
    , @NamedQuery(name = "ContableRptFiduciaDet.findByCreado", query = "SELECT c FROM ContableRptFiduciaDet c WHERE c.creado = :creado")
    , @NamedQuery(name = "ContableRptFiduciaDet.findByModificado", query = "SELECT c FROM ContableRptFiduciaDet c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "ContableRptFiduciaDet.findByEstadoReg", query = "SELECT c FROM ContableRptFiduciaDet c WHERE c.estadoReg = :estadoReg")})
public class ContableRptFiduciaDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contable_rpt_fiducia_det")
    private Integer idContableRptFiduciaDet;
    @Column(name = "valor")
    private Integer valor;
    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_contable_cta", referencedColumnName = "id_contable_cta")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContableCta idContableCta;
    @JoinColumn(name = "id_contable_rpt_fiducia", referencedColumnName = "id_contable_rpt_fiducia")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContableRptFiducia idContableRptFiducia;

    public ContableRptFiduciaDet() {
    }

    public ContableRptFiduciaDet(Integer idContableRptFiduciaDet) {
        this.idContableRptFiduciaDet = idContableRptFiduciaDet;
    }

    public Integer getIdContableRptFiduciaDet() {
        return idContableRptFiduciaDet;
    }

    public void setIdContableRptFiduciaDet(Integer idContableRptFiduciaDet) {
        this.idContableRptFiduciaDet = idContableRptFiduciaDet;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public ContableCta getIdContableCta() {
        return idContableCta;
    }

    public void setIdContableCta(ContableCta idContableCta) {
        this.idContableCta = idContableCta;
    }

    public ContableRptFiducia getIdContableRptFiducia() {
        return idContableRptFiducia;
    }

    public void setIdContableRptFiducia(ContableRptFiducia idContableRptFiducia) {
        this.idContableRptFiducia = idContableRptFiducia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContableRptFiduciaDet != null ? idContableRptFiduciaDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContableRptFiduciaDet)) {
            return false;
        }
        ContableRptFiduciaDet other = (ContableRptFiduciaDet) object;
        if ((this.idContableRptFiduciaDet == null && other.idContableRptFiduciaDet != null) || (this.idContableRptFiduciaDet != null && !this.idContableRptFiduciaDet.equals(other.idContableRptFiduciaDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ContableRptFiduciaDet[ idContableRptFiduciaDet=" + idContableRptFiduciaDet + " ]";
    }
    
}
