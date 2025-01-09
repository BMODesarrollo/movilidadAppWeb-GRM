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
 * @author soluciones-it
 */
@Entity
@Table(name = "lavado_calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LavadoCalificacion.findAll", query = "SELECT l FROM LavadoCalificacion l")
    , @NamedQuery(name = "LavadoCalificacion.findByIdLavadoCalificacion", query = "SELECT l FROM LavadoCalificacion l WHERE l.idLavadoCalificacion = :idLavadoCalificacion")
    , @NamedQuery(name = "LavadoCalificacion.findByNombre", query = "SELECT l FROM LavadoCalificacion l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "LavadoCalificacion.findByValor", query = "SELECT l FROM LavadoCalificacion l WHERE l.valor = :valor")
    , @NamedQuery(name = "LavadoCalificacion.findByDescripcion", query = "SELECT l FROM LavadoCalificacion l WHERE l.descripcion = :descripcion")
    , @NamedQuery(name = "LavadoCalificacion.findByUsername", query = "SELECT l FROM LavadoCalificacion l WHERE l.username = :username")
    , @NamedQuery(name = "LavadoCalificacion.findByCreado", query = "SELECT l FROM LavadoCalificacion l WHERE l.creado = :creado")
    , @NamedQuery(name = "LavadoCalificacion.findByModificado", query = "SELECT l FROM LavadoCalificacion l WHERE l.modificado = :modificado")
    , @NamedQuery(name = "LavadoCalificacion.findByEstadoReg", query = "SELECT l FROM LavadoCalificacion l WHERE l.estadoReg = :estadoReg")})
public class LavadoCalificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado_calificacion")
    private Integer idLavadoCalificacion;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor")
    private Integer valor;
    @Size(max = 255)
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
    @OneToMany(mappedBy = "idLavadoCalificacion", fetch = FetchType.LAZY)
    private List<LavadoGm> lavadoGmList;

    public LavadoCalificacion() {
    }

    public LavadoCalificacion(Integer idLavadoCalificacion) {
        this.idLavadoCalificacion = idLavadoCalificacion;
    }

    public Integer getIdLavadoCalificacion() {
        return idLavadoCalificacion;
    }

    public void setIdLavadoCalificacion(Integer idLavadoCalificacion) {
        this.idLavadoCalificacion = idLavadoCalificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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
    public List<LavadoGm> getLavadoGmList() {
        return lavadoGmList;
    }

    public void setLavadoGmList(List<LavadoGm> lavadoGmList) {
        this.lavadoGmList = lavadoGmList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLavadoCalificacion != null ? idLavadoCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavadoCalificacion)) {
            return false;
        }
        LavadoCalificacion other = (LavadoCalificacion) object;
        if ((this.idLavadoCalificacion == null && other.idLavadoCalificacion != null) || (this.idLavadoCalificacion != null && !this.idLavadoCalificacion.equals(other.idLavadoCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.LavadoCalificacion[ idLavadoCalificacion=" + idLavadoCalificacion + " ]";
    }
    
}
