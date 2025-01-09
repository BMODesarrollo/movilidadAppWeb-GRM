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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "auditoria_realizado_por")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditoriaRealizadoPor.findAll", query = "SELECT a FROM AuditoriaRealizadoPor a")
    , @NamedQuery(name = "AuditoriaRealizadoPor.findByIdAuditoriaRealizadoPor", query = "SELECT a FROM AuditoriaRealizadoPor a WHERE a.idAuditoriaRealizadoPor = :idAuditoriaRealizadoPor")
    , @NamedQuery(name = "AuditoriaRealizadoPor.findByFecha", query = "SELECT a FROM AuditoriaRealizadoPor a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AuditoriaRealizadoPor.findByUsername", query = "SELECT a FROM AuditoriaRealizadoPor a WHERE a.username = :username")
    , @NamedQuery(name = "AuditoriaRealizadoPor.findByCreado", query = "SELECT a FROM AuditoriaRealizadoPor a WHERE a.creado = :creado")
    , @NamedQuery(name = "AuditoriaRealizadoPor.findByModificado", query = "SELECT a FROM AuditoriaRealizadoPor a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AuditoriaRealizadoPor.findByEstadoReg", query = "SELECT a FROM AuditoriaRealizadoPor a WHERE a.estadoReg = :estadoReg")})
public class AuditoriaRealizadoPor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_realizado_por")
    private Integer idAuditoriaRealizadoPor;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_auditoria", referencedColumnName = "id_auditoria")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auditoria idAuditoria;
    @JoinColumn(name = "id_auditoria_area_comun", referencedColumnName = "id_auditoria_area_comun")
    @ManyToOne(fetch = FetchType.LAZY)
    private AuditoriaAreaComun idAuditoriaAreaComun;
    @JoinColumn(name = "id_auditoria_estacion", referencedColumnName = "id_auditoria_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AuditoriaEstacion idAuditoriaEstacion;
    @JoinColumn(name = "id_empleado_auditado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleadoAuditado;
    @JoinColumn(name = "id_empleado_realiza", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleadoRealiza;
    @JoinColumn(name = "id_vehiculo_auditado", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculoAuditado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuditoriaRealizadoPor", fetch = FetchType.LAZY)
    private List<AuditoriaRespuesta> auditoriaRespuestaList;

    public AuditoriaRealizadoPor() {
    }

    public AuditoriaRealizadoPor(Integer idAuditoriaRealizadoPor) {
        this.idAuditoriaRealizadoPor = idAuditoriaRealizadoPor;
    }

    public Integer getIdAuditoriaRealizadoPor() {
        return idAuditoriaRealizadoPor;
    }

    public void setIdAuditoriaRealizadoPor(Integer idAuditoriaRealizadoPor) {
        this.idAuditoriaRealizadoPor = idAuditoriaRealizadoPor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public Auditoria getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Auditoria idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public AuditoriaAreaComun getIdAuditoriaAreaComun() {
        return idAuditoriaAreaComun;
    }

    public void setIdAuditoriaAreaComun(AuditoriaAreaComun idAuditoriaAreaComun) {
        this.idAuditoriaAreaComun = idAuditoriaAreaComun;
    }

    public AuditoriaEstacion getIdAuditoriaEstacion() {
        return idAuditoriaEstacion;
    }

    public void setIdAuditoriaEstacion(AuditoriaEstacion idAuditoriaEstacion) {
        this.idAuditoriaEstacion = idAuditoriaEstacion;
    }

    public Empleado getIdEmpleadoAuditado() {
        return idEmpleadoAuditado;
    }

    public void setIdEmpleadoAuditado(Empleado idEmpleadoAuditado) {
        this.idEmpleadoAuditado = idEmpleadoAuditado;
    }

    public Empleado getIdEmpleadoRealiza() {
        return idEmpleadoRealiza;
    }

    public void setIdEmpleadoRealiza(Empleado idEmpleadoRealiza) {
        this.idEmpleadoRealiza = idEmpleadoRealiza;
    }

    public Vehiculo getIdVehiculoAuditado() {
        return idVehiculoAuditado;
    }

    public void setIdVehiculoAuditado(Vehiculo idVehiculoAuditado) {
        this.idVehiculoAuditado = idVehiculoAuditado;
    }

    public List<AuditoriaRespuesta> getAuditoriaRespuestaList() {
        return auditoriaRespuestaList;
    }

    public void setAuditoriaRespuestaList(List<AuditoriaRespuesta> auditoriaRespuestaList) {
        this.auditoriaRespuestaList = auditoriaRespuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaRealizadoPor != null ? idAuditoriaRealizadoPor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditoriaRealizadoPor)) {
            return false;
        }
        AuditoriaRealizadoPor other = (AuditoriaRealizadoPor) object;
        if ((this.idAuditoriaRealizadoPor == null && other.idAuditoriaRealizadoPor != null) || (this.idAuditoriaRealizadoPor != null && !this.idAuditoriaRealizadoPor.equals(other.idAuditoriaRealizadoPor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AuditoriaRealizadoPor[ idAuditoriaRealizadoPor=" + idAuditoriaRealizadoPor + " ]";
    }

}
