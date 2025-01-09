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
@Table(name = "prg_tc_resumen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgTcResumen.findAll", query = "SELECT p FROM PrgTcResumen p")
    ,
    @NamedQuery(name = "PrgTcResumen.findByIdPrgTcResumen", query = "SELECT p FROM PrgTcResumen p WHERE p.idPrgTcResumen = :idPrgTcResumen")
    ,
    @NamedQuery(name = "PrgTcResumen.findByFecha", query = "SELECT p FROM PrgTcResumen p WHERE p.fecha = :fecha")
    ,
    @NamedQuery(name = "PrgTcResumen.findByTipoDia", query = "SELECT p FROM PrgTcResumen p WHERE p.tipoDia = :tipoDia")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMcomPrg", query = "SELECT p FROM PrgTcResumen p WHERE p.mcomPrg = :mcomPrg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMhlpProg", query = "SELECT p FROM PrgTcResumen p WHERE p.mhlpProg = :mhlpProg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMcomArtPrg", query = "SELECT p FROM PrgTcResumen p WHERE p.mcomArtPrg = :mcomArtPrg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMhlpArtPrg", query = "SELECT p FROM PrgTcResumen p WHERE p.mhlpArtPrg = :mhlpArtPrg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMcomBiPrg", query = "SELECT p FROM PrgTcResumen p WHERE p.mcomBiPrg = :mcomBiPrg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMhlpBiPrg", query = "SELECT p FROM PrgTcResumen p WHERE p.mhlpBiPrg = :mhlpBiPrg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMcomArtCon", query = "SELECT p FROM PrgTcResumen p WHERE p.mcomArtCon = :mcomArtCon")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMcomBiCon", query = "SELECT p FROM PrgTcResumen p WHERE p.mcomBiCon = :mcomBiCon")
    ,
    @NamedQuery(name = "PrgTcResumen.findByVrMcomArtCon", query = "SELECT p FROM PrgTcResumen p WHERE p.vrMcomArtCon = :vrMcomArtCon")
    ,
    @NamedQuery(name = "PrgTcResumen.findByVrMconBiCon", query = "SELECT p FROM PrgTcResumen p WHERE p.vrMconBiCon = :vrMconBiCon")
    ,
    @NamedQuery(name = "PrgTcResumen.findByOperadoresPrg", query = "SELECT p FROM PrgTcResumen p WHERE p.operadoresPrg = :operadoresPrg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByOperadoresEje", query = "SELECT p FROM PrgTcResumen p WHERE p.operadoresEje = :operadoresEje")
    ,
    @NamedQuery(name = "PrgTcResumen.findByVehiculosPrg", query = "SELECT p FROM PrgTcResumen p WHERE p.vehiculosPrg = :vehiculosPrg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByVehiculosEje", query = "SELECT p FROM PrgTcResumen p WHERE p.vehiculosEje = :vehiculosEje")
    ,
    @NamedQuery(name = "PrgTcResumen.findByServiciosPrg", query = "SELECT p FROM PrgTcResumen p WHERE p.serviciosPrg = :serviciosPrg")
    ,
    @NamedQuery(name = "PrgTcResumen.findByServiciosEje", query = "SELECT p FROM PrgTcResumen p WHERE p.serviciosEje = :serviciosEje")
    ,
    @NamedQuery(name = "PrgTcResumen.findByConciliado", query = "SELECT p FROM PrgTcResumen p WHERE p.conciliado = :conciliado")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMelimArt", query = "SELECT p FROM PrgTcResumen p WHERE p.melimArt = :melimArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMelimBi", query = "SELECT p FROM PrgTcResumen p WHERE p.melimBi = :melimBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMhlpArtEje", query = "SELECT p FROM PrgTcResumen p WHERE p.mhlpArtEje = :mhlpArtEje")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMhlpBiEje", query = "SELECT p FROM PrgTcResumen p WHERE p.mhlpBiEje = :mhlpBiEje")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMadicArt", query = "SELECT p FROM PrgTcResumen p WHERE p.madicArt = :madicArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByMadicBi", query = "SELECT p FROM PrgTcResumen p WHERE p.madicBi = :madicBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByIphComArt", query = "SELECT p FROM PrgTcResumen p WHERE p.iphComArt = :iphComArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByIphComBi", query = "SELECT p FROM PrgTcResumen p WHERE p.iphComBi = :iphComBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByPrgComArt", query = "SELECT p FROM PrgTcResumen p WHERE p.prgComArt = :prgComArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByPrgComBi", query = "SELECT p FROM PrgTcResumen p WHERE p.prgComBi = :prgComBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByIphHlpArt", query = "SELECT p FROM PrgTcResumen p WHERE p.iphHlpArt = :iphHlpArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByIphHlpBi", query = "SELECT p FROM PrgTcResumen p WHERE p.iphHlpBi = :iphHlpBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByPrgHlpArt", query = "SELECT p FROM PrgTcResumen p WHERE p.prgHlpArt = :prgHlpArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByPrgHlpBi", query = "SELECT p FROM PrgTcResumen p WHERE p.prgHlpBi = :prgHlpBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByHlpiphOptimizadoArt", query = "SELECT p FROM PrgTcResumen p WHERE p.hlpiphOptimizadoArt = :hlpiphOptimizadoArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByHlpiphOptimizadoBi", query = "SELECT p FROM PrgTcResumen p WHERE p.hlpiphOptimizadoBi = :hlpiphOptimizadoBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByHlpOptArt", query = "SELECT p FROM PrgTcResumen p WHERE p.hlpOptArt = :hlpOptArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByHlpOptBi", query = "SELECT p FROM PrgTcResumen p WHERE p.hlpOptBi = :hlpOptBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByHlpNoptArt", query = "SELECT p FROM PrgTcResumen p WHERE p.hlpNoptArt = :hlpNoptArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByHlpNoptBi", query = "SELECT p FROM PrgTcResumen p WHERE p.hlpNoptBi = :hlpNoptBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByFactorArt", query = "SELECT p FROM PrgTcResumen p WHERE p.factorArt = :factorArt")
    ,
    @NamedQuery(name = "PrgTcResumen.findByFactorBi", query = "SELECT p FROM PrgTcResumen p WHERE p.factorBi = :factorBi")
    ,
    @NamedQuery(name = "PrgTcResumen.findByUsername", query = "SELECT p FROM PrgTcResumen p WHERE p.username = :username")
    ,
    @NamedQuery(name = "PrgTcResumen.findByCreado", query = "SELECT p FROM PrgTcResumen p WHERE p.creado = :creado")
    ,
    @NamedQuery(name = "PrgTcResumen.findByModificado", query = "SELECT p FROM PrgTcResumen p WHERE p.modificado = :modificado")
    ,
    @NamedQuery(name = "PrgTcResumen.findByEstadoReg", query = "SELECT p FROM PrgTcResumen p WHERE p.estadoReg = :estadoReg")})
