/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.dto.ParrillaDTO;
import com.movilidad.util.beans.ResEstadoActFlota;
import com.movilidad.util.beans.SumDistanciaEntradaPatioDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "vehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v"),
    @NamedQuery(name = "Vehiculo.findByIdVehiculo", query = "SELECT v FROM Vehiculo v WHERE v.idVehiculo = :idVehiculo"),
    @NamedQuery(name = "Vehiculo.findByCodigo", query = "SELECT v FROM Vehiculo v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "Vehiculo.findByPlaca", query = "SELECT v FROM Vehiculo v WHERE v.placa = :placa"),
    @NamedQuery(name = "Vehiculo.findByModelo", query = "SELECT v FROM Vehiculo v WHERE v.modelo = :modelo"),
    @NamedQuery(name = "Vehiculo.findByNumeroMotor", query = "SELECT v FROM Vehiculo v WHERE v.numeroMotor = :numeroMotor"),
    @NamedQuery(name = "Vehiculo.findByNumeroChasis", query = "SELECT v FROM Vehiculo v WHERE v.numeroChasis = :numeroChasis"),
    @NamedQuery(name = "Vehiculo.findByNumeroCarroceria", query = "SELECT v FROM Vehiculo v WHERE v.numeroCarroceria = :numeroCarroceria"),
    @NamedQuery(name = "Vehiculo.findByNumeroSerie", query = "SELECT v FROM Vehiculo v WHERE v.numeroSerie = :numeroSerie"),
    @NamedQuery(name = "Vehiculo.findByCapacidad", query = "SELECT v FROM Vehiculo v WHERE v.capacidad = :capacidad"),
    @NamedQuery(name = "Vehiculo.findByCilindraje", query = "SELECT v FROM Vehiculo v WHERE v.cilindraje = :cilindraje"),
    @NamedQuery(name = "Vehiculo.findByOdometroInicial", query = "SELECT v FROM Vehiculo v WHERE v.odometroInicial = :odometroInicial"),
    @NamedQuery(name = "Vehiculo.findByOdometro", query = "SELECT v FROM Vehiculo v WHERE v.odometro = :odometro"),
    @NamedQuery(name = "Vehiculo.findByFechaVinculacion", query = "SELECT v FROM Vehiculo v WHERE v.fechaVinculacion = :fechaVinculacion"),
    @NamedQuery(name = "Vehiculo.findByColor", query = "SELECT v FROM Vehiculo v WHERE v.color = :color"),
    @NamedQuery(name = "Vehiculo.findByUsername", query = "SELECT v FROM Vehiculo v WHERE v.username = :username"),
    @NamedQuery(name = "Vehiculo.findByCreado", query = "SELECT v FROM Vehiculo v WHERE v.creado = :creado"),
    @NamedQuery(name = "Vehiculo.findByModificado", query = "SELECT v FROM Vehiculo v WHERE v.modificado = :modificado"),
    @NamedQuery(name = "Vehiculo.findByEstadoReg", query = "SELECT v FROM Vehiculo v WHERE v.estadoReg = :estadoReg")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "ResEstadoActFlotaMapping",
            classes = {
                @ConstructorResult(targetClass = ResEstadoActFlota.class,
                        columns = {
                            @ColumnResult(name = "id_gop_unidad_funcional"),
                            @ColumnResult(name = "nombre"),
                            @ColumnResult(name = "vehi_reg_art", type = Long.class),
                            @ColumnResult(name = "vehi_reg_bi", type = Long.class),
                            @ColumnResult(name = "total_vehi_reg", type = BigDecimal.class),
                            @ColumnResult(name = "vehi_nov_art", type = Long.class),
                            @ColumnResult(name = "vehi_nov_bi", type = Long.class),
                            @ColumnResult(name = "total_vehi_nov", type = BigDecimal.class),
                            @ColumnResult(name = "disp_art", type = Double.class),
                            @ColumnResult(name = "disp_bi", type = Double.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "ParrillaDTOMapping",
            classes = {
                @ConstructorResult(targetClass = ParrillaDTO.class,
                        columns = {
                            @ColumnResult(name = "id"),
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "fecha_hora_evento_accidente", type = String.class),
                            @ColumnResult(name = "fecha_hora_evento_varado", type = String.class),
                            @ColumnResult(name = "fecha_hora_evento_uri", type = String.class),
                            @ColumnResult(name = "id_acc_desplaza_a", type = Integer.class),
                            @ColumnResult(name = "desplaza_a", type = String.class),
                            @ColumnResult(name = "id_accidente", type = Integer.class),
                            @ColumnResult(name = "codigo"),
                            @ColumnResult(name = "estado"),
                            @ColumnResult(name = "tipo_vehiculo"),
                            @ColumnResult(name = "tipo_novedad", type = String.class),
                            @ColumnResult(name = "ruta", type = String.class),
                            @ColumnResult(name = "is_acc_asistido"),
                            @ColumnResult(name = "is_accidente"),
                            @ColumnResult(name = "is_uri"),
                            @ColumnResult(name = "is_varado"),
                            @ColumnResult(name = "dias_inoperativo", type = Integer.class),
                            @ColumnResult(name = "causa_falla", type = String.class),
                            @ColumnResult(name = "nombre_empleado", type = String.class),
                            @ColumnResult(name = "codigo_tm", type = String.class),
                            @ColumnResult(name = "telefono", type = String.class),
                            @ColumnResult(name = "ubicacion", type = Integer.class),
                            @ColumnResult(name = "is_mtto_patio", type = Integer.class)
                        }
                )
            }),})

public class Vehiculo implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo")
    private List<Hallazgo> hallazgosList;

    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<DispConciliacionDet> dispConciliacionDetList;

    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<VehiculoEstadoHistorico> vehiculoEstadoHistoricoList;

    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<SegInoperativos> segInoperativosList;

    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<ContableCtaVehiculo> contableCtaVehiculoList;

    @Column(name = "odometro")
    private int odometro;
    @OneToMany(mappedBy = "idVehiculoAuditado", fetch = FetchType.LAZY)
    private List<AuditoriaRealizadoPor> auditoriaRealizadoPorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo")
    private List<VehiculoOdometro> vehiculoOdometroList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo", fetch = FetchType.EAGER)
    private List<MttoAsig> mttoAsigList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo")
    private List<Lavado> lavadoList;
    @OneToMany(mappedBy = "idOldVehiculo", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList1;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<MttoNovedad> mttoNovedadList;

    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;
    @OneToMany(mappedBy = "idVehiculo")
    private List<KmConciliado> kmConciliadoList;
    @OneToMany(mappedBy = "idVehiculo")
    private List<PrgVehiculoInactivo> prgVehiculoInactivoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 6)
    @Column(name = "placa")
    private String placa;
    @Size(max = 50)
    @Column(name = "modelo")
    private String modelo;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 50)
    @Column(name = "numero_motor")
    private String numeroMotor;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 50)
    @Column(name = "numero_chasis")
    private String numeroChasis;
