/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author luis.lancheros
 */
@Entity
@Table(name = "pla_recu_medicina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuMedicina.findAll", query = "SELECT i FROM PlaRecuMedicina i"),
    @NamedQuery(name = "PlaRecuMedicina.findByIdPla_recu_medicina", query = "SELECT i FROM PlaRecuMedicina i WHERE i.idPlaRecuMedicina = :idPlaRecuMedicina"),
    @NamedQuery(name = "PlaRecuMedicina.findByEstadoReg", query = "SELECT i FROM PlaRecuMedicina i WHERE i.estadoReg = :estadoReg")})
public class PlaRecuMedicina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_medicina")
    private Integer idPlaRecuMedicina;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado;
    @JoinColumn(name = "id_pla_recu_conduccion", referencedColumnName = "id_pla_recu_conduccion")
    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER
    private PlaRecuConduccion idPlaRecuConduccion;
    @JoinColumn(name = "id_pla_recu_turno", referencedColumnName = "id_pla_recu_turno")
    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER
    private PlaRecuTurno idPlaRecuTurno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
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
    @ManyToOne(fetch = FetchType.EAGER)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PlaRecuMedicina() {
    }

    public Integer getIdPlaRecuMedicina() {
        return idPlaRecuMedicina;
    }

    public void setIdPlaRecuMedicina(Integer idPlaRecuMedicina) {
        this.idPlaRecuMedicina = idPlaRecuMedicina;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public PlaRecuConduccion getIdPlaRecuConduccion() {
        return idPlaRecuConduccion;
    }

    public void setIdPlaRecuConduccion(PlaRecuConduccion idPlaRecuConduccion) {
        this.idPlaRecuConduccion = idPlaRecuConduccion;
    }

    public PlaRecuTurno getIdPlaRecuTurno() {
        return idPlaRecuTurno;
    }

    public void setIdPlaRecuTurno(PlaRecuTurno idPlaRecuTurno) {
        this.idPlaRecuTurno = idPlaRecuTurno;
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
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaRecuMedicina != null ? idPlaRecuMedicina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuMedicina)) {
            return false;
        }
        PlaRecuMedicina other = (PlaRecuMedicina) object;
        if ((this.idPlaRecuMedicina == null && other.idPlaRecuMedicina != null) || (this.idPlaRecuMedicina != null && !idPlaRecuMedicina.equals(other.idPlaRecuMedicina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.Pla_recu_medicina[ id=" + idPlaRecuMedicina + " ]";
    }
    
}
