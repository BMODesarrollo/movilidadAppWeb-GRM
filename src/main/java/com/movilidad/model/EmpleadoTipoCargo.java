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
import javax.persistence.CascadeType;
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
 * @author cesar
 */
@Entity
@Table(name = "empleado_tipo_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoTipoCargo.findAll", query = "SELECT e FROM EmpleadoTipoCargo e"),
    @NamedQuery(name = "EmpleadoTipoCargo.findByIdEmpleadoTipoCargo", query = "SELECT e FROM EmpleadoTipoCargo e WHERE e.idEmpleadoTipoCargo = :idEmpleadoTipoCargo"),
    @NamedQuery(name = "EmpleadoTipoCargo.findByNombreCargo", query = "SELECT e FROM EmpleadoTipoCargo e WHERE e.nombreCargo = :nombreCargo"),
    @NamedQuery(name = "EmpleadoTipoCargo.findByIdSoftwareNombreCargo", query = "SELECT e FROM EmpleadoTipoCargo e WHERE e.idSoftwareNombreCargo = :idSoftwareNombreCargo"),
    @NamedQuery(name = "EmpleadoTipoCargo.findByUsername", query = "SELECT e FROM EmpleadoTipoCargo e WHERE e.username = :username"),
    @NamedQuery(name = "EmpleadoTipoCargo.findByCreado", query = "SELECT e FROM EmpleadoTipoCargo e WHERE e.creado = :creado"),
    @NamedQuery(name = "EmpleadoTipoCargo.findByModificado", query = "SELECT e FROM EmpleadoTipoCargo e WHERE e.modificado = :modificado"),
    @NamedQuery(name = "EmpleadoTipoCargo.findByEstadoReg", query = "SELECT e FROM EmpleadoTipoCargo e WHERE e.estadoReg = :estadoReg")})
