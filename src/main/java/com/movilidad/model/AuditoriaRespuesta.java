/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "auditoria_respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaRespuesta.findAll", query = "SELECT a FROM AuditoriaRespuesta a")
    ,
    @NamedQuery(name = "AuditoriaRespuesta.findByIdAuditoriaRespuesta", query = "SELECT a FROM AuditoriaRespuesta a WHERE a.idAuditoriaRespuesta = :idAuditoriaRespuesta")
    ,
    @NamedQuery(name = "AuditoriaRespuesta.findByUsername", query = "SELECT a FROM AuditoriaRespuesta a WHERE a.username = :username")
    ,
    @NamedQuery(name = "AuditoriaRespuesta.findByCreado", query = "SELECT a FROM AuditoriaRespuesta a WHERE a.creado = :creado")
    ,
    @NamedQuery(name = "AuditoriaRespuesta.findByModificado", query = "SELECT a FROM AuditoriaRespuesta a WHERE a.modificado = :modificado")
    ,
    @NamedQuery(name = "AuditoriaRespuesta.findByEstadoReg", query = "SELECT a FROM AuditoriaRespuesta a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_respuesta")
    private Integer idAuditoriaRespuesta;
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
    @Size(max = 150)
    @Column(name = "path_documento")
    private String pathDocumento;
    @Size(max = 45)
    @Column(name = "respuesta_abierta")
    private String respuestaAbierta;
    @Size(max = 200)
    @Column(name = "respuesta_observacion")
    private String respuestaObservacion;
    @JoinColumn(name = "id_auditoria_alternativa_respuesta", referencedColumnName = "id_auditoria_alternativa_respuesta")
    @ManyToOne(fetch = FetchType.LAZY)
    private AuditoriaAlternativaRespuesta idAuditoriaAlternativaRespuesta;
    @JoinColumn(name = "id_auditoria_pregunta", referencedColumnName = "id_auditoria_pregunta")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AuditoriaPregunta idAuditoriaPregunta;
    @JoinColumn(name = "id_auditoria_realizado_por", referencedColumnName = "id_auditoria_realizado_por")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AuditoriaRealizadoPor idAuditoriaRealizadoPor;

    public AuditoriaRespuesta() {
    }

    public AuditoriaRespuesta(Integer idAuditoriaRespuesta) {
        this.idAuditoriaRespuesta = idAuditoriaRespuesta;
    }

    public AuditoriaRespuesta(Integer idAuditoriaRespuesta, String username, Date creado, int estadoReg) {
        this.idAuditoriaRespuesta = idAuditoriaRespuesta;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaRespuesta() {
        return idAuditoriaRespuesta;
    }

    public void setIdAuditoriaRespuesta(Integer idAuditoriaRespuesta) {
        this.idAuditoriaRespuesta = idAuditoriaRespuesta;
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

    public AuditoriaAlternativaRespuesta getIdAuditoriaAlternativaRespuesta() {
        return idAuditoriaAlternativaRespuesta;
    }

    public void setIdAuditoriaAlternativaRespuesta(AuditoriaAlternativaRespuesta idAuditoriaAlternativaRespuesta) {
        this.idAuditoriaAlternativaRespuesta = idAuditoriaAlternativaRespuesta;
    }

    public AuditoriaPregunta getIdAuditoriaPregunta() {
        return idAuditoriaPregunta;
    }

    public void setIdAuditoriaPregunta(AuditoriaPregunta idAuditoriaPregunta) {
        this.idAuditoriaPregunta = idAuditoriaPregunta;
    }

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    public String getRespuestaAbierta() {
        return respuestaAbierta;
    }

    public void setRespuestaAbierta(String respuestaAbierta) {
        this.respuestaAbierta = respuestaAbierta;
    }

    public String getRespuestaObservacion() {
        return respuestaObservacion;
    }

    public void setRespuestaObservacion(String respuestaObservacion) {
        this.respuestaObservacion = respuestaObservacion;
    }

    public AuditoriaRealizadoPor getIdAuditoriaRealizadoPor() {
        return idAuditoriaRealizadoPor;
    }

    public void setIdAuditoriaRealizadoPor(AuditoriaRealizadoPor idAuditoriaRealizadoPor) {
        this.idAuditoriaRealizadoPor = idAuditoriaRealizadoPor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaRespuesta != null ? idAuditoriaRespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaRespuesta)) {
            return false;
        }
        AuditoriaRespuesta other = (AuditoriaRespuesta) object;
        if ((this.idAuditoriaRespuesta == null && other.idAuditoriaRespuesta != null) || (this.idAuditoriaRespuesta != null && !this.idAuditoriaRespuesta.equals(other.idAuditoriaRespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaRespuesta[ idAuditoriaRespuesta=" + idAuditoriaRespuesta + " ]";
    }

}
