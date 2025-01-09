/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "contable_rpt_fiducia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContableRptFiducia.findAll", query = "SELECT c FROM ContableRptFiducia c")
    , @NamedQuery(name = "ContableRptFiducia.findByIdContableRptFiducia", query = "SELECT c FROM ContableRptFiducia c WHERE c.idContableRptFiducia = :idContableRptFiducia")
    , @NamedQuery(name = "ContableRptFiducia.findByDesde", query = "SELECT c FROM ContableRptFiducia c WHERE c.desde = :desde")
    , @NamedQuery(name = "ContableRptFiducia.findByHasta", query = "SELECT c FROM ContableRptFiducia c WHERE c.hasta = :hasta")
    , @NamedQuery(name = "ContableRptFiducia.findByVrKmArt", query = "SELECT c FROM ContableRptFiducia c WHERE c.vrKmArt = :vrKmArt")
    , @NamedQuery(name = "ContableRptFiducia.findByVrKmBi", query = "SELECT c FROM ContableRptFiducia c WHERE c.vrKmBi = :vrKmBi")
    , @NamedQuery(name = "ContableRptFiducia.findByIngresosArt", query = "SELECT c FROM ContableRptFiducia c WHERE c.ingresosArt = :ingresosArt")
    , @NamedQuery(name = "ContableRptFiducia.findByEgresosArt", query = "SELECT c FROM ContableRptFiducia c WHERE c.egresosArt = :egresosArt")
    , @NamedQuery(name = "ContableRptFiducia.findByIngresosBi", query = "SELECT c FROM ContableRptFiducia c WHERE c.ingresosBi = :ingresosBi")
    , @NamedQuery(name = "ContableRptFiducia.findByEgresosBi", query = "SELECT c FROM ContableRptFiducia c WHERE c.egresosBi = :egresosBi")
    , @NamedQuery(name = "ContableRptFiducia.findByTotal", query = "SELECT c FROM ContableRptFiducia c WHERE c.total = :total")
    , @NamedQuery(name = "ContableRptFiducia.findByObservacion", query = "SELECT c FROM ContableRptFiducia c WHERE c.observacion = :observacion")
    , @NamedQuery(name = "ContableRptFiducia.findByDistribuido", query = "SELECT c FROM ContableRptFiducia c WHERE c.distribuido = :distribuido")
    , @NamedQuery(name = "ContableRptFiducia.findByUsername", query = "SELECT c FROM ContableRptFiducia c WHERE c.username = :username")
    , @NamedQuery(name = "ContableRptFiducia.findByCreado", query = "SELECT c FROM ContableRptFiducia c WHERE c.creado = :creado")
    , @NamedQuery(name = "ContableRptFiducia.findByModificado", query = "SELECT c FROM ContableRptFiducia c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "ContableRptFiducia.findByEstadoReg", query = "SELECT c FROM ContableRptFiducia c WHERE c.estadoReg = :estadoReg")})
public class ContableRptFiducia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contable_rpt_fiducia")
    private Integer idContableRptFiducia;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vr_km_art")
    private BigDecimal vrKmArt;
    @Column(name = "vr_km_bi")
    private BigDecimal vrKmBi;
    @Column(name = "ingresos_art")
    private BigDecimal ingresosArt;
    @Column(name = "egresos_art")
    private BigDecimal egresosArt;
    @Column(name = "ingresos_bi")
    private BigDecimal ingresosBi;
    @Column(name = "egresos_bi")
    private BigDecimal egresosBi;
    @Column(name = "total")
    private BigDecimal total;
    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 255)
    @Column(name = "path_documento")
    private String pathDocumento;
    @Column(name = "distribuido")
    private Integer distribuido;
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
    @OneToMany(mappedBy = "idContableRptFiducia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ContableRptFiduciaDist> contableRptFiduciaDistList;
    @OneToMany(mappedBy = "idContableRptFiducia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ContableRptFiduciaDet> contableRptFiduciaDetList;

    public ContableRptFiducia() {
    }

    public ContableRptFiducia(Integer idContableRptFiducia) {
        this.idContableRptFiducia = idContableRptFiducia;
    }

    public Integer getIdContableRptFiducia() {
        return idContableRptFiducia;
    }

    public void setIdContableRptFiducia(Integer idContableRptFiducia) {
        this.idContableRptFiducia = idContableRptFiducia;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public BigDecimal getVrKmArt() {
        return vrKmArt;
    }

    public void setVrKmArt(BigDecimal vrKmArt) {
        this.vrKmArt = vrKmArt;
    }

    public BigDecimal getVrKmBi() {
        return vrKmBi;
    }

    public void setVrKmBi(BigDecimal vrKmBi) {
        this.vrKmBi = vrKmBi;
    }

    public BigDecimal getIngresosArt() {
        return ingresosArt;
    }

    public void setIngresosArt(BigDecimal ingresosArt) {
        this.ingresosArt = ingresosArt;
    }

    public BigDecimal getEgresosArt() {
        return egresosArt;
    }

    public void setEgresosArt(BigDecimal egresosArt) {
        this.egresosArt = egresosArt;
    }

    public BigDecimal getIngresosBi() {
        return ingresosBi;
    }

    public void setIngresosBi(BigDecimal ingresosBi) {
        this.ingresosBi = ingresosBi;
    }

    public BigDecimal getEgresosBi() {
        return egresosBi;
    }

    public void setEgresosBi(BigDecimal egresosBi) {
        this.egresosBi = egresosBi;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getDistribuido() {
        return distribuido;
    }

    public void setDistribuido(Integer distribuido) {
        this.distribuido = distribuido;
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

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    @XmlTransient
    public List<ContableRptFiduciaDist> getContableRptFiduciaDistList() {
        return contableRptFiduciaDistList;
    }

    public void setContableRptFiduciaDistList(List<ContableRptFiduciaDist> contableRptFiduciaDistList) {
        this.contableRptFiduciaDistList = contableRptFiduciaDistList;
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
        hash += (idContableRptFiducia != null ? idContableRptFiducia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContableRptFiducia)) {
            return false;
        }
        ContableRptFiducia other = (ContableRptFiducia) object;
        if ((this.idContableRptFiducia == null && other.idContableRptFiducia != null) || (this.idContableRptFiducia != null && !this.idContableRptFiducia.equals(other.idContableRptFiducia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ContableRptFiducia[ idContableRptFiducia=" + idContableRptFiducia + " ]";
    }

}
