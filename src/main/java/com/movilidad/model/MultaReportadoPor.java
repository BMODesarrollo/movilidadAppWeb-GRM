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
@Table(name = "multa_reportado_por")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MultaReportadoPor.findAll", query = "SELECT m FROM MultaReportadoPor m")
    , @NamedQuery(name = "MultaReportadoPor.findByIdMultaReportadoPor", query = "SELECT m FROM MultaReportadoPor m WHERE m.idMultaReportadoPor = :idMultaReportadoPor")
    , @NamedQuery(name = "MultaReportadoPor.findByNombres", query = "SELECT m FROM MultaReportadoPor m WHERE m.nombres = :nombres")
    , @NamedQuery(name = "MultaReportadoPor.findByUsername", query = "SELECT m FROM MultaReportadoPor m WHERE m.username = :username")
    , @NamedQuery(name = "MultaReportadoPor.findByCreado", query = "SELECT m FROM MultaReportadoPor m WHERE m.creado = :creado")
    , @NamedQuery(name = "MultaReportadoPor.findByModificado", query = "SELECT m FROM MultaReportadoPor m WHERE m.modificado = :modificado")
    , @NamedQuery(name = "MultaReportadoPor.findByEstadoReg", query = "SELECT m FROM MultaReportadoPor m WHERE m.estadoReg = :estadoReg")})
public class MultaReportadoPor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa_reportado_por")
    private Integer idMultaReportadoPor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "nombres")
    private String nombres;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMultaReportadoPor", fetch = FetchType.LAZY)
    private List<Multa> multaList;

    public MultaReportadoPor() {
    }

    public MultaReportadoPor(Integer idMultaReportadoPor) {
        this.idMultaReportadoPor = idMultaReportadoPor;
    }

    public MultaReportadoPor(Integer idMultaReportadoPor, String nombres, String username, Date creado, int estadoReg) {
        this.idMultaReportadoPor = idMultaReportadoPor;
        this.nombres = nombres;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMultaReportadoPor() {
        return idMultaReportadoPor;
    }

    public void setIdMultaReportadoPor(Integer idMultaReportadoPor) {
        this.idMultaReportadoPor = idMultaReportadoPor;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
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
    public List<Multa> getMultaList() {
        return multaList;
    }

    public void setMultaList(List<Multa> multaList) {
        this.multaList = multaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultaReportadoPor != null ? idMultaReportadoPor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MultaReportadoPor)) {
            return false;
        }
        MultaReportadoPor other = (MultaReportadoPor) object;
        if ((this.idMultaReportadoPor == null && other.idMultaReportadoPor != null) || (this.idMultaReportadoPor != null && !this.idMultaReportadoPor.equals(other.idMultaReportadoPor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MultaReportadoPor[ idMultaReportadoPor=" + idMultaReportadoPor + " ]";
    }
    
}
