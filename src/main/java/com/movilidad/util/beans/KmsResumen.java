package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Devuelve kms adicionales y eliminados para resúmen de kms (Módulo conciliación).
 */
@XmlRootElement
public class KmsResumen implements Serializable {

    private BigDecimal AdcArt;
    private BigDecimal AdcBi;
    private BigDecimal ElimArt;
    private BigDecimal ElimBi;

    public KmsResumen(BigDecimal AdcArt, BigDecimal AdcBi, BigDecimal ElimArt, BigDecimal ElimBi) {
        this.AdcArt = AdcArt;
        this.AdcBi = AdcBi;
        this.ElimArt = ElimArt;
        this.ElimBi = ElimBi;
    }

    public BigDecimal getAdcArt() {
        return AdcArt;
    }

    public void setAdcArt(BigDecimal AdcArt) {
        this.AdcArt = AdcArt;
    }

    public BigDecimal getAdcBi() {
        return AdcBi;
    }

    public void setAdcBi(BigDecimal AdcBi) {
        this.AdcBi = AdcBi;
    }

    public BigDecimal getElimArt() {
        return ElimArt;
    }

    public void setElimArt(BigDecimal ElimArt) {
        this.ElimArt = ElimArt;
    }

    public BigDecimal getElimBi() {
        return ElimBi;
    }

    public void setElimBi(BigDecimal ElimBi) {
        this.ElimBi = ElimBi;
    }
}
