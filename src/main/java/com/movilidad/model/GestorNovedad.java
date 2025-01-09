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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "gestor_novedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestorNovedad.findAll", query = "SELECT g FROM GestorNovedad g"),
    @NamedQuery(name = "GestorNovedad.findByIdGestorNovedad", query = "SELECT g FROM GestorNovedad g WHERE g.idGestorNovedad = :idGestorNovedad"),
    @NamedQuery(name = "GestorNovedad.findByDesde", query = "SELECT g FROM GestorNovedad g WHERE g.desde = :desde"),
    @NamedQuery(name = "GestorNovedad.findByHasta", query = "SELECT g FROM GestorNovedad g WHERE g.hasta = :hasta"),
    @NamedQuery(name = "GestorNovedad.findByLiquidado", query = "SELECT g FROM GestorNovedad g WHERE g.liquidado = :liquidado"),
    @NamedQuery(name = "GestorNovedad.findByUsername", query = "SELECT g FROM GestorNovedad g WHERE g.username = :username"),
    @NamedQuery(name = "GestorNovedad.findByCreado", query = "SELECT g FROM GestorNovedad g WHERE g.creado = :creado"),
    @NamedQuery(name = "GestorNovedad.findByModificado", query = "SELECT g FROM GestorNovedad g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GestorNovedad.findByEstadoReg", query = "SELECT g FROM GestorNovedad g WHERE g.estadoReg = :estadoReg")})
public class GestorNovedad implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGestorNovedad", fetch = FetchType.LAZY)
    private List<GestorNovReqDet> gestorNovReqDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGestorNovedad", fetch = FetchType.LAZY)
    private List<GestorNovDet> gestorNovDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGestorNovedad", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<GestorNovDetSemana> gestorNovDetSemanaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGestorNovedad", fetch = FetchType.LAZY)
    private List<GestorNovReqSemana> gestorNovReqSemanaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGestorNovedad", fetch = FetchType.LAZY)
    private List<GestorNovParamDet> gestorNovParamDetList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestor_novedad")
    private Integer idGestorNovedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "liquidado")
    private int liquidado;
    @Column(name = "fecha_liquidado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLiquidado;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public GestorNovedad() {
    }

    public GestorNovedad(Integer idGestorNovedad) {
        this.idGestorNovedad = idGestorNovedad;
    }

    public GestorNovedad(Integer idGestorNovedad, Date desde, Date hasta, int liquidado, String username, Date creado, int estadoReg) {
        this.idGestorNovedad = idGestorNovedad;
        this.desde = desde;
        this.hasta = hasta;
        this.liquidado = liquidado;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGestorNovedad() {
        return idGestorNovedad;
    }

    public void setIdGestorNovedad(Integer idGestorNovedad) {
        this.idGestorNovedad = idGestorNovedad;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public int getLiquidado() {
        return liquidado;
    }

    public void setLiquidado(int liquidado) {
        this.liquidado = liquidado;
    }

    public Date getFechaLiquidado() {
        return fechaLiquidado;
    }

    public void setFechaLiquidado(Date fechaLiquidado) {
        this.fechaLiquidado = fechaLiquidado;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestorNovedad != null ? idGestorNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestorNovedad)) {
            return false;
        }
        GestorNovedad other = (GestorNovedad) object;
        if ((this.idGestorNovedad == null && other.idGestorNovedad != null) || (this.idGestorNovedad != null && !this.idGestorNovedad.equals(other.idGestorNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GestorNovedad[ idGestorNovedad=" + idGestorNovedad + " ]";
    }

    @XmlTransient
    public List<GestorNovReqDet> getGestorNovReqDetList() {
        return gestorNovReqDetList;
    }

    public void setGestorNovReqDetList(List<GestorNovReqDet> gestorNovReqDetList) {
        this.gestorNovReqDetList = gestorNovReqDetList;
    }

    @XmlTransient
    public List<GestorNovDet> getGestorNovDetList() {
        return gestorNovDetList;
    }

    public void setGestorNovDetList(List<GestorNovDet> gestorNovDetList) {
        this.gestorNovDetList = gestorNovDetList;
    }

    @XmlTransient
    public List<GestorNovDetSemana> getGestorNovDetSemanaList() {
        return gestorNovDetSemanaList;
    }

    public void setGestorNovDetSemanaList(List<GestorNovDetSemana> gestorNovDetSemanaList) {
        this.gestorNovDetSemanaList = gestorNovDetSemanaList;
    }

    @XmlTransient
    public List<GestorNovReqSemana> getGestorNovReqSemanaList() {
        return gestorNovReqSemanaList;
    }

    public void setGestorNovReqSemanaList(List<GestorNovReqSemana> gestorNovReqSemanaList) {
        this.gestorNovReqSemanaList = gestorNovReqSemanaList;
    }

    @XmlTransient
    public List<GestorNovParamDet> getGestorNovParamDetList() {
        return gestorNovParamDetList;
    }

    public void setGestorNovParamDetList(List<GestorNovParamDet> gestorNovParamDetList) {
        this.gestorNovParamDetList = gestorNovParamDetList;
    }

}
