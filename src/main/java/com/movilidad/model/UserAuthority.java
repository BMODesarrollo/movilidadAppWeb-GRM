/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "user_authority")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAuthority.findAll", query = "SELECT u FROM UserAuthority u")
    , @NamedQuery(name = "UserAuthority.findByIdUserAuthority", query = "SELECT u FROM UserAuthority u WHERE u.idUserAuthority = :idUserAuthority")
    , @NamedQuery(name = "UserAuthority.findByAuthority", query = "SELECT u FROM UserAuthority u WHERE u.authority = :authority")
    , @NamedQuery(name = "UserAuthority.findByLandPage", query = "SELECT u FROM UserAuthority u WHERE u.landPage = :landPage")
    , @NamedQuery(name = "UserAuthority.findByUsername", query = "SELECT u FROM UserAuthority u WHERE u.username = :username")
    , @NamedQuery(name = "UserAuthority.findByCreado", query = "SELECT u FROM UserAuthority u WHERE u.creado = :creado")
    , @NamedQuery(name = "UserAuthority.findByModificado", query = "SELECT u FROM UserAuthority u WHERE u.modificado = :modificado")
    , @NamedQuery(name = "UserAuthority.findByEstadoReg", query = "SELECT u FROM UserAuthority u WHERE u.estadoReg = :estadoReg")})
public class UserAuthority implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user_authority")
    private Integer idUserAuthority;
    @Size(max = 45)
    @Column(name = "authority")
    private String authority;
    @Size(max = 150)
    @Column(name = "land_page")
    private String landPage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUserAuthority", fetch = FetchType.LAZY)
    private List<UserAuthorityOption> userAuthorityOptionList;

    public UserAuthority() {
    }

    public UserAuthority(Integer idUserAuthority) {
        this.idUserAuthority = idUserAuthority;
    }

    public UserAuthority(Integer idUserAuthority, String username, Date creado, int estadoReg) {
        this.idUserAuthority = idUserAuthority;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdUserAuthority() {
        return idUserAuthority;
    }

    public void setIdUserAuthority(Integer idUserAuthority) {
        this.idUserAuthority = idUserAuthority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getLandPage() {
        return landPage;
    }

    public void setLandPage(String landPage) {
        this.landPage = landPage;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<UserAuthorityOption> getUserAuthorityOptionList() {
        return userAuthorityOptionList;
    }

    public void setUserAuthorityOptionList(List<UserAuthorityOption> userAuthorityOptionList) {
        this.userAuthorityOptionList = userAuthorityOptionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserAuthority != null ? idUserAuthority.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAuthority)) {
            return false;
        }
        UserAuthority other = (UserAuthority) object;
        if ((this.idUserAuthority == null && other.idUserAuthority != null) || (this.idUserAuthority != null && !this.idUserAuthority.equals(other.idUserAuthority))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.UserAuthority[ idUserAuthority=" + idUserAuthority + " ]";
    }
    
}
