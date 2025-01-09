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
@Table(name = "generica_jornada_param")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaJornadaParam.findAll", query = "SELECT g FROM GenericaJornadaParam g"),
    @NamedQuery(name = "GenericaJornadaParam.findByIdGenericaJornadaParam", query = "SELECT g FROM GenericaJornadaParam g WHERE g.idGenericaJornadaParam = :idGenericaJornadaParam"),
    @NamedQuery(name = "GenericaJornadaParam.findByCtrlCambioJornada", query = "SELECT g FROM GenericaJornadaParam g WHERE g.ctrlCambioJornada = :ctrlCambioJornada"),
    @NamedQuery(name = "GenericaJornadaParam.findByCtrlAutorizarExtensionJornada", query = "SELECT g FROM GenericaJornadaParam g WHERE g.ctrlAutorizarExtensionJornada = :ctrlAutorizarExtensionJornada"),
    @NamedQuery(name = "GenericaJornadaParam.findByCtrlAprobarExtrasFeriadas", query = "SELECT g FROM GenericaJornadaParam g WHERE g.ctrlAprobarExtrasFeriadas = :ctrlAprobarExtrasFeriadas"),
    @NamedQuery(name = "GenericaJornadaParam.findByRequerirOrdenTrabajo", query = "SELECT g FROM GenericaJornadaParam g WHERE g.requerirOrdenTrabajo = :requerirOrdenTrabajo"),
    @NamedQuery(name = "GenericaJornadaParam.findByValidarDescansoDominical", query = "SELECT g FROM GenericaJornadaParam g WHERE g.validarDescansoDominical = :validarDescansoDominical"),
    @NamedQuery(name = "GenericaJornadaParam.findByNroDiasDescansoDominical", query = "SELECT g FROM GenericaJornadaParam g WHERE g.nroDiasDescansoDominical = :nroDiasDescansoDominical"),
    @NamedQuery(name = "GenericaJornadaParam.findByHorasExtrasSemanales", query = "SELECT g FROM GenericaJornadaParam g WHERE g.horasExtrasSemanales = :horasExtrasSemanales"),
    @NamedQuery(name = "GenericaJornadaParam.findByHorasExtrasMensuales", query = "SELECT g FROM GenericaJornadaParam g WHERE g.horasExtrasMensuales = :horasExtrasMensuales"),
    @NamedQuery(name = "GenericaJornadaParam.findByUsername", query = "SELECT g FROM GenericaJornadaParam g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaJornadaParam.findByCreado", query = "SELECT g FROM GenericaJornadaParam g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaJornadaParam.findByModificado", query = "SELECT g FROM GenericaJornadaParam g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaJornadaParam.findByEstadoReg", query = "SELECT g FROM GenericaJornadaParam g WHERE g.estadoReg = :estadoReg")})
