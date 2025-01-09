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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "sst_empresa_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstEmpresaTipo.findAll", query = "SELECT s FROM SstEmpresaTipo s"),
    @NamedQuery(name = "SstEmpresaTipo.findByIdSstEmpresaTipo", query = "SELECT s FROM SstEmpresaTipo s WHERE s.idSstEmpresaTipo = :idSstEmpresaTipo"),
    @NamedQuery(name = "SstEmpresaTipo.findByNombre", query = "SELECT s FROM SstEmpresaTipo s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SstEmpresaTipo.findByUsername", query = "SELECT s FROM SstEmpresaTipo s WHERE s.username = :username"),
    @NamedQuery(name = "SstEmpresaTipo.findByCreado", query = "SELECT s FROM SstEmpresaTipo s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstEmpresaTipo.findByModificado", query = "SELECT s FROM SstEmpresaTipo s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstEmpresaTipo.findByEstadoReg", query = "SELECT s FROM SstEmpresaTipo s WHERE s.estadoReg = :estadoReg")})
public class SstEmpresaTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_empresa_tipo")
    private Integer idSstEmpresaTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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

    public SstEmpresaTipo() {
    }

    public SstEmpresaTipo(Integer idSstEmpresaTipo) {
        this.idSstEmpresaTipo = idSstEmpresaTipo;
    }

    public SstEmpresaTipo(Integer idSstEmpresaTipo, String nombre, String username, Date creado, int estadoReg) {
        this.idSstEmpresaTipo = idSstEmpresaTipo;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSstEmpresaTipo() {
        return idSstEmpresaTipo;
    }

    public void setIdSstEmpresaTipo(Integer idSstEmpresaTipo) {
        this.idSstEmpresaTipo = idSstEmpresaTipo;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstEmpresaTipo != null ? idSstEmpresaTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstEmpresaTipo)) {
            return false;
        }
        SstEmpresaTipo other = (SstEmpresaTipo) object;
        if ((this.idSstEmpresaTipo == null && other.idSstEmpresaTipo != null) || (this.idSstEmpresaTipo != null && !this.idSstEmpresaTipo.equals(other.idSstEmpresaTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstEmpresaTipo[ idSstEmpresaTipo=" + idSstEmpresaTipo + " ]";
    }
    
}
