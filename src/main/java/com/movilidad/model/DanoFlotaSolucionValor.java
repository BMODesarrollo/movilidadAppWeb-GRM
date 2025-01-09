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
@Table(name = "dano_flota_solucion_valor")
public class DanoFlotaSolucionValor implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solucion_valor")
    @Basic(optional = false)
    private Integer idSolucionValor;
    
    @Size(max = 50)
    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "costo")
    private Integer costo;
    
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "vigencia_desde")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date vigenciaDesde;
    
    @Column(name = "vigencia_hasta")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date vigenciaHasta;
    
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date creado;

    @Column(name = "estado_reg")
    private Integer estadoReg;

    @Size(max = 15)
    @Column(name = "username")
    private String username;
    
    @JoinColumn(name = "id_dano_componente", referencedColumnName = "id_dano_componente")
    @ManyToOne(fetch = FetchType.LAZY)
    private DanoFlotaComponente danoFlotaComponente;

    public DanoFlotaSolucionValor() {
    }

    public DanoFlotaSolucionValor(Integer idSolucionValor) {
        this.idSolucionValor = idSolucionValor;
    }

    public DanoFlotaSolucionValor(Integer idSolucionValor, String tipo, Integer costo, String descripcion, Date vigenciaDesde, Date vigenciaHasta, Date creado, Integer estadoReg, String username, DanoFlotaComponente danoFlotaComponente) {
        this.idSolucionValor = idSolucionValor;
        this.tipo = tipo;
        this.costo = costo;
        this.descripcion = descripcion;
        this.vigenciaDesde = vigenciaDesde;
        this.vigenciaHasta = vigenciaHasta;
        this.creado = creado;
        this.estadoReg = estadoReg;
        this.username = username;
        this.danoFlotaComponente = danoFlotaComponente;
    }

    public Integer getIdSolucionValor() {
        return idSolucionValor;
    }

    public void setIdSolucionValor(Integer idSolucionValor) {
        this.idSolucionValor = idSolucionValor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getVigenciaDesde() {
        return vigenciaDesde;
    }

    public void setVigenciaDesde(Date vigenciaDesde) {
        this.vigenciaDesde = vigenciaDesde;
    }

    public Date getVigenciaHasta() {
        return vigenciaHasta;
    }

    public void setVigenciaHasta(Date vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
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

    public DanoFlotaComponente getDanoFlotaComponente() {
        return danoFlotaComponente;
    }

    public void setDanoFlotaComponente(DanoFlotaComponente danoFlotaComponente) {
        this.danoFlotaComponente = danoFlotaComponente;
    }

}

