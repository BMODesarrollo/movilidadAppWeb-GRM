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
@Table(name = "consumo_energia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConsumoEnergia.findAll", query = "SELECT c FROM ConsumoEnergia c")
    , @NamedQuery(name = "ConsumoEnergia.findByIdConsumoEnergia", query = "SELECT c FROM ConsumoEnergia c WHERE c.idConsumoEnergia = :idConsumoEnergia")
    , @NamedQuery(name = "ConsumoEnergia.findByFechaHora", query = "SELECT c FROM ConsumoEnergia c WHERE c.fechaHora = :fechaHora")
    , @NamedQuery(name = "ConsumoEnergia.findByLecturaUno", query = "SELECT c FROM ConsumoEnergia c WHERE c.lecturaUno = :lecturaUno")
    , @NamedQuery(name = "ConsumoEnergia.findByLecturaDos", query = "SELECT c FROM ConsumoEnergia c WHERE c.lecturaDos = :lecturaDos")
    , @NamedQuery(name = "ConsumoEnergia.findByVelocidad", query = "SELECT c FROM ConsumoEnergia c WHERE c.velocidad = :velocidad")
    , @NamedQuery(name = "ConsumoEnergia.findByUsername", query = "SELECT c FROM ConsumoEnergia c WHERE c.username = :username")
    , @NamedQuery(name = "ConsumoEnergia.findByCreado", query = "SELECT c FROM ConsumoEnergia c WHERE c.creado = :creado")
    , @NamedQuery(name = "ConsumoEnergia.findByModificado", query = "SELECT c FROM ConsumoEnergia c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "ConsumoEnergia.findByEstadoReg", query = "SELECT c FROM ConsumoEnergia c WHERE c.estadoReg = :estadoReg")})
public class ConsumoEnergia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_consumo_energia")
    private Integer idConsumoEnergia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lectura_uno")
    private BigDecimal lecturaUno;
    @Column(name = "lectura_dos")
    private BigDecimal lecturaDos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "velocidad")
    private BigDecimal velocidad;
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
    @JoinColumn(name = "id_consumo_energia_estado", referencedColumnName = "id_consumo_energia_estado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ConsumoEnergiaEstado idConsumoEnergiaEstado;

    public ConsumoEnergia() {
    }

    public ConsumoEnergia(Integer idConsumoEnergia) {
        this.idConsumoEnergia = idConsumoEnergia;
    }

    public ConsumoEnergia(Integer idConsumoEnergia, Date fechaHora, BigDecimal velocidad, String username, Date creado, int estadoReg) {
        this.idConsumoEnergia = idConsumoEnergia;
        this.fechaHora = fechaHora;
        this.velocidad = velocidad;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdConsumoEnergia() {
        return idConsumoEnergia;
    }

    public void setIdConsumoEnergia(Integer idConsumoEnergia) {
        this.idConsumoEnergia = idConsumoEnergia;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public BigDecimal getLecturaUno() {
        return lecturaUno;
    }

    public void setLecturaUno(BigDecimal lecturaUno) {
        this.lecturaUno = lecturaUno;
    }

    public BigDecimal getLecturaDos() {
        return lecturaDos;
    }

    public void setLecturaDos(BigDecimal lecturaDos) {
        this.lecturaDos = lecturaDos;
    }

    public BigDecimal getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(BigDecimal velocidad) {
        this.velocidad = velocidad;
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

    public ConsumoEnergiaEstado getIdConsumoEnergiaEstado() {
        return idConsumoEnergiaEstado;
    }

    public void setIdConsumoEnergiaEstado(ConsumoEnergiaEstado idConsumoEnergiaEstado) {
        this.idConsumoEnergiaEstado = idConsumoEnergiaEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConsumoEnergia != null ? idConsumoEnergia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsumoEnergia)) {
            return false;
        }
        ConsumoEnergia other = (ConsumoEnergia) object;
        if ((this.idConsumoEnergia == null && other.idConsumoEnergia != null) || (this.idConsumoEnergia != null && !this.idConsumoEnergia.equals(other.idConsumoEnergia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ConsumoEnergia[ idConsumoEnergia=" + idConsumoEnergia + " ]";
    }

}
