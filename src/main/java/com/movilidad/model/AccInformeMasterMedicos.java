/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_informe_master_medicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterMedicos.findAll", query = "SELECT a FROM AccInformeMasterMedicos a")
    , @NamedQuery(name = "AccInformeMasterMedicos.findByIdAccInformeMasterMedicos", query = "SELECT a FROM AccInformeMasterMedicos a WHERE a.idAccInformeMasterMedicos = :idAccInformeMasterMedicos")
    , @NamedQuery(name = "AccInformeMasterMedicos.findByNombres", query = "SELECT a FROM AccInformeMasterMedicos a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeMasterMedicos.findByIdentificacion", query = "SELECT a FROM AccInformeMasterMedicos a WHERE a.identificacion = :identificacion")
    , @NamedQuery(name = "AccInformeMasterMedicos.findByNroAmbulancia", query = "SELECT a FROM AccInformeMasterMedicos a WHERE a.nroAmbulancia = :nroAmbulancia")
    , @NamedQuery(name = "AccInformeMasterMedicos.findByPlacaAmbulancia", query = "SELECT a FROM AccInformeMasterMedicos a WHERE a.placaAmbulancia = :placaAmbulancia")
    , @NamedQuery(name = "AccInformeMasterMedicos.findByNumeroAmbulancia", query = "SELECT a FROM AccInformeMasterMedicos a WHERE a.numeroAmbulancia = :numeroAmbulancia")})
public class AccInformeMasterMedicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_medicos")
    private Integer idAccInformeMasterMedicos;
    @Size(max = 60)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 45)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 15)
    @Column(name = "nro_ambulancia")
    private String nroAmbulancia;
    @Size(max = 15)
    @Column(name = "placa_ambulancia")
    private String placaAmbulancia;
    @Size(max = 15)
    @Column(name = "numero_ambulancia")
    private String numeroAmbulancia;
    @JoinColumn(name = "id_acc_informe_master", referencedColumnName = "id_acc_informe_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeMaster idAccInformeMaster;

    public AccInformeMasterMedicos() {
    }

    public AccInformeMasterMedicos(Integer idAccInformeMasterMedicos) {
        this.idAccInformeMasterMedicos = idAccInformeMasterMedicos;
    }

    public Integer getIdAccInformeMasterMedicos() {
        return idAccInformeMasterMedicos;
    }

    public void setIdAccInformeMasterMedicos(Integer idAccInformeMasterMedicos) {
        this.idAccInformeMasterMedicos = idAccInformeMasterMedicos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNroAmbulancia() {
        return nroAmbulancia;
    }

    public void setNroAmbulancia(String nroAmbulancia) {
        this.nroAmbulancia = nroAmbulancia;
    }

    public String getPlacaAmbulancia() {
        return placaAmbulancia;
    }

    public void setPlacaAmbulancia(String placaAmbulancia) {
        this.placaAmbulancia = placaAmbulancia;
    }

    public String getNumeroAmbulancia() {
        return numeroAmbulancia;
    }

    public void setNumeroAmbulancia(String numeroAmbulancia) {
        this.numeroAmbulancia = numeroAmbulancia;
    }

    public AccInformeMaster getIdAccInformeMaster() {
        return idAccInformeMaster;
    }

    public void setIdAccInformeMaster(AccInformeMaster idAccInformeMaster) {
        this.idAccInformeMaster = idAccInformeMaster;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInformeMasterMedicos != null ? idAccInformeMasterMedicos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterMedicos)) {
            return false;
        }
        AccInformeMasterMedicos other = (AccInformeMasterMedicos) object;
        if ((this.idAccInformeMasterMedicos == null && other.idAccInformeMasterMedicos != null) || (this.idAccInformeMasterMedicos != null && !this.idAccInformeMasterMedicos.equals(other.idAccInformeMasterMedicos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterMedicos[ idAccInformeMasterMedicos=" + idAccInformeMasterMedicos + " ]";
    }
    
}
