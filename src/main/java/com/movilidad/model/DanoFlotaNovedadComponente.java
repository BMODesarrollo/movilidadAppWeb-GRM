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
@Table(name = "dano_flota_novedad_componente")
public class DanoFlotaNovedadComponente implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_novedad_componente")
    @Basic(optional = false)
    private Integer idNovedadDanoComponente;
    
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date creado;

    @Column(name = "estado_reg")
    private Integer estadoReg;

    @Size(max = 15)
    @Column(name = "username")
    private String username;

    @JoinColumn(name = "id_novedad_dano", referencedColumnName = "id_novedad_dano")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadDano novedadDano;    
    
    @JoinColumn(name = "id_solucion_valor", referencedColumnName = "id_solucion_valor")
    @ManyToOne(fetch = FetchType.LAZY)
    private DanoFlotaSolucionValor danoFlotaSolucionValor;    
    
    public DanoFlotaNovedadComponente() {
    }

    public DanoFlotaNovedadComponente(Integer idNovedadDanoComponente) {
        this.idNovedadDanoComponente = idNovedadDanoComponente;
    }

    public DanoFlotaNovedadComponente(Integer idNovedadDanoComponente, Date creado, Integer estadoReg, String username, NovedadDano novedadDano, DanoFlotaSolucionValor danoFlotaSolucionValor) {
        this.idNovedadDanoComponente = idNovedadDanoComponente;
        this.creado = creado;
        this.estadoReg = estadoReg;
        this.username = username;
        this.novedadDano = novedadDano;
        this.danoFlotaSolucionValor = danoFlotaSolucionValor;
    }

    public Integer getIdNovedadDanoComponente() {
        return idNovedadDanoComponente;
    }

    public void setIdNovedadDanoComponente(Integer idNovedadDanoComponente) {
        this.idNovedadDanoComponente = idNovedadDanoComponente;
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

    public NovedadDano getNovedadDano() {
        return novedadDano;
    }

    public void setNovedadDano(NovedadDano novedadDano) {
        this.novedadDano = novedadDano;
    }

    public DanoFlotaSolucionValor getDanoFlotaSolucionValor() {
        return danoFlotaSolucionValor;
    }

    public void setDanoFlotaSolucionValor(DanoFlotaSolucionValor danoFlotaSolucionValor) {
        this.danoFlotaSolucionValor = danoFlotaSolucionValor;
    }

}

