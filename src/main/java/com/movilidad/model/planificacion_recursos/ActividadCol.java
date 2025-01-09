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
 * Clase que permite la iteracción del sistema con la tabla
 * actividad_col_especifico es que corresponde a 'ActividadColEspecifico' del
 * módulo 'Planificación de recursos'
 *
 * @author Omar Beltrán
 */
@Entity
@Table(name = "actividad_col")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadCol.findAll", query = "SELECT i FROM ActividadCol i"),
    @NamedQuery(name = "ActividadCol.findByIdActividadCol", query = "SELECT i FROM ActividadCol i WHERE i.idActividad = :idActividad"),
    @NamedQuery(name = "ActividadCol.findByDescripcion", query = "SELECT i FROM ActividadCol i WHERE i.descripcion = :descripcion"),
    //@NamedQuery(name = "ActividadCol.findByPlaRecuLugar", query = "SELECT i FROM ActividadCol i WHERE i.idLugar = :idLugar"),
    @NamedQuery(name = "ActividadCol.findByEstado", query = "SELECT i FROM ActividadCol i WHERE i.estado = :estado"),
    @NamedQuery(name = "ActividadCol.findByCreado", query = "SELECT i FROM ActividadCol i WHERE i.creado = :creado"),
    //@NamedQuery(name = "ActividadCol.findByGopUniFun", query = "SELECT i FROM ActividadCol i WHERE i.id_gop_unidad_funcional = :id_gop_unidad_funcional"),
    @NamedQuery(name = "ActividadCol.findByModificado", query = "SELECT i FROM ActividadCol i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "ActividadCol.findByEstadoReg", query = "SELECT i FROM ActividadCol i WHERE i.estadoReg = :estadoReg")})

public class ActividadCol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad_col")
    private Integer idActividad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIni;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(max = 8)
    @Column(name = "hora_ini")
    private String horaIni;
    @Size(max = 8)
    @Column(name = "hora_fin")
    private String horaFin;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_pla_recu_lugar", referencedColumnName = "id_pla_recu_lugar")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuLugar lugar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER
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
    @ManyToOne(fetch = FetchType.EAGER)
    private GopUnidadFuncional idGopUnidadFuncional;

    public ActividadCol() {
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String horaIni) {
        this.horaIni = horaIni;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public PlaRecuLugar getPlaRecuLugar() {
        return lugar;
    }

    public void setPlaRecuLugar(PlaRecuLugar lugar) {
        this.lugar = lugar;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadCol)) {
            return false;
        }
        ActividadCol other = (ActividadCol) object;
        return !((this.idActividad == null && other.idActividad != null) || (this.idActividad != null
                && !this.idActividad.equals(other.idActividad)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.ActividadCol[ idActividad=" + idActividad + " ]";
    }

}
