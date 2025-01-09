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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author HP
 */
@Entity
@Table(name = "prg_pattern")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgPattern.findAll", query = "SELECT p FROM PrgPattern p")
    ,
    @NamedQuery(name = "PrgPattern.findByIdPrgPattern", query = "SELECT p FROM PrgPattern p WHERE p.idPrgPattern = :idPrgPattern")
    ,
    @NamedQuery(name = "PrgPattern.findByIdPattern", query = "SELECT p FROM PrgPattern p WHERE p.idPattern = :idPattern")
    ,
    @NamedQuery(name = "PrgPattern.findByPattern", query = "SELECT p FROM PrgPattern p WHERE p.pattern = :pattern")
    ,
    @NamedQuery(name = "PrgPattern.findByIdRoute", query = "SELECT p FROM PrgPattern p WHERE p.idRoute = :idRoute")
    ,
    @NamedQuery(name = "PrgPattern.findByRoute", query = "SELECT p FROM PrgPattern p WHERE p.route = :route")
    ,
    @NamedQuery(name = "PrgPattern.findBySecuenceNumber", query = "SELECT p FROM PrgPattern p WHERE p.secuenceNumber = :secuenceNumber")
    ,
    @NamedQuery(name = "PrgPattern.findByDistance", query = "SELECT p FROM PrgPattern p WHERE p.distance = :distance")
    ,
    @NamedQuery(name = "PrgPattern.findByIsActive", query = "SELECT p FROM PrgPattern p WHERE p.isActive = :isActive")
    ,
    @NamedQuery(name = "PrgPattern.findByIsCheckStopPoint", query = "SELECT p FROM PrgPattern p WHERE p.isCheckStopPoint = :isCheckStopPoint")
    ,
    @NamedQuery(name = "PrgPattern.findByIdStopPoint", query = "SELECT p FROM PrgPattern p WHERE p.idStopPoint = :idStopPoint")
    ,
    @NamedQuery(name = "PrgPattern.findByStopPoint", query = "SELECT p FROM PrgPattern p WHERE p.stopPoint = :stopPoint")
    ,
    @NamedQuery(name = "PrgPattern.findByUsername", query = "SELECT p FROM PrgPattern p WHERE p.username = :username")
    ,
    @NamedQuery(name = "PrgPattern.findByCreado", query = "SELECT p FROM PrgPattern p WHERE p.creado = :creado")
    ,
    @NamedQuery(name = "PrgPattern.findByModificado", query = "SELECT p FROM PrgPattern p WHERE p.modificado = :modificado")
    ,
    @NamedQuery(name = "PrgPattern.findByEstadoReg", query = "SELECT p FROM PrgPattern p WHERE p.estadoReg = :estadoReg")})
public class PrgPattern implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_pattern")
    private Integer idPrgPattern;
    @Size(max = 40)
    @Column(name = "id_pattern")
    private String idPattern;
    @Size(max = 45)
    @Column(name = "pattern")
    private String pattern;
    @Size(max = 40)
    @Column(name = "id_route")
    private String idRoute;
    @Size(max = 45)
    @Column(name = "route")
    private String route;
    @Column(name = "secuence_number")
    private Integer secuenceNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distance")
    private Double distance;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "is_check_stop_point")
    private Integer isCheckStopPoint;
    @Size(max = 40)
    @Column(name = "id_stop_point")
    private String idStopPoint;
    @Size(max = 45)
    @Column(name = "stop_point")
    private String stopPoint;
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
    @OneToMany(mappedBy = "idPrgPattern", fetch = FetchType.LAZY)
    private List<PrgTcDet> prgTcDetList;
    @JoinColumn(name = "id_prg_route", referencedColumnName = "id_prg_route")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgRoute idPrgRoute;
    @JoinColumn(name = "id_prg_stoppoint", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPrgStoppoint;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgPattern() {
    }

    public PrgPattern(Integer idPrgPattern) {
        this.idPrgPattern = idPrgPattern;
    }

    public PrgPattern(Integer idPrgPattern, String username, Date creado, int estadoReg) {
        this.idPrgPattern = idPrgPattern;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgPattern() {
        return idPrgPattern;
    }

    public void setIdPrgPattern(Integer idPrgPattern) {
        this.idPrgPattern = idPrgPattern;
    }

    public String getIdPattern() {
        return idPattern;
    }

    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(String idRoute) {
        this.idRoute = idRoute;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getSecuenceNumber() {
        return secuenceNumber;
    }

    public void setSecuenceNumber(Integer secuenceNumber) {
        this.secuenceNumber = secuenceNumber;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsCheckStopPoint() {
        return isCheckStopPoint;
    }

    public void setIsCheckStopPoint(Integer isCheckStopPoint) {
        this.isCheckStopPoint = isCheckStopPoint;
    }

    public String getIdStopPoint() {
        return idStopPoint;
    }

    public void setIdStopPoint(String idStopPoint) {
        this.idStopPoint = idStopPoint;
    }

    public String getStopPoint() {
        return stopPoint;
    }

    public void setStopPoint(String stopPoint) {
        this.stopPoint = stopPoint;
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

    @XmlTransient
    public List<PrgTcDet> getPrgTcDetList() {
        return prgTcDetList;
    }

    public void setPrgTcDetList(List<PrgTcDet> prgTcDetList) {
        this.prgTcDetList = prgTcDetList;
    }

    public PrgRoute getIdPrgRoute() {
        return idPrgRoute;
    }

    public void setIdPrgRoute(PrgRoute idPrgRoute) {
        this.idPrgRoute = idPrgRoute;
    }

    public PrgStopPoint getIdPrgStoppoint() {
        return idPrgStoppoint;
    }

    public void setIdPrgStoppoint(PrgStopPoint idPrgStoppoint) {
        this.idPrgStoppoint = idPrgStoppoint;
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
        hash += (idPrgPattern != null ? idPrgPattern.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgPattern)) {
            return false;
        }
        PrgPattern other = (PrgPattern) object;
        if ((this.idPrgPattern == null && other.idPrgPattern != null) || (this.idPrgPattern != null && !this.idPrgPattern.equals(other.idPrgPattern))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgPattern[ idPrgPattern=" + idPrgPattern + " ]";
    }
}
