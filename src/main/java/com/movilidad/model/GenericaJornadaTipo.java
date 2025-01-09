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
 * @author solucionesit
 */
@Entity
@Table(name = "generica_jornada_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaJornadaTipo.findAll", query = "SELECT g FROM GenericaJornadaTipo g")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByIdGenericaJornadaTipo", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.idGenericaJornadaTipo = :idGenericaJornadaTipo")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByHiniT1", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.hiniT1 = :hiniT1")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByHfinT1", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.hfinT1 = :hfinT1")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByHiniT2", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.hiniT2 = :hiniT2")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByHfinT2", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.hfinT2 = :hfinT2")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByHiniT3", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.hiniT3 = :hiniT3")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByHfinT3", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.hfinT3 = :hfinT3")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByDescanso", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.descanso = :descanso")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByUsername", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.username = :username")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByCreado", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.creado = :creado")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByModificado", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.modificado = :modificado")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByIdAreaParam", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.idParamArea = :idParamArea")
    ,
    @NamedQuery(name = "GenericaJornadaTipo.findByEstadoReg", query = "SELECT g FROM GenericaJornadaTipo g WHERE g.estadoReg = :estadoReg")})
public class GenericaJornadaTipo implements Serializable {

    @OneToMany(mappedBy = "idGenericaJornadaTipo", fetch = FetchType.LAZY)
    private List<GenericaPrgJornada> genericaPrgJornadaList;
    @OneToMany(mappedBy = "idGenericaJornadaTipo", fetch = FetchType.LAZY)
    private List<GenericaTurnoFijo> genericaTurnoFijoList;
    @OneToMany(mappedBy = "idGenericaJornadaTipo", fetch = FetchType.LAZY)
    private List<GenericaTurnoJornada> genericaTurnoJornadaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_jornada_tipo")
    private Integer idGenericaJornadaTipo;
    @Size(max = 8)
    @Column(name = "hini_t1")
    private String hiniT1;
    @Size(max = 8)
    @Column(name = "hfin_t1")
    private String hfinT1;
    @Size(max = 8)
    @Column(name = "hini_t2")
    private String hiniT2;
    @Size(max = 8)
    @Column(name = "hfin_t2")
    private String hfinT2;
    @Size(max = 8)
    @Column(name = "hini_t3")
    private String hiniT3;
    @Size(max = 8)
    @Column(name = "hfin_t3")
    private String hfinT3;
    @Size(max = 8)
    @Column(name = "descanso")
    private String descanso;
    @Column(name = "tipo_calculo")
    private int tipoCalculo;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @OneToMany(mappedBy = "idGenericaJornadaTipo", fetch = FetchType.LAZY)
    private List<GenericaJornada> genericaJornadaList;

    public GenericaJornadaTipo() {
    }

    public GenericaJornadaTipo(Integer idGenericaJornadaTipo) {
        this.idGenericaJornadaTipo = idGenericaJornadaTipo;
    }

    public Integer getIdGenericaJornadaTipo() {
        return idGenericaJornadaTipo;
    }

    public void setIdGenericaJornadaTipo(Integer idGenericaJornadaTipo) {
        this.idGenericaJornadaTipo = idGenericaJornadaTipo;
    }

    public String getHiniT1() {
        return hiniT1;
    }

    public void setHiniT1(String hiniT1) {
        this.hiniT1 = hiniT1;
    }

    public String getHfinT1() {
        return hfinT1;
    }

    public void setHfinT1(String hfinT1) {
        this.hfinT1 = hfinT1;
    }

    public String getHiniT2() {
        return hiniT2;
    }

    public void setHiniT2(String hiniT2) {
        this.hiniT2 = hiniT2;
    }

    public String getHfinT2() {
        return hfinT2;
    }

    public void setHfinT2(String hfinT2) {
        this.hfinT2 = hfinT2;
    }

    public String getHiniT3() {
        return hiniT3;
    }

    public void setHiniT3(String hiniT3) {
        this.hiniT3 = hiniT3;
    }

    public String getHfinT3() {
        return hfinT3;
    }

    public void setHfinT3(String hfinT3) {
        this.hfinT3 = hfinT3;
    }

    public String getDescanso() {
        return descanso;
    }

    public void setDescanso(String descanso) {
        this.descanso = descanso;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    public List<GenericaJornada> getGenericaJornadaList() {
        return genericaJornadaList;
    }

    public void setGenericaJornadaList(List<GenericaJornada> genericaJornadaList) {
        this.genericaJornadaList = genericaJornadaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaJornadaTipo != null ? idGenericaJornadaTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaJornadaTipo)) {
            return false;
        }
        GenericaJornadaTipo other = (GenericaJornadaTipo) object;
        if ((this.idGenericaJornadaTipo == null && other.idGenericaJornadaTipo != null) || (this.idGenericaJornadaTipo != null && !this.idGenericaJornadaTipo.equals(other.idGenericaJornadaTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaJornadaTipo[ idGenericaJornadaTipo=" + idGenericaJornadaTipo + " ]";
    }

    @XmlTransient
    public List<GenericaTurnoJornada> getGenericaTurnoJornadaList() {
        return genericaTurnoJornadaList;
    }

    public void setGenericaTurnoJornadaList(List<GenericaTurnoJornada> genericaTurnoJornadaList) {
        this.genericaTurnoJornadaList = genericaTurnoJornadaList;
    }

    @XmlTransient
    public List<GenericaTurnoFijo> getGenericaTurnoFijoList() {
        return genericaTurnoFijoList;
    }

    public void setGenericaTurnoFijoList(List<GenericaTurnoFijo> genericaTurnoFijoList) {
        this.genericaTurnoFijoList = genericaTurnoFijoList;
    }

    public List<GenericaPrgJornada> getGenericaPrgJornadaList() {
        return genericaPrgJornadaList;
    }

    public void setGenericaPrgJornadaList(List<GenericaPrgJornada> genericaPrgJornadaList) {
        this.genericaPrgJornadaList = genericaPrgJornadaList;
    }

    public int getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(int tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

}
