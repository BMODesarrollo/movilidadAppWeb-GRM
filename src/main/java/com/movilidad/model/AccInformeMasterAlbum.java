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
import javax.persistence.Lob;
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
@Table(name = "acc_informe_master_album")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterAlbum.findAll", query = "SELECT a FROM AccInformeMasterAlbum a")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByIdAccInformeMasterAlbum", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.idAccInformeMasterAlbum = :idAccInformeMasterAlbum")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByTecnologia", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.tecnologia = :tecnologia")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByInstrumentos", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.instrumentos = :instrumentos")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByMarca", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.marca = :marca")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByReferencia", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.referencia = :referencia")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByLente", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.lente = :lente")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByZoom", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.zoom = :zoom")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByIso", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.iso = :iso")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByTarjeta", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.tarjeta = :tarjeta")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByTipoFoto", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.tipoFoto = :tipoFoto")
    , @NamedQuery(name = "AccInformeMasterAlbum.findByPathFoto", query = "SELECT a FROM AccInformeMasterAlbum a WHERE a.pathFoto = :pathFoto")})
public class AccInformeMasterAlbum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_album")
    private Integer idAccInformeMasterAlbum;
    @Column(name = "tecnologia")
    private Integer tecnologia;
    @Size(max = 60)
    @Column(name = "instrumentos")
    private String instrumentos;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
    @Size(max = 45)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 45)
    @Column(name = "lente")
    private String lente;
    @Size(max = 15)
    @Column(name = "zoom")
    private String zoom;
    @Size(max = 15)
    @Column(name = "iso")
    private String iso;
    @Size(max = 45)
    @Column(name = "tarjeta")
    private String tarjeta;
    @Size(max = 45)
    @Column(name = "tipo_foto")
    private String tipoFoto;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "path_foto")
    private String pathFoto;
    @JoinColumn(name = "id_acc_informe_master", referencedColumnName = "id_acc_informe_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeMaster idAccInformeMaster;

    public AccInformeMasterAlbum() {
    }

    public AccInformeMasterAlbum(Integer idAccInformeMasterAlbum) {
        this.idAccInformeMasterAlbum = idAccInformeMasterAlbum;
    }

    public Integer getIdAccInformeMasterAlbum() {
        return idAccInformeMasterAlbum;
    }

    public void setIdAccInformeMasterAlbum(Integer idAccInformeMasterAlbum) {
        this.idAccInformeMasterAlbum = idAccInformeMasterAlbum;
    }

    public Integer getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(Integer tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(String instrumentos) {
        this.instrumentos = instrumentos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLente() {
        return lente;
    }

    public void setLente(String lente) {
        this.lente = lente;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getTipoFoto() {
        return tipoFoto;
    }

    public void setTipoFoto(String tipoFoto) {
        this.tipoFoto = tipoFoto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
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
        hash += (idAccInformeMasterAlbum != null ? idAccInformeMasterAlbum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterAlbum)) {
            return false;
        }
        AccInformeMasterAlbum other = (AccInformeMasterAlbum) object;
        if ((this.idAccInformeMasterAlbum == null && other.idAccInformeMasterAlbum != null) || (this.idAccInformeMasterAlbum != null && !this.idAccInformeMasterAlbum.equals(other.idAccInformeMasterAlbum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterAlbum[ idAccInformeMasterAlbum=" + idAccInformeMasterAlbum + " ]";
    }
    
}
