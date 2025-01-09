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
@Table(name = "aseo_cabina_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AseoCabinaTipo.findAll", query = "SELECT a FROM AseoCabinaTipo a"),
    @NamedQuery(name = "AseoCabinaTipo.findByIdAseoCabinaTipo", query = "SELECT a FROM AseoCabinaTipo a WHERE a.idAseoCabinaTipo = :idAseoCabinaTipo"),
    @NamedQuery(name = "AseoCabinaTipo.findByNombre", query = "SELECT a FROM AseoCabinaTipo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AseoCabinaTipo.findByUsername", query = "SELECT a FROM AseoCabinaTipo a WHERE a.username = :username"),
    @NamedQuery(name = "AseoCabinaTipo.findByCreado", query = "SELECT a FROM AseoCabinaTipo a WHERE a.creado = :creado"),
    @NamedQuery(name = "AseoCabinaTipo.findByModificado", query = "SELECT a FROM AseoCabinaTipo a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AseoCabinaTipo.findByEstadoReg", query = "SELECT a FROM AseoCabinaTipo a WHERE a.estadoReg = :estadoReg")})
public class AseoCabinaTipo implements Serializable {
    @Column(name = "estado_reg")
    private Integer estadoReg;

    @OneToMany(mappedBy = "aseoCabinaTipo", fetch = FetchType.LAZY)
    private List<AseoCabina> aseoCabinaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aseo_cabina_tipo")
    private Integer idAseoCabinaTipo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
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

    public AseoCabinaTipo() {
    }

    public AseoCabinaTipo(Integer idAseoCabinaTipo) {
        this.idAseoCabinaTipo = idAseoCabinaTipo;
    }

    public AseoCabinaTipo(Integer idAseoCabinaTipo, String nombre, String username, Date creado, int estadoReg) {
        this.idAseoCabinaTipo = idAseoCabinaTipo;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAseoCabinaTipo() {
        return idAseoCabinaTipo;
    }

    public void setIdAseoCabinaTipo(Integer idAseoCabinaTipo) {
        this.idAseoCabinaTipo = idAseoCabinaTipo;
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
        hash += (idAseoCabinaTipo != null ? idAseoCabinaTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AseoCabinaTipo)) {
            return false;
        }
        AseoCabinaTipo other = (AseoCabinaTipo) object;
        if ((this.idAseoCabinaTipo == null && other.idAseoCabinaTipo != null) || (this.idAseoCabinaTipo != null && !this.idAseoCabinaTipo.equals(other.idAseoCabinaTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AseoCabinaTipo[ idAseoCabinaTipo=" + idAseoCabinaTipo + " ]";
    }

    @XmlTransient
    public List<AseoCabina> getAseoCabinaList() {
        return aseoCabinaList;
    }

    public void setAseoCabinaList(List<AseoCabina> aseoCabinaList) {
        this.aseoCabinaList = aseoCabinaList;
    }

}
