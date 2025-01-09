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
@Table(name = "chk_componente_falla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChkComponenteFalla.findAll", query = "SELECT c FROM ChkComponenteFalla c"),
    @NamedQuery(name = "ChkComponenteFalla.findByIdChkComponenteFalla", query = "SELECT c FROM ChkComponenteFalla c WHERE c.idChkComponenteFalla = :idChkComponenteFalla"),
    @NamedQuery(name = "ChkComponenteFalla.findByNombre", query = "SELECT c FROM ChkComponenteFalla c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ChkComponenteFalla.findByAfectaDisponibilidad", query = "SELECT c FROM ChkComponenteFalla c WHERE c.afectaDisponibilidad = :afectaDisponibilidad"),
    @NamedQuery(name = "ChkComponenteFalla.findByUsername", query = "SELECT c FROM ChkComponenteFalla c WHERE c.username = :username"),
    @NamedQuery(name = "ChkComponenteFalla.findByCreado", query = "SELECT c FROM ChkComponenteFalla c WHERE c.creado = :creado"),
    @NamedQuery(name = "ChkComponenteFalla.findByModificado", query = "SELECT c FROM ChkComponenteFalla c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "ChkComponenteFalla.findByEstadoReg", query = "SELECT c FROM ChkComponenteFalla c WHERE c.estadoReg = :estadoReg")})
public class ChkComponenteFalla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chk_componente_falla")
    private Integer idChkComponenteFalla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "afecta_disponibilidad")
    private Integer afectaDisponibilidad;
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
    @JoinColumn(name = "id_chk_componente", referencedColumnName = "id_chk_componente")
    @ManyToOne(optional = false)
    private ChkComponente idChkComponente;
    @JoinColumn(name = "id_disp_sistema", referencedColumnName = "id_disp_sistema")
    @ManyToOne
    private DispSistema idDispSistema;
    @JoinColumn(name = "id_novedad_tipo", referencedColumnName = "id_novedad_tipo")
    @ManyToOne
    private NovedadTipo idNovedadTipo;
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne
    private NovedadTipoDetalles idNovedadTipoDetalle;

    public ChkComponenteFalla() {
    }

    public ChkComponenteFalla(Integer idChkComponenteFalla) {
        this.idChkComponenteFalla = idChkComponenteFalla;
    }

    public ChkComponenteFalla(Integer idChkComponenteFalla, String nombre) {
        this.idChkComponenteFalla = idChkComponenteFalla;
        this.nombre = nombre;
    }

    public Integer getIdChkComponenteFalla() {
        return idChkComponenteFalla;
    }

    public void setIdChkComponenteFalla(Integer idChkComponenteFalla) {
        this.idChkComponenteFalla = idChkComponenteFalla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAfectaDisponibilidad() {
        return afectaDisponibilidad;
    }

    public void setAfectaDisponibilidad(Integer afectaDisponibilidad) {
        this.afectaDisponibilidad = afectaDisponibilidad;
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

    public ChkComponente getIdChkComponente() {
        return idChkComponente;
    }

    public void setIdChkComponente(ChkComponente idChkComponente) {
        this.idChkComponente = idChkComponente;
    }

    public DispSistema getIdDispSistema() {
        return idDispSistema;
    }

    public void setIdDispSistema(DispSistema idDispSistema) {
        this.idDispSistema = idDispSistema;
    }

    public NovedadTipo getIdNovedadTipo() {
        return idNovedadTipo;
    }

    public void setIdNovedadTipo(NovedadTipo idNovedadTipo) {
        this.idNovedadTipo = idNovedadTipo;
    }

    public NovedadTipoDetalles getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(NovedadTipoDetalles idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChkComponenteFalla != null ? idChkComponenteFalla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChkComponenteFalla)) {
            return false;
        }
        ChkComponenteFalla other = (ChkComponenteFalla) object;
        if ((this.idChkComponenteFalla == null && other.idChkComponenteFalla != null) || (this.idChkComponenteFalla != null && !this.idChkComponenteFalla.equals(other.idChkComponenteFalla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ChkComponenteFalla[ idChkComponenteFalla=" + idChkComponenteFalla + " ]";
    }
    
}
