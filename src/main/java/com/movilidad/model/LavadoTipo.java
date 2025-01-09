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
@Table(name = "lavado_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LavadoTipo.findAll", query = "SELECT l FROM LavadoTipo l"),
    @NamedQuery(name = "LavadoTipo.findByIdLavadoTipo", query = "SELECT l FROM LavadoTipo l WHERE l.idLavadoTipo = :idLavadoTipo"),
    @NamedQuery(name = "LavadoTipo.findByTipoLavado", query = "SELECT l FROM LavadoTipo l WHERE l.tipoLavado = :tipoLavado"),
    @NamedQuery(name = "LavadoTipo.findByUsername", query = "SELECT l FROM LavadoTipo l WHERE l.username = :username"),
    @NamedQuery(name = "LavadoTipo.findByCreado", query = "SELECT l FROM LavadoTipo l WHERE l.creado = :creado"),
    @NamedQuery(name = "LavadoTipo.findByModificado", query = "SELECT l FROM LavadoTipo l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "LavadoTipo.findByEstadoReg", query = "SELECT l FROM LavadoTipo l WHERE l.estadoReg = :estadoReg")})
public class LavadoTipo implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLavadoTipo")
    private List<Lavado> lavadoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado_tipo")
    private Integer idLavadoTipo;
    @Size(max = 30)
    @Column(name = "tipo_lavado")
    private String tipoLavado;
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

    public LavadoTipo() {
    }

    public LavadoTipo(Integer idLavadoTipo) {
        this.idLavadoTipo = idLavadoTipo;
    }

    public Integer getIdLavadoTipo() {
        return idLavadoTipo;
    }

    public void setIdLavadoTipo(Integer idLavadoTipo) {
        this.idLavadoTipo = idLavadoTipo;
    }

    public String getTipoLavado() {
        return tipoLavado;
    }

    public void setTipoLavado(String tipoLavado) {
        this.tipoLavado = tipoLavado;
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
        hash += (idLavadoTipo != null ? idLavadoTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavadoTipo)) {
            return false;
        }
        LavadoTipo other = (LavadoTipo) object;
        if ((this.idLavadoTipo == null && other.idLavadoTipo != null) || (this.idLavadoTipo != null && !this.idLavadoTipo.equals(other.idLavadoTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.LavadoTipo[ idLavadoTipo=" + idLavadoTipo + " ]";
    }

    @XmlTransient
    public List<Lavado> getLavadoList() {
        return lavadoList;
    }

    public void setLavadoList(List<Lavado> lavadoList) {
        this.lavadoList = lavadoList;
    }
    
}
