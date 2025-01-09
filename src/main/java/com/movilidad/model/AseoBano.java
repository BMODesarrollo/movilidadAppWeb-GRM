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
 * @author solucionesit
 */
@Entity
@Table(name = "aseo_bano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AseoBano.findAll", query = "SELECT a FROM AseoBano a"),
    @NamedQuery(name = "AseoBano.findByIdAseoBano", query = "SELECT a FROM AseoBano a WHERE a.idAseoBano = :idAseoBano"),
    @NamedQuery(name = "AseoBano.findByFechaHoraIni", query = "SELECT a FROM AseoBano a WHERE a.fechaHoraIni = :fechaHoraIni"),
    @NamedQuery(name = "AseoBano.findByFechaHoraFin", query = "SELECT a FROM AseoBano a WHERE a.fechaHoraFin = :fechaHoraFin"),
    @NamedQuery(name = "AseoBano.findByPathFoto", query = "SELECT a FROM AseoBano a WHERE a.pathFoto = :pathFoto"),
    @NamedQuery(name = "AseoBano.findByCodigoPlanilla", query = "SELECT a FROM AseoBano a WHERE a.codigoPlanilla = :codigoPlanilla")})
public class AseoBano implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aseo_bano")
    private Integer idAseoBano;
    @Column(name = "fecha_hora_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraIni;
    @Column(name = "fecha_hora_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraFin;
    @Size(max = 150)
    @Column(name = "path_foto")
    private String pathFoto;
    @Size(max = 6)
    @Column(name = "codigo_planilla")
    private String codigoPlanilla;
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
    @JoinColumn(name = "id_aseo_param_area", referencedColumnName = "id_aseo_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private AseoParamArea aseoParamArea;
    @JoinColumn(name = "id_sst_empresa_visitante", referencedColumnName = "id_sst_empresa_visitante")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstEmpresaVisitante sstEmpresaVisitante;
    @Column(name = "autorizado")
    private Integer autorizado;

    @Column(name = "fecha_autoriza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAutoriza;
    @Size(max = 65)
    @Column(name = "user_autoriza")
    private String userAutoriza;

    public AseoBano() {
    }

    public AseoBano(Integer idAseoBano) {
        this.idAseoBano = idAseoBano;
    }

    public Integer getIdAseoBano() {
        return idAseoBano;
    }

    public void setIdAseoBano(Integer idAseoBano) {
        this.idAseoBano = idAseoBano;
    }

    public Date getFechaHoraIni() {
        return fechaHoraIni;
    }

    public void setFechaHoraIni(Date fechaHoraIni) {
        this.fechaHoraIni = fechaHoraIni;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public String getCodigoPlanilla() {
        return codigoPlanilla;
    }

    public void setCodigoPlanilla(String codigoPlanilla) {
        this.codigoPlanilla = codigoPlanilla;
    }

    public AseoParamArea getAseoParamArea() {
        return aseoParamArea;
    }

    public void setAseoParamArea(AseoParamArea aseoParamArea) {
        this.aseoParamArea = aseoParamArea;
    }

    public SstEmpresaVisitante getSstEmpresaVisitante() {
        return sstEmpresaVisitante;
    }

    public void setSstEmpresaVisitante(SstEmpresaVisitante sstEmpresaVisitante) {
        this.sstEmpresaVisitante = sstEmpresaVisitante;
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

    public Integer getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Integer autorizado) {
        this.autorizado = autorizado;
    }

    
    public Date getFechaAutoriza() {
        return fechaAutoriza;
    }

    public void setFechaAutoriza(Date fechaAutoriza) {
        this.fechaAutoriza = fechaAutoriza;
    }

    public String getUserAutoriza() {
        return userAutoriza;
    }

    public void setUserAutoriza(String userAutoriza) {
        this.userAutoriza = userAutoriza;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAseoBano != null ? idAseoBano.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AseoBano)) {
            return false;
        }
        AseoBano other = (AseoBano) object;
        if ((this.idAseoBano == null && other.idAseoBano != null) || (this.idAseoBano != null && !this.idAseoBano.equals(other.idAseoBano))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AseoBano[ idAseoBano=" + idAseoBano + " ]";
    }

}
