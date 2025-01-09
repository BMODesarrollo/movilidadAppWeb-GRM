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
@Table(name = "acc_informe_master_inspectores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterInspectores.findAll", query = "SELECT a FROM AccInformeMasterInspectores a")
    , @NamedQuery(name = "AccInformeMasterInspectores.findByIdAccInformeMasterInspectores", query = "SELECT a FROM AccInformeMasterInspectores a WHERE a.idAccInformeMasterInspectores = :idAccInformeMasterInspectores")
    , @NamedQuery(name = "AccInformeMasterInspectores.findByNombres", query = "SELECT a FROM AccInformeMasterInspectores a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeMasterInspectores.findByChaleco", query = "SELECT a FROM AccInformeMasterInspectores a WHERE a.chaleco = :chaleco")
    , @NamedQuery(name = "AccInformeMasterInspectores.findByPlacaChaleco", query = "SELECT a FROM AccInformeMasterInspectores a WHERE a.placaChaleco = :placaChaleco")
    , @NamedQuery(name = "AccInformeMasterInspectores.findByIdentificacion", query = "SELECT a FROM AccInformeMasterInspectores a WHERE a.identificacion = :identificacion")
    , @NamedQuery(name = "AccInformeMasterInspectores.findByUnidadMovil", query = "SELECT a FROM AccInformeMasterInspectores a WHERE a.unidadMovil = :unidadMovil")
    , @NamedQuery(name = "AccInformeMasterInspectores.findByPlacaUnidadMovil", query = "SELECT a FROM AccInformeMasterInspectores a WHERE a.placaUnidadMovil = :placaUnidadMovil")
    , @NamedQuery(name = "AccInformeMasterInspectores.findByNumeroUnidadMovil", query = "SELECT a FROM AccInformeMasterInspectores a WHERE a.numeroUnidadMovil = :numeroUnidadMovil")})
public class AccInformeMasterInspectores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_inspectores")
    private Integer idAccInformeMasterInspectores;
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

    public AccInformeMasterInspectores() {
    }

    public AccInformeMasterInspectores(Integer idAccInformeMasterInspectores) {
        this.idAccInformeMasterInspectores = idAccInformeMasterInspectores;
    }

    public Integer getIdAccInformeMasterInspectores() {
        return idAccInformeMasterInspectores;
    }

    public void setIdAccInformeMasterInspectores(Integer idAccInformeMasterInspectores) {
        this.idAccInformeMasterInspectores = idAccInformeMasterInspectores;
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
        hash += (idAccInformeMasterInspectores != null ? idAccInformeMasterInspectores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterInspectores)) {
            return false;
        }
        AccInformeMasterInspectores other = (AccInformeMasterInspectores) object;
        if ((this.idAccInformeMasterInspectores == null && other.idAccInformeMasterInspectores != null) || (this.idAccInformeMasterInspectores != null && !this.idAccInformeMasterInspectores.equals(other.idAccInformeMasterInspectores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterInspectores[ idAccInformeMasterInspectores=" + idAccInformeMasterInspectores + " ]";
    }
    
}
