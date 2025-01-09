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
 * @author solucionesit
 */
@Entity
@Table(name = "nomina_autorizacion_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NominaAutorizacionDet.findAll", query = "SELECT n FROM NominaAutorizacionDet n"),
    @NamedQuery(name = "NominaAutorizacionDet.findByIdNominaAutorizacionDet", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.idNominaAutorizacionDet = :idNominaAutorizacionDet"),
    @NamedQuery(name = "NominaAutorizacionDet.findByDiurnas", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.diurnas = :diurnas"),
    @NamedQuery(name = "NominaAutorizacionDet.findByNocturnas", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.nocturnas = :nocturnas"),
    @NamedQuery(name = "NominaAutorizacionDet.findByExtraDiurna", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.extraDiurna = :extraDiurna"),
    @NamedQuery(name = "NominaAutorizacionDet.findByExtraNocturna", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.extraNocturna = :extraNocturna"),
    @NamedQuery(name = "NominaAutorizacionDet.findByFestivoDiurno", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.festivoDiurno = :festivoDiurno"),
    @NamedQuery(name = "NominaAutorizacionDet.findByFestivoNocturno", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.festivoNocturno = :festivoNocturno"),
    @NamedQuery(name = "NominaAutorizacionDet.findByFestivoExtraDiurno", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.festivoExtraDiurno = :festivoExtraDiurno"),
    @NamedQuery(name = "NominaAutorizacionDet.findByFestivoExtraNocturno", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.festivoExtraNocturno = :festivoExtraNocturno"),
    @NamedQuery(name = "NominaAutorizacionDet.findByDominicalCompDiurnas", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.dominicalCompDiurnas = :dominicalCompDiurnas"),
    @NamedQuery(name = "NominaAutorizacionDet.findByDominicalCompNocturnas", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.dominicalCompNocturnas = :dominicalCompNocturnas"),
    @NamedQuery(name = "NominaAutorizacionDet.findByDominicalCompDiurnaExtra", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.dominicalCompDiurnaExtra = :dominicalCompDiurnaExtra"),
    @NamedQuery(name = "NominaAutorizacionDet.findByDominicalCompNocturnaExtra", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.dominicalCompNocturnaExtra = :dominicalCompNocturnaExtra"),
    @NamedQuery(name = "NominaAutorizacionDet.findByCodigoError", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.codigoError = :codigoError"),
    @NamedQuery(name = "NominaAutorizacionDet.findByUsername", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.username = :username"),
    @NamedQuery(name = "NominaAutorizacionDet.findByCreado", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.creado = :creado"),
    @NamedQuery(name = "NominaAutorizacionDet.findByModificado", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NominaAutorizacionDet.findByEstadoReg", query = "SELECT n FROM NominaAutorizacionDet n WHERE n.estadoReg = :estadoReg")})
public class NominaAutorizacionDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nomina_autorizacion_det")
    private Integer idNominaAutorizacionDet;

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "identificacion")
    private String identificacion;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_nomina_autorizacion", referencedColumnName = "id_nomina_autorizacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private NominaAutorizacion idNominaAutorizacion;
    @Column(name = "codigo_error")
    private Integer codigoError;
    @Column(name = "conceptos")
    private String conceptos;
    @Lob
    @Size(max = 65535)
    @Column(name = "mensaje_error")
    private String mensajeError;

    public NominaAutorizacionDet() {
    }

    public NominaAutorizacionDet(Integer idNominaAutorizacionDet) {
        this.idNominaAutorizacionDet = idNominaAutorizacionDet;
    }

    public NominaAutorizacionDet(Integer idNominaAutorizacionDet, String username, Date creado, int estadoReg) {
        this.idNominaAutorizacionDet = idNominaAutorizacionDet;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNominaAutorizacionDet() {
        return idNominaAutorizacionDet;
    }

    public void setIdNominaAutorizacionDet(Integer idNominaAutorizacionDet) {
        this.idNominaAutorizacionDet = idNominaAutorizacionDet;
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public NominaAutorizacion getIdNominaAutorizacion() {
        return idNominaAutorizacion;
    }

    public void setIdNominaAutorizacion(NominaAutorizacion idNominaAutorizacion) {
        this.idNominaAutorizacion = idNominaAutorizacion;
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
        hash += (idNominaAutorizacionDet != null ? idNominaAutorizacionDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaAutorizacionDet)) {
            return false;
        }
        NominaAutorizacionDet other = (NominaAutorizacionDet) object;
        if ((this.idNominaAutorizacionDet == null && other.idNominaAutorizacionDet != null) || (this.idNominaAutorizacionDet != null && !this.idNominaAutorizacionDet.equals(other.idNominaAutorizacionDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NominaAutorizacionDet[ idNominaAutorizacionDet=" + idNominaAutorizacionDet + " ]";
    }

}
