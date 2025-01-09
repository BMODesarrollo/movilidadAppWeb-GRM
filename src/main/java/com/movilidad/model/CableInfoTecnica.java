/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "cable_info_tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableInfoTecnica.findAll", query = "SELECT c FROM CableInfoTecnica c"),
    @NamedQuery(name = "CableInfoTecnica.findByIdCableInfoTecnica", query = "SELECT c FROM CableInfoTecnica c WHERE c.idCableInfoTecnica = :idCableInfoTecnica"),
    @NamedQuery(name = "CableInfoTecnica.findByFecha", query = "SELECT c FROM CableInfoTecnica c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CableInfoTecnica.findByTempMotorPanel", query = "SELECT c FROM CableInfoTecnica c WHERE c.tempMotorPanel = :tempMotorPanel"),
    @NamedQuery(name = "CableInfoTecnica.findByTempCuartoVariador", query = "SELECT c FROM CableInfoTecnica c WHERE c.tempCuartoVariador = :tempCuartoVariador"),
    @NamedQuery(name = "CableInfoTecnica.findByTemReductor", query = "SELECT c FROM CableInfoTecnica c WHERE c.temReductor = :temReductor"),
    @NamedQuery(name = "CableInfoTecnica.findByTemReductorPanel", query = "SELECT c FROM CableInfoTecnica c WHERE c.temReductorPanel = :temReductorPanel"),
    @NamedQuery(name = "CableInfoTecnica.findByPar", query = "SELECT c FROM CableInfoTecnica c WHERE c.par = :par"),
    @NamedQuery(name = "CableInfoTecnica.findByPresionFsBar", query = "SELECT c FROM CableInfoTecnica c WHERE c.presionFsBar = :presionFsBar"),
    @NamedQuery(name = "CableInfoTecnica.findByPresionFeBar", query = "SELECT c FROM CableInfoTecnica c WHERE c.presionFeBar = :presionFeBar"),
    @NamedQuery(name = "CableInfoTecnica.findByCarroTensor1", query = "SELECT c FROM CableInfoTecnica c WHERE c.carroTensor1 = :carroTensor1"),
    @NamedQuery(name = "CableInfoTecnica.findByCarroTensor2", query = "SELECT c FROM CableInfoTecnica c WHERE c.carroTensor2 = :carroTensor2"),
    @NamedQuery(name = "CableInfoTecnica.findByLongitudLinea", query = "SELECT c FROM CableInfoTecnica c WHERE c.longitudLinea = :longitudLinea"),
    @NamedQuery(name = "CableInfoTecnica.findByVientoP06", query = "SELECT c FROM CableInfoTecnica c WHERE c.vientoP06 = :vientoP06"),
    @NamedQuery(name = "CableInfoTecnica.findByVientoP16", query = "SELECT c FROM CableInfoTecnica c WHERE c.vientoP16 = :vientoP16"),
    @NamedQuery(name = "CableInfoTecnica.findByVientoP23", query = "SELECT c FROM CableInfoTecnica c WHERE c.vientoP23 = :vientoP23"),
    @NamedQuery(name = "CableInfoTecnica.findByVientoP06Final", query = "SELECT c FROM CableInfoTecnica c WHERE c.vientoP06Final = :vientoP06Final"),
    @NamedQuery(name = "CableInfoTecnica.findByVientoP16Final", query = "SELECT c FROM CableInfoTecnica c WHERE c.vientoP16Final = :vientoP16Final"),
    @NamedQuery(name = "CableInfoTecnica.findByVientoP23Final", query = "SELECT c FROM CableInfoTecnica c WHERE c.vientoP23Final = :vientoP23Final"),
    @NamedQuery(name = "CableInfoTecnica.findByUsername", query = "SELECT c FROM CableInfoTecnica c WHERE c.username = :username"),
    @NamedQuery(name = "CableInfoTecnica.findByCreado", query = "SELECT c FROM CableInfoTecnica c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableInfoTecnica.findByModificado", query = "SELECT c FROM CableInfoTecnica c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableInfoTecnica.findByEstadoReg", query = "SELECT c FROM CableInfoTecnica c WHERE c.estadoReg = :estadoReg")})
