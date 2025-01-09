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
 * @author solucionesit
 */
@Entity
@Table(name = "generica_pm_vrbonos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmVrbonos.findAll", query = "SELECT g FROM GenericaPmVrbonos g"),
    @NamedQuery(name = "GenericaPmVrbonos.findByIdGenericaPmVrbonos", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.idGenericaPmVrbonos = :idGenericaPmVrbonos"),
    @NamedQuery(name = "GenericaPmVrbonos.findByDesde", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.desde = :desde"),
    @NamedQuery(name = "GenericaPmVrbonos.findByHasta", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.hasta = :hasta"),
    @NamedQuery(name = "GenericaPmVrbonos.findByVrBonoSalarial", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.vrBonoSalarial = :vrBonoSalarial"),
    @NamedQuery(name = "GenericaPmVrbonos.findByVrBonoAlimentos", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.vrBonoAlimentos = :vrBonoAlimentos"),
    @NamedQuery(name = "GenericaPmVrbonos.findByUsername", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaPmVrbonos.findByCreado", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPmVrbonos.findByModificado", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPmVrbonos.findByEstadoReg", query = "SELECT g FROM GenericaPmVrbonos g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmVrbonos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pm_vrbonos")
    private Integer idGenericaPmVrbonos;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;

    public GenericaPmVrbonos() {
    }

    public GenericaPmVrbonos(Integer idGenericaPmVrbonos) {
        this.idGenericaPmVrbonos = idGenericaPmVrbonos;
    }

    public GenericaPmVrbonos(Integer idGenericaPmVrbonos, Date desde, Date hasta, int vrBonoSalarial, int vrBonoAlimentos, String username, Date creado, int estadoReg) {
        this.idGenericaPmVrbonos = idGenericaPmVrbonos;
        this.desde = desde;
        this.hasta = hasta;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaPmVrbonos() {
        return idGenericaPmVrbonos;
    }

    public void setIdGenericaPmVrbonos(Integer idGenericaPmVrbonos) {
        this.idGenericaPmVrbonos = idGenericaPmVrbonos;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
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
        hash += (idGenericaPmVrbonos != null ? idGenericaPmVrbonos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmVrbonos)) {
            return false;
        }
        GenericaPmVrbonos other = (GenericaPmVrbonos) object;
        if ((this.idGenericaPmVrbonos == null && other.idGenericaPmVrbonos != null) || (this.idGenericaPmVrbonos != null && !this.idGenericaPmVrbonos.equals(other.idGenericaPmVrbonos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmVrbonos[ idGenericaPmVrbonos=" + idGenericaPmVrbonos + " ]";
    }
    
}
