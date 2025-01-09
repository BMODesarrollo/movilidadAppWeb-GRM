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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "notificacion_proceso_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionProcesoDet.findAll", query = "SELECT n FROM NotificacionProcesoDet n"),
    @NamedQuery(name = "NotificacionProcesoDet.findByIdNotificacionProcesoDet", query = "SELECT n FROM NotificacionProcesoDet n WHERE n.idNotificacionProcesoDet = :idNotificacionProcesoDet"),
    @NamedQuery(name = "NotificacionProcesoDet.findByUsername", query = "SELECT n FROM NotificacionProcesoDet n WHERE n.username = :username"),
    @NamedQuery(name = "NotificacionProcesoDet.findByCreado", query = "SELECT n FROM NotificacionProcesoDet n WHERE n.creado = :creado"),
    @NamedQuery(name = "NotificacionProcesoDet.findByModificado", query = "SELECT n FROM NotificacionProcesoDet n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NotificacionProcesoDet.findByEstadoReg", query = "SELECT n FROM NotificacionProcesoDet n WHERE n.estadoReg = :estadoReg")})
public class NotificacionProcesoDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion_proceso_det")
    private Integer idNotificacionProcesoDet;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "emails")
    private String emails;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_notificacion_proceso", referencedColumnName = "id_notificacion_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotificacionProcesos idNotificacionProceso;

    public NotificacionProcesoDet() {
    }

    public NotificacionProcesoDet(Integer idNotificacionProcesoDet) {
        this.idNotificacionProcesoDet = idNotificacionProcesoDet;
    }

    public NotificacionProcesoDet(Integer idNotificacionProcesoDet, String emails, String username, Date creado, int estadoReg) {
        this.idNotificacionProcesoDet = idNotificacionProcesoDet;
        this.emails = emails;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNotificacionProcesoDet() {
        return idNotificacionProcesoDet;
    }

    public void setIdNotificacionProcesoDet(Integer idNotificacionProcesoDet) {
        this.idNotificacionProcesoDet = idNotificacionProcesoDet;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
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
        hash += (idNotificacionProcesoDet != null ? idNotificacionProcesoDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionProcesoDet)) {
            return false;
        }
        NotificacionProcesoDet other = (NotificacionProcesoDet) object;
        if ((this.idNotificacionProcesoDet == null && other.idNotificacionProcesoDet != null) || (this.idNotificacionProcesoDet != null && !this.idNotificacionProcesoDet.equals(other.idNotificacionProcesoDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NotificacionProcesoDet[ idNotificacionProcesoDet=" + idNotificacionProcesoDet + " ]";
    }
    
}
