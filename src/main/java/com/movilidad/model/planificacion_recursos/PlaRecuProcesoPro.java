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
 * Clase que permite la iteracción del sistema con la tabla pla_recu_proceso_pro que corresponde 
 a 'PlaRecuLugar' del módulo 'Planificación de recursos'
 * @author Luis Lancheros
 */
@Entity
@Table(name = "pla_recu_proceso_pro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuProcesoPro.findAll", query = "SELECT i FROM PlaRecuProcesoPro i"),
    @NamedQuery(name = "PlaRecuProcesoPro.findByIdProcesoPro", query = "SELECT i FROM PlaRecuProcesoPro i WHERE i.idPlaRecuProcesoPro = :idPlaRecuProcesoPro"),
    @NamedQuery(name = "PlaRecuProcesoPro.findByDescripcion", query = "SELECT i FROM PlaRecuProcesoPro i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "PlaRecuProcesoPro.findByCreado", query = "SELECT i FROM PlaRecuProcesoPro i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuProcesoPro.findByModificado", query = "SELECT i FROM PlaRecuProcesoPro i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuProcesoPro.findByEstadoReg", query = "SELECT i FROM PlaRecuProcesoPro i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuProcesoPro implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_proceso_pro")
    private Integer idPlaRecuProcesoPro;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
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
    
    public PlaRecuProcesoPro() {
    }

    public Integer getIdPlaRecuProcesoPro() {
        return idPlaRecuProcesoPro;
    }

    public void setIdPlaRecuProcesoPro(Integer idPlaRecuProcesoPro) {
        this.idPlaRecuProcesoPro = idPlaRecuProcesoPro;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaRecuProcesoPro != null ? idPlaRecuProcesoPro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuProcesoPro)) {
            return false;
        }
        PlaRecuProcesoPro other = (PlaRecuProcesoPro) object;
        return !((this.idPlaRecuProcesoPro == null && other.idPlaRecuProcesoPro != null) || (this.idPlaRecuProcesoPro != null && 
                !this.idPlaRecuProcesoPro.equals(other.idPlaRecuProcesoPro)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.Lugares[ idLugar=" + idPlaRecuProcesoPro + " ]";
    }
    
}