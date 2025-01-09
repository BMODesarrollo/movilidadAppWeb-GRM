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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "lavado_responsable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LavadoResponsable.findAll", query = "SELECT l FROM LavadoResponsable l"),
    @NamedQuery(name = "LavadoResponsable.findByIdLavadoResponsable", query = "SELECT l FROM LavadoResponsable l WHERE l.idLavadoResponsable = :idLavadoResponsable"),
    @NamedQuery(name = "LavadoResponsable.findByUsername", query = "SELECT l FROM LavadoResponsable l WHERE l.username = :username"),
    @NamedQuery(name = "LavadoResponsable.findByCreado", query = "SELECT l FROM LavadoResponsable l WHERE l.creado = :creado"),
    @NamedQuery(name = "LavadoResponsable.findByModificado", query = "SELECT l FROM LavadoResponsable l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "LavadoResponsable.findByEstadoReg", query = "SELECT l FROM LavadoResponsable l WHERE l.estadoReg = :estadoReg")})
public class LavadoResponsable implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLavadoResponsable")
    private List<Lavado> lavadoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado_responsable")
    private Integer idLavadoResponsable;
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
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false)
    private Empleado idEmpleado;

    public LavadoResponsable() {
    }

    public LavadoResponsable(Integer idLavadoResponsable) {
        this.idLavadoResponsable = idLavadoResponsable;
    }

    public Integer getIdLavadoResponsable() {
        return idLavadoResponsable;
    }

    public void setIdLavadoResponsable(Integer idLavadoResponsable) {
        this.idLavadoResponsable = idLavadoResponsable;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLavadoResponsable != null ? idLavadoResponsable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavadoResponsable)) {
            return false;
        }
        LavadoResponsable other = (LavadoResponsable) object;
        if ((this.idLavadoResponsable == null && other.idLavadoResponsable != null) || (this.idLavadoResponsable != null && !this.idLavadoResponsable.equals(other.idLavadoResponsable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.LavadoResponsable[ idLavadoResponsable=" + idLavadoResponsable + " ]";
    }

    @XmlTransient
    public List<Lavado> getLavadoList() {
        return lavadoList;
    }

    public void setLavadoList(List<Lavado> lavadoList) {
        this.lavadoList = lavadoList;
    }
    
}
