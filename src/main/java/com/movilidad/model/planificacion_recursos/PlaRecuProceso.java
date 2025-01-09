package com.movilidad.model.planificacion_recursos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * Clase que permite la iteracción del sistema con la tabla pla_recu_proceso que corresponde 
 * a la entidad 'PlaRecuProceso' del módulo 'Planificación de recursos'
 * @author Omar Beltrán
 */
@Entity
@Table(name = "pla_recu_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuProceso.findAll", query = "SELECT i FROM PlaRecuProceso i"),
    @NamedQuery(name = "PlaRecuProceso.findByIdProceso", query = "SELECT i FROM PlaRecuProceso i WHERE i.idPlaRecuProceso = :idPlaRecuProceso"),
    @NamedQuery(name = "PlaRecuProceso.findByDescripcion", query = "SELECT i FROM PlaRecuProceso i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "PlaRecuProceso.findByName", query = "SELECT i FROM PlaRecuProceso i WHERE i.proceso = :proceso"),
    @NamedQuery(name = "PlaRecuProceso.findByCreado", query = "SELECT i FROM PlaRecuProceso i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuProceso.findByModificado", query = "SELECT i FROM PlaRecuProceso i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuProceso.findByEstadoReg", query = "SELECT i FROM PlaRecuProceso i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuProceso implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_proceso")
    private Integer idPlaRecuProceso;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "proceso")
    private String proceso;
    @Column(name = "descripcion")
    private String descripcion;
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
    
    public PlaRecuProceso() {
    }

    public Integer getIdPlaRecuProceso() {
        return idPlaRecuProceso;
    }

    public void setIdPlaRecuProceso(Integer idPlaRecuProceso) {
        this.idPlaRecuProceso = idPlaRecuProceso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaRecuProceso != null ? idPlaRecuProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuProceso)) {
            return false;
        }
        PlaRecuProceso other = (PlaRecuProceso) object;
        return !((this.idPlaRecuProceso == null && other.idPlaRecuProceso != null) || (this.idPlaRecuProceso != null && 
                !this.idPlaRecuProceso.equals(other.idPlaRecuProceso)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.PlaRecuProceso[ idPlaRecuProceso=" + idPlaRecuProceso + " ]";
    }
    
}
