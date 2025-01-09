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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "tbl_liquidacion_empleado_mes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findAll", query = "SELECT t FROM TblLiquidacionEmpleadoMes t")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByIdEmpleado", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.idEmpleado = :idEmpleado")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByDesde", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.desde = :desde")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByHasta", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.hasta = :hasta")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByIdEmpleadoCargo", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.idEmpleadoCargo = :idEmpleadoCargo")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByDiasLaborados", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.diasLaborados = :diasLaborados")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByDiasNovedad", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.diasNovedad = :diasNovedad")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByPuntosPmConciliados", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.puntosPmConciliados = :puntosPmConciliados")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByVrBonoSalarial", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.vrBonoSalarial = :vrBonoSalarial")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByVrBonoAlimentos", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.vrBonoAlimentos = :vrBonoAlimentos")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByPuntosVrBonoSalarial", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.puntosVrBonoSalarial = :puntosVrBonoSalarial")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByDiasVrBonoSalarial", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.diasVrBonoSalarial = :diasVrBonoSalarial")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByDiasVrBonoAlimentos", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.diasVrBonoAlimentos = :diasVrBonoAlimentos")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByDescuentoPuntos", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.descuentoPuntos = :descuentoPuntos")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByDescuentoDiasSalarial", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.descuentoDiasSalarial = :descuentoDiasSalarial")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByDescuentoDiasAlimentos", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.descuentoDiasAlimentos = :descuentoDiasAlimentos")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findBySubtotalBonoAlimento", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.subtotalBonoAlimento = :subtotalBonoAlimento")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findBySubtotalBonoSalarial", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.subtotalBonoSalarial = :subtotalBonoSalarial")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByTotalBonoVehiculoTipo", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.totalBonoVehiculoTipo = :totalBonoVehiculoTipo")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByBonoAmplitud", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.bonoAmplitud = :bonoAmplitud")
    ,
    @NamedQuery(name = "TblLiquidacionEmpleadoMes.findByTotalPagar", query = "SELECT t FROM TblLiquidacionEmpleadoMes t WHERE t.totalPagar = :totalPagar")})
public class TblLiquidacionEmpleadoMes implements Serializable {

