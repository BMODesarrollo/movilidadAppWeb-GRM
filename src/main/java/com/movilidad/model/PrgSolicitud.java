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
@Table(name = "prg_solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgSolicitud.findAll", query = "SELECT p FROM PrgSolicitud p"),
    @NamedQuery(name = "PrgSolicitud.findByIdPrgSolicitud", query = "SELECT p FROM PrgSolicitud p WHERE p.idPrgSolicitud = :idPrgSolicitud"),
    @NamedQuery(name = "PrgSolicitud.findByFechaSolicitud", query = "SELECT p FROM PrgSolicitud p WHERE p.fechaSolicitud = :fechaSolicitud"),
    @NamedQuery(name = "PrgSolicitud.findByMotivo", query = "SELECT p FROM PrgSolicitud p WHERE p.motivo = :motivo"),
    @NamedQuery(name = "PrgSolicitud.findByUserAprueba", query = "SELECT p FROM PrgSolicitud p WHERE p.userAprueba = :userAprueba"),
    @NamedQuery(name = "PrgSolicitud.findByRequiereSoporte", query = "SELECT p FROM PrgSolicitud p WHERE p.requiereSoporte = :requiereSoporte"),
    @NamedQuery(name = "PrgSolicitud.findByReponeTiempo", query = "SELECT p FROM PrgSolicitud p WHERE p.reponeTiempo = :reponeTiempo"),
    @NamedQuery(name = "PrgSolicitud.findByNota", query = "SELECT p FROM PrgSolicitud p WHERE p.nota = :nota"),
    @NamedQuery(name = "PrgSolicitud.findByCreado", query = "SELECT p FROM PrgSolicitud p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgSolicitud.findByModificado", query = "SELECT p FROM PrgSolicitud p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgSolicitud.findByEstadoReg", query = "SELECT p FROM PrgSolicitud p WHERE p.estadoReg = :estadoReg")})
public class PrgSolicitud implements Serializable {

    @OneToMany(mappedBy = "idPrgSolicitud")
    private List<PrgSolicitudCambio> prgSolicitudCambioList;
    @OneToMany(mappedBy = "idPrgSolicitud")
    private List<PrgSolicitudPermiso> prgSolicitudPermisoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_solicitud")
    private Integer idPrgSolicitud;
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
    @Column(name = "consecutivo")
    private Integer consecutivo;
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
    @JoinColumn(name = "id_prg_solicitud_motivo", referencedColumnName = "id_prg_solicitud_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgSolicitudMotivo idPrgSolicitudMotivo;
    @JoinColumn(name = "id_prg_sercon", referencedColumnName = "id_prg_sercon")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgSercon idPrgSercon;
    @JoinColumn(name = "id_prg_token", referencedColumnName = "id_prg_token")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgToken idPrgToken;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgSolicitud() {
    }

    public PrgSolicitud(Integer idPrgSolicitud) {
        this.idPrgSolicitud = idPrgSolicitud;
    }

    public PrgSolicitud(Integer idPrgSolicitud, String motivo, Date creado) {
        this.idPrgSolicitud = idPrgSolicitud;
        this.motivo = motivo;
        this.creado = creado;
    }

    public Integer getIdPrgSolicitud() {
        return idPrgSolicitud;
    }

    public void setIdPrgSolicitud(Integer idPrgSolicitud) {
        this.idPrgSolicitud = idPrgSolicitud;
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

    public PrgSolicitudMotivo getIdPrgSolicitudMotivo() {
        return idPrgSolicitudMotivo;
    }

    public void setIdPrgSolicitudMotivo(PrgSolicitudMotivo idPrgSolicitudMotivo) {
        this.idPrgSolicitudMotivo = idPrgSolicitudMotivo;
    }

    public PrgSercon getIdPrgSercon() {
        return idPrgSercon;
    }

    public void setIdPrgSercon(PrgSercon idPrgSercon) {
        this.idPrgSercon = idPrgSercon;
    }

    public PrgToken getIdPrgToken() {
        return idPrgToken;
    }

    public void setIdPrgToken(PrgToken idPrgToken) {
        this.idPrgToken = idPrgToken;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
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
        hash += (idPrgSolicitud != null ? idPrgSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgSolicitud)) {
            return false;
        }
        PrgSolicitud other = (PrgSolicitud) object;
        if ((this.idPrgSolicitud == null && other.idPrgSolicitud != null) || (this.idPrgSolicitud != null && !this.idPrgSolicitud.equals(other.idPrgSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgSolicitud[ idPrgSolicitud=" + idPrgSolicitud + " ]";
    }

    @XmlTransient
    public List<PrgSolicitudPermiso> getPrgSolicitudPermisoList() {
        return prgSolicitudPermisoList;
    }

    public void setPrgSolicitudPermisoList(List<PrgSolicitudPermiso> prgSolicitudPermisoList) {
        this.prgSolicitudPermisoList = prgSolicitudPermisoList;
    }

    @XmlTransient
    public List<PrgSolicitudCambio> getPrgSolicitudCambioList() {
        return prgSolicitudCambioList;
    }

    public void setPrgSolicitudCambioList(List<PrgSolicitudCambio> prgSolicitudCambioList) {
        this.prgSolicitudCambioList = prgSolicitudCambioList;
    }

}
