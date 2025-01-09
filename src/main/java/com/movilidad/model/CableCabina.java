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
 * @author cesar
 */
@Entity
@Table(name = "cable_cabina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableCabina.findAll", query = "SELECT c FROM CableCabina c"),
    @NamedQuery(name = "CableCabina.findByIdCableCabina", query = "SELECT c FROM CableCabina c WHERE c.idCableCabina = :idCableCabina"),
    @NamedQuery(name = "CableCabina.findByCodigo", query = "SELECT c FROM CableCabina c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CableCabina.findByNombre", query = "SELECT c FROM CableCabina c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CableCabina.findByDescripcion", query = "SELECT c FROM CableCabina c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CableCabina.findByFechaIniOp", query = "SELECT c FROM CableCabina c WHERE c.fechaIniOp = :fechaIniOp"),
    @NamedQuery(name = "CableCabina.findByFechaFinOp", query = "SELECT c FROM CableCabina c WHERE c.fechaFinOp = :fechaFinOp"),
    @NamedQuery(name = "CableCabina.findByHorometro", query = "SELECT c FROM CableCabina c WHERE c.horometro = :horometro"),
    @NamedQuery(name = "CableCabina.findByHashString", query = "SELECT c FROM CableCabina c WHERE c.hashString = :hashString"),
    @NamedQuery(name = "CableCabina.findByUsername", query = "SELECT c FROM CableCabina c WHERE c.username = :username"),
    @NamedQuery(name = "CableCabina.findByCreado", query = "SELECT c FROM CableCabina c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableCabina.findByModificado", query = "SELECT c FROM CableCabina c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableCabina.findByEstadoReg", query = "SELECT c FROM CableCabina c WHERE c.estadoReg = :estadoReg")})
public class CableCabina implements Serializable {

    @OneToMany(mappedBy = "idCabina", fetch = FetchType.LAZY)
    private List<NovedadMttoDiaria> novedadMttoDiariaList;

    @OneToMany(mappedBy = "idCableCabina", fetch = FetchType.LAZY)
    private List<CableAccidentalidad> cableAccidentalidadList;

    @OneToMany(mappedBy = "idCableCabina", fetch = FetchType.LAZY)
    private List<CableNovedadOp> cableNovedadOpList;
    @OneToMany(mappedBy = "idCableCabinaDos", fetch = FetchType.LAZY)
    private List<CableNovedadOp> cableNovedadOpList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_cabina")
    private Integer idCableCabina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_ini_op")
    @Temporal(TemporalType.DATE)
    private Date fechaIniOp;
    @Column(name = "fecha_fin_op")
    @Temporal(TemporalType.DATE)
    private Date fechaFinOp;
    @Column(name = "horometro")
    private BigDecimal horometro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "hash_string")
    private String hashString;
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
    @OneToMany(mappedBy = "idCableCabina", fetch = FetchType.LAZY)
    private List<NovedadCab> novedadCabList;
    @OneToMany(mappedBy = "cableCabina", fetch = FetchType.LAZY)
    private List<AseoCabina> aseoCabinaList;

    @Column(name = "mtto")
    private boolean mtto;
    @OneToMany(mappedBy = "idCableCabina", fetch = FetchType.LAZY)
    private List<NovedadMtto> novedadMttoList;
    @JoinColumn(name = "id_cable_pinza", referencedColumnName = "id_cable_pinza")
    @ManyToOne(fetch = FetchType.LAZY)
    private CablePinza idCablePinza;

    public CablePinza getIdCablePinza() {
        return idCablePinza;
    }

    public void setIdCablePinza(CablePinza idCablePinza) {
        this.idCablePinza = idCablePinza;
    }

    public CableCabina() {
    }

    public CableCabina(Integer idCableCabina) {
        this.idCableCabina = idCableCabina;
    }

    public CableCabina(Integer idCableCabina, String codigo, String nombre, String hashString) {
        this.idCableCabina = idCableCabina;
        this.codigo = codigo;
        this.nombre = nombre;
        this.hashString = hashString;
    }

    public CableCabina(Integer idCableCabina, String codigo, String nombre, String descripcion, Date fechaIniOp, Date fechaFinOp, BigDecimal horometro, String hashString, String username, Date creado, Date modificado, Integer estadoReg, boolean mtto) {
        this.idCableCabina = idCableCabina;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaIniOp = fechaIniOp;
        this.fechaFinOp = fechaFinOp;
        this.horometro = horometro;
        this.hashString = hashString;
        this.username = username;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.mtto = mtto;
    }

    public Integer getIdCableCabina() {
        return idCableCabina;
    }

    public void setIdCableCabina(Integer idCableCabina) {
        this.idCableCabina = idCableCabina;
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

    public String getHashString() {
        return hashString;
    }

    public void setHashString(String hashString) {
        this.hashString = hashString;
    }

    @XmlTransient
    public List<AseoCabina> getAseoCabinaList() {
        return aseoCabinaList;
    }

    public void setAseoCabinaList(List<AseoCabina> aseoCabinaList) {
        this.aseoCabinaList = aseoCabinaList;
    }

    public List<NovedadCab> getNovedadCabList() {
        return novedadCabList;
    }

    public List<CableNovedadOp> getCableNovedadOpList() {
        return cableNovedadOpList;
    }

    public void setCableNovedadOpList(List<CableNovedadOp> cableNovedadOpList) {
        this.cableNovedadOpList = cableNovedadOpList;
    }

    public List<CableNovedadOp> getCableNovedadOpList1() {
        return cableNovedadOpList1;
    }

    public void setCableNovedadOpList1(List<CableNovedadOp> cableNovedadOpList1) {
        this.cableNovedadOpList1 = cableNovedadOpList1;
    }

    public void setNovedadCabList(List<NovedadCab> novedadCabList) {
        this.novedadCabList = novedadCabList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableCabina != null ? idCableCabina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableCabina)) {
            return false;
        }
        CableCabina other = (CableCabina) object;
        if ((this.idCableCabina == null && other.idCableCabina != null) || (this.idCableCabina != null && !this.idCableCabina.equals(other.idCableCabina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableCabina[ idCableCabina=" + idCableCabina + " ]";
    }

    @XmlTransient
    public List<CableAccidentalidad> getCableAccidentalidadList() {
        return cableAccidentalidadList;
    }

    public void setCableAccidentalidadList(List<CableAccidentalidad> cableAccidentalidadList) {
        this.cableAccidentalidadList = cableAccidentalidadList;
    }

    public List<NovedadMtto> getNovedadMttoList() {
        return novedadMttoList;
    }

    public void setNovedadMttoList(List<NovedadMtto> novedadMttoList) {
        this.novedadMttoList = novedadMttoList;
    }

    public boolean isMtto() {
        return mtto;
    }

    public void setMtto(boolean mtto) {
        this.mtto = mtto;
    }

    @XmlTransient
    public List<NovedadMttoDiaria> getNovedadMttoDiariaList() {
        return novedadMttoDiariaList;
    }

    public void setNovedadMttoDiariaList(List<NovedadMttoDiaria> novedadMttoDiariaList) {
        this.novedadMttoDiariaList = novedadMttoDiariaList;
    }

}
