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
@Table(name = "generica_nomina_autorizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaNominaAutorizacion.findAll", query = "SELECT g FROM GenericaNominaAutorizacion g"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByIdGenericaNominaAutorizacion", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.idGenericaNominaAutorizacion = :idGenericaNominaAutorizacion"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByDesde", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.desde = :desde"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByHasta", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.hasta = :hasta"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByDiurnas", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.diurnas = :diurnas"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByNocturnas", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.nocturnas = :nocturnas"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByExtraDiurna", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.extraDiurna = :extraDiurna"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByFestivoDiurno", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.festivoDiurno = :festivoDiurno"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByFestivoNocturno", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.festivoNocturno = :festivoNocturno"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByFestivoExtraDiurno", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.festivoExtraDiurno = :festivoExtraDiurno"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByFestivoExtraNocturno", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.festivoExtraNocturno = :festivoExtraNocturno"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByExtraNocturna", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.extraNocturna = :extraNocturna"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByDominicalCompDiurnas", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.dominicalCompDiurnas = :dominicalCompDiurnas"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByDominicalCompNocturnas", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.dominicalCompNocturnas = :dominicalCompNocturnas"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByDominicalCompDiurnaExtra", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.dominicalCompDiurnaExtra = :dominicalCompDiurnaExtra"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByDominicalCompNocturnaExtra", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.dominicalCompNocturnaExtra = :dominicalCompNocturnaExtra"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByAprobacionGestor", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.aprobacionGestor = :aprobacionGestor"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByAprobacionProfOp", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.aprobacionProfOp = :aprobacionProfOp"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByAprobacionDirOp", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.aprobacionDirOp = :aprobacionDirOp"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByFechaPago", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.fechaPago = :fechaPago"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByEnviadoNomina", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.enviadoNomina = :enviadoNomina"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByUsername", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByCreado", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByModificado", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaNominaAutorizacion.findByEstadoReg", query = "SELECT g FROM GenericaNominaAutorizacion g WHERE g.estadoReg = :estadoReg")})
public class GenericaNominaAutorizacion implements Serializable {

    @OneToMany(mappedBy = "idGenericaNominaAutorizacion", fetch = FetchType.LAZY)
    private List<GenericaNominaAutorizacionDet> genericaNominaAutorizacionDetList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_nomina_autorizacion")
    private Integer idGenericaNominaAutorizacion;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaNominaAutorizacion() {
    }

    public GenericaNominaAutorizacion(Integer idGenericaNominaAutorizacion) {
        this.idGenericaNominaAutorizacion = idGenericaNominaAutorizacion;
    }

    public GenericaNominaAutorizacion(Integer idGenericaNominaAutorizacion, Date desde, Date hasta, String username, Date creado, int estadoReg) {
        this.idGenericaNominaAutorizacion = idGenericaNominaAutorizacion;
        this.desde = desde;
        this.hasta = hasta;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaNominaAutorizacion() {
        return idGenericaNominaAutorizacion;
    }

    public void setIdGenericaNominaAutorizacion(Integer idGenericaNominaAutorizacion) {
        this.idGenericaNominaAutorizacion = idGenericaNominaAutorizacion;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaNominaAutorizacion != null ? idGenericaNominaAutorizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaNominaAutorizacion)) {
            return false;
        }
        GenericaNominaAutorizacion other = (GenericaNominaAutorizacion) object;
        if ((this.idGenericaNominaAutorizacion == null && other.idGenericaNominaAutorizacion != null) || (this.idGenericaNominaAutorizacion != null && !this.idGenericaNominaAutorizacion.equals(other.idGenericaNominaAutorizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaNominaAutorizacion[ idGenericaNominaAutorizacion=" + idGenericaNominaAutorizacion + " ]";
    }

    @XmlTransient
    public List<GenericaNominaAutorizacionDet> getGenericaNominaAutorizacionDetList() {
        return genericaNominaAutorizacionDetList;
    }

    public void setGenericaNominaAutorizacionDetList(List<GenericaNominaAutorizacionDet> genericaNominaAutorizacionDetList) {
        this.genericaNominaAutorizacionDetList = genericaNominaAutorizacionDetList;
    }

}
