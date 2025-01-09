/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteCalificacionFacadeLocal;
import com.movilidad.model.AccidenteCalificacion;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author cesar
 */
@Named(value = "accLoginJSF")
@ViewScoped
public class AccLoginJSF implements Serializable {

    @EJB
    private AccidenteCalificacionFacadeLocal accidenteCalificacionFacadeLocal;

    private Integer iPin;
    private boolean bFlag;

    public AccLoginJSF() {
    }

    /**
     * Inicializa las variables del bean, toma un valor de la url.
     */
    @PostConstruct
    public void init() {
        bFlag = true;
        iPin = null;
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        String get = params.get("pin");
        if (get != null) {
            try {
                iPin = new Integer(get);
                bFlag = false;
            } catch (NumberFormatException e) {
            }
        }
    }

    /**
     * Verifica que el token se encuentre valido en el momento.
     */
    public void validarPin() {
        if (iPin != null) {
            List<AccidenteCalificacion> listAccC = accidenteCalificacionFacadeLocal.findByPin(new Date(), iPin, 1);
            if (listAccC != null && !listAccC.isEmpty()) {
                bFlag = false;
                return;
            }
        }
        MovilidadUtil.addErrorMessage("Pin no valido");
    }

    /**
     * Retorna url a la cual se de de acuerdo al parametro recibido.
     *
     * @param op int
     * @return String url
     */
    public String direccionar(int op) {
        if (iPin != null) {
            if (op == 1) {
                List<AccidenteCalificacion> listAccC = accidenteCalificacionFacadeLocal.findByPin(new Date(), iPin, 3);
                if (listAccC != null && !listAccC.isEmpty()) {
                    return "/public/page/evaluacionAcc.jsf?faces-redirect=true&pin=" + iPin;
                }
                MovilidadUtil.addErrorMessage("No se encuentran casos a evaluar");
            }
            if (op == 2) {
                List<AccidenteCalificacion> listAccC = accidenteCalificacionFacadeLocal.findByPin(new Date(), iPin, 2);
                if (listAccC != null && !listAccC.isEmpty()) {
                    return "/public/page/preEvaluacionAcc.jsf?faces-redirect=true&pin=" + iPin;
                }
                MovilidadUtil.addErrorMessage("No se encuentran casos para agregar causalidades.");
            }
        }
        return "#";
    }

    public Integer getiPin() {
        return iPin;
    }

    public void setiPin(Integer iPin) {
        this.iPin = iPin;
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

}
