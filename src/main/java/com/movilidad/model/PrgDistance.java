/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author HP
 */
@Entity
@Table(name = "prg_distance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgDistance.findAll", query = "SELECT p FROM PrgDistance p")
    , @NamedQuery(name = "PrgDistance.findByIdPrgDistance", query = "SELECT p FROM PrgDistance p WHERE p.idPrgDistance = :idPrgDistance")
    , @NamedQuery(name = "PrgDistance.findByFromStopPoint", query = "SELECT p FROM PrgDistance p WHERE p.fromStopPoint = :fromStopPoint")
    , @NamedQuery(name = "PrgDistance.findByToStopPoint", query = "SELECT p FROM PrgDistance p WHERE p.toStopPoint = :toStopPoint")
    , @NamedQuery(name = "PrgDistance.findByDistance", query = "SELECT p FROM PrgDistance p WHERE p.distance = :distance")
    , @NamedQuery(name = "PrgDistance.findByOrigin", query = "SELECT p FROM PrgDistance p WHERE p.origin = :origin")
    , @NamedQuery(name = "PrgDistance.findByVigente", query = "SELECT p FROM PrgDistance p WHERE p.vigente = :vigente")
    , @NamedQuery(name = "PrgDistance.findByUsername", query = "SELECT p FROM PrgDistance p WHERE p.username = :username")
    , @NamedQuery(name = "PrgDistance.findByCreado", query = "SELECT p FROM PrgDistance p WHERE p.creado = :creado")
    , @NamedQuery(name = "PrgDistance.findByModificado", query = "SELECT p FROM PrgDistance p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgDistance.findByEstadoReg", query = "SELECT p FROM PrgDistance p WHERE p.estadoReg = :estadoReg")})
public class PrgDistance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_distance")
    private Integer idPrgDistance;
    @Size(max = 45)
    @Column(name = "from_stop_point")
    private String fromStopPoint;
    @Size(max = 45)
    @Column(name = "to_stop_point")
    private String toStopPoint;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distance")
    private BigDecimal distance;
    @Column(name = "origin")
    private Integer origin;
    @Column(name = "vigente")
    private Integer vigente;
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
    @Column(name = "propia")
    private int propia;
    @JoinColumn(name = "id_prg_from_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPrgFromStop;
    @JoinColumn(name = "id_prg_to_stop", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPrgToStop;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgDistance() {
    }

    public PrgDistance(Integer idPrgDistance) {
        this.idPrgDistance = idPrgDistance;
    }

    public PrgDistance(Integer idPrgDistance, String username, Date creado, int estadoReg) {
        this.idPrgDistance = idPrgDistance;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgDistance() {
        return idPrgDistance;
    }

    public void setIdPrgDistance(Integer idPrgDistance) {
        this.idPrgDistance = idPrgDistance;
    }

    public String getFromStopPoint() {
        return fromStopPoint;
    }

    public void setFromStopPoint(String fromStopPoint) {
        this.fromStopPoint = fromStopPoint;
    }

    public String getToStopPoint() {
        return toStopPoint;
    }

    public void setToStopPoint(String toStopPoint) {
        this.toStopPoint = toStopPoint;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getVigente() {
        return vigente;
    }

    public void setVigente(Integer vigente) {
        this.vigente = vigente;
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

    public PrgStopPoint getIdPrgFromStop() {
        return idPrgFromStop;
    }

    public void setIdPrgFromStop(PrgStopPoint idPrgFromStop) {
        this.idPrgFromStop = idPrgFromStop;
    }

    public PrgStopPoint getIdPrgToStop() {
        return idPrgToStop;
    }

    public void setIdPrgToStop(PrgStopPoint idPrgToStop) {
        this.idPrgToStop = idPrgToStop;
    }

    public int getPropia() {
        return propia;
    }

    public void setPropia(int propia) {
        this.propia = propia;
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
        hash += (idPrgDistance != null ? idPrgDistance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgDistance)) {
            return false;
        }
        PrgDistance other = (PrgDistance) object;
        if ((this.idPrgDistance == null && other.idPrgDistance != null) || (this.idPrgDistance != null && !this.idPrgDistance.equals(other.idPrgDistance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgDistance[ idPrgDistance=" + idPrgDistance + " ]";
    }

}
