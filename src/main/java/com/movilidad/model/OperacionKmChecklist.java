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
@Table(name = "operacion_km_checklist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperacionKmChecklist.findAll", query = "SELECT o FROM OperacionKmChecklist o")
    , @NamedQuery(name = "OperacionKmChecklist.findByIdOperacionKmChecklist", query = "SELECT o FROM OperacionKmChecklist o WHERE o.idOperacionKmChecklist = :idOperacionKmChecklist")
    , @NamedQuery(name = "OperacionKmChecklist.findByFecha", query = "SELECT o FROM OperacionKmChecklist o WHERE o.fecha = :fecha")
    , @NamedQuery(name = "OperacionKmChecklist.findByKmInicial", query = "SELECT o FROM OperacionKmChecklist o WHERE o.kmInicial = :kmInicial")
    , @NamedQuery(name = "OperacionKmChecklist.findByKmFinal", query = "SELECT o FROM OperacionKmChecklist o WHERE o.kmFinal = :kmFinal")
    , @NamedQuery(name = "OperacionKmChecklist.findByPathChecklist", query = "SELECT o FROM OperacionKmChecklist o WHERE o.pathChecklist = :pathChecklist")
    , @NamedQuery(name = "OperacionKmChecklist.findByUsername", query = "SELECT o FROM OperacionKmChecklist o WHERE o.username = :username")
    , @NamedQuery(name = "OperacionKmChecklist.findByCreado", query = "SELECT o FROM OperacionKmChecklist o WHERE o.creado = :creado")
    , @NamedQuery(name = "OperacionKmChecklist.findByModificado", query = "SELECT o FROM OperacionKmChecklist o WHERE o.modificado = :modificado")
    , @NamedQuery(name = "OperacionKmChecklist.findByEstadoReg", query = "SELECT o FROM OperacionKmChecklist o WHERE o.estadoReg = :estadoReg")})
public class OperacionKmChecklist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operacion_km_checklist")
    private Integer idOperacionKmChecklist;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "km_inicial")
    private int kmInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "km_final")
    private int kmFinal;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "path_checklist")
    private String pathChecklist;
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
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOperacionKmChecklist", fetch = FetchType.LAZY)
    private List<OperacionKmChecklistDet> operacionKmChecklistDetList;

    public OperacionKmChecklist() {
    }

    public OperacionKmChecklist(Integer idOperacionKmChecklist) {
        this.idOperacionKmChecklist = idOperacionKmChecklist;
    }

    public OperacionKmChecklist(Integer idOperacionKmChecklist, Date fecha, int kmInicial, int kmFinal, String pathChecklist, String username, Date creado, int estadoReg) {
        this.idOperacionKmChecklist = idOperacionKmChecklist;
        this.fecha = fecha;
        this.kmInicial = kmInicial;
        this.kmFinal = kmFinal;
        this.pathChecklist = pathChecklist;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdOperacionKmChecklist() {
        return idOperacionKmChecklist;
    }

    public void setIdOperacionKmChecklist(Integer idOperacionKmChecklist) {
        this.idOperacionKmChecklist = idOperacionKmChecklist;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getKmInicial() {
        return kmInicial;
    }

    public void setKmInicial(int kmInicial) {
        this.kmInicial = kmInicial;
    }

    public int getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(int kmFinal) {
        this.kmFinal = kmFinal;
    }

    public String getPathChecklist() {
        return pathChecklist;
    }

    public void setPathChecklist(String pathChecklist) {
        this.pathChecklist = pathChecklist;
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

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @XmlTransient
    public List<OperacionKmChecklistDet> getOperacionKmChecklistDetList() {
        return operacionKmChecklistDetList;
    }

    public void setOperacionKmChecklistDetList(List<OperacionKmChecklistDet> operacionKmChecklistDetList) {
        this.operacionKmChecklistDetList = operacionKmChecklistDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperacionKmChecklist != null ? idOperacionKmChecklist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperacionKmChecklist)) {
            return false;
        }
        OperacionKmChecklist other = (OperacionKmChecklist) object;
        if ((this.idOperacionKmChecklist == null && other.idOperacionKmChecklist != null) || (this.idOperacionKmChecklist != null && !this.idOperacionKmChecklist.equals(other.idOperacionKmChecklist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.OperacionKmChecklist[ idOperacionKmChecklist=" + idOperacionKmChecklist + " ]";
    }
    
}
