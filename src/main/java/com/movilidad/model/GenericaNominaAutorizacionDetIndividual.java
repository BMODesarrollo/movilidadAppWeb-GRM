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
@Table(name = "generica_nomina_autorizacion_det_individual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findAll", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByIdGenericaNominaAutorizacionDetIndividual", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.idGenericaNominaAutorizacionDetIndividual = :idGenericaNominaAutorizacionDetIndividual"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByIdentificacion", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.identificacion = :identificacion"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByDiurnas", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.diurnas = :diurnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByNocturnas", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.nocturnas = :nocturnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByExtraDiurna", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.extraDiurna = :extraDiurna"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByExtraNocturna", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.extraNocturna = :extraNocturna"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByFestivoDiurno", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.festivoDiurno = :festivoDiurno"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByFestivoNocturno", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.festivoNocturno = :festivoNocturno"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByFestivoExtraDiurno", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.festivoExtraDiurno = :festivoExtraDiurno"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByFestivoExtraNocturno", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.festivoExtraNocturno = :festivoExtraNocturno"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByDominicalCompDiurnas", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.dominicalCompDiurnas = :dominicalCompDiurnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByDominicalCompNocturnas", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.dominicalCompNocturnas = :dominicalCompNocturnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByDominicalCompDiurnaExtra", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.dominicalCompDiurnaExtra = :dominicalCompDiurnaExtra"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByDominicalCompNocturnaExtra", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.dominicalCompNocturnaExtra = :dominicalCompNocturnaExtra"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByCodigoError", query = "SELECT n FROM GenericaNominaAutorizacionDetIndividual n WHERE n.codigoError = :codigoError"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByUsername", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByCreado", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByModificado", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaNominaAutorizacionDetIndividual.findByEstadoReg", query = "SELECT g FROM GenericaNominaAutorizacionDetIndividual g WHERE g.estadoReg = :estadoReg")})
public class GenericaNominaAutorizacionDetIndividual implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_nomina_autorizacion_det_individual")
    private Integer idGenericaNominaAutorizacionDetIndividual;
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
    @JoinColumn(name = "id_generica_nomina_autorizacion_individual", referencedColumnName = "id_generica_nomina_autorizacion_individual")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaNominaAutorizacionIndividual idGenericaNominaAutorizacionIndividual;
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

    public GenericaNominaAutorizacionDetIndividual() {
    }

    public GenericaNominaAutorizacionDetIndividual(Integer idGenericaNominaAutorizacionDetIndividual) {
        this.idGenericaNominaAutorizacionDetIndividual = idGenericaNominaAutorizacionDetIndividual;
    }

    public GenericaNominaAutorizacionDetIndividual(Integer idGenericaNominaAutorizacionDetIndividual, String username, Date creado, int estadoReg) {
        this.idGenericaNominaAutorizacionDetIndividual = idGenericaNominaAutorizacionDetIndividual;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaNominaAutorizacionDetIndividual() {
        return idGenericaNominaAutorizacionDetIndividual;
    }

    public void setIdGenericaNominaAutorizacionDetIndividual(Integer idGenericaNominaAutorizacionDetIndividual) {
        this.idGenericaNominaAutorizacionDetIndividual = idGenericaNominaAutorizacionDetIndividual;
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

    public GenericaNominaAutorizacionIndividual getIdGenericaNominaAutorizacionIndividual() {
        return idGenericaNominaAutorizacionIndividual;
    }

    public void setIdGenericaNominaAutorizacionIndividual(GenericaNominaAutorizacionIndividual idGenericaNominaAutorizacionIndividual) {
        this.idGenericaNominaAutorizacionIndividual = idGenericaNominaAutorizacionIndividual;
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
        hash += (idGenericaNominaAutorizacionDetIndividual != null ? idGenericaNominaAutorizacionDetIndividual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaNominaAutorizacionDetIndividual)) {
            return false;
        }
        GenericaNominaAutorizacionDetIndividual other = (GenericaNominaAutorizacionDetIndividual) object;
        if ((this.idGenericaNominaAutorizacionDetIndividual == null && other.idGenericaNominaAutorizacionDetIndividual != null) || (this.idGenericaNominaAutorizacionDetIndividual != null && !this.idGenericaNominaAutorizacionDetIndividual.equals(other.idGenericaNominaAutorizacionDetIndividual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaNominaAutorizacionDetIndividual[ idGenericaNominaAutorizacionDetIndividual=" + idGenericaNominaAutorizacionDetIndividual + " ]";
    }

}
