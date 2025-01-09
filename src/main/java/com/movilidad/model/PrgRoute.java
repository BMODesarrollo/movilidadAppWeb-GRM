/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author luis
 */
@Entity
@Table(name = "prg_route")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgRoute.findAll", query = "SELECT p FROM PrgRoute p")
    ,
    @NamedQuery(name = "PrgRoute.findByIdPrgRoute", query = "SELECT p FROM PrgRoute p WHERE p.idPrgRoute = :idPrgRoute")
    ,
    @NamedQuery(name = "PrgRoute.findByIdRoute", query = "SELECT p FROM PrgRoute p WHERE p.idRoute = :idRoute")
    ,
    @NamedQuery(name = "PrgRoute.findByCode", query = "SELECT p FROM PrgRoute p WHERE p.code = :code")
    ,
    @NamedQuery(name = "PrgRoute.findByName", query = "SELECT p FROM PrgRoute p WHERE p.name = :name")
    ,
    @NamedQuery(name = "PrgRoute.findByDescription", query = "SELECT p FROM PrgRoute p WHERE p.description = :description")
    ,
    @NamedQuery(name = "PrgRoute.findByIdLine", query = "SELECT p FROM PrgRoute p WHERE p.idLine = :idLine")
    ,
    @NamedQuery(name = "PrgRoute.findByLine", query = "SELECT p FROM PrgRoute p WHERE p.line = :line")
    ,
    @NamedQuery(name = "PrgRoute.findByIdFromStopPoint", query = "SELECT p FROM PrgRoute p WHERE p.idFromStopPoint = :idFromStopPoint")
    ,
    @NamedQuery(name = "PrgRoute.findByFromStopPoint", query = "SELECT p FROM PrgRoute p WHERE p.fromStopPoint = :fromStopPoint")
    ,
    @NamedQuery(name = "PrgRoute.findByIdToStopPoint", query = "SELECT p FROM PrgRoute p WHERE p.idToStopPoint = :idToStopPoint")
    ,
    @NamedQuery(name = "PrgRoute.findByToStopPoint", query = "SELECT p FROM PrgRoute p WHERE p.toStopPoint = :toStopPoint")
    ,
    @NamedQuery(name = "PrgRoute.findByIsActive", query = "SELECT p FROM PrgRoute p WHERE p.isActive = :isActive")
    ,
    @NamedQuery(name = "PrgRoute.findByIsCircular", query = "SELECT p FROM PrgRoute p WHERE p.isCircular = :isCircular")
    ,
    @NamedQuery(name = "PrgRoute.findByIsCommercial", query = "SELECT p FROM PrgRoute p WHERE p.isCommercial = :isCommercial")
    ,
    @NamedQuery(name = "PrgRoute.findByVigente", query = "SELECT p FROM PrgRoute p WHERE p.vigente = :vigente")
    ,
    @NamedQuery(name = "PrgRoute.findByUsername", query = "SELECT p FROM PrgRoute p WHERE p.username = :username")
    ,
    @NamedQuery(name = "PrgRoute.findByCreado", query = "SELECT p FROM PrgRoute p WHERE p.creado = :creado")
    ,
    @NamedQuery(name = "PrgRoute.findByModificado", query = "SELECT p FROM PrgRoute p WHERE p.modificado = :modificado")
    ,
    @NamedQuery(name = "PrgRoute.findByEstadoReg", query = "SELECT p FROM PrgRoute p WHERE p.estadoReg = :estadoReg")})
