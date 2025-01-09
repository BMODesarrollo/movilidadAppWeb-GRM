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
@Table(name = "dano_flota_componente_grupo")
public class DanoFlotaComponenteGrupo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_componente_grupo")
    @Basic(optional = false)
    private Integer idComponenteGrupo;
    
    @Size(max = 50)
    @Column(name = "grupo")
    private String grupo;
    
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date creado;

    @Column(name = "estado_reg")
    private Integer estadoReg;

    @Size(max = 15)
    @Column(name = "username")
    private String username;
    
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipo vehiculoTipo;

    public DanoFlotaComponenteGrupo() {
    }

    public DanoFlotaComponenteGrupo(Integer idComponenteGrupo) {
        this.idComponenteGrupo = idComponenteGrupo;
    }

    public Integer getIdComponenteGrupo() {
        return idComponenteGrupo;
    }

    public void setIdComponenteGrupo(Integer idComponenteGrupo) {
        this.idComponenteGrupo = idComponenteGrupo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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

    public VehiculoTipo getVehiculoTipo() {
        return vehiculoTipo;
    }

    public void setVehiculoTipo(VehiculoTipo vehiculoTipo) {
        this.vehiculoTipo = vehiculoTipo;
    }
    
}

