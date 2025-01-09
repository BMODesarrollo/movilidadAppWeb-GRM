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
 * @author Omar Beltrán
 */
@Entity
@Table(name = "marcacion_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarcacionDocumentos.findAll", query = "SELECT e FROM MarcacionDocumentos e")
    , @NamedQuery(name = "MarcacionDocumentos.findByIdMarcacionDocumento", query = "SELECT e FROM MarcacionDocumentos e WHERE e.idMarcacionDocumento = :idMarcacionDocumento")
    , @NamedQuery(name = "MarcacionDocumentos.findByPathDocumento", query = "SELECT e FROM MarcacionDocumentos e WHERE e.pathDocumento = :pathDocumento")
    , @NamedQuery(name = "MarcacionDocumentos.findByDescripcion", query = "SELECT e FROM MarcacionDocumentos e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "MarcacionDocumentos.findByFechaRadicado", query = "SELECT e FROM MarcacionDocumentos e WHERE e.fechaRadicado = :fechaRadicado")
    , @NamedQuery(name = "MarcacionDocumentos.findByUsuario", query = "SELECT e FROM MarcacionDocumentos e WHERE e.usuario = :usuario")
    , @NamedQuery(name = "MarcacionDocumentos.findByCreado", query = "SELECT e FROM MarcacionDocumentos e WHERE e.creado = :creado")
    , @NamedQuery(name = "MarcacionDocumentos.findByModificado", query = "SELECT e FROM MarcacionDocumentos e WHERE e.modificado = :modificado")
    , @NamedQuery(name = "MarcacionDocumentos.findByEstadoReg", query = "SELECT e FROM MarcacionDocumentos e WHERE e.estadoReg = :estadoReg")})
public class MarcacionDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_marcacion_documento")
    private Integer idMarcacionDocumento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 150)
    @Column(name = "path_documento")
    private String pathDocumento;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_radicado")
    @Temporal(TemporalType.DATE)
    private Date fechaRadicado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleado;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public MarcacionDocumentos() {
    }

    public MarcacionDocumentos(Integer idMarcacionDocumento) {
        this.idMarcacionDocumento = idMarcacionDocumento;
    }

    public MarcacionDocumentos(Integer idMarcacionDocumento, String usuario, Date creado, int estadoReg) {
        this.idMarcacionDocumento = idMarcacionDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMarcacionDocumento() {
        return idMarcacionDocumento;
    }

    public void setIdMarcacionDocumento(Integer idMarcacionDocumento) {
        this.idMarcacionDocumento = idMarcacionDocumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRadicado() {
        return fechaRadicado;
    }

    public void setFechaRadicado(Date fechaRadicado) {
        this.fechaRadicado = fechaRadicado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
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
        hash += (idMarcacionDocumento != null ? idMarcacionDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarcacionDocumentos)) {
            return false;
        }
        MarcacionDocumentos other = (MarcacionDocumentos) object;
        if ((this.idMarcacionDocumento == null && other.idMarcacionDocumento != null) || (this.idMarcacionDocumento != null && !this.idMarcacionDocumento.equals(other.idMarcacionDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MarcacionDocumentos[ idMarcacionDocumento=" + idMarcacionDocumento + " ]";
    }

}
