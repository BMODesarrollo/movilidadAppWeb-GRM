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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "user_authority_option")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAuthorityOption.findAll", query = "SELECT u FROM UserAuthorityOption u"),
    @NamedQuery(name = "UserAuthorityOption.findByIdUserAuthorityOption", query = "SELECT u FROM UserAuthorityOption u WHERE u.idUserAuthorityOption = :idUserAuthorityOption"),
    @NamedQuery(name = "UserAuthorityOption.findByUsername", query = "SELECT u FROM UserAuthorityOption u WHERE u.username = :username"),
    @NamedQuery(name = "UserAuthorityOption.findByCreado", query = "SELECT u FROM UserAuthorityOption u WHERE u.creado = :creado"),
    @NamedQuery(name = "UserAuthorityOption.findByModificado", query = "SELECT u FROM UserAuthorityOption u WHERE u.modificado = :modificado"),
    @NamedQuery(name = "UserAuthorityOption.findByEstadoReg", query = "SELECT u FROM UserAuthorityOption u WHERE u.estadoReg = :estadoReg")})
public class UserAuthorityOption implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user_authority_option")
    private Integer idUserAuthorityOption;
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
    @JoinColumn(name = "id_user_authority", referencedColumnName = "id_user_authority")
    @ManyToOne(optional = false)
    private UserAuthority idUserAuthority;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id")
    @ManyToOne
    private Opcion idOpcion;

    public UserAuthorityOption() {
    }

    public UserAuthorityOption(Integer idUserAuthorityOption) {
        this.idUserAuthorityOption = idUserAuthorityOption;
    }

    public Integer getIdUserAuthorityOption() {
        return idUserAuthorityOption;
    }

    public void setIdUserAuthorityOption(Integer idUserAuthorityOption) {
        this.idUserAuthorityOption = idUserAuthorityOption;
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

    public UserAuthority getIdUserAuthority() {
        return idUserAuthority;
    }

    public void setIdUserAuthority(UserAuthority idUserAuthority) {
        this.idUserAuthority = idUserAuthority;
    }

    public Opcion getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Opcion idOpcion) {
        this.idOpcion = idOpcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserAuthorityOption != null ? idUserAuthorityOption.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAuthorityOption)) {
            return false;
        }
        UserAuthorityOption other = (UserAuthorityOption) object;
        if ((this.idUserAuthorityOption == null && other.idUserAuthorityOption != null) || (this.idUserAuthorityOption != null && !this.idUserAuthorityOption.equals(other.idUserAuthorityOption))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.UserAuthorityOption[ idUserAuthorityOption=" + idUserAuthorityOption + " ]";
    }
    
}
