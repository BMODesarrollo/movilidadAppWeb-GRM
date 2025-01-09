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
import javax.persistence.Lob;
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
@Table(name = "chk_componente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChkComponente.findAll", query = "SELECT c FROM ChkComponente c"),
    @NamedQuery(name = "ChkComponente.findByIdChkComponente", query = "SELECT c FROM ChkComponente c WHERE c.idChkComponente = :idChkComponente"),
    @NamedQuery(name = "ChkComponente.findByNombre", query = "SELECT c FROM ChkComponente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ChkComponente.findByLista", query = "SELECT c FROM ChkComponente c WHERE c.lista = :lista"),
    @NamedQuery(name = "ChkComponente.findByTextoLibre", query = "SELECT c FROM ChkComponente c WHERE c.textoLibre = :textoLibre"),
    @NamedQuery(name = "ChkComponente.findByUsername", query = "SELECT c FROM ChkComponente c WHERE c.username = :username"),
    @NamedQuery(name = "ChkComponente.findByCreado", query = "SELECT c FROM ChkComponente c WHERE c.creado = :creado"),
    @NamedQuery(name = "ChkComponente.findByModificado", query = "SELECT c FROM ChkComponente c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "ChkComponente.findByEstadoReg", query = "SELECT c FROM ChkComponente c WHERE c.estadoReg = :estadoReg")})
public class ChkComponente implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idChkComponente")
    private List<ChkComponenteFalla> chkComponenteFallaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chk_componente")
    private Integer idChkComponente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "lista")
    private Integer lista;
    @Column(name = "texto_libre")
    private Integer textoLibre;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_notificacion_proceso", referencedColumnName = "id_notificacion_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotificacionProcesos idNotificacionProceso;

    public ChkComponente() {
    }

    public ChkComponente(Integer idChkComponente) {
        this.idChkComponente = idChkComponente;
    }

    public ChkComponente(Integer idChkComponente, String nombre) {
        this.idChkComponente = idChkComponente;
        this.nombre = nombre;
    }

    public Integer getIdChkComponente() {
        return idChkComponente;
    }

    public void setIdChkComponente(Integer idChkComponente) {
        this.idChkComponente = idChkComponente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getLista() {
        return lista;
    }

    public void setLista(Integer lista) {
        this.lista = lista;
    }

    public Integer getTextoLibre() {
        return textoLibre;
    }

    public void setTextoLibre(Integer textoLibre) {
        this.textoLibre = textoLibre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
    public NotificacionProcesos getIdNotificacionProceso() {
        return idNotificacionProceso;
    }

    public void setIdNotificacionProceso(NotificacionProcesos idNotificacionProceso) {
        this.idNotificacionProceso = idNotificacionProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChkComponente != null ? idChkComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChkComponente)) {
            return false;
        }
        ChkComponente other = (ChkComponente) object;
        if ((this.idChkComponente == null && other.idChkComponente != null) || (this.idChkComponente != null && !this.idChkComponente.equals(other.idChkComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ChkComponente[ idChkComponente=" + idChkComponente + " ]";
    }

    @XmlTransient
    public List<ChkComponenteFalla> getChkComponenteFallaList() {
        return chkComponenteFallaList;
    }

    public void setChkComponenteFallaList(List<ChkComponenteFalla> chkComponenteFallaList) {
        this.chkComponenteFallaList = chkComponenteFallaList;
    }
    
}
