/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.BitacoraAccidentalidad;
import com.movilidad.util.beans.ReporteLucroCesante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "accidente")
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "ReporteLucroCesanteMapping",
            classes = {
                @ConstructorResult(targetClass = ReporteLucroCesante.class,
                        columns = {
                            @ColumnResult(name = "id_empleado"),
                            @ColumnResult(name = "codigo"),
                            @ColumnResult(name = "fecha_acc"),
                            @ColumnResult(name = "codigo_tm"),
                            @ColumnResult(name = "nombre"),
                            @ColumnResult(name = "fecha_salida_inmovilizado", type = Date.class),
                            @ColumnResult(name = "dias_patio", type = Long.class),
                            @ColumnResult(name = "costos_directos", type = BigDecimal.class),
                            @ColumnResult(name = "costos_indirectos", type = BigDecimal.class)

                        }
                )
            }),
    @SqlResultSetMapping(name = "BitacoraAccMapping",
            classes = {
                @ConstructorResult(targetClass = BitacoraAccidentalidad.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "hora"),
                            @ColumnResult(name = "hora_asistida"),
                            @ColumnResult(name = "hora_cierre_asistida"),
                            @ColumnResult(name = "hora_cierre_caso"),
                            @ColumnResult(name = "ruta"),
                            @ColumnResult(name = "direccion"),
                            @ColumnResult(name = "placa"),
                            @ColumnResult(name = "codigo"),
                            @ColumnResult(name = "codigo_operador"),
                            @ColumnResult(name = "caso_tm"),
                            @ColumnResult(name = "juridica"),
                            @ColumnResult(name = "nombre_operador"),
                            @ColumnResult(name = "identificacion"),
                            @ColumnResult(name = "tipo_evento"),
                            @ColumnResult(name = "clasificacion"),
                            @ColumnResult(name = "causalidad", type = String.class),
                            @ColumnResult(name = "hipotesis", type = String.class),
                            @ColumnResult(name = "estado_conciliacion"),
                            @ColumnResult(name = "valor_conciliado", type = Integer.class),
                            @ColumnResult(name = "dia_evento"),
                            @ColumnResult(name = "franja_horaria"),
                            @ColumnResult(name = "tipo_vehiculo_tercero"),
                            @ColumnResult(name = "empresa_operadora"),
                            @ColumnResult(name = "inmovilizado", type = Integer.class),
                            @ColumnResult(name = "ipat", type = Integer.class),
                            @ColumnResult(name = "observacion", type = String.class),
                            @ColumnResult(name = "victimas", type = Long.class),
                            @ColumnResult(name = "costos_directos", type = BigDecimal.class),
                            @ColumnResult(name = "costos_indirectos", type = BigDecimal.class),
                            @ColumnResult(name = "puntos_afectacion", type = Long.class)
                        }
                )
            }),})

