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
 * @author HP
 */
@Entity
@Table(name = "operacion_km_tacografo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperacionKmTacografo.findAll", query = "SELECT o FROM OperacionKmTacografo o")
    , @NamedQuery(name = "OperacionKmTacografo.findByIdOperacionKmTacografo", query = "SELECT o FROM OperacionKmTacografo o WHERE o.idOperacionKmTacografo = :idOperacionKmTacografo")
    , @NamedQuery(name = "OperacionKmTacografo.findByFecha", query = "SELECT o FROM OperacionKmTacografo o WHERE o.fecha = :fecha")
    , @NamedQuery(name = "OperacionKmTacografo.findByKmInicial", query = "SELECT o FROM OperacionKmTacografo o WHERE o.kmInicial = :kmInicial")
    , @NamedQuery(name = "OperacionKmTacografo.findByKmFinal", query = "SELECT o FROM OperacionKmTacografo o WHERE o.kmFinal = :kmFinal")
    , @NamedQuery(name = "OperacionKmTacografo.findByPathTacografo", query = "SELECT o FROM OperacionKmTacografo o WHERE o.pathTacografo = :pathTacografo")
    , @NamedQuery(name = "OperacionKmTacografo.findByUsername", query = "SELECT o FROM OperacionKmTacografo o WHERE o.username = :username")
    , @NamedQuery(name = "OperacionKmTacografo.findByCreado", query = "SELECT o FROM OperacionKmTacografo o WHERE o.creado = :creado")
    , @NamedQuery(name = "OperacionKmTacografo.findByModificado", query = "SELECT o FROM OperacionKmTacografo o WHERE o.modificado = :modificado")
    , @NamedQuery(name = "OperacionKmTacografo.findByEstadoReg", query = "SELECT o FROM OperacionKmTacografo o WHERE o.estadoReg = :estadoReg")})
public class OperacionKmTacografo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operacion_km_tacografo")
    private Integer idOperacionKmTacografo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "km_inicial")
    private int kmInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "km_final")
    private int kmFinal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "path_tacografo")
    private String pathTacografo;
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
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;

    public OperacionKmTacografo() {
    }

    public OperacionKmTacografo(Integer idOperacionKmTacografo) {
        this.idOperacionKmTacografo = idOperacionKmTacografo;
    }

    public OperacionKmTacografo(Integer idOperacionKmTacografo, Date fecha, int kmInicial, int kmFinal, String pathTacografo, String username, Date creado, int estadoReg) {
        this.idOperacionKmTacografo = idOperacionKmTacografo;
        this.fecha = fecha;
        this.kmInicial = kmInicial;
        this.kmFinal = kmFinal;
        this.pathTacografo = pathTacografo;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdOperacionKmTacografo() {
        return idOperacionKmTacografo;
    }

    public void setIdOperacionKmTacografo(Integer idOperacionKmTacografo) {
        this.idOperacionKmTacografo = idOperacionKmTacografo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getKmInicial() {
        return kmInicial;
    }

    public void setKmInicial(int kmInicial) {
        this.kmInicial = kmInicial;
    }

    public int getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(int kmFinal) {
        this.kmFinal = kmFinal;
    }

    public String getPathTacografo() {
        return pathTacografo;
    }

    public void setPathTacografo(String pathTacografo) {
        this.pathTacografo = pathTacografo;
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

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperacionKmTacografo != null ? idOperacionKmTacografo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperacionKmTacografo)) {
            return false;
        }
        OperacionKmTacografo other = (OperacionKmTacografo) object;
        if ((this.idOperacionKmTacografo == null && other.idOperacionKmTacografo != null) || (this.idOperacionKmTacografo != null && !this.idOperacionKmTacografo.equals(other.idOperacionKmTacografo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.OperacionKmTacografo[ idOperacionKmTacografo=" + idOperacionKmTacografo + " ]";
    }
    
}
