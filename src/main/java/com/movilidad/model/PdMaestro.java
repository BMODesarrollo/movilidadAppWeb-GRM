/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.dto.PdAgendaMasivaDTO;
import com.movilidad.dto.PdPrincipalDTO;
import com.movilidad.dto.PdReporteNovedadesDTO;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pd_maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdMaestro.findAll", query = "SELECT p FROM PdMaestro p"),
    @NamedQuery(name = "PdMaestro.findByIdPdMaestro", query = "SELECT p FROM PdMaestro p WHERE p.idPdMaestro = :idPdMaestro"),
    @NamedQuery(name = "PdMaestro.findByFechaApertura", query = "SELECT p FROM PdMaestro p WHERE p.fechaApertura = :fechaApertura"),
    @NamedQuery(name = "PdMaestro.findByFechaCierre", query = "SELECT p FROM PdMaestro p WHERE p.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "PdMaestro.findByUsuarioApertura", query = "SELECT p FROM PdMaestro p WHERE p.usuarioApertura = :usuarioApertura"),
    @NamedQuery(name = "PdMaestro.findByUsuarioCierre", query = "SELECT p FROM PdMaestro p WHERE p.usuarioCierre = :usuarioCierre"),
    @NamedQuery(name = "PdMaestro.findByCreado", query = "SELECT p FROM PdMaestro p WHERE p.creado = :creado"),
    @NamedQuery(name = "PdMaestro.findByModificado", query = "SELECT p FROM PdMaestro p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PdMaestro.findByEstadoReg", query = "SELECT p FROM PdMaestro p WHERE p.estadoReg = :estadoReg")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "PdMaestroDTOMapping",
            classes = {
                @ConstructorResult(targetClass = PdPrincipalDTO.class,
                        columns = {
                            @ColumnResult(name = "id_pd_maestro", type = Integer.class),
                            @ColumnResult(name = "fecha_apertura", type = Date.class),
                            @ColumnResult(name = "fecha_cierre", type = Date.class),
                            @ColumnResult(name = "comentarios", type = String.class),
                            @ColumnResult(name = "usuario_apertura", type = String.class),
                            @ColumnResult(name = "usuario_cierre", type = String.class),
                            @ColumnResult(name = "id_pd_tipo_sancion", type = int.class),
                            @ColumnResult(name = "id_pd_estado_proceso", type = int.class),
                            @ColumnResult(name = "creado", type = Date.class),
                            @ColumnResult(name = "modificado", type = Date.class),
                            @ColumnResult(name = "estado_reg", type = Integer.class),
                            @ColumnResult(name = "fecha_citacion", type = Date.class),
                            @ColumnResult(name = "id_empleado", type = int.class),
                            @ColumnResult(name = "fecha_inicio_sancion", type = Date.class),
                            @ColumnResult(name = "fecha_fin_sancion", type = Date.class),
                            @ColumnResult(name = "id_responsable", type = int.class),
                            @ColumnResult(name = "fecha_apertura_date", type = Date.class),
                            @ColumnResult(name = "fecha_cierre_date", type = Date.class),
                            @ColumnResult(name = "fecha_citacion_date", type = Date.class),
                            @ColumnResult(name = "codigo_tm", type = String.class),
                            @ColumnResult(name = "estado_proceso", type = String.class),
                            @ColumnResult(name = "tipo_sancion", type = String.class),
                            @ColumnResult(name = "usuario_responsable", type = String.class),
                            @ColumnResult(name = "identificacion", type = BigInteger.class),
                            @ColumnResult(name = "asiste", type = Integer.class),
                            @ColumnResult(name = "id_empleado_estado", type = Integer.class),}
                )
            }),
    @SqlResultSetMapping(name = "PdAgendaMasivaDTOMapping",
            classes = {
                @ConstructorResult(targetClass = PdAgendaMasivaDTO.class,
                        columns = {
                            @ColumnResult(name = "id_empleado", type = Integer.class),
                            @ColumnResult(name = "fecha_citacion", type = Date.class),
                            @ColumnResult(name = "id_uf", type = Integer.class),
                            @ColumnResult(name = "codigo_tm", type = String.class),
                            @ColumnResult(name = "nombre_completo", type = String.class),}
                )
            }),
    @SqlResultSetMapping(name = "PdReporteNovedades",
            classes = {
                @ConstructorResult(targetClass = PdReporteNovedadesDTO.class,
                        columns = {
                            @ColumnResult(name = "id_pd_maestro", type = Integer.class),
                            @ColumnResult(name = "fecha", type = Date.class),
                            @ColumnResult(name = "tipo", type = String.class),
                            @ColumnResult(name = "detalle", type = String.class),
                            @ColumnResult(name = "identificacion", type = BigInteger.class),
                            @ColumnResult(name = "codigo_tm", type = String.class),
                            @ColumnResult(name = "nombre", type = String.class),
                            @ColumnResult(name = "procede", type = String.class),
                            @ColumnResult(name = "observacion", type = String.class),
                            @ColumnResult(name = "fecha_pd", type = Date.class),}
                )
            }),})

