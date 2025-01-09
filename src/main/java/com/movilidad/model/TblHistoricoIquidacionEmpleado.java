/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.TblHistoricoLiquidacionEmpleadoDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
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
@Table(name = "tbl_historico_iquidacion_empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findAll", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByIdEjecucion", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.tblHistoricoIquidacionEmpleadoPK.idEjecucion = :idEjecucion")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByIdEmpleado", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.tblHistoricoIquidacionEmpleadoPK.idEmpleado = :idEmpleado")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByDesde", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.desde = :desde")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByHasta", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.hasta = :hasta")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByIdEmpleadoCargo", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.idEmpleadoCargo = :idEmpleadoCargo")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByIdGrupo", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.idGrupo = :idGrupo")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByDiasLaborados", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.diasLaborados = :diasLaborados")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByDiasNovedad", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.diasNovedad = :diasNovedad")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByPuntosEmpresa", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.puntosEmpresa = :puntosEmpresa")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByPuntosPmConciliados", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.puntosPmConciliados = :puntosPmConciliados")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByVrBonoSalarial", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.vrBonoSalarial = :vrBonoSalarial")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByVrBonoAlimentos", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.vrBonoAlimentos = :vrBonoAlimentos")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByPuntosVrBonoSalarial", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.puntosVrBonoSalarial = :puntosVrBonoSalarial")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByDiasVrBonoSalarial", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.diasVrBonoSalarial = :diasVrBonoSalarial")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByDiasVrBonoAlimentos", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.diasVrBonoAlimentos = :diasVrBonoAlimentos")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByDescuentoPuntos", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.descuentoPuntos = :descuentoPuntos")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByDescuentoDiasSalarial", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.descuentoDiasSalarial = :descuentoDiasSalarial")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByDescuentoDiasAlimentos", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.descuentoDiasAlimentos = :descuentoDiasAlimentos")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findBySubtotalBonoAlimento", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.subtotalBonoAlimento = :subtotalBonoAlimento")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findBySubtotalBonoSalarial", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.subtotalBonoSalarial = :subtotalBonoSalarial")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByTotalBonoVehiculoTipo", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.totalBonoVehiculoTipo = :totalBonoVehiculoTipo")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByBonoAmplitud", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.bonoAmplitud = :bonoAmplitud")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByTotalPagar", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.totalPagar = :totalPagar")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByEstadoReg", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.estadoReg = :estadoReg")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByModificado", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.modificado = :modificado")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByUsername", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.username = :username")
    ,
    @NamedQuery(name = "TblHistoricoIquidacionEmpleado.findByUserBorra", query = "SELECT t FROM TblHistoricoIquidacionEmpleado t WHERE t.userBorra = :userBorra")})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "resumenLiquidacionDTO",
            classes = {
                @ConstructorResult(targetClass = TblHistoricoLiquidacionEmpleadoDTO.class,
                        columns = {
                            @ColumnResult(name = "desde")
                            ,
                            @ColumnResult(name = "hasta")
                            ,
                            @ColumnResult(name = "grupo")
                            ,
                            @ColumnResult(name = "diasLaborados")
                            ,
                            @ColumnResult(name = "diasNovedad")
                            ,
                            @ColumnResult(name = "puntosEmpresa")
                            ,
                            @ColumnResult(name = "puntosPmConciliados")
                            ,
                            @ColumnResult(name = "vrBonoSalarial")
                            ,
                            @ColumnResult(name = "vrBonoAlimentos")
                            ,
                            @ColumnResult(name = "puntosVrBonoSalarial")
                            ,
                            @ColumnResult(name = "diasVrBonoSalarial")
                            ,
                            @ColumnResult(name = "diasVrBonoAlimentos")
                            ,
                            @ColumnResult(name = "descuentoPuntos")
                            ,
                            @ColumnResult(name = "descuentoDiasSalarial")
                            ,
                            @ColumnResult(name = "descuentoDiasAlimentos")
                            ,
                            @ColumnResult(name = "subtotalBonoAlimento")
                            ,
                            @ColumnResult(name = "subtotalBonoSalarial")
                            ,
                            @ColumnResult(name = "totalBonoVehiculoTipo")
                            ,
                            @ColumnResult(name = "bonoAmplitud")
                            ,
                            @ColumnResult(name = "totalPagar")
                        }
                )
            })})
public class TblHistoricoIquidacionEmpleado implements Serializable {

