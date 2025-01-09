/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "notificacion_correo_conf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionCorreoConf.findAll", query = "SELECT n FROM NotificacionCorreoConf n"),
    @NamedQuery(name = "NotificacionCorreoConf.findByIdNotificacionCorreoConf", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.idNotificacionCorreoConf = :idNotificacionCorreoConf"),
    @NamedQuery(name = "NotificacionCorreoConf.findBySmtpServer", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.smtpServer = :smtpServer"),
    @NamedQuery(name = "NotificacionCorreoConf.findByPuerto", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.puerto = :puerto"),
    @NamedQuery(name = "NotificacionCorreoConf.findBySeguridad", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.seguridad = :seguridad"),
    @NamedQuery(name = "NotificacionCorreoConf.findByAuth", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.auth = :auth"),
    @NamedQuery(name = "NotificacionCorreoConf.findByRemitente", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.remitente = :remitente"),
    @NamedQuery(name = "NotificacionCorreoConf.findByEmail", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.email = :email"),
    @NamedQuery(name = "NotificacionCorreoConf.findByPassword", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.password = :password"),
    @NamedQuery(name = "NotificacionCorreoConf.findByCc1", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.cc1 = :cc1"),
    @NamedQuery(name = "NotificacionCorreoConf.findByCc2", query = "SELECT n FROM NotificacionCorreoConf n WHERE n.cc2 = :cc2")})
public class NotificacionCorreoConf implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion_correo_conf")
    private Integer idNotificacionCorreoConf;
    @Size(max = 150)
    @Column(name = "smtpServer")
    private String smtpServer;
    @Column(name = "puerto")
    private Integer puerto;
    @Column(name = "seguridad")
    private Integer seguridad;
    @Column(name = "auth")
    private Integer auth;
    @Size(max = 100)
    @Column(name = "remitente")
    private String remitente;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 150)
    @Column(name = "email")
    private String email;
    @Size(max = 150)
    @Column(name = "password")
    private String password;
    @Size(max = 150)
    @Column(name = "cc1")
    private String cc1;
    @Size(max = 150)
    @Column(name = "cc2")
    private String cc2;

    public NotificacionCorreoConf() {
    }

    public NotificacionCorreoConf(Integer idNotificacionCorreoConf) {
        this.idNotificacionCorreoConf = idNotificacionCorreoConf;
    }

    public Integer getIdNotificacionCorreoConf() {
        return idNotificacionCorreoConf;
    }

    public void setIdNotificacionCorreoConf(Integer idNotificacionCorreoConf) {
        this.idNotificacionCorreoConf = idNotificacionCorreoConf;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

    public Integer getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(Integer seguridad) {
        this.seguridad = seguridad;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCc1() {
        return cc1;
    }

    public void setCc1(String cc1) {
        this.cc1 = cc1;
    }

    public String getCc2() {
        return cc2;
    }

    public void setCc2(String cc2) {
        this.cc2 = cc2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacionCorreoConf != null ? idNotificacionCorreoConf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionCorreoConf)) {
            return false;
        }
        NotificacionCorreoConf other = (NotificacionCorreoConf) object;
        if ((this.idNotificacionCorreoConf == null && other.idNotificacionCorreoConf != null) || (this.idNotificacionCorreoConf != null && !this.idNotificacionCorreoConf.equals(other.idNotificacionCorreoConf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NotificacionCorreoConf[ idNotificacionCorreoConf=" + idNotificacionCorreoConf + " ]";
    }
    
}
