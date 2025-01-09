/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "aseo_cabina_novedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AseoCabinaNovedad.findAll", query = "SELECT a FROM AseoCabinaNovedad a"),
    @NamedQuery(name = "AseoCabinaNovedad.findByIdAseoCabinaNovedad", query = "SELECT a FROM AseoCabinaNovedad a WHERE a.idAseoCabinaNovedad = :idAseoCabinaNovedad"),
    @NamedQuery(name = "AseoCabinaNovedad.findByUsername", query = "SELECT a FROM AseoCabinaNovedad a WHERE a.username = :username"),
    @NamedQuery(name = "AseoCabinaNovedad.findByCreado", query = "SELECT a FROM AseoCabinaNovedad a WHERE a.creado = :creado"),
    @NamedQuery(name = "AseoCabinaNovedad.findByModificado", query = "SELECT a FROM AseoCabinaNovedad a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AseoCabinaNovedad.findByEstadoReg", query = "SELECT a FROM AseoCabinaNovedad a WHERE a.estadoReg = :estadoReg")})
public class AseoCabinaNovedad implements Serializable {

    @JoinColumn(name = "id_novedad_cab", referencedColumnName = "id_novedad_cab")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private NovedadCab novedadCab;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aseo_cabina_novedad")
    private Integer idAseoCabinaNovedad;
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
    @JoinColumn(name = "id_aseo_cabina", referencedColumnName = "id_aseo_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private AseoCabina aseoCabina;

    public AseoCabinaNovedad() {
    }

    public AseoCabinaNovedad(Integer idAseoCabinaNovedad, String username, Date creado, Date modificado, Integer estadoReg, AseoCabina aseoCabina, NovedadCab novedadCab) {
        this.idAseoCabinaNovedad = idAseoCabinaNovedad;
        this.username = username;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.aseoCabina = aseoCabina;
        this.novedadCab = novedadCab;
    }

    public AseoCabinaNovedad(Integer idAseoCabinaNovedad) {
        this.idAseoCabinaNovedad = idAseoCabinaNovedad;
    }

    public Integer getIdAseoCabinaNovedad() {
        return idAseoCabinaNovedad;
    }

    public void setIdAseoCabinaNovedad(Integer idAseoCabinaNovedad) {
        this.idAseoCabinaNovedad = idAseoCabinaNovedad;
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

    public AseoCabina getAseoCabina() {
        return aseoCabina;
    }

    public void setAseoCabina(AseoCabina aseoCabina) {
        this.aseoCabina = aseoCabina;
    }

    public NovedadCab getNovedadCab() {
        return novedadCab;
    }

    public void setNovedadCab(NovedadCab novedadCab) {
        this.novedadCab = novedadCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAseoCabinaNovedad != null ? idAseoCabinaNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AseoCabinaNovedad)) {
            return false;
        }
        AseoCabinaNovedad other = (AseoCabinaNovedad) object;
        if ((this.idAseoCabinaNovedad == null && other.idAseoCabinaNovedad != null) || (this.idAseoCabinaNovedad != null && !this.idAseoCabinaNovedad.equals(other.idAseoCabinaNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AseoCabinaNovedad[ idAseoCabinaNovedad=" + idAseoCabinaNovedad + " ]";
    }

}
