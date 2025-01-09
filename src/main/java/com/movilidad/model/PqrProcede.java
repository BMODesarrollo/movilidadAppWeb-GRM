/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ricardo.lopez
 */
@Entity
@Table(name = "pqr_tb_procede")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PqrProcede.findAll", query = "SELECT a FROM PqrProcede a"),
    @NamedQuery(name = "PqrProcede.findByPqr_proc_iden", query = "SELECT a FROM PqrProcede a WHERE a.obj_proc_iden = :obj_proc_iden"),
    @NamedQuery(name = "PqrProcede.findByPqr_proc_nomb", query = "SELECT a FROM PqrProcede a WHERE a.obj_proc_nomb = :obj_proc_nomb"),
    @NamedQuery(name = "PqrProcede.findByPqr_proc_desc", query = "SELECT a FROM PqrProcede a WHERE a.obj_proc_desc = :obj_proc_desc"),
    @NamedQuery(name = "PqrProcede.findByPqr_proc_user", query = "SELECT a FROM PqrProcede a WHERE a.obj_proc_user = :obj_proc_user"),
    @NamedQuery(name = "PqrProcede.findByPqr_proc_crea", query = "SELECT a FROM PqrProcede a WHERE a.obj_proc_crea = :obj_proc_crea"),
    @NamedQuery(name = "PqrProcede.findByPqr_proc_actu", query = "SELECT a FROM PqrProcede a WHERE a.obj_proc_actu = :obj_proc_actu"),
    @NamedQuery(name = "PqrProcede.findByPqr_proc_esta", query = "SELECT a FROM PqrProcede a WHERE a.obj_proc_esta = :obj_proc_esta"),
    @NamedQuery(name = "PqrProcede.findByPqr_proc_sino", query = "SELECT a FROM PqrProcede a WHERE a.obj_proc_sino = :obj_proc_sino")})
public class PqrProcede implements Serializable {

    public PqrProcede() {

    }

    public PqrProcede(int iden_PqrProcede) {
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqr_proc_i")
    private Integer obj_proc_iden;
   

    @Size(max = 60)
    @Column(name = "pqr_proc_nomb")
    private String obj_proc_nomb;

    @Size(max = 65535)
    @Column(name = "pqr_proc_desc")
    private String obj_proc_desc;

    @Size(max = 15)
    @Column(name = "pqr_proc_user")
    private String obj_proc_user;

    @Column(name = "pqr_proc_crea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date obj_proc_crea;

    @Column(name = "pqr_proc_actu")
    @Temporal(TemporalType.TIMESTAMP)
    private Date obj_proc_actu;

    @Column(name = "pqr_proc_esta")
    private Integer obj_proc_esta;

    @Column(name = "pqr_proc_sino")
    private String obj_proc_sino;

    
    public Integer getObj_proc_iden() {
        return obj_proc_iden;
    }

    public void setObj_proc_iden(Integer obj_proc_iden) {
        this.obj_proc_iden = obj_proc_iden;
    }

    public String getObj_proc_nomb() {
        return obj_proc_nomb;
    }

    public void setObj_proc_nomb(String obj_proc_nomb) {
        this.obj_proc_nomb = obj_proc_nomb;
    }

    public String getObj_proc_desc() {
        return obj_proc_desc;
    }

    public void setObj_proc_desc(String obj_proc_desc) {
        this.obj_proc_desc = obj_proc_desc;
    }

    public String getObj_proc_user() {
        return obj_proc_user;
    }

    public void setObj_proc_user(String obj_proc_user) {
        this.obj_proc_user = obj_proc_user;
    }

    public Date getObj_proc_crea() {
        return obj_proc_crea;
    }

    public void setObj_proc_crea(Date obj_proc_crea) {
        this.obj_proc_crea = obj_proc_crea;
    }

    public Date getObj_proc_actu() {
        return obj_proc_actu;
    }

    public void setObj_proc_actu(Date obj_proc_actu) {
        this.obj_proc_actu = obj_proc_actu;
    }

    public Integer getObj_proc_esta() {
        return obj_proc_esta;
    }

    public void setObj_proc_esta(Integer obj_proc_esta) {
        this.obj_proc_esta = obj_proc_esta;
    }

//    public String getObj_proc_sino() {
//        return obj_proc_sino;
//    }
//
//    public void setObj_proc_sino(String obj_proc_sino) {
//        this.obj_proc_sino = obj_proc_sino;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (obj_proc_iden != null ? obj_proc_iden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PqrProcede)) {
            return false;
        }
        PqrProcede other = (PqrProcede) object;
        if ((this.obj_proc_iden == null && other.obj_proc_iden != null) || (this.obj_proc_iden != null && !this.obj_proc_iden.equals(other.obj_proc_iden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PqrProcede[ obj_proc_iden=" + obj_proc_iden + " ]";
    }

}
