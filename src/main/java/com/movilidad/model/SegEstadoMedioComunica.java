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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "seg_estado_medio_comunica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegEstadoMedioComunica.findAll", query = "SELECT s FROM SegEstadoMedioComunica s"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByIdSegEstadoMedioComunica", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.idSegEstadoMedioComunica = :idSegEstadoMedioComunica"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByFechaHora", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.fechaHora = :fechaHora"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByAntena", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.antena = :antena"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByBateria", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.bateria = :bateria"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByBateriaRepuesto", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.bateriaRepuesto = :bateriaRepuesto"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByCargador", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.cargador = :cargador"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByAdaptador", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.adaptador = :adaptador"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByPathFotos", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.pathFotos = :pathFotos"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByUsername", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.username = :username"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByCreado", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.creado = :creado"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByModificado", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SegEstadoMedioComunica.findByEstadoReg", query = "SELECT s FROM SegEstadoMedioComunica s WHERE s.estadoReg = :estadoReg")})
public class SegEstadoMedioComunica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_estado_medio_comunica")
    private Integer idSegEstadoMedioComunica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "antena")
    private Integer antena;
    @Column(name = "bateria")
    private Integer bateria;
    @Column(name = "bateria_repuesto")
    private Integer bateriaRepuesto;
    @Column(name = "cargador")
    private Integer cargador;
    @Column(name = "adaptador")
    private Integer adaptador;
    @Size(max = 150)
    @Column(name = "path_fotos")
    private String pathFotos;
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
    @JoinColumn(name = "id_seg_medio_comunicacion", referencedColumnName = "id_seg_medio_comunicacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegMedioComunicacion idSegMedioComunicacion;

    public SegEstadoMedioComunica() {
    }

    public SegEstadoMedioComunica(Integer idSegEstadoMedioComunica) {
        this.idSegEstadoMedioComunica = idSegEstadoMedioComunica;
    }

    public SegEstadoMedioComunica(Integer idSegEstadoMedioComunica, Date fechaHora, String username, Date creado, int estadoReg) {
        this.idSegEstadoMedioComunica = idSegEstadoMedioComunica;
        this.fechaHora = fechaHora;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSegEstadoMedioComunica() {
        return idSegEstadoMedioComunica;
    }

    public void setIdSegEstadoMedioComunica(Integer idSegEstadoMedioComunica) {
        this.idSegEstadoMedioComunica = idSegEstadoMedioComunica;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getAntena() {
        return antena;
    }

    public void setAntena(Integer antena) {
        this.antena = antena;
    }

    public Integer getBateria() {
        return bateria;
    }

    public void setBateria(Integer bateria) {
        this.bateria = bateria;
    }

    public Integer getBateriaRepuesto() {
        return bateriaRepuesto;
    }

    public void setBateriaRepuesto(Integer bateriaRepuesto) {
        this.bateriaRepuesto = bateriaRepuesto;
    }

    public Integer getCargador() {
        return cargador;
    }

    public void setCargador(Integer cargador) {
        this.cargador = cargador;
    }

    public Integer getAdaptador() {
        return adaptador;
    }

    public void setAdaptador(Integer adaptador) {
        this.adaptador = adaptador;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegEstadoMedioComunica != null ? idSegEstadoMedioComunica.hashCode() : 0);
        return hash;
    }

    public SegMedioComunicacion getIdSegMedioComunicacion() {
        return idSegMedioComunicacion;
    }

    public void setIdSegMedioComunicacion(SegMedioComunicacion idSegMedioComunicacion) {
        this.idSegMedioComunicacion = idSegMedioComunicacion;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegEstadoMedioComunica)) {
            return false;
        }
        SegEstadoMedioComunica other = (SegEstadoMedioComunica) object;
        if ((this.idSegEstadoMedioComunica == null && other.idSegEstadoMedioComunica != null) || (this.idSegEstadoMedioComunica != null && !this.idSegEstadoMedioComunica.equals(other.idSegEstadoMedioComunica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegEstadoMedioComunica[ idSegEstadoMedioComunica=" + idSegEstadoMedioComunica + " ]";
    }

}
