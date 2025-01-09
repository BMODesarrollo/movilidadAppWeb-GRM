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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "incapacidad_ordena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncapacidadOrdena.findAll", query = "SELECT i FROM IncapacidadOrdena i"),
    @NamedQuery(name = "IncapacidadOrdena.findByIdIncapacidadOrdena", query = "SELECT i FROM IncapacidadOrdena i WHERE i.idIncapacidadOrdena = :idIncapacidadOrdena"),
    @NamedQuery(name = "IncapacidadOrdena.findByOrdena", query = "SELECT i FROM IncapacidadOrdena i WHERE i.ordena = :ordena"),
    @NamedQuery(name = "IncapacidadOrdena.findByUsername", query = "SELECT i FROM IncapacidadOrdena i WHERE i.username = :username"),
    @NamedQuery(name = "IncapacidadOrdena.findByCreado", query = "SELECT i FROM IncapacidadOrdena i WHERE i.creado = :creado"),
    @NamedQuery(name = "IncapacidadOrdena.findByModificado", query = "SELECT i FROM IncapacidadOrdena i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "IncapacidadOrdena.findByEstadoReg", query = "SELECT i FROM IncapacidadOrdena i WHERE i.estadoReg = :estadoReg")})
public class IncapacidadOrdena implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_incapacidad_ordena")
    private Integer idIncapacidadOrdena;
    @Size(max = 15)
    @Column(name = "ordena")
    private String ordena;
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
    @OneToMany(mappedBy = "idIncapacidadOrdena")
    private List<NovedadIncapacidad> novedadIncapacidadList;

    public IncapacidadOrdena() {
    }

    public IncapacidadOrdena(Integer idIncapacidadOrdena) {
        this.idIncapacidadOrdena = idIncapacidadOrdena;
    }

    public Integer getIdIncapacidadOrdena() {
        return idIncapacidadOrdena;
    }

    public void setIdIncapacidadOrdena(Integer idIncapacidadOrdena) {
        this.idIncapacidadOrdena = idIncapacidadOrdena;
    }

    public String getOrdena() {
        return ordena;
    }

    public void setOrdena(String ordena) {
        this.ordena = ordena;
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
    public List<NovedadIncapacidad> getNovedadIncapacidadList() {
        return novedadIncapacidadList;
    }

    public void setNovedadIncapacidadList(List<NovedadIncapacidad> novedadIncapacidadList) {
        this.novedadIncapacidadList = novedadIncapacidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIncapacidadOrdena != null ? idIncapacidadOrdena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncapacidadOrdena)) {
            return false;
        }
        IncapacidadOrdena other = (IncapacidadOrdena) object;
        if ((this.idIncapacidadOrdena == null && other.idIncapacidadOrdena != null) || (this.idIncapacidadOrdena != null && !this.idIncapacidadOrdena.equals(other.idIncapacidadOrdena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.IncapacidadOrdena[ idIncapacidadOrdena=" + idIncapacidadOrdena + " ]";
    }
    
}
