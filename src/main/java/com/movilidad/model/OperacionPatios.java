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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
 * @author HP
 */
@Entity
@Table(name = "operacion_patios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperacionPatios.findAll", query = "SELECT o FROM OperacionPatios o")
    , @NamedQuery(name = "OperacionPatios.findByIdOperacionPatios", query = "SELECT o FROM OperacionPatios o WHERE o.idOperacionPatios = :idOperacionPatios")
    , @NamedQuery(name = "OperacionPatios.findByCodigoPatio", query = "SELECT o FROM OperacionPatios o WHERE o.codigoPatio = :codigoPatio")
    , @NamedQuery(name = "OperacionPatios.findByNombrePatio", query = "SELECT o FROM OperacionPatios o WHERE o.nombrePatio = :nombrePatio")
    , @NamedQuery(name = "OperacionPatios.findByUsername", query = "SELECT o FROM OperacionPatios o WHERE o.username = :username")
    , @NamedQuery(name = "OperacionPatios.findByCreado", query = "SELECT o FROM OperacionPatios o WHERE o.creado = :creado")
    , @NamedQuery(name = "OperacionPatios.findByModificado", query = "SELECT o FROM OperacionPatios o WHERE o.modificado = :modificado")
    , @NamedQuery(name = "OperacionPatios.findByEstadoReg", query = "SELECT o FROM OperacionPatios o WHERE o.estadoReg = :estadoReg")})
public class OperacionPatios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operacion_patios")
    private Integer idOperacionPatios;
    @Size(max = 45)
    @Column(name = "codigo_patio")
    private String codigoPatio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_patio")
    private String nombrePatio;
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
    @OneToMany(mappedBy = "idPatio", fetch = FetchType.LAZY)
    private List<Multa> multaList;
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa idEmpresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOperacionPatio", fetch = FetchType.LAZY)
    private List<Empleado> empleadoList;
    @OneToMany(mappedBy = "idOperacionPatio", fetch = FetchType.LAZY)
    private List<OperacionGrua> operacionGruaList;

    //georeferencia
    @Column(name = "longitud")
    private String longitud;
    @Column(name = "latitud")
    private String latitud;

    public OperacionPatios() {
    }

    public OperacionPatios(Integer idOperacionPatios) {
        this.idOperacionPatios = idOperacionPatios;
    }

    public OperacionPatios(Integer idOperacionPatios, String nombrePatio, String username, Date creado, int estadoReg) {
        this.idOperacionPatios = idOperacionPatios;
        this.nombrePatio = nombrePatio;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdOperacionPatios() {
        return idOperacionPatios;
    }

    public void setIdOperacionPatios(Integer idOperacionPatios) {
        this.idOperacionPatios = idOperacionPatios;
    }

    public String getCodigoPatio() {
        return codigoPatio;
    }

    public void setCodigoPatio(String codigoPatio) {
        this.codigoPatio = codigoPatio;
    }

    public String getNombrePatio() {
        return nombrePatio;
    }

    public void setNombrePatio(String nombrePatio) {
        this.nombrePatio = nombrePatio;
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

    @XmlTransient
    public List<Multa> getMultaList() {
        return multaList;
    }

    public void setMultaList(List<Multa> multaList) {
        this.multaList = multaList;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @XmlTransient
    public List<OperacionGrua> getOperacionGruaList() {
        return operacionGruaList;
    }

    public void setOperacionGruaList(List<OperacionGrua> operacionGruaList) {
        this.operacionGruaList = operacionGruaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperacionPatios != null ? idOperacionPatios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperacionPatios)) {
            return false;
        }
        OperacionPatios other = (OperacionPatios) object;
        if ((this.idOperacionPatios == null && other.idOperacionPatios != null) || (this.idOperacionPatios != null && !this.idOperacionPatios.equals(other.idOperacionPatios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.OperacionPatios[ idOperacionPatios=" + idOperacionPatios + " ]";
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

}
