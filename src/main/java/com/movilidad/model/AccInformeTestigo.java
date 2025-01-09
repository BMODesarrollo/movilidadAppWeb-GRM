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
@Table(name = "acc_informe_testigo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeTestigo.findAll", query = "SELECT a FROM AccInformeTestigo a")
    , @NamedQuery(name = "AccInformeTestigo.findByIdAccInformeTestigo", query = "SELECT a FROM AccInformeTestigo a WHERE a.idAccInformeTestigo = :idAccInformeTestigo")
    , @NamedQuery(name = "AccInformeTestigo.findByNombres", query = "SELECT a FROM AccInformeTestigo a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeTestigo.findByNroDoc", query = "SELECT a FROM AccInformeTestigo a WHERE a.nroDoc = :nroDoc")
    , @NamedQuery(name = "AccInformeTestigo.findByDireccion", query = "SELECT a FROM AccInformeTestigo a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "AccInformeTestigo.findByCiudad", query = "SELECT a FROM AccInformeTestigo a WHERE a.ciudad = :ciudad")
    , @NamedQuery(name = "AccInformeTestigo.findByTelefono", query = "SELECT a FROM AccInformeTestigo a WHERE a.telefono = :telefono")})
public class AccInformeTestigo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_testigo")
    private Integer idAccInformeTestigo;
    @Size(max = 60)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 15)
    @Column(name = "nro_doc")
    private String nroDoc;
    @Size(max = 60)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 45)
    @Column(name = "telefono")
    private String telefono;
    @JoinColumn(name = "id_acc_informe_ope", referencedColumnName = "id_acc_informe_ope")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeOpe idAccInformeOpe;
    @JoinColumn(name = "id_tipo_identificacion", referencedColumnName = "id_empleado_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoIdentificacion idTipoIdentificacion;

    public AccInformeTestigo() {
    }

    public AccInformeTestigo(Integer idAccInformeTestigo) {
        this.idAccInformeTestigo = idAccInformeTestigo;
    }

    public Integer getIdAccInformeTestigo() {
        return idAccInformeTestigo;
    }

    public void setIdAccInformeTestigo(Integer idAccInformeTestigo) {
        this.idAccInformeTestigo = idAccInformeTestigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public AccInformeOpe getIdAccInformeOpe() {
        return idAccInformeOpe;
    }

    public void setIdAccInformeOpe(AccInformeOpe idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }

    public EmpleadoTipoIdentificacion getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(EmpleadoTipoIdentificacion idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInformeTestigo != null ? idAccInformeTestigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeTestigo)) {
            return false;
        }
        AccInformeTestigo other = (AccInformeTestigo) object;
        if ((this.idAccInformeTestigo == null && other.idAccInformeTestigo != null) || (this.idAccInformeTestigo != null && !this.idAccInformeTestigo.equals(other.idAccInformeTestigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeTestigo[ idAccInformeTestigo=" + idAccInformeTestigo + " ]";
    }
    
}
