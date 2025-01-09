/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "gestor_nov_req_semana")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestorNovReqSemana.findAll", query = "SELECT g FROM GestorNovReqSemana g"),
    @NamedQuery(name = "GestorNovReqSemana.findByIdGestorNovReqSemana", query = "SELECT g FROM GestorNovReqSemana g WHERE g.idGestorNovReqSemana = :idGestorNovReqSemana"),
    @NamedQuery(name = "GestorNovReqSemana.findByDia1", query = "SELECT g FROM GestorNovReqSemana g WHERE g.dia1 = :dia1"),
    @NamedQuery(name = "GestorNovReqSemana.findByDia2", query = "SELECT g FROM GestorNovReqSemana g WHERE g.dia2 = :dia2"),
    @NamedQuery(name = "GestorNovReqSemana.findByDia3", query = "SELECT g FROM GestorNovReqSemana g WHERE g.dia3 = :dia3"),
    @NamedQuery(name = "GestorNovReqSemana.findByDia4", query = "SELECT g FROM GestorNovReqSemana g WHERE g.dia4 = :dia4"),
    @NamedQuery(name = "GestorNovReqSemana.findByDia5", query = "SELECT g FROM GestorNovReqSemana g WHERE g.dia5 = :dia5"),
    @NamedQuery(name = "GestorNovReqSemana.findByDia6", query = "SELECT g FROM GestorNovReqSemana g WHERE g.dia6 = :dia6"),
    @NamedQuery(name = "GestorNovReqSemana.findByDia7", query = "SELECT g FROM GestorNovReqSemana g WHERE g.dia7 = :dia7")})
public class GestorNovReqSemana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestor_nov_req_semana")
    private Integer idGestorNovReqSemana;
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
    @JoinColumn(name = "id_gestor_nov_requerimiento", referencedColumnName = "id_gestor_nov_requerimiento")
    @ManyToOne(fetch = FetchType.LAZY)
    private GestorNovRequerimiento idGestorNovRequerimiento;
    @JoinColumn(name = "id_gestor_novedad", referencedColumnName = "id_gestor_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private GestorNovedad idGestorNovedad;
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;

    public GestorNovReqSemana() {
    }

    public GestorNovReqSemana(Integer idGestorNovReqSemana) {
        this.idGestorNovReqSemana = idGestorNovReqSemana;
    }

    public GestorNovReqSemana(Integer idGestorNovReqSemana, Date desde, Date hasta) {
        this.idGestorNovReqSemana = idGestorNovReqSemana;
    }

    public Integer getIdGestorNovReqSemana() {
        return idGestorNovReqSemana;
    }

    public void setIdGestorNovReqSemana(Integer idGestorNovReqSemana) {
        this.idGestorNovReqSemana = idGestorNovReqSemana;
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

    public GestorNovRequerimiento getIdGestorNovRequerimiento() {
        return idGestorNovRequerimiento;
    }

    public void setIdGestorNovRequerimiento(GestorNovRequerimiento idGestorNovRequerimiento) {
        this.idGestorNovRequerimiento = idGestorNovRequerimiento;
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
        hash += (idGestorNovReqSemana != null ? idGestorNovReqSemana.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestorNovReqSemana)) {
            return false;
        }
        GestorNovReqSemana other = (GestorNovReqSemana) object;
        if ((this.idGestorNovReqSemana == null && other.idGestorNovReqSemana != null) || (this.idGestorNovReqSemana != null && !this.idGestorNovReqSemana.equals(other.idGestorNovReqSemana))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GestorNovReqSemana[ idGestorNovReqSemana=" + idGestorNovReqSemana + " ]";
    }

}
