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
@Table(name = "notificacion_telegram")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionTelegram.findAll", query = "SELECT n FROM NotificacionTelegram n"),
    @NamedQuery(name = "NotificacionTelegram.findByIdNotificacionTelegram", query = "SELECT n FROM NotificacionTelegram n WHERE n.idNotificacionTelegram = :idNotificacionTelegram"),
    @NamedQuery(name = "NotificacionTelegram.findByToken", query = "SELECT n FROM NotificacionTelegram n WHERE n.token = :token"),
    @NamedQuery(name = "NotificacionTelegram.findByNombreBot", query = "SELECT n FROM NotificacionTelegram n WHERE n.nombreBot = :nombreBot"),
    @NamedQuery(name = "NotificacionTelegram.findByUsername", query = "SELECT n FROM NotificacionTelegram n WHERE n.username = :username"),
    @NamedQuery(name = "NotificacionTelegram.findByCreado", query = "SELECT n FROM NotificacionTelegram n WHERE n.creado = :creado"),
    @NamedQuery(name = "NotificacionTelegram.findByModificado", query = "SELECT n FROM NotificacionTelegram n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NotificacionTelegram.findByEstadoReg", query = "SELECT n FROM NotificacionTelegram n WHERE n.estadoReg = :estadoReg")})
public class NotificacionTelegram implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion_telegram")
    private Integer idNotificacionTelegram;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombre_bot")
    private String nombreBot;
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
    @OneToMany(mappedBy = "idNotificacionTelegram", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificacionTelegramDet> notificacionTelegramDetList;

    public NotificacionTelegram() {
    }

    public NotificacionTelegram(Integer idNotificacionTelegram) {
        this.idNotificacionTelegram = idNotificacionTelegram;
    }

    public NotificacionTelegram(Integer idNotificacionTelegram, String token, String nombreBot) {
        this.idNotificacionTelegram = idNotificacionTelegram;
        this.token = token;
        this.nombreBot = nombreBot;
    }

    public Integer getIdNotificacionTelegram() {
        return idNotificacionTelegram;
    }

    public void setIdNotificacionTelegram(Integer idNotificacionTelegram) {
        this.idNotificacionTelegram = idNotificacionTelegram;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombreBot() {
        return nombreBot;
    }

    public void setNombreBot(String nombreBot) {
        this.nombreBot = nombreBot;
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

    @XmlTransient
    public List<NotificacionTelegramDet> getNotificacionTelegramDetList() {
        return notificacionTelegramDetList;
    }

    public void setNotificacionTelegramDetList(List<NotificacionTelegramDet> notificacionTelegramDetList) {
        this.notificacionTelegramDetList = notificacionTelegramDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacionTelegram != null ? idNotificacionTelegram.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionTelegram)) {
            return false;
        }
        NotificacionTelegram other = (NotificacionTelegram) object;
        if ((this.idNotificacionTelegram == null && other.idNotificacionTelegram != null) || (this.idNotificacionTelegram != null && !this.idNotificacionTelegram.equals(other.idNotificacionTelegram))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NotificacionTelegram[ idNotificacionTelegram=" + idNotificacionTelegram + " ]";
    }

}
