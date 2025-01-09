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
 a 'PlaRecuRuta' del módulo 'Planificación de recursos'
 * @author Omar Beltrán
 */
@Entity
@Table(name = "pla_recu_ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuRuta.findAll", query = "SELECT i FROM PlaRecuRuta i"),
    @NamedQuery(name = "PlaRecuRuta.findByIdRuta", query = "SELECT i FROM PlaRecuRuta i WHERE i.idPlaRecuRuta = :idPlaRecuRuta"),
    @NamedQuery(name = "PlaRecuRuta.findByRuta", query = "SELECT i FROM PlaRecuRuta i WHERE i.ruta = :ruta"),
    @NamedQuery(name = "PlaRecuRuta.findByCreado", query = "SELECT i FROM PlaRecuRuta i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuRuta.findByModificado", query = "SELECT i FROM PlaRecuRuta i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuRuta.findByEstadoReg", query = "SELECT i FROM PlaRecuRuta i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuRuta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_ruta")
    private Integer idPlaRecuRuta;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "ruta")
    private String ruta;
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
    
    public PlaRecuRuta() {
    }

    public Integer getIdPlaRecuRuta() {
        return idPlaRecuRuta;
    }

    public void setIdPlaRecuRuta(Integer idPlaRecuRuta) {
        this.idPlaRecuRuta = idPlaRecuRuta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
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
        hash += (idPlaRecuRuta != null ? idPlaRecuRuta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuRuta)) {
            return false;
        }
        PlaRecuRuta other = (PlaRecuRuta) object;
        return !((this.idPlaRecuRuta == null && other.idPlaRecuRuta != null) || (this.idPlaRecuRuta != null && 
                !this.idPlaRecuRuta.equals(other.idPlaRecuRuta)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.Ruta[ idPlaRecuRuta=" + idPlaRecuRuta + " ]";
    }
    
}
