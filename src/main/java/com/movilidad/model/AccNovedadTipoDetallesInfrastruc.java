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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "acc_novedad_tipo_detalles_infrastruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findAll", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByIdAccNovedadTipoDetalleInfrastruc", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.idAccNovedadTipoDetalleInfrastruc = :idAccNovedadTipoDetalleInfrastruc"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByNotifica", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.notifica = :notifica"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByEmails", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.emails = :emails"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByNombre", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByDescripcion", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByUsername", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.username = :username"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByCreado", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByModificado", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccNovedadTipoDetallesInfrastruc.findByEstadoReg", query = "SELECT a FROM AccNovedadTipoDetallesInfrastruc a WHERE a.estadoReg = :estadoReg")})
public class AccNovedadTipoDetallesInfrastruc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_novedad_tipo_detalle_infrastruc")
    private Integer idAccNovedadTipoDetalleInfrastruc;
    @Column(name = "notifica")
    private Integer notifica;
    @Size(max = 200)
    @Column(name = "emails")
    private String emails;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
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
    @OneToMany(mappedBy = "idAccNovedadTipoDetalleInfrastruc", fetch = FetchType.LAZY)
    private List<AccNovedadInfraestruc> accNovedadInfraestrucList;
    @JoinColumn(name = "id_acc_novedad_tipo_infrastruc", referencedColumnName = "id_acc_novedad_tipo_infrastruc")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccNovedadTipoInfrastruc idAccNovedadTipoInfrastruc;

    public AccNovedadTipoDetallesInfrastruc() {
    }

    public AccNovedadTipoDetallesInfrastruc(Integer idAccNovedadTipoDetalleInfrastruc) {
        this.idAccNovedadTipoDetalleInfrastruc = idAccNovedadTipoDetalleInfrastruc;
    }

    public AccNovedadTipoDetallesInfrastruc(Integer idAccNovedadTipoDetalleInfrastruc, String nombre, String descripcion) {
        this.idAccNovedadTipoDetalleInfrastruc = idAccNovedadTipoDetalleInfrastruc;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdAccNovedadTipoDetalleInfrastruc() {
        return idAccNovedadTipoDetalleInfrastruc;
    }

    public void setIdAccNovedadTipoDetalleInfrastruc(Integer idAccNovedadTipoDetalleInfrastruc) {
        this.idAccNovedadTipoDetalleInfrastruc = idAccNovedadTipoDetalleInfrastruc;
    }

    public Integer getNotifica() {
        return notifica;
    }

    public void setNotifica(Integer notifica) {
        this.notifica = notifica;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @XmlTransient
    public List<AccNovedadInfraestruc> getAccNovedadInfraestrucList() {
        return accNovedadInfraestrucList;
    }

    public void setAccNovedadInfraestrucList(List<AccNovedadInfraestruc> accNovedadInfraestrucList) {
        this.accNovedadInfraestrucList = accNovedadInfraestrucList;
    }

    public AccNovedadTipoInfrastruc getIdAccNovedadTipoInfrastruc() {
        return idAccNovedadTipoInfrastruc;
    }

    public void setIdAccNovedadTipoInfrastruc(AccNovedadTipoInfrastruc idAccNovedadTipoInfrastruc) {
        this.idAccNovedadTipoInfrastruc = idAccNovedadTipoInfrastruc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccNovedadTipoDetalleInfrastruc != null ? idAccNovedadTipoDetalleInfrastruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccNovedadTipoDetallesInfrastruc)) {
            return false;
        }
        AccNovedadTipoDetallesInfrastruc other = (AccNovedadTipoDetallesInfrastruc) object;
        if ((this.idAccNovedadTipoDetalleInfrastruc == null && other.idAccNovedadTipoDetalleInfrastruc != null) || (this.idAccNovedadTipoDetalleInfrastruc != null && !this.idAccNovedadTipoDetalleInfrastruc.equals(other.idAccNovedadTipoDetalleInfrastruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccNovedadTipoDetallesInfrastruc[ idAccNovedadTipoDetalleInfrastruc=" + idAccNovedadTipoDetalleInfrastruc + " ]";
    }
    
}
