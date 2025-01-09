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
@Table(name = "tbl_calendario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCalendario.findAll", query = "SELECT t FROM TblCalendario t"),
    @NamedQuery(name = "TblCalendario.findByIdTblCalendario", query = "SELECT t FROM TblCalendario t WHERE t.idTblCalendario = :idTblCalendario"),
    @NamedQuery(name = "TblCalendario.findByFecha", query = "SELECT t FROM TblCalendario t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TblCalendario.findByTipoDia", query = "SELECT t FROM TblCalendario t WHERE t.tipoDia = :tipoDia"),
    @NamedQuery(name = "TblCalendario.findByUsername", query = "SELECT t FROM TblCalendario t WHERE t.username = :username"),
    @NamedQuery(name = "TblCalendario.findByCreado", query = "SELECT t FROM TblCalendario t WHERE t.creado = :creado"),
    @NamedQuery(name = "TblCalendario.findByModificado", query = "SELECT t FROM TblCalendario t WHERE t.modificado = :modificado"),
    @NamedQuery(name = "TblCalendario.findByEstadoReg", query = "SELECT t FROM TblCalendario t WHERE t.estadoReg = :estadoReg")})
public class TblCalendario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tbl_calendario")
    private Integer idTblCalendario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo_dia")
    private String tipoDia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estacionalidad")
    private int estacionalidad;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
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
    @JoinColumn(name = "id_tbl_calendario_caracteristica_dia", referencedColumnName = "id_tbl_calendario_caracteristica_dia")
    @ManyToOne(fetch = FetchType.LAZY)
    private TblCalendarioCaracteristicasDia idTblCalendarioCaracteristicaDia;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public TblCalendario() {
    }

    public TblCalendario(Integer idTblCalendario) {
        this.idTblCalendario = idTblCalendario;
    }

    public TblCalendario(Integer idTblCalendario, Date fecha, String tipoDia, int estacionalidad, String observacion, String username, Date creado, int estadoReg) {
        this.idTblCalendario = idTblCalendario;
        this.fecha = fecha;
        this.tipoDia = tipoDia;
        this.estacionalidad = estacionalidad;
        this.observacion = observacion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdTblCalendario() {
        return idTblCalendario;
    }

    public void setIdTblCalendario(Integer idTblCalendario) {
        this.idTblCalendario = idTblCalendario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public int getEstacionalidad() {
        return estacionalidad;
    }

    public void setEstacionalidad(int estacionalidad) {
        this.estacionalidad = estacionalidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public TblCalendarioCaracteristicasDia getIdTblCalendarioCaracteristicaDia() {
        return idTblCalendarioCaracteristicaDia;
    }

    public void setIdTblCalendarioCaracteristicaDia(TblCalendarioCaracteristicasDia idTblCalendarioCaracteristicaDia) {
        this.idTblCalendarioCaracteristicaDia = idTblCalendarioCaracteristicaDia;
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
        hash += (idTblCalendario != null ? idTblCalendario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCalendario)) {
            return false;
        }
        TblCalendario other = (TblCalendario) object;
        if ((this.idTblCalendario == null && other.idTblCalendario != null) || (this.idTblCalendario != null && !this.idTblCalendario.equals(other.idTblCalendario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.TblCalendario[ idTblCalendario=" + idTblCalendario + " ]";
    }

}
