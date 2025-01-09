/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.dto.DocumentosPdDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
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
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
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
@Table(name = "pd_maestro_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdMaestroSeguimiento.findAll", query = "SELECT p FROM PdMaestroSeguimiento p"),
    @NamedQuery(name = "PdMaestroSeguimiento.findByIdPdMaestroSeguimiento", query = "SELECT p FROM PdMaestroSeguimiento p WHERE p.idPdMaestroSeguimiento = :idPdMaestroSeguimiento"),
    @NamedQuery(name = "PdMaestroSeguimiento.findByFecha", query = "SELECT p FROM PdMaestroSeguimiento p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "PdMaestroSeguimiento.findByPath", query = "SELECT p FROM PdMaestroSeguimiento p WHERE p.path = :path"),
    @NamedQuery(name = "PdMaestroSeguimiento.findByUsername", query = "SELECT p FROM PdMaestroSeguimiento p WHERE p.username = :username"),
    @NamedQuery(name = "PdMaestroSeguimiento.findByCreado", query = "SELECT p FROM PdMaestroSeguimiento p WHERE p.creado = :creado"),
    @NamedQuery(name = "PdMaestroSeguimiento.findByModificado", query = "SELECT p FROM PdMaestroSeguimiento p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PdMaestroSeguimiento.findByEstadoReg", query = "SELECT p FROM PdMaestroSeguimiento p WHERE p.estadoReg = :estadoReg")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "DocumentosDTOMapping",
            classes = {
                @ConstructorResult(targetClass = DocumentosPdDTO.class,
                        columns = {
                            @ColumnResult(name = "id_pd_maestro_seguimiento", type = Integer.class),
                            @ColumnResult(name = "id_pd_maestro", type = Integer.class),
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "seguimiento", type = String.class),
                            @ColumnResult(name = "path", type = String.class),
                            @ColumnResult(name = "username", type = String.class),
                            @ColumnResult(name = "creado", type = Date.class),
                            @ColumnResult(name = "modificado", type = Date.class),
                            @ColumnResult(name = "estado_reg", type = Integer.class),
                            @ColumnResult(name = "documento_novedad", type = String.class),
                        }
                )
            }),})
public class PdMaestroSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pd_maestro_seguimiento")
    private Integer idPdMaestroSeguimiento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "seguimiento")
    private String seguimiento;
    @Size(max = 255)
    @Column(name = "path")
    private String path;
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
    @JoinColumn(name = "id_pd_maestro", referencedColumnName = "id_pd_maestro")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PdMaestro idPdMaestro;

    public PdMaestroSeguimiento() {
    }

    public PdMaestroSeguimiento(Integer idPdMaestroSeguimiento) {
        this.idPdMaestroSeguimiento = idPdMaestroSeguimiento;
    }

    public PdMaestroSeguimiento(Integer idPdMaestroSeguimiento, String seguimiento) {
        this.idPdMaestroSeguimiento = idPdMaestroSeguimiento;
        this.seguimiento = seguimiento;
    }

    public Integer getIdPdMaestroSeguimiento() {
        return idPdMaestroSeguimiento;
    }

    public void setIdPdMaestroSeguimiento(Integer idPdMaestroSeguimiento) {
        this.idPdMaestroSeguimiento = idPdMaestroSeguimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public PdMaestro getIdPdMaestro() {
        return idPdMaestro;
    }

    public void setIdPdMaestro(PdMaestro idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPdMaestroSeguimiento != null ? idPdMaestroSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdMaestroSeguimiento)) {
            return false;
        }
        PdMaestroSeguimiento other = (PdMaestroSeguimiento) object;
        if ((this.idPdMaestroSeguimiento == null && other.idPdMaestroSeguimiento != null) || (this.idPdMaestroSeguimiento != null && !this.idPdMaestroSeguimiento.equals(other.idPdMaestroSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PdMaestroSeguimiento[ idPdMaestroSeguimiento=" + idPdMaestroSeguimiento + " ]";
    }
    
}
