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
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "generica_tipo_detalles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaTipoDetalles.findAll", query = "SELECT g FROM GenericaTipoDetalles g"),
    @NamedQuery(name = "GenericaTipoDetalles.findByIdGenericaTipoDetalle", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.idGenericaTipoDetalle = :idGenericaTipoDetalle"),
    @NamedQuery(name = "GenericaTipoDetalles.findByTituloTipoGenerica", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.tituloTipoGenerica = :tituloTipoGenerica"),
    @NamedQuery(name = "GenericaTipoDetalles.findByFechas", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.fechas = :fechas"),
    @NamedQuery(name = "GenericaTipoDetalles.findByHora", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.hora = :hora"),
    @NamedQuery(name = "GenericaTipoDetalles.findByPrevencionVial", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.prevencionVial = :prevencionVial"),
    @NamedQuery(name = "GenericaTipoDetalles.findByAfectaPm", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.afectaPm = :afectaPm"),
    @NamedQuery(name = "GenericaTipoDetalles.findByPuntosPm", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.puntosPm = :puntosPm"),
    @NamedQuery(name = "GenericaTipoDetalles.findByAfectaProgramacion", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.afectaProgramacion = :afectaProgramacion"),
    @NamedQuery(name = "GenericaTipoDetalles.findByRequiereSoporte", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.requiereSoporte = :requiereSoporte"),
    @NamedQuery(name = "GenericaTipoDetalles.findByNotificacion", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.notificacion = :notificacion"),
    @NamedQuery(name = "GenericaTipoDetalles.findByUsername", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaTipoDetalles.findByCreado", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaTipoDetalles.findByModificado", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaTipoDetalles.findByEstadoReg", query = "SELECT g FROM GenericaTipoDetalles g WHERE g.estadoReg = :estadoReg")})
public class GenericaTipoDetalles implements Serializable {

    @OneToMany(mappedBy = "idGenericaTipoDetalle", fetch = FetchType.LAZY)
    private List<NominaServerParamDetGen> nominaServerParamDetGenList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_tipo_detalle")
    private Integer idGenericaTipoDetalle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "titulo_tipo_generica")
    private String tituloTipoGenerica;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion_tipo_generica")
    private String descripcionTipoGenerica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechas")
    private int fechas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora")
    private int hora;
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
    @OneToMany(mappedBy = "idGenericaTipoDetalle", fetch = FetchType.LAZY)
    private List<Generica> genericaList;
    @JoinColumn(name = "id_generica_notif_procesos", referencedColumnName = "id_generica_notif_procesos")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaNotifProcesos idGenericaNotifProcesos;
    @JoinColumn(name = "id_generica_tipo", referencedColumnName = "id_generica_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaTipo idGenericaTipo;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenericaTipoDetalle", fetch = FetchType.LAZY)
    private List<GenericaPmNovedadExcluir> genericaPmNovedadExcluirList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenericaTipoDetalle", fetch = FetchType.LAZY)
    private List<GenericaPmNovedadIncluir> genericaPmNovedadIncluirList;
    @OneToMany(mappedBy = "idGenericaTipoDetalles", fetch = FetchType.LAZY)
    private List<AuditoriaEncabezado> AuditoriaEncabezadoList;

    public GenericaTipoDetalles() {
    }

    public GenericaTipoDetalles(Integer idGenericaTipoDetalle) {
        this.idGenericaTipoDetalle = idGenericaTipoDetalle;
    }

    public GenericaTipoDetalles(Integer idGenericaTipoDetalle, String tituloTipoGenerica, int fechas, int hora, int prevencionVial, int afectaPm, int afectaProgramacion, int requiereSoporte, int notificacion, String username, Date creado, int estadoReg) {
        this.idGenericaTipoDetalle = idGenericaTipoDetalle;
        this.tituloTipoGenerica = tituloTipoGenerica;
        this.fechas = fechas;
        this.hora = hora;
        this.prevencionVial = prevencionVial;
        this.afectaPm = afectaPm;
        this.afectaProgramacion = afectaProgramacion;
        this.requiereSoporte = requiereSoporte;
        this.notificacion = notificacion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaTipoDetalle() {
        return idGenericaTipoDetalle;
    }

    public void setIdGenericaTipoDetalle(Integer idGenericaTipoDetalle) {
        this.idGenericaTipoDetalle = idGenericaTipoDetalle;
    }

    public String getTituloTipoGenerica() {
        return tituloTipoGenerica;
    }

    public void setTituloTipoGenerica(String tituloTipoGenerica) {
        this.tituloTipoGenerica = tituloTipoGenerica;
    }

    public String getDescripcionTipoGenerica() {
        return descripcionTipoGenerica;
    }

    public void setDescripcionTipoGenerica(String descripcionTipoGenerica) {
        this.descripcionTipoGenerica = descripcionTipoGenerica;
    }

    public int getFechas() {
        return fechas;
    }

    public void setFechas(int fechas) {
        this.fechas = fechas;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
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
    public List<Generica> getGenericaList() {
        return genericaList;
    }

    public void setGenericaList(List<Generica> genericaList) {
        this.genericaList = genericaList;
    }

    public GenericaNotifProcesos getIdGenericaNotifProcesos() {
        return idGenericaNotifProcesos;
    }

    public void setIdGenericaNotifProcesos(GenericaNotifProcesos idGenericaNotifProcesos) {
        this.idGenericaNotifProcesos = idGenericaNotifProcesos;
    }

    public GenericaTipo getIdGenericaTipo() {
        return idGenericaTipo;
    }

    public void setIdGenericaTipo(GenericaTipo idGenericaTipo) {
        this.idGenericaTipo = idGenericaTipo;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @XmlTransient
    public List<GenericaPmNovedadExcluir> getGenericaPmNovedadExcluirList() {
        return genericaPmNovedadExcluirList;
    }

    public void setGenericaPmNovedadExcluirList(List<GenericaPmNovedadExcluir> genericaPmNovedadExcluirList) {
        this.genericaPmNovedadExcluirList = genericaPmNovedadExcluirList;
    }

    @XmlTransient
    public List<AuditoriaEncabezado> getAuditoriaEncabezadoList() {
        return AuditoriaEncabezadoList;
    }

    public void setAuditoriaEncabezadoList(List<AuditoriaEncabezado> AuditoriaEncabezadoList) {
        this.AuditoriaEncabezadoList = AuditoriaEncabezadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaTipoDetalle != null ? idGenericaTipoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaTipoDetalles)) {
            return false;
        }
        GenericaTipoDetalles other = (GenericaTipoDetalles) object;
        if ((this.idGenericaTipoDetalle == null && other.idGenericaTipoDetalle != null) || (this.idGenericaTipoDetalle != null && !this.idGenericaTipoDetalle.equals(other.idGenericaTipoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaTipoDetalles[ idGenericaTipoDetalle=" + idGenericaTipoDetalle + " ]";
    }

    @XmlTransient
    public List<GenericaPmNovedadIncluir> getGenericaPmNovedadIncluirList() {
        return genericaPmNovedadIncluirList;
    }

    public void setGenericaPmNovedadIncluirList(List<GenericaPmNovedadIncluir> genericaPmNovedadIncluirList) {
        this.genericaPmNovedadIncluirList = genericaPmNovedadIncluirList;
    }

    @XmlTransient
    public List<NominaServerParamDetGen> getNominaServerParamDetGenList() {
        return nominaServerParamDetGenList;
    }

    public void setNominaServerParamDetGenList(List<NominaServerParamDetGen> nominaServerParamDetGenList) {
        this.nominaServerParamDetGenList = nominaServerParamDetGenList;
    }

}
