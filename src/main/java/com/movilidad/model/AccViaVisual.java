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
import javax.persistence.Lob;
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
 * @author HP
 */
@Entity
@Table(name = "acc_via_visual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccViaVisual.findAll", query = "SELECT a FROM AccViaVisual a")
    , @NamedQuery(name = "AccViaVisual.findByIdAccViaVisual", query = "SELECT a FROM AccViaVisual a WHERE a.idAccViaVisual = :idAccViaVisual")
    , @NamedQuery(name = "AccViaVisual.findByViaVisual", query = "SELECT a FROM AccViaVisual a WHERE a.viaVisual = :viaVisual")
    , @NamedQuery(name = "AccViaVisual.findByUsername", query = "SELECT a FROM AccViaVisual a WHERE a.username = :username")
    , @NamedQuery(name = "AccViaVisual.findByCreado", query = "SELECT a FROM AccViaVisual a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccViaVisual.findByModificado", query = "SELECT a FROM AccViaVisual a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccViaVisual.findByEstadoReg", query = "SELECT a FROM AccViaVisual a WHERE a.estadoReg = :estadoReg")})
public class AccViaVisual implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_via_visual")
    private Integer idAccViaVisual;
    @Size(max = 60)
    @Column(name = "via_visual")
    private String viaVisual;
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
    @OneToMany(mappedBy = "idAccViaVisual", fetch = FetchType.LAZY)
    private List<AccVisualInforme> accVisualInformeList;

    public AccViaVisual() {
    }

    public AccViaVisual(Integer idAccViaVisual) {
        this.idAccViaVisual = idAccViaVisual;
    }

    public Integer getIdAccViaVisual() {
        return idAccViaVisual;
    }

    public void setIdAccViaVisual(Integer idAccViaVisual) {
        this.idAccViaVisual = idAccViaVisual;
    }

    public String getViaVisual() {
        return viaVisual;
    }

    public void setViaVisual(String viaVisual) {
        this.viaVisual = viaVisual;
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

    @XmlTransient
    public List<AccVisualInforme> getAccVisualInformeList() {
        return accVisualInformeList;
    }

    public void setAccVisualInformeList(List<AccVisualInforme> accVisualInformeList) {
        this.accVisualInformeList = accVisualInformeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccViaVisual != null ? idAccViaVisual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccViaVisual)) {
            return false;
        }
        AccViaVisual other = (AccViaVisual) object;
        if ((this.idAccViaVisual == null && other.idAccViaVisual != null) || (this.idAccViaVisual != null && !this.idAccViaVisual.equals(other.idAccViaVisual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccViaVisual[ idAccViaVisual=" + idAccViaVisual + " ]";
    }
    
}
