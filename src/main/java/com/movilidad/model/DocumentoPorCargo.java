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
 * @author solucionesit
 */
@Entity
@Table(name = "documento_por_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoPorCargo.findAll", query = "SELECT d FROM DocumentoPorCargo d")
    , @NamedQuery(name = "DocumentoPorCargo.findByIdDocumentoPorCargo", query = "SELECT d FROM DocumentoPorCargo d WHERE d.idDocumentoPorCargo = :idDocumentoPorCargo")
    , @NamedQuery(name = "DocumentoPorCargo.findByEstadoReg", query = "SELECT d FROM DocumentoPorCargo d WHERE d.estadoReg = :estadoReg")
    , @NamedQuery(name = "DocumentoPorCargo.findByUsername", query = "SELECT d FROM DocumentoPorCargo d WHERE d.username = :username")
    , @NamedQuery(name = "DocumentoPorCargo.findByCreado", query = "SELECT d FROM DocumentoPorCargo d WHERE d.creado = :creado")
    , @NamedQuery(name = "DocumentoPorCargo.findByModificado", query = "SELECT d FROM DocumentoPorCargo d WHERE d.modificado = :modificado")})
public class DocumentoPorCargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_documento_por_cargo")
    private Integer idDocumentoPorCargo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;
    @JoinColumn(name = "id_empleado_tipo_documento", referencedColumnName = "id_empleado_tipo_documento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoDocumentos idEmpleadoTipoDocumento;

    public DocumentoPorCargo() {
    }

    public DocumentoPorCargo(Integer idDocumentoPorCargo) {
        this.idDocumentoPorCargo = idDocumentoPorCargo;
    }

    public DocumentoPorCargo(Integer idDocumentoPorCargo, int estadoReg) {
        this.idDocumentoPorCargo = idDocumentoPorCargo;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDocumentoPorCargo() {
        return idDocumentoPorCargo;
    }

    public void setIdDocumentoPorCargo(Integer idDocumentoPorCargo) {
        this.idDocumentoPorCargo = idDocumentoPorCargo;
    }

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
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

    public EmpleadoTipoCargo getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(EmpleadoTipoCargo idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    public EmpleadoTipoDocumentos getIdEmpleadoTipoDocumento() {
        return idEmpleadoTipoDocumento;
    }

    public void setIdEmpleadoTipoDocumento(EmpleadoTipoDocumentos idEmpleadoTipoDocumento) {
        this.idEmpleadoTipoDocumento = idEmpleadoTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentoPorCargo != null ? idDocumentoPorCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoPorCargo)) {
            return false;
        }
        DocumentoPorCargo other = (DocumentoPorCargo) object;
        if ((this.idDocumentoPorCargo == null && other.idDocumentoPorCargo != null) || (this.idDocumentoPorCargo != null && !this.idDocumentoPorCargo.equals(other.idDocumentoPorCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DocumentoPorCargo[ idDocumentoPorCargo=" + idDocumentoPorCargo + " ]";
    }
    
}
