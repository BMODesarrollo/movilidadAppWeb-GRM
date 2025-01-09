/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "prg_vehiculo_inactivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgVehiculoInactivo.findAll", query = "SELECT p FROM PrgVehiculoInactivo p"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByIdPrgVehiculoInactivo", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.idPrgVehiculoInactivo = :idPrgVehiculoInactivo"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByFromDate", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.fromDate = :fromDate"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByToDate", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.toDate = :toDate"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByActivo", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.activo = :activo"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByUsername", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.username = :username"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByCreado", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByUsrHabilita", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.usrHabilita = :usrHabilita"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByModificado", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgVehiculoInactivo.findByEstadoReg", query = "SELECT p FROM PrgVehiculoInactivo p WHERE p.estadoReg = :estadoReg")})
public class PrgVehiculoInactivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_vehiculo_inactivo")
    private Integer idPrgVehiculoInactivo;
    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Integer activo;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Size(max = 15)
    @Column(name = "usr_habilita")
    private String usrHabilita;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne
    private Vehiculo idVehiculo;
    @JoinColumn(name = "id_vehiculo_estado", referencedColumnName = "id_vehiculo_tipo_estado")
    @ManyToOne
    private VehiculoTipoEstado idVehiculoEstado;

    public PrgVehiculoInactivo() {
    }

    public PrgVehiculoInactivo(Integer idPrgVehiculoInactivo) {
        this.idPrgVehiculoInactivo = idPrgVehiculoInactivo;
    }

    public Integer getIdPrgVehiculoInactivo() {
        return idPrgVehiculoInactivo;
    }

    public void setIdPrgVehiculoInactivo(Integer idPrgVehiculoInactivo) {
        this.idPrgVehiculoInactivo = idPrgVehiculoInactivo;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public String getUsrHabilita() {
        return usrHabilita;
    }

    public void setUsrHabilita(String usrHabilita) {
        this.usrHabilita = usrHabilita;
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

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public VehiculoTipoEstado getIdVehiculoEstado() {
        return idVehiculoEstado;
    }

    public void setIdVehiculoEstado(VehiculoTipoEstado idVehiculoEstado) {
        this.idVehiculoEstado = idVehiculoEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgVehiculoInactivo != null ? idPrgVehiculoInactivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgVehiculoInactivo)) {
            return false;
        }
        PrgVehiculoInactivo other = (PrgVehiculoInactivo) object;
        if ((this.idPrgVehiculoInactivo == null && other.idPrgVehiculoInactivo != null) || (this.idPrgVehiculoInactivo != null && !this.idPrgVehiculoInactivo.equals(other.idPrgVehiculoInactivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgVehiculoInactivo[ idPrgVehiculoInactivo=" + idPrgVehiculoInactivo + " ]";
    }
    
}
