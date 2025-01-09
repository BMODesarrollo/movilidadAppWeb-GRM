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
@Table(name = "acc_informe_master_testigo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterTestigo.findAll", query = "SELECT a FROM AccInformeMasterTestigo a")
    , @NamedQuery(name = "AccInformeMasterTestigo.findByIdAccInformeMasterTestigo", query = "SELECT a FROM AccInformeMasterTestigo a WHERE a.idAccInformeMasterTestigo = :idAccInformeMasterTestigo")
    , @NamedQuery(name = "AccInformeMasterTestigo.findByNombres", query = "SELECT a FROM AccInformeMasterTestigo a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeMasterTestigo.findByVideo", query = "SELECT a FROM AccInformeMasterTestigo a WHERE a.video = :video")
    , @NamedQuery(name = "AccInformeMasterTestigo.findByHoraVideo", query = "SELECT a FROM AccInformeMasterTestigo a WHERE a.horaVideo = :horaVideo")
    , @NamedQuery(name = "AccInformeMasterTestigo.findByDireccion", query = "SELECT a FROM AccInformeMasterTestigo a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "AccInformeMasterTestigo.findByIdentificacion", query = "SELECT a FROM AccInformeMasterTestigo a WHERE a.identificacion = :identificacion")
    , @NamedQuery(name = "AccInformeMasterTestigo.findByTelefono", query = "SELECT a FROM AccInformeMasterTestigo a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "AccInformeMasterTestigo.findByPathVideo", query = "SELECT a FROM AccInformeMasterTestigo a WHERE a.pathVideo = :pathVideo")})
public class AccInformeMasterTestigo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_testigo")
    private Integer idAccInformeMasterTestigo;
    @Size(max = 60)
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "video")
    private Integer video;
    @Size(max = 15)
    @Column(name = "hora_video")
    private String horaVideo;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 15)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 255)
    @Column(name = "path_video")
    private String pathVideo;
    @JoinColumn(name = "id_acc_informe_master", referencedColumnName = "id_acc_informe_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeMaster idAccInformeMaster;

    public AccInformeMasterTestigo() {
    }

    public AccInformeMasterTestigo(Integer idAccInformeMasterTestigo) {
        this.idAccInformeMasterTestigo = idAccInformeMasterTestigo;
    }

    public Integer getIdAccInformeMasterTestigo() {
        return idAccInformeMasterTestigo;
    }

    public void setIdAccInformeMasterTestigo(Integer idAccInformeMasterTestigo) {
        this.idAccInformeMasterTestigo = idAccInformeMasterTestigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    public String getHoraVideo() {
        return horaVideo;
    }

    public void setHoraVideo(String horaVideo) {
        this.horaVideo = horaVideo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
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
        hash += (idAccInformeMasterTestigo != null ? idAccInformeMasterTestigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterTestigo)) {
            return false;
        }
        AccInformeMasterTestigo other = (AccInformeMasterTestigo) object;
        if ((this.idAccInformeMasterTestigo == null && other.idAccInformeMasterTestigo != null) || (this.idAccInformeMasterTestigo != null && !this.idAccInformeMasterTestigo.equals(other.idAccInformeMasterTestigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterTestigo[ idAccInformeMasterTestigo=" + idAccInformeMasterTestigo + " ]";
    }
    
}
