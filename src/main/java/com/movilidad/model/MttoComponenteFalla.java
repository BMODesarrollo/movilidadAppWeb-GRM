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
import javax.persistence.Lob;
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
@Table(name = "mtto_componente_falla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MttoComponenteFalla.findAll", query = "SELECT m FROM MttoComponenteFalla m"),
    @NamedQuery(name = "MttoComponenteFalla.findByIdMttoComponenteFalla", query = "SELECT m FROM MttoComponenteFalla m WHERE m.idMttoComponenteFalla = :idMttoComponenteFalla"),
    @NamedQuery(name = "MttoComponenteFalla.findByFalla", query = "SELECT m FROM MttoComponenteFalla m WHERE m.falla = :falla"),
    @NamedQuery(name = "MttoComponenteFalla.findByUsername", query = "SELECT m FROM MttoComponenteFalla m WHERE m.username = :username"),
    @NamedQuery(name = "MttoComponenteFalla.findByCreado", query = "SELECT m FROM MttoComponenteFalla m WHERE m.creado = :creado"),
    @NamedQuery(name = "MttoComponenteFalla.findByModificado", query = "SELECT m FROM MttoComponenteFalla m WHERE m.modificado = :modificado"),
    @NamedQuery(name = "MttoComponenteFalla.findByEstadoReg", query = "SELECT m FROM MttoComponenteFalla m WHERE m.estadoReg = :estadoReg")})
public class MttoComponenteFalla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mtto_componente_falla")
    private Integer idMttoComponenteFalla;
    @Size(max = 45)
    @Column(name = "falla")
    private String falla;
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
    @OneToMany(mappedBy = "idMttoComponenteFalla", fetch = FetchType.LAZY)
    private List<MttoNovedad> mttoNovedadList;
    @JoinColumn(name = "id_mtto_componente", referencedColumnName = "id_mtto_componente")
    @ManyToOne(fetch = FetchType.LAZY)
    private MttoComponente idMttoComponente;
    @JoinColumn(name = "id_mtto_criticidad", referencedColumnName = "id_mtto_criticidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private MttoCriticidad idMttoCriticidad;

    public MttoComponenteFalla() {
    }

    public MttoComponenteFalla(Integer idMttoComponenteFalla) {
        this.idMttoComponenteFalla = idMttoComponenteFalla;
    }

    public Integer getIdMttoComponenteFalla() {
        return idMttoComponenteFalla;
    }

    public void setIdMttoComponenteFalla(Integer idMttoComponenteFalla) {
        this.idMttoComponenteFalla = idMttoComponenteFalla;
    }

    public String getFalla() {
        return falla;
    }

    public void setFalla(String falla) {
        this.falla = falla;
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
    public List<MttoNovedad> getMttoNovedadList() {
        return mttoNovedadList;
    }

    public void setMttoNovedadList(List<MttoNovedad> mttoNovedadList) {
        this.mttoNovedadList = mttoNovedadList;
    }

    public MttoComponente getIdMttoComponente() {
        return idMttoComponente;
    }

    public void setIdMttoComponente(MttoComponente idMttoComponente) {
        this.idMttoComponente = idMttoComponente;
    }

    public MttoCriticidad getIdMttoCriticidad() {
        return idMttoCriticidad;
    }

    public void setIdMttoCriticidad(MttoCriticidad idMttoCriticidad) {
        this.idMttoCriticidad = idMttoCriticidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMttoComponenteFalla != null ? idMttoComponenteFalla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MttoComponenteFalla)) {
            return false;
        }
        MttoComponenteFalla other = (MttoComponenteFalla) object;
        if ((this.idMttoComponenteFalla == null && other.idMttoComponenteFalla != null) || (this.idMttoComponenteFalla != null && !this.idMttoComponenteFalla.equals(other.idMttoComponenteFalla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MttoComponenteFalla[ idMttoComponenteFalla=" + idMttoComponenteFalla + " ]";
    }
    
}
