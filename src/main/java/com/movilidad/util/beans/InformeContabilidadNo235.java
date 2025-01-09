package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class InformeContabilidadNo235 implements Serializable {

    private String codigo_vehiculo;
    private BigDecimal comercial;
    private BigDecimal hlp;

    public InformeContabilidadNo235(String codigo_vehiculo, BigDecimal comercial, BigDecimal hlp) {
        this.codigo_vehiculo = codigo_vehiculo;
        this.comercial = comercial;
        this.hlp = hlp;
    }

    public String getCodigo_vehiculo() {
        return codigo_vehiculo;
    }

    public void setCodigo_vehiculo(String codigo_vehiculo) {
        this.codigo_vehiculo = codigo_vehiculo;
    }

    public BigDecimal getComercial() {
        return comercial;
    }

    public void setComercial(BigDecimal comercial) {
        this.comercial = comercial;
    }

    public BigDecimal getHlp() {
        return hlp;
    }

    public void setHlp(BigDecimal hlp) {
        this.hlp = hlp;
    }

}
