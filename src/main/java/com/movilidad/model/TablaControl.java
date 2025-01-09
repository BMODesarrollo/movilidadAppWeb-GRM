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
@Table(name = "tabla_control")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablaControl.findAll", query = "SELECT t FROM TablaControl t")
    , @NamedQuery(name = "TablaControl.findByIdTablaControl", query = "SELECT t FROM TablaControl t WHERE t.idTablaControl = :idTablaControl")
    , @NamedQuery(name = "TablaControl.findByFecha", query = "SELECT t FROM TablaControl t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "TablaControl.findByTipoDia", query = "SELECT t FROM TablaControl t WHERE t.tipoDia = :tipoDia")
    , @NamedQuery(name = "TablaControl.findByMcomPrg", query = "SELECT t FROM TablaControl t WHERE t.mcomPrg = :mcomPrg")
    , @NamedQuery(name = "TablaControl.findByMvacProg", query = "SELECT t FROM TablaControl t WHERE t.mvacProg = :mvacProg")
    , @NamedQuery(name = "TablaControl.findByMcomArtPrg", query = "SELECT t FROM TablaControl t WHERE t.mcomArtPrg = :mcomArtPrg")
    , @NamedQuery(name = "TablaControl.findByMvacArtPrg", query = "SELECT t FROM TablaControl t WHERE t.mvacArtPrg = :mvacArtPrg")
    , @NamedQuery(name = "TablaControl.findByMcomBiPrg", query = "SELECT t FROM TablaControl t WHERE t.mcomBiPrg = :mcomBiPrg")
    , @NamedQuery(name = "TablaControl.findByMvacBiPrg", query = "SELECT t FROM TablaControl t WHERE t.mvacBiPrg = :mvacBiPrg")
    , @NamedQuery(name = "TablaControl.findByMcomArtCon", query = "SELECT t FROM TablaControl t WHERE t.mcomArtCon = :mcomArtCon")
    , @NamedQuery(name = "TablaControl.findByMcomBiCon", query = "SELECT t FROM TablaControl t WHERE t.mcomBiCon = :mcomBiCon")
    , @NamedQuery(name = "TablaControl.findByVrMcomArtCon", query = "SELECT t FROM TablaControl t WHERE t.vrMcomArtCon = :vrMcomArtCon")
    , @NamedQuery(name = "TablaControl.findByVrMconBiCon", query = "SELECT t FROM TablaControl t WHERE t.vrMconBiCon = :vrMconBiCon")
    , @NamedQuery(name = "TablaControl.findByOperadoresPrg", query = "SELECT t FROM TablaControl t WHERE t.operadoresPrg = :operadoresPrg")
    , @NamedQuery(name = "TablaControl.findByOperadoresEje", query = "SELECT t FROM TablaControl t WHERE t.operadoresEje = :operadoresEje")
    , @NamedQuery(name = "TablaControl.findByVehiculosPrg", query = "SELECT t FROM TablaControl t WHERE t.vehiculosPrg = :vehiculosPrg")
    , @NamedQuery(name = "TablaControl.findByVehiculosEje", query = "SELECT t FROM TablaControl t WHERE t.vehiculosEje = :vehiculosEje")
    , @NamedQuery(name = "TablaControl.findByServiciosPrg", query = "SELECT t FROM TablaControl t WHERE t.serviciosPrg = :serviciosPrg")
    , @NamedQuery(name = "TablaControl.findByServiciosEje", query = "SELECT t FROM TablaControl t WHERE t.serviciosEje = :serviciosEje")
    , @NamedQuery(name = "TablaControl.findByUsername", query = "SELECT t FROM TablaControl t WHERE t.username = :username")
    , @NamedQuery(name = "TablaControl.findByCreado", query = "SELECT t FROM TablaControl t WHERE t.creado = :creado")
    , @NamedQuery(name = "TablaControl.findByModificado", query = "SELECT t FROM TablaControl t WHERE t.modificado = :modificado")
    , @NamedQuery(name = "TablaControl.findByEstadoReg", query = "SELECT t FROM TablaControl t WHERE t.estadoReg = :estadoReg")})
