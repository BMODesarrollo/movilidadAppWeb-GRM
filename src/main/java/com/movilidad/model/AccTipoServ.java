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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_tipo_serv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccTipoServ.findAll", query = "SELECT a FROM AccTipoServ a")
    , @NamedQuery(name = "AccTipoServ.findByIdAccTipoServ", query = "SELECT a FROM AccTipoServ a WHERE a.idAccTipoServ = :idAccTipoServ")
    , @NamedQuery(name = "AccTipoServ.findByTipoServ", query = "SELECT a FROM AccTipoServ a WHERE a.tipoServ = :tipoServ")
    , @NamedQuery(name = "AccTipoServ.findByUsername", query = "SELECT a FROM AccTipoServ a WHERE a.username = :username")
    , @NamedQuery(name = "AccTipoServ.findByCreado", query = "SELECT a FROM AccTipoServ a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccTipoServ.findByModificado", query = "SELECT a FROM AccTipoServ a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccTipoServ.findByEstadoReg", query = "SELECT a FROM AccTipoServ a WHERE a.estadoReg = :estadoReg")})
public class AccTipoServ implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_tipo_serv")
    private Integer idAccTipoServ;
    @Size(max = 60)
    @Column(name = "tipo_serv")
    private String tipoServ;
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
    @OneToMany(mappedBy = "idTipoServ", fetch = FetchType.LAZY)
    private List<AccInformeVehCond> accInformeVehCondList;
    @OneToMany(mappedBy = "idAccTipoServ", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;

    public AccTipoServ() {
    }

    public AccTipoServ(Integer idAccTipoServ) {
        this.idAccTipoServ = idAccTipoServ;
    }

    public Integer getIdAccTipoServ() {
        return idAccTipoServ;
    }

    public void setIdAccTipoServ(Integer idAccTipoServ) {
        this.idAccTipoServ = idAccTipoServ;
    }

    public String getTipoServ() {
        return tipoServ;
    }

    public void setTipoServ(String tipoServ) {
        this.tipoServ = tipoServ;
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
    public List<AccInformeVehCond> getAccInformeVehCondList() {
        return accInformeVehCondList;
    }

    public void setAccInformeVehCondList(List<AccInformeVehCond> accInformeVehCondList) {
        this.accInformeVehCondList = accInformeVehCondList;
    }

    @XmlTransient
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccTipoServ != null ? idAccTipoServ.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccTipoServ)) {
            return false;
        }
        AccTipoServ other = (AccTipoServ) object;
        if ((this.idAccTipoServ == null && other.idAccTipoServ != null) || (this.idAccTipoServ != null && !this.idAccTipoServ.equals(other.idAccTipoServ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccTipoServ[ idAccTipoServ=" + idAccTipoServ + " ]";
    }
    
}
