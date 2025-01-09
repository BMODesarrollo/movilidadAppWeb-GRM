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
@Table(name = "atv_vehiculos_atencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvVehiculosAtencion.findAll", query = "SELECT a FROM AtvVehiculosAtencion a")
    , @NamedQuery(name = "AtvVehiculosAtencion.findByIdAtvVehiculosAtencion", query = "SELECT a FROM AtvVehiculosAtencion a WHERE a.idAtvVehiculosAtencion = :idAtvVehiculosAtencion")
    , @NamedQuery(name = "AtvVehiculosAtencion.findByPlaca", query = "SELECT a FROM AtvVehiculosAtencion a WHERE a.placa = :placa")
    , @NamedQuery(name = "AtvVehiculosAtencion.findByActivo", query = "SELECT a FROM AtvVehiculosAtencion a WHERE a.activo = :activo")
    , @NamedQuery(name = "AtvVehiculosAtencion.findByDescripcion", query = "SELECT a FROM AtvVehiculosAtencion a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "AtvVehiculosAtencion.findByUsername", query = "SELECT a FROM AtvVehiculosAtencion a WHERE a.username = :username")
    , @NamedQuery(name = "AtvVehiculosAtencion.findByCreado", query = "SELECT a FROM AtvVehiculosAtencion a WHERE a.creado = :creado")
    , @NamedQuery(name = "AtvVehiculosAtencion.findByModificado", query = "SELECT a FROM AtvVehiculosAtencion a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AtvVehiculosAtencion.findByEstadoReg", query = "SELECT a FROM AtvVehiculosAtencion a WHERE a.estadoReg = :estadoReg")})
public class AtvVehiculosAtencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_vehiculos_atencion")
    private Integer idAtvVehiculosAtencion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "placa")
    private String placa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private int activo;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_atv_prestador", referencedColumnName = "id_atv_prestador")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AtvPrestador idAtvPrestador;
    @JoinColumn(name = "id_atv_tipo_atencion", referencedColumnName = "id_atv_tipo_atencion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AtvTipoAtencion idAtvTipoAtencion;
    
    //
    @Column(name = "contrasena_usuario")
    private String contrasenaUsuario;
    @Column(name = "telefono")
    private String telefono;

    public AtvVehiculosAtencion() {
    }

    public AtvVehiculosAtencion(Integer idAtvVehiculosAtencion) {
        this.idAtvVehiculosAtencion = idAtvVehiculosAtencion;
    }

    public AtvVehiculosAtencion(Integer idAtvVehiculosAtencion, String placa, int activo) {
        this.idAtvVehiculosAtencion = idAtvVehiculosAtencion;
        this.placa = placa;
        this.activo = activo;
    }

    public Integer getIdAtvVehiculosAtencion() {
        return idAtvVehiculosAtencion;
    }

    public void setIdAtvVehiculosAtencion(Integer idAtvVehiculosAtencion) {
        this.idAtvVehiculosAtencion = idAtvVehiculosAtencion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public AtvPrestador getIdAtvPrestador() {
        return idAtvPrestador;
    }

    public void setIdAtvPrestador(AtvPrestador idAtvPrestador) {
        this.idAtvPrestador = idAtvPrestador;
    }

    public AtvTipoAtencion getIdAtvTipoAtencion() {
        return idAtvTipoAtencion;
    }

    public void setIdAtvTipoAtencion(AtvTipoAtencion idAtvTipoAtencion) {
        this.idAtvTipoAtencion = idAtvTipoAtencion;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtvVehiculosAtencion != null ? idAtvVehiculosAtencion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvVehiculosAtencion)) {
            return false;
        }
        AtvVehiculosAtencion other = (AtvVehiculosAtencion) object;
        if ((this.idAtvVehiculosAtencion == null && other.idAtvVehiculosAtencion != null) || (this.idAtvVehiculosAtencion != null && !this.idAtvVehiculosAtencion.equals(other.idAtvVehiculosAtencion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvVehiculosAtencion[ idAtvVehiculosAtencion=" + idAtvVehiculosAtencion + " ]";
    }
    
}
