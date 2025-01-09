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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "generica_turno_jornada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaTurnoJornada.findAll", query = "SELECT g FROM GenericaTurnoJornada g"),
    @NamedQuery(name = "GenericaTurnoJornada.findByIdGenericaTurnoJornada", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.idGenericaTurnoJornada = :idGenericaTurnoJornada"),
    @NamedQuery(name = "GenericaTurnoJornada.findByDiaHabil", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.diaHabil = :diaHabil"),
    @NamedQuery(name = "GenericaTurnoJornada.findByDiaFeriado", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.diaFeriado = :diaFeriado"),
    @NamedQuery(name = "GenericaTurnoJornada.findByColor", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.color = :color"),
    @NamedQuery(name = "GenericaTurnoJornada.findByPrioridad", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.prioridad = :prioridad"),
    @NamedQuery(name = "GenericaTurnoJornada.findByUsername", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaTurnoJornada.findByCreado", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaTurnoJornada.findByModificado", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaTurnoJornada.findByEstadoReg", query = "SELECT g FROM GenericaTurnoJornada g WHERE g.estadoReg = :estadoReg")})
public class GenericaTurnoJornada implements Serializable {
    @OneToMany(mappedBy = "idGenericaTurnoJornada", fetch = FetchType.LAZY)
    private List<GenericaTurnoJornadaDet> genericaTurnoJornadaDetList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_turno_jornada")
    private Integer idGenericaTurnoJornada;
    @Column(name = "dia_habil")
    private Integer diaHabil;
    @Column(name = "dia_feriado")
    private Integer diaFeriado;
    @Size(max = 15)
    @Column(name = "color")
    private String color;
    @Column(name = "prioridad")
    private Integer prioridad;
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
    @JoinColumn(name = "id_generica_jornada_tipo", referencedColumnName = "id_generica_jornada_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private GenericaJornadaTipo idGenericaJornadaTipo;

    public GenericaTurnoJornada() {
    }

    public GenericaTurnoJornada(Integer idGenericaTurnoJornada) {
        this.idGenericaTurnoJornada = idGenericaTurnoJornada;
    }

    public Integer getIdGenericaTurnoJornada() {
        return idGenericaTurnoJornada;
    }

    public void setIdGenericaTurnoJornada(Integer idGenericaTurnoJornada) {
        this.idGenericaTurnoJornada = idGenericaTurnoJornada;
    }

    public Integer getDiaHabil() {
        return diaHabil;
    }

    public void setDiaHabil(Integer diaHabil) {
        this.diaHabil = diaHabil;
    }

    public Integer getDiaFeriado() {
        return diaFeriado;
    }

    public void setDiaFeriado(Integer diaFeriado) {
        this.diaFeriado = diaFeriado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
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

    public GenericaJornadaTipo getIdGenericaJornadaTipo() {
        return idGenericaJornadaTipo;
    }

    public void setIdGenericaJornadaTipo(GenericaJornadaTipo idGenericaJornadaTipo) {
        this.idGenericaJornadaTipo = idGenericaJornadaTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaTurnoJornada != null ? idGenericaTurnoJornada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaTurnoJornada)) {
            return false;
        }
        GenericaTurnoJornada other = (GenericaTurnoJornada) object;
        if ((this.idGenericaTurnoJornada == null && other.idGenericaTurnoJornada != null) || (this.idGenericaTurnoJornada != null && !this.idGenericaTurnoJornada.equals(other.idGenericaTurnoJornada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaTurnoJornada[ idGenericaTurnoJornada=" + idGenericaTurnoJornada + " ]";
    }

    @XmlTransient
    public List<GenericaTurnoJornadaDet> getGenericaTurnoJornadaDetList() {
        return genericaTurnoJornadaDetList;
    }

    public void setGenericaTurnoJornadaDetList(List<GenericaTurnoJornadaDet> genericaTurnoJornadaDetList) {
        this.genericaTurnoJornadaDetList = genericaTurnoJornadaDetList;
    }
    
}
