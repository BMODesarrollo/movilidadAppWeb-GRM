package com.movilidad.model.planificacion_recursos;

import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.planificacion_recursos.PlaRecuModalidad;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Permite hacer persistencia con la tabla  formación y desarrollo entrega de operadores
 * @author Omar.beltran
 */
@Entity
@Table(name = "pla_recu_ausentismo_paa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuAusentismoPAA.findAll", query = "SELECT i FROM PlaRecuAusentismoPAA i"),
    @NamedQuery(name = "PlaRecuAusentismoPAA.findByIdAusentismoPAA", query = "SELECT i FROM PlaRecuAusentismoPAA i WHERE i.idPlaRecuAusentismoPAA = :idPlaRecuAusentismoPAA"),
    @NamedQuery(name = "PlaRecuAusentismoPAA.findByCreado", query = "SELECT i FROM PlaRecuAusentismoPAA i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuAusentismoPAA.findByModificado", query = "SELECT i FROM PlaRecuAusentismoPAA i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuAusentismoPAA.findByEstadoReg", query = "SELECT i FROM PlaRecuAusentismoPAA i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuAusentismoPAA implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_ausentismo_paa")
    private Integer idPlaRecuAusentismoPAA;
    @JoinColumn(name = "id_pla_recu_modalidad", referencedColumnName = "id_pla_recu_modalidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuModalidad idPlaRecuModalidad;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado; // se emplea para obtiener código TM, cédula, nombres, apellidos, cargo
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio_paa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioPAA;
    @Column(name = "dias_pendientes")
    private String diasPendientes;
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 15)
    @Column(name = "username_create")
    private String usernameCreate;
    @Size(max = 15)
    @Column(name = "username_edit")
    private String usernameEdit;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public Integer getIdEjecucion() {
        return idPlaRecuAusentismoPAA;
    }

    public void setIdEjecucion(Integer idPlaRecuAusentismoPAA) {
        this.idPlaRecuAusentismoPAA = idPlaRecuAusentismoPAA;
    }

    public PlaRecuModalidad getIdPlaRecuModalidad() {
        return idPlaRecuModalidad;
    }

    public void setIdPlaRecuModalidad(PlaRecuModalidad idPlaRecuModalidad) {
        this.idPlaRecuModalidad = idPlaRecuModalidad;
    }

    public Date getFechaInicioPAA() {
        return fechaInicioPAA;
    }

    public void setFechaInicioPAA(Date fechaInicioPAA) {
        this.fechaInicioPAA = fechaInicioPAA;
    }

    public String getDiasPendientes() {
        return diasPendientes;
    }

    public void setDiasPendientes(String diasPendientes) {
        this.diasPendientes = diasPendientes;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUsernameCreate() {
        return usernameCreate;
    }

    public void setUsernameCreate(String usernameCreate) {
        this.usernameCreate = usernameCreate;
    }

    public String getUsernameEdit() {
        return usernameEdit;
    }

    public void setUsernameEdit(String usernameEdit) {
        this.usernameEdit = usernameEdit;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public Integer getIdPlaRecuAusentismoPAA() {
        return idPlaRecuAusentismoPAA;
    }

    public void setIdPlaRecuAusentismoPAA(Integer idPlaRecuAusentismoPAA) {
        this.idPlaRecuAusentismoPAA = idPlaRecuAusentismoPAA;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuAusentismoPAA)) {
            return false;
        }
        PlaRecuAusentismoPAA other = (PlaRecuAusentismoPAA) object;
        return !((this.idPlaRecuAusentismoPAA == null && other.idPlaRecuAusentismoPAA != null) || (this.idPlaRecuAusentismoPAA != null && 
                !this.idPlaRecuAusentismoPAA.equals(other.idPlaRecuAusentismoPAA)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuAusentismoPAA[ idPlaRecuAusentismoPAA=" + idPlaRecuAusentismoPAA + " ]";
    }
    
}
