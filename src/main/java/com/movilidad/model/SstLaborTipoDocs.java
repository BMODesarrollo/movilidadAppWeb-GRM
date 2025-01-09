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
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "sst_labor_tipo_docs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstLaborTipoDocs.findAll", query = "SELECT s FROM SstLaborTipoDocs s"),
    @NamedQuery(name = "SstLaborTipoDocs.findByIdSstLaborTipoDocs", query = "SELECT s FROM SstLaborTipoDocs s WHERE s.idSstLaborTipoDocs = :idSstLaborTipoDocs"),
    @NamedQuery(name = "SstLaborTipoDocs.findByUsername", query = "SELECT s FROM SstLaborTipoDocs s WHERE s.username = :username"),
    @NamedQuery(name = "SstLaborTipoDocs.findByCreado", query = "SELECT s FROM SstLaborTipoDocs s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstLaborTipoDocs.findByModificado", query = "SELECT s FROM SstLaborTipoDocs s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstLaborTipoDocs.findByEstadoReg", query = "SELECT s FROM SstLaborTipoDocs s WHERE s.estadoReg = :estadoReg")})
public class SstLaborTipoDocs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_labor_tipo_docs")
    private Integer idSstLaborTipoDocs;
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
    @JoinColumn(name = "id_tipo_doc_tercero", referencedColumnName = "id_sst_documento_tercero")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstDocumentoTercero idTipoDocTercero;
    @JoinColumn(name = "id_tipo_labor", referencedColumnName = "id_sst_tipo_labor")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstTipoLabor idTipoLabor;

    public SstLaborTipoDocs() {
    }

    public SstLaborTipoDocs(Integer idSstLaborTipoDocs) {
        this.idSstLaborTipoDocs = idSstLaborTipoDocs;
    }

    public Integer getIdSstLaborTipoDocs() {
        return idSstLaborTipoDocs;
    }

    public void setIdSstLaborTipoDocs(Integer idSstLaborTipoDocs) {
        this.idSstLaborTipoDocs = idSstLaborTipoDocs;
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

    public SstDocumentoTercero getIdTipoDocTercero() {
        return idTipoDocTercero;
    }

    public void setIdTipoDocTercero(SstDocumentoTercero idTipoDocTercero) {
        this.idTipoDocTercero = idTipoDocTercero;
    }

    public SstTipoLabor getIdTipoLabor() {
        return idTipoLabor;
    }

    public void setIdTipoLabor(SstTipoLabor idTipoLabor) {
        this.idTipoLabor = idTipoLabor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstLaborTipoDocs != null ? idSstLaborTipoDocs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstLaborTipoDocs)) {
            return false;
        }
        SstLaborTipoDocs other = (SstLaborTipoDocs) object;
        if ((this.idSstLaborTipoDocs == null && other.idSstLaborTipoDocs != null) || (this.idSstLaborTipoDocs != null && !this.idSstLaborTipoDocs.equals(other.idSstLaborTipoDocs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstLaborTipoDocs[ idSstLaborTipoDocs=" + idSstLaborTipoDocs + " ]";
    }
    
}
