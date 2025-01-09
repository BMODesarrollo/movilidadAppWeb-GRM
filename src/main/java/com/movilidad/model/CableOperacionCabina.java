/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author solucionesit
 */
@Entity
@Table(name = "cable_operacion_cabina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableOperacionCabina.findAll", query = "SELECT c FROM CableOperacionCabina c")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByIdCableOperacionCabina", query = "SELECT c FROM CableOperacionCabina c WHERE c.idCableOperacionCabina = :idCableOperacionCabina")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByFecha", query = "SELECT c FROM CableOperacionCabina c WHERE c.fecha = :fecha")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByIdCableCabina", query = "SELECT c FROM CableOperacionCabina c WHERE c.idCableCabina = :idCableCabina")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByOperando", query = "SELECT c FROM CableOperacionCabina c WHERE c.operando = :operando")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByHoras", query = "SELECT c FROM CableOperacionCabina c WHERE c.horas = :horas")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByUsername", query = "SELECT c FROM CableOperacionCabina c WHERE c.username = :username")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByCreado", query = "SELECT c FROM CableOperacionCabina c WHERE c.creado = :creado")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByModificado", query = "SELECT c FROM CableOperacionCabina c WHERE c.modificado = :modificado")
    ,
    @NamedQuery(name = "CableOperacionCabina.findByEstadoReg", query = "SELECT c FROM CableOperacionCabina c WHERE c.estadoReg = :estadoReg")})
public class CableOperacionCabina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_operacion_cabina")
    private Integer idCableOperacionCabina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "id_cable_cabina", referencedColumnName = "id_cable_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableCabina idCableCabina;
    @JoinColumn(name = "id_cable_pinza", referencedColumnName = "id_cable_pinza")
    @ManyToOne(fetch = FetchType.LAZY)
    private CablePinza idCablePinza;
    @JoinColumn(name = "id_cable_pinza_para_hoy", referencedColumnName = "id_cable_pinza")
    @ManyToOne(fetch = FetchType.LAZY)
    private CablePinza idCablePinzaParaHoy;
    @Column(name = "operando")
    private Integer operando;
    @Column(name = "horas")
    private BigDecimal horas;
    @Column(name = "bateria_am")
    private Integer bateriaAm;
    @Column(name = "bateria_pm")
    private Integer bateriaPm;
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

    public CableOperacionCabina() {
    }

    public CableOperacionCabina(Integer idCableOperacionCabina) {
        this.idCableOperacionCabina = idCableOperacionCabina;
    }

    public CableOperacionCabina(Integer idCableOperacionCabina, Date fecha, CableCabina idCableCabina, String username, Date creado, int estadoReg) {
        this.idCableOperacionCabina = idCableOperacionCabina;
        this.fecha = fecha;
        this.idCableCabina = idCableCabina;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableOperacionCabina() {
        return idCableOperacionCabina;
    }

    public void setIdCableOperacionCabina(Integer idCableOperacionCabina) {
        this.idCableOperacionCabina = idCableOperacionCabina;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CableCabina getIdCableCabina() {
        return idCableCabina;
    }

    public void setIdCableCabina(CableCabina idCableCabina) {
        this.idCableCabina = idCableCabina;
    }

    public Integer getOperando() {
        return operando;
    }

    public void setOperando(Integer operando) {
        this.operando = operando;
    }

    public BigDecimal getHoras() {
        return horas;
    }

    public void setHoras(BigDecimal horas) {
        this.horas = horas;
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

    public Integer getBateriaAm() {
        return bateriaAm;
    }

    public void setBateriaAm(Integer bateriaAm) {
        this.bateriaAm = bateriaAm;
    }

    public Integer getBateriaPm() {
        return bateriaPm;
    }

    public void setBateriaPm(Integer bateriaPm) {
        this.bateriaPm = bateriaPm;
    }

    public CablePinza getIdCablePinza() {
        return idCablePinza;
    }

    public void setIdCablePinza(CablePinza idCablePinza) {
        this.idCablePinza = idCablePinza;
    }

    public CablePinza getIdCablePinzaParaHoy() {
        return idCablePinzaParaHoy;
    }

    public void setIdCablePinzaParaHoy(CablePinza idCablePinzaParaHoy) {
        this.idCablePinzaParaHoy = idCablePinzaParaHoy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableOperacionCabina != null ? idCableOperacionCabina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableOperacionCabina)) {
            return false;
        }
        CableOperacionCabina other = (CableOperacionCabina) object;
        if ((this.idCableOperacionCabina == null && other.idCableOperacionCabina != null) || (this.idCableOperacionCabina != null && !this.idCableOperacionCabina.equals(other.idCableOperacionCabina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableOperacionCabina[ idCableOperacionCabina=" + idCableOperacionCabina + " ]";
    }

}
