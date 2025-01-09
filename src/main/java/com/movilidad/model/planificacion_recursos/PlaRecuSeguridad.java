package com.movilidad.model.planificacion_recursos;

import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PrgRoute;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Permite hacer persistencia con la tabla seguridad 
 * @author Omar.beltran
 */
@Entity
@Table(name = "pla_recu_seguridad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuSeguridad.findAll", query = "SELECT i FROM PlaRecuSeguridad i"),
    @NamedQuery(name = "PlaRecuSeguridad.findByIdSeguridad", query = "SELECT i FROM PlaRecuSeguridad i WHERE i.idPlaRecuSeguridad = :idPlaRecuSeguridad"),
    @NamedQuery(name = "PlaRecuSeguridad.findByCreado", query = "SELECT i FROM PlaRecuSeguridad i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuSeguridad.findByModificado", query = "SELECT i FROM PlaRecuSeguridad i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuSeguridad.findByEstadoReg", query = "SELECT i FROM PlaRecuSeguridad i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuSeguridad implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_seguridad")
    private Integer idPlaRecuSeguridad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @JoinColumn(name = "id_pla_recu_motivo", referencedColumnName = "id_pla_recu_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuMotivo idMotivo;
    @JoinColumn(name = "id_pla_recu_ruta", referencedColumnName = "id_pla_recu_ruta")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuRuta idPlaRecuRuta;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado; // se emplea para obtiener código TM, cédula, nombres, apellidos, cargo
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
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
        return idPlaRecuSeguridad;
    }

    public void setIdEjecucion(Integer idPlaRecuSeguridad) {
        this.idPlaRecuSeguridad = idPlaRecuSeguridad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public PlaRecuMotivo getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(PlaRecuMotivo idMotivo) {
        this.idMotivo = idMotivo;
    }

    public PlaRecuRuta getIdPlaRecuRuta() {
        return idPlaRecuRuta;
    }

    public void setIdPlaRecuRuta(PlaRecuRuta idPlaRecuRuta) {
        this.idPlaRecuRuta = idPlaRecuRuta;
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

    public Integer getIdPlaRecuSeguridad() {
        return idPlaRecuSeguridad;
    }

    public void setIdPlaRecuSeguridad(Integer idPlaRecuSeguridad) {
        this.idPlaRecuSeguridad = idPlaRecuSeguridad;
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
        if (!(object instanceof PlaRecuSeguridad)) {
            return false;
        }
        PlaRecuSeguridad other = (PlaRecuSeguridad) object;
        return !((this.idPlaRecuSeguridad == null && other.idPlaRecuSeguridad != null) || (this.idPlaRecuSeguridad != null && 
                !this.idPlaRecuSeguridad.equals(other.idPlaRecuSeguridad)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuSeguridad[ idPlaRecuSeguridad=" + idPlaRecuSeguridad + " ]";
    }
    
}
