/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccEtapaProcesoFacadeLocal;
import com.movilidad.ejb.AccidenteDiligenciasjFacadeLocal;
import com.movilidad.model.AccEtapaProceso;
import com.movilidad.model.AccTipoProceso;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteConductor;
import com.movilidad.model.AccidenteDiligenciasj;
import com.movilidad.model.AccidenteTestigo;
import com.movilidad.model.AccidenteVehiculo;
import com.movilidad.model.AccidenteVictima;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteDiligenciasjJSF")
@ViewScoped
public class AccidenteDiligenciasjJSF implements Serializable {

    @EJB
    private AccidenteDiligenciasjFacadeLocal accidenteDiligenciasjFacadeLocal;
    @EJB
    private AccEtapaProcesoFacadeLocal accEtapaProcesoFacadeLocal;

    private AccidenteDiligenciasj accidenteDiligenciasj;

    private List<AccidenteDiligenciasj> listAccidenteDiligenciasj;
    private List<AccEtapaProceso> listAccEtapaProceso;
    private List<AccidenteConductor> listAccidenteConductor;
    private List<AccidenteTestigo> listAccidenteTestigo;
    private List<AccidenteVictima> listAccidenteVictima;
    private List<AccidenteVehiculo> listAccidenteVehiculo;

    private int i_idAccidente;
    private int i_idAccTipoProceso;
    private Integer i_idAccEtapaProceso;

    private boolean b_flag;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteJSF accidenteJSF;

    public AccidenteDiligenciasjJSF() {
    }

    @PostConstruct
    public void init() {
        PrimeFaces.current().executeScript("PF('bui5').show()");
        i_idAccEtapaProceso = 0;
        i_idAccTipoProceso = 0;
        b_flag = true;
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        listAccidenteConductor = new ArrayList<>();
        listAccidenteTestigo = new ArrayList<>();
        listAccidenteVictima = new ArrayList<>();
        listAccidenteVehiculo = new ArrayList<>();
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                if (accidenteDiligenciasj != null) {
                    cargarObjetos();
                    accidenteDiligenciasj.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteDiligenciasj.setCreado(new Date());
                    accidenteDiligenciasj.setModificado(new Date());
                    accidenteDiligenciasj.setUsername(user.getUsername());
                    accidenteDiligenciasj.setEstadoReg(0);
                    accidenteDiligenciasjFacadeLocal.create(accidenteDiligenciasj);
                    MovilidadUtil.addSuccessMessage("Se guardó el Accidente Diligenciasj correctamente");
                    reset();
                }
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
        } catch (Exception e) {

        }
    }

    public void prepareGuardar() {
        accidenteDiligenciasj = new AccidenteDiligenciasj();
        accidenteDiligenciasj.setFecha(new Date());
    }

    public void editar() {
        try {
            if (accidenteDiligenciasj != null) {
                cargarObjetos();
                accidenteDiligenciasjFacadeLocal.edit(accidenteDiligenciasj);
                MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Diligenciasj correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Diligenciasj");
        }
    }

    public void eliminarLista(AccidenteDiligenciasj at) {
        try {
            at.setEstadoReg(1);
            accidenteDiligenciasjFacadeLocal.edit(at);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Diligenciasj de la lista");
            reset();
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Diligenciasj");
        }
    }

    public void prepareEditar(AccidenteDiligenciasj adj) {
        accidenteDiligenciasj = adj;
        b_flag = false;
        if (adj.getIdAccTipoProceso() != null) {
            i_idAccTipoProceso = adj.getIdAccTipoProceso().getIdAccTipoProceso();
        }
        if (adj.getIdAccEtapaProceso() != null) {
            i_idAccEtapaProceso = adj.getIdAccEtapaProceso().getIdAccEtapaProceso();
        }
    }

    void cargarObjetos() {
        if (i_idAccEtapaProceso != 0) {
            accidenteDiligenciasj.setIdAccEtapaProceso(new AccEtapaProceso(i_idAccEtapaProceso));
        }
        if (i_idAccTipoProceso != 0) {
            accidenteDiligenciasj.setIdAccTipoProceso(new AccTipoProceso(i_idAccTipoProceso));
        }
    }

    public void reset() {
        accidenteDiligenciasj = null;
        i_idAccEtapaProceso = 0;
        i_idAccTipoProceso = 0;
        b_flag = true;
        PrimeFaces.current().executeScript("PF('bui5').show()");
    }

    public AccidenteDiligenciasj getAccidenteDiligenciasj() {
        return accidenteDiligenciasj;
    }

    public void setAccidenteDiligenciasj(AccidenteDiligenciasj accidenteDiligenciasj) {
        this.accidenteDiligenciasj = accidenteDiligenciasj;
    }

    public List<AccidenteDiligenciasj> getListAccidenteDiligenciasj() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        if (i_idAccidente != 0) {
            listAccidenteDiligenciasj = accidenteDiligenciasjFacadeLocal.estadoReg(i_idAccidente);
        }
        return listAccidenteDiligenciasj;
    }

    public int getI_idAccidente() {
        return i_idAccidente;
    }

    public void setI_idAccidente(int i_idAccidente) {
        this.i_idAccidente = i_idAccidente;
    }

    public int getI_idAccTipoProceso() {
        return i_idAccTipoProceso;
    }

    public void setI_idAccTipoProceso(int i_idAccTipoProceso) {
        this.i_idAccTipoProceso = i_idAccTipoProceso;
    }

    public Integer getI_idAccEtapaProceso() {
        return i_idAccEtapaProceso;
    }

    public void setI_idAccEtapaProceso(Integer i_idAccEtapaProceso) {
        this.i_idAccEtapaProceso = i_idAccEtapaProceso;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    public List<AccidenteConductor> getListAccidenteConductor() {
        return listAccidenteConductor;
    }

    public void setListAccidenteConductor(List<AccidenteConductor> listAccidenteConductor) {
        this.listAccidenteConductor = listAccidenteConductor;
    }

    public List<AccidenteTestigo> getListAccidenteTestigo() {
        return listAccidenteTestigo;
    }

    public void setListAccidenteTestigo(List<AccidenteTestigo> listAccidenteTestigo) {
        this.listAccidenteTestigo = listAccidenteTestigo;
    }

    public List<AccidenteVictima> getListAccidenteVictima() {
        return listAccidenteVictima;
    }

    public void setListAccidenteVictima(List<AccidenteVictima> listAccidenteVictima) {
        this.listAccidenteVictima = listAccidenteVictima;
    }

    public List<AccidenteVehiculo> getListAccidenteVehiculo() {
        return listAccidenteVehiculo;
    }

    public void setListAccidenteVehiculo(List<AccidenteVehiculo> listAccidenteVehiculo) {
        this.listAccidenteVehiculo = listAccidenteVehiculo;
    }

    public List<AccEtapaProceso> getListAccEtapaProceso() {
        listAccEtapaProceso = accEtapaProcesoFacadeLocal.listPorTipoProceso(i_idAccTipoProceso);
        return listAccEtapaProceso;
    }

    public void setListAccEtapaProceso(List<AccEtapaProceso> listAccEtapaProceso) {
        this.listAccEtapaProceso = listAccEtapaProceso;
    }

}
