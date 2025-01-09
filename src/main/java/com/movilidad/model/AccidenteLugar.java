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
@Table(name = "accidente_lugar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteLugar.findAll", query = "SELECT a FROM AccidenteLugar a")
    , @NamedQuery(name = "AccidenteLugar.findByIdAccidenteLugar", query = "SELECT a FROM AccidenteLugar a WHERE a.idAccidenteLugar = :idAccidenteLugar")
    , @NamedQuery(name = "AccidenteLugar.findByLatitude", query = "SELECT a FROM AccidenteLugar a WHERE a.latitude = :latitude")
    , @NamedQuery(name = "AccidenteLugar.findByLongitude", query = "SELECT a FROM AccidenteLugar a WHERE a.longitude = :longitude")
    , @NamedQuery(name = "AccidenteLugar.findByUsername", query = "SELECT a FROM AccidenteLugar a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteLugar.findByCreado", query = "SELECT a FROM AccidenteLugar a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteLugar.findByModificado", query = "SELECT a FROM AccidenteLugar a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteLugar.findByEstadoReg", query = "SELECT a FROM AccidenteLugar a WHERE a.estadoReg = :estadoReg")})
public class AccidenteLugar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_lugar")
    private Integer idAccidenteLugar;
    @Lob
    @Size(max = 65535)
    @Column(name = "direccion")
    private String direccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_acc_via_carriles", referencedColumnName = "id_acc_via_carriles")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaCarriles idAccViaCarriles;
    @JoinColumn(name = "id_acc_sentido", referencedColumnName = "id_acc_sentido")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccSentido idAccSentido;
    @JoinColumn(name = "id_acc_via_calzadas", referencedColumnName = "id_acc_via_calzadas")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaCalzadas idAccViaCalzadas;
    @JoinColumn(name = "id_acc_via_clase", referencedColumnName = "id_acc_via_clase")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaClase idAccViaClase;
    @JoinColumn(name = "id_acc_via_clima", referencedColumnName = "id_acc_via_clima")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaClima idAccViaClima;
    @JoinColumn(name = "id_acc_via_condiciones", referencedColumnName = "id_acc_via_condicion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaCondicion idAccViaCondiciones;
    @JoinColumn(name = "id_acc_via_semaforo", referencedColumnName = "id_acc_via_semaforo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaSemaforo idAccViaSemaforo;
    @JoinColumn(name = "id_acc_via_diseno", referencedColumnName = "id_acc_via_diseno")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaDiseno idAccViaDiseno;
    @JoinColumn(name = "id_acc_via_estado", referencedColumnName = "id_acc_via_estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaEstado idAccViaEstado;
    @JoinColumn(name = "id_acc_via_sector", referencedColumnName = "id_acc_via_sector")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaSector idAccViaSector;
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;
    @JoinColumn(name = "id_acc_via_geometrica", referencedColumnName = "id_acc_via_geometrica")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaGeometrica idAccViaGeometrica;
    @JoinColumn(name = "id_acc_via_zona", referencedColumnName = "id_acc_via_zona")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaZona idAccViaZona;
    @JoinColumn(name = "id_acc_via_iluminacion", referencedColumnName = "id_acc_via_iluminacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaIluminacion idAccViaIluminacion;
    @JoinColumn(name = "id_acc_via_zonat", referencedColumnName = "id_acc_via_zonat")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaZonat idAccViaZonat;
    @JoinColumn(name = "id_acc_via_mixta", referencedColumnName = "id_acc_via_mixta")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaMixta idAccViaMixta;
    @JoinColumn(name = "id_acc_via_utilizacion", referencedColumnName = "id_acc_via_utilizacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaUtilizacion idAccViaUtilizacion;
    @JoinColumn(name = "id_acc_via_visual", referencedColumnName = "id_acc_via_visual")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaVisual idAccViaVisual;
    @JoinColumn(name = "id_prg_stoppoint", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPrgStoppoint;
    @JoinColumn(name = "id_acc_via_troncal", referencedColumnName = "id_acc_via_troncal")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaTroncal idAccViaTroncal;
    @JoinColumn(name = "id_acc_via_material", referencedColumnName = "id_acc_via_material")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccViaMaterial idAccViaMaterial;
    @OneToMany(mappedBy = "idAccidenteLugar", fetch = FetchType.LAZY)
    private List<AccidenteLugarDemar> accidenteLugarDemarList;

    public AccidenteLugar() {
    }

    public AccidenteLugar(Integer idAccidenteLugar) {
        this.idAccidenteLugar = idAccidenteLugar;
    }

    public Integer getIdAccidenteLugar() {
        return idAccidenteLugar;
    }

    public void setIdAccidenteLugar(Integer idAccidenteLugar) {
        this.idAccidenteLugar = idAccidenteLugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public AccViaCarriles getIdAccViaCarriles() {
        return idAccViaCarriles;
    }

    public void setIdAccViaCarriles(AccViaCarriles idAccViaCarriles) {
        this.idAccViaCarriles = idAccViaCarriles;
    }

    public AccSentido getIdAccSentido() {
        return idAccSentido;
    }

    public void setIdAccSentido(AccSentido idAccSentido) {
        this.idAccSentido = idAccSentido;
    }

    public AccViaCalzadas getIdAccViaCalzadas() {
        return idAccViaCalzadas;
    }

    public void setIdAccViaCalzadas(AccViaCalzadas idAccViaCalzadas) {
        this.idAccViaCalzadas = idAccViaCalzadas;
    }

    public AccViaClase getIdAccViaClase() {
        return idAccViaClase;
    }

    public void setIdAccViaClase(AccViaClase idAccViaClase) {
        this.idAccViaClase = idAccViaClase;
    }

    public AccViaClima getIdAccViaClima() {
        return idAccViaClima;
    }

    public void setIdAccViaClima(AccViaClima idAccViaClima) {
        this.idAccViaClima = idAccViaClima;
    }

    public AccViaCondicion getIdAccViaCondiciones() {
        return idAccViaCondiciones;
    }

    public void setIdAccViaCondiciones(AccViaCondicion idAccViaCondiciones) {
        this.idAccViaCondiciones = idAccViaCondiciones;
    }

    public AccViaSemaforo getIdAccViaSemaforo() {
        return idAccViaSemaforo;
    }

    public void setIdAccViaSemaforo(AccViaSemaforo idAccViaSemaforo) {
        this.idAccViaSemaforo = idAccViaSemaforo;
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

    public AccViaSector getIdAccViaSector() {
        return idAccViaSector;
    }

    public void setIdAccViaSector(AccViaSector idAccViaSector) {
        this.idAccViaSector = idAccViaSector;
    }

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    public AccViaGeometrica getIdAccViaGeometrica() {
        return idAccViaGeometrica;
    }

    public void setIdAccViaGeometrica(AccViaGeometrica idAccViaGeometrica) {
        this.idAccViaGeometrica = idAccViaGeometrica;
    }

    public AccViaZona getIdAccViaZona() {
        return idAccViaZona;
    }

    public void setIdAccViaZona(AccViaZona idAccViaZona) {
        this.idAccViaZona = idAccViaZona;
    }

    public AccViaIluminacion getIdAccViaIluminacion() {
        return idAccViaIluminacion;
    }

    public void setIdAccViaIluminacion(AccViaIluminacion idAccViaIluminacion) {
        this.idAccViaIluminacion = idAccViaIluminacion;
    }

    public AccViaZonat getIdAccViaZonat() {
        return idAccViaZonat;
    }

    public void setIdAccViaZonat(AccViaZonat idAccViaZonat) {
        this.idAccViaZonat = idAccViaZonat;
    }

    public AccViaMixta getIdAccViaMixta() {
        return idAccViaMixta;
    }

    public void setIdAccViaMixta(AccViaMixta idAccViaMixta) {
        this.idAccViaMixta = idAccViaMixta;
    }

    public AccViaUtilizacion getIdAccViaUtilizacion() {
        return idAccViaUtilizacion;
    }

    public void setIdAccViaUtilizacion(AccViaUtilizacion idAccViaUtilizacion) {
        this.idAccViaUtilizacion = idAccViaUtilizacion;
    }

    public AccViaVisual getIdAccViaVisual() {
        return idAccViaVisual;
    }

    public void setIdAccViaVisual(AccViaVisual idAccViaVisual) {
        this.idAccViaVisual = idAccViaVisual;
    }

    public PrgStopPoint getIdPrgStoppoint() {
        return idPrgStoppoint;
    }

    public void setIdPrgStoppoint(PrgStopPoint idPrgStoppoint) {
        this.idPrgStoppoint = idPrgStoppoint;
    }

    public AccViaTroncal getIdAccViaTroncal() {
        return idAccViaTroncal;
    }

    public void setIdAccViaTroncal(AccViaTroncal idAccViaTroncal) {
        this.idAccViaTroncal = idAccViaTroncal;
    }

    public AccViaMaterial getIdAccViaMaterial() {
        return idAccViaMaterial;
    }

    public void setIdAccViaMaterial(AccViaMaterial idAccViaMaterial) {
        this.idAccViaMaterial = idAccViaMaterial;
    }

    @XmlTransient
    public List<AccidenteLugarDemar> getAccidenteLugarDemarList() {
        return accidenteLugarDemarList;
    }

    public void setAccidenteLugarDemarList(List<AccidenteLugarDemar> accidenteLugarDemarList) {
        this.accidenteLugarDemarList = accidenteLugarDemarList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteLugar != null ? idAccidenteLugar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteLugar)) {
            return false;
        }
        AccidenteLugar other = (AccidenteLugar) object;
        if ((this.idAccidenteLugar == null && other.idAccidenteLugar != null) || (this.idAccidenteLugar != null && !this.idAccidenteLugar.equals(other.idAccidenteLugar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteLugar[ idAccidenteLugar=" + idAccidenteLugar + " ]";
    }
    
}
