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
@Table(name = "vehiculo_tipo_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoTipoDocumentos.findAll", query = "SELECT v FROM VehiculoTipoDocumentos v")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByIdVehiculoTipoDocumento", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.idVehiculoTipoDocumento = :idVehiculoTipoDocumento")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByNombreTipoDocumento", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.nombreTipoDocumento = :nombreTipoDocumento")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByDescripcionTipoDocumento", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.descripcionTipoDocumento = :descripcionTipoDocumento")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByObligatorio", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.obligatorio = :obligatorio")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByVencimiento", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.vencimiento = :vencimiento")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByUsername", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.username = :username")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByCreado", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.creado = :creado")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByModificado", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.modificado = :modificado")
    , @NamedQuery(name = "VehiculoTipoDocumentos.findByEstadoReg", query = "SELECT v FROM VehiculoTipoDocumentos v WHERE v.estadoReg = :estadoReg")})
public class VehiculoTipoDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_tipo_documento")
    private Integer idVehiculoTipoDocumento;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculoTipoDocumento", fetch = FetchType.LAZY)
    private List<VehiculoDocumentos> vehiculoDocumentosList;

    public VehiculoTipoDocumentos() {
    }

    public VehiculoTipoDocumentos(Integer idVehiculoTipoDocumento) {
        this.idVehiculoTipoDocumento = idVehiculoTipoDocumento;
    }

    public VehiculoTipoDocumentos(Integer idVehiculoTipoDocumento, String nombreTipoDocumento, int obligatorio, int vencimiento, String username, int estadoReg) {
        this.idVehiculoTipoDocumento = idVehiculoTipoDocumento;
        this.nombreTipoDocumento = nombreTipoDocumento;
        this.obligatorio = obligatorio;
        this.vencimiento = vencimiento;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoTipoDocumento() {
        return idVehiculoTipoDocumento;
    }

    public void setIdVehiculoTipoDocumento(Integer idVehiculoTipoDocumento) {
        this.idVehiculoTipoDocumento = idVehiculoTipoDocumento;
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
    public List<VehiculoDocumentos> getVehiculoDocumentosList() {
        return vehiculoDocumentosList;
    }

    public void setVehiculoDocumentosList(List<VehiculoDocumentos> vehiculoDocumentosList) {
        this.vehiculoDocumentosList = vehiculoDocumentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoTipoDocumento != null ? idVehiculoTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoTipoDocumentos)) {
            return false;
        }
        VehiculoTipoDocumentos other = (VehiculoTipoDocumentos) object;
        if ((this.idVehiculoTipoDocumento == null && other.idVehiculoTipoDocumento != null) || (this.idVehiculoTipoDocumento != null && !this.idVehiculoTipoDocumento.equals(other.idVehiculoTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoTipoDocumentos[ idVehiculoTipoDocumento=" + idVehiculoTipoDocumento + " ]";
    }

}
