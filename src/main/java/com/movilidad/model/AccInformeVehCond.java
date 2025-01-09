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
@Table(name = "acc_informe_veh_cond")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeVehCond.findAll", query = "SELECT a FROM AccInformeVehCond a")
    , @NamedQuery(name = "AccInformeVehCond.findByIdAccInformeVehCond", query = "SELECT a FROM AccInformeVehCond a WHERE a.idAccInformeVehCond = :idAccInformeVehCond")
    , @NamedQuery(name = "AccInformeVehCond.findByNombres", query = "SELECT a FROM AccInformeVehCond a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeVehCond.findByNroDocumento", query = "SELECT a FROM AccInformeVehCond a WHERE a.nroDocumento = :nroDocumento")
    , @NamedQuery(name = "AccInformeVehCond.findByFechaNac", query = "SELECT a FROM AccInformeVehCond a WHERE a.fechaNac = :fechaNac")
    , @NamedQuery(name = "AccInformeVehCond.findBySexo", query = "SELECT a FROM AccInformeVehCond a WHERE a.sexo = :sexo")
    , @NamedQuery(name = "AccInformeVehCond.findByDireccion", query = "SELECT a FROM AccInformeVehCond a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "AccInformeVehCond.findByCiudad", query = "SELECT a FROM AccInformeVehCond a WHERE a.ciudad = :ciudad")
    , @NamedQuery(name = "AccInformeVehCond.findByTelefono", query = "SELECT a FROM AccInformeVehCond a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "AccInformeVehCond.findByCondicion", query = "SELECT a FROM AccInformeVehCond a WHERE a.condicion = :condicion")
    , @NamedQuery(name = "AccInformeVehCond.findByCinturon", query = "SELECT a FROM AccInformeVehCond a WHERE a.cinturon = :cinturon")
    , @NamedQuery(name = "AccInformeVehCond.findByLicencia", query = "SELECT a FROM AccInformeVehCond a WHERE a.licencia = :licencia")
    , @NamedQuery(name = "AccInformeVehCond.findByCasco", query = "SELECT a FROM AccInformeVehCond a WHERE a.casco = :casco")
    , @NamedQuery(name = "AccInformeVehCond.findByNroLicencia", query = "SELECT a FROM AccInformeVehCond a WHERE a.nroLicencia = :nroLicencia")
    , @NamedQuery(name = "AccInformeVehCond.findByCategoria", query = "SELECT a FROM AccInformeVehCond a WHERE a.categoria = :categoria")
    , @NamedQuery(name = "AccInformeVehCond.findByRestriccion", query = "SELECT a FROM AccInformeVehCond a WHERE a.restriccion = :restriccion")
    , @NamedQuery(name = "AccInformeVehCond.findByFechaExp", query = "SELECT a FROM AccInformeVehCond a WHERE a.fechaExp = :fechaExp")
    , @NamedQuery(name = "AccInformeVehCond.findByFechaVencimiento", query = "SELECT a FROM AccInformeVehCond a WHERE a.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "AccInformeVehCond.findBySitioAtencion", query = "SELECT a FROM AccInformeVehCond a WHERE a.sitioAtencion = :sitioAtencion")
    , @NamedQuery(name = "AccInformeVehCond.findByPruebaEmbriaguez", query = "SELECT a FROM AccInformeVehCond a WHERE a.pruebaEmbriaguez = :pruebaEmbriaguez")
    , @NamedQuery(name = "AccInformeVehCond.findByPruebaDroga", query = "SELECT a FROM AccInformeVehCond a WHERE a.pruebaDroga = :pruebaDroga")
    , @NamedQuery(name = "AccInformeVehCond.findByResultEmbriaguez", query = "SELECT a FROM AccInformeVehCond a WHERE a.resultEmbriaguez = :resultEmbriaguez")
    , @NamedQuery(name = "AccInformeVehCond.findByResulDroga", query = "SELECT a FROM AccInformeVehCond a WHERE a.resulDroga = :resulDroga")
    , @NamedQuery(name = "AccInformeVehCond.findByPlaca", query = "SELECT a FROM AccInformeVehCond a WHERE a.placa = :placa")
    , @NamedQuery(name = "AccInformeVehCond.findByMarca", query = "SELECT a FROM AccInformeVehCond a WHERE a.marca = :marca")
    , @NamedQuery(name = "AccInformeVehCond.findByLinea", query = "SELECT a FROM AccInformeVehCond a WHERE a.linea = :linea")
    , @NamedQuery(name = "AccInformeVehCond.findByModelo", query = "SELECT a FROM AccInformeVehCond a WHERE a.modelo = :modelo")
    , @NamedQuery(name = "AccInformeVehCond.findByCargaTonelada", query = "SELECT a FROM AccInformeVehCond a WHERE a.cargaTonelada = :cargaTonelada")
    , @NamedQuery(name = "AccInformeVehCond.findByColor", query = "SELECT a FROM AccInformeVehCond a WHERE a.color = :color")
    , @NamedQuery(name = "AccInformeVehCond.findByNroPasajeros", query = "SELECT a FROM AccInformeVehCond a WHERE a.nroPasajeros = :nroPasajeros")
    , @NamedQuery(name = "AccInformeVehCond.findByInmovilizado", query = "SELECT a FROM AccInformeVehCond a WHERE a.inmovilizado = :inmovilizado")
    , @NamedQuery(name = "AccInformeVehCond.findByDisposicion", query = "SELECT a FROM AccInformeVehCond a WHERE a.disposicion = :disposicion")
    , @NamedQuery(name = "AccInformeVehCond.findBySeguroObligatorio", query = "SELECT a FROM AccInformeVehCond a WHERE a.seguroObligatorio = :seguroObligatorio")
    , @NamedQuery(name = "AccInformeVehCond.findByNroPoliza", query = "SELECT a FROM AccInformeVehCond a WHERE a.nroPoliza = :nroPoliza")
    , @NamedQuery(name = "AccInformeVehCond.findByCompaniaAseguradora", query = "SELECT a FROM AccInformeVehCond a WHERE a.companiaAseguradora = :companiaAseguradora")
    , @NamedQuery(name = "AccInformeVehCond.findByFechaVencSeguro", query = "SELECT a FROM AccInformeVehCond a WHERE a.fechaVencSeguro = :fechaVencSeguro")
    , @NamedQuery(name = "AccInformeVehCond.findByNombresPropietario", query = "SELECT a FROM AccInformeVehCond a WHERE a.nombresPropietario = :nombresPropietario")
    , @NamedQuery(name = "AccInformeVehCond.findByNroDocPropietario", query = "SELECT a FROM AccInformeVehCond a WHERE a.nroDocPropietario = :nroDocPropietario")
    , @NamedQuery(name = "AccInformeVehCond.findBySeguroRespCivil", query = "SELECT a FROM AccInformeVehCond a WHERE a.seguroRespCivil = :seguroRespCivil")
    , @NamedQuery(name = "AccInformeVehCond.findByNacionalidad", query = "SELECT a FROM AccInformeVehCond a WHERE a.nacionalidad = :nacionalidad")})
public class AccInformeVehCond implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_veh_cond")
    private Integer idAccInformeVehCond;
    @Size(max = 150)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 15)
    @Column(name = "nro_documento")
    private String nroDocumento;
    @Column(name = "fecha_nac")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Column(name = "sexo")
    private Character sexo;
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
    @Column(name = "licencia")
    private Integer licencia;
    @Column(name = "casco")
    private Integer casco;
    @Size(max = 45)
    @Column(name = "nro_licencia")
    private String nroLicencia;
    @Size(max = 45)
    @Column(name = "categoria")
    private String categoria;
    @Size(max = 45)
    @Column(name = "restriccion")
    private String restriccion;
    @Column(name = "fecha_exp")
    @Temporal(TemporalType.DATE)
    private Date fechaExp;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Size(max = 60)
    @Column(name = "sitio_atencion")
    private String sitioAtencion;
    @Column(name = "prueba_embriaguez")
    private Integer pruebaEmbriaguez;
    @Column(name = "prueba_droga")
    private Integer pruebaDroga;
    @Size(max = 30)
    @Column(name = "result_embriaguez")
    private String resultEmbriaguez;
    @Size(max = 30)
    @Column(name = "resul_droga")
    private String resulDroga;
    @Size(max = 45)
    @Column(name = "placa")
    private String placa;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
    @Size(max = 45)
    @Column(name = "linea")
    private String linea;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Size(max = 45)
    @Column(name = "carga_tonelada")
    private String cargaTonelada;
    @Size(max = 45)
    @Column(name = "color")
    private String color;
    @Column(name = "nro_pasajeros")
    private Integer nroPasajeros;
    @Size(max = 45)
    @Column(name = "inmovilizado")
    private String inmovilizado;
    @Size(max = 45)
    @Column(name = "disposicion")
    private String disposicion;
    @Column(name = "seguro_obligatorio")
    private Integer seguroObligatorio;
    @Size(max = 15)
    @Column(name = "nro_poliza")
    private String nroPoliza;
    @Size(max = 45)
    @Column(name = "compania_aseguradora")
    private String companiaAseguradora;
    @Column(name = "fecha_venc_seguro")
    @Temporal(TemporalType.DATE)
    private Date fechaVencSeguro;
    @Size(max = 60)
    @Column(name = "nombres_propietario")
    private String nombresPropietario;
    @Size(max = 15)
    @Column(name = "nro_doc_propietario")
    private String nroDocPropietario;
    @Size(max = 15)
    @Column(name = "seguro_resp_civil")
    private String seguroRespCivil;
    @Column(name = "nacionalidad")
    private Integer nacionalidad;
    @JoinColumn(name = "id_empresa_operadora", referencedColumnName = "id_acc_empresa_operadora")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccEmpresaOperadora idEmpresaOperadora;
    @JoinColumn(name = "id_acc_informe_ope", referencedColumnName = "id_acc_informe_ope")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeOpe idAccInformeOpe;
    @JoinColumn(name = "id_tipo_serv", referencedColumnName = "id_acc_tipo_serv")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoServ idTipoServ;
    @JoinColumn(name = "id_acc_tipo_vehiculo", referencedColumnName = "id_acc_tipo_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoVehiculo idAccTipoVehiculo;
    @JoinColumn(name = "id_acc_falla", referencedColumnName = "id_acc_veh_falla")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccVehFalla idAccFalla;
    @JoinColumn(name = "id_tipo_iden_prop", referencedColumnName = "id_empleado_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoIdentificacion idTipoIdenProp;
    @JoinColumn(name = "id_tipo_identificacion", referencedColumnName = "id_empleado_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoIdentificacion idTipoIdentificacion;

    public AccInformeVehCond() {
    }

    public AccInformeVehCond(Integer idAccInformeVehCond) {
        this.idAccInformeVehCond = idAccInformeVehCond;
    }

    public Integer getIdAccInformeVehCond() {
        return idAccInformeVehCond;
    }

    public void setIdAccInformeVehCond(Integer idAccInformeVehCond) {
        this.idAccInformeVehCond = idAccInformeVehCond;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
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

    public Integer getLicencia() {
        return licencia;
    }

    public void setLicencia(Integer licencia) {
        this.licencia = licencia;
    }

    public Integer getCasco() {
        return casco;
    }

    public void setCasco(Integer casco) {
        this.casco = casco;
    }

    public String getNroLicencia() {
        return nroLicencia;
    }

    public void setNroLicencia(String nroLicencia) {
        this.nroLicencia = nroLicencia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    public Date getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(Date fechaExp) {
        this.fechaExp = fechaExp;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getSitioAtencion() {
        return sitioAtencion;
    }

    public void setSitioAtencion(String sitioAtencion) {
        this.sitioAtencion = sitioAtencion;
    }

    public Integer getPruebaEmbriaguez() {
        return pruebaEmbriaguez;
    }

    public void setPruebaEmbriaguez(Integer pruebaEmbriaguez) {
        this.pruebaEmbriaguez = pruebaEmbriaguez;
    }

    public Integer getPruebaDroga() {
        return pruebaDroga;
    }

    public void setPruebaDroga(Integer pruebaDroga) {
        this.pruebaDroga = pruebaDroga;
    }

    public String getResultEmbriaguez() {
        return resultEmbriaguez;
    }

    public void setResultEmbriaguez(String resultEmbriaguez) {
        this.resultEmbriaguez = resultEmbriaguez;
    }

    public String getResulDroga() {
        return resulDroga;
    }

    public void setResulDroga(String resulDroga) {
        this.resulDroga = resulDroga;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCargaTonelada() {
        return cargaTonelada;
    }

    public void setCargaTonelada(String cargaTonelada) {
        this.cargaTonelada = cargaTonelada;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNroPasajeros() {
        return nroPasajeros;
    }

    public void setNroPasajeros(Integer nroPasajeros) {
        this.nroPasajeros = nroPasajeros;
    }

    public String getInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(String inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public String getDisposicion() {
        return disposicion;
    }

    public void setDisposicion(String disposicion) {
        this.disposicion = disposicion;
    }

    public Integer getSeguroObligatorio() {
        return seguroObligatorio;
    }

    public void setSeguroObligatorio(Integer seguroObligatorio) {
        this.seguroObligatorio = seguroObligatorio;
    }

    public String getNroPoliza() {
        return nroPoliza;
    }

    public void setNroPoliza(String nroPoliza) {
        this.nroPoliza = nroPoliza;
    }

    public String getCompaniaAseguradora() {
        return companiaAseguradora;
    }

    public void setCompaniaAseguradora(String companiaAseguradora) {
        this.companiaAseguradora = companiaAseguradora;
    }

    public Date getFechaVencSeguro() {
        return fechaVencSeguro;
    }

    public void setFechaVencSeguro(Date fechaVencSeguro) {
        this.fechaVencSeguro = fechaVencSeguro;
    }

    public String getNombresPropietario() {
        return nombresPropietario;
    }

    public void setNombresPropietario(String nombresPropietario) {
        this.nombresPropietario = nombresPropietario;
    }

    public String getNroDocPropietario() {
        return nroDocPropietario;
    }

    public void setNroDocPropietario(String nroDocPropietario) {
        this.nroDocPropietario = nroDocPropietario;
    }

    public String getSeguroRespCivil() {
        return seguroRespCivil;
    }

    public void setSeguroRespCivil(String seguroRespCivil) {
        this.seguroRespCivil = seguroRespCivil;
    }

    public Integer getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Integer nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public AccEmpresaOperadora getIdEmpresaOperadora() {
        return idEmpresaOperadora;
    }

    public void setIdEmpresaOperadora(AccEmpresaOperadora idEmpresaOperadora) {
        this.idEmpresaOperadora = idEmpresaOperadora;
    }

    public AccInformeOpe getIdAccInformeOpe() {
        return idAccInformeOpe;
    }

    public void setIdAccInformeOpe(AccInformeOpe idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }

    public AccTipoServ getIdTipoServ() {
        return idTipoServ;
    }

    public void setIdTipoServ(AccTipoServ idTipoServ) {
        this.idTipoServ = idTipoServ;
    }

    public AccTipoVehiculo getIdAccTipoVehiculo() {
        return idAccTipoVehiculo;
    }

    public void setIdAccTipoVehiculo(AccTipoVehiculo idAccTipoVehiculo) {
        this.idAccTipoVehiculo = idAccTipoVehiculo;
    }

    public AccVehFalla getIdAccFalla() {
        return idAccFalla;
    }

    public void setIdAccFalla(AccVehFalla idAccFalla) {
        this.idAccFalla = idAccFalla;
    }

    public EmpleadoTipoIdentificacion getIdTipoIdenProp() {
        return idTipoIdenProp;
    }

    public void setIdTipoIdenProp(EmpleadoTipoIdentificacion idTipoIdenProp) {
        this.idTipoIdenProp = idTipoIdenProp;
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
        hash += (idAccInformeVehCond != null ? idAccInformeVehCond.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeVehCond)) {
            return false;
        }
        AccInformeVehCond other = (AccInformeVehCond) object;
        if ((this.idAccInformeVehCond == null && other.idAccInformeVehCond != null) || (this.idAccInformeVehCond != null && !this.idAccInformeVehCond.equals(other.idAccInformeVehCond))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeVehCond[ idAccInformeVehCond=" + idAccInformeVehCond + " ]";
    }
    
}
