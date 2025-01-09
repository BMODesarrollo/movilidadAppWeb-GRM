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
import javax.persistence.Lob;
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
 * @author HP
 */
@Entity
@Table(name = "notificacion_procesos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionProcesos.findAll", query = "SELECT n FROM NotificacionProcesos n"),
    @NamedQuery(name = "NotificacionProcesos.findByIdNotificacionProceso", query = "SELECT n FROM NotificacionProcesos n WHERE n.idNotificacionProceso = :idNotificacionProceso"),
    @NamedQuery(name = "NotificacionProcesos.findByCodigoProceso", query = "SELECT n FROM NotificacionProcesos n WHERE n.codigoProceso = :codigoProceso"),
    @NamedQuery(name = "NotificacionProcesos.findByNombreProceso", query = "SELECT n FROM NotificacionProcesos n WHERE n.nombreProceso = :nombreProceso"),
    @NamedQuery(name = "NotificacionProcesos.findByUsername", query = "SELECT n FROM NotificacionProcesos n WHERE n.username = :username"),
    @NamedQuery(name = "NotificacionProcesos.findByCreado", query = "SELECT n FROM NotificacionProcesos n WHERE n.creado = :creado"),
    @NamedQuery(name = "NotificacionProcesos.findByModificado", query = "SELECT n FROM NotificacionProcesos n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NotificacionProcesos.findByEstadoReg", query = "SELECT n FROM NotificacionProcesos n WHERE n.estadoReg = :estadoReg")})
public class NotificacionProcesos implements Serializable {

    @OneToMany(mappedBy = "idNotificacionProcesos", fetch = FetchType.LAZY)
    private List<MttoBateriaCritica> mttoBateriaCriticaList;

    @OneToMany(mappedBy = "idNotificacionProceso", fetch = FetchType.LAZY)
    private List<AccReincidencia> accReincidenciaList;
    @OneToMany(mappedBy = "idNotificacionProceso", fetch = FetchType.LAZY)
    private List<MttoDiasFs> mttoDiasFsList;
    @OneToMany(mappedBy = "idNotificacionProcesos", fetch = FetchType.LAZY)
    private List<CableEventoTipoDet> cableEventoTipoDetList;
    @OneToMany(mappedBy = "idNotificacionProceso", fetch = FetchType.LAZY)
    private List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList;
    @OneToMany(mappedBy = "idNotificacionProceso", fetch = FetchType.LAZY)
    private List<ChkComponente> chkComponenteList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion_proceso")
    private Integer idNotificacionProceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codigo_proceso")
    private String codigoProceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_proceso")
    private String nombreProceso;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "mensaje")
    private String mensaje;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "emails")
    private String emails;
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
    @OneToMany(mappedBy = "idNotificacionProcesos", fetch = FetchType.LAZY)
    private List<NovedadTipoDetalles> novedadTipoDetallesList;
    @OneToMany(mappedBy = "idNotificacionProceso", fetch = FetchType.LAZY)
    private List<NotificacionTelegramDet> notificacionTelegramDetList;
    @OneToMany(mappedBy = "idNotificacionProceso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NotificacionProcesoDet> notificacionProcesoDetList;

    public NotificacionProcesos() {
    }

    public NotificacionProcesos(Integer idNotificacionProceso) {
        this.idNotificacionProceso = idNotificacionProceso;
    }

    public NotificacionProcesos(Integer idNotificacionProceso, String codigoProceso, String nombreProceso, String mensaje, String emails, String username, Date creado, int estadoReg) {
        this.idNotificacionProceso = idNotificacionProceso;
        this.codigoProceso = codigoProceso;
        this.nombreProceso = nombreProceso;
        this.mensaje = mensaje;
        this.emails = emails;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNotificacionProceso() {
        return idNotificacionProceso;
    }

    public void setIdNotificacionProceso(Integer idNotificacionProceso) {
        this.idNotificacionProceso = idNotificacionProceso;
    }

    public String getCodigoProceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
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

    @XmlTransient
    public List<NovedadTipoDetalles> getNovedadTipoDetallesList() {
        return novedadTipoDetallesList;
    }

    public void setNovedadTipoDetallesList(List<NovedadTipoDetalles> novedadTipoDetallesList) {
        this.novedadTipoDetallesList = novedadTipoDetallesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacionProceso != null ? idNotificacionProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionProcesos)) {
            return false;
        }
        NotificacionProcesos other = (NotificacionProcesos) object;
        if ((this.idNotificacionProceso == null && other.idNotificacionProceso != null) || (this.idNotificacionProceso != null && !this.idNotificacionProceso.equals(other.idNotificacionProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NotificacionProcesos[ idNotificacionProceso=" + idNotificacionProceso + " ]";
    }

    @XmlTransient
    public List<PrgSolicitudNovedadParam> getPrgSolicitudNovedadParamList() {
        return prgSolicitudNovedadParamList;
    }

    public void setPrgSolicitudNovedadParamList(List<PrgSolicitudNovedadParam> prgSolicitudNovedadParamList) {
        this.prgSolicitudNovedadParamList = prgSolicitudNovedadParamList;
    }

    @XmlTransient
    public List<CableEventoTipoDet> getCableEventoTipoDetList() {
        return cableEventoTipoDetList;
    }

    public void setCableEventoTipoDetList(List<CableEventoTipoDet> cableEventoTipoDetList) {
        this.cableEventoTipoDetList = cableEventoTipoDetList;
    }

    @XmlTransient
    public List<AccReincidencia> getAccReincidenciaList() {
        return accReincidenciaList;
    }

    public void setAccReincidenciaList(List<AccReincidencia> accReincidenciaList) {
        this.accReincidenciaList = accReincidenciaList;
    }

    @XmlTransient
    public List<MttoDiasFs> getMttoDiasFsList() {
        return mttoDiasFsList;
    }

    public void setMttoDiasFsList(List<MttoDiasFs> mttoDiasFsList) {
        this.mttoDiasFsList = mttoDiasFsList;
    }

    @XmlTransient
    public List<MttoBateriaCritica> getMttoBateriaCriticaList() {
        return mttoBateriaCriticaList;
    }

    public void setMttoBateriaCriticaList(List<MttoBateriaCritica> mttoBateriaCriticaList) {
        this.mttoBateriaCriticaList = mttoBateriaCriticaList;
    }

    @XmlTransient
    public List<ChkComponente> getChkComponenteList() {
        return chkComponenteList;
    }

    public void setChkComponenteList(List<ChkComponente> chkComponenteList) {
        this.chkComponenteList = chkComponenteList;
    }

    @XmlTransient
    public List<NotificacionTelegramDet> getNotificacionTelegramDetList() {
        return notificacionTelegramDetList;
    }

    public void setNotificacionTelegramDetList(List<NotificacionTelegramDet> notificacionTelegramDetList) {
        this.notificacionTelegramDetList = notificacionTelegramDetList;
    }

    @XmlTransient
    public List<NotificacionProcesoDet> getNotificacionProcesoDetList() {
        return notificacionProcesoDetList;
    }

    public void setNotificacionProcesoDetList(List<NotificacionProcesoDet> notificacionProcesoDetList) {
        this.notificacionProcesoDetList = notificacionProcesoDetList;
    }

}
