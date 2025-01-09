/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class SumEntradaSalidaDTO implements Serializable {

    private Integer sumPrg;
    private Integer sumEje;

    public SumEntradaSalidaDTO(Integer sumPrg, Integer sumEje) {
        this.sumPrg = sumPrg;
        this.sumEje = sumEje;
    }

    public Integer getSumPrg() {
        return sumPrg;
    }

    public void setSumPrg(Integer sumPrg) {
        this.sumPrg = sumPrg;
    }

    public Integer getSumEje() {
        return sumEje;
    }

    public void setSumEje(Integer sumEje) {
        this.sumEje = sumEje;
    }

}
