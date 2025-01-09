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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "notificacion_template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionTemplate.findAll", query = "SELECT n FROM NotificacionTemplate n"),
    @NamedQuery(name = "NotificacionTemplate.findByIdNotificacionTemplate", query = "SELECT n FROM NotificacionTemplate n WHERE n.idNotificacionTemplate = :idNotificacionTemplate"),
    @NamedQuery(name = "NotificacionTemplate.findByTemplate", query = "SELECT n FROM NotificacionTemplate n WHERE n.template = :template"),
    @NamedQuery(name = "NotificacionTemplate.findByPath", query = "SELECT n FROM NotificacionTemplate n WHERE n.path = :path"),
    @NamedQuery(name = "NotificacionTemplate.findByUsername", query = "SELECT n FROM NotificacionTemplate n WHERE n.username = :username"),
    @NamedQuery(name = "NotificacionTemplate.findByCreado", query = "SELECT n FROM NotificacionTemplate n WHERE n.creado = :creado"),
    @NamedQuery(name = "NotificacionTemplate.findByModificado", query = "SELECT n FROM NotificacionTemplate n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NotificacionTemplate.findByEstadoReg", query = "SELECT n FROM NotificacionTemplate n WHERE n.estadoReg = :estadoReg")})
public class NotificacionTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion_template")
    private Integer idNotificacionTemplate;
    @Size(max = 45)
    @Column(name = "template")
    private String template;
    @Size(max = 150)
    @Column(name = "path")
    private String path;
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

    public NotificacionTemplate() {
    }

    public NotificacionTemplate(Integer idNotificacionTemplate) {
        this.idNotificacionTemplate = idNotificacionTemplate;
    }

    public NotificacionTemplate(Integer idNotificacionTemplate, String username, Date creado, int estadoReg) {
        this.idNotificacionTemplate = idNotificacionTemplate;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNotificacionTemplate() {
        return idNotificacionTemplate;
    }

    public void setIdNotificacionTemplate(Integer idNotificacionTemplate) {
        this.idNotificacionTemplate = idNotificacionTemplate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacionTemplate != null ? idNotificacionTemplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionTemplate)) {
            return false;
        }
        NotificacionTemplate other = (NotificacionTemplate) object;
        if ((this.idNotificacionTemplate == null && other.idNotificacionTemplate != null) || (this.idNotificacionTemplate != null && !this.idNotificacionTemplate.equals(other.idNotificacionTemplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NotificacionTemplate[ idNotificacionTemplate=" + idNotificacionTemplate + " ]";
    }
    
}
