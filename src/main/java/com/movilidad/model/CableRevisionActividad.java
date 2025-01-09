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
import javax.persistence.Lob;
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
@Table(name = "cable_revision_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableRevisionActividad.findAll", query = "SELECT c FROM CableRevisionActividad c"),
    @NamedQuery(name = "CableRevisionActividad.findByIdCableRevisionActividad", query = "SELECT c FROM CableRevisionActividad c WHERE c.idCableRevisionActividad = :idCableRevisionActividad"),
    @NamedQuery(name = "CableRevisionActividad.findByNombre", query = "SELECT c FROM CableRevisionActividad c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CableRevisionActividad.findByNotifica", query = "SELECT c FROM CableRevisionActividad c WHERE c.notifica = :notifica"),
    @NamedQuery(name = "CableRevisionActividad.findByLimiteInferior", query = "SELECT c FROM CableRevisionActividad c WHERE c.limiteInferior = :limiteInferior"),
    @NamedQuery(name = "CableRevisionActividad.findByLimiteSuperior", query = "SELECT c FROM CableRevisionActividad c WHERE c.limiteSuperior = :limiteSuperior"),
    @NamedQuery(name = "CableRevisionActividad.findByUsername", query = "SELECT c FROM CableRevisionActividad c WHERE c.username = :username"),
    @NamedQuery(name = "CableRevisionActividad.findByCreado", query = "SELECT c FROM CableRevisionActividad c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableRevisionActividad.findByModificado", query = "SELECT c FROM CableRevisionActividad c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableRevisionActividad.findByEstadoReg", query = "SELECT c FROM CableRevisionActividad c WHERE c.estadoReg = :estadoReg")})
public class CableRevisionActividad implements Serializable {
    @OneToMany(mappedBy = "idCableRevisionActividad", fetch = FetchType.LAZY)
    private List<CableRevisionEstacion> cableRevisionEstacionList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_revision_actividad")
    private Integer idCableRevisionActividad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "notifica")
    private Integer notifica;
    @Lob
    @Size(max = 65535)
    @Column(name = "emails")
    private String emails;
    @Column(name = "limite_inferior")
    private Integer limiteInferior;
    @Column(name = "limite_superior")
    private Integer limiteSuperior;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
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

    public CableRevisionActividad() {
    }

    public CableRevisionActividad(Integer idCableRevisionActividad) {
        this.idCableRevisionActividad = idCableRevisionActividad;
    }

    public CableRevisionActividad(Integer idCableRevisionActividad, String nombre, String descripcion, int estadoReg) {
        this.idCableRevisionActividad = idCableRevisionActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableRevisionActividad() {
        return idCableRevisionActividad;
    }

    public void setIdCableRevisionActividad(Integer idCableRevisionActividad) {
        this.idCableRevisionActividad = idCableRevisionActividad;
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

    public Integer getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(Integer limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public Integer getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(Integer limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableRevisionActividad != null ? idCableRevisionActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableRevisionActividad)) {
            return false;
        }
        CableRevisionActividad other = (CableRevisionActividad) object;
        if ((this.idCableRevisionActividad == null && other.idCableRevisionActividad != null) || (this.idCableRevisionActividad != null && !this.idCableRevisionActividad.equals(other.idCableRevisionActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableRevisionActividad[ idCableRevisionActividad=" + idCableRevisionActividad + " ]";
    }

    @XmlTransient
    public List<CableRevisionEstacion> getCableRevisionEstacionList() {
        return cableRevisionEstacionList;
    }

    public void setCableRevisionEstacionList(List<CableRevisionEstacion> cableRevisionEstacionList) {
        this.cableRevisionEstacionList = cableRevisionEstacionList;
    }
    
}
