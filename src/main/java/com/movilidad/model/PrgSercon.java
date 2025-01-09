package com.movilidad.model;

import com.movilidad.dto.EmpleadoDescansoDTO;
import com.movilidad.dto.EntradaSalidaJornadaDTO;
import com.movilidad.dto.HoraPrgEjecDTO;
import com.movilidad.dto.PrgSerconDTO;
import com.movilidad.util.beans.ConsolidadoLiquidacionDetallado;
import com.movilidad.util.beans.ConsolidadoLiquidacionGMO;
import com.movilidad.util.beans.ConsolidadoNominaDetallado;
import com.movilidad.util.beans.ReporteHoras;
import com.movilidad.util.beans.ReporteHorasKactus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
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
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "prg_sercon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgSercon.findAll", query = "SELECT p FROM PrgSercon p")
    ,
    @NamedQuery(name = "PrgSercon.findByIdPrgSercon", query = "SELECT p FROM PrgSercon p WHERE p.idPrgSercon = :idPrgSercon")
    ,
    @NamedQuery(name = "PrgSercon.findByFecha", query = "SELECT p FROM PrgSercon p WHERE p.fecha = :fecha")
    ,
    @NamedQuery(name = "PrgSercon.findBySercon", query = "SELECT p FROM PrgSercon p WHERE p.sercon = :sercon")
    ,
    @NamedQuery(name = "PrgSercon.findByTimeOrigin", query = "SELECT p FROM PrgSercon p WHERE p.timeOrigin = :timeOrigin")
    ,
    @NamedQuery(name = "PrgSercon.findByTimeDestiny", query = "SELECT p FROM PrgSercon p WHERE p.timeDestiny = :timeDestiny")
    ,
    @NamedQuery(name = "PrgSercon.findByAmplitude", query = "SELECT p FROM PrgSercon p WHERE p.amplitude = :amplitude")
    ,
    @NamedQuery(name = "PrgSercon.findByWorkTime", query = "SELECT p FROM PrgSercon p WHERE p.workTime = :workTime")
    ,
    @NamedQuery(name = "PrgSercon.findByUsername", query = "SELECT p FROM PrgSercon p WHERE p.username = :username")
    ,
    @NamedQuery(name = "PrgSercon.findByCreado", query = "SELECT p FROM PrgSercon p WHERE p.creado = :creado")
    ,
    @NamedQuery(name = "PrgSercon.findByModificado", query = "SELECT p FROM PrgSercon p WHERE p.modificado = :modificado")
    ,
    @NamedQuery(name = "PrgSercon.findByPrgModificada", query = "SELECT p FROM PrgSercon p WHERE p.prgModificada = :prgModificada")
    ,
    @NamedQuery(name = "PrgSercon.findByRealTimeOrigin", query = "SELECT p FROM PrgSercon p WHERE p.realTimeOrigin = :realTimeOrigin")
    ,
    @NamedQuery(name = "PrgSercon.findByRealTimeDestiny", query = "SELECT p FROM PrgSercon p WHERE p.realTimeDestiny = :realTimeDestiny")
    ,
    @NamedQuery(name = "PrgSercon.findByUserGenera", query = "SELECT p FROM PrgSercon p WHERE p.userGenera = :userGenera")
    ,
    @NamedQuery(name = "PrgSercon.findByFechaGenera", query = "SELECT p FROM PrgSercon p WHERE p.fechaGenera = :fechaGenera")
    ,
    @NamedQuery(name = "PrgSercon.findByAutorizado", query = "SELECT p FROM PrgSercon p WHERE p.autorizado = :autorizado")
    ,
    @NamedQuery(name = "PrgSercon.findByUserAutorizado", query = "SELECT p FROM PrgSercon p WHERE p.userAutorizado = :userAutorizado")
    ,
    @NamedQuery(name = "PrgSercon.findByFechaAutoriza", query = "SELECT p FROM PrgSercon p WHERE p.fechaAutoriza = :fechaAutoriza")
    ,
    @NamedQuery(name = "PrgSercon.findByLiquidado", query = "SELECT p FROM PrgSercon p WHERE p.liquidado = :liquidado")
    ,
    @NamedQuery(name = "PrgSercon.findByUserLiquida", query = "SELECT p FROM PrgSercon p WHERE p.userLiquida = :userLiquida")
    ,
    @NamedQuery(name = "PrgSercon.findByFechaLiquida", query = "SELECT p FROM PrgSercon p WHERE p.fechaLiquida = :fechaLiquida")
    ,
    @NamedQuery(name = "PrgSercon.findByEstadoReg", query = "SELECT p FROM PrgSercon p WHERE p.estadoReg = :estadoReg")})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "ReporteHorasMapping",
            classes = {
                @ConstructorResult(targetClass = ReporteHoras.class,
                        columns = {
                            @ColumnResult(name = "identificacion")
                            ,
                            @ColumnResult(name = "diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_diurna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_nocturna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturna_extra", type = BigDecimal.class),}
                )
            })
    ,
    @SqlResultSetMapping(name = "ReporteHorasKactusMapping",
            classes = {
                @ConstructorResult(targetClass = ReporteHorasKactus.class,
                        columns = {
                            @ColumnResult(name = "diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_diurna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_nocturna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturna_extra", type = BigDecimal.class),}
                )
            })
    ,
    @SqlResultSetMapping(name = "prgSerconDTOMapping",
            classes = {
                @ConstructorResult(targetClass = PrgSerconDTO.class,
                        columns = {
                            @ColumnResult(name = "id_prg_sercon")
                            ,
                            @ColumnResult(name = "codigo_tm", type = Integer.class)
                            ,
                            @ColumnResult(name = "time_origin")
                            ,
                            @ColumnResult(name = "time_destiny")}
                )
            })
    ,
    @SqlResultSetMapping(name = "ReporteConsolidadoLiqDetalladoMapping",
            classes = {
                @ConstructorResult(targetClass = ConsolidadoLiquidacionDetallado.class,
                        columns = {
                            @ColumnResult(name = "fecha")
                            ,
                            @ColumnResult(name = "identificacion")
                            ,
                            @ColumnResult(name = "nombre")
                            ,
                            @ColumnResult(name = "diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_diurna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_nocturna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "motivo", type = String.class)
                            ,
                            @ColumnResult(name = "observaciones", type = String.class),}
                )
            })
    ,
    @SqlResultSetMapping(name = "ReporteConsolidadoLiqDetalladoMapping",
            classes = {
                @ConstructorResult(targetClass = ConsolidadoLiquidacionDetallado.class,
                        columns = {
                            @ColumnResult(name = "fecha")
                            ,
                            @ColumnResult(name = "identificacion")
                            ,
                            @ColumnResult(name = "nombre")
                            ,
                            @ColumnResult(name = "diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_diurna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_nocturna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "motivo", type = String.class)
                            ,
                            @ColumnResult(name = "observaciones", type = String.class),}
                )
            })
    ,
    @SqlResultSetMapping(name = "ReporteDetalladoNominaMapping",
            classes = {
                @ConstructorResult(targetClass = ConsolidadoNominaDetallado.class,
                        columns = {
                            @ColumnResult(name = "empresa")
                            ,
                            @ColumnResult(name = "identificacion")
                            ,
                            @ColumnResult(name = "nombre")
                            ,
                            @ColumnResult(name = "fecha")
                            ,
                            @ColumnResult(name = "time_origin")
                            ,
                            @ColumnResult(name = "time_destiny")
                            ,
                            @ColumnResult(name = "diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_diurna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_nocturna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturna_extra", type = BigDecimal.class),}
                )
            })
    ,
    @SqlResultSetMapping(name = "ReporteConsolidadoLiqQuincenalMapping",
            classes = {
                @ConstructorResult(targetClass = ConsolidadoLiquidacionGMO.class,
                        columns = {
                            @ColumnResult(name = "desde", type = Date.class)
                            ,
                            @ColumnResult(name = "hasta", type = Date.class)
                            ,
                            @ColumnResult(name = "quincena", type = String.class)
                            ,
                            @ColumnResult(name = "nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_diurna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_nocturna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturna_extra", type = BigDecimal.class),}
                )
            })
    ,
    @SqlResultSetMapping(name = "ReporteConsolidadoLiqDetallelMapping",
            classes = {
                @ConstructorResult(targetClass = ConsolidadoLiquidacionGMO.class,
                        columns = {
                            @ColumnResult(name = "fecha", type = Date.class)
                            ,
                            @ColumnResult(name = "quincena", type = String.class)
                            ,
                            @ColumnResult(name = "salario", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_diurna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "extra_nocturna", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_diurno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "festivo_extra_nocturno", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturnas", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_diurna_extra", type = BigDecimal.class)
                            ,
                            @ColumnResult(name = "dominical_comp_nocturna_extra", type = BigDecimal.class),}
                )
            })
    ,
    @SqlResultSetMapping(name = "EntradaSalidaJornadaMapping",
            classes = {
                @ConstructorResult(targetClass = EntradaSalidaJornadaDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha", type = Date.class)
                            ,
                            @ColumnResult(name = "nombre_uf", type = String.class)
                            ,
                            @ColumnResult(name = "identificacion", type = String.class)
                            ,
                            @ColumnResult(name = "codigo_tm", type = String.class)
                            ,
                            @ColumnResult(name = "nombre_operador", type = String.class)
                            ,
                            @ColumnResult(name = "hora_ingreso_prg", type = String.class)
                            ,
                            @ColumnResult(name = "presentacion_mymovil", type = Integer.class)
                            ,
                            @ColumnResult(name = "hora_ingreso_presentacion", type = String.class)
                            ,
                            @ColumnResult(name = "hora_salida_prg", type = String.class)
                            ,
                            @ColumnResult(name = "salida_mymovil", type = Integer.class)
                            ,
                            @ColumnResult(name = "hora_salida_registrada", type = String.class)
                            ,
                            @ColumnResult(name = "prg_modificada", type = Integer.class)
                            ,
                            @ColumnResult(name = "nomina_borrada", type = Integer.class)
                            ,
                            @ColumnResult(name = "total_horas_programdas", type = String.class)
                            ,
                            @ColumnResult(name = "total_horas_reales", type = String.class)
                            ,
                            @ColumnResult(name = "ultima_ruta", type = String.class),}
                )
            })
    ,
    @SqlResultSetMapping(name = "HoraPrgEjecMapping",
            classes = {
                @ConstructorResult(targetClass = HoraPrgEjecDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha_inicio", type = Date.class)
                            ,
                            @ColumnResult(name = "fecha_fin", type = Date.class)
                            ,
                            @ColumnResult(name = "nombre_uf", type = String.class)
                            ,
                            @ColumnResult(name = "identificacion", type = String.class)
                            ,
                            @ColumnResult(name = "codigo_tm", type = Integer.class)
                            ,
                            @ColumnResult(name = "nombres", type = String.class)
                            ,
                            @ColumnResult(name = "apellidos", type = String.class)
                            ,
                            @ColumnResult(name = "horas_programadas", type = String.class)
                            ,
                            @ColumnResult(name = "horas_reales", type = String.class)
                            ,
                            @ColumnResult(name = "dias_sin_operar", type = Long.class)}
                )
            })
    ,
    @SqlResultSetMapping(name = "EmpleadoDescansoMapping",
            classes = {
                @ConstructorResult(targetClass = EmpleadoDescansoDTO.class,
                        columns = {
                            @ColumnResult(name = "id_empleado", type = Integer.class)
                            ,
                            @ColumnResult(name = "total_dias_descanso", type = Integer.class)}
                )
            }),})
