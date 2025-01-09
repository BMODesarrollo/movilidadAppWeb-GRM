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
@Table(name = "informe_seguridad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformeSeguridad.findAll", query = "SELECT i FROM InformeSeguridad i"),
    @NamedQuery(name = "InformeSeguridad.findByIdInformeSeguridad", query = "SELECT i FROM InformeSeguridad i WHERE i.idInformeSeguridad = :idInformeSeguridad"),
    @NamedQuery(name = "InformeSeguridad.findByFechaIni", query = "SELECT i FROM InformeSeguridad i WHERE i.fechaIni = :fechaIni"),
    @NamedQuery(name = "InformeSeguridad.findByFechaFin", query = "SELECT i FROM InformeSeguridad i WHERE i.fechaFin = :fechaFin"),
    @NamedQuery(name = "InformeSeguridad.findByPathDocumento", query = "SELECT i FROM InformeSeguridad i WHERE i.pathDocumento = :pathDocumento"),
    @NamedQuery(name = "InformeSeguridad.findByUsername", query = "SELECT i FROM InformeSeguridad i WHERE i.username = :username"),
    @NamedQuery(name = "InformeSeguridad.findByCreado", query = "SELECT i FROM InformeSeguridad i WHERE i.creado = :creado"),
    @NamedQuery(name = "InformeSeguridad.findByModificado", query = "SELECT i FROM InformeSeguridad i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "InformeSeguridad.findByEstadoReg", query = "SELECT i FROM InformeSeguridad i WHERE i.estadoReg = :estadoReg")})
public class InformeSeguridad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_informe_seguridad")
    private Integer idInformeSeguridad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ini")
    @Temporal(TemporalType.DATE)
    private Date fechaIni;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Size(max = 100)
    @Column(name = "path_documento")
    private String pathDocumento;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_sst_empresa", referencedColumnName = "id_sst_empresa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstEmpresa idSstEmpresa;
    @JoinColumn(name = "id_sst_empresa_visitante", referencedColumnName = "id_sst_empresa_visitante")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstEmpresaVisitante idSstEmpresaVisitante;

    public InformeSeguridad() {
    }

    public InformeSeguridad(Integer idInformeSeguridad) {
        this.idInformeSeguridad = idInformeSeguridad;
    }

    public InformeSeguridad(Integer idInformeSeguridad, Date fechaIni, Date fechaFin, String username, Date creado, int estadoReg) {
        this.idInformeSeguridad = idInformeSeguridad;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdInformeSeguridad() {
        return idInformeSeguridad;
    }

    public void setIdInformeSeguridad(Integer idInformeSeguridad) {
        this.idInformeSeguridad = idInformeSeguridad;
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

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public SstEmpresa getIdSstEmpresa() {
        return idSstEmpresa;
    }

    public void setIdSstEmpresa(SstEmpresa idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }

    public SstEmpresaVisitante getIdSstEmpresaVisitante() {
        return idSstEmpresaVisitante;
    }

    public void setIdSstEmpresaVisitante(SstEmpresaVisitante idSstEmpresaVisitante) {
        this.idSstEmpresaVisitante = idSstEmpresaVisitante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInformeSeguridad != null ? idInformeSeguridad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformeSeguridad)) {
            return false;
        }
        InformeSeguridad other = (InformeSeguridad) object;
        if ((this.idInformeSeguridad == null && other.idInformeSeguridad != null) || (this.idInformeSeguridad != null && !this.idInformeSeguridad.equals(other.idInformeSeguridad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.InformeSeguridad[ idInformeSeguridad=" + idInformeSeguridad + " ]";
    }
    
}
