/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "sst_es_mat_equi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstEsMatEqui.findAll", query = "SELECT s FROM SstEsMatEqui s"),
    @NamedQuery(name = "SstEsMatEqui.findByIdSstEsMatEqui", query = "SELECT s FROM SstEsMatEqui s WHERE s.idSstEsMatEqui = :idSstEsMatEqui"),
    @NamedQuery(name = "SstEsMatEqui.findByTipoAcceso", query = "SELECT s FROM SstEsMatEqui s WHERE s.tipoAcceso = :tipoAcceso"),
    @NamedQuery(name = "SstEsMatEqui.findByEstado", query = "SELECT s FROM SstEsMatEqui s WHERE s.estado = :estado"),
    @NamedQuery(name = "SstEsMatEqui.findBySalidaAprobada", query = "SELECT s FROM SstEsMatEqui s WHERE s.salidaAprobada = :salidaAprobada"),
    @NamedQuery(name = "SstEsMatEqui.findByEntradaAprobada", query = "SELECT s FROM SstEsMatEqui s WHERE s.entradaAprobada = :entradaAprobada"),
    @NamedQuery(name = "SstEsMatEqui.findByFechaAprobacionEntrada", query = "SELECT s FROM SstEsMatEqui s WHERE s.fechaAprobacionEntrada = :fechaAprobacionEntrada"),
    @NamedQuery(name = "SstEsMatEqui.findByFechaAprobacionSalida", query = "SELECT s FROM SstEsMatEqui s WHERE s.fechaAprobacionSalida = :fechaAprobacionSalida"),
    @NamedQuery(name = "SstEsMatEqui.findByUserApruebaEntrada", query = "SELECT s FROM SstEsMatEqui s WHERE s.userApruebaEntrada = :userApruebaEntrada"),
    @NamedQuery(name = "SstEsMatEqui.findByUserApruebaSalida", query = "SELECT s FROM SstEsMatEqui s WHERE s.userApruebaSalida = :userApruebaSalida"),
    @NamedQuery(name = "SstEsMatEqui.findByUsername", query = "SELECT s FROM SstEsMatEqui s WHERE s.username = :username"),
    @NamedQuery(name = "SstEsMatEqui.findByCreado", query = "SELECT s FROM SstEsMatEqui s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstEsMatEqui.findByModificado", query = "SELECT s FROM SstEsMatEqui s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstEsMatEqui.findByEstadoReg", query = "SELECT s FROM SstEsMatEqui s WHERE s.estadoReg = :estadoReg")})
public class SstEsMatEqui implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSstEsMatEqui", fetch = FetchType.LAZY)
    private List<SstAutorizacion> sstAutorizacionList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_es_mat_equi")
    private Integer idSstEsMatEqui;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_acceso")
    private int tipoAcceso;
    @Column(name = "estado")
    private Integer estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "salida_aprobada")
    private int salidaAprobada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "entrada_aprobada")
    private int entradaAprobada;
    @Column(name = "fecha_aprobacion_entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprobacionEntrada;
    @Column(name = "fecha_aprobacion_salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprobacionSalida;
    @Size(min = 1, max = 15)
    @Column(name = "user_aprueba_entrada")
    private String userApruebaEntrada;
    @Size(min = 1, max = 15)
    @Column(name = "user_aprueba_salida")
    private String userApruebaSalida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
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
    @JoinColumn(name = "id_sst_empresa_visitante", referencedColumnName = "id_sst_empresa_visitante")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstEmpresaVisitante idSstEmpresaVisitante;
    @OneToMany(mappedBy = "idSstEsMatEqui", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SstEsMatEquiDet> sstEsMatEquiDetList;

    public SstEsMatEqui() {
    }

    public SstEsMatEqui(Integer idSstEsMatEqui) {
        this.idSstEsMatEqui = idSstEsMatEqui;
    }

    public SstEsMatEqui(Integer idSstEsMatEqui, int tipoAcceso, int salidaAprobada, int entradaAprobada, String userApruebaEntrada, String userApruebaSalida, String username) {
        this.idSstEsMatEqui = idSstEsMatEqui;
        this.tipoAcceso = tipoAcceso;
        this.salidaAprobada = salidaAprobada;
        this.entradaAprobada = entradaAprobada;
        this.userApruebaEntrada = userApruebaEntrada;
        this.userApruebaSalida = userApruebaSalida;
        this.username = username;
    }

    public Integer getIdSstEsMatEqui() {
        return idSstEsMatEqui;
    }

    public void setIdSstEsMatEqui(Integer idSstEsMatEqui) {
        this.idSstEsMatEqui = idSstEsMatEqui;
    }

    public int getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(int tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public int getSalidaAprobada() {
        return salidaAprobada;
    }

    public void setSalidaAprobada(int salidaAprobada) {
        this.salidaAprobada = salidaAprobada;
    }

    public int getEntradaAprobada() {
        return entradaAprobada;
    }

    public void setEntradaAprobada(int entradaAprobada) {
        this.entradaAprobada = entradaAprobada;
    }

    public Date getFechaAprobacionEntrada() {
        return fechaAprobacionEntrada;
    }

    public void setFechaAprobacionEntrada(Date fechaAprobacionEntrada) {
        this.fechaAprobacionEntrada = fechaAprobacionEntrada;
    }

    public Date getFechaAprobacionSalida() {
        return fechaAprobacionSalida;
    }

    public void setFechaAprobacionSalida(Date fechaAprobacionSalida) {
        this.fechaAprobacionSalida = fechaAprobacionSalida;
    }

    public String getUserApruebaEntrada() {
        return userApruebaEntrada;
    }

    public void setUserApruebaEntrada(String userApruebaEntrada) {
        this.userApruebaEntrada = userApruebaEntrada;
    }

    public String getUserApruebaSalida() {
        return userApruebaSalida;
    }

    public void setUserApruebaSalida(String userApruebaSalida) {
        this.userApruebaSalida = userApruebaSalida;
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

    public SstEmpresaVisitante getIdSstEmpresaVisitante() {
        return idSstEmpresaVisitante;
    }

    public void setIdSstEmpresaVisitante(SstEmpresaVisitante idSstEmpresaVisitante) {
        this.idSstEmpresaVisitante = idSstEmpresaVisitante;
    }

    @XmlTransient
    public List<SstEsMatEquiDet> getSstEsMatEquiDetList() {
        Collections.reverse(sstEsMatEquiDetList);
        return sstEsMatEquiDetList;
    }

    public void setSstEsMatEquiDetList(List<SstEsMatEquiDet> sstEsMatEquiDetList) {
        this.sstEsMatEquiDetList = sstEsMatEquiDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstEsMatEqui != null ? idSstEsMatEqui.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstEsMatEqui)) {
            return false;
        }
        SstEsMatEqui other = (SstEsMatEqui) object;
        if ((this.idSstEsMatEqui == null && other.idSstEsMatEqui != null) || (this.idSstEsMatEqui != null && !this.idSstEsMatEqui.equals(other.idSstEsMatEqui))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstEsMatEqui[ idSstEsMatEqui=" + idSstEsMatEqui + " ]";
    }

    @XmlTransient
    public List<SstAutorizacion> getSstAutorizacionList() {
        return sstAutorizacionList;
    }

    public void setSstAutorizacionList(List<SstAutorizacion> sstAutorizacionList) {
        this.sstAutorizacionList = sstAutorizacionList;
    }

}
