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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "tbl_calendario_caracteristicas_dia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findAll", query = "SELECT t FROM TblCalendarioCaracteristicasDia t"),
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findByIdTblCalendarioCaracteristicaDia", query = "SELECT t FROM TblCalendarioCaracteristicasDia t WHERE t.idTblCalendarioCaracteristicaDia = :idTblCalendarioCaracteristicaDia"),
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findByNombre", query = "SELECT t FROM TblCalendarioCaracteristicasDia t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findByAfectaTec", query = "SELECT t FROM TblCalendarioCaracteristicasDia t WHERE t.afectaTec = :afectaTec"),
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findByAfectaPrg", query = "SELECT t FROM TblCalendarioCaracteristicasDia t WHERE t.afectaPrg = :afectaPrg"),
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findByUsername", query = "SELECT t FROM TblCalendarioCaracteristicasDia t WHERE t.username = :username"),
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findByCreado", query = "SELECT t FROM TblCalendarioCaracteristicasDia t WHERE t.creado = :creado"),
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findByModificado", query = "SELECT t FROM TblCalendarioCaracteristicasDia t WHERE t.modificado = :modificado"),
    @NamedQuery(name = "TblCalendarioCaracteristicasDia.findByEstadoReg", query = "SELECT t FROM TblCalendarioCaracteristicasDia t WHERE t.estadoReg = :estadoReg")})
public class TblCalendarioCaracteristicasDia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tbl_calendario_caracteristica_dia")
    private Integer idTblCalendarioCaracteristicaDia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "afecta_tec")
    private int afectaTec;
    @Basic(optional = false)
    @NotNull
    @Column(name = "afecta_prg")
    private int afectaPrg;
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
    @OneToMany(mappedBy = "idTblCalendarioCaracteristicaDia", fetch = FetchType.LAZY)
    private List<TblCalendario> tblCalendarioList;

    public TblCalendarioCaracteristicasDia() {
    }

    public TblCalendarioCaracteristicasDia(Integer idTblCalendarioCaracteristicaDia) {
        this.idTblCalendarioCaracteristicaDia = idTblCalendarioCaracteristicaDia;
    }

    public TblCalendarioCaracteristicasDia(Integer idTblCalendarioCaracteristicaDia, String nombre, String descripcion, int afectaTec, int afectaPrg) {
        this.idTblCalendarioCaracteristicaDia = idTblCalendarioCaracteristicaDia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.afectaTec = afectaTec;
        this.afectaPrg = afectaPrg;
    }

    public Integer getIdTblCalendarioCaracteristicaDia() {
        return idTblCalendarioCaracteristicaDia;
    }

    public void setIdTblCalendarioCaracteristicaDia(Integer idTblCalendarioCaracteristicaDia) {
        this.idTblCalendarioCaracteristicaDia = idTblCalendarioCaracteristicaDia;
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

    public int getAfectaTec() {
        return afectaTec;
    }

    public void setAfectaTec(int afectaTec) {
        this.afectaTec = afectaTec;
    }

    public int getAfectaPrg() {
        return afectaPrg;
    }

    public void setAfectaPrg(int afectaPrg) {
        this.afectaPrg = afectaPrg;
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

    @XmlTransient
    public List<TblCalendario> getTblCalendarioList() {
        return tblCalendarioList;
    }

    public void setTblCalendarioList(List<TblCalendario> tblCalendarioList) {
        this.tblCalendarioList = tblCalendarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTblCalendarioCaracteristicaDia != null ? idTblCalendarioCaracteristicaDia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCalendarioCaracteristicasDia)) {
            return false;
        }
        TblCalendarioCaracteristicasDia other = (TblCalendarioCaracteristicasDia) object;
        if ((this.idTblCalendarioCaracteristicaDia == null && other.idTblCalendarioCaracteristicaDia != null) || (this.idTblCalendarioCaracteristicaDia != null && !this.idTblCalendarioCaracteristicaDia.equals(other.idTblCalendarioCaracteristicaDia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.TblCalendarioCaracteristicasDia[ idTblCalendarioCaracteristicaDia=" + idTblCalendarioCaracteristicaDia + " ]";
    }
    
}
