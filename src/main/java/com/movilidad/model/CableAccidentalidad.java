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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "cable_accidentalidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableAccidentalidad.findAll", query = "SELECT c FROM CableAccidentalidad c")
    , @NamedQuery(name = "CableAccidentalidad.findByIdCableAccidentalidad", query = "SELECT c FROM CableAccidentalidad c WHERE c.idCableAccidentalidad = :idCableAccidentalidad")
    , @NamedQuery(name = "CableAccidentalidad.findByFechaHora", query = "SELECT c FROM CableAccidentalidad c WHERE c.fechaHora = :fechaHora")
    , @NamedQuery(name = "CableAccidentalidad.findByDescripcionOperador", query = "SELECT c FROM CableAccidentalidad c WHERE c.descripcionOperador = :descripcionOperador")
    , @NamedQuery(name = "CableAccidentalidad.findByOtroLugar", query = "SELECT c FROM CableAccidentalidad c WHERE c.otroLugar = :otroLugar")
    , @NamedQuery(name = "CableAccidentalidad.findByFirmaOperador", query = "SELECT c FROM CableAccidentalidad c WHERE c.firmaOperador = :firmaOperador")
    , @NamedQuery(name = "CableAccidentalidad.findByUsername", query = "SELECT c FROM CableAccidentalidad c WHERE c.username = :username")
    , @NamedQuery(name = "CableAccidentalidad.findByCreado", query = "SELECT c FROM CableAccidentalidad c WHERE c.creado = :creado")
    , @NamedQuery(name = "CableAccidentalidad.findByModificado", query = "SELECT c FROM CableAccidentalidad c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "CableAccidentalidad.findByEstadoReg", query = "SELECT c FROM CableAccidentalidad c WHERE c.estadoReg = :estadoReg")})
public class CableAccidentalidad implements Serializable {

    @OneToMany(mappedBy = "idCableAccidentalidad", fetch = FetchType.LAZY)
    private List<CableAccPlanAccion> cableAccPlanAccionList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_accidentalidad")
    private Integer idCableAccidentalidad;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion_operador")
    private String descripcionOperador;
    @Size(max = 60)
    @Column(name = "otro_lugar")
    private String otroLugar;
    @Column(name = "afecta_poliza")
    private Integer afectaPoliza;
    @Size(max = 255)
    @Column(name = "firma_operador")
    private String firmaOperador;
     @Lob
    @Size(max = 65535)
    @Column(name = "causa")
    private String causa;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @OneToMany(mappedBy = "idCableAccidentalidad", fetch = FetchType.LAZY)
    private List<CableAccSiniestrado> cableAccSiniestradoList;
    @OneToMany(mappedBy = "idCableAccidentalidad", fetch = FetchType.LAZY)
    private List<CableAccDocumento> cableAccDocumentoList;
    @JoinColumn(name = "id_cable_acc_clasificacion", referencedColumnName = "id_cable_acc_clasificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccClasificacion idCableAccClasificacion;
    @JoinColumn(name = "id_cable_acc_tipo", referencedColumnName = "id_cable_acc_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccTipo idCableAccTipo;
    @JoinColumn(name = "id_cable_acc_tipo_evento", referencedColumnName = "id_cable_acc_tipo_evento")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccTipoEvento idCableAccTipoEvento;
    @JoinColumn(name = "id_cable_cabina", referencedColumnName = "id_cable_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableCabina idCableCabina;
    @JoinColumn(name = "id_cable_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEstacion idCableEstacion;
    @JoinColumn(name = "id_operador", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idOperador;

    public CableAccidentalidad() {
    }

    public CableAccidentalidad(Integer idCableAccidentalidad) {
        this.idCableAccidentalidad = idCableAccidentalidad;
    }

    public Integer getIdCableAccidentalidad() {
        return idCableAccidentalidad;
    }

    public void setIdCableAccidentalidad(Integer idCableAccidentalidad) {
        this.idCableAccidentalidad = idCableAccidentalidad;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDescripcionOperador() {
        return descripcionOperador;
    }

    public void setDescripcionOperador(String descripcionOperador) {
        this.descripcionOperador = descripcionOperador;
    }

    public String getOtroLugar() {
        return otroLugar;
    }

    public void setOtroLugar(String otroLugar) {
        this.otroLugar = otroLugar;
    }

    public Integer getAfectaPoliza() {
        return afectaPoliza;
    }

    public void setAfectaPoliza(Integer afectaPoliza) {
        this.afectaPoliza = afectaPoliza;
    }

    public String getFirmaOperador() {
        return firmaOperador;
    }

    public void setFirmaOperador(String firmaOperador) {
        this.firmaOperador = firmaOperador;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<CableAccSiniestrado> getCableAccSiniestradoList() {
        return cableAccSiniestradoList;
    }

    public void setCableAccSiniestradoList(List<CableAccSiniestrado> cableAccSiniestradoList) {
        this.cableAccSiniestradoList = cableAccSiniestradoList;
    }

    @XmlTransient
    public List<CableAccDocumento> getCableAccDocumentoList() {
        return cableAccDocumentoList;
    }

    public void setCableAccDocumentoList(List<CableAccDocumento> cableAccDocumentoList) {
        this.cableAccDocumentoList = cableAccDocumentoList;
    }

    public CableAccClasificacion getIdCableAccClasificacion() {
        return idCableAccClasificacion;
    }

    public void setIdCableAccClasificacion(CableAccClasificacion idCableAccClasificacion) {
        this.idCableAccClasificacion = idCableAccClasificacion;
    }

    public CableAccTipo getIdCableAccTipo() {
        return idCableAccTipo;
    }

    public void setIdCableAccTipo(CableAccTipo idCableAccTipo) {
        this.idCableAccTipo = idCableAccTipo;
    }

    public CableAccTipoEvento getIdCableAccTipoEvento() {
        return idCableAccTipoEvento;
    }

    public void setIdCableAccTipoEvento(CableAccTipoEvento idCableAccTipoEvento) {
        this.idCableAccTipoEvento = idCableAccTipoEvento;
    }

    public CableCabina getIdCableCabina() {
        return idCableCabina;
    }

    public void setIdCableCabina(CableCabina idCableCabina) {
        this.idCableCabina = idCableCabina;
    }

    public CableEstacion getIdCableEstacion() {
        return idCableEstacion;
    }

    public void setIdCableEstacion(CableEstacion idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    public Empleado getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Empleado idOperador) {
        this.idOperador = idOperador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccidentalidad != null ? idCableAccidentalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccidentalidad)) {
            return false;
        }
        CableAccidentalidad other = (CableAccidentalidad) object;
        if ((this.idCableAccidentalidad == null && other.idCableAccidentalidad != null) || (this.idCableAccidentalidad != null && !this.idCableAccidentalidad.equals(other.idCableAccidentalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccidentalidad[ idCableAccidentalidad=" + idCableAccidentalidad + " ]";
    }

    @XmlTransient
    public List<CableAccPlanAccion> getCableAccPlanAccionList() {
        return cableAccPlanAccionList;
    }

    public void setCableAccPlanAccionList(List<CableAccPlanAccion> cableAccPlanAccionList) {
        this.cableAccPlanAccionList = cableAccPlanAccionList;
    }

}
