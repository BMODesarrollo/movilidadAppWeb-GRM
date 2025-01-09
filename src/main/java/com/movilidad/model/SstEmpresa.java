/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "sst_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstEmpresa.findAll", query = "SELECT s FROM SstEmpresa s"),
    @NamedQuery(name = "SstEmpresa.findByIdSstEmpresa", query = "SELECT s FROM SstEmpresa s WHERE s.idSstEmpresa = :idSstEmpresa"),
    @NamedQuery(name = "SstEmpresa.findByNitCedula", query = "SELECT s FROM SstEmpresa s WHERE s.nitCedula = :nitCedula"),
    @NamedQuery(name = "SstEmpresa.findByRazonSocial", query = "SELECT s FROM SstEmpresa s WHERE s.razonSocial = :razonSocial"),
    @NamedQuery(name = "SstEmpresa.findByNumeroDocRepresentante", query = "SELECT s FROM SstEmpresa s WHERE s.numeroDocRepresentante = :numeroDocRepresentante"),
    @NamedQuery(name = "SstEmpresa.findByNombreRepresentante", query = "SELECT s FROM SstEmpresa s WHERE s.nombreRepresentante = :nombreRepresentante"),
    @NamedQuery(name = "SstEmpresa.findByDireccionEmpresa", query = "SELECT s FROM SstEmpresa s WHERE s.direccionEmpresa = :direccionEmpresa"),
    @NamedQuery(name = "SstEmpresa.findByTelefonoEmpresa", query = "SELECT s FROM SstEmpresa s WHERE s.telefonoEmpresa = :telefonoEmpresa"),
    @NamedQuery(name = "SstEmpresa.findByEmailEmpresa", query = "SELECT s FROM SstEmpresa s WHERE s.emailEmpresa = :emailEmpresa"),
    @NamedQuery(name = "SstEmpresa.findByNumeroDocResponsable", query = "SELECT s FROM SstEmpresa s WHERE s.numeroDocResponsable = :numeroDocResponsable"),
    @NamedQuery(name = "SstEmpresa.findByNombreResponsable", query = "SELECT s FROM SstEmpresa s WHERE s.nombreResponsable = :nombreResponsable"),
    @NamedQuery(name = "SstEmpresa.findByTelefonoFijoResponsable", query = "SELECT s FROM SstEmpresa s WHERE s.telefonoFijoResponsable = :telefonoFijoResponsable"),
    @NamedQuery(name = "SstEmpresa.findByTelefonoMovilResponsable", query = "SELECT s FROM SstEmpresa s WHERE s.telefonoMovilResponsable = :telefonoMovilResponsable"),
    @NamedQuery(name = "SstEmpresa.findByEmailResponsable", query = "SELECT s FROM SstEmpresa s WHERE s.emailResponsable = :emailResponsable"),
    @NamedQuery(name = "SstEmpresa.findByUsrNombre", query = "SELECT s FROM SstEmpresa s WHERE s.usrNombre = :usrNombre"),
    @NamedQuery(name = "SstEmpresa.findByUsername", query = "SELECT s FROM SstEmpresa s WHERE s.username = :username"),
    @NamedQuery(name = "SstEmpresa.findByCreado", query = "SELECT s FROM SstEmpresa s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstEmpresa.findByModificado", query = "SELECT s FROM SstEmpresa s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstEmpresa.findByEstadoReg", query = "SELECT s FROM SstEmpresa s WHERE s.estadoReg = :estadoReg")})
