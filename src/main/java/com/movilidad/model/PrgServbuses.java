/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.ReporteServbuses;
import com.movilidad.util.beans.ReporteServbusesPatioFin;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "prg_servbuses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgServbuses.findAll", query = "SELECT p FROM PrgServbuses p"),
    @NamedQuery(name = "PrgServbuses.findByIdPrgServbuses", query = "SELECT p FROM PrgServbuses p WHERE p.idPrgServbuses = :idPrgServbuses"),
    @NamedQuery(name = "PrgServbuses.findByFecha", query = "SELECT p FROM PrgServbuses p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "PrgServbuses.findByServbus", query = "SELECT p FROM PrgServbuses p WHERE p.servbus = :servbus"),
    @NamedQuery(name = "PrgServbuses.findByTimeOrigin", query = "SELECT p FROM PrgServbuses p WHERE p.timeOrigin = :timeOrigin"),
    @NamedQuery(name = "PrgServbuses.findByTimeDestiny", query = "SELECT p FROM PrgServbuses p WHERE p.timeDestiny = :timeDestiny"),
    @NamedQuery(name = "PrgServbuses.findByComercialDistance", query = "SELECT p FROM PrgServbuses p WHERE p.comercialDistance = :comercialDistance"),
    @NamedQuery(name = "PrgServbuses.findByHlpDistance", query = "SELECT p FROM PrgServbuses p WHERE p.hlpDistance = :hlpDistance"),
    @NamedQuery(name = "PrgServbuses.findByProductionDistance", query = "SELECT p FROM PrgServbuses p WHERE p.productionDistance = :productionDistance"),
    @NamedQuery(name = "PrgServbuses.findByUsername", query = "SELECT p FROM PrgServbuses p WHERE p.username = :username"),
    @NamedQuery(name = "PrgServbuses.findByCreado", query = "SELECT p FROM PrgServbuses p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgServbuses.findByModificado", query = "SELECT p FROM PrgServbuses p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgServbuses.findByEstadoReg", query = "SELECT p FROM PrgServbuses p WHERE p.estadoReg = :estadoReg")})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "ReporteServbusesMapping",
            classes = {
                @ConstructorResult(targetClass = ReporteServbuses.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "codigo"),
                            @ColumnResult(name = "servbus"),
                            @ColumnResult(name = "fromdepot"),
                            @ColumnResult(name = "todepot"),
                            @ColumnResult(name = "time_origin"),
                            @ColumnResult(name = "time_destiny")
                        }
                )
            }),
    @SqlResultSetMapping(name = "ReporteServbusesPatioFinMapping",
            classes = {
                @ConstructorResult(targetClass = ReporteServbusesPatioFin.class,
                        columns = {
                            @ColumnResult(name = "fecha"),
                            @ColumnResult(name = "codigo"),
                            @ColumnResult(name = "servbus"),
                            @ColumnResult(name = "todepot")
                        }
                )
            })
})
public class PrgServbuses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_servbuses")
    private Integer idPrgServbuses;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "servbus")
    private String servbus;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "comercial_distance")
    private Double comercialDistance;
    @Column(name = "hlp_distance")
    private Double hlpDistance;
    @Column(name = "production_distance")
    private Double productionDistance;
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
    @JoinColumn(name = "from_depot", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint fromDepot;
    @JoinColumn(name = "to_depot", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint toDepot;

    public PrgServbuses() {
    }

    public PrgServbuses(Integer idPrgServbuses) {
        this.idPrgServbuses = idPrgServbuses;
    }

    public PrgServbuses(Integer idPrgServbuses, int estadoReg) {
        this.idPrgServbuses = idPrgServbuses;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgServbuses() {
        return idPrgServbuses;
    }

    public void setIdPrgServbuses(Integer idPrgServbuses) {
        this.idPrgServbuses = idPrgServbuses;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
    }

    public Double getComercialDistance() {
        return comercialDistance;
    }

    public void setComercialDistance(Double comercialDistance) {
        this.comercialDistance = comercialDistance;
    }

    public Double getHlpDistance() {
        return hlpDistance;
    }

    public void setHlpDistance(Double hlpDistance) {
        this.hlpDistance = hlpDistance;
    }

    public Double getProductionDistance() {
        return productionDistance;
    }

    public void setProductionDistance(Double productionDistance) {
        this.productionDistance = productionDistance;
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

    public PrgStopPoint getFromDepot() {
        return fromDepot;
    }

    public void setFromDepot(PrgStopPoint fromDepot) {
        this.fromDepot = fromDepot;
    }

    public PrgStopPoint getToDepot() {
        return toDepot;
    }

    public void setToDepot(PrgStopPoint toDepot) {
        this.toDepot = toDepot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgServbuses != null ? idPrgServbuses.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgServbuses)) {
            return false;
        }
        PrgServbuses other = (PrgServbuses) object;
        if ((this.idPrgServbuses == null && other.idPrgServbuses != null) || (this.idPrgServbuses != null && !this.idPrgServbuses.equals(other.idPrgServbuses))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgServbuses[ idPrgServbuses=" + idPrgServbuses + " ]";
    }
}
