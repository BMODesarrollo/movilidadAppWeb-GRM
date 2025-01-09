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
 * @author solucionesit
 */
@Entity
@Table(name = "aseo_cabina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AseoCabina.findAll", query = "SELECT a FROM AseoCabina a"),
    @NamedQuery(name = "AseoCabina.findByIdAseoCabina", query = "SELECT a FROM AseoCabina a WHERE a.idAseoCabina = :idAseoCabina"),
    @NamedQuery(name = "AseoCabina.findByFechaHora", query = "SELECT a FROM AseoCabina a WHERE a.fechaHora = :fechaHora"),
    @NamedQuery(name = "AseoCabina.findByPathFotos", query = "SELECT a FROM AseoCabina a WHERE a.pathFotos = :pathFotos"),
    @NamedQuery(name = "AseoCabina.findByUsername", query = "SELECT a FROM AseoCabina a WHERE a.username = :username"),
    @NamedQuery(name = "AseoCabina.findByCreado", query = "SELECT a FROM AseoCabina a WHERE a.creado = :creado"),
    @NamedQuery(name = "AseoCabina.findByModificado", query = "SELECT a FROM AseoCabina a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AseoCabina.findByEstadoReg", query = "SELECT a FROM AseoCabina a WHERE a.estadoReg = :estadoReg")})
public class AseoCabina implements Serializable {

    @JoinColumn(name = "id_aseo_cabina_tipo", referencedColumnName = "id_aseo_cabina_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AseoCabinaTipo aseoCabinaTipo;
    @JoinColumn(name = "id_cable_cabina", referencedColumnName = "id_cable_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableCabina cableCabina;

    @OneToMany(mappedBy = "aseoCabina", fetch = FetchType.LAZY)
    private List<AseoCabinaNovedad> aseoCabinaNovedadList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aseo_cabina")
    private Integer idAseoCabina;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Size(min = 1, max = 150)
    @Column(name = "path_fotos")
    private String pathFotos;
    @Basic(optional = false)
    @NotNull
    @Size(max = 15)
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
    @Column(name = "estado_reg")
    private Integer estadoReg;

    public AseoCabina() {
    }

    public AseoCabina(Integer idAseoCabina) {
        this.idAseoCabina = idAseoCabina;
    }

    public AseoCabina(Integer idAseoCabina, String pathFotos) {
        this.idAseoCabina = idAseoCabina;
        this.pathFotos = pathFotos;
    }

    public Integer getIdAseoCabina() {
        return idAseoCabina;
    }

    public void setIdAseoCabina(Integer idAseoCabina) {
        this.idAseoCabina = idAseoCabina;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
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

    public AseoCabinaTipo getAseoCabinaTipo() {
        return aseoCabinaTipo;
    }

    public void setAseoCabinaTipo(AseoCabinaTipo aseoCabinaTipo) {
        this.aseoCabinaTipo = aseoCabinaTipo;
    }

    public CableCabina getCableCabina() {
        return cableCabina;
    }

    public void setCableCabina(CableCabina cableCabina) {
        this.cableCabina = cableCabina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAseoCabina != null ? idAseoCabina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AseoCabina)) {
            return false;
        }
        AseoCabina other = (AseoCabina) object;
        if ((this.idAseoCabina == null && other.idAseoCabina != null) || (this.idAseoCabina != null && !this.idAseoCabina.equals(other.idAseoCabina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AseoCabina[ idAseoCabina=" + idAseoCabina + " ]";
    }

    @XmlTransient
    public List<AseoCabinaNovedad> getAseoCabinaNovedadList() {
        return aseoCabinaNovedadList;
    }

    public void setAseoCabinaNovedadList(List<AseoCabinaNovedad> aseoCabinaNovedadList) {
        this.aseoCabinaNovedadList = aseoCabinaNovedadList;
    }

}