public class PrgRoute implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrgRoute")
    private Collection<GmoNovedadInfrastrucRutaAfectada> gmoNovedadInfrastrucRutaAfectadaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_route")
    private Integer idPrgRoute;
    @Size(max = 40)
    @Column(name = "id_route")
    private String idRoute;
    @Size(max = 45)
    @Column(name = "code")
    private String code;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 150)
    @Column(name = "description")
    private String description;
    @Size(max = 40)
    @Column(name = "id_line")
    private String idLine;
    @Size(max = 45)
    @Column(name = "line")
    private String line;
    @Size(max = 40)
    @Column(name = "id_from_stop_point")
    private String idFromStopPoint;
    @Size(max = 45)
    @Column(name = "from_stop_point")
    private String fromStopPoint;
    @Size(max = 40)
    @Column(name = "id_to_stop_point")
    private String idToStopPoint;
    @Size(max = 45)
    @Column(name = "to_stop_point")
    private String toStopPoint;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "is_circular")
    private Integer isCircular;
    @Column(name = "is_commercial")
    private Integer isCommercial;
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
    @JoinColumn(name = "id_prg_from_stoppoint", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPrgFromStoppoint;
    @JoinColumn(name = "id_prg_to_stoppoint", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPrgToStoppoint;
    @OneToMany(mappedBy = "idPrgRoute", fetch = FetchType.LAZY)
    private List<PrgPattern> prgPatternList;
    @OneToMany(mappedBy = "idRuta", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgRoute() {
    }

    public PrgRoute(Integer idPrgRoute) {
        this.idPrgRoute = idPrgRoute;
    }

    public PrgRoute(Integer idPrgRoute, String username, Date creado, int estadoReg) {
        this.idPrgRoute = idPrgRoute;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgRoute() {
        return idPrgRoute;
    }

    public void setIdPrgRoute(Integer idPrgRoute) {
        this.idPrgRoute = idPrgRoute;
    }

    public String getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(String idRoute) {
        this.idRoute = idRoute;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdLine() {
        return idLine;
    }

    public void setIdLine(String idLine) {
        this.idLine = idLine;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getIdFromStopPoint() {
        return idFromStopPoint;
    }

    public void setIdFromStopPoint(String idFromStopPoint) {
        this.idFromStopPoint = idFromStopPoint;
    }

    public String getFromStopPoint() {
        return fromStopPoint;
    }

    public void setFromStopPoint(String fromStopPoint) {
        this.fromStopPoint = fromStopPoint;
    }

    public String getIdToStopPoint() {
        return idToStopPoint;
    }

    public void setIdToStopPoint(String idToStopPoint) {
        this.idToStopPoint = idToStopPoint;
    }

    public String getToStopPoint() {
        return toStopPoint;
    }

    public void setToStopPoint(String toStopPoint) {
        this.toStopPoint = toStopPoint;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsCircular() {
        return isCircular;
    }

    public void setIsCircular(Integer isCircular) {
        this.isCircular = isCircular;
    }

    public Integer getIsCommercial() {
        return isCommercial;
    }

    public void setIsCommercial(Integer isCommercial) {
        this.isCommercial = isCommercial;
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

    public PrgStopPoint getIdPrgFromStoppoint() {
        return idPrgFromStoppoint;
    }

    public void setIdPrgFromStoppoint(PrgStopPoint idPrgFromStoppoint) {
        this.idPrgFromStoppoint = idPrgFromStoppoint;
    }

    public PrgStopPoint getIdPrgToStoppoint() {
        return idPrgToStoppoint;
    }

    public void setIdPrgToStoppoint(PrgStopPoint idPrgToStoppoint) {
        this.idPrgToStoppoint = idPrgToStoppoint;
    }

    @XmlTransient
    public List<PrgPattern> getPrgPatternList() {
        return prgPatternList;
    }

    public void setPrgPatternList(List<PrgPattern> prgPatternList) {
        this.prgPatternList = prgPatternList;
    }

    @XmlTransient
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgRoute != null ? idPrgRoute.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgRoute)) {
            return false;
        }
        PrgRoute other = (PrgRoute) object;
        if ((this.idPrgRoute == null && other.idPrgRoute != null) || (this.idPrgRoute != null && !this.idPrgRoute.equals(other.idPrgRoute))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgRoute[ idPrgRoute=" + idPrgRoute + " ]";
    }

    @XmlTransient
    public Collection<GmoNovedadInfrastrucRutaAfectada> getGmoNovedadInfrastrucRutaAfectadaCollection() {
        return gmoNovedadInfrastrucRutaAfectadaCollection;
    }

    public void setGmoNovedadInfrastrucRutaAfectadaCollection(Collection<GmoNovedadInfrastrucRutaAfectada> gmoNovedadInfrastrucRutaAfectadaCollection) {
        this.gmoNovedadInfrastrucRutaAfectadaCollection = gmoNovedadInfrastrucRutaAfectadaCollection;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

}
