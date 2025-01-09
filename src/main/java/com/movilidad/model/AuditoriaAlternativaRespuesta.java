/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "auditoria_alternativa_respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaAlternativaRespuesta.findAll", query = "SELECT a FROM AuditoriaAlternativaRespuesta a")
    ,
    @NamedQuery(name = "AuditoriaAlternativaRespuesta.findByIdAuditoriaAlternativaRespuesta", query = "SELECT a FROM AuditoriaAlternativaRespuesta a WHERE a.idAuditoriaAlternativaRespuesta = :idAuditoriaAlternativaRespuesta")
    ,
    @NamedQuery(name = "AuditoriaAlternativaRespuesta.findByEnunciado", query = "SELECT a FROM AuditoriaAlternativaRespuesta a WHERE a.enunciado = :enunciado")
    ,
    @NamedQuery(name = "AuditoriaAlternativaRespuesta.findByDescripcion", query = "SELECT a FROM AuditoriaAlternativaRespuesta a WHERE a.descripcion = :descripcion")
    ,
    @NamedQuery(name = "AuditoriaAlternativaRespuesta.findByNumero", query = "SELECT a FROM AuditoriaAlternativaRespuesta a WHERE a.numero = :numero")
    ,
    @NamedQuery(name = "AuditoriaAlternativaRespuesta.findByValor", query = "SELECT a FROM AuditoriaAlternativaRespuesta a WHERE a.valor = :valor")})
public class AuditoriaAlternativaRespuesta implements Serializable, Comparable<AuditoriaAlternativaRespuesta> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_alternativa_respuesta")
    private Integer idAuditoriaAlternativaRespuesta;
    @Size(max = 60)
    @Column(name = "enunciado")
    private String enunciado;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "genera_novedad")
    private boolean generaNovedad;
    @Column(name = "valor")
    private Integer valor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoriaAlternativaRespuesta", fetch = FetchType.LAZY)
    private List<AuditoriaRespuesta> auditoriaRespuestaList;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_auditoria_tipo_respuesta", referencedColumnName = "id_auditoria_tipo_respuesta")
    @ManyToOne(fetch = FetchType.LAZY)
    private AuditoriaTipoRespuesta idAuditoriaTipoRespuesta;

    public AuditoriaAlternativaRespuesta() {
    }

    public AuditoriaAlternativaRespuesta(Integer idAuditoriaAlternativaRespuesta) {
        this.idAuditoriaAlternativaRespuesta = idAuditoriaAlternativaRespuesta;
    }

    public Integer getIdAuditoriaAlternativaRespuesta() {
        return idAuditoriaAlternativaRespuesta;
    }

    public void setIdAuditoriaAlternativaRespuesta(Integer idAuditoriaAlternativaRespuesta) {
        this.idAuditoriaAlternativaRespuesta = idAuditoriaAlternativaRespuesta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    @XmlTransient
    public List<AuditoriaRespuesta> getAuditoriaRespuestaList() {
        return auditoriaRespuestaList;
    }

    public void setAuditoriaRespuestaList(List<AuditoriaRespuesta> auditoriaRespuestaList) {
        this.auditoriaRespuestaList = auditoriaRespuestaList;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public AuditoriaTipoRespuesta getIdAuditoriaTipoRespuesta() {
        return idAuditoriaTipoRespuesta;
    }

    public void setIdAuditoriaTipoRespuesta(AuditoriaTipoRespuesta idAuditoriaTipoRespuesta) {
        this.idAuditoriaTipoRespuesta = idAuditoriaTipoRespuesta;
    }

    public boolean isGeneraNovedad() {
        return generaNovedad;
    }

    public void setGeneraNovedad(boolean generaNovedad) {
        this.generaNovedad = generaNovedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaAlternativaRespuesta != null ? idAuditoriaAlternativaRespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaAlternativaRespuesta)) {
            return false;
        }
        AuditoriaAlternativaRespuesta other = (AuditoriaAlternativaRespuesta) object;
        if ((this.idAuditoriaAlternativaRespuesta == null && other.idAuditoriaAlternativaRespuesta != null) || (this.idAuditoriaAlternativaRespuesta != null && !this.idAuditoriaAlternativaRespuesta.equals(other.idAuditoriaAlternativaRespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaAlternativaRespuesta[ idAuditoriaAlternativaRespuesta=" + idAuditoriaAlternativaRespuesta + " ]";
    }

    @Override
    public int compareTo(AuditoriaAlternativaRespuesta e) {
        return e.getNumero().compareTo(numero);
    }
}
