/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.AccidenteCtrl;
import com.movilidad.util.beans.InformeAccidente;
import java.io.Serializable;
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
@Table(name = "novedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Novedad.findAll", query = "SELECT n FROM Novedad n"),
    @NamedQuery(name = "Novedad.findByIdNovedad", query = "SELECT n FROM Novedad n WHERE n.idNovedad = :idNovedad"),
    @NamedQuery(name = "Novedad.findByFecha", query = "SELECT n FROM Novedad n WHERE n.fecha = :fecha"),
    @NamedQuery(name = "Novedad.findByDesde", query = "SELECT n FROM Novedad n WHERE n.desde = :desde"),
    @NamedQuery(name = "Novedad.findByHasta", query = "SELECT n FROM Novedad n WHERE n.hasta = :hasta"),
    @NamedQuery(name = "Novedad.findByPuntosPm", query = "SELECT n FROM Novedad n WHERE n.puntosPm = :puntosPm"),
    @NamedQuery(name = "Novedad.findByPuntosPmConciliados", query = "SELECT n FROM Novedad n WHERE n.puntosPmConciliados = :puntosPmConciliados"),
    @NamedQuery(name = "Novedad.findByProcede", query = "SELECT n FROM Novedad n WHERE n.procede = :procede"),
    @NamedQuery(name = "Novedad.findByLiquidada", query = "SELECT n FROM Novedad n WHERE n.liquidada = :liquidada"),
    @NamedQuery(name = "Novedad.findByUsername", query = "SELECT n FROM Novedad n WHERE n.username = :username"),
    @NamedQuery(name = "Novedad.findByCreado", query = "SELECT n FROM Novedad n WHERE n.creado = :creado"),
    @NamedQuery(name = "Novedad.findByModificado", query = "SELECT n FROM Novedad n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "Novedad.findByEstadoReg", query = "SELECT n FROM Novedad n WHERE n.estadoReg = :estadoReg")})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "AccidenteCtrlMapping",
            classes = {
                @ConstructorResult(targetClass = AccidenteCtrl.class,
                        columns = {
                            @ColumnResult(name = "codigo_operador"),
                            @ColumnResult(name = "codigo_vehiculo"),
                            @ColumnResult(name = "nombre_operador"),
                            @ColumnResult(name = "incidente"),
                            @ColumnResult(name = "percance"),
                            @ColumnResult(name = "TM01"),
                            @ColumnResult(name = "TM02"),
                            @ColumnResult(name = "TM16")
                        }
                )
            }),
    @SqlResultSetMapping(name = "InformeAccidenteMapping",
            classes = {
                @ConstructorResult(targetClass = InformeAccidente.class,
                        columns = {
                            @ColumnResult(name = "incidente"),
                            @ColumnResult(name = "TM01"),
                            @ColumnResult(name = "TM02"),
                            @ColumnResult(name = "TM16")
                        }
                )
            }),})
