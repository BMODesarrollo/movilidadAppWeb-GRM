/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "user_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRoles.findAll", query = "SELECT u FROM UserRoles u"),
    @NamedQuery(name = "UserRoles.findByUserRoleId", query = "SELECT u FROM UserRoles u WHERE u.userRoleId = :userRoleId"),
    @NamedQuery(name = "UserRoles.findByAuthority", query = "SELECT u FROM UserRoles u WHERE u.authority = :authority")})
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id", nullable = false)
    private Integer userRoleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "authority", nullable = false, length = 45)
    private String authority;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Users userId;
    @ManyToMany
    @JoinTable(name = "opcion_role",
            joinColumns = {
                @JoinColumn(name = "idrole")},
            inverseJoinColumns = {
                @JoinColumn(name = "idopc")})
    private Collection<Opcion> listaOpciones;

    public Collection<Opcion> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(Collection<Opcion> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    public UserRoles() {
    }

    public UserRoles(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public UserRoles(Integer userRoleId, String authority) {
        this.userRoleId = userRoleId;
        this.authority = authority;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleId != null ? userRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoles)) {
            return false;
        }
        UserRoles other = (UserRoles) object;
        if ((this.userRoleId == null && other.userRoleId != null) || (this.userRoleId != null && !this.userRoleId.equals(other.userRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + userRoleId;
    }

}
