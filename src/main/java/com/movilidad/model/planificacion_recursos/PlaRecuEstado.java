package com.movilidad.model.planificacion_recursos;

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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que permite la iteracción del sistema con la tabla pla_recu_lugar que corresponde 
 a 'PlaRecuEstado' del módulo 'Planificación de recursos'
 * @author Omar Beltrán
 */
@Entity
@Table(name = "pla_recu_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuEstado.findAll", query = "SELECT i FROM PlaRecuEstado i"),
    @NamedQuery(name = "PlaRecuEstado.findByIdLugar", query = "SELECT i FROM PlaRecuEstado i WHERE i.idPlaRecuEstado = :idPlaRecuEstado"),
    @NamedQuery(name = "PlaRecuEstado.findByDescripcion", query = "SELECT i FROM PlaRecuEstado i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "PlaRecuEstado.findByEstado", query = "SELECT i FROM PlaRecuEstado i WHERE i.estado = :estado"),
    @NamedQuery(name = "PlaRecuEstado.findByCreado", query = "SELECT i FROM PlaRecuEstado i WHERE i.usernameCreate = :usernameCreate"),
    @NamedQuery(name = "PlaRecuEstado.findByModificado", query = "SELECT i FROM PlaRecuEstado i WHERE i.usernameEdit = :usernameEdit"),
    @NamedQuery(name = "PlaRecuEstado.findByEstadoReg", query = "SELECT i FROM PlaRecuEstado i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuEstado implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_estado")
    private Integer idPlaRecuEstado;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "estado")
    private String estado;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_pla_recu_proceso", referencedColumnName = "id_pla_recu_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaRecuProceso idPlaRecuProceso;
    @Size(max = 15)
    @Column(name = "username_create")
    private String usernameCreate;
    @Size(max = 15)
    @Column(name = "username_edit")
    private String usernameEdit;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    
    public PlaRecuEstado() {
    }

    public Integer getIdPlaRecuEstado() {
        return idPlaRecuEstado;
    }

    public void setIdPlaRecuEstado(Integer idEstado) {
        this.idPlaRecuEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsernameCreate() {
        return usernameCreate;
    }

    public void setUsernameCreate(String usernameCreate) {
        this.usernameCreate = usernameCreate;
    }

    public String getUsernameEdit() {
        return usernameEdit;
    }

    public void setUsernameEdit(String usernameEdit) {
        this.usernameEdit = usernameEdit;
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

    public PlaRecuProceso getIdPlaRecuProceso() {
        return idPlaRecuProceso;
    }

    public void setIdPlaRecuProceso(PlaRecuProceso idPlaRecuProceso) {
        this.idPlaRecuProceso = idPlaRecuProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaRecuEstado != null ? idPlaRecuEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuEstado)) {
            return false;
        }
        PlaRecuEstado other = (PlaRecuEstado) object;
        return !((this.idPlaRecuEstado == null && other.idPlaRecuEstado != null) || (this.idPlaRecuEstado != null && 
                !this.idPlaRecuEstado.equals(other.idPlaRecuEstado)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuEstado[ idPlaRecuEstado=" + idPlaRecuEstado + " ]";
    }
    
}