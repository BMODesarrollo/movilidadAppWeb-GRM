/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author HP
 */
@Named(value = "paramReporteHorasJSF")
@ViewScoped
public class ParamReporteHorasJSF implements Serializable {

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasFacadeLocal;

    private List<ParamReporteHoras> listParamReporteHoras;

    private String cNuevoVal;

    public ParamReporteHorasJSF() {
    }

    @PostConstruct
    public void init() {
        listParamReporteHoras = new ArrayList<>();
        cNuevoVal = null;
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            ParamReporteHoras prh = (ParamReporteHoras) ((DataTable) event.getComponent()).getRowData();
            if (prh != null) {
                if (cNuevoVal != null) {
                    if (event.getColumn().getAriaHeaderText().equals("1")) { // 1 para la columna de cocepto
                        prh.setConcepto(cNuevoVal);
                    }
                    if (event.getColumn().getAriaHeaderText().equals("2")) { // 2 para código
                        prh.setCodigo(cNuevoVal);
                    }
                    if (event.getColumn().getAriaHeaderText().equals("3")) { // 3 para el recargo
                        prh.setRecargo(Integer.parseInt(cNuevoVal));
                    }
                    paramReporteHorasFacadeLocal.edit(prh);
                    MovilidadUtil.addSuccessMessage("Operación realizada con éxito");
                    return;
                }
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            } else {
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            }
            cNuevoVal = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ParamReporteHoras> getListParamReporteHoras() {
        listParamReporteHoras = paramReporteHorasFacadeLocal.findAllActivos(0);
        return listParamReporteHoras;
    }

    public void setListParamReporteHoras(List<ParamReporteHoras> listParamReporteHoras) {
        this.listParamReporteHoras = listParamReporteHoras;
    }

    public String getcNuevoVal() {
        return cNuevoVal;
    }

    public void setcNuevoVal(String cNuevoVal) {
        this.cNuevoVal = cNuevoVal;
    }

}
