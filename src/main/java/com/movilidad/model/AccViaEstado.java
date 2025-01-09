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
@Table(name = "acc_via_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccViaEstado.findAll", query = "SELECT a FROM AccViaEstado a")
    , @NamedQuery(name = "AccViaEstado.findByIdAccViaEstado", query = "SELECT a FROM AccViaEstado a WHERE a.idAccViaEstado = :idAccViaEstado")
    , @NamedQuery(name = "AccViaEstado.findByViaEstado", query = "SELECT a FROM AccViaEstado a WHERE a.viaEstado = :viaEstado")
    , @NamedQuery(name = "AccViaEstado.findByUsername", query = "SELECT a FROM AccViaEstado a WHERE a.username = :username")
    , @NamedQuery(name = "AccViaEstado.findByCreado", query = "SELECT a FROM AccViaEstado a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccViaEstado.findByModificado", query = "SELECT a FROM AccViaEstado a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccViaEstado.findByEstadoReg", query = "SELECT a FROM AccViaEstado a WHERE a.estadoReg = :estadoReg")})
public class AccViaEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_via_estado")
    private Integer idAccViaEstado;
    @Size(max = 60)
    @Column(name = "via_estado")
    private String viaEstado;
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
    @OneToMany(mappedBy = "idAccViaEstado", fetch = FetchType.LAZY)
    private List<AccInformeOpe> accInformeOpeList;

    public AccViaEstado() {
    }

    public AccViaEstado(Integer idAccViaEstado) {
        this.idAccViaEstado = idAccViaEstado;
    }

    public Integer getIdAccViaEstado() {
        return idAccViaEstado;
    }

    public void setIdAccViaEstado(Integer idAccViaEstado) {
        this.idAccViaEstado = idAccViaEstado;
    }

    public String getViaEstado() {
        return viaEstado;
    }

    public void setViaEstado(String viaEstado) {
        this.viaEstado = viaEstado;
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
        hash += (idAccViaEstado != null ? idAccViaEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccViaEstado)) {
            return false;
        }
        AccViaEstado other = (AccViaEstado) object;
        if ((this.idAccViaEstado == null && other.idAccViaEstado != null) || (this.idAccViaEstado != null && !this.idAccViaEstado.equals(other.idAccViaEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccViaEstado[ idAccViaEstado=" + idAccViaEstado + " ]";
    }
    
}
