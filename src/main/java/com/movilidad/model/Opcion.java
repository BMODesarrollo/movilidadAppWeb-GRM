/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "opcion")

public class Opcion implements Serializable {
    @OneToMany(mappedBy = "idOpcion")
    private List<UserAuthorityOption> userAuthorityOptionList;

    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "NAMEOP")
    private String nameop;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToMany(mappedBy = "listaOpciones")
    private Collection<UserRoles> roles;
    @Column(name = "recurso")
    private String recurso;
    @Column(name = "iconchild")
    private String iconChild;
    @Column(name = "iconParent")
    private String iconParent;

    public String getIconChild() {
        return iconChild;
    }

    public void setIconChild(String iconChild) {
        this.iconChild = iconChild;
    }

    public String getIconParent() {
        return iconParent;
    }

    public void setIconParent(String iconParent) {
        this.iconParent = iconParent;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public Collection<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Collection<UserRoles> roles) {
        this.roles = roles;
    }

    public String getNameop() {
        return nameop;
    }

    public void setNameop(String nameop) {
        this.nameop = nameop;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opcion)) {
            return false;
        }
        Opcion other = (Opcion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.todopagoexpress.finansysweb2.model.Opcion[ id=" + id + " ]";
    }

    public Opcion() {
    }

    @XmlTransient
    public List<UserAuthorityOption> getUserAuthorityOptionList() {
        return userAuthorityOptionList;
    }

    public void setUserAuthorityOptionList(List<UserAuthorityOption> userAuthorityOptionList) {
        this.userAuthorityOptionList = userAuthorityOptionList;
    }
}
