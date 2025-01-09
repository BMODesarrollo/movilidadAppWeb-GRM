/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteAnalisisFacadeLocal;
import com.movilidad.ejb.AccidenteCalificacionDetFacadeLocal;
import com.movilidad.ejb.AccidenteCalificacionFacadeLocal;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteAnalisis;
import com.movilidad.model.AccidenteCalificacion;
import com.movilidad.model.AccidenteCalificacionDet;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author cesar
 */
@Named(value = "evaluacionAccDetalleJSF")
@ViewScoped
public class EvaluacionAccDetalleJSF implements Serializable {

    @EJB
    private AccidenteCalificacionDetFacadeLocal accidenteCalificacionDetFacadeLocal;
    @EJB
    private AccidenteCalificacionFacadeLocal accidenteCalificacionFacadeLocal;
    @EJB
    private AccidenteAnalisisFacadeLocal accidenteAnalisisFacadeLocal;

    private List<AccidenteCalificacionDet> listAccidenteCalificacionDet;
    private Accidente accidente;
    private List<AccidenteCalificacion> listAccidenteCalificacion;

    private Integer iPin;
    private Integer iIndex;
    private Integer iValue;
    private Integer iLimite;
    private Integer iAcumulado; //tiene que ser 100
    private String cUUID;
    private String cUserLider;

    private boolean bFlag;

    public EvaluacionAccDetalleJSF() {
    }

    @PostConstruct
    public void init() {
        accidente = null;
        bFlag = true;
        listAccidenteCalificacion = new ArrayList<>();
        listAccidenteCalificacionDet = new ArrayList<>();
        iPin = null;
        iValue = null;
        iIndex = 0;
        iAcumulado = 0;
        iLimite = null;
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        String get = params.get("pin");
        if (get != null) {
            cUUID = java.util.UUID.randomUUID().toString().toUpperCase();
            try {
                iPin = new Integer(get);
            } catch (NumberFormatException e) {
                return;
            }
            listAccidenteCalificacion = accidenteCalificacionFacadeLocal.findByPin(new Date(), iPin, 3);
            if (listAccidenteCalificacion != null && !listAccidenteCalificacion.isEmpty()) {
                iLimite = listAccidenteCalificacion.size();
                accidente = listAccidenteCalificacion.get(iIndex).getIdAccidente();
                cUserLider = listAccidenteCalificacion.get(0).getUsername();
                cargarCausalidades();
            }
        }
    }

    void guardar() {
        try {
            for (AccidenteCalificacionDet acd : listAccidenteCalificacionDet) {
                acd.setCreado(new Date());
                acd.setEstadoReg(0);
                acd.setUsrLidera(cUserLider);
                acd.setUuidInvitado(cUUID);
                acd.setIdAccidenteCalificacion(listAccidenteCalificacion.get(iIndex));
                accidenteCalificacionDetFacadeLocal.create(acd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void siguiente() {
        if (iAcumulado != null && iAcumulado.equals(100)) {
            guardar();
            iIndex = iIndex + 1;
            if (iIndex < iLimite) {
                accidente = listAccidenteCalificacion.get(iIndex).getIdAccidente();
                cargarCausalidades();
            } else {
                accidente = null;
                bFlag = false;
                iIndex = iLimite;
                MovilidadUtil.addSuccessMessage("Se evaluaron todos los casos de accidentalidad para este pin, Gracias.");
            }
            iAcumulado = 0;
        }
    }

    void cargarCausalidades() {
        listAccidenteCalificacionDet.clear();
        AccidenteCalificacionDet acd;
        List<AccidenteAnalisis> findList = accidenteAnalisisFacadeLocal.estadoReg(accidente.getIdAccidente());
        if (findList != null && !findList.isEmpty()) {
            for (AccidenteAnalisis aa : findList) {
                acd = new AccidenteCalificacionDet();
                acd.setIdAccidenteAnalisis(aa);
                listAccidenteCalificacionDet.add(acd);
            }
        }
    }

    public void onCellEdit(CellEditEvent event) {
        AccidenteCalificacionDet acd = (AccidenteCalificacionDet) ((DataTable) event.getComponent()).getRowData();
        acd.setCalificacion(new Integer(iValue));
        iValue = null;
        iAcumulado = 0;
        for (AccidenteCalificacionDet ac : listAccidenteCalificacionDet) {
            if (ac.getCalificacion() != null) {
                iAcumulado = iAcumulado + ac.getCalificacion();
            }
        }
    }

    public Integer getiPin() {
        return iPin;
    }

    public void setiPin(Integer iPin) {
        this.iPin = iPin;
    }

    public List<AccidenteCalificacion> getListAccidenteCalificacion() {
        return listAccidenteCalificacion;
    }

    public void setListAccidenteCalificacion(List<AccidenteCalificacion> listAccidenteCalificacion) {
        this.listAccidenteCalificacion = listAccidenteCalificacion;
    }

    public List<AccidenteCalificacionDet> getListAccidenteCalificacionDet() {
        return listAccidenteCalificacionDet;
    }

    public void setListAccidenteCalificacionDet(List<AccidenteCalificacionDet> listAccidenteCalificacionDet) {
        this.listAccidenteCalificacionDet = listAccidenteCalificacionDet;
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public void setAccidente(Accidente accidente) {
        this.accidente = accidente;
    }

    public Integer getiAcumulado() {
        return iAcumulado;
    }

    public void setiAcumulado(Integer iAcumulado) {
        this.iAcumulado = iAcumulado;
    }

    public String getcUUID() {
        return cUUID;
    }

    public void setcUUID(String cUUID) {
        this.cUUID = cUUID;
    }

    public Integer getiValue() {
        return iValue;
    }

    public void setiValue(Integer iValue) {
        this.iValue = iValue;
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

}
