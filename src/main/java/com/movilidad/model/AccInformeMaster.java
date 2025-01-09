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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_informe_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMaster.findAll", query = "SELECT a FROM AccInformeMaster a")
    , @NamedQuery(name = "AccInformeMaster.findByIdAccInformeMaster", query = "SELECT a FROM AccInformeMaster a WHERE a.idAccInformeMaster = :idAccInformeMaster")
    , @NamedQuery(name = "AccInformeMaster.findByLugar", query = "SELECT a FROM AccInformeMaster a WHERE a.lugar = :lugar")
    , @NamedQuery(name = "AccInformeMaster.findByFechaInforme", query = "SELECT a FROM AccInformeMaster a WHERE a.fechaInforme = :fechaInforme")
    , @NamedQuery(name = "AccInformeMaster.findByHoraEvento", query = "SELECT a FROM AccInformeMaster a WHERE a.horaEvento = :horaEvento")
    , @NamedQuery(name = "AccInformeMaster.findByTiempoReaccion", query = "SELECT a FROM AccInformeMaster a WHERE a.tiempoReaccion = :tiempoReaccion")
    , @NamedQuery(name = "AccInformeMaster.findByHoraFinEvento", query = "SELECT a FROM AccInformeMaster a WHERE a.horaFinEvento = :horaFinEvento")
    , @NamedQuery(name = "AccInformeMaster.findByVideoOperador", query = "SELECT a FROM AccInformeMaster a WHERE a.videoOperador = :videoOperador")
    , @NamedQuery(name = "AccInformeMaster.findByHoraVideo", query = "SELECT a FROM AccInformeMaster a WHERE a.horaVideo = :horaVideo")
    , @NamedQuery(name = "AccInformeMaster.findByAutorizaGrabacion", query = "SELECT a FROM AccInformeMaster a WHERE a.autorizaGrabacion = :autorizaGrabacion")
    , @NamedQuery(name = "AccInformeMaster.findByColillaTacografo", query = "SELECT a FROM AccInformeMaster a WHERE a.colillaTacografo = :colillaTacografo")
    , @NamedQuery(name = "AccInformeMaster.findByTelemetria", query = "SELECT a FROM AccInformeMaster a WHERE a.telemetria = :telemetria")
    , @NamedQuery(name = "AccInformeMaster.findByPathVideoOpe", query = "SELECT a FROM AccInformeMaster a WHERE a.pathVideoOpe = :pathVideoOpe")
    , @NamedQuery(name = "AccInformeMaster.findByPathBosquejo", query = "SELECT a FROM AccInformeMaster a WHERE a.pathBosquejo = :pathBosquejo")
    , @NamedQuery(name = "AccInformeMaster.findByUsername", query = "SELECT a FROM AccInformeMaster a WHERE a.username = :username")
    , @NamedQuery(name = "AccInformeMaster.findByCreado", query = "SELECT a FROM AccInformeMaster a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccInformeMaster.findByModificado", query = "SELECT a FROM AccInformeMaster a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccInformeMaster.findByEstadoReg", query = "SELECT a FROM AccInformeMaster a WHERE a.estadoReg = :estadoReg")})
