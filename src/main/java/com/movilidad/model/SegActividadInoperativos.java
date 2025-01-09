/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "seg_actividad_inoperativos")
@XmlRootElement
public class SegActividadInoperativos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_actividad_inoperativos")
    private Integer idSegActividadInoperativos;
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
    @JoinColumn(name = "id_actividad_habilitacion", referencedColumnName = "id_seg_actividad_habilitacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SegActividadHabilitacion idActividadHabilitacion;
    @JoinColumn(name = "id_seg_inoperativos", referencedColumnName = "id_seg_inoperativos")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SegInoperativos idSegInoperativos;

    public SegActividadInoperativos() {
    }

    public SegActividadInoperativos(Integer idSegActividadInoperativos) {
        this.idSegActividadInoperativos = idSegActividadInoperativos;
    }

    public Integer getIdSegActividadInoperativos() {
        return idSegActividadInoperativos;
    }

    public void setIdSegActividadInoperativos(Integer idSegActividadInoperativos) {
        this.idSegActividadInoperativos = idSegActividadInoperativos;
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

    public SegActividadHabilitacion getIdActividadHabilitacion() {
        return idActividadHabilitacion;
    }

    public void setIdActividadHabilitacion(SegActividadHabilitacion idActividadHabilitacion) {
        this.idActividadHabilitacion = idActividadHabilitacion;
    }

    public SegInoperativos getIdSegInoperativos() {
        return idSegInoperativos;
    }

    public void setIdSegInoperativos(SegInoperativos idSegInoperativos) {
        this.idSegInoperativos = idSegInoperativos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegActividadInoperativos != null ? idSegActividadInoperativos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegActividadInoperativos)) {
            return false;
        }
        SegActividadInoperativos other = (SegActividadInoperativos) object;
        if ((this.idSegActividadInoperativos == null && other.idSegActividadInoperativos != null) || (this.idSegActividadInoperativos != null && !this.idSegActividadInoperativos.equals(other.idSegActividadInoperativos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegActividadInoperativos[ idSegActividadInoperativos=" + idSegActividadInoperativos + " ]";
    }
    
}
