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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "disp_clasificacion_novedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispClasificacionNovedad.findAll", query = "SELECT d FROM DispClasificacionNovedad d")
    , @NamedQuery(name = "DispClasificacionNovedad.findByIdDispClasificacionNovedad", query = "SELECT d FROM DispClasificacionNovedad d WHERE d.idDispClasificacionNovedad = :idDispClasificacionNovedad")
    , @NamedQuery(name = "DispClasificacionNovedad.findByObservacion", query = "SELECT d FROM DispClasificacionNovedad d WHERE d.observacion = :observacion")
    , @NamedQuery(name = "DispClasificacionNovedad.findByUsername", query = "SELECT d FROM DispClasificacionNovedad d WHERE d.username = :username")
    , @NamedQuery(name = "DispClasificacionNovedad.findByCreado", query = "SELECT d FROM DispClasificacionNovedad d WHERE d.creado = :creado")
    , @NamedQuery(name = "DispClasificacionNovedad.findByModificado", query = "SELECT d FROM DispClasificacionNovedad d WHERE d.modificado = :modificado")
    , @NamedQuery(name = "DispClasificacionNovedad.findByEstadoReg", query = "SELECT d FROM DispClasificacionNovedad d WHERE d.estadoReg = :estadoReg")})
public class DispClasificacionNovedad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_clasificacion_novedad")
    private Integer idDispClasificacionNovedad;
    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_disp_causa_entrada", referencedColumnName = "id_disp_causa_entrada")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DispCausaEntrada idDispCausaEntrada;
    @JoinColumn(name = "id_disp_estado_pend_actual", referencedColumnName = "id_disp_estado_pend_actual")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispEstadoPendActual idDispEstadoPendActual;
    @JoinColumn(name = "id_disp_sistema", referencedColumnName = "id_disp_sistema")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DispSistema idDispSistema;
    @Column(name = "fecha_habilitacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHabilitacion;
    @Size(max = 255)
    @Column(name = "mx_ticket_id")
    private String mxTicketId;
    @Column(name = "enviar_maximo")
    private int enviarMaximo;

    @OneToMany(mappedBy = "idDispClasificacionNovedad", fetch = FetchType.LAZY)
    private List<Novedad> noveadadList;

    public DispClasificacionNovedad() {
    }

    public DispClasificacionNovedad(Integer idDispClasificacionNovedad) {
        this.idDispClasificacionNovedad = idDispClasificacionNovedad;
    }

    public DispClasificacionNovedad(Integer idDispClasificacionNovedad, String username, Date creado, int estadoReg) {
        this.idDispClasificacionNovedad = idDispClasificacionNovedad;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdDispClasificacionNovedad() {
        return idDispClasificacionNovedad;
    }

    public void setIdDispClasificacionNovedad(Integer idDispClasificacionNovedad) {
        this.idDispClasificacionNovedad = idDispClasificacionNovedad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public DispCausaEntrada getIdDispCausaEntrada() {
        return idDispCausaEntrada;
    }

    public void setIdDispCausaEntrada(DispCausaEntrada idDispCausaEntrada) {
        this.idDispCausaEntrada = idDispCausaEntrada;
    }

    public DispEstadoPendActual getIdDispEstadoPendActual() {
        return idDispEstadoPendActual;
    }

    public void setIdDispEstadoPendActual(DispEstadoPendActual idDispEstadoPendActual) {
        this.idDispEstadoPendActual = idDispEstadoPendActual;
    }

    public DispSistema getIdDispSistema() {
        return idDispSistema;
    }

    public void setIdDispSistema(DispSistema idDispSistema) {
        this.idDispSistema = idDispSistema;
    }

    @XmlTransient
    public List<Novedad> getNoveadadList() {
        return noveadadList;
    }

    public void setNoveadadList(List<Novedad> noveadadList) {
        this.noveadadList = noveadadList;
    }

    public Date getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(Date fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    public String getMxTicketId() {
        return mxTicketId;
    }

    public void setMxTicketId(String mxTicketId) {
        this.mxTicketId = mxTicketId;
    }

    public int getEnviarMaximo() {
        return enviarMaximo;
    }

    public void setEnviarMaximo(int enviarMaximo) {
        this.enviarMaximo = enviarMaximo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispClasificacionNovedad != null ? idDispClasificacionNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispClasificacionNovedad)) {
            return false;
        }
        DispClasificacionNovedad other = (DispClasificacionNovedad) object;
        if ((this.idDispClasificacionNovedad == null && other.idDispClasificacionNovedad != null) || (this.idDispClasificacionNovedad != null && !this.idDispClasificacionNovedad.equals(other.idDispClasificacionNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispClasificacionNovedad[ idDispClasificacionNovedad=" + idDispClasificacionNovedad + " ]";
    }

}
