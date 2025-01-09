/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "operacion_km_checklist_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperacionKmChecklistDet.findAll", query = "SELECT o FROM OperacionKmChecklistDet o")
    , @NamedQuery(name = "OperacionKmChecklistDet.findByIdOperacionKmChecklistDet", query = "SELECT o FROM OperacionKmChecklistDet o WHERE o.idOperacionKmChecklistDet = :idOperacionKmChecklistDet")
    , @NamedQuery(name = "OperacionKmChecklistDet.findByUsername", query = "SELECT o FROM OperacionKmChecklistDet o WHERE o.username = :username")
    , @NamedQuery(name = "OperacionKmChecklistDet.findByCreado", query = "SELECT o FROM OperacionKmChecklistDet o WHERE o.creado = :creado")
    , @NamedQuery(name = "OperacionKmChecklistDet.findByModificado", query = "SELECT o FROM OperacionKmChecklistDet o WHERE o.modificado = :modificado")
    , @NamedQuery(name = "OperacionKmChecklistDet.findByEstadoReg", query = "SELECT o FROM OperacionKmChecklistDet o WHERE o.estadoReg = :estadoReg")})
public class OperacionKmChecklistDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operacion_km_checklist_det")
    private Integer idOperacionKmChecklistDet;
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
    @JoinColumn(name = "id_operacion_km_checklist", referencedColumnName = "id_operacion_km_checklist")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private OperacionKmChecklist idOperacionKmChecklist;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleado;

    public OperacionKmChecklistDet() {
    }

    public OperacionKmChecklistDet(Integer idOperacionKmChecklistDet) {
        this.idOperacionKmChecklistDet = idOperacionKmChecklistDet;
    }

    public OperacionKmChecklistDet(String username, Date creado, int estadoReg, Empleado idEmpleado) {
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
        this.idEmpleado = idEmpleado;
    }

    public OperacionKmChecklistDet(Integer idOperacionKmChecklistDet, String username, Date creado, int estadoReg) {
        this.idOperacionKmChecklistDet = idOperacionKmChecklistDet;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdOperacionKmChecklistDet() {
        return idOperacionKmChecklistDet;
    }

    public void setIdOperacionKmChecklistDet(Integer idOperacionKmChecklistDet) {
        this.idOperacionKmChecklistDet = idOperacionKmChecklistDet;
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

    public OperacionKmChecklist getIdOperacionKmChecklist() {
        return idOperacionKmChecklist;
    }

    public void setIdOperacionKmChecklist(OperacionKmChecklist idOperacionKmChecklist) {
        this.idOperacionKmChecklist = idOperacionKmChecklist;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperacionKmChecklistDet != null ? idOperacionKmChecklistDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperacionKmChecklistDet)) {
            return false;
        }
        OperacionKmChecklistDet other = (OperacionKmChecklistDet) object;
        if ((this.idOperacionKmChecklistDet == null && other.idOperacionKmChecklistDet != null) || (this.idOperacionKmChecklistDet != null && !this.idOperacionKmChecklistDet.equals(other.idOperacionKmChecklistDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.OperacionKmChecklistDet[ idOperacionKmChecklistDet=" + idOperacionKmChecklistDet + " ]";
    }
    
}
