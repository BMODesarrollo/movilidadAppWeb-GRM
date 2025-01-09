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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author solucionesit
 */
@Entity
@Table(name = "disp_estado_pend_actual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispEstadoPendActual.findAll", query = "SELECT d FROM DispEstadoPendActual d")
    , @NamedQuery(name = "DispEstadoPendActual.findByIdDispEstadoPendActual", query = "SELECT d FROM DispEstadoPendActual d WHERE d.idDispEstadoPendActual = :idDispEstadoPendActual")
    , @NamedQuery(name = "DispEstadoPendActual.findByPrimerEstado", query = "SELECT d FROM DispEstadoPendActual d WHERE d.primerEstado = :primerEstado")
    , @NamedQuery(name = "DispEstadoPendActual.findByNombre", query = "SELECT d FROM DispEstadoPendActual d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispEstadoPendActual.findByNombreByIdVehiculoTipoEstadoDet", query = "SELECT d FROM DispEstadoPendActual d WHERE d.nombre = :nombre AND d.idDispEstadoPendActual <> :idDispEstadoPendActual AND d.estadoReg = :estadoReg")
    , @NamedQuery(name = "DispEstadoPendActual.findByUsername", query = "SELECT d FROM DispEstadoPendActual d WHERE d.username = :username")
    , @NamedQuery(name = "DispEstadoPendActual.findByCreado", query = "SELECT d FROM DispEstadoPendActual d WHERE d.creado = :creado")
    , @NamedQuery(name = "DispEstadoPendActual.findByModificado", query = "SELECT d FROM DispEstadoPendActual d WHERE d.modificado = :modificado")
    , @NamedQuery(name = "DispEstadoPendActual.findByEstadoReg", query = "SELECT d FROM DispEstadoPendActual d WHERE d.estadoReg = :estadoReg")})
public class DispEstadoPendActual implements Serializable {

    @Column(name = "por_defecto_diferir")
    private int porDefectoDiferir;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDispEstadoPendActual", fetch = FetchType.LAZY)
    private List<DispEpaVted> dispEpaVtedList;

    @OneToMany(mappedBy = "idDispEstadoPendActual", fetch = FetchType.LAZY)
    private List<DispConciliacionDet> dispConciliacionDetList;

    @OneToMany(mappedBy = "idDispEstadoPendActual", fetch = FetchType.LAZY)
    private List<DispActividad> dispActividadList;

    @OneToMany(mappedBy = "idDispEstadoPendActual", fetch = FetchType.LAZY)
    private List<DispClasificacionNovedad> dispClasificacionNovedadList;
    @OneToMany(mappedBy = "idDispEstadoPendActual", fetch = FetchType.LAZY)
    private List<VehiculoEstadoHistorico> VehiculoEstadoHistoricoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_estado_pend_actual")
    private Integer idDispEstadoPendActual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "primer_estado")
    private int primerEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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

    public DispEstadoPendActual() {
    }

    public DispEstadoPendActual(Integer idDispEstadoPendActual) {
        this.idDispEstadoPendActual = idDispEstadoPendActual;
    }

    public DispEstadoPendActual(Integer idDispEstadoPendActual, int primerEstado, String nombre, String username, Date creado, int estadoReg) {
        this.idDispEstadoPendActual = idDispEstadoPendActual;
        this.primerEstado = primerEstado;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDispEstadoPendActual() {
        return idDispEstadoPendActual;
    }

    public void setIdDispEstadoPendActual(Integer idDispEstadoPendActual) {
        this.idDispEstadoPendActual = idDispEstadoPendActual;
    }

    public int getPrimerEstado() {
        return primerEstado;
    }

    public void setPrimerEstado(int primerEstado) {
        this.primerEstado = primerEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idDispEstadoPendActual != null ? idDispEstadoPendActual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispEstadoPendActual)) {
            return false;
        }
        DispEstadoPendActual other = (DispEstadoPendActual) object;
        if ((this.idDispEstadoPendActual == null && other.idDispEstadoPendActual != null) || (this.idDispEstadoPendActual != null && !this.idDispEstadoPendActual.equals(other.idDispEstadoPendActual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispEstadoPendActual[ idDispEstadoPendActual=" + idDispEstadoPendActual + " ]";
    }

    @XmlTransient
    public List<DispClasificacionNovedad> getDispClasificacionNovedadList() {
        return dispClasificacionNovedadList;
    }

    public void setDispClasificacionNovedadList(List<DispClasificacionNovedad> dispClasificacionNovedadList) {
        this.dispClasificacionNovedadList = dispClasificacionNovedadList;
    }

    @XmlTransient
    public List<DispActividad> getDispActividadList() {
        return dispActividadList;
    }

    public void setDispActividadList(List<DispActividad> dispActividadList) {
        this.dispActividadList = dispActividadList;
    }

    public int getPorDefectoDiferir() {
        return porDefectoDiferir;
    }

    public void setPorDefectoDiferir(int porDefectoDiferir) {
        this.porDefectoDiferir = porDefectoDiferir;
    }

    @XmlTransient
    public List<DispConciliacionDet> getDispConciliacionDetList() {
        return dispConciliacionDetList;
    }

    public void setDispConciliacionDetList(List<DispConciliacionDet> dispConciliacionDetList) {
        this.dispConciliacionDetList = dispConciliacionDetList;
    }

    @XmlTransient
    public List<VehiculoEstadoHistorico> getVehiculoEstadoHistoricoList() {
        return VehiculoEstadoHistoricoList;
    }

    public void setVehiculoEstadoHistoricoList(List<VehiculoEstadoHistorico> VehiculoEstadoHistoricoList) {
        this.VehiculoEstadoHistoricoList = VehiculoEstadoHistoricoList;
    }


    @XmlTransient
    public List<DispEpaVted> getDispEpaVtedList() {
        return dispEpaVtedList;
    }

    public void setDispEpaVtedList(List<DispEpaVted> dispEpaVtedList) {
        this.dispEpaVtedList = dispEpaVtedList;
    }

}
