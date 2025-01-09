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
 * @author HP
 */
@Entity
@Table(name = "vehiculo_idf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoIdf.findAll", query = "SELECT v FROM VehiculoIdf v"),
    @NamedQuery(name = "VehiculoIdf.findByIdVehiculoIdf", query = "SELECT v FROM VehiculoIdf v WHERE v.idVehiculoIdf = :idVehiculoIdf"),
    @NamedQuery(name = "VehiculoIdf.findByFecha", query = "SELECT v FROM VehiculoIdf v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VehiculoIdf.findByFecha_Fin", query = "SELECT v FROM VehiculoIdf v WHERE v.fecha_fin = :fecha_fin"),
    @NamedQuery(name = "VehiculoIdf.findByKm", query = "SELECT v FROM VehiculoIdf v WHERE v.km = :km"),
    @NamedQuery(name = "VehiculoIdf.findByMetros", query = "SELECT v FROM VehiculoIdf v WHERE v.metros = :metros"),
    @NamedQuery(name = "VehiculoIdf.findByPathDocumento", query = "SELECT v FROM VehiculoIdf v WHERE v.pathDocumento = :pathDocumento"),
    @NamedQuery(name = "VehiculoIdf.findByUsername", query = "SELECT v FROM VehiculoIdf v WHERE v.username = :username"),
    @NamedQuery(name = "VehiculoIdf.findByCreado", query = "SELECT v FROM VehiculoIdf v WHERE v.creado = :creado"),
    @NamedQuery(name = "VehiculoIdf.findByModificado", query = "SELECT v FROM VehiculoIdf v WHERE v.modificado = :modificado"),
    @NamedQuery(name = "VehiculoIdf.findByEstadoReg", query = "SELECT v FROM VehiculoIdf v WHERE v.estadoReg = :estadoReg")})
public class VehiculoIdf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_idf")
    private Integer idVehiculoIdf;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fecha_fin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "km")
    private BigDecimal km;
    @Column(name = "metros")
    private BigDecimal metros;
    @Size(max = 100)
    @Column(name = "path_documento")
    private String pathDocumento;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;

    public VehiculoIdf() {
    }

    public VehiculoIdf(Integer idVehiculoIdf) {
        this.idVehiculoIdf = idVehiculoIdf;
    }

    public VehiculoIdf(Integer idVehiculoIdf, String username, Date creado, int estadoReg) {
        this.idVehiculoIdf = idVehiculoIdf;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoIdf() {
        return idVehiculoIdf;
    }

    public void setIdVehiculoIdf(Integer idVehiculoIdf) {
        this.idVehiculoIdf = idVehiculoIdf;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public BigDecimal getKm() {
        return km;
    }

    public void setKm(BigDecimal km) {
        this.km = km;
    }

    public BigDecimal getMetros() {
        return metros;
    }

    public void setMetros(BigDecimal metros) {
        this.metros = metros;
    }

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
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
        hash += (idVehiculoIdf != null ? idVehiculoIdf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoIdf)) {
            return false;
        }
        VehiculoIdf other = (VehiculoIdf) object;
        if ((this.idVehiculoIdf == null && other.idVehiculoIdf != null) || (this.idVehiculoIdf != null && !this.idVehiculoIdf.equals(other.idVehiculoIdf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoIdf[ idVehiculoIdf=" + idVehiculoIdf + " ]";
    }

}
