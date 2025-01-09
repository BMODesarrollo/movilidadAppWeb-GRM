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
@Table(name = "atv_tipo_evidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvTipoEvidencia.findAll", query = "SELECT a FROM AtvTipoEvidencia a"),
    @NamedQuery(name = "AtvTipoEvidencia.findByIdAtvTipoEvidencia", query = "SELECT a FROM AtvTipoEvidencia a WHERE a.idAtvTipoEvidencia = :idAtvTipoEvidencia"),
    @NamedQuery(name = "AtvTipoEvidencia.findByNombre", query = "SELECT a FROM AtvTipoEvidencia a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AtvTipoEvidencia.findByUsername", query = "SELECT a FROM AtvTipoEvidencia a WHERE a.username = :username"),
    @NamedQuery(name = "AtvTipoEvidencia.findByCreado", query = "SELECT a FROM AtvTipoEvidencia a WHERE a.creado = :creado"),
    @NamedQuery(name = "AtvTipoEvidencia.findByModificado", query = "SELECT a FROM AtvTipoEvidencia a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AtvTipoEvidencia.findByEstadoReg", query = "SELECT a FROM AtvTipoEvidencia a WHERE a.estadoReg = :estadoReg")})
public class AtvTipoEvidencia implements Serializable {

    @Size(max = 45)
    @Column(name = "tipo_evidencia")
    private String tipoEvidencia;
    @OneToMany(mappedBy = "idAtvTipoEvidencia", fetch = FetchType.LAZY)
    private List<AtvEvidencia> atvEvidenciaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_tipo_evidencia")
    private Integer idAtvTipoEvidencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
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

    public AtvTipoEvidencia() {
    }

    public AtvTipoEvidencia(Integer idAtvTipoEvidencia) {
        this.idAtvTipoEvidencia = idAtvTipoEvidencia;
    }

    public AtvTipoEvidencia(Integer idAtvTipoEvidencia, String nombre, String descripcion, String username, int estadoReg) {
        this.idAtvTipoEvidencia = idAtvTipoEvidencia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAtvTipoEvidencia() {
        return idAtvTipoEvidencia;
    }

    public void setIdAtvTipoEvidencia(Integer idAtvTipoEvidencia) {
        this.idAtvTipoEvidencia = idAtvTipoEvidencia;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtvTipoEvidencia != null ? idAtvTipoEvidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvTipoEvidencia)) {
            return false;
        }
        AtvTipoEvidencia other = (AtvTipoEvidencia) object;
        if ((this.idAtvTipoEvidencia == null && other.idAtvTipoEvidencia != null) || (this.idAtvTipoEvidencia != null && !this.idAtvTipoEvidencia.equals(other.idAtvTipoEvidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvTipoEvidencia[ idAtvTipoEvidencia=" + idAtvTipoEvidencia + " ]";
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public void setTipoEvidencia(String tipoEvidencia) {
        this.tipoEvidencia = tipoEvidencia;
    }

    @XmlTransient
    public List<AtvEvidencia> getAtvEvidenciaList() {
        return atvEvidenciaList;
    }

    public void setAtvEvidenciaList(List<AtvEvidencia> atvEvidenciaList) {
        this.atvEvidenciaList = atvEvidenciaList;
    }
    
}
