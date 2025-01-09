/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteAnalisisFacadeLocal;
import com.movilidad.ejb.AccidenteCalificacionDetFacadeLocal;
import com.movilidad.ejb.AccidenteCalificacionFacadeLocal;
import com.movilidad.ejb.AccidentePreCalificacionFacadeLocal;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteAnalisis;
import com.movilidad.model.AccidenteCalificacion;
import com.movilidad.model.AccidenteCalificacionDet;
import com.movilidad.model.AccidentePreCalificacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "evaluacionAccJSF")
@ViewScoped
public class EvaluacionAccJSF implements Serializable {

    @EJB
    private AccidenteCalificacionFacadeLocal accidenteCalificacionFacadeLocal;
    @EJB
    private AccidenteAnalisisFacadeLocal accidenteAnalisisFacadeLocal;
    @EJB
    private AccidenteCalificacionDetFacadeLocal accidenteCalificacionDetFacadeLocal;
    @EJB
    private AccidentePreCalificacionFacadeLocal accidentePreCalificacionFacadeLocal;

    private List<AccidenteCalificacion> listAccidenteCalificacion;
    private List<AccidenteCalificacion> listAccidenteCalificacionResumen;

    private Integer iPin;
    private Date dFecha;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public EvaluacionAccJSF() {
    }

    @PostConstruct
    public void init() {
        listAccidenteCalificacion = new ArrayList<>();
        listAccidenteCalificacionResumen = new ArrayList<>();
        iPin = null;
        dFecha = new Date();
    }

