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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author HP
 */
@Entity
@Table(name = "multa_clasificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MultaClasificacion.findAll", query = "SELECT m FROM MultaClasificacion m")
    , @NamedQuery(name = "MultaClasificacion.findByIdMultaClasificacion", query = "SELECT m FROM MultaClasificacion m WHERE m.idMultaClasificacion = :idMultaClasificacion")
    , @NamedQuery(name = "MultaClasificacion.findByCodigo", query = "SELECT m FROM MultaClasificacion m WHERE m.codigo = :codigo")
    , @NamedQuery(name = "MultaClasificacion.findByMetros", query = "SELECT m FROM MultaClasificacion m WHERE m.metros = :metros")
    , @NamedQuery(name = "MultaClasificacion.findByUsername", query = "SELECT m FROM MultaClasificacion m WHERE m.username = :username")
    , @NamedQuery(name = "MultaClasificacion.findByCreado", query = "SELECT m FROM MultaClasificacion m WHERE m.creado = :creado")
    , @NamedQuery(name = "MultaClasificacion.findByModificado", query = "SELECT m FROM MultaClasificacion m WHERE m.modificado = :modificado")
    , @NamedQuery(name = "MultaClasificacion.findByEstadoReg", query = "SELECT m FROM MultaClasificacion m WHERE m.estadoReg = :estadoReg")})
public class MultaClasificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa_clasificacion")
    private Integer idMultaClasificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "metros")
    private int metros;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMultaClasificacion", fetch = FetchType.LAZY)
    private List<Multa> multaList;
    @JoinColumn(name = "id_multa_tipo", referencedColumnName = "id_multa_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MultaTipo idMultaTipo;

    public MultaClasificacion() {
    }

    public MultaClasificacion(Integer idMultaClasificacion) {
        this.idMultaClasificacion = idMultaClasificacion;
    }

    public MultaClasificacion(Integer idMultaClasificacion, String codigo, String descripcion, int metros, String username, Date creado, int estadoReg) {
        this.idMultaClasificacion = idMultaClasificacion;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.metros = metros;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMultaClasificacion() {
        return idMultaClasificacion;
    }

    public void setIdMultaClasificacion(Integer idMultaClasificacion) {
        this.idMultaClasificacion = idMultaClasificacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMetros() {
        return metros;
    }

    public void setMetros(int metros) {
        this.metros = metros;
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

    @XmlTransient
    public List<Multa> getMultaList() {
        return multaList;
    }

    public void setMultaList(List<Multa> multaList) {
        this.multaList = multaList;
    }

    public MultaTipo getIdMultaTipo() {
        return idMultaTipo;
    }

    public void setIdMultaTipo(MultaTipo idMultaTipo) {
        this.idMultaTipo = idMultaTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultaClasificacion != null ? idMultaClasificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MultaClasificacion)) {
            return false;
        }
        MultaClasificacion other = (MultaClasificacion) object;
        if ((this.idMultaClasificacion == null && other.idMultaClasificacion != null) || (this.idMultaClasificacion != null && !this.idMultaClasificacion.equals(other.idMultaClasificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MultaClasificacion[ idMultaClasificacion=" + idMultaClasificacion + " ]";
    }
    
}
