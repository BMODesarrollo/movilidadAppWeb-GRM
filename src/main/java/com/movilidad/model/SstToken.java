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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "sst_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstToken.findAll", query = "SELECT s FROM SstToken s")
    , @NamedQuery(name = "SstToken.findByIdSstToken", query = "SELECT s FROM SstToken s WHERE s.idSstToken = :idSstToken")
    , @NamedQuery(name = "SstToken.findBySolicitado", query = "SELECT s FROM SstToken s WHERE s.solicitado = :solicitado")
    , @NamedQuery(name = "SstToken.findByToken", query = "SELECT s FROM SstToken s WHERE s.token = :token")
    , @NamedQuery(name = "SstToken.findByUsado", query = "SELECT s FROM SstToken s WHERE s.usado = :usado")
    , @NamedQuery(name = "SstToken.findByActivo", query = "SELECT s FROM SstToken s WHERE s.activo = :activo")
    , @NamedQuery(name = "SstToken.findByModificado", query = "SELECT s FROM SstToken s WHERE s.modificado = :modificado")
    , @NamedQuery(name = "SstToken.findByEstadoReg", query = "SELECT s FROM SstToken s WHERE s.estadoReg = :estadoReg")})
public class SstToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_token")
    private Integer idSstToken;
    @Column(name = "solicitado")
    @Temporal(TemporalType.DATE)
    private Date solicitado;
    @Size(max = 6)
    @Column(name = "token")
    private String token;
    @Column(name = "usado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usado;
    @Column(name = "activo")
    private Integer activo;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_sst_empresa", referencedColumnName = "id_sst_empresa")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstEmpresa idSstEmpresa;

    public SstToken() {
    }

    public SstToken(Integer idSstToken) {
        this.idSstToken = idSstToken;
    }

    public Integer getIdSstToken() {
        return idSstToken;
    }

    public void setIdSstToken(Integer idSstToken) {
        this.idSstToken = idSstToken;
    }

    public Date getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(Date solicitado) {
        this.solicitado = solicitado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUsado() {
        return usado;
    }

    public void setUsado(Date usado) {
        this.usado = usado;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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

    public SstEmpresa getIdSstEmpresa() {
        return idSstEmpresa;
    }

    public void setIdSstEmpresa(SstEmpresa idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstToken != null ? idSstToken.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstToken)) {
            return false;
        }
        SstToken other = (SstToken) object;
        if ((this.idSstToken == null && other.idSstToken != null) || (this.idSstToken != null && !this.idSstToken.equals(other.idSstToken))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstToken[ idSstToken=" + idSstToken + " ]";
    }
    
}
