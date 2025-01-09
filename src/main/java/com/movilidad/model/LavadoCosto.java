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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "lavado_costo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LavadoCosto.findAll", query = "SELECT l FROM LavadoCosto l"),
    @NamedQuery(name = "LavadoCosto.findByIdLavadoCosto", query = "SELECT l FROM LavadoCosto l WHERE l.idLavadoCosto = :idLavadoCosto"),
    @NamedQuery(name = "LavadoCosto.findByCosto", query = "SELECT l FROM LavadoCosto l WHERE l.costo = :costo"),
    @NamedQuery(name = "LavadoCosto.findByDesde", query = "SELECT l FROM LavadoCosto l WHERE l.desde = :desde"),
    @NamedQuery(name = "LavadoCosto.findByHasta", query = "SELECT l FROM LavadoCosto l WHERE l.hasta = :hasta"),
    @NamedQuery(name = "LavadoCosto.findByUsername", query = "SELECT l FROM LavadoCosto l WHERE l.username = :username"),
    @NamedQuery(name = "LavadoCosto.findByCreado", query = "SELECT l FROM LavadoCosto l WHERE l.creado = :creado"),
    @NamedQuery(name = "LavadoCosto.findByModificado", query = "SELECT l FROM LavadoCosto l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "LavadoCosto.findByEstadoReg", query = "SELECT l FROM LavadoCosto l WHERE l.estadoReg = :estadoReg")})
public class LavadoCosto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado_costo")
    private Integer idLavadoCosto;
    @Column(name = "costo")
    private Integer costo;
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
    @JoinColumn(name = "id_lavado_contratista", referencedColumnName = "id_lavado_contratista")
    @ManyToOne(fetch = FetchType.LAZY)
    private LavadoContratista idLavadoContratista;
    @JoinColumn(name = "id_lavado_tipo_servicio", referencedColumnName = "id_lavado_tipo_servicio")
    @ManyToOne(fetch = FetchType.LAZY)
    private LavadoTipoServicio idLavadoTipoServicio;

    public LavadoCosto() {
    }

    public LavadoCosto(Integer idLavadoCosto) {
        this.idLavadoCosto = idLavadoCosto;
    }

    public LavadoCosto(Integer idLavadoCosto, Date desde, Date hasta, String username, int estadoReg) {
        this.idLavadoCosto = idLavadoCosto;
        this.desde = desde;
        this.hasta = hasta;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdLavadoCosto() {
        return idLavadoCosto;
    }

    public void setIdLavadoCosto(Integer idLavadoCosto) {
        this.idLavadoCosto = idLavadoCosto;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
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

    public LavadoContratista getIdLavadoContratista() {
        return idLavadoContratista;
    }

    public void setIdLavadoContratista(LavadoContratista idLavadoContratista) {
        this.idLavadoContratista = idLavadoContratista;
    }

    public LavadoTipoServicio getIdLavadoTipoServicio() {
        return idLavadoTipoServicio;
    }

    public void setIdLavadoTipoServicio(LavadoTipoServicio idLavadoTipoServicio) {
        this.idLavadoTipoServicio = idLavadoTipoServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLavadoCosto != null ? idLavadoCosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavadoCosto)) {
            return false;
        }
        LavadoCosto other = (LavadoCosto) object;
        if ((this.idLavadoCosto == null && other.idLavadoCosto != null) || (this.idLavadoCosto != null && !this.idLavadoCosto.equals(other.idLavadoCosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.LavadoCosto[ idLavadoCosto=" + idLavadoCosto + " ]";
    }

}
