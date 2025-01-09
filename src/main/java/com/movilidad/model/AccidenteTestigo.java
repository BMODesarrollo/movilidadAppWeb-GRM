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
@Table(name = "accidente_testigo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteTestigo.findAll", query = "SELECT a FROM AccidenteTestigo a")
    , @NamedQuery(name = "AccidenteTestigo.findByIdAccidenteTestigo", query = "SELECT a FROM AccidenteTestigo a WHERE a.idAccidenteTestigo = :idAccidenteTestigo")
    , @NamedQuery(name = "AccidenteTestigo.findByCedula", query = "SELECT a FROM AccidenteTestigo a WHERE a.cedula = :cedula")
    , @NamedQuery(name = "AccidenteTestigo.findByNombres", query = "SELECT a FROM AccidenteTestigo a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccidenteTestigo.findByApellidos", query = "SELECT a FROM AccidenteTestigo a WHERE a.apellidos = :apellidos")
    , @NamedQuery(name = "AccidenteTestigo.findByTelefono", query = "SELECT a FROM AccidenteTestigo a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "AccidenteTestigo.findByCelular", query = "SELECT a FROM AccidenteTestigo a WHERE a.celular = :celular")
    , @NamedQuery(name = "AccidenteTestigo.findByDireccion", query = "SELECT a FROM AccidenteTestigo a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "AccidenteTestigo.findByGenero", query = "SELECT a FROM AccidenteTestigo a WHERE a.genero = :genero")
    , @NamedQuery(name = "AccidenteTestigo.findByFechaNcto", query = "SELECT a FROM AccidenteTestigo a WHERE a.fechaNcto = :fechaNcto")
    , @NamedQuery(name = "AccidenteTestigo.findByUsername", query = "SELECT a FROM AccidenteTestigo a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteTestigo.findByCreado", query = "SELECT a FROM AccidenteTestigo a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteTestigo.findByModificado", query = "SELECT a FROM AccidenteTestigo a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteTestigo.findByEstadoReg", query = "SELECT a FROM AccidenteTestigo a WHERE a.estadoReg = :estadoReg")})
public class AccidenteTestigo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accidente_testigo")
    private Integer idAccidenteTestigo;
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
    @Basic(optional = false)
    @Column(name = "celular")
    private String celular;
    @Size(max = 150)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "genero")
    private Character genero;
    @Column(name = "fecha_ncto")
    @Temporal(TemporalType.DATE)
    private Date fechaNcto;
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
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Accidente idAccidente;
    @JoinColumn(name = "id_acc_profesion", referencedColumnName = "id_acc_profesion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccProfesion idAccProfesion;

    public AccidenteTestigo() {
    }

    public AccidenteTestigo(Integer idAccidenteTestigo) {
        this.idAccidenteTestigo = idAccidenteTestigo;
    }

    public AccidenteTestigo(Integer idAccidenteTestigo, String celular) {
        this.idAccidenteTestigo = idAccidenteTestigo;
        this.celular = celular;
    }

    public Integer getIdAccidenteTestigo() {
        return idAccidenteTestigo;
    }

    public void setIdAccidenteTestigo(Integer idAccidenteTestigo) {
        this.idAccidenteTestigo = idAccidenteTestigo;
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

    public Date getFechaNcto() {
        return fechaNcto;
    }

    public void setFechaNcto(Date fechaNcto) {
        this.fechaNcto = fechaNcto;
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

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    public AccProfesion getIdAccProfesion() {
        return idAccProfesion;
    }

    public void setIdAccProfesion(AccProfesion idAccProfesion) {
        this.idAccProfesion = idAccProfesion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteTestigo != null ? idAccidenteTestigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteTestigo)) {
            return false;
        }
        AccidenteTestigo other = (AccidenteTestigo) object;
        if ((this.idAccidenteTestigo == null && other.idAccidenteTestigo != null) || (this.idAccidenteTestigo != null && !this.idAccidenteTestigo.equals(other.idAccidenteTestigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteTestigo[ idAccidenteTestigo=" + idAccidenteTestigo + " ]";
    }

}
