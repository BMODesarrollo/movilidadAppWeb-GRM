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
 * @author cesar
 */
@Entity
@Table(name = "prg_solicitud_licencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgSolicitudLicencia.findAll", query = "SELECT p FROM PrgSolicitudLicencia p"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByIdPrgSolicitudLicencia", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.idPrgSolicitudLicencia = :idPrgSolicitudLicencia"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByDescripcion", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByDesde", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.desde = :desde"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByHasta", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.hasta = :hasta"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByNumeroDiasAprobado", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.numeroDiasAprobado = :numeroDiasAprobado"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByAprobadoDesde", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.aprobadoDesde = :aprobadoDesde"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByAprobadoHasta", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.aprobadoHasta = :aprobadoHasta"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByNota", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.nota = :nota"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByUserAprueba", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.userAprueba = :userAprueba"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByCreado", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByModificado", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgSolicitudLicencia.findByEstadoReg", query = "SELECT p FROM PrgSolicitudLicencia p WHERE p.estadoReg = :estadoReg")})
public class PrgSolicitudLicencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_solicitud_licencia")
    private Integer idPrgSolicitudLicencia;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Column(name = "numero_dias_aprobado")
    private Integer numeroDiasAprobado;
    @Column(name = "aprobado_desde")
    @Temporal(TemporalType.DATE)
    private Date aprobadoDesde;
    @Column(name = "aprobado_hasta")
    @Temporal(TemporalType.DATE)
    private Date aprobadoHasta;
    @Size(max = 255)
    @Column(name = "nota")
    private String nota;
    @Column(name = "consecutivo")
    private Integer consecutivo;
    @Size(max = 15)
    @Column(name = "user_aprueba")
    private String userAprueba;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_prg_solicitud_motivo", referencedColumnName = "id_prg_solicitud_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgSolicitudMotivo idPrgSolicitudMotivo;
    @JoinColumn(name = "id_prg_token", referencedColumnName = "id_prg_token")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PrgToken idPrgToken;

    public PrgSolicitudLicencia() {
    }

    public PrgSolicitudLicencia(Integer idPrgSolicitudLicencia) {
        this.idPrgSolicitudLicencia = idPrgSolicitudLicencia;
    }

    public Integer getIdPrgSolicitudLicencia() {
        return idPrgSolicitudLicencia;
    }

    public void setIdPrgSolicitudLicencia(Integer idPrgSolicitudLicencia) {
        this.idPrgSolicitudLicencia = idPrgSolicitudLicencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Integer getNumeroDiasAprobado() {
        return numeroDiasAprobado;
    }

    public void setNumeroDiasAprobado(Integer numeroDiasAprobado) {
        this.numeroDiasAprobado = numeroDiasAprobado;
    }

    public Date getAprobadoDesde() {
        return aprobadoDesde;
    }

    public void setAprobadoDesde(Date aprobadoDesde) {
        this.aprobadoDesde = aprobadoDesde;
    }

    public Date getAprobadoHasta() {
        return aprobadoHasta;
    }

    public void setAprobadoHasta(Date aprobadoHasta) {
        this.aprobadoHasta = aprobadoHasta;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getUserAprueba() {
        return userAprueba;
    }

    public void setUserAprueba(String userAprueba) {
        this.userAprueba = userAprueba;
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

    public PrgSolicitudMotivo getIdPrgSolicitudMotivo() {
        return idPrgSolicitudMotivo;
    }

    public void setIdPrgSolicitudMotivo(PrgSolicitudMotivo idPrgSolicitudMotivo) {
        this.idPrgSolicitudMotivo = idPrgSolicitudMotivo;
    }

    public PrgToken getIdPrgToken() {
        return idPrgToken;
    }

    public void setIdPrgToken(PrgToken idPrgToken) {
        this.idPrgToken = idPrgToken;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgSolicitudLicencia != null ? idPrgSolicitudLicencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgSolicitudLicencia)) {
            return false;
        }
        PrgSolicitudLicencia other = (PrgSolicitudLicencia) object;
        if ((this.idPrgSolicitudLicencia == null && other.idPrgSolicitudLicencia != null) || (this.idPrgSolicitudLicencia != null && !this.idPrgSolicitudLicencia.equals(other.idPrgSolicitudLicencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgSolicitudLicencia[ idPrgSolicitudLicencia=" + idPrgSolicitudLicencia + " ]";
    }

}
