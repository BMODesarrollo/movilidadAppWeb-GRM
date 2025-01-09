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
import javax.persistence.Lob;
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
 * @author HP
 */
@Entity
@Table(name = "accidente_diligenciasj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteDiligenciasj.findAll", query = "SELECT a FROM AccidenteDiligenciasj a")
    , @NamedQuery(name = "AccidenteDiligenciasj.findByIdAccidenteDiligenciasj", query = "SELECT a FROM AccidenteDiligenciasj a WHERE a.idAccidenteDiligenciasj = :idAccidenteDiligenciasj")
    , @NamedQuery(name = "AccidenteDiligenciasj.findByFecha", query = "SELECT a FROM AccidenteDiligenciasj a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AccidenteDiligenciasj.findByIdAccTipoProceso", query = "SELECT a FROM AccidenteDiligenciasj a WHERE a.idAccTipoProceso = :idAccTipoProceso")
    , @NamedQuery(name = "AccidenteDiligenciasj.findByIdAccEtapaProceso", query = "SELECT a FROM AccidenteDiligenciasj a WHERE a.idAccEtapaProceso = :idAccEtapaProceso")
    , @NamedQuery(name = "AccidenteDiligenciasj.findByUsername", query = "SELECT a FROM AccidenteDiligenciasj a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteDiligenciasj.findByCreado", query = "SELECT a FROM AccidenteDiligenciasj a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteDiligenciasj.findByModificado", query = "SELECT a FROM AccidenteDiligenciasj a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteDiligenciasj.findByEstadoReg", query = "SELECT a FROM AccidenteDiligenciasj a WHERE a.estadoReg = :estadoReg")})
public class AccidenteDiligenciasj implements Serializable {

    @JoinColumn(name = "id_acc_etapa_proceso", referencedColumnName = "id_acc_etapa_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccEtapaProceso idAccEtapaProceso;
    @JoinColumn(name = "id_acc_tipo_proceso", referencedColumnName = "id_acc_tipo_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoProceso idAccTipoProceso;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accidente_diligenciasj")
    private Integer idAccidenteDiligenciasj;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;

    public AccidenteDiligenciasj() {
    }

    public AccidenteDiligenciasj(Integer idAccidenteDiligenciasj) {
        this.idAccidenteDiligenciasj = idAccidenteDiligenciasj;
    }

    public Integer getIdAccidenteDiligenciasj() {
        return idAccidenteDiligenciasj;
    }

    public void setIdAccidenteDiligenciasj(Integer idAccidenteDiligenciasj) {
        this.idAccidenteDiligenciasj = idAccidenteDiligenciasj;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteDiligenciasj != null ? idAccidenteDiligenciasj.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteDiligenciasj)) {
            return false;
        }
        AccidenteDiligenciasj other = (AccidenteDiligenciasj) object;
        if ((this.idAccidenteDiligenciasj == null && other.idAccidenteDiligenciasj != null) || (this.idAccidenteDiligenciasj != null && !this.idAccidenteDiligenciasj.equals(other.idAccidenteDiligenciasj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteDiligenciasj[ idAccidenteDiligenciasj=" + idAccidenteDiligenciasj + " ]";
    }

    public AccEtapaProceso getIdAccEtapaProceso() {
        return idAccEtapaProceso;
    }

    public void setIdAccEtapaProceso(AccEtapaProceso idAccEtapaProceso) {
        this.idAccEtapaProceso = idAccEtapaProceso;
    }

    public AccTipoProceso getIdAccTipoProceso() {
        return idAccTipoProceso;
    }

    public void setIdAccTipoProceso(AccTipoProceso idAccTipoProceso) {
        this.idAccTipoProceso = idAccTipoProceso;
    }
    
}
