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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "disp_faltante_repuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispFaltanteRepuesto.findAll", query = "SELECT d FROM DispFaltanteRepuesto d")
    , @NamedQuery(name = "DispFaltanteRepuesto.findByIdDispFaltanteRepuesto", query = "SELECT d FROM DispFaltanteRepuesto d WHERE d.idDispFaltanteRepuesto = :idDispFaltanteRepuesto")
    , @NamedQuery(name = "DispFaltanteRepuesto.findByNombre", query = "SELECT d FROM DispFaltanteRepuesto d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispFaltanteRepuesto.findByCantidad", query = "SELECT d FROM DispFaltanteRepuesto d WHERE d.cantidad = :cantidad")
    , @NamedQuery(name = "DispFaltanteRepuesto.findByObservacion", query = "SELECT d FROM DispFaltanteRepuesto d WHERE d.observacion = :observacion")
    , @NamedQuery(name = "DispFaltanteRepuesto.findByUsername", query = "SELECT d FROM DispFaltanteRepuesto d WHERE d.username = :username")
    , @NamedQuery(name = "DispFaltanteRepuesto.findByCreado", query = "SELECT d FROM DispFaltanteRepuesto d WHERE d.creado = :creado")
    , @NamedQuery(name = "DispFaltanteRepuesto.findByModificado", query = "SELECT d FROM DispFaltanteRepuesto d WHERE d.modificado = :modificado")
    , @NamedQuery(name = "DispFaltanteRepuesto.findByEstadoReg", query = "SELECT d FROM DispFaltanteRepuesto d WHERE d.estadoReg = :estadoReg")})
public class DispFaltanteRepuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_faltante_repuesto")
    private Integer idDispFaltanteRepuesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;
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

    @JoinColumn(name = "id_disp_actividad", referencedColumnName = "id_disp_actividad")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispActividad idDispActividad;

    public DispFaltanteRepuesto() {
    }

    public DispFaltanteRepuesto(Integer idDispFaltanteRepuesto) {
        this.idDispFaltanteRepuesto = idDispFaltanteRepuesto;
    }

    public DispFaltanteRepuesto(Integer idDispFaltanteRepuesto, String nombre, int cantidad) {
        this.idDispFaltanteRepuesto = idDispFaltanteRepuesto;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Integer getIdDispFaltanteRepuesto() {
        return idDispFaltanteRepuesto;
    }

    public void setIdDispFaltanteRepuesto(Integer idDispFaltanteRepuesto) {
        this.idDispFaltanteRepuesto = idDispFaltanteRepuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public DispActividad getIdDispActividad() {
        return idDispActividad;
    }

    public void setIdDispActividad(DispActividad idDispActividad) {
        this.idDispActividad = idDispActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispFaltanteRepuesto != null ? idDispFaltanteRepuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispFaltanteRepuesto)) {
            return false;
        }
        DispFaltanteRepuesto other = (DispFaltanteRepuesto) object;
        if ((this.idDispFaltanteRepuesto == null && other.idDispFaltanteRepuesto != null) || (this.idDispFaltanteRepuesto != null && !this.idDispFaltanteRepuesto.equals(other.idDispFaltanteRepuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispFaltanteRepuesto[ idDispFaltanteRepuesto=" + idDispFaltanteRepuesto + " ]";
    }

}
