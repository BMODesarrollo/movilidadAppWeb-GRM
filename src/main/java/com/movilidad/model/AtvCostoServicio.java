/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "atv_costo_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvCostoServicio.findAll", query = "SELECT a FROM AtvCostoServicio a")
    , @NamedQuery(name = "AtvCostoServicio.findByIdAtvCostoServicio", query = "SELECT a FROM AtvCostoServicio a WHERE a.idAtvCostoServicio = :idAtvCostoServicio")
    , @NamedQuery(name = "AtvCostoServicio.findByCosto", query = "SELECT a FROM AtvCostoServicio a WHERE a.costo = :costo")
    , @NamedQuery(name = "AtvCostoServicio.findByDesde", query = "SELECT a FROM AtvCostoServicio a WHERE a.desde = :desde")
    , @NamedQuery(name = "AtvCostoServicio.findByHasta", query = "SELECT a FROM AtvCostoServicio a WHERE a.hasta = :hasta")
    , @NamedQuery(name = "AtvCostoServicio.findByDescripcion", query = "SELECT a FROM AtvCostoServicio a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "AtvCostoServicio.findByUsername", query = "SELECT a FROM AtvCostoServicio a WHERE a.username = :username")
    , @NamedQuery(name = "AtvCostoServicio.findByCreado", query = "SELECT a FROM AtvCostoServicio a WHERE a.creado = :creado")
    , @NamedQuery(name = "AtvCostoServicio.findByModificado", query = "SELECT a FROM AtvCostoServicio a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AtvCostoServicio.findByEstadoReg", query = "SELECT a FROM AtvCostoServicio a WHERE a.estadoReg = :estadoReg")})
public class AtvCostoServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_costo_servicio")
    private Integer idAtvCostoServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costo")
    private int costo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Size(max = 150)
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
    @JoinColumn(name = "id_atv_prestador", referencedColumnName = "id_atv_prestador")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AtvPrestador idAtvPrestador;
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;

    public AtvCostoServicio() {
    }

    public AtvCostoServicio(Integer idAtvCostoServicio) {
        this.idAtvCostoServicio = idAtvCostoServicio;
    }

    public AtvCostoServicio(Integer idAtvCostoServicio, int costo, Date desde, Date hasta) {
        this.idAtvCostoServicio = idAtvCostoServicio;
        this.costo = costo;
        this.desde = desde;
        this.hasta = hasta;
    }

    public Integer getIdAtvCostoServicio() {
        return idAtvCostoServicio;
    }

    public void setIdAtvCostoServicio(Integer idAtvCostoServicio) {
        this.idAtvCostoServicio = idAtvCostoServicio;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
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

    public AtvPrestador getIdAtvPrestador() {
        return idAtvPrestador;
    }

    public void setIdAtvPrestador(AtvPrestador idAtvPrestador) {
        this.idAtvPrestador = idAtvPrestador;
    }

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtvCostoServicio != null ? idAtvCostoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvCostoServicio)) {
            return false;
        }
        AtvCostoServicio other = (AtvCostoServicio) object;
        if ((this.idAtvCostoServicio == null && other.idAtvCostoServicio != null) || (this.idAtvCostoServicio != null && !this.idAtvCostoServicio.equals(other.idAtvCostoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvCostoServicio[ idAtvCostoServicio=" + idAtvCostoServicio + " ]";
    }

}
