/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "generica_nomina_autorizacion_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findAll", query = "SELECT g FROM GenericaNominaAutorizacionDet g"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByIdGenericaNominaAutorizacionDet", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.idGenericaNominaAutorizacionDet = :idGenericaNominaAutorizacionDet"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByIdentificacion", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.identificacion = :identificacion"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByDiurnas", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.diurnas = :diurnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByNocturnas", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.nocturnas = :nocturnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByExtraDiurna", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.extraDiurna = :extraDiurna"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByExtraNocturna", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.extraNocturna = :extraNocturna"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByFestivoDiurno", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.festivoDiurno = :festivoDiurno"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByFestivoNocturno", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.festivoNocturno = :festivoNocturno"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByFestivoExtraDiurno", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.festivoExtraDiurno = :festivoExtraDiurno"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByFestivoExtraNocturno", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.festivoExtraNocturno = :festivoExtraNocturno"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByDominicalCompDiurnas", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.dominicalCompDiurnas = :dominicalCompDiurnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByDominicalCompNocturnas", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.dominicalCompNocturnas = :dominicalCompNocturnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByDominicalCompDiurnaExtra", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.dominicalCompDiurnaExtra = :dominicalCompDiurnaExtra"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByDominicalCompNocturnaExtra", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.dominicalCompNocturnaExtra = :dominicalCompNocturnaExtra"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByIdGopUnidadFuncional", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.idGopUnidadFuncional = :idGopUnidadFuncional"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByCodigoError", query = "SELECT n FROM GenericaNominaAutorizacionDet n WHERE n.codigoError = :codigoError"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByUsername", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByCreado", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByModificado", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaNominaAutorizacionDet.findByEstadoReg", query = "SELECT g FROM GenericaNominaAutorizacionDet g WHERE g.estadoReg = :estadoReg")})
public class GenericaNominaAutorizacionDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_nomina_autorizacion_det")
    private Integer idGenericaNominaAutorizacionDet;
    @Size(max = 15)
    @Column(name = "identificacion")
    private String identificacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "diurnas")
    private BigDecimal diurnas;
    @Column(name = "nocturnas")
    private BigDecimal nocturnas;
    @Column(name = "extra_diurna")
    private BigDecimal extraDiurna;
    @Column(name = "extra_nocturna")
    private BigDecimal extraNocturna;
    @Column(name = "festivo_diurno")
    private BigDecimal festivoDiurno;
    @Column(name = "festivo_nocturno")
    private BigDecimal festivoNocturno;
    @Column(name = "festivo_extra_diurno")
    private BigDecimal festivoExtraDiurno;
    @Column(name = "festivo_extra_nocturno")
    private BigDecimal festivoExtraNocturno;
    @Column(name = "dominical_comp_diurnas")
    private BigDecimal dominicalCompDiurnas;
    @Column(name = "dominical_comp_nocturnas")
    private BigDecimal dominicalCompNocturnas;
    @Column(name = "dominical_comp_diurna_extra")
    private BigDecimal dominicalCompDiurnaExtra;
    @Column(name = "dominical_comp_nocturna_extra")
    private BigDecimal dominicalCompNocturnaExtra;
    @Column(name = "id_gop_unidad_funcional")
    private Integer idGopUnidadFuncional;
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
    @JoinColumn(name = "id_generica_nomina_autorizacion", referencedColumnName = "id_generica_nomina_autorizacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaNominaAutorizacion idGenericaNominaAutorizacion;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @Column(name = "codigo_error")
    private Integer codigoError;
    @Lob
    @Size(max = 65535)
    @Column(name = "mensaje_error")
    private String mensajeError;
    @Column(name = "conceptos")
    private String conceptos;

    public GenericaNominaAutorizacionDet() {
    }

    public GenericaNominaAutorizacionDet(Integer idGenericaNominaAutorizacionDet) {
        this.idGenericaNominaAutorizacionDet = idGenericaNominaAutorizacionDet;
    }

    public GenericaNominaAutorizacionDet(Integer idGenericaNominaAutorizacionDet, String username, Date creado, int estadoReg) {
        this.idGenericaNominaAutorizacionDet = idGenericaNominaAutorizacionDet;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaNominaAutorizacionDet() {
        return idGenericaNominaAutorizacionDet;
    }

    public void setIdGenericaNominaAutorizacionDet(Integer idGenericaNominaAutorizacionDet) {
        this.idGenericaNominaAutorizacionDet = idGenericaNominaAutorizacionDet;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public BigDecimal getDiurnas() {
        return diurnas;
    }

    public void setDiurnas(BigDecimal diurnas) {
        this.diurnas = diurnas;
    }

    public BigDecimal getNocturnas() {
        return nocturnas;
    }

    public void setNocturnas(BigDecimal nocturnas) {
        this.nocturnas = nocturnas;
    }

    public BigDecimal getExtraDiurna() {
        return extraDiurna;
    }

    public void setExtraDiurna(BigDecimal extraDiurna) {
        this.extraDiurna = extraDiurna;
    }

    public BigDecimal getExtraNocturna() {
        return extraNocturna;
    }

    public void setExtraNocturna(BigDecimal extraNocturna) {
        this.extraNocturna = extraNocturna;
    }

    public BigDecimal getFestivoDiurno() {
        return festivoDiurno;
    }

    public void setFestivoDiurno(BigDecimal festivoDiurno) {
        this.festivoDiurno = festivoDiurno;
    }

    public BigDecimal getFestivoNocturno() {
        return festivoNocturno;
    }

    public void setFestivoNocturno(BigDecimal festivoNocturno) {
        this.festivoNocturno = festivoNocturno;
    }

    public BigDecimal getFestivoExtraDiurno() {
        return festivoExtraDiurno;
    }

    public void setFestivoExtraDiurno(BigDecimal festivoExtraDiurno) {
        this.festivoExtraDiurno = festivoExtraDiurno;
    }

    public BigDecimal getFestivoExtraNocturno() {
        return festivoExtraNocturno;
    }

    public void setFestivoExtraNocturno(BigDecimal festivoExtraNocturno) {
        this.festivoExtraNocturno = festivoExtraNocturno;
    }

    public BigDecimal getDominicalCompDiurnas() {
        return dominicalCompDiurnas;
    }

    public void setDominicalCompDiurnas(BigDecimal dominicalCompDiurnas) {
        this.dominicalCompDiurnas = dominicalCompDiurnas;
    }

    public BigDecimal getDominicalCompNocturnas() {
        return dominicalCompNocturnas;
    }

    public void setDominicalCompNocturnas(BigDecimal dominicalCompNocturnas) {
        this.dominicalCompNocturnas = dominicalCompNocturnas;
    }

    public BigDecimal getDominicalCompDiurnaExtra() {
        return dominicalCompDiurnaExtra;
    }

    public void setDominicalCompDiurnaExtra(BigDecimal dominicalCompDiurnaExtra) {
        this.dominicalCompDiurnaExtra = dominicalCompDiurnaExtra;
    }

    public BigDecimal getDominicalCompNocturnaExtra() {
        return dominicalCompNocturnaExtra;
    }

    public void setDominicalCompNocturnaExtra(BigDecimal dominicalCompNocturnaExtra) {
        this.dominicalCompNocturnaExtra = dominicalCompNocturnaExtra;
    }

    public Integer getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
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

    public GenericaNominaAutorizacion getIdGenericaNominaAutorizacion() {
        return idGenericaNominaAutorizacion;
    }

    public void setIdGenericaNominaAutorizacion(GenericaNominaAutorizacion idGenericaNominaAutorizacion) {
        this.idGenericaNominaAutorizacion = idGenericaNominaAutorizacion;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public Integer getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(Integer codigoError) {
        this.codigoError = codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getConceptos() {
        return conceptos;
    }

    public void setConceptos(String conceptos) {
        this.conceptos = conceptos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaNominaAutorizacionDet != null ? idGenericaNominaAutorizacionDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaNominaAutorizacionDet)) {
            return false;
        }
        GenericaNominaAutorizacionDet other = (GenericaNominaAutorizacionDet) object;
        if ((this.idGenericaNominaAutorizacionDet == null && other.idGenericaNominaAutorizacionDet != null) || (this.idGenericaNominaAutorizacionDet != null && !this.idGenericaNominaAutorizacionDet.equals(other.idGenericaNominaAutorizacionDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaNominaAutorizacionDet[ idGenericaNominaAutorizacionDet=" + idGenericaNominaAutorizacionDet + " ]";
    }

}
