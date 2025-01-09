/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "prg_tc_resumen_vr_conciliados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgTcResumenVrConciliados.findAll", query = "SELECT p FROM PrgTcResumenVrConciliados p"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByIdPrgTcResumenVrConciliado", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.idPrgTcResumenVrConciliado = :idPrgTcResumenVrConciliado"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByFecha", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByKmsComercial", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.kmsComercial = :kmsComercial"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByKmsVacio", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.kmsVacio = :kmsVacio"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByVrKmComercial", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.vrKmComercial = :vrKmComercial"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByVrKmVacio", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.vrKmVacio = :vrKmVacio"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByTotalConciliacion", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.totalConciliacion = :totalConciliacion"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByUsername", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.username = :username"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByCreado", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByModificado", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgTcResumenVrConciliados.findByEstadoReg", query = "SELECT p FROM PrgTcResumenVrConciliados p WHERE p.estadoReg = :estadoReg")})
public class PrgTcResumenVrConciliados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_tc_resumen_vr_conciliado")
    private Integer idPrgTcResumenVrConciliado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kms_comercial")
    private int kmsComercial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kms_vacio")
    private int kmsVacio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_km_comercial")
    private int vrKmComercial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_km_vacio")
    private int vrKmVacio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_conciliacion")
    private BigDecimal totalConciliacion;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;

    public PrgTcResumenVrConciliados() {
    }

    public PrgTcResumenVrConciliados(Integer idPrgTcResumenVrConciliado) {
        this.idPrgTcResumenVrConciliado = idPrgTcResumenVrConciliado;
    }

    public PrgTcResumenVrConciliados(Integer idPrgTcResumenVrConciliado, Date fecha, int vrKmComercial, String username, Date creado, int estadoReg) {
        this.idPrgTcResumenVrConciliado = idPrgTcResumenVrConciliado;
        this.fecha = fecha;
        this.vrKmComercial = vrKmComercial;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgTcResumenVrConciliado() {
        return idPrgTcResumenVrConciliado;
    }

    public void setIdPrgTcResumenVrConciliado(Integer idPrgTcResumenVrConciliado) {
        this.idPrgTcResumenVrConciliado = idPrgTcResumenVrConciliado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getKmsComercial() {
        return kmsComercial;
    }

    public void setKmsComercial(int kmsComercial) {
        this.kmsComercial = kmsComercial;
    }

    public int getKmsVacio() {
        return kmsVacio;
    }

    public void setKmsVacio(int kmsVacio) {
        this.kmsVacio = kmsVacio;
    }

    public int getVrKmComercial() {
        return vrKmComercial;
    }

    public void setVrKmComercial(int vrKmComercial) {
        this.vrKmComercial = vrKmComercial;
    }

    public int getVrKmVacio() {
        return vrKmVacio;
    }

    public void setVrKmVacio(int vrKmVacio) {
        this.vrKmVacio = vrKmVacio;
    }

    public BigDecimal getTotalConciliacion() {
        return totalConciliacion;
    }

    public void setTotalConciliacion(BigDecimal totalConciliacion) {
        this.totalConciliacion = totalConciliacion;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgTcResumenVrConciliado != null ? idPrgTcResumenVrConciliado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgTcResumenVrConciliados)) {
            return false;
        }
        PrgTcResumenVrConciliados other = (PrgTcResumenVrConciliados) object;
        if ((this.idPrgTcResumenVrConciliado == null && other.idPrgTcResumenVrConciliado != null) || (this.idPrgTcResumenVrConciliado != null && !this.idPrgTcResumenVrConciliado.equals(other.idPrgTcResumenVrConciliado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgTcResumenVrConciliados[ idPrgTcResumenVrConciliado=" + idPrgTcResumenVrConciliado + " ]";
    }

}
