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
@Table(name = "novedad_tipo_detalles_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadTipoDetallesCab.findAll", query = "SELECT n FROM NovedadTipoDetallesCab n"),
    @NamedQuery(name = "NovedadTipoDetallesCab.findByIdNovedadTipoDetCab", query = "SELECT n FROM NovedadTipoDetallesCab n WHERE n.idNovedadTipoDetCab = :idNovedadTipoDetCab"),
    @NamedQuery(name = "NovedadTipoDetallesCab.findByNotifica", query = "SELECT n FROM NovedadTipoDetallesCab n WHERE n.notifica = :notifica"),
    @NamedQuery(name = "NovedadTipoDetallesCab.findByNombre", query = "SELECT n FROM NovedadTipoDetallesCab n WHERE n.nombre = :nombre"),
    @NamedQuery(name = "NovedadTipoDetallesCab.findByDescripcion", query = "SELECT n FROM NovedadTipoDetallesCab n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NovedadTipoDetallesCab.findByUsername", query = "SELECT n FROM NovedadTipoDetallesCab n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadTipoDetallesCab.findByCreado", query = "SELECT n FROM NovedadTipoDetallesCab n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadTipoDetallesCab.findByModificado", query = "SELECT n FROM NovedadTipoDetallesCab n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadTipoDetallesCab.findByEstadoReg", query = "SELECT n FROM NovedadTipoDetallesCab n WHERE n.estadoReg = :estadoReg")})
public class NovedadTipoDetallesCab implements Serializable {
    @OneToMany(mappedBy = "idNovedadTipoDetCab", fetch = FetchType.LAZY)
    private List<NovedadCab> novedadCabList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_tipo_det_cab")
    private Integer idNovedadTipoDetCab;
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
    @Lob
    @Size(min = 1, max = 65535)
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @JoinColumn(name = "id_novedad_tipo_cab", referencedColumnName = "id_novedad_tipo_cab")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoCab idNovedadTipoCab;

    public NovedadTipoDetallesCab() {
    }

    public NovedadTipoDetallesCab(Integer idNovedadTipoDetCab) {
        this.idNovedadTipoDetCab = idNovedadTipoDetCab;
    }

    public NovedadTipoDetallesCab(Integer idNovedadTipoDetCab, String nombre, String descripcion) {
        this.idNovedadTipoDetCab = idNovedadTipoDetCab;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public NovedadTipoDetallesCab(Integer idNovedadTipoDetCab, String nombre, String descripcion, int estadoReg) {
        this.idNovedadTipoDetCab = idNovedadTipoDetCab;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadTipoDetCab() {
        return idNovedadTipoDetCab;
    }

    public void setIdNovedadTipoDetCab(Integer idNovedadTipoDetCab) {
        this.idNovedadTipoDetCab = idNovedadTipoDetCab;
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

    @XmlTransient
    public List<NovedadCab> getNovedadCabList() {
        return novedadCabList;
    }

    public void setNovedadCabList(List<NovedadCab> novedadCabList) {
        this.novedadCabList = novedadCabList;
    }

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public NovedadTipoCab getIdNovedadTipoCab() {
        return idNovedadTipoCab;
    }

    public void setIdNovedadTipoCab(NovedadTipoCab idNovedadTipoCab) {
        this.idNovedadTipoCab = idNovedadTipoCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadTipoDetCab != null ? idNovedadTipoDetCab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadTipoDetallesCab)) {
            return false;
        }
        NovedadTipoDetallesCab other = (NovedadTipoDetallesCab) object;
        if ((this.idNovedadTipoDetCab == null && other.idNovedadTipoDetCab != null) || (this.idNovedadTipoDetCab != null && !this.idNovedadTipoDetCab.equals(other.idNovedadTipoDetCab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadTipoDetallesCab[ idNovedadTipoDetCab=" + idNovedadTipoDetCab + " ]";
    }

    }
