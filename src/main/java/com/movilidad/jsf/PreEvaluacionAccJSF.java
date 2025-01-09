/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccArbolFacadeLocal;
import com.movilidad.ejb.AccCausaFacadeLocal;
import com.movilidad.ejb.AccCausaRaizFacadeLocal;
import com.movilidad.ejb.AccCausaSubFacadeLocal;
import com.movilidad.ejb.AccidenteCalificacionFacadeLocal;
import com.movilidad.ejb.AccidentePreCalificacionFacadeLocal;
import com.movilidad.model.AccArbol;
import com.movilidad.model.AccCausa;
import com.movilidad.model.AccCausaRaiz;
import com.movilidad.model.AccCausaSub;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteCalificacion;
import com.movilidad.model.AccidentePreCalificacion;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
@Named(value = "preEvaluacionAccJSF")
@ViewScoped
public class PreEvaluacionAccJSF implements Serializable {

    @EJB
    private AccidenteCalificacionFacadeLocal accidenteCalificacionFacadeLocal;
    @EJB
    private AccCausaSubFacadeLocal accCausaSubFacadeLocal;
    @EJB
    private AccCausaRaizFacadeLocal accCausaRaizFacadeLocal;
    @EJB
    private AccidentePreCalificacionFacadeLocal accidentePreCalificacionFacadeLocal;
    @EJB
    private AccCausaFacadeLocal accCausaFacadeLocal;
    @EJB
    private AccArbolFacadeLocal accArbolFacadeLocal;

    private Accidente accidente;
    private List<AccidenteCalificacion> listAccidenteCalificacion;
    private List<AccidentePreCalificacion> listAccidentePreCalificacion;
    private List<AccCausa> listAccCausa;
    private List<AccCausaSub> listAccCausaSub;
    private List<AccCausaRaiz> listAccCausaRaiz;
    private List<AccArbol> listAccArbol;

    private HashMap<Integer, AccCausaSub> mapAccCausaSub;
    private HashMap<Integer, AccCausaRaiz> mapAccCausaRaiz;

    private Integer iPin;
    private Integer iIndex;
    private Integer iLimite;
    private Integer idAccCausaSub;
    private Integer idAccCausaRaiz;
    private Integer idAccArbol;
    private Integer idAccCausa;

    private boolean bFlag;
    private boolean bNuevo;

    public PreEvaluacionAccJSF() {
    }

    @PostConstruct
    public void init() {
        accidente = null;
        idAccCausaSub = null;
        idAccCausaRaiz = null;
        idAccArbol = null;
        idAccCausa = null;
        bFlag = true;
        bNuevo = true;
        listAccidenteCalificacion = new ArrayList<>();
        listAccidentePreCalificacion = new ArrayList<>();
        listAccCausa = null;
        listAccCausaRaiz = null;
        listAccArbol = null;
        listAccCausaSub = null;
        mapAccCausaRaiz = new HashMap<>();
        mapAccCausaSub = new HashMap<>();
        iPin = null;
        iIndex = 0;
        iLimite = null;
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        String get = params.get("pin");
        if (get != null) {
            try {
                iPin = new Integer(get);
            } catch (NumberFormatException e) {
                return;
            }
            listAccidenteCalificacion = accidenteCalificacionFacadeLocal.findByPin(new Date(), iPin, 2);
            if (listAccidenteCalificacion != null && !listAccidenteCalificacion.isEmpty()) {
                iLimite = listAccidenteCalificacion.size();
                accidente = listAccidenteCalificacion.get(iIndex).getIdAccidente();
                cargarMap();
                listAccArbol = accArbolFacadeLocal.estadoReg();
            }
        }
    }

    public void siguiente() {
        guardar();
        iIndex = iIndex + 1;
        if (iIndex < iLimite) {
            accidente = listAccidenteCalificacion.get(iIndex).getIdAccidente();
        } else {
            bFlag = false;
            bNuevo = false;
            iIndex = iLimite;
            MovilidadUtil.addSuccessMessage("Se asignaron las causalidades para todos los casos de accidentalidad asociados este pin, Gracias.");
        }
    }

    public void agregarLista() {
        if (idAccCausaSub == null) {
            MovilidadUtil.addErrorMessage("Sub Causa es requerido");
            return;
        }
        if (idAccCausaRaiz == null) {
            if (listAccCausaRaiz != null && !listAccCausaRaiz.isEmpty()) {
                MovilidadUtil.addErrorMessage("Causa Raíz es requerido");
                return;
            }
        }
        AccidentePreCalificacion accidentePreCal;
        accidentePreCal = new AccidentePreCalificacion();
        if (idAccCausaRaiz != null) {
            accidentePreCal.setIdCausaraiz(mapAccCausaRaiz.get(idAccCausaRaiz));
        }
        accidentePreCal.setIdCausasub(mapAccCausaSub.get(idAccCausaSub));
        listAccidentePreCalificacion.add(accidentePreCal);
        bNuevo = true;
        resetId();
        MovilidadUtil.addSuccessMessage("Causalidad agregada a la lista");
    }

