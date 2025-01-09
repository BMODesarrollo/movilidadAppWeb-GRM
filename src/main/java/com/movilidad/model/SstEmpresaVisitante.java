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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "sst_empresa_visitante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstEmpresaVisitante.findAll", query = "SELECT s FROM SstEmpresaVisitante s")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByIdSstEmpresaVisitante", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.idSstEmpresaVisitante = :idSstEmpresaVisitante")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByNumeroDocumento", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.numeroDocumento = :numeroDocumento")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByNombre", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.nombre = :nombre")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByApellidos", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.apellidos = :apellidos")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByNivelRiesgoArl", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.nivelRiesgoArl = :nivelRiesgoArl")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByTipoSangre", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.tipoSangre = :tipoSangre")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByNombreContactoEmergencia", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.nombreContactoEmergencia = :nombreContactoEmergencia")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByTelefonoFijoContactoEmergencia", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.telefonoFijoContactoEmergencia = :telefonoFijoContactoEmergencia")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByTelefonoMovilContactoEmergencia", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.telefonoMovilContactoEmergencia = :telefonoMovilContactoEmergencia")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByPlacaVehiculo", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.placaVehiculo = :placaVehiculo")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByColorVehiculo", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.colorVehiculo = :colorVehiculo")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByQrString", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.qrString = :qrString")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByVisitanteAprobado", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.visitanteAprobado = :visitanteAprobado")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByFechaAprobacion", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.fechaAprobacion = :fechaAprobacion")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByUsername", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.username = :username")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByUserAprueba", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.userAprueba = :userAprueba")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByCreado", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.creado = :creado")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByModificado", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.modificado = :modificado")
    ,
    @NamedQuery(name = "SstEmpresaVisitante.findByEstadoReg", query = "SELECT s FROM SstEmpresaVisitante s WHERE s.estadoReg = :estadoReg")})
