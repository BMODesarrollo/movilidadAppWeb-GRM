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
@Table(name = "generica_pm_novedad_excluir")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmNovedadExcluir.findAll", query = "SELECT g FROM GenericaPmNovedadExcluir g"),
    @NamedQuery(name = "GenericaPmNovedadExcluir.findByIdgenericaPmNovedadExcluir", query = "SELECT g FROM GenericaPmNovedadExcluir g WHERE g.idgenericaPmNovedadExcluir = :idgenericaPmNovedadExcluir"),
    @NamedQuery(name = "GenericaPmNovedadExcluir.findByActivo", query = "SELECT g FROM GenericaPmNovedadExcluir g WHERE g.activo = :activo"),
    @NamedQuery(name = "GenericaPmNovedadExcluir.findByUsername", query = "SELECT g FROM GenericaPmNovedadExcluir g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaPmNovedadExcluir.findByCreado", query = "SELECT g FROM GenericaPmNovedadExcluir g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPmNovedadExcluir.findByModificado", query = "SELECT g FROM GenericaPmNovedadExcluir g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPmNovedadExcluir.findByEstadoReg", query = "SELECT g FROM GenericaPmNovedadExcluir g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmNovedadExcluir implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgenerica_pm_novedad_excluir")
    private Integer idgenericaPmNovedadExcluir;
    @Column(name = "activo")
    private Integer activo;
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
    @JoinColumn(name = "id_generica_tipo_detalle", referencedColumnName = "id_generica_tipo_detalle")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenericaTipoDetalles idGenericaTipoDetalle;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaPmNovedadExcluir() {
    }

    public GenericaPmNovedadExcluir(Integer idgenericaPmNovedadExcluir) {
        this.idgenericaPmNovedadExcluir = idgenericaPmNovedadExcluir;
    }

    public Integer getIdgenericaPmNovedadExcluir() {
        return idgenericaPmNovedadExcluir;
    }

    public void setIdgenericaPmNovedadExcluir(Integer idgenericaPmNovedadExcluir) {
        this.idgenericaPmNovedadExcluir = idgenericaPmNovedadExcluir;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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

    public GenericaTipoDetalles getIdGenericaTipoDetalle() {
        return idGenericaTipoDetalle;
    }

    public void setIdGenericaTipoDetalle(GenericaTipoDetalles idGenericaTipoDetalle) {
        this.idGenericaTipoDetalle = idGenericaTipoDetalle;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgenericaPmNovedadExcluir != null ? idgenericaPmNovedadExcluir.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmNovedadExcluir)) {
            return false;
        }
        GenericaPmNovedadExcluir other = (GenericaPmNovedadExcluir) object;
        if ((this.idgenericaPmNovedadExcluir == null && other.idgenericaPmNovedadExcluir != null) || (this.idgenericaPmNovedadExcluir != null && !this.idgenericaPmNovedadExcluir.equals(other.idgenericaPmNovedadExcluir))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmNovedadExcluir[ idgenericaPmNovedadExcluir=" + idgenericaPmNovedadExcluir + " ]";
    }
    
}
