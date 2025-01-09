package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class InformeContabilidad235 implements Serializable {

    private String codigo_vehiculo;
    private BigDecimal km_com_235;
    private BigDecimal km_hlp_235;

    public InformeContabilidad235(String codigo_vehiculo, BigDecimal km_com_235, BigDecimal km_hlp_235) {
        this.codigo_vehiculo = codigo_vehiculo;
        this.km_com_235 = km_com_235;
        this.km_hlp_235 = km_hlp_235;
    }

    public String getCodigo_vehiculo() {
        return codigo_vehiculo;
    }

    public void setCodigo_vehiculo(String codigo_vehiculo) {
        this.codigo_vehiculo = codigo_vehiculo;
    }

    public BigDecimal getKm_com_235() {
        return km_com_235;
    }

    public void setKm_com_235(BigDecimal km_com_235) {
        this.km_com_235 = km_com_235;
    }

    public BigDecimal getKm_hlp_235() {
        return km_hlp_235;
    }

    public void setKm_hlp_235(BigDecimal km_hlp_235) {
        this.km_hlp_235 = km_hlp_235;
    }

}
