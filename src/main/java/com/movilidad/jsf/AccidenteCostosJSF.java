/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoCostosFacadeLocal;
import com.movilidad.ejb.AccidenteCostosFacadeLocal;
import com.movilidad.model.AccTipoCostos;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteCostos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteCostosJSF")
@ViewScoped
public class AccidenteCostosJSF implements Serializable {

    @EJB
    private AccidenteCostosFacadeLocal accidenteCostosFacadeLocal;
    @EJB
    private AccTipoCostosFacadeLocal accTipoCostosFacadeLocal;

    private AccidenteCostos accidenteCostos;

    // para costosDirectos : 1
    // para costosIndirectos: 2
    private List<AccidenteCostos> listAccidenteCostosDirectos;
    private List<AccidenteCostos> listAccidenteCostosIndirectos;
    private List<AccTipoCostos> listAccTipoCostos;

    private int i_idAccidente;
    private int i_idAccTipoCostos;
    private int i_tipoCosto;//directo o indirecto
    private int i_totaIndirecto;
    private int i_totaDirecto;

    private boolean b_flag;
    private boolean b_control;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteJSF accidenteJSF;

    public AccidenteCostosJSF() {
    }

    @PostConstruct
    public void init() {
        i_idAccTipoCostos = 0;
        b_flag = true;
        b_control = false;
        i_tipoCosto = 0;
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        i_totaDirecto = 0;
        i_totaIndirecto = 0;
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                if (accidenteCostos != null) {
                    cargarObjetos();
                    if (b_control) {
                        b_control = false;
                        return;
                    }
                    accidenteCostos.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteCostos.setCreado(new Date());
                    accidenteCostos.setModificado(new Date());
                    accidenteCostos.setUsername(user.getUsername());
                    accidenteCostos.setEstadoReg(0);
                    accidenteCostosFacadeLocal.create(accidenteCostos);
                    MovilidadUtil.addSuccessMessage("Se guardó el Accidente Costos correctamente");
                    reset();
                }
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
        } catch (Exception e) {

        }
    }

    public void prepareGuardar() {
        accidenteCostos = new AccidenteCostos();
    }

    public void editar() {
        try {
            if (accidenteCostos != null) {
                cargarObjetos();
                if (b_control) {
                    b_control = false;
                    return;
                }
                accidenteCostosFacadeLocal.edit(accidenteCostos);
                MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Costos correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Costos");
        }
    }

    public void eliminarLista(AccidenteCostos at) {
        try {
            at.setEstadoReg(1);
            accidenteCostosFacadeLocal.edit(at);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Costos de la lista");
            reset();
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Costos");
        }
    }

    public void prepareEditar(AccidenteCostos ac) {
        accidenteCostos = ac;
        b_flag = false;
        if (ac.getIdAccTipoCostos() != null) {
            i_idAccTipoCostos = ac.getIdAccTipoCostos().getIdAccTipoCostos();
            i_tipoCosto = ac.getIdAccTipoCostos().getDirecto();
            buscarPorTipoCostos();
        }
    }

    void cargarObjetos() {
        if (i_idAccTipoCostos != 0) {
            accidenteCostos.setIdAccTipoCostos(new AccTipoCostos(i_idAccTipoCostos));
        } else {
            MovilidadUtil.addErrorMessage("Acc Tipo Costos es requerido");
            b_control = true;
        }
    }

    public void reset() {
        accidenteCostos = null;
        i_idAccTipoCostos = 0;
        b_flag = true;
        i_tipoCosto = 0;
    }

    public void buscarPorTipoCostos() {
        if (i_tipoCosto != 0) {
            listAccTipoCostos = accTipoCostosFacadeLocal.findByTipoCosto(i_tipoCosto);
        }
    }

    public int getI_totaIndirecto() {
        return i_totaIndirecto;
    }

    public void setI_totaIndirecto(int i_totaIndirecto) {
        this.i_totaIndirecto = i_totaIndirecto;
    }

    public int getI_totaDirecto() {
        return i_totaDirecto;
    }

    public void setI_totaDirecto(int i_totaDirecto) {
        this.i_totaDirecto = i_totaDirecto;
    }

    public AccidenteCostos getAccidenteCostos() {
        return accidenteCostos;
    }

    public void setAccidenteCostos(AccidenteCostos accidenteCostos) {
        this.accidenteCostos = accidenteCostos;
    }

    public List<AccidenteCostos> getListAccidenteCostosDirectos() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        i_totaDirecto = 0;
        listAccidenteCostosDirectos = accidenteCostosFacadeLocal.estadoReg(1, i_idAccidente);
        if (listAccidenteCostosDirectos != null) {
            for (AccidenteCostos o : listAccidenteCostosDirectos) {
                if (o.getValor() != null) {
                    i_totaDirecto = i_totaDirecto + o.getValor();
                }
            }
        }
        return listAccidenteCostosDirectos;
    }

    public List<AccidenteCostos> getListAccidenteCostosIndirectos() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        i_totaIndirecto = 0;
        listAccidenteCostosIndirectos = accidenteCostosFacadeLocal.estadoReg(2, i_idAccidente);
        if (listAccidenteCostosIndirectos != null) {
            for (AccidenteCostos o : listAccidenteCostosIndirectos) {
                if (o.getValor() != null) {
                    i_totaIndirecto = i_totaIndirecto + o.getValor();
                }
            }
        }
        return listAccidenteCostosIndirectos;
    }

    public int getI_idAccTipoCostos() {
        return i_idAccTipoCostos;
    }

    public void setI_idAccTipoCostos(int i_idAccTipoCostos) {
        this.i_idAccTipoCostos = i_idAccTipoCostos;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    public void setB_flag(boolean b_flag) {
        this.b_flag = b_flag;
    }

    public int getI_tipoCosto() {
        return i_tipoCosto;
    }

    public void setI_tipoCosto(int i_tipoCosto) {
        this.i_tipoCosto = i_tipoCosto;
    }

    public List<AccTipoCostos> getListAccTipoCostos() {
        return listAccTipoCostos;
    }

}
