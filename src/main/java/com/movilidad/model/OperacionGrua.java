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
@Table(name = "operacion_grua")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperacionGrua.findAll", query = "SELECT o FROM OperacionGrua o"),
    @NamedQuery(name = "OperacionGrua.findByIdOperacionGrua", query = "SELECT o FROM OperacionGrua o WHERE o.idOperacionGrua = :idOperacionGrua"),
    @NamedQuery(name = "OperacionGrua.findByFecha", query = "SELECT o FROM OperacionGrua o WHERE o.fecha = :fecha"),
    @NamedQuery(name = "OperacionGrua.findByPlacaGrua", query = "SELECT o FROM OperacionGrua o WHERE o.placaGrua = :placaGrua"),
    @NamedQuery(name = "OperacionGrua.findByCcOperador", query = "SELECT o FROM OperacionGrua o WHERE o.ccOperador = :ccOperador"),
    @NamedQuery(name = "OperacionGrua.findByOperadorGrua", query = "SELECT o FROM OperacionGrua o WHERE o.operadorGrua = :operadorGrua"),
    @NamedQuery(name = "OperacionGrua.findByObservaciones", query = "SELECT o FROM OperacionGrua o WHERE o.observaciones = :observaciones"),
    @NamedQuery(name = "OperacionGrua.findByPathFotos", query = "SELECT o FROM OperacionGrua o WHERE o.pathFotos = :pathFotos"),
    @NamedQuery(name = "OperacionGrua.findByUsername", query = "SELECT o FROM OperacionGrua o WHERE o.username = :username"),
    @NamedQuery(name = "OperacionGrua.findByCreado", query = "SELECT o FROM OperacionGrua o WHERE o.creado = :creado"),
    @NamedQuery(name = "OperacionGrua.findByModificado", query = "SELECT o FROM OperacionGrua o WHERE o.modificado = :modificado"),
    @NamedQuery(name = "OperacionGrua.findByEstadoReg", query = "SELECT o FROM OperacionGrua o WHERE o.estadoReg = :estadoReg")})
public class OperacionGrua implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operacion_grua")
    private Integer idOperacionGrua;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 6)
    @Column(name = "placa_grua")
    private String placaGrua;
    @Size(max = 15)
    @Column(name = "cc_operador")
    private String ccOperador;
    @Size(max = 60)
    @Column(name = "operador_grua")
    private String operadorGrua;
    @Size(max = 30)
    @Column(name = "empresa_grua")
    private String empresaGrua;
    @Size(max = 150)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 60)
    @Column(name = "path_fotos")
    private String pathFotos;
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
    @JoinColumn(name = "id_operacion_patio", referencedColumnName = "id_operacion_patios")
    @ManyToOne(fetch = FetchType.LAZY)
    private OperacionPatios idOperacionPatio;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;

    public OperacionGrua() {
    }

    public OperacionGrua(Integer idOperacionGrua) {
        this.idOperacionGrua = idOperacionGrua;
    }

    public OperacionGrua(Integer idOperacionGrua, String username, Date creado, int estadoReg) {
        this.idOperacionGrua = idOperacionGrua;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdOperacionGrua() {
        return idOperacionGrua;
    }

    public void setIdOperacionGrua(Integer idOperacionGrua) {
        this.idOperacionGrua = idOperacionGrua;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPlacaGrua() {
        return placaGrua;
    }

    public void setPlacaGrua(String placaGrua) {
        this.placaGrua = placaGrua;
    }

    public String getCcOperador() {
        return ccOperador;
    }

    public void setCcOperador(String ccOperador) {
        this.ccOperador = ccOperador;
    }

    public String getOperadorGrua() {
        return operadorGrua;
    }

    public void setOperadorGrua(String operadorGrua) {
        this.operadorGrua = operadorGrua;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
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

    public OperacionPatios getIdOperacionPatio() {
        return idOperacionPatio;
    }

    public void setIdOperacionPatio(OperacionPatios idOperacionPatio) {
        this.idOperacionPatio = idOperacionPatio;
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
        hash += (idOperacionGrua != null ? idOperacionGrua.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperacionGrua)) {
            return false;
        }
        OperacionGrua other = (OperacionGrua) object;
        if ((this.idOperacionGrua == null && other.idOperacionGrua != null) || (this.idOperacionGrua != null && !this.idOperacionGrua.equals(other.idOperacionGrua))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.OperacionGrua[ idOperacionGrua=" + idOperacionGrua + " ]";
    }

    public String getEmpresaGrua() {
        return empresaGrua;
    }

    public void setEmpresaGrua(String empresaGrua) {
        this.empresaGrua = empresaGrua;
    }

}
