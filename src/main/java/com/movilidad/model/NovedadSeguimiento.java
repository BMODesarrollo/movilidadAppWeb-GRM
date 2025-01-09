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
@Table(name = "novedad_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadSeguimiento.findAll", query = "SELECT n FROM NovedadSeguimiento n")
    , @NamedQuery(name = "NovedadSeguimiento.findByIdNovedadSeguimiento", query = "SELECT n FROM NovedadSeguimiento n WHERE n.idNovedadSeguimiento = :idNovedadSeguimiento")
    , @NamedQuery(name = "NovedadSeguimiento.findByFecha", query = "SELECT n FROM NovedadSeguimiento n WHERE n.fecha = :fecha")
    , @NamedQuery(name = "NovedadSeguimiento.findByUsername", query = "SELECT n FROM NovedadSeguimiento n WHERE n.username = :username")
    , @NamedQuery(name = "NovedadSeguimiento.findByCreado", query = "SELECT n FROM NovedadSeguimiento n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadSeguimiento.findByModificado", query = "SELECT n FROM NovedadSeguimiento n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadSeguimiento.findByEstadoReg", query = "SELECT n FROM NovedadSeguimiento n WHERE n.estadoReg = :estadoReg")})
public class NovedadSeguimiento implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadSeguimiento", fetch = FetchType.LAZY)
    private List<NovedadSeguimientoDocs> novedadSeguimientoDocsList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_seguimiento")
    private Integer idNovedadSeguimiento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "seguimiento")
    private String seguimiento;
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
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Novedad idNovedad;

    public NovedadSeguimiento() {
    }

    public NovedadSeguimiento(Integer idNovedadSeguimiento) {
        this.idNovedadSeguimiento = idNovedadSeguimiento;
    }

    public NovedadSeguimiento(Integer idNovedadSeguimiento, String seguimiento, String username, Date creado, int estadoReg) {
        this.idNovedadSeguimiento = idNovedadSeguimiento;
        this.seguimiento = seguimiento;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadSeguimiento() {
        return idNovedadSeguimiento;
    }

    public void setIdNovedadSeguimiento(Integer idNovedadSeguimiento) {
        this.idNovedadSeguimiento = idNovedadSeguimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
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

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadSeguimiento != null ? idNovedadSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadSeguimiento)) {
            return false;
        }
        NovedadSeguimiento other = (NovedadSeguimiento) object;
        if ((this.idNovedadSeguimiento == null && other.idNovedadSeguimiento != null) || (this.idNovedadSeguimiento != null && !this.idNovedadSeguimiento.equals(other.idNovedadSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadSeguimiento[ idNovedadSeguimiento=" + idNovedadSeguimiento + " ]";
    }

    @XmlTransient
    public List<NovedadSeguimientoDocs> getNovedadSeguimientoDocsList() {
        return novedadSeguimientoDocsList;
    }

    public void setNovedadSeguimientoDocsList(List<NovedadSeguimientoDocs> novedadSeguimientoDocsList) {
        this.novedadSeguimientoDocsList = novedadSeguimientoDocsList;
    }
    
}
