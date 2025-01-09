/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.dto.InsumoProgramacionDTO;
import com.movilidad.dto.KmPrgEjecDTO;
import com.movilidad.dto.PorcentajeDisponibilidadFlotaDTO;
import com.movilidad.dto.PrimerServicioDTO;
import com.movilidad.dto.ReporteSemanaActualDTO;
import com.movilidad.dto.ServbusPlanificadoDTO;
import com.movilidad.dto.ServbusPlanificadoDetalleDTO;
import com.movilidad.dto.ServbusTipoTablaDTO;
import com.movilidad.dto.SumEliminadoResponsableDTO;
import com.movilidad.dto.SumEntradaSalidaDTO;
import com.movilidad.dto.TP28UltimoServicioDTO;
import com.movilidad.dto.UltimoServicioDTO;
import com.movilidad.util.beans.ConsolidadServicios;
import com.movilidad.util.beans.DiasSinOperar;
import com.movilidad.util.beans.InformeAmplitud;
import com.movilidad.util.beans.InformeOperacion;
import com.movilidad.util.beans.InformeOperadores;
import com.movilidad.util.beans.KmsAdicionalesCtrl;
import com.movilidad.util.beans.KmsComercial;
import com.movilidad.util.beans.KmsEjecutadoRes;
import com.movilidad.util.beans.KmsOperador;
import com.movilidad.util.beans.KmsPerdidosArt;
import com.movilidad.util.beans.KmsPerdidosBi;
import com.movilidad.util.beans.KmsResumen;
import com.movilidad.util.beans.KmsVehiculo;
import com.movilidad.util.beans.NovedadesTQ04;
import com.movilidad.util.beans.ServbusIdTipoVehiculo;
import com.movilidad.util.beans.ServiciosPorSalir;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "prg_tc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgTc.findAll", query = "SELECT p FROM PrgTc p"),
    @NamedQuery(name = "PrgTc.findByIdPrgTc", query = "SELECT p FROM PrgTc p WHERE p.idPrgTc = :idPrgTc"),
    @NamedQuery(name = "PrgTc.findByFecha", query = "SELECT p FROM PrgTc p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "PrgTc.findByTipoDia", query = "SELECT p FROM PrgTc p WHERE p.tipoDia = :tipoDia"),
    @NamedQuery(name = "PrgTc.findBySercon", query = "SELECT p FROM PrgTc p WHERE p.sercon = :sercon"),
    @NamedQuery(name = "PrgTc.findByAmplitude", query = "SELECT p FROM PrgTc p WHERE p.amplitude = :amplitude"),
    @NamedQuery(name = "PrgTc.findByWorkTime", query = "SELECT p FROM PrgTc p WHERE p.workTime = :workTime"),
    @NamedQuery(name = "PrgTc.findByProductionDistance", query = "SELECT p FROM PrgTc p WHERE p.productionDistance = :productionDistance"),
    @NamedQuery(name = "PrgTc.findByWorkPiece", query = "SELECT p FROM PrgTc p WHERE p.workPiece = :workPiece"),
    @NamedQuery(name = "PrgTc.findByTimeOrigin", query = "SELECT p FROM PrgTc p WHERE p.timeOrigin = :timeOrigin"),
    @NamedQuery(name = "PrgTc.findByTimeDestiny", query = "SELECT p FROM PrgTc p WHERE p.timeDestiny = :timeDestiny"),
    @NamedQuery(name = "PrgTc.findByTaskDuration", query = "SELECT p FROM PrgTc p WHERE p.taskDuration = :taskDuration"),
    @NamedQuery(name = "PrgTc.findByDistance", query = "SELECT p FROM PrgTc p WHERE p.distance = :distance"),
    @NamedQuery(name = "PrgTc.findByTmDistance", query = "SELECT p FROM PrgTc p WHERE p.tmDistance = :tmDistance"),
    @NamedQuery(name = "PrgTc.findByServbus", query = "SELECT p FROM PrgTc p WHERE p.servbus = :servbus"),
    @NamedQuery(name = "PrgTc.findByServicioBase", query = "SELECT p FROM PrgTc p WHERE p.servicioBase = :servicioBase"),
    @NamedQuery(name = "PrgTc.findByTabla", query = "SELECT p FROM PrgTc p WHERE p.tabla = :tabla"),
    @NamedQuery(name = "PrgTc.findByViajes", query = "SELECT p FROM PrgTc p WHERE p.viajes = :viajes"),
    @NamedQuery(name = "PrgTc.findByTrayecto", query = "SELECT p FROM PrgTc p WHERE p.trayecto = :trayecto"),
    @NamedQuery(name = "PrgTc.findByOldVehiculo", query = "SELECT p FROM PrgTc p WHERE p.oldVehiculo = :oldVehiculo"),
    @NamedQuery(name = "PrgTc.findByUsername", query = "SELECT p FROM PrgTc p WHERE p.username = :username"),
    @NamedQuery(name = "PrgTc.findByCreado", query = "SELECT p FROM PrgTc p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgTc.findByModificado", query = "SELECT p FROM PrgTc p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgTc.findByEstadoReg", query = "SELECT p FROM PrgTc p WHERE p.estadoReg = :estadoReg"),
    @NamedQuery(name = "PrgTc.findByEstadoOperacion", query = "SELECT p FROM PrgTc p WHERE p.estadoOperacion = :estadoOperacion")})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "KmByOperadorMapping",
            classes = {
                @ConstructorResult(targetClass = KmsOperador.class,
                        columns = {
                            @ColumnResult(name = "codigo_tm"),
                            @ColumnResult(name = "nombres"),
                            @ColumnResult(name = "apellidos"),
                            @ColumnResult(name = "total"),
                            @ColumnResult(name = "comercial"),
                            @ColumnResult(name = "hlp_prg"),
                            @ColumnResult(name = "adicionales"),
                            @ColumnResult(name = "vaccom"),
                            @ColumnResult(name = "comercial_Eliminados"),
                            @ColumnResult(name = "hlp_Eliminados"),
                            @ColumnResult(name = "vac")
                        }
                )
            }),
    @SqlResultSetMapping(name = "KmByVehiculoMapping",
            classes = {
                @ConstructorResult(targetClass = KmsVehiculo.class,
                        columns = {
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "codigo_vehiculo"),
                            @ColumnResult(name = "total_km", type = BigDecimal.class),
                            @ColumnResult(name = "total_mts", type = BigDecimal.class),
                            @ColumnResult(name = "comercial", type = BigDecimal.class),
                            @ColumnResult(name = "hlp_prg", type = BigDecimal.class),
                            @ColumnResult(name = "adicionales", type = BigDecimal.class),
                            @ColumnResult(name = "vaccom", type = BigDecimal.class),
                            @ColumnResult(name = "comercial_Eliminados", type = BigDecimal.class),
                            @ColumnResult(name = "hlp_Eliminados", type = BigDecimal.class),
                            @ColumnResult(name = "vac", type = BigDecimal.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "DiasSinOperarMapping",
            classes = {
                @ConstructorResult(targetClass = DiasSinOperar.class,
                        columns = {
                            @ColumnResult(name = "desde", type = String.class),
                            @ColumnResult(name = "hasta", type = String.class),
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "codigo_vehiculo"),
                            @ColumnResult(name = "total_km", type = BigDecimal.class),
                            @ColumnResult(name = "estado")

                        }
                )
            }),
    @SqlResultSetMapping(name = "KmsComercialMapping",
            classes = {
                @ConstructorResult(targetClass = KmsComercial.class,
                        columns = {
                            @ColumnResult(name = "codigo_vehiculo", type = int.class),
                            @ColumnResult(name = "comercial")
                        }
                )
            }),
    @SqlResultSetMapping(name = "KmsResumenMapping",
            classes = {
                @ConstructorResult(targetClass = KmsResumen.class,
                        columns = {
                            @ColumnResult(name = "AdcArt"),
                            @ColumnResult(name = "AdcBi"),
                            @ColumnResult(name = "ElimArt"),
                            @ColumnResult(name = "ElimBi")
                        }
                )
            }),
    @SqlResultSetMapping(name = "KmsEjecutadoResMapping",
            classes = {
                @ConstructorResult(targetClass = KmsEjecutadoRes.class,
                        columns = {
                            @ColumnResult(name = "hlpArt"),
                            @ColumnResult(name = "hlpBi")
                        }
                )
            }),
    @SqlResultSetMapping(name = "KmsPerdidosArtMapping",
            classes = {
                @ConstructorResult(targetClass = KmsPerdidosArt.class,
                        columns = {
                            @ColumnResult(name = "count_operaciones"),
                            @ColumnResult(name = "operaciones", type = BigDecimal.class),
                            @ColumnResult(name = "count_mtto"),
                            @ColumnResult(name = "mtto", type = BigDecimal.class),
                            @ColumnResult(name = "count_otros"),
                            @ColumnResult(name = "otros", type = BigDecimal.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "KmsPerdidosBiMapping",
            classes = {
                @ConstructorResult(targetClass = KmsPerdidosBi.class,
                        columns = {
                            @ColumnResult(name = "count_operaciones"),
                            @ColumnResult(name = "operaciones", type = BigDecimal.class),
                            @ColumnResult(name = "count_mtto"),
                            @ColumnResult(name = "mtto", type = BigDecimal.class),
                            @ColumnResult(name = "count_otros"),
                            @ColumnResult(name = "otros", type = BigDecimal.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "KmsAdicionalesCtrlMapping",
            classes = {
                @ConstructorResult(targetClass = KmsAdicionalesCtrl.class,
                        columns = {
                            @ColumnResult(name = "count_adicionales_art"),
                            @ColumnResult(name = "count_adicionales_bi")
                        }
                )
            }),
    @SqlResultSetMapping(name = "InformeOperadoresMapping",
            classes = {
                @ConstructorResult(targetClass = InformeOperadores.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "c_fecha"),
                            @ColumnResult(name = "codigo_tm"),
                            @ColumnResult(name = "nombres"),
                            @ColumnResult(name = "apellidos"),
                            @ColumnResult(name = "nombre_cargo"),
                            @ColumnResult(name = "certificado"),
                            @ColumnResult(name = "nombre_tipo_vehiculo"),
                            @ColumnResult(name = "total_viajes"),
                            @ColumnResult(name = "km_realizados")
                        }
                )
            }),
    @SqlResultSetMapping(name = "InformeAmplitudMapping",
            classes = {
                @ConstructorResult(targetClass = InformeAmplitud.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "codigo_tm"),
                            @ColumnResult(name = "identificacion"),
                            @ColumnResult(name = "nombres"),
                            @ColumnResult(name = "apellidos"),
                            @ColumnResult(name = "amplitude"),
                            @ColumnResult(name = "veces")
                        }
                )
            }),
    @SqlResultSetMapping(name = "consolidadServiciosMapping",
            classes = {
                @ConstructorResult(targetClass = ConsolidadServicios.class,
                        columns = {
                            @ColumnResult(name = "id"),
//                            @ColumnResult(name = "programado"),
//                            @ColumnResult(name = "eliminado"),
//                            @ColumnResult(name = "adicional")
                            @ColumnResult(name = "programado", type = BigDecimal.class),
                            @ColumnResult(name = "eliminado", type = BigDecimal.class),
                            @ColumnResult(name = "adicional", type = BigDecimal.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "InformeOperacionMapping",
            classes = {
                @ConstructorResult(targetClass = InformeOperacion.class,
                        columns = {
                            @ColumnResult(name = "dia_operacion"),
                            @ColumnResult(name = "bus"),
                            @ColumnResult(name = "servbus"),
                            @ColumnResult(name = "servicio"),
                            @ColumnResult(name = "operador"),
                            @ColumnResult(name = "codigo_operador")
                        }
                )
            }),
    @SqlResultSetMapping(name = "ServiciosPorSalirMapping",
            classes = {
                @ConstructorResult(targetClass = ServiciosPorSalir.class,
                        columns = {
                            @ColumnResult(name = "codigoVehiculo"),
                            @ColumnResult(name = "servbus"),
                            @ColumnResult(name = "tarea"),
                            @ColumnResult(name = "timeOrigin"),
                            @ColumnResult(name = "placaVehiculo")
                        }
                )
            }),
    @SqlResultSetMapping(name = "NovedadesTQ04Mapping",
            classes = {
                @ConstructorResult(targetClass = NovedadesTQ04.class,
                        columns = {
                            @ColumnResult(name = "fecha_registro"),
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "servicio_afectado"),
                            @ColumnResult(name = "servbus"),
                            @ColumnResult(name = "ultima_parada"),
                            @ColumnResult(name = "tipo_novedad"),
                            @ColumnResult(name = "tipo_detalle_novedad"),
                            @ColumnResult(name = "operador"),
                            @ColumnResult(name = "codigo_operador"),
                            @ColumnResult(name = "vehiculo"),
                            @ColumnResult(name = "vehiculo_remplazo"),
                            @ColumnResult(name = "descripcion"),
                            @ColumnResult(name = "usuario"),
                            @ColumnResult(name = "estado_op")
                        }
                )
            }),
    @SqlResultSetMapping(name = "sumEntradaSalidaDTO",
            classes = {
                @ConstructorResult(targetClass = SumEntradaSalidaDTO.class,
                        columns = {
                            @ColumnResult(name = "sumPrg", type = Integer.class),
                            @ColumnResult(name = "sumEje", type = Integer.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "sumEliminadoResponsableDTO",
            classes = {
                @ConstructorResult(targetClass = SumEliminadoResponsableDTO.class,
                        columns = {
                            @ColumnResult(name = "responsable", type = String.class),
                            @ColumnResult(name = "total", type = Integer.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "ServbusPlanificadoDTOMapping",
            classes = {
                @ConstructorResult(targetClass = ServbusPlanificadoDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "id_uf", type = int.class),
                            @ColumnResult(name = "nombre_uf", type = String.class),
                            @ColumnResult(name = "nombre_tipo_vehiculo", type = String.class),
                            @ColumnResult(name = "id_vehiculo_tipo", type = Integer.class),
                            @ColumnResult(name = "total_programado", type = Long.class),
                            @ColumnResult(name = "total_asignado", type = Long.class),
                            @ColumnResult(name = "tipo_dia", type = String.class),
                            @ColumnResult(name = "estacionalidad", type = Integer.class)
                        }
                )
            }),

        @SqlResultSetMapping(name = "ServbusPlanificadoDetalleDTOMapping",
            classes = {
                @ConstructorResult(targetClass = ServbusPlanificadoDetalleDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "servbus", type = String.class),
                            @ColumnResult(name = "tipologia", type = String.class),
                            @ColumnResult(name = "nombre", type = String.class),
                            @ColumnResult(name = "codigo_tm", type = String.class),
                            @ColumnResult(name = "time_origin", type = String.class),
                            @ColumnResult(name = "time_destiny", type = String.class),
                            @ColumnResult(name = "codigo_bus", type = String.class),
                            @ColumnResult(name = "uf", type = String.class),
                            @ColumnResult(name = "tipo_tarea", type = String.class),
                            @ColumnResult(name = "km", type = Long.class),
                            @ColumnResult(name = "estado_operacion", type = String.class),
                            @ColumnResult(name = "tipo_novedad", type = String.class),
                            @ColumnResult(name = "tipo_novedad_detalle", type = String.class),
                            @ColumnResult(name = "motivo", type = String.class)
                        }
                )
            }),
        
                @SqlResultSetMapping(name = "ReporteSemanaActualDTOMapping",
            classes = {
                @ConstructorResult(targetClass = ReporteSemanaActualDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "servbus", type = String.class),
                            @ColumnResult(name = "tipologia", type = String.class),
                            @ColumnResult(name = "nombre", type = String.class),
                            @ColumnResult(name = "codigo_tm", type = String.class),
                            @ColumnResult(name = "time_origin", type = String.class),
                            @ColumnResult(name = "time_destiny", type = String.class),
                            @ColumnResult(name = "codigo_bus", type = String.class),
                            @ColumnResult(name = "uf", type = String.class),
                            @ColumnResult(name = "tipo_tarea", type = String.class),
                            @ColumnResult(name = "km", type = Long.class),
                            @ColumnResult(name = "estado_operacion", type = String.class),
                            @ColumnResult(name = "tabla", type = Integer.class),
                            @ColumnResult(name = "id_empleado", type = Integer.class),
                            @ColumnResult(name = "id_prg_tc", type = Integer.class)
                        }
                )
            }),

    @SqlResultSetMapping(name = "KmPrgEjecDTOMapping",
            classes = {
                @ConstructorResult(targetClass = KmPrgEjecDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "nombre_uf", type = String.class),
                            @ColumnResult(name = "nombre_tipo_vehiculo", type = String.class),
                            @ColumnResult(name = "id_vehiculo_tipo", type = Integer.class),
                            @ColumnResult(name = "km_programado", type = Long.class),
                            @ColumnResult(name = "km_ejecutado", type = Long.class),
                            @ColumnResult(name = "tipo_dia", type = String.class),
                            @ColumnResult(name = "estacionalidad", type = Integer.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "UltimoServicioDTOMapping",
            classes = {
                @ConstructorResult(targetClass = UltimoServicioDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "nombre_uf", type = String.class),
                            @ColumnResult(name = "servbus", type = String.class),
                            @ColumnResult(name = "lugar_entrada", type = String.class),
                            @ColumnResult(name = "id_prg_tc", type = Integer.class),
                            @ColumnResult(name = "hora_entrada", type = String.class),
                            @ColumnResult(name = "detalle_servicio", type = String.class),
                            @ColumnResult(name = "hora_real_ingreso", type = String.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "primerServicioDTOMapping",
            classes = {
                @ConstructorResult(targetClass = PrimerServicioDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "nombre_uf", type = String.class),
                            @ColumnResult(name = "servbus", type = String.class),
                            @ColumnResult(name = "lugar_salida", type = String.class),
                            @ColumnResult(name = "id_prg_tc", type = Integer.class),
                            @ColumnResult(name = "hora_salida", type = String.class),
                            @ColumnResult(name = "detalle_servicio", type = String.class),
                            @ColumnResult(name = "hora_real_salida", type = String.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "ServbusTipoTablaDTOMapping",
            classes = {
                @ConstructorResult(targetClass = ServbusTipoTablaDTO.class,
                        columns = {
                            @ColumnResult(name = "servbus", type = String.class),
                            @ColumnResult(name = "num_entry_depot", type = Short.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "servbusIdVehiculoTipoDTO",
            classes = {
                @ConstructorResult(targetClass = ServbusIdTipoVehiculo.class,
                        columns = {
                            @ColumnResult(name = "servbus", type = String.class),
                            @ColumnResult(name = "id_vehiculo_tipo", type = Integer.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "InsumoProgramacionDTOMapping",
            classes = {
                @ConstructorResult(targetClass = InsumoProgramacionDTO.class,
                        columns = {
                            @ColumnResult(name = "id_empleado_tipo_cargo", type = Integer.class),
                            @ColumnResult(name = "nombre_uf", type = String.class),
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "tipologia", type = String.class),
                            @ColumnResult(name = "total_tareas_prg", type = Long.class),
                            @ColumnResult(name = "total_reservas_prg", type = Long.class),
                            @ColumnResult(name = "total_ausentismo", type = Long.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "PorcentajeDisponibilidadFlotaDTOMapping",
            classes = {
                @ConstructorResult(targetClass = PorcentajeDisponibilidadFlotaDTO.class,
                        columns = {
                            @ColumnResult(name = "id_uf", type = Integer.class),
                            @ColumnResult(name = "nombre_uf", type = String.class),
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "total_flota", type = Long.class),
                            @ColumnResult(name = "total_disponibles", type = Long.class),
                            @ColumnResult(name = "total_inoperativos", type = Long.class),
                            @ColumnResult(name = "porcentaje_dispo", type = Double.class),
                            @ColumnResult(name = "nombre_tipo_vehiculo", type = String.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "TP28UltimoServicioDTOMapping",
            classes = {
                @ConstructorResult(targetClass = TP28UltimoServicioDTO.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "nombre_uf", type = String.class),
                            @ColumnResult(name = "time_origin", type = String.class),
                            @ColumnResult(name = "tarea", type = String.class),
                            @ColumnResult(name = "tabla", type = String.class),
                            @ColumnResult(name = "codigo_tm", type = String.class),
                            @ColumnResult(name = "nombres", type = String.class),
                            @ColumnResult(name = "apellidos", type = String.class),
                            @ColumnResult(name = "telefono_movil", type = String.class),
                            @ColumnResult(name = "from_stop", type = String.class),
                            @ColumnResult(name = "codigo_vehiculo", type = String.class)}
                )
            })
})
public class PrgTc implements Serializable, Comparable<PrgTc> {

    @OneToMany(mappedBy = "idPrgTc", fetch = FetchType.LAZY)
    private List<MyAppConfirmDepotExit> myAppConfirmDepotExitList;
    @OneToMany(mappedBy = "idPrgTc", fetch = FetchType.LAZY)
    private List<MyAppConfirmDepotEntry> myAppConfirmDepotEntryList;

    @OneToMany(mappedBy = "idPrgTc", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList;

    @OneToMany(mappedBy = "idPrgTc", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;

    @OneToMany(mappedBy = "idPrgTc", fetch = FetchType.LAZY)
    private List<MyAppSerconConfirm> myAppSerconConfirmList;

    @Column(name = "old_empleado")
    private Integer oldEmpleado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_tc")
    private Integer idPrgTc;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "tipo_dia")
    private Character tipoDia;
    @Size(max = 30)
    @Column(name = "sercon")
    private String sercon;
    @Size(max = 8)
    @Column(name = "amplitude")
    private String amplitude;
    @Size(max = 8)
    @Column(name = "work_time")
    private String workTime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "production_distance")
    private BigDecimal productionDistance;
    @Column(name = "work_piece")
    private Integer workPiece;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
    @Size(max = 8)
    @Column(name = "task_duration")
    private String taskDuration;
    @Column(name = "distance")
    private BigDecimal distance;
    @Column(name = "tm_distance")
    private BigDecimal tmDistance;
    @Size(max = 10)
    @Column(name = "servbus")
    private String servbus;
    @Size(max = 10)
    @Column(name = "servicio_base")
    private String servicioBase;
    @Column(name = "tabla")
    private Integer tabla;
    @Column(name = "viajes")
    private Integer viajes;
    @Size(max = 25)
    @Column(name = "trayecto")
    private String trayecto;
    @Column(name = "old_vehiculo")
    private Integer oldVehiculo;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
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
    @Column(name = "estado_operacion")
    private Integer estadoOperacion;
    @Size(max = 15)
    @Column(name = "control")
    private String control;
    @OneToMany(mappedBy = "idPrgTc", fetch = FetchType.LAZY)
    private List<Multa> multaList;
    @OneToMany(mappedBy = "idPrgTc", fetch = FetchType.LAZY)
    private List<PrgTcDet> prgTcDetList;
    @JoinColumn(name = "id_task_type", referencedColumnName = "id_prg_tarea")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTarea idTaskType;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "from_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint fromStop;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @JoinColumn(name = "id_prg_tc_responsable", referencedColumnName = "id_prg_tc_responsable")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTcResponsable idPrgTcResponsable;
    @JoinColumn(name = "id_prg_tc_resumen", referencedColumnName = "id_prg_tc_resumen")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTcResumen idPrgTcResumen;
    @JoinColumn(name = "id_ruta", referencedColumnName = "id_prg_route")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgRoute idRuta;
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;
    @JoinColumn(name = "to_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint toStop;

    @JoinColumn(name = "id_prg_clasificacion_motivo", referencedColumnName = "id_prg_clasificacion_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgClasificacionMotivo idPrgClasificacionMotivo;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    @Transient
    private Long bateria;

    public PrgTc() {
    }

    public PrgTc(Integer idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    public PrgTc(Integer idPrgTc, String username, Date creado, int estadoReg) {
        this.idPrgTc = idPrgTc;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public PrgTc(String sercon, String servbus) {
        this.sercon = sercon;
        this.servbus = servbus;
    }

    public Integer getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(Integer idPrgTc) {
        this.idPrgTc = idPrgTc;
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

    public String getSercon() {
        return sercon;
    }

    public void setSercon(String sercon) {
        this.sercon = sercon;
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

    public BigDecimal getProductionDistance() {
        return productionDistance;
    }

    public void setProductionDistance(BigDecimal productionDistance) {
        this.productionDistance = productionDistance;
    }

    public Integer getWorkPiece() {
        return workPiece;
    }

    public void setWorkPiece(Integer workPiece) {
        this.workPiece = workPiece;
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

    public String getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(String taskDuration) {
        this.taskDuration = taskDuration;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getTmDistance() {
        return tmDistance;
    }

    public void setTmDistance(BigDecimal tmDistance) {
        this.tmDistance = tmDistance;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getServicioBase() {
        return servicioBase;
    }

    public void setServicioBase(String servicioBase) {
        this.servicioBase = servicioBase;
    }

    public Integer getTabla() {
        return tabla;
    }

    public void setTabla(Integer tabla) {
        this.tabla = tabla;
    }

    public Integer getViajes() {
        return viajes;
    }

    public void setViajes(Integer viajes) {
        this.viajes = viajes;
    }

    public String getTrayecto() {
        return trayecto;
    }

    public void setTrayecto(String trayecto) {
        this.trayecto = trayecto;
    }

    public Integer getOldVehiculo() {
        return oldVehiculo;
    }

    public void setOldVehiculo(Integer oldVehiculo) {
        this.oldVehiculo = oldVehiculo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public Integer getEstadoOperacion() {
        return estadoOperacion;
    }

    public void setEstadoOperacion(Integer estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    @XmlTransient
    public List<Multa> getMultaList() {
        return multaList;
    }

    public void setMultaList(List<Multa> multaList) {
        this.multaList = multaList;
    }

    @XmlTransient
    public List<PrgTcDet> getPrgTcDetList() {
        return prgTcDetList;
    }

    public void setPrgTcDetList(List<PrgTcDet> prgTcDetList) {
        this.prgTcDetList = prgTcDetList;
    }

    public PrgTarea getIdTaskType() {
        return idTaskType;
    }

    public void setIdTaskType(PrgTarea idTaskType) {
        this.idTaskType = idTaskType;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public PrgStopPoint getFromStop() {
        return fromStop;
    }

    public void setFromStop(PrgStopPoint fromStop) {
        this.fromStop = fromStop;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public PrgTcResponsable getIdPrgTcResponsable() {
        return idPrgTcResponsable;
    }

    public void setIdPrgTcResponsable(PrgTcResponsable idPrgTcResponsable) {
        this.idPrgTcResponsable = idPrgTcResponsable;
    }

    public PrgTcResumen getIdPrgTcResumen() {
        return idPrgTcResumen;
    }

    public void setIdPrgTcResumen(PrgTcResumen idPrgTcResumen) {
        this.idPrgTcResumen = idPrgTcResumen;
    }

    public PrgRoute getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(PrgRoute idRuta) {
        this.idRuta = idRuta;
    }

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public PrgStopPoint getToStop() {
        return toStop;
    }

    public void setToStop(PrgStopPoint toStop) {
        this.toStop = toStop;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public Long getBateria() {
        return bateria;
    }

    public void setBateria(Long bateria) {
        this.bateria = bateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgTc != null ? idPrgTc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgTc)) {
            return false;
        }
        PrgTc other = (PrgTc) object;
        if ((this.idPrgTc == null && other.idPrgTc != null) || (this.idPrgTc != null && !this.idPrgTc.equals(other.idPrgTc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PrgTc{" + "oldEmpleado=" + oldEmpleado + ", idPrgTc=" + idPrgTc + ", fecha=" + fecha + ", tipoDia=" + tipoDia + ", sercon=" + sercon + ", amplitude=" + amplitude + ", workTime=" + workTime + ", productionDistance=" + productionDistance + ", workPiece=" + workPiece + ", timeOrigin=" + timeOrigin + ", timeDestiny=" + timeDestiny + ", taskDuration=" + taskDuration + ", distance=" + distance + ", tmDistance=" + tmDistance + ", servbus=" + servbus + ", servicioBase=" + servicioBase + ", tabla=" + tabla + ", viajes=" + viajes + ", trayecto=" + trayecto + ", oldVehiculo=" + oldVehiculo + ", observaciones=" + observaciones + ", username=" + username + ", creado=" + creado + ", modificado=" + modificado + ", estadoReg=" + estadoReg + ", estadoOperacion=" + estadoOperacion + ", control=" + control + ", idTaskType=" + idTaskType + ", idEmpleado=" + idEmpleado + ", fromStop=" + fromStop + ", idVehiculo=" + idVehiculo + ", idPrgTcResponsable=" + idPrgTcResponsable + ", idPrgTcResumen=" + idPrgTcResumen + ", idRuta=" + idRuta + ", idVehiculoTipo=" + idVehiculoTipo + ", toStop=" + toStop + ", idPrgClasificacionMotivo=" + idPrgClasificacionMotivo + ", idGopUnidadFuncional=" + idGopUnidadFuncional + '}';
    }

    public Integer getOldEmpleado() {
        return oldEmpleado;
    }

    public void setOldEmpleado(Integer oldEmpleado) {
        this.oldEmpleado = oldEmpleado;
    }

    @XmlTransient
    public List<NovedadPrgTc> getNovedadPrgTcList() {
        return novedadPrgTcList;
    }

    public void setNovedadPrgTcList(List<NovedadPrgTc> novedadPrgTcList) {
        this.novedadPrgTcList = novedadPrgTcList;
    }

    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    @Override
    public int compareTo(PrgTc tc) {
        return idVehiculo.getCodigo().compareTo(tc.getIdVehiculo().getCodigo());
    }

    public PrgClasificacionMotivo getIdPrgClasificacionMotivo() {
        return idPrgClasificacionMotivo;
    }

    public void setIdPrgClasificacionMotivo(PrgClasificacionMotivo idPrgClasificacionMotivo) {
        this.idPrgClasificacionMotivo = idPrgClasificacionMotivo;
    }

    @XmlTransient
    public List<MyAppConfirmDepotEntry> getMyAppConfirmDepotEntryList() {
        return myAppConfirmDepotEntryList;
    }

    public void setMyAppConfirmDepotEntryList(List<MyAppConfirmDepotEntry> myAppConfirmDepotEntryList) {
        this.myAppConfirmDepotEntryList = myAppConfirmDepotEntryList;
    }

    @XmlTransient
    public List<MyAppConfirmDepotExit> getMyAppConfirmDepotExitList() {
        return myAppConfirmDepotExitList;
    }

    public void setMyAppConfirmDepotExitList(List<MyAppConfirmDepotExit> myAppConfirmDepotExitList) {
        this.myAppConfirmDepotExitList = myAppConfirmDepotExitList;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @XmlTransient
    public List<MyAppSerconConfirm> getMyAppSerconConfirmList() {
        return myAppSerconConfirmList;
    }

    public void setMyAppSerconConfirmList(List<MyAppSerconConfirm> myAppSerconConfirmList) {
        this.myAppSerconConfirmList = myAppSerconConfirmList;
    }

}
