/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "config")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Config.findAll", query = "SELECT c FROM Config c"),
    @NamedQuery(name = "Config.findByIdCondig", query = "SELECT c FROM Config c WHERE c.idCondig = :idCondig"),
    @NamedQuery(name = "Config.findByKey", query = "SELECT c FROM Config c WHERE c.key = :key"),
    @NamedQuery(name = "Config.findByValue", query = "SELECT c FROM Config c WHERE c.value = :value")})
public class Config implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_condig")
    private Integer idCondig;
    @Size(max = 8)
    @Column(name = "key")
    private String key;
    @Column(name = "value")
    private int value;

    public Config() {
    }

    public Config(Integer idCondig) {
        this.idCondig = idCondig;
    }

    public Integer getIdCondig() {
        return idCondig;
    }

    public void setIdCondig(Integer idCondig) {
        this.idCondig = idCondig;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCondig != null ? idCondig.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Config)) {
            return false;
        }
        Config other = (Config) object;
        if ((this.idCondig == null && other.idCondig != null) || (this.idCondig != null && !this.idCondig.equals(other.idCondig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Config[ idCondig=" + idCondig + " ]";
    }
    
}
