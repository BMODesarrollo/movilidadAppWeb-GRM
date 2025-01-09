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
@Table(name = "acc_informe_master_bomberos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterBomberos.findAll", query = "SELECT a FROM AccInformeMasterBomberos a")
    , @NamedQuery(name = "AccInformeMasterBomberos.findByIdAccInformeMasterBomberos", query = "SELECT a FROM AccInformeMasterBomberos a WHERE a.idAccInformeMasterBomberos = :idAccInformeMasterBomberos")
    , @NamedQuery(name = "AccInformeMasterBomberos.findByNombres", query = "SELECT a FROM AccInformeMasterBomberos a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeMasterBomberos.findByIdentificacion", query = "SELECT a FROM AccInformeMasterBomberos a WHERE a.identificacion = :identificacion")
    , @NamedQuery(name = "AccInformeMasterBomberos.findByUnidadMovil", query = "SELECT a FROM AccInformeMasterBomberos a WHERE a.unidadMovil = :unidadMovil")
    , @NamedQuery(name = "AccInformeMasterBomberos.findByPlacaUnidadMovil", query = "SELECT a FROM AccInformeMasterBomberos a WHERE a.placaUnidadMovil = :placaUnidadMovil")
    , @NamedQuery(name = "AccInformeMasterBomberos.findByNumeroUnidadMovil", query = "SELECT a FROM AccInformeMasterBomberos a WHERE a.numeroUnidadMovil = :numeroUnidadMovil")})
public class AccInformeMasterBomberos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_bomberos")
    private Integer idAccInformeMasterBomberos;
    @Size(max = 60)
    @Column(name = "nombres")
    private String nombres;
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

    public AccInformeMasterBomberos() {
    }

    public AccInformeMasterBomberos(Integer idAccInformeMasterBomberos) {
        this.idAccInformeMasterBomberos = idAccInformeMasterBomberos;
    }

    public Integer getIdAccInformeMasterBomberos() {
        return idAccInformeMasterBomberos;
    }

    public void setIdAccInformeMasterBomberos(Integer idAccInformeMasterBomberos) {
        this.idAccInformeMasterBomberos = idAccInformeMasterBomberos;
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
        hash += (idAccInformeMasterBomberos != null ? idAccInformeMasterBomberos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterBomberos)) {
            return false;
        }
        AccInformeMasterBomberos other = (AccInformeMasterBomberos) object;
        if ((this.idAccInformeMasterBomberos == null && other.idAccInformeMasterBomberos != null) || (this.idAccInformeMasterBomberos != null && !this.idAccInformeMasterBomberos.equals(other.idAccInformeMasterBomberos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterBomberos[ idAccInformeMasterBomberos=" + idAccInformeMasterBomberos + " ]";
    }
    
}
