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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "novedad_mtto_tipo_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadMttoTipoDet.findAll", query = "SELECT n FROM NovedadMttoTipoDet n")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByIdNovedadMttoTipoDet", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.idNovedadMttoTipoDet = :idNovedadMttoTipoDet")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByNotifica", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.notifica = :notifica")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByEmails", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.emails = :emails")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByNombre", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.nombre = :nombre")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByDescripcion", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.descripcion = :descripcion")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByUsername", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.username = :username")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByCreado", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByModificado", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadMttoTipoDet.findByEstadoReg", query = "SELECT n FROM NovedadMttoTipoDet n WHERE n.estadoReg = :estadoReg")})
public class NovedadMttoTipoDet implements Serializable {

    @OneToMany(mappedBy = "idNovedadMttoTipoDet", fetch = FetchType.LAZY)
    private List<NovedadMtto> novedadMttoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_mtto_tipo_det")
    private Integer idNovedadMttoTipoDet;
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
    @JoinColumn(name = "id_novedad_mtto_tipo", referencedColumnName = "id_novedad_mtto_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadMttoTipo idNovedadMttoTipo;

    public NovedadMttoTipoDet() {
    }

    public NovedadMttoTipoDet(Integer idNovedadMttoTipoDet) {
        this.idNovedadMttoTipoDet = idNovedadMttoTipoDet;
    }

    public NovedadMttoTipoDet(Integer idNovedadMttoTipoDet, String nombre, String descripcion) {
        this.idNovedadMttoTipoDet = idNovedadMttoTipoDet;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdNovedadMttoTipoDet() {
        return idNovedadMttoTipoDet;
    }

    public void setIdNovedadMttoTipoDet(Integer idNovedadMttoTipoDet) {
        this.idNovedadMttoTipoDet = idNovedadMttoTipoDet;
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

    public NovedadMttoTipo getIdNovedadMttoTipo() {
        return idNovedadMttoTipo;
    }

    public void setIdNovedadMttoTipo(NovedadMttoTipo idNovedadMttoTipo) {
        this.idNovedadMttoTipo = idNovedadMttoTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadMttoTipoDet != null ? idNovedadMttoTipoDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadMttoTipoDet)) {
            return false;
        }
        NovedadMttoTipoDet other = (NovedadMttoTipoDet) object;
        if ((this.idNovedadMttoTipoDet == null && other.idNovedadMttoTipoDet != null) || (this.idNovedadMttoTipoDet != null && !this.idNovedadMttoTipoDet.equals(other.idNovedadMttoTipoDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadMttoTipoDet[ idNovedadMttoTipoDet=" + idNovedadMttoTipoDet + " ]";
    }

    @XmlTransient
    public List<NovedadMtto> getNovedadMttoList() {
        return novedadMttoList;
    }

    public void setNovedadMttoList(List<NovedadMtto> novedadMttoList) {
        this.novedadMttoList = novedadMttoList;
    }

}
