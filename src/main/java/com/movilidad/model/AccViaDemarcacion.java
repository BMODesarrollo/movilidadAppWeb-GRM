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
@Table(name = "acc_via_demarcacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccViaDemarcacion.findAll", query = "SELECT a FROM AccViaDemarcacion a")
    , @NamedQuery(name = "AccViaDemarcacion.findByIdAccViaDemarcacion", query = "SELECT a FROM AccViaDemarcacion a WHERE a.idAccViaDemarcacion = :idAccViaDemarcacion")
    , @NamedQuery(name = "AccViaDemarcacion.findByViaDemarcacion", query = "SELECT a FROM AccViaDemarcacion a WHERE a.viaDemarcacion = :viaDemarcacion")
    , @NamedQuery(name = "AccViaDemarcacion.findByUsername", query = "SELECT a FROM AccViaDemarcacion a WHERE a.username = :username")
    , @NamedQuery(name = "AccViaDemarcacion.findByCreado", query = "SELECT a FROM AccViaDemarcacion a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccViaDemarcacion.findByModificado", query = "SELECT a FROM AccViaDemarcacion a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccViaDemarcacion.findByEstadoReg", query = "SELECT a FROM AccViaDemarcacion a WHERE a.estadoReg = :estadoReg")})
public class AccViaDemarcacion implements Serializable {

    @OneToMany(mappedBy = "idAccViaDemarcacion", fetch = FetchType.LAZY)
    private List<AccidenteLugarDemar> accidenteLugarDemarList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_via_demarcacion")
    private Integer idAccViaDemarcacion;
    @Size(max = 60)
    @Column(name = "via_demarcacion")
    private String viaDemarcacion;
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

    public AccViaDemarcacion() {
    }

    public AccViaDemarcacion(Integer idAccViaDemarcacion) {
        this.idAccViaDemarcacion = idAccViaDemarcacion;
    }

    public Integer getIdAccViaDemarcacion() {
        return idAccViaDemarcacion;
    }

    public void setIdAccViaDemarcacion(Integer idAccViaDemarcacion) {
        this.idAccViaDemarcacion = idAccViaDemarcacion;
    }

    public String getViaDemarcacion() {
        return viaDemarcacion;
    }

    public void setViaDemarcacion(String viaDemarcacion) {
        this.viaDemarcacion = viaDemarcacion;
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
        hash += (idAccViaDemarcacion != null ? idAccViaDemarcacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccViaDemarcacion)) {
            return false;
        }
        AccViaDemarcacion other = (AccViaDemarcacion) object;
        if ((this.idAccViaDemarcacion == null && other.idAccViaDemarcacion != null) || (this.idAccViaDemarcacion != null && !this.idAccViaDemarcacion.equals(other.idAccViaDemarcacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccViaDemarcacion[ idAccViaDemarcacion=" + idAccViaDemarcacion + " ]";
    }

    @XmlTransient
    public List<AccidenteLugarDemar> getAccidenteLugarDemarList() {
        return accidenteLugarDemarList;
    }

    public void setAccidenteLugarDemarList(List<AccidenteLugarDemar> accidenteLugarDemarList) {
        this.accidenteLugarDemarList = accidenteLugarDemarList;
    }
    
}
