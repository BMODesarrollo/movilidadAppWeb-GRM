/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.InformeInterventoria;
import com.movilidad.util.beans.InformeLocalidadEmpleado;
import com.movilidad.util.beans.PlantaObz;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
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
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByIdEmpleado", query = "SELECT e FROM Empleado e WHERE e.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Empleado.findByCodigoInterno", query = "SELECT e FROM Empleado e WHERE e.codigoInterno = :codigoInterno"),
    @NamedQuery(name = "Empleado.findByCodigoTm", query = "SELECT e FROM Empleado e WHERE e.codigoTm = :codigoTm"),
    @NamedQuery(name = "Empleado.findByIdentificacion", query = "SELECT e FROM Empleado e WHERE e.identificacion = :identificacion"),
    @NamedQuery(name = "Empleado.findByNombres", query = "SELECT e FROM Empleado e WHERE e.nombres = :nombres"),
    @NamedQuery(name = "Empleado.findByApellidos", query = "SELECT e FROM Empleado e WHERE e.apellidos = :apellidos"),
    @NamedQuery(name = "Empleado.findByFechaNcto", query = "SELECT e FROM Empleado e WHERE e.fechaNcto = :fechaNcto"),
    @NamedQuery(name = "Empleado.findByDireccion", query = "SELECT e FROM Empleado e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empleado.findByTelefonoFijo", query = "SELECT e FROM Empleado e WHERE e.telefonoFijo = :telefonoFijo"),
    @NamedQuery(name = "Empleado.findByTelefonoMovil", query = "SELECT e FROM Empleado e WHERE e.telefonoMovil = :telefonoMovil"),
    @NamedQuery(name = "Empleado.findByEmailPersonal", query = "SELECT e FROM Empleado e WHERE e.emailPersonal = :emailPersonal"),
    @NamedQuery(name = "Empleado.findByEmailCorporativo", query = "SELECT e FROM Empleado e WHERE e.emailCorporativo = :emailCorporativo"),
    @NamedQuery(name = "Empleado.findByNombreContactoEmergencia", query = "SELECT e FROM Empleado e WHERE e.nombreContactoEmergencia = :nombreContactoEmergencia"),
    @NamedQuery(name = "Empleado.findByTelefonoContactoEmergencia", query = "SELECT e FROM Empleado e WHERE e.telefonoContactoEmergencia = :telefonoContactoEmergencia"),
    @NamedQuery(name = "Empleado.findByMovilContactoEmergencia", query = "SELECT e FROM Empleado e WHERE e.movilContactoEmergencia = :movilContactoEmergencia"),
    @NamedQuery(name = "Empleado.findByGenero", query = "SELECT e FROM Empleado e WHERE e.genero = :genero"),
    @NamedQuery(name = "Empleado.findByRh", query = "SELECT e FROM Empleado e WHERE e.rh = :rh"),
    @NamedQuery(name = "Empleado.findByPathFoto", query = "SELECT e FROM Empleado e WHERE e.pathFoto = :pathFoto"),
    @NamedQuery(name = "Empleado.findByFechaIngreso", query = "SELECT e FROM Empleado e WHERE e.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Empleado.findByUsername", query = "SELECT e FROM Empleado e WHERE e.username = :username"),
    @NamedQuery(name = "Empleado.findByCreado", query = "SELECT e FROM Empleado e WHERE e.creado = :creado"),
    @NamedQuery(name = "Empleado.findByModificado", query = "SELECT e FROM Empleado e WHERE e.modificado = :modificado"),
    @NamedQuery(name = "Empleado.findByEstadoReg", query = "SELECT e FROM Empleado e WHERE e.estadoReg = :estadoReg")})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "InformeInterventoriaMapping",
            classes = {
                @ConstructorResult(targetClass = InformeInterventoria.class,
                        columns = {
                            @ColumnResult(name = "fecha_registro"),
                            @ColumnResult(name = "codigo_tm"),
                            @ColumnResult(name = "nombre_cargo"),
                            @ColumnResult(name = "tiempoTotal"),
                            @ColumnResult(name = "comercial"),
                            @ColumnResult(name = "vacio"),
                            @ColumnResult(name = "quejas"),
                            @ColumnResult(name = "multas")
                        }
                )
            }),
    @SqlResultSetMapping(name = "PlantaObzMapping",
            classes = {
                @ConstructorResult(targetClass = PlantaObz.class,
                        columns = {
                            @ColumnResult(name = "id_empleado"),
                            @ColumnResult(name = "codigo_empresa"),
                            @ColumnResult(name = "identificacion"),
                            @ColumnResult(name = "nombres"),
                            @ColumnResult(name = "apellidos"),
                            @ColumnResult(name = "nombrecompleto"),
                            @ColumnResult(name = "fecha_contrato"),
                            @ColumnResult(name = "descripcion_cargo"),
                            @ColumnResult(name = "codigo_tm"),
                            @ColumnResult(name = "accidentes", type = Long.class),
                            @ColumnResult(name = "km_recorrido", type = BigDecimal.class),
                            @ColumnResult(name = "capacitacion", type = Integer.class),
                            @ColumnResult(name = "infracciones", type = Integer.class),
                            @ColumnResult(name = "pqr", type = Integer.class),
                            @ColumnResult(name = "horas_programadas", type = String.class),
                            @ColumnResult(name = "horas_reales", type = String.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "InformeLocalidadEmpleado",
            classes = {
                @ConstructorResult(targetClass = InformeLocalidadEmpleado.class,
                        columns = {
                            @ColumnResult(name = "localidad", type = String.class),
                            @ColumnResult(name = "total", type = Float.class)
                        }
                )
            })
})
public class Empleado implements Serializable {

    @OneToMany(mappedBy = "idEmpleado")
    private List<Hallazgo> hallazgosList;

    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<MyAppSerconConfirm> myAppSerconConfirmList;

    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<SegInoperativos> segInoperativosList;

    @OneToMany(mappedBy = "idEmpleadoSupervisor", fetch = FetchType.LAZY)
    private List<CableAccInformeRespondiente> cableAccInformeRespondienteList;
    @OneToMany(mappedBy = "idOperador", fetch = FetchType.LAZY)
    private List<CableAccidentalidad> cableAccidentalidadList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaPmIngEgr> genericaPmIngEgrList;

    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<PmIngEgr> pmIngEgrList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<AccNovedadInfraestruc> accNovedadInfraestrucList;

    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<CableRevisionDiaRta> cableRevisionDiaRtaList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<CableNovedadOp> cableNovedadOpList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<NovedadVacaciones> novedadVacacionesList;

    @OneToMany(mappedBy = "idEmpleadoAuditado", fetch = FetchType.LAZY)
    private List<AuditoriaRealizadoPor> auditoriaRealizadoPorList;
    @OneToMany(mappedBy = "idEmpleadoRealiza", fetch = FetchType.LAZY)
    private List<AuditoriaRealizadoPor> auditoriaRealizadoPorList1;
    @OneToMany(mappedBy = "idResponsable", fetch = FetchType.LAZY)
    private List<SstAutorizacion> sstAutorizacionList;
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
    private List<GenericaPmLiquidacionHist> genericaPmLiquidacionHistList;
    @OneToOne(mappedBy = "empleado", fetch = FetchType.LAZY)
    private GenericaPmLiquidacion genericaPmLiquidacion;
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
    private List<TblHistoricoIquidacionEmpleado> tblHistoricoIquidacionEmpleadoList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaTurnoFijo> genericaTurnoFijoList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaPrgJornada> genericaPrgJornadaList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaToken> genericaTokenList;

    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<PrgToken> prgTokenList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaJornadaExtra> genericaJornadaExtraList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaPmGrupo> genericaPmGrupoList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaPmGrupoDetalle> genericaPmGrupoDetalleList;

    @JoinColumn(name = "id_empleado_empleador", referencedColumnName = "id_empleado_empleador")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoEmpleador idEmpleadoEmpleador;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<Generica> genericaList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaJornada> genericaJornadaList;
    @OneToMany(mappedBy = "idEmpleado")
    private List<LavadoResponsable> lavadoResponsableList;

    @OneToMany(mappedBy = "idMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterApoyo> accInformeMasterApoyoList;
    @OneToMany(mappedBy = "idOperadorPrincipal", fetch = FetchType.LAZY)
    private List<AccInformeOpe> accInformeOpeList;
    @OneToMany(mappedBy = "idEmpleadoMaster", fetch = FetchType.LAZY)
    private List<AccInformeMaster> accInformeMasterList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList;
    @OneToMany(mappedBy = "idOldEmpleado", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList1;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<MttoNovedad> mttoNovedadList;

    @Column(name = "certificado")
    private Integer certificado;
    @OneToOne(mappedBy = "empleado", fetch = FetchType.LAZY)
    private TblLiquidacionEmpleadoMes tblLiquidacionEmpleadoMes;

    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;
    @OneToMany(mappedBy = "idMaster", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList1;

    @OneToMany(mappedBy = "idEmpleado")
    private List<PrgOperadorInactivo> prgOperadorInactivoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Column(name = "codigo_interno")
    private Integer codigoInterno;
    @Column(name = "codigo_tm")
    private Integer codigoTm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ncto")
    @Temporal(TemporalType.DATE)
    private Date fechaNcto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono_fijo")
    private String telefonoFijo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono_movil")
    private String telefonoMovil;
    @Column(name = "email_personal")
    private String emailPersonal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "email_corporativo")
    private String emailCorporativo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombre_contacto_emergencia")
    private String nombreContactoEmergencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono_contacto_emergencia")
    private String telefonoContactoEmergencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "movil_contacto_emergencia")
    private String movilContactoEmergencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "genero")
    private Character genero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rh")
    private String rh;
    @Basic(optional = false)
    @NotNull
    @Column(name = "path_foto")
    private String pathFoto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Basic(optional = false)
    @Column(name = "fecha_retiro")
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;
    @Basic(optional = false)
    @NotNull
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
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<Multa> multaList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<Users> usersList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<PmGrupo> pmGrupoList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<PmGrupoDetalle> pmGrupoDetalleList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<Novedad> novedadList;
    @OneToMany(mappedBy = "oldEmpleado", fetch = FetchType.LAZY)
    private List<Novedad> novedadListOldEmpleado;
    @JoinColumn(name = "id_empleado_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoCargo;
    @JoinColumn(name = "id_empleado_departamento", referencedColumnName = "id_empleado_departamento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoDepartamento idEmpleadoDepartamento;
    @JoinColumn(name = "id_empleado_estado", referencedColumnName = "id_empleado_estado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoEstado idEmpleadoEstado;
    @JoinColumn(name = "id_empleado_municipio", referencedColumnName = "id_empleado_ciudad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoMunicipio idEmpleadoMunicipio;
    @JoinColumn(name = "id_operacion_patio", referencedColumnName = "id_operacion_patios")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private OperacionPatios idOperacionPatio;
    @JoinColumn(name = "id_empleado_tipo_identificacion", referencedColumnName = "id_empleado_tipo_identificacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoTipoIdentificacion idEmpleadoTipoIdentificacion;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<PrgSercon> prgSerconList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<EmpleadoContrato> empleadoContratoList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<EmpleadoDocumentos> empleadoDocumentosList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<NovedadDano> novedadDanoList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<OperacionKmChecklistDet> operacionKmChecklistDetList;
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
    private List<CableInfoTecnica> cableInfoTecnicaList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<NominaAutorizacionIndividual> nominaAutorizacionIndividualList;
    @OneToMany(mappedBy = "idEmpleado", fetch = FetchType.LAZY)
    private List<GenericaNominaAutorizacionIndividual> genericaNominaAutorizacionIndividualList;

    //geoReferencia
    @Column(name = "longitud")
    private String longitud;
    @Column(name = "latitud")
    private String latitud;
    @Column(name = "localidad")
    private String localidad;

    //Nro Contrato    
    @Column(name = "nro_contrato")
    private String nroContrato;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public Empleado() {
    }

    public Empleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleado(Integer idEmpleado, String identificacion, String nombres, String apellidos, Date fechaNcto, String direccion, String telefonoMovil, String emailCorporativo, String nombreContactoEmergencia, String telefonoContactoEmergencia, String movilContactoEmergencia, Character genero, String rh, String pathFoto, Date fechaIngreso, String username, Date creado, int estadoReg, Date fechaRetiro) {
        this.idEmpleado = idEmpleado;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNcto = fechaNcto;
        this.direccion = direccion;
        this.telefonoMovil = telefonoMovil;
        this.emailCorporativo = emailCorporativo;
        this.nombreContactoEmergencia = nombreContactoEmergencia;
        this.telefonoContactoEmergencia = telefonoContactoEmergencia;
        this.movilContactoEmergencia = movilContactoEmergencia;
        this.genero = genero;
        this.rh = rh;
        this.pathFoto = pathFoto;
        this.fechaIngreso = fechaIngreso;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
        this.fechaRetiro = fechaRetiro;
    }

    public double getKmDistanciaPaitos(String lat, String lon) {
        if (this.latitud != null && this.longitud != null && lat != null && lon != null) {
            double dLon = Double.parseDouble(lon);
            double dLat = Double.parseDouble(lat);
            double dLatEmp = Double.parseDouble(this.latitud);
            double dLonEmp = Double.parseDouble(this.longitud);
            return Util.distanciaCoord(dLat, dLon, dLatEmp, dLonEmp);
        }
        return 0;
    }

    public String getNombresApellidos() {
        return this.nombres + " " + this.apellidos;
    }

    public String getNombresApellidosCodigo() {
        return this.nombres + " " + this.apellidos + "-" + this.codigoTm;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(Integer codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public Integer getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(Integer codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNcto() {
        return fechaNcto;
    }

    public void setFechaNcto(Date fechaNcto) {
        this.fechaNcto = fechaNcto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    public String getEmailCorporativo() {
        return emailCorporativo;
    }

    public void setEmailCorporativo(String emailCorporativo) {
        this.emailCorporativo = emailCorporativo;
    }

    public String getNombreContactoEmergencia() {
        return nombreContactoEmergencia;
    }

    public void setNombreContactoEmergencia(String nombreContactoEmergencia) {
        this.nombreContactoEmergencia = nombreContactoEmergencia;
    }

    public String getTelefonoContactoEmergencia() {
        return telefonoContactoEmergencia;
    }

    public void setTelefonoContactoEmergencia(String telefonoContactoEmergencia) {
        this.telefonoContactoEmergencia = telefonoContactoEmergencia;
    }

    public String getMovilContactoEmergencia() {
        return movilContactoEmergencia;
    }

    public void setMovilContactoEmergencia(String movilContactoEmergencia) {
        this.movilContactoEmergencia = movilContactoEmergencia;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @XmlTransient
    public List<PmGrupo> getPmGrupoList() {
        return pmGrupoList;
    }

    public void setPmGrupoList(List<PmGrupo> pmGrupoList) {
        this.pmGrupoList = pmGrupoList;
    }

    @XmlTransient
    public List<PmGrupoDetalle> getPmGrupoDetalleList() {
        return pmGrupoDetalleList;
    }

    public void setPmGrupoDetalleList(List<PmGrupoDetalle> pmGrupoDetalleList) {
        this.pmGrupoDetalleList = pmGrupoDetalleList;
    }

    @XmlTransient
    public List<AccNovedadInfraestruc> getAccNovedadInfraestrucList() {
        return accNovedadInfraestrucList;
    }

    public void setAccNovedadInfraestrucList(List<AccNovedadInfraestruc> accNovedadInfraestrucList) {
        this.accNovedadInfraestrucList = accNovedadInfraestrucList;
    }

    @XmlTransient
    public List<Novedad> getNovedadList() {
        return novedadList;
    }

    public void setNovedadList(List<Novedad> novedadList) {
        this.novedadList = novedadList;
    }

    @XmlTransient
    public List<Novedad> getNovedadListOldEmpleado() {
        return novedadListOldEmpleado;
    }

    public void setNovedadListOldEmpleado(List<Novedad> novedadListOldEmpleado) {
        this.novedadListOldEmpleado = novedadListOldEmpleado;
    }

    public EmpleadoTipoCargo getIdEmpleadoCargo() {
        return idEmpleadoCargo;
    }

    public void setIdEmpleadoCargo(EmpleadoTipoCargo idEmpleadoCargo) {
        this.idEmpleadoCargo = idEmpleadoCargo;
    }

    public EmpleadoDepartamento getIdEmpleadoDepartamento() {
        return idEmpleadoDepartamento;
    }

    public void setIdEmpleadoDepartamento(EmpleadoDepartamento idEmpleadoDepartamento) {
        this.idEmpleadoDepartamento = idEmpleadoDepartamento;
    }

    public EmpleadoEstado getIdEmpleadoEstado() {
        return idEmpleadoEstado;
    }

    public void setIdEmpleadoEstado(EmpleadoEstado idEmpleadoEstado) {
        this.idEmpleadoEstado = idEmpleadoEstado;
    }

    public EmpleadoMunicipio getIdEmpleadoMunicipio() {
        return idEmpleadoMunicipio;
    }

    public void setIdEmpleadoMunicipio(EmpleadoMunicipio idEmpleadoMunicipio) {
        this.idEmpleadoMunicipio = idEmpleadoMunicipio;
    }

    public OperacionPatios getIdOperacionPatio() {
        return idOperacionPatio;
    }

    public void setIdOperacionPatio(OperacionPatios idOperacionPatio) {
        this.idOperacionPatio = idOperacionPatio;
    }

    public EmpleadoTipoIdentificacion getIdEmpleadoTipoIdentificacion() {
        return idEmpleadoTipoIdentificacion;
    }

    public void setIdEmpleadoTipoIdentificacion(EmpleadoTipoIdentificacion idEmpleadoTipoIdentificacion) {
        this.idEmpleadoTipoIdentificacion = idEmpleadoTipoIdentificacion;
    }

    @XmlTransient
    public List<PrgSercon> getPrgSerconList() {
        return prgSerconList;
    }

    public void setPrgSerconList(List<PrgSercon> prgSerconList) {
        this.prgSerconList = prgSerconList;
    }

    @XmlTransient
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    @XmlTransient
    public List<EmpleadoContrato> getEmpleadoContratoList() {
        return empleadoContratoList;
    }

    public void setEmpleadoContratoList(List<EmpleadoContrato> empleadoContratoList) {
        this.empleadoContratoList = empleadoContratoList;
    }

    @XmlTransient
    public List<EmpleadoDocumentos> getEmpleadoDocumentosList() {
        return empleadoDocumentosList;
    }

    public void setEmpleadoDocumentosList(List<EmpleadoDocumentos> empleadoDocumentosList) {
        this.empleadoDocumentosList = empleadoDocumentosList;
    }

    @XmlTransient
    public List<NovedadDano> getNovedadDanoList() {
        return novedadDanoList;
    }

    public void setNovedadDanoList(List<NovedadDano> novedadDanoList) {
        this.novedadDanoList = novedadDanoList;
    }

    @XmlTransient
    public List<OperacionKmChecklistDet> getOperacionKmChecklistDetList() {
        return operacionKmChecklistDetList;
    }

    public void setOperacionKmChecklistDetList(List<OperacionKmChecklistDet> operacionKmChecklistDetList) {
        this.operacionKmChecklistDetList = operacionKmChecklistDetList;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Empleado{" + "idEmpleado=" + idEmpleado + ", codigoTm=" + codigoTm + ", identificacion=" + identificacion + ", nombres=" + nombres + '}';
    }

    @XmlTransient
    public List<PrgOperadorInactivo> getPrgOperadorInactivoList() {
        return prgOperadorInactivoList;
    }

    public void setPrgOperadorInactivoList(List<PrgOperadorInactivo> prgOperadorInactivoList) {
        this.prgOperadorInactivoList = prgOperadorInactivoList;
    }

    @XmlTransient
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    public TblLiquidacionEmpleadoMes getTblLiquidacionEmpleadoMes() {
        return tblLiquidacionEmpleadoMes;
    }

    public void setTblLiquidacionEmpleadoMes(TblLiquidacionEmpleadoMes tblLiquidacionEmpleadoMes) {
        this.tblLiquidacionEmpleadoMes = tblLiquidacionEmpleadoMes;
    }

    public Integer getCertificado() {
        return certificado;
    }

    public void setCertificado(Integer certificado) {
        this.certificado = certificado;
    }

    @XmlTransient
    public List<MttoNovedad> getMttoNovedadList() {
        return mttoNovedadList;
    }

    public void setMttoNovedadList(List<MttoNovedad> mttoNovedadList) {
        this.mttoNovedadList = mttoNovedadList;
    }

    @XmlTransient
    public List<AccInformeMasterApoyo> getAccInformeMasterApoyoList() {
        return accInformeMasterApoyoList;
    }

    public void setAccInformeMasterApoyoList(List<AccInformeMasterApoyo> accInformeMasterApoyoList) {
        this.accInformeMasterApoyoList = accInformeMasterApoyoList;
    }

    @XmlTransient
    public List<AccInformeOpe> getAccInformeOpeList() {
        return accInformeOpeList;
    }

    public void setAccInformeOpeList(List<AccInformeOpe> accInformeOpeList) {
        this.accInformeOpeList = accInformeOpeList;
    }

    @XmlTransient
    public List<AccInformeMaster> getAccInformeMasterList() {
        return accInformeMasterList;
    }

    public void setAccInformeMasterList(List<AccInformeMaster> accInformeMasterList) {
        this.accInformeMasterList = accInformeMasterList;
    }

    @XmlTransient
    public List<NovedadPrgTc> getNovedadPrgTcList() {
        return novedadPrgTcList;
    }

    public void setNovedadPrgTcList(List<NovedadPrgTc> novedadPrgTcList) {
        this.novedadPrgTcList = novedadPrgTcList;
    }

    @XmlTransient
    public List<LavadoResponsable> getLavadoResponsableList() {
        return lavadoResponsableList;
    }

    public void setLavadoResponsableList(List<LavadoResponsable> lavadoResponsableList) {
        this.lavadoResponsableList = lavadoResponsableList;
    }

    public EmpleadoEmpleador getIdEmpleadoEmpleador() {
        return idEmpleadoEmpleador;
    }

    public void setIdEmpleadoEmpleador(EmpleadoEmpleador idEmpleadoEmpleador) {
        this.idEmpleadoEmpleador = idEmpleadoEmpleador;
    }

    @XmlTransient
    public List<GenericaPmGrupo> getGenericaPmGrupoList() {
        return genericaPmGrupoList;
    }

    public void setGenericaPmGrupoList(List<GenericaPmGrupo> genericaPmGrupoList) {
        this.genericaPmGrupoList = genericaPmGrupoList;
    }

    @XmlTransient
    public List<GenericaPmGrupoDetalle> getGenericaPmGrupoDetalleList() {
        return genericaPmGrupoDetalleList;
    }

    public void setGenericaPmGrupoDetalleList(List<GenericaPmGrupoDetalle> genericaPmGrupoDetalleList) {
        this.genericaPmGrupoDetalleList = genericaPmGrupoDetalleList;
    }

    @XmlTransient
    public List<GenericaJornadaExtra> getGenericaJornadaExtraList() {
        return genericaJornadaExtraList;
    }

    public void setGenericaJornadaExtraList(List<GenericaJornadaExtra> genericaJornadaExtraList) {
        this.genericaJornadaExtraList = genericaJornadaExtraList;
    }

    @XmlTransient
    public List<PrgToken> getPrgTokenList() {
        return prgTokenList;
    }

    public void setPrgTokenList(List<PrgToken> prgTokenList) {
        this.prgTokenList = prgTokenList;
    }

    @XmlTransient
    public List<GenericaToken> getGenericaTokenList() {
        return genericaTokenList;
    }

    public void setGenericaTokenList(List<GenericaToken> genericaTokenList) {
        this.genericaTokenList = genericaTokenList;
    }

    @XmlTransient
    public List<GenericaTurnoFijo> getGenericaTurnoFijoList() {
        return genericaTurnoFijoList;
    }

    public void setGenericaTurnoFijoList(List<GenericaTurnoFijo> genericaTurnoFijoList) {
        this.genericaTurnoFijoList = genericaTurnoFijoList;
    }

    @XmlTransient
    public List<TblHistoricoIquidacionEmpleado> getTblHistoricoIquidacionEmpleadoList() {
        return tblHistoricoIquidacionEmpleadoList;
    }

    public void setTblHistoricoIquidacionEmpleadoList(List<TblHistoricoIquidacionEmpleado> tblHistoricoIquidacionEmpleadoList) {
        this.tblHistoricoIquidacionEmpleadoList = tblHistoricoIquidacionEmpleadoList;
    }

    public List<GenericaPrgJornada> getGenericaPrgJornadaList() {
        return genericaPrgJornadaList;
    }

    public void setGenericaPrgJornadaList(List<GenericaPrgJornada> genericaPrgJornadaList) {
        this.genericaPrgJornadaList = genericaPrgJornadaList;
    }

    @XmlTransient
    public List<SstAutorizacion> getSstAutorizacionList() {
        return sstAutorizacionList;
    }

    public void setSstAutorizacionList(List<SstAutorizacion> sstAutorizacionList) {
        this.sstAutorizacionList = sstAutorizacionList;
    }

    @XmlTransient
    public List<GenericaPmLiquidacionHist> getGenericaPmLiquidacionHistList() {
        return genericaPmLiquidacionHistList;
    }

    public void setGenericaPmLiquidacionHistList(List<GenericaPmLiquidacionHist> genericaPmLiquidacionHistList) {
        this.genericaPmLiquidacionHistList = genericaPmLiquidacionHistList;
    }

    public GenericaPmLiquidacion getGenericaPmLiquidacion() {
        return genericaPmLiquidacion;
    }

    public void setGenericaPmLiquidacion(GenericaPmLiquidacion genericaPmLiquidacion) {
        this.genericaPmLiquidacion = genericaPmLiquidacion;
    }

    @XmlTransient
    public List<AuditoriaRealizadoPor> getAuditoriaRealizadoPorList() {
        return auditoriaRealizadoPorList;
    }

    public void setAuditoriaRealizadoPorList(List<AuditoriaRealizadoPor> auditoriaRealizadoPorList) {
        this.auditoriaRealizadoPorList = auditoriaRealizadoPorList;
    }

    @XmlTransient
    public List<AuditoriaRealizadoPor> getAuditoriaRealizadoPorList1() {
        return auditoriaRealizadoPorList1;
    }

    public void setAuditoriaRealizadoPorList1(List<AuditoriaRealizadoPor> auditoriaRealizadoPorList1) {
        this.auditoriaRealizadoPorList1 = auditoriaRealizadoPorList1;
    }

    public List<CableNovedadOp> getCableNovedadOpList() {
        return cableNovedadOpList;
    }

    public void setCableNovedadOpList(List<CableNovedadOp> cableNovedadOpList) {
        this.cableNovedadOpList = cableNovedadOpList;
    }

    @XmlTransient
    public List<CableInfoTecnica> getCableInfoTecnicaList() {
        return cableInfoTecnicaList;
    }

    public void setCableInfoTecnicaList(List<CableInfoTecnica> cableInfoTecnicaList) {
        this.cableInfoTecnicaList = cableInfoTecnicaList;
    }

    @XmlTransient
    public List<CableRevisionDiaRta> getCableRevisionDiaRtaList() {
        return cableRevisionDiaRtaList;
    }

    public void setCableRevisionDiaRtaList(List<CableRevisionDiaRta> cableRevisionDiaRtaList) {
        this.cableRevisionDiaRtaList = cableRevisionDiaRtaList;
    }

    @XmlTransient
    public List<CableAccInformeRespondiente> getCableAccInformeRespondienteList() {
        return cableAccInformeRespondienteList;
    }

    public void setCableAccInformeRespondienteList(List<CableAccInformeRespondiente> cableAccInformeRespondienteList) {
        this.cableAccInformeRespondienteList = cableAccInformeRespondienteList;
    }

    @XmlTransient
    public List<CableAccidentalidad> getCableAccidentalidadList() {
        return cableAccidentalidadList;
    }

    public void setCableAccidentalidadList(List<CableAccidentalidad> cableAccidentalidadList) {
        this.cableAccidentalidadList = cableAccidentalidadList;
    }

    public List<PmIngEgr> getPmIngEgrList() {
        return pmIngEgrList;
    }

    public void setPmIngEgrList(List<PmIngEgr> pmIngEgrList) {
        this.pmIngEgrList = pmIngEgrList;
    }

    @XmlTransient
    public List<GenericaPmIngEgr> getGenericaPmIngEgrList() {
        return genericaPmIngEgrList;
    }

    public void setGenericaPmIngEgrList(List<GenericaPmIngEgr> genericaPmIngEgrList) {
        this.genericaPmIngEgrList = genericaPmIngEgrList;
    }

    @XmlTransient
    public List<SegInoperativos> getSegInoperativosList() {
        return segInoperativosList;
    }

    public void setSegInoperativosList(List<SegInoperativos> segInoperativosList) {
        this.segInoperativosList = segInoperativosList;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @XmlTransient
    public List<MyAppSerconConfirm> getMyAppSerconConfirmList() {
        return myAppSerconConfirmList;
    }

    public void setMyAppSerconConfirmList(List<MyAppSerconConfirm> myAppSerconConfirmList) {
        this.myAppSerconConfirmList = myAppSerconConfirmList;
    }

    @XmlTransient
    public List<NovedadVacaciones> getNovedadVacacionesList() {
        return novedadVacacionesList;
    }

    public void setNovedadVacacionesList(List<NovedadVacaciones> novedadVacacionesList) {
        this.novedadVacacionesList = novedadVacacionesList;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public String nombresApellidos() {
        return this.nombres.concat(" ").concat(this.apellidos);
    }

    public String getNroContrato() {
        return nroContrato;
    }

    public void setNroContrato(String nroContrato) {
        this.nroContrato = nroContrato;
    }

    @XmlTransient
    public List<NominaAutorizacionIndividual> getNominaAutorizacionIndividualList() {
        return nominaAutorizacionIndividualList;
    }

    public void setNominaAutorizacionIndividualList(List<NominaAutorizacionIndividual> nominaAutorizacionIndividualList) {
        this.nominaAutorizacionIndividualList = nominaAutorizacionIndividualList;
    }

    @XmlTransient
    public List<GenericaNominaAutorizacionIndividual> getGenericaNominaAutorizacionIndividualList() {
        return genericaNominaAutorizacionIndividualList;
    }

    public void setGenericaNominaAutorizacionIndividualList(List<GenericaNominaAutorizacionIndividual> genericaNominaAutorizacionIndividualList) {
        this.genericaNominaAutorizacionIndividualList = genericaNominaAutorizacionIndividualList;
    }

    @XmlTransient
    public List<Hallazgo> getHallazgosList() {
        return hallazgosList;
    }

    public void setHallazgosList(List<Hallazgo> hallazgosList) {
        this.hallazgosList = hallazgosList;
    }
}
