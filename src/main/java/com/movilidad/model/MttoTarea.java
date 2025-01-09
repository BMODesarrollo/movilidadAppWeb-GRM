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
 * @author solucionesit
 */
@Entity
@Table(name = "mtto_tarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MttoTarea.findAll", query = "SELECT m FROM MttoTarea m"),
    @NamedQuery(name = "MttoTarea.findByIdMttoTarea", query = "SELECT m FROM MttoTarea m WHERE m.idMttoTarea = :idMttoTarea"),
    @NamedQuery(name = "MttoTarea.findByNombreMttoTarea", query = "SELECT m FROM MttoTarea m WHERE m.nombreMttoTarea = :nombreMttoTarea"),
    @NamedQuery(name = "MttoTarea.findByDescripcionMttoTarea", query = "SELECT m FROM MttoTarea m WHERE m.descripcionMttoTarea = :descripcionMttoTarea"),
    @NamedQuery(name = "MttoTarea.findByUsername", query = "SELECT m FROM MttoTarea m WHERE m.username = :username"),
    @NamedQuery(name = "MttoTarea.findByCreado", query = "SELECT m FROM MttoTarea m WHERE m.creado = :creado"),
    @NamedQuery(name = "MttoTarea.findByModificado", query = "SELECT m FROM MttoTarea m WHERE m.modificado = :modificado"),
    @NamedQuery(name = "MttoTarea.findByEstadoReg", query = "SELECT m FROM MttoTarea m WHERE m.estadoReg = :estadoReg")})
public class MttoTarea implements Serializable {

    @OneToMany(mappedBy = "idMttoTarea", fetch = FetchType.LAZY)
    private List<PrgAsignacion> prgAsignacionList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mtto_tarea")
    private Integer idMttoTarea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre_mtto_tarea")
    private String nombreMttoTarea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion_mtto_tarea")
    private String descripcionMttoTarea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMttoTarea", fetch = FetchType.EAGER)
    private List<MttoAsig> mttoAsigList;

    public MttoTarea() {
    }

    public MttoTarea(Integer idMttoTarea) {
        this.idMttoTarea = idMttoTarea;
    }

    public MttoTarea(Integer idMttoTarea, String nombreMttoTarea, String descripcionMttoTarea, String username, Date creado, int estadoReg) {
        this.idMttoTarea = idMttoTarea;
        this.nombreMttoTarea = nombreMttoTarea;
        this.descripcionMttoTarea = descripcionMttoTarea;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMttoTarea() {
        return idMttoTarea;
    }

    public void setIdMttoTarea(Integer idMttoTarea) {
        this.idMttoTarea = idMttoTarea;
    }

    public String getNombreMttoTarea() {
        return nombreMttoTarea;
    }

    public void setNombreMttoTarea(String nombreMttoTarea) {
        this.nombreMttoTarea = nombreMttoTarea;
    }

    public String getDescripcionMttoTarea() {
        return descripcionMttoTarea;
    }

    public void setDescripcionMttoTarea(String descripcionMttoTarea) {
        this.descripcionMttoTarea = descripcionMttoTarea;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<MttoAsig> getMttoAsigList() {
        return mttoAsigList;
    }

    public void setMttoAsigList(List<MttoAsig> mttoAsigList) {
        this.mttoAsigList = mttoAsigList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMttoTarea != null ? idMttoTarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MttoTarea)) {
            return false;
        }
        MttoTarea other = (MttoTarea) object;
        if ((this.idMttoTarea == null && other.idMttoTarea != null) || (this.idMttoTarea != null && !this.idMttoTarea.equals(other.idMttoTarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MttoTarea[ idMttoTarea=" + idMttoTarea + " ]";
    }

    @XmlTransient
    public List<PrgAsignacion> getPrgAsignacionList() {
        return prgAsignacionList;
    }

    public void setPrgAsignacionList(List<PrgAsignacion> prgAsignacionList) {
        this.prgAsignacionList = prgAsignacionList;
    }
    
}