public class GenericaJornadaParam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_jornada_param")
    private Integer idGenericaJornadaParam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ctrl_cambio_jornada")
    private int ctrlCambioJornada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ctrl_autorizar_extension_jornada")
    private int ctrlAutorizarExtensionJornada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ctrl_aprobar_extras_feriadas")
    private int ctrlAprobarExtrasFeriadas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requerir_orden_trabajo")
    private int requerirOrdenTrabajo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "validar_descanso_dominical")
    private int validarDescansoDominical;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nro_dias_descanso_dominical")
    private int nroDiasDescansoDominical;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horas_extras_semanales")
    private int horasExtrasSemanales;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horas_extras_mensuales")
    private int horasExtrasMensuales;
    @Basic(optional = false)
    @NotNull
    @Column(name = "notifica")
    private int notifica;
    @Basic(optional = false)
    @Lob
    @Size(min = 0, max = 65535)
    @Column(name = "emails")
    private String emails;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaJornadaParam() {
    }

    public GenericaJornadaParam(Integer idGenericaJornadaParam) {
        this.idGenericaJornadaParam = idGenericaJornadaParam;
    }

    public GenericaJornadaParam(Integer idGenericaJornadaParam, int ctrlCambioJornada, int ctrlAutorizarExtensionJornada, int ctrlAprobarExtrasFeriadas, int requerirOrdenTrabajo, int validarDescansoDominical, int nroDiasDescansoDominical, int horasExtrasSemanales, int horasExtrasMensuales, String username, Date creado, int estadoReg) {
        this.idGenericaJornadaParam = idGenericaJornadaParam;
        this.ctrlCambioJornada = ctrlCambioJornada;
        this.ctrlAutorizarExtensionJornada = ctrlAutorizarExtensionJornada;
        this.ctrlAprobarExtrasFeriadas = ctrlAprobarExtrasFeriadas;
        this.requerirOrdenTrabajo = requerirOrdenTrabajo;
        this.validarDescansoDominical = validarDescansoDominical;
        this.nroDiasDescansoDominical = nroDiasDescansoDominical;
        this.horasExtrasSemanales = horasExtrasSemanales;
        this.horasExtrasMensuales = horasExtrasMensuales;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaJornadaParam() {
        return idGenericaJornadaParam;
    }

    public void setIdGenericaJornadaParam(Integer idGenericaJornadaParam) {
        this.idGenericaJornadaParam = idGenericaJornadaParam;
    }

    public int getCtrlCambioJornada() {
        return ctrlCambioJornada;
    }

    public void setCtrlCambioJornada(int ctrlCambioJornada) {
        this.ctrlCambioJornada = ctrlCambioJornada;
    }

    public int getCtrlAutorizarExtensionJornada() {
        return ctrlAutorizarExtensionJornada;
    }

    public void setCtrlAutorizarExtensionJornada(int ctrlAutorizarExtensionJornada) {
        this.ctrlAutorizarExtensionJornada = ctrlAutorizarExtensionJornada;
    }

    public int getCtrlAprobarExtrasFeriadas() {
        return ctrlAprobarExtrasFeriadas;
    }

    public void setCtrlAprobarExtrasFeriadas(int ctrlAprobarExtrasFeriadas) {
        this.ctrlAprobarExtrasFeriadas = ctrlAprobarExtrasFeriadas;
    }

    public int getRequerirOrdenTrabajo() {
        return requerirOrdenTrabajo;
    }

    public void setRequerirOrdenTrabajo(int requerirOrdenTrabajo) {
        this.requerirOrdenTrabajo = requerirOrdenTrabajo;
    }

    public int getValidarDescansoDominical() {
        return validarDescansoDominical;
    }

    public void setValidarDescansoDominical(int validarDescansoDominical) {
        this.validarDescansoDominical = validarDescansoDominical;
    }

    public int getNroDiasDescansoDominical() {
        return nroDiasDescansoDominical;
    }

    public void setNroDiasDescansoDominical(int nroDiasDescansoDominical) {
        this.nroDiasDescansoDominical = nroDiasDescansoDominical;
    }

    public int getHorasExtrasSemanales() {
        return horasExtrasSemanales;
    }

    public void setHorasExtrasSemanales(int horasExtrasSemanales) {
        this.horasExtrasSemanales = horasExtrasSemanales;
    }

    public int getHorasExtrasMensuales() {
        return horasExtrasMensuales;
    }

    public void setHorasExtrasMensuales(int horasExtrasMensuales) {
        this.horasExtrasMensuales = horasExtrasMensuales;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public int getNotifica() {
        return notifica;
    }

    public void setNotifica(int notifica) {
        this.notifica = notifica;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaJornadaParam != null ? idGenericaJornadaParam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaJornadaParam)) {
            return false;
        }
        GenericaJornadaParam other = (GenericaJornadaParam) object;
        if ((this.idGenericaJornadaParam == null && other.idGenericaJornadaParam != null) || (this.idGenericaJornadaParam != null && !this.idGenericaJornadaParam.equals(other.idGenericaJornadaParam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaJornadaParam[ idGenericaJornadaParam=" + idGenericaJornadaParam + " ]";
    }

}
