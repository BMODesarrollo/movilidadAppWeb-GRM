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
 * @author solucionesit
 */
@Entity
@Table(name = "disp_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispSistema.findAll", query = "SELECT d FROM DispSistema d")
    , @NamedQuery(name = "DispSistema.findByIdDispSistema", query = "SELECT d FROM DispSistema d WHERE d.idDispSistema = :idDispSistema")
    , @NamedQuery(name = "DispSistema.findByNombre", query = "SELECT d FROM DispSistema d WHERE d.nombre = :nombre AND d.idDispSistema<> :idDispSistema")
    , @NamedQuery(name = "DispSistema.findByUsername", query = "SELECT d FROM DispSistema d WHERE d.username = :username")
    , @NamedQuery(name = "DispSistema.findByCreado", query = "SELECT d FROM DispSistema d WHERE d.creado = :creado")
    , @NamedQuery(name = "DispSistema.findByModificado", query = "SELECT d FROM DispSistema d WHERE d.modificado = :modificado")
    , @NamedQuery(name = "DispSistema.findByEstadoReg", query = "SELECT d FROM DispSistema d WHERE d.estadoReg = :estadoReg")})
public class DispSistema implements Serializable {

    @OneToMany(mappedBy = "idDispSistema")
    private List<ChkComponenteFalla> chkComponenteFallaList;

    @OneToMany(mappedBy = "idDispSistema", fetch = FetchType.LAZY)
    private List<DispConciliacionDet> dispConciliacionDetList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDispSistema", fetch = FetchType.LAZY)
    private List<DispClasificacionNovedad> dispClasificacionNovedadList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_sistema")
    private Integer idDispSistema;
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
    @OneToMany(mappedBy = "idDispSistema", fetch = FetchType.LAZY)
    private List<DispCausaEntrada> dispCausaEntradaList;

    public DispSistema() {
    }

    public DispSistema(Integer idDispSistema) {
        this.idDispSistema = idDispSistema;
    }

    public DispSistema(Integer idDispSistema, String nombre, String username, Date creado, int estadoReg) {
        this.idDispSistema = idDispSistema;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDispSistema() {
        return idDispSistema;
    }

    public void setIdDispSistema(Integer idDispSistema) {
        this.idDispSistema = idDispSistema;
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

    @XmlTransient
    public List<DispCausaEntrada> getDispCausaEntradaList() {
        return dispCausaEntradaList;
    }

    public void setDispCausaEntradaList(List<DispCausaEntrada> dispCausaEntradaList) {
        this.dispCausaEntradaList = dispCausaEntradaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispSistema != null ? idDispSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispSistema)) {
            return false;
        }
        DispSistema other = (DispSistema) object;
        if ((this.idDispSistema == null && other.idDispSistema != null) || (this.idDispSistema != null && !this.idDispSistema.equals(other.idDispSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispSistema[ idDispSistema=" + idDispSistema + " ]";
    }

    @XmlTransient
    public List<DispClasificacionNovedad> getDispClasificacionNovedadList() {
        return dispClasificacionNovedadList;
    }

    public void setDispClasificacionNovedadList(List<DispClasificacionNovedad> dispClasificacionNovedadList) {
        this.dispClasificacionNovedadList = dispClasificacionNovedadList;
    }

    @XmlTransient
    public List<DispConciliacionDet> getDispConciliacionDetList() {
        return dispConciliacionDetList;
    }

    public void setDispConciliacionDetList(List<DispConciliacionDet> dispConciliacionDetList) {
        this.dispConciliacionDetList = dispConciliacionDetList;
    }

    @XmlTransient
    public List<ChkComponenteFalla> getChkComponenteFallaList() {
        return chkComponenteFallaList;
    }

    public void setChkComponenteFallaList(List<ChkComponenteFalla> chkComponenteFallaList) {
        this.chkComponenteFallaList = chkComponenteFallaList;
    }

}
