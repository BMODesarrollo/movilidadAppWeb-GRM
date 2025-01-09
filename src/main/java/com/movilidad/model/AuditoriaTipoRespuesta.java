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
@Table(name = "auditoria_tipo_respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaTipoRespuesta.findAll", query = "SELECT a FROM AuditoriaTipoRespuesta a"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findByIdAuditoriaTipoRespuesta", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.idAuditoriaTipoRespuesta = :idAuditoriaTipoRespuesta"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findByNombre", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findBySeleccionMultiple", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.seleccionMultiple = :seleccionMultiple"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findByObservacion", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.observacion = :observacion"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findByDocumento", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.documento = :documento"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findByUsername", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.username = :username"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findByCreado", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.creado = :creado"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findByModificado", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AuditoriaTipoRespuesta.findByEstadoReg", query = "SELECT a FROM AuditoriaTipoRespuesta a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaTipoRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_tipo_respuesta")
    private Integer idAuditoriaTipoRespuesta;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "seleccion_multiple")
    private Integer seleccionMultiple;
    @Column(name = "observacion")
    private Integer observacion;
    @Column(name = "documento")
    private Integer documento;
    @Column(name = "abierta")
    private Integer abierta;
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
    @OneToMany(mappedBy = "idAuditoriaTipoRespuesta", fetch = FetchType.LAZY)
    private List<AuditoriaPregunta> auditoriaPreguntaList;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoriaTipoRespuesta", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AuditoriaAlternativaRespuesta> auditoriaAlternativaRespuestaList;

    public AuditoriaTipoRespuesta() {
    }

    public AuditoriaTipoRespuesta(Integer idAuditoriaTipoRespuesta) {
        this.idAuditoriaTipoRespuesta = idAuditoriaTipoRespuesta;
    }

    public AuditoriaTipoRespuesta(Integer idAuditoriaTipoRespuesta, String username, Date creado, int estadoReg) {
        this.idAuditoriaTipoRespuesta = idAuditoriaTipoRespuesta;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaTipoRespuesta() {
        return idAuditoriaTipoRespuesta;
    }

    public void setIdAuditoriaTipoRespuesta(Integer idAuditoriaTipoRespuesta) {
        this.idAuditoriaTipoRespuesta = idAuditoriaTipoRespuesta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSeleccionMultiple() {
        return seleccionMultiple;
    }

    public void setSeleccionMultiple(Integer seleccionMultiple) {
        this.seleccionMultiple = seleccionMultiple;
    }

    public Integer getObservacion() {
        return observacion;
    }

    public void setObservacion(Integer observacion) {
        this.observacion = observacion;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
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
    public List<AuditoriaPregunta> getAuditoriaPreguntaList() {
        return auditoriaPreguntaList;
    }

    public void setAuditoriaPreguntaList(List<AuditoriaPregunta> auditoriaPreguntaList) {
        this.auditoriaPreguntaList = auditoriaPreguntaList;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @XmlTransient
    public List<AuditoriaAlternativaRespuesta> getAuditoriaAlternativaRespuestaList() {
        return auditoriaAlternativaRespuestaList;
    }

    public void setAuditoriaAlternativaRespuestaList(List<AuditoriaAlternativaRespuesta> auditoriaAlternativaRespuestaList) {
        this.auditoriaAlternativaRespuestaList = auditoriaAlternativaRespuestaList;
    }

    public Integer getAbierta() {
        return abierta;
    }

    public void setAbierta(Integer abierta) {
        this.abierta = abierta;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaTipoRespuesta != null ? idAuditoriaTipoRespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaTipoRespuesta)) {
            return false;
        }
        AuditoriaTipoRespuesta other = (AuditoriaTipoRespuesta) object;
        if ((this.idAuditoriaTipoRespuesta == null && other.idAuditoriaTipoRespuesta != null) || (this.idAuditoriaTipoRespuesta != null && !this.idAuditoriaTipoRespuesta.equals(other.idAuditoriaTipoRespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaTipoRespuesta[ idAuditoriaTipoRespuesta=" + idAuditoriaTipoRespuesta + " ]";
    }

}