    public void prepararNuevo() {
        bNuevo = false;
    }

    public void elimiar(AccidentePreCalificacion apc) {
        listAccidentePreCalificacion.remove(apc);
        MovilidadUtil.addSuccessMessage("Eliminado de la lista correctamente.");
    }

    public void actualizarOpcion(int i) {
        switch (i) {
            case 1: ;
                idAccCausaRaiz = null;
                idAccCausaSub = null;
                idAccCausa = null;
                listAccCausa = null;
                listAccCausaRaiz = null;
                listAccCausaSub = null;
                break;
            case 2: ;
                idAccCausaRaiz = null;
                idAccCausaSub = null;
                listAccCausaRaiz = null;
                listAccCausaSub = null;
                break;
        }
    }

    void guardar() {
        for (AccidentePreCalificacion apc : listAccidentePreCalificacion) {
            apc.setIdAccidenteCalificacion(listAccidenteCalificacion.get(iIndex));
            Integer idAccRaiz = null;
            if (apc.getIdCausaraiz() != null) {
                idAccRaiz = apc.getIdCausaraiz().getIdAccCausaRaiz();
            }
            AccidentePreCalificacion apcAux = accidentePreCalificacionFacadeLocal.findByAccCla(
                    apc.getIdAccidenteCalificacion().getIdAccidenteCalificacion(),
                    apc.getIdCausasub().getIdAccSubcausa(),
                    idAccRaiz);
            if (apcAux == null) {
                accidentePreCalificacionFacadeLocal.create(apc);
            }
        }
        listAccidentePreCalificacion.clear();
    }

    void resetId() {
        idAccCausaSub = null;
        idAccCausaRaiz = null;
        idAccCausa = null;
        idAccArbol = null;
    }

    void cargarMap() {
        mapAccCausaRaiz.clear();
        mapAccCausaSub.clear();
        for (AccCausaSub acs : accCausaSubFacadeLocal.findAll()) {
            mapAccCausaSub.put(acs.getIdAccSubcausa(), acs);
        }
        for (AccCausaRaiz acr : accCausaRaizFacadeLocal.findAll()) {
            mapAccCausaRaiz.put(acr.getIdAccCausaRaiz(), acr);
        }
    }

    public List<AccCausa> getListAccCausa() {
        if (idAccArbol != null) {
            listAccCausa = accCausaFacadeLocal.findByArbol(idAccArbol);
        }
        return listAccCausa;
    }

    public List<AccCausaSub> getListAccCausaSub() {
        if (idAccCausa != null) {
            listAccCausaSub = accCausaSubFacadeLocal.findByCausa(idAccCausa);
        }
        return listAccCausaSub;
    }

    public List<AccCausaRaiz> getListAccCausaRaiz() {
        if (idAccCausaSub != null) {
            listAccCausaRaiz = accCausaRaizFacadeLocal.findByCausaSub(idAccCausaSub);
        }
        return listAccCausaRaiz;
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public void setAccidente(Accidente accidente) {
        this.accidente = accidente;
    }

    public List<AccidentePreCalificacion> getListAccidentePreCalificacion() {
        return listAccidentePreCalificacion;
    }

    public void setListAccidentePreCalificacion(List<AccidentePreCalificacion> listAccidentePreCalificacion) {
        this.listAccidentePreCalificacion = listAccidentePreCalificacion;
    }

    public List<AccArbol> getListAccArbol() {
        return listAccArbol;
    }

    public Integer getIdAccCausaSub() {
        return idAccCausaSub;
    }

    public void setIdAccCausaSub(Integer idAccCausaSub) {
        this.idAccCausaSub = idAccCausaSub;
    }

    public Integer getIdAccCausaRaiz() {
        return idAccCausaRaiz;
    }

    public void setIdAccCausaRaiz(Integer idAccCausaRaiz) {
        this.idAccCausaRaiz = idAccCausaRaiz;
    }

    public Integer getIdAccArbol() {
        return idAccArbol;
    }

    public void setIdAccArbol(Integer idAccArbol) {
        this.idAccArbol = idAccArbol;
    }

    public Integer getIdAccCausa() {
        return idAccCausa;
    }

    public void setIdAccCausa(Integer idAccCausa) {
        this.idAccCausa = idAccCausa;
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

    public boolean isbNuevo() {
        return bNuevo;
    }

    public void setbNuevo(boolean bNuevo) {
        this.bNuevo = bNuevo;
    }

    public Integer getiPin() {
        return iPin;
    }

    public void setiPin(Integer iPin) {
        this.iPin = iPin;
    }

}
