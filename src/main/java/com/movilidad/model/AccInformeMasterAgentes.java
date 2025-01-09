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
@Table(name = "acc_informe_master_agentes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterAgentes.findAll", query = "SELECT a FROM AccInformeMasterAgentes a")
    , @NamedQuery(name = "AccInformeMasterAgentes.findByIdAccInformeMasterAgentes", query = "SELECT a FROM AccInformeMasterAgentes a WHERE a.idAccInformeMasterAgentes = :idAccInformeMasterAgentes")
    , @NamedQuery(name = "AccInformeMasterAgentes.findByNombres", query = "SELECT a FROM AccInformeMasterAgentes a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeMasterAgentes.findByChaleco", query = "SELECT a FROM AccInformeMasterAgentes a WHERE a.chaleco = :chaleco")
    , @NamedQuery(name = "AccInformeMasterAgentes.findByPlacaChaleco", query = "SELECT a FROM AccInformeMasterAgentes a WHERE a.placaChaleco = :placaChaleco")
    , @NamedQuery(name = "AccInformeMasterAgentes.findByIdentificacion", query = "SELECT a FROM AccInformeMasterAgentes a WHERE a.identificacion = :identificacion")
    , @NamedQuery(name = "AccInformeMasterAgentes.findByUnidadMovil", query = "SELECT a FROM AccInformeMasterAgentes a WHERE a.unidadMovil = :unidadMovil")
    , @NamedQuery(name = "AccInformeMasterAgentes.findByPlacaUnidadMovil", query = "SELECT a FROM AccInformeMasterAgentes a WHERE a.placaUnidadMovil = :placaUnidadMovil")
    , @NamedQuery(name = "AccInformeMasterAgentes.findByNumeroUnidadMovil", query = "SELECT a FROM AccInformeMasterAgentes a WHERE a.numeroUnidadMovil = :numeroUnidadMovil")})
public class AccInformeMasterAgentes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_agentes")
    private Integer idAccInformeMasterAgentes;
    @Size(max = 60)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 45)
    @Column(name = "chaleco")
    private String chaleco;
    @Size(max = 45)
    @Column(name = "placa_chaleco")
    private String placaChaleco;
    @Size(max = 45)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 45)
    @Column(name = "unidad_movil")
    private String unidadMovil;
    @Size(max = 15)
    @Column(name = "placa_unidad_movil")
    private String placaUnidadMovil;
    @Size(max = 15)
    @Column(name = "numero_unidad_movil")
    private String numeroUnidadMovil;
    @JoinColumn(name = "id_acc_informe_master", referencedColumnName = "id_acc_informe_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeMaster idAccInformeMaster;

    public AccInformeMasterAgentes() {
    }

    public AccInformeMasterAgentes(Integer idAccInformeMasterAgentes) {
        this.idAccInformeMasterAgentes = idAccInformeMasterAgentes;
    }

    public Integer getIdAccInformeMasterAgentes() {
        return idAccInformeMasterAgentes;
    }

    public void setIdAccInformeMasterAgentes(Integer idAccInformeMasterAgentes) {
        this.idAccInformeMasterAgentes = idAccInformeMasterAgentes;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getChaleco() {
        return chaleco;
    }

    public void setChaleco(String chaleco) {
        this.chaleco = chaleco;
    }

    public String getPlacaChaleco() {
        return placaChaleco;
    }

    public void setPlacaChaleco(String placaChaleco) {
        this.placaChaleco = placaChaleco;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getUnidadMovil() {
        return unidadMovil;
    }

    public void setUnidadMovil(String unidadMovil) {
        this.unidadMovil = unidadMovil;
    }

    public String getPlacaUnidadMovil() {
        return placaUnidadMovil;
    }

    public void setPlacaUnidadMovil(String placaUnidadMovil) {
        this.placaUnidadMovil = placaUnidadMovil;
    }

    public String getNumeroUnidadMovil() {
        return numeroUnidadMovil;
    }

    public void setNumeroUnidadMovil(String numeroUnidadMovil) {
        this.numeroUnidadMovil = numeroUnidadMovil;
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
        hash += (idAccInformeMasterAgentes != null ? idAccInformeMasterAgentes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterAgentes)) {
            return false;
        }
        AccInformeMasterAgentes other = (AccInformeMasterAgentes) object;
        if ((this.idAccInformeMasterAgentes == null && other.idAccInformeMasterAgentes != null) || (this.idAccInformeMasterAgentes != null && !this.idAccInformeMasterAgentes.equals(other.idAccInformeMasterAgentes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterAgentes[ idAccInformeMasterAgentes=" + idAccInformeMasterAgentes + " ]";
    }
    
}
