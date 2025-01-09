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
 * @author solucionesit
 */
@Entity
@Table(name = "disp_causa_entrada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispCausaEntrada.findAll", query = "SELECT d FROM DispCausaEntrada d")
    , @NamedQuery(name = "DispCausaEntrada.findByIdDispCausaEntrada", query = "SELECT d FROM DispCausaEntrada d WHERE d.idDispCausaEntrada = :idDispCausaEntrada")
    , @NamedQuery(name = "DispCausaEntrada.findByNombre", query = "SELECT d FROM DispCausaEntrada d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispCausaEntrada.findByNombreByIdDispSistema",
            query = "SELECT d FROM DispCausaEntrada d WHERE d.nombre = :nombre AND d.idDispSistema.idDispSistema = :idDispSistema AND d.idDispCausaEntrada <> :id AND d.estadoReg = :estadoReg")
    , @NamedQuery(name = "DispCausaEntrada.findByUsername", query = "SELECT d FROM DispCausaEntrada d WHERE d.username = :username")
    , @NamedQuery(name = "DispCausaEntrada.findByCreado", query = "SELECT d FROM DispCausaEntrada d WHERE d.creado = :creado")
    , @NamedQuery(name = "DispCausaEntrada.findByModificado", query = "SELECT d FROM DispCausaEntrada d WHERE d.modificado = :modificado")
    , @NamedQuery(name = "DispCausaEntrada.findByEstadoReg", query = "SELECT d FROM DispCausaEntrada d WHERE d.estadoReg = :estadoReg")})
public class DispCausaEntrada implements Serializable {

    @OneToMany(mappedBy = "idDispCausaEntrada", fetch = FetchType.LAZY)
    private List<DispConciliacionDet> dispConciliacionDetList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDispCausaEntrada", fetch = FetchType.LAZY)
    private List<DispClasificacionNovedad> dispClasificacionNovedadList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_causa_entrada")
    private Integer idDispCausaEntrada;
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
    @JoinColumn(name = "id_disp_sistema", referencedColumnName = "id_disp_sistema")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DispSistema idDispSistema;

    public DispCausaEntrada() {
    }

    public DispCausaEntrada(Integer idDispCausaEntrada) {
        this.idDispCausaEntrada = idDispCausaEntrada;
    }

    public DispCausaEntrada(Integer idDispCausaEntrada, String nombre, String username, Date creado, int estadoReg) {
        this.idDispCausaEntrada = idDispCausaEntrada;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDispCausaEntrada() {
        return idDispCausaEntrada;
    }

    public void setIdDispCausaEntrada(Integer idDispCausaEntrada) {
        this.idDispCausaEntrada = idDispCausaEntrada;
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

    public DispSistema getIdDispSistema() {
        return idDispSistema;
    }

    public void setIdDispSistema(DispSistema idDispSistema) {
        this.idDispSistema = idDispSistema;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispCausaEntrada != null ? idDispCausaEntrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispCausaEntrada)) {
            return false;
        }
        DispCausaEntrada other = (DispCausaEntrada) object;
        if ((this.idDispCausaEntrada == null && other.idDispCausaEntrada != null) || (this.idDispCausaEntrada != null && !this.idDispCausaEntrada.equals(other.idDispCausaEntrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispCausaEntrada[ idDispCausaEntrada=" + idDispCausaEntrada + " ]";
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

}
