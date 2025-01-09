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
@Table(name = "param_cierre_ausentismo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamCierreAusentismo.findAll", query = "SELECT p FROM ParamCierreAusentismo p"),
    @NamedQuery(name = "ParamCierreAusentismo.findByIdParamCierreAusentismo", query = "SELECT p FROM ParamCierreAusentismo p WHERE p.idParamCierreAusentismo = :idParamCierreAusentismo"),
    @NamedQuery(name = "ParamCierreAusentismo.findByDesde", query = "SELECT p FROM ParamCierreAusentismo p WHERE p.desde = :desde"),
    @NamedQuery(name = "ParamCierreAusentismo.findByHasta", query = "SELECT p FROM ParamCierreAusentismo p WHERE p.hasta = :hasta"),
    @NamedQuery(name = "ParamCierreAusentismo.findByBloqueado", query = "SELECT p FROM ParamCierreAusentismo p WHERE p.bloqueado = :bloqueado"),
    @NamedQuery(name = "ParamCierreAusentismo.findByUsername", query = "SELECT p FROM ParamCierreAusentismo p WHERE p.username = :username"),
    @NamedQuery(name = "ParamCierreAusentismo.findByCreado", query = "SELECT p FROM ParamCierreAusentismo p WHERE p.creado = :creado"),
    @NamedQuery(name = "ParamCierreAusentismo.findByModificado", query = "SELECT p FROM ParamCierreAusentismo p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "ParamCierreAusentismo.findByEstadoReg", query = "SELECT p FROM ParamCierreAusentismo p WHERE p.estadoReg = :estadoReg")})
public class ParamCierreAusentismo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_cierre_ausentismo")
    private Integer idParamCierreAusentismo;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bloqueado")
    private int bloqueado;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
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

    public ParamCierreAusentismo() {
    }

    public ParamCierreAusentismo(Integer idParamCierreAusentismo) {
        this.idParamCierreAusentismo = idParamCierreAusentismo;
    }

    public ParamCierreAusentismo(Integer idParamCierreAusentismo, int bloqueado, int estadoReg) {
        this.idParamCierreAusentismo = idParamCierreAusentismo;
        this.bloqueado = bloqueado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdParamCierreAusentismo() {
        return idParamCierreAusentismo;
    }

    public void setIdParamCierreAusentismo(Integer idParamCierreAusentismo) {
        this.idParamCierreAusentismo = idParamCierreAusentismo;
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

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParamCierreAusentismo != null ? idParamCierreAusentismo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamCierreAusentismo)) {
            return false;
        }
        ParamCierreAusentismo other = (ParamCierreAusentismo) object;
        if ((this.idParamCierreAusentismo == null && other.idParamCierreAusentismo != null) || (this.idParamCierreAusentismo != null && !this.idParamCierreAusentismo.equals(other.idParamCierreAusentismo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ParamCierreAusentismo[ idParamCierreAusentismo=" + idParamCierreAusentismo + " ]";
    }
    
}
