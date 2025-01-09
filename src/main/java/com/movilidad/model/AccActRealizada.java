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
@Table(name = "acc_act_realizada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccActRealizada.findAll", query = "SELECT a FROM AccActRealizada a")
    , @NamedQuery(name = "AccActRealizada.findByIdAccActRealizada", query = "SELECT a FROM AccActRealizada a WHERE a.idAccActRealizada = :idAccActRealizada")
    , @NamedQuery(name = "AccActRealizada.findByActRealizada", query = "SELECT a FROM AccActRealizada a WHERE a.actRealizada = :actRealizada")
    , @NamedQuery(name = "AccActRealizada.findByUsername", query = "SELECT a FROM AccActRealizada a WHERE a.username = :username")
    , @NamedQuery(name = "AccActRealizada.findByCreado", query = "SELECT a FROM AccActRealizada a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccActRealizada.findByModificado", query = "SELECT a FROM AccActRealizada a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccActRealizada.findByEstadoReg", query = "SELECT a FROM AccActRealizada a WHERE a.estadoReg = :estadoReg")})
public class AccActRealizada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_act_realizada")
    private Integer idAccActRealizada;
    @Size(max = 60)
    @Column(name = "act_realizada")
    private String actRealizada;
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
    @OneToMany(mappedBy = "idAccActRealizada", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;

    public AccActRealizada() {
    }

    public AccActRealizada(Integer idAccActRealizada) {
        this.idAccActRealizada = idAccActRealizada;
    }

    public Integer getIdAccActRealizada() {
        return idAccActRealizada;
    }

    public void setIdAccActRealizada(Integer idAccActRealizada) {
        this.idAccActRealizada = idAccActRealizada;
    }

    public String getActRealizada() {
        return actRealizada;
    }

    public void setActRealizada(String actRealizada) {
        this.actRealizada = actRealizada;
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
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccActRealizada != null ? idAccActRealizada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccActRealizada)) {
            return false;
        }
        AccActRealizada other = (AccActRealizada) object;
        if ((this.idAccActRealizada == null && other.idAccActRealizada != null) || (this.idAccActRealizada != null && !this.idAccActRealizada.equals(other.idAccActRealizada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccActRealizada[ idAccActRealizada=" + idAccActRealizada + " ]";
    }
    
}
