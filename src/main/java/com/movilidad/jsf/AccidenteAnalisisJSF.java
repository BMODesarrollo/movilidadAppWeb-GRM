/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCausaFacadeLocal;
import com.movilidad.ejb.AccCausaRaizFacadeLocal;
import com.movilidad.ejb.AccCausaSubFacadeLocal;
import com.movilidad.ejb.AccidenteAnalisisFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.AccArbol;
import com.movilidad.model.AccCausa;
import com.movilidad.model.AccCausaRaiz;
import com.movilidad.model.AccCausaSub;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteAnalisis;
import com.movilidad.model.Novedad;
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
@Named(value = "accidenteAnalisisJSF")
@ViewScoped
public class AccidenteAnalisisJSF implements Serializable {

    @EJB
    private AccidenteAnalisisFacadeLocal accidenteAnalisisFacadeLocal;
    @EJB
    private AccCausaFacadeLocal accCausaFacadeLocal;
    @EJB
    private AccCausaSubFacadeLocal accCausaSubFacadeLocal;
    @EJB
    private AccCausaRaizFacadeLocal accCausaRaizFacadeLocal;
    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private NovedadFacadeLocal novedadFacadeLocal;
    @EJB
    private NovedadFacadeLocal novedadEjb;
    
    private AccidenteAnalisis accidenteAnalisis;

    private List<AccidenteAnalisis> listAccidenteAnalisis;
    private List<AccCausa> listAccCausa;
    private List<AccCausaSub> listAccCausaSub;
    private List<AccCausaRaiz> listAccCausaRaiz;

    private int i_idAccidente;
    private int i_idAccCausaRaiz;//objetos que persisten en accidente_análisis
    private int i_idAccCausaSub;//objetos que persisten en accidente_análisis
    private int i_idAccArbol;
    private int i_idAccCausa;
    private int puntosPmConciliados;
    private boolean puntosPmConciliadosEnabled;
    
    private boolean b_flag;
    private boolean b_control;
    private boolean isResponsable;
    private boolean isAfectaBonificacion;
    private boolean isEdit;
    private String descripcion;

    private Novedad novedad;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteJSF accidenteJSF;

    public AccidenteAnalisisJSF() {
    }