    public void guardar() {
        try {
            if (!listAccidenteCalificacion.isEmpty()) {
                if (iPin != null) {
                    if (String.valueOf(iPin).length() != 6) {
                        MovilidadUtil.addErrorMessage("Pin debe ser de 6 dígitos");
                        return;
                    }
                    Date d = new Date();
                    if (validarPin(d)) {
                        MovilidadUtil.addErrorMessage("Pin no valido, intente con otro");
                        return;
                    }
                    for (AccidenteCalificacion ac : listAccidenteCalificacion) {
                        ac.setCalificado(0);
                        ac.setPinReunion(iPin);
                        ac.setFechaCalificacion(d);
                        ac.setCreado(d);
                        ac.setUsername(user.getUsername());
                        ac.setEstadoReg(0);
                        accidenteCalificacionFacadeLocal.create(ac);
                    }
                    MovilidadUtil.addSuccessMessage("Casos de accidentalidad se encuentran disponibles para evaluación, "
                            + "fecha limite hasta las 23:59 de "
                            + Util.dateFormat(d));
                    listAccidenteCalificacion.clear();
                    listAccidenteCalificacionResumen = accidenteCalificacionFacadeLocal.findByPin(d, iPin, 1);
                } else {
                    MovilidadUtil.addErrorMessage("Pin de la reunión es requerido");
                }
            } else {
                MovilidadUtil.addErrorMessage("No se agregaron casos de accidentalidad al proceso");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(AccidenteCalificacion ac) {
        listAccidenteCalificacion.remove(ac);
        MovilidadUtil.addSuccessMessage("Accidente removido de la lista");
    }

    public void onRowSelectAcc(SelectEvent event) {
        AccidenteCalificacion ac;
        Accidente acc = (Accidente) event.getObject();
        if (validarAccidente(acc.getIdAccidente())) {
            MovilidadUtil.addErrorMessage("Caso de accidentalidad se encuentra en la lista");
            return;
        }
        if (validarAccidenteCausalidad(acc.getIdAccidente())) {
            MovilidadUtil.addErrorMessage("Caso de accidentalidad cuenta con árbol de causalidad");
            return;
        }
        if (validarCalificadoAcc(acc.getIdAccidente())) {
            MovilidadUtil.addErrorMessage("Caso de accidentalidad se encuentra en proceso de evaluación o ya fue evaluado");
            return;
        }
        ac = new AccidenteCalificacion();
        ac.setIdAccidente(acc);
        listAccidenteCalificacion.add(ac);
        MovilidadUtil.addSuccessMessage("Caso de accidentalidad agregado a la lista");
    }

    public void procesarCalificar(AccidenteCalificacion ac) {
        Integer iTotal = 0;
        List<AccidenteAnalisis> listIdAccAnalisis;
        List<AccidenteCalificacionDet> listAccCal = accidenteCalificacionDetFacadeLocal.findByAccClasificacion(ac.getIdAccidenteCalificacion());
        if (listAccCal == null) {
            MovilidadUtil.addErrorMessage("No hay calificaciones en el sistema para este evento");
            return;
        }
        if (listAccCal.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay calificaciones en el sistema para este evento");
            return;
        }
        listIdAccAnalisis = new ArrayList<>();
        for (AccidenteCalificacionDet acd : listAccCal) {
            if (!listIdAccAnalisis.contains(acd.getIdAccidenteAnalisis())) {
                listIdAccAnalisis.add(acd.getIdAccidenteAnalisis());
            }
        }
        for (AccidenteAnalisis accAnalisis : listIdAccAnalisis) {
            Integer iValue = accidenteCalificacionDetFacadeLocal.calcularevaluacion(ac.getIdAccidenteCalificacion(), accAnalisis.getIdAccidenteAnalisis());
            if (iValue != null) {
                accAnalisis.setValoracion(iValue);
            }
        }
        for (AccidenteAnalisis accAnalisis : listIdAccAnalisis) {
            Integer iValue = accAnalisis.getValoracion();
            if (iValue != null) {
                iTotal = iTotal + iValue;
            }
        }
        for (AccidenteAnalisis accAnalisis : listIdAccAnalisis) {
            Integer iValue = accAnalisis.getValoracion();
            if (iValue != null) {
                float f = (float) iValue / iTotal * 100;
                accAnalisis.setValoracion((int) Math.round(f));
                accidenteAnalisisFacadeLocal.edit(accAnalisis);
            }
        }
        Integer iTotalAux = 0;
        for (AccidenteAnalisis accAnalisis : listIdAccAnalisis) {
            Integer iValue = accAnalisis.getValoracion();
            if (iValue != null) {
                iTotalAux = iTotalAux + iValue;
            }

        }
        if (iTotalAux == 101) {
            AccidenteAnalisis aa = listIdAccAnalisis.get(0);
            aa.setValoracion(aa.getValoracion() - 1);
            accidenteAnalisisFacadeLocal.edit(aa);
        }
        if (iTotalAux == 99) {
            AccidenteAnalisis aa = listIdAccAnalisis.get(0);
            aa.setValoracion(aa.getValoracion() + 1);
            accidenteAnalisisFacadeLocal.edit(aa);
        }
        ac.setCalificado(1);
        ac.setModificado(new Date());
        accidenteCalificacionFacadeLocal.edit(ac);
        MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
    }

    public List<AccidentePreCalificacion> procesarPreCalificar(AccidenteCalificacion ac) {
        return accidentePreCalificacionFacadeLocal.findCausaSubByCalificaion(ac.getIdAccidenteCalificacion());
    }

    public void eliminarAccPreClaLista(AccidentePreCalificacion acp) {
        accidentePreCalificacionFacadeLocal.remove(acp);
        MovilidadUtil.addSuccessMessage("Causalidad eliminada.");
    }

    public void preCalificar(AccidenteCalificacion ac) {
        List<AccidentePreCalificacion> listAccidentePreCalificacion = null;
        listAccidentePreCalificacion = accidentePreCalificacionFacadeLocal.findCausaSubByCalificaion(ac.getIdAccidenteCalificacion());
        AccidenteAnalisis aa;
        if (listAccidentePreCalificacion != null) {
            for (AccidentePreCalificacion apc : listAccidentePreCalificacion) {
                aa = new AccidenteAnalisis();
                aa.setIdAccidente(ac.getIdAccidente());
//                aa.setIdCausaSub(apc.getIdCausasub());
//                aa.setIdCausaRaiz(apc.getIdCausaraiz());
                aa.setCreado(new Date());
                aa.setEstadoReg(0);
                aa.setFecha(new Date());
                aa.setUsername(ac.getUsername());
                accidenteAnalisisFacadeLocal.create(aa);
            }
            ac.setCalificado(2);
            accidenteCalificacionFacadeLocal.edit(ac);
            MovilidadUtil.addSuccessMessage("Datos Procesados, caso de accidentalidad listo para calificación de sus causalidades.");
        }
    }

    public String getUrl() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return Util.getUrlContext(request) + "/public/page/loginAcc.jsf";
    }

    public void buscarCasoPorPin() {
        listAccidenteCalificacionResumen = accidenteCalificacionFacadeLocal.findByPin(dFecha, iPin, 1);
    }

    public List<AccidenteAnalisis> getListCausalidad(AccidenteCalificacion ac) {
        return accidenteAnalisisFacadeLocal.estadoReg(ac.getIdAccidente().getIdAccidente());
    }

    boolean validarAccidente(Integer idAccidente) {
        for (AccidenteCalificacion ac : listAccidenteCalificacion) {
            if (ac.getIdAccidente().getIdAccidente().equals(idAccidente)) {
                return true;
            }
        }
        return false;
    }

    //validadr si el accidente tiene causalidades
    boolean validarAccidenteCausalidad(Integer idAccidente) {
        List<AccidenteAnalisis> findAccCau = accidenteAnalisisFacadeLocal.estadoReg(idAccidente);
        if (findAccCau == null) {
            return false;
        } else {
            if (findAccCau.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    boolean validarPin(Date d) {
        return accidenteCalificacionFacadeLocal.validateByPin(d, iPin);
    }

    boolean validarCalificadoAcc(Integer idAccidente) {
        return accidenteCalificacionFacadeLocal.validateByAccidente(idAccidente);
    }

    public List<AccidenteCalificacion> getListAccidenteCalificacion() {
        return listAccidenteCalificacion;
    }

    public void setListAccidenteCalificacion(List<AccidenteCalificacion> listAccidenteCalificacion) {
        this.listAccidenteCalificacion = listAccidenteCalificacion;
    }

    public Integer getiPin() {
        return iPin;
    }

    public void setiPin(Integer iPin) {
        this.iPin = iPin;
    }

    public List<AccidenteCalificacion> getListAccidenteCalificacionResumen() {
        return listAccidenteCalificacionResumen;
    }

    public void setListAccidenteCalificacionResumen(List<AccidenteCalificacion> listAccidenteCalificacionResumen) {
        this.listAccidenteCalificacionResumen = listAccidenteCalificacionResumen;
    }

    public Date getdFecha() {
        return dFecha;
    }

    public void setdFecha(Date dFecha) {
        this.dFecha = dFecha;
    }

}
