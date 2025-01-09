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
@Table(name = "acc_via_zonat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccViaZonat.findAll", query = "SELECT a FROM AccViaZonat a")
    , @NamedQuery(name = "AccViaZonat.findByIdAccViaZonat", query = "SELECT a FROM AccViaZonat a WHERE a.idAccViaZonat = :idAccViaZonat")
    , @NamedQuery(name = "AccViaZonat.findByViaZonat", query = "SELECT a FROM AccViaZonat a WHERE a.viaZonat = :viaZonat")
    , @NamedQuery(name = "AccViaZonat.findByUsername", query = "SELECT a FROM AccViaZonat a WHERE a.username = :username")
    , @NamedQuery(name = "AccViaZonat.findByCreado", query = "SELECT a FROM AccViaZonat a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccViaZonat.findByModificado", query = "SELECT a FROM AccViaZonat a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccViaZonat.findByEstadoReg", query = "SELECT a FROM AccViaZonat a WHERE a.estadoReg = :estadoReg")})
public class AccViaZonat implements Serializable {

    @OneToMany(mappedBy = "idAccViaZonat", fetch = FetchType.LAZY)
    private List<AccidenteLugar> accidenteLugarList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_via_zonat")
    private Integer idAccViaZonat;
    @Size(max = 60)
    @Column(name = "via_zonat")
    private String viaZonat;
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

    public AccViaZonat() {
    }

    public AccViaZonat(Integer idAccViaZonat) {
        this.idAccViaZonat = idAccViaZonat;
    }

    public Integer getIdAccViaZonat() {
        return idAccViaZonat;
    }

    public void setIdAccViaZonat(Integer idAccViaZonat) {
        this.idAccViaZonat = idAccViaZonat;
    }

    public String getViaZonat() {
        return viaZonat;
    }

    public void setViaZonat(String viaZonat) {
        this.viaZonat = viaZonat;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccViaZonat != null ? idAccViaZonat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccViaZonat)) {
            return false;
        }
        AccViaZonat other = (AccViaZonat) object;
        if ((this.idAccViaZonat == null && other.idAccViaZonat != null) || (this.idAccViaZonat != null && !this.idAccViaZonat.equals(other.idAccViaZonat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccViaZonat[ idAccViaZonat=" + idAccViaZonat + " ]";
    }

    @XmlTransient
    public List<AccidenteLugar> getAccidenteLugarList() {
        return accidenteLugarList;
    }

    public void setAccidenteLugarList(List<AccidenteLugar> accidenteLugarList) {
        this.accidenteLugarList = accidenteLugarList;
    }
    
}
