/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "tbl_liquidacion_grupo_mes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblLiquidacionGrupoMes.findAll", query = "SELECT t FROM TblLiquidacionGrupoMes t")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByIdTblLiquidacionGrupoMes", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.idTblLiquidacionGrupoMes = :idTblLiquidacionGrupoMes")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByDesde", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.desde = :desde")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByHasta", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.hasta = :hasta")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByPosicion", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.posicion = :posicion")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByEmpleados", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.empleados = :empleados")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByPuntosPmConciliados", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.puntosPmConciliados = :puntosPmConciliados")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByPuntosTrimestre", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.puntosTrimestre = :puntosTrimestre")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByVrBonoSalarial", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.vrBonoSalarial = :vrBonoSalarial")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByVrBonoAlimentos", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.vrBonoAlimentos = :vrBonoAlimentos")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByPorcentaje", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.porcentaje = :porcentaje")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByTotalBonoGrupal", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.totalBonoGrupal = :totalBonoGrupal")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByTotalBonoIndividual", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.totalBonoIndividual = :totalBonoIndividual")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByEstadoReg", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.estadoReg = :estadoReg")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByModificado", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.modificado = :modificado")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByUsername", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.username = :username")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByCreado", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.creado = :creado")
    , @NamedQuery(name = "TblLiquidacionGrupoMes.findByUserBorra", query = "SELECT t FROM TblLiquidacionGrupoMes t WHERE t.userBorra = :userBorra")})
public class TblLiquidacionGrupoMes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tbl_liquidacion_grupo_mes")
    private Integer idTblLiquidacionGrupoMes;
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
    @Column(name = "posicion")
    private Integer posicion;
    @Column(name = "empleados")
    private Integer empleados;
    @Column(name = "puntos_pm_conciliados")
    private BigInteger puntosPmConciliados;
    @Column(name = "puntos_trimestre")
    private BigInteger puntosTrimestre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_bono_salarial")
    private int vrBonoSalarial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_bono_alimentos")
    private int vrBonoAlimentos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentaje")
    private BigDecimal porcentaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_bono_grupal")
    private BigDecimal totalBonoGrupal;
    @Column(name = "total_bono_individual")
    private BigDecimal totalBonoIndividual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Size(max = 15)
    @Column(name = "user_borra")
    private String userBorra;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_pm_grupo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PmGrupo idGrupo;

    public TblLiquidacionGrupoMes() {
    }

    public TblLiquidacionGrupoMes(Integer idTblLiquidacionGrupoMes) {
        this.idTblLiquidacionGrupoMes = idTblLiquidacionGrupoMes;
    }

    public TblLiquidacionGrupoMes(Integer idTblLiquidacionGrupoMes, Date desde, Date hasta, int vrBonoSalarial, int vrBonoAlimentos, BigDecimal totalBonoGrupal, int estadoReg) {
        this.idTblLiquidacionGrupoMes = idTblLiquidacionGrupoMes;
        this.desde = desde;
        this.hasta = hasta;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.totalBonoGrupal = totalBonoGrupal;
        this.estadoReg = estadoReg;
    }

    public Integer getIdTblLiquidacionGrupoMes() {
        return idTblLiquidacionGrupoMes;
    }

    public void setIdTblLiquidacionGrupoMes(Integer idTblLiquidacionGrupoMes) {
        this.idTblLiquidacionGrupoMes = idTblLiquidacionGrupoMes;
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

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Integer empleados) {
        this.empleados = empleados;
    }

    public BigInteger getPuntosPmConciliados() {
        return puntosPmConciliados;
    }

    public void setPuntosPmConciliados(BigInteger puntosPmConciliados) {
        this.puntosPmConciliados = puntosPmConciliados;
    }

    public BigInteger getPuntosTrimestre() {
        return puntosTrimestre;
    }

    public void setPuntosTrimestre(BigInteger puntosTrimestre) {
        this.puntosTrimestre = puntosTrimestre;
    }

    public int getVrBonoSalarial() {
        return vrBonoSalarial;
    }

    public void setVrBonoSalarial(int vrBonoSalarial) {
        this.vrBonoSalarial = vrBonoSalarial;
    }

    public int getVrBonoAlimentos() {
        return vrBonoAlimentos;
    }

    public void setVrBonoAlimentos(int vrBonoAlimentos) {
        this.vrBonoAlimentos = vrBonoAlimentos;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getTotalBonoGrupal() {
        return totalBonoGrupal;
    }

    public void setTotalBonoGrupal(BigDecimal totalBonoGrupal) {
        this.totalBonoGrupal = totalBonoGrupal;
    }

    public BigDecimal getTotalBonoIndividual() {
        return totalBonoIndividual;
    }

    public void setTotalBonoIndividual(BigDecimal totalBonoIndividual) {
        this.totalBonoIndividual = totalBonoIndividual;
    }

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
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

    public String getUserBorra() {
        return userBorra;
    }

    public void setUserBorra(String userBorra) {
        this.userBorra = userBorra;
    }

    public PmGrupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(PmGrupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTblLiquidacionGrupoMes != null ? idTblLiquidacionGrupoMes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblLiquidacionGrupoMes)) {
            return false;
        }
        TblLiquidacionGrupoMes other = (TblLiquidacionGrupoMes) object;
        if ((this.idTblLiquidacionGrupoMes == null && other.idTblLiquidacionGrupoMes != null) || (this.idTblLiquidacionGrupoMes != null && !this.idTblLiquidacionGrupoMes.equals(other.idTblLiquidacionGrupoMes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.TblLiquidacionGrupoMes[ idTblLiquidacionGrupoMes=" + idTblLiquidacionGrupoMes + " ]";
    }
    
}
