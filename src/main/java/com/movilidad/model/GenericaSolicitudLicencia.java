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
@Table(name = "generica_solicitud_licencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaSolicitudLicencia.findAll", query = "SELECT g FROM GenericaSolicitudLicencia g")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByIdGenericaSolicitudLicencia", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.idGenericaSolicitudLicencia = :idGenericaSolicitudLicencia")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByDescripcion", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.descripcion = :descripcion")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByDesde", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.desde = :desde")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByHasta", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.hasta = :hasta")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByNumeroDiasAprobado", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.numeroDiasAprobado = :numeroDiasAprobado")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByAprobadoDesde", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.aprobadoDesde = :aprobadoDesde")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByAprobadoHasta", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.aprobadoHasta = :aprobadoHasta")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByNota", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.nota = :nota")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByUserAprueba", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.userAprueba = :userAprueba")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByCreado", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.creado = :creado")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByModificado", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GenericaSolicitudLicencia.findByEstadoReg", query = "SELECT g FROM GenericaSolicitudLicencia g WHERE g.estadoReg = :estadoReg")})
public class GenericaSolicitudLicencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_solicitud_licencia")
    private Integer idGenericaSolicitudLicencia;
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
    @JoinColumn(name = "id_generica_solicitud_motivo", referencedColumnName = "id_generica_solicitud_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaSolicitudMotivo idGenericaSolicitudMotivo;
    @JoinColumn(name = "id_generica_token", referencedColumnName = "id_generica_token")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GenericaToken idGenericaToken;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaSolicitudLicencia() {
    }

    public GenericaSolicitudLicencia(Integer idGenericaSolicitudLicencia) {
        this.idGenericaSolicitudLicencia = idGenericaSolicitudLicencia;
    }

    public Integer getIdGenericaSolicitudLicencia() {
        return idGenericaSolicitudLicencia;
    }

    public void setIdGenericaSolicitudLicencia(Integer idGenericaSolicitudLicencia) {
        this.idGenericaSolicitudLicencia = idGenericaSolicitudLicencia;
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

    public GenericaSolicitudMotivo getIdGenericaSolicitudMotivo() {
        return idGenericaSolicitudMotivo;
    }

    public void setIdGenericaSolicitudMotivo(GenericaSolicitudMotivo idGenericaSolicitudMotivo) {
        this.idGenericaSolicitudMotivo = idGenericaSolicitudMotivo;
    }

    public GenericaToken getIdGenericaToken() {
        return idGenericaToken;
    }

    public void setIdGenericaToken(GenericaToken idGenericaToken) {
        this.idGenericaToken = idGenericaToken;
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
        hash += (idGenericaSolicitudLicencia != null ? idGenericaSolicitudLicencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaSolicitudLicencia)) {
            return false;
        }
        GenericaSolicitudLicencia other = (GenericaSolicitudLicencia) object;
        if ((this.idGenericaSolicitudLicencia == null && other.idGenericaSolicitudLicencia != null) || (this.idGenericaSolicitudLicencia != null && !this.idGenericaSolicitudLicencia.equals(other.idGenericaSolicitudLicencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaSolicitudLicencia[ idGenericaSolicitudLicencia=" + idGenericaSolicitudLicencia + " ]";
    }
    
}
