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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "seg_registro_armamento_doc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegRegistroArmamentoDoc.findAll", query = "SELECT s FROM SegRegistroArmamentoDoc s"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByIdSegRegistroArmamentoDoc", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.idSegRegistroArmamentoDoc = :idSegRegistroArmamentoDoc"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByPathDocumento", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.pathDocumento = :pathDocumento"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByVigenteDesde", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.vigenteDesde = :vigenteDesde"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByVigenteHasta", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.vigenteHasta = :vigenteHasta"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByNumeroDoc", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.numeroDoc = :numeroDoc"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByActivo", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.activo = :activo"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByUsername", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.username = :username"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByCreado", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.creado = :creado"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByModificado", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SegRegistroArmamentoDoc.findByEstadoReg", query = "SELECT s FROM SegRegistroArmamentoDoc s WHERE s.estadoReg = :estadoReg")})
public class SegRegistroArmamentoDoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_registro_armamento_doc")
    private Integer idSegRegistroArmamentoDoc;
    @Size(max = 100)
    @Column(name = "path_documento")
    private String pathDocumento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vigente_desde")
    @Temporal(TemporalType.DATE)
    private Date vigenteDesde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vigente_hasta")
    @Temporal(TemporalType.DATE)
    private Date vigenteHasta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numero_doc")
    private String numeroDoc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private int activo;
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
    @JoinColumn(name = "id_seg_registro_armamento", referencedColumnName = "id_seg_registro_armamento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SegRegistroArmamento idSegRegistroArmamento;

    public SegRegistroArmamentoDoc() {
    }

    public SegRegistroArmamentoDoc(Integer idSegRegistroArmamentoDoc) {
        this.idSegRegistroArmamentoDoc = idSegRegistroArmamentoDoc;
    }

    public SegRegistroArmamentoDoc(Integer idSegRegistroArmamentoDoc, Date vigenteDesde, Date vigenteHasta, String numeroDoc, int activo, String username, Date creado, int estadoReg) {
        this.idSegRegistroArmamentoDoc = idSegRegistroArmamentoDoc;
        this.vigenteDesde = vigenteDesde;
        this.vigenteHasta = vigenteHasta;
        this.numeroDoc = numeroDoc;
        this.activo = activo;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSegRegistroArmamentoDoc() {
        return idSegRegistroArmamentoDoc;
    }

    public void setIdSegRegistroArmamentoDoc(Integer idSegRegistroArmamentoDoc) {
        this.idSegRegistroArmamentoDoc = idSegRegistroArmamentoDoc;
    }

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    public Date getVigenteDesde() {
        return vigenteDesde;
    }

    public void setVigenteDesde(Date vigenteDesde) {
        this.vigenteDesde = vigenteDesde;
    }

    public Date getVigenteHasta() {
        return vigenteHasta;
    }

    public void setVigenteHasta(Date vigenteHasta) {
        this.vigenteHasta = vigenteHasta;
    }

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
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

    public SegRegistroArmamento getIdSegRegistroArmamento() {
        return idSegRegistroArmamento;
    }

    public void setIdSegRegistroArmamento(SegRegistroArmamento idSegRegistroArmamento) {
        this.idSegRegistroArmamento = idSegRegistroArmamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegRegistroArmamentoDoc != null ? idSegRegistroArmamentoDoc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegRegistroArmamentoDoc)) {
            return false;
        }
        SegRegistroArmamentoDoc other = (SegRegistroArmamentoDoc) object;
        if ((this.idSegRegistroArmamentoDoc == null && other.idSegRegistroArmamentoDoc != null) || (this.idSegRegistroArmamentoDoc != null && !this.idSegRegistroArmamentoDoc.equals(other.idSegRegistroArmamentoDoc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegRegistroArmamentoDoc[ idSegRegistroArmamentoDoc=" + idSegRegistroArmamentoDoc + " ]";
    }
    
}
