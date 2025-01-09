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
@Table(name = "nomina_server_param_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NominaServerParamEmpresa.findAll", query = "SELECT n FROM NominaServerParamEmpresa n"),
    @NamedQuery(name = "NominaServerParamEmpresa.findByIdNominaServerParamEmpresa", query = "SELECT n FROM NominaServerParamEmpresa n WHERE n.idNominaServerParamEmpresa = :idNominaServerParamEmpresa"),
    @NamedQuery(name = "NominaServerParamEmpresa.findByCodSwNomina", query = "SELECT n FROM NominaServerParamEmpresa n WHERE n.codSwNomina = :codSwNomina"),
    @NamedQuery(name = "NominaServerParamEmpresa.findByUsername", query = "SELECT n FROM NominaServerParamEmpresa n WHERE n.username = :username"),
    @NamedQuery(name = "NominaServerParamEmpresa.findByCreado", query = "SELECT n FROM NominaServerParamEmpresa n WHERE n.creado = :creado"),
    @NamedQuery(name = "NominaServerParamEmpresa.findByModificado", query = "SELECT n FROM NominaServerParamEmpresa n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NominaServerParamEmpresa.findByEstadoReg", query = "SELECT n FROM NominaServerParamEmpresa n WHERE n.estadoReg = :estadoReg")})
public class NominaServerParamEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nomina_server_param_empresa")
    private Integer idNominaServerParamEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cod_sw_nomina")
    private String codSwNomina;
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
    @JoinColumn(name = "id_nomina_server_param", referencedColumnName = "id_nomina_server_param")
    @ManyToOne(fetch = FetchType.LAZY)
    private NominaServerParam idNominaServerParam;

    public NominaServerParamEmpresa() {
    }

    public NominaServerParamEmpresa(Integer idNominaServerParamEmpresa) {
        this.idNominaServerParamEmpresa = idNominaServerParamEmpresa;
    }

    public NominaServerParamEmpresa(Integer idNominaServerParamEmpresa, String codSwNomina, String username, Date creado, int estadoReg) {
        this.idNominaServerParamEmpresa = idNominaServerParamEmpresa;
        this.codSwNomina = codSwNomina;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNominaServerParamEmpresa() {
        return idNominaServerParamEmpresa;
    }

    public void setIdNominaServerParamEmpresa(Integer idNominaServerParamEmpresa) {
        this.idNominaServerParamEmpresa = idNominaServerParamEmpresa;
    }

    public String getCodSwNomina() {
        return codSwNomina;
    }

    public void setCodSwNomina(String codSwNomina) {
        this.codSwNomina = codSwNomina;
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

    public NominaServerParam getIdNominaServerParam() {
        return idNominaServerParam;
    }

    public void setIdNominaServerParam(NominaServerParam idNominaServerParam) {
        this.idNominaServerParam = idNominaServerParam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNominaServerParamEmpresa != null ? idNominaServerParamEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaServerParamEmpresa)) {
            return false;
        }
        NominaServerParamEmpresa other = (NominaServerParamEmpresa) object;
        if ((this.idNominaServerParamEmpresa == null && other.idNominaServerParamEmpresa != null) || (this.idNominaServerParamEmpresa != null && !this.idNominaServerParamEmpresa.equals(other.idNominaServerParamEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NominaServerParamEmpresa[ idNominaServerParamEmpresa=" + idNominaServerParamEmpresa + " ]";
    }
    
}
