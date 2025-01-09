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
@Table(name = "nomina_server_param_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NominaServerParamDet.findAll", query = "SELECT n FROM NominaServerParamDet n"),
    @NamedQuery(name = "NominaServerParamDet.findByIdNominaServerParamDet", query = "SELECT n FROM NominaServerParamDet n WHERE n.idNominaServerParamDet = :idNominaServerParamDet"),
    @NamedQuery(name = "NominaServerParamDet.findByCodConc", query = "SELECT n FROM NominaServerParamDet n WHERE n.codConc = :codConc"),
    @NamedQuery(name = "NominaServerParamDet.findByTipo", query = "SELECT n FROM NominaServerParamDet n WHERE n.tipo = :tipo"),
    @NamedQuery(name = "NominaServerParamDet.findByGeneraNov", query = "SELECT n FROM NominaServerParamDet n WHERE n.generaNov = :generaNov"),
    @NamedQuery(name = "NominaServerParamDet.findByCodMause", query = "SELECT n FROM NominaServerParamDet n WHERE n.codMause = :codMause"),
    @NamedQuery(name = "NominaServerParamDet.findByUsername", query = "SELECT n FROM NominaServerParamDet n WHERE n.username = :username"),
    @NamedQuery(name = "NominaServerParamDet.findByCreado", query = "SELECT n FROM NominaServerParamDet n WHERE n.creado = :creado"),
    @NamedQuery(name = "NominaServerParamDet.findByModificado", query = "SELECT n FROM NominaServerParamDet n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NominaServerParamDet.findByEstadoReg", query = "SELECT n FROM NominaServerParamDet n WHERE n.estadoReg = :estadoReg")})
public class NominaServerParamDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nomina_server_param_det")
    private Integer idNominaServerParamDet;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cod_conc")
    private String codConc;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "genera_nov")
    private String generaNov;
    @Size(max = 255)
    @Column(name = "cod_mause")
    private String codMause;
    @Basic(optional = false)
    @NotNull
    @Column(name = "clasificacion")
    private int clasificacion;
    @Size(max = 4)
    @Column(name = "tipo_incapacidad")
    private String tipoIncapacidad;
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
    @JoinColumn(name = "id_nomina_server_param", referencedColumnName = "id_nomina_server_param")
    @ManyToOne(fetch = FetchType.LAZY)
    private NominaServerParam idNominaServerParam;
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadTipoDetalle;

    public NominaServerParamDet() {
    }

    public NominaServerParamDet(Integer idNominaServerParamDet) {
        this.idNominaServerParamDet = idNominaServerParamDet;
    }

    public NominaServerParamDet(Integer idNominaServerParamDet, String codConcepto, String username, int clasificacion, Date creado, int estadoReg) {
        this.idNominaServerParamDet = idNominaServerParamDet;
        this.codConc = codConcepto;
        this.clasificacion = clasificacion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNominaServerParamDet() {
        return idNominaServerParamDet;
    }

    public void setIdNominaServerParamDet(Integer idNominaServerParamDet) {
        this.idNominaServerParamDet = idNominaServerParamDet;
    }

    public String getCodConc() {
        return codConc;
    }

    public void setCodConc(String codConc) {
        this.codConc = codConc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGeneraNov() {
        return generaNov;
    }

    public void setGeneraNov(String generaNov) {
        this.generaNov = generaNov;
    }

    public String getCodMause() {
        return codMause;
    }

    public void setCodMause(String codMause) {
        this.codMause = codMause;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTipoIncapacidad() {
        return tipoIncapacidad;
    }

    public void setTipoIncapacidad(String tipoIncapacidad) {
        this.tipoIncapacidad = tipoIncapacidad;
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

    public NominaServerParam getIdNominaServerParam() {
        return idNominaServerParam;
    }

    public void setIdNominaServerParam(NominaServerParam idNominaServerParam) {
        this.idNominaServerParam = idNominaServerParam;
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
        hash += (idNominaServerParamDet != null ? idNominaServerParamDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaServerParamDet)) {
            return false;
        }
        NominaServerParamDet other = (NominaServerParamDet) object;
        if ((this.idNominaServerParamDet == null && other.idNominaServerParamDet != null) || (this.idNominaServerParamDet != null && !this.idNominaServerParamDet.equals(other.idNominaServerParamDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NominaServerParamDet[ idNominaServerParamDet=" + idNominaServerParamDet + " ]";
    }

}
