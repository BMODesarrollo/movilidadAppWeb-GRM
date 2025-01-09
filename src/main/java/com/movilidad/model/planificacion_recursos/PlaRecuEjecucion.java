package com.movilidad.model.planificacion_recursos;

import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
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
 * Permite hacer persistencia con la tabla ejecucion 
 * @author Omar.beltran
 */
@Entity
@Table(name = "pla_recu_ejecucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuEjecucion.findAll", query = "SELECT i FROM PlaRecuEjecucion i"),
    @NamedQuery(name = "PlaRecuEjecucion.findByIdEjecucion", query = "SELECT i FROM PlaRecuEjecucion i WHERE i.idPlaRecuEjecucion = :idPlaRecuEjecucion"),
    @NamedQuery(name = "PlaRecuEjecucion.findByDescripcion", query = "SELECT i FROM PlaRecuEjecucion i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "PlaRecuEjecucion.findByCreado", query = "SELECT i FROM PlaRecuEjecucion i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuEjecucion.findByModificado", query = "SELECT i FROM PlaRecuEjecucion i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuEjecucion.findByEstadoReg", query = "SELECT i FROM PlaRecuEjecucion i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuEjecucion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_ejecucion")
    private Integer idPlaRecuEjecucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Size(max = 8)
    @Column(name = "hora_inicio")
    private String horaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(max = 8)
    @Column(name = "hora_fin")
    private String horaFin;
    @Basic(optional = false)
    @Lob
    @Size(min = 0, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_pla_recu_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuNovedad idNovedad;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado; // se emplea para obtiener código TM, cédula, nombres, apellidos, cargo
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
        return idPlaRecuEjecucion;
    }

    public void setIdEjecucion(Integer idPlaRecuEjecucion) {
        this.idPlaRecuEjecucion = idPlaRecuEjecucion;
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

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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

    public PlaRecuNovedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(PlaRecuNovedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public Integer getIdPlaRecuEjecucion() {
        return idPlaRecuEjecucion;
    }

    public void setIdPlaRecuEjecucion(Integer idPlaRecuEjecucion) {
        this.idPlaRecuEjecucion = idPlaRecuEjecucion;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuEjecucion)) {
            return false;
        }
        PlaRecuEjecucion other = (PlaRecuEjecucion) object;
        return !((this.idPlaRecuEjecucion == null && other.idPlaRecuEjecucion != null) || (this.idPlaRecuEjecucion != null && 
                !this.idPlaRecuEjecucion.equals(other.idPlaRecuEjecucion)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuEjecucion[ idPlaRecuEjecucion=" + idPlaRecuEjecucion + " ]";
    }
    
}
