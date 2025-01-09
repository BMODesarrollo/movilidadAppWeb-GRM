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
 * @author solucionesit
 */
@Entity
@Table(name = "gop_unidad_funcional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GopUnidadFuncional.findAll", query = "SELECT g FROM GopUnidadFuncional g")
    ,
    @NamedQuery(name = "GopUnidadFuncional.findByIdGopUnidadFuncional", query = "SELECT g FROM GopUnidadFuncional g WHERE g.idGopUnidadFuncional = :idGopUnidadFuncional")
    ,
    @NamedQuery(name = "GopUnidadFuncional.findByNombre", query = "SELECT g FROM GopUnidadFuncional g WHERE g.nombre = :nombre")
    ,
    @NamedQuery(name = "GopUnidadFuncional.findByUsername", query = "SELECT g FROM GopUnidadFuncional g WHERE g.username = :username")
    ,
    @NamedQuery(name = "GopUnidadFuncional.findByCreado", query = "SELECT g FROM GopUnidadFuncional g WHERE g.creado = :creado")
    ,
    @NamedQuery(name = "GopUnidadFuncional.findByModificado", query = "SELECT g FROM GopUnidadFuncional g WHERE g.modificado = :modificado")
    ,
    @NamedQuery(name = "GopUnidadFuncional.findByEstadoReg", query = "SELECT g FROM GopUnidadFuncional g WHERE g.estadoReg = :estadoReg")})
