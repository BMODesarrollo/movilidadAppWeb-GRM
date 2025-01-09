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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "actividad_infra_tipo_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadInfraTipoDet.findAll", query = "SELECT a FROM ActividadInfraTipoDet a")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByIdActividadInfraTipoDet", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.idActividadInfraTipoDet = :idActividadInfraTipoDet")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByNotifica", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.notifica = :notifica")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByEmails", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.emails = :emails")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByNombre", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByDescripcion", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByUsername", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.username = :username")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByCreado", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.creado = :creado")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByModificado", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "ActividadInfraTipoDet.findByEstadoReg", query = "SELECT a FROM ActividadInfraTipoDet a WHERE a.estadoReg = :estadoReg")})
public class ActividadInfraTipoDet implements Serializable {

    @OneToMany(mappedBy = "idActividadInfraTipoDet", fetch = FetchType.LAZY)
    private List<ActividadInfraDiaria> actividadInfraDiariaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad_infra_tipo_det")
    private Integer idActividadInfraTipoDet;
    @Column(name = "notifica")
    private Integer notifica;
    @Lob
    @Size(max = 65535)
    private String emails;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
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
    @JoinColumn(name = "id_actividad_infra_tipo", referencedColumnName = "id_actividad_infra_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private ActividadInfraTipo idActividadInfraTipo;

    public ActividadInfraTipoDet() {
    }

    public ActividadInfraTipoDet(Integer idActividadInfraTipoDet) {
        this.idActividadInfraTipoDet = idActividadInfraTipoDet;
    }

    public ActividadInfraTipoDet(Integer idActividadInfraTipoDet, String nombre, String descripcion) {
        this.idActividadInfraTipoDet = idActividadInfraTipoDet;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdActividadInfraTipoDet() {
        return idActividadInfraTipoDet;
    }

    public void setIdActividadInfraTipoDet(Integer idActividadInfraTipoDet) {
        this.idActividadInfraTipoDet = idActividadInfraTipoDet;
    }

    public Integer getNotifica() {
        return notifica;
    }

    public void setNotifica(Integer notifica) {
        this.notifica = notifica;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public ActividadInfraTipo getIdActividadInfraTipo() {
        return idActividadInfraTipo;
    }

    public void setIdActividadInfraTipo(ActividadInfraTipo idActividadInfraTipo) {
        this.idActividadInfraTipo = idActividadInfraTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividadInfraTipoDet != null ? idActividadInfraTipoDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadInfraTipoDet)) {
            return false;
        }
        ActividadInfraTipoDet other = (ActividadInfraTipoDet) object;
        if ((this.idActividadInfraTipoDet == null && other.idActividadInfraTipoDet != null) || (this.idActividadInfraTipoDet != null && !this.idActividadInfraTipoDet.equals(other.idActividadInfraTipoDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ActividadInfraTipoDet[ idActividadInfraTipoDet=" + idActividadInfraTipoDet + " ]";
    }

    @XmlTransient
    public List<ActividadInfraDiaria> getActividadInfraDiariaList() {
        return actividadInfraDiariaList;
    }

    public void setActividadInfraDiariaList(List<ActividadInfraDiaria> actividadInfraDiariaList) {
        this.actividadInfraDiariaList = actividadInfraDiariaList;
    }
    
}
