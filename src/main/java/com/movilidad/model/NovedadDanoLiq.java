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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "novedad_dano_liq")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadDanoLiq.findAll", query = "SELECT n FROM NovedadDanoLiq n")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByIdNovedadDanoLiq", query = "SELECT n FROM NovedadDanoLiq n WHERE n.idNovedadDanoLiq = :idNovedadDanoLiq")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByFechaLiq", query = "SELECT n FROM NovedadDanoLiq n WHERE n.fechaLiq = :fechaLiq")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByDesde", query = "SELECT n FROM NovedadDanoLiq n WHERE n.desde = :desde")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByHasta", query = "SELECT n FROM NovedadDanoLiq n WHERE n.hasta = :hasta")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByLiquidado", query = "SELECT n FROM NovedadDanoLiq n WHERE n.liquidado = :liquidado")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findBySumCostoParam", query = "SELECT n FROM NovedadDanoLiq n WHERE n.sumCostoParam = :sumCostoParam")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findBySumCostoAjustado", query = "SELECT n FROM NovedadDanoLiq n WHERE n.sumCostoAjustado = :sumCostoAjustado")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByUsername", query = "SELECT n FROM NovedadDanoLiq n WHERE n.username = :username")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByCreado", query = "SELECT n FROM NovedadDanoLiq n WHERE n.creado = :creado")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByModificado", query = "SELECT n FROM NovedadDanoLiq n WHERE n.modificado = :modificado")
    ,
    @NamedQuery(name = "NovedadDanoLiq.findByEstadoReg", query = "SELECT n FROM NovedadDanoLiq n WHERE n.estadoReg = :estadoReg")})
public class NovedadDanoLiq implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadDanoLiq", fetch = FetchType.LAZY)
    private List<NovedadDanoLiqDet> novedadDanoLiqDetList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_dano_liq")
    private Integer idNovedadDanoLiq;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_liq")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLiq;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Column(name = "liquidado")
    private Integer liquidado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sum_costo_param")
    private BigDecimal sumCostoParam;
    @Column(name = "sum_costo_ajustado")
    private BigDecimal sumCostoAjustado;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public NovedadDanoLiq() {
    }

    public NovedadDanoLiq(Integer idNovedadDanoLiq) {
        this.idNovedadDanoLiq = idNovedadDanoLiq;
    }

    public NovedadDanoLiq(Integer idNovedadDanoLiq, Date fechaLiq, Date desde, Date hasta) {
        this.idNovedadDanoLiq = idNovedadDanoLiq;
        this.fechaLiq = fechaLiq;
        this.desde = desde;
        this.hasta = hasta;
    }

    public Integer getIdNovedadDanoLiq() {
        return idNovedadDanoLiq;
    }

    public void setIdNovedadDanoLiq(Integer idNovedadDanoLiq) {
        this.idNovedadDanoLiq = idNovedadDanoLiq;
    }

    public Date getFechaLiq() {
        return fechaLiq;
    }

    public void setFechaLiq(Date fechaLiq) {
        this.fechaLiq = fechaLiq;
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

    public Integer getLiquidado() {
        return liquidado;
    }

    public void setLiquidado(Integer liquidado) {
        this.liquidado = liquidado;
    }

    public BigDecimal getSumCostoParam() {
        return sumCostoParam;
    }

    public void setSumCostoParam(BigDecimal sumCostoParam) {
        this.sumCostoParam = sumCostoParam;
    }

    public BigDecimal getSumCostoAjustado() {
        return sumCostoAjustado;
    }

    public void setSumCostoAjustado(BigDecimal sumCostoAjustado) {
        this.sumCostoAjustado = sumCostoAjustado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadDanoLiq != null ? idNovedadDanoLiq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadDanoLiq)) {
            return false;
        }
        NovedadDanoLiq other = (NovedadDanoLiq) object;
        if ((this.idNovedadDanoLiq == null && other.idNovedadDanoLiq != null) || (this.idNovedadDanoLiq != null && !this.idNovedadDanoLiq.equals(other.idNovedadDanoLiq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadDanoLiq[ idNovedadDanoLiq=" + idNovedadDanoLiq + " ]";
    }

    @XmlTransient
    public List<NovedadDanoLiqDet> getNovedadDanoLiqDetList() {
        return novedadDanoLiqDetList;
    }

    public void setNovedadDanoLiqDetList(List<NovedadDanoLiqDet> novedadDanoLiqDetList) {
        this.novedadDanoLiqDetList = novedadDanoLiqDetList;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

}
