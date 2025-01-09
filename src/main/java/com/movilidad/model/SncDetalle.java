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
 * @author HP
 */
@Entity
@Table(name = "snc_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SncDetalle.findAll", query = "SELECT s FROM SncDetalle s")
    , @NamedQuery(name = "SncDetalle.findByIdSncDetalle", query = "SELECT s FROM SncDetalle s WHERE s.idSncDetalle = :idSncDetalle")
    , @NamedQuery(name = "SncDetalle.findByCodigo", query = "SELECT s FROM SncDetalle s WHERE s.codigo = :codigo")
    , @NamedQuery(name = "SncDetalle.findByNombre", query = "SELECT s FROM SncDetalle s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "SncDetalle.findByDescripcion", query = "SELECT s FROM SncDetalle s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "SncDetalle.findByUsername", query = "SELECT s FROM SncDetalle s WHERE s.username = :username")
    , @NamedQuery(name = "SncDetalle.findByCreado", query = "SELECT s FROM SncDetalle s WHERE s.creado = :creado")
    , @NamedQuery(name = "SncDetalle.findByModificado", query = "SELECT s FROM SncDetalle s WHERE s.modificado = :modificado")
    , @NamedQuery(name = "SncDetalle.findByEstadoReg", query = "SELECT s FROM SncDetalle s WHERE s.estadoReg = :estadoReg")})
public class SncDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_snc_detalle")
    private Integer idSncDetalle;
    @Size(max = 6)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_snc_area", referencedColumnName = "id_snc_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SncArea idSncArea;
    @JoinColumn(name = "id_snc_correcion", referencedColumnName = "id_snc_correcion")
    @ManyToOne(fetch = FetchType.LAZY)
    private SncCorrecion idSncCorrecion;
    @JoinColumn(name = "id_snc_tipo", referencedColumnName = "id_snc_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SncTipo idSncTipo;
    @OneToMany(mappedBy = "idSncDetalle", fetch = FetchType.LAZY)
    private List<NovedadTipoDetalles> novedadTipoDetallesList;

    public SncDetalle() {
    }

    public SncDetalle(Integer idSncDetalle) {
        this.idSncDetalle = idSncDetalle;
    }

    public SncDetalle(Integer idSncDetalle, String nombre, String username, Date creado, int estadoReg) {
        this.idSncDetalle = idSncDetalle;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSncDetalle() {
        return idSncDetalle;
    }

    public void setIdSncDetalle(Integer idSncDetalle) {
        this.idSncDetalle = idSncDetalle;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public SncArea getIdSncArea() {
        return idSncArea;
    }

    public void setIdSncArea(SncArea idSncArea) {
        this.idSncArea = idSncArea;
    }

    public SncCorrecion getIdSncCorrecion() {
        return idSncCorrecion;
    }

    public void setIdSncCorrecion(SncCorrecion idSncCorrecion) {
        this.idSncCorrecion = idSncCorrecion;
    }

    public SncTipo getIdSncTipo() {
        return idSncTipo;
    }

    public void setIdSncTipo(SncTipo idSncTipo) {
        this.idSncTipo = idSncTipo;
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
        hash += (idSncDetalle != null ? idSncDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SncDetalle)) {
            return false;
        }
        SncDetalle other = (SncDetalle) object;
        if ((this.idSncDetalle == null && other.idSncDetalle != null) || (this.idSncDetalle != null && !this.idSncDetalle.equals(other.idSncDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SncDetalle[ idSncDetalle=" + idSncDetalle + " ]";
    }
    
}
