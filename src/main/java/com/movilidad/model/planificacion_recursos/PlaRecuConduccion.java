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
 * Clase que permite la iteracción del sistema con la tabla pla_recu_motivo que corresponde 
 a 'PlaRecuConduccion' del módulo 'Planificación de recursos'
 * @author Luis Miguel Lancheros
 */
@Entity
@Table(name = "pla_recu_conduccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuConduccion.findAll", query = "SELECT i FROM PlaRecuConduccion i"),
    @NamedQuery(name = "PlaRecuConduccion.findByIdConduccion", query = "SELECT i FROM PlaRecuConduccion i WHERE i.idPlaRecuConduccion = :idPlaRecuConduccion"),
    @NamedQuery(name = "PlaRecuConduccion.findByDescripcion", query = "SELECT i FROM PlaRecuConduccion i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "PlaRecuConduccion.findByCreado", query = "SELECT i FROM PlaRecuConduccion i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuConduccion.findByModificado", query = "SELECT i FROM PlaRecuConduccion i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuConduccion.findByEstadoReg", query = "SELECT i FROM PlaRecuConduccion i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuConduccion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_conduccion")
    private Integer idPlaRecuConduccion;
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
    
    public PlaRecuConduccion() {
    }

    public Integer getIdPlaRecuConduccion() {
        return idPlaRecuConduccion;
    }

    public void setIdPlaRecuConduccion(Integer idPlaRecuConduccion) {
        this.idPlaRecuConduccion = idPlaRecuConduccion;
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
        hash += (idPlaRecuConduccion != null ? idPlaRecuConduccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuConduccion)) {
            return false;
        }
        PlaRecuConduccion other = (PlaRecuConduccion) object;
        return !((this.idPlaRecuConduccion == null && other.idPlaRecuConduccion != null) || (this.idPlaRecuConduccion != null && 
                !this.idPlaRecuConduccion.equals(other.idPlaRecuConduccion)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.Conduccion[ idPlaRecuConduccion=" + idPlaRecuConduccion + " ]";
    }
    
}
