/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "acc_checklist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccChecklist.findAll", query = "SELECT a FROM AccChecklist a"),
    @NamedQuery(name = "AccChecklist.findByIdAccChecklist", query = "SELECT a FROM AccChecklist a WHERE a.idAccChecklist = :idAccChecklist"),
    @NamedQuery(name = "AccChecklist.findByRequerido", query = "SELECT a FROM AccChecklist a WHERE a.requerido = :requerido"),
    @NamedQuery(name = "AccChecklist.findByUsername", query = "SELECT a FROM AccChecklist a WHERE a.username = :username"),
    @NamedQuery(name = "AccChecklist.findByCreado", query = "SELECT a FROM AccChecklist a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccChecklist.findByModificado", query = "SELECT a FROM AccChecklist a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccChecklist.findByEstadoReg", query = "SELECT a FROM AccChecklist a WHERE a.estadoReg = :estadoReg")})
public class AccChecklist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_checklist")
    private Integer idAccChecklist;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requerido")
    private int requerido;
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
    @JoinColumn(name = "id_acc_tipo_documento", referencedColumnName = "id_acc_tipo_docs")
    @ManyToOne(optional = false)
    private AccTipoDocs idAccTipoDocumento;
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(optional = false)
    private NovedadTipoDetalles idNovedadTipoDetalle;

    public AccChecklist() {
    }

    public AccChecklist(Integer idAccChecklist) {
        this.idAccChecklist = idAccChecklist;
    }

    public AccChecklist(Integer idAccChecklist, int requerido) {
        this.idAccChecklist = idAccChecklist;
        this.requerido = requerido;
    }

    public Integer getIdAccChecklist() {
        return idAccChecklist;
    }

    public void setIdAccChecklist(Integer idAccChecklist) {
        this.idAccChecklist = idAccChecklist;
    }

    public int getRequerido() {
        return requerido;
    }

    public void setRequerido(int requerido) {
        this.requerido = requerido;
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

    public AccTipoDocs getIdAccTipoDocumento() {
        return idAccTipoDocumento;
    }

    public void setIdAccTipoDocumento(AccTipoDocs idAccTipoDocumento) {
        this.idAccTipoDocumento = idAccTipoDocumento;
    }

    public NovedadTipoDetalles getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(NovedadTipoDetalles idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccChecklist != null ? idAccChecklist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccChecklist)) {
            return false;
        }
        AccChecklist other = (AccChecklist) object;
        if ((this.idAccChecklist == null && other.idAccChecklist != null) || (this.idAccChecklist != null && !this.idAccChecklist.equals(other.idAccChecklist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccChecklist[ idAccChecklist=" + idAccChecklist + " ]";
    }
    
}
