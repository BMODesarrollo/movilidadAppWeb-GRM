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
@Table(name = "generica_pm_liquidacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmLiquidacion.findAll", query = "SELECT g FROM GenericaPmLiquidacion g"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByIdEmpleado", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByDesde", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.desde = :desde"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByHasta", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.hasta = :hasta"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByIdGrupo", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.idGrupo = :idGrupo"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByDiasLaborados", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.diasLaborados = :diasLaborados"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByDiasNovedad", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.diasNovedad = :diasNovedad"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByPuntosEmpresa", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.puntosEmpresa = :puntosEmpresa"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByPuntosPmConciliados", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.puntosPmConciliados = :puntosPmConciliados"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByVrBonoSalarial", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.vrBonoSalarial = :vrBonoSalarial"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByVrBonoAlimentos", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.vrBonoAlimentos = :vrBonoAlimentos"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByPuntosVrBonoSalarial", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.puntosVrBonoSalarial = :puntosVrBonoSalarial"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByDiasVrBonoSalarial", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.diasVrBonoSalarial = :diasVrBonoSalarial"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByDiasVrBonoAlimentos", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.diasVrBonoAlimentos = :diasVrBonoAlimentos"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByDescuentoPuntos", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.descuentoPuntos = :descuentoPuntos"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByDescuentoDiasSalarial", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.descuentoDiasSalarial = :descuentoDiasSalarial"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByDescuentoDiasAlimentos", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.descuentoDiasAlimentos = :descuentoDiasAlimentos"),
    @NamedQuery(name = "GenericaPmLiquidacion.findBySubtotalBonoAlimento", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.subtotalBonoAlimento = :subtotalBonoAlimento"),
    @NamedQuery(name = "GenericaPmLiquidacion.findBySubtotalBonoSalarial", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.subtotalBonoSalarial = :subtotalBonoSalarial"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByTotalPagar", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.totalPagar = :totalPagar"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByEstadoReg", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.estadoReg = :estadoReg"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByUsername", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByCreado", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByModificado", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPmLiquidacion.findByUserBorra", query = "SELECT g FROM GenericaPmLiquidacion g WHERE g.userBorra = :userBorra")})
public class GenericaPmLiquidacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empleado")
    private Integer idEmpleado;
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
    @Column(name = "id_grupo")
    private Integer idGrupo;
    @Column(name = "diasLaborados")
    private Integer diasLaborados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "diasNovedad")
    private BigInteger diasNovedad;
    @Column(name = "puntosEmpresa")
    private Integer puntosEmpresa;
    @Column(name = "diasEmpresa")
    private Integer diasEmpresa;
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
    @Column(name = "totalPagar")
    private BigDecimal totalPagar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Size(max = 15)
    @Column(name = "user_borra")
    private String userBorra;
    @JoinColumn(name = "id_empleado_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoCargo;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado empleado;

    public GenericaPmLiquidacion() {
    }

    public GenericaPmLiquidacion(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public GenericaPmLiquidacion(Integer idEmpleado, Date desde, Date hasta, BigInteger diasNovedad, int vrBonoSalarial, int vrBonoAlimentos, int estadoReg) {
        this.idEmpleado = idEmpleado;
        this.desde = desde;
        this.hasta = hasta;
        this.diasNovedad = diasNovedad;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.estadoReg = estadoReg;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
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

    public String getUserBorra() {
        return userBorra;
    }

    public void setUserBorra(String userBorra) {
        this.userBorra = userBorra;
    }

    public EmpleadoTipoCargo getIdEmpleadoCargo() {
        return idEmpleadoCargo;
    }

    public void setIdEmpleadoCargo(EmpleadoTipoCargo idEmpleadoCargo) {
        this.idEmpleadoCargo = idEmpleadoCargo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public Integer getDiasEmpresa() {
        return diasEmpresa;
    }

    public void setDiasEmpresa(Integer diasEmpresa) {
        this.diasEmpresa = diasEmpresa;
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
        if (!(object instanceof GenericaPmLiquidacion)) {
            return false;
        }
        GenericaPmLiquidacion other = (GenericaPmLiquidacion) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmLiquidacion[ idEmpleado=" + idEmpleado + " ]";
    }

}