public class PdMaestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pd_maestro")
    private Integer idPdMaestro;
    @Column(name = "fecha_apertura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "comentarios")
    private String comentarios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario_apertura")
    private String usuarioApertura;
    @Size(min = 1, max = 15)
    @Column(name = "usuario_cierre")
    private String usuarioCierre;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPdMaestro", fetch = FetchType.LAZY)
    private List<PdMaestroSeguimiento> pdMaestroSeguimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPdMaestro", fetch = FetchType.LAZY)
    private List<PdMaestroDetalle> pdMaestroDetalleList;
    @JoinColumn(name = "id_pd_estado_proceso", referencedColumnName = "id_pd_estado_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private PdEstadoProceso idPdEstadoProceso;
    @JoinColumn(name = "id_pd_tipo_sancion", referencedColumnName = "id_pd_tipo_sancion")
    @ManyToOne(fetch = FetchType.LAZY)
    private PdTipoSancion idPdTipoSancion;
    @Basic(optional = true)
    @Column(name = "fecha_citacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCitacion;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @Basic(optional = true)
    @Column(name = "fecha_inicio_sancion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioSancion;
    @Basic(optional = true)
    @Column(name = "fecha_fin_sancion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinSancion;
    @JoinColumn(name = "id_responsable", referencedColumnName = "id_responsable")
    @ManyToOne(fetch = FetchType.LAZY)
    private PdResponsables responsable;

    public PdMaestro() {
    }

    public PdMaestro(Integer idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }

    public PdMaestro(Integer idPdMaestro, Date fechaApertura, Date fechaCierre, String comentarios, String usuarioApertura, String usuarioCierre, Date creado, Date modificado, int estadoReg, Date fechaInicioSancion, Date fechaFinSancion, List<PdMaestroSeguimiento> pdMaestroSeguimientoList, List<PdMaestroDetalle> pdMaestroDetalleList, PdEstadoProceso idPdEstadoProceso, PdTipoSancion idPdTipoSancion, Date fechaCitacion, Empleado idEmpleado, PdResponsables responsable) {
        this.idPdMaestro = idPdMaestro;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.comentarios = comentarios;
        this.usuarioApertura = usuarioApertura;
        this.usuarioCierre = usuarioCierre;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.pdMaestroSeguimientoList = pdMaestroSeguimientoList;
        this.pdMaestroDetalleList = pdMaestroDetalleList;
        this.idPdEstadoProceso = idPdEstadoProceso;
        this.idPdTipoSancion = idPdTipoSancion;
        this.fechaCitacion = fechaCitacion;
        this.idEmpleado = idEmpleado;
        this.responsable = responsable;
    }

    public Integer getIdPdMaestro() {
        return idPdMaestro;
    }

    public void setIdPdMaestro(Integer idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getUsuarioApertura() {
        return usuarioApertura;
    }

    public void setUsuarioApertura(String usuarioApertura) {
        this.usuarioApertura = usuarioApertura;
    }

    public String getUsuarioCierre() {
        return usuarioCierre;
    }

    public void setUsuarioCierre(String usuarioCierre) {
        this.usuarioCierre = usuarioCierre;
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
    public List<PdMaestroSeguimiento> getPdMaestroSeguimientoList() {
        return pdMaestroSeguimientoList;
    }

    public void setPdMaestroSeguimientoList(List<PdMaestroSeguimiento> pdMaestroSeguimientoList) {
        this.pdMaestroSeguimientoList = pdMaestroSeguimientoList;
    }

    @XmlTransient
    public List<PdMaestroDetalle> getPdMaestroDetalleList() {
        return pdMaestroDetalleList;
    }

    public void setPdMaestroDetalleList(List<PdMaestroDetalle> pdMaestroDetalleList) {
        this.pdMaestroDetalleList = pdMaestroDetalleList;
    }

    public PdEstadoProceso getIdPdEstadoProceso() {
        return idPdEstadoProceso;
    }

    public void setIdPdEstadoProceso(PdEstadoProceso idPdEstadoProceso) {
        this.idPdEstadoProceso = idPdEstadoProceso;
    }

    public PdTipoSancion getIdPdTipoSancion() {
        return idPdTipoSancion;
    }

    public void setIdPdTipoSancion(PdTipoSancion idPdTipoSancion) {
        this.idPdTipoSancion = idPdTipoSancion;
    }

    public Date getFechaCitacion() {
        return fechaCitacion;
    }

    public void setFechaCitacion(Date fechaCitacion) {
        this.fechaCitacion = fechaCitacion;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaInicioSancion() {
        return fechaInicioSancion;
    }

    public void setFechaInicioSancion(Date fechaInicioSancion) {
        this.fechaInicioSancion = fechaInicioSancion;
    }

    public Date getFechaFinSancion() {
        return fechaFinSancion;
    }

    public void setFechaFinSancion(Date fechaFinSancion) {
        this.fechaFinSancion = fechaFinSancion;
    }

    public PdResponsables getResponsable() {
        return responsable;
    }

    public void setResponsable(PdResponsables responsable) {
        this.responsable = responsable;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPdMaestro != null ? idPdMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdMaestro)) {
            return false;
        }
        PdMaestro other = (PdMaestro) object;
        if ((this.idPdMaestro == null && other.idPdMaestro != null) || (this.idPdMaestro != null && !this.idPdMaestro.equals(other.idPdMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PdMaestro[ idPdMaestro=" + idPdMaestro + " ]";
    }

}
