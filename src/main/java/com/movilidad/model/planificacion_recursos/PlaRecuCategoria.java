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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Omar.beltran
 */
@Entity
@Table(name = "pla_recu_categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuCategoria.findAll", query = "SELECT a FROM PlaRecuCategoria a")
    , @NamedQuery(name = "PlaRecuCategoria.findByIdCategoria", query = "SELECT a FROM PlaRecuCategoria a WHERE a.idPlaRecuCategoria = :idPlaRecuCategoria")
    , @NamedQuery(name = "PlaRecuCategoria.findByCategoriaName", query = "SELECT a FROM PlaRecuCategoria a WHERE a.name = :name")
    , @NamedQuery(name = "PlaRecuCategoria.findByUserCreate", query = "SELECT a FROM PlaRecuCategoria a WHERE a.usernameCreate = :usernameCreate")
    , @NamedQuery(name = "PlaRecuCategoria.findByUserEdit", query = "SELECT a FROM PlaRecuCategoria a WHERE a.usernameEdit = :usernameEdit")
    , @NamedQuery(name = "PlaRecuCategoria.findByCreado", query = "SELECT a FROM PlaRecuCategoria a WHERE a.creado = :creado")
    , @NamedQuery(name = "PlaRecuCategoria.findByModificado", query = "SELECT a FROM PlaRecuCategoria a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "PlaRecuCategoria.findByEstadoReg", query = "SELECT a FROM PlaRecuCategoria a WHERE a.estadoReg = :estadoReg")})

public class PlaRecuCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_categoria")
    private Integer idPlaRecuCategoria;
    @Size(max = 60)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
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

    public PlaRecuCategoria() {
    }

    public Integer getIdPlaRecuCategoria() {
        return idPlaRecuCategoria;
    }

    public void setIdPlaRecuCategoria(Integer idPlaRecuCategoria) {
        this.idPlaRecuCategoria = idPlaRecuCategoria;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (idPlaRecuCategoria != null ? idPlaRecuCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuCategoria)) {
            return false;
        }
        PlaRecuCategoria other = (PlaRecuCategoria) object;
        if ((this.idPlaRecuCategoria == null && other.idPlaRecuCategoria != null) || (this.idPlaRecuCategoria != null && !this.idPlaRecuCategoria.equals(other.idPlaRecuCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PlaRecuCategoria[ idPlaRecuCategoria=" + idPlaRecuCategoria + " ]";
    }
    
}

