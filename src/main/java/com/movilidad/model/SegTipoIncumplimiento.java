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
@Table(name = "seg_tipo_incumplimiento")
@XmlRootElement
public class SegTipoIncumplimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_tipo_incumplimiento")
    private Integer idSegTipoIncumplimiento;
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
    @OneToMany(mappedBy = "idSegTipoIncumplimiento", fetch = FetchType.LAZY)
    private List<SegInoperativos> segInoperativosList;

    public SegTipoIncumplimiento() {
    }

    public SegTipoIncumplimiento(Integer idSegTipoIncumplimiento) {
        this.idSegTipoIncumplimiento = idSegTipoIncumplimiento;
    }

    public Integer getIdSegTipoIncumplimiento() {
        return idSegTipoIncumplimiento;
    }

    public void setIdSegTipoIncumplimiento(Integer idSegTipoIncumplimiento) {
        this.idSegTipoIncumplimiento = idSegTipoIncumplimiento;
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
        hash += (idSegTipoIncumplimiento != null ? idSegTipoIncumplimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegTipoIncumplimiento)) {
            return false;
        }
        SegTipoIncumplimiento other = (SegTipoIncumplimiento) object;
        if ((this.idSegTipoIncumplimiento == null && other.idSegTipoIncumplimiento != null) || (this.idSegTipoIncumplimiento != null && !this.idSegTipoIncumplimiento.equals(other.idSegTipoIncumplimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegTipoIncumplimiento[ idSegTipoIncumplimiento=" + idSegTipoIncumplimiento + " ]";
    }
    
}