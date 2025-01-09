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
@Table(name = "generica_nomina_autorizacion_individual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findAll", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByIdGenericaNominaAutorizacionIndividual", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.idGenericaNominaAutorizacionIndividual = :idGenericaNominaAutorizacionIndividual"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByDesde", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.desde = :desde"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByHasta", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.hasta = :hasta"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByDiurnas", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.diurnas = :diurnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByNocturnas", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.nocturnas = :nocturnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByExtraDiurna", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.extraDiurna = :extraDiurna"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByFestivoDiurno", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.festivoDiurno = :festivoDiurno"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByFestivoNocturno", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.festivoNocturno = :festivoNocturno"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByFestivoExtraDiurno", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.festivoExtraDiurno = :festivoExtraDiurno"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByFestivoExtraNocturno", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.festivoExtraNocturno = :festivoExtraNocturno"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByExtraNocturna", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.extraNocturna = :extraNocturna"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByDominicalCompDiurnas", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.dominicalCompDiurnas = :dominicalCompDiurnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByDominicalCompNocturnas", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.dominicalCompNocturnas = :dominicalCompNocturnas"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByDominicalCompDiurnaExtra", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.dominicalCompDiurnaExtra = :dominicalCompDiurnaExtra"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByDominicalCompNocturnaExtra", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.dominicalCompNocturnaExtra = :dominicalCompNocturnaExtra"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByAprobacionGestor", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.aprobacionGestor = :aprobacionGestor"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByAprobacionProfOp", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.aprobacionProfOp = :aprobacionProfOp"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByAprobacionDirOp", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.aprobacionDirOp = :aprobacionDirOp"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByFechaPago", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.fechaPago = :fechaPago"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByEnviadoNomina", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.enviadoNomina = :enviadoNomina"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByUsername", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByCreado", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByModificado", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaNominaAutorizacionIndividual.findByEstadoReg", query = "SELECT g FROM GenericaNominaAutorizacionIndividual g WHERE g.estadoReg = :estadoReg")})
public class GenericaNominaAutorizacionIndividual implements Serializable {

    @OneToMany(mappedBy = "idGenericaNominaAutorizacionIndividual", fetch = FetchType.LAZY)
    private List<GenericaNominaAutorizacionDetIndividual> genericaNominaAutorizacionDetIndividualList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_nomina_autorizacion_individual")
    private Integer idGenericaNominaAutorizacionIndividual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Column(name = "diurnas")
    private Integer diurnas;
    @Column(name = "nocturnas")
    private Integer nocturnas;
    @Column(name = "extra_diurna")
    private Integer extraDiurna;
    @Column(name = "festivo_diurno")
    private Integer festivoDiurno;
    @Column(name = "festivo_nocturno")
    private Integer festivoNocturno;
    @Column(name = "festivo_extra_diurno")
    private Integer festivoExtraDiurno;
    @Column(name = "festivo_extra_nocturno")
    private Integer festivoExtraNocturno;
    @Column(name = "extra_nocturna")
    private Integer extraNocturna;
    @Column(name = "dominical_comp_diurnas")
    private Integer dominicalCompDiurnas;
    @Column(name = "dominical_comp_nocturnas")
    private Integer dominicalCompNocturnas;
    @Column(name = "dominical_comp_diurna_extra")
    private Integer dominicalCompDiurnaExtra;
    @Column(name = "dominical_comp_nocturna_extra")
    private Integer dominicalCompNocturnaExtra;
    @Column(name = "aprobacion_gestor")
    private Integer aprobacionGestor;
    @Column(name = "aprobacion_prof_op")
    private Integer aprobacionProfOp;
    @Column(name = "aprobacion_dir_op")
    private Integer aprobacionDirOp;
    @Column(name = "enviado_nomina")
    private Integer enviadoNomina;
    @Size(max = 15)
    @Column(name = "usr_gestor")
    private String usrGestor;
    @Size(max = 15)
    @Column(name = "usr_prof_op")
    private String usrProfOp;
    @Size(max = 15)
    @Column(name = "usr_dir_op")
    private String usrDirOp;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
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
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaNominaAutorizacionIndividual() {
    }

    public GenericaNominaAutorizacionIndividual(Integer idGenericaNominaAutorizacionIndividual) {
        this.idGenericaNominaAutorizacionIndividual = idGenericaNominaAutorizacionIndividual;
    }

    public GenericaNominaAutorizacionIndividual(Integer idGenericaNominaAutorizacionIndividual, Date desde, Date hasta, String username, Date creado, int estadoReg) {
        this.idGenericaNominaAutorizacionIndividual = idGenericaNominaAutorizacionIndividual;
        this.desde = desde;
        this.hasta = hasta;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaNominaAutorizacionIndividual() {
        return idGenericaNominaAutorizacionIndividual;
    }

    public void setIdGenericaNominaAutorizacionIndividual(Integer idGenericaNominaAutorizacionIndividual) {
        this.idGenericaNominaAutorizacionIndividual = idGenericaNominaAutorizacionIndividual;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getDiurnas() {
        return diurnas;
    }

    public void setDiurnas(Integer diurnas) {
        this.diurnas = diurnas;
    }

    public Integer getNocturnas() {
        return nocturnas;
    }

    public void setNocturnas(Integer nocturnas) {
        this.nocturnas = nocturnas;
    }

    public Integer getExtraDiurna() {
        return extraDiurna;
    }

    public void setExtraDiurna(Integer extraDiurna) {
        this.extraDiurna = extraDiurna;
    }

    public Integer getFestivoDiurno() {
        return festivoDiurno;
    }

    public void setFestivoDiurno(Integer festivoDiurno) {
        this.festivoDiurno = festivoDiurno;
    }

    public Integer getFestivoNocturno() {
        return festivoNocturno;
    }

    public void setFestivoNocturno(Integer festivoNocturno) {
        this.festivoNocturno = festivoNocturno;
    }

    public Integer getFestivoExtraDiurno() {
        return festivoExtraDiurno;
    }

    public void setFestivoExtraDiurno(Integer festivoExtraDiurno) {
        this.festivoExtraDiurno = festivoExtraDiurno;
    }

    public Integer getFestivoExtraNocturno() {
        return festivoExtraNocturno;
    }

    public void setFestivoExtraNocturno(Integer festivoExtraNocturno) {
        this.festivoExtraNocturno = festivoExtraNocturno;
    }

    public Integer getExtraNocturna() {
        return extraNocturna;
    }

    public void setExtraNocturna(Integer extraNocturna) {
        this.extraNocturna = extraNocturna;
    }

    public Integer getDominicalCompDiurnas() {
        return dominicalCompDiurnas;
    }

    public void setDominicalCompDiurnas(Integer dominicalCompDiurnas) {
        this.dominicalCompDiurnas = dominicalCompDiurnas;
    }

    public Integer getDominicalCompNocturnas() {
        return dominicalCompNocturnas;
    }

    public void setDominicalCompNocturnas(Integer dominicalCompNocturnas) {
        this.dominicalCompNocturnas = dominicalCompNocturnas;
    }

    public Integer getDominicalCompDiurnaExtra() {
        return dominicalCompDiurnaExtra;
    }

    public void setDominicalCompDiurnaExtra(Integer dominicalCompDiurnaExtra) {
        this.dominicalCompDiurnaExtra = dominicalCompDiurnaExtra;
    }

    public Integer getDominicalCompNocturnaExtra() {
        return dominicalCompNocturnaExtra;
    }

    public void setDominicalCompNocturnaExtra(Integer dominicalCompNocturnaExtra) {
        this.dominicalCompNocturnaExtra = dominicalCompNocturnaExtra;
    }

    public Integer getAprobacionGestor() {
        return aprobacionGestor;
    }

    public void setAprobacionGestor(Integer aprobacionGestor) {
        this.aprobacionGestor = aprobacionGestor;
    }

    public Integer getAprobacionProfOp() {
        return aprobacionProfOp;
    }

    public void setAprobacionProfOp(Integer aprobacionProfOp) {
        this.aprobacionProfOp = aprobacionProfOp;
    }

    public Integer getAprobacionDirOp() {
        return aprobacionDirOp;
    }

    public void setAprobacionDirOp(Integer aprobacionDirOp) {
        this.aprobacionDirOp = aprobacionDirOp;
    }

    public Integer getEnviadoNomina() {
        return enviadoNomina;
    }

    public void setEnviadoNomina(Integer enviadoNomina) {
        this.enviadoNomina = enviadoNomina;
    }

    public String getUsrGestor() {
        return usrGestor;
    }

    public void setUsrGestor(String usrGestor) {
        this.usrGestor = usrGestor;
    }

    public String getUsrProfOp() {
        return usrProfOp;
    }

    public void setUsrProfOp(String usrProfOp) {
        this.usrProfOp = usrProfOp;
    }

    public String getUsrDirOp() {
        return usrDirOp;
    }

    public void setUsrDirOp(String usrDirOp) {
        this.usrDirOp = usrDirOp;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaNominaAutorizacionIndividual != null ? idGenericaNominaAutorizacionIndividual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaNominaAutorizacionIndividual)) {
            return false;
        }
        GenericaNominaAutorizacionIndividual other = (GenericaNominaAutorizacionIndividual) object;
        if ((this.idGenericaNominaAutorizacionIndividual == null && other.idGenericaNominaAutorizacionIndividual != null) || (this.idGenericaNominaAutorizacionIndividual != null && !this.idGenericaNominaAutorizacionIndividual.equals(other.idGenericaNominaAutorizacionIndividual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaNominaAutorizacionIndividual[ idGenericaNominaAutorizacionIndividual=" + idGenericaNominaAutorizacionIndividual + " ]";
    }

    @XmlTransient
    public List<GenericaNominaAutorizacionDetIndividual> getGenericaNominaAutorizacionDetIndividualList() {
        return genericaNominaAutorizacionDetIndividualList;
    }

    public void setGenericaNominaAutorizacionDetIndividualList(List<GenericaNominaAutorizacionDetIndividual> genericaNominaAutorizacionDetIndividualList) {
        this.genericaNominaAutorizacionDetIndividualList = genericaNominaAutorizacionDetIndividualList;
    }

}
