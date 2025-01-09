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
@Table(name = "contable_cta_vehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContableCtaVehiculo.findAll", query = "SELECT c FROM ContableCtaVehiculo c")
    , @NamedQuery(name = "ContableCtaVehiculo.findByIdContableCtaVehiculo", query = "SELECT c FROM ContableCtaVehiculo c WHERE c.idContableCtaVehiculo = :idContableCtaVehiculo")
    , @NamedQuery(name = "ContableCtaVehiculo.findByNroCta", query = "SELECT c FROM ContableCtaVehiculo c WHERE c.nroCta = :nroCta")
    , @NamedQuery(name = "ContableCtaVehiculo.findByConcepto", query = "SELECT c FROM ContableCtaVehiculo c WHERE c.concepto = :concepto")
    , @NamedQuery(name = "ContableCtaVehiculo.findByDescripcion", query = "SELECT c FROM ContableCtaVehiculo c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "ContableCtaVehiculo.findByUsername", query = "SELECT c FROM ContableCtaVehiculo c WHERE c.username = :username")
    , @NamedQuery(name = "ContableCtaVehiculo.findByCreado", query = "SELECT c FROM ContableCtaVehiculo c WHERE c.creado = :creado")
    , @NamedQuery(name = "ContableCtaVehiculo.findByModificado", query = "SELECT c FROM ContableCtaVehiculo c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "ContableCtaVehiculo.findByEstadoReg", query = "SELECT c FROM ContableCtaVehiculo c WHERE c.estadoReg = :estadoReg")})
public class ContableCtaVehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contable_cta_vehiculo")
    private Integer idContableCtaVehiculo;
    @Size(max = 45)
    @Column(name = "nro_cta")
    private String nroCta;
    @Size(max = 45)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @OneToMany(mappedBy = "idContableCtaVehiculo", fetch = FetchType.LAZY)
    private List<ContableRptFiduciaDist> contableRptFiduciaDistList;

    public ContableCtaVehiculo() {
    }

    public ContableCtaVehiculo(Integer idContableCtaVehiculo) {
        this.idContableCtaVehiculo = idContableCtaVehiculo;
    }

    public Integer getIdContableCtaVehiculo() {
        return idContableCtaVehiculo;
    }

    public void setIdContableCtaVehiculo(Integer idContableCtaVehiculo) {
        this.idContableCtaVehiculo = idContableCtaVehiculo;
    }

    public String getNroCta() {
        return nroCta;
    }

    public void setNroCta(String nroCta) {
        this.nroCta = nroCta;
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

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @XmlTransient
    public List<ContableRptFiduciaDist> getContableRptFiduciaDistList() {
        return contableRptFiduciaDistList;
    }

    public void setContableRptFiduciaDistList(List<ContableRptFiduciaDist> contableRptFiduciaDistList) {
        this.contableRptFiduciaDistList = contableRptFiduciaDistList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContableCtaVehiculo != null ? idContableCtaVehiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContableCtaVehiculo)) {
            return false;
        }
        ContableCtaVehiculo other = (ContableCtaVehiculo) object;
        if ((this.idContableCtaVehiculo == null && other.idContableCtaVehiculo != null) || (this.idContableCtaVehiculo != null && !this.idContableCtaVehiculo.equals(other.idContableCtaVehiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ContableCtaVehiculo[ idContableCtaVehiculo=" + idContableCtaVehiculo + " ]";
    }
    
}
