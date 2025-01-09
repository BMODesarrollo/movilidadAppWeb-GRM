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
 * @author HP
 */
@Entity
@Table(name = "novedad_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadTipo.findAll", query = "SELECT n FROM NovedadTipo n")
    , @NamedQuery(name = "NovedadTipo.findByIdNovedadTipo", query = "SELECT n FROM NovedadTipo n WHERE n.idNovedadTipo = :idNovedadTipo")
    , @NamedQuery(name = "NovedadTipo.findByNombreTipoNovedad", query = "SELECT n FROM NovedadTipo n WHERE n.nombreTipoNovedad = :nombreTipoNovedad")
    , @NamedQuery(name = "NovedadTipo.findByDescripcionTipoNovedad", query = "SELECT n FROM NovedadTipo n WHERE n.descripcionTipoNovedad = :descripcionTipoNovedad")
    , @NamedQuery(name = "NovedadTipo.findByUsername", query = "SELECT n FROM NovedadTipo n WHERE n.username = :username")
    , @NamedQuery(name = "NovedadTipo.findByCreado", query = "SELECT n FROM NovedadTipo n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadTipo.findByModificado", query = "SELECT n FROM NovedadTipo n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadTipo.findByEstadoReg", query = "SELECT n FROM NovedadTipo n WHERE n.estadoReg = :estadoReg")})
public class NovedadTipo implements Serializable {

    @OneToMany(mappedBy = "idNovedadTipo")
    private List<ChkComponenteFalla> chkComponenteFallaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_tipo")
    private Integer idNovedadTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_novedad")
    private String nombreTipoNovedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion_tipo_novedad")
    private String descripcionTipoNovedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadTipo", fetch = FetchType.LAZY)
    private List<Novedad> novedadList;
    @OneToMany(mappedBy = "idNovedadTipo", fetch = FetchType.LAZY)
    private List<NovedadTipoDetalles> novedadTipoDetallesList;

    public NovedadTipo() {
    }

    public NovedadTipo(Integer idNovedadTipo) {
        this.idNovedadTipo = idNovedadTipo;
    }

    public NovedadTipo(Integer idNovedadTipo, String nombreTipoNovedad, String descripcionTipoNovedad, String username, int estadoReg) {
        this.idNovedadTipo = idNovedadTipo;
        this.nombreTipoNovedad = nombreTipoNovedad;
        this.descripcionTipoNovedad = descripcionTipoNovedad;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadTipo() {
        return idNovedadTipo;
    }

    public void setIdNovedadTipo(Integer idNovedadTipo) {
        this.idNovedadTipo = idNovedadTipo;
    }

    public String getNombreTipoNovedad() {
        return nombreTipoNovedad;
    }

    public void setNombreTipoNovedad(String nombreTipoNovedad) {
        this.nombreTipoNovedad = nombreTipoNovedad;
    }

    public String getDescripcionTipoNovedad() {
        return descripcionTipoNovedad;
    }

    public void setDescripcionTipoNovedad(String descripcionTipoNovedad) {
        this.descripcionTipoNovedad = descripcionTipoNovedad;
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
    public List<Novedad> getNovedadList() {
        return novedadList;
    }

    public void setNovedadList(List<Novedad> novedadList) {
        this.novedadList = novedadList;
    }

    @XmlTransient
    public List<NovedadTipoDetalles> getNovedadTipoDetallesList() {
        return novedadTipoDetallesList;
    }

    public void setNovedadTipoDetallesList(List<NovedadTipoDetalles> novedadTipoDetallesList) {
        this.novedadTipoDetallesList = novedadTipoDetallesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadTipo != null ? idNovedadTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadTipo)) {
            return false;
        }
        NovedadTipo other = (NovedadTipo) object;
        if ((this.idNovedadTipo == null && other.idNovedadTipo != null) || (this.idNovedadTipo != null && !this.idNovedadTipo.equals(other.idNovedadTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadTipo[ idNovedadTipo=" + idNovedadTipo + " ]";
    }

    @XmlTransient
    public List<ChkComponenteFalla> getChkComponenteFallaList() {
        return chkComponenteFallaList;
    }

    public void setChkComponenteFallaList(List<ChkComponenteFalla> chkComponenteFallaList) {
        this.chkComponenteFallaList = chkComponenteFallaList;
    }
    
}
