/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author cesar
 */
@Entity
@Table(name = "generica_jornada_historial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaJornadaHistorial.findAll", query = "SELECT g FROM GenericaJornadaHistorial g")})
public class GenericaJornadaHistorial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_jornada_historial")
    private Integer idGenericaJornadaHistorial;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 30)
    @Column(name = "sercon")
    private String sercon;
    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
    @Column(name = "id_param_area")
    private Integer idParamArea;
    @Column(name = "cargada")
    private Integer cargada;
    @Column(name = "prg_modificada")
    private Integer prgModificada;
    @Size(max = 8)
    @Column(name = "real_time_origin")
    private String realTimeOrigin;
    @Size(max = 8)
    @Column(name = "real_time_destiny")
    private String realTimeDestiny;
    @Size(max = 8)
    @Column(name = "hini_turno2")
    private String hiniTurno2;
    @Size(max = 8)
    @Column(name = "hfin_turno2")
    private String hfinTurno2;
    @Size(max = 8)
    @Column(name = "hini_turno3")
    private String hiniTurno3;
    @Size(max = 8)
    @Column(name = "hfin_turno3")
    private String hfinTurno3;
    @Column(name = "id_generica_jornada_motivo")
    private Integer idGenericaJornadaMotivo;
    @Column(name = "id_generica_jornada_tipo")
    private Integer idGenericaJornadaTipo;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 15)
    @Column(name = "user_genera")
    private String userGenera;
    @Column(name = "fecha_genera")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGenera;
    @Column(name = "autorizado")
    private Integer autorizado;
    @Size(max = 15)
    @Column(name = "user_autorizado")
    private String userAutorizado;
    @Column(name = "fecha_autoriza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAutoriza;
    @Column(name = "liquidado")
    private Integer liquidado;
    @Size(max = 15)
    @Column(name = "user_liquida")
    private String userLiquida;
    @Column(name = "fecha_liquida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLiquida;
    @Column(name = "tipo_calculo")
    private Integer tipoCalculo;
    @Column(name = "nomina_borrada")
    private Integer nominaBorrada;
    @Size(max = 8)
    @Column(name = "diurnas")
    private String diurnas;
    @Size(max = 8)
    @Column(name = "nocturnas")
    private String nocturnas;
    @Size(max = 8)
    @Column(name = "extra_diurna")
    private String extraDiurna;
    @Size(max = 8)
    @Column(name = "extra_nocturna")
    private String extraNocturna;
    @Size(max = 8)
    @Column(name = "festivo_diurno")
    private String festivoDiurno;
    @Size(max = 8)
    @Column(name = "festivo_nocturno")
    private String festivoNocturno;
    @Size(max = 8)
    @Column(name = "festivo_extra_diurno")
    private String festivoExtraDiurno;
    @Size(max = 8)
    @Column(name = "festivo_extra_nocturno")
    private String festivoExtraNocturno;
    @Size(max = 8)
    @Column(name = "dominical_comp_diurnas")
    private String dominicalCompDiurnas;
    @Size(max = 8)
    @Column(name = "dominical_comp_nocturnas")
    private String dominicalCompNocturnas;
    @Size(max = 8)
    @Column(name = "dominical_comp_diurna_extra")
    private String dominicalCompDiurnaExtra;
    @Size(max = 8)
    @Column(name = "dominical_comp_nocturna_extra")
    private String dominicalCompNocturnaExtra;
    @Size(max = 8)
    @Column(name = "production_time")
    private String productionTime;
    @Size(max = 8)
    @Column(name = "compensatorio")
    private String compensatorio;
    @Size(max = 8)
    @Column(name = "production_time_real")
    private String productionTimeReal;
    @Size(max = 15)
    @Column(name = "orden_trabajo")
    private String ordenTrabajo;
    @Column(name = "id_generica_jornada")
    private Integer idGenericaJornada;
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
    @Size(max = 15)
    @Column(name = "user_elimina")
    private String userElimina;
    @Column(name = "creado_elimina")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creadoElimina;
    @Column(name = "estado_reg_elimina")
    private Integer estadoRegElimina;

    public GenericaJornadaHistorial() {
    }

    public GenericaJornadaHistorial(Integer idGenericaJornadaHistorial) {
        this.idGenericaJornadaHistorial = idGenericaJornadaHistorial;
    }

    public GenericaJornadaHistorial(Integer idGenericaJornadaHistorial, String username, Date creado, int estadoReg) {
        this.idGenericaJornadaHistorial = idGenericaJornadaHistorial;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaJornadaHistorial() {
        return idGenericaJornadaHistorial;
    }

    public void setIdGenericaJornadaHistorial(Integer idGenericaJornadaHistorial) {
        this.idGenericaJornadaHistorial = idGenericaJornadaHistorial;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSercon() {
        return sercon;
    }

    public void setSercon(String sercon) {
        this.sercon = sercon;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
    }

    public Integer getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(Integer idParamArea) {
        this.idParamArea = idParamArea;
    }

    public Integer getCargada() {
        return cargada;
    }

    public void setCargada(Integer cargada) {
        this.cargada = cargada;
    }

    public Integer getPrgModificada() {
        return prgModificada;
    }

    public void setPrgModificada(Integer prgModificada) {
        this.prgModificada = prgModificada;
    }

    public String getRealTimeOrigin() {
        return realTimeOrigin;
    }

    public void setRealTimeOrigin(String realTimeOrigin) {
        this.realTimeOrigin = realTimeOrigin;
    }

    public String getRealTimeDestiny() {
        return realTimeDestiny;
    }

    public void setRealTimeDestiny(String realTimeDestiny) {
        this.realTimeDestiny = realTimeDestiny;
    }

    public String getHiniTurno2() {
        return hiniTurno2;
    }

    public void setHiniTurno2(String hiniTurno2) {
        this.hiniTurno2 = hiniTurno2;
    }

    public String getHfinTurno2() {
        return hfinTurno2;
    }

    public void setHfinTurno2(String hfinTurno2) {
        this.hfinTurno2 = hfinTurno2;
    }

    public String getHiniTurno3() {
        return hiniTurno3;
    }

    public void setHiniTurno3(String hiniTurno3) {
        this.hiniTurno3 = hiniTurno3;
    }

    public String getHfinTurno3() {
        return hfinTurno3;
    }

    public void setHfinTurno3(String hfinTurno3) {
        this.hfinTurno3 = hfinTurno3;
    }

    public Integer getIdGenericaJornadaMotivo() {
        return idGenericaJornadaMotivo;
    }

    public void setIdGenericaJornadaMotivo(Integer idGenericaJornadaMotivo) {
        this.idGenericaJornadaMotivo = idGenericaJornadaMotivo;
    }

    public Integer getIdGenericaJornadaTipo() {
        return idGenericaJornadaTipo;
    }

    public void setIdGenericaJornadaTipo(Integer idGenericaJornadaTipo) {
        this.idGenericaJornadaTipo = idGenericaJornadaTipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUserGenera() {
        return userGenera;
    }

    public void setUserGenera(String userGenera) {
        this.userGenera = userGenera;
    }

    public Date getFechaGenera() {
        return fechaGenera;
    }

    public void setFechaGenera(Date fechaGenera) {
        this.fechaGenera = fechaGenera;
    }

    public Integer getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Integer autorizado) {
        this.autorizado = autorizado;
    }

    public String getUserAutorizado() {
        return userAutorizado;
    }

    public void setUserAutorizado(String userAutorizado) {
        this.userAutorizado = userAutorizado;
    }

    public Date getFechaAutoriza() {
        return fechaAutoriza;
    }

    public void setFechaAutoriza(Date fechaAutoriza) {
        this.fechaAutoriza = fechaAutoriza;
    }

    public Integer getLiquidado() {
        return liquidado;
    }

    public void setLiquidado(Integer liquidado) {
        this.liquidado = liquidado;
    }

    public String getUserLiquida() {
        return userLiquida;
    }

    public void setUserLiquida(String userLiquida) {
        this.userLiquida = userLiquida;
    }

    public Date getFechaLiquida() {
        return fechaLiquida;
    }

    public void setFechaLiquida(Date fechaLiquida) {
        this.fechaLiquida = fechaLiquida;
    }

    public Integer getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(Integer tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public Integer getNominaBorrada() {
        return nominaBorrada;
    }

    public void setNominaBorrada(Integer nominaBorrada) {
        this.nominaBorrada = nominaBorrada;
    }

    public String getDiurnas() {
        return diurnas;
    }

    public void setDiurnas(String diurnas) {
        this.diurnas = diurnas;
    }

    public String getNocturnas() {
        return nocturnas;
    }

    public void setNocturnas(String nocturnas) {
        this.nocturnas = nocturnas;
    }

    public String getExtraDiurna() {
        return extraDiurna;
    }

    public void setExtraDiurna(String extraDiurna) {
        this.extraDiurna = extraDiurna;
    }

    public String getExtraNocturna() {
        return extraNocturna;
    }

    public void setExtraNocturna(String extraNocturna) {
        this.extraNocturna = extraNocturna;
    }

    public String getFestivoDiurno() {
        return festivoDiurno;
    }

    public void setFestivoDiurno(String festivoDiurno) {
        this.festivoDiurno = festivoDiurno;
    }

    public String getFestivoNocturno() {
        return festivoNocturno;
    }

    public void setFestivoNocturno(String festivoNocturno) {
        this.festivoNocturno = festivoNocturno;
    }

    public String getFestivoExtraDiurno() {
        return festivoExtraDiurno;
    }

    public void setFestivoExtraDiurno(String festivoExtraDiurno) {
        this.festivoExtraDiurno = festivoExtraDiurno;
    }

    public String getFestivoExtraNocturno() {
        return festivoExtraNocturno;
    }

    public void setFestivoExtraNocturno(String festivoExtraNocturno) {
        this.festivoExtraNocturno = festivoExtraNocturno;
    }

    public String getDominicalCompDiurnas() {
        return dominicalCompDiurnas;
    }

    public void setDominicalCompDiurnas(String dominicalCompDiurnas) {
        this.dominicalCompDiurnas = dominicalCompDiurnas;
    }

    public String getDominicalCompNocturnas() {
        return dominicalCompNocturnas;
    }

    public void setDominicalCompNocturnas(String dominicalCompNocturnas) {
        this.dominicalCompNocturnas = dominicalCompNocturnas;
    }

    public String getDominicalCompDiurnaExtra() {
        return dominicalCompDiurnaExtra;
    }

    public void setDominicalCompDiurnaExtra(String dominicalCompDiurnaExtra) {
        this.dominicalCompDiurnaExtra = dominicalCompDiurnaExtra;
    }

    public String getDominicalCompNocturnaExtra() {
        return dominicalCompNocturnaExtra;
    }

    public void setDominicalCompNocturnaExtra(String dominicalCompNocturnaExtra) {
        this.dominicalCompNocturnaExtra = dominicalCompNocturnaExtra;
    }

    public String getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(String productionTime) {
        this.productionTime = productionTime;
    }

    public String getCompensatorio() {
        return compensatorio;
    }

    public void setCompensatorio(String compensatorio) {
        this.compensatorio = compensatorio;
    }

    public String getProductionTimeReal() {
        return productionTimeReal;
    }

    public void setProductionTimeReal(String productionTimeReal) {
        this.productionTimeReal = productionTimeReal;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public Integer getIdGenericaJornada() {
        return idGenericaJornada;
    }

    public void setIdGenericaJornada(Integer idGenericaJornada) {
        this.idGenericaJornada = idGenericaJornada;
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

    public String getUserElimina() {
        return userElimina;
    }

    public void setUserElimina(String userElimina) {
        this.userElimina = userElimina;
    }

    public Date getCreadoElimina() {
        return creadoElimina;
    }

    public void setCreadoElimina(Date creadoElimina) {
        this.creadoElimina = creadoElimina;
    }

    public Integer getEstadoRegElimina() {
        return estadoRegElimina;
    }

    public void setEstadoRegElimina(Integer estadoRegElimina) {
        this.estadoRegElimina = estadoRegElimina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaJornadaHistorial != null ? idGenericaJornadaHistorial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaJornadaHistorial)) {
            return false;
        }
        GenericaJornadaHistorial other = (GenericaJornadaHistorial) object;
        if ((this.idGenericaJornadaHistorial == null && other.idGenericaJornadaHistorial != null) || (this.idGenericaJornadaHistorial != null && !this.idGenericaJornadaHistorial.equals(other.idGenericaJornadaHistorial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaJornadaHistorial[ idGenericaJornadaHistorial=" + idGenericaJornadaHistorial + " ]";
    }

}
