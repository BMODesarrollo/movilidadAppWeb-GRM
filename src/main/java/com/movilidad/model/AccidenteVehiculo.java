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
@Table(name = "accidente_vehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteVehiculo.findAll", query = "SELECT a FROM AccidenteVehiculo a")
    , @NamedQuery(name = "AccidenteVehiculo.findByIdAccidenteVehiculo", query = "SELECT a FROM AccidenteVehiculo a WHERE a.idAccidenteVehiculo = :idAccidenteVehiculo")
    , @NamedQuery(name = "AccidenteVehiculo.findByNroVehiculos", query = "SELECT a FROM AccidenteVehiculo a WHERE a.nroVehiculos = :nroVehiculos")
    , @NamedQuery(name = "AccidenteVehiculo.findByCodigoVehiculo", query = "SELECT a FROM AccidenteVehiculo a WHERE a.codigoVehiculo = :codigoVehiculo")
    , @NamedQuery(name = "AccidenteVehiculo.findByPlaca", query = "SELECT a FROM AccidenteVehiculo a WHERE a.placa = :placa")
    , @NamedQuery(name = "AccidenteVehiculo.findByMarca", query = "SELECT a FROM AccidenteVehiculo a WHERE a.marca = :marca")
    , @NamedQuery(name = "AccidenteVehiculo.findByModelo", query = "SELECT a FROM AccidenteVehiculo a WHERE a.modelo = :modelo")
    , @NamedQuery(name = "AccidenteVehiculo.findByFechaSalidaInmovilizado", query = "SELECT a FROM AccidenteVehiculo a WHERE a.fechaSalidaInmovilizado = :fechaSalidaInmovilizado")
    , @NamedQuery(name = "AccidenteVehiculo.findByUsername", query = "SELECT a FROM AccidenteVehiculo a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteVehiculo.findByCreado", query = "SELECT a FROM AccidenteVehiculo a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteVehiculo.findByModificado", query = "SELECT a FROM AccidenteVehiculo a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteVehiculo.findByEstadoReg", query = "SELECT a FROM AccidenteVehiculo a WHERE a.estadoReg = :estadoReg")})
public class AccidenteVehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_vehiculo")
    private Integer idAccidenteVehiculo;
    @Column(name = "nro_vehiculos")
    private Integer nroVehiculos;
    @Size(max = 12)
    @Column(name = "codigo_vehiculo")
    private String codigoVehiculo;
    @Column(name = "placa")
    private String placa;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
    @Column(name = "modelo")
    private Integer modelo;
    @Column(name = "fecha_salida_inmovilizado")
    @Temporal(TemporalType.DATE)
    private Date fechaSalidaInmovilizado;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaccion")
    private String observaccion;
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
    @OneToMany(mappedBy = "idAccidenteVehiculo", fetch = FetchType.LAZY)
    private List<AccidenteConductor> accidenteConductorList;
    @JoinColumn(name = "id_acc_empresa_operadora", referencedColumnName = "id_acc_empresa_operadora")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccEmpresaOperadora idAccEmpresaOperadora;
    @JoinColumn(name = "id_acc_inmovilizado", referencedColumnName = "id_acc_inmovilizado")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInmovilizado idAccInmovilizado;
    @JoinColumn(name = "id_acc_tipo_vehiculo", referencedColumnName = "id_acc_tipo_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoVehiculo idAccTipoVehiculo;
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;
    @OneToMany(mappedBy = "idAccidenteVehiculo", fetch = FetchType.LAZY)
    private List<AccidenteVictima> accidenteVictimaList;

    public AccidenteVehiculo() {
    }

    public AccidenteVehiculo(Integer idAccidenteVehiculo) {
        this.idAccidenteVehiculo = idAccidenteVehiculo;
    }

    public Integer getIdAccidenteVehiculo() {
        return idAccidenteVehiculo;
    }

    public void setIdAccidenteVehiculo(Integer idAccidenteVehiculo) {
        this.idAccidenteVehiculo = idAccidenteVehiculo;
    }

    public Integer getNroVehiculos() {
        return nroVehiculos;
    }

    public void setNroVehiculos(Integer nroVehiculos) {
        this.nroVehiculos = nroVehiculos;
    }

    public String getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(String codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
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

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public Date getFechaSalidaInmovilizado() {
        return fechaSalidaInmovilizado;
    }

    public void setFechaSalidaInmovilizado(Date fechaSalidaInmovilizado) {
        this.fechaSalidaInmovilizado = fechaSalidaInmovilizado;
    }

    public String getObservaccion() {
        return observaccion;
    }

    public void setObservaccion(String observaccion) {
        this.observaccion = observaccion;
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

    @XmlTransient
    public List<AccidenteConductor> getAccidenteConductorList() {
        return accidenteConductorList;
    }

    public void setAccidenteConductorList(List<AccidenteConductor> accidenteConductorList) {
        this.accidenteConductorList = accidenteConductorList;
    }

    public AccEmpresaOperadora getIdAccEmpresaOperadora() {
        return idAccEmpresaOperadora;
    }

    public void setIdAccEmpresaOperadora(AccEmpresaOperadora idAccEmpresaOperadora) {
        this.idAccEmpresaOperadora = idAccEmpresaOperadora;
    }

    public AccInmovilizado getIdAccInmovilizado() {
        return idAccInmovilizado;
    }

    public void setIdAccInmovilizado(AccInmovilizado idAccInmovilizado) {
        this.idAccInmovilizado = idAccInmovilizado;
    }

    public AccTipoVehiculo getIdAccTipoVehiculo() {
        return idAccTipoVehiculo;
    }

    public void setIdAccTipoVehiculo(AccTipoVehiculo idAccTipoVehiculo) {
        this.idAccTipoVehiculo = idAccTipoVehiculo;
    }

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    @XmlTransient
    public List<AccidenteVictima> getAccidenteVictimaList() {
        return accidenteVictimaList;
    }

    public void setAccidenteVictimaList(List<AccidenteVictima> accidenteVictimaList) {
        this.accidenteVictimaList = accidenteVictimaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteVehiculo != null ? idAccidenteVehiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteVehiculo)) {
            return false;
        }
        AccidenteVehiculo other = (AccidenteVehiculo) object;
        if ((this.idAccidenteVehiculo == null && other.idAccidenteVehiculo != null) || (this.idAccidenteVehiculo != null && !this.idAccidenteVehiculo.equals(other.idAccidenteVehiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteVehiculo[ idAccidenteVehiculo=" + idAccidenteVehiculo + " ]";
    }
    
}