    @JoinColumn(name = "id_empleado_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoCargo;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado empleado;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TblHistoricoIquidacionEmpleadoPK tblHistoricoIquidacionEmpleadoPK;
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
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_pm_grupo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PmGrupo idGrupo;
    @Column(name = "diasLaborados")
    private Integer diasLaborados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "diasNovedad")
    private BigInteger diasNovedad;
    @Column(name = "puntosEmpresa")
    private Integer puntosEmpresa;
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
    @Column(name = "ingresos")
    private BigDecimal ingresos;
    @Column(name = "egresos")
    private BigDecimal egresos;
    @Column(name = "bono_individual")
    private BigDecimal bonoIndividual;

    public TblHistoricoIquidacionEmpleado() {
    }

    public TblHistoricoIquidacionEmpleado(TblHistoricoIquidacionEmpleadoPK tblHistoricoIquidacionEmpleadoPK) {
        this.tblHistoricoIquidacionEmpleadoPK = tblHistoricoIquidacionEmpleadoPK;
    }

    public TblHistoricoIquidacionEmpleado(TblHistoricoIquidacionEmpleadoPK tblHistoricoIquidacionEmpleadoPK, String desde, String hasta, EmpleadoTipoCargo idEmpleadoCargo, BigInteger diasNovedad, int vrBonoSalarial, int vrBonoAlimentos, BigDecimal totalPagar, int estadoReg, Date modificado) {
        this.tblHistoricoIquidacionEmpleadoPK = tblHistoricoIquidacionEmpleadoPK;
        this.desde = desde;
        this.hasta = hasta;
        this.idEmpleadoCargo = idEmpleadoCargo;
        this.diasNovedad = diasNovedad;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.totalPagar = totalPagar;
        this.estadoReg = estadoReg;
        this.modificado = modificado;
    }

    public TblHistoricoIquidacionEmpleado(String desde, String hasta, PmGrupo idGrupo, Integer diasLaborados, BigInteger diasNovedad, Integer puntosEmpresa, BigInteger puntosPmConciliados, int vrBonoSalarial, int vrBonoAlimentos, BigDecimal puntosVrBonoSalarial, BigDecimal diasVrBonoSalarial, BigDecimal diasVrBonoAlimentos, BigDecimal descuentoPuntos, BigDecimal descuentoDiasSalarial, BigDecimal descuentoDiasAlimentos, BigDecimal subtotalBonoAlimento, BigDecimal subtotalBonoSalarial, BigDecimal totalBonoVehiculoTipo, BigInteger bonoAmplitud, BigDecimal totalPagar) {
        this.desde = desde;
        this.hasta = hasta;
        this.idGrupo = idGrupo;
        this.diasLaborados = diasLaborados;
        this.diasNovedad = diasNovedad;
        this.puntosEmpresa = puntosEmpresa;
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
    }

    public TblHistoricoIquidacionEmpleado(Date idEjecucion, int idEmpleado) {
        this.tblHistoricoIquidacionEmpleadoPK = new TblHistoricoIquidacionEmpleadoPK(idEjecucion, idEmpleado);
    }

    public TblHistoricoIquidacionEmpleadoPK getTblHistoricoIquidacionEmpleadoPK() {
        return tblHistoricoIquidacionEmpleadoPK;
    }

    public void setTblHistoricoIquidacionEmpleadoPK(TblHistoricoIquidacionEmpleadoPK tblHistoricoIquidacionEmpleadoPK) {
        this.tblHistoricoIquidacionEmpleadoPK = tblHistoricoIquidacionEmpleadoPK;
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

    public PmGrupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(PmGrupo idGrupo) {
        this.idGrupo = idGrupo;
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

    public Integer getPuntosEmpresa() {
        return puntosEmpresa;
    }

    public void setPuntosEmpresa(Integer puntosEmpresa) {
        this.puntosEmpresa = puntosEmpresa;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tblHistoricoIquidacionEmpleadoPK != null ? tblHistoricoIquidacionEmpleadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblHistoricoIquidacionEmpleado)) {
            return false;
        }
        TblHistoricoIquidacionEmpleado other = (TblHistoricoIquidacionEmpleado) object;
        if ((this.tblHistoricoIquidacionEmpleadoPK == null && other.tblHistoricoIquidacionEmpleadoPK != null) || (this.tblHistoricoIquidacionEmpleadoPK != null && !this.tblHistoricoIquidacionEmpleadoPK.equals(other.tblHistoricoIquidacionEmpleadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.TblHistoricoIquidacionEmpleado[ tblHistoricoIquidacionEmpleadoPK=" + tblHistoricoIquidacionEmpleadoPK + " ]";
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
