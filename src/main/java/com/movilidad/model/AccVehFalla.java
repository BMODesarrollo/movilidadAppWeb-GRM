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
@Table(name = "acc_veh_falla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccVehFalla.findAll", query = "SELECT a FROM AccVehFalla a")
    , @NamedQuery(name = "AccVehFalla.findByIdAccVehFalla", query = "SELECT a FROM AccVehFalla a WHERE a.idAccVehFalla = :idAccVehFalla")
    , @NamedQuery(name = "AccVehFalla.findByFalla", query = "SELECT a FROM AccVehFalla a WHERE a.falla = :falla")
    , @NamedQuery(name = "AccVehFalla.findByUsername", query = "SELECT a FROM AccVehFalla a WHERE a.username = :username")
    , @NamedQuery(name = "AccVehFalla.findByCreado", query = "SELECT a FROM AccVehFalla a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccVehFalla.findByModificado", query = "SELECT a FROM AccVehFalla a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccVehFalla.findByEstadoReg", query = "SELECT a FROM AccVehFalla a WHERE a.estadoReg = :estadoReg")})
public class AccVehFalla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_veh_falla")
    private Integer idAccVehFalla;
    @Size(max = 60)
    @Column(name = "falla")
    private String falla;
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
    @OneToMany(mappedBy = "idAccFalla", fetch = FetchType.LAZY)
    private List<AccInformeVehCond> accInformeVehCondList;

    public AccVehFalla() {
    }

    public AccVehFalla(Integer idAccVehFalla) {
        this.idAccVehFalla = idAccVehFalla;
    }

    public Integer getIdAccVehFalla() {
        return idAccVehFalla;
    }

    public void setIdAccVehFalla(Integer idAccVehFalla) {
        this.idAccVehFalla = idAccVehFalla;
    }

    public String getFalla() {
        return falla;
    }

    public void setFalla(String falla) {
        this.falla = falla;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccVehFalla != null ? idAccVehFalla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccVehFalla)) {
            return false;
        }
        AccVehFalla other = (AccVehFalla) object;
        if ((this.idAccVehFalla == null && other.idAccVehFalla != null) || (this.idAccVehFalla != null && !this.idAccVehFalla.equals(other.idAccVehFalla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccVehFalla[ idAccVehFalla=" + idAccVehFalla + " ]";
    }
    
}
