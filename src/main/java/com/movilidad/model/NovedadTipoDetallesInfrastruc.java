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
import javax.persistence.JoinColumns;
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
@Table(name = "novedad_tipo_detalles_infrastruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findAll", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n"),
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findByIdNovedadTipoDetInfrastruc", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n WHERE n.idNovedadTipoDetInfrastruc = :idNovedadTipoDetInfrastruc"),
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findByNotifica", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n WHERE n.notifica = :notifica"),
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findByNombre", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n WHERE n.nombre = :nombre"),
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findByDescripcion", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findByUsername", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findByCreado", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findByModificado", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadTipoDetallesInfrastruc.findByEstadoReg", query = "SELECT n FROM NovedadTipoDetallesInfrastruc n WHERE n.estadoReg = :estadoReg")})
public class NovedadTipoDetallesInfrastruc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_tipo_det_infrastruc")
    private Integer idNovedadTipoDetInfrastruc;
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
    @OneToMany(mappedBy = "novedadTipoDetallesInfrastruc", fetch = FetchType.LAZY)
    private List<NovedadInfrastruc> novedadInfrastrucList;
    @JoinColumn(name = "id_novedad_tipo_infrastruc", referencedColumnName = "id_novedad_tipo_infrastruc")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoInfrastruc novedadTipoInfrastruc;

    public NovedadTipoDetallesInfrastruc() {
    }

    public NovedadTipoDetallesInfrastruc(Integer idNovedadTipoDetInfrastruc) {
        this.idNovedadTipoDetInfrastruc = idNovedadTipoDetInfrastruc;
    }

    public NovedadTipoDetallesInfrastruc(Integer idNovedadTipoDetInfrastruc, String nombre, String descripcion) {
        this.idNovedadTipoDetInfrastruc = idNovedadTipoDetInfrastruc;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdNovedadTipoDetInfrastruc() {
        return idNovedadTipoDetInfrastruc;
    }

    public void setIdNovedadTipoDetInfrastruc(Integer idNovedadTipoDetInfrastruc) {
        this.idNovedadTipoDetInfrastruc = idNovedadTipoDetInfrastruc;
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
    public List<NovedadInfrastruc> getNovedadInfrastrucList() {
        return novedadInfrastrucList;
    }

    public void setNovedadInfrastrucList(List<NovedadInfrastruc> novedadInfrastrucList) {
        this.novedadInfrastrucList = novedadInfrastrucList;
    }

    public NovedadTipoInfrastruc getNovedadTipoInfrastruc() {
        return novedadTipoInfrastruc;
    }

    public void setNovedadTipoInfrastruc(NovedadTipoInfrastruc novedadTipoInfrastruc) {
        this.novedadTipoInfrastruc = novedadTipoInfrastruc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadTipoDetInfrastruc != null ? idNovedadTipoDetInfrastruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadTipoDetallesInfrastruc)) {
            return false;
        }
        NovedadTipoDetallesInfrastruc other = (NovedadTipoDetallesInfrastruc) object;
        if ((this.idNovedadTipoDetInfrastruc == null && other.idNovedadTipoDetInfrastruc != null) || (this.idNovedadTipoDetInfrastruc != null && !this.idNovedadTipoDetInfrastruc.equals(other.idNovedadTipoDetInfrastruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadTipoDetallesInfrastruc[ idNovedadTipoDetInfrastruc=" + idNovedadTipoDetInfrastruc + " ]";
    }

    }
