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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "notificacion_telegram_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionTelegramDet.findAll", query = "SELECT n FROM NotificacionTelegramDet n"),
    @NamedQuery(name = "NotificacionTelegramDet.findByIdNotificacionTelegramDet", query = "SELECT n FROM NotificacionTelegramDet n WHERE n.idNotificacionTelegramDet = :idNotificacionTelegramDet"),
    @NamedQuery(name = "NotificacionTelegramDet.findByChatId", query = "SELECT n FROM NotificacionTelegramDet n WHERE n.chatId = :chatId"),
    @NamedQuery(name = "NotificacionTelegramDet.findByUsername", query = "SELECT n FROM NotificacionTelegramDet n WHERE n.username = :username"),
    @NamedQuery(name = "NotificacionTelegramDet.findByCreado", query = "SELECT n FROM NotificacionTelegramDet n WHERE n.creado = :creado"),
    @NamedQuery(name = "NotificacionTelegramDet.findByModificado", query = "SELECT n FROM NotificacionTelegramDet n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NotificacionTelegramDet.findByEstadoReg", query = "SELECT n FROM NotificacionTelegramDet n WHERE n.estadoReg = :estadoReg")})
public class NotificacionTelegramDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion_telegram_det")
    private Integer idNotificacionTelegramDet;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "chat_id")
    private String chatId;
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
    @JoinColumn(name = "id_notificacion_telegram", referencedColumnName = "id_notificacion_telegram")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotificacionTelegram idNotificacionTelegram;

    public NotificacionTelegramDet() {
    }

    public NotificacionTelegramDet(Integer idNotificacionTelegramDet) {
        this.idNotificacionTelegramDet = idNotificacionTelegramDet;
    }

    public NotificacionTelegramDet(Integer idNotificacionTelegramDet, String chatId, String username, Date creado, int estadoReg) {
        this.idNotificacionTelegramDet = idNotificacionTelegramDet;
        this.chatId = chatId;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNotificacionTelegramDet() {
        return idNotificacionTelegramDet;
    }

    public void setIdNotificacionTelegramDet(Integer idNotificacionTelegramDet) {
        this.idNotificacionTelegramDet = idNotificacionTelegramDet;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
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

    public NotificacionTelegram getIdNotificacionTelegram() {
        return idNotificacionTelegram;
    }

    public void setIdNotificacionTelegram(NotificacionTelegram idNotificacionTelegram) {
        this.idNotificacionTelegram = idNotificacionTelegram;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacionTelegramDet != null ? idNotificacionTelegramDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionTelegramDet)) {
            return false;
        }
        NotificacionTelegramDet other = (NotificacionTelegramDet) object;
        if ((this.idNotificacionTelegramDet == null && other.idNotificacionTelegramDet != null) || (this.idNotificacionTelegramDet != null && !this.idNotificacionTelegramDet.equals(other.idNotificacionTelegramDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NotificacionTelegramDet[ idNotificacionTelegramDet=" + idNotificacionTelegramDet + " ]";
    }
    
}
