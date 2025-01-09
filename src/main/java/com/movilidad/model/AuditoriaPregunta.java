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
@Table(name = "auditoria_pregunta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaPregunta.findAll", query = "SELECT a FROM AuditoriaPregunta a"),
    @NamedQuery(name = "AuditoriaPregunta.findByIdAuditoriaPregunta", query = "SELECT a FROM AuditoriaPregunta a WHERE a.idAuditoriaPregunta = :idAuditoriaPregunta"),
    @NamedQuery(name = "AuditoriaPregunta.findByCodigo", query = "SELECT a FROM AuditoriaPregunta a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "AuditoriaPregunta.findByEnunciado", query = "SELECT a FROM AuditoriaPregunta a WHERE a.enunciado = :enunciado"),
    @NamedQuery(name = "AuditoriaPregunta.findByNumero", query = "SELECT a FROM AuditoriaPregunta a WHERE a.numero = :numero"),
    @NamedQuery(name = "AuditoriaPregunta.findByUsername", query = "SELECT a FROM AuditoriaPregunta a WHERE a.username = :username"),
    @NamedQuery(name = "AuditoriaPregunta.findByCreado", query = "SELECT a FROM AuditoriaPregunta a WHERE a.creado = :creado"),
    @NamedQuery(name = "AuditoriaPregunta.findByModificado", query = "SELECT a FROM AuditoriaPregunta a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AuditoriaPregunta.findByEstadoReg", query = "SELECT a FROM AuditoriaPregunta a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaPregunta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_pregunta")
    private Integer idAuditoriaPregunta;
    @Size(max = 8)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 150)
    @Column(name = "enunciado")
    private String enunciado;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "req")
    private boolean req;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoriaPregunta", fetch = FetchType.LAZY)
    private List<AuditoriaPreguntaRelacion> auditoriaPreguntaRelacionList;
    @JoinColumn(name = "id_auditoria_tipo_respuesta", referencedColumnName = "id_auditoria_tipo_respuesta")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AuditoriaTipoRespuesta idAuditoriaTipoRespuesta;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoriaPregunta", fetch = FetchType.LAZY)
    private List<AuditoriaRespuesta> auditoriaRespuestaList;

    public AuditoriaPregunta() {
    }

    public AuditoriaPregunta(Integer idAuditoriaPregunta) {
        this.idAuditoriaPregunta = idAuditoriaPregunta;
    }

    public AuditoriaPregunta(Integer idAuditoriaPregunta, String username, Date creado, int estadoReg) {
        this.idAuditoriaPregunta = idAuditoriaPregunta;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaPregunta() {
        return idAuditoriaPregunta;
    }

    public void setIdAuditoriaPregunta(Integer idAuditoriaPregunta) {
        this.idAuditoriaPregunta = idAuditoriaPregunta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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
    public List<AuditoriaPreguntaRelacion> getAuditoriaPreguntaRelacionList() {
        return auditoriaPreguntaRelacionList;
    }

    public void setAuditoriaPreguntaRelacionList(List<AuditoriaPreguntaRelacion> auditoriaPreguntaRelacionList) {
        this.auditoriaPreguntaRelacionList = auditoriaPreguntaRelacionList;
    }

    public AuditoriaTipoRespuesta getIdAuditoriaTipoRespuesta() {
        return idAuditoriaTipoRespuesta;
    }

    public void setIdAuditoriaTipoRespuesta(AuditoriaTipoRespuesta idAuditoriaTipoRespuesta) {
        this.idAuditoriaTipoRespuesta = idAuditoriaTipoRespuesta;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @XmlTransient
    public List<AuditoriaRespuesta> getAuditoriaRespuestaList() {
        return auditoriaRespuestaList;
    }

    public void setAuditoriaRespuestaList(List<AuditoriaRespuesta> auditoriaRespuestaList) {
        this.auditoriaRespuestaList = auditoriaRespuestaList;
    }

    public boolean isReq() {
        return req;
    }

    public void setReq(boolean req) {
        this.req = req;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaPregunta != null ? idAuditoriaPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaPregunta)) {
            return false;
        }
        AuditoriaPregunta other = (AuditoriaPregunta) object;
        if ((this.idAuditoriaPregunta == null && other.idAuditoriaPregunta != null) || (this.idAuditoriaPregunta != null && !this.idAuditoriaPregunta.equals(other.idAuditoriaPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaPregunta[ idAuditoriaPregunta=" + idAuditoriaPregunta + " ]";
    }
    
}