public class EmpleadoTipoCargo implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<DocumentoPorCargo> documentoPorCargoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoCargo", fetch = FetchType.LAZY)
    private List<GenericaPmLiquidacionHist> genericaPmLiquidacionHistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoCargo", fetch = FetchType.LAZY)
    private List<GenericaPmLiquidacion> genericaPmLiquidacionList;
    @OneToMany(mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<GenericaPmGrupoParam> genericaPmGrupoParamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoCargo", fetch = FetchType.LAZY)
    private List<TblHistoricoIquidacionEmpleado> tblHistoricoIquidacionEmpleadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoCargo", fetch = FetchType.LAZY)
    private List<TblLiquidacionEmpleadoMes> tblLiquidacionEmpleadoMesList;
    @OneToMany(mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<GenericaTurnoJornadaDet> genericaTurnoJornadaDetList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<ParamCargoCc> paramCargoCcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<GenericaPmVrbonos> genericaPmVrbonosList;
    @OneToMany(mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<EmpleadoCargoCosto> empleadoCargoCostoList;

    @OneToMany(mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<ParamAreaCargo> paramAreaCargoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_tipo_cargo")
    private Integer idEmpleadoTipoCargo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_cargo")
    private String nombreCargo;
    @Column(name = "id_software_nombre_cargo")
    private Integer idSoftwareNombreCargo;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoCargo", fetch = FetchType.LAZY)
    private List<Empleado> empleadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<PmVrbonos> pmVrbonosList;
    @OneToMany(mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<GestorTablaTmp> gestorTablaTmpList;
    @OneToMany(mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<GestorNovDetSemana> gestorNovDetSemanaList;
    @OneToMany(mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<GestorNovParamDet> gestorNovParamDetList;
    @OneToMany(mappedBy = "idEmpleadoTipoCargo", fetch = FetchType.LAZY)
    private List<GestorNovReqSemana> gestorNovReqSemanaList;

    public EmpleadoTipoCargo() {
    }

    public EmpleadoTipoCargo(Integer idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    public EmpleadoTipoCargo(Integer idEmpleadoTipoCargo, String nombreCargo, String username, Date creado, int estadoReg) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
        this.nombreCargo = nombreCargo;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(Integer idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public Integer getIdSoftwareNombreCargo() {
        return idSoftwareNombreCargo;
    }

    public void setIdSoftwareNombreCargo(Integer idSoftwareNombreCargo) {
        this.idSoftwareNombreCargo = idSoftwareNombreCargo;
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
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @XmlTransient
    public List<PmVrbonos> getPmVrbonosList() {
        return pmVrbonosList;
    }

    public void setPmVrbonosList(List<PmVrbonos> pmVrbonosList) {
        this.pmVrbonosList = pmVrbonosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleadoTipoCargo != null ? idEmpleadoTipoCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoTipoCargo)) {
            return false;
        }
        EmpleadoTipoCargo other = (EmpleadoTipoCargo) object;
        if ((this.idEmpleadoTipoCargo == null && other.idEmpleadoTipoCargo != null) || (this.idEmpleadoTipoCargo != null && !this.idEmpleadoTipoCargo.equals(other.idEmpleadoTipoCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.EmpleadoTipoCargo[ idEmpleadoTipoCargo=" + idEmpleadoTipoCargo + " ]";
    }

    @XmlTransient
    public List<ParamAreaCargo> getParamAreaCargoList() {
        return paramAreaCargoList;
    }

    public void setParamAreaCargoList(List<ParamAreaCargo> paramAreaCargoList) {
        this.paramAreaCargoList = paramAreaCargoList;
    }

    @XmlTransient
    public List<EmpleadoCargoCosto> getEmpleadoCargoCostoList() {
        return empleadoCargoCostoList;
    }

    public void setEmpleadoCargoCostoList(List<EmpleadoCargoCosto> empleadoCargoCostoList) {
        this.empleadoCargoCostoList = empleadoCargoCostoList;
    }

    @XmlTransient
    public List<GenericaPmVrbonos> getGenericaPmVrbonosList() {
        return genericaPmVrbonosList;
    }

    public void setGenericaPmVrbonosList(List<GenericaPmVrbonos> genericaPmVrbonosList) {
        this.genericaPmVrbonosList = genericaPmVrbonosList;
    }

    @XmlTransient
    public List<ParamCargoCc> getParamCargoCcList() {
        return paramCargoCcList;
    }

    public void setParamCargoCcList(List<ParamCargoCc> paramCargoCcList) {
        this.paramCargoCcList = paramCargoCcList;
    }

    @XmlTransient
    public List<GenericaPmGrupoParam> getGenericaPmGrupoParamList() {
        return genericaPmGrupoParamList;
    }

    public void setGenericaPmGrupoParamList(List<GenericaPmGrupoParam> genericaPmGrupoParamList) {
        this.genericaPmGrupoParamList = genericaPmGrupoParamList;
    }

    @XmlTransient
    public List<GenericaTurnoJornadaDet> getGenericaTurnoJornadaDetList() {
        return genericaTurnoJornadaDetList;
    }

    public void setGenericaTurnoJornadaDetList(List<GenericaTurnoJornadaDet> genericaTurnoJornadaDetList) {
        this.genericaTurnoJornadaDetList = genericaTurnoJornadaDetList;
    }

    @XmlTransient
    public List<TblLiquidacionEmpleadoMes> getTblLiquidacionEmpleadoMesList() {
        return tblLiquidacionEmpleadoMesList;
    }

    public void setTblLiquidacionEmpleadoMesList(List<TblLiquidacionEmpleadoMes> tblLiquidacionEmpleadoMesList) {
        this.tblLiquidacionEmpleadoMesList = tblLiquidacionEmpleadoMesList;
    }

    @XmlTransient
    public List<TblHistoricoIquidacionEmpleado> getTblHistoricoIquidacionEmpleadoList() {
        return tblHistoricoIquidacionEmpleadoList;
    }

    public void setTblHistoricoIquidacionEmpleadoList(List<TblHistoricoIquidacionEmpleado> tblHistoricoIquidacionEmpleadoList) {
        this.tblHistoricoIquidacionEmpleadoList = tblHistoricoIquidacionEmpleadoList;
    }

    @XmlTransient
    public List<GenericaPmLiquidacionHist> getGenericaPmLiquidacionHistList() {
        return genericaPmLiquidacionHistList;
    }

    public void setGenericaPmLiquidacionHistList(List<GenericaPmLiquidacionHist> genericaPmLiquidacionHistList) {
        this.genericaPmLiquidacionHistList = genericaPmLiquidacionHistList;
    }

    @XmlTransient
    public List<GenericaPmLiquidacion> getGenericaPmLiquidacionList() {
        return genericaPmLiquidacionList;
    }

    public void setGenericaPmLiquidacionList(List<GenericaPmLiquidacion> genericaPmLiquidacionList) {
        this.genericaPmLiquidacionList = genericaPmLiquidacionList;
    }

    @XmlTransient
    public List<DocumentoPorCargo> getDocumentoPorCargoList() {
        return documentoPorCargoList;
    }

    public void setDocumentoPorCargoList(List<DocumentoPorCargo> documentoPorCargoList) {
        this.documentoPorCargoList = documentoPorCargoList;
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
    public List<GestorNovParamDet> getGestorNovParamDetList() {
        return gestorNovParamDetList;
    }

    public void setGestorNovParamDetList(List<GestorNovParamDet> gestorNovParamDetList) {
        this.gestorNovParamDetList = gestorNovParamDetList;
    }

    @XmlTransient
    public List<GestorNovReqSemana> getGestorNovReqSemanaList() {
        return gestorNovReqSemanaList;
    }

    public void setGestorNovReqSemanaList(List<GestorNovReqSemana> gestorNovReqSemanaList) {
        this.gestorNovReqSemanaList = gestorNovReqSemanaList;
    }

}
