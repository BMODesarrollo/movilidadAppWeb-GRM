/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "empleado_tipo_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoTipoDocumentos.findAll", query = "SELECT e FROM EmpleadoTipoDocumentos e")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByIdEmpleadoTipoDocumento", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.idEmpleadoTipoDocumento = :idEmpleadoTipoDocumento")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByNombreTipoDocumento", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.nombreTipoDocumento = :nombreTipoDocumento")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByDescripcionTipoDocumento", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.descripcionTipoDocumento = :descripcionTipoDocumento")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByRadicado", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.radicado = :radicado")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByObligatorio", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.obligatorio = :obligatorio")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByVencimiento", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.vencimiento = :vencimiento")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByUsername", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.username = :username")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByCreado", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.creado = :creado")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByModificado", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.modificado = :modificado")
    , @NamedQuery(name = "EmpleadoTipoDocumentos.findByEstadoReg", query = "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.estadoReg = :estadoReg")})
public class EmpleadoTipoDocumentos implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoTipoDocumento", fetch = FetchType.LAZY)
    private List<DocumentoPorCargo> documentoPorCargoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_tipo_documento")
    private Integer idEmpleadoTipoDocumento;
    @Size(max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_documento")
    private String nombreTipoDocumento;
    @Size(max = 100)
    @Column(name = "descripcion_tipo_documento")
    private String descripcionTipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "radicado")
    private int radicado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "obligatorio")
    private int obligatorio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vencimiento")
    private int vencimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoTipoDocumento", fetch = FetchType.LAZY)
    private List<EmpleadoDocumentos> empleadoDocumentosList;

    public EmpleadoTipoDocumentos() {
    }

    public EmpleadoTipoDocumentos(Integer idEmpleadoTipoDocumento) {
        this.idEmpleadoTipoDocumento = idEmpleadoTipoDocumento;
    }

    public EmpleadoTipoDocumentos(Integer idEmpleadoTipoDocumento, String nombreTipoDocumento, int radicado, int obligatorio, int vencimiento, String username, int estadoReg) {
        this.idEmpleadoTipoDocumento = idEmpleadoTipoDocumento;
        this.nombreTipoDocumento = nombreTipoDocumento;
        this.radicado = radicado;
        this.obligatorio = obligatorio;
        this.vencimiento = vencimiento;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdEmpleadoTipoDocumento() {
        return idEmpleadoTipoDocumento;
    }

    public void setIdEmpleadoTipoDocumento(Integer idEmpleadoTipoDocumento) {
        this.idEmpleadoTipoDocumento = idEmpleadoTipoDocumento;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    public String getDescripcionTipoDocumento() {
        return descripcionTipoDocumento;
    }

    public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
        this.descripcionTipoDocumento = descripcionTipoDocumento;
    }

    public int getRadicado() {
        return radicado;
    }

    public void setRadicado(int radicado) {
        this.radicado = radicado;
    }

    public int getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(int obligatorio) {
        this.obligatorio = obligatorio;
    }

    public int getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(int vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<EmpleadoDocumentos> getEmpleadoDocumentosList() {
        return empleadoDocumentosList;
    }

    public void setEmpleadoDocumentosList(List<EmpleadoDocumentos> empleadoDocumentosList) {
        this.empleadoDocumentosList = empleadoDocumentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleadoTipoDocumento != null ? idEmpleadoTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoTipoDocumentos)) {
            return false;
        }
        EmpleadoTipoDocumentos other = (EmpleadoTipoDocumentos) object;
        if ((this.idEmpleadoTipoDocumento == null && other.idEmpleadoTipoDocumento != null) || (this.idEmpleadoTipoDocumento != null && !this.idEmpleadoTipoDocumento.equals(other.idEmpleadoTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.EmpleadoTipoDocumentos[ idEmpleadoTipoDocumento=" + idEmpleadoTipoDocumento + " ]";
    }


    @XmlTransient
    public List<DocumentoPorCargo> getDocumentoPorCargoList() {
        return documentoPorCargoList;
    }

    public void setDocumentoPorCargoList(List<DocumentoPorCargo> documentoPorCargoList) {
        this.documentoPorCargoList = documentoPorCargoList;
    }
    


}
