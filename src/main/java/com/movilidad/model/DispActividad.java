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
 * @author solucionesit
 */
@Entity
@Table(name = "disp_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispActividad.findAll", query = "SELECT d FROM DispActividad d")
    , @NamedQuery(name = "DispActividad.findByIdDispActividad", query = "SELECT d FROM DispActividad d WHERE d.idDispActividad = :idDispActividad")
    , @NamedQuery(name = "DispActividad.findByFechaHora", query = "SELECT d FROM DispActividad d WHERE d.fechaHora = :fechaHora")
    , @NamedQuery(name = "DispActividad.findByDiferir", query = "SELECT d FROM DispActividad d WHERE d.diferir = :diferir")
    , @NamedQuery(name = "DispActividad.findByObservacion", query = "SELECT d FROM DispActividad d WHERE d.observacion = :observacion")
    , @NamedQuery(name = "DispActividad.findByUsername", query = "SELECT d FROM DispActividad d WHERE d.username = :username")
    , @NamedQuery(name = "DispActividad.findByCreado", query = "SELECT d FROM DispActividad d WHERE d.creado = :creado")
    , @NamedQuery(name = "DispActividad.findByModificado", query = "SELECT d FROM DispActividad d WHERE d.modificado = :modificado")
    , @NamedQuery(name = "DispActividad.findByEstadoReg", query = "SELECT d FROM DispActividad d WHERE d.estadoReg = :estadoReg")})
public class DispActividad implements Serializable {

    @OneToMany(mappedBy = "idDispActividad", fetch = FetchType.LAZY)
    private List<VehiculoEstadoHistorico> vehiculoEstadoHistoricoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_actividad")
    private Integer idDispActividad;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "diferir")
    private Integer diferir;
    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_disp_estado_pend_actual", referencedColumnName = "id_disp_estado_pend_actual")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispEstadoPendActual idDispEstadoPendActual;
    @JoinColumn(name = "id_vehiculo_tipo_estado_det", referencedColumnName = "id_vehiculo_tipo_estado_det")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipoEstadoDet idVehiculoTipoEstadoDet;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Novedad idNovedad;

    @OneToMany(mappedBy = "idDispActividad", fetch = FetchType.LAZY)
    private List<DispFaltanteRepuesto> dispFaltanteRepuestoList;

    public DispActividad() {
    }

    public DispActividad(Integer idDispActividad) {
        this.idDispActividad = idDispActividad;
    }

    public DispActividad(Integer idDispActividad, String username, Date creado, int estadoReg) {
        this.idDispActividad = idDispActividad;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDispActividad() {
        return idDispActividad;
    }

    public void setIdDispActividad(Integer idDispActividad) {
        this.idDispActividad = idDispActividad;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getDiferir() {
        return diferir;
    }

    public void setDiferir(Integer diferir) {
        this.diferir = diferir;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public DispEstadoPendActual getIdDispEstadoPendActual() {
        return idDispEstadoPendActual;
    }

    public void setIdDispEstadoPendActual(DispEstadoPendActual idDispEstadoPendActual) {
        this.idDispEstadoPendActual = idDispEstadoPendActual;
    }

    public VehiculoTipoEstadoDet getIdVehiculoTipoEstadoDet() {
        return idVehiculoTipoEstadoDet;
    }

    public void setIdVehiculoTipoEstadoDet(VehiculoTipoEstadoDet idVehiculoTipoEstadoDet) {
        this.idVehiculoTipoEstadoDet = idVehiculoTipoEstadoDet;
    }

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    @XmlTransient
    public List<VehiculoEstadoHistorico> getVehiculoEstadoHistoricoList() {
        return vehiculoEstadoHistoricoList;
    }

    public void setVehiculoEstadoHistoricoList(List<VehiculoEstadoHistorico> vehiculoEstadoHistoricoList) {
        this.vehiculoEstadoHistoricoList = vehiculoEstadoHistoricoList;
    }

    @XmlTransient
    public List<DispFaltanteRepuesto> getDispFaltanteRepuestoList() {
        return dispFaltanteRepuestoList;
    }

    public void setDispFaltanteRepuestoList(List<DispFaltanteRepuesto> dispFaltanteRepuestoList) {
        this.dispFaltanteRepuestoList = dispFaltanteRepuestoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispActividad != null ? idDispActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispActividad)) {
            return false;
        }
        DispActividad other = (DispActividad) object;
        if ((this.idDispActividad == null && other.idDispActividad != null) || (this.idDispActividad != null && !this.idDispActividad.equals(other.idDispActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispActividad[ idDispActividad=" + idDispActividad + " ]";
    }

}
