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
@Table(name = "prg_sercon_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgSerconDet.findAll", query = "SELECT g FROM PrgSerconDet g")
    , @NamedQuery(name = "PrgSerconDet.findByIdPrgSerconDet", query = "SELECT g FROM PrgSerconDet g WHERE g.idPrgSerconDet = :idPrgSerconDet")
    , @NamedQuery(name = "PrgSerconDet.findByCantidad", query = "SELECT g FROM PrgSerconDet g WHERE g.cantidad = :cantidad")
    , @NamedQuery(name = "PrgSerconDet.findByTimeorigin", query = "SELECT g FROM PrgSerconDet g WHERE g.timeorigin = :timeorigin")
    , @NamedQuery(name = "PrgSerconDet.findByTimedestiny", query = "SELECT g FROM PrgSerconDet g WHERE g.timedestiny = :timedestiny")
    , @NamedQuery(name = "PrgSerconDet.findByUsername", query = "SELECT g FROM PrgSerconDet g WHERE g.username = :username")
    , @NamedQuery(name = "PrgSerconDet.findByCreado", query = "SELECT g FROM PrgSerconDet g WHERE g.creado = :creado")
    , @NamedQuery(name = "PrgSerconDet.findByModificado", query = "SELECT g FROM PrgSerconDet g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "PrgSerconDet.findByEstadoReg", query = "SELECT g FROM PrgSerconDet g WHERE g.estadoReg = :estadoReg")})
public class PrgSerconDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_sercon_det")
    private Integer idPrgSerconDet;
    @Size(max = 11)
    @Column(name = "cantidad")
    private String cantidad;
    @Size(max = 11)
    @Column(name = "timeorigin")
    private String timeorigin;
    @Size(max = 11)
    @Column(name = "timedestiny")
    private String timedestiny;
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
    @Column(name = "parte")
    private Integer parte;
    @JoinColumn(name = "id_prg_sercon", referencedColumnName = "id_prg_sercon")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PrgSercon idPrgSercon;
    @JoinColumn(name = "id_param_reporte", referencedColumnName = "id_param_reporte_horas")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamReporteHoras idParamReporte;

    public PrgSerconDet() {
    }

    public PrgSerconDet(Integer idPrgSerconDet) {
        this.idPrgSerconDet = idPrgSerconDet;
    }

    public Integer getIdPrgSerconDet() {
        return idPrgSerconDet;
    }

    public void setIdPrgSerconDet(Integer idPrgSerconDet) {
        this.idPrgSerconDet = idPrgSerconDet;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTimeorigin() {
        return timeorigin;
    }

    public void setTimeorigin(String timeorigin) {
        this.timeorigin = timeorigin;
    }

    public String getTimedestiny() {
        return timedestiny;
    }

    public void setTimedestiny(String timedestiny) {
        this.timedestiny = timedestiny;
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

    public PrgSercon getIdPrgSercon() {
        return idPrgSercon;
    }

    public void setIdPrgSercon(PrgSercon idPrgSercon) {
        this.idPrgSercon = idPrgSercon;
    }

    public ParamReporteHoras getIdParamReporte() {
        return idParamReporte;
    }

    public void setIdParamReporte(ParamReporteHoras idParamReporte) {
        this.idParamReporte = idParamReporte;
    }

    public Integer getParte() {
        return parte;
    }

    public void setParte(Integer parte) {
        this.parte = parte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgSerconDet != null ? idPrgSerconDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgSerconDet)) {
            return false;
        }
        PrgSerconDet other = (PrgSerconDet) object;
        if ((this.idPrgSerconDet == null && other.idPrgSerconDet != null) || (this.idPrgSerconDet != null && !this.idPrgSerconDet.equals(other.idPrgSerconDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgSerconDet[ idPrgSerconDet=" + idPrgSerconDet + " ]";
    }

}
