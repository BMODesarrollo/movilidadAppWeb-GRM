/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author HP
 */
@Entity
@Table(name = "empleado_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoDocumentos.findAll", query = "SELECT e FROM EmpleadoDocumentos e")
    , @NamedQuery(name = "EmpleadoDocumentos.findByIdEmpleadoDocumento", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.idEmpleadoDocumento = :idEmpleadoDocumento")
    , @NamedQuery(name = "EmpleadoDocumentos.findByVigenteDesde", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.vigenteDesde = :vigenteDesde")
    , @NamedQuery(name = "EmpleadoDocumentos.findByVigenteHasta", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.vigenteHasta = :vigenteHasta")
    , @NamedQuery(name = "EmpleadoDocumentos.findByPathDocumento", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.pathDocumento = :pathDocumento")
    , @NamedQuery(name = "EmpleadoDocumentos.findByRadicado", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.radicado = :radicado")
    , @NamedQuery(name = "EmpleadoDocumentos.findByFechaRadicado", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.fechaRadicado = :fechaRadicado")
    , @NamedQuery(name = "EmpleadoDocumentos.findByUsuario", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.usuario = :usuario")
    , @NamedQuery(name = "EmpleadoDocumentos.findByCreado", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.creado = :creado")
    , @NamedQuery(name = "EmpleadoDocumentos.findByModificado", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.modificado = :modificado")
    , @NamedQuery(name = "EmpleadoDocumentos.findByEstadoReg", query = "SELECT e FROM EmpleadoDocumentos e WHERE e.estadoReg = :estadoReg")})
public class EmpleadoDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_documento")
    private Integer idEmpleadoDocumento;
    @Column(name = "vigente_desde")
    @Temporal(TemporalType.DATE)
    private Date vigenteDesde;
    @Column(name = "vigente_hasta")
    @Temporal(TemporalType.DATE)
    private Date vigenteHasta;
    @Size(max = 150)
    @Column(name = "path_documento")
    private String pathDocumento;
    @Size(max = 20)
    @Column(name = "radicado")
    private String radicado;
    @Column(name = "fecha_radicado")
    @Temporal(TemporalType.DATE)
    private Date fechaRadicado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "numero")
    private String numero;
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
    @JoinColumn(name = "id_empleado_tipo_documento", referencedColumnName = "id_empleado_tipo_documento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoDocumentos idEmpleadoTipoDocumento;
    @Column(name = "activo")
    private boolean activo;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public EmpleadoDocumentos() {
    }

    public EmpleadoDocumentos(Integer idEmpleadoDocumento) {
        this.idEmpleadoDocumento = idEmpleadoDocumento;
    }

    public EmpleadoDocumentos(Integer idEmpleadoDocumento, String usuario, Date creado, int estadoReg) {
        this.idEmpleadoDocumento = idEmpleadoDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdEmpleadoDocumento() {
        return idEmpleadoDocumento;
    }

    public void setIdEmpleadoDocumento(Integer idEmpleadoDocumento) {
        this.idEmpleadoDocumento = idEmpleadoDocumento;
    }

    public Date getVigenteDesde() {
        return vigenteDesde;
    }

    public void setVigenteDesde(Date vigenteDesde) {
        this.vigenteDesde = vigenteDesde;
    }

    public Date getVigenteHasta() {
        return vigenteHasta;
    }

    public void setVigenteHasta(Date vigenteHasta) {
        this.vigenteHasta = vigenteHasta;
    }

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    public String getRadicado() {
        return radicado;
    }

    public void setRadicado(String radicado) {
        this.radicado = radicado;
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

    public EmpleadoTipoDocumentos getIdEmpleadoTipoDocumento() {
        return idEmpleadoTipoDocumento;
    }

    public void setIdEmpleadoTipoDocumento(EmpleadoTipoDocumentos idEmpleadoTipoDocumento) {
        this.idEmpleadoTipoDocumento = idEmpleadoTipoDocumento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
        hash += (idEmpleadoDocumento != null ? idEmpleadoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoDocumentos)) {
            return false;
        }
        EmpleadoDocumentos other = (EmpleadoDocumentos) object;
        if ((this.idEmpleadoDocumento == null && other.idEmpleadoDocumento != null) || (this.idEmpleadoDocumento != null && !this.idEmpleadoDocumento.equals(other.idEmpleadoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.EmpleadoDocumentos[ idEmpleadoDocumento=" + idEmpleadoDocumento + " ]";
    }

}
