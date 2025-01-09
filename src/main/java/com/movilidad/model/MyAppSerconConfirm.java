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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "my_app_sercon_confirm")
@XmlRootElement
public class MyAppSerconConfirm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_my_app_sercon_confirm")
    private Integer idMyAppSerconConfirm;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "verbo")
    private String verbo;
    @Column(name = "id_stop")
    private Integer isStop;
    @Column(name = "id_task")
    private Integer idTask;
    @Column(name = "procesado")
    private Integer procesado; // default 0
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Basic(optional = false)
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_prg_stoppoint", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPrgStopPoint;
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTc idPrgTc;

    @OneToMany(mappedBy = "idMyAppSerconConfirm", fetch = FetchType.LAZY)
    private List<PrgSercon> prgSerconList;

    public MyAppSerconConfirm() {
    }

    public Integer getIdMyAppSerconConfirm() {
        return idMyAppSerconConfirm;
    }

    public void setIdMyAppSerconConfirm(Integer idMyAppSerconConfirm) {
        this.idMyAppSerconConfirm = idMyAppSerconConfirm;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getVerbo() {
        return verbo;
    }

    public void setVerbo(String verbo) {
        this.verbo = verbo;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public Integer getProcesado() {
        return procesado;
    }

    public void setProcesado(Integer procesado) {
        this.procesado = procesado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public PrgStopPoint getIdPrgStopPoint() {
        return idPrgStopPoint;
    }

    public void setIdPrgStopPoint(PrgStopPoint idPrgStopPoint) {
        this.idPrgStopPoint = idPrgStopPoint;
    }

    public PrgTc getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(PrgTc idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    @XmlTransient
    public List<PrgSercon> getPrgSerconList() {
        return prgSerconList;
    }

    public void setPrgSerconList(List<PrgSercon> prgSerconList) {
        this.prgSerconList = prgSerconList;
    }

    @Override
    public String toString() {
        return "MyAppSerconConfirm{" + "idMyAppSerconConfirm=" + idMyAppSerconConfirm + ", identificacion=" + identificacion + ", verbo=" + verbo + ", isStop=" + isStop + ", idTask=" + idTask + ", procesado=" + procesado + ", fecha=" + fecha + ", creado=" + creado + ", modificado=" + modificado + ", estadoReg=" + estadoReg + ", idEmpleado=" + idEmpleado + ", idPrgStopPoint=" + idPrgStopPoint + ", idPrgTc=" + idPrgTc + '}';
    }

}
