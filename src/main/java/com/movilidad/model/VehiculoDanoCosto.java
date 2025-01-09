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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "vehiculo_dano_costo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoDanoCosto.findAll", query = "SELECT v FROM VehiculoDanoCosto v"),
    @NamedQuery(name = "VehiculoDanoCosto.findByIdVehiculoDanoCosto", query = "SELECT v FROM VehiculoDanoCosto v WHERE v.idVehiculoDanoCosto = :idVehiculoDanoCosto"),
    @NamedQuery(name = "VehiculoDanoCosto.findByDesde", query = "SELECT v FROM VehiculoDanoCosto v WHERE v.desde = :desde"),
    @NamedQuery(name = "VehiculoDanoCosto.findByHasta", query = "SELECT v FROM VehiculoDanoCosto v WHERE v.hasta = :hasta"),
    @NamedQuery(name = "VehiculoDanoCosto.findByValor", query = "SELECT v FROM VehiculoDanoCosto v WHERE v.valor = :valor"),
    @NamedQuery(name = "VehiculoDanoCosto.findByUsername", query = "SELECT v FROM VehiculoDanoCosto v WHERE v.username = :username"),
    @NamedQuery(name = "VehiculoDanoCosto.findByCreado", query = "SELECT v FROM VehiculoDanoCosto v WHERE v.creado = :creado"),
    @NamedQuery(name = "VehiculoDanoCosto.findByModificado", query = "SELECT v FROM VehiculoDanoCosto v WHERE v.modificado = :modificado"),
    @NamedQuery(name = "VehiculoDanoCosto.findByEstadoReg", query = "SELECT v FROM VehiculoDanoCosto v WHERE v.estadoReg = :estadoReg")})
public class VehiculoDanoCosto implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculoDanoCosto", fetch = FetchType.LAZY)
    private List<NovedadDanoLiqDet> novedadDanoLiqDetList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_dano_costo")
    private Integer idVehiculoDanoCosto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private int valor;
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
    @JoinColumn(name = "id_vehiculo_dano_severidad", referencedColumnName = "id_vehiculo_dano_severidad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoDanoSeveridad idVehiculoDanoSeveridad;

    public VehiculoDanoCosto() {
    }

    public VehiculoDanoCosto(Integer idVehiculoDanoCosto) {
        this.idVehiculoDanoCosto = idVehiculoDanoCosto;
    }

    public VehiculoDanoCosto(Integer idVehiculoDanoCosto, Date desde, Date hasta, int valor) {
        this.idVehiculoDanoCosto = idVehiculoDanoCosto;
        this.desde = desde;
        this.hasta = hasta;
        this.valor = valor;
    }

    public Integer getIdVehiculoDanoCosto() {
        return idVehiculoDanoCosto;
    }

    public void setIdVehiculoDanoCosto(Integer idVehiculoDanoCosto) {
        this.idVehiculoDanoCosto = idVehiculoDanoCosto;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
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

    public VehiculoDanoSeveridad getIdVehiculoDanoSeveridad() {
        return idVehiculoDanoSeveridad;
    }

    public void setIdVehiculoDanoSeveridad(VehiculoDanoSeveridad idVehiculoDanoSeveridad) {
        this.idVehiculoDanoSeveridad = idVehiculoDanoSeveridad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoDanoCosto != null ? idVehiculoDanoCosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoDanoCosto)) {
            return false;
        }
        VehiculoDanoCosto other = (VehiculoDanoCosto) object;
        if ((this.idVehiculoDanoCosto == null && other.idVehiculoDanoCosto != null) || (this.idVehiculoDanoCosto != null && !this.idVehiculoDanoCosto.equals(other.idVehiculoDanoCosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoDanoCosto[ idVehiculoDanoCosto=" + idVehiculoDanoCosto + " ]";
    }

    @XmlTransient
    public List<NovedadDanoLiqDet> getNovedadDanoLiqDetList() {
        return novedadDanoLiqDetList;
    }

    public void setNovedadDanoLiqDetList(List<NovedadDanoLiqDet> novedadDanoLiqDetList) {
        this.novedadDanoLiqDetList = novedadDanoLiqDetList;
    }
    
}
