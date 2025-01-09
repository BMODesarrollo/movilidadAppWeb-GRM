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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sst_mat_equi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstMatEqui.findAll", query = "SELECT s FROM SstMatEqui s"),
    @NamedQuery(name = "SstMatEqui.findByIdSstMatEqui", query = "SELECT s FROM SstMatEqui s WHERE s.idSstMatEqui = :idSstMatEqui"),
    @NamedQuery(name = "SstMatEqui.findByNombre", query = "SELECT s FROM SstMatEqui s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SstMatEqui.findByUsername", query = "SELECT s FROM SstMatEqui s WHERE s.username = :username"),
    @NamedQuery(name = "SstMatEqui.findByCreado", query = "SELECT s FROM SstMatEqui s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstMatEqui.findByModificado", query = "SELECT s FROM SstMatEqui s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstMatEqui.findByEstadoReg", query = "SELECT s FROM SstMatEqui s WHERE s.estadoReg = :estadoReg")})
public class SstMatEqui implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_mat_equi")
    private Integer idSstMatEqui;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
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
    @JoinColumn(name = "id_sst_empresa", referencedColumnName = "id_sst_empresa")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstEmpresa idSstEmpresa;
    @OneToMany(mappedBy = "idSstMatEqui", fetch = FetchType.LAZY)
    private List<SstEsMatEquiDet> sstEsMatEquiDetList;
    @JoinColumn(name = "id_sst_mat_equi_marca", referencedColumnName = "id_sst_mat_equi_marca")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstMatEquiMarca idSstMatEquiMarca;
    @JoinColumn(name = "id_sst_mat_equi_tipo", referencedColumnName = "id_sst_mat_equi_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstMatEquiTipo idSstMatEquiTipo;

    public SstMatEqui() {
    }

    public SstMatEqui(Integer idSstMatEqui) {
        this.idSstMatEqui = idSstMatEqui;
    }

    public Integer getIdSstMatEqui() {
        return idSstMatEqui;
    }

    public void setIdSstMatEqui(Integer idSstMatEqui) {
        this.idSstMatEqui = idSstMatEqui;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public SstEmpresa getIdSstEmpresa() {
        return idSstEmpresa;
    }

    public void setIdSstEmpresa(SstEmpresa idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }

    @XmlTransient
    public List<SstEsMatEquiDet> getSstEsMatEquiDetList() {
        return sstEsMatEquiDetList;
    }

    public void setSstEsMatEquiDetList(List<SstEsMatEquiDet> sstEsMatEquiDetList) {
        this.sstEsMatEquiDetList = sstEsMatEquiDetList;
    }

    public SstMatEquiMarca getIdSstMatEquiMarca() {
        return idSstMatEquiMarca;
    }

    public void setIdSstMatEquiMarca(SstMatEquiMarca idSstMatEquiMarca) {
        this.idSstMatEquiMarca = idSstMatEquiMarca;
    }

    public SstMatEquiTipo getIdSstMatEquiTipo() {
        return idSstMatEquiTipo;
    }

    public void setIdSstMatEquiTipo(SstMatEquiTipo idSstMatEquiTipo) {
        this.idSstMatEquiTipo = idSstMatEquiTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstMatEqui != null ? idSstMatEqui.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstMatEqui)) {
            return false;
        }
        SstMatEqui other = (SstMatEqui) object;
        if ((this.idSstMatEqui == null && other.idSstMatEqui != null) || (this.idSstMatEqui != null && !this.idSstMatEqui.equals(other.idSstMatEqui))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstMatEqui[ idSstMatEqui=" + idSstMatEqui + " ]";
    }

}
