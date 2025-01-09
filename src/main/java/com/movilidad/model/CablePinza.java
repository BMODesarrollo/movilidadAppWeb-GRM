/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "cable_pinza")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CablePinza.findAll", query = "SELECT c FROM CablePinza c")
    , @NamedQuery(name = "CablePinza.findByIdCablePinza", query = "SELECT c FROM CablePinza c WHERE c.idCablePinza = :idCablePinza")
    , @NamedQuery(name = "CablePinza.findByCodigo", query = "SELECT c FROM CablePinza c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "CablePinza.findByNombre", query = "SELECT c FROM CablePinza c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "CablePinza.findByFechaIniOp", query = "SELECT c FROM CablePinza c WHERE c.fechaIniOp = :fechaIniOp")
    , @NamedQuery(name = "CablePinza.findByFechaFinOp", query = "SELECT c FROM CablePinza c WHERE c.fechaFinOp = :fechaFinOp")
    , @NamedQuery(name = "CablePinza.findByHorometro", query = "SELECT c FROM CablePinza c WHERE c.horometro = :horometro")
    , @NamedQuery(name = "CablePinza.findByUsername", query = "SELECT c FROM CablePinza c WHERE c.username = :username")
    , @NamedQuery(name = "CablePinza.findByCreado", query = "SELECT c FROM CablePinza c WHERE c.creado = :creado")
    , @NamedQuery(name = "CablePinza.findByModificado", query = "SELECT c FROM CablePinza c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "CablePinza.findByEstadoReg", query = "SELECT c FROM CablePinza c WHERE c.estadoReg = :estadoReg")
    , @NamedQuery(name = "CablePinza.findByMtto", query = "SELECT c FROM CablePinza c WHERE c.mtto = :mtto")})
public class CablePinza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_pinza")
    private Integer idCablePinza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ini_op")
    @Temporal(TemporalType.DATE)
    private Date fechaIniOp;
    @Column(name = "fecha_fin_op")
    @Temporal(TemporalType.DATE)
    private Date fechaFinOp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "horometro")
    private BigDecimal horometro;
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
    @Column(name = "mtto")
    private boolean mtto;

    @OneToMany(mappedBy = "idCablePinza", fetch = FetchType.LAZY)
    private List<CableOperacionCabina> cableOperacionCabinaList;
    @OneToMany(mappedBy = "idCablePinzaParaHoy", fetch = FetchType.LAZY)
    private List<CableOperacionCabina> cableOperacionCabinaList1;
    @OneToMany(mappedBy = "idCablePinza", fetch = FetchType.LAZY)
    private List<CableCabina> CableCabinaList;

    public CablePinza() {
    }

    public CablePinza(Integer idCablePinza) {
        this.idCablePinza = idCablePinza;
    }

    public CablePinza(Integer idCablePinza, String codigo, String nombre, Date fechaIniOp, BigDecimal horometro, String username, Date creado, int estadoReg) {
        this.idCablePinza = idCablePinza;
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaIniOp = fechaIniOp;
        this.horometro = horometro;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCablePinza() {
        return idCablePinza;
    }

    public void setIdCablePinza(Integer idCablePinza) {
        this.idCablePinza = idCablePinza;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaIniOp() {
        return fechaIniOp;
    }

    public void setFechaIniOp(Date fechaIniOp) {
        this.fechaIniOp = fechaIniOp;
    }

    public Date getFechaFinOp() {
        return fechaFinOp;
    }

    public void setFechaFinOp(Date fechaFinOp) {
        this.fechaFinOp = fechaFinOp;
    }

    public BigDecimal getHorometro() {
        return horometro;
    }

    public void setHorometro(BigDecimal horometro) {
        this.horometro = horometro;
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

    public boolean getMtto() {
        return mtto;
    }

    public void setMtto(boolean mtto) {
        this.mtto = mtto;
    }

    public List<CableOperacionCabina> getCableOperacionCabinaList() {
        return cableOperacionCabinaList;
    }

    public void setCableOperacionCabinaList(List<CableOperacionCabina> cableOperacionCabinaList) {
        this.cableOperacionCabinaList = cableOperacionCabinaList;
    }

    public List<CableCabina> getCableCabinaList() {
        return CableCabinaList;
    }

    public void setCableCabinaList(List<CableCabina> CableCabinaList) {
        this.CableCabinaList = CableCabinaList;
    }

    public List<CableOperacionCabina> getCableOperacionCabinaList1() {
        return cableOperacionCabinaList1;
    }

    public void setCableOperacionCabinaList1(List<CableOperacionCabina> cableOperacionCabinaList1) {
        this.cableOperacionCabinaList1 = cableOperacionCabinaList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCablePinza != null ? idCablePinza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CablePinza)) {
            return false;
        }
        CablePinza other = (CablePinza) object;
        if ((this.idCablePinza == null && other.idCablePinza != null) || (this.idCablePinza != null && !this.idCablePinza.equals(other.idCablePinza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CablePinza[ idCablePinza=" + idCablePinza + " ]";
    }

}