//    @Size(max = 50)
    @Column(name = "numero_carroceria")
    private String numeroCarroceria;
    @Size(max = 70)
    @Column(name = "numero_serie")
    private String numeroSerie;
    @Column(name = "capacidad")
    private Integer capacidad;
    @Column(name = "cilindraje")
    private Integer cilindraje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "odometro_inicial")
    private int odometroInicial;
    @Column(name = "fecha_vinculacion")
    @Temporal(TemporalType.DATE)
    private Date fechaVinculacion;
    @Size(max = 50)
    @Column(name = "color")
    private String color;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<Multa> multaList;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<PrgBusUbicacion> prgBusUbicacionList;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<PrgAsignacion> prgAsignacionList;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<VehiculoIdf> vehiculoIdfList;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<Novedad> novedadList;
    @OneToMany(mappedBy = "oldVehiculo", fetch = FetchType.LAZY)
    private List<Novedad> novedadList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<OperacionKmTacografo> operacionKmTacografoList;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<TanqueoRendimiento> tanqueoRendimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<VehiculoDocumentos> vehiculoDocumentosList;
    @JoinColumn(name = "id_vehiculo_direccion", referencedColumnName = "id_vehiculo_tipo_direccion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipoDireccion idVehiculoDireccion;
    @JoinColumn(name = "id_vehiculo_carroceria", referencedColumnName = "id_vehiculo_tipo_carroceria")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipoCarroceria idVehiculoCarroceria;
    @JoinColumn(name = "id_vehiculo_chasis", referencedColumnName = "id_vehiculo_tipo_chasis")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipoChasis idVehiculoChasis;
    @JoinColumn(name = "id_vehiculo_combustible", referencedColumnName = "id_vehiculo_tipo_combustible")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipoCombustible idVehiculoCombustible;
    @JoinColumn(name = "id_vehiculo_tipo_estado", referencedColumnName = "id_vehiculo_tipo_estado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipoEstado idVehiculoTipoEstado;
    @JoinColumn(name = "id_vehiculo_propietario", referencedColumnName = "id_vehiculo_propietario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoPropietarios idVehiculoPropietario;
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;
    @JoinColumn(name = "id_vehiculo_transmision", referencedColumnName = "id_vehiculo_tipo_transmision")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipoTransmision idVehiculoTransmision;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<Tanqueo> tanqueoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<OperacionKmChecklist> operacionKmChecklistList;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<OperacionGrua> operacionGruaList;
    @OneToMany(mappedBy = "idVehiculo", fetch = FetchType.LAZY)
    private List<NovedadDano> novedadDanoList;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    
    @Transient
    private VehiculoVia vehiculoVia;

    public Vehiculo() {
    }

    public Vehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Vehiculo(Integer idVehiculo, String codigo, String numeroMotor, String numeroChasis, int odometroInicial, int odometro, String username, Date creado, int estadoReg) {
        this.idVehiculo = idVehiculo;
        this.codigo = codigo;
        this.numeroMotor = numeroMotor;
        this.numeroChasis = numeroChasis;
        this.odometroInicial = odometroInicial;
        this.odometro = odometro;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public String getNumeroChasis() {
        return numeroChasis;
    }

    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    public String getNumeroCarroceria() {
        return numeroCarroceria;
    }

    public void setNumeroCarroceria(String numeroCarroceria) {
        this.numeroCarroceria = numeroCarroceria;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(Integer cilindraje) {
        this.cilindraje = cilindraje;
    }

    public int getOdometroInicial() {
        return odometroInicial;
    }

    public void setOdometroInicial(int odometroInicial) {
        this.odometroInicial = odometroInicial;
    }

    public Date getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(Date fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<Multa> getMultaList() {
        return multaList;
    }

    public void setMultaList(List<Multa> multaList) {
        this.multaList = multaList;
    }

    @XmlTransient
    public List<PrgBusUbicacion> getPrgBusUbicacionList() {
        return prgBusUbicacionList;
    }

    public void setPrgBusUbicacionList(List<PrgBusUbicacion> prgBusUbicacionList) {
        this.prgBusUbicacionList = prgBusUbicacionList;
    }

    @XmlTransient
    public List<PrgAsignacion> getPrgAsignacionList() {
        return prgAsignacionList;
    }

    public void setPrgAsignacionList(List<PrgAsignacion> prgAsignacionList) {
        this.prgAsignacionList = prgAsignacionList;
    }

    @XmlTransient
    public List<VehiculoIdf> getVehiculoIdfList() {
        return vehiculoIdfList;
    }

    public void setVehiculoIdfList(List<VehiculoIdf> vehiculoIdfList) {
        this.vehiculoIdfList = vehiculoIdfList;
    }

    @XmlTransient
    public List<Novedad> getNovedadList() {
        return novedadList;
    }

    public void setNovedadList(List<Novedad> novedadList) {
        this.novedadList = novedadList;
    }

    @XmlTransient
    public List<Novedad> getNovedadList1() {
        return novedadList1;
    }

    public void setNovedadList1(List<Novedad> novedadList1) {
        this.novedadList1 = novedadList1;
    }

    @XmlTransient
    public List<OperacionKmTacografo> getOperacionKmTacografoList() {
        return operacionKmTacografoList;
    }

    public void setOperacionKmTacografoList(List<OperacionKmTacografo> operacionKmTacografoList) {
        this.operacionKmTacografoList = operacionKmTacografoList;
    }

    @XmlTransient
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    @XmlTransient
    public List<TanqueoRendimiento> getTanqueoRendimientoList() {
        return tanqueoRendimientoList;
    }

    public void setTanqueoRendimientoList(List<TanqueoRendimiento> tanqueoRendimientoList) {
        this.tanqueoRendimientoList = tanqueoRendimientoList;
    }

    @XmlTransient
    public List<VehiculoDocumentos> getVehiculoDocumentosList() {
        return vehiculoDocumentosList;
    }

    public void setVehiculoDocumentosList(List<VehiculoDocumentos> vehiculoDocumentosList) {
        this.vehiculoDocumentosList = vehiculoDocumentosList;
    }

    public VehiculoTipoDireccion getIdVehiculoDireccion() {
        return idVehiculoDireccion;
    }

    public void setIdVehiculoDireccion(VehiculoTipoDireccion idVehiculoDireccion) {
        this.idVehiculoDireccion = idVehiculoDireccion;
    }

    public VehiculoTipoCarroceria getIdVehiculoCarroceria() {
        return idVehiculoCarroceria;
    }

    public void setIdVehiculoCarroceria(VehiculoTipoCarroceria idVehiculoCarroceria) {
        this.idVehiculoCarroceria = idVehiculoCarroceria;
    }

    public VehiculoTipoChasis getIdVehiculoChasis() {
        return idVehiculoChasis;
    }

    public void setIdVehiculoChasis(VehiculoTipoChasis idVehiculoChasis) {
        this.idVehiculoChasis = idVehiculoChasis;
    }

    public VehiculoTipoCombustible getIdVehiculoCombustible() {
        return idVehiculoCombustible;
    }

    public void setIdVehiculoCombustible(VehiculoTipoCombustible idVehiculoCombustible) {
        this.idVehiculoCombustible = idVehiculoCombustible;
    }

    public VehiculoTipoEstado getIdVehiculoTipoEstado() {
        return idVehiculoTipoEstado;
    }

    public void setIdVehiculoTipoEstado(VehiculoTipoEstado idVehiculoTipoEstado) {
        this.idVehiculoTipoEstado = idVehiculoTipoEstado;
    }

    public VehiculoPropietarios getIdVehiculoPropietario() {
        return idVehiculoPropietario;
    }

    public void setIdVehiculoPropietario(VehiculoPropietarios idVehiculoPropietario) {
        this.idVehiculoPropietario = idVehiculoPropietario;
    }

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public VehiculoTipoTransmision getIdVehiculoTransmision() {
        return idVehiculoTransmision;
    }

    public void setIdVehiculoTransmision(VehiculoTipoTransmision idVehiculoTransmision) {
        this.idVehiculoTransmision = idVehiculoTransmision;
    }

    @XmlTransient
    public List<Tanqueo> getTanqueoList() {
        return tanqueoList;
    }

    public void setTanqueoList(List<Tanqueo> tanqueoList) {
        this.tanqueoList = tanqueoList;
    }

    @XmlTransient
    public List<OperacionKmChecklist> getOperacionKmChecklistList() {
        return operacionKmChecklistList;
    }

    public void setOperacionKmChecklistList(List<OperacionKmChecklist> operacionKmChecklistList) {
        this.operacionKmChecklistList = operacionKmChecklistList;
    }

    @XmlTransient
    public List<OperacionGrua> getOperacionGruaList() {
        return operacionGruaList;
    }

    public void setOperacionGruaList(List<OperacionGrua> operacionGruaList) {
        this.operacionGruaList = operacionGruaList;
    }

    @XmlTransient
    public List<NovedadDano> getNovedadDanoList() {
        return novedadDanoList;
    }

    public void setNovedadDanoList(List<NovedadDano> novedadDanoList) {
        this.novedadDanoList = novedadDanoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculo != null ? idVehiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.idVehiculo == null && other.idVehiculo != null) || (this.idVehiculo != null && !this.idVehiculo.equals(other.idVehiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "idVehiculo=" + idVehiculo + ", codigo=" + codigo + ", placa=" + placa + '}';
    }

    @XmlTransient
    public List<PrgVehiculoInactivo> getPrgVehiculoInactivoList() {
        return prgVehiculoInactivoList;
    }

    public void setPrgVehiculoInactivoList(List<PrgVehiculoInactivo> prgVehiculoInactivoList) {
        this.prgVehiculoInactivoList = prgVehiculoInactivoList;
    }

    @XmlTransient
    public List<KmConciliado> getKmConciliadoList() {
        return kmConciliadoList;
    }

    public void setKmConciliadoList(List<KmConciliado> kmConciliadoList) {
        this.kmConciliadoList = kmConciliadoList;
    }

    @XmlTransient
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    @XmlTransient
    public List<MttoNovedad> getMttoNovedadList() {
        return mttoNovedadList;
    }

    public void setMttoNovedadList(List<MttoNovedad> mttoNovedadList) {
        this.mttoNovedadList = mttoNovedadList;
    }

    @XmlTransient
    public List<NovedadPrgTc> getNovedadPrgTcList() {
        return novedadPrgTcList;
    }

    public void setNovedadPrgTcList(List<NovedadPrgTc> novedadPrgTcList) {
        this.novedadPrgTcList = novedadPrgTcList;
    }

    @XmlTransient
    public List<NovedadPrgTc> getNovedadPrgTcList1() {
        return novedadPrgTcList1;
    }

    public void setNovedadPrgTcList1(List<NovedadPrgTc> novedadPrgTcList1) {
        this.novedadPrgTcList1 = novedadPrgTcList1;
    }

    @XmlTransient
    public List<Lavado> getLavadoList() {
        return lavadoList;
    }

    public void setLavadoList(List<Lavado> lavadoList) {
        this.lavadoList = lavadoList;
    }

    @XmlTransient
    public List<MttoAsig> getMttoAsigList() {
        return mttoAsigList;
    }

    public void setMttoAsigList(List<MttoAsig> mttoAsigList) {
        this.mttoAsigList = mttoAsigList;
    }

    @XmlTransient
    public List<VehiculoOdometro> getVehiculoOdometroList() {
        return vehiculoOdometroList;
    }

    public void setVehiculoOdometroList(List<VehiculoOdometro> vehiculoOdometroList) {
        this.vehiculoOdometroList = vehiculoOdometroList;
    }

    public int getOdometro() {
        return odometro;
    }

    public void setOdometro(int odometro) {
        this.odometro = odometro;

    }

    @XmlTransient
    public List<AuditoriaRealizadoPor> getAuditoriaRealizadoPorList() {
        return auditoriaRealizadoPorList;
    }

    public void setAuditoriaRealizadoPorList(List<AuditoriaRealizadoPor> auditoriaRealizadoPorList) {
        this.auditoriaRealizadoPorList = auditoriaRealizadoPorList;
    }

    @XmlTransient
    public List<ContableCtaVehiculo> getContableCtaVehiculoList() {
        return contableCtaVehiculoList;
    }

    public void setContableCtaVehiculoList(List<ContableCtaVehiculo> contableCtaVehiculoList) {
        this.contableCtaVehiculoList = contableCtaVehiculoList;
    }

    @XmlTransient
    public List<SegInoperativos> getSegInoperativosList() {
        return segInoperativosList;
    }

    public void setSegInoperativosList(List<SegInoperativos> segInoperativosList) {
        this.segInoperativosList = segInoperativosList;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @XmlTransient
    public List<VehiculoEstadoHistorico> getVehiculoEstadoHistoricoList() {
        return vehiculoEstadoHistoricoList;
    }

    public void setVehiculoEstadoHistoricoList(List<VehiculoEstadoHistorico> vehiculoEstadoHistoricoList) {
        this.vehiculoEstadoHistoricoList = vehiculoEstadoHistoricoList;
    }

    @XmlTransient
    public List<DispConciliacionDet> getDispConciliacionDetList() {
        return dispConciliacionDetList;
    }

    public void setDispConciliacionDetList(List<DispConciliacionDet> dispConciliacionDetList) {
        this.dispConciliacionDetList = dispConciliacionDetList;
    }

    @XmlTransient
    public List<Hallazgo> getHallazgosList() {
        return hallazgosList;
    }

    public void setHallazgosList(List<Hallazgo> hallazgosList) {
        this.hallazgosList = hallazgosList;
    }

    public VehiculoVia getVehiculoVia() {
        return vehiculoVia;
    }

    public void setVehiculoVia(VehiculoVia vehiculoVia) {
        this.vehiculoVia = vehiculoVia;
    }
    
}
