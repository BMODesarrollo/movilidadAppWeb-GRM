/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.MultasCtrl;
import java.io.Serializable;
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
 * @author HP
 */
@Entity
@Table(name = "multa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Multa.findAll", query = "SELECT m FROM Multa m"),
    @NamedQuery(name = "Multa.findByIdMulta", query = "SELECT m FROM Multa m WHERE m.idMulta = :idMulta"),
    @NamedQuery(name = "Multa.findByFechaHora", query = "SELECT m FROM Multa m WHERE m.fechaHora = :fechaHora"),
    @NamedQuery(name = "Multa.findByIdServicio", query = "SELECT m FROM Multa m WHERE m.idServicio = :idServicio"),
    @NamedQuery(name = "Multa.findByTabla", query = "SELECT m FROM Multa m WHERE m.tabla = :tabla"),
    @NamedQuery(name = "Multa.findByProcede", query = "SELECT m FROM Multa m WHERE m.procede = :procede"),
    @NamedQuery(name = "Multa.findByUsername", query = "SELECT m FROM Multa m WHERE m.username = :username"),
    @NamedQuery(name = "Multa.findByCreado", query = "SELECT m FROM Multa m WHERE m.creado = :creado"),
    @NamedQuery(name = "Multa.findByModificado", query = "SELECT m FROM Multa m WHERE m.modificado = :modificado"),
    @NamedQuery(name = "Multa.findByEstadoReg", query = "SELECT m FROM Multa m WHERE m.estadoReg = :estadoReg")})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "MultasCtrlMapping",
            classes = {
                @ConstructorResult(targetClass = MultasCtrl.class,
                        columns = {
                            @ColumnResult(name = "codigo_operador"),
                            @ColumnResult(name = "codigo_vehiculo"),
                            @ColumnResult(name = "nombre_operador"),
                            @ColumnResult(name = "operaciones"),
                            @ColumnResult(name = "mantenimiento"),
                            @ColumnResult(name = "lavado"),
                            @ColumnResult(name = "seguridad_vial"),
                            @ColumnResult(name = "kms_operaciones"),
                            @ColumnResult(name = "kms_mantenimiento"),
                            @ColumnResult(name = "kms_lavado"),
                            @ColumnResult(name = "kms_seguridad_vial")
                        }
                )
            })

})
public class Multa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa")
    private Integer idMulta;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "id_servicio")
    private Integer idServicio;
    @Column(name = "tabla")
    private Integer tabla;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "procede")
    private int procede;
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
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_multa_clasificacion", referencedColumnName = "id_multa_clasificacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MultaClasificacion idMultaClasificacion;
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTc idPrgTc;
    @JoinColumn(name = "id_multa_reportado_por", referencedColumnName = "id_multa_reportado_por")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MultaReportadoPor idMultaReportadoPor;
    @JoinColumn(name = "id_multa_tipo", referencedColumnName = "id_multa_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MultaTipo idMultaTipo;
    @JoinColumn(name = "id_patio", referencedColumnName = "id_operacion_patios")
    @ManyToOne(fetch = FetchType.LAZY)
    private OperacionPatios idPatio;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @OneToMany(mappedBy = "idMulta", fetch = FetchType.LAZY)
    private List<Novedad> novedadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMulta", fetch = FetchType.LAZY)
    private List<MultaDocumentos> multaDocumentosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMulta", fetch = FetchType.LAZY)
    private List<MultaSeguimiento> multaSeguimientoList;

    public Multa() {
    }

    public Multa(Integer idMulta) {
        this.idMulta = idMulta;
    }

    public Multa(Integer idMulta, String descripcion, int procede, String username, Date creado, int estadoReg) {
        this.idMulta = idMulta;
        this.descripcion = descripcion;
        this.procede = procede;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Integer idMulta) {
        this.idMulta = idMulta;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Integer getTabla() {
        return tabla;
    }

    public void setTabla(Integer tabla) {
        this.tabla = tabla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProcede() {
        return procede;
    }

    public void setProcede(int procede) {
        this.procede = procede;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public MultaClasificacion getIdMultaClasificacion() {
        return idMultaClasificacion;
    }

    public void setIdMultaClasificacion(MultaClasificacion idMultaClasificacion) {
        this.idMultaClasificacion = idMultaClasificacion;
    }

    public PrgTc getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(PrgTc idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    public MultaReportadoPor getIdMultaReportadoPor() {
        return idMultaReportadoPor;
    }

    public void setIdMultaReportadoPor(MultaReportadoPor idMultaReportadoPor) {
        this.idMultaReportadoPor = idMultaReportadoPor;
    }

    public MultaTipo getIdMultaTipo() {
        return idMultaTipo;
    }

    public void setIdMultaTipo(MultaTipo idMultaTipo) {
        this.idMultaTipo = idMultaTipo;
    }

    public OperacionPatios getIdPatio() {
        return idPatio;
    }

    public void setIdPatio(OperacionPatios idPatio) {
        this.idPatio = idPatio;
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

    @XmlTransient
    public List<Novedad> getNovedadList() {
        return novedadList;
    }

    public void setNovedadList(List<Novedad> novedadList) {
        this.novedadList = novedadList;
    }

    @XmlTransient
    public List<MultaDocumentos> getMultaDocumentosList() {
        return multaDocumentosList;
    }

    public void setMultaDocumentosList(List<MultaDocumentos> multaDocumentosList) {
        this.multaDocumentosList = multaDocumentosList;
    }

    @XmlTransient
    public List<MultaSeguimiento> getMultaSeguimientoList() {
        return multaSeguimientoList;
    }

    public void setMultaSeguimientoList(List<MultaSeguimiento> multaSeguimientoList) {
        this.multaSeguimientoList = multaSeguimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMulta != null ? idMulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Multa)) {
            return false;
        }
        Multa other = (Multa) object;
        if ((this.idMulta == null && other.idMulta != null) || (this.idMulta != null && !this.idMulta.equals(other.idMulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Multa[ idMulta=" + idMulta + " ]";
    }

}
