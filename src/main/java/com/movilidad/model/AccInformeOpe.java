/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_informe_ope")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeOpe.findAll", query = "SELECT a FROM AccInformeOpe a")
    , @NamedQuery(name = "AccInformeOpe.findByIdAccInformeOpe", query = "SELECT a FROM AccInformeOpe a WHERE a.idAccInformeOpe = :idAccInformeOpe")
    , @NamedQuery(name = "AccInformeOpe.findByCondicion", query = "SELECT a FROM AccInformeOpe a WHERE a.condicion = :condicion")
    , @NamedQuery(name = "AccInformeOpe.findByLatitude", query = "SELECT a FROM AccInformeOpe a WHERE a.latitude = :latitude")
    , @NamedQuery(name = "AccInformeOpe.findByLongitude", query = "SELECT a FROM AccInformeOpe a WHERE a.longitude = :longitude")
    , @NamedQuery(name = "AccInformeOpe.findByFecha", query = "SELECT a FROM AccInformeOpe a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AccInformeOpe.findByHoraOcurrencia", query = "SELECT a FROM AccInformeOpe a WHERE a.horaOcurrencia = :horaOcurrencia")
    , @NamedQuery(name = "AccInformeOpe.findByHoraLevantamiento", query = "SELECT a FROM AccInformeOpe a WHERE a.horaLevantamiento = :horaLevantamiento")
    , @NamedQuery(name = "AccInformeOpe.findByArea", query = "SELECT a FROM AccInformeOpe a WHERE a.area = :area")
    , @NamedQuery(name = "AccInformeOpe.findByAgente", query = "SELECT a FROM AccInformeOpe a WHERE a.agente = :agente")
    , @NamedQuery(name = "AccInformeOpe.findByPathCroquis", query = "SELECT a FROM AccInformeOpe a WHERE a.pathCroquis = :pathCroquis")
    , @NamedQuery(name = "AccInformeOpe.findByAnexoNombres", query = "SELECT a FROM AccInformeOpe a WHERE a.anexoNombres = :anexoNombres")
    , @NamedQuery(name = "AccInformeOpe.findByAnexoPlaca", query = "SELECT a FROM AccInformeOpe a WHERE a.anexoPlaca = :anexoPlaca")
    , @NamedQuery(name = "AccInformeOpe.findByAnexoCorresponde", query = "SELECT a FROM AccInformeOpe a WHERE a.anexoCorresponde = :anexoCorresponde")
    , @NamedQuery(name = "AccInformeOpe.findByAnexoEntidad", query = "SELECT a FROM AccInformeOpe a WHERE a.anexoEntidad = :anexoEntidad")
    , @NamedQuery(name = "AccInformeOpe.findByPathAnexo", query = "SELECT a FROM AccInformeOpe a WHERE a.pathAnexo = :pathAnexo")
    , @NamedQuery(name = "AccInformeOpe.findByUsername", query = "SELECT a FROM AccInformeOpe a WHERE a.username = :username")
    , @NamedQuery(name = "AccInformeOpe.findByCreado", query = "SELECT a FROM AccInformeOpe a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccInformeOpe.findByModificado", query = "SELECT a FROM AccInformeOpe a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccInformeOpe.findByEstadoReg", query = "SELECT a FROM AccInformeOpe a WHERE a.estadoReg = :estadoReg")
    , @NamedQuery(name = "AccInformeOpe.findByTotalHeridos", query = "SELECT a FROM AccInformeOpe a WHERE a.totalHeridos = :totalHeridos")
    , @NamedQuery(name = "AccInformeOpe.findByTotalMuertos", query = "SELECT a FROM AccInformeOpe a WHERE a.totalMuertos = :totalMuertos")})
