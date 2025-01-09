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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "acc_reincidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccReincidencia.findAll", query = "SELECT a FROM AccReincidencia a")
    , @NamedQuery(name = "AccReincidencia.findByIdAccReincidencia", query = "SELECT a FROM AccReincidencia a WHERE a.idAccReincidencia = :idAccReincidencia")
    , @NamedQuery(name = "AccReincidencia.findByDias", query = "SELECT a FROM AccReincidencia a WHERE a.dias = :dias")
    , @NamedQuery(name = "AccReincidencia.findByUsername", query = "SELECT a FROM AccReincidencia a WHERE a.username = :username")
    , @NamedQuery(name = "AccReincidencia.findByCreado", query = "SELECT a FROM AccReincidencia a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccReincidencia.findByModificado", query = "SELECT a FROM AccReincidencia a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccReincidencia.findByEstadoReg", query = "SELECT a FROM AccReincidencia a WHERE a.estadoReg = :estadoReg")})
public class AccReincidencia implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_reincidencia")
    private Integer idAccReincidencia;
    @Column(name = "dias")
    private Integer dias;
    @Lob
    @Size(max = 65535)
    @Column(name = "emails")
    private String emails;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_notificacion_proceso", referencedColumnName = "id_notificacion_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotificacionProcesos idNotificacionProceso;

    public AccReincidencia() {
    }

    public AccReincidencia(Integer idAccReincidencia) {
        this.idAccReincidencia = idAccReincidencia;
    }

    public Integer getIdAccReincidencia() {
        return idAccReincidencia;
    }

    public void setIdAccReincidencia(Integer idAccReincidencia) {
        this.idAccReincidencia = idAccReincidencia;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public NotificacionProcesos getIdNotificacionProceso() {
        return idNotificacionProceso;
    }

    public void setIdNotificacionProceso(NotificacionProcesos idNotificacionProceso) {
        this.idNotificacionProceso = idNotificacionProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccReincidencia != null ? idAccReincidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccReincidencia)) {
            return false;
        }
        AccReincidencia other = (AccReincidencia) object;
        if ((this.idAccReincidencia == null && other.idAccReincidencia != null) || (this.idAccReincidencia != null && !this.idAccReincidencia.equals(other.idAccReincidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccReincidencia[ idAccReincidencia=" + idAccReincidencia + " ]";
    }

}
