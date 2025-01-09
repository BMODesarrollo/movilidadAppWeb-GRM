package com.movilidad.util.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Kms Adicionales para informe de control.
 */
@XmlRootElement
public class KmsAdicionalesCtrl implements Serializable {

    private long count_adicionales_art;
    private long count_adicionales_bi;

    public KmsAdicionalesCtrl(long count_adicionales_art, long count_adicionales_bi) {
        this.count_adicionales_art = count_adicionales_art;
        this.count_adicionales_bi = count_adicionales_bi;
    }

    public long getCount_adicionales_art() {
        return count_adicionales_art;
    }

    public void setCount_adicionales_art(long count_adicionales_art) {
        this.count_adicionales_art = count_adicionales_art;
    }

    public long getCount_adicionales_bi() {
        return count_adicionales_bi;
    }

    public void setCount_adicionales_bi(long count_adicionales_bi) {
        this.count_adicionales_bi = count_adicionales_bi;
    }
}