public class TablaControl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tabla_control")
    private Integer idTablaControl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "tipo_dia")
    private Character tipoDia;
    @Column(name = "mcom_prg")
    private Integer mcomPrg;
    @Column(name = "mvac_prog")
    private Integer mvacProg;
    @Column(name = "mcom_art_prg")
    private Integer mcomArtPrg;
    @Column(name = "mvac_art_prg")
    private Integer mvacArtPrg;
    @Column(name = "mcom_bi_prg")
    private Integer mcomBiPrg;
    @Column(name = "mvac_bi_prg")
    private Integer mvacBiPrg;
    @Column(name = "mcom_art_con")
    private Integer mcomArtCon;
    @Column(name = "mcom_bi_con")
    private Integer mcomBiCon;
    @Column(name = "vr_mcom_art_con")
    private Integer vrMcomArtCon;
    @Column(name = "vr_mcon_bi_con")
    private Integer vrMconBiCon;
    @Column(name = "operadores_prg")
    private Integer operadoresPrg;
    @Column(name = "operadores_eje")
    private Integer operadoresEje;
    @Column(name = "vehiculos_prg")
    private Integer vehiculosPrg;
    @Column(name = "vehiculos_eje")
    private Integer vehiculosEje;
    @Column(name = "servicios_prg")
    private Integer serviciosPrg;
    @Column(name = "servicios_eje")
    private Integer serviciosEje;
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
    @OneToMany(mappedBy = "idTablaControl", fetch = FetchType.LAZY)
    private List<TablaControlDet> tablaControlDetList;

    public TablaControl() {
    }

    public TablaControl(Integer idTablaControl) {
        this.idTablaControl = idTablaControl;
    }

    public TablaControl(Integer idTablaControl, Date fecha, String username, Date creado, int estadoReg) {
        this.idTablaControl = idTablaControl;
        this.fecha = fecha;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdTablaControl() {
        return idTablaControl;
    }

    public void setIdTablaControl(Integer idTablaControl) {
        this.idTablaControl = idTablaControl;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Character getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(Character tipoDia) {
        this.tipoDia = tipoDia;
    }

    public Integer getMcomPrg() {
        return mcomPrg;
    }

    public void setMcomPrg(Integer mcomPrg) {
        this.mcomPrg = mcomPrg;
    }

    public Integer getMvacProg() {
        return mvacProg;
    }

    public void setMvacProg(Integer mvacProg) {
        this.mvacProg = mvacProg;
    }

    public Integer getMcomArtPrg() {
        return mcomArtPrg;
    }

    public void setMcomArtPrg(Integer mcomArtPrg) {
        this.mcomArtPrg = mcomArtPrg;
    }

    public Integer getMvacArtPrg() {
        return mvacArtPrg;
    }

    public void setMvacArtPrg(Integer mvacArtPrg) {
        this.mvacArtPrg = mvacArtPrg;
    }

    public Integer getMcomBiPrg() {
        return mcomBiPrg;
    }

    public void setMcomBiPrg(Integer mcomBiPrg) {
        this.mcomBiPrg = mcomBiPrg;
    }

    public Integer getMvacBiPrg() {
        return mvacBiPrg;
    }

    public void setMvacBiPrg(Integer mvacBiPrg) {
        this.mvacBiPrg = mvacBiPrg;
    }

    public Integer getMcomArtCon() {
        return mcomArtCon;
    }

    public void setMcomArtCon(Integer mcomArtCon) {
        this.mcomArtCon = mcomArtCon;
    }

    public Integer getMcomBiCon() {
        return mcomBiCon;
    }

    public void setMcomBiCon(Integer mcomBiCon) {
        this.mcomBiCon = mcomBiCon;
    }

    public Integer getVrMcomArtCon() {
        return vrMcomArtCon;
    }

    public void setVrMcomArtCon(Integer vrMcomArtCon) {
        this.vrMcomArtCon = vrMcomArtCon;
    }

    public Integer getVrMconBiCon() {
        return vrMconBiCon;
    }

    public void setVrMconBiCon(Integer vrMconBiCon) {
        this.vrMconBiCon = vrMconBiCon;
    }

    public Integer getOperadoresPrg() {
        return operadoresPrg;
    }

    public void setOperadoresPrg(Integer operadoresPrg) {
        this.operadoresPrg = operadoresPrg;
    }

    public Integer getOperadoresEje() {
        return operadoresEje;
    }

    public void setOperadoresEje(Integer operadoresEje) {
        this.operadoresEje = operadoresEje;
    }

    public Integer getVehiculosPrg() {
        return vehiculosPrg;
    }

    public void setVehiculosPrg(Integer vehiculosPrg) {
        this.vehiculosPrg = vehiculosPrg;
    }

    public Integer getVehiculosEje() {
        return vehiculosEje;
    }

    public void setVehiculosEje(Integer vehiculosEje) {
        this.vehiculosEje = vehiculosEje;
    }

    public Integer getServiciosPrg() {
        return serviciosPrg;
    }

    public void setServiciosPrg(Integer serviciosPrg) {
        this.serviciosPrg = serviciosPrg;
    }

    public Integer getServiciosEje() {
        return serviciosEje;
    }

    public void setServiciosEje(Integer serviciosEje) {
        this.serviciosEje = serviciosEje;
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

    @XmlTransient
    public List<TablaControlDet> getTablaControlDetList() {
        return tablaControlDetList;
    }

    public void setTablaControlDetList(List<TablaControlDet> tablaControlDetList) {
        this.tablaControlDetList = tablaControlDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTablaControl != null ? idTablaControl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaControl)) {
            return false;
        }
        TablaControl other = (TablaControl) object;
        if ((this.idTablaControl == null && other.idTablaControl != null) || (this.idTablaControl != null && !this.idTablaControl.equals(other.idTablaControl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.TablaControl[ idTablaControl=" + idTablaControl + " ]";
    }
    
}
