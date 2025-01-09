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
 * @author HP
 */
@Entity
@Table(name = "accidente_victima")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteVictima.findAll", query = "SELECT a FROM AccidenteVictima a")
    , @NamedQuery(name = "AccidenteVictima.findByIdAccidenteVictima", query = "SELECT a FROM AccidenteVictima a WHERE a.idAccidenteVictima = :idAccidenteVictima")
    , @NamedQuery(name = "AccidenteVictima.findByCedula", query = "SELECT a FROM AccidenteVictima a WHERE a.cedula = :cedula")
    , @NamedQuery(name = "AccidenteVictima.findByNombres", query = "SELECT a FROM AccidenteVictima a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccidenteVictima.findByApellidos", query = "SELECT a FROM AccidenteVictima a WHERE a.apellidos = :apellidos")
    , @NamedQuery(name = "AccidenteVictima.findByTelefono", query = "SELECT a FROM AccidenteVictima a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "AccidenteVictima.findByCelular", query = "SELECT a FROM AccidenteVictima a WHERE a.celular = :celular")
    , @NamedQuery(name = "AccidenteVictima.findByDireccion", query = "SELECT a FROM AccidenteVictima a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "AccidenteVictima.findByGenero", query = "SELECT a FROM AccidenteVictima a WHERE a.genero = :genero")
    , @NamedQuery(name = "AccidenteVictima.findByEstrato", query = "SELECT a FROM AccidenteVictima a WHERE a.estrato = :estrato")
    , @NamedQuery(name = "AccidenteVictima.findByFechaNcto", query = "SELECT a FROM AccidenteVictima a WHERE a.fechaNcto = :fechaNcto")
    , @NamedQuery(name = "AccidenteVictima.findByCentroAsistencial", query = "SELECT a FROM AccidenteVictima a WHERE a.centroAsistencial = :centroAsistencial")
    , @NamedQuery(name = "AccidenteVictima.findByConciliado", query = "SELECT a FROM AccidenteVictima a WHERE a.conciliado = :conciliado")
    , @NamedQuery(name = "AccidenteVictima.findByVrConciliado", query = "SELECT a FROM AccidenteVictima a WHERE a.vrConciliado = :vrConciliado")
    , @NamedQuery(name = "AccidenteVictima.findByPathSoporteConciliacion", query = "SELECT a FROM AccidenteVictima a WHERE a.pathSoporteConciliacion = :pathSoporteConciliacion")
    , @NamedQuery(name = "AccidenteVictima.findByUsername", query = "SELECT a FROM AccidenteVictima a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteVictima.findByCreado", query = "SELECT a FROM AccidenteVictima a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteVictima.findByModificado", query = "SELECT a FROM AccidenteVictima a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteVictima.findByEstadoReg", query = "SELECT a FROM AccidenteVictima a WHERE a.estadoReg = :estadoReg")})
