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
 * Permite hacer persistencia con la tabla formación y desarrollo ascenso padron
 * @author Omar.beltran
 */
@Entity
@Table(name = "pla_recu_ascenso_padron")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuAscensoPadron.findAll", query = "SELECT i FROM PlaRecuAscensoPadron i"),
    @NamedQuery(name = "PlaRecuAscensoPadron.findByIdBienestar", query = "SELECT i FROM PlaRecuAscensoPadron i WHERE i.idPlaRecuAscensoPadron = :idPlaRecuAscensoPadron"),
    @NamedQuery(name = "PlaRecuAscensoPadron.findByEstadoReg", query = "SELECT i FROM PlaRecuAscensoPadron i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuAscensoPadron implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_ascenso_padron")
    private Integer idPlaRecuAscensoPadron;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado; // se emplea para obtiener código TM, cédula, nombres, apellidos, cargo
    @Column(name = "estado_operaciones")
    private Integer estadoOperaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_operaciones")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOperaciones;
    @Size(max = 15)
    @Column(name = "username_operaciones")
    private String usernameOperaciones;
    @Column(name = "estado_segop")
    private Integer estadoSegop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_segop")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSegop;
    @Size(max = 15)
    @Column(name = "username_segop")
    private String usernameSegop;
    @Column(name = "estado_fyd")
    private Integer estadoFYD;//estado formación y desarrollo
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fyd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFYD;// fecha formación y desarrollo
    @Size(max = 15)
    @Column(name = "username_fyd")
    private String usernameFYD;
    @Column(name = "estado_programacion")
    private Integer estadoProgramacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_programacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProgramacion;
    @Size(max = 15)
    @Column(name = "username_programacion")
    private String usernameProgramacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ascenso_operaciones")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAscensoOperaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ascenso_nomina")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAscensoNomina;
    @Column(name = "observacion")
    private String observacion;
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
        return idPlaRecuAscensoPadron;
    }

    public void setIdEjecucion(Integer idPlaRecuAscensoPadron) {
        this.idPlaRecuAscensoPadron = idPlaRecuAscensoPadron;
    }

    public Integer getIdPlaRecuAscensoPadron() {
        return idPlaRecuAscensoPadron;
    }

    public void setIdPlaRecuAscensoPadron(Integer idPlaRecuAscensoPadron) {
        this.idPlaRecuAscensoPadron = idPlaRecuAscensoPadron;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Date getFechaSegop() {
        return fechaSegop;
    }

    public void setFechaSegop(Date fechaSegop) {
        this.fechaSegop = fechaSegop;
    }

    public Integer getEstadoOperaciones() {
        return estadoOperaciones;
    }

    public void setEstadoOperaciones(Integer estadoOperaciones) {
        this.estadoOperaciones = estadoOperaciones;
    }

    public Date getFechaOperaciones() {
        return fechaOperaciones;
    }

    public void setFechaOperaciones(Date fechaOperaciones) {
        this.fechaOperaciones = fechaOperaciones;
    }

    public Integer getEstadoSegop() {
        return estadoSegop;
    }

    public void setEstadoSegop(Integer estadoSegop) {
        this.estadoSegop = estadoSegop;
    }

    public Integer getEstadoFYD() {
        return estadoFYD;
    }

    public void setEstadoFYD(Integer estadoFYD) {
        this.estadoFYD = estadoFYD;
    }

    public Date getFechaFYD() {
        return fechaFYD;
    }

    public void setFechaFYD(Date fechaFYD) {
        this.fechaFYD = fechaFYD;
    }

    public Integer getEstadoProgramacion() {
        return estadoProgramacion;
    }

    public void setEstadoProgramacion(Integer estadoProgramacion) {
        this.estadoProgramacion = estadoProgramacion;
    }

    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public Date getFechaAscensoOperaciones() {
        return fechaAscensoOperaciones;
    }

    public void setFechaAscensoOperaciones(Date fechaAscensoOperaciones) {
        this.fechaAscensoOperaciones = fechaAscensoOperaciones;
    }

    public Date getFechaAscensoNomina() {
        return fechaAscensoNomina;
    }

    public void setFechaAscensoNomina(Date fechaAscensoNomina) {
        this.fechaAscensoNomina = fechaAscensoNomina;
    }

    public String getUsernameOperaciones() {
        return usernameOperaciones;
    }

    public void setUsernameOperaciones(String usernameOperaciones) {
        this.usernameOperaciones = usernameOperaciones;
    }

    public String getUsernameSegop() {
        return usernameSegop;
    }

    public void setUsernameSegop(String usernameSegop) {
        this.usernameSegop = usernameSegop;
    }

    public String getUsernameFYD() {
        return usernameFYD;
    }

    public void setUsernameFYD(String usernameFYD) {
        this.usernameFYD = usernameFYD;
    }

    public String getUsernameProgramacion() {
        return usernameProgramacion;
    }

    public void setUsernameProgramacion(String usernameProgramacion) {
        this.usernameProgramacion = usernameProgramacion;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuAscensoPadron)) {
            return false;
        }
        PlaRecuAscensoPadron other = (PlaRecuAscensoPadron) object;
        return !((this.idPlaRecuAscensoPadron == null && other.idPlaRecuAscensoPadron != null) || (this.idPlaRecuAscensoPadron != null && 
                !this.idPlaRecuAscensoPadron.equals(other.idPlaRecuAscensoPadron)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuAscensoPadron[ idPlaRecuAscensoPadron=" + idPlaRecuAscensoPadron + " ]";
    }
    
}
