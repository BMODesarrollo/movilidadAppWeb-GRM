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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "generica_jornada_inicial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaJornadaInicial.findAll", query = "SELECT g FROM GenericaJornadaInicial g"),
    @NamedQuery(name = "GenericaJornadaInicial.findByIdGenericaJornada", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.idGenericaJornada = :idGenericaJornada"),
    @NamedQuery(name = "GenericaJornadaInicial.findByFecha", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "GenericaJornadaInicial.findBySercon", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.sercon = :sercon"),
    @NamedQuery(name = "GenericaJornadaInicial.findByTimeOrigin", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.timeOrigin = :timeOrigin"),
    @NamedQuery(name = "GenericaJornadaInicial.findByTimeDestiny", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.timeDestiny = :timeDestiny"),
    @NamedQuery(name = "GenericaJornadaInicial.findByUsername", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaJornadaInicial.findByCreado", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaJornadaInicial.findByModificado", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaJornadaInicial.findByCargada", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.cargada = :cargada"),
    @NamedQuery(name = "GenericaJornadaInicial.findByPrgModificada", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.prgModificada = :prgModificada"),
    @NamedQuery(name = "GenericaJornadaInicial.findByRealTimeOrigin", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.realTimeOrigin = :realTimeOrigin"),
    @NamedQuery(name = "GenericaJornadaInicial.findByRealTimeDestiny", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.realTimeDestiny = :realTimeDestiny"),
    @NamedQuery(name = "GenericaJornadaInicial.findByHiniTurno2", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.hiniTurno2 = :hiniTurno2"),
    @NamedQuery(name = "GenericaJornadaInicial.findByHfinTurno2", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.hfinTurno2 = :hfinTurno2"),
    @NamedQuery(name = "GenericaJornadaInicial.findByHiniTurno3", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.hiniTurno3 = :hiniTurno3"),
    @NamedQuery(name = "GenericaJornadaInicial.findByHfinTurno3", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.hfinTurno3 = :hfinTurno3"),
    @NamedQuery(name = "GenericaJornadaInicial.findByUserGenera", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.userGenera = :userGenera"),
    @NamedQuery(name = "GenericaJornadaInicial.findByFechaGenera", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.fechaGenera = :fechaGenera"),
    @NamedQuery(name = "GenericaJornadaInicial.findByAutorizado", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.autorizado = :autorizado"),
    @NamedQuery(name = "GenericaJornadaInicial.findByUserAutorizado", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.userAutorizado = :userAutorizado"),
    @NamedQuery(name = "GenericaJornadaInicial.findByFechaAutoriza", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.fechaAutoriza = :fechaAutoriza"),
    @NamedQuery(name = "GenericaJornadaInicial.findByLiquidado", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.liquidado = :liquidado"),
    @NamedQuery(name = "GenericaJornadaInicial.findByUserLiquida", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.userLiquida = :userLiquida"),
    @NamedQuery(name = "GenericaJornadaInicial.findByFechaLiquida", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.fechaLiquida = :fechaLiquida"),
    @NamedQuery(name = "GenericaJornadaInicial.findByTipoCalculo", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.tipoCalculo = :tipoCalculo"),
    @NamedQuery(name = "GenericaJornadaInicial.findByNominaBorrada", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.nominaBorrada = :nominaBorrada"),
    @NamedQuery(name = "GenericaJornadaInicial.findByDiurnas", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.diurnas = :diurnas"),
    @NamedQuery(name = "GenericaJornadaInicial.findByNocturnas", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.nocturnas = :nocturnas"),
    @NamedQuery(name = "GenericaJornadaInicial.findByExtraDiurna", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.extraDiurna = :extraDiurna"),
    @NamedQuery(name = "GenericaJornadaInicial.findByExtraNocturna", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.extraNocturna = :extraNocturna"),
    @NamedQuery(name = "GenericaJornadaInicial.findByFestivoDiurno", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.festivoDiurno = :festivoDiurno"),
    @NamedQuery(name = "GenericaJornadaInicial.findByFestivoNocturno", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.festivoNocturno = :festivoNocturno"),
    @NamedQuery(name = "GenericaJornadaInicial.findByFestivoExtraDiurno", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.festivoExtraDiurno = :festivoExtraDiurno"),
    @NamedQuery(name = "GenericaJornadaInicial.findByFestivoExtraNocturno", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.festivoExtraNocturno = :festivoExtraNocturno"),
    @NamedQuery(name = "GenericaJornadaInicial.findByProductionTime", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.productionTime = :productionTime"),
    @NamedQuery(name = "GenericaJornadaInicial.findByCompensatorio", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.compensatorio = :compensatorio"),
    @NamedQuery(name = "GenericaJornadaInicial.findByProductionTimeReal", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.productionTimeReal = :productionTimeReal"),
    @NamedQuery(name = "GenericaJornadaInicial.findByEstadoReg", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.estadoReg = :estadoReg"),
    @NamedQuery(name = "GenericaJornadaInicial.findByEstadoMarcacion", query = "SELECT g FROM GenericaJornadaInicial g WHERE g.estadoMarcacion = :estadoMarcacion")})

public class GenericaJornadaInicial implements Serializable {

    @Column(name = "cargada")
    private int cargada;
    @Column(name = "nomina_borrada")
    private int nominaBorrada;
    @Size(max = 15)
    @Column(name = "orden_trabajo")
    private String ordenTrabajo;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_jornada_inicial")
    private Integer idGenericaJornadaInicial;
    @JoinColumn(name = "id_generica_jornada", referencedColumnName = "id_generica_jornada")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaJornada idGenericaJornada;    
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 30)
    @Column(name = "sercon")
    private String sercon;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
    @Size(max = 8)
    @Column(name = "bio_salida")
    private String bioSalida;
    @Size(max = 8)
    @Column(name = "bio_entrada")
    private String bioEntrada;
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
    @Column(name = "production_time")
    private String productionTime;
    @Size(max = 8)
    @Column(name = "compensatorio")
    private String compensatorio;
    @Size(max = 8)
    @Column(name = "production_time_real")
    private String productionTimeReal;
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
    @Column(name = "work_time")
    private String workTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @Basic(optional = false)
    @Column(name = "estado_marcacion")
    private int estadoMarcacion;
    @Basic(optional = false)
    @Column(name = "marcacion_gestionada")
    private int marcacionGestionada;
    @Basic(optional = false)
    @Column(name = "marcacion_autorizada")
    private int marcacionAutorizada;
    @Size(max = 15)
    @Column(name = "username_gestion")
    private String usernameGestion;
    @Size(max = 15)
    @Column(name = "username_auth")
    private String usernameAuth;
    @Column(name = "fecha_gestion")
    @Temporal(TemporalType.DATE)
    private Date fechaGestion;
    @Column(name = "fecha_auth")
    @Temporal(TemporalType.DATE)
    private Date fechaAuth;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_generica_jornada_motivo", referencedColumnName = "id_generica_jornada_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaJornadaMotivo idGenericaJornadaMotivo;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_generica_jornada_tipo", referencedColumnName = "id_generica_jornada_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaJornadaTipo idGenericaJornadaTipo;
    @Size(max = 8)
    @Column(name = "real_hini_turno2")
    private String realHiniTurno2;
    @Size(max = 8)
    @Column(name = "real_hfin_turno2")
    private String realHfinTurno2;
//    @Size(max = 8)
//    @Column(name = "real_hini_turno3")
//    private String realHiniTurno3;
//    @Size(max = 8)
//    @Column(name = "real_hfin_turno3")
//    private String realHfinTurno3;
    public GenericaJornadaInicial() {
    }

    public GenericaJornadaInicial(Integer idGenericaJornadaInicial) {
        this.idGenericaJornadaInicial = idGenericaJornadaInicial;
    }

    public GenericaJornadaInicial(Integer idGenericaJornadaInicial, String username, Date creado, int estadoReg) {
        this.idGenericaJornadaInicial = idGenericaJornadaInicial;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaJornadaInic() {
        return idGenericaJornadaInicial;
    }

    public void setIdGenericaJornada(Integer idGenericaJornadaInicial) {
        this.idGenericaJornadaInicial = idGenericaJornadaInicial;
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

    public String getUsernameGestion() {
        return usernameGestion;
    }

    public void setUsernameGestion(String usernameGestion) {
        this.usernameGestion = usernameGestion;
    }

    public String getUsernameAuth() {
        return usernameAuth;
    }

    public void setUsernameAuth(String usernameAuth) {
        this.usernameAuth = usernameAuth;
    }

    public Date getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(Date fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public Date getFechaAuth() {
        return fechaAuth;
    }

    public void setFechaAuth(Date fechaAuth) {
        this.fechaAuth = fechaAuth;
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

    public int getNominaBorrada() {
        return nominaBorrada;
    }

    public void setNominaBorrada(int nominaBorrada) {
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public GenericaJornadaMotivo getIdGenericaJornadaMotivo() {
        return idGenericaJornadaMotivo;
    }

    public void setIdGenericaJornadaMotivo(GenericaJornadaMotivo idGenericaJornadaMotivo) {
        this.idGenericaJornadaMotivo = idGenericaJornadaMotivo;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public GenericaJornadaTipo getIdGenericaJornadaTipo() {
        return idGenericaJornadaTipo;
    }

    public void setIdGenericaJornadaTipo(GenericaJornadaTipo idGenericaJornadaTipo) {
        this.idGenericaJornadaTipo = idGenericaJornadaTipo;
    }

    public String getBioSalida() {
        return bioSalida;
    }

    public void setBioSalida(String bioSalida) {
        this.bioSalida = bioSalida;
    }

    public String getBioEntrada() {
        return bioEntrada;
    }

    public void setBioEntrada(String bioEntrada) {
        this.bioEntrada = bioEntrada;
    }

    public int getEstadoMarcacion() {
        return estadoMarcacion;
    }

    public void setEstadoMarcacion(int estadoMarcacion) {
        this.estadoMarcacion = estadoMarcacion;
    }

    public int getMarcacionGestionada() {
        return marcacionGestionada;
    }

    public void setMarcacionGestionada(int marcacionGestionada) {
        this.marcacionGestionada = marcacionGestionada;
    }

    public int getMarcacionAutorizada() {
        return marcacionAutorizada;
    }

    public void setMarcacionAutorizada(int marcacionAutorizada) {
        this.marcacionAutorizada = marcacionAutorizada;
    }
    
//    public String getRealHiniTurno2() {
//        return realHiniTurno2;
//    }
//
//    public void setRealHiniTurno2(String realHiniTurno2) {
//        this.realHiniTurno2 = realHiniTurno2;
//    }
//
//    public String getRealHfinTurno2() {
//        return realHfinTurno2;
//    }
//
//    public void setRealHfinTurno2(String realHfinTurno2) {
//        this.realHfinTurno2 = realHfinTurno2;
//    }
//
//    public String getRealHiniTurno3() {
//        return realHiniTurno3;
//    }
//
//    public void setRealHiniTurno3(String realHiniTurno3) {
//        this.realHiniTurno3 = realHiniTurno3;
//    }
//
//    public String getRealHfinTurno3() {
//        return realHfinTurno3;
//    }
//
//    public void setRealHfinTurno3(String realHfinTurno3) {
//        this.realHfinTurno3 = realHfinTurno3;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaJornada != null ? idGenericaJornada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaJornada)) {
            return false;
        }
        GenericaJornadaInicial other = (GenericaJornadaInicial) object;
//        if ((this.idGenericaJornada == null && other.idGenericaJornada != null) || (this.idGenericaJornada != null && !this.idGenericaJornada.equals(other.idGenericaJornada))) {
//            return false;
//        }
        return !((this.idGenericaJornada == null && other.idGenericaJornada != null) || (this.idGenericaJornada != null && !this.idGenericaJornada.equals(other.idGenericaJornada)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaJornadaInicial[ idGenericaJornada=" + idGenericaJornada + " ]";
    }

    public int getCargada() {
        return cargada;
    }

    public void setCargada(int cargada) {
        this.cargada = cargada;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
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

//    @XmlTransient
//    public List<GenericaJornadaToken> getGenericaJorandaTokenList() {
//        return genericaJorandaTokenList;
//    }
//
//    public void setGenericaJorandaTokenList(List<GenericaJornadaToken> genericaJorandaTokenList) {
//        this.genericaJorandaTokenList = genericaJorandaTokenList;
//    }
//
//    @XmlTransient
//    public List<GenericaSolicitud> getGenericaSolicitudList() {
//        return genericaSolicitudList;
//    }
//
//    public void setGenericaSolicitudList(List<GenericaSolicitud> genericaSolicitudList) {
//        this.genericaSolicitudList = genericaSolicitudList;
//    }
//
//    @XmlTransient
//    public List<GenericaSolicitudCambio> getGenericaSolicitudCambioList() {
//        return genericaSolicitudCambioList;
//    }
//
//    public void setGenericaSolicitudCambioList(List<GenericaSolicitudCambio> genericaSolicitudCambioList) {
//        this.genericaSolicitudCambioList = genericaSolicitudCambioList;
//    }
//
//    @XmlTransient
//    public List<GenericaJornadaDet> getGenericaJornadaDetList() {
//        return genericaJornadaDetList;
//    }
//
//    public void setGenericaJornadaDetList(List<GenericaJornadaDet> genericaJornadaDetList) {
//        this.genericaJornadaDetList = genericaJornadaDetList;
//    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getRealHiniTurno2() {
        return realHiniTurno2;
    }

    public void setRealHiniTurno2(String realHiniTurno2) {
        this.realHiniTurno2 = realHiniTurno2;
    }

    public String getRealHfinTurno2() {
        return realHfinTurno2;
    }

    public void setRealHfinTurno2(String realHfinTurno2) {
        this.realHfinTurno2 = realHfinTurno2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