public class AccInformeOpe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_ope")
    private Integer idAccInformeOpe;
    @Column(name = "condicion")
    private Integer condicion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Lob
    @Size(max = 65535)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 8)
    @Column(name = "hora_ocurrencia")
    private String horaOcurrencia;
    @Size(max = 8)
    @Column(name = "hora_levantamiento")
    private String horaLevantamiento;
    @Column(name = "area")
    private Integer area;
    @Column(name = "agente")
    private Integer agente;
    @Size(max = 255)
    @Column(name = "path_croquis")
    private String pathCroquis;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Lob
    @Size(max = 65535)
    @Column(name = "version_operador")
    private String versionOperador;
    @Lob
    @Size(max = 65535)
    @Column(name = "firma")
    private String firma;
    @Lob
    @Size(max = 65535)
    @Column(name = "anexos")
    private String anexos;
    @Size(max = 60)
    @Column(name = "anexo_nombres")
    private String anexoNombres;
    @Size(max = 15)
    @Column(name = "anexo_placa")
    private String anexoPlaca;
    @Size(max = 45)
    @Column(name = "anexo_corresponde")
    private String anexoCorresponde;
    @Size(max = 45)
    @Column(name = "anexo_entidad")
    private String anexoEntidad;
    @Lob
    @Size(max = 65535)
    @Column(name = "anexo_firma")
    private String anexoFirma;
    @Size(max = 255)
    @Column(name = "path_anexo")
    private String pathAnexo;
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
    @Size(max = 15)
    @Column(name = "total_heridos")
    private String totalHeridos;
    @Size(max = 15)
    @Column(name = "total_muertos")
    private String totalMuertos;
    @OneToMany(mappedBy = "idAccInformeOpe", fetch = FetchType.LAZY)
    private List<AccInformeVehCond> accInformeVehCondList;
    @OneToMany(mappedBy = "idAccInformeOpe", fetch = FetchType.LAZY)
    private List<AccInformeOpeCausalidad> accInformeOpeCausalidadList;
    @OneToMany(mappedBy = "idAccInformeOpe", fetch = FetchType.LAZY)
    private List<AccInformeVic> accInformeVicList;
    @JoinColumn(name = "id_acc_via_condicion", referencedColumnName = "id_acc_via_condicion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaCondicion idAccViaCondicion;
    @JoinColumn(name = "id_acc_clase", referencedColumnName = "id_acc_clase")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccClase idAccClase;
    @JoinColumn(name = "id_acc_via_calzada", referencedColumnName = "id_acc_via_calzadas")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaCalzadas idAccViaCalzada;
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;
    @JoinColumn(name = "id_operador_principal", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idOperadorPrincipal;
    @JoinColumn(name = "id_acc_via_semaforo", referencedColumnName = "id_acc_via_semaforo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaSemaforo idAccViaSemaforo;
    @JoinColumn(name = "id_acc_via_troncal", referencedColumnName = "id_acc_via_troncal")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaTroncal idAccViaTroncal;
    @JoinColumn(name = "id_acc_via_carriles", referencedColumnName = "id_acc_via_carriles")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaCarriles idAccViaCarriles;
    @JoinColumn(name = "id_acc_via_clima", referencedColumnName = "id_acc_via_clima")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaClima idAccViaClima;
    @JoinColumn(name = "id_acc_via_diseno", referencedColumnName = "id_acc_via_diseno")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaDiseno idAccViaDiseno;
    @JoinColumn(name = "id_acc_via_estado", referencedColumnName = "id_acc_via_estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaEstado idAccViaEstado;
    @JoinColumn(name = "id_acc_via_geometrica", referencedColumnName = "id_acc_via_geometrica")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaGeometrica idAccViaGeometrica;
    @JoinColumn(name = "id_acc_via_iluminacion", referencedColumnName = "id_acc_via_iluminacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaIluminacion idAccViaIluminacion;
    @JoinColumn(name = "id_acc_via_material", referencedColumnName = "id_acc_via_material")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaMaterial idAccViaMaterial;
    @JoinColumn(name = "id_acc_via_sector", referencedColumnName = "id_acc_via_sector")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaSector idAccViaSector;
    @JoinColumn(name = "id_acc_via_utilizacion", referencedColumnName = "id_acc_via_utilizacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaUtilizacion idAccViaUtilizacion;
    @JoinColumn(name = "id_acc_via_zona", referencedColumnName = "id_acc_via_zona")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaZona idAccViaZona;
    @OneToMany(mappedBy = "idAccInformeOpe", fetch = FetchType.LAZY)
    private List<AccInformeTestigo> accInformeTestigoList;

    public AccInformeOpe() {
    }

    public AccInformeOpe(Integer idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }

    public Integer getIdAccInformeOpe() {
        return idAccInformeOpe;
    }

    public void setIdAccInformeOpe(Integer idAccInformeOpe) {
        this.idAccInformeOpe = idAccInformeOpe;
    }

    public Integer getCondicion() {
        return condicion;
    }

    public void setCondicion(Integer condicion) {
        this.condicion = condicion;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoraOcurrencia() {
        return horaOcurrencia;
    }

    public void setHoraOcurrencia(String horaOcurrencia) {
        this.horaOcurrencia = horaOcurrencia;
    }

    public String getHoraLevantamiento() {
        return horaLevantamiento;
    }

    public void setHoraLevantamiento(String horaLevantamiento) {
        this.horaLevantamiento = horaLevantamiento;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getAgente() {
        return agente;
    }

    public void setAgente(Integer agente) {
        this.agente = agente;
    }

    public String getPathCroquis() {
        return pathCroquis;
    }

    public void setPathCroquis(String pathCroquis) {
        this.pathCroquis = pathCroquis;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getVersionOperador() {
        return versionOperador;
    }

    public void setVersionOperador(String versionOperador) {
        this.versionOperador = versionOperador;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getAnexos() {
        return anexos;
    }

    public void setAnexos(String anexos) {
        this.anexos = anexos;
    }

    public String getAnexoNombres() {
        return anexoNombres;
    }

    public void setAnexoNombres(String anexoNombres) {
        this.anexoNombres = anexoNombres;
    }

    public String getAnexoPlaca() {
        return anexoPlaca;
    }

    public void setAnexoPlaca(String anexoPlaca) {
        this.anexoPlaca = anexoPlaca;
    }

    public String getAnexoCorresponde() {
        return anexoCorresponde;
    }

    public void setAnexoCorresponde(String anexoCorresponde) {
        this.anexoCorresponde = anexoCorresponde;
    }

    public String getAnexoEntidad() {
        return anexoEntidad;
    }

    public void setAnexoEntidad(String anexoEntidad) {
        this.anexoEntidad = anexoEntidad;
    }

    public String getAnexoFirma() {
        return anexoFirma;
    }

    public void setAnexoFirma(String anexoFirma) {
        this.anexoFirma = anexoFirma;
    }

    public String getPathAnexo() {
        return pathAnexo;
    }

    public void setPathAnexo(String pathAnexo) {
        this.pathAnexo = pathAnexo;
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

    public String getTotalHeridos() {
        return totalHeridos;
    }

    public void setTotalHeridos(String totalHeridos) {
        this.totalHeridos = totalHeridos;
    }

    public String getTotalMuertos() {
        return totalMuertos;
    }

    public void setTotalMuertos(String totalMuertos) {
        this.totalMuertos = totalMuertos;
    }

    @XmlTransient
    public List<AccInformeVehCond> getAccInformeVehCondList() {
        return accInformeVehCondList;
    }

    public void setAccInformeVehCondList(List<AccInformeVehCond> accInformeVehCondList) {
        this.accInformeVehCondList = accInformeVehCondList;
    }

    @XmlTransient
    public List<AccInformeOpeCausalidad> getAccInformeOpeCausalidadList() {
        return accInformeOpeCausalidadList;
    }

    public void setAccInformeOpeCausalidadList(List<AccInformeOpeCausalidad> accInformeOpeCausalidadList) {
        this.accInformeOpeCausalidadList = accInformeOpeCausalidadList;
    }

    @XmlTransient
    public List<AccInformeVic> getAccInformeVicList() {
        return accInformeVicList;
    }

    public void setAccInformeVicList(List<AccInformeVic> accInformeVicList) {
        this.accInformeVicList = accInformeVicList;
    }

    public AccViaCondicion getIdAccViaCondicion() {
        return idAccViaCondicion;
    }

    public void setIdAccViaCondicion(AccViaCondicion idAccViaCondicion) {
        this.idAccViaCondicion = idAccViaCondicion;
    }

    public AccClase getIdAccClase() {
        return idAccClase;
    }

    public void setIdAccClase(AccClase idAccClase) {
        this.idAccClase = idAccClase;
    }

    public AccViaCalzadas getIdAccViaCalzada() {
        return idAccViaCalzada;
    }

    public void setIdAccViaCalzada(AccViaCalzadas idAccViaCalzada) {
        this.idAccViaCalzada = idAccViaCalzada;
    }

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    public Empleado getIdOperadorPrincipal() {
        return idOperadorPrincipal;
    }

    public void setIdOperadorPrincipal(Empleado idOperadorPrincipal) {
        this.idOperadorPrincipal = idOperadorPrincipal;
    }

    public AccViaSemaforo getIdAccViaSemaforo() {
        return idAccViaSemaforo;
    }

    public void setIdAccViaSemaforo(AccViaSemaforo idAccViaSemaforo) {
        this.idAccViaSemaforo = idAccViaSemaforo;
    }

    public AccViaTroncal getIdAccViaTroncal() {
        return idAccViaTroncal;
    }

    public void setIdAccViaTroncal(AccViaTroncal idAccViaTroncal) {
        this.idAccViaTroncal = idAccViaTroncal;
    }

    public AccViaCarriles getIdAccViaCarriles() {
        return idAccViaCarriles;
    }

    public void setIdAccViaCarriles(AccViaCarriles idAccViaCarriles) {
        this.idAccViaCarriles = idAccViaCarriles;
    }

    public AccViaClima getIdAccViaClima() {
        return idAccViaClima;
    }

    public void setIdAccViaClima(AccViaClima idAccViaClima) {
        this.idAccViaClima = idAccViaClima;
    }

    public AccViaDiseno getIdAccViaDiseno() {
        return idAccViaDiseno;
    }

    public void setIdAccViaDiseno(AccViaDiseno idAccViaDiseno) {
        this.idAccViaDiseno = idAccViaDiseno;
    }

    public AccViaEstado getIdAccViaEstado() {
        return idAccViaEstado;
    }

    public void setIdAccViaEstado(AccViaEstado idAccViaEstado) {
        this.idAccViaEstado = idAccViaEstado;
    }

    public AccViaGeometrica getIdAccViaGeometrica() {
        return idAccViaGeometrica;
    }

    public void setIdAccViaGeometrica(AccViaGeometrica idAccViaGeometrica) {
        this.idAccViaGeometrica = idAccViaGeometrica;
    }

    public AccViaIluminacion getIdAccViaIluminacion() {
        return idAccViaIluminacion;
    }

    public void setIdAccViaIluminacion(AccViaIluminacion idAccViaIluminacion) {
        this.idAccViaIluminacion = idAccViaIluminacion;
    }

    public AccViaMaterial getIdAccViaMaterial() {
        return idAccViaMaterial;
    }

    public void setIdAccViaMaterial(AccViaMaterial idAccViaMaterial) {
        this.idAccViaMaterial = idAccViaMaterial;
    }

    public AccViaSector getIdAccViaSector() {
        return idAccViaSector;
    }

    public void setIdAccViaSector(AccViaSector idAccViaSector) {
        this.idAccViaSector = idAccViaSector;
    }

    public AccViaUtilizacion getIdAccViaUtilizacion() {
        return idAccViaUtilizacion;
    }

    public void setIdAccViaUtilizacion(AccViaUtilizacion idAccViaUtilizacion) {
        this.idAccViaUtilizacion = idAccViaUtilizacion;
    }

    public AccViaZona getIdAccViaZona() {
        return idAccViaZona;
    }

    public void setIdAccViaZona(AccViaZona idAccViaZona) {
        this.idAccViaZona = idAccViaZona;
    }

    @XmlTransient
    public List<AccInformeTestigo> getAccInformeTestigoList() {
        return accInformeTestigoList;
    }

    public void setAccInformeTestigoList(List<AccInformeTestigo> accInformeTestigoList) {
        this.accInformeTestigoList = accInformeTestigoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInformeOpe != null ? idAccInformeOpe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeOpe)) {
            return false;
        }
        AccInformeOpe other = (AccInformeOpe) object;
        if ((this.idAccInformeOpe == null && other.idAccInformeOpe != null) || (this.idAccInformeOpe != null && !this.idAccInformeOpe.equals(other.idAccInformeOpe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeOpe[ idAccInformeOpe=" + idAccInformeOpe + " ]";
    }
    
}
