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
import javax.validation.constraints.Size;
import javax.ws.rs.HEAD;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 *
 * @author Carlos Ballestas 
 */
@Entity
@Table(name = "mtto_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MttoEstado.findAll", query = "SELECT m FROM MttoEstado m"),
    @NamedQuery(name = "MttoEstado.findByIdMttoEstado", query = "SELECT m FROM MttoEstado m WHERE m.idMttoEstado = :idMttoEstado"),
    @NamedQuery(name = "MttoEstado.findByEstado", query = "SELECT m FROM MttoEstado m WHERE m.estado = :estado"),
    @NamedQuery(name = "MttoEstado.findByUsername", query = "SELECT m FROM MttoEstado m WHERE m.username = :username"),
    @NamedQuery(name = "MttoEstado.findByCreado", query = "SELECT m FROM MttoEstado m WHERE m.creado = :creado"),
    @NamedQuery(name = "MttoEstado.findByModificado", query = "SELECT m FROM MttoEstado m WHERE m.modificado = :modificado"),
    @NamedQuery(name = "MttoEstado.findByEstadoReg", query = "SELECT m FROM MttoEstado m WHERE m.estadoReg = :estadoReg")})
public class MttoEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mtto_estado")
    private Integer idMttoEstado;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Lob
    @Size(max = 65535)
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
    @OneToMany(mappedBy = "idMttoEstado", fetch = FetchType.LAZY)
    private List<MttoNovedad> mttoNovedadList;

    public MttoEstado() {
    }

    public MttoEstado(Integer idMttoEstado) {
        this.idMttoEstado = idMttoEstado;
    }

    public Integer getIdMttoEstado() {
        return idMttoEstado;
    }

    public void setIdMttoEstado(Integer idMttoEstado) {
        this.idMttoEstado = idMttoEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
    public List<MttoNovedad> getMttoNovedadList() {
        return mttoNovedadList;
    }

    public void setMttoNovedadList(List<MttoNovedad> mttoNovedadList) {
        this.mttoNovedadList = mttoNovedadList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMttoEstado != null ? idMttoEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MttoEstado)) {
            return false;
        }
        MttoEstado other = (MttoEstado) object;
        if ((this.idMttoEstado == null && other.idMttoEstado != null) || (this.idMttoEstado != null && !this.idMttoEstado.equals(other.idMttoEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MttoEstado[ idMttoEstado=" + idMttoEstado + " ]";
    }

}
