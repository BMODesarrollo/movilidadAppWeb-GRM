/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "cable_evento_tipo_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableEventoTipoDet.findAll", query = "SELECT c FROM CableEventoTipoDet c")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByIdCableEventoTipoDet", query = "SELECT c FROM CableEventoTipoDet c WHERE c.idCableEventoTipoDet = :idCableEventoTipoDet")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByCodigo", query = "SELECT c FROM CableEventoTipoDet c WHERE c.codigo = :codigo")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByNombre", query = "SELECT c FROM CableEventoTipoDet c WHERE c.nombre = :nombre")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqUbicacion", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqUbicacion = :reqUbicacion")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqCabina", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqCabina = :reqCabina")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqHoraEventoParada", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqHoraEventoParada = :reqHoraEventoParada")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqHoraEventoReinicio", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqHoraEventoReinicio = :reqHoraEventoReinicio")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqTiempoOperacionCom", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqTiempoOperacionCom = :reqTiempoOperacionCom")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqHorometroTotal", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqHorometroTotal = :reqHorometroTotal")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqTiempoOperacionSistema", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqTiempoOperacionSistema = :reqTiempoOperacionSistema")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqTramo", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqTramo = :reqTramo")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByReqOperador", query = "SELECT c FROM CableEventoTipoDet c WHERE c.reqOperador = :reqOperador")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByNotifica", query = "SELECT c FROM CableEventoTipoDet c WHERE c.notifica = :notifica")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByClaseEvento", query = "SELECT c FROM CableEventoTipoDet c WHERE c.claseEvento = :claseEvento")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByUsername", query = "SELECT c FROM CableEventoTipoDet c WHERE c.username = :username")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByCreado", query = "SELECT c FROM CableEventoTipoDet c WHERE c.creado = :creado")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByModificado", query = "SELECT c FROM CableEventoTipoDet c WHERE c.modificado = :modificado")
    ,
    @NamedQuery(name = "CableEventoTipoDet.findByEstadoReg", query = "SELECT c FROM CableEventoTipoDet c WHERE c.estadoReg = :estadoReg")})
public class CableEventoTipoDet implements Serializable {

