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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "acc_pre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccPre.findAll", query = "SELECT a FROM AccPre a"),
    @NamedQuery(name = "AccPre.findByIdAccPre", query = "SELECT a FROM AccPre a WHERE a.idAccPre = :idAccPre"),
    @NamedQuery(name = "AccPre.findByUsername", query = "SELECT a FROM AccPre a WHERE a.username = :username"),
    @NamedQuery(name = "AccPre.findByUsernameModifica", query = "SELECT a FROM AccPre a WHERE a.usernameModifica = :usernameModifica"),
    @NamedQuery(name = "AccPre.findByEstado", query = "SELECT a FROM AccPre a WHERE a.estado = :estado"),
    @NamedQuery(name = "AccPre.findByCreado", query = "SELECT a FROM AccPre a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccPre.findByModificado", query = "SELECT a FROM AccPre a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccPre.findByEstadoReg", query = "SELECT a FROM AccPre a WHERE a.estadoReg = :estadoReg")})
public class AccPre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_pre")
    private Integer idAccPre;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Size(max = 15)
    @Column(name = "username_modifica")
    private String usernameModifica;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(optional = false)
    private Novedad idNovedad;
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(optional = false)
    private PrgTc idPrgTc;

    public AccPre() {
    }

    public AccPre(Integer idAccPre) {
        this.idAccPre = idAccPre;
    }

    public Integer getIdAccPre() {
        return idAccPre;
    }

    public void setIdAccPre(Integer idAccPre) {
        this.idAccPre = idAccPre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameModifica() {
        return usernameModifica;
    }

    public void setUsernameModifica(String usernameModifica) {
        this.usernameModifica = usernameModifica;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public PrgTc getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(PrgTc idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccPre != null ? idAccPre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccPre)) {
            return false;
        }
        AccPre other = (AccPre) object;
        if ((this.idAccPre == null && other.idAccPre != null) || (this.idAccPre != null && !this.idAccPre.equals(other.idAccPre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccPre[ idAccPre=" + idAccPre + " ]";
    }

}