    @Column(name = "id_grupo")
    private Integer idGrupo;
    @Column(name = "puntosEmpresa")
    private Integer puntosEmpresa;
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
    @Size(max = 15)
    @Column(name = "user_borra")
    private String userBorra;
    @JoinColumn(name = "id_empleado_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoCargo;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "desde")
    private String desde;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "hasta")
    private String hasta;
    @Column(name = "diasLaborados")
    private Integer diasLaborados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "diasNovedad")
    private BigInteger diasNovedad;
    @Column(name = "puntos_pm_conciliados")
    private BigInteger puntosPmConciliados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_bono_salarial")
    private int vrBonoSalarial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_bono_alimentos")
    private int vrBonoAlimentos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "puntosVrBonoSalarial")
    private BigDecimal puntosVrBonoSalarial;
    @Column(name = "diasVrBonoSalarial")
    private BigDecimal diasVrBonoSalarial;
    @Column(name = "diasVrBonoAlimentos")
    private BigDecimal diasVrBonoAlimentos;
    @Column(name = "descuentoPuntos")
    private BigDecimal descuentoPuntos;
    @Column(name = "descuentoDiasSalarial")
    private BigDecimal descuentoDiasSalarial;
    @Column(name = "descuentoDiasAlimentos")
    private BigDecimal descuentoDiasAlimentos;
    @Column(name = "subtotalBonoAlimento")
    private BigDecimal subtotalBonoAlimento;
    @Column(name = "subtotalBonoSalarial")
    private BigDecimal subtotalBonoSalarial;
    @Column(name = "totalBonoVehiculoTipo")
    private BigDecimal totalBonoVehiculoTipo;
    @Column(name = "BonoAmplitud")
    private BigInteger bonoAmplitud;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalPagar")
    private BigDecimal totalPagar;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado empleado;
    @Column(name = "ingresos")
    private BigDecimal ingresos;
    @Column(name = "egresos")
    private BigDecimal egresos;
    @Column(name = "bono_individual")
    private BigDecimal bonoIndividual;

    public TblLiquidacionEmpleadoMes() {
    }

    public TblLiquidacionEmpleadoMes(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public TblLiquidacionEmpleadoMes(Integer idEmpleado, String desde, String hasta, EmpleadoTipoCargo idEmpleadoCargo, BigInteger diasNovedad, int vrBonoSalarial, int vrBonoAlimentos, BigDecimal totalPagar) {
        this.idEmpleado = idEmpleado;
        this.desde = desde;
        this.hasta = hasta;
        this.idEmpleadoCargo = idEmpleadoCargo;
        this.diasNovedad = diasNovedad;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.totalPagar = totalPagar;
    }

    public TblLiquidacionEmpleadoMes(Integer idEmpleado, String desde, String hasta, EmpleadoTipoCargo idEmpleadoCargo, Integer diasLaborados, BigInteger diasNovedad, BigInteger puntosPmConciliados, int vrBonoSalarial, int vrBonoAlimentos, BigDecimal puntosVrBonoSalarial, BigDecimal diasVrBonoSalarial, BigDecimal diasVrBonoAlimentos, BigDecimal descuentoPuntos, BigDecimal descuentoDiasSalarial, BigDecimal descuentoDiasAlimentos, BigDecimal subtotalBonoAlimento, BigDecimal subtotalBonoSalarial, BigDecimal totalBonoVehiculoTipo, BigInteger bonoAmplitud, BigDecimal totalPagar, Empleado empleado, int puntosEmpresa) {
        this.idEmpleado = idEmpleado;
        this.desde = desde;
        this.hasta = hasta;
        this.idEmpleadoCargo = idEmpleadoCargo;
        this.diasLaborados = diasLaborados;
        this.diasNovedad = diasNovedad;
        this.puntosPmConciliados = puntosPmConciliados;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.puntosVrBonoSalarial = puntosVrBonoSalarial;
        this.diasVrBonoSalarial = diasVrBonoSalarial;
        this.diasVrBonoAlimentos = diasVrBonoAlimentos;
        this.descuentoPuntos = descuentoPuntos;
        this.descuentoDiasSalarial = descuentoDiasSalarial;
        this.descuentoDiasAlimentos = descuentoDiasAlimentos;
        this.subtotalBonoAlimento = subtotalBonoAlimento;
        this.subtotalBonoSalarial = subtotalBonoSalarial;
        this.totalBonoVehiculoTipo = totalBonoVehiculoTipo;
        this.bonoAmplitud = bonoAmplitud;
        this.totalPagar = totalPagar;
        this.empleado = empleado;
        this.puntosEmpresa = puntosEmpresa;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public EmpleadoTipoCargo getIdEmpleadoCargo() {
        return idEmpleadoCargo;
    }

    public void setIdEmpleadoCargo(EmpleadoTipoCargo idEmpleadoCargo) {
        this.idEmpleadoCargo = idEmpleadoCargo;
    }

    public Integer getDiasLaborados() {
        return diasLaborados;
    }

    public void setDiasLaborados(Integer diasLaborados) {
        this.diasLaborados = diasLaborados;
    }

    public BigInteger getDiasNovedad() {
        return diasNovedad;
    }

    public void setDiasNovedad(BigInteger diasNovedad) {
        this.diasNovedad = diasNovedad;
    }

    public BigInteger getPuntosPmConciliados() {
        return puntosPmConciliados;
    }

    public void setPuntosPmConciliados(BigInteger puntosPmConciliados) {
        this.puntosPmConciliados = puntosPmConciliados;
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

    public BigDecimal getPuntosVrBonoSalarial() {
        return puntosVrBonoSalarial;
    }

    public void setPuntosVrBonoSalarial(BigDecimal puntosVrBonoSalarial) {
        this.puntosVrBonoSalarial = puntosVrBonoSalarial;
    }

    public BigDecimal getDiasVrBonoSalarial() {
        return diasVrBonoSalarial;
    }

    public void setDiasVrBonoSalarial(BigDecimal diasVrBonoSalarial) {
        this.diasVrBonoSalarial = diasVrBonoSalarial;
    }

    public BigDecimal getDiasVrBonoAlimentos() {
        return diasVrBonoAlimentos;
    }

    public void setDiasVrBonoAlimentos(BigDecimal diasVrBonoAlimentos) {
        this.diasVrBonoAlimentos = diasVrBonoAlimentos;
    }

    public BigDecimal getDescuentoPuntos() {
        return descuentoPuntos;
    }

    public void setDescuentoPuntos(BigDecimal descuentoPuntos) {
        this.descuentoPuntos = descuentoPuntos;
    }

    public BigDecimal getDescuentoDiasSalarial() {
        return descuentoDiasSalarial;
    }

    public void setDescuentoDiasSalarial(BigDecimal descuentoDiasSalarial) {
        this.descuentoDiasSalarial = descuentoDiasSalarial;
    }

    public BigDecimal getDescuentoDiasAlimentos() {
        return descuentoDiasAlimentos;
    }

    public void setDescuentoDiasAlimentos(BigDecimal descuentoDiasAlimentos) {
        this.descuentoDiasAlimentos = descuentoDiasAlimentos;
    }

    public BigDecimal getSubtotalBonoAlimento() {
        return subtotalBonoAlimento;
    }

    public void setSubtotalBonoAlimento(BigDecimal subtotalBonoAlimento) {
        this.subtotalBonoAlimento = subtotalBonoAlimento;
    }

    public BigDecimal getSubtotalBonoSalarial() {
        return subtotalBonoSalarial;
    }

    public void setSubtotalBonoSalarial(BigDecimal subtotalBonoSalarial) {
        this.subtotalBonoSalarial = subtotalBonoSalarial;
    }

    public BigDecimal getTotalBonoVehiculoTipo() {
        return totalBonoVehiculoTipo;
    }

    public void setTotalBonoVehiculoTipo(BigDecimal totalBonoVehiculoTipo) {
        this.totalBonoVehiculoTipo = totalBonoVehiculoTipo;
    }

    public BigInteger getBonoAmplitud() {
        return bonoAmplitud;
    }

    public void setBonoAmplitud(BigInteger bonoAmplitud) {
        this.bonoAmplitud = bonoAmplitud;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblLiquidacionEmpleadoMes)) {
            return false;
        }
        TblLiquidacionEmpleadoMes other = (TblLiquidacionEmpleadoMes) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.TblLiquidacionEmpleadoMes[ idEmpleado=" + idEmpleado + " ]";
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getPuntosEmpresa() {
        return puntosEmpresa;
    }

    public void setPuntosEmpresa(Integer puntosEmpresa) {
        this.puntosEmpresa = puntosEmpresa;
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

    public String getUserBorra() {
        return userBorra;
    }

    public void setUserBorra(String userBorra) {
        this.userBorra = userBorra;
    }

    public BigDecimal getIngresos() {
        return ingresos;
    }

    public void setIngresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    public BigDecimal getEgresos() {
        return egresos;
    }

    public void setEgresos(BigDecimal egresos) {
        this.egresos = egresos;
    }

    public BigDecimal getBonoIndividual() {
        return bonoIndividual;
    }

    public void setBonoIndividual(BigDecimal bonoIndividual) {
        this.bonoIndividual = bonoIndividual;
    }

}
