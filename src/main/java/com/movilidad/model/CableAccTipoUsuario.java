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
 * @author soluciones-it
 */
@Entity
@Table(name = "cable_acc_tipo_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableAccTipoUsuario.findAll", query = "SELECT c FROM CableAccTipoUsuario c")
    , @NamedQuery(name = "CableAccTipoUsuario.findByIdCableAccTipoUsuario", query = "SELECT c FROM CableAccTipoUsuario c WHERE c.idCableAccTipoUsuario = :idCableAccTipoUsuario")
    , @NamedQuery(name = "CableAccTipoUsuario.findByTipo", query = "SELECT c FROM CableAccTipoUsuario c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "CableAccTipoUsuario.findByDescripcion", query = "SELECT c FROM CableAccTipoUsuario c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "CableAccTipoUsuario.findByUsername", query = "SELECT c FROM CableAccTipoUsuario c WHERE c.username = :username")
    , @NamedQuery(name = "CableAccTipoUsuario.findByCreado", query = "SELECT c FROM CableAccTipoUsuario c WHERE c.creado = :creado")
    , @NamedQuery(name = "CableAccTipoUsuario.findByModificado", query = "SELECT c FROM CableAccTipoUsuario c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "CableAccTipoUsuario.findByEstadoReg", query = "SELECT c FROM CableAccTipoUsuario c WHERE c.estadoReg = :estadoReg")})
public class CableAccTipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_acc_tipo_usuario")
    private Integer idCableAccTipoUsuario;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
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
    @OneToMany(mappedBy = "idCableAccTpUsuario", fetch = FetchType.LAZY)
    private List<CableAccSiniestrado> cableAccSiniestradoList;

    public CableAccTipoUsuario() {
    }

    public CableAccTipoUsuario(Integer idCableAccTipoUsuario) {
        this.idCableAccTipoUsuario = idCableAccTipoUsuario;
    }

    public Integer getIdCableAccTipoUsuario() {
        return idCableAccTipoUsuario;
    }

    public void setIdCableAccTipoUsuario(Integer idCableAccTipoUsuario) {
        this.idCableAccTipoUsuario = idCableAccTipoUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    public List<CableAccSiniestrado> getCableAccSiniestradoList() {
        return cableAccSiniestradoList;
    }

    public void setCableAccSiniestradoList(List<CableAccSiniestrado> cableAccSiniestradoList) {
        this.cableAccSiniestradoList = cableAccSiniestradoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccTipoUsuario != null ? idCableAccTipoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccTipoUsuario)) {
            return false;
        }
        CableAccTipoUsuario other = (CableAccTipoUsuario) object;
        if ((this.idCableAccTipoUsuario == null && other.idCableAccTipoUsuario != null) || (this.idCableAccTipoUsuario != null && !this.idCableAccTipoUsuario.equals(other.idCableAccTipoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccTipoUsuario[ idCableAccTipoUsuario=" + idCableAccTipoUsuario + " ]";
    }
    
}
