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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "acc_causa_sub")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccCausaSub.findAll", query = "SELECT a FROM AccCausaSub a")
    , @NamedQuery(name = "AccCausaSub.findByIdAccSubcausa", query = "SELECT a FROM AccCausaSub a WHERE a.idAccSubcausa = :idAccSubcausa")
    , @NamedQuery(name = "AccCausaSub.findBySubcausa", query = "SELECT a FROM AccCausaSub a WHERE a.subcausa = :subcausa")
    , @NamedQuery(name = "AccCausaSub.findByUsername", query = "SELECT a FROM AccCausaSub a WHERE a.username = :username")
    , @NamedQuery(name = "AccCausaSub.findByCreado", query = "SELECT a FROM AccCausaSub a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccCausaSub.findByModificado", query = "SELECT a FROM AccCausaSub a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccCausaSub.findByEstadoReg", query = "SELECT a FROM AccCausaSub a WHERE a.estadoReg = :estadoReg")})
public class AccCausaSub implements Serializable {

    @OneToMany(mappedBy = "idCausasub", fetch = FetchType.LAZY)
    private List<AccidentePreCalificacion> accidentePreCalificacionList;

    @OneToMany(mappedBy = "idCausaSub", fetch = FetchType.LAZY)
    private List<AccInformeOpeCausalidad> accInformeOpeCausalidadList;

//    @OneToMany(mappedBy = "idCausaSub", fetch = FetchType.LAZY)
//    private List<AccidenteAnalisis> accidenteAnalisisList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccSubcausa", fetch = FetchType.LAZY)
    private List<AccCausaRaiz> accCausaRaizList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_subcausa")
    private Integer idAccSubcausa;
    @Size(max = 60)
    @Column(name = "subcausa")
    private String subcausa;
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
    @JoinColumn(name = "id_causa", referencedColumnName = "id_acc_causa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AccCausa idCausa;

    public AccCausaSub() {
    }

    public AccCausaSub(Integer idAccSubcausa) {
        this.idAccSubcausa = idAccSubcausa;
    }

    public Integer getIdAccSubcausa() {
        return idAccSubcausa;
    }

    public void setIdAccSubcausa(Integer idAccSubcausa) {
        this.idAccSubcausa = idAccSubcausa;
    }

    public String getSubcausa() {
        return subcausa;
    }

    public void setSubcausa(String subcausa) {
        this.subcausa = subcausa;
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

    public AccCausa getIdCausa() {
        return idCausa;
    }

    public void setIdCausa(AccCausa idCausa) {
        this.idCausa = idCausa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccSubcausa != null ? idAccSubcausa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccCausaSub)) {
            return false;
        }
        AccCausaSub other = (AccCausaSub) object;
        if ((this.idAccSubcausa == null && other.idAccSubcausa != null) || (this.idAccSubcausa != null && !this.idAccSubcausa.equals(other.idAccSubcausa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccCausaSub[ idAccSubcausa=" + idAccSubcausa + " ]";
    }

    @XmlTransient
    public List<AccCausaRaiz> getAccCausaRaizList() {
        return accCausaRaizList;
    }

    public void setAccCausaRaizList(List<AccCausaRaiz> accCausaRaizList) {
        this.accCausaRaizList = accCausaRaizList;
    }

//    @XmlTransient
//    public List<AccidenteAnalisis> getAccidenteAnalisisList() {
//        return accidenteAnalisisList;
//    }
//
//    public void setAccidenteAnalisisList(List<AccidenteAnalisis> accidenteAnalisisList) {
//        this.accidenteAnalisisList = accidenteAnalisisList;
//    }

    @XmlTransient
    public List<AccInformeOpeCausalidad> getAccInformeOpeCausalidadList() {
        return accInformeOpeCausalidadList;
    }

    public void setAccInformeOpeCausalidadList(List<AccInformeOpeCausalidad> accInformeOpeCausalidadList) {
        this.accInformeOpeCausalidadList = accInformeOpeCausalidadList;
    }

    @XmlTransient
    public List<AccidentePreCalificacion> getAccidentePreCalificacionList() {
        return accidentePreCalificacionList;
    }

    public void setAccidentePreCalificacionList(List<AccidentePreCalificacion> accidentePreCalificacionList) {
        this.accidentePreCalificacionList = accidentePreCalificacionList;
    }
    
}
