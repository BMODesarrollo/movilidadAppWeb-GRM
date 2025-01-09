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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "acc_desplaza_a")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccDesplazaA.findAll", query = "SELECT a FROM AccDesplazaA a"),
    @NamedQuery(name = "AccDesplazaA.findByIdAccDesplazaA", query = "SELECT a FROM AccDesplazaA a WHERE a.idAccDesplazaA = :idAccDesplazaA"),
    @NamedQuery(name = "AccDesplazaA.findByNombre", query = "SELECT a FROM AccDesplazaA a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AccDesplazaA.findByUsername", query = "SELECT a FROM AccDesplazaA a WHERE a.username = :username"),
    @NamedQuery(name = "AccDesplazaA.findByCreado", query = "SELECT a FROM AccDesplazaA a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccDesplazaA.findByModificado", query = "SELECT a FROM AccDesplazaA a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccDesplazaA.findByEstadoReg", query = "SELECT a FROM AccDesplazaA a WHERE a.estadoReg = :estadoReg")})
public class AccDesplazaA implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_desplaza_a")
    private Integer idAccDesplazaA;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
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

    @OneToMany(mappedBy = "idAccDesplazaA", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;

    public AccDesplazaA() {
    }

    public AccDesplazaA(Integer idAccDesplazaA) {
        this.idAccDesplazaA = idAccDesplazaA;
    }

    public AccDesplazaA(Integer idAccDesplazaA, String nombre, String descripcion) {
        this.idAccDesplazaA = idAccDesplazaA;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdAccDesplazaA() {
        return idAccDesplazaA;
    }

    public void setIdAccDesplazaA(Integer idAccDesplazaA) {
        this.idAccDesplazaA = idAccDesplazaA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccDesplazaA != null ? idAccDesplazaA.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccDesplazaA)) {
            return false;
        }
        AccDesplazaA other = (AccDesplazaA) object;
        if ((this.idAccDesplazaA == null && other.idAccDesplazaA != null) || (this.idAccDesplazaA != null && !this.idAccDesplazaA.equals(other.idAccDesplazaA))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccDesplazaA[ idAccDesplazaA=" + idAccDesplazaA + " ]";
    }

}
