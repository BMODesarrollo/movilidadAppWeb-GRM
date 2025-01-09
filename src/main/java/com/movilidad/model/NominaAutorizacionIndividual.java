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
@Table(name = "nomina_autorizacion_individual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NominaAutorizacionIndividual.findAll", query = "SELECT n FROM NominaAutorizacionIndividual n"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByIdNominaAutorizacionIndividual", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.idNominaAutorizacionIndividual = :idNominaAutorizacionIndividual"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByDesde", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.desde = :desde"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByHasta", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.hasta = :hasta"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByDiurnas", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.diurnas = :diurnas"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByNocturnas", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.nocturnas = :nocturnas"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByExtraDiurna", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.extraDiurna = :extraDiurna"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByFestivoDiurno", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.festivoDiurno = :festivoDiurno"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByFestivoNocturno", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.festivoNocturno = :festivoNocturno"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByFestivoExtraDiurno", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.festivoExtraDiurno = :festivoExtraDiurno"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByFestivoExtraNocturno", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.festivoExtraNocturno = :festivoExtraNocturno"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByExtraNocturna", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.extraNocturna = :extraNocturna"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByDominicalCompDiurnas", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.dominicalCompDiurnas = :dominicalCompDiurnas"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByDominicalCompNocturnas", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.dominicalCompNocturnas = :dominicalCompNocturnas"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByDominicalCompDiurnaExtra", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.dominicalCompDiurnaExtra = :dominicalCompDiurnaExtra"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByDominicalCompNocturnaExtra", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.dominicalCompNocturnaExtra = :dominicalCompNocturnaExtra"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByAprobacionGestor", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.aprobacionGestor = :aprobacionGestor"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByAprobacionProfOp", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.aprobacionProfOp = :aprobacionProfOp"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByAprobacionDirOp", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.aprobacionDirOp = :aprobacionDirOp"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByFechaPago", query = "SELECT g FROM NominaAutorizacionIndividual g WHERE g.fechaPago = :fechaPago"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByEnviadoNomina", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.enviadoNomina = :enviadoNomina"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByUsername", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.username = :username"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByCreado", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.creado = :creado"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByModificado", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NominaAutorizacionIndividual.findByEstadoReg", query = "SELECT n FROM NominaAutorizacionIndividual n WHERE n.estadoReg = :estadoReg")})
public class NominaAutorizacionIndividual implements Serializable {

    @OneToMany(mappedBy = "idNominaAutorizacionIndividual", fetch = FetchType.LAZY)
    private List<NominaAutorizacionDetIndividual> nominaAutorizacionDetIndividualList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nomina_autorizacion_individual")
    private Integer idNominaAutorizacionIndividual;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public NominaAutorizacionIndividual() {
    }

    public NominaAutorizacionIndividual(Integer idNominaAutorizacionIndividual) {
        this.idNominaAutorizacionIndividual = idNominaAutorizacionIndividual;
    }

    public NominaAutorizacionIndividual(Integer idNominaAutorizacionIndividual, Date desde, Date hasta, String username, Date creado, int estadoReg) {
        this.idNominaAutorizacionIndividual = idNominaAutorizacionIndividual;
        this.desde = desde;
        this.hasta = hasta;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNominaAutorizacionIndividual() {
        return idNominaAutorizacionIndividual;
    }

    public void setIdNominaAutorizacionIndividual(Integer idNominaAutorizacionIndividual) {
        this.idNominaAutorizacionIndividual = idNominaAutorizacionIndividual;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNominaAutorizacionIndividual != null ? idNominaAutorizacionIndividual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaAutorizacionIndividual)) {
            return false;
        }
        NominaAutorizacionIndividual other = (NominaAutorizacionIndividual) object;
        if ((this.idNominaAutorizacionIndividual == null && other.idNominaAutorizacionIndividual != null) || (this.idNominaAutorizacionIndividual != null && !this.idNominaAutorizacionIndividual.equals(other.idNominaAutorizacionIndividual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NominaAutorizacionIndividual[ idNominaAutorizacionIndividual=" + idNominaAutorizacionIndividual + " ]";
    }

    @XmlTransient
    public List<NominaAutorizacionDetIndividual> getNominaAutorizacionDetIndividualList() {
        return nominaAutorizacionDetIndividualList;
    }

    public void setNominaAutorizacionDetIndividualList(List<NominaAutorizacionDetIndividual> nominaAutorizacionDetIndividualList) {
        this.nominaAutorizacionDetIndividualList = nominaAutorizacionDetIndividualList;
    }

}
