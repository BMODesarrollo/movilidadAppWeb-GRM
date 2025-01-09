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
@Table(name = "prg_stop_point")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgStopPoint.findAll", query = "SELECT p FROM PrgStopPoint p"),
    @NamedQuery(name = "PrgStopPoint.findByIdPrgStoppoint", query = "SELECT p FROM PrgStopPoint p WHERE p.idPrgStoppoint = :idPrgStoppoint"),
    @NamedQuery(name = "PrgStopPoint.findByIdStopPoint", query = "SELECT p FROM PrgStopPoint p WHERE p.idStopPoint = :idStopPoint"),
    @NamedQuery(name = "PrgStopPoint.findByName", query = "SELECT p FROM PrgStopPoint p WHERE p.name = :name"),
    @NamedQuery(name = "PrgStopPoint.findByCode", query = "SELECT p FROM PrgStopPoint p WHERE p.code = :code"),
    @NamedQuery(name = "PrgStopPoint.findByDescription", query = "SELECT p FROM PrgStopPoint p WHERE p.description = :description"),
    @NamedQuery(name = "PrgStopPoint.findByIsActive", query = "SELECT p FROM PrgStopPoint p WHERE p.isActive = :isActive"),
    @NamedQuery(name = "PrgStopPoint.findByIsDepot", query = "SELECT p FROM PrgStopPoint p WHERE p.isDepot = :isDepot"),
    @NamedQuery(name = "PrgStopPoint.findByIsFuelAvaible", query = "SELECT p FROM PrgStopPoint p WHERE p.isFuelAvaible = :isFuelAvaible"),
    @NamedQuery(name = "PrgStopPoint.findByIsRelayPosible", query = "SELECT p FROM PrgStopPoint p WHERE p.isRelayPosible = :isRelayPosible"),
    @NamedQuery(name = "PrgStopPoint.findByVehicleCapacity", query = "SELECT p FROM PrgStopPoint p WHERE p.vehicleCapacity = :vehicleCapacity"),
    @NamedQuery(name = "PrgStopPoint.findByUsername", query = "SELECT p FROM PrgStopPoint p WHERE p.username = :username"),
    @NamedQuery(name = "PrgStopPoint.findByCreado", query = "SELECT p FROM PrgStopPoint p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgStopPoint.findByModificado", query = "SELECT p FROM PrgStopPoint p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgStopPoint.findByEstadoReg", query = "SELECT p FROM PrgStopPoint p WHERE p.estadoReg = :estadoReg")})
public class PrgStopPoint implements Serializable {

    @OneToMany(mappedBy = "idFromDepot", fetch = FetchType.LAZY)
    private List<MttoAsig> mttoAsigList;
    @OneToMany(mappedBy = "idToDepot", fetch = FetchType.LAZY)
    private List<MttoAsig> mttoAsigList1;

