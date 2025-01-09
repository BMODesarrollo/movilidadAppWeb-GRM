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
@Table(name = "acc_rango_edad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccRangoEdad.findAll", query = "SELECT a FROM AccRangoEdad a")
    , @NamedQuery(name = "AccRangoEdad.findByIdAccRangoEdad", query = "SELECT a FROM AccRangoEdad a WHERE a.idAccRangoEdad = :idAccRangoEdad")
    , @NamedQuery(name = "AccRangoEdad.findByRangoEdad", query = "SELECT a FROM AccRangoEdad a WHERE a.rangoEdad = :rangoEdad")
    , @NamedQuery(name = "AccRangoEdad.findByUsername", query = "SELECT a FROM AccRangoEdad a WHERE a.username = :username")
    , @NamedQuery(name = "AccRangoEdad.findByCreado", query = "SELECT a FROM AccRangoEdad a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccRangoEdad.findByModificado", query = "SELECT a FROM AccRangoEdad a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccRangoEdad.findByEstadoReg", query = "SELECT a FROM AccRangoEdad a WHERE a.estadoReg = :estadoReg")})
public class AccRangoEdad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_rango_edad")
    private Integer idAccRangoEdad;
    @Size(max = 60)
    @Column(name = "rango_edad")
    private String rangoEdad;
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
    @OneToMany(mappedBy = "idAccRangoEdad", fetch = FetchType.LAZY)
    private List<AccidenteVictima> accidenteVictimaList;

    public AccRangoEdad() {
    }

    public AccRangoEdad(Integer idAccRangoEdad) {
        this.idAccRangoEdad = idAccRangoEdad;
    }

    public Integer getIdAccRangoEdad() {
        return idAccRangoEdad;
    }

    public void setIdAccRangoEdad(Integer idAccRangoEdad) {
        this.idAccRangoEdad = idAccRangoEdad;
    }

    public String getRangoEdad() {
        return rangoEdad;
    }

    public void setRangoEdad(String rangoEdad) {
        this.rangoEdad = rangoEdad;
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
    public List<AccidenteVictima> getAccidenteVictimaList() {
        return accidenteVictimaList;
    }

    public void setAccidenteVictimaList(List<AccidenteVictima> accidenteVictimaList) {
        this.accidenteVictimaList = accidenteVictimaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccRangoEdad != null ? idAccRangoEdad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccRangoEdad)) {
            return false;
        }
        AccRangoEdad other = (AccRangoEdad) object;
        if ((this.idAccRangoEdad == null && other.idAccRangoEdad != null) || (this.idAccRangoEdad != null && !this.idAccRangoEdad.equals(other.idAccRangoEdad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccRangoEdad[ idAccRangoEdad=" + idAccRangoEdad + " ]";
    }
    
}
