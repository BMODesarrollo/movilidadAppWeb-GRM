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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "disp_conciliacion_resumen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispConciliacionResumen.findAll", query = "SELECT d FROM DispConciliacionResumen d"),
    @NamedQuery(name = "DispConciliacionResumen.findByIdDispConciliacionResumen", query = "SELECT d FROM DispConciliacionResumen d WHERE d.idDispConciliacionResumen = :idDispConciliacionResumen"),
    @NamedQuery(name = "DispConciliacionResumen.findByTotalVehiculosOperativos", query = "SELECT d FROM DispConciliacionResumen d WHERE d.totalVehiculosOperativos = :totalVehiculosOperativos"),
    @NamedQuery(name = "DispConciliacionResumen.findByTotalVehiculosInoperativos", query = "SELECT d FROM DispConciliacionResumen d WHERE d.totalVehiculosInoperativos = :totalVehiculosInoperativos"),
    @NamedQuery(name = "DispConciliacionResumen.findByGeneradoPor", query = "SELECT d FROM DispConciliacionResumen d WHERE d.generadoPor = :generadoPor"),
    @NamedQuery(name = "DispConciliacionResumen.findByCreado", query = "SELECT d FROM DispConciliacionResumen d WHERE d.creado = :creado"),
    @NamedQuery(name = "DispConciliacionResumen.findByModificado", query = "SELECT d FROM DispConciliacionResumen d WHERE d.modificado = :modificado"),
    @NamedQuery(name = "DispConciliacionResumen.findByEstadoReg", query = "SELECT d FROM DispConciliacionResumen d WHERE d.estadoReg = :estadoReg")})
public class DispConciliacionResumen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_conciliacion_resumen")
    private Integer idDispConciliacionResumen;
    @Column(name = "total_vehiculos_operativos")
    private Integer totalVehiculosOperativos;
    @Column(name = "total_vehiculos_inoperativos")
    private Integer totalVehiculosInoperativos;
    @Column(name = "fecha_hora_aprobacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraAprobacion;
    @Size(min = 1, max = 15)
    @Column(name = "usr_operaciones")
    private String usrOperaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aprobado")
    private int aprobado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "generado_por")
    private String generadoPor;
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
    @JoinColumn(name = "id_disp_conciliacion", referencedColumnName = "id_disp_conciliacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispConciliacion idDispConciliacion;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public DispConciliacionResumen() {
    }

    public DispConciliacionResumen(Integer idDispConciliacionResumen) {
        this.idDispConciliacionResumen = idDispConciliacionResumen;
    }

    public DispConciliacionResumen(GopUnidadFuncional idGopUnidadFuncional, Integer totalVehiculosInoperativos, Integer totalVehiculosOperativos) {
        this.totalVehiculosOperativos = totalVehiculosOperativos;
        this.totalVehiculosInoperativos = totalVehiculosInoperativos;
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public DispConciliacionResumen(Integer idDispConciliacionResumen, int aprobado, String generadoPor, Date creado, int estadoReg) {
        this.idDispConciliacionResumen = idDispConciliacionResumen;
        this.aprobado = aprobado;
        this.generadoPor = generadoPor;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDispConciliacionResumen() {
        return idDispConciliacionResumen;
    }

    public void setIdDispConciliacionResumen(Integer idDispConciliacionResumen) {
        this.idDispConciliacionResumen = idDispConciliacionResumen;
    }

    public Integer getTotalVehiculosOperativos() {
        return totalVehiculosOperativos;
    }

    public void setTotalVehiculosOperativos(Integer totalVehiculosOperativos) {
        this.totalVehiculosOperativos = totalVehiculosOperativos;
    }

    public Integer getTotalVehiculosInoperativos() {
        return totalVehiculosInoperativos;
    }

    public void setTotalVehiculosInoperativos(Integer totalVehiculosInoperativos) {
        this.totalVehiculosInoperativos = totalVehiculosInoperativos;
    }

    public Date getFechaHoraAprobacion() {
        return fechaHoraAprobacion;
    }

    public void setFechaHoraAprobacion(Date fechaHoraAprobacion) {
        this.fechaHoraAprobacion = fechaHoraAprobacion;
    }

    public String getUsrOperaciones() {
        return usrOperaciones;
    }

    public void setUsrOperaciones(String usrOperaciones) {
        this.usrOperaciones = usrOperaciones;
    }

    public int getAprobado() {
        return aprobado;
    }

    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }

    public String getGeneradoPor() {
        return generadoPor;
    }

    public void setGeneradoPor(String generadoPor) {
        this.generadoPor = generadoPor;
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

    public DispConciliacion getIdDispConciliacion() {
        return idDispConciliacion;
    }

    public void setIdDispConciliacion(DispConciliacion idDispConciliacion) {
        this.idDispConciliacion = idDispConciliacion;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispConciliacionResumen != null ? idDispConciliacionResumen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispConciliacionResumen)) {
            return false;
        }
        DispConciliacionResumen other = (DispConciliacionResumen) object;
        if ((this.idDispConciliacionResumen == null && other.idDispConciliacionResumen != null) || (this.idDispConciliacionResumen != null && !this.idDispConciliacionResumen.equals(other.idDispConciliacionResumen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispConciliacionResumen[ idDispConciliacionResumen=" + idDispConciliacionResumen + " ]";
    }

}
