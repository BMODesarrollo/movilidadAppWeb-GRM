/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Collection;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "gmo_novedad_tipo_detalles_infrastruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findAll", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g"),
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findByIdGmoNovedadTipoDetInfrastruc", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g WHERE g.idGmoNovedadTipoDetInfrastruc = :idGmoNovedadTipoDetInfrastruc"),
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findByNotifica", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g WHERE g.notifica = :notifica"),
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findByNombre", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findByDescripcion", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findByUsername", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g WHERE g.username = :username"),
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findByCreado", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g WHERE g.creado = :creado"),
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findByModificado", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GmoNovedadTipoDetallesInfrastruc.findByEstadoReg", query = "SELECT g FROM GmoNovedadTipoDetallesInfrastruc g WHERE g.estadoReg = :estadoReg")})
public class GmoNovedadTipoDetallesInfrastruc implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGmoNovedadTipoDetInfrastruc", fetch = FetchType.LAZY)
    private List<GmoNovedadInfrastruc> gmoNovedadInfrastrucList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gmo_novedad_tipo_det_infrastruc")
    private Integer idGmoNovedadTipoDetInfrastruc;
    @Column(name = "notifica")
    private Integer notifica;
    @Lob
    @Size(max = 65535)
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
    @JoinColumn(name = "id_gmo_novedad_tipo_infrastruc", referencedColumnName = "id_gmo_novedad_tipo_infrastruc")
    @ManyToOne(fetch = FetchType.LAZY)
    private GmoNovedadTipoInfrastruc idGmoNovedadTipoInfrastruc;

    public GmoNovedadTipoDetallesInfrastruc() {
    }

    public GmoNovedadTipoDetallesInfrastruc(Integer idGmoNovedadTipoDetInfrastruc) {
        this.idGmoNovedadTipoDetInfrastruc = idGmoNovedadTipoDetInfrastruc;
    }

    public GmoNovedadTipoDetallesInfrastruc(Integer idGmoNovedadTipoDetInfrastruc, String nombre, String descripcion) {
        this.idGmoNovedadTipoDetInfrastruc = idGmoNovedadTipoDetInfrastruc;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdGmoNovedadTipoDetInfrastruc() {
        return idGmoNovedadTipoDetInfrastruc;
    }

    public void setIdGmoNovedadTipoDetInfrastruc(Integer idGmoNovedadTipoDetInfrastruc) {
        this.idGmoNovedadTipoDetInfrastruc = idGmoNovedadTipoDetInfrastruc;
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

    public GmoNovedadTipoInfrastruc getIdGmoNovedadTipoInfrastruc() {
        return idGmoNovedadTipoInfrastruc;
    }

    public void setIdGmoNovedadTipoInfrastruc(GmoNovedadTipoInfrastruc idGmoNovedadTipoInfrastruc) {
        this.idGmoNovedadTipoInfrastruc = idGmoNovedadTipoInfrastruc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGmoNovedadTipoDetInfrastruc != null ? idGmoNovedadTipoDetInfrastruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GmoNovedadTipoDetallesInfrastruc)) {
            return false;
        }
        GmoNovedadTipoDetallesInfrastruc other = (GmoNovedadTipoDetallesInfrastruc) object;
        if ((this.idGmoNovedadTipoDetInfrastruc == null && other.idGmoNovedadTipoDetInfrastruc != null) || (this.idGmoNovedadTipoDetInfrastruc != null && !this.idGmoNovedadTipoDetInfrastruc.equals(other.idGmoNovedadTipoDetInfrastruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GmoNovedadTipoDetallesInfrastruc[ idGmoNovedadTipoDetInfrastruc=" + idGmoNovedadTipoDetInfrastruc + " ]";
    }

    @XmlTransient
    public List<GmoNovedadInfrastruc> getGmoNovedadInfrastrucList() {
        return gmoNovedadInfrastrucList;
    }

    public void setGmoNovedadInfrastrucList(List<GmoNovedadInfrastruc> gmoNovedadInfrastrucList) {
        this.gmoNovedadInfrastrucList = gmoNovedadInfrastrucList;
    }

}
