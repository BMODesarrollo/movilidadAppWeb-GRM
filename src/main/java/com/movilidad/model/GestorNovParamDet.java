/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "gestor_nov_param_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestorNovParamDet.findAll", query = "SELECT g FROM GestorNovParamDet g"),
    @NamedQuery(name = "GestorNovParamDet.findByIdGestorNovParamDet", query = "SELECT g FROM GestorNovParamDet g WHERE g.idGestorNovParamDet = :idGestorNovParamDet"),
    @NamedQuery(name = "GestorNovParamDet.findByDia1", query = "SELECT g FROM GestorNovParamDet g WHERE g.dia1 = :dia1"),
    @NamedQuery(name = "GestorNovParamDet.findByDia2", query = "SELECT g FROM GestorNovParamDet g WHERE g.dia2 = :dia2"),
    @NamedQuery(name = "GestorNovParamDet.findByDia3", query = "SELECT g FROM GestorNovParamDet g WHERE g.dia3 = :dia3"),
    @NamedQuery(name = "GestorNovParamDet.findByDia4", query = "SELECT g FROM GestorNovParamDet g WHERE g.dia4 = :dia4"),
    @NamedQuery(name = "GestorNovParamDet.findByDia5", query = "SELECT g FROM GestorNovParamDet g WHERE g.dia5 = :dia5"),
    @NamedQuery(name = "GestorNovParamDet.findByDia6", query = "SELECT g FROM GestorNovParamDet g WHERE g.dia6 = :dia6"),
    @NamedQuery(name = "GestorNovParamDet.findByDia7", query = "SELECT g FROM GestorNovParamDet g WHERE g.dia7 = :dia7")})
public class GestorNovParamDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestor_nov_param_det")
    private Integer idGestorNovParamDet;
    @Column(name = "dia1")
    private double dia1;
    @Column(name = "dia2")
    private double dia2;
    @Column(name = "dia3")
    private double dia3;
    @Column(name = "dia4")
    private double dia4;
    @Column(name = "dia5")
    private double dia5;
    @Column(name = "dia6")
    private double dia6;
    @Column(name = "dia7")
    private double dia7;
    @JoinColumn(name = "id_gestor_novedad_param", referencedColumnName = "id_gestor_novedad_param")
    @ManyToOne(fetch = FetchType.LAZY)
    private GestorNovedadParam idGestorNovedadParam;
    @JoinColumn(name = "id_gestor_novedad", referencedColumnName = "id_gestor_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private GestorNovedad idGestorNovedad;
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;

    public GestorNovParamDet() {
    }

    public GestorNovParamDet(Integer idGestorNovParamDet) {
        this.idGestorNovParamDet = idGestorNovParamDet;
    }

    public Integer getIdGestorNovParamDet() {
        return idGestorNovParamDet;
    }

    public void setIdGestorNovParamDet(Integer idGestorNovParamDet) {
        this.idGestorNovParamDet = idGestorNovParamDet;
    }

    public double getDia1() {
        return dia1;
    }

    public void setDia1(double dia1) {
        this.dia1 = dia1;
    }

    public double getDia2() {
        return dia2;
    }

    public void setDia2(double dia2) {
        this.dia2 = dia2;
    }

    public double getDia3() {
        return dia3;
    }

    public void setDia3(double dia3) {
        this.dia3 = dia3;
    }

    public double getDia4() {
        return dia4;
    }

    public void setDia4(double dia4) {
        this.dia4 = dia4;
    }

    public double getDia5() {
        return dia5;
    }

    public void setDia5(double dia5) {
        this.dia5 = dia5;
    }

    public double getDia6() {
        return dia6;
    }

    public void setDia6(double dia6) {
        this.dia6 = dia6;
    }

    public double getDia7() {
        return dia7;
    }

    public void setDia7(double dia7) {
        this.dia7 = dia7;
    }

    public GestorNovedadParam getIdGestorNovedadParam() {
        return idGestorNovedadParam;
    }

    public void setIdGestorNovedadParam(GestorNovedadParam idGestorNovedadParam) {
        this.idGestorNovedadParam = idGestorNovedadParam;
    }

    public GestorNovedad getIdGestorNovedad() {
        return idGestorNovedad;
    }

    public void setIdGestorNovedad(GestorNovedad idGestorNovedad) {
        this.idGestorNovedad = idGestorNovedad;
    }

    public EmpleadoTipoCargo getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(EmpleadoTipoCargo idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestorNovParamDet != null ? idGestorNovParamDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestorNovParamDet)) {
            return false;
        }
        GestorNovParamDet other = (GestorNovParamDet) object;
        if ((this.idGestorNovParamDet == null && other.idGestorNovParamDet != null) || (this.idGestorNovParamDet != null && !this.idGestorNovParamDet.equals(other.idGestorNovParamDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GestorNovParamDet[ idGestorNovParamDet=" + idGestorNovParamDet + " ]";
    }

}
