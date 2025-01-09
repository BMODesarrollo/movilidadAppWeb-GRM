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
 * @author Omar.beltran
 */
@Entity
@Table(name = "biometrico_config")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BiometricoConfig.findAll", query = "SELECT b FROM BiometricoConfig b")
    ,
    @NamedQuery(name = "BiometricoConfig.findByIdBiometricoConfig", query = "SELECT b FROM BiometricoConfig b WHERE b.idBioConfig = :idBioConfig")
    ,
    @NamedQuery(name = "BiometricoConfig.findByKey", query = "SELECT b FROM BiometricoConfig b WHERE b.item = :item")
    ,
    @NamedQuery(name = "BiometricoConfig.findByConcepto", query = "SELECT b FROM BiometricoConfig b WHERE b.concepto = :concepto")
    ,
    @NamedQuery(name = "BiometricoConfig.findByUsernameCre", query = "SELECT b FROM BiometricoConfig b WHERE b.usernameCre = :usernameCre")
    ,
    @NamedQuery(name = "BiometricoConfig.findByUsernameMod", query = "SELECT b FROM BiometricoConfig b WHERE b.usernameMod = :usernameMod")
    ,
    @NamedQuery(name = "BiometricoConfig.findByCreado", query = "SELECT b FROM BiometricoConfig b WHERE b.creado = :creado")
    ,
    @NamedQuery(name = "BiometricoConfig.findByModificado", query = "SELECT b FROM BiometricoConfig b WHERE b.modificado = :modificado")
    ,
    @NamedQuery(name = "BiometricoConfig.findByIdParamArea", query = "SELECT b FROM BiometricoConfig b WHERE b.idParamArea = :idParamArea")
    ,
    @NamedQuery(name = "BiometricoConfig.findByEstadoReg", query = "SELECT b FROM BiometricoConfig b WHERE b.estadoReg = :estadoReg")})

public class BiometricoConfig implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_bio_config")
    private Integer idBioConfig;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "item")
    private String item;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "concepto")
    private String concepto;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "valor")
    private String valor;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username_cre")
    private String usernameCre;
    
    @Basic(optional = false)
    @Size(min = 1, max = 15)
    @Column(name = "username_mod")
    private String usernameMod;
    
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    
    @Column(name = "estado_reg")
    private Integer estadoReg;
    
    @Column(name = "id_param_area")
    private Integer idParamArea;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getIdBioConfig() {
        return idBioConfig;
    }

    public void setIdBioConfig(Integer idBioConfig) {
        this.idBioConfig = idBioConfig;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getUsernameCre() {
        return usernameCre;
    }

    public void setUsernameCre(String usernameCre) {
        this.usernameCre = usernameCre;
    }

    public String getUsernameMod() {
        return usernameMod;
    }

    public void setUsernameMod(String usernameMod) {
        this.usernameMod = usernameMod;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBioConfig != null ? idBioConfig.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamReporteHoras)) {
            return false;
        }
        BiometricoConfig other = (BiometricoConfig) object;
        if ((this.idBioConfig == null && other.idBioConfig != null) || (this.idBioConfig != null && !this.idBioConfig.equals(other.idBioConfig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.BiometricoConfig[ idBioConfig=" + idBioConfig + " ]";
    }

    public Integer getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(Integer idParamArea) {
        this.idParamArea = idParamArea;
    }
    
}
