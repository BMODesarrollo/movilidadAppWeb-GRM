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
@Table(name = "gestor_tabla_tmp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestorTablaTmp.findAll", query = "SELECT g FROM GestorTablaTmp g"),
    @NamedQuery(name = "GestorTablaTmp.findByIdGestorTablaTmp", query = "SELECT g FROM GestorTablaTmp g WHERE g.idGestorTablaTmp = :idGestorTablaTmp"),
    @NamedQuery(name = "GestorTablaTmp.findByDia1", query = "SELECT g FROM GestorTablaTmp g WHERE g.dia1 = :dia1"),
    @NamedQuery(name = "GestorTablaTmp.findByDia2", query = "SELECT g FROM GestorTablaTmp g WHERE g.dia2 = :dia2"),
    @NamedQuery(name = "GestorTablaTmp.findByDia3", query = "SELECT g FROM GestorTablaTmp g WHERE g.dia3 = :dia3"),
    @NamedQuery(name = "GestorTablaTmp.findByDia4", query = "SELECT g FROM GestorTablaTmp g WHERE g.dia4 = :dia4"),
    @NamedQuery(name = "GestorTablaTmp.findByDia5", query = "SELECT g FROM GestorTablaTmp g WHERE g.dia5 = :dia5"),
    @NamedQuery(name = "GestorTablaTmp.findByDia6", query = "SELECT g FROM GestorTablaTmp g WHERE g.dia6 = :dia6"),
    @NamedQuery(name = "GestorTablaTmp.findByDia7", query = "SELECT g FROM GestorTablaTmp g WHERE g.dia7 = :dia7")})
public class GestorTablaTmp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestor_tabla_tmp")
    private Integer idGestorTablaTmp;
    @Column(name = "dia1")
    private Integer dia1;
    @Column(name = "dia2")
    private Integer dia2;
    @Column(name = "dia3")
    private Integer dia3;
    @Column(name = "dia4")
    private Integer dia4;
    @Column(name = "dia5")
    private Integer dia5;
    @Column(name = "dia6")
    private Integer dia6;
    @Column(name = "dia7")
    private Integer dia7;
    @JoinColumn(name = "id_novedad_tipo_detalle", referencedColumnName = "id_novedad_tipo_detalle", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadTipoDetalle;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;
    @Column(name = "suma_gestor")
    private Integer sumaGestor;

    public GestorTablaTmp() {
    }

    public GestorTablaTmp(Integer idGestorTablaTmp) {
        this.idGestorTablaTmp = idGestorTablaTmp;
    }

    public Integer getIdGestorTablaTmp() {
        return idGestorTablaTmp;
    }

    public void setIdGestorTablaTmp(Integer idGestorTablaTmp) {
        this.idGestorTablaTmp = idGestorTablaTmp;
    }

    public Integer getDia1() {
        return dia1;
    }

    public void setDia1(Integer dia1) {
        this.dia1 = dia1;
    }

    public Integer getDia2() {
        return dia2;
    }

    public void setDia2(Integer dia2) {
        this.dia2 = dia2;
    }

    public Integer getDia3() {
        return dia3;
    }

    public void setDia3(Integer dia3) {
        this.dia3 = dia3;
    }

    public Integer getDia4() {
        return dia4;
    }

    public void setDia4(Integer dia4) {
        this.dia4 = dia4;
    }

    public Integer getDia5() {
        return dia5;
    }

    public void setDia5(Integer dia5) {
        this.dia5 = dia5;
    }

    public Integer getDia6() {
        return dia6;
    }

    public void setDia6(Integer dia6) {
        this.dia6 = dia6;
    }

    public Integer getDia7() {
        return dia7;
    }

    public void setDia7(Integer dia7) {
        this.dia7 = dia7;
    }

    public NovedadTipoDetalles getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(NovedadTipoDetalles idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public EmpleadoTipoCargo getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(EmpleadoTipoCargo idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    public Integer getSumaGestor() {
        return sumaGestor;
    }

    public void setSumaGestor(Integer sumaGestor) {
        this.sumaGestor = sumaGestor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestorTablaTmp != null ? idGestorTablaTmp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestorTablaTmp)) {
            return false;
        }
        GestorTablaTmp other = (GestorTablaTmp) object;
        if ((this.idGestorTablaTmp == null && other.idGestorTablaTmp != null) || (this.idGestorTablaTmp != null && !this.idGestorTablaTmp.equals(other.idGestorTablaTmp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GestorTablaTmp[ idGestorTablaTmp=" + idGestorTablaTmp + " ]";
    }

}
