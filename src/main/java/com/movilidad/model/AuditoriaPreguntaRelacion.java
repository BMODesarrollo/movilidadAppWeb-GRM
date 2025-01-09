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
@Table(name = "auditoria_pregunta_relacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaPreguntaRelacion.findAll", query = "SELECT a FROM AuditoriaPreguntaRelacion a")
    , @NamedQuery(name = "AuditoriaPreguntaRelacion.findByIdAuditoriaPreguntaRelacion", query = "SELECT a FROM AuditoriaPreguntaRelacion a WHERE a.idAuditoriaPreguntaRelacion = :idAuditoriaPreguntaRelacion")
    , @NamedQuery(name = "AuditoriaPreguntaRelacion.findByUsername", query = "SELECT a FROM AuditoriaPreguntaRelacion a WHERE a.username = :username")
    , @NamedQuery(name = "AuditoriaPreguntaRelacion.findByCreado", query = "SELECT a FROM AuditoriaPreguntaRelacion a WHERE a.creado = :creado")
    , @NamedQuery(name = "AuditoriaPreguntaRelacion.findByModificado", query = "SELECT a FROM AuditoriaPreguntaRelacion a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AuditoriaPreguntaRelacion.findByEstadoReg", query = "SELECT a FROM AuditoriaPreguntaRelacion a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaPreguntaRelacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_pregunta_relacion")
    private Integer idAuditoriaPreguntaRelacion;
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
    @JoinColumn(name = "id_auditoria", referencedColumnName = "id_auditoria")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auditoria idAuditoria;
    @JoinColumn(name = "id_auditoria_pregunta", referencedColumnName = "id_auditoria_pregunta")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AuditoriaPregunta idAuditoriaPregunta;

    public AuditoriaPreguntaRelacion() {
    }

    public AuditoriaPreguntaRelacion(Integer idAuditoriaPreguntaRelacion) {
        this.idAuditoriaPreguntaRelacion = idAuditoriaPreguntaRelacion;
    }

    public AuditoriaPreguntaRelacion(Integer idAuditoriaPreguntaRelacion, String username, Date creado, int estadoReg) {
        this.idAuditoriaPreguntaRelacion = idAuditoriaPreguntaRelacion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoriaPreguntaRelacion() {
        return idAuditoriaPreguntaRelacion;
    }

    public void setIdAuditoriaPreguntaRelacion(Integer idAuditoriaPreguntaRelacion) {
        this.idAuditoriaPreguntaRelacion = idAuditoriaPreguntaRelacion;
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

    public Auditoria getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Auditoria idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public AuditoriaPregunta getIdAuditoriaPregunta() {
        return idAuditoriaPregunta;
    }

    public void setIdAuditoriaPregunta(AuditoriaPregunta idAuditoriaPregunta) {
        this.idAuditoriaPregunta = idAuditoriaPregunta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaPreguntaRelacion != null ? idAuditoriaPreguntaRelacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaPreguntaRelacion)) {
            return false;
        }
        AuditoriaPreguntaRelacion other = (AuditoriaPreguntaRelacion) object;
        if ((this.idAuditoriaPreguntaRelacion == null && other.idAuditoriaPreguntaRelacion != null) || (this.idAuditoriaPreguntaRelacion != null && !this.idAuditoriaPreguntaRelacion.equals(other.idAuditoriaPreguntaRelacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaPreguntaRelacion[ idAuditoriaPreguntaRelacion=" + idAuditoriaPreguntaRelacion + " ]";
    }
    
}
