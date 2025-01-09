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
@Table(name = "vehiculo_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoDocumentos.findAll", query = "SELECT v FROM VehiculoDocumentos v")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByIdVehiculoDocumento", query = "SELECT v FROM VehiculoDocumentos v WHERE v.idVehiculoDocumento = :idVehiculoDocumento")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByVigenteDesde", query = "SELECT v FROM VehiculoDocumentos v WHERE v.vigenteDesde = :vigenteDesde")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByVigenteHasta", query = "SELECT v FROM VehiculoDocumentos v WHERE v.vigenteHasta = :vigenteHasta")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByActivo", query = "SELECT v FROM VehiculoDocumentos v WHERE v.activo = :activo")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByPathDocumento", query = "SELECT v FROM VehiculoDocumentos v WHERE v.pathDocumento = :pathDocumento")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByUsuario", query = "SELECT v FROM VehiculoDocumentos v WHERE v.usuario = :usuario")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByCreado", query = "SELECT v FROM VehiculoDocumentos v WHERE v.creado = :creado")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByModificado", query = "SELECT v FROM VehiculoDocumentos v WHERE v.modificado = :modificado")
    ,
    @NamedQuery(name = "VehiculoDocumentos.findByEstadoReg", query = "SELECT v FROM VehiculoDocumentos v WHERE v.estadoReg = :estadoReg")})
public class VehiculoDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_documento")
    private Integer idVehiculoDocumento;
    @Column(name = "vigente_desde")
    @Temporal(TemporalType.DATE)
    private Date vigenteDesde;
    @Column(name = "vigente_hasta")
    @Temporal(TemporalType.DATE)
    private Date vigenteHasta;
    @Size(max = 255)
    @Column(name = "path_documento")
    private String pathDocumento;
    @Column(name = "activo")
    private int activo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 45)
    @Column(name = "nro_Licencia")
    private String nro_Licencia;
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
    @JoinColumn(name = "id_vehiculo_tipo_documento", referencedColumnName = "id_vehiculo_tipo_documento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipoDocumentos idVehiculoTipoDocumento;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;

    public VehiculoDocumentos() {
    }

    public VehiculoDocumentos(Integer idVehiculoDocumento) {
        this.idVehiculoDocumento = idVehiculoDocumento;
    }

    public VehiculoDocumentos(Integer idVehiculoDocumento, String usuario, Date creado, int estadoReg) {
        this.idVehiculoDocumento = idVehiculoDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoDocumento() {
        return idVehiculoDocumento;
    }

    public void setIdVehiculoDocumento(Integer idVehiculoDocumento) {
        this.idVehiculoDocumento = idVehiculoDocumento;
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

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNro_Licencia() {
        return nro_Licencia;
    }

    public void setNro_Licencia(String nro_Licencia) {
        this.nro_Licencia = nro_Licencia;
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

    public VehiculoTipoDocumentos getIdVehiculoTipoDocumento() {
        return idVehiculoTipoDocumento;
    }

    public void setIdVehiculoTipoDocumento(VehiculoTipoDocumentos idVehiculoTipoDocumento) {
        this.idVehiculoTipoDocumento = idVehiculoTipoDocumento;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoDocumento != null ? idVehiculoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoDocumentos)) {
            return false;
        }
        VehiculoDocumentos other = (VehiculoDocumentos) object;
        if ((this.idVehiculoDocumento == null && other.idVehiculoDocumento != null) || (this.idVehiculoDocumento != null && !this.idVehiculoDocumento.equals(other.idVehiculoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoDocumentos[ idVehiculoDocumento=" + idVehiculoDocumento + " ]";
    }

}