public class AccidenteVictima implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accidente_victima")
    private Integer idAccidenteVictima;
    @Size(max = 15)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 150)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 150)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 45)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 45)
    @Column(name = "celular")
    private String celular;
    @Size(max = 150)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "genero")
    private Character genero;
    @Column(name = "estrato")
    private Integer estrato;
    @Column(name = "fecha_ncto")
    @Temporal(TemporalType.DATE)
    private Date fechaNcto;
    @Size(max = 150)
    @Column(name = "centro_asistencial")
    private String centroAsistencial;
    @Column(name = "conciliado")
    private Integer conciliado;
    @Column(name = "vr_conciliado")
    private Integer vrConciliado;
    @Size(max = 150)
    @Column(name = "path_soporte_conciliacion")
    private String pathSoporteConciliacion;
    @Lob
    @Size(max = 65535)
    @Column(name = "lesiones")
    private String lesiones;
    @Lob
    @Size(max = 65535)
    @Column(name = "version")
    private String version;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_acc_condicion", referencedColumnName = "id_acc_condicion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccCondicion idAccCondicion;
    @JoinColumn(name = "id_acc_profesion", referencedColumnName = "id_acc_profesion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccProfesion idAccProfesion;
    @JoinColumn(name = "id_acc_rango_edad", referencedColumnName = "id_acc_rango_edad")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccRangoEdad idAccRangoEdad;
    @JoinColumn(name = "id_accidente_vehiculo", referencedColumnName = "id_accidente_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccidenteVehiculo idAccidenteVehiculo;
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Accidente idAccidente;
    @JoinColumn(name = "id_acc_eps", referencedColumnName = "id_acc_eps")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccEps idAccEps;

    public AccidenteVictima() {
    }

    public AccidenteVictima(Integer idAccidenteVictima) {
        this.idAccidenteVictima = idAccidenteVictima;
    }

    public Integer getIdAccidenteVictima() {
        return idAccidenteVictima;
    }

    public void setIdAccidenteVictima(Integer idAccidenteVictima) {
        this.idAccidenteVictima = idAccidenteVictima;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public Integer getEstrato() {
        return estrato;
    }

    public void setEstrato(Integer estrato) {
        this.estrato = estrato;
    }

    public Date getFechaNcto() {
        return fechaNcto;
    }

    public void setFechaNcto(Date fechaNcto) {
        this.fechaNcto = fechaNcto;
    }

    public String getCentroAsistencial() {
        return centroAsistencial;
    }

    public void setCentroAsistencial(String centroAsistencial) {
        this.centroAsistencial = centroAsistencial;
    }

    public Integer getConciliado() {
        return conciliado;
    }

    public void setConciliado(Integer conciliado) {
        this.conciliado = conciliado;
    }

    public Integer getVrConciliado() {
        return vrConciliado;
    }

    public void setVrConciliado(Integer vrConciliado) {
        this.vrConciliado = vrConciliado;
    }

    public String getPathSoporteConciliacion() {
        return pathSoporteConciliacion;
    }

    public void setPathSoporteConciliacion(String pathSoporteConciliacion) {
        this.pathSoporteConciliacion = pathSoporteConciliacion;
    }

    public String getLesiones() {
        return lesiones;
    }

    public void setLesiones(String lesiones) {
        this.lesiones = lesiones;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public AccCondicion getIdAccCondicion() {
        return idAccCondicion;
    }

    public void setIdAccCondicion(AccCondicion idAccCondicion) {
        this.idAccCondicion = idAccCondicion;
    }

    public AccProfesion getIdAccProfesion() {
        return idAccProfesion;
    }

    public void setIdAccProfesion(AccProfesion idAccProfesion) {
        this.idAccProfesion = idAccProfesion;
    }

    public AccRangoEdad getIdAccRangoEdad() {
        return idAccRangoEdad;
    }

    public void setIdAccRangoEdad(AccRangoEdad idAccRangoEdad) {
        this.idAccRangoEdad = idAccRangoEdad;
    }

    public AccidenteVehiculo getIdAccidenteVehiculo() {
        return idAccidenteVehiculo;
    }

    public void setIdAccidenteVehiculo(AccidenteVehiculo idAccidenteVehiculo) {
        this.idAccidenteVehiculo = idAccidenteVehiculo;
    }

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    public AccEps getIdAccEps() {
        return idAccEps;
    }

    public void setIdAccEps(AccEps idAccEps) {
        this.idAccEps = idAccEps;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteVictima != null ? idAccidenteVictima.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteVictima)) {
            return false;
        }
        AccidenteVictima other = (AccidenteVictima) object;
        if ((this.idAccidenteVictima == null && other.idAccidenteVictima != null) || (this.idAccidenteVictima != null && !this.idAccidenteVictima.equals(other.idAccidenteVictima))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteVictima[ idAccidenteVictima=" + idAccidenteVictima + " ]";
    }

}