public class AccInformeMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master")
    private Integer idAccInformeMaster;
    @Size(max = 255)
    @Column(name = "lugar")
    private String lugar;
    @Column(name = "fecha_informe")
    @Temporal(TemporalType.DATE)
    private Date fechaInforme;
    @Size(max = 15)
    @Column(name = "hora_evento")
    private String horaEvento;
    @Size(max = 15)
    @Column(name = "tiempo_reaccion")
    private String tiempoReaccion;
    @Size(max = 15)
    @Column(name = "hora_fin_evento")
    private String horaFinEvento;
    @Lob
    @Size(max = 65535)
    @Column(name = "version_master")
    private String versionMaster;
    @Lob
    @Size(max = 65535)
    @Column(name = "version_operador")
    private String versionOperador;
    @Column(name = "video_operador")
    private Integer videoOperador;
    @Size(max = 15)
    @Column(name = "hora_video")
    private String horaVideo;
    @Column(name = "autoriza_grabacion")
    private Integer autorizaGrabacion;
    @Column(name = "colilla_tacografo")
    private Integer colillaTacografo;
    @Column(name = "telemetria")
    private Integer telemetria;
    @Size(max = 255)
    @Column(name = "path_video_ope")
    private String pathVideoOpe;
    @Lob
    @Size(max = 65535)
    @Column(name = "firma_master")
    private String firmaMaster;
    @Size(max = 255)
    @Column(name = "path_bosquejo")
    private String pathBosquejo;
    @Size(max = 45)
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
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterVic> accInformeMasterVicList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterAgentes> accInformeMasterAgentesList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterTestigo> accInformeMasterTestigoList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterBomberos> accInformeMasterBomberosList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterInspectores> accInformeMasterInspectoresList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterApoyo> accInformeMasterApoyoList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterLesionado> accInformeMasterLesionadoList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterRecomotos> accInformeMasterRecomotosList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterMedicos> accInformeMasterMedicosList;
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;
    @JoinColumn(name = "id_empleado_master", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleadoMaster;
    @JoinColumn(name = "id_prg_stoppoint", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPrgStoppoint;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterVehCond> accInformeMasterVehCondList;
    @OneToMany(mappedBy = "idAccInformeMaster", fetch = FetchType.LAZY)
    private List<AccInformeMasterAlbum> accInformeMasterAlbumList;

    public AccInformeMaster() {
    }

    public AccInformeMaster(Integer idAccInformeMaster) {
        this.idAccInformeMaster = idAccInformeMaster;
    }

    public Integer getIdAccInformeMaster() {
        return idAccInformeMaster;
    }

    public void setIdAccInformeMaster(Integer idAccInformeMaster) {
        this.idAccInformeMaster = idAccInformeMaster;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFechaInforme() {
        return fechaInforme;
    }

    public void setFechaInforme(Date fechaInforme) {
        this.fechaInforme = fechaInforme;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getTiempoReaccion() {
        return tiempoReaccion;
    }

    public void setTiempoReaccion(String tiempoReaccion) {
        this.tiempoReaccion = tiempoReaccion;
    }

    public String getHoraFinEvento() {
        return horaFinEvento;
    }

    public void setHoraFinEvento(String horaFinEvento) {
        this.horaFinEvento = horaFinEvento;
    }

    public String getVersionMaster() {
        return versionMaster;
    }

    public void setVersionMaster(String versionMaster) {
        this.versionMaster = versionMaster;
    }

    public String getVersionOperador() {
        return versionOperador;
    }

    public void setVersionOperador(String versionOperador) {
        this.versionOperador = versionOperador;
    }

    public Integer getVideoOperador() {
        return videoOperador;
    }

    public void setVideoOperador(Integer videoOperador) {
        this.videoOperador = videoOperador;
    }

    public String getHoraVideo() {
        return horaVideo;
    }

    public void setHoraVideo(String horaVideo) {
        this.horaVideo = horaVideo;
    }

    public Integer getAutorizaGrabacion() {
        return autorizaGrabacion;
    }

    public void setAutorizaGrabacion(Integer autorizaGrabacion) {
        this.autorizaGrabacion = autorizaGrabacion;
    }

    public Integer getColillaTacografo() {
        return colillaTacografo;
    }

    public void setColillaTacografo(Integer colillaTacografo) {
        this.colillaTacografo = colillaTacografo;
    }

    public Integer getTelemetria() {
        return telemetria;
    }

    public void setTelemetria(Integer telemetria) {
        this.telemetria = telemetria;
    }

    public String getPathVideoOpe() {
        return pathVideoOpe;
    }

    public void setPathVideoOpe(String pathVideoOpe) {
        this.pathVideoOpe = pathVideoOpe;
    }

    public String getFirmaMaster() {
        return firmaMaster;
    }

    public void setFirmaMaster(String firmaMaster) {
        this.firmaMaster = firmaMaster;
    }

    public String getPathBosquejo() {
        return pathBosquejo;
    }

    public void setPathBosquejo(String pathBosquejo) {
        this.pathBosquejo = pathBosquejo;
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

    @XmlTransient
    public List<AccInformeMasterVic> getAccInformeMasterVicList() {
        return accInformeMasterVicList;
    }

    public void setAccInformeMasterVicList(List<AccInformeMasterVic> accInformeMasterVicList) {
        this.accInformeMasterVicList = accInformeMasterVicList;
    }

    @XmlTransient
    public List<AccInformeMasterAgentes> getAccInformeMasterAgentesList() {
        return accInformeMasterAgentesList;
    }

    public void setAccInformeMasterAgentesList(List<AccInformeMasterAgentes> accInformeMasterAgentesList) {
        this.accInformeMasterAgentesList = accInformeMasterAgentesList;
    }

    @XmlTransient
    public List<AccInformeMasterTestigo> getAccInformeMasterTestigoList() {
        return accInformeMasterTestigoList;
    }

    public void setAccInformeMasterTestigoList(List<AccInformeMasterTestigo> accInformeMasterTestigoList) {
        this.accInformeMasterTestigoList = accInformeMasterTestigoList;
    }

    @XmlTransient
    public List<AccInformeMasterBomberos> getAccInformeMasterBomberosList() {
        return accInformeMasterBomberosList;
    }

    public void setAccInformeMasterBomberosList(List<AccInformeMasterBomberos> accInformeMasterBomberosList) {
        this.accInformeMasterBomberosList = accInformeMasterBomberosList;
    }

    @XmlTransient
    public List<AccInformeMasterInspectores> getAccInformeMasterInspectoresList() {
        return accInformeMasterInspectoresList;
    }

    public void setAccInformeMasterInspectoresList(List<AccInformeMasterInspectores> accInformeMasterInspectoresList) {
        this.accInformeMasterInspectoresList = accInformeMasterInspectoresList;
    }

    @XmlTransient
    public List<AccInformeMasterApoyo> getAccInformeMasterApoyoList() {
        return accInformeMasterApoyoList;
    }

    public void setAccInformeMasterApoyoList(List<AccInformeMasterApoyo> accInformeMasterApoyoList) {
        this.accInformeMasterApoyoList = accInformeMasterApoyoList;
    }

    @XmlTransient
    public List<AccInformeMasterLesionado> getAccInformeMasterLesionadoList() {
        return accInformeMasterLesionadoList;
    }

    public void setAccInformeMasterLesionadoList(List<AccInformeMasterLesionado> accInformeMasterLesionadoList) {
        this.accInformeMasterLesionadoList = accInformeMasterLesionadoList;
    }

    @XmlTransient
    public List<AccInformeMasterRecomotos> getAccInformeMasterRecomotosList() {
        return accInformeMasterRecomotosList;
    }

    public void setAccInformeMasterRecomotosList(List<AccInformeMasterRecomotos> accInformeMasterRecomotosList) {
        this.accInformeMasterRecomotosList = accInformeMasterRecomotosList;
    }

    @XmlTransient
    public List<AccInformeMasterMedicos> getAccInformeMasterMedicosList() {
        return accInformeMasterMedicosList;
    }

    public void setAccInformeMasterMedicosList(List<AccInformeMasterMedicos> accInformeMasterMedicosList) {
        this.accInformeMasterMedicosList = accInformeMasterMedicosList;
    }

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    public Empleado getIdEmpleadoMaster() {
        return idEmpleadoMaster;
    }

    public void setIdEmpleadoMaster(Empleado idEmpleadoMaster) {
        this.idEmpleadoMaster = idEmpleadoMaster;
    }

    public PrgStopPoint getIdPrgStoppoint() {
        return idPrgStoppoint;
    }

    public void setIdPrgStoppoint(PrgStopPoint idPrgStoppoint) {
        this.idPrgStoppoint = idPrgStoppoint;
    }

    @XmlTransient
    public List<AccInformeMasterVehCond> getAccInformeMasterVehCondList() {
        return accInformeMasterVehCondList;
    }

    public void setAccInformeMasterVehCondList(List<AccInformeMasterVehCond> accInformeMasterVehCondList) {
        this.accInformeMasterVehCondList = accInformeMasterVehCondList;
    }

    @XmlTransient
    public List<AccInformeMasterAlbum> getAccInformeMasterAlbumList() {
        return accInformeMasterAlbumList;
    }

    public void setAccInformeMasterAlbumList(List<AccInformeMasterAlbum> accInformeMasterAlbumList) {
        this.accInformeMasterAlbumList = accInformeMasterAlbumList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInformeMaster != null ? idAccInformeMaster.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMaster)) {
            return false;
        }
        AccInformeMaster other = (AccInformeMaster) object;
        if ((this.idAccInformeMaster == null && other.idAccInformeMaster != null) || (this.idAccInformeMaster != null && !this.idAccInformeMaster.equals(other.idAccInformeMaster))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMaster[ idAccInformeMaster=" + idAccInformeMaster + " ]";
    }
    
}
