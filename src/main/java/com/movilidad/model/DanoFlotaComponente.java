/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.model;

/**
 *
 * @author julian.arevalo
 */
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dano_flota_componente")
public class DanoFlotaComponente implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dano_componente")
    @Basic(optional = false)
    private Integer idDanoComponente;
    
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "afecta_pm")
    private Integer afectaPm;
    
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date creado;

    @Column(name = "estado_reg")
    private Integer estadoReg;

    @Size(max = 15)
    @Column(name = "username")
    private String username;
    
    @JoinColumn(name = "id_componente_grupo", referencedColumnName = "id_componente_grupo")
    @ManyToOne(fetch = FetchType.LAZY)
    private DanoFlotaComponenteGrupo danoFlotaGrupo;
    
    @Transient
    private DanoFlotaSolucionValor danoFlotaSolucionValor;
    @Transient
    private DanoFlotaNovedadComponente danoFlotaComponenteEdit;

    public DanoFlotaComponente() {
    }

    public DanoFlotaComponente(Integer idDanoComponente) {
        this.idDanoComponente = idDanoComponente;
    }

    public DanoFlotaComponente(Integer idDanoComponente, String nombre, String descripcion, Integer afectaPm, Date creado, Integer estadoReg, String username, DanoFlotaComponenteGrupo danoFlotaGrupo) {
        this.idDanoComponente = idDanoComponente;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.afectaPm = afectaPm;
        this.creado = creado;
        this.estadoReg = estadoReg;
        this.username = username;
        this.danoFlotaGrupo = danoFlotaGrupo;
    }

    public Integer getIdDanoComponente() {
        return idDanoComponente;
    }

    public void setIdDanoComponente(Integer idDanoComponente) {
        this.idDanoComponente = idDanoComponente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAfectaPm() {
        return afectaPm;
    }

    public void setAfectaPm(Integer afectaPm) {
        this.afectaPm = afectaPm;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DanoFlotaComponenteGrupo getDanoFlotaGrupo() {
        return danoFlotaGrupo;
    }

    public void setDanoFlotaGrupo(DanoFlotaComponenteGrupo danoFlotaGrupo) {
        this.danoFlotaGrupo = danoFlotaGrupo;
    }

    public DanoFlotaSolucionValor getDanoFlotaSolucionValor() {
        return danoFlotaSolucionValor;
    }

    public void setDanoFlotaSolucionValor(DanoFlotaSolucionValor danoFlotaSolucionValor) {
        this.danoFlotaSolucionValor = danoFlotaSolucionValor;
    }

    public DanoFlotaNovedadComponente getDanoFlotaComponenteEdit() {
        return danoFlotaComponenteEdit;
    }

    public void setDanoFlotaComponenteEdit(DanoFlotaNovedadComponente danoFlotaComponenteEdit) {
        this.danoFlotaComponenteEdit = danoFlotaComponenteEdit;
    }
    
}

