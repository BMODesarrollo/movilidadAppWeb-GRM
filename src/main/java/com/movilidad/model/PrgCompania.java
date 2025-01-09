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
@Table(name = "prg_compania")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgCompania.findAll", query = "SELECT p FROM PrgCompania p")
    , @NamedQuery(name = "PrgCompania.findByIdPrgCompania", query = "SELECT p FROM PrgCompania p WHERE p.idPrgCompania = :idPrgCompania")
    , @NamedQuery(name = "PrgCompania.findByNombreCompania", query = "SELECT p FROM PrgCompania p WHERE p.nombreCompania = :nombreCompania")
    , @NamedQuery(name = "PrgCompania.findByUsername", query = "SELECT p FROM PrgCompania p WHERE p.username = :username")
    , @NamedQuery(name = "PrgCompania.findByCreado", query = "SELECT p FROM PrgCompania p WHERE p.creado = :creado")
    , @NamedQuery(name = "PrgCompania.findByModificado", query = "SELECT p FROM PrgCompania p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgCompania.findByEstadoReg", query = "SELECT p FROM PrgCompania p WHERE p.estadoReg = :estadoReg")})
public class PrgCompania implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_compania")
    private Integer idPrgCompania;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_compania")
    private String nombreCompania;
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

    public PrgCompania() {
    }

    public PrgCompania(Integer idPrgCompania) {
        this.idPrgCompania = idPrgCompania;
    }

    public PrgCompania(Integer idPrgCompania, String nombreCompania, String username, Date creado, int estadoReg) {
        this.idPrgCompania = idPrgCompania;
        this.nombreCompania = nombreCompania;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgCompania() {
        return idPrgCompania;
    }

    public void setIdPrgCompania(Integer idPrgCompania) {
        this.idPrgCompania = idPrgCompania;
    }

    public String getNombreCompania() {
        return nombreCompania;
    }

    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania = nombreCompania;
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
        hash += (idPrgCompania != null ? idPrgCompania.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgCompania)) {
            return false;
        }
        PrgCompania other = (PrgCompania) object;
        if ((this.idPrgCompania == null && other.idPrgCompania != null) || (this.idPrgCompania != null && !this.idPrgCompania.equals(other.idPrgCompania))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgCompania[ idPrgCompania=" + idPrgCompania + " ]";
    }
    
}