public class Novedad implements Serializable, Comparable<Novedad> {

    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<AtvEvidencia> atvEvidenciaList;
    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<AtvLocation> atvLocationList;
    @JoinColumn(name = "id_atv_tipo_atencion", referencedColumnName = "id_atv_tipo_atencion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AtvTipoAtencion idAtvTipoAtencion;
    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<AtvLocationSugerida> atvLocationSugeridaList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedad")
    private List<AccPre> accPreList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<PdMaestroDetalle> pdMaestroDetalleList;

    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList;
    @OneToMany(mappedBy = "idNovedad")
    private List<NovedadIncapacidad> novedadIncapacidadList;
    @OneToMany(mappedBy = "idNovedad")
    private List<Accidente> accidenteList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad")
    private Integer idNovedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "puntos_pm")
    private Integer puntosPm;
    @Column(name = "puntos_pm_conciliados")
    private Integer puntosPmConciliados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "procede")
    private int procede;
    @Basic(optional = false)
    @NotNull
    @Column(name = "liquidada")
    private int liquidada;
    @Size(max = 8)
    @Column(name = "hora_inicio")
    private String horaInicio;
    @Size(max = 8)
    @Column(name = "hora_fin")
    private String horaFin;
    @Size(max = 8)
    @Column(name = "hora")
    private String hora;
    @Size(max = 150)
    @Column(name = "sitio")
    private String sitio;
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
    @Column(name = "inmovilizado")
    private int inmovilizado;
    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<NovedadSeguimiento> novedadSeguimientoList;
    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<NovedadDocumentos> novedadDocumentosList;
    @JoinColumn(name = "id_novedad_dano", referencedColumnName = "id_novedad_dano")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadDano idNovedadDano;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_multa", referencedColumnName = "id_multa")
    @ManyToOne(fetch = FetchType.LAZY)
    private Multa idMulta;
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadTipoDetalle;
    @JoinColumn(name = "id_novedad_tipo", referencedColumnName = "id_novedad_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadTipo idNovedadTipo;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @JoinColumn(name = "old_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo oldVehiculo;
    @JoinColumn(name = "old_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado oldEmpleado;
    @Size(max = 15)
    @Column(name = "control")
    private String control;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    @Column(name = "estado_novedad")
    private int estadoNovedad;

    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Column(name = "usuario_cierre")
    private String usuarioCierre;

    @JoinColumn(name = "id_disp_clasificacion_novedad",
            referencedColumnName = "id_disp_clasificacion_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispClasificacionNovedad idDispClasificacionNovedad;

    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<DispActividad> dispActividadList;
    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<NovedadDocs> novedadDocsList;

    @Column(name = "fecha_recibido_atv")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecibidoAtv;
    @Column(name = "user_recibido_atv")
    private String userRecibidoAtv;
    @Column(name = "cierre_app_atv")
    private Integer cierreAppAtv;
    @Column(name = "costo_liquidado_atv")
    private Integer costoLiquidadoAtv;
    @Column(name = "liquidado_atv")
    private Integer liquidadoAtv;
    @Column(name = "aceptar_pago_atv")
    private Integer aceptarPagoAtv;
    @Lob
    @Column(name = "aceptar_pago_obs_atv")
    private String aceptarPagoObsAtv;

    @Basic(optional = false)
    @NotNull
    @Column(name = "soporte_ausentismo")
    private int soporteAusentismo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "laboro")
    private int laboro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "laboro_parcial")
    private int laboroParcial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "no_laboro")
    private int noLaboro;
    @Column(name = "numero_semana")
    private Integer numeroSemana;

    @JoinColumn(name = "id_atv_vehiculo_atencion", referencedColumnName = "id_atv_vehiculos_atencion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AtvVehiculosAtencion idAtvVehiculosAtencion;

    @OneToMany(mappedBy = "idNovedad", fetch = FetchType.LAZY)
    private List<NovedadAutorizacionAusentismos> novedadAutorizacionAusentismosList;

    @Column(name = "id_pqr")
    private int idPqr;

    @JoinColumn(name = "id_novedad_tipo_infraccion", referencedColumnName = "id_novedad_tipo_infraccion")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoInfraccion idNovedadTipoInfraccion;
    

    @Transient
    private PrgTc prgTc;

    public Novedad() {
        this.cierreAppAtv = 1;
        this.liquidadoAtv = 0;
        this.aceptarPagoAtv = 0;
    }

    public Novedad(Integer idNovedad) {
        this.idNovedad = idNovedad;
    }

    public Novedad(Integer idNovedad, Date fecha, String observaciones, int procede, int liquidada, String username, Date creado, int estadoReg) {
        this.idNovedad = idNovedad;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.procede = procede;
        this.liquidada = liquidada;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Integer idNovedad) {
        this.idNovedad = idNovedad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getPuntosPm() {
        return puntosPm;
    }

    public void setPuntosPm(Integer puntosPm) {
        this.puntosPm = puntosPm;
    }

    public Integer getPuntosPmConciliados() {
        return puntosPmConciliados;
    }

    public void setPuntosPmConciliados(Integer puntosPmConciliados) {
        this.puntosPmConciliados = puntosPmConciliados;
    }

    public int getProcede() {
        return procede;
    }

    public void setProcede(int procede) {
        this.procede = procede;
    }

    public int getLiquidada() {
        return liquidada;
    }

    public void setLiquidada(int liquidada) {
        this.liquidada = liquidada;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getSoporteAusentismo() {
        return soporteAusentismo;
    }

    public void setSoporteAusentismo(int soporteAusentismo) {
        this.soporteAusentismo = soporteAusentismo;
    }

    public int getLaboro() {
        return laboro;
    }

    public void setLaboro(int laboro) {
        this.laboro = laboro;
    }

    public int getLaboroParcial() {
        return laboroParcial;
    }

    public void setLaboroParcial(int laboroParcial) {
        this.laboroParcial = laboroParcial;
    }

    public int getNoLaboro() {
        return noLaboro;
    }

    public void setNoLaboro(int noLaboro) {
        this.noLaboro = noLaboro;
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

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    @XmlTransient
    public List<NovedadSeguimiento> getNovedadSeguimientoList() {
        return novedadSeguimientoList;
    }

    public void setNovedadSeguimientoList(List<NovedadSeguimiento> novedadSeguimientoList) {
        this.novedadSeguimientoList = novedadSeguimientoList;
    }

    @XmlTransient
    public List<NovedadDocumentos> getNovedadDocumentosList() {
        return novedadDocumentosList;
    }

    public void setNovedadDocumentosList(List<NovedadDocumentos> novedadDocumentosList) {
        this.novedadDocumentosList = novedadDocumentosList;
    }

    public NovedadDano getIdNovedadDano() {
        return idNovedadDano;
    }

    public void setIdNovedadDano(NovedadDano idNovedadDano) {
        this.idNovedadDano = idNovedadDano;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Multa getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Multa idMulta) {
        this.idMulta = idMulta;
    }

    public NovedadTipoDetalles getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(NovedadTipoDetalles idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    public NovedadTipo getIdNovedadTipo() {
        return idNovedadTipo;
    }

    public void setIdNovedadTipo(NovedadTipo idNovedadTipo) {
        this.idNovedadTipo = idNovedadTipo;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Vehiculo getOldVehiculo() {
        return oldVehiculo;
    }

    public void setOldVehiculo(Vehiculo oldVehiculo) {
        this.oldVehiculo = oldVehiculo;
    }

    public Empleado getOldEmpleado() {
        return oldEmpleado;
    }

    public void setOldEmpleado(Empleado oldEmpleado) {
        this.oldEmpleado = oldEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedad != null ? idNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Novedad)) {
            return false;
        }
        Novedad other = (Novedad) object;
        if ((this.idNovedad == null && other.idNovedad != null) || (this.idNovedad != null && !this.idNovedad.equals(other.idNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Novedad[ idNovedad=" + idNovedad + " ]";
    }

    @XmlTransient
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    @XmlTransient
    public List<NovedadIncapacidad> getNovedadIncapacidadList() {
        return novedadIncapacidadList;
    }

    public void setNovedadIncapacidadList(List<NovedadIncapacidad> novedadIncapacidadList) {
        this.novedadIncapacidadList = novedadIncapacidadList;
    }

    @XmlTransient
    public List<NovedadPrgTc> getNovedadPrgTcList() {
        return novedadPrgTcList;
    }

    public void setNovedadPrgTcList(List<NovedadPrgTc> novedadPrgTcList) {
        this.novedadPrgTcList = novedadPrgTcList;
    }

    @Override
    public int compareTo(Novedad e) {
        return e.getFecha().compareTo(fecha);
    }

    @XmlTransient
    public List<AccPre> getAccPreList() {
        return accPreList;
    }

    public void setAccPreList(List<AccPre> accPreList) {
        this.accPreList = accPreList;
    }

    @XmlTransient
    public List<PdMaestroDetalle> getPdMaestroDetalleList() {
        return pdMaestroDetalleList;
    }

    public void setPdMaestroDetalleList(List<PdMaestroDetalle> pdMaestroDetalleList) {
        this.pdMaestroDetalleList = pdMaestroDetalleList;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public DispClasificacionNovedad getIdDispClasificacionNovedad() {
        return idDispClasificacionNovedad;
    }

    public void setIdDispClasificacionNovedad(DispClasificacionNovedad idDispClasificacionNovedad) {
        this.idDispClasificacionNovedad = idDispClasificacionNovedad;
    }

    @XmlTransient
    public List<DispActividad> getDispActividadList() {
        return dispActividadList;
    }

    public void setDispActividadList(List<DispActividad> dispActividadList) {
        this.dispActividadList = dispActividadList;
    }

    public int getEstadoNovedad() {
        return estadoNovedad;
    }

    public void setEstadoNovedad(int estadoNovedad) {
        this.estadoNovedad = estadoNovedad;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getUsuarioCierre() {
        return usuarioCierre;
    }

    public void setUsuarioCierre(String usuarioCierre) {
        this.usuarioCierre = usuarioCierre;
    }

    public int getInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(int inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public List<NovedadDocs> getNovedadDocsList() {
        return novedadDocsList;
    }

    public void setNovedadDocsList(List<NovedadDocs> novedadDocsList) {
        this.novedadDocsList = novedadDocsList;
    }

    @XmlTransient
    public List<AtvEvidencia> getAtvEvidenciaList() {
        return atvEvidenciaList;
    }

    public void setAtvEvidenciaList(List<AtvEvidencia> atvEvidenciaList) {
        this.atvEvidenciaList = atvEvidenciaList;
    }

    @XmlTransient
    public List<AtvLocation> getAtvLocationList() {
        return atvLocationList;
    }

    public void setAtvLocationList(List<AtvLocation> atvLocationList) {
        this.atvLocationList = atvLocationList;
    }

    public AtvTipoAtencion getIdAtvTipoAtencion() {
        return idAtvTipoAtencion;
    }

    public void setIdAtvTipoAtencion(AtvTipoAtencion idAtvTipoAtencion) {
        this.idAtvTipoAtencion = idAtvTipoAtencion;
    }

    @XmlTransient
    public List<AtvLocationSugerida> getAtvLocationSugeridaList() {
        return atvLocationSugeridaList;
    }

    public void setAtvLocationSugeridaList(List<AtvLocationSugerida> atvLocationSugeridaList) {
        this.atvLocationSugeridaList = atvLocationSugeridaList;
    }

    public Date getFechaRecibidoAtv() {
        return fechaRecibidoAtv;
    }

    public void setFechaRecibidoAtv(Date fechaRecibidoAtv) {
        this.fechaRecibidoAtv = fechaRecibidoAtv;
    }

    public String getUserRecibidoAtv() {
        return userRecibidoAtv;
    }

    public void setUserRecibidoAtv(String userRecibidoAtv) {
        this.userRecibidoAtv = userRecibidoAtv;
    }

    public Integer getCierreAppAtv() {
        return cierreAppAtv;
    }

    public void setCierreAppAtv(Integer cierreAppAtv) {
        this.cierreAppAtv = cierreAppAtv;
    }

    public Integer getCostoLiquidadoAtv() {
        return costoLiquidadoAtv;
    }

    public void setCostoLiquidadoAtv(Integer costoLiquidadoAtv) {
        this.costoLiquidadoAtv = costoLiquidadoAtv;
    }

    public Integer getLiquidadoAtv() {
        return liquidadoAtv;
    }

    public void setLiquidadoAtv(Integer liquidadoAtv) {
        this.liquidadoAtv = liquidadoAtv;
    }

    public Integer getAceptarPagoAtv() {
        return aceptarPagoAtv;
    }

    public void setAceptarPagoAtv(Integer aceptarPagoAtv) {
        this.aceptarPagoAtv = aceptarPagoAtv;
    }

    public String getAceptarPagoObsAtv() {
        return aceptarPagoObsAtv;
    }

    public void setAceptarPagoObsAtv(String aceptarPagoObsAtv) {
        this.aceptarPagoObsAtv = aceptarPagoObsAtv;
    }

    public AtvVehiculosAtencion getIdAtvVehiculosAtencion() {
        return idAtvVehiculosAtencion;
    }

    public void setIdAtvVehiculosAtencion(AtvVehiculosAtencion idAtvVehiculosAtencion) {
        this.idAtvVehiculosAtencion = idAtvVehiculosAtencion;
    }

    @XmlTransient
    public List<NovedadAutorizacionAusentismos> getNovedadAutorizacionAusentismosList() {
        return novedadAutorizacionAusentismosList;
    }

    public void setNovedadAutorizacionAusentismosList(List<NovedadAutorizacionAusentismos> novedadAutorizacionAusentismosList) {
        this.novedadAutorizacionAusentismosList = novedadAutorizacionAusentismosList;
    }

    public Integer getNumeroSemana() {
        return numeroSemana;
    }

    public void setNumeroSemana(Integer numeroSemana) {
        this.numeroSemana = numeroSemana;
    }

    public int getIdPqr() {
        return idPqr;
    }

    public void setIdPqr(int idPqr) {
        this.idPqr = idPqr;
    }

    public NovedadTipoInfraccion getIdNovedadTipoInfraccion() {
        return idNovedadTipoInfraccion;
    }

    public void setIdNovedadTipoInfraccion(NovedadTipoInfraccion idNovedadTipoInfraccion) {
        this.idNovedadTipoInfraccion = idNovedadTipoInfraccion;
    }

}
