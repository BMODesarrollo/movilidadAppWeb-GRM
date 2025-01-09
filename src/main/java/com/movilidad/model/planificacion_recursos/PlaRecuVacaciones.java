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
 * Permite hacer persistencia con la tabla vacaciones 
 * @author Omar.beltran
 */
@Entity
@Table(name = "pla_recu_vacaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuVacaciones.findAll", query = "SELECT i FROM PlaRecuVacaciones i"),
    @NamedQuery(name = "PlaRecuVacaciones.findByIdVacaciones", query = "SELECT i FROM PlaRecuVacaciones i WHERE i.idPlaRecuVacaciones = :idPlaRecuVacaciones"),
    @NamedQuery(name = "PlaRecuVacaciones.findByEmpleado", query = "SELECT i FROM PlaRecuVacaciones i WHERE i.empleado = :empleado"),
    @NamedQuery(name = "PlaRecuVacaciones.findByUserCreate", query = "SELECT i FROM PlaRecuVacaciones i WHERE i.usernameCreate = :usernameCreate"),
    @NamedQuery(name = "PlaRecuVacaciones.findByUserEdit", query = "SELECT i FROM PlaRecuVacaciones i WHERE i.usernameEdit = :usernameEdit"),
    @NamedQuery(name = "PlaRecuVacaciones.findByEstadoReg", query = "SELECT i FROM PlaRecuVacaciones i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuVacaciones implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_vacaciones")
    private Integer idPlaRecuVacaciones;
    @Basic(optional = false)
    @Lob
    @Size(min = 0, max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_pla_recu_grupo_vacaciones", referencedColumnName = "id_pla_recu_grupo_vacaciones")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuGrupoVacaciones idGrupoVacaciones;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado; // se emplea para obtiener código TM, cédula, nombres, apellidos, cargo
    @Column(name = "pasivo_vacacional")
    private Integer pasivoVacacional;
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
    
    public Integer getIdVacaciones() {
        return idPlaRecuVacaciones;
    }

    public void setIdVacaciones(Integer idPlaRecuVacaciones) {
        this.idPlaRecuVacaciones = idPlaRecuVacaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String descripcion) {
        this.observaciones = descripcion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Integer getPasivoVacacional() {
        return pasivoVacacional;
    }

    public void setPasivoVacacional(Integer pasivoVacacional) {
        this.pasivoVacacional = pasivoVacacional;
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

    public PlaRecuGrupoVacaciones getIdGrupoVacaciones() {
        return idGrupoVacaciones;
    }

    public void setIdGrupoVacaciones(PlaRecuGrupoVacaciones idGrupoVacaciones) {
        this.idGrupoVacaciones = idGrupoVacaciones;
    }

    public Integer getIdPlaRecuVacaciones() {
        return idPlaRecuVacaciones;
    }

    public void setIdPlaRecuVacaciones(Integer idPlaRecuVacaciones) {
        this.idPlaRecuVacaciones = idPlaRecuVacaciones;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuVacaciones)) {
            return false;
        }
        PlaRecuVacaciones other = (PlaRecuVacaciones) object;
        return !((this.idPlaRecuVacaciones == null && other.idPlaRecuVacaciones != null) || (this.idPlaRecuVacaciones != null && 
                !this.idPlaRecuVacaciones.equals(other.idPlaRecuVacaciones)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuVacaciones[ idPlaRecuVacaciones=" + idPlaRecuVacaciones + " ]";
    }
    
}
