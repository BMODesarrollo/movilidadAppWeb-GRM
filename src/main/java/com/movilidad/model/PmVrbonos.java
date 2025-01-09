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
 * @author HP
 */
@Entity
@Table(name = "pm_vrbonos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmVrbonos.findAll", query = "SELECT p FROM PmVrbonos p")
    , @NamedQuery(name = "PmVrbonos.findByIdPmVrbonos", query = "SELECT p FROM PmVrbonos p WHERE p.idPmVrbonos = :idPmVrbonos")
    , @NamedQuery(name = "PmVrbonos.findByDesde", query = "SELECT p FROM PmVrbonos p WHERE p.desde = :desde")
    , @NamedQuery(name = "PmVrbonos.findByHasta", query = "SELECT p FROM PmVrbonos p WHERE p.hasta = :hasta")
    , @NamedQuery(name = "PmVrbonos.findByVrBonoSalarial", query = "SELECT p FROM PmVrbonos p WHERE p.vrBonoSalarial = :vrBonoSalarial")
    , @NamedQuery(name = "PmVrbonos.findByVrBonoAlimentos", query = "SELECT p FROM PmVrbonos p WHERE p.vrBonoAlimentos = :vrBonoAlimentos")
    , @NamedQuery(name = "PmVrbonos.findByUsername", query = "SELECT p FROM PmVrbonos p WHERE p.username = :username")
    , @NamedQuery(name = "PmVrbonos.findByCreado", query = "SELECT p FROM PmVrbonos p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmVrbonos.findByModificado", query = "SELECT p FROM PmVrbonos p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmVrbonos.findByEstadoReg", query = "SELECT p FROM PmVrbonos p WHERE p.estadoReg = :estadoReg")})
public class PmVrbonos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_vrbonos")
    private Integer idPmVrbonos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_bono_salarial")
    private int vrBonoSalarial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_bono_alimentos")
    private int vrBonoAlimentos;
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
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;

    public PmVrbonos() {
    }

    public PmVrbonos(Integer idPmVrbonos) {
        this.idPmVrbonos = idPmVrbonos;
    }

    public PmVrbonos(Integer idPmVrbonos, Date desde, Date hasta, int vrBonoSalarial, int vrBonoAlimentos, String username, Date creado, int estadoReg) {
        this.idPmVrbonos = idPmVrbonos;
        this.desde = desde;
        this.hasta = hasta;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPmVrbonos() {
        return idPmVrbonos;
    }

    public void setIdPmVrbonos(Integer idPmVrbonos) {
        this.idPmVrbonos = idPmVrbonos;
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

    public int getVrBonoSalarial() {
        return vrBonoSalarial;
    }

    public void setVrBonoSalarial(int vrBonoSalarial) {
        this.vrBonoSalarial = vrBonoSalarial;
    }

    public int getVrBonoAlimentos() {
        return vrBonoAlimentos;
    }

    public void setVrBonoAlimentos(int vrBonoAlimentos) {
        this.vrBonoAlimentos = vrBonoAlimentos;
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

    public EmpleadoTipoCargo getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(EmpleadoTipoCargo idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmVrbonos != null ? idPmVrbonos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmVrbonos)) {
            return false;
        }
        PmVrbonos other = (PmVrbonos) object;
        if ((this.idPmVrbonos == null && other.idPmVrbonos != null) || (this.idPmVrbonos != null && !this.idPmVrbonos.equals(other.idPmVrbonos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmVrbonos[ idPmVrbonos=" + idPmVrbonos + " ]";
    }
    
}
