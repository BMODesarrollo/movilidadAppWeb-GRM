package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que permite la iteracción del sistema con la tabla infracciones
 * @author Omar Beltrán
 */
@Entity
@Table(name = "hallazgos_infracciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Infracciones.findAll", query = "SELECT i FROM Infracciones i"),
    @NamedQuery(name = "Infracciones.findByIdInfracciones", query = "SELECT i FROM Infracciones i WHERE i.idInfraccion = :idInfraccion"),
    @NamedQuery(name = "Infracciones.findByIdICO", query = "SELECT i FROM Infracciones i WHERE i.idICO = :idICO"),
    @NamedQuery(name = "Infracciones.findEtapa", query = "SELECT i FROM Infracciones i WHERE i.etapa = :etapa"),
    @NamedQuery(name = "Infracciones.findEstado", query = "SELECT i FROM Infracciones i WHERE i.estado = :estado"),
    @NamedQuery(name = "Infracciones.findEstado2", query = "SELECT i FROM Infracciones i WHERE i.estado2 = :estado2"),
    @NamedQuery(name = "Infracciones.findByFechaIniDP", query = "SELECT i FROM Infracciones i WHERE i.fechaIniDP = :fechaIniDP"),
    @NamedQuery(name = "Infracciones.findByFechaFinDP", query = "SELECT i FROM Infracciones i WHERE i.fechaFinDP = :fechaFinDP"),
    @NamedQuery(name = "Infracciones.findByFechaNovedad", query = "SELECT i FROM Infracciones i WHERE i.fechaNovedad = :fechaNovedad"),
    @NamedQuery(name = "Infracciones.findByArea", query = "SELECT i FROM Infracciones i WHERE i.area = :area"),
    @NamedQuery(name = "Infracciones.findByLinea", query = "SELECT i FROM Infracciones i WHERE i.linea = :linea"),
    @NamedQuery(name = "Infracciones.findByDireccion", query = "SELECT i FROM Infracciones i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "Infracciones.findByPlaca", query = "SELECT i FROM Infracciones i WHERE i.placa = :placa"),
    @NamedQuery(name = "Infracciones.findByMovil", query = "SELECT i FROM Infracciones i WHERE i.movil = :movil"),
    @NamedQuery(name = "Infracciones.findByNSAE", query = "SELECT i FROM Infracciones i WHERE i.nSAE = :nSAE"),
    @NamedQuery(name = "Infracciones.findByCedulaOperador", query = "SELECT i FROM Infracciones i WHERE i.cedulaOperador = :cedulaOperador"),
    @NamedQuery(name = "Infracciones.findByNombreOperador", query = "SELECT i FROM Infracciones i WHERE i.nombreOperador = :nombreOperador"),
    @NamedQuery(name = "Infracciones.findByPuntosICO", query = "SELECT i FROM Infracciones i WHERE i.puntosICO = :puntosICO"),
    @NamedQuery(name = "Infracciones.findByPuntosPMConciliados", query = "SELECT i FROM Infracciones i WHERE i.puntosPMConciliados = :puntosPMConciliados"),
    @NamedQuery(name = "Infracciones.findByDescripcion", query = "SELECT i FROM Infracciones i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Infracciones.findByCreado", query = "SELECT i FROM Infracciones i WHERE i.creado = :creado"),
    @NamedQuery(name = "Infracciones.findByModificado", query = "SELECT i FROM Infracciones i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "Infracciones.findByEstadoReg", query = "SELECT i FROM Infracciones i WHERE i.estadoReg = :estadoReg")})

public class Infracciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_infraccion")
    private Integer idInfraccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_ICO")
    private int idICO;
    @Basic(optional = false)
    @NotNull
    @Column(name = "etapa")
    private String etapa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ini_DP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIniDP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin_DP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinDP;
    @Column(name = "empresa")
    private String empresa;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Novedad idNovedad;
    @Column(name = "tipo_novedad")
    private String tipoNovedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_novedad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNovedad;
    @Column(name = "area")
    private String area;
    @Column(name = "linea")
    private String linea;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "placa")
    private String placa;
    @Column(name = "movil")
    private String movil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "n_SAE")
    private Integer nSAE;
    @Column(name = "cedula_operador")
    private Long cedulaOperador;
    @Column(name = "nombre_operador")
    private String nombreOperador;
    @Column(name = "puntos_ico")
    private Integer puntosICO;
    @Column(name = "puntos_pm_conciliados")
    private Integer puntosPMConciliados;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "estado2")
    private String estado2;
    @Size(max = 15)
    @Column(name = "username_create")
    private String usernameCreate;
    @Size(max = 15)
    @Column(name = "username_edit")
    private String usernameEdit;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    
    public Infracciones() {
    }

    public Infracciones(Integer idInfraccion) {
        this.idInfraccion = idInfraccion;
    }

    public Integer getIdInfraccion() {
        return idInfraccion;
    }

    public void setIdInfracciones(Integer idInfraccion) {
        this.idInfraccion = idInfraccion;
    }

    public int getIdICO() {
        return idICO;
    }

    public void setIdICO(int idICO) {
        this.idICO = idICO;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaIniDP() {
        return fechaIniDP;
    }

    public void setFechaIniDP(Date fechaIniDP) {
        this.fechaIniDP = fechaIniDP;
    }

    public Date getFechaFinDP() {
        return fechaFinDP;
    }

    public void setFechaFinDP(Date fechaFinDP) {
        this.fechaFinDP = fechaFinDP;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipoNovedad() {
        return tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public Integer getnSAE() {
        return nSAE;
    }

    public void setnSAE(Integer nSAE) {
        this.nSAE = nSAE;
    }

    public Long getCedulaOperador() {
        return cedulaOperador;
    }

    public void setCedulaOperador(Long cedulaOperador) {
        this.cedulaOperador = cedulaOperador;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public Integer getPuntosICO() {
        return puntosICO;
    }

    public void setPuntosICO(Integer puntosICO) {
        this.puntosICO = puntosICO;
    }

    public Integer getPuntosPMConciliados() {
        return puntosPMConciliados;
    }

    public void setPuntosPMConciliados(Integer puntosPMConciliados) {
        this.puntosPMConciliados = puntosPMConciliados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }

    public String getUsernameCreate() {
        return usernameCreate;
    }

    public void setUsernameCreate(String usernameCreate) {
        this.usernameCreate = usernameCreate;
    }

    public String getUsernameEdit() {
        return usernameEdit;
    }

    public void setUsernameEdit(String usernameEdit) {
        this.usernameEdit = usernameEdit;
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

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInfraccion != null ? idInfraccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Infracciones)) {
            return false;
        }
        Infracciones other = (Infracciones) object;
        return !((this.idInfraccion == null && other.idInfraccion != null) || (this.idInfraccion != null && !this.idInfraccion.equals(other.idInfraccion)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Infracciones[ idInfraccion=" + idInfraccion + " ]";
    }
    
}