    @PostConstruct
    public void init() {
        i_idAccCausaRaiz = 0;
        i_idAccCausaSub = 0;
        i_idAccCausa = 0;
        i_idAccArbol = 0;
        b_flag = true;
        b_control = false;
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        isEdit = false;
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                if (accidenteAnalisis != null) {
                    cargarObjetos();
                    if (b_control) {
                        b_control = false;
                        return;
                    }
                    Date d = new Date();
                    accidenteAnalisis.setFecha(d);
                    accidenteAnalisis.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteAnalisis.setCreado(d);
                    accidenteAnalisis.setModificado(d);
                    accidenteAnalisis.setUsername(user.getUsername());
                    accidenteAnalisis.setEstadoReg(0);
                    accidenteAnalisisFacadeLocal.create(accidenteAnalisis);
                    MovilidadUtil.addSuccessMessage("Se guardó el Accidente Analisis correctamente");
                    reset();
                }
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
        } catch (Exception e) {

        }
    }

    public void prepareGuardar() {
        accidenteAnalisis = new AccidenteAnalisis();
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        if (i_idAccidente != 0) {
            Accidente a = accidenteFacadeLocal.find(i_idAccidente);
            isResponsable = a.getOperadorIsResponsable() != null ? a.getOperadorIsResponsable().equals(1) : false;
            puntosPmConciliados = a.getIdNovedad().getPuntosPmConciliados();
            if (a.getIdNovedad() != null) {
                isAfectaBonificacion = a.getIdNovedad().getProcede() == 1;
            }
        }
    }

    public void editar() {
        try {
            if (accidenteAnalisis != null) {
                cargarObjetos();
                if (b_control) {
                    b_control = false;
                    return;
                }
                accidenteAnalisisFacadeLocal.edit(accidenteAnalisis);
                MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Analisis correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Analisis");
        }
    }

    public void eliminarLista(AccidenteAnalisis at) {
        try {
            at.setEstadoReg(1);
            accidenteAnalisisFacadeLocal.edit(at);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Analisis de la lista");
            reset();
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Analisis");
        }
    }

    /**
     * Se determina si el operador es responsable del accidente
     */
    public void responsabilidadOperador() {
        System.out.println("Es reponsable "+isResponsable);
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        if (i_idAccidente == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return;
        }
        descripcion = accidenteAnalisis.getObservaciones();
        Accidente a = accidenteFacadeLocal.find(i_idAccidente);
        //novedad = a.getIdNovedad();
        //novedad.setPuntosPmConciliados(isResponsable ? puntosPmConciliados : 0);
        //novedad.setProcede(isResponsable ? 1 : 0);
        a.setOperadorIsResponsable(isResponsable ? 1 : 0);
        a.getIdNovedad().setPuntosPmConciliados(isResponsable ? a.getIdNovedad().getPuntosPmConciliados() : 0);
        puntosPmConciliados = a.getIdNovedad().getPuntosPmConciliados();
        accidenteFacadeLocal.edit(a);
        puntosNovedad();
        //novedadEjb.edit(novedad);
        
        MovilidadUtil.addSuccessMessage("Proceso realizado con exito");
    }
    public void cargarObservacion() {
        System.out.println("Entra a cargar observacion " + descripcion);
        System.out.println("Valor de observacion antes " + accidenteAnalisis.getObservaciones());
        accidenteAnalisis.setObservaciones(descripcion);
        System.out.println("Valor de observacion despues " + accidenteAnalisis.getObservaciones());
    }

    public void puntosNovedad() {
        if (isResponsable) {
            Accidente a = accidenteFacadeLocal.find(i_idAccidente);
            novedad = a.getIdNovedad();
            novedad.setPuntosPmConciliados(isResponsable ? puntosPmConciliados : 0);
            novedadEjb.edit(novedad);
            MovilidadUtil.addSuccessMessage("Proceso realizado con exito");
        } else {
            Accidente a = accidenteFacadeLocal.find(i_idAccidente);
            novedad = a.getIdNovedad();
            novedad.setPuntosPmConciliados(0);
            puntosPmConciliados = 0;
            novedadEjb.edit(novedad);
        }
    }

    public void procesarConciliacion() {
        if (isAfectaBonificacion) {
            procedeCociliacion();
        } else {
            procedeNoCociliacion();
        }
    }

    /**
     * Realiza la conciliación de una novedad y su asignación de puntos en el
     * programa master
     */
    private void procedeCociliacion() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        if (i_idAccidente == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return;
        }
        Accidente a = accidenteFacadeLocal.find(i_idAccidente);
        Novedad nov = a.getIdNovedad();
        if (nov == null) {
            MovilidadUtil.addErrorMessage("Accidente no cuenta con novedad relacionada");
            return;
        }
//        if (nov.getIdNovedadTipoDetalle().getAfectaPm() == 1) {
//            MovilidadUtil.addErrorMessage("Novedad no afecta programa de desempeño");
//            return;
//        }
        if (nov.getProcede() == 1) {
            MovilidadUtil.addErrorMessage("Ya se ha ejecutado la acción de 'Procede' para esta novedad");
            return;
        }
        Long existLiq = novedadFacadeLocal.existLiquidacionByFecha(a.getFechaAcc());
        if (existLiq == null) {
            MovilidadUtil.addErrorMessage("Error al liquidar");
            return;
        }
        if (existLiq.intValue() > 0) {
            MovilidadUtil.addErrorMessage("Para la fecha del accidente, ya existe una liquidación aplicada.");
            return;
        }
//        nov.setPuntosPmConciliados(puntoView(nov));
        nov.setProcede(1);
        novedadFacadeLocal.edit(nov);
        MovilidadUtil.addSuccessMessage("Proceso realizado con exito");
    }

    /**
     * Realiza la no conciliación de una novedad y su asignación de puntos en el
     * programa master
     */
    private void procedeNoCociliacion() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        if (i_idAccidente == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return;
        }
        Accidente a = accidenteFacadeLocal.find(i_idAccidente);
        Novedad nov = a.getIdNovedad();
        if (nov == null) {
            MovilidadUtil.addErrorMessage("Accidente no cuenta con novedad relacionada");
            return;
        }
