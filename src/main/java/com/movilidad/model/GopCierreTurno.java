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
 * @author solucionesit
 */
@Entity
@Table(name = "gop_cierre_turno")
@XmlRootElement
public class GopCierreTurno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gop_cierre_turno")
    private Integer idGopCierreTurno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "user_tec_control")
    private String userTecControl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "user_tec_control_recibe")
    private String userTecControlRecibe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "user_tec_patio")
    private String userTecPatio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "user_tec_patio_recibe")
    private String userTecPatioRecibe;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public GopCierreTurno() {
    }

    public GopCierreTurno(Integer idGopCierreTurno) {
        this.idGopCierreTurno = idGopCierreTurno;
    }

    public GopCierreTurno(Integer idGopCierreTurno, Date fechaHora, String observacion, String descripcion, String userTecControl, String userTecPatio) {
        this.idGopCierreTurno = idGopCierreTurno;
        this.fechaHora = fechaHora;
        this.observacion = observacion;
        this.descripcion = descripcion;
        this.userTecControl = userTecControl;
        this.userTecPatio = userTecPatio;
    }

    public Integer getIdGopCierreTurno() {
        return idGopCierreTurno;
    }

    public void setIdGopCierreTurno(Integer idGopCierreTurno) {
        this.idGopCierreTurno = idGopCierreTurno;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUserTecControl() {
        return userTecControl;
    }

    public void setUserTecControl(String userTecControl) {
        this.userTecControl = userTecControl;
    }

    public String getUserTecPatio() {
        return userTecPatio;
    }

    public void setUserTecPatio(String userTecPatio) {
        this.userTecPatio = userTecPatio;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public String getUserTecControlRecibe() {
        return userTecControlRecibe;
    }

    public void setUserTecControlRecibe(String userTecControlRecibe) {
        this.userTecControlRecibe = userTecControlRecibe;
    }

    public String getUserTecPatioRecibe() {
        return userTecPatioRecibe;
    }

    public void setUserTecPatioRecibe(String userTecPatioRecibe) {
        this.userTecPatioRecibe = userTecPatioRecibe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGopCierreTurno != null ? idGopCierreTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GopCierreTurno)) {
            return false;
        }
        GopCierreTurno other = (GopCierreTurno) object;
        if ((this.idGopCierreTurno == null && other.idGopCierreTurno != null) || (this.idGopCierreTurno != null && !this.idGopCierreTurno.equals(other.idGopCierreTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GopCierreTurno[ idGopCierreTurno=" + idGopCierreTurno + " ]";
    }

}
