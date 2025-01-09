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
@Table(name = "seg_registro_armamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegRegistroArmamento.findAll", query = "SELECT s FROM SegRegistroArmamento s"),
    @NamedQuery(name = "SegRegistroArmamento.findByIdSegRegistroArmamento", query = "SELECT s FROM SegRegistroArmamento s WHERE s.idSegRegistroArmamento = :idSegRegistroArmamento"),
    @NamedQuery(name = "SegRegistroArmamento.findByMarca", query = "SELECT s FROM SegRegistroArmamento s WHERE s.marca = :marca"),
    @NamedQuery(name = "SegRegistroArmamento.findByCalibre", query = "SELECT s FROM SegRegistroArmamento s WHERE s.calibre = :calibre"),
    @NamedQuery(name = "SegRegistroArmamento.findBySerial", query = "SELECT s FROM SegRegistroArmamento s WHERE s.serial = :serial"),
    @NamedQuery(name = "SegRegistroArmamento.findByPathFoto", query = "SELECT s FROM SegRegistroArmamento s WHERE s.pathFoto = :pathFoto"),
    @NamedQuery(name = "SegRegistroArmamento.findByMunicion", query = "SELECT s FROM SegRegistroArmamento s WHERE s.municion = :municion"),
    @NamedQuery(name = "SegRegistroArmamento.findByUsername", query = "SELECT s FROM SegRegistroArmamento s WHERE s.username = :username"),
    @NamedQuery(name = "SegRegistroArmamento.findByCreado", query = "SELECT s FROM SegRegistroArmamento s WHERE s.creado = :creado"),
    @NamedQuery(name = "SegRegistroArmamento.findByModificado", query = "SELECT s FROM SegRegistroArmamento s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SegRegistroArmamento.findByEstadoReg", query = "SELECT s FROM SegRegistroArmamento s WHERE s.estadoReg = :estadoReg")})
public class SegRegistroArmamento implements Serializable {

    @OneToMany(mappedBy = "idSegRegistroArmamento", fetch = FetchType.LAZY)
    private List<CableUbicacion> cableUbicacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSegRegistroArmamento", fetch = FetchType.LAZY)
    private List<SegRegistroArmamentoDoc> segRegistroArmamentoDocList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "segRegistroArmamento", fetch = FetchType.LAZY)
    private List<SegAseoArmamento> segPlantillaAseoArmamentoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_registro_armamento")
    private Integer idSegRegistroArmamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "calibre")
    private String calibre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "serial")
    private String serial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "municion")
    private int municion;
    @Size(min = 1, max = 100)
    @Column(name = "path_foto")
    private String pathFoto;
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

    public SegRegistroArmamento() {
    }

    public SegRegistroArmamento(Integer idSegRegistroArmamento) {
        this.idSegRegistroArmamento = idSegRegistroArmamento;
    }

    public SegRegistroArmamento(Integer idSegRegistroArmamento, String marca, String calibre, String serial, int municion, String pathFoto, String username, Date creado, int estadoReg) {
        this.idSegRegistroArmamento = idSegRegistroArmamento;
        this.marca = marca;
        this.calibre = calibre;
        this.serial = serial;
        this.municion = municion;
        this.pathFoto = pathFoto;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSegRegistroArmamento() {
        return idSegRegistroArmamento;
    }

    public void setIdSegRegistroArmamento(Integer idSegRegistroArmamento) {
        this.idSegRegistroArmamento = idSegRegistroArmamento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getMunicion() {
        return municion;
    }

    public void setMunicion(int municion) {
        this.municion = municion;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
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
        hash += (idSegRegistroArmamento != null ? idSegRegistroArmamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegRegistroArmamento)) {
            return false;
        }
        SegRegistroArmamento other = (SegRegistroArmamento) object;
        if ((this.idSegRegistroArmamento == null && other.idSegRegistroArmamento != null) || (this.idSegRegistroArmamento != null && !this.idSegRegistroArmamento.equals(other.idSegRegistroArmamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegRegistroArmamento[ idSegRegistroArmamento=" + idSegRegistroArmamento + " ]";
    }

    @XmlTransient
    public List<SegRegistroArmamentoDoc> getSegRegistroArmamentoDocList() {
        return segRegistroArmamentoDocList;
    }

    public void setSegRegistroArmamentoDocList(List<SegRegistroArmamentoDoc> segRegistroArmamentoDocList) {
        this.segRegistroArmamentoDocList = segRegistroArmamentoDocList;
    }

    @XmlTransient
    public List<CableUbicacion> getCableUbicacionList() {
        return cableUbicacionList;
    }

    public void setCableUbicacionList(List<CableUbicacion> cableUbicacionList) {
        this.cableUbicacionList = cableUbicacionList;
    }

    @XmlTransient
    public List<SegAseoArmamento> getSegPlantillaAseoArmamentoList() {
        return segPlantillaAseoArmamentoList;
    }

    public void setSegPlantillaAseoArmamentoList(List<SegAseoArmamento> segPlantillaAseoArmamentoList) {
        this.segPlantillaAseoArmamentoList = segPlantillaAseoArmamentoList;
    }

}
