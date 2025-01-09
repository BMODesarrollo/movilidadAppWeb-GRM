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
@Table(name = "aseo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aseo.findAll", query = "SELECT a FROM Aseo a"),
    @NamedQuery(name = "Aseo.findByIdAseo", query = "SELECT a FROM Aseo a WHERE a.idAseo = :idAseo"),
    @NamedQuery(name = "Aseo.findByFechaIni", query = "SELECT a FROM Aseo a WHERE a.fechaIni = :fechaIni"),
    @NamedQuery(name = "Aseo.findByFechaFin", query = "SELECT a FROM Aseo a WHERE a.fechaFin = :fechaFin")})
public class Aseo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aseo")
    private Integer idAseo;
    @Column(name = "fecha_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIni;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(min = 1, max = 150)
    @Column(name = "path_fotos")
    private String pathFotos;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AseoParamArea aseoParamArea;
    @JoinColumn(name = "id_aseo_tipo", referencedColumnName = "id_aseo_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AseoTipo aseoTipo;
    @JoinColumn(name = "id_sst_empresa_visitante", referencedColumnName = "id_sst_empresa_visitante")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstEmpresaVisitante sstEmpresaVisitante;
  

    public Aseo() {
    }

    public Aseo(Integer idAseo) {
        this.idAseo = idAseo;
    }

    public Integer getIdAseo() {
        return idAseo;
    }

    public void setIdAseo(Integer idAseo) {
        this.idAseo = idAseo;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public AseoParamArea getAseoParamArea() {
        return aseoParamArea;
    }

    public void setAseoParamArea(AseoParamArea aseoParamArea) {
        this.aseoParamArea = aseoParamArea;
    }

    public AseoTipo getAseoTipo() {
        return aseoTipo;
    }

    public void setAseoTipo(AseoTipo aseoTipo) {
        this.aseoTipo = aseoTipo;
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

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAseo != null ? idAseo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aseo)) {
            return false;
        }
        Aseo other = (Aseo) object;
        if ((this.idAseo == null && other.idAseo != null) || (this.idAseo != null && !this.idAseo.equals(other.idAseo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Aseo[ idAseo=" + idAseo + " ]";
    }

}
