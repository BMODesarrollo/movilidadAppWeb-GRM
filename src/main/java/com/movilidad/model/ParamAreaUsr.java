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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "param_area_usr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamAreaUsr.findAll", query = "SELECT p FROM ParamAreaUsr p"),
    @NamedQuery(name = "ParamAreaUsr.findByIdParamAreaUsr", query = "SELECT p FROM ParamAreaUsr p WHERE p.idParamAreaUsr = :idParamAreaUsr"),
    @NamedQuery(name = "ParamAreaUsr.findByUsername", query = "SELECT p FROM ParamAreaUsr p WHERE p.username = :username"),
    @NamedQuery(name = "ParamAreaUsr.findByCreado", query = "SELECT p FROM ParamAreaUsr p WHERE p.creado = :creado"),
    @NamedQuery(name = "ParamAreaUsr.findByModificado", query = "SELECT p FROM ParamAreaUsr p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "ParamAreaUsr.findByEstadoReg", query = "SELECT p FROM ParamAreaUsr p WHERE p.estadoReg = :estadoReg")})
public class ParamAreaUsr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_area_usr")
    private Integer idParamAreaUsr;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_param_usr", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users idParamUsr;

    public ParamAreaUsr() {
    }

    public ParamAreaUsr(Integer idParamAreaUsr) {
        this.idParamAreaUsr = idParamAreaUsr;
    }

    public Integer getIdParamAreaUsr() {
        return idParamAreaUsr;
    }

    public void setIdParamAreaUsr(Integer idParamAreaUsr) {
        this.idParamAreaUsr = idParamAreaUsr;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public Users getIdParamUsr() {
        return idParamUsr;
    }

    public void setIdParamUsr(Users idParamUsr) {
        this.idParamUsr = idParamUsr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParamAreaUsr != null ? idParamAreaUsr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamAreaUsr)) {
            return false;
        }
        ParamAreaUsr other = (ParamAreaUsr) object;
        if ((this.idParamAreaUsr == null && other.idParamAreaUsr != null) || (this.idParamAreaUsr != null && !this.idParamAreaUsr.equals(other.idParamAreaUsr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ParamAreaUsr[ idParamAreaUsr=" + idParamAreaUsr + " ]";
    }
    
}
