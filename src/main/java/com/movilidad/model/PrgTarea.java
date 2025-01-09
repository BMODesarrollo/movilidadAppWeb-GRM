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
 * @author HP
 */
@Entity
@Table(name = "prg_tarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgTarea.findAll", query = "SELECT p FROM PrgTarea p")
    ,
    @NamedQuery(name = "PrgTarea.findByIdPrgTarea", query = "SELECT p FROM PrgTarea p WHERE p.idPrgTarea = :idPrgTarea")
    ,
    @NamedQuery(name = "PrgTarea.findByTarea", query = "SELECT p FROM PrgTarea p WHERE p.tarea = :tarea")
    ,
    @NamedQuery(name = "PrgTarea.findBySumDistancia", query = "SELECT p FROM PrgTarea p WHERE p.sumDistancia = :sumDistancia")
    ,
    @NamedQuery(name = "PrgTarea.findByComercial", query = "SELECT p FROM PrgTarea p WHERE p.comercial = :comercial")
    ,
    @NamedQuery(name = "PrgTarea.findByMantenimiento", query = "SELECT p FROM PrgTarea p WHERE p.mantenimiento = :mantenimiento")
    ,
    @NamedQuery(name = "PrgTarea.findByOpDisponible", query = "SELECT p FROM PrgTarea p WHERE p.opDisponible = :opDisponible")
    ,
    @NamedQuery(name = "PrgTarea.findByUsername", query = "SELECT p FROM PrgTarea p WHERE p.username = :username")
    ,
    @NamedQuery(name = "PrgTarea.findByCreado", query = "SELECT p FROM PrgTarea p WHERE p.creado = :creado")
    ,
    @NamedQuery(name = "PrgTarea.findByModificado", query = "SELECT p FROM PrgTarea p WHERE p.modificado = :modificado")
    ,
    @NamedQuery(name = "PrgTarea.findByEstadoReg", query = "SELECT p FROM PrgTarea p WHERE p.estadoReg = :estadoReg")})
public class PrgTarea implements Serializable {

    @Column(name = "op_disponible")
    private int opDisponible;

    @OneToMany(mappedBy = "idTaskType", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_tarea")
    private Integer idPrgTarea;
    @Size(max = 45)
    @Column(name = "tarea")
    private String tarea;
    @Column(name = "sum_distancia")
    private Integer sumDistancia;
    @Column(name = "comercial")
    private Integer comercial;
    @Column(name = "mantenimiento")
    private Integer mantenimiento;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgTarea() {
    }

    public PrgTarea(Integer idPrgTarea) {
        this.idPrgTarea = idPrgTarea;
    }

    public PrgTarea(Integer idPrgTarea, String username, Date creado, int estadoReg) {
        this.idPrgTarea = idPrgTarea;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgTarea() {
        return idPrgTarea;
    }

    public void setIdPrgTarea(Integer idPrgTarea) {
        this.idPrgTarea = idPrgTarea;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public Integer getSumDistancia() {
        return sumDistancia;
    }

    public void setSumDistancia(Integer sumDistancia) {
        this.sumDistancia = sumDistancia;
    }

    public Integer getComercial() {
        return comercial;
    }

    public void setComercial(Integer comercial) {
        this.comercial = comercial;
    }

    public Integer getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Integer mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public int getOpDisponible() {
        return opDisponible;
    }

    public void setOpDisponible(int opDisponible) {
        this.opDisponible = opDisponible;
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
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgTarea != null ? idPrgTarea.hashCode() : 0);
        return hash;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgTarea)) {
            return false;
        }
        PrgTarea other = (PrgTarea) object;
        if ((this.idPrgTarea == null && other.idPrgTarea != null) || (this.idPrgTarea != null && !this.idPrgTarea.equals(other.idPrgTarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgTarea[ idPrgTarea=" + idPrgTarea + " ]";
    }
}
