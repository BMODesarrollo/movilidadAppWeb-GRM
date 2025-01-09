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
@Table(name = "hallazgos_param_area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HallazgosParamArea.findAll", query = "SELECT h FROM HallazgosParamArea h"),
    @NamedQuery(name = "HallazgosParamArea.findByIdHallazgosParamArea", query = "SELECT h FROM HallazgosParamArea h WHERE h.idHallazgosParamArea = :idHallazgosParamArea"),
    @NamedQuery(name = "HallazgosParamArea.findByNombre", query = "SELECT h FROM HallazgosParamArea h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "HallazgosParamArea.findByUsername", query = "SELECT h FROM HallazgosParamArea h WHERE h.username = :username"),
    @NamedQuery(name = "HallazgosParamArea.findByCreado", query = "SELECT h FROM HallazgosParamArea h WHERE h.creado = :creado"),
    @NamedQuery(name = "HallazgosParamArea.findByModificado", query = "SELECT h FROM HallazgosParamArea h WHERE h.modificado = :modificado"),
    @NamedQuery(name = "HallazgosParamArea.findByEstadoReg", query = "SELECT h FROM HallazgosParamArea h WHERE h.estadoReg = :estadoReg")})
public class HallazgosParamArea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_hallazgos_param_area")
    private Integer idHallazgosParamArea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(min = 0, max = 65535)
    @Column(name = "emails")
    private String emails;
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
    @OneToMany(mappedBy = "idHallazgoParamArea")
    private List<Hallazgo> hallazgosList;

    public HallazgosParamArea() {
    }

    public HallazgosParamArea(Integer idHallazgosParamArea) {
        this.idHallazgosParamArea = idHallazgosParamArea;
    }

    public HallazgosParamArea(Integer idHallazgosParamArea, String nombre, String descripcion) {
        this.idHallazgosParamArea = idHallazgosParamArea;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdHallazgosParamArea() {
        return idHallazgosParamArea;
    }

    public void setIdHallazgosParamArea(Integer idHallazgosParamArea) {
        this.idHallazgosParamArea = idHallazgosParamArea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
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
    public List<Hallazgo> getHallazgosList() {
        return hallazgosList;
    }

    public void setHallazgosList(List<Hallazgo> hallazgosList) {
        this.hallazgosList = hallazgosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHallazgosParamArea != null ? idHallazgosParamArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HallazgosParamArea)) {
            return false;
        }
        HallazgosParamArea other = (HallazgosParamArea) object;
        if ((this.idHallazgosParamArea == null && other.idHallazgosParamArea != null) || (this.idHallazgosParamArea != null && !this.idHallazgosParamArea.equals(other.idHallazgosParamArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.HallazgosParamArea[ idHallazgosParamArea=" + idHallazgosParamArea + " ]";
    }

}