    @OneToMany(mappedBy = "fromStop", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList;
    @OneToMany(mappedBy = "toStop", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList1;

    @OneToMany(mappedBy = "fromStop", fetch = FetchType.LAZY)
    private List<NovedadTipoDetalles> novedadTipoDetallesPrgTcList;
    @OneToMany(mappedBy = "toStop", fetch = FetchType.LAZY)
    private List<NovedadTipoDetalles> novedadTipoDetallesPrgTcList1;

    @OneToMany(mappedBy = "idPrgStoppoint", fetch = FetchType.LAZY)
    private List<AccInformeMaster> accInformeMasterList;

    @OneToMany(mappedBy = "idPrgStoppoint", fetch = FetchType.LAZY)
    private List<AccidenteLugar> accidenteLugarList;

    @Column(name = "propia")
    private Integer propia;
    @OneToMany(mappedBy = "fromDepot", fetch = FetchType.LAZY)
    private List<PrgServbuses> prgServbusesList;
    @OneToMany(mappedBy = "toDepot", fetch = FetchType.LAZY)
    private List<PrgServbuses> prgServbusesList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_stoppoint")
    private Integer idPrgStoppoint;
    @Size(max = 40)
    @Column(name = "id_stop_point")
    private String idStopPoint;
    @Size(max = 150)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "code")
    private String code;
    @Size(max = 150)
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "is_depot")
    private Integer isDepot;
    @Column(name = "is_fuel_avaible")
    private Integer isFuelAvaible;
    @Column(name = "is_relay_posible")
    private Integer isRelayPosible;
    @Column(name = "vehicle_capacity")
    private Integer vehicleCapacity;
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
    @OneToMany(mappedBy = "idPatio", fetch = FetchType.LAZY)
    private List<PrgBusUbicacion> prgBusUbicacionList;
    @OneToMany(mappedBy = "idPrgFromStoppoint", fetch = FetchType.LAZY)
    private List<PrgRoute> prgRouteList;
    @OneToMany(mappedBy = "idPrgToStoppoint", fetch = FetchType.LAZY)
    private List<PrgRoute> prgRouteList1;
    @OneToMany(mappedBy = "idPrgStoppoint", fetch = FetchType.LAZY)
    private List<PrgPattern> prgPatternList;
    @OneToMany(mappedBy = "idFromStop", fetch = FetchType.LAZY)
    private List<PrgSercon> prgSerconList;
    @OneToMany(mappedBy = "idToStop", fetch = FetchType.LAZY)
    private List<PrgSercon> prgSerconList1;
    @OneToMany(mappedBy = "fromStop", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList;
    @OneToMany(mappedBy = "toStop", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList1;
    @OneToMany(mappedBy = "idFromDepot", fetch = FetchType.LAZY)
    private List<PrgVehicleStatus> prgVehicleStatusList;
    @OneToMany(mappedBy = "idToDepot", fetch = FetchType.LAZY)
    private List<PrgVehicleStatus> prgVehicleStatusList1;
    @OneToMany(mappedBy = "idPrgFromStop", fetch = FetchType.LAZY)
    private List<PrgDistance> prgDistanceList;
    @OneToMany(mappedBy = "idPrgToStop", fetch = FetchType.LAZY)
    private List<PrgDistance> prgDistanceList1;

    @OneToMany(mappedBy = "idPrgStopPoint", fetch = FetchType.LAZY)
    private List<MyAppSerconConfirm> myAppSerconConfirmList;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgStopPoint() {
    }

    public PrgStopPoint(Integer idPrgStoppoint) {
        this.idPrgStoppoint = idPrgStoppoint;
    }

    public PrgStopPoint(Integer idPrgStoppoint, String username, Date creado, int estadoReg) {
        this.idPrgStoppoint = idPrgStoppoint;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgStoppoint() {
        return idPrgStoppoint;
    }

    public void setIdPrgStoppoint(Integer idPrgStoppoint) {
        this.idPrgStoppoint = idPrgStoppoint;
    }

    public String getIdStopPoint() {
        return idStopPoint;
    }

    public void setIdStopPoint(String idStopPoint) {
        this.idStopPoint = idStopPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsDepot() {
        return isDepot;
    }

    public void setIsDepot(Integer isDepot) {
        this.isDepot = isDepot;
    }

    public Integer getIsFuelAvaible() {
        return isFuelAvaible;
    }

    public void setIsFuelAvaible(Integer isFuelAvaible) {
        this.isFuelAvaible = isFuelAvaible;
    }

    public Integer getIsRelayPosible() {
        return isRelayPosible;
    }

    public void setIsRelayPosible(Integer isRelayPosible) {
        this.isRelayPosible = isRelayPosible;
    }

    public Integer getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(Integer vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
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
    public List<PrgBusUbicacion> getPrgBusUbicacionList() {
        return prgBusUbicacionList;
    }

    public void setPrgBusUbicacionList(List<PrgBusUbicacion> prgBusUbicacionList) {
        this.prgBusUbicacionList = prgBusUbicacionList;
    }

    @XmlTransient
    public List<PrgRoute> getPrgRouteList() {
        return prgRouteList;
    }

    public void setPrgRouteList(List<PrgRoute> prgRouteList) {
        this.prgRouteList = prgRouteList;
    }

    @XmlTransient
    public List<PrgRoute> getPrgRouteList1() {
        return prgRouteList1;
    }

    public void setPrgRouteList1(List<PrgRoute> prgRouteList1) {
        this.prgRouteList1 = prgRouteList1;
    }

    @XmlTransient
    public List<PrgPattern> getPrgPatternList() {
        return prgPatternList;
    }

    public void setPrgPatternList(List<PrgPattern> prgPatternList) {
        this.prgPatternList = prgPatternList;
    }

    @XmlTransient
    public List<PrgSercon> getPrgSerconList() {
        return prgSerconList;
    }

    public void setPrgSerconList(List<PrgSercon> prgSerconList) {
        this.prgSerconList = prgSerconList;
    }

    @XmlTransient
    public List<PrgSercon> getPrgSerconList1() {
        return prgSerconList1;
    }

    public void setPrgSerconList1(List<PrgSercon> prgSerconList1) {
        this.prgSerconList1 = prgSerconList1;
    }

    @XmlTransient
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    @XmlTransient
    public List<PrgTc> getPrgTcList1() {
        return prgTcList1;
    }

    public void setPrgTcList1(List<PrgTc> prgTcList1) {
        this.prgTcList1 = prgTcList1;
    }

    @XmlTransient
    public List<PrgVehicleStatus> getPrgVehicleStatusList() {
        return prgVehicleStatusList;
    }

    public void setPrgVehicleStatusList(List<PrgVehicleStatus> prgVehicleStatusList) {
        this.prgVehicleStatusList = prgVehicleStatusList;
    }

    @XmlTransient
    public List<PrgVehicleStatus> getPrgVehicleStatusList1() {
        return prgVehicleStatusList1;
    }

    public void setPrgVehicleStatusList1(List<PrgVehicleStatus> prgVehicleStatusList1) {
        this.prgVehicleStatusList1 = prgVehicleStatusList1;
    }

    @XmlTransient
    public List<PrgDistance> getPrgDistanceList() {
        return prgDistanceList;
    }

    public void setPrgDistanceList(List<PrgDistance> prgDistanceList) {
        this.prgDistanceList = prgDistanceList;
    }

    @XmlTransient
    public List<PrgDistance> getPrgDistanceList1() {
        return prgDistanceList1;
    }

    public void setPrgDistanceList1(List<PrgDistance> prgDistanceList1) {
        this.prgDistanceList1 = prgDistanceList1;
    }

    @XmlTransient
    public List<NovedadTipoDetalles> getNovedadTipoDetallesPrgTcList() {
        return novedadTipoDetallesPrgTcList;
    }

    public void setNovedadTipoDetallesPrgTcList(List<NovedadTipoDetalles> novedadTipoDetallesPrgTcList) {
        this.novedadTipoDetallesPrgTcList = novedadTipoDetallesPrgTcList;
    }

    @XmlTransient
    public List<NovedadTipoDetalles> getNovedadTipoDetallesPrgTcList1() {
        return novedadTipoDetallesPrgTcList1;
    }

    public void setNovedadTipoDetallesPrgTcList1(List<NovedadTipoDetalles> novedadTipoDetallesPrgTcList1) {
        this.novedadTipoDetallesPrgTcList1 = novedadTipoDetallesPrgTcList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgStoppoint != null ? idPrgStoppoint.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgStopPoint)) {
            return false;
        }
        PrgStopPoint other = (PrgStopPoint) object;
        if ((this.idPrgStoppoint == null && other.idPrgStoppoint != null) || (this.idPrgStoppoint != null && !this.idPrgStoppoint.equals(other.idPrgStoppoint))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PrgStopPoint{" + "propia=" + propia + ", idPrgStoppoint=" + idPrgStoppoint + ", idStopPoint=" + idStopPoint + ", name=" + name + ", code=" + code + ", description=" + description + ", isActive=" + isActive + ", isDepot=" + isDepot + ", isFuelAvaible=" + isFuelAvaible + ", isRelayPosible=" + isRelayPosible + ", vehicleCapacity=" + vehicleCapacity + ", username=" + username + ", creado=" + creado + ", modificado=" + modificado + ", estadoReg=" + estadoReg + '}';
    }

    @XmlTransient
    public List<PrgServbuses> getPrgServbusesList() {
        return prgServbusesList;
    }

    public void setPrgServbusesList(List<PrgServbuses> prgServbusesList) {
        this.prgServbusesList = prgServbusesList;
    }

    @XmlTransient
    public List<PrgServbuses> getPrgServbusesList1() {
        return prgServbusesList1;
    }

    public void setPrgServbusesList1(List<PrgServbuses> prgServbusesList1) {
        this.prgServbusesList1 = prgServbusesList1;
    }

    public Integer getPropia() {
        return propia;
    }

    public void setPropia(Integer propia) {
        this.propia = propia;
    }

    @XmlTransient
    public List<AccidenteLugar> getAccidenteLugarList() {
        return accidenteLugarList;
    }

    public void setAccidenteLugarList(List<AccidenteLugar> accidenteLugarList) {
        this.accidenteLugarList = accidenteLugarList;
    }

    @XmlTransient
    public List<AccInformeMaster> getAccInformeMasterList() {
        return accInformeMasterList;
    }

    public void setAccInformeMasterList(List<AccInformeMaster> accInformeMasterList) {
        this.accInformeMasterList = accInformeMasterList;
    }

    @XmlTransient
    public List<NovedadPrgTc> getNovedadPrgTcList() {
        return novedadPrgTcList;
    }

    public void setNovedadPrgTcList(List<NovedadPrgTc> novedadPrgTcList) {
        this.novedadPrgTcList = novedadPrgTcList;
    }

    @XmlTransient
    public List<MttoAsig> getMttoAsigList() {
        return mttoAsigList;
    }

    public void setMttoAsigList(List<MttoAsig> mttoAsigList) {
        this.mttoAsigList = mttoAsigList;
    }

    @XmlTransient
    public List<MttoAsig> getMttoAsigList1() {
        return mttoAsigList1;
    }

    public void setMttoAsigList1(List<MttoAsig> mttoAsigList1) {
        this.mttoAsigList1 = mttoAsigList1;
    }

    @XmlTransient
    public List<MyAppSerconConfirm> getMyAppSerconConfirmList() {
        return myAppSerconConfirmList;
    }

    public void setMyAppSerconConfirmList(List<MyAppSerconConfirm> myAppSerconConfirmList) {
        this.myAppSerconConfirmList = myAppSerconConfirmList;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

}
