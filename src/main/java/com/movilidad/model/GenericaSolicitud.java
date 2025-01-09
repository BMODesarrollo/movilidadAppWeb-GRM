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
@Table(name = "generica_solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaSolicitud.findAll", query = "SELECT g FROM GenericaSolicitud g"),
    @NamedQuery(name = "GenericaSolicitud.findByIdGenericaSolicitud", query = "SELECT g FROM GenericaSolicitud g WHERE g.idGenericaSolicitud = :idGenericaSolicitud"),
    @NamedQuery(name = "GenericaSolicitud.findByFechaSolicitud", query = "SELECT g FROM GenericaSolicitud g WHERE g.fechaSolicitud = :fechaSolicitud"),
    @NamedQuery(name = "GenericaSolicitud.findByMotivo", query = "SELECT g FROM GenericaSolicitud g WHERE g.motivo = :motivo"),
    @NamedQuery(name = "GenericaSolicitud.findByUserAprueba", query = "SELECT g FROM GenericaSolicitud g WHERE g.userAprueba = :userAprueba"),
    @NamedQuery(name = "GenericaSolicitud.findByRequiereSoporte", query = "SELECT g FROM GenericaSolicitud g WHERE g.requiereSoporte = :requiereSoporte"),
    @NamedQuery(name = "GenericaSolicitud.findByReponeTiempo", query = "SELECT g FROM GenericaSolicitud g WHERE g.reponeTiempo = :reponeTiempo"),
    @NamedQuery(name = "GenericaSolicitud.findByNota", query = "SELECT g FROM GenericaSolicitud g WHERE g.nota = :nota"),
    @NamedQuery(name = "GenericaSolicitud.findByCreado", query = "SELECT g FROM GenericaSolicitud g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaSolicitud.findByModificado", query = "SELECT g FROM GenericaSolicitud g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaSolicitud.findByEstadoReg", query = "SELECT g FROM GenericaSolicitud g WHERE g.estadoReg = :estadoReg")})
public class GenericaSolicitud implements Serializable {
    @OneToMany(mappedBy = "idGenericaSolicitud")
    private List<GenericaSolicitudCambio> genericaSolicitudCambioList;
    @OneToMany(mappedBy = "idGenericaSolicitud")
    private List<GenericaSolicitudPermiso> genericaSolicitudPermisoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_solicitud")
    private Integer idGenericaSolicitud;
    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "motivo")
    private String motivo;
    @Size(max = 15)
    @Column(name = "user_aprueba")
    private String userAprueba;
    @Column(name = "requiere_soporte")
    private Integer requiereSoporte;
    @Column(name = "repone_tiempo")
    private Integer reponeTiempo;
    @Size(max = 255)
    @Column(name = "nota")
    private String nota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_generica_jornada", referencedColumnName = "id_generica_jornada")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaJornada idGenericaJornada;
    @JoinColumn(name = "id_generica_solicitud_motivo", referencedColumnName = "id_generica_solicitud_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaSolicitudMotivo idGenericaSolicitudMotivo;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_generica_token", referencedColumnName = "id_generica_token")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaToken idGenericaToken;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public GenericaSolicitud() {
    }

    public GenericaSolicitud(Integer idGenericaSolicitud) {
        this.idGenericaSolicitud = idGenericaSolicitud;
    }

    public GenericaSolicitud(Integer idGenericaSolicitud, String motivo, Date creado) {
        this.idGenericaSolicitud = idGenericaSolicitud;
        this.motivo = motivo;
        this.creado = creado;
    }

    public Integer getIdGenericaSolicitud() {
        return idGenericaSolicitud;
    }

    public void setIdGenericaSolicitud(Integer idGenericaSolicitud) {
        this.idGenericaSolicitud = idGenericaSolicitud;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getUserAprueba() {
        return userAprueba;
    }

    public void setUserAprueba(String userAprueba) {
        this.userAprueba = userAprueba;
    }

    public Integer getRequiereSoporte() {
        return requiereSoporte;
    }

    public void setRequiereSoporte(Integer requiereSoporte) {
        this.requiereSoporte = requiereSoporte;
    }

    public Integer getReponeTiempo() {
        return reponeTiempo;
    }

    public void setReponeTiempo(Integer reponeTiempo) {
        this.reponeTiempo = reponeTiempo;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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

    public GenericaJornada getIdGenericaJornada() {
        return idGenericaJornada;
    }

    public void setIdGenericaJornada(GenericaJornada idGenericaJornada) {
        this.idGenericaJornada = idGenericaJornada;
    }

    public GenericaSolicitudMotivo getIdGenericaSolicitudMotivo() {
        return idGenericaSolicitudMotivo;
    }

    public void setIdGenericaSolicitudMotivo(GenericaSolicitudMotivo idGenericaSolicitudMotivo) {
        this.idGenericaSolicitudMotivo = idGenericaSolicitudMotivo;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public GenericaToken getIdGenericaToken() {
        return idGenericaToken;
    }

    public void setIdGenericaToken(GenericaToken idGenericaToken) {
        this.idGenericaToken = idGenericaToken;
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
        hash += (idGenericaSolicitud != null ? idGenericaSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaSolicitud)) {
            return false;
        }
        GenericaSolicitud other = (GenericaSolicitud) object;
        if ((this.idGenericaSolicitud == null && other.idGenericaSolicitud != null) || (this.idGenericaSolicitud != null && !this.idGenericaSolicitud.equals(other.idGenericaSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaSolicitud[ idGenericaSolicitud=" + idGenericaSolicitud + " ]";
    }

    @XmlTransient
    public List<GenericaSolicitudPermiso> getGenericaSolicitudPermisoList() {
        return genericaSolicitudPermisoList;
    }

    public void setGenericaSolicitudPermisoList(List<GenericaSolicitudPermiso> genericaSolicitudPermisoList) {
        this.genericaSolicitudPermisoList = genericaSolicitudPermisoList;
    }

    @XmlTransient
    public List<GenericaSolicitudCambio> getGenericaSolicitudCambioList() {
        return genericaSolicitudCambioList;
    }

    public void setGenericaSolicitudCambioList(List<GenericaSolicitudCambio> genericaSolicitudCambioList) {
        this.genericaSolicitudCambioList = genericaSolicitudCambioList;
    }

}
