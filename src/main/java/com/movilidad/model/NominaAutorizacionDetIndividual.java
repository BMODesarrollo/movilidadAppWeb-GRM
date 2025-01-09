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
@Table(name = "nomina_autorizacion_det_individual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findAll", query = "SELECT n FROM NominaAutorizacionDetIndividual n"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByIdNominaAutorizacionDetIndividual", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.idNominaAutorizacionDetIndividual = :idNominaAutorizacionDetIndividual"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByIdentificacion", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.identificacion = :identificacion"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByDiurnas", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.diurnas = :diurnas"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByNocturnas", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.nocturnas = :nocturnas"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByExtraDiurna", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.extraDiurna = :extraDiurna"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByExtraNocturna", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.extraNocturna = :extraNocturna"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByFestivoDiurno", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.festivoDiurno = :festivoDiurno"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByFestivoNocturno", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.festivoNocturno = :festivoNocturno"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByFestivoExtraDiurno", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.festivoExtraDiurno = :festivoExtraDiurno"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByFestivoExtraNocturno", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.festivoExtraNocturno = :festivoExtraNocturno"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByDominicalCompDiurnas", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.dominicalCompDiurnas = :dominicalCompDiurnas"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByDominicalCompNocturnas", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.dominicalCompNocturnas = :dominicalCompNocturnas"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByDominicalCompDiurnaExtra", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.dominicalCompDiurnaExtra = :dominicalCompDiurnaExtra"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByDominicalCompNocturnaExtra", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.dominicalCompNocturnaExtra = :dominicalCompNocturnaExtra"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByCodigoError", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.codigoError = :codigoError"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByUsername", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.username = :username"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByCreado", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.creado = :creado"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByModificado", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NominaAutorizacionDetIndividual.findByEstadoReg", query = "SELECT n FROM NominaAutorizacionDetIndividual n WHERE n.estadoReg = :estadoReg")})
public class NominaAutorizacionDetIndividual implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nomina_autorizacion_det_individual")
    private Integer idNominaAutorizacionDetIndividual;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_nomina_autorizacion_individual", referencedColumnName = "id_nomina_autorizacion_individual")
    @ManyToOne(fetch = FetchType.LAZY)
    private NominaAutorizacionIndividual idNominaAutorizacionIndividual;
    @Column(name = "codigo_error")
    private Integer codigoError;
    @Lob
    @Size(max = 65535)
    @Column(name = "mensaje_error")
    private String mensajeError;
    @Column(name = "conceptos")
    private String conceptos;

    public NominaAutorizacionDetIndividual() {
    }

    public NominaAutorizacionDetIndividual(Integer idNominaAutorizacionDetIndividual) {
        this.idNominaAutorizacionDetIndividual = idNominaAutorizacionDetIndividual;
    }

    public NominaAutorizacionDetIndividual(Integer idNominaAutorizacionDetIndividual, String username, Date creado, int estadoReg) {
        this.idNominaAutorizacionDetIndividual = idNominaAutorizacionDetIndividual;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNominaAutorizacionDetIndividual() {
        return idNominaAutorizacionDetIndividual;
    }

    public void setIdNominaAutorizacionDetIndividual(Integer idNominaAutorizacionDetIndividual) {
        this.idNominaAutorizacionDetIndividual = idNominaAutorizacionDetIndividual;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public NominaAutorizacionIndividual getIdNominaAutorizacionIndividual() {
        return idNominaAutorizacionIndividual;
    }

    public void setIdNominaAutorizacionIndividual(NominaAutorizacionIndividual idNominaAutorizacionIndividual) {
        this.idNominaAutorizacionIndividual = idNominaAutorizacionIndividual;
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
        hash += (idNominaAutorizacionDetIndividual != null ? idNominaAutorizacionDetIndividual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaAutorizacionDetIndividual)) {
            return false;
        }
        NominaAutorizacionDetIndividual other = (NominaAutorizacionDetIndividual) object;
        if ((this.idNominaAutorizacionDetIndividual == null && other.idNominaAutorizacionDetIndividual != null) || (this.idNominaAutorizacionDetIndividual != null && !this.idNominaAutorizacionDetIndividual.equals(other.idNominaAutorizacionDetIndividual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NominaAutorizacionDetIndividual[ idNominaAutorizacionDetIndividual=" + idNominaAutorizacionDetIndividual + " ]";
    }

}
