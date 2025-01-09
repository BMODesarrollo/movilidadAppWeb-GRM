/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "contable_cta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContableCta.findAll", query = "SELECT c FROM ContableCta c")
    , @NamedQuery(name = "ContableCta.findByIdContableCta", query = "SELECT c FROM ContableCta c WHERE c.idContableCta = :idContableCta")
    , @NamedQuery(name = "ContableCta.findByNroCuenta", query = "SELECT c FROM ContableCta c WHERE c.nroCuenta = :nroCuenta")
    , @NamedQuery(name = "ContableCta.findByConcepto", query = "SELECT c FROM ContableCta c WHERE c.concepto = :concepto")
    , @NamedQuery(name = "ContableCta.findByDescripcion", query = "SELECT c FROM ContableCta c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "ContableCta.findByEsMulta", query = "SELECT c FROM ContableCta c WHERE c.esMulta = :esMulta")
    , @NamedQuery(name = "ContableCta.findByUsername", query = "SELECT c FROM ContableCta c WHERE c.username = :username")
    , @NamedQuery(name = "ContableCta.findByCreado", query = "SELECT c FROM ContableCta c WHERE c.creado = :creado")
    , @NamedQuery(name = "ContableCta.findByModificado", query = "SELECT c FROM ContableCta c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "ContableCta.findByEstadoReg", query = "SELECT c FROM ContableCta c WHERE c.estadoReg = :estadoReg")})
public class ContableCta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contable_cta")
    private Integer idContableCta;
    @Size(max = 45)
    @Column(name = "nro_cuenta")
    private String nroCuenta;
    @Size(max = 45)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "es_multa")
    private Integer esMulta;
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
    @JoinColumn(name = "id_contable_cta_tipo", referencedColumnName = "id_contable_cta_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContableCtaTipo idContableCtaTipo;
    @OneToMany(mappedBy = "idContableCta", fetch = FetchType.LAZY)
    private List<ContableRptFiduciaDet> contableRptFiduciaDetList;

    public ContableCta() {
    }

    public ContableCta(Integer idContableCta) {
        this.idContableCta = idContableCta;
    }

    public Integer getIdContableCta() {
        return idContableCta;
    }

    public void setIdContableCta(Integer idContableCta) {
        this.idContableCta = idContableCta;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEsMulta() {
        return esMulta;
    }

    public void setEsMulta(Integer esMulta) {
        this.esMulta = esMulta;
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

    public ContableCtaTipo getIdContableCtaTipo() {
        return idContableCtaTipo;
    }

    public void setIdContableCtaTipo(ContableCtaTipo idContableCtaTipo) {
        this.idContableCtaTipo = idContableCtaTipo;
    }

    @XmlTransient
    public List<ContableRptFiduciaDet> getContableRptFiduciaDetList() {
        return contableRptFiduciaDetList;
    }

    public void setContableRptFiduciaDetList(List<ContableRptFiduciaDet> contableRptFiduciaDetList) {
        this.contableRptFiduciaDetList = contableRptFiduciaDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContableCta != null ? idContableCta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContableCta)) {
            return false;
        }
        ContableCta other = (ContableCta) object;
        if ((this.idContableCta == null && other.idContableCta != null) || (this.idContableCta != null && !this.idContableCta.equals(other.idContableCta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ContableCta[ idContableCta=" + idContableCta + " ]";
    }
    
}
