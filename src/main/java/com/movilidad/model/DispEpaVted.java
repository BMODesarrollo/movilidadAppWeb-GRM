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
 * @author solucionesit
 */
@Entity
@Table(name = "disp_epa_vted")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispEpaVted.findAll", query = "SELECT d FROM DispEpaVted d")
    , @NamedQuery(name = "DispEpaVted.findByIdDispEpaVted", query = "SELECT d FROM DispEpaVted d WHERE d.idDispEpaVted = :idDispEpaVted")
    , @NamedQuery(name = "DispEpaVted.findByUsername", query = "SELECT d FROM DispEpaVted d WHERE d.username = :username")
    , @NamedQuery(name = "DispEpaVted.findByCreado", query = "SELECT d FROM DispEpaVted d WHERE d.creado = :creado")
    , @NamedQuery(name = "DispEpaVted.findByModificado", query = "SELECT d FROM DispEpaVted d WHERE d.modificado = :modificado")
    , @NamedQuery(name = "DispEpaVted.findByEstadoReg", query = "SELECT d FROM DispEpaVted d WHERE d.estadoReg = :estadoReg")})
public class DispEpaVted implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_epa_vted")
    private Integer idDispEpaVted;
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
    @JoinColumn(name = "id_disp_estado_pend_actual", referencedColumnName = "id_disp_estado_pend_actual")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DispEstadoPendActual idDispEstadoPendActual;
    @JoinColumn(name = "id_vehiculo_tipo_estado_det", referencedColumnName = "id_vehiculo_tipo_estado_det")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipoEstadoDet idVehiculoTipoEstadoDet;

    public DispEpaVted() {
    }

    public DispEpaVted(Integer idDispEpaVted) {
        this.idDispEpaVted = idDispEpaVted;
    }

    public DispEpaVted(Integer idDispEpaVted, String username, Date creado, int estadoReg) {
        this.idDispEpaVted = idDispEpaVted;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDispEpaVted() {
        return idDispEpaVted;
    }

    public void setIdDispEpaVted(Integer idDispEpaVted) {
        this.idDispEpaVted = idDispEpaVted;
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

    public DispEstadoPendActual getIdDispEstadoPendActual() {
        return idDispEstadoPendActual;
    }

    public void setIdDispEstadoPendActual(DispEstadoPendActual idDispEstadoPendActual) {
        this.idDispEstadoPendActual = idDispEstadoPendActual;
    }

    public VehiculoTipoEstadoDet getIdVehiculoTipoEstadoDet() {
        return idVehiculoTipoEstadoDet;
    }

    public void setIdVehiculoTipoEstadoDet(VehiculoTipoEstadoDet idVehiculoTipoEstadoDet) {
        this.idVehiculoTipoEstadoDet = idVehiculoTipoEstadoDet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispEpaVted != null ? idDispEpaVted.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispEpaVted)) {
            return false;
        }
        DispEpaVted other = (DispEpaVted) object;
        if ((this.idDispEpaVted == null && other.idDispEpaVted != null) || (this.idDispEpaVted != null && !this.idDispEpaVted.equals(other.idDispEpaVted))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispEpaVted[ idDispEpaVted=" + idDispEpaVted + " ]";
    }
    
}
