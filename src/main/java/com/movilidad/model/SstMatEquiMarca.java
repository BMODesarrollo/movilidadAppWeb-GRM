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
@Table(name = "sst_mat_equi_marca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstMatEquiMarca.findAll", query = "SELECT s FROM SstMatEquiMarca s"),
    @NamedQuery(name = "SstMatEquiMarca.findByIdSstMatEquiMarca", query = "SELECT s FROM SstMatEquiMarca s WHERE s.idSstMatEquiMarca = :idSstMatEquiMarca"),
    @NamedQuery(name = "SstMatEquiMarca.findByMarca", query = "SELECT s FROM SstMatEquiMarca s WHERE s.marca = :marca"),
    @NamedQuery(name = "SstMatEquiMarca.findByDescripcion", query = "SELECT s FROM SstMatEquiMarca s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SstMatEquiMarca.findByUsername", query = "SELECT s FROM SstMatEquiMarca s WHERE s.username = :username"),
    @NamedQuery(name = "SstMatEquiMarca.findByCreado", query = "SELECT s FROM SstMatEquiMarca s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstMatEquiMarca.findByModificado", query = "SELECT s FROM SstMatEquiMarca s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstMatEquiMarca.findByEstadoReg", query = "SELECT s FROM SstMatEquiMarca s WHERE s.estadoReg = :estadoReg")})
public class SstMatEquiMarca implements Serializable {
    
    @JoinColumn(name = "id_sst_empresa", referencedColumnName = "id_sst_empresa")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstEmpresa idSstEmpresa;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_mat_equi_marca")
    private Integer idSstMatEquiMarca;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
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
    @OneToMany(mappedBy = "idSstMatEquiMarca", fetch = FetchType.LAZY)
    private List<SstMatEqui> sstMatEquiList;

    public SstMatEquiMarca() {
    }

    public SstMatEquiMarca(Integer idSstMatEquiMarca) {
        this.idSstMatEquiMarca = idSstMatEquiMarca;
    }

    public Integer getIdSstMatEquiMarca() {
        return idSstMatEquiMarca;
    }

    public void setIdSstMatEquiMarca(Integer idSstMatEquiMarca) {
        this.idSstMatEquiMarca = idSstMatEquiMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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
    public List<SstMatEqui> getSstMatEquiList() {
        return sstMatEquiList;
    }

    public void setSstMatEquiList(List<SstMatEqui> sstMatEquiList) {
        this.sstMatEquiList = sstMatEquiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstMatEquiMarca != null ? idSstMatEquiMarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstMatEquiMarca)) {
            return false;
        }
        SstMatEquiMarca other = (SstMatEquiMarca) object;
        if ((this.idSstMatEquiMarca == null && other.idSstMatEquiMarca != null) || (this.idSstMatEquiMarca != null && !this.idSstMatEquiMarca.equals(other.idSstMatEquiMarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstMatEquiMarca[ idSstMatEquiMarca=" + idSstMatEquiMarca + " ]";
    }

    public SstEmpresa getIdSstEmpresa() {
        return idSstEmpresa;
    }

    public void setIdSstEmpresa(SstEmpresa idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }
    
}
