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
import javax.persistence.Lob;
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
@Table(name = "pqr_maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PqrMaestro.findAll", query = "SELECT p FROM PqrMaestro p"),
    @NamedQuery(name = "PqrMaestro.findByIdPqrMaestro", query = "SELECT p FROM PqrMaestro p WHERE p.idPqrMaestro = :idPqrMaestro"),
    @NamedQuery(name = "PqrMaestro.findByRpCedula", query = "SELECT p FROM PqrMaestro p WHERE p.rpCedula = :rpCedula"),
    @NamedQuery(name = "PqrMaestro.findByRpNombre", query = "SELECT p FROM PqrMaestro p WHERE p.rpNombre = :rpNombre"),
    @NamedQuery(name = "PqrMaestro.findByRpDireccion", query = "SELECT p FROM PqrMaestro p WHERE p.rpDireccion = :rpDireccion"),
    @NamedQuery(name = "PqrMaestro.findByRpTelefono", query = "SELECT p FROM PqrMaestro p WHERE p.rpTelefono = :rpTelefono"),
    @NamedQuery(name = "PqrMaestro.findByRpEmail", query = "SELECT p FROM PqrMaestro p WHERE p.rpEmail = :rpEmail"),
    @NamedQuery(name = "PqrMaestro.findByEstado", query = "SELECT p FROM PqrMaestro p WHERE p.estado = :estado"),
    @NamedQuery(name = "PqrMaestro.findByUsrCierre", query = "SELECT p FROM PqrMaestro p WHERE p.usrCierre = :usrCierre"),
    @NamedQuery(name = "PqrMaestro.findByFechaCierre", query = "SELECT p FROM PqrMaestro p WHERE p.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "PqrMaestro.findByRegistradaPor", query = "SELECT p FROM PqrMaestro p WHERE p.registradaPor = :registradaPor"),
    @NamedQuery(name = "PqrMaestro.findByUsername", query = "SELECT p FROM PqrMaestro p WHERE p.username = :username"),
    @NamedQuery(name = "PqrMaestro.findByCreado", query = "SELECT p FROM PqrMaestro p WHERE p.creado = :creado"),
    @NamedQuery(name = "PqrMaestro.findByModificado", query = "SELECT p FROM PqrMaestro p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PqrMaestro.findByEstadoReg", query = "SELECT p FROM PqrMaestro p WHERE p.estadoReg = :estadoReg")})
