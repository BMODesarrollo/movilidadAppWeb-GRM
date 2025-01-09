package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class InformeContabilidad implements Serializable {

    private String codigo_vehiculo;
    private BigDecimal total;

    public InformeContabilidad(String codigo_vehiculo, BigDecimal total) {
        this.codigo_vehiculo = codigo_vehiculo;
        this.total = total;
    }

    public String getCodigo_vehiculo() {
        return codigo_vehiculo;
    }

    public void setCodigo_vehiculo(String codigo_vehiculo) {
        this.codigo_vehiculo = codigo_vehiculo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
