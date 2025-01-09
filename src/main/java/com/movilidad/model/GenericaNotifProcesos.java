/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "generica_notif_procesos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaNotifProcesos.findAll", query = "SELECT g FROM GenericaNotifProcesos g"),
    @NamedQuery(name = "GenericaNotifProcesos.findByIdGenericaNotifProcesos", query = "SELECT g FROM GenericaNotifProcesos g WHERE g.idGenericaNotifProcesos = :idGenericaNotifProcesos"),
    @NamedQuery(name = "GenericaNotifProcesos.findByCodigoProceso", query = "SELECT g FROM GenericaNotifProcesos g WHERE g.codigoProceso = :codigoProceso"),
    @NamedQuery(name = "GenericaNotifProcesos.findByNombreProceso", query = "SELECT g FROM GenericaNotifProcesos g WHERE g.nombreProceso = :nombreProceso"),
    @NamedQuery(name = "GenericaNotifProcesos.findByUsername", query = "SELECT g FROM GenericaNotifProcesos g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaNotifProcesos.findByCreado", query = "SELECT g FROM GenericaNotifProcesos g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaNotifProcesos.findByModificado", query = "SELECT g FROM GenericaNotifProcesos g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaNotifProcesos.findByEstadoReg", query = "SELECT g FROM GenericaNotifProcesos g WHERE g.estadoReg = :estadoReg")})
public class GenericaNotifProcesos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_notif_procesos")
    private Integer idGenericaNotifProcesos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codigo_proceso")
    private String codigoProceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_proceso")
    private String nombreProceso;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "mensaje")
    private String mensaje;
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
    @OneToMany(mappedBy = "idGenericaNotifProcesos", fetch = FetchType.LAZY)
    private List<GenericaTipoDetalles> genericaTipoDetallesList;

    public GenericaNotifProcesos() {
    }

    public GenericaNotifProcesos(Integer idGenericaNotifProcesos) {
        this.idGenericaNotifProcesos = idGenericaNotifProcesos;
    }

    public GenericaNotifProcesos(Integer idGenericaNotifProcesos, String codigoProceso, String nombreProceso, String mensaje, String emails, String username, Date creado, int estadoReg) {
        this.idGenericaNotifProcesos = idGenericaNotifProcesos;
        this.codigoProceso = codigoProceso;
        this.nombreProceso = nombreProceso;
        this.mensaje = mensaje;
        this.emails = emails;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaNotifProcesos() {
        return idGenericaNotifProcesos;
    }

    public void setIdGenericaNotifProcesos(Integer idGenericaNotifProcesos) {
        this.idGenericaNotifProcesos = idGenericaNotifProcesos;
    }

    public String getCodigoProceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    @XmlTransient
    public List<GenericaTipoDetalles> getGenericaTipoDetallesList() {
        return genericaTipoDetallesList;
    }

    public void setGenericaTipoDetallesList(List<GenericaTipoDetalles> genericaTipoDetallesList) {
        this.genericaTipoDetallesList = genericaTipoDetallesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaNotifProcesos != null ? idGenericaNotifProcesos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaNotifProcesos)) {
            return false;
        }
        GenericaNotifProcesos other = (GenericaNotifProcesos) object;
        if ((this.idGenericaNotifProcesos == null && other.idGenericaNotifProcesos != null) || (this.idGenericaNotifProcesos != null && !this.idGenericaNotifProcesos.equals(other.idGenericaNotifProcesos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaNotifProcesos[ idGenericaNotifProcesos=" + idGenericaNotifProcesos + " ]";
    }

}
