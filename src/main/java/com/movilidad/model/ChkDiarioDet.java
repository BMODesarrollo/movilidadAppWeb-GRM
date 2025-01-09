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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "chk_diario_det")
@XmlRootElement
public class ChkDiarioDet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chk_diario_det")
    private Integer idChkDiarioDet;

    @JoinColumn(name = "id_chk_diario", referencedColumnName = "id_chk_diario")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChkDiario idChkDiario;

    @JoinColumn(name = "id_chk_componente_falla", referencedColumnName = "id_chk_componente_falla")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChkComponenteFalla idChkComponenteFalla;

    @JoinColumn(name = "id_chk_componente", referencedColumnName = "id_chk_componente")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChkComponente idChkComponente;

    // 1 = true,  0 = false
    @Column(name = "estado")
    private Integer estado;
    @Lob
    @Column(name = "observacion")
    private String observacion;

    public ChkDiarioDet() {
    }

    public ChkDiarioDet(Integer idChkDiarioDet) {
        this.idChkDiarioDet = idChkDiarioDet;
    }

    public Integer getIdChkDiarioDet() {
        return idChkDiarioDet;
    }

    public void setIdChkDiarioDet(Integer idChkDiarioDet) {
        this.idChkDiarioDet = idChkDiarioDet;
    }

    public ChkDiario getIdChkDiario() {
        return idChkDiario;
    }

    public void setIdChkDiario(ChkDiario idChkDiario) {
        this.idChkDiario = idChkDiario;
    }

    public ChkComponenteFalla getIdChkComponenteFalla() {
        return idChkComponenteFalla;
    }

    public void setIdChkComponenteFalla(ChkComponenteFalla idChkComponenteFalla) {
        this.idChkComponenteFalla = idChkComponenteFalla;
    }

    public ChkComponente getIdChkComponente() {
        return idChkComponente;
    }

    public void setIdChkComponente(ChkComponente idChkComponente) {
        this.idChkComponente = idChkComponente;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
