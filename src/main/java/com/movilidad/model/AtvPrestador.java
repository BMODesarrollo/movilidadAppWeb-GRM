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
 * @author solucionesit
 */
@Entity
@Table(name = "atv_prestador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvPrestador.findAll", query = "SELECT a FROM AtvPrestador a")
    , @NamedQuery(name = "AtvPrestador.findByIdAtvPrestador", query = "SELECT a FROM AtvPrestador a WHERE a.idAtvPrestador = :idAtvPrestador")
    , @NamedQuery(name = "AtvPrestador.findByNombre", query = "SELECT a FROM AtvPrestador a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "AtvPrestador.findByActivo", query = "SELECT a FROM AtvPrestador a WHERE a.activo = :activo")
    , @NamedQuery(name = "AtvPrestador.findByDescripcion", query = "SELECT a FROM AtvPrestador a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "AtvPrestador.findByUsername", query = "SELECT a FROM AtvPrestador a WHERE a.username = :username")
    , @NamedQuery(name = "AtvPrestador.findByCreado", query = "SELECT a FROM AtvPrestador a WHERE a.creado = :creado")
    , @NamedQuery(name = "AtvPrestador.findByModificado", query = "SELECT a FROM AtvPrestador a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AtvPrestador.findByEstadoReg", query = "SELECT a FROM AtvPrestador a WHERE a.estadoReg = :estadoReg")})
public class AtvPrestador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_prestador")
    private Integer idAtvPrestador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private int activo;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAtvPrestador", fetch = FetchType.LAZY)
    private List<AtvCostoServicio> atvCostoServicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAtvPrestador", fetch = FetchType.LAZY)
    private List<AtvVehiculosAtencion> atvVehiculosAtencionList;

    @Column(name = "correo")
    private String correo;
    
    public AtvPrestador() {
    }

    public AtvPrestador(Integer idAtvPrestador) {
        this.idAtvPrestador = idAtvPrestador;
    }

    public AtvPrestador(Integer idAtvPrestador, String nombre, int activo) {
        this.idAtvPrestador = idAtvPrestador;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Integer getIdAtvPrestador() {
        return idAtvPrestador;
    }

    public void setIdAtvPrestador(Integer idAtvPrestador) {
        this.idAtvPrestador = idAtvPrestador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }
    

    @XmlTransient
    public List<AtvCostoServicio> getAtvCostoServicioList() {
        return atvCostoServicioList;
    }

    public void setAtvCostoServicioList(List<AtvCostoServicio> atvCostoServicioList) {
        this.atvCostoServicioList = atvCostoServicioList;
    }

    @XmlTransient
    public List<AtvVehiculosAtencion> getAtvVehiculosAtencionList() {
        return atvVehiculosAtencionList;
    }

    public void setAtvVehiculosAtencionList(List<AtvVehiculosAtencion> atvVehiculosAtencionList) {
        this.atvVehiculosAtencionList = atvVehiculosAtencionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtvPrestador != null ? idAtvPrestador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvPrestador)) {
            return false;
        }
        AtvPrestador other = (AtvPrestador) object;
        if ((this.idAtvPrestador == null && other.idAtvPrestador != null) || (this.idAtvPrestador != null && !this.idAtvPrestador.equals(other.idAtvPrestador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvPrestador[ idAtvPrestador=" + idAtvPrestador + " ]";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
