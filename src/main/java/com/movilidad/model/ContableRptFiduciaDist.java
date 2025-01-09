/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "contable_rpt_fiducia_dist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContableRptFiduciaDist.findAll", query = "SELECT c FROM ContableRptFiduciaDist c")
    , @NamedQuery(name = "ContableRptFiduciaDist.findByIdContableRptFiduciaDist", query = "SELECT c FROM ContableRptFiduciaDist c WHERE c.idContableRptFiduciaDist = :idContableRptFiduciaDist")
    , @NamedQuery(name = "ContableRptFiduciaDist.findByValor", query = "SELECT c FROM ContableRptFiduciaDist c WHERE c.valor = :valor")
    , @NamedQuery(name = "ContableRptFiduciaDist.findByKmAplicado", query = "SELECT c FROM ContableRptFiduciaDist c WHERE c.kmAplicado = :kmAplicado")})
public class ContableRptFiduciaDist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contable_rpt_fiducia_dist")
    private Integer idContableRptFiduciaDist;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "km_aplicado")
    private BigDecimal kmAplicado;
    @JoinColumn(name = "id_contable_cta_vehiculo", referencedColumnName = "id_contable_cta_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContableCtaVehiculo idContableCtaVehiculo;
    @JoinColumn(name = "id_contable_rpt_fiducia", referencedColumnName = "id_contable_rpt_fiducia")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContableRptFiducia idContableRptFiducia;

    public ContableRptFiduciaDist() {
    }

    public ContableRptFiduciaDist(Integer idContableRptFiduciaDist) {
        this.idContableRptFiduciaDist = idContableRptFiduciaDist;
    }

    public Integer getIdContableRptFiduciaDist() {
        return idContableRptFiduciaDist;
    }

    public void setIdContableRptFiduciaDist(Integer idContableRptFiduciaDist) {
        this.idContableRptFiduciaDist = idContableRptFiduciaDist;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getKmAplicado() {
        return kmAplicado;
    }

    public void setKmAplicado(BigDecimal kmAplicado) {
        this.kmAplicado = kmAplicado;
    }

    public ContableCtaVehiculo getIdContableCtaVehiculo() {
        return idContableCtaVehiculo;
    }

    public void setIdContableCtaVehiculo(ContableCtaVehiculo idContableCtaVehiculo) {
        this.idContableCtaVehiculo = idContableCtaVehiculo;
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
        hash += (idContableRptFiduciaDist != null ? idContableRptFiduciaDist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContableRptFiduciaDist)) {
            return false;
        }
        ContableRptFiduciaDist other = (ContableRptFiduciaDist) object;
        if ((this.idContableRptFiduciaDist == null && other.idContableRptFiduciaDist != null) || (this.idContableRptFiduciaDist != null && !this.idContableRptFiduciaDist.equals(other.idContableRptFiduciaDist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ContableRptFiduciaDist[ idContableRptFiduciaDist=" + idContableRptFiduciaDist + " ]";
    }
    
}