@XmlRootElement
public class Accidente implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccidente",
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AccidentePlanAccion> accidentePlanAccionList;

    @OneToMany(mappedBy = "idAccidente", fetch = FetchType.LAZY)
    private List<AccidenteAnalisis> accidenteAnalisisList;
    @OneToMany(mappedBy = "idAccidente", fetch = FetchType.LAZY)
    private List<AccidenteCalificacion> accidenteCalificacionList;

    @OneToMany(mappedBy = "idAccidente", fetch = FetchType.LAZY)
    private List<AccidenteBorrador> accidenteBorradorList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente")
    private Integer idAccidente;
    @Column(name = "fecha_acc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAcc;
    @Column(name = "fecha_ingreso_empleado")
    @Temporal(TemporalType.DATE)
    private Date fechaIngresoEmpleado;
    @Column(name = "inmovilizado")
    private Integer inmovilizado;
    @Column(name = "caso_tm")
    private Integer casoTm;
    @Column(name = "juridica")
    private Integer juridica;
    @Size(max = 30)
    @Column(name = "nro_ipat")
    private String nroIpat;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_asistencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsistencia;
    @Size(max = 8)
    @Column(name = "hora_final_turno_previo")
    private String horaFinalTurnoPrevio;
    @Column(name = "horas_descanso")
    private Integer horasDescanso;
    @Column(name = "meses_operando")
    private Integer mesesOperando;
    @Column(name = "km_recorrido_operador")
    private BigDecimal kmRecorridoOperador;
    @Size(max = 8)
    @Column(name = "hora_inicio_turno_actual")
    private String horaInicioTurnoActual;
    @Column(name = "turno_sercon")
    private Integer turnoSercon;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Size(max = 15)
    @Column(name = "user_cierre")
    private String userCierre;
    @Column(name = "conciliado")
    private Integer conciliado;
    @Column(name = "valor_conciliado")
    private Integer valorConciliado;
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

    @Column(name = "operador_is_responsable")
    private Integer operadorIsResponsable;

    @OneToMany(mappedBy = "idAccidente", fetch = FetchType.LAZY)
    private List<AccInformeOpe> accInformeOpeList;
    @OneToMany(mappedBy = "idAccidente", fetch = FetchType.LAZY)
    private List<AccInformeMaster> accInformeMasterList;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Novedad idNovedad;
    @JoinColumn(name = "id_acc_act_realizada", referencedColumnName = "id_acc_act_realizada")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccActRealizada idAccActRealizada;
    @JoinColumn(name = "id_clase", referencedColumnName = "id_acc_clase")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccClase idClase;
    @JoinColumn(name = "id_acc_det_clase", referencedColumnName = "id_acc_det_clase")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccDetClase idAccDetClase;
    @JoinColumn(name = "id_acc_tipo_serv", referencedColumnName = "id_acc_tipo_serv")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoServ idAccTipoServ;
    @JoinColumn(name = "id_acc_tipo_turno", referencedColumnName = "id_acc_tipo_turno")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoTurno idAccTipoTurno;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_master", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idMaster;
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadTipoDetalle;
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTc idPrgTc;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_acc_atencion_via", referencedColumnName = "id_acc_atencion_via")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccAtencionVia idAccAtencionVia;
    @JoinColumn(name = "id_acc_abogado", referencedColumnName = "id_acc_abogado")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccAbogado idAccAbogado;
    @JoinColumn(name = "id_prg_sercon_actual", referencedColumnName = "id_prg_sercon")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgSercon idPrgSerconActual;
    @JoinColumn(name = "id_acc_desplaza_a", referencedColumnName = "id_acc_desplaza_a")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccDesplazaA idAccDesplazaA;
    @JoinColumn(name = "id_prg_sercon_pass", referencedColumnName = "id_prg_sercon")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgSercon idPrgSerconPass;
    @Column(name = "fecha_cierre_recomoto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierreRecomoto;

    public Accidente() {
    }

    public Accidente(Integer idAccidente) {
        this.idAccidente = idAccidente;
    }

    public Integer getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Integer idAccidente) {
        this.idAccidente = idAccidente;
    }

    public Date getFechaAcc() {
        return fechaAcc;
    }

    public void setFechaAcc(Date fechaAcc) {
        this.fechaAcc = fechaAcc;
    }

    public Date getFechaIngresoEmpleado() {
        return fechaIngresoEmpleado;
    }

    public void setFechaIngresoEmpleado(Date fechaIngresoEmpleado) {
        this.fechaIngresoEmpleado = fechaIngresoEmpleado;
    }

    public Integer getInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(Integer inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public Integer getCasoTm() {
        return casoTm;
    }

    public void setCasoTm(Integer casoTm) {
        this.casoTm = casoTm;
    }

    public Integer getJuridica() {
        return juridica;
    }

    public void setJuridica(Integer juridica) {
        this.juridica = juridica;
    }

    public String getNroIpat() {
        return nroIpat;
    }

    public void setNroIpat(String nroIpat) {
        this.nroIpat = nroIpat;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public String getHoraFinalTurnoPrevio() {
        return horaFinalTurnoPrevio;
    }

    public void setHoraFinalTurnoPrevio(String horaFinalTurnoPrevio) {
        this.horaFinalTurnoPrevio = horaFinalTurnoPrevio;
    }

    public Integer getHorasDescanso() {
        return horasDescanso;
    }

    public void setHorasDescanso(Integer horasDescanso) {
        this.horasDescanso = horasDescanso;
    }

    public Integer getMesesOperando() {
        return mesesOperando;
    }

    public void setMesesOperando(Integer mesesOperando) {
        this.mesesOperando = mesesOperando;
    }

    public String getHoraInicioTurnoActual() {
        return horaInicioTurnoActual;
    }

    public void setHoraInicioTurnoActual(String horaInicioTurnoActual) {
        this.horaInicioTurnoActual = horaInicioTurnoActual;
    }

    public Integer getTurnoSercon() {
        return turnoSercon;
    }

    public void setTurnoSercon(Integer turnoSercon) {
        this.turnoSercon = turnoSercon;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getUserCierre() {
        return userCierre;
    }

    public void setUserCierre(String userCierre) {
        this.userCierre = userCierre;
    }

    public Integer getConciliado() {
        return conciliado;
    }

    public void setConciliado(Integer conciliado) {
        this.conciliado = conciliado;
    }

    public Integer getValorConciliado() {
        return valorConciliado;
    }

    public void setValorConciliado(Integer valorConciliado) {
        this.valorConciliado = valorConciliado;
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

    public BigDecimal getKmRecorridoOperador() {
        return kmRecorridoOperador;
    }

    public void setKmRecorridoOperador(BigDecimal kmRecorridoOperador) {
        this.kmRecorridoOperador = kmRecorridoOperador;
    }

    public AccDesplazaA getIdAccDesplazaA() {
        return idAccDesplazaA;
    }

    public void setIdAccDesplazaA(AccDesplazaA idAccDesplazaA) {
        this.idAccDesplazaA = idAccDesplazaA;
    }

    @XmlTransient
    public List<AccInformeOpe> getAccInformeOpeList() {
        return accInformeOpeList;
    }

    public void setAccInformeOpeList(List<AccInformeOpe> accInformeOpeList) {
        this.accInformeOpeList = accInformeOpeList;
    }

    @XmlTransient
    public List<AccInformeMaster> getAccInformeMasterList() {
        return accInformeMasterList;
    }

    public void setAccInformeMasterList(List<AccInformeMaster> accInformeMasterList) {
        this.accInformeMasterList = accInformeMasterList;
    }

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public AccActRealizada getIdAccActRealizada() {
        return idAccActRealizada;
    }

    public void setIdAccActRealizada(AccActRealizada idAccActRealizada) {
        this.idAccActRealizada = idAccActRealizada;
    }

    public AccClase getIdClase() {
        return idClase;
    }

    public void setIdClase(AccClase idClase) {
        this.idClase = idClase;
    }

    public AccDetClase getIdAccDetClase() {
        return idAccDetClase;
    }

    public void setIdAccDetClase(AccDetClase idAccDetClase) {
        this.idAccDetClase = idAccDetClase;
    }

    public AccTipoServ getIdAccTipoServ() {
        return idAccTipoServ;
    }

    public void setIdAccTipoServ(AccTipoServ idAccTipoServ) {
        this.idAccTipoServ = idAccTipoServ;
    }

    public AccTipoTurno getIdAccTipoTurno() {
        return idAccTipoTurno;
    }

    public void setIdAccTipoTurno(AccTipoTurno idAccTipoTurno) {
        this.idAccTipoTurno = idAccTipoTurno;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleado getIdMaster() {
        return idMaster;
    }

    public void setIdMaster(Empleado idMaster) {
        this.idMaster = idMaster;
    }

    public NovedadTipoDetalles getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(NovedadTipoDetalles idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    public PrgTc getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(PrgTc idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
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
        hash += (idAccidente != null ? idAccidente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accidente)) {
            return false;
        }
        Accidente other = (Accidente) object;
        if ((this.idAccidente == null && other.idAccidente != null) || (this.idAccidente != null && !this.idAccidente.equals(other.idAccidente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Accidente[ idAccidente=" + idAccidente + " ]";
    }

    @XmlTransient
    public List<AccidenteBorrador> getAccidenteBorradorList() {
        return accidenteBorradorList;
    }

    public void setAccidenteBorradorList(List<AccidenteBorrador> accidenteBorradorList) {
        this.accidenteBorradorList = accidenteBorradorList;
    }

    @XmlTransient
    public List<AccidenteAnalisis> getAccidenteAnalisisList() {
        return accidenteAnalisisList;
    }

    public void setAccidenteAnalisisList(List<AccidenteAnalisis> accidenteAnalisisList) {
        this.accidenteAnalisisList = accidenteAnalisisList;
    }

    @XmlTransient
    public List<AccidenteCalificacion> getAccidenteCalificacionList() {
        return accidenteCalificacionList;
    }

    public void setAccidenteCalificacionList(List<AccidenteCalificacion> accidenteCalificacionList) {
        this.accidenteCalificacionList = accidenteCalificacionList;
    }

    @XmlTransient
    public List<AccidentePlanAccion> getAccidentePlanAccionList() {
        return accidentePlanAccionList;
    }

    public void setAccidentePlanAccionList(List<AccidentePlanAccion> accidentePlanAccionList) {
        this.accidentePlanAccionList = accidentePlanAccionList;
    }

    public AccAtencionVia getIdAccAtencionVia() {
        return idAccAtencionVia;
    }

    public void setIdAccAtencionVia(AccAtencionVia idAccAtencionVia) {
        this.idAccAtencionVia = idAccAtencionVia;
    }

    public AccAbogado getIdAccAbogado() {
        return idAccAbogado;
    }

    public void setIdAccAbogado(AccAbogado idAccAbogado) {
        this.idAccAbogado = idAccAbogado;
    }

    public PrgSercon getIdPrgSerconActual() {
        return idPrgSerconActual;
    }

    public void setIdPrgSerconActual(PrgSercon idPrgSerconActual) {
        this.idPrgSerconActual = idPrgSerconActual;
    }

    public PrgSercon getIdPrgSerconPass() {
        return idPrgSerconPass;
    }

    public void setIdPrgSerconPass(PrgSercon idPrgSerconPass) {
        this.idPrgSerconPass = idPrgSerconPass;
    }

    public Date getFechaCierreRecomoto() {
        return fechaCierreRecomoto;
    }

    public void setFechaCierreRecomoto(Date fechaCierreRecomoto) {
        this.fechaCierreRecomoto = fechaCierreRecomoto;
    }

    public Integer getOperadorIsResponsable() {
        return operadorIsResponsable;
    }

    public void setOperadorIsResponsable(Integer operadorIsResponsable) {
        this.operadorIsResponsable = operadorIsResponsable;
    }

}
