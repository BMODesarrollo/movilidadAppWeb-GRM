package com.movilidad.model.planificacion_recursos;

import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.planificacion_recursos.PlaRecuCategoria;
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
@Table(name = "pla_recu_entrega_operador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuEntregaOperador.findAll", query = "SELECT i FROM PlaRecuEntregaOperador i"),
    @NamedQuery(name = "PlaRecuEntregaOperador.findByIdBienestar", query = "SELECT i FROM PlaRecuEntregaOperador i WHERE i.idPlaRecuEntregaOperador = :idPlaRecuEntregaOperador"),
    @NamedQuery(name = "PlaRecuEntregaOperador.findByCategoria", query = "SELECT i FROM PlaRecuEntregaOperador i WHERE i.idPlaRecuCategoria = :idPlaRecuCategoria"),
    @NamedQuery(name = "PlaRecuEntregaOperador.findByCreado", query = "SELECT i FROM PlaRecuEntregaOperador i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuEntregaOperador.findByModificado", query = "SELECT i FROM PlaRecuEntregaOperador i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuEntregaOperador.findByEstadoReg", query = "SELECT i FROM PlaRecuEntregaOperador i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuEntregaOperador implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_entrega_operador")
    private Integer idPlaRecuEntregaOperador;
    @JoinColumn(name = "id_pla_recu_categoria", referencedColumnName = "id_pla_recu_categoria")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuCategoria idPlaRecuCategoria;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado; // se emplea para obtiener código TM, cédula, nombres, apellidos, cargo
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_entrega_programacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntregaProgramacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ascenso_nomina")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAscensoNomina;
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

    public PlaRecuCategoria getIdPlaRecuCategoria() {
        return idPlaRecuCategoria;
    }

    public void setIdPlaRecuCategoria(PlaRecuCategoria idPlaRecuCategoria) {
        this.idPlaRecuCategoria = idPlaRecuCategoria;
    }

    public Date getFechaEntregaProgramacion() {
        return fechaEntregaProgramacion;
    }

    public void setFechaEntregaProgramacion(Date fechaEntregaProgramacion) {
        this.fechaEntregaProgramacion = fechaEntregaProgramacion;
    }

    public Date getFechaAscensoNomina() {
        return fechaAscensoNomina;
    }

    public void setFechaAscensoNomina(Date fechaAscensoNomina) {
        this.fechaAscensoNomina = fechaAscensoNomina;
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

    public Integer getIdPlaRecuEntregaOperador() {
        return idPlaRecuEntregaOperador;
    }

    public void setIdPlaRecuEntregaOperador(Integer idPlaRecuEntregaOperador) {
        this.idPlaRecuEntregaOperador = idPlaRecuEntregaOperador;
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
        if (!(object instanceof PlaRecuEntregaOperador)) {
            return false;
        }
        PlaRecuEntregaOperador other = (PlaRecuEntregaOperador) object;
        return !((this.idPlaRecuEntregaOperador == null && other.idPlaRecuEntregaOperador != null) || (this.idPlaRecuEntregaOperador != null && 
                !this.idPlaRecuEntregaOperador.equals(other.idPlaRecuEntregaOperador)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuEntregaOperador[ idPlaRecuEntregaOperador=" + idPlaRecuEntregaOperador + " ]";
    }
    
}
