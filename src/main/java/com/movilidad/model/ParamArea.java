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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "param_area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamArea.findAll", query = "SELECT p FROM ParamArea p"),
    @NamedQuery(name = "ParamArea.findByIdParamArea", query = "SELECT p FROM ParamArea p WHERE p.idParamArea = :idParamArea"),
    @NamedQuery(name = "ParamArea.findByArea", query = "SELECT p FROM ParamArea p WHERE p.area = :area"),
    @NamedQuery(name = "ParamArea.findByControlJornada", query = "SELECT p FROM ParamArea p WHERE p.controlJornada = :controlJornada"),
    @NamedQuery(name = "ParamArea.findByNovedad", query = "SELECT p FROM ParamArea p WHERE p.novedad = :novedad"),
    @NamedQuery(name = "ParamArea.findByProgramaMaster", query = "SELECT p FROM ParamArea p WHERE p.programaMaster = :programaMaster"),
    @NamedQuery(name = "ParamArea.findByJornadaFlexible", query = "SELECT p FROM ParamArea p WHERE p.jornadaFlexible = :jornadaFlexible"),
    @NamedQuery(name = "ParamArea.findByUsername", query = "SELECT p FROM ParamArea p WHERE p.username = :username"),
    @NamedQuery(name = "ParamArea.findByCreado", query = "SELECT p FROM ParamArea p WHERE p.creado = :creado"),
    @NamedQuery(name = "ParamArea.findByModificado", query = "SELECT p FROM ParamArea p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "ParamArea.findByEstadoReg", query = "SELECT p FROM ParamArea p WHERE p.estadoReg = :estadoReg")})
public class ParamArea implements Serializable {

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaNominaAutorizacionDet> genericaNominaAutorizacionDetList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<NominaServerParamDetGen> nominaServerParamDetGenList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaNominaAutorizacionIndividual> genericaNominaAutorizacionIndividualList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmIngEgr> genericaPmIngEgrList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmTablaPremios> genericaPmTablaPremiosList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmVrbonoGrupal> genericaPmVrbonoGrupalList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmIeConceptos> genericaPmIeConceptosList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPdMaestro> genericaPdMaestroList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<AuditoriaTipo> auditoriaTipoList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<AuditoriaPregunta> auditoriaPreguntaList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<Auditoria> auditoriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<AuditoriaEncabezado> auditoriaEncabezadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<AuditoriaLugar> auditoriaLugarList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<AuditoriaTipoRespuesta> auditoriaTipoRespuestaList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmGrupoParam> genericaPmGrupoParamList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPrgJornada> genericaPrgJornadaList;
    @OneToMany(mappedBy = "idParamArea")
    private List<GenericaSolicitud> genericaSolicitudList;

    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaSolicitudLicencia> genericaSolicitudLicenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaJornadaExtra> genericaJornadaExtraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaJornadaParam> genericaJornadaParamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmGrupo> genericaPmGrupoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmVrbonos> genericaPmVrbonosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmPuntosEmpresa> genericaPmPuntosEmpresaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmDiasEmpresa> genericaPmDiasEmpresaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParamArea")
    private List<GenericaTipoDocumentos> genericaTipoDocumentosList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaJornadaTipo> genericaJornadaTipoList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<ParamAreaCargo> paramAreaCargoList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<Generica> genericaList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<ParamAreaUsr> paramAreaUsrList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaTipoDetalles> genericaTipoDetallesList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaTipo> genericaTipoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_area")
    private Integer idParamArea;
    @Size(max = 56)
    @Column(name = "area")
    private String area;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "control_jornada")
    private Integer controlJornada;
    @Column(name = "novedad")
    private Integer novedad;
    @Column(name = "programa_master")
    private Integer programaMaster;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaJornadaMotivo> genericaJornadaMotivoList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaJornada> genericaJornadaList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmNovedadIncluir> genericaPmNovedadIncluirList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaPmNovedadExcluir> genericaPmNovedadExcluirList;
    @OneToMany(mappedBy = "idParamArea", fetch = FetchType.LAZY)
    private List<GenericaNominaAutorizacion> genericaNominaAutorizacionList;

    @Column(name = "jornada_flexible")
    private Integer jornadaFlexible;

    public ParamArea() {
    }

    public ParamArea(Integer idParamArea) {
        this.idParamArea = idParamArea;
    }

    public Integer getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(Integer idParamArea) {
        this.idParamArea = idParamArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getControlJornada() {
        return controlJornada;
    }

    public void setControlJornada(Integer controlJornada) {
        this.controlJornada = controlJornada;
    }

    public Integer getNovedad() {
        return novedad;
    }

    public void setNovedad(Integer novedad) {
        this.novedad = novedad;
    }

    public Integer getProgramaMaster() {
        return programaMaster;
    }

    public void setProgramaMaster(Integer programaMaster) {
        this.programaMaster = programaMaster;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<ParamAreaCargo> getParamAreaCargoList() {
        return paramAreaCargoList;
    }

    public void setParamAreaCargoList(List<ParamAreaCargo> paramAreaCargoList) {
        this.paramAreaCargoList = paramAreaCargoList;
    }

    @XmlTransient
    public List<ParamAreaUsr> getParamAreaUsrList() {
        return paramAreaUsrList;
    }

    public void setParamAreaUsrList(List<ParamAreaUsr> paramAreaUsrList) {
        this.paramAreaUsrList = paramAreaUsrList;
    }

    @XmlTransient
    public List<GenericaTipoDetalles> getGenericaTipoDetallesList() {
        return genericaTipoDetallesList;
    }

    public void setGenericaTipoDetallesList(List<GenericaTipoDetalles> genericaTipoDetallesList) {
        this.genericaTipoDetallesList = genericaTipoDetallesList;
    }

    @XmlTransient
    public List<GenericaTipo> getGenericaTipoList() {
        return genericaTipoList;
    }

    public void setGenericaTipoList(List<GenericaTipo> genericaTipoList) {
        this.genericaTipoList = genericaTipoList;
    }

    @XmlTransient
    public List<GenericaJornadaMotivo> getGenericaJornadaMotivoList() {
        return genericaJornadaMotivoList;
    }

    public void setGenericaJornadaMotivoList(List<GenericaJornadaMotivo> genericaJornadaMotivoList) {
        this.genericaJornadaMotivoList = genericaJornadaMotivoList;
    }

    @XmlTransient
    public List<GenericaJornada> getGenericaJornadaList() {
        return genericaJornadaList;
    }

    public void setGenericaJornadaList(List<GenericaJornada> genericaJornadaList) {
        this.genericaJornadaList = genericaJornadaList;
    }

    @XmlTransient
    public List<GenericaPdMaestro> getGenericaPdMaestroList() {
        return genericaPdMaestroList;
    }

    public void setGenericaPdMaestroList(List<GenericaPdMaestro> genericaPdMaestroList) {
        this.genericaPdMaestroList = genericaPdMaestroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParamArea != null ? idParamArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamArea)) {
            return false;
        }
        ParamArea other = (ParamArea) object;
        if ((this.idParamArea == null && other.idParamArea != null) || (this.idParamArea != null && !this.idParamArea.equals(other.idParamArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ParamArea[ idParamArea=" + idParamArea + " ]";
    }

    @XmlTransient
    public List<Generica> getGenericaList() {
        return genericaList;
    }

    public void setGenericaList(List<Generica> genericaList) {
        this.genericaList = genericaList;
    }

    @XmlTransient
    public List<GenericaJornadaTipo> getGenericaJornadaTipoList() {
        return genericaJornadaTipoList;
    }

    public void setGenericaJornadaTipoList(List<GenericaJornadaTipo> genericaJornadaTipoList) {
        this.genericaJornadaTipoList = genericaJornadaTipoList;
    }

    @XmlTransient
    public List<GenericaPmPuntosEmpresa> getGenericaPmPuntosEmpresaList() {
        return genericaPmPuntosEmpresaList;
    }

    public void setGenericaPmPuntosEmpresaList(List<GenericaPmPuntosEmpresa> genericaPmPuntosEmpresaList) {
        this.genericaPmPuntosEmpresaList = genericaPmPuntosEmpresaList;
    }

    @XmlTransient
    public List<GenericaPmDiasEmpresa> getGenericaPmDiasEmpresaList() {
        return genericaPmDiasEmpresaList;
    }

    public void setGenericaPmDiasEmpresaList(List<GenericaPmDiasEmpresa> genericaPmDiasEmpresaList) {
        this.genericaPmDiasEmpresaList = genericaPmDiasEmpresaList;
    }

    @XmlTransient
    public List<GenericaPmVrbonos> getGenericaPmVrbonosList() {
        return genericaPmVrbonosList;
    }

    public void setGenericaPmVrbonosList(List<GenericaPmVrbonos> genericaPmVrbonosList) {
        this.genericaPmVrbonosList = genericaPmVrbonosList;
    }

    @XmlTransient
    public List<GenericaTipoDocumentos> getGenericaTipoDocumentosList() {
        return genericaTipoDocumentosList;
    }

    public void setGenericaTipoDocumentosList(List<GenericaTipoDocumentos> genericaTipoDocumentosList) {
        this.genericaTipoDocumentosList = genericaTipoDocumentosList;
    }

    @XmlTransient
    public List<GenericaPmGrupo> getGenericaPmGrupoList() {
        return genericaPmGrupoList;
    }

    public void setGenericaPmGrupoList(List<GenericaPmGrupo> genericaPmGrupoList) {
        this.genericaPmGrupoList = genericaPmGrupoList;
    }

    @XmlTransient
    public List<GenericaJornadaExtra> getGenericaJornadaExtraList() {
        return genericaJornadaExtraList;
    }

    public void setGenericaJornadaExtraList(List<GenericaJornadaExtra> genericaJornadaExtraList) {
        this.genericaJornadaExtraList = genericaJornadaExtraList;
    }

    @XmlTransient
    public List<GenericaJornadaParam> getGenericaJornadaParamList() {
        return genericaJornadaParamList;
    }

    public void setGenericaJornadaParamList(List<GenericaJornadaParam> genericaJornadaParamList) {
        this.genericaJornadaParamList = genericaJornadaParamList;
    }

    @XmlTransient
    public List<GenericaSolicitudLicencia> getGenericaSolicitudLicenciaList() {
        return genericaSolicitudLicenciaList;
    }

    public void setGenericaSolicitudLicenciaList(List<GenericaSolicitudLicencia> genericaSolicitudLicenciaList) {
        this.genericaSolicitudLicenciaList = genericaSolicitudLicenciaList;
    }

    @XmlTransient
    public List<GenericaSolicitud> getGenericaSolicitudList() {
        return genericaSolicitudList;
    }

    public void setGenericaSolicitudList(List<GenericaSolicitud> genericaSolicitudList) {
        this.genericaSolicitudList = genericaSolicitudList;
    }

    @XmlTransient
    public List<GenericaPmGrupoParam> getGenericaPmGrupoParamList() {
        return genericaPmGrupoParamList;
    }

    public void setGenericaPmGrupoParamList(List<GenericaPmGrupoParam> genericaPmGrupoParamList) {
        this.genericaPmGrupoParamList = genericaPmGrupoParamList;
    }

    @XmlTransient
    public List<GenericaPrgJornada> getGenericaPrgJornadaList() {
        return genericaPrgJornadaList;
    }

    public void setGenericaPrgJornadaList(List<GenericaPrgJornada> genericaPrgJornadaList) {
        this.genericaPrgJornadaList = genericaPrgJornadaList;
    }

    @XmlTransient
    public List<GenericaPmNovedadExcluir> getGenericaPmNovedadExcluirList() {
        return genericaPmNovedadExcluirList;
    }

    public void setGenericaPmNovedadExcluirList(List<GenericaPmNovedadExcluir> genericaPmNovedadExcluirList) {
        this.genericaPmNovedadExcluirList = genericaPmNovedadExcluirList;
    }

    @XmlTransient
    public List<GenericaPmNovedadIncluir> getGenericaPmNovedadIncluirList() {
        return genericaPmNovedadIncluirList;
    }

    public void setGenericaPmNovedadIncluirList(List<GenericaPmNovedadIncluir> genericaPmNovedadIncluirList) {
        this.genericaPmNovedadIncluirList = genericaPmNovedadIncluirList;
    }

    @XmlTransient
    public List<AuditoriaTipo> getAuditoriaTipoList() {
        return auditoriaTipoList;
    }

    public void setAuditoriaTipoList(List<AuditoriaTipo> auditoriaTipoList) {
        this.auditoriaTipoList = auditoriaTipoList;
    }

    @XmlTransient
    public List<AuditoriaPregunta> getAuditoriaPreguntaList() {
        return auditoriaPreguntaList;
    }

    public void setAuditoriaPreguntaList(List<AuditoriaPregunta> auditoriaPreguntaList) {
        this.auditoriaPreguntaList = auditoriaPreguntaList;
    }

    @XmlTransient
    public List<Auditoria> getAuditoriaList() {
        return auditoriaList;
    }

    public void setAuditoriaList(List<Auditoria> auditoriaList) {
        this.auditoriaList = auditoriaList;
    }

    @XmlTransient
    public List<AuditoriaEncabezado> getAuditoriaEncabezadoList() {
        return auditoriaEncabezadoList;
    }

    public void setAuditoriaEncabezadoList(List<AuditoriaEncabezado> auditoriaEncabezadoList) {
        this.auditoriaEncabezadoList = auditoriaEncabezadoList;
    }

    @XmlTransient
    public List<AuditoriaLugar> getAuditoriaLugarList() {
        return auditoriaLugarList;
    }

    public void setAuditoriaLugarList(List<AuditoriaLugar> auditoriaLugarList) {
        this.auditoriaLugarList = auditoriaLugarList;
    }

    @XmlTransient
    public List<AuditoriaTipoRespuesta> getAuditoriaTipoRespuestaList() {
        return auditoriaTipoRespuestaList;
    }

    public void setAuditoriaTipoRespuestaList(List<AuditoriaTipoRespuesta> auditoriaTipoRespuestaList) {
        this.auditoriaTipoRespuestaList = auditoriaTipoRespuestaList;
    }

    @XmlTransient
    public List<GenericaPmIeConceptos> getGenericaPmIeConceptosList() {
        return genericaPmIeConceptosList;
    }

    public void setGenericaPmIeConceptosList(List<GenericaPmIeConceptos> genericaPmIeConceptosList) {
        this.genericaPmIeConceptosList = genericaPmIeConceptosList;
    }

    @XmlTransient
    public List<GenericaPmVrbonoGrupal> getGenericaPmVrbonoGrupalList() {
        return genericaPmVrbonoGrupalList;
    }

    public void setGenericaPmVrbonoGrupalList(List<GenericaPmVrbonoGrupal> genericaPmVrbonoGrupalList) {
        this.genericaPmVrbonoGrupalList = genericaPmVrbonoGrupalList;
    }

    @XmlTransient
    public List<GenericaPmTablaPremios> getGenericaPmTablaPremiosList() {
        return genericaPmTablaPremiosList;
    }

    public void setGenericaPmTablaPremiosList(List<GenericaPmTablaPremios> genericaPmTablaPremiosList) {
        this.genericaPmTablaPremiosList = genericaPmTablaPremiosList;
    }

    @XmlTransient
    public List<GenericaPmIngEgr> getGenericaPmIngEgrList() {
        return genericaPmIngEgrList;
    }

    public void setGenericaPmIngEgrList(List<GenericaPmIngEgr> genericaPmIngEgrList) {
        this.genericaPmIngEgrList = genericaPmIngEgrList;
    }

    @XmlTransient
    public List<GenericaNominaAutorizacion> getGenericaNominaAutorizacionList() {
        return genericaNominaAutorizacionList;
    }

    public void setGenericaNominaAutorizacionList(List<GenericaNominaAutorizacion> genericaNominaAutorizacionList) {
        this.genericaNominaAutorizacionList = genericaNominaAutorizacionList;
    }

    @XmlTransient
    public List<GenericaNominaAutorizacionIndividual> getGenericaNominaAutorizacionIndividualList() {
        return genericaNominaAutorizacionIndividualList;
    }

    public void setGenericaNominaAutorizacionIndividualList(List<GenericaNominaAutorizacionIndividual> genericaNominaAutorizacionIndividualList) {
        this.genericaNominaAutorizacionIndividualList = genericaNominaAutorizacionIndividualList;
    }

    @XmlTransient
    public List<NominaServerParamDetGen> getNominaServerParamDetGenList() {
        return nominaServerParamDetGenList;
    }

    public void setNominaServerParamDetGenList(List<NominaServerParamDetGen> nominaServerParamDetGenList) {
        this.nominaServerParamDetGenList = nominaServerParamDetGenList;
    }

    @XmlTransient
    public List<GenericaNominaAutorizacionDet> getGenericaNominaAutorizacionDetList() {
        return genericaNominaAutorizacionDetList;
    }

    public void setGenericaNominaAutorizacionDetList(List<GenericaNominaAutorizacionDet> genericaNominaAutorizacionDetList) {
        this.genericaNominaAutorizacionDetList = genericaNominaAutorizacionDetList;
    }

    public Integer getJornadaFlexible() {
        return jornadaFlexible;
    }

    public void setJornadaFlexible(Integer jornadaFlexible) {
        this.jornadaFlexible = jornadaFlexible;
    }

    
}
