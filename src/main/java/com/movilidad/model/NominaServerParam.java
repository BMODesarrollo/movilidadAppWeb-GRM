/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "nomina_server_param")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NominaServerParam.findAll", query = "SELECT n FROM NominaServerParam n"),
    @NamedQuery(name = "NominaServerParam.findByIdNominaServerParam", query = "SELECT n FROM NominaServerParam n WHERE n.idNominaServerParam = :idNominaServerParam"),
    @NamedQuery(name = "NominaServerParam.findByUrlWs", query = "SELECT n FROM NominaServerParam n WHERE n.urlWs = :urlWs"),
    @NamedQuery(name = "NominaServerParam.findByUsr", query = "SELECT n FROM NominaServerParam n WHERE n.usr = :usr"),
    @NamedQuery(name = "NominaServerParam.findByPwd", query = "SELECT n FROM NominaServerParam n WHERE n.pwd = :pwd"),
    @NamedQuery(name = "NominaServerParam.findByCodigoBonoSalarial", query = "SELECT n FROM NominaServerParam n WHERE n.codigoBonoSalarial = :codigoBonoSalarial"),
    @NamedQuery(name = "NominaServerParam.findByCodigoBonoAlimentacion", query = "SELECT n FROM NominaServerParam n WHERE n.codigoBonoAlimentacion = :codigoBonoAlimentacion"),
    @NamedQuery(name = "NominaServerParam.findByUsername", query = "SELECT n FROM NominaServerParam n WHERE n.username = :username"),
    @NamedQuery(name = "NominaServerParam.findByCreado", query = "SELECT n FROM NominaServerParam n WHERE n.creado = :creado"),
    @NamedQuery(name = "NominaServerParam.findByModificado", query = "SELECT n FROM NominaServerParam n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NominaServerParam.findByEstadoReg", query = "SELECT n FROM NominaServerParam n WHERE n.estadoReg = :estadoReg")})
public class NominaServerParam implements Serializable {

    @OneToMany(mappedBy = "idNominaServerParam", fetch = FetchType.LAZY)
    private List<NominaServerParamDetGen> nominaServerParamDetGenList;

    @OneToMany(mappedBy = "idNominaServerParam", fetch = FetchType.LAZY)
    private List<NominaServerParamDet> nominaServerParamDetList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nomina_server_param")
    private Integer idNominaServerParam;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "url_ws")
    private String urlWs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "usr")
    private String usr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "pwd")
    private String pwd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigo_bono_salarial")
    private String codigoBonoSalarial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigo_bono_alimentacion")
    private String codigoBonoAlimentacion;
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
    @OneToMany(mappedBy = "idNominaServerParam", fetch = FetchType.LAZY)
    private List<NominaServerParamEmpresa> nominaServerParamEmpresaList;

    public NominaServerParam() {
    }

    public NominaServerParam(Integer idNominaServerParam) {
        this.idNominaServerParam = idNominaServerParam;
    }

    public NominaServerParam(Integer idNominaServerParam, String urlWs, String usr, String pwd, String codigoBonoSalarial, String codigoBonoAlimentacion) {
        this.idNominaServerParam = idNominaServerParam;
        this.urlWs = urlWs;
        this.usr = usr;
        this.pwd = pwd;
        this.codigoBonoSalarial = codigoBonoSalarial;
        this.codigoBonoAlimentacion = codigoBonoAlimentacion;
    }

    public Integer getIdNominaServerParam() {
        return idNominaServerParam;
    }

    public void setIdNominaServerParam(Integer idNominaServerParam) {
        this.idNominaServerParam = idNominaServerParam;
    }

    public String getUrlWs() {
        return urlWs;
    }

    public void setUrlWs(String urlWs) {
        this.urlWs = urlWs;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCodigoBonoSalarial() {
        return codigoBonoSalarial;
    }

    public void setCodigoBonoSalarial(String codigoBonoSalarial) {
        this.codigoBonoSalarial = codigoBonoSalarial;
    }

    public String getCodigoBonoAlimentacion() {
        return codigoBonoAlimentacion;
    }

    public void setCodigoBonoAlimentacion(String codigoBonoAlimentacion) {
        this.codigoBonoAlimentacion = codigoBonoAlimentacion;
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
        hash += (idNominaServerParam != null ? idNominaServerParam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaServerParam)) {
            return false;
        }
        NominaServerParam other = (NominaServerParam) object;
        if ((this.idNominaServerParam == null && other.idNominaServerParam != null) || (this.idNominaServerParam != null && !this.idNominaServerParam.equals(other.idNominaServerParam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NominaServerParam[ idNominaServerParam=" + idNominaServerParam + " ]";
    }

    @XmlTransient
    public List<NominaServerParamDet> getNominaServerParamDetList() {
        return nominaServerParamDetList;
    }

    public void setNominaServerParamDetList(List<NominaServerParamDet> nominaServerParamDetList) {
        this.nominaServerParamDetList = nominaServerParamDetList;
    }

    @XmlTransient
    public List<NominaServerParamEmpresa> getNominaServerParamEmpresaList() {
        return nominaServerParamEmpresaList;
    }

    public void setNominaServerParamEmpresaList(List<NominaServerParamEmpresa> nominaServerParamEmpresaList) {
        this.nominaServerParamEmpresaList = nominaServerParamEmpresaList;
    }

    @XmlTransient
    public List<NominaServerParamDetGen> getNominaServerParamDetGenList() {
        return nominaServerParamDetGenList;
    }

    public void setNominaServerParamDetGenList(List<NominaServerParamDetGen> nominaServerParamDetGenList) {
        this.nominaServerParamDetGenList = nominaServerParamDetGenList;
    }
    
}
