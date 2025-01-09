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
 * Permite hacer persistencia con la tabla bienestar 
 * @author Omar.beltran
 */
@Entity
@Table(name = "pla_recu_bienestar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuBienestar.findAll", query = "SELECT i FROM PlaRecuBienestar i"),
    @NamedQuery(name = "PlaRecuBienestar.findByIdBienestar", query = "SELECT i FROM PlaRecuBienestar i WHERE i.idPlaRecuBienestar = :idPlaRecuBienestar"),
    @NamedQuery(name = "PlaRecuBienestar.findByVigencia", query = "SELECT i FROM PlaRecuBienestar i WHERE i.vigencia = :vigencia"),
    @NamedQuery(name = "PlaRecuBienestar.findByCreado", query = "SELECT i FROM PlaRecuBienestar i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuBienestar.findByModificado", query = "SELECT i FROM PlaRecuBienestar i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuBienestar.findByEstadoReg", query = "SELECT i FROM PlaRecuBienestar i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuBienestar implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_bienestar")
    private Integer idPlaRecuBienestar;
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
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "vigencia")
    private String vigencia;
    @JoinColumn(name = "id_pla_recu_motivo", referencedColumnName = "id_pla_recu_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuMotivo idPlaRecuMotivo;
    @JoinColumn(name = "id_pla_recu_turno", referencedColumnName = "id_pla_recu_turno")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuTurno idPlaRecuTurno;
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
        return idPlaRecuBienestar;
    }

    public void setIdEjecucion(Integer idPlaRecuBienestar) {
        this.idPlaRecuBienestar = idPlaRecuBienestar;
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

    public PlaRecuMotivo getIdPlaRecuMotivo() {
        return idPlaRecuMotivo;
    }

    public void setIdPlaRecuMotivo(PlaRecuMotivo idPlaRecuMotivo) {
        this.idPlaRecuMotivo = idPlaRecuMotivo;
    }

    public PlaRecuTurno getIdPlaRecuTurno() {
        return idPlaRecuTurno;
    }

    public void setIdPlaRecuTurno(PlaRecuTurno idPlaRecuTurno) {
        this.idPlaRecuTurno = idPlaRecuTurno;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
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

    public Integer getIdPlaRecuBienestar() {
        return idPlaRecuBienestar;
    }

    public void setIdPlaRecuBienestar(Integer idPlaRecuBienestar) {
        this.idPlaRecuBienestar = idPlaRecuBienestar;
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
        if (!(object instanceof PlaRecuBienestar)) {
            return false;
        }
        PlaRecuBienestar other = (PlaRecuBienestar) object;
        return !((this.idPlaRecuBienestar == null && other.idPlaRecuBienestar != null) || (this.idPlaRecuBienestar != null && 
                !this.idPlaRecuBienestar.equals(other.idPlaRecuBienestar)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuBienestar[ idPlaRecuBienestar=" + idPlaRecuBienestar + " ]";
    }
    
}
