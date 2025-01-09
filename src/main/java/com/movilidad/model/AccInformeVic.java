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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_informe_vic")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeVic.findAll", query = "SELECT a FROM AccInformeVic a")
    , @NamedQuery(name = "AccInformeVic.findByIdAccInformeVic", query = "SELECT a FROM AccInformeVic a WHERE a.idAccInformeVic = :idAccInformeVic")
    , @NamedQuery(name = "AccInformeVic.findByNombres", query = "SELECT a FROM AccInformeVic a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeVic.findByNroDoc", query = "SELECT a FROM AccInformeVic a WHERE a.nroDoc = :nroDoc")
    , @NamedQuery(name = "AccInformeVic.findByFechaNac", query = "SELECT a FROM AccInformeVic a WHERE a.fechaNac = :fechaNac")
    , @NamedQuery(name = "AccInformeVic.findByDireccion", query = "SELECT a FROM AccInformeVic a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "AccInformeVic.findByCiudad", query = "SELECT a FROM AccInformeVic a WHERE a.ciudad = :ciudad")
    , @NamedQuery(name = "AccInformeVic.findByTelefono", query = "SELECT a FROM AccInformeVic a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "AccInformeVic.findByCondicion", query = "SELECT a FROM AccInformeVic a WHERE a.condicion = :condicion")
    , @NamedQuery(name = "AccInformeVic.findByCinturon", query = "SELECT a FROM AccInformeVic a WHERE a.cinturon = :cinturon")
    , @NamedQuery(name = "AccInformeVic.findByCasco", query = "SELECT a FROM AccInformeVic a WHERE a.casco = :casco")
    , @NamedQuery(name = "AccInformeVic.findBySitioAtencion", query = "SELECT a FROM AccInformeVic a WHERE a.sitioAtencion = :sitioAtencion")
    , @NamedQuery(name = "AccInformeVic.findByPrebaEmbriaguez", query = "SELECT a FROM AccInformeVic a WHERE a.prebaEmbriaguez = :prebaEmbriaguez")
    , @NamedQuery(name = "AccInformeVic.findByPruebaDrogra", query = "SELECT a FROM AccInformeVic a WHERE a.pruebaDrogra = :pruebaDrogra")
    , @NamedQuery(name = "AccInformeVic.findByResultEmbriaguez", query = "SELECT a FROM AccInformeVic a WHERE a.resultEmbriaguez = :resultEmbriaguez")
    , @NamedQuery(name = "AccInformeVic.findByResultDroga", query = "SELECT a FROM AccInformeVic a WHERE a.resultDroga = :resultDroga")
    , @NamedQuery(name = "AccInformeVic.findByTipoVictima", query = "SELECT a FROM AccInformeVic a WHERE a.tipoVictima = :tipoVictima")
    , @NamedQuery(name = "AccInformeVic.findBySexo", query = "SELECT a FROM AccInformeVic a WHERE a.sexo = :sexo")
    , @NamedQuery(name = "AccInformeVic.findByGravedad", query = "SELECT a FROM AccInformeVic a WHERE a.gravedad = :gravedad")})
public class AccInformeVic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_vic")
    private Integer idAccInformeVic;
    @Size(max = 60)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 15)
    @Column(name = "nro_doc")
    private String nroDoc;
    @Column(name = "fecha_nac")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Size(max = 60)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 45)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "condicion")
    private Integer condicion;
    @Column(name = "cinturon")
    private Integer cinturon;
    @Column(name = "casco")
    private Integer casco;
    @Size(max = 45)
    @Column(name = "sitio_atencion")
    private String sitioAtencion;
    @Column(name = "preba_embriaguez")
    private Integer prebaEmbriaguez;
    @Column(name = "prueba_drogra")
    private Integer pruebaDrogra;
    @Size(max = 30)
    @Column(name = "result_embriaguez")
    private String resultEmbriaguez;
    @Size(max = 30)
    @Column(name = "result_droga")
    private String resultDroga;
    @Column(name = "tipo_victima")
    private Integer tipoVictima;
    @Column(name = "sexo")
    private Character sexo;
    @Column(name = "gravedad")
    private Integer gravedad;
    @JoinColumn(name = "id_acc_informe_ope", referencedColumnName = "id_acc_informe_ope")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeOpe idAccInformeOpe;
    @JoinColumn(name = "id_tipo_identificacion", referencedColumnName = "id_empleado_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoIdentificacion idTipoIdentificacion;

    public AccInformeVic() {
    }

    public AccInformeVic(Integer idAccInformeVic) {
        this.idAccInformeVic = idAccInformeVic;
    }

    public Integer getIdAccInformeVic() {
        return idAccInformeVic;
    }

    public void setIdAccInformeVic(Integer idAccInformeVic) {
        this.idAccInformeVic = idAccInformeVic;
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

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
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

    public Integer getCondicion() {
        return condicion;
    }

    public void setCondicion(Integer condicion) {
        this.condicion = condicion;
    }

    public Integer getCinturon() {
        return cinturon;
    }

    public void setCinturon(Integer cinturon) {
        this.cinturon = cinturon;
    }

    public Integer getCasco() {
        return casco;
    }

    public void setCasco(Integer casco) {
        this.casco = casco;
    }

    public String getSitioAtencion() {
        return sitioAtencion;
    }

    public void setSitioAtencion(String sitioAtencion) {
        this.sitioAtencion = sitioAtencion;
    }

    public Integer getPrebaEmbriaguez() {
        return prebaEmbriaguez;
    }

    public void setPrebaEmbriaguez(Integer prebaEmbriaguez) {
        this.prebaEmbriaguez = prebaEmbriaguez;
    }

    public Integer getPruebaDrogra() {
        return pruebaDrogra;
    }

    public void setPruebaDrogra(Integer pruebaDrogra) {
        this.pruebaDrogra = pruebaDrogra;
    }

    public String getResultEmbriaguez() {
        return resultEmbriaguez;
    }

    public void setResultEmbriaguez(String resultEmbriaguez) {
        this.resultEmbriaguez = resultEmbriaguez;
    }

    public String getResultDroga() {
        return resultDroga;
    }

    public void setResultDroga(String resultDroga) {
        this.resultDroga = resultDroga;
    }

    public Integer getTipoVictima() {
        return tipoVictima;
    }

    public void setTipoVictima(Integer tipoVictima) {
        this.tipoVictima = tipoVictima;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Integer getGravedad() {
        return gravedad;
    }

    public void setGravedad(Integer gravedad) {
        this.gravedad = gravedad;
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
        hash += (idAccInformeVic != null ? idAccInformeVic.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeVic)) {
            return false;
        }
        AccInformeVic other = (AccInformeVic) object;
        if ((this.idAccInformeVic == null && other.idAccInformeVic != null) || (this.idAccInformeVic != null && !this.idAccInformeVic.equals(other.idAccInformeVic))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeVic[ idAccInformeVic=" + idAccInformeVic + " ]";
    }
    
}
