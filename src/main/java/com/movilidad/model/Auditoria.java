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
 * @author solucionesit
 */
@Entity
@Table(name = "auditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auditoria.findAll", query = "SELECT a FROM Auditoria a")
    ,
    @NamedQuery(name = "Auditoria.findByIdAuditoria", query = "SELECT a FROM Auditoria a WHERE a.idAuditoria = :idAuditoria")
    ,
    @NamedQuery(name = "Auditoria.findByCodigo", query = "SELECT a FROM Auditoria a WHERE a.codigo = :codigo")
    ,
    @NamedQuery(name = "Auditoria.findByNombre", query = "SELECT a FROM Auditoria a WHERE a.nombre = :nombre")
    ,
    @NamedQuery(name = "Auditoria.findByUsername", query = "SELECT a FROM Auditoria a WHERE a.username = :username")
    ,
    @NamedQuery(name = "Auditoria.findByCreado", query = "SELECT a FROM Auditoria a WHERE a.creado = :creado")
    ,
    @NamedQuery(name = "Auditoria.findByModificado", query = "SELECT a FROM Auditoria a WHERE a.modificado = :modificado")
    ,
    @NamedQuery(name = "Auditoria.findByEstadoReg", query = "SELECT a FROM Auditoria a WHERE a.estadoReg = :estadoReg")})
public class Auditoria implements Serializable {

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "idAuditoria", fetch = FetchType.LAZY)
    private List<AuditoriaCosto> auditoriaCostoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoria", fetch = FetchType.LAZY)
    private List<AuditoriaRealizadoPor> auditoriaRealizadoPorList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria")
    private Integer idAuditoria;
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoria", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AuditoriaPreguntaRelacion> auditoriaPreguntaRelacionList;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_auditoria_encabezado", referencedColumnName = "id_auditoria_encabezado")
    @ManyToOne(fetch = FetchType.LAZY)
    private AuditoriaEncabezado idAuditoriaEncabezado;
    
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public Auditoria() {
    }

    public Auditoria(Integer idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public Auditoria(Integer idAuditoria, String username, Date creado, int estadoReg) {
        this.idAuditoria = idAuditoria;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Integer idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<AuditoriaPreguntaRelacion> getAuditoriaPreguntaRelacionList() {
        return auditoriaPreguntaRelacionList;
    }

    public void setAuditoriaPreguntaRelacionList(List<AuditoriaPreguntaRelacion> auditoriaPreguntaRelacionList) {
        this.auditoriaPreguntaRelacionList = auditoriaPreguntaRelacionList;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public AuditoriaEncabezado getIdAuditoriaEncabezado() {
        return idAuditoriaEncabezado;
    }

    public void setIdAuditoriaEncabezado(AuditoriaEncabezado idAuditoriaEncabezado) {
        this.idAuditoriaEncabezado = idAuditoriaEncabezado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoria != null ? idAuditoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auditoria)) {
            return false;
        }
        Auditoria other = (Auditoria) object;
        if ((this.idAuditoria == null && other.idAuditoria != null) || (this.idAuditoria != null && !this.idAuditoria.equals(other.idAuditoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Auditoria[ idAuditoria=" + idAuditoria + " ]";
    }

    @XmlTransient
    public List<AuditoriaRealizadoPor> getAuditoriaRealizadoPorList() {
        return auditoriaRealizadoPorList;
    }

    public void setAuditoriaRealizadoPorList(List<AuditoriaRealizadoPor> auditoriaRealizadoPorList) {
        this.auditoriaRealizadoPorList = auditoriaRealizadoPorList;
    }

    @XmlTransient
    public List<AuditoriaCosto> getAuditoriaCostoList() {
        return auditoriaCostoList;
    }

    public void setAuditoriaCostoList(List<AuditoriaCosto> auditoriaCostoList) {
        this.auditoriaCostoList = auditoriaCostoList;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

}
