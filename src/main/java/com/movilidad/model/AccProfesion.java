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
@Table(name = "acc_profesion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccProfesion.findAll", query = "SELECT a FROM AccProfesion a")
    , @NamedQuery(name = "AccProfesion.findByIdAccProfesion", query = "SELECT a FROM AccProfesion a WHERE a.idAccProfesion = :idAccProfesion")
    , @NamedQuery(name = "AccProfesion.findByProfesion", query = "SELECT a FROM AccProfesion a WHERE a.profesion = :profesion")
    , @NamedQuery(name = "AccProfesion.findByUsername", query = "SELECT a FROM AccProfesion a WHERE a.username = :username")
    , @NamedQuery(name = "AccProfesion.findByCreado", query = "SELECT a FROM AccProfesion a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccProfesion.findByModificado", query = "SELECT a FROM AccProfesion a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccProfesion.findByEstadoReg", query = "SELECT a FROM AccProfesion a WHERE a.estadoReg = :estadoReg")})
public class AccProfesion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_profesion")
    private Integer idAccProfesion;
    @Size(max = 60)
    @Column(name = "profesion")
    private String profesion;
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
    @OneToMany(mappedBy = "idAccProfesion", fetch = FetchType.LAZY)
    private List<AccidenteTestigo> accidenteTestigoList;
    @OneToMany(mappedBy = "idAccProfesion", fetch = FetchType.LAZY)
    private List<AccidenteVictima> accidenteVictimaList;

    public AccProfesion() {
    }

    public AccProfesion(Integer idAccProfesion) {
        this.idAccProfesion = idAccProfesion;
    }

    public Integer getIdAccProfesion() {
        return idAccProfesion;
    }

    public void setIdAccProfesion(Integer idAccProfesion) {
        this.idAccProfesion = idAccProfesion;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
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
    public List<AccidenteTestigo> getAccidenteTestigoList() {
        return accidenteTestigoList;
    }

    public void setAccidenteTestigoList(List<AccidenteTestigo> accidenteTestigoList) {
        this.accidenteTestigoList = accidenteTestigoList;
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
        hash += (idAccProfesion != null ? idAccProfesion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccProfesion)) {
            return false;
        }
        AccProfesion other = (AccProfesion) object;
        if ((this.idAccProfesion == null && other.idAccProfesion != null) || (this.idAccProfesion != null && !this.idAccProfesion.equals(other.idAccProfesion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccProfesion[ idAccProfesion=" + idAccProfesion + " ]";
    }
    
}
