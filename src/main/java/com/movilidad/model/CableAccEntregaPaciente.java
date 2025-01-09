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
@Table(name = "cable_acc_entrega_paciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableAccEntregaPaciente.findAll", query = "SELECT c FROM CableAccEntregaPaciente c")
    , @NamedQuery(name = "CableAccEntregaPaciente.findByIdCableAccEntregaPaciente", query = "SELECT c FROM CableAccEntregaPaciente c WHERE c.idCableAccEntregaPaciente = :idCableAccEntregaPaciente")
    , @NamedQuery(name = "CableAccEntregaPaciente.findByNombre", query = "SELECT c FROM CableAccEntregaPaciente c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "CableAccEntregaPaciente.findByDescripcion", query = "SELECT c FROM CableAccEntregaPaciente c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "CableAccEntregaPaciente.findByUsername", query = "SELECT c FROM CableAccEntregaPaciente c WHERE c.username = :username")
    , @NamedQuery(name = "CableAccEntregaPaciente.findByCreado", query = "SELECT c FROM CableAccEntregaPaciente c WHERE c.creado = :creado")
    , @NamedQuery(name = "CableAccEntregaPaciente.findByModificado", query = "SELECT c FROM CableAccEntregaPaciente c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "CableAccEntregaPaciente.findByEstadoReg", query = "SELECT c FROM CableAccEntregaPaciente c WHERE c.estadoReg = :estadoReg")})
public class CableAccEntregaPaciente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_acc_entrega_paciente")
    private Integer idCableAccEntregaPaciente;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
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
    @OneToMany(mappedBy = "idCableAccEntregaPaciente", fetch = FetchType.LAZY)
    private List<CableAccSiniestrado> cableAccSiniestradoList;

    public CableAccEntregaPaciente() {
    }

    public CableAccEntregaPaciente(Integer idCableAccEntregaPaciente) {
        this.idCableAccEntregaPaciente = idCableAccEntregaPaciente;
    }

    public Integer getIdCableAccEntregaPaciente() {
        return idCableAccEntregaPaciente;
    }

    public void setIdCableAccEntregaPaciente(Integer idCableAccEntregaPaciente) {
        this.idCableAccEntregaPaciente = idCableAccEntregaPaciente;
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

    @XmlTransient
    public List<CableAccSiniestrado> getCableAccSiniestradoList() {
        return cableAccSiniestradoList;
    }

    public void setCableAccSiniestradoList(List<CableAccSiniestrado> cableAccSiniestradoList) {
        this.cableAccSiniestradoList = cableAccSiniestradoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccEntregaPaciente != null ? idCableAccEntregaPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccEntregaPaciente)) {
            return false;
        }
        CableAccEntregaPaciente other = (CableAccEntregaPaciente) object;
        if ((this.idCableAccEntregaPaciente == null && other.idCableAccEntregaPaciente != null) || (this.idCableAccEntregaPaciente != null && !this.idCableAccEntregaPaciente.equals(other.idCableAccEntregaPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccEntregaPaciente[ idCableAccEntregaPaciente=" + idCableAccEntregaPaciente + " ]";
    }
    
}
