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
@Table(name = "mtto_dias_fs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MttoDiasFs.findAll", query = "SELECT m FROM MttoDiasFs m")
    , @NamedQuery(name = "MttoDiasFs.findByIdMttoDiasFs", query = "SELECT m FROM MttoDiasFs m WHERE m.idMttoDiasFs = :idMttoDiasFs")
    , @NamedQuery(name = "MttoDiasFs.findByDias", query = "SELECT m FROM MttoDiasFs m WHERE m.dias = :dias")
    , @NamedQuery(name = "MttoDiasFs.findByUsername", query = "SELECT m FROM MttoDiasFs m WHERE m.username = :username")
    , @NamedQuery(name = "MttoDiasFs.findByCreado", query = "SELECT m FROM MttoDiasFs m WHERE m.creado = :creado")
    , @NamedQuery(name = "MttoDiasFs.findByModificado", query = "SELECT m FROM MttoDiasFs m WHERE m.modificado = :modificado")
    , @NamedQuery(name = "MttoDiasFs.findByEstadoReg", query = "SELECT m FROM MttoDiasFs m WHERE m.estadoReg = :estadoReg")})
public class MttoDiasFs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mtto_dias_fs")
    private Integer idMttoDiasFs;
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

    public MttoDiasFs() {
    }

    public MttoDiasFs(Integer idMttoDiasFs) {
        this.idMttoDiasFs = idMttoDiasFs;
    }

    public Integer getIdMttoDiasFs() {
        return idMttoDiasFs;
    }

    public void setIdMttoDiasFs(Integer idMttoDiasFs) {
        this.idMttoDiasFs = idMttoDiasFs;
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
        hash += (idMttoDiasFs != null ? idMttoDiasFs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MttoDiasFs)) {
            return false;
        }
        MttoDiasFs other = (MttoDiasFs) object;
        if ((this.idMttoDiasFs == null && other.idMttoDiasFs != null) || (this.idMttoDiasFs != null && !this.idMttoDiasFs.equals(other.idMttoDiasFs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MttoDiasFs[ idMttoDiasFs=" + idMttoDiasFs + " ]";
    }
    
}
