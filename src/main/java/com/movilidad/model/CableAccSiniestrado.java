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
 * @author soluciones-it
 */
@Entity
@Table(name = "cable_acc_siniestrado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableAccSiniestrado.findAll", query = "SELECT c FROM CableAccSiniestrado c")
    , @NamedQuery(name = "CableAccSiniestrado.findByIdCableAccSiniestrado", query = "SELECT c FROM CableAccSiniestrado c WHERE c.idCableAccSiniestrado = :idCableAccSiniestrado")
    , @NamedQuery(name = "CableAccSiniestrado.findByNombre", query = "SELECT c FROM CableAccSiniestrado c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "CableAccSiniestrado.findByApellidos", query = "SELECT c FROM CableAccSiniestrado c WHERE c.apellidos = :apellidos")
    , @NamedQuery(name = "CableAccSiniestrado.findByNumIdentificacion", query = "SELECT c FROM CableAccSiniestrado c WHERE c.numIdentificacion = :numIdentificacion")
    , @NamedQuery(name = "CableAccSiniestrado.findByEdad", query = "SELECT c FROM CableAccSiniestrado c WHERE c.edad = :edad")
    , @NamedQuery(name = "CableAccSiniestrado.findByDireccion", query = "SELECT c FROM CableAccSiniestrado c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "CableAccSiniestrado.findByTelefono", query = "SELECT c FROM CableAccSiniestrado c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "CableAccSiniestrado.findByNombreTecSalud", query = "SELECT c FROM CableAccSiniestrado c WHERE c.nombreTecSalud = :nombreTecSalud")
    , @NamedQuery(name = "CableAccSiniestrado.findByRealizaValoracionMedica", query = "SELECT c FROM CableAccSiniestrado c WHERE c.realizaValoracionMedica = :realizaValoracionMedica")
    , @NamedQuery(name = "CableAccSiniestrado.findByPresentaLesion", query = "SELECT c FROM CableAccSiniestrado c WHERE c.presentaLesion = :presentaLesion")
    , @NamedQuery(name = "CableAccSiniestrado.findByLesion", query = "SELECT c FROM CableAccSiniestrado c WHERE c.lesion = :lesion")
    , @NamedQuery(name = "CableAccSiniestrado.findByEntregaPacienteOtro", query = "SELECT c FROM CableAccSiniestrado c WHERE c.entregaPacienteOtro = :entregaPacienteOtro")
    , @NamedQuery(name = "CableAccSiniestrado.findByEntregaPacientePpAuxilios", query = "SELECT c FROM CableAccSiniestrado c WHERE c.entregaPacientePpAuxilios = :entregaPacientePpAuxilios")
    , @NamedQuery(name = "CableAccSiniestrado.findByDesistimiento", query = "SELECT c FROM CableAccSiniestrado c WHERE c.desistimiento = :desistimiento")
    , @NamedQuery(name = "CableAccSiniestrado.findByObservaciones", query = "SELECT c FROM CableAccSiniestrado c WHERE c.observaciones = :observaciones")
    , @NamedQuery(name = "CableAccSiniestrado.findByUsername", query = "SELECT c FROM CableAccSiniestrado c WHERE c.username = :username")
    , @NamedQuery(name = "CableAccSiniestrado.findByCreado", query = "SELECT c FROM CableAccSiniestrado c WHERE c.creado = :creado")
    , @NamedQuery(name = "CableAccSiniestrado.findByModificado", query = "SELECT c FROM CableAccSiniestrado c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "CableAccSiniestrado.findByEstadoReg", query = "SELECT c FROM CableAccSiniestrado c WHERE c.estadoReg = :estadoReg")})
public class CableAccSiniestrado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_acc_siniestrado")
    private Integer idCableAccSiniestrado;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 30)
    @Column(name = "num_identificacion")
    private String numIdentificacion;
    @Column(name = "edad")
    private Integer edad;
    @Size(max = 60)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 30)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "genero")
    private Character genero;
    @Size(max = 100)
    @Column(name = "nombre_tec_salud")
    private String nombreTecSalud;
    @Column(name = "realiza_valoracion_medica")
    private Integer realizaValoracionMedica;
    @Column(name = "presenta_lesion")
    private Integer presentaLesion;
    @Size(max = 60)
    @Column(name = "lesion")
    private String lesion;
    @Size(max = 60)
    @Column(name = "entrega_paciente_otro")
    private String entregaPacienteOtro;
    @Size(max = 60)
    @Column(name = "entrega_paciente_pp_auxilios")
    private String entregaPacientePpAuxilios;
    @Column(name = "desistimiento")
    private Integer desistimiento;
    @Size(max = 255)
    @Column(name = "observaciones")
    private String observaciones;
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
    @JoinColumn(name = "id_cable_acc_entrega_paciente", referencedColumnName = "id_cable_acc_entrega_paciente")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccEntregaPaciente idCableAccEntregaPaciente;
    @JoinColumn(name = "id_cable_acc_tp_usuario", referencedColumnName = "id_cable_acc_tipo_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccTipoUsuario idCableAccTpUsuario;
    @JoinColumn(name = "id_cable_acc_tp_asistencia", referencedColumnName = "id_cable_acc_tp_asistencia")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccTpAsistencia idCableAccTpAsistencia;
    @JoinColumn(name = "id_cable_accidentalidad", referencedColumnName = "id_cable_accidentalidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccidentalidad idCableAccidentalidad;
    @JoinColumn(name = "id_tipo_doc", referencedColumnName = "id_empleado_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoIdentificacion idTipoDoc;

    public CableAccSiniestrado() {
    }

    public CableAccSiniestrado(Integer idCableAccSiniestrado) {
        this.idCableAccSiniestrado = idCableAccSiniestrado;
    }

    public Integer getIdCableAccSiniestrado() {
        return idCableAccSiniestrado;
    }

    public void setIdCableAccSiniestrado(Integer idCableAccSiniestrado) {
        this.idCableAccSiniestrado = idCableAccSiniestrado;
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

    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }
    
    public String getNombreTecSalud() {
        return nombreTecSalud;
    }

    public void setNombreTecSalud(String nombreTecSalud) {
        this.nombreTecSalud = nombreTecSalud;
    }

    public Integer getRealizaValoracionMedica() {
        return realizaValoracionMedica;
    }

    public void setRealizaValoracionMedica(Integer realizaValoracionMedica) {
        this.realizaValoracionMedica = realizaValoracionMedica;
    }

    public Integer getPresentaLesion() {
        return presentaLesion;
    }

    public void setPresentaLesion(Integer presentaLesion) {
        this.presentaLesion = presentaLesion;
    }

    public String getLesion() {
        return lesion;
    }

    public void setLesion(String lesion) {
        this.lesion = lesion;
    }

    public String getEntregaPacienteOtro() {
        return entregaPacienteOtro;
    }

    public void setEntregaPacienteOtro(String entregaPacienteOtro) {
        this.entregaPacienteOtro = entregaPacienteOtro;
    }

    public String getEntregaPacientePpAuxilios() {
        return entregaPacientePpAuxilios;
    }

    public void setEntregaPacientePpAuxilios(String entregaPacientePpAuxilios) {
        this.entregaPacientePpAuxilios = entregaPacientePpAuxilios;
    }

    public Integer getDesistimiento() {
        return desistimiento;
    }

    public void setDesistimiento(Integer desistimiento) {
        this.desistimiento = desistimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public CableAccEntregaPaciente getIdCableAccEntregaPaciente() {
        return idCableAccEntregaPaciente;
    }

    public void setIdCableAccEntregaPaciente(CableAccEntregaPaciente idCableAccEntregaPaciente) {
        this.idCableAccEntregaPaciente = idCableAccEntregaPaciente;
    }

    public CableAccTipoUsuario getIdCableAccTpUsuario() {
        return idCableAccTpUsuario;
    }

    public void setIdCableAccTpUsuario(CableAccTipoUsuario idCableAccTpUsuario) {
        this.idCableAccTpUsuario = idCableAccTpUsuario;
    }

    public CableAccTpAsistencia getIdCableAccTpAsistencia() {
        return idCableAccTpAsistencia;
    }

    public void setIdCableAccTpAsistencia(CableAccTpAsistencia idCableAccTpAsistencia) {
        this.idCableAccTpAsistencia = idCableAccTpAsistencia;
    }

    public CableAccidentalidad getIdCableAccidentalidad() {
        return idCableAccidentalidad;
    }

    public void setIdCableAccidentalidad(CableAccidentalidad idCableAccidentalidad) {
        this.idCableAccidentalidad = idCableAccidentalidad;
    }

    public EmpleadoTipoIdentificacion getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(EmpleadoTipoIdentificacion idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccSiniestrado != null ? idCableAccSiniestrado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccSiniestrado)) {
            return false;
        }
        CableAccSiniestrado other = (CableAccSiniestrado) object;
        if ((this.idCableAccSiniestrado == null && other.idCableAccSiniestrado != null) || (this.idCableAccSiniestrado != null && !this.idCableAccSiniestrado.equals(other.idCableAccSiniestrado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccSiniestrado[ idCableAccSiniestrado=" + idCableAccSiniestrado + " ]";
    }
    
}