public class CableInfoTecnica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_info_tecnica")
    private Integer idCableInfoTecnica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "temp_motor_panel")
    private Integer tempMotorPanel;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "temp_cuarto_variador")
    private BigDecimal tempCuartoVariador;
    @Column(name = "tem_reductor")
    private BigDecimal temReductor;
    @Column(name = "tem_reductor_panel")
    private Integer temReductorPanel;
    @Column(name = "par")
    private Integer par;
    @Column(name = "presion_fs_bar")
    private Integer presionFsBar;
    @Column(name = "presion_fe_bar")
    private Integer presionFeBar;
    @Column(name = "carro_tensor_1")
    private BigDecimal carroTensor1;
    @Column(name = "carro_tensor_2")
    private BigDecimal carroTensor2;
    @Column(name = "longitud_linea")
    private Integer longitudLinea;
    @Column(name = "viento_p06")
    private Integer vientoP06;
    @Column(name = "viento_p16")
    private Integer vientoP16;
    @Column(name = "viento_p23")
    private Integer vientoP23;
    @Column(name = "viento_p06_final")
    private Integer vientoP06Final;
    @Column(name = "viento_p16_final")
    private Integer vientoP16Final;
    @Column(name = "viento_p23_final")
    private Integer vientoP23Final;
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
    private Empleado empleado;

    public CableInfoTecnica() {
    }

    public CableInfoTecnica(Integer idCableInfoTecnica) {
        this.idCableInfoTecnica = idCableInfoTecnica;
    }

    public CableInfoTecnica(Integer idCableInfoTecnica, Date fecha, String username, Date creado, int estadoReg) {
        this.idCableInfoTecnica = idCableInfoTecnica;
        this.fecha = fecha;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdCableInfoTecnica() {
        return idCableInfoTecnica;
    }

    public void setIdCableInfoTecnica(Integer idCableInfoTecnica) {
        this.idCableInfoTecnica = idCableInfoTecnica;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTempMotorPanel() {
        return tempMotorPanel;
    }

    public void setTempMotorPanel(Integer tempMotorPanel) {
        this.tempMotorPanel = tempMotorPanel;
    }

    public BigDecimal getTempCuartoVariador() {
        return tempCuartoVariador;
    }

    public void setTempCuartoVariador(BigDecimal tempCuartoVariador) {
        this.tempCuartoVariador = tempCuartoVariador;
    }

    public BigDecimal getTemReductor() {
        return temReductor;
    }

    public void setTemReductor(BigDecimal temReductor) {
        this.temReductor = temReductor;
    }

    public Integer getTemReductorPanel() {
        return temReductorPanel;
    }

    public void setTemReductorPanel(Integer temReductorPanel) {
        this.temReductorPanel = temReductorPanel;
    }

    public Integer getPar() {
        return par;
    }

    public void setPar(Integer par) {
        this.par = par;
    }

    public Integer getPresionFsBar() {
        return presionFsBar;
    }

    public void setPresionFsBar(Integer presionFsBar) {
        this.presionFsBar = presionFsBar;
    }

    public Integer getPresionFeBar() {
        return presionFeBar;
    }

    public void setPresionFeBar(Integer presionFeBar) {
        this.presionFeBar = presionFeBar;
    }

    public BigDecimal getCarroTensor1() {
        return carroTensor1;
    }

    public void setCarroTensor1(BigDecimal carroTensor1) {
        this.carroTensor1 = carroTensor1;
    }

    public BigDecimal getCarroTensor2() {
        return carroTensor2;
    }

    public void setCarroTensor2(BigDecimal carroTensor2) {
        this.carroTensor2 = carroTensor2;
    }

    public Integer getLongitudLinea() {
        return longitudLinea;
    }

    public void setLongitudLinea(Integer longitudLinea) {
        this.longitudLinea = longitudLinea;
    }

    public Integer getVientoP06() {
        return vientoP06;
    }

    public void setVientoP06(Integer vientoP06) {
        this.vientoP06 = vientoP06;
    }

    public Integer getVientoP16() {
        return vientoP16;
    }

    public void setVientoP16(Integer vientoP16) {
        this.vientoP16 = vientoP16;
    }

    public Integer getVientoP23() {
        return vientoP23;
    }

    public void setVientoP23(Integer vientoP23) {
        this.vientoP23 = vientoP23;
    }

    public Integer getVientoP06Final() {
        return vientoP06Final;
    }

    public void setVientoP06Final(Integer vientoP06Final) {
        this.vientoP06Final = vientoP06Final;
    }

    public Integer getVientoP16Final() {
        return vientoP16Final;
    }

    public void setVientoP16Final(Integer vientoP16Final) {
        this.vientoP16Final = vientoP16Final;
    }

    public Integer getVientoP23Final() {
        return vientoP23Final;
    }

    public void setVientoP23Final(Integer vientoP23Final) {
        this.vientoP23Final = vientoP23Final;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableInfoTecnica != null ? idCableInfoTecnica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableInfoTecnica)) {
            return false;
        }
        CableInfoTecnica other = (CableInfoTecnica) object;
        if ((this.idCableInfoTecnica == null && other.idCableInfoTecnica != null) || (this.idCableInfoTecnica != null && !this.idCableInfoTecnica.equals(other.idCableInfoTecnica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableInfoTecnica[ idCableInfoTecnica=" + idCableInfoTecnica + " ]";
    }

}