public class PrgTcResumen implements Serializable {

    @OneToMany(mappedBy = "idPrgTcResumen")
    private List<PrgTc> prgTcList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_tc_resumen")
    private Integer idPrgTcResumen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "tipo_dia")
    private Character tipoDia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mcom_prg")
    private BigDecimal mcomPrg;
    @Column(name = "mhlp_prog")
    private BigDecimal mhlpProg;
    @Column(name = "mcom_art_prg")
    private BigDecimal mcomArtPrg;
    @Column(name = "mhlp_art_prg")
    private BigDecimal mhlpArtPrg;
    @Column(name = "mcom_bi_prg")
    private BigDecimal mcomBiPrg;
    @Column(name = "mhlp_bi_prg")
    private BigDecimal mhlpBiPrg;
    @Column(name = "mcom_art_con")
    private BigDecimal mcomArtCon;
    @Column(name = "mcom_bi_con")
    private BigDecimal mcomBiCon;
    @Column(name = "vr_mcom_art_con")
    private BigDecimal vrMcomArtCon;
    @Column(name = "vr_mcon_bi_con")
    private BigDecimal vrMconBiCon;
    @Column(name = "operadores_prg")
    private Integer operadoresPrg;
    @Column(name = "operadores_eje")
    private Integer operadoresEje;
    @Column(name = "vehiculos_prg")
    private Integer vehiculosPrg;
    @Column(name = "vehiculos_eje")
    private Integer vehiculosEje;
    @Column(name = "servicios_prg")
    private Integer serviciosPrg;
    @Column(name = "servicios_eje")
    private Integer serviciosEje;
    @Column(name = "conciliado")
    private Integer conciliado;
    @Column(name = "melim_art")
    private BigDecimal melimArt;
    @Column(name = "melim_bi")
    private BigDecimal melimBi;
    @Column(name = "mhlp_art_eje")
    private BigDecimal mhlpArtEje;
    @Column(name = "mhlp_bi_eje")
    private BigDecimal mhlpBiEje;
    @Column(name = "madic_art")
    private BigDecimal madicArt;
    @Column(name = "madic_bi")
    private BigDecimal madicBi;
    @Column(name = "iph_com_art")
    private BigDecimal iphComArt;
    @Column(name = "iph_com_bi")
    private BigDecimal iphComBi;
    @Column(name = "prg_com_art")
    private BigDecimal prgComArt;
    @Column(name = "prg_com_bi")
    private BigDecimal prgComBi;
    @Column(name = "iph_hlp_art")
    private BigDecimal iphHlpArt;
    @Column(name = "iph_hlp_bi")
    private BigDecimal iphHlpBi;
    @Column(name = "prg_hlp_art")
    private BigDecimal prgHlpArt;
    @Column(name = "prg_hlp_bi")
    private BigDecimal prgHlpBi;
    @Column(name = "hlpiph_optimizado_art")
    private BigDecimal hlpiphOptimizadoArt;
    @Column(name = "hlpiph_optimizado_bi")
    private BigDecimal hlpiphOptimizadoBi;
    @Column(name = "hlp_opt_art")
    private BigDecimal hlpOptArt;
    @Column(name = "hlp_opt_bi")
    private BigDecimal hlpOptBi;
    @Column(name = "hlp_nopt_art")
    private BigDecimal hlpNoptArt;
    @Column(name = "hlp_nopt_bi")
    private BigDecimal hlpNoptBi;
    @Column(name = "factor_art")
    private BigDecimal factorArt;
    @Column(name = "factor_bi")
    private BigDecimal factorBi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;

    
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    public PrgTcResumen() {
    }

    public PrgTcResumen(Integer idPrgTcResumen) {
        this.idPrgTcResumen = idPrgTcResumen;
    }

    public PrgTcResumen(Integer idPrgTcResumen, Date fecha, String username, Date creado, int estadoReg) {
        this.idPrgTcResumen = idPrgTcResumen;
        this.fecha = fecha;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgTcResumen() {
        return idPrgTcResumen;
    }

    public void setIdPrgTcResumen(Integer idPrgTcResumen) {
        this.idPrgTcResumen = idPrgTcResumen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Character getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(Character tipoDia) {
        this.tipoDia = tipoDia;
    }

    public BigDecimal getMcomPrg() {
        return mcomPrg;
    }

    public void setMcomPrg(BigDecimal mcomPrg) {
        this.mcomPrg = mcomPrg;
    }

    public BigDecimal getMhlpProg() {
        return mhlpProg;
    }

    public void setMhlpProg(BigDecimal mhlpProg) {
        this.mhlpProg = mhlpProg;
    }

    public BigDecimal getMcomArtPrg() {
        return mcomArtPrg;
    }

    public void setMcomArtPrg(BigDecimal mcomArtPrg) {
        this.mcomArtPrg = mcomArtPrg;
    }

    public BigDecimal getMhlpArtPrg() {
        return mhlpArtPrg;
    }

    public void setMhlpArtPrg(BigDecimal mhlpArtPrg) {
        this.mhlpArtPrg = mhlpArtPrg;
    }

    public BigDecimal getMcomBiPrg() {
        return mcomBiPrg;
    }

    public void setMcomBiPrg(BigDecimal mcomBiPrg) {
        this.mcomBiPrg = mcomBiPrg;
    }

    public BigDecimal getMhlpBiPrg() {
        return mhlpBiPrg;
    }

    public void setMhlpBiPrg(BigDecimal mhlpBiPrg) {
        this.mhlpBiPrg = mhlpBiPrg;
    }

    public BigDecimal getMcomArtCon() {
        return mcomArtCon;
    }

    public void setMcomArtCon(BigDecimal mcomArtCon) {
        this.mcomArtCon = mcomArtCon;
    }

    public BigDecimal getMcomBiCon() {
        return mcomBiCon;
    }

    public void setMcomBiCon(BigDecimal mcomBiCon) {
        this.mcomBiCon = mcomBiCon;
    }

    public BigDecimal getVrMcomArtCon() {
        return vrMcomArtCon;
    }

    public void setVrMcomArtCon(BigDecimal vrMcomArtCon) {
        this.vrMcomArtCon = vrMcomArtCon;
    }

    public BigDecimal getVrMconBiCon() {
        return vrMconBiCon;
    }

    public void setVrMconBiCon(BigDecimal vrMconBiCon) {
        this.vrMconBiCon = vrMconBiCon;
    }

    public Integer getOperadoresPrg() {
        return operadoresPrg;
    }

    public void setOperadoresPrg(Integer operadoresPrg) {
        this.operadoresPrg = operadoresPrg;
    }

    public Integer getOperadoresEje() {
        return operadoresEje;
    }

    public void setOperadoresEje(Integer operadoresEje) {
        this.operadoresEje = operadoresEje;
    }

    public Integer getVehiculosPrg() {
        return vehiculosPrg;
    }

    public void setVehiculosPrg(Integer vehiculosPrg) {
        this.vehiculosPrg = vehiculosPrg;
    }

    public Integer getVehiculosEje() {
        return vehiculosEje;
    }

    public void setVehiculosEje(Integer vehiculosEje) {
        this.vehiculosEje = vehiculosEje;
    }

    public Integer getServiciosPrg() {
        return serviciosPrg;
    }

    public void setServiciosPrg(Integer serviciosPrg) {
        this.serviciosPrg = serviciosPrg;
    }

    public Integer getServiciosEje() {
        return serviciosEje;
    }

    public void setServiciosEje(Integer serviciosEje) {
        this.serviciosEje = serviciosEje;
    }

    public Integer getConciliado() {
        return conciliado;
    }

    public void setConciliado(Integer conciliado) {
        this.conciliado = conciliado;
    }

    public BigDecimal getMelimArt() {
        return melimArt;
    }

    public void setMelimArt(BigDecimal melimArt) {
        this.melimArt = melimArt;
    }

    public BigDecimal getMelimBi() {
        return melimBi;
    }

    public void setMelimBi(BigDecimal melimBi) {
        this.melimBi = melimBi;
    }

    public BigDecimal getMhlpArtEje() {
        return mhlpArtEje;
    }

    public void setMhlpArtEje(BigDecimal mhlpArtEje) {
        this.mhlpArtEje = mhlpArtEje;
    }

    public BigDecimal getMhlpBiEje() {
        return mhlpBiEje;
    }

    public void setMhlpBiEje(BigDecimal mhlpBiEje) {
        this.mhlpBiEje = mhlpBiEje;
    }

    public BigDecimal getMadicArt() {
        return madicArt;
    }

    public void setMadicArt(BigDecimal madicArt) {
        this.madicArt = madicArt;
    }

    public BigDecimal getMadicBi() {
        return madicBi;
    }

    public void setMadicBi(BigDecimal madicBi) {
        this.madicBi = madicBi;
    }

    public BigDecimal getIphComArt() {
        return iphComArt;
    }

    public void setIphComArt(BigDecimal iphComArt) {
        this.iphComArt = iphComArt;
    }

    public BigDecimal getIphComBi() {
        return iphComBi;
    }

    public void setIphComBi(BigDecimal iphComBi) {
        this.iphComBi = iphComBi;
    }

    public BigDecimal getPrgComArt() {
        return prgComArt;
    }

    public void setPrgComArt(BigDecimal prgComArt) {
        this.prgComArt = prgComArt;
    }

    public BigDecimal getPrgComBi() {
        return prgComBi;
    }

    public void setPrgComBi(BigDecimal prgComBi) {
        this.prgComBi = prgComBi;
    }

    public BigDecimal getIphHlpArt() {
        return iphHlpArt;
    }

    public void setIphHlpArt(BigDecimal iphHlpArt) {
        this.iphHlpArt = iphHlpArt;
    }

    public BigDecimal getIphHlpBi() {
        return iphHlpBi;
    }

    public void setIphHlpBi(BigDecimal iphHlpBi) {
        this.iphHlpBi = iphHlpBi;
    }

    public BigDecimal getPrgHlpArt() {
        return prgHlpArt;
    }

    public void setPrgHlpArt(BigDecimal prgHlpArt) {
        this.prgHlpArt = prgHlpArt;
    }

    public BigDecimal getPrgHlpBi() {
        return prgHlpBi;
    }

    public void setPrgHlpBi(BigDecimal prgHlpBi) {
        this.prgHlpBi = prgHlpBi;
    }

    public BigDecimal getHlpiphOptimizadoArt() {
        return hlpiphOptimizadoArt;
    }

    public void setHlpiphOptimizadoArt(BigDecimal hlpiphOptimizadoArt) {
        this.hlpiphOptimizadoArt = hlpiphOptimizadoArt;
    }

    public BigDecimal getHlpiphOptimizadoBi() {
        return hlpiphOptimizadoBi;
    }

    public void setHlpiphOptimizadoBi(BigDecimal hlpiphOptimizadoBi) {
        this.hlpiphOptimizadoBi = hlpiphOptimizadoBi;
    }

    public BigDecimal getHlpOptArt() {
        return hlpOptArt;
    }

    public void setHlpOptArt(BigDecimal hlpOptArt) {
        this.hlpOptArt = hlpOptArt;
    }

    public BigDecimal getHlpOptBi() {
        return hlpOptBi;
    }

    public void setHlpOptBi(BigDecimal hlpOptBi) {
        this.hlpOptBi = hlpOptBi;
    }

    public BigDecimal getHlpNoptArt() {
        return hlpNoptArt;
    }

    public void setHlpNoptArt(BigDecimal hlpNoptArt) {
        this.hlpNoptArt = hlpNoptArt;
    }

    public BigDecimal getHlpNoptBi() {
        return hlpNoptBi;
    }

    public void setHlpNoptBi(BigDecimal hlpNoptBi) {
        this.hlpNoptBi = hlpNoptBi;
    }

    public BigDecimal getFactorArt() {
        return factorArt;
    }

    public void setFactorArt(BigDecimal factorArt) {
        this.factorArt = factorArt;
    }

    public BigDecimal getFactorBi() {
        return factorBi;
    }

    public void setFactorBi(BigDecimal factorBi) {
        this.factorBi = factorBi;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgTcResumen != null ? idPrgTcResumen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgTcResumen)) {
            return false;
        }
        PrgTcResumen other = (PrgTcResumen) object;
        if ((this.idPrgTcResumen == null && other.idPrgTcResumen != null) || (this.idPrgTcResumen != null && !this.idPrgTcResumen.equals(other.idPrgTcResumen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgTcResumen[ idPrgTcResumen=" + idPrgTcResumen + " ]";
    }

    @XmlTransient
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

}