    @OneToMany(mappedBy = "idCableEventoTipoDet", fetch = FetchType.LAZY)
    private List<CableNovedadOp> cableNovedadOpList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_evento_tipo_det")
    private Integer idCableEventoTipoDet;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_ubicacion")
    private int reqUbicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_cabina")
    private int reqCabina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_hora_evento_parada")
    private int reqHoraEventoParada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_hora_evento_reinicio")
    private int reqHoraEventoReinicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_tiempo_operacion_com")
    private int reqTiempoOperacionCom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_horometro_total")
    private int reqHorometroTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_tiempo_operacion_sistema")
    private int reqTiempoOperacionSistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_tramo")
    private int reqTramo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "req_operador")
    private int reqOperador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "notifica")
    private int notifica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "clase_evento")
    private int claseEvento;
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
    @JoinColumn(name = "id_cable_evento_tipo", referencedColumnName = "id_cable_evento_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEventoTipo idCableEventoTipo;
    @JoinColumn(name = "id_notificacion_procesos", referencedColumnName = "id_notificacion_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotificacionProcesos idNotificacionProcesos;

    public CableEventoTipoDet() {
    }

    public CableEventoTipoDet(Integer idCableEventoTipoDet) {
        this.idCableEventoTipoDet = idCableEventoTipoDet;
    }

    public CableEventoTipoDet(Integer idCableEventoTipoDet, String codigo, String nombre, String descripcion, int reqUbicacion, int reqCabina, int reqHoraEventoParada, int reqHoraEventoReinicio, int reqTiempoOperacionCom, int reqHorometroTotal, int reqTiempoOperacionSistema, int reqOperador, int reqTramo, int notifica, int claseEvento, String username, Date creado, int estadoReg) {
        this.idCableEventoTipoDet = idCableEventoTipoDet;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.reqUbicacion = reqUbicacion;
        this.reqCabina = reqCabina;
        this.reqHoraEventoParada = reqHoraEventoParada;
        this.reqHoraEventoReinicio = reqHoraEventoReinicio;
        this.reqTiempoOperacionCom = reqTiempoOperacionCom;
        this.reqHorometroTotal = reqHorometroTotal;
        this.reqTiempoOperacionSistema = reqTiempoOperacionSistema;
        this.reqTramo = reqTramo;
        this.reqOperador = reqOperador;
        this.notifica = notifica;
        this.claseEvento = claseEvento;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableEventoTipoDet() {
        return idCableEventoTipoDet;
    }

    public void setIdCableEventoTipoDet(Integer idCableEventoTipoDet) {
        this.idCableEventoTipoDet = idCableEventoTipoDet;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getReqUbicacion() {
        return reqUbicacion;
    }

    public void setReqUbicacion(int reqUbicacion) {
        this.reqUbicacion = reqUbicacion;
    }

    public int getReqCabina() {
        return reqCabina;
    }

    public void setReqCabina(int reqCabina) {
        this.reqCabina = reqCabina;
    }

    public int getReqHoraEventoParada() {
        return reqHoraEventoParada;
    }

    public void setReqHoraEventoParada(int reqHoraEventoParada) {
        this.reqHoraEventoParada = reqHoraEventoParada;
    }

    public int getReqHoraEventoReinicio() {
        return reqHoraEventoReinicio;
    }

    public void setReqHoraEventoReinicio(int reqHoraEventoReinicio) {
        this.reqHoraEventoReinicio = reqHoraEventoReinicio;
    }

    public int getReqTiempoOperacionCom() {
        return reqTiempoOperacionCom;
    }

    public void setReqTiempoOperacionCom(int reqTiempoOperacionCom) {
        this.reqTiempoOperacionCom = reqTiempoOperacionCom;
    }

    public int getReqHorometroTotal() {
        return reqHorometroTotal;
    }

    public void setReqHorometroTotal(int reqHorometroTotal) {
        this.reqHorometroTotal = reqHorometroTotal;
    }

    public int getReqTiempoOperacionSistema() {
        return reqTiempoOperacionSistema;
    }

    public void setReqTiempoOperacionSistema(int reqTiempoOperacionSistema) {
        this.reqTiempoOperacionSistema = reqTiempoOperacionSistema;
    }

    public int getReqOperador() {
        return reqOperador;
    }

    public void setReqOperador(int reqOperador) {
        this.reqOperador = reqOperador;
    }

    public int getReqTramo() {
        return reqTramo;
    }

    public void setReqTramo(int reqTramo) {
        this.reqTramo = reqTramo;
    }

    public int getNotifica() {
        return notifica;
    }

    public void setNotifica(int notifica) {
        this.notifica = notifica;
    }

    public int getClaseEvento() {
        return claseEvento;
    }

    public void setClaseEvento(int claseEvento) {
        this.claseEvento = claseEvento;
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

    public CableEventoTipo getIdCableEventoTipo() {
        return idCableEventoTipo;
    }

    public void setIdCableEventoTipo(CableEventoTipo idCableEventoTipo) {
        this.idCableEventoTipo = idCableEventoTipo;
    }

    public NotificacionProcesos getIdNotificacionProcesos() {
        return idNotificacionProcesos;
    }

    public void setIdNotificacionProcesos(NotificacionProcesos idNotificacionProcesos) {
        this.idNotificacionProcesos = idNotificacionProcesos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableEventoTipoDet != null ? idCableEventoTipoDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableEventoTipoDet)) {
            return false;
        }
        CableEventoTipoDet other = (CableEventoTipoDet) object;
        if ((this.idCableEventoTipoDet == null && other.idCableEventoTipoDet != null) || (this.idCableEventoTipoDet != null && !this.idCableEventoTipoDet.equals(other.idCableEventoTipoDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableEventoTipoDet[ idCableEventoTipoDet=" + idCableEventoTipoDet + " ]";
    }

    @XmlTransient
    public List<CableNovedadOp> getCableNovedadOpList() {
        return cableNovedadOpList;
    }

    public void setCableNovedadOpList(List<CableNovedadOp> cableNovedadOpList) {
        this.cableNovedadOpList = cableNovedadOpList;
    }

}
