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
import javax.persistence.Lob;
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
@Table(name = "acc_tipo_costos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccTipoCostos.findAll", query = "SELECT a FROM AccTipoCostos a")
    , @NamedQuery(name = "AccTipoCostos.findByIdAccTipoCostos", query = "SELECT a FROM AccTipoCostos a WHERE a.idAccTipoCostos = :idAccTipoCostos")
    , @NamedQuery(name = "AccTipoCostos.findByTipoCostos", query = "SELECT a FROM AccTipoCostos a WHERE a.tipoCostos = :tipoCostos")
    , @NamedQuery(name = "AccTipoCostos.findByDirecto", query = "SELECT a FROM AccTipoCostos a WHERE a.directo = :directo")
    , @NamedQuery(name = "AccTipoCostos.findByUsername", query = "SELECT a FROM AccTipoCostos a WHERE a.username = :username")
    , @NamedQuery(name = "AccTipoCostos.findByCreado", query = "SELECT a FROM AccTipoCostos a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccTipoCostos.findByModificado", query = "SELECT a FROM AccTipoCostos a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccTipoCostos.findByEstadoReg", query = "SELECT a FROM AccTipoCostos a WHERE a.estadoReg = :estadoReg")})
public class AccTipoCostos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_tipo_costos")
    private Integer idAccTipoCostos;
    @Size(max = 60)
    @Column(name = "tipo_costos")
    private String tipoCostos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "directo")
    private int directo;
    @Lob
    @Size(max = 65535)
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
    @OneToMany(mappedBy = "idAccTipoCostos", fetch = FetchType.LAZY)
    private List<AccidenteCostos> accidenteCostosList;

    public AccTipoCostos() {
    }

    public AccTipoCostos(Integer idAccTipoCostos) {
        this.idAccTipoCostos = idAccTipoCostos;
    }

    public AccTipoCostos(Integer idAccTipoCostos, int directo) {
        this.idAccTipoCostos = idAccTipoCostos;
        this.directo = directo;
    }

    public Integer getIdAccTipoCostos() {
        return idAccTipoCostos;
    }

    public void setIdAccTipoCostos(Integer idAccTipoCostos) {
        this.idAccTipoCostos = idAccTipoCostos;
    }

    public String getTipoCostos() {
        return tipoCostos;
    }

    public void setTipoCostos(String tipoCostos) {
        this.tipoCostos = tipoCostos;
    }

    public int getDirecto() {
        return directo;
    }

    public void setDirecto(int directo) {
        this.directo = directo;
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
    public List<AccidenteCostos> getAccidenteCostosList() {
        return accidenteCostosList;
    }

    public void setAccidenteCostosList(List<AccidenteCostos> accidenteCostosList) {
        this.accidenteCostosList = accidenteCostosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccTipoCostos != null ? idAccTipoCostos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccTipoCostos)) {
            return false;
        }
        AccTipoCostos other = (AccTipoCostos) object;
        if ((this.idAccTipoCostos == null && other.idAccTipoCostos != null) || (this.idAccTipoCostos != null && !this.idAccTipoCostos.equals(other.idAccTipoCostos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccTipoCostos[ idAccTipoCostos=" + idAccTipoCostos + " ]";
    }
    
}
