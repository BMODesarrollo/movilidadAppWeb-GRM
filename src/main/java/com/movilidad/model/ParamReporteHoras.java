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
@Table(name = "param_reporte_horas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamReporteHoras.findAll", query = "SELECT p FROM ParamReporteHoras p")
    ,
    @NamedQuery(name = "ParamReporteHoras.findByIdParamReporteHoras", query = "SELECT p FROM ParamReporteHoras p WHERE p.idParamReporteHoras = :idParamReporteHoras")
    ,
    @NamedQuery(name = "ParamReporteHoras.findByTipoHora", query = "SELECT p FROM ParamReporteHoras p WHERE p.tipoHora = :tipoHora")
    ,
    @NamedQuery(name = "ParamReporteHoras.findByConcepto", query = "SELECT p FROM ParamReporteHoras p WHERE p.concepto = :concepto")
    ,
    @NamedQuery(name = "ParamReporteHoras.findByCodigo", query = "SELECT p FROM ParamReporteHoras p WHERE p.codigo = :codigo")
    ,
    @NamedQuery(name = "ParamReporteHoras.findByUsername", query = "SELECT p FROM ParamReporteHoras p WHERE p.username = :username")
    ,
    @NamedQuery(name = "ParamReporteHoras.findByCreado", query = "SELECT p FROM ParamReporteHoras p WHERE p.creado = :creado")
    ,
    @NamedQuery(name = "ParamReporteHoras.findByModificado", query = "SELECT p FROM ParamReporteHoras p WHERE p.modificado = :modificado")
    ,
    @NamedQuery(name = "ParamReporteHoras.findByEstadoReg", query = "SELECT p FROM ParamReporteHoras p WHERE p.estadoReg = :estadoReg")})
public class ParamReporteHoras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_reporte_horas")
    private Integer idParamReporteHoras;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo_hora")
    private String tipoHora;
    @Column(name = "recargo")
    private Integer recargo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "concepto")
    private String concepto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
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

    @OneToMany(mappedBy = "idParamReporte", fetch = FetchType.LAZY)
    private List<GenericaJornadaDet> genericaJornadaDetList;
    @OneToMany(mappedBy = "idParamReporte", fetch = FetchType.LAZY)
    private List<PrgSerconDet> prgSerconList;

    public ParamReporteHoras() {
    }

    public ParamReporteHoras(Integer idParamReporteHoras) {
        this.idParamReporteHoras = idParamReporteHoras;
    }

    public ParamReporteHoras(Integer idParamReporteHoras, String tipoHora, String concepto, String codigo, String username) {
        this.idParamReporteHoras = idParamReporteHoras;
        this.tipoHora = tipoHora;
        this.concepto = concepto;
        this.codigo = codigo;
        this.username = username;
    }

    @XmlTransient
    public List<PrgSerconDet> getPrgSerconList() {
        return prgSerconList;
    }

    public void setPrgSerconList(List<PrgSerconDet> prgSerconList) {
        this.prgSerconList = prgSerconList;
    }

    public Integer getIdParamReporteHoras() {
        return idParamReporteHoras;
    }

    public void setIdParamReporteHoras(Integer idParamReporteHoras) {
        this.idParamReporteHoras = idParamReporteHoras;
    }

    public String getTipoHora() {
        return tipoHora;
    }

    public Integer getRecargo() {
        return recargo;
    }

    public void setRecargo(Integer recargo) {
        this.recargo = recargo;
    }

    public void setTipoHora(String tipoHora) {
        this.tipoHora = tipoHora;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        hash += (idParamReporteHoras != null ? idParamReporteHoras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamReporteHoras)) {
            return false;
        }
        ParamReporteHoras other = (ParamReporteHoras) object;
        if ((this.idParamReporteHoras == null && other.idParamReporteHoras != null) || (this.idParamReporteHoras != null && !this.idParamReporteHoras.equals(other.idParamReporteHoras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ParamReporteHoras[ idParamReporteHoras=" + idParamReporteHoras + " ]";
    }

    @XmlTransient
    public List<GenericaJornadaDet> getGenericaJornadaDetList() {
        return genericaJornadaDetList;
    }

    public void setGenericaJornadaDetList(List<GenericaJornadaDet> genericaJornadaDetList) {
        this.genericaJornadaDetList = genericaJornadaDetList;
    }

}
