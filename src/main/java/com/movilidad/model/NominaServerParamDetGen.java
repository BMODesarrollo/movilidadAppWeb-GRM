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
@Table(name = "nomina_server_param_det_gen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NominaServerParamDetGen.findAll", query = "SELECT n FROM NominaServerParamDetGen n"),
    @NamedQuery(name = "NominaServerParamDetGen.findByIdNominaServerParamDetGen", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.idNominaServerParamDetGen = :idNominaServerParamDetGen"),
    @NamedQuery(name = "NominaServerParamDetGen.findByCodConc", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.codConc = :codConc"),
    @NamedQuery(name = "NominaServerParamDetGen.findByTipo", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.tipo = :tipo"),
    @NamedQuery(name = "NominaServerParamDetGen.findByGeneraNov", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.generaNov = :generaNov"),
    @NamedQuery(name = "NominaServerParamDetGen.findByCodMause", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.codMause = :codMause"),
    @NamedQuery(name = "NominaServerParamDetGen.findByUsername", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.username = :username"),
    @NamedQuery(name = "NominaServerParamDetGen.findByCreado", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.creado = :creado"),
    @NamedQuery(name = "NominaServerParamDetGen.findByModificado", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NominaServerParamDetGen.findByEstadoReg", query = "SELECT n FROM NominaServerParamDetGen n WHERE n.estadoReg = :estadoReg")})
public class NominaServerParamDetGen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nomina_server_param_det_gen")
    private Integer idNominaServerParamDetGen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cod_conc")
    private String codConc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "genera_nov")
    private String generaNov;
    @Size(max = 255)
    @Column(name = "cod_mause")
    private String codMause;
    @Column(name = "clasificacion")
    private int clasificacion;
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
    @JoinColumn(name = "id_generica_tipo_detalle", referencedColumnName = "id_generica_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaTipoDetalles idGenericaTipoDetalle;
    @JoinColumn(name = "id_nomina_server_param", referencedColumnName = "id_nomina_server_param")
    @ManyToOne(fetch = FetchType.LAZY)
    private NominaServerParam idNominaServerParam;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public NominaServerParamDetGen() {
    }

    public NominaServerParamDetGen(Integer idNominaServerParamDetGen) {
        this.idNominaServerParamDetGen = idNominaServerParamDetGen;
    }

    public NominaServerParamDetGen(Integer idNominaServerParamDetGen, String codConcepto, String tipo, String generaNov, String codigoMaus, int clasificacion, String username, Date creado, int estadoReg) {
        this.idNominaServerParamDetGen = idNominaServerParamDetGen;
        this.codConc = codConcepto;
        this.clasificacion = clasificacion;
        this.tipo = tipo;
        this.generaNov = generaNov;
        this.codMause = codigoMaus;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNominaServerParamDetGen() {
        return idNominaServerParamDetGen;
    }

    public void setIdNominaServerParamDetGen(Integer idNominaServerParamDetGen) {
        this.idNominaServerParamDetGen = idNominaServerParamDetGen;
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

    public GenericaTipoDetalles getIdGenericaTipoDetalle() {
        return idGenericaTipoDetalle;
    }

    public void setIdGenericaTipoDetalle(GenericaTipoDetalles idGenericaTipoDetalle) {
        this.idGenericaTipoDetalle = idGenericaTipoDetalle;
    }

    public NominaServerParam getIdNominaServerParam() {
        return idNominaServerParam;
    }

    public void setIdNominaServerParam(NominaServerParam idNominaServerParam) {
        this.idNominaServerParam = idNominaServerParam;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNominaServerParamDetGen != null ? idNominaServerParamDetGen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaServerParamDetGen)) {
            return false;
        }
        NominaServerParamDetGen other = (NominaServerParamDetGen) object;
        if ((this.idNominaServerParamDetGen == null && other.idNominaServerParamDetGen != null) || (this.idNominaServerParamDetGen != null && !this.idNominaServerParamDetGen.equals(other.idNominaServerParamDetGen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NominaServerParamDetGen[ idNominaServerParamDetGen=" + idNominaServerParamDetGen + " ]";
    }

}
