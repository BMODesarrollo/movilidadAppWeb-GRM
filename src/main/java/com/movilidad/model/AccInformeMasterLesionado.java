/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_informe_master_lesionado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterLesionado.findAll", query = "SELECT a FROM AccInformeMasterLesionado a")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByIdAccInformeMasterLesionado", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.idAccInformeMasterLesionado = :idAccInformeMasterLesionado")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByNombres", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByEdad", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.edad = :edad")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByIdentificacion", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.identificacion = :identificacion")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByEps", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.eps = :eps")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByDireccion", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByBarrio", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.barrio = :barrio")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByTelefono", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByCelular", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.celular = :celular")
    , @NamedQuery(name = "AccInformeMasterLesionado.findByClinicaTraslado", query = "SELECT a FROM AccInformeMasterLesionado a WHERE a.clinicaTraslado = :clinicaTraslado")})
public class AccInformeMasterLesionado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_lesionado")
    private Integer idAccInformeMasterLesionado;
    @Size(max = 60)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 3)
    @Column(name = "edad")
    private String edad;
    @Size(max = 45)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 45)
    @Column(name = "eps")
    private String eps;
    @Size(max = 60)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "barrio")
    private String barrio;
    @Size(max = 15)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 15)
    @Column(name = "celular")
    private String celular;
    @Lob
    @Size(max = 65535)
    @Column(name = "diagnostigo")
    private String diagnostigo;
    @Size(max = 45)
    @Column(name = "clinica_traslado")
    private String clinicaTraslado;
    @JoinColumn(name = "id_acc_informe_master", referencedColumnName = "id_acc_informe_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeMaster idAccInformeMaster;

    public AccInformeMasterLesionado() {
    }

    public AccInformeMasterLesionado(Integer idAccInformeMasterLesionado) {
        this.idAccInformeMasterLesionado = idAccInformeMasterLesionado;
    }

    public Integer getIdAccInformeMasterLesionado() {
        return idAccInformeMasterLesionado;
    }

    public void setIdAccInformeMasterLesionado(Integer idAccInformeMasterLesionado) {
        this.idAccInformeMasterLesionado = idAccInformeMasterLesionado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
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

    public String getDiagnostigo() {
        return diagnostigo;
    }

    public void setDiagnostigo(String diagnostigo) {
        this.diagnostigo = diagnostigo;
    }

    public String getClinicaTraslado() {
        return clinicaTraslado;
    }

    public void setClinicaTraslado(String clinicaTraslado) {
        this.clinicaTraslado = clinicaTraslado;
    }

    public AccInformeMaster getIdAccInformeMaster() {
        return idAccInformeMaster;
    }

    public void setIdAccInformeMaster(AccInformeMaster idAccInformeMaster) {
        this.idAccInformeMaster = idAccInformeMaster;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInformeMasterLesionado != null ? idAccInformeMasterLesionado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterLesionado)) {
            return false;
        }
        AccInformeMasterLesionado other = (AccInformeMasterLesionado) object;
        if ((this.idAccInformeMasterLesionado == null && other.idAccInformeMasterLesionado != null) || (this.idAccInformeMasterLesionado != null && !this.idAccInformeMasterLesionado.equals(other.idAccInformeMasterLesionado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterLesionado[ idAccInformeMasterLesionado=" + idAccInformeMasterLesionado + " ]";
    }
    
}