public class SstEmpresa implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSstEmpresa", fetch = FetchType.LAZY)
    private List<InformeSeguridad> informeSeguridadList;

    @OneToMany(mappedBy = "idSstEmpresa", fetch = FetchType.LAZY)
    private List<SstAutorizacion> sstAutorizacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSstEmpresa", fetch = FetchType.LAZY)
    private List<SstEmpresaDocs> sstEmpresaDocsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSstEmpresa", fetch = FetchType.LAZY)
    private List<SstEmpresaVisitante> sstEmpresaVisitanteList;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_empresa")
    private Integer idSstEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nit_cedula")
    private String nitCedula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "razon_social")
    private String razonSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numero_doc_representante")
    private String numeroDocRepresentante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "nombre_representante")
    private String nombreRepresentante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "direccion_empresa")
    private String direccionEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "telefono_empresa")
    private String telefonoEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "email_empresa")
    private String emailEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numero_doc_responsable")
    private String numeroDocResponsable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "nombre_responsable")
    private String nombreResponsable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "telefono_fijo_responsable")
    private String telefonoFijoResponsable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "telefono_movil_responsable")
    private String telefonoMovilResponsable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "email_responsable")
    private String emailResponsable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usr_nombre")
    private String usrNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
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
    @JoinColumn(name = "id_sst_arl", referencedColumnName = "id_sst_arl")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstArl idSstArl;
    @JoinColumn(name = "id_sst_empresa_tipo", referencedColumnName = "id_sst_empresa_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstEmpresaTipo idSstEmpresaTipo;
    @JoinColumn(name = "id_sst_tipo_identificacion_representante", referencedColumnName = "id_sst_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstTipoIdentificacion idSstTipoIdentificacionRepresentante;
    @JoinColumn(name = "id_sst_tipo_identificacion_responsable", referencedColumnName = "id_sst_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstTipoIdentificacion idSstTipoIdentificacionResponsable;

    public SstEmpresa() {
    }

    public SstEmpresa(Integer idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }

    public SstEmpresa(Integer idSstEmpresa, String nitCedula, String razonSocial, String numeroDocRepresentante, String nombreRepresentante, String direccionEmpresa, String telefonoEmpresa, String emailEmpresa, String numeroDocResponsable, String nombreResponsable, String telefonoFijoResponsable, String telefonoMovilResponsable, String emailResponsable, String usrNombre, String username) {
        this.idSstEmpresa = idSstEmpresa;
        this.nitCedula = nitCedula;
        this.razonSocial = razonSocial;
        this.numeroDocRepresentante = numeroDocRepresentante;
        this.nombreRepresentante = nombreRepresentante;
        this.direccionEmpresa = direccionEmpresa;
        this.telefonoEmpresa = telefonoEmpresa;
        this.emailEmpresa = emailEmpresa;
        this.numeroDocResponsable = numeroDocResponsable;
        this.nombreResponsable = nombreResponsable;
        this.telefonoFijoResponsable = telefonoFijoResponsable;
        this.telefonoMovilResponsable = telefonoMovilResponsable;
        this.emailResponsable = emailResponsable;
        this.usrNombre = usrNombre;
        this.username = username;
    }

    public Integer getIdSstEmpresa() {
        return idSstEmpresa;
    }

    public void setIdSstEmpresa(Integer idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }

    public String getNitCedula() {
        return nitCedula;
    }

    public void setNitCedula(String nitCedula) {
        this.nitCedula = nitCedula;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNumeroDocRepresentante() {
        return numeroDocRepresentante;
    }

    public void setNumeroDocRepresentante(String numeroDocRepresentante) {
        this.numeroDocRepresentante = numeroDocRepresentante;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getNumeroDocResponsable() {
        return numeroDocResponsable;
    }

    public void setNumeroDocResponsable(String numeroDocResponsable) {
        this.numeroDocResponsable = numeroDocResponsable;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getTelefonoFijoResponsable() {
        return telefonoFijoResponsable;
    }

    public void setTelefonoFijoResponsable(String telefonoFijoResponsable) {
        this.telefonoFijoResponsable = telefonoFijoResponsable;
    }

    public String getTelefonoMovilResponsable() {
        return telefonoMovilResponsable;
    }

    public void setTelefonoMovilResponsable(String telefonoMovilResponsable) {
        this.telefonoMovilResponsable = telefonoMovilResponsable;
    }

    public String getEmailResponsable() {
        return emailResponsable;
    }

    public void setEmailResponsable(String emailResponsable) {
        this.emailResponsable = emailResponsable;
    }

    public String getUsrNombre() {
        return usrNombre;
    }

    public void setUsrNombre(String usrNombre) {
        this.usrNombre = usrNombre;
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

    public SstArl getIdSstArl() {
        return idSstArl;
    }

    public void setIdSstArl(SstArl idSstArl) {
        this.idSstArl = idSstArl;
    }

    public SstTipoIdentificacion getIdSstTipoIdentificacionRepresentante() {
        return idSstTipoIdentificacionRepresentante;
    }

    public void setIdSstTipoIdentificacionRepresentante(SstTipoIdentificacion idSstTipoIdentificacionRepresentante) {
        this.idSstTipoIdentificacionRepresentante = idSstTipoIdentificacionRepresentante;
    }

    public SstTipoIdentificacion getIdSstTipoIdentificacionResponsable() {
        return idSstTipoIdentificacionResponsable;
    }

    public void setIdSstTipoIdentificacionResponsable(SstTipoIdentificacion idSstTipoIdentificacionResponsable) {
        this.idSstTipoIdentificacionResponsable = idSstTipoIdentificacionResponsable;
    }

    public SstEmpresaTipo getIdSstEmpresaTipo() {
        return idSstEmpresaTipo;
    }

    public void setIdSstEmpresaTipo(SstEmpresaTipo idSstEmpresaTipo) {
        this.idSstEmpresaTipo = idSstEmpresaTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstEmpresa != null ? idSstEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstEmpresa)) {
            return false;
        }
        SstEmpresa other = (SstEmpresa) object;
        if ((this.idSstEmpresa == null && other.idSstEmpresa != null) || (this.idSstEmpresa != null && !this.idSstEmpresa.equals(other.idSstEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstEmpresa[ idSstEmpresa=" + idSstEmpresa + " ]";
    }

    @XmlTransient
    public List<SstEmpresaVisitante> getSstEmpresaVisitanteList() {
        return sstEmpresaVisitanteList;
    }

    public void setSstEmpresaVisitanteList(List<SstEmpresaVisitante> sstEmpresaVisitanteList) {
        this.sstEmpresaVisitanteList = sstEmpresaVisitanteList;
    }

    @XmlTransient
    public List<SstEmpresaDocs> getSstEmpresaDocsList() {
        return sstEmpresaDocsList;
    }

    public void setSstEmpresaDocsList(List<SstEmpresaDocs> sstEmpresaDocsList) {
        this.sstEmpresaDocsList = sstEmpresaDocsList;
    }

    @XmlTransient
    public List<SstAutorizacion> getSstAutorizacionList() {
        return sstAutorizacionList;
    }

    public void setSstAutorizacionList(List<SstAutorizacion> sstAutorizacionList) {
        this.sstAutorizacionList = sstAutorizacionList;
    }

    @XmlTransient
    public List<InformeSeguridad> getInformeSeguridadList() {
        return informeSeguridadList;
    }

    public void setInformeSeguridadList(List<InformeSeguridad> informeSeguridadList) {
        this.informeSeguridadList = informeSeguridadList;
    }

}
