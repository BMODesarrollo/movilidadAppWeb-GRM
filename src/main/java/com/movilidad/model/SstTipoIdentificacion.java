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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "sst_tipo_identificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstTipoIdentificacion.findAll", query = "SELECT s FROM SstTipoIdentificacion s")
    , @NamedQuery(name = "SstTipoIdentificacion.findByIdSstTipoIdentificacion", query = "SELECT s FROM SstTipoIdentificacion s WHERE s.idSstTipoIdentificacion = :idSstTipoIdentificacion")
    , @NamedQuery(name = "SstTipoIdentificacion.findByNombre", query = "SELECT s FROM SstTipoIdentificacion s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "SstTipoIdentificacion.findByDescripcion", query = "SELECT s FROM SstTipoIdentificacion s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "SstTipoIdentificacion.findByUsername", query = "SELECT s FROM SstTipoIdentificacion s WHERE s.username = :username")
    , @NamedQuery(name = "SstTipoIdentificacion.findByCreado", query = "SELECT s FROM SstTipoIdentificacion s WHERE s.creado = :creado")
    , @NamedQuery(name = "SstTipoIdentificacion.findByModificado", query = "SELECT s FROM SstTipoIdentificacion s WHERE s.modificado = :modificado")
    , @NamedQuery(name = "SstTipoIdentificacion.findByEstadoReg", query = "SELECT s FROM SstTipoIdentificacion s WHERE s.estadoReg = :estadoReg")})
public class SstTipoIdentificacion implements Serializable {
    @OneToMany(mappedBy = "idSstTipoIdentificacion", fetch = FetchType.LAZY)
    private List<SstEmpresaVisitante> sstEmpresaVisitanteList;
    @OneToMany(mappedBy = "idSstTipoIdentificacionRepresentante", fetch = FetchType.LAZY)
    private List<SstEmpresa> sstEmpresaList;
    @OneToMany(mappedBy = "idSstTipoIdentificacionResponsable", fetch = FetchType.LAZY)
    private List<SstEmpresa> sstEmpresaList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_tipo_identificacion")
    private Integer idSstTipoIdentificacion;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
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

    public SstTipoIdentificacion() {
    }

    public SstTipoIdentificacion(Integer idSstTipoIdentificacion) {
        this.idSstTipoIdentificacion = idSstTipoIdentificacion;
    }

    public Integer getIdSstTipoIdentificacion() {
        return idSstTipoIdentificacion;
    }

    public void setIdSstTipoIdentificacion(Integer idSstTipoIdentificacion) {
        this.idSstTipoIdentificacion = idSstTipoIdentificacion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstTipoIdentificacion != null ? idSstTipoIdentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstTipoIdentificacion)) {
            return false;
        }
        SstTipoIdentificacion other = (SstTipoIdentificacion) object;
        if ((this.idSstTipoIdentificacion == null && other.idSstTipoIdentificacion != null) || (this.idSstTipoIdentificacion != null && !this.idSstTipoIdentificacion.equals(other.idSstTipoIdentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstTipoIdentificacion[ idSstTipoIdentificacion=" + idSstTipoIdentificacion + " ]";
    }

    @XmlTransient
    public List<SstEmpresa> getSstEmpresaList() {
        return sstEmpresaList;
    }

    public void setSstEmpresaList(List<SstEmpresa> sstEmpresaList) {
        this.sstEmpresaList = sstEmpresaList;
    }

    @XmlTransient
    public List<SstEmpresa> getSstEmpresaList1() {
        return sstEmpresaList1;
    }

    public void setSstEmpresaList1(List<SstEmpresa> sstEmpresaList1) {
        this.sstEmpresaList1 = sstEmpresaList1;
    }

    @XmlTransient
    public List<SstEmpresaVisitante> getSstEmpresaVisitanteList() {
        return sstEmpresaVisitanteList;
    }

    public void setSstEmpresaVisitanteList(List<SstEmpresaVisitante> sstEmpresaVisitanteList) {
        this.sstEmpresaVisitanteList = sstEmpresaVisitanteList;
    }
    
}
