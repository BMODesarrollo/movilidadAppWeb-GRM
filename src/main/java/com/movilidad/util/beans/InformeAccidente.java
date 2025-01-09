package com.movilidad.util.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Información detallada de accidentes para informe de accidentes
 */
@XmlRootElement
public class InformeAccidente implements Serializable {

    private long incidente;
    private long TM01;
    private long TM02;
    private long TM16;

    public InformeAccidente(long incidente, long TM01, long TM02, long TM16) {
        this.incidente = incidente;
        this.TM01 = TM01;
        this.TM02 = TM02;
        this.TM16 = TM16;
    }

    public long getIncidente() {
        return incidente;
    }

    public void setIncidente(long incidente) {
        this.incidente = incidente;
    }

    public long getTM01() {
        return TM01;
    }

    public void setTM01(long TM01) {
        this.TM01 = TM01;
    }

    public long getTM02() {
        return TM02;
    }

    public void setTM02(long TM02) {
        this.TM02 = TM02;
    }

    public long getTM16() {
        return TM16;
    }

    public void setTM16(long TM16) {
        this.TM16 = TM16;
    }
}
