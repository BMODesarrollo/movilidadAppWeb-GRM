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
@Table(name = "acc_via_diseno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccViaDiseno.findAll", query = "SELECT a FROM AccViaDiseno a")
    , @NamedQuery(name = "AccViaDiseno.findByIdAccViaDiseno", query = "SELECT a FROM AccViaDiseno a WHERE a.idAccViaDiseno = :idAccViaDiseno")
    , @NamedQuery(name = "AccViaDiseno.findByViaDiseno", query = "SELECT a FROM AccViaDiseno a WHERE a.viaDiseno = :viaDiseno")
    , @NamedQuery(name = "AccViaDiseno.findByUsername", query = "SELECT a FROM AccViaDiseno a WHERE a.username = :username")
    , @NamedQuery(name = "AccViaDiseno.findByCreado", query = "SELECT a FROM AccViaDiseno a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccViaDiseno.findByModificado", query = "SELECT a FROM AccViaDiseno a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccViaDiseno.findByEstadoReg", query = "SELECT a FROM AccViaDiseno a WHERE a.estadoReg = :estadoReg")})
public class AccViaDiseno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_via_diseno")
    private Integer idAccViaDiseno;
    @Size(max = 60)
    @Column(name = "via_diseno")
    private String viaDiseno;
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
    @OneToMany(mappedBy = "idAccViaDiseno", fetch = FetchType.LAZY)
    private List<AccInformeOpe> accInformeOpeList;

    public AccViaDiseno() {
    }

    public AccViaDiseno(Integer idAccViaDiseno) {
        this.idAccViaDiseno = idAccViaDiseno;
    }

    public Integer getIdAccViaDiseno() {
        return idAccViaDiseno;
    }

    public void setIdAccViaDiseno(Integer idAccViaDiseno) {
        this.idAccViaDiseno = idAccViaDiseno;
    }

    public String getViaDiseno() {
        return viaDiseno;
    }

    public void setViaDiseno(String viaDiseno) {
        this.viaDiseno = viaDiseno;
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
        hash += (idAccViaDiseno != null ? idAccViaDiseno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccViaDiseno)) {
            return false;
        }
        AccViaDiseno other = (AccViaDiseno) object;
        if ((this.idAccViaDiseno == null && other.idAccViaDiseno != null) || (this.idAccViaDiseno != null && !this.idAccViaDiseno.equals(other.idAccViaDiseno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccViaDiseno[ idAccViaDiseno=" + idAccViaDiseno + " ]";
    }
    
}