public class PrgSercon implements Serializable {

    @OneToMany(mappedBy = "idPrgSerconReemplazo")
    private List<PrgSolicitudCambio> prgSolicitudCambioList;

    @OneToMany(mappedBy = "idPrgSercon")
    private List<PrgSolicitud> prgSolicitudList;
    @OneToMany(mappedBy = "idPrgSercon")
    private List<PrgSerconDet> prgSerconDetList;

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
    @Column(name = "tipo_calculo")
    private Integer tipoCalculo;
    @Column(name = "nomina_borrada")
    private int nominaBorrada;
    @Size(max = 15)
    @Column(name = "production_time")
    private String productionTime;
    @Size(max = 15)
    @Column(name = "production_time_real")
    private String productionTimeReal;
    @Column(name = "cargada")
    private int cargada;

    @Size(max = 15)
    @Column(name = "diurnas")
    private String diurnas;
    @Size(max = 15)
    @Column(name = "nocturnas")
    private String nocturnas;
    @Size(max = 15)
    @Column(name = "extra_diurna")
    private String extraDiurna;
    @Size(max = 15)
    @Column(name = "festivo_diurno")
    private String festivoDiurno;
    @Size(max = 15)
    @Column(name = "festivo_nocturno")
    private String festivoNocturno;
    @Size(max = 15)
    @Column(name = "festivo_extra_diurno")
    private String festivoExtraDiurno;
    @Size(max = 15)
    @Column(name = "festivo_extra_nocturno")
    private String festivoExtraNocturno;
    @Size(max = 15)
    @Column(name = "compensatorio")
    private String compensatorio;
    @Size(max = 15)
    @Column(name = "extra_nocturna")
    private String extraNocturna;

    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_from_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idFromStop;
    @JoinColumn(name = "id_prg_sercon_motivo", referencedColumnName = "id_prg_sercon_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgSerconMotivo idPrgSerconMotivo;
    @JoinColumn(name = "id_to_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idToStop;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_sercon")
    private Integer idPrgSercon;
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
    @Column(name = "amplitude")
    private String amplitude;
    @Size(max = 8)
    @Column(name = "work_time")
    private String workTime;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
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
    @Column(name = "real_hini_turno2")
//    @Transient
    private String realHiniTurno2;
    @Size(max = 8)
    @Column(name = "real_hfin_turno2")
//    @Transient
    private String realHfinTurno2;
    @Size(max = 8)
    @Column(name = "real_hini_turno3")
//    @Transient
    private String realHiniTurno3;
    @Size(max = 8)
    @Column(name = "real_hfin_turno3")
//    @Transient
    private String realHfinTurno3;
    @Column(name = "visto")
//    @Transient
    private int visto;

    @JoinColumn(name = "id_my_app_sercon_confirm", referencedColumnName = "id_my_app_sercon_confirm")
    @ManyToOne(fetch = FetchType.LAZY)
    private MyAppSerconConfirm idMyAppSerconConfirm;

    @JoinColumn(name = "id_my_app_sercon_confirm_last", referencedColumnName = "id_my_app_sercon_confirm_last")
    @ManyToOne(fetch = FetchType.LAZY)
    private MyAppSerconConfirmLast idMyAppSerconConfirmLast;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgSercon() {
    }

    public PrgSercon(Integer idPrgSercon) {
        this.idPrgSercon = idPrgSercon;
    }

    public PrgSercon(Integer idPrgSercon, String username, Date creado, int estadoReg) {
        this.idPrgSercon = idPrgSercon;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgSercon() {
        return idPrgSercon;
    }

    public void setIdPrgSercon(Integer idPrgSercon) {
        this.idPrgSercon = idPrgSercon;
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

    public String getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgSercon != null ? idPrgSercon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgSercon)) {
            return false;
        }
        PrgSercon other = (PrgSercon) object;
        if ((this.idPrgSercon == null && other.idPrgSercon != null) || (this.idPrgSercon != null && !this.idPrgSercon.equals(other.idPrgSercon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PrgSercon{" + "hiniTurno2=" + hiniTurno2 + ", \nhfinTurno2=" + hfinTurno2 + ", \nhiniTurno3=" + hiniTurno3 + ", \nhfinTurno3=" + hfinTurno3 + ", \ndiurnas=" + diurnas + ", \nnocturnas=" + nocturnas + ", \nextraDiurna=" + extraDiurna + ", \nfestivoDiurno=" + festivoDiurno + ", \nfestivoNocturno=" + festivoNocturno + ", \nfestivoExtraDiurno=" + festivoExtraDiurno + ", \nfestivoExtraNocturno=" + festivoExtraNocturno + ", \ncompensatorio=" + compensatorio + ", \nextraNocturna=" + extraNocturna + ", \ntimeOrigin=" + timeOrigin + ", \ntimeDestiny=" + timeDestiny + ", \nrealTimeOrigin=" + realTimeOrigin + ", \nrealTimeDestiny=" + realTimeDestiny + ", \ndominicalCompDiurnas=" + dominicalCompDiurnas + ", \ndominicalCompNocturnas=" + dominicalCompNocturnas + ", \ndominicalCompDiurnaExtra=" + dominicalCompDiurnaExtra + ", \ndominicalCompNocturnaExtra=" + dominicalCompNocturnaExtra + ", \nrealHiniTurno2=" + realHiniTurno2 + ", \nrealHfinTurno2=" + realHfinTurno2 + ", \nrealHiniTurno3=" + realHiniTurno3 + ", \nrealHfinTurno3=" + realHfinTurno3 + '}';
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public PrgStopPoint getIdFromStop() {
        return idFromStop;
    }

    public void setIdFromStop(PrgStopPoint idFromStop) {
        this.idFromStop = idFromStop;
    }

    public PrgSerconMotivo getIdPrgSerconMotivo() {
        return idPrgSerconMotivo;
    }

    public void setIdPrgSerconMotivo(PrgSerconMotivo idPrgSerconMotivo) {
        this.idPrgSerconMotivo = idPrgSerconMotivo;
    }

    public PrgStopPoint getIdToStop() {
        return idToStop;
    }

    public void setIdToStop(PrgStopPoint idToStop) {
        this.idToStop = idToStop;
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

    public String getCompensatorio() {
        return compensatorio;
    }

    public void setCompensatorio(String compensatorio) {
        this.compensatorio = compensatorio;
    }

    public String getExtraNocturna() {
        return extraNocturna;
    }

    public void setExtraNocturna(String extraNocturna) {
        this.extraNocturna = extraNocturna;
    }

    public String getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(String productionTime) {
        this.productionTime = productionTime;
    }

    public int getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(int tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public int getNominaBorrada() {
        return nominaBorrada;
    }

    public void setNominaBorrada(int nominaBorrada) {
        this.nominaBorrada = nominaBorrada;
    }

    public void setTipoCalculo(Integer tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
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

    public String getProductionTimeReal() {
        return productionTimeReal;
    }

    public void setProductionTimeReal(String productionTimeReal) {
        this.productionTimeReal = productionTimeReal;
    }

    public int getCargada() {
        return cargada;
    }

    public void setCargada(int cargada) {
        this.cargada = cargada;
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

    @XmlTransient
    public List<PrgSolicitud> getPrgSolicitudList() {
        return prgSolicitudList;
    }

    public void setPrgSolicitudList(List<PrgSolicitud> prgSolicitudList) {
        this.prgSolicitudList = prgSolicitudList;
    }

    @XmlTransient
    public List<PrgSolicitudCambio> getPrgSolicitudCambioList() {
        return prgSolicitudCambioList;
    }

    public void setPrgSolicitudCambioList(List<PrgSolicitudCambio> prgSolicitudCambioList) {
        this.prgSolicitudCambioList = prgSolicitudCambioList;
    }

    @XmlTransient
    public List<PrgSerconDet> getPrgSerconDetList() {
        return prgSerconDetList;
    }

    public void setPrgSerconDetList(List<PrgSerconDet> prgSerconDetList) {
        this.prgSerconDetList = prgSerconDetList;
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

    public String getRealHiniTurno3() {
        return realHiniTurno3;
    }

    public void setRealHiniTurno3(String realHiniTurno3) {
        this.realHiniTurno3 = realHiniTurno3;
    }

    public String getRealHfinTurno3() {
        return realHfinTurno3;
    }

    public void setRealHfinTurno3(String realHfinTurno3) {
        this.realHfinTurno3 = realHfinTurno3;
    }

    public int getVisto() {
        return visto;
    }

    public void setVisto(int visto) {
        this.visto = visto;
    }

    public MyAppSerconConfirm getIdMyAppSerconConfirm() {
        return idMyAppSerconConfirm;
    }

    public void setIdMyAppSerconConfirm(MyAppSerconConfirm idMyAppSerconConfirm) {
        this.idMyAppSerconConfirm = idMyAppSerconConfirm;
    }

    public MyAppSerconConfirmLast getIdMyAppSerconConfirmLast() {
        return idMyAppSerconConfirmLast;
    }

    public void setIdMyAppSerconConfirmLast(MyAppSerconConfirmLast idMyAppSerconConfirmLast) {
        this.idMyAppSerconConfirmLast = idMyAppSerconConfirmLast;
    }

}