public class PqrMaestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqr_maestro")
    private Integer idPqrMaestro;
    @Basic(optional = false)
    @Column(name = "fecha_evento")
    @Temporal(TemporalType.DATE)
    private Date fecha_evento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_radicado")
    @Temporal(TemporalType.DATE)
    private Date fecha_radicado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_estimada_1")
    @Temporal(TemporalType.DATE)
    private Date fecha_estimada1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_estimada_2")
    @Temporal(TemporalType.DATE)
    private Date fecha_estimada2;
    @Size(max = 200)
    @Column(name = "radicado")
    private String radicado;
    @Lob
    @Size(max = 65535)
    @Column(name = "reporte")
    private String reporte;
    @Lob
    @Size(max = 65535)
    @Column(name = "analisis")
    private String analisis;
    @Size(max = 20)
    @Column(name = "rp_cedula")
    private String rpCedula;
    @Size(max = 200)
    @Column(name = "rp_nombre")
    private String rpNombre;
    @Size(max = 200)
    @Column(name = "rp_direccion")
    private String rpDireccion;
    @Size(max = 15)
    @Column(name = "rp_telefono")
    private String rpTelefono;
    @Size(max = 150)
    @Column(name = "rp_email")
    private String rpEmail;
    @Column(name = "estado")
    private Integer estado;
    @Lob
    @Size(max = 65535)
    @Column(name = "obs_cierre")
    private String obsCierre;
    @Size(max = 15)
    @Column(name = "usr_cierre")
    private String usrCierre;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.DATE)
    private Date fechaCierre;
    @Size(max = 15)
    @Column(name = "registrada_por")
    private String registradaPor;
    @Lob
    @Size(max = 65535)
    @Column(name = "c1")
    private String c1;
    @Lob
    @Size(max = 65535)
    @Column(name = "c2")
    private String c2;
    @Lob
    @Size(max = 65535)
    @Column(name = "c3")
    private String c3;
    @Lob
    @Size(max = 65535)
    @Column(name = "c4")
    private String c4;
    @Size(max = 100)
    @Column(name = "ar1")
    private String ar1;
    @Basic(optional = false)
    @Column(name = "af1")
    @Temporal(TemporalType.DATE)
    private Date af1;
    @Lob
    @Size(max = 65535)
    @Column(name = "aa1")
    private String aa1;
    @Size(max = 100)
    @Column(name = "ar2")
    private String ar2;
    @Basic(optional = false)
    @Column(name = "af2")
    @Temporal(TemporalType.DATE)
    private Date af2;
    @Lob
    @Size(max = 65535)
    @Column(name = "aa2")
    private String aa2;
    @Size(max = 100)
    @Column(name = "ar3")
    private String ar3;
    @Basic(optional = false)
    @Column(name = "af3")
    @Temporal(TemporalType.DATE)
    private Date af3;
    @Lob
    @Size(max = 65535)
    @Column(name = "aa3")
    private String aa3;
    @Size(max = 100)
    @Column(name = "ar4")
    private String ar4;
    @Basic(optional = false)
    @Column(name = "af4")
    @Temporal(TemporalType.DATE)
    private Date af4;
    @Lob
    @Size(max = 65535)
    @Column(name = "aa4")
    private String aa4;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "paradero")
    private String paradero;
    @Basic(optional = false)
    @Column(name = "id_procede")
    private int id_procede;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_prg_route", referencedColumnName = "id_prg_route")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PrgRoute idPrgRoute;
    @JoinColumn(name = "id_pqr_franja_horaria", referencedColumnName = "id_pqr_franja_horaria")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PqrFranjaHoraria idPqrFranjaHoraria;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
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
    @JoinColumn(name = "id_pqr_medio_reporte", referencedColumnName = "id_pqr_medio_reporte")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PqrMedioReporte idPqrMedioReporte;
    @JoinColumn(name = "id_pqr_tipo", referencedColumnName = "id_pqr_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PqrTipo idPqrTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPqrMaestro", fetch = FetchType.LAZY)
    private List<PqrMaestroDocumentos> pqrMaestroDocumentosList;

    public PqrMaestro() {
    }

    public PqrMaestro(Integer idPqrMaestro) {
        this.idPqrMaestro = idPqrMaestro;
    }

    public PqrMaestro(Integer idPqrMaestro, String username, Date creado, int estadoReg) {
        this.idPqrMaestro = idPqrMaestro;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPqrMaestro() {
        return idPqrMaestro;
    }

    public void setIdPqrMaestro(Integer idPqrMaestro) {
        this.idPqrMaestro = idPqrMaestro;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public Date getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(Date fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public Date getFecha_radicado() {
        return fecha_radicado;
    }

    public void setFecha_radicado(Date fecha_radicado) {
        this.fecha_radicado = fecha_radicado;
    }

    public Date getFecha_estimada1() {
        return fecha_estimada1;
    }

    public void setFecha_estimada1(Date fecha_estimada1) {
        this.fecha_estimada1 = fecha_estimada1;
    }

    public Date getFecha_estimada2() {
        return fecha_estimada2;
    }

    public void setFecha_estimada2(Date fecha_estimada2) {
        this.fecha_estimada2 = fecha_estimada2;
    }

    public String getRadicado() {
        return radicado;
    }

    public void setRadicado(String radicado) {
        this.radicado = radicado;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    public String getRpCedula() {
        return rpCedula;
    }

    public void setRpCedula(String rpCedula) {
        this.rpCedula = rpCedula;
    }

    public String getRpNombre() {
        return rpNombre;
    }

    public void setRpNombre(String rpNombre) {
        this.rpNombre = rpNombre;
    }

    public String getRpDireccion() {
        return rpDireccion;
    }

    public void setRpDireccion(String rpDireccion) {
        this.rpDireccion = rpDireccion;
    }

    public String getRpTelefono() {
        return rpTelefono;
    }

    public void setRpTelefono(String rpTelefono) {
        this.rpTelefono = rpTelefono;
    }

    public String getRpEmail() {
        return rpEmail;
    }

    public void setRpEmail(String rpEmail) {
        this.rpEmail = rpEmail;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getObsCierre() {
        return obsCierre;
    }

    public void setObsCierre(String obsCierre) {
        this.obsCierre = obsCierre;
    }

    public String getUsrCierre() {
        return usrCierre;
    }

    public void setUsrCierre(String usrCierre) {
        this.usrCierre = usrCierre;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getRegistradaPor() {
        return registradaPor;
    }

    public void setRegistradaPor(String registradaPor) {
        this.registradaPor = registradaPor;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public String getC4() {
        return c4;
    }

    public void setC4(String c4) {
        this.c4 = c4;
    }

    public String getAr1() {
        return ar1;
    }

    public void setAr1(String ar1) {
        this.ar1 = ar1;
    }

    public Date getAf1() {
        return af1;
    }

    public void setAf1(Date af1) {
        this.af1 = af1;
    }

    public String getAa1() {
        return aa1;
    }

    public void setAa1(String aa1) {
        this.aa1 = aa1;
    }

    public String getAr2() {
        return ar2;
    }

    public void setAr2(String ar2) {
        this.ar2 = ar2;
    }

    public Date getAf2() {
        return af2;
    }

    public void setAf2(Date af2) {
        this.af2 = af2;
    }

    public String getAa2() {
        return aa2;
    }

    public void setAa2(String aa2) {
        this.aa2 = aa2;
    }

    public String getAr3() {
        return ar3;
    }

    public void setAr3(String ar3) {
        this.ar3 = ar3;
    }

    public Date getAf3() {
        return af3;
    }

    public void setAf3(Date af3) {
        this.af3 = af3;
    }

    public String getAa3() {
        return aa3;
    }

    public void setAa3(String aa3) {
        this.aa3 = aa3;
    }

    public String getAr4() {
        return ar4;
    }

    public void setAr4(String ar4) {
        this.ar4 = ar4;
    }

    public Date getAf4() {
        return af4;
    }

    public void setAf4(Date af4) {
        this.af4 = af4;
    }

    public String getAa4() {
        return aa4;
    }

    public void setAa4(String aa4) {
        this.aa4 = aa4;
    }

    public int getId_procede() {
        return id_procede;
    }

    public void setId_procede(int id_procede) {
        this.id_procede = id_procede;
    }

    public PrgRoute getIdPrgRoute() {
        return idPrgRoute;
    }

    public void setIdPrgRoute(PrgRoute idPrgRoute) {
        this.idPrgRoute = idPrgRoute;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getParadero() {
        return paradero;
    }

    public void setParadero(String paradero) {
        this.paradero = paradero;
    }

    public PqrFranjaHoraria getIdPqrFranjaHoraria() {
        return idPqrFranjaHoraria;
    }

    public void setIdPqrFranjaHoraria(PqrFranjaHoraria idPqrFranjaHoraria) {
        this.idPqrFranjaHoraria = idPqrFranjaHoraria;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
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

    public PqrMedioReporte getIdPqrMedioReporte() {
        return idPqrMedioReporte;
    }

    public void setIdPqrMedioReporte(PqrMedioReporte idPqrMedioReporte) {
        this.idPqrMedioReporte = idPqrMedioReporte;
    }

    public PqrTipo getIdPqrTipo() {
        return idPqrTipo;
    }

    public void setIdPqrTipo(PqrTipo idPqrTipo) {
        this.idPqrTipo = idPqrTipo;
    }

    @XmlTransient
    public List<PqrMaestroDocumentos> getPqrMaestroDocumentosList() {
        return pqrMaestroDocumentosList;
    }

    public void setPqrMaestroDocumentosList(List<PqrMaestroDocumentos> pqrMaestroDocumentosList) {
        this.pqrMaestroDocumentosList = pqrMaestroDocumentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPqrMaestro != null ? idPqrMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PqrMaestro)) {
            return false;
        }
        PqrMaestro other = (PqrMaestro) object;
        if ((this.idPqrMaestro == null && other.idPqrMaestro != null) || (this.idPqrMaestro != null && !this.idPqrMaestro.equals(other.idPqrMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PqrMaestro[ idPqrMaestro=" + idPqrMaestro + " ]";
    }

}
