/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.CableOperacionCabinaFacadeLocal;
import com.movilidad.ejb.CablePinzaFacadeLocal;
import com.movilidad.model.CableCabina;
import com.movilidad.model.CableOperacionCabina;
import com.movilidad.model.CablePinza;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "horometroUpdateJSFMB")
@ViewScoped
public class HorometroUpdateJSFMB implements Serializable {

    /**
     * Creates a new instance of HorometroUpdateJSFMB
     */
    public HorometroUpdateJSFMB() {
    }

    @EJB
    private CableCabinaFacadeLocal cableCabinaEJB;
    @EJB
    private CablePinzaFacadeLocal cablePinzaEJB;
    @EJB
    private CableOperacionCabinaFacadeLocal CableOperacionCabinaEJB;

    private List<CableCabina> listaCabina;

    public void horometroCabinas(double AntesHorometroDia, double despuesHorometroDia, Date fecha, String user) {
        double horometroDia;
        if (AntesHorometroDia > 0) {
            horometroDia = despuesHorometroDia - AntesHorometroDia;
        } else {
            horometroDia = despuesHorometroDia;
        }
        listaCabina = cableCabinaEJB.findAllByEstadoReg();
        for (CableCabina cc : listaCabina) {
            CableOperacionCabina coc = CableOperacionCabinaEJB
                    .findByIdCableCabinaAndFecha(cc.getIdCableCabina(), fecha);
            if (coc == null) {
                coc = new CableOperacionCabina();
                coc.setIdCableCabina(cc);
                coc.setIdCablePinza(cc.getIdCablePinza());
                coc.setCreado(MovilidadUtil.fechaCompletaHoy());
                coc.setEstadoReg(0);
                coc.setHoras(new BigDecimal(despuesHorometroDia));
                coc.setOperando(1);
                coc.setUsername(user);
                coc.setFecha(fecha);
                CableOperacionCabinaEJB.create(coc);
            }
            if (coc.getOperando() > 0) {
                coc.setModificado(MovilidadUtil.fechaCompletaHoy());
                coc.setUsername(user);
                /**
                 * Horometro para registro de operacion cabina
                 */
                double horometroOperacionCabina = coc.getHoras().doubleValue();
                horometroOperacionCabina = horometroOperacionCabina + horometroDia;
                coc.setHoras(new BigDecimal(horometroOperacionCabina));
                CableOperacionCabinaEJB.edit(coc);
                /**
                 * Horometro para registro de cabina
                 */
                double horometroCabina = cc.getHorometro().doubleValue();
                horometroCabina = horometroCabina + horometroDia;
                cc.setHorometro(new BigDecimal(horometroCabina));

                CablePinza cablePinza;
                if (coc.getIdCablePinzaParaHoy() != null) {
                    cablePinza = coc.getIdCablePinzaParaHoy();
                } else {
                    cablePinza = coc.getIdCablePinza();
                }
                if (cablePinza != null) {
                    /**
                     * Horometro para registro de operacion pinza
                     */
                    double horometroPinza = cablePinza.getHorometro().doubleValue();
                    horometroPinza = horometroPinza + horometroDia;
                    cablePinza.setHorometro(new BigDecimal(horometroPinza));
                    cablePinzaEJB.edit(cablePinza);
                }
                cableCabinaEJB.edit(cc);
            }
        }
    }
}
