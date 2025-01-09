/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import java.util.Date;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "aseo_param_area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AseoParamArea.findAll", query = "SELECT a FROM AseoParamArea a"),
    @NamedQuery(name = "AseoParamArea.findByIdAseoParamArea", query = "SELECT a FROM AseoParamArea a WHERE a.idAseoParamArea = :idAseoParamArea"),
    @NamedQuery(name = "AseoParamArea.findByCodigo", query = "SELECT a FROM AseoParamArea a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "AseoParamArea.findByNombre", query = "SELECT a FROM AseoParamArea a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AseoParamArea.findByHashString", query = "SELECT a FROM AseoParamArea a WHERE a.hashString = :hashString"),
    @NamedQuery(name = "AseoParamArea.findByUsername", query = "SELECT a FROM AseoParamArea a WHERE a.username = :username"),
    @NamedQuery(name = "AseoParamArea.findByCreado", query = "SELECT a FROM AseoParamArea a WHERE a.creado = :creado"),
    @NamedQuery(name = "AseoParamArea.findByModificado", query = "SELECT a FROM AseoParamArea a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AseoParamArea.findByEstadoReg", query = "SELECT a FROM AseoParamArea a WHERE a.estadoReg = :estadoReg")})
public class AseoParamArea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aseo_param_area")
    private Integer idAseoParamArea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseoParamArea", fetch = FetchType.LAZY)
    private List<Aseo> aseoList;
    @OneToMany(mappedBy = "aseoParamArea", fetch = FetchType.LAZY)
    private List<AseoBano> aseoBanoList;
    @Size(min = 1, max = 100)
    @Column(name = "hash_string")
    private String hashString;
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
    @JoinColumn(name = "id_cable_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CableEstacion idCableEstacion;

    @OneToMany(mappedBy = "idAseoParamArea", fetch = FetchType.LAZY)
    private List<NovedadInfrastruc> novedadInfrastrucList;

    public AseoParamArea() {
    }

    public AseoParamArea(Integer idAseoParamArea) {
        this.idAseoParamArea = idAseoParamArea;
    }

    public AseoParamArea(Integer idAseoParamArea, String codigo) {
        this.idAseoParamArea = idAseoParamArea;
        this.codigo = codigo;
    }

    public AseoParamArea(Integer idAseoParamArea, String codigo, String nombre, String hashString, String username, Date creado, int estadoReg) {
        this.idAseoParamArea = idAseoParamArea;
        this.codigo = codigo;
        this.nombre = nombre;
        this.hashString = hashString;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAseoParamArea() {
        return idAseoParamArea;
    }

    public void setIdAseoParamArea(Integer idAseoParamArea) {
        this.idAseoParamArea = idAseoParamArea;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getHashString() {
        return hashString;
    }

    public void setHashString(String hashString) {
        this.hashString = hashString;
    }

    @XmlTransient
    public List<Aseo> getAseoList() {
        return aseoList;
    }

    public void setAseoList(List<Aseo> aseoList) {
        this.aseoList = aseoList;
    }

    @XmlTransient
    public List<AseoBano> getAseoBanoList() {
        return aseoBanoList;
    }

    public void setAseoBanoList(List<AseoBano> aseoBanoList) {
        this.aseoBanoList = aseoBanoList;
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

    public CableEstacion getIdCableEstacion() {
        return idCableEstacion;
    }

    public void setIdCableEstacion(CableEstacion idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    public List<NovedadInfrastruc> getNovedadInfrastrucList() {
        return novedadInfrastrucList;
    }

    public void setNovedadInfrastrucList(List<NovedadInfrastruc> novedadInfrastrucList) {
        this.novedadInfrastrucList = novedadInfrastrucList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAseoParamArea != null ? idAseoParamArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AseoParamArea)) {
            return false;
        }
        AseoParamArea other = (AseoParamArea) object;
        if ((this.idAseoParamArea == null && other.idAseoParamArea != null) || (this.idAseoParamArea != null && !this.idAseoParamArea.equals(other.idAseoParamArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AseoParamArea[ idAseoParamArea=" + idAseoParamArea + " ]";
    }

}