public class SstEmpresaVisitante implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sstEmpresaVisitante", fetch = FetchType.LAZY)
    private List<SegAseoArmamento> segPlantillaAseoArmamentoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSstEmpresaVisitante", fetch = FetchType.LAZY)
    private List<InformeSeguridad> informeSeguridadList;

    @OneToMany(mappedBy = "idSstEmpresaVisitante", fetch = FetchType.LAZY)
    private List<SstAutorizacion> sstAutorizacionList;
    @OneToMany(mappedBy = "idSstEmpresaVisitante", fetch = FetchType.LAZY)
    private List<SstEsMatEqui> sstEsMatEquiList;
    @OneToMany(mappedBy = "idSstEmpresaVisitante", fetch = FetchType.LAZY)
    private List<SstEmpresaVisitanteDocs> sstEmpresaVisitanteDocsList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_empresa_visitante")
    private Integer idSstEmpresaVisitante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numero_documento")
    private String numeroDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nivel_riesgo_arl")
    private String nivelRiesgoArl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "tipo_sangre")
    private String tipoSangre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "nombre_contacto_emergencia")
    private String nombreContactoEmergencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "telefono_fijo_contacto_emergencia")
    private String telefonoFijoContactoEmergencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "telefono_movil_contacto_emergencia")
    private String telefonoMovilContactoEmergencia;
    @Size(max = 10)
    @Column(name = "placa_vehiculo")
    private String placaVehiculo;
    @Size(max = 30)
    @Column(name = "color_vehiculo")
    private String colorVehiculo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "qr_string")
    private String qrString;
    @Basic(optional = false)
    @NotNull
    @Column(name = "visitante_aprobado")
    private int visitanteAprobado;
    @Column(name = "fecha_aprobacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAprobacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Size(max = 15)
    @Column(name = "user_aprueba")
    private String userAprueba;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_sst_empresa", referencedColumnName = "id_sst_empresa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstEmpresa idSstEmpresa;
    @JoinColumn(name = "id_sst_eps", referencedColumnName = "id_sst_eps")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstEps idSstEps;
    @JoinColumn(name = "id_sst_tipo_identificacion", referencedColumnName = "id_sst_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstTipoIdentificacion idSstTipoIdentificacion;
    @JoinColumn(name = "id_sst_vehiculo_marca", referencedColumnName = "id_sst_vehiculo_marca")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstVehiculoMarca idSstVehiculoMarca;
    @JoinColumn(name = "id_sst_vehiculo_tipo", referencedColumnName = "id_sst_vehiculo_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstVehiculoTipo idSstVehiculoTipo;
    @JoinColumn(name = "id_sst_tipo_labor", referencedColumnName = "id_sst_tipo_labor")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstTipoLabor idSstTipoLabor;
    @Size(min = 1, max = 100)
    @Column(name = "hash_string")
    private String hashString;

    @Column(name = "supervisor")
    private int supervisor;

    public SstEmpresaVisitante() {
    }

    public SstEmpresaVisitante(Integer idSstEmpresaVisitante) {
        this.idSstEmpresaVisitante = idSstEmpresaVisitante;
    }

    public SstEmpresaVisitante(Integer idSstEmpresaVisitante, String numeroDocumento, String nombre, String apellidos, String nivelRiesgoArl, String tipoSangre, String nombreContactoEmergencia, String telefonoFijoContactoEmergencia, String telefonoMovilContactoEmergencia, String placaVehiculo, String colorVehiculo, String qrString, int visitanteAprobado, String username) {
        this.idSstEmpresaVisitante = idSstEmpresaVisitante;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nivelRiesgoArl = nivelRiesgoArl;
        this.tipoSangre = tipoSangre;
        this.nombreContactoEmergencia = nombreContactoEmergencia;
        this.telefonoFijoContactoEmergencia = telefonoFijoContactoEmergencia;
        this.telefonoMovilContactoEmergencia = telefonoMovilContactoEmergencia;
        this.placaVehiculo = placaVehiculo;
        this.colorVehiculo = colorVehiculo;
        this.qrString = qrString;
        this.visitanteAprobado = visitanteAprobado;
        this.username = username;
    }

    public Integer getIdSstEmpresaVisitante() {
        return idSstEmpresaVisitante;
    }

    public void setIdSstEmpresaVisitante(Integer idSstEmpresaVisitante) {
        this.idSstEmpresaVisitante = idSstEmpresaVisitante;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNivelRiesgoArl() {
        return nivelRiesgoArl;
    }

    public void setNivelRiesgoArl(String nivelRiesgoArl) {
        this.nivelRiesgoArl = nivelRiesgoArl;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getNombreContactoEmergencia() {
        return nombreContactoEmergencia;
    }

    public void setNombreContactoEmergencia(String nombreContactoEmergencia) {
        this.nombreContactoEmergencia = nombreContactoEmergencia;
    }

    public String getTelefonoFijoContactoEmergencia() {
        return telefonoFijoContactoEmergencia;
    }

    public void setTelefonoFijoContactoEmergencia(String telefonoFijoContactoEmergencia) {
        this.telefonoFijoContactoEmergencia = telefonoFijoContactoEmergencia;
    }

    public String getTelefonoMovilContactoEmergencia() {
        return telefonoMovilContactoEmergencia;
    }

    public void setTelefonoMovilContactoEmergencia(String telefonoMovilContactoEmergencia) {
        this.telefonoMovilContactoEmergencia = telefonoMovilContactoEmergencia;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getColorVehiculo() {
        return colorVehiculo;
    }

    public void setColorVehiculo(String colorVehiculo) {
        this.colorVehiculo = colorVehiculo;
    }

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    public int getVisitanteAprobado() {
        return visitanteAprobado;
    }

    public void setVisitanteAprobado(int visitanteAprobado) {
        this.visitanteAprobado = visitanteAprobado;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAprueba() {
        return userAprueba;
    }

    public void setUserAprueba(String userAprueba) {
        this.userAprueba = userAprueba;
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

    public SstEmpresa getIdSstEmpresa() {
        return idSstEmpresa;
    }

    public void setIdSstEmpresa(SstEmpresa idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }

    public SstEps getIdSstEps() {
        return idSstEps;
    }

    public void setIdSstEps(SstEps idSstEps) {
        this.idSstEps = idSstEps;
    }

    public SstTipoIdentificacion getIdSstTipoIdentificacion() {
        return idSstTipoIdentificacion;
    }

    public void setIdSstTipoIdentificacion(SstTipoIdentificacion idSstTipoIdentificacion) {
        this.idSstTipoIdentificacion = idSstTipoIdentificacion;
    }

    public SstVehiculoMarca getIdSstVehiculoMarca() {
        return idSstVehiculoMarca;
    }

    public void setIdSstVehiculoMarca(SstVehiculoMarca idSstVehiculoMarca) {
        this.idSstVehiculoMarca = idSstVehiculoMarca;
    }

    public SstVehiculoTipo getIdSstVehiculoTipo() {
        return idSstVehiculoTipo;
    }

    public void setIdSstVehiculoTipo(SstVehiculoTipo idSstVehiculoTipo) {
        this.idSstVehiculoTipo = idSstVehiculoTipo;
    }

    public SstTipoLabor getIdSstTipoLabor() {
        return idSstTipoLabor;
    }

    public void setIdSstTipoLabor(SstTipoLabor idSstTipoLabor) {
        this.idSstTipoLabor = idSstTipoLabor;
    }

    @XmlTransient
    public List<SstEmpresaVisitanteDocs> getSstEmpresaVisitanteDocsList() {
        return sstEmpresaVisitanteDocsList;
    }

    public void setSstEmpresaVisitanteDocsList(List<SstEmpresaVisitanteDocs> sstEmpresaVisitanteDocsList) {
        this.sstEmpresaVisitanteDocsList = sstEmpresaVisitanteDocsList;
    }

    public String getHashString() {
        return hashString;
    }

    public void setHashString(String hashString) {
        this.hashString = hashString;
    }

    public int getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(int supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstEmpresaVisitante != null ? idSstEmpresaVisitante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstEmpresaVisitante)) {
            return false;
        }
        SstEmpresaVisitante other = (SstEmpresaVisitante) object;
        if ((this.idSstEmpresaVisitante == null && other.idSstEmpresaVisitante != null) || (this.idSstEmpresaVisitante != null && !this.idSstEmpresaVisitante.equals(other.idSstEmpresaVisitante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstEmpresaVisitante[ idSstEmpresaVisitante=" + idSstEmpresaVisitante + " ]";
    }

    @XmlTransient
    public List<SstEsMatEqui> getSstEsMatEquiList() {
        return sstEsMatEquiList;
    }

    public void setSstEsMatEquiList(List<SstEsMatEqui> sstEsMatEquiList) {
        this.sstEsMatEquiList = sstEsMatEquiList;
    }

    @XmlTransient
    public List<SstAutorizacion> getSstAutorizacionList() {
        return sstAutorizacionList;
    }

    public void setSstAutorizacionList(List<SstAutorizacion> sstAutorizacionList) {
        this.sstAutorizacionList = sstAutorizacionList;
    }

    @XmlTransient
    public List<InformeSeguridad> getInformeSeguridadList() {
        return informeSeguridadList;
    }

    public void setInformeSeguridadList(List<InformeSeguridad> informeSeguridadList) {
        this.informeSeguridadList = informeSeguridadList;
    }

    @XmlTransient
    public List<SegAseoArmamento> getSegPlantillaAseoArmamentoList() {
        return segPlantillaAseoArmamentoList;
    }

    public void setSegPlantillaAseoArmamentoList(List<SegAseoArmamento> segPlantillaAseoArmamentoList) {
        this.segPlantillaAseoArmamentoList = segPlantillaAseoArmamentoList;
    }

}
