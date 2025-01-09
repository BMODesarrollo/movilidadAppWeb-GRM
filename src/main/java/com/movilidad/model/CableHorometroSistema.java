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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "cable_horometro_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableHorometroSistema.findAll", query = "SELECT c FROM CableHorometroSistema c"),
    @NamedQuery(name = "CableHorometroSistema.findByIdCableHorometroSistema", query = "SELECT c FROM CableHorometroSistema c WHERE c.idCableHorometroSistema = :idCableHorometroSistema"),
    @NamedQuery(name = "CableHorometroSistema.findByFecha", query = "SELECT c FROM CableHorometroSistema c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CableHorometroSistema.findByHoraIniOperacion", query = "SELECT c FROM CableHorometroSistema c WHERE c.horaIniOperacion = :horaIniOperacion"),
    @NamedQuery(name = "CableHorometroSistema.findByHorometroInicial", query = "SELECT c FROM CableHorometroSistema c WHERE c.horometroInicial = :horometroInicial"),
    @NamedQuery(name = "CableHorometroSistema.findByHoraFinOperacion", query = "SELECT c FROM CableHorometroSistema c WHERE c.horaFinOperacion = :horaFinOperacion"),
    @NamedQuery(name = "CableHorometroSistema.findByHorasOperacionComercial", query = "SELECT c FROM CableHorometroSistema c WHERE c.horasOperacionComercial = :horasOperacionComercial"),
    @NamedQuery(name = "CableHorometroSistema.findByHorasSistema", query = "SELECT c FROM CableHorometroSistema c WHERE c.horasSistema = :horasSistema"),
    @NamedQuery(name = "CableHorometroSistema.findByHorasCabinas", query = "SELECT c FROM CableHorometroSistema c WHERE c.horasCabinas = :horasCabinas"),
    @NamedQuery(name = "CableHorometroSistema.findByUsername", query = "SELECT c FROM CableHorometroSistema c WHERE c.username = :username"),
    @NamedQuery(name = "CableHorometroSistema.findByCreado", query = "SELECT c FROM CableHorometroSistema c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableHorometroSistema.findByModificado", query = "SELECT c FROM CableHorometroSistema c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableHorometroSistema.findByEstadoReg", query = "SELECT c FROM CableHorometroSistema c WHERE c.estadoReg = :estadoReg")})
public class CableHorometroSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_horometro_sistema")
    private Integer idCableHorometroSistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 8)
    @Column(name = "hora_ini_operacion")
    private String horaIniOperacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "horometro_inicial")
    private BigDecimal horometroInicial;
    @Size(max = 8)
    @Column(name = "hora_fin_operacion")
    private String horaFinOperacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "horometro_final")
    private BigDecimal horometroFinal;
    @Column(name = "horas_operacion_comercial")
    private BigDecimal horasOperacionComercial;
    @Column(name = "horas_sistema")
    private BigDecimal horasSistema;
    @Column(name = "horas_cabinas")
    private BigDecimal horasCabinas;
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

    public CableHorometroSistema() {
    }

    public CableHorometroSistema(Integer idCableHorometroSistema) {
        this.idCableHorometroSistema = idCableHorometroSistema;
    }

    public CableHorometroSistema(Integer idCableHorometroSistema, Date fecha, String username, Date creado, int estadoReg) {
        this.idCableHorometroSistema = idCableHorometroSistema;
        this.fecha = fecha;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableHorometroSistema() {
        return idCableHorometroSistema;
    }

    public void setIdCableHorometroSistema(Integer idCableHorometroSistema) {
        this.idCableHorometroSistema = idCableHorometroSistema;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoraIniOperacion() {
        return horaIniOperacion;
    }

    public void setHoraIniOperacion(String horaIniOperacion) {
        this.horaIniOperacion = horaIniOperacion;
    }

    public BigDecimal getHorometroInicial() {
        return horometroInicial;
    }

    public void setHorometroInicial(BigDecimal horometroInicial) {
        this.horometroInicial = horometroInicial;
    }

    public String getHoraFinOperacion() {
        return horaFinOperacion;
    }

    public void setHoraFinOperacion(String horaFinOperacion) {
        this.horaFinOperacion = horaFinOperacion;
    }

    public BigDecimal getHorasOperacionComercial() {
        return horasOperacionComercial;
    }

    public void setHorasOperacionComercial(BigDecimal horasOperacionComercial) {
        this.horasOperacionComercial = horasOperacionComercial;
    }

    public BigDecimal getHorasSistema() {
        return horasSistema;
    }

    public void setHorasSistema(BigDecimal horasSistema) {
        this.horasSistema = horasSistema;
    }

    public BigDecimal getHorasCabinas() {
        return horasCabinas;
    }

    public void setHorasCabinas(BigDecimal horasCabinas) {
        this.horasCabinas = horasCabinas;
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

    public BigDecimal getHorometroFinal() {
        return horometroFinal;
    }

    public void setHorometroFinal(BigDecimal horometroFinal) {
        this.horometroFinal = horometroFinal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableHorometroSistema != null ? idCableHorometroSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableHorometroSistema)) {
            return false;
        }
        CableHorometroSistema other = (CableHorometroSistema) object;
        if ((this.idCableHorometroSistema == null && other.idCableHorometroSistema != null) || (this.idCableHorometroSistema != null && !this.idCableHorometroSistema.equals(other.idCableHorometroSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableHorometroSistema[ idCableHorometroSistema=" + idCableHorometroSistema + " ]";
    }
    
}