public class GopUnidadFuncional implements Serializable {

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<ParamCierreAusentismo> paramCierreAusentismoList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NovedadAutorizacionAusentismos> novedadAutorizacionAusentismosList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NominaAutorizacionDetIndividual> nominaAutorizacionDetIndividualList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NominaAutorizacionIndividual> nominaAutorizacionIndividualList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<ConfigFreeway> configFreewayList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NominaAutorizacionDet> nominaAutorizacionDetList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NominaAutorizacion> autorizacionNominaKactusList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgTcResumenVrConciliados> prgTcResumenVrConciliadosList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<LavadoGm> lavadoGmList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<GmoNovedadInfrastruc> gmoNovedadInfrastrucList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<DispConciliacionResumen> dispConciliacionResumenList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<DispConciliacionDet> dispConciliacionDetList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<TblCalendario> tblCalendarioList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgStopPoint> prgStopPointList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgRoute> prgRouteList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgDistance> prgDistanceList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgTarea> prgTareaList;

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgPattern> prgPatternList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gop_unidad_funcional")
    private Integer idGopUnidadFuncional;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "codigo")
    private String codigo;
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

    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<Empleado> empleadoList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgTcResumen> prgTcResumenList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<Users> usersList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<Novedad> novedadList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<EmpleadoDocumentos> EmpleadoDocumentosList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgSercon> prgSerconList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<KmConciliado> kmConciliadoList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NovedadDano> novedadDanoList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<Multa> multaList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<Generica> genericaList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgSolicitud> prgSolicitudList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<GenericaSolicitud> genericaSolicitudList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PmGrupo> pmGrupoList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<GopParamTiempoCierre> gopParamTiempoCierreList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<Auditoria> AuditoriaList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NovedadDanoLiq> novedadDanoLiqList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<GopCierreTurno> gopCierreTurnoList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<GestorNovedad> gestorNovedadList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<GestorTablaTmp> gestorTablaTmpList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<GestorNovDetSemana> gestorNovDetSemanaList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NominaServerParamEmpresa> nominaServerParamEmpresaList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NotificacionTelegramDet> notificacionTelegramDetList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<NotificacionProcesoDet> notificacionProcesoDetList;
    @OneToMany(mappedBy = "idGopUnidadFuncional", fetch = FetchType.LAZY)
    private List<PrgVehicleStatus> prgVehicleStatusList;

    public GopUnidadFuncional() {
    }

    public GopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public GopUnidadFuncional(Integer idGopUnidadFuncional, String nombre, String username, Date creado, int estadoReg) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGopUnidadFuncional != null ? idGopUnidadFuncional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GopUnidadFuncional)) {
            return false;
        }
        GopUnidadFuncional other = (GopUnidadFuncional) object;
        if ((this.idGopUnidadFuncional == null && other.idGopUnidadFuncional != null) || (this.idGopUnidadFuncional != null && !this.idGopUnidadFuncional.equals(other.idGopUnidadFuncional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GopUnidadFuncional[ idGopUnidadFuncional=" + idGopUnidadFuncional + " ]";
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @XmlTransient
    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }

    @XmlTransient
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    @XmlTransient
    public List<PrgTcResumen> getPrgTcResumenList() {
        return prgTcResumenList;
    }

    public void setPrgTcResumenList(List<PrgTcResumen> prgTcResumenList) {
        this.prgTcResumenList = prgTcResumenList;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @XmlTransient
    public List<Novedad> getNovedadList() {
        return novedadList;
    }

    public void setNovedadList(List<Novedad> novedadList) {
        this.novedadList = novedadList;
    }

    @XmlTransient
    public List<EmpleadoDocumentos> getEmpleadoDocumentosList() {
        return EmpleadoDocumentosList;
    }

    public void setEmpleadoDocumentosList(List<EmpleadoDocumentos> EmpleadoDocumentosList) {
        this.EmpleadoDocumentosList = EmpleadoDocumentosList;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<DispConciliacionDet> getDispConciliacionDetList() {
        return dispConciliacionDetList;
    }

    public void setDispConciliacionDetList(List<DispConciliacionDet> dispConciliacionDetList) {
        this.dispConciliacionDetList = dispConciliacionDetList;
    }

    @XmlTransient
    public List<DispConciliacionResumen> getDispConciliacionResumenList() {
        return dispConciliacionResumenList;
    }

    public void setDispConciliacionResumenList(List<DispConciliacionResumen> dispConciliacionResumenList) {
        this.dispConciliacionResumenList = dispConciliacionResumenList;
    }

    @XmlTransient
    public List<PrgSercon> getPrgSerconList() {
        return prgSerconList;
    }

    public void setPrgSerconList(List<PrgSercon> prgSerconList) {
        this.prgSerconList = prgSerconList;
    }

    @XmlTransient
    public List<KmConciliado> getKmConciliadoList() {
        return kmConciliadoList;
    }

    public void setKmConciliadoList(List<KmConciliado> kmConciliadoList) {
        this.kmConciliadoList = kmConciliadoList;
    }

    @XmlTransient
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    @XmlTransient
    public List<NovedadPrgTc> getNovedadPrgTcList() {
        return novedadPrgTcList;
    }

    public void setNovedadPrgTcList(List<NovedadPrgTc> novedadPrgTcList) {
        this.novedadPrgTcList = novedadPrgTcList;
    }

    @XmlTransient
    public List<NovedadDano> getNovedadDanoList() {
        return novedadDanoList;
    }

    public void setNovedadDanoList(List<NovedadDano> novedadDanoList) {
        this.novedadDanoList = novedadDanoList;
    }

    @XmlTransient
    public List<Multa> getMultaList() {
        return multaList;
    }

    public void setMultaList(List<Multa> multaList) {
        this.multaList = multaList;
    }

    @XmlTransient
    public List<Generica> getGenericaList() {
        return genericaList;
    }

    public void setGenericaList(List<Generica> genericaList) {
        this.genericaList = genericaList;
    }

    @XmlTransient
    public List<PrgSolicitud> getPrgSolicitudList() {
        return prgSolicitudList;
    }

    public void setPrgSolicitudList(List<PrgSolicitud> prgSolicitudList) {
        this.prgSolicitudList = prgSolicitudList;
    }

    @XmlTransient
    public List<GenericaSolicitud> getGenericaSolicitudList() {
        return genericaSolicitudList;
    }

    public void setGenericaSolicitudList(List<GenericaSolicitud> genericaSolicitudList) {
        this.genericaSolicitudList = genericaSolicitudList;
    }

    @XmlTransient
    public List<PmGrupo> getPmGrupoList() {
        return pmGrupoList;
    }

    public void setPmGrupoList(List<PmGrupo> pmGrupoList) {
        this.pmGrupoList = pmGrupoList;
    }

    @XmlTransient
    public List<GopParamTiempoCierre> getGopParamTiempoCierreList() {
        return gopParamTiempoCierreList;
    }

    public void setGopParamTiempoCierreList(List<GopParamTiempoCierre> gopParamTiempoCierreList) {
        this.gopParamTiempoCierreList = gopParamTiempoCierreList;
    }

    @XmlTransient
    public List<LavadoGm> getLavadoGmList() {
        return lavadoGmList;
    }

    public void setLavadoGmList(List<LavadoGm> lavadoGmList) {
        this.lavadoGmList = lavadoGmList;
    }

    @XmlTransient
    public List<Auditoria> getAuditoriaList() {
        return AuditoriaList;
    }

    public void setAuditoriaList(List<Auditoria> AuditoriaList) {
        this.AuditoriaList = AuditoriaList;
    }

    @XmlTransient
    public List<GmoNovedadInfrastruc> getGmoNovedadInfrastrucList() {
        return gmoNovedadInfrastrucList;
    }

    public void setGmoNovedadInfrastrucList(List<GmoNovedadInfrastruc> gmoNovedadInfrastrucList) {
        this.gmoNovedadInfrastrucList = gmoNovedadInfrastrucList;
    }

    @XmlTransient
    public List<NovedadDanoLiq> getNovedadDanoLiqList() {
        return novedadDanoLiqList;
    }

    public void setNovedadDanoLiqList(List<NovedadDanoLiq> novedadDanoLiqList) {
        this.novedadDanoLiqList = novedadDanoLiqList;
    }

    @XmlTransient
    public List<GopCierreTurno> getGopCierreTurnoList() {
        return gopCierreTurnoList;
    }

    public void setGopCierreTurnoList(List<GopCierreTurno> gopCierreTurnoList) {
        this.gopCierreTurnoList = gopCierreTurnoList;
    }

    @XmlTransient
    public List<PrgTcResumenVrConciliados> getPrgTcResumenVrConciliadosList() {
        return prgTcResumenVrConciliadosList;
    }

    public void setPrgTcResumenVrConciliadosList(List<PrgTcResumenVrConciliados> prgTcResumenVrConciliadosList) {
        this.prgTcResumenVrConciliadosList = prgTcResumenVrConciliadosList;
    }

    @XmlTransient
    public List<GestorNovedad> getGestorNovedadList() {
        return gestorNovedadList;
    }

    public void setGestorNovedadList(List<GestorNovedad> gestorNovedadList) {
        this.gestorNovedadList = gestorNovedadList;
    }

    @XmlTransient
    public List<GestorTablaTmp> getGestorTablaTmpList() {
        return gestorTablaTmpList;
    }

    public void setGestorTablaTmpList(List<GestorTablaTmp> gestorTablaTmpList) {
        this.gestorTablaTmpList = gestorTablaTmpList;
    }

    @XmlTransient
    public List<GestorNovDetSemana> getGestorNovDetSemanaList() {
        return gestorNovDetSemanaList;
    }

    public void setGestorNovDetSemanaList(List<GestorNovDetSemana> gestorNovDetSemanaList) {
        this.gestorNovDetSemanaList = gestorNovDetSemanaList;
    }

    @XmlTransient
    public List<NominaAutorizacion> getAutorizacionNominaKactusList() {
        return autorizacionNominaKactusList;
    }

    public void setAutorizacionNominaKactusList(List<NominaAutorizacion> autorizacionNominaKactusList) {
        this.autorizacionNominaKactusList = autorizacionNominaKactusList;
    }

    public List<TblCalendario> getTblCalendarioList() {
        return tblCalendarioList;
    }

    public void setTblCalendarioList(List<TblCalendario> tblCalendarioList) {
        this.tblCalendarioList = tblCalendarioList;
    }

    @XmlTransient
    public List<NominaServerParamEmpresa> getNominaServerParamEmpresaList() {
        return nominaServerParamEmpresaList;
    }

    public void setNominaServerParamEmpresaList(List<NominaServerParamEmpresa> nominaServerParamEmpresaList) {
        this.nominaServerParamEmpresaList = nominaServerParamEmpresaList;
    }

    @XmlTransient
    public List<NotificacionTelegramDet> getNotificacionTelegramDetList() {
        return notificacionTelegramDetList;
    }

    public void setNotificacionTelegramDetList(List<NotificacionTelegramDet> notificacionTelegramDetList) {
        this.notificacionTelegramDetList = notificacionTelegramDetList;
    }

    @XmlTransient
    public List<NotificacionProcesoDet> getNotificacionProcesoDetList() {
        return notificacionProcesoDetList;
    }

    public void setNotificacionProcesoDetList(List<NotificacionProcesoDet> notificacionProcesoDetList) {
        this.notificacionProcesoDetList = notificacionProcesoDetList;
    }

    @XmlTransient
    public List<NominaAutorizacionDet> getNominaAutorizacionDetList() {
        return nominaAutorizacionDetList;
    }

    public void setNominaAutorizacionDetList(List<NominaAutorizacionDet> nominaAutorizacionDetList) {
        this.nominaAutorizacionDetList = nominaAutorizacionDetList;
    }

    @XmlTransient
    public List<ConfigFreeway> getConfigFreewayList() {
        return configFreewayList;
    }

    public void setConfigFreewayList(List<ConfigFreeway> configFreewayList) {
        this.configFreewayList = configFreewayList;
    }

    public List<PrgVehicleStatus> getPrgVehicleStatusList() {
        return prgVehicleStatusList;
    }

    public void setPrgVehicleStatusList(List<PrgVehicleStatus> prgVehicleStatusList) {
        this.prgVehicleStatusList = prgVehicleStatusList;
    }

    @XmlTransient
    public List<NominaAutorizacionIndividual> getNominaAutorizacionIndividualList() {
        return nominaAutorizacionIndividualList;
    }

    public void setNominaAutorizacionIndividualList(List<NominaAutorizacionIndividual> nominaAutorizacionIndividualList) {
        this.nominaAutorizacionIndividualList = nominaAutorizacionIndividualList;
    }

    @XmlTransient
    public List<NominaAutorizacionDetIndividual> getNominaAutorizacionDetIndividualList() {
        return nominaAutorizacionDetIndividualList;
    }

    public void setNominaAutorizacionDetIndividualList(List<NominaAutorizacionDetIndividual> nominaAutorizacionDetIndividualList) {
        this.nominaAutorizacionDetIndividualList = nominaAutorizacionDetIndividualList;
    }

    @XmlTransient
    public List<NovedadAutorizacionAusentismos> getNovedadAutorizacionAusentismosList() {
        return novedadAutorizacionAusentismosList;
    }

    public void setNovedadAutorizacionAusentismosList(List<NovedadAutorizacionAusentismos> novedadAutorizacionAusentismosList) {
        this.novedadAutorizacionAusentismosList = novedadAutorizacionAusentismosList;
        }
        @XmlTransient
    public List<PrgStopPoint> getPrgStopPointList() {
        return prgStopPointList;
    }

    public void setPrgStopPointList(List<PrgStopPoint> prgStopPointList) {
        this.prgStopPointList = prgStopPointList;
    }

    @XmlTransient
    public List<PrgRoute> getPrgRouteList() {
        return prgRouteList;
    }

    public void setPrgRouteList(List<PrgRoute> prgRouteList) {
        this.prgRouteList = prgRouteList;
    }

    @XmlTransient
    public List<PrgDistance> getPrgDistanceList() {
        return prgDistanceList;
    }

    public void setPrgDistanceList(List<PrgDistance> prgDistanceList) {
        this.prgDistanceList = prgDistanceList;
    }

    @XmlTransient
    public List<PrgTarea> getPrgTareaList() {
        return prgTareaList;
    }

    public void setPrgTareaList(List<PrgTarea> prgTareaList) {
        this.prgTareaList = prgTareaList;
    }

    public List<PrgPattern> getPrgPatternList() {
        return prgPatternList;
    }

    public void setPrgPatternList(List<PrgPattern> prgPatternList) {
        this.prgPatternList = prgPatternList;
    }

    @XmlTransient
    public List<ParamCierreAusentismo> getParamCierreAusentismoList() {
        return paramCierreAusentismoList;
    }

    public void setParamCierreAusentismoList(List<ParamCierreAusentismo> paramCierreAusentismoList) {
        this.paramCierreAusentismoList = paramCierreAusentismoList;
    }

}
