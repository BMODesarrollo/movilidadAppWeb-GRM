/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.InformeContabilidad;
import com.movilidad.util.beans.InformeContabilidad235;
import com.movilidad.util.beans.InformeContabilidadNo235;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
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
@Table(name = "km_conciliado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KmConciliado.findAll", query = "SELECT k FROM KmConciliado k"),
    @NamedQuery(name = "KmConciliado.findByIdKmConciliado", query = "SELECT k FROM KmConciliado k WHERE k.idKmConciliado = :idKmConciliado"),
    @NamedQuery(name = "KmConciliado.findByFecha", query = "SELECT k FROM KmConciliado k WHERE k.fecha = :fecha"),
    @NamedQuery(name = "KmConciliado.findByKmMtto", query = "SELECT k FROM KmConciliado k WHERE k.kmMtto = :kmMtto"),
    @NamedQuery(name = "KmConciliado.findByKmComercial", query = "SELECT k FROM KmConciliado k WHERE k.kmComercial = :kmComercial"),
    @NamedQuery(name = "KmConciliado.findByKmHlp", query = "SELECT k FROM KmConciliado k WHERE k.kmHlp = :kmHlp"),
    @NamedQuery(name = "KmConciliado.findByKmRecorrido", query = "SELECT k FROM KmConciliado k WHERE k.kmRecorrido = :kmRecorrido"),
    @NamedQuery(name = "KmConciliado.findByKmContabilidad", query = "SELECT k FROM KmConciliado k WHERE k.kmContabilidad = :kmContabilidad"),
    @NamedQuery(name = "KmConciliado.findByKmCom235", query = "SELECT k FROM KmConciliado k WHERE k.kmCom235 = :kmCom235"),
    @NamedQuery(name = "KmConciliado.findByKmHlp235", query = "SELECT k FROM KmConciliado k WHERE k.kmHlp235 = :kmHlp235"),
    @NamedQuery(name = "KmConciliado.findByUsername", query = "SELECT k FROM KmConciliado k WHERE k.username = :username"),
    @NamedQuery(name = "KmConciliado.findByCreado", query = "SELECT k FROM KmConciliado k WHERE k.creado = :creado"),
    @NamedQuery(name = "KmConciliado.findByModificado", query = "SELECT k FROM KmConciliado k WHERE k.modificado = :modificado"),
    @NamedQuery(name = "KmConciliado.findByEstadoReg", query = "SELECT k FROM KmConciliado k WHERE k.estadoReg = :estadoReg")})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "InformeContabilidadMapping",
            classes = {
                @ConstructorResult(targetClass = InformeContabilidad.class,
                        columns = {
                            @ColumnResult(name = "codigo_vehiculo"),
                            @ColumnResult(name = "total", type = BigDecimal.class)
                        }
                )
            }),
    @SqlResultSetMapping(name = "InformeContabilidad235Mapping",
            classes = {
                @ConstructorResult(targetClass = InformeContabilidad235.class,
                        columns = {
                            @ColumnResult(name = "codigo_vehiculo"),
                            @ColumnResult(name = "km_com_235"),
                            @ColumnResult(name = "km_hlp_235")
                        }
                )
            }),
    @SqlResultSetMapping(name = "InformeContabilidadNo235Mapping",
            classes = {
                @ConstructorResult(targetClass = InformeContabilidadNo235.class,
                        columns = {
                            @ColumnResult(name = "codigo_vehiculo"),
                            @ColumnResult(name = "comercial"),
                            @ColumnResult(name = "hlp")
                        }
                )
            })

})
public class KmConciliado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_km_conciliado")
    private Integer idKmConciliado;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "km_mtto")
    private Integer kmMtto;
    @Column(name = "km_comercial")
    private Integer kmComercial;
    @Column(name = "km_hlp")
    private Integer kmHlp;
    @Column(name = "km_recorrido")
    private Integer kmRecorrido;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "km_contabilidad")
    private BigDecimal kmContabilidad;
    @Column(name = "km_com_235")
    private BigDecimal kmCom235;
    @Column(name = "km_hlp_235")
    private BigDecimal kmHlp235;
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
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne
    private Vehiculo idVehiculo;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public KmConciliado() {
    }

    public KmConciliado(Integer idKmConciliado) {
        this.idKmConciliado = idKmConciliado;
    }

    public Integer getIdKmConciliado() {
        return idKmConciliado;
    }

    public void setIdKmConciliado(Integer idKmConciliado) {
        this.idKmConciliado = idKmConciliado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getKmMtto() {
        return kmMtto;
    }

    public void setKmMtto(Integer kmMtto) {
        this.kmMtto = kmMtto;
    }

    public Integer getKmComercial() {
        return kmComercial;
    }

    public void setKmComercial(Integer kmComercial) {
        this.kmComercial = kmComercial;
    }

    public Integer getKmHlp() {
        return kmHlp;
    }

    public void setKmHlp(Integer kmHlp) {
        this.kmHlp = kmHlp;
    }

    public Integer getKmRecorrido() {
        return kmRecorrido;
    }

    public void setKmRecorrido(Integer kmRecorrido) {
        this.kmRecorrido = kmRecorrido;
    }

    public BigDecimal getKmContabilidad() {
        return kmContabilidad;
    }

    public void setKmContabilidad(BigDecimal kmContabilidad) {
        this.kmContabilidad = kmContabilidad;
    }

    public BigDecimal getKmCom235() {
        return kmCom235;
    }

    public void setKmCom235(BigDecimal kmCom235) {
        this.kmCom235 = kmCom235;
    }

    public BigDecimal getKmHlp235() {
        return kmHlp235;
    }

    public void setKmHlp235(BigDecimal kmHlp235) {
        this.kmHlp235 = kmHlp235;
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

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    
    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKmConciliado != null ? idKmConciliado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KmConciliado)) {
            return false;
        }
        KmConciliado other = (KmConciliado) object;
        if ((this.idKmConciliado == null && other.idKmConciliado != null) || (this.idKmConciliado != null && !this.idKmConciliado.equals(other.idKmConciliado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.KmConciliado[ idKmConciliado=" + idKmConciliado + " ]";
    }
}
