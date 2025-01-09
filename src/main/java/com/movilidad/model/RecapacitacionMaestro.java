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
import javax.persistence.FetchType;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeisson Junco
 */
@Entity
@Table(name = "recapacitacion_maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecapacitacionMaestro.findAll", query = "SELECT r FROM RecapacitacionMaestro r"),
    @NamedQuery(name = "RecapacitacionMaestro.findByIdRecapacitacionMaestro", query = "SELECT r FROM RecapacitacionMaestro r WHERE r.idRecapacitacionMaestro = :idRecapacitacionMaestro"),
    @NamedQuery(name = "RecapacitacionMaestro.findByIdEmpleado", query = "SELECT r FROM RecapacitacionMaestro r WHERE r.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "RecapacitacionMaestro.findByAsistencia", query = "SELECT r FROM RecapacitacionMaestro r WHERE r.asistencia = :asistencia"),
    @NamedQuery(name = "RecapacitacionMaestro.findByUsername", query = "SELECT r FROM RecapacitacionMaestro r WHERE r.username = :username"),
    @NamedQuery(name = "RecapacitacionMaestro.findByCreado", query = "SELECT r FROM RecapacitacionMaestro r WHERE r.creado = :creado"),
    @NamedQuery(name = "RecapacitacionMaestro.findByModificado", query = "SELECT r FROM RecapacitacionMaestro r WHERE r.modificado = :modificado"),
    @NamedQuery(name = "RecapacitacionMaestro.findByEstadoReg", query = "SELECT r FROM RecapacitacionMaestro r WHERE r.estadoReg = :estadoReg")})
public class RecapacitacionMaestro implements Serializable {

    @Basic(optional = false)
    @Column(name = "fecha_citacion")
    private Date fechaCitacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inoperable")
    @Temporal(TemporalType.DATE)
    private Date fechaInoperable;
    @Basic(optional = false)
    @Column(name = "fecha_ejecucion")
    @Temporal(TemporalType.DATE)
    private Date fechaEjecucion;
    @Column(name = "asistencia")
    private Boolean asistencia;
    @Column(name = "programado")
    private Boolean programado;
    @Column(name = "notificado")
    private Boolean notificado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_recapacitacion_maestro")
    private Integer idRecapacitacionMaestro;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER
    private GopUnidadFuncional idGopUnidadFuncional;

    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(optional = false, fetch = FetchType.EAGER) // Cambiado a EAGER
    private Novedad idNovedad;

    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER
    private Empleado idEmpleado;

    @JoinColumn(name = "id_capacitador", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER
    private Empleado idCapacitador;

    @Lob
    @Size(max = 16777215)
    @Column(name = "observacion")
    private String observacion;

    @Size(max = 15)
    @Column(name = "username")
    private String username;

    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;

    @Column(name = "path_asistencia")
    private String pathAsistencia;

    @Column(name = "path_evaluacion")
    private String pathEvaluacion;
    
    
    @Column(name = "path_photos")
    private String pathPhotos;
    
    
    @Column(name = "estado_reg")
    private Integer estadoReg;
        
    @Transient
    private Date fechaCitacionDateF;
    
    @Transient
    private Date fechaEjecucionDateF;

    public RecapacitacionMaestro() {
    }

    public RecapacitacionMaestro(Integer idRecapacitacionMaestro) {
        this.idRecapacitacionMaestro = idRecapacitacionMaestro;
    }

    public Integer getIdRecapacitacionMaestro() {
        return idRecapacitacionMaestro;
    }

    public void setIdRecapacitacionMaestro(Integer idRecapacitacionMaestro) {
        this.idRecapacitacionMaestro = idRecapacitacionMaestro;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleado getIdCapacitador() {
        return idCapacitador;
    }

    public void setIdCapacitador(Empleado idCapacitador) {
        this.idCapacitador = idCapacitador;
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

    public String getPathAsistencia() {
        return pathAsistencia;
    }

    public void setPathAsistencia(String pathAsistencia) {
        this.pathAsistencia = pathAsistencia;
    }

    public String getPathEvaluacion() {
        return pathEvaluacion;
    }

    public void setPathEvaluacion(String pathEvaluacion) {
        this.pathEvaluacion = pathEvaluacion;
    }

    public String getPathPhotos() {
        return pathPhotos;
    }

    public void setPathPhotos(String pathPhotos) {
        this.pathPhotos = pathPhotos;
    }

    
    
    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecapacitacionMaestro != null ? idRecapacitacionMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecapacitacionMaestro)) {
            return false;
        }
        RecapacitacionMaestro other = (RecapacitacionMaestro) object;
        if ((this.idRecapacitacionMaestro == null && other.idRecapacitacionMaestro != null) || (this.idRecapacitacionMaestro != null && !this.idRecapacitacionMaestro.equals(other.idRecapacitacionMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.RecapacitacionMaestro[ idRecapacitacionMaestro=" + idRecapacitacionMaestro + " ]";
    }

    public Date getFechaCitacion() {
        return fechaCitacion;
    }

    public void setFechaCitacion(Date fechaCitacion) {
        this.fechaCitacion = fechaCitacion;
    }

    public Date getFechaInoperable() {
        return fechaInoperable;
    }

    public void setFechaInoperable(Date fechaInoperable) {
        this.fechaInoperable = fechaInoperable;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

    public Boolean getProgramado() {
        return programado;
    }

    public void setProgramado(Boolean programado) {
        this.programado = programado;
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public Date getFechaCitacionDateF() {
        return fechaCitacionDateF;
    }

    public void setFechaCitacionDateF(Date fechaCitacionDateF) {
        this.fechaCitacionDateF = fechaCitacionDateF;
    }

    public Date getFechaEjecucionDateF() {
        return fechaEjecucionDateF;
    }

    public void setFechaEjecucionDateF(Date fechaEjecucionDateF) {
        this.fechaEjecucionDateF = fechaEjecucionDateF;
    }

    

}
