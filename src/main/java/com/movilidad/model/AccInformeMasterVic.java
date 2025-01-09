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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_informe_master_vic")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterVic.findAll", query = "SELECT a FROM AccInformeMasterVic a")
    , @NamedQuery(name = "AccInformeMasterVic.findByIdAccInformeMasterVic", query = "SELECT a FROM AccInformeMasterVic a WHERE a.idAccInformeMasterVic = :idAccInformeMasterVic")
    , @NamedQuery(name = "AccInformeMasterVic.findByNombres", query = "SELECT a FROM AccInformeMasterVic a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeMasterVic.findByNroDoc", query = "SELECT a FROM AccInformeMasterVic a WHERE a.nroDoc = :nroDoc")
    , @NamedQuery(name = "AccInformeMasterVic.findByFechaNac", query = "SELECT a FROM AccInformeMasterVic a WHERE a.fechaNac = :fechaNac")
    , @NamedQuery(name = "AccInformeMasterVic.findByDireccion", query = "SELECT a FROM AccInformeMasterVic a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "AccInformeMasterVic.findByCiudad", query = "SELECT a FROM AccInformeMasterVic a WHERE a.ciudad = :ciudad")
    , @NamedQuery(name = "AccInformeMasterVic.findByTelefono", query = "SELECT a FROM AccInformeMasterVic a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "AccInformeMasterVic.findByCondicion", query = "SELECT a FROM AccInformeMasterVic a WHERE a.condicion = :condicion")
    , @NamedQuery(name = "AccInformeMasterVic.findByCinturon", query = "SELECT a FROM AccInformeMasterVic a WHERE a.cinturon = :cinturon")
    , @NamedQuery(name = "AccInformeMasterVic.findByCasco", query = "SELECT a FROM AccInformeMasterVic a WHERE a.casco = :casco")
    , @NamedQuery(name = "AccInformeMasterVic.findBySitioAtencion", query = "SELECT a FROM AccInformeMasterVic a WHERE a.sitioAtencion = :sitioAtencion")
    , @NamedQuery(name = "AccInformeMasterVic.findBySexo", query = "SELECT a FROM AccInformeMasterVic a WHERE a.sexo = :sexo")
    , @NamedQuery(name = "AccInformeMasterVic.findByGravedad", query = "SELECT a FROM AccInformeMasterVic a WHERE a.gravedad = :gravedad")
    , @NamedQuery(name = "AccInformeMasterVic.findByEps", query = "SELECT a FROM AccInformeMasterVic a WHERE a.eps = :eps")})
public class AccInformeMasterVic implements Serializable {

    @Size(max = 60)
    @Column(name = "otro")
    private String otro;
  
    @JoinColumn(name = "id_acc_tipo_victima", referencedColumnName = "id_acc_tipo_victima")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoVictima idAccTipoVictima;
    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_vic")
    private Integer idAccInformeMasterVic;
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
    @Size(max = 15)
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
    @Column(name = "sexo")
    private Character sexo;
    @Column(name = "gravedad")
    private Integer gravedad;
    @Size(max = 60)
    @Column(name = "eps")
    private String eps;
    @Lob
    @Size(max = 65535)
    @Column(name = "diagnostico")
    private String diagnostico;
    @JoinColumn(name = "id_acc_informe_master", referencedColumnName = "id_acc_informe_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeMaster idAccInformeMaster;
    @JoinColumn(name = "id_tipo_identificacion", referencedColumnName = "id_empleado_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoIdentificacion idTipoIdentificacion;

    public AccInformeMasterVic() {
    }

    public AccInformeMasterVic(Integer idAccInformeMasterVic) {
        this.idAccInformeMasterVic = idAccInformeMasterVic;
    }

    public Integer getIdAccInformeMasterVic() {
        return idAccInformeMasterVic;
    }

    public void setIdAccInformeMasterVic(Integer idAccInformeMasterVic) {
        this.idAccInformeMasterVic = idAccInformeMasterVic;
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

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public AccInformeMaster getIdAccInformeMaster() {
        return idAccInformeMaster;
    }

    public void setIdAccInformeMaster(AccInformeMaster idAccInformeMaster) {
        this.idAccInformeMaster = idAccInformeMaster;
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
        hash += (idAccInformeMasterVic != null ? idAccInformeMasterVic.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterVic)) {
            return false;
        }
        AccInformeMasterVic other = (AccInformeMasterVic) object;
        if ((this.idAccInformeMasterVic == null && other.idAccInformeMasterVic != null) || (this.idAccInformeMasterVic != null && !this.idAccInformeMasterVic.equals(other.idAccInformeMasterVic))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterVic[ idAccInformeMasterVic=" + idAccInformeMasterVic + " ]";
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public AccTipoVictima getIdAccTipoVictima() {
        return idAccTipoVictima;
    }

    public void setIdAccTipoVictima(AccTipoVictima idAccTipoVictima) {
        this.idAccTipoVictima = idAccTipoVictima;
    }
    
}
