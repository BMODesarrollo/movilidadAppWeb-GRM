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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "seg_gestiona_habilitacion")
@XmlRootElement
public class SegGestionaHabilitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_gestiona_habilitacion")
    private Integer idSegGestionaHabilitacion;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 150)
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
    @OneToMany(mappedBy = "idSegGestionaHabilitacion", fetch = FetchType.LAZY)
    private List<SegInoperativos> segInoperativosList;

    public SegGestionaHabilitacion() {
    }

    public SegGestionaHabilitacion(Integer idSegGestionaHabilitacion) {
        this.idSegGestionaHabilitacion = idSegGestionaHabilitacion;
    }

    public Integer getIdSegGestionaHabilitacion() {
        return idSegGestionaHabilitacion;
    }

    public void setIdSegGestionaHabilitacion(Integer idSegGestionaHabilitacion) {
        this.idSegGestionaHabilitacion = idSegGestionaHabilitacion;
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
    public List<SegInoperativos> getSegInoperativosList() {
        return segInoperativosList;
    }

    public void setSegInoperativosList(List<SegInoperativos> segInoperativosList) {
        this.segInoperativosList = segInoperativosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegGestionaHabilitacion != null ? idSegGestionaHabilitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegGestionaHabilitacion)) {
            return false;
        }
        SegGestionaHabilitacion other = (SegGestionaHabilitacion) object;
        if ((this.idSegGestionaHabilitacion == null && other.idSegGestionaHabilitacion != null) || (this.idSegGestionaHabilitacion != null && !this.idSegGestionaHabilitacion.equals(other.idSegGestionaHabilitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegGestionaHabilitacion[ idSegGestionaHabilitacion=" + idSegGestionaHabilitacion + " ]";
    }
    
}
