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
@Table(name = "acc_via_clima")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccViaClima.findAll", query = "SELECT a FROM AccViaClima a")
    , @NamedQuery(name = "AccViaClima.findByIdAccViaClima", query = "SELECT a FROM AccViaClima a WHERE a.idAccViaClima = :idAccViaClima")
    , @NamedQuery(name = "AccViaClima.findByViaClima", query = "SELECT a FROM AccViaClima a WHERE a.viaClima = :viaClima")
    , @NamedQuery(name = "AccViaClima.findByUsername", query = "SELECT a FROM AccViaClima a WHERE a.username = :username")
    , @NamedQuery(name = "AccViaClima.findByCreado", query = "SELECT a FROM AccViaClima a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccViaClima.findByModificado", query = "SELECT a FROM AccViaClima a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccViaClima.findByEstadoReg", query = "SELECT a FROM AccViaClima a WHERE a.estadoReg = :estadoReg")})
public class AccViaClima implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_via_clima")
    private Integer idAccViaClima;
    @Size(max = 60)
    @Column(name = "via_clima")
    private String viaClima;
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
    @OneToMany(mappedBy = "idAccViaClima", fetch = FetchType.LAZY)
    private List<AccInformeOpe> accInformeOpeList;

    public AccViaClima() {
    }

    public AccViaClima(Integer idAccViaClima) {
        this.idAccViaClima = idAccViaClima;
    }

    public Integer getIdAccViaClima() {
        return idAccViaClima;
    }

    public void setIdAccViaClima(Integer idAccViaClima) {
        this.idAccViaClima = idAccViaClima;
    }

    public String getViaClima() {
        return viaClima;
    }

    public void setViaClima(String viaClima) {
        this.viaClima = viaClima;
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
        hash += (idAccViaClima != null ? idAccViaClima.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccViaClima)) {
            return false;
        }
        AccViaClima other = (AccViaClima) object;
        if ((this.idAccViaClima == null && other.idAccViaClima != null) || (this.idAccViaClima != null && !this.idAccViaClima.equals(other.idAccViaClima))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccViaClima[ idAccViaClima=" + idAccViaClima + " ]";
    }
    
}
