/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.GestionNovedad;
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
import javax.persistence.JoinColumns;
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
@Table(name = "novedad_tipo_detalles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadTipoDetalles.findAll", query = "SELECT n FROM NovedadTipoDetalles n"),
    @NamedQuery(name = "NovedadTipoDetalles.findByIdNovedadTipoDetalle", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.idNovedadTipoDetalle = :idNovedadTipoDetalle"),
    @NamedQuery(name = "NovedadTipoDetalles.findByTituloTipoNovedad", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.tituloTipoNovedad = :tituloTipoNovedad"),
    @NamedQuery(name = "NovedadTipoDetalles.findByDescripcionTipoNovedad", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.descripcionTipoNovedad = :descripcionTipoNovedad"),
    @NamedQuery(name = "NovedadTipoDetalles.findByFechas", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.fechas = :fechas"),
    @NamedQuery(name = "NovedadTipoDetalles.findByPrevencionVial", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.prevencionVial = :prevencionVial"),
    @NamedQuery(name = "NovedadTipoDetalles.findByAfectaPm", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.afectaPm = :afectaPm"),
    @NamedQuery(name = "NovedadTipoDetalles.findByReqHoras", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.reqHoras = :reqHoras"),
    @NamedQuery(name = "NovedadTipoDetalles.findByAfectaGestor", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.afectaGestor = :afectaGestor"),
    @NamedQuery(name = "NovedadTipoDetalles.findByIncluirReporte", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.incluirReporte = :incluirReporte"),
    @NamedQuery(name = "NovedadTipoDetalles.findByReqSitio", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.reqSitio = :reqSitio"),
    @NamedQuery(name = "NovedadTipoDetalles.findByAfectaDisponibilidad", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.afectaDisponibilidad = :afectaDisponibilidad"),
    @NamedQuery(name = "NovedadTipoDetalles.findByReqHora", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.reqHora = :reqHora"),
    @NamedQuery(name = "NovedadTipoDetalles.findByReqVehiculo", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.reqVehiculo = :reqVehiculo"),
    @NamedQuery(name = "NovedadTipoDetalles.findByPuntosPm", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.puntosPm = :puntosPm"),
    @NamedQuery(name = "NovedadTipoDetalles.findByAfectaProgramacion", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.afectaProgramacion = :afectaProgramacion"),
    @NamedQuery(name = "NovedadTipoDetalles.findByRequiereSoporte", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.requiereSoporte = :requiereSoporte"),
    @NamedQuery(name = "NovedadTipoDetalles.findByNotificacion", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.notificacion = :notificacion"),
    @NamedQuery(name = "NovedadTipoDetalles.findByUsername", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadTipoDetalles.findByCreado", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadTipoDetalles.findByModificado", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadTipoDetalles.findByEstadoReg", query = "SELECT n FROM NovedadTipoDetalles n WHERE n.estadoReg = :estadoReg")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "GestionNovedadMapping",
            classes = {
                @ConstructorResult(targetClass = GestionNovedad.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "id_novedad_tipo_detalle"),
                            @ColumnResult(name = "id_gop_unidad_funcional"),
                            @ColumnResult(name = "id_empleado_tipo_cargo"),
                            @ColumnResult(name = "suma_gestor"),
                            @ColumnResult(name = "valor")
                        }
                )
            })
})
public class NovedadTipoDetalles implements Serializable {

    @OneToMany(mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<GestorNovDetSemana> gestorNovDetSemanaList;

    @OneToMany(mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<GestorNovDet> gestorNovDetList;
    @OneToMany(mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<GestorTablaTmp> gestorTablaTmpList;
    @OneToMany(mappedBy = "idNovedadTipoDetalle")
    private List<ChkComponenteFalla> chkComponenteFallaList;

    @OneToMany(mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<PrgDesasignarParam> prgDesasignarParamList;
    @OneToMany(mappedBy = "idNovedadTipoDetalles", fetch = FetchType.LAZY)
    private List<AuditoriaEncabezado> AuditoriaEncabezadoList;
    @OneToMany(mappedBy = "idNovedadCambio", fetch = FetchType.LAZY)
    private List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList;
    @OneToMany(mappedBy = "idNovedadLicencia", fetch = FetchType.LAZY)
    private List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList1;
    @OneToMany(mappedBy = "idNovedadNoFirma", fetch = FetchType.LAZY)
    private List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList2;
    @OneToMany(mappedBy = "idNovedadPermiso", fetch = FetchType.LAZY)
    private List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<PmNovedadExcluir> pmNovedadExcluirList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<PmNovedadIncluir> pmNovedadIncluirList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadTipoDetalle")
    private List<AccChecklist> accChecklistList;

    @OneToMany(mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_tipo_detalle")
    private Integer idNovedadTipoDetalle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "titulo_tipo_novedad")
    private String tituloTipoNovedad;
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion_tipo_novedad")
    private String descripcionTipoNovedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechas")
    private int fechas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prevencion_vial")
    private int prevencionVial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "afecta_pm")
    private int afectaPm;
    @Column(name = "puntos_pm")
    private Integer puntosPm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "afecta_programacion")
    private int afectaProgramacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requiere_soporte")
    private int requiereSoporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "notificacion")
    private int notificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "notifica_operador")
    private int notificaOperador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_horas")
    private int reqHoras;
    @Basic(optional = false)
    @NotNull
    @Column(name = "afecta_gestor")
    private int afectaGestor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "incluir_reporte")
    private int incluirReporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "afecta_disponibilidad")
    private int afectaDisponibilidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_sitio")
    private int reqSitio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_hora")
    private int reqHora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_vehiculo")
    private int reqVehiculo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "suma_gestor")
    private int sumaGestor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "novedad_nomina")
    private int novedadNomina;
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
    @OneToMany(mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<Novedad> novedadList;
    @JoinColumn(name = "id_notificacion_procesos", referencedColumnName = "id_notificacion_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotificacionProcesos idNotificacionProcesos;
    @JoinColumn(name = "id_snc_detalle", referencedColumnName = "id_snc_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private SncDetalle idSncDetalle;
    @JoinColumn(name = "id_novedad_tipo", referencedColumnName = "id_novedad_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipo idNovedadTipo;
    @JoinColumn(name = "id_novedad_clasificacion", referencedColumnName = "id_novedad_clasificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadClasificacion idNovedadClasificacion;
    @JoinColumn(name = "id_vehiculo_tipo_estado_det", referencedColumnName = "id_vehiculo_tipo_estado_det")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipoEstadoDet idVehiculoTipoEstadoDet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mtto")
    private int mtto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "atv")
    private int atv;
    @Column(name = "nombre_acceso_rapido")
    private String nombreAccesoRapido;
    @Column(name = "descripcion_gestion_servicio")
    private String descripcionGestionServicio;
    @JoinColumn(name = "id_prg_tc_responsable", referencedColumnName = "id_prg_tc_responsable")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTcResponsable idPrgTcResponsable;
    @JoinColumn(name = "id_prg_clasificacion_motivo", referencedColumnName = "id_prg_clasificacion_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgClasificacionMotivo idPrgClasificacionMotivo;
    @JoinColumn(name = "from_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint fromStop;
    @JoinColumn(name = "to_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint toStop;

    @OneToMany(mappedBy = "idNovedadTipoDetalle", fetch = FetchType.LAZY)
    private List<NominaServerParamDet> nominaServerParamDetList;

    public NovedadTipoDetalles() {
    }

    public NovedadTipoDetalles(Integer idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    public NovedadTipoDetalles(Integer idNovedadTipoDetalle,
            String tituloTipoNovedad, int fechas, int prevencionVial,
            int afectaPm, int afectaProgramacion, int requiereSoporte,
            int notificacion, int reqHoras, int afectaGestor, int incluirReporte,
            int reqSitio, int reqHora, int reqVehiculo, int afectaDisponibilidad,
            String username, Date creado, int estadoReg) {

        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
        this.tituloTipoNovedad = tituloTipoNovedad;
        this.fechas = fechas;
        this.prevencionVial = prevencionVial;
        this.afectaPm = afectaPm;
        this.afectaProgramacion = afectaProgramacion;
        this.requiereSoporte = requiereSoporte;
        this.notificacion = notificacion;
        this.reqHoras = reqHoras;
        this.afectaGestor = afectaGestor;
        this.incluirReporte = incluirReporte;
        this.reqSitio = reqSitio;
        this.reqHora = reqHora;
        this.reqVehiculo = reqVehiculo;
        this.afectaDisponibilidad = afectaDisponibilidad;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(Integer idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    public String getTituloTipoNovedad() {
        return tituloTipoNovedad;
    }

    public void setTituloTipoNovedad(String tituloTipoNovedad) {
        this.tituloTipoNovedad = tituloTipoNovedad;
    }

    public String getDescripcionTipoNovedad() {
        return descripcionTipoNovedad;
    }

    public void setDescripcionTipoNovedad(String descripcionTipoNovedad) {
        this.descripcionTipoNovedad = descripcionTipoNovedad;
    }

    public int getFechas() {
        return fechas;
    }

    public void setFechas(int fechas) {
        this.fechas = fechas;
    }

    public int getPrevencionVial() {
        return prevencionVial;
    }

    public void setPrevencionVial(int prevencionVial) {
        this.prevencionVial = prevencionVial;
    }

    public int getAfectaPm() {
        return afectaPm;
    }

    public void setAfectaPm(int afectaPm) {
        this.afectaPm = afectaPm;
    }

    public Integer getPuntosPm() {
        return puntosPm;
    }

    public void setPuntosPm(Integer puntosPm) {
        this.puntosPm = puntosPm;
    }

    public int getAfectaProgramacion() {
        return afectaProgramacion;
    }

    public void setAfectaProgramacion(int afectaProgramacion) {
        this.afectaProgramacion = afectaProgramacion;
    }

    public int getRequiereSoporte() {
        return requiereSoporte;
    }

    public void setRequiereSoporte(int requiereSoporte) {
        this.requiereSoporte = requiereSoporte;
    }

    public int getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(int notificacion) {
        this.notificacion = notificacion;
    }

    public int getReqHoras() {
        return reqHoras;
    }

    public void setReqHoras(int reqHoras) {
        this.reqHoras = reqHoras;
    }

    public int getAfectaGestor() {
        return afectaGestor;
    }

    public void setAfectaGestor(int afectaGestor) {
        this.afectaGestor = afectaGestor;
    }

    public int getIncluirReporte() {
        return incluirReporte;
    }

    public void setIncluirReporte(int incluirReporte) {
        this.incluirReporte = incluirReporte;
    }

    public int getAfectaDisponibilidad() {
        return afectaDisponibilidad;
    }

    public void setAfectaDisponibilidad(int afectaDisponibilidad) {
        this.afectaDisponibilidad = afectaDisponibilidad;
    }

    public int getReqSitio() {
        return reqSitio;
    }

    public void setReqSitio(int reqSitio) {
        this.reqSitio = reqSitio;
    }

    public int getReqHora() {
        return reqHora;
    }

    public void setReqHora(int reqHora) {
        this.reqHora = reqHora;
    }

    public int getReqVehiculo() {
        return reqVehiculo;
    }

    public void setReqVehiculo(int reqVehiculo) {
        this.reqVehiculo = reqVehiculo;
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

    @XmlTransient
    public List<Novedad> getNovedadList() {
        return novedadList;
    }

    public void setNovedadList(List<Novedad> novedadList) {
        this.novedadList = novedadList;
    }

    public NotificacionProcesos getIdNotificacionProcesos() {
        return idNotificacionProcesos;
    }

    public void setIdNotificacionProcesos(NotificacionProcesos idNotificacionProcesos) {
        this.idNotificacionProcesos = idNotificacionProcesos;
    }

    public SncDetalle getIdSncDetalle() {
        return idSncDetalle;
    }

    public void setIdSncDetalle(SncDetalle idSncDetalle) {
        this.idSncDetalle = idSncDetalle;
    }

    public NovedadTipo getIdNovedadTipo() {
        return idNovedadTipo;
    }

    public void setIdNovedadTipo(NovedadTipo idNovedadTipo) {
        this.idNovedadTipo = idNovedadTipo;
    }

    public NovedadClasificacion getIdNovedadClasificacion() {
        return idNovedadClasificacion;
    }

    public void setIdNovedadClasificacion(NovedadClasificacion idNovedadClasificacion) {
        this.idNovedadClasificacion = idNovedadClasificacion;
    }

    public VehiculoTipoEstadoDet getIdVehiculoTipoEstadoDet() {
        return idVehiculoTipoEstadoDet;
    }

    public void setIdVehiculoTipoEstadoDet(VehiculoTipoEstadoDet idVehiculoTipoEstadoDet) {
        this.idVehiculoTipoEstadoDet = idVehiculoTipoEstadoDet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadTipoDetalle != null ? idNovedadTipoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadTipoDetalles)) {
            return false;
        }
        NovedadTipoDetalles other = (NovedadTipoDetalles) object;
        if ((this.idNovedadTipoDetalle == null && other.idNovedadTipoDetalle != null) || (this.idNovedadTipoDetalle != null && !this.idNovedadTipoDetalle.equals(other.idNovedadTipoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadTipoDetalles[ idNovedadTipoDetalle=" + idNovedadTipoDetalle + " ]";
    }

    @XmlTransient
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    @XmlTransient
    public List<AccChecklist> getAccChecklistList() {
        return accChecklistList;
    }

    public void setAccChecklistList(List<AccChecklist> accChecklistList) {
        this.accChecklistList = accChecklistList;
    }

    @XmlTransient
    public List<PmNovedadExcluir> getPmNovedadExcluirList() {
        return pmNovedadExcluirList;
    }

    public void setPmNovedadExcluirList(List<PmNovedadExcluir> pmNovedadExcluirList) {
        this.pmNovedadExcluirList = pmNovedadExcluirList;
    }

    @XmlTransient
    public List<PmNovedadIncluir> getPmNovedadIncluirList() {
        return pmNovedadIncluirList;
    }

    public void setPmNovedadIncluirList(List<PmNovedadIncluir> pmNovedadIncluirList) {
        this.pmNovedadIncluirList = pmNovedadIncluirList;
    }

    @XmlTransient
    public List<PrgSolicitudNovedadParam> getPrgSolicitudNovedadParamList() {
        return prgSolicitudNovedadParamList;
    }

    public void setPrgSolicitudNovedadParamList(List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList) {
        this.prgSolicitudNovedadParamList = prgSolicitudNovedadParamList;
    }

    @XmlTransient
    public List<PrgSolicitudNovedadParam> getPrgSolicitudNovedadParamList1() {
        return prgSolicitudNovedadParamList1;
    }

    public void setPrgSolicitudNovedadParamList1(List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList1) {
        this.prgSolicitudNovedadParamList1 = prgSolicitudNovedadParamList1;
    }

    @XmlTransient
    public List<PrgSolicitudNovedadParam> getPrgSolicitudNovedadParamList2() {
        return prgSolicitudNovedadParamList2;
    }

    public void setPrgSolicitudNovedadParamList2(List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList2) {
        this.prgSolicitudNovedadParamList2 = prgSolicitudNovedadParamList2;
    }

    @XmlTransient
    public List<PrgSolicitudNovedadParam> getPrgSolicitudNovedadParamList3() {
        return prgSolicitudNovedadParamList3;
    }

    public void setPrgSolicitudNovedadParamList3(List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList3) {
        this.prgSolicitudNovedadParamList3 = prgSolicitudNovedadParamList3;
    }

    @XmlTransient
    public List<AuditoriaEncabezado> getAuditoriaEncabezadoList() {
        return AuditoriaEncabezadoList;
    }

    public void setAuditoriaEncabezadoList(List<AuditoriaEncabezado> AuditoriaEncabezadoList) {
        this.AuditoriaEncabezadoList = AuditoriaEncabezadoList;
    }

    public List<PrgDesasignarParam> getPrgDesasignarParamList() {
        return prgDesasignarParamList;
    }

    public void setPrgDesasignarParamList(List<PrgDesasignarParam> prgDesasignarParamList) {
        this.prgDesasignarParamList = prgDesasignarParamList;
    }

    public int getMtto() {
        return mtto;
    }

    public void setMtto(int mtto) {
        this.mtto = mtto;
    }

    public int getSumaGestor() {
        return sumaGestor;
    }

    public void setSumaGestor(int sumaGestor) {
        this.sumaGestor = sumaGestor;
    }

    public int getNovedadNomina() {
        return novedadNomina;
    }

    public void setNovedadNomina(int novedadNomina) {
        this.novedadNomina = novedadNomina;
    }

    public int getAtv() {
        return atv;
    }

    public void setAtv(int atv) {
        this.atv = atv;
    }

    @XmlTransient
    public List<ChkComponenteFalla> getChkComponenteFallaList() {
        return chkComponenteFallaList;
    }

    public void setChkComponenteFallaList(List<ChkComponenteFalla> chkComponenteFallaList) {
        this.chkComponenteFallaList = chkComponenteFallaList;
    }

    @XmlTransient
    public List<GestorNovDet> getGestorNovDetList() {
        return gestorNovDetList;
    }

    public void setGestorNovDetList(List<GestorNovDet> gestorNovDetList) {
        this.gestorNovDetList = gestorNovDetList;
    }

    @XmlTransient
    public List<GestorTablaTmp> getGestorTablaTmpList() {
        return gestorTablaTmpList;
    }

    public void setGestorTablaTmpList(List<GestorTablaTmp> gestorTablaTmpList) {
        this.gestorTablaTmpList = gestorTablaTmpList;
    }

    @XmlTransient
    public List<GestorNovDetSemana> getGestorNovDetSemanaList() {
        return gestorNovDetSemanaList;
    }

    public void setGestorNovDetSemanaList(List<GestorNovDetSemana> gestorNovDetSemanaList) {
        this.gestorNovDetSemanaList = gestorNovDetSemanaList;
    }

    @XmlTransient
    public List<NominaServerParamDet> getNominaServerParamDetList() {
        return nominaServerParamDetList;
    }

    public void setNominaServerParamDetList(List<NominaServerParamDet> nominaServerParamDetList) {
        this.nominaServerParamDetList = nominaServerParamDetList;
    }

    public String getNombreAccesoRapido() {
        return nombreAccesoRapido;
    }

    public void setNombreAccesoRapido(String nombreAccesoRapido) {
        this.nombreAccesoRapido = nombreAccesoRapido;
    }

    public String getDescripcionGestionServicio() {
        return descripcionGestionServicio;
    }

    public void setDescripcionGestionServicio(String descripcionGestionServicio) {
        this.descripcionGestionServicio = descripcionGestionServicio;
    }

    public PrgTcResponsable getIdPrgTcResponsable() {
        return idPrgTcResponsable;
    }

    public void setIdPrgTcResponsable(PrgTcResponsable idPrgTcResponsable) {
        this.idPrgTcResponsable = idPrgTcResponsable;
    }

    public PrgClasificacionMotivo getIdPrgClasificacionMotivo() {
        return idPrgClasificacionMotivo;
    }

    public void setIdPrgClasificacionMotivo(PrgClasificacionMotivo idPrgClasificacionMotivo) {
        this.idPrgClasificacionMotivo = idPrgClasificacionMotivo;
    }

    public PrgStopPoint getFromStop() {
        return fromStop;
    }

    public void setFromStop(PrgStopPoint fromStop) {
        this.fromStop = fromStop;
    }

    public PrgStopPoint getToStop() {
        return toStop;
    }

    public void setToStop(PrgStopPoint toStop) {
        this.toStop = toStop;
    }

    public int getNotificaOperador() {
        return notificaOperador;
    }

    public void setNotificaOperador(int notificaOperador) {
        this.notificaOperador = notificaOperador;
    }

}
