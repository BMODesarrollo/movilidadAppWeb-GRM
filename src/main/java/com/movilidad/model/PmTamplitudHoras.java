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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author HP
 */
@Entity
@Table(name = "pm_tamplitud_horas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmTamplitudHoras.findAll", query = "SELECT p FROM PmTamplitudHoras p")
    , @NamedQuery(name = "PmTamplitudHoras.findByIdPmTamplitudHoras", query = "SELECT p FROM PmTamplitudHoras p WHERE p.idPmTamplitudHoras = :idPmTamplitudHoras")
    , @NamedQuery(name = "PmTamplitudHoras.findByDesde", query = "SELECT p FROM PmTamplitudHoras p WHERE p.desde = :desde")
    , @NamedQuery(name = "PmTamplitudHoras.findByHasta", query = "SELECT p FROM PmTamplitudHoras p WHERE p.hasta = :hasta")
    , @NamedQuery(name = "PmTamplitudHoras.findByNroHoras", query = "SELECT p FROM PmTamplitudHoras p WHERE p.nroHoras = :nroHoras")
    , @NamedQuery(name = "PmTamplitudHoras.findByUsername", query = "SELECT p FROM PmTamplitudHoras p WHERE p.username = :username")
    , @NamedQuery(name = "PmTamplitudHoras.findByCreado", query = "SELECT p FROM PmTamplitudHoras p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmTamplitudHoras.findByModificado", query = "SELECT p FROM PmTamplitudHoras p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmTamplitudHoras.findByEstadoReg", query = "SELECT p FROM PmTamplitudHoras p WHERE p.estadoReg = :estadoReg")})
public class PmTamplitudHoras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_tamplitud_horas")
    private Integer idPmTamplitudHoras;
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
    @Column(name = "nro_horas")
    private String nroHoras;
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

    public PmTamplitudHoras() {
    }

    public PmTamplitudHoras(Integer idPmTamplitudHoras) {
        this.idPmTamplitudHoras = idPmTamplitudHoras;
    }

    public PmTamplitudHoras(Integer idPmTamplitudHoras, Date desde, Date hasta, String nroHoras, String username, Date creado, int estadoReg) {
        this.idPmTamplitudHoras = idPmTamplitudHoras;
        this.desde = desde;
        this.hasta = hasta;
        this.nroHoras = nroHoras;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPmTamplitudHoras() {
        return idPmTamplitudHoras;
    }

    public void setIdPmTamplitudHoras(Integer idPmTamplitudHoras) {
        this.idPmTamplitudHoras = idPmTamplitudHoras;
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

    public String getNroHoras() {
        return nroHoras;
    }

    public void setNroHoras(String nroHoras) {
        this.nroHoras = nroHoras;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmTamplitudHoras != null ? idPmTamplitudHoras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmTamplitudHoras)) {
            return false;
        }
        PmTamplitudHoras other = (PmTamplitudHoras) object;
        if ((this.idPmTamplitudHoras == null && other.idPmTamplitudHoras != null) || (this.idPmTamplitudHoras != null && !this.idPmTamplitudHoras.equals(other.idPmTamplitudHoras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmTamplitudHoras[ idPmTamplitudHoras=" + idPmTamplitudHoras + " ]";
    }
    
}
