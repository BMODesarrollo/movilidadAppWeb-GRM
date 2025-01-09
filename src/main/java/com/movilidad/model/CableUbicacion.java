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
@Table(name = "cable_ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableUbicacion.findAll", query = "SELECT c FROM CableUbicacion c"),
    @NamedQuery(name = "CableUbicacion.findByIdCableUbicacion", query = "SELECT c FROM CableUbicacion c WHERE c.idCableUbicacion = :idCableUbicacion"),
    @NamedQuery(name = "CableUbicacion.findByCodigo", query = "SELECT c FROM CableUbicacion c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CableUbicacion.findByNombre", query = "SELECT c FROM CableUbicacion c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CableUbicacion.findByUsername", query = "SELECT c FROM CableUbicacion c WHERE c.username = :username"),
    @NamedQuery(name = "CableUbicacion.findByCreado", query = "SELECT c FROM CableUbicacion c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableUbicacion.findByModificado", query = "SELECT c FROM CableUbicacion c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableUbicacion.findByEstadoReg", query = "SELECT c FROM CableUbicacion c WHERE c.estadoReg = :estadoReg")})
public class CableUbicacion implements Serializable {
    @OneToMany(mappedBy = "idCableUbicacion", fetch = FetchType.LAZY)
    private List<RegistroEstadoArmamento> registroEstadoArmamentoList;

    @OneToMany(mappedBy = "cableUbicacion", fetch = FetchType.LAZY)
    private List<SegAseoArmamento> SegPlantillaAseoArmamentoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_ubicacion")
    private Integer idCableUbicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_cable_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CableEstacion idCableEstacion;
    @JoinColumn(name = "id_seg_medio_comunicacion", referencedColumnName = "id_seg_medio_comunicacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegMedioComunicacion idSegMedioComunicacion;
    @JoinColumn(name = "id_seg_registro_armamento", referencedColumnName = "id_seg_registro_armamento")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegRegistroArmamento idSegRegistroArmamento;

    public CableUbicacion() {
    }

    public CableUbicacion(Integer idCableUbicacion) {
        this.idCableUbicacion = idCableUbicacion;
    }

    public CableUbicacion(Integer idCableUbicacion, String codigo, String nombre, String username, Date creado, int estadoReg) {
        this.idCableUbicacion = idCableUbicacion;
        this.codigo = codigo;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableUbicacion() {
        return idCableUbicacion;
    }

    public void setIdCableUbicacion(Integer idCableUbicacion) {
        this.idCableUbicacion = idCableUbicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public CableEstacion getIdCableEstacion() {
        return idCableEstacion;
    }

    public void setIdCableEstacion(CableEstacion idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    public SegMedioComunicacion getIdSegMedioComunicacion() {
        return idSegMedioComunicacion;
    }

    public void setIdSegMedioComunicacion(SegMedioComunicacion idSegMedioComunicacion) {
        this.idSegMedioComunicacion = idSegMedioComunicacion;
    }

    public SegRegistroArmamento getIdSegRegistroArmamento() {
        return idSegRegistroArmamento;
    }

    public void setIdSegRegistroArmamento(SegRegistroArmamento idSegRegistroArmamento) {
        this.idSegRegistroArmamento = idSegRegistroArmamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableUbicacion != null ? idCableUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableUbicacion)) {
            return false;
        }
        CableUbicacion other = (CableUbicacion) object;
        if ((this.idCableUbicacion == null && other.idCableUbicacion != null) || (this.idCableUbicacion != null && !this.idCableUbicacion.equals(other.idCableUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableUbicacion[ idCableUbicacion=" + idCableUbicacion + " ]";
    }

    @XmlTransient
    public List<SegAseoArmamento> getSegPlantillaAseoArmamentoList() {
        return SegPlantillaAseoArmamentoList;
    }

    public void setSegPlantillaAseoArmamentoList(List<SegAseoArmamento> SegPlantillaAseoArmamentoList) {
        this.SegPlantillaAseoArmamentoList = SegPlantillaAseoArmamentoList;
    }


    @XmlTransient
    public List<RegistroEstadoArmamento> getRegistroEstadoArmamentoList() {
        return registroEstadoArmamentoList;
    }

    public void setRegistroEstadoArmamentoList(List<RegistroEstadoArmamento> registroEstadoArmamentoList) {
        this.registroEstadoArmamentoList = registroEstadoArmamentoList;
    }
}
