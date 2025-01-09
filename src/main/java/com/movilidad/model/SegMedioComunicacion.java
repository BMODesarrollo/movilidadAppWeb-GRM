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
@Table(name = "seg_medio_comunicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegMedioComunicacion.findAll", query = "SELECT s FROM SegMedioComunicacion s"),
    @NamedQuery(name = "SegMedioComunicacion.findByIdSegMedioComunicacion", query = "SELECT s FROM SegMedioComunicacion s WHERE s.idSegMedioComunicacion = :idSegMedioComunicacion"),
    @NamedQuery(name = "SegMedioComunicacion.findByCodigo", query = "SELECT s FROM SegMedioComunicacion s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "SegMedioComunicacion.findByMarca", query = "SELECT s FROM SegMedioComunicacion s WHERE s.marca = :marca"),
    @NamedQuery(name = "SegMedioComunicacion.findBySerial", query = "SELECT s FROM SegMedioComunicacion s WHERE s.serial = :serial"),
    @NamedQuery(name = "SegMedioComunicacion.findByModelo", query = "SELECT s FROM SegMedioComunicacion s WHERE s.modelo = :modelo"),
    @NamedQuery(name = "SegMedioComunicacion.findByImei", query = "SELECT s FROM SegMedioComunicacion s WHERE s.imei = :imei"),
    @NamedQuery(name = "SegMedioComunicacion.findByUsername", query = "SELECT s FROM SegMedioComunicacion s WHERE s.username = :username"),
    @NamedQuery(name = "SegMedioComunicacion.findByCreado", query = "SELECT s FROM SegMedioComunicacion s WHERE s.creado = :creado"),
    @NamedQuery(name = "SegMedioComunicacion.findByModificado", query = "SELECT s FROM SegMedioComunicacion s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SegMedioComunicacion.findByEstadoReg", query = "SELECT s FROM SegMedioComunicacion s WHERE s.estadoReg = :estadoReg")})
public class SegMedioComunicacion implements Serializable {

    @OneToMany(mappedBy = "idSegMedioComunicacion", fetch = FetchType.LAZY)
    private List<CableUbicacion> cableUbicacionList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_medio_comunicacion")
    private Integer idSegMedioComunicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "serial")
    private String serial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "imei")
    private String imei;
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

    @OneToMany(mappedBy = "idSegMedioComunicacion", fetch = FetchType.LAZY)
    private List<SegEstadoMedioComunica> segEstadoMedioComunicaList;

    public SegMedioComunicacion() {
    }

    public SegMedioComunicacion(Integer idSegMedioComunicacion) {
        this.idSegMedioComunicacion = idSegMedioComunicacion;
    }

    public SegMedioComunicacion(Integer idSegMedioComunicacion, String codigo, String marca, String serial, String modelo, String imei, String username, Date creado, int estadoReg) {
        this.idSegMedioComunicacion = idSegMedioComunicacion;
        this.codigo = codigo;
        this.marca = marca;
        this.serial = serial;
        this.modelo = modelo;
        this.imei = imei;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSegMedioComunicacion() {
        return idSegMedioComunicacion;
    }

    public void setIdSegMedioComunicacion(Integer idSegMedioComunicacion) {
        this.idSegMedioComunicacion = idSegMedioComunicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegMedioComunicacion != null ? idSegMedioComunicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegMedioComunicacion)) {
            return false;
        }
        SegMedioComunicacion other = (SegMedioComunicacion) object;
        if ((this.idSegMedioComunicacion == null && other.idSegMedioComunicacion != null) || (this.idSegMedioComunicacion != null && !this.idSegMedioComunicacion.equals(other.idSegMedioComunicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegMedioComunicacion[ idSegMedioComunicacion=" + idSegMedioComunicacion + " ]";
    }

    @XmlTransient
    public List<CableUbicacion> getCableUbicacionList() {
        return cableUbicacionList;
    }

    public void setCableUbicacionList(List<CableUbicacion> cableUbicacionList) {
        this.cableUbicacionList = cableUbicacionList;
    }

    @XmlTransient
    public List<SegEstadoMedioComunica> getSegEstadoMedioComunicaList() {
        return segEstadoMedioComunicaList;
    }

    public void setSegEstadoMedioComunicaList(List<SegEstadoMedioComunica> segEstadoMedioComunicaList) {
        this.segEstadoMedioComunicaList = segEstadoMedioComunicaList;
    }

}
