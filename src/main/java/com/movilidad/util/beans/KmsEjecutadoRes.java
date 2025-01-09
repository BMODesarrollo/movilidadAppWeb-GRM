package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@XmlRootElement
public class KmsEjecutadoRes implements Serializable {

    private BigDecimal hlpArt;
    private BigDecimal hlpBi;

    public KmsEjecutadoRes(BigDecimal hlpArt, BigDecimal hlpBi) {
        this.hlpArt = hlpArt;
        this.hlpBi = hlpBi;
    }

    public BigDecimal getHlpArt() {
        return hlpArt;
    }

    public void setHlpArt(BigDecimal hlpArt) {
        this.hlpArt = hlpArt;
    }

    public BigDecimal getHlpBi() {
        return hlpBi;
    }

    public void setHlpBi(BigDecimal hlpBi) {
        this.hlpBi = hlpBi;
    }
}
