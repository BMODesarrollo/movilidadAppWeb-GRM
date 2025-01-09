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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "novedad_autorizacion_ausentismos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findAll", query = "SELECT n FROM NovedadAutorizacionAusentismos n"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByIdNovedadAutorizacionAusentismo", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.idNovedadAutorizacionAusentismo = :idNovedadAutorizacionAusentismo"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByAprobacionGestor", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.aprobacionGestor = :aprobacionGestor"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByAprobacionProfOp", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.aprobacionProfOp = :aprobacionProfOp"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByAprobacionDirOp", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.aprobacionDirOp = :aprobacionDirOp"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByEnviadoNomina", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.enviadoNomina = :enviadoNomina"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByUsername", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByCreado", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByModificado", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadAutorizacionAusentismos.findByEstadoReg", query = "SELECT n FROM NovedadAutorizacionAusentismos n WHERE n.estadoReg = :estadoReg")})
public class NovedadAutorizacionAusentismos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_autorizacion_ausentismo")
    private Integer idNovedadAutorizacionAusentismo;
    @Column(name = "aprobacion_gestor")
    private Integer aprobacionGestor;
    @Column(name = "aprobacion_prof_op")
    private Integer aprobacionProfOp;
    @Column(name = "aprobacion_dir_op")
    private Integer aprobacionDirOp;
    @Column(name = "enviado_nomina")
    private Integer enviadoNomina;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Novedad idNovedad;

    public NovedadAutorizacionAusentismos() {
    }

    public NovedadAutorizacionAusentismos(Integer idNovedadAutorizacionAusentismo) {
        this.idNovedadAutorizacionAusentismo = idNovedadAutorizacionAusentismo;
    }

    public NovedadAutorizacionAusentismos(Integer idNovedadAutorizacionAusentismo, String username, Date creado, int estadoReg) {
        this.idNovedadAutorizacionAusentismo = idNovedadAutorizacionAusentismo;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadAutorizacionAusentismo() {
        return idNovedadAutorizacionAusentismo;
    }

    public void setIdNovedadAutorizacionAusentismo(Integer idNovedadAutorizacionAusentismo) {
        this.idNovedadAutorizacionAusentismo = idNovedadAutorizacionAusentismo;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadAutorizacionAusentismo != null ? idNovedadAutorizacionAusentismo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadAutorizacionAusentismos)) {
            return false;
        }
        NovedadAutorizacionAusentismos other = (NovedadAutorizacionAusentismos) object;
        if ((this.idNovedadAutorizacionAusentismo == null && other.idNovedadAutorizacionAusentismo != null) || (this.idNovedadAutorizacionAusentismo != null && !this.idNovedadAutorizacionAusentismo.equals(other.idNovedadAutorizacionAusentismo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadAutorizacionAusentismos[ idNovedadAutorizacionAusentismo=" + idNovedadAutorizacionAusentismo + " ]";
    }
    
}
