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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "param_cumplimiento_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamCumplimientoServicio.findAll", query = "SELECT p FROM ParamCumplimientoServicio p"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByIdParamCumplimientoServicio", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.idParamCumplimientoServicio = :idParamCumplimientoServicio"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByPeriodo", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.periodo = :periodo"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByTipoDia", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.tipoDia = :tipoDia"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByNombre", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByHoraInicio", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.horaInicio = :horaInicio"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByHoraFin", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.horaFin = :horaFin"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByDesde", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.desde = :desde"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByHasta", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.hasta = :hasta"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByUsername", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.username = :username"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByCreado", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.creado = :creado"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByModificado", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "ParamCumplimientoServicio.findByEstadoReg", query = "SELECT p FROM ParamCumplimientoServicio p WHERE p.estadoReg = :estadoReg")})
public class ParamCumplimientoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_cumplimiento_servicio")
    private Integer idParamCumplimientoServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodo")
    private int periodo;
    @Column(name = "tipo_dia")
    private Character tipoDia;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 8)
    @Column(name = "hora_inicio")
    private String horaInicio;
    @Size(max = 8)
    @Column(name = "hora_fin")
    private String horaFin;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
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

    public ParamCumplimientoServicio() {
    }

    public ParamCumplimientoServicio(Integer idParamCumplimientoServicio) {
        this.idParamCumplimientoServicio = idParamCumplimientoServicio;
    }

    public ParamCumplimientoServicio(Integer idParamCumplimientoServicio, int periodo) {
        this.idParamCumplimientoServicio = idParamCumplimientoServicio;
        this.periodo = periodo;
    }

    public Integer getIdParamCumplimientoServicio() {
        return idParamCumplimientoServicio;
    }

    public void setIdParamCumplimientoServicio(Integer idParamCumplimientoServicio) {
        this.idParamCumplimientoServicio = idParamCumplimientoServicio;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public Character getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(Character tipoDia) {
        this.tipoDia = tipoDia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParamCumplimientoServicio != null ? idParamCumplimientoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamCumplimientoServicio)) {
            return false;
        }
        ParamCumplimientoServicio other = (ParamCumplimientoServicio) object;
        if ((this.idParamCumplimientoServicio == null && other.idParamCumplimientoServicio != null) || (this.idParamCumplimientoServicio != null && !this.idParamCumplimientoServicio.equals(other.idParamCumplimientoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ParamCumplimientoServicio[ idParamCumplimientoServicio=" + idParamCumplimientoServicio + " ]";
    }
    
}
