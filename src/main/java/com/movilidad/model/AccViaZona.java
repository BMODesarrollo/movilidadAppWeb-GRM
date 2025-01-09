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
@Table(name = "acc_via_zona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccViaZona.findAll", query = "SELECT a FROM AccViaZona a")
    , @NamedQuery(name = "AccViaZona.findByIdAccViaZona", query = "SELECT a FROM AccViaZona a WHERE a.idAccViaZona = :idAccViaZona")
    , @NamedQuery(name = "AccViaZona.findByViaZona", query = "SELECT a FROM AccViaZona a WHERE a.viaZona = :viaZona")
    , @NamedQuery(name = "AccViaZona.findByUsername", query = "SELECT a FROM AccViaZona a WHERE a.username = :username")
    , @NamedQuery(name = "AccViaZona.findByCreado", query = "SELECT a FROM AccViaZona a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccViaZona.findByModificado", query = "SELECT a FROM AccViaZona a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccViaZona.findByEstadoReg", query = "SELECT a FROM AccViaZona a WHERE a.estadoReg = :estadoReg")})
public class AccViaZona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_via_zona")
    private Integer idAccViaZona;
    @Size(max = 60)
    @Column(name = "via_zona")
    private String viaZona;
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
    @OneToMany(mappedBy = "idAccViaZona", fetch = FetchType.LAZY)
    private List<AccInformeOpe> accInformeOpeList;

    public AccViaZona() {
    }

    public AccViaZona(Integer idAccViaZona) {
        this.idAccViaZona = idAccViaZona;
    }

    public Integer getIdAccViaZona() {
        return idAccViaZona;
    }

    public void setIdAccViaZona(Integer idAccViaZona) {
        this.idAccViaZona = idAccViaZona;
    }

    public String getViaZona() {
        return viaZona;
    }

    public void setViaZona(String viaZona) {
        this.viaZona = viaZona;
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
    public List<AccInformeOpe> getAccInformeOpeList() {
        return accInformeOpeList;
    }

    public void setAccInformeOpeList(List<AccInformeOpe> accInformeOpeList) {
        this.accInformeOpeList = accInformeOpeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccViaZona != null ? idAccViaZona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccViaZona)) {
            return false;
        }
        AccViaZona other = (AccViaZona) object;
        if ((this.idAccViaZona == null && other.idAccViaZona != null) || (this.idAccViaZona != null && !this.idAccViaZona.equals(other.idAccViaZona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccViaZona[ idAccViaZona=" + idAccViaZona + " ]";
    }
    
}