//        if (nov.getIdNovedadTipoDetalle().getAfectaPm() == 1) {
//            MovilidadUtil.addErrorMessage("Novedad no afecta programa de desempeño");
//            return;
//        }
        if (nov.getProcede() == 0) {
            MovilidadUtil.addErrorMessage("Ya se ha ejecutado la acción de 'No Procede' para esta novedad");
            return;
        }
        Long existLiq = novedadFacadeLocal.existLiquidacionByFecha(a.getFechaAcc());
        if (existLiq == null) {
            MovilidadUtil.addErrorMessage("Error al liquidar");
            return;
        }
        if (existLiq.intValue() > 0) {
            MovilidadUtil.addErrorMessage("Para la fecha del accidente, ya existe una liquidación aplicada.");
            return;
        }
        nov.setPuntosPmConciliados(0);
        nov.setProcede(0);
        novedadFacadeLocal.edit(nov);
        MovilidadUtil.addSuccessMessage("Proceso realizado con exito");
    }

    /**
     * Retorna la cantidad de puntos PM de una novedad
     *
     * @param n
     * @return puntos Programa máster
     */
    public int puntoView(Novedad n) {
        if (n == null) {
            return 0;
        }
        if (n.getIdNovedadDano() != null) {
            return n.getIdNovedadDano().getIdVehiculoDanoSeveridad().getPuntosPm();
        }
        if (n.getIdMulta() != null) {
            return n.getPuntosPm();
        }
        if (n.getPuntosPm() != 0) {
            return n.getPuntosPm();
        }
        if (n.getIdNovedadDano() == null && n.getIdMulta() == null) {
            return n.getIdNovedadTipoDetalle().getPuntosPm();
        }
        return 0;
    }

    public void prepareEditar(AccidenteAnalisis at) {
        accidenteAnalisis = at;
        b_flag = false;
        i_idAccArbol = at.getIdAccArbol() != null ? at.getIdAccArbol().getIdAccArbol() : 0;
        i_idAccCausa = at.getIdAccCausa() != null ? at.getIdAccCausa().getIdAccCausa() : 0;
        isEdit = true;

        Accidente a = accidenteFacadeLocal.find(i_idAccidente);
        isResponsable = a.getOperadorIsResponsable() != null ? a.getOperadorIsResponsable().equals(1) : false;
        if (a.getIdNovedad() != null) {
            isAfectaBonificacion = a.getIdNovedad().getProcede() == 1;
            puntosPmConciliados = a.getIdNovedad().getPuntosPmConciliados();
        }

//        if (at.getIdCausaRaiz() != null) {
//            i_idAccCausaRaiz = at.getIdCausaRaiz().getIdAccCausaRaiz();
//        }
//        if (at.getIdCausaSub() != null) {
//            i_idAccCausaSub = at.getIdCausaSub().getIdAccSubcausa();
//            if (at.getIdCausaSub().getIdCausa() != null) {
//                i_idAccCausa = at.getIdCausaSub().getIdCausa().getIdAccCausa();
//                if (at.getIdCausaSub().getIdCausa().getIdAccArbol() != null) {
//                    i_idAccArbol = at.getIdCausaSub().getIdCausa().getIdAccArbol().getIdAccArbol();
//                }
//            }
//        }
    }

    void cargarObjetos() {
        if (i_idAccArbol != 0) {
            accidenteAnalisis.setIdAccArbol(new AccArbol(i_idAccArbol));
        }
        if (i_idAccCausa != 0) {
            accidenteAnalisis.setIdAccCausa(new AccCausa(i_idAccCausa));
        }
    }

    public void reset() {
        accidenteAnalisis = null;
        i_idAccCausaRaiz = 0;
        i_idAccCausaSub = 0;
        i_idAccCausa = 0;
        i_idAccArbol = 0;
        listAccCausa = null;
        listAccCausaRaiz = null;
        listAccCausaSub = null;
        b_flag = true;
        isEdit = false;
        isResponsable = false;
        isAfectaBonificacion = false;
        puntosPmConciliados = 0;
    }

    public void actualizarOpcion(int i) {
        switch (i) {
            case 1: ;
                i_idAccCausaRaiz = 0;
                i_idAccCausaSub = 0;
                i_idAccCausa = 0;
                listAccCausa = null;
                listAccCausaRaiz = null;
                listAccCausaSub = null;
                break;
            case 2: ;
                i_idAccCausaRaiz = 0;
                i_idAccCausaSub = 0;
                listAccCausaRaiz = null;
                listAccCausaSub = null;
                break;
        }
    }

    public AccidenteAnalisis getAccidenteAnalisis() {
        return accidenteAnalisis;
    }

    public void setAccidenteAnalisis(AccidenteAnalisis accidenteAnalisis) {
        this.accidenteAnalisis = accidenteAnalisis;
    }

    public List<AccidenteAnalisis> getListAccidenteAnalisis() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        listAccidenteAnalisis = accidenteAnalisisFacadeLocal.estadoReg(i_idAccidente);
        return listAccidenteAnalisis;
    }

    public int getI_idAccidente() {
        return i_idAccidente;
    }

    public void setI_idAccidente(int i_idAccidente) {
        this.i_idAccidente = i_idAccidente;
    }

    public int getI_idAccCausaRaiz() {
        return i_idAccCausaRaiz;
    }

    public void setI_idAccCausaRaiz(int i_idAccCausaRaiz) {
        this.i_idAccCausaRaiz = i_idAccCausaRaiz;
    }

    public int getI_idAccCausaSub() {
        return i_idAccCausaSub;
    }

    public void setI_idAccCausaSub(int i_idAccCausaSub) {
        this.i_idAccCausaSub = i_idAccCausaSub;
    }

    public int getI_idAccArbol() {
        return i_idAccArbol;
    }

    public void setI_idAccArbol(int i_idAccArbol) {
        this.i_idAccArbol = i_idAccArbol;
    }

    public int getI_idAccCausa() {
        return i_idAccCausa;
    }

    public void setI_idAccCausa(int i_idAccCausa) {
        this.i_idAccCausa = i_idAccCausa;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    public void setB_flag(boolean b_flag) {
        this.b_flag = b_flag;
    }

    public List<AccCausa> getListAccCausa() {
        if (i_idAccArbol != 0) {
            listAccCausa = accCausaFacadeLocal.findByArbol(i_idAccArbol);
        }
        return listAccCausa;
    }

    public List<AccCausaSub> getListAccCausaSub() {
        if (i_idAccCausa != 0) {
            listAccCausaSub = accCausaSubFacadeLocal.findByCausa(i_idAccCausa);
        }
        return listAccCausaSub;
    }

    public List<AccCausaRaiz> getListAccCausaRaiz() {
        if (i_idAccCausaSub != 0) {
            listAccCausaRaiz = accCausaRaizFacadeLocal.findByCausaSub(i_idAccCausaSub);
        }
        return listAccCausaRaiz;
    }

    public boolean isIsResponsable() {
        return isResponsable;
    }

    public void setIsResponsable(boolean isResponsable) {
        this.isResponsable = isResponsable;
    }

    public boolean isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean isIsAfectaBonificacion() {
        return isAfectaBonificacion;
    }

    public void setIsAfectaBonificacion(boolean isAfectaBonificacion) {
        this.isAfectaBonificacion = isAfectaBonificacion;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public int getPuntosPmConciliados() {
        return puntosPmConciliados;
    }

    public void setPuntosPmConciliados(int puntosPmConciliados) {
        this.puntosPmConciliados = puntosPmConciliados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
