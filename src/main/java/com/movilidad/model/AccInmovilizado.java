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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_inmovilizado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInmovilizado.findAll", query = "SELECT a FROM AccInmovilizado a")
    , @NamedQuery(name = "AccInmovilizado.findByIdAccInmovilizado", query = "SELECT a FROM AccInmovilizado a WHERE a.idAccInmovilizado = :idAccInmovilizado")
    , @NamedQuery(name = "AccInmovilizado.findByInmovilizado", query = "SELECT a FROM AccInmovilizado a WHERE a.inmovilizado = :inmovilizado")
    , @NamedQuery(name = "AccInmovilizado.findByUsername", query = "SELECT a FROM AccInmovilizado a WHERE a.username = :username")
    , @NamedQuery(name = "AccInmovilizado.findByCreado", query = "SELECT a FROM AccInmovilizado a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccInmovilizado.findByModificado", query = "SELECT a FROM AccInmovilizado a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccInmovilizado.findByEstadoReg", query = "SELECT a FROM AccInmovilizado a WHERE a.estadoReg = :estadoReg")})
public class AccInmovilizado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_inmovilizado")
    private Integer idAccInmovilizado;
    @Size(max = 60)
    @Column(name = "inmovilizado")
    private String inmovilizado;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(mappedBy = "idAccInmovilizado", fetch = FetchType.LAZY)
    private List<AccidenteVehiculo> accidenteVehiculoList;

    public AccInmovilizado() {
    }

    public AccInmovilizado(Integer idAccInmovilizado) {
        this.idAccInmovilizado = idAccInmovilizado;
    }

    public Integer getIdAccInmovilizado() {
        return idAccInmovilizado;
    }

    public void setIdAccInmovilizado(Integer idAccInmovilizado) {
        this.idAccInmovilizado = idAccInmovilizado;
    }

    public String getInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(String inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public List<AccidenteVehiculo> getAccidenteVehiculoList() {
        return accidenteVehiculoList;
    }

    public void setAccidenteVehiculoList(List<AccidenteVehiculo> accidenteVehiculoList) {
        this.accidenteVehiculoList = accidenteVehiculoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInmovilizado != null ? idAccInmovilizado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInmovilizado)) {
            return false;
        }
        AccInmovilizado other = (AccInmovilizado) object;
        if ((this.idAccInmovilizado == null && other.idAccInmovilizado != null) || (this.idAccInmovilizado != null && !this.idAccInmovilizado.equals(other.idAccInmovilizado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInmovilizado[ idAccInmovilizado=" + idAccInmovilizado + " ]";
    }
    
}
