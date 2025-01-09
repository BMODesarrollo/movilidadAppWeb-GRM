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
 * @author HP
 */
@Entity
@Table(name = "empleado_tipo_identificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoTipoIdentificacion.findAll", query = "SELECT e FROM EmpleadoTipoIdentificacion e")
    , @NamedQuery(name = "EmpleadoTipoIdentificacion.findByIdEmpleadoTipoIdentificacion", query = "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.idEmpleadoTipoIdentificacion = :idEmpleadoTipoIdentificacion")
    , @NamedQuery(name = "EmpleadoTipoIdentificacion.findByNombre", query = "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "EmpleadoTipoIdentificacion.findByUsername", query = "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.username = :username")
    , @NamedQuery(name = "EmpleadoTipoIdentificacion.findByCreado", query = "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.creado = :creado")
    , @NamedQuery(name = "EmpleadoTipoIdentificacion.findByModificado", query = "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.modificado = :modificado")
    , @NamedQuery(name = "EmpleadoTipoIdentificacion.findByEstadoReg", query = "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.estadoReg = :estadoReg")})
public class EmpleadoTipoIdentificacion implements Serializable {

    @OneToMany(mappedBy = "idTipoDoc", fetch = FetchType.LAZY)
    private List<CableAccSiniestrado> cableAccSiniestradoList;
    @OneToMany(mappedBy = "idTipoDoc", fetch = FetchType.LAZY)
    private List<CableAccTestigo> cableAccTestigoList;
    @OneToMany(mappedBy = "idTipoIdenProp", fetch = FetchType.LAZY)
    private List<AccInformeVehCond> accInformeVehCondList;
    @OneToMany(mappedBy = "idTipoIdentificacion", fetch = FetchType.LAZY)
    private List<AccInformeVehCond> accInformeVehCondList1;
    @OneToMany(mappedBy = "idTipoIdentificacion", fetch = FetchType.LAZY)
    private List<AccInformeMasterVic> accInformeMasterVicList;
    @OneToMany(mappedBy = "idTipoIdentificacion", fetch = FetchType.LAZY)
    private List<AccInformeVic> accInformeVicList;
    @OneToMany(mappedBy = "idTipoIdentificacion", fetch = FetchType.LAZY)
    private List<AccInformeTestigo> accInformeTestigoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_tipo_identificacion")
    private Integer idEmpleadoTipoIdentificacion;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoTipoIdentificacion", fetch = FetchType.LAZY)
    private List<Empleado> empleadoList;

    public EmpleadoTipoIdentificacion() {
    }

    public EmpleadoTipoIdentificacion(Integer idEmpleadoTipoIdentificacion) {
        this.idEmpleadoTipoIdentificacion = idEmpleadoTipoIdentificacion;
    }

    public EmpleadoTipoIdentificacion(Integer idEmpleadoTipoIdentificacion, String username, Date creado, int estadoReg) {
        this.idEmpleadoTipoIdentificacion = idEmpleadoTipoIdentificacion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdEmpleadoTipoIdentificacion() {
        return idEmpleadoTipoIdentificacion;
    }

    public void setIdEmpleadoTipoIdentificacion(Integer idEmpleadoTipoIdentificacion) {
        this.idEmpleadoTipoIdentificacion = idEmpleadoTipoIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleadoTipoIdentificacion != null ? idEmpleadoTipoIdentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoTipoIdentificacion)) {
            return false;
        }
        EmpleadoTipoIdentificacion other = (EmpleadoTipoIdentificacion) object;
        if ((this.idEmpleadoTipoIdentificacion == null && other.idEmpleadoTipoIdentificacion != null) || (this.idEmpleadoTipoIdentificacion != null && !this.idEmpleadoTipoIdentificacion.equals(other.idEmpleadoTipoIdentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.EmpleadoTipoIdentificacion[ idEmpleadoTipoIdentificacion=" + idEmpleadoTipoIdentificacion + " ]";
    }

    @XmlTransient
    public List<AccInformeVehCond> getAccInformeVehCondList() {
        return accInformeVehCondList;
    }

    public void setAccInformeVehCondList(List<AccInformeVehCond> accInformeVehCondList) {
        this.accInformeVehCondList = accInformeVehCondList;
    }

    @XmlTransient
    public List<AccInformeVehCond> getAccInformeVehCondList1() {
        return accInformeVehCondList1;
    }

    public void setAccInformeVehCondList1(List<AccInformeVehCond> accInformeVehCondList1) {
        this.accInformeVehCondList1 = accInformeVehCondList1;
    }

    @XmlTransient
    public List<AccInformeMasterVic> getAccInformeMasterVicList() {
        return accInformeMasterVicList;
    }

    public void setAccInformeMasterVicList(List<AccInformeMasterVic> accInformeMasterVicList) {
        this.accInformeMasterVicList = accInformeMasterVicList;
    }

    @XmlTransient
    public List<AccInformeVic> getAccInformeVicList() {
        return accInformeVicList;
    }

    public void setAccInformeVicList(List<AccInformeVic> accInformeVicList) {
        this.accInformeVicList = accInformeVicList;
    }

    @XmlTransient
    public List<AccInformeTestigo> getAccInformeTestigoList() {
        return accInformeTestigoList;
    }

    public void setAccInformeTestigoList(List<AccInformeTestigo> accInformeTestigoList) {
        this.accInformeTestigoList = accInformeTestigoList;
    }

    @XmlTransient
    public List<CableAccSiniestrado> getCableAccSiniestradoList() {
        return cableAccSiniestradoList;
    }

    public void setCableAccSiniestradoList(List<CableAccSiniestrado> cableAccSiniestradoList) {
        this.cableAccSiniestradoList = cableAccSiniestradoList;
    }

    @XmlTransient
    public List<CableAccTestigo> getCableAccTestigoList() {
        return cableAccTestigoList;
    }

    public void setCableAccTestigoList(List<CableAccTestigo> cableAccTestigoList) {
        this.cableAccTestigoList = cableAccTestigoList;
    }
}
