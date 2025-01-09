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
import javax.persistence.Lob;
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
 * @author HP
 */
@Entity
@Table(name = "multa_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MultaSeguimiento.findAll", query = "SELECT m FROM MultaSeguimiento m")
    , @NamedQuery(name = "MultaSeguimiento.findByIdMultaSeguimiento", query = "SELECT m FROM MultaSeguimiento m WHERE m.idMultaSeguimiento = :idMultaSeguimiento")
    , @NamedQuery(name = "MultaSeguimiento.findByFecha", query = "SELECT m FROM MultaSeguimiento m WHERE m.fecha = :fecha")
    , @NamedQuery(name = "MultaSeguimiento.findByUsername", query = "SELECT m FROM MultaSeguimiento m WHERE m.username = :username")
    , @NamedQuery(name = "MultaSeguimiento.findByCreado", query = "SELECT m FROM MultaSeguimiento m WHERE m.creado = :creado")
    , @NamedQuery(name = "MultaSeguimiento.findByModificado", query = "SELECT m FROM MultaSeguimiento m WHERE m.modificado = :modificado")
    , @NamedQuery(name = "MultaSeguimiento.findByEstadoReg", query = "SELECT m FROM MultaSeguimiento m WHERE m.estadoReg = :estadoReg")})
public class MultaSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa_seguimiento")
    private Integer idMultaSeguimiento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "seguimiento")
    private String seguimiento;
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
    @JoinColumn(name = "id_multa", referencedColumnName = "id_multa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Multa idMulta;

    public MultaSeguimiento() {
    }

    public MultaSeguimiento(Integer idMultaSeguimiento) {
        this.idMultaSeguimiento = idMultaSeguimiento;
    }

    public MultaSeguimiento(Integer idMultaSeguimiento, String seguimiento, String username, Date creado, int estadoReg) {
        this.idMultaSeguimiento = idMultaSeguimiento;
        this.seguimiento = seguimiento;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMultaSeguimiento() {
        return idMultaSeguimiento;
    }

    public void setIdMultaSeguimiento(Integer idMultaSeguimiento) {
        this.idMultaSeguimiento = idMultaSeguimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
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

    public Multa getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Multa idMulta) {
        this.idMulta = idMulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultaSeguimiento != null ? idMultaSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MultaSeguimiento)) {
            return false;
        }
        MultaSeguimiento other = (MultaSeguimiento) object;
        if ((this.idMultaSeguimiento == null && other.idMultaSeguimiento != null) || (this.idMultaSeguimiento != null && !this.idMultaSeguimiento.equals(other.idMultaSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MultaSeguimiento[ idMultaSeguimiento=" + idMultaSeguimiento + " ]";
    }
    
}
