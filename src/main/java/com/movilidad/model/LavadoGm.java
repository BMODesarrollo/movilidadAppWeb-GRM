/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.CostoLavadoDTO;
import java.io.Serializable;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "lavado_gm")

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "CostoLavadoCerradoMapping",
            classes = {
                @ConstructorResult(targetClass = CostoLavadoDTO.class,
                        columns = {
                            @ColumnResult(name = "lavado_cerrado")
                            ,
                            @ColumnResult(name = "fecha")
                            ,
                            @ColumnResult(name = "total_costo")
                        }
                )
            })
})
@XmlRootElement
public class LavadoGm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado_gm")
    private Integer idLavadoGm;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Size(max = 15)
    @Column(name = "usr_califica")
    private String usrCalifica;
    @Lob
    @Size(max = 65535)
    @Column(name = "obs_motivo")
    private String obsMotivo;
    @Column(name = "fecha_califica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCalifica;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Size(max = 15)
    @Column(name = "usr_cierra")
    private String usrCierra;
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

    @Column(name = "estado")
    private Integer estado;
    @Column(name = "costo")
    private Integer costo;

    //
    @Column(name = "aprobado")
    private int aprobado;
    @Column(name = "fecha_aprueba")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprueba;
    @Size(max = 15)
    @Column(name = "usr_aprueba")
    private String usrAprueba;

    //
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_lavado_calificacion", referencedColumnName = "id_lavado_calificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private LavadoCalificacion idLavadoCalificacion;
    @JoinColumn(name = "id_lavado_motivo", referencedColumnName = "id_lavado_motivo")
    @ManyToOne(fetch = FetchType.LAZY)
    private LavadoMotivo idLavadoMotivo;
    @JoinColumn(name = "id_lavado_tipo_servicio", referencedColumnName = "id_lavado_tipo_servicio")
    @ManyToOne(fetch = FetchType.LAZY)
    private LavadoTipoServicio idLavadoTipoServicio;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;

    public LavadoGm() {
        this.estado = 0;
    }

    public LavadoGm(Integer idLavadoGm) {
        this.idLavadoGm = idLavadoGm;
    }

    public Integer getIdLavadoGm() {
        return idLavadoGm;
    }

    public void setIdLavadoGm(Integer idLavadoGm) {
        this.idLavadoGm = idLavadoGm;
    }

    public String getUsrCalifica() {
        return usrCalifica;
    }

    public void setUsrCalifica(String usrCalifica) {
        this.usrCalifica = usrCalifica;
    }

    public String getObsMotivo() {
        return obsMotivo;
    }

    public void setObsMotivo(String obsMotivo) {
        this.obsMotivo = obsMotivo;
    }

    public Date getFechaCalifica() {
        return fechaCalifica;
    }

    public void setFechaCalifica(Date fechaCalifica) {
        this.fechaCalifica = fechaCalifica;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getUsrCierra() {
        return usrCierra;
    }

    public void setUsrCierra(String usrCierra) {
        this.usrCierra = usrCierra;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public LavadoCalificacion getIdLavadoCalificacion() {
        return idLavadoCalificacion;
    }

    public void setIdLavadoCalificacion(LavadoCalificacion idLavadoCalificacion) {
        this.idLavadoCalificacion = idLavadoCalificacion;
    }

    public LavadoMotivo getIdLavadoMotivo() {
        return idLavadoMotivo;
    }

    public void setIdLavadoMotivo(LavadoMotivo idLavadoMotivo) {
        this.idLavadoMotivo = idLavadoMotivo;
    }

    public LavadoTipoServicio getIdLavadoTipoServicio() {
        return idLavadoTipoServicio;
    }

    public void setIdLavadoTipoServicio(LavadoTipoServicio idLavadoTipoServicio) {
        this.idLavadoTipoServicio = idLavadoTipoServicio;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public int getAprobado() {
        return aprobado;
    }

    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }

    public Date getFechaAprueba() {
        return fechaAprueba;
    }

    public void setFechaAprueba(Date fechaAprueba) {
        this.fechaAprueba = fechaAprueba;
    }

    public String getUsrAprueba() {
        return usrAprueba;
    }

    public void setUsrAprueba(String usrAprueba) {
        this.usrAprueba = usrAprueba;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLavadoGm != null ? idLavadoGm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavadoGm)) {
            return false;
        }
        LavadoGm other = (LavadoGm) object;
        if ((this.idLavadoGm == null && other.idLavadoGm != null) || (this.idLavadoGm != null && !this.idLavadoGm.equals(other.idLavadoGm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.LavadoGm[ idLavadoGm=" + idLavadoGm + " ]";
    }

}
