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
 * Clase que permite la iteracción del sistema con la tabla pla_recu_lugar que corresponde 
 a 'PlaRecuLugar' del módulo 'Planificación de recursos'
 * @author Omar Beltrán
 */
@Entity
@Table(name = "pla_recu_lugar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuLugar.findAll", query = "SELECT i FROM PlaRecuLugar i"),
    @NamedQuery(name = "PlaRecuLugar.findByIdLugar", query = "SELECT i FROM PlaRecuLugar i WHERE i.idLugar = :idLugar"),
    @NamedQuery(name = "PlaRecuLugar.findByDescripcion", query = "SELECT i FROM PlaRecuLugar i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "PlaRecuLugar.findByLugar", query = "SELECT i FROM PlaRecuLugar i WHERE i.lugar = :lugar"),
    @NamedQuery(name = "PlaRecuLugar.findByCreado", query = "SELECT i FROM PlaRecuLugar i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuLugar.findByModificado", query = "SELECT i FROM PlaRecuLugar i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuLugar.findByEstadoReg", query = "SELECT i FROM PlaRecuLugar i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuLugar implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_lugar")
    private Integer idLugar;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "lugar")
    private String lugar;
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
    
    public PlaRecuLugar() {
    }

    public Integer getIdPlaRecuLugar() {
        return idLugar;
    }

    public void setIdPlaRecuLugar(Integer idLugar) {
        this.idLugar = idLugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLugar != null ? idLugar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuLugar)) {
            return false;
        }
        PlaRecuLugar other = (PlaRecuLugar) object;
        return !((this.idLugar == null && other.idLugar != null) || (this.idLugar != null && 
                !this.idLugar.equals(other.idLugar)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.Lugares[ idLugar=" + idLugar + " ]";
    }
    
}