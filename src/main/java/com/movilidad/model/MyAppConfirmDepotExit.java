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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "my_app_confirm_depot_exit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MyAppConfirmDepotExit.findAll", query = "SELECT m FROM MyAppConfirmDepotExit m"),
    @NamedQuery(name = "MyAppConfirmDepotExit.findByIdMyAppConfirmDepotExit", query = "SELECT m FROM MyAppConfirmDepotExit m WHERE m.idMyAppConfirmDepotExit = :idMyAppConfirmDepotExit"),
    @NamedQuery(name = "MyAppConfirmDepotExit.findByFechaHora", query = "SELECT m FROM MyAppConfirmDepotExit m WHERE m.fechaHora = :fechaHora"),
    @NamedQuery(name = "MyAppConfirmDepotExit.findByProcesado", query = "SELECT m FROM MyAppConfirmDepotExit m WHERE m.procesado = :procesado"),
    @NamedQuery(name = "MyAppConfirmDepotExit.findByCreado", query = "SELECT m FROM MyAppConfirmDepotExit m WHERE m.creado = :creado"),
    @NamedQuery(name = "MyAppConfirmDepotExit.findByModificado", query = "SELECT m FROM MyAppConfirmDepotExit m WHERE m.modificado = :modificado"),
    @NamedQuery(name = "MyAppConfirmDepotExit.findByEstadoReg", query = "SELECT m FROM MyAppConfirmDepotExit m WHERE m.estadoReg = :estadoReg")})
public class MyAppConfirmDepotExit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_my_app_confirm_depot_exit")
    private Integer idMyAppConfirmDepotExit;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "procesado")
    private Integer procesado;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @Column(name = "verbo")
    private String verbo;
    @Column(name = "username")
    private String username;
    @Column(name = "id_task")
    private Integer idTask;
    @Column(name = "tipo_tabla")
    private Integer tipoTabla;
    @Column(name = "forzar")
    private Integer forzar;
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTc idPrgTc;

    public MyAppConfirmDepotExit() {
    }

    public MyAppConfirmDepotExit(Integer idMyAppConfirmDepotExit) {
        this.idMyAppConfirmDepotExit = idMyAppConfirmDepotExit;
    }

    public Integer getIdMyAppConfirmDepotExit() {
        return idMyAppConfirmDepotExit;
    }

    public void setIdMyAppConfirmDepotExit(Integer idMyAppConfirmDepotExit) {
        this.idMyAppConfirmDepotExit = idMyAppConfirmDepotExit;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getProcesado() {
        return procesado;
    }

    public void setProcesado(Integer procesado) {
        this.procesado = procesado;
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

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public PrgTc getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(PrgTc idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerbo() {
        return verbo;
    }

    public void setVerbo(String verbo) {
        this.verbo = verbo;
    }

    public Integer getTipoTabla() {
        return tipoTabla;
    }

    public void setTipoTabla(Integer tipoTabla) {
        this.tipoTabla = tipoTabla;
    }

    public Integer getForzar() {
        return forzar;
    }

    public void setForzar(Integer forzar) {
        this.forzar = forzar;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMyAppConfirmDepotExit != null ? idMyAppConfirmDepotExit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MyAppConfirmDepotExit)) {
            return false;
        }
        MyAppConfirmDepotExit other = (MyAppConfirmDepotExit) object;
        if ((this.idMyAppConfirmDepotExit == null && other.idMyAppConfirmDepotExit != null) || (this.idMyAppConfirmDepotExit != null && !this.idMyAppConfirmDepotExit.equals(other.idMyAppConfirmDepotExit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MyAppConfirmDepotExit[ idMyAppConfirmDepotExit=" + idMyAppConfirmDepotExit + " ]";
    }

}
