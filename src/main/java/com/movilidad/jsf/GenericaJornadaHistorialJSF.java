/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.GenericaJornadaHistorialFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaHistorial;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "genericaJornadaGestionJSF")
@ViewScoped
public class GenericaJornadaHistorialJSF implements Serializable {

    @EJB
    private GenericaJornadaHistorialFacadeLocal historialFacadeLocal;
    @EJB
    private GenericaJornadaFacadeLocal jornadaFacadeLocal;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacade;

    private List<GenericaJornada> listGenericaJornada;
    private List<Empleado> listEmpleados;
    private ParamAreaUsr paramAreaUsr;
    private Date dDesde;
    private Date dHasta;
    private Empleado empleado;

    UserExtended user;

    public GenericaJornadaHistorialJSF() {
    }

    @PostConstruct
    public void init() {
        paramAreaUsr = null;
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        listGenericaJornada = new ArrayList<>();
        dDesde = new Date();
        dHasta = new Date();
    }

    public void buscarJornadas() {
        listGenericaJornada.clear();
        if (validarDatos(dDesde, dHasta)) {
            return;
        }
        if (paramAreaUsr == null) {
            MovilidadUtil.addErrorMessage("Proceso no permitido");
            return;
        }
        int id_empleado = 0;
        if (empleado != null) {
            id_empleado = empleado.getIdEmpleado() != null ? empleado.getIdEmpleado() : 0;
        }
        List<GenericaJornada> listGJ = jornadaFacadeLocal.findByDateAndLiquidado(dDesde,
                dHasta,
                paramAreaUsr.getIdParamArea().getIdParamArea(),
                Util.ID_LIQUIDADO,
                id_empleado);
        if (listGJ != null && !listGJ.isEmpty()) {
            MovilidadUtil.addErrorMessage("Entre las fechas seleccionadas, existen Jornadas liquidadas. No se permite realizar el procedimiento");
            return;
        }
        listGenericaJornada = jornadaFacadeLocal.findByDateAndLiquidado(dDesde,
                dHasta,
                paramAreaUsr.getIdParamArea().getIdParamArea(),
                Util.ID_NO_LIQUIDADO, id_empleado);
        if (listGenericaJornada != null && listGenericaJornada.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron registros entre las fechas indicadas.");
            return;
        }
        MovilidadUtil.addSuccessMessage("Registros encontrados");
        PrimeFaces.current().executeScript("PF('jornadaWV').hide()");
    }

    public void cargarEmpleados() {
        listEmpleados = empleadoFacade.getEmpledosByIdArea(paramAreaUsr.getIdParamArea().getIdParamArea(), 0);
    }

    /**
     * Cargar los registros en historial y eliminarlos en Generica_Jornada
     */
    @Transactional
    public void onEliminarJornadaSeleccionada() {
        try {
            if (validarDatos(dDesde, dHasta)) {
                return;
            }
            int id_empleado = 0;
            if (empleado != null) {
                id_empleado = empleado.getIdEmpleado() != null ? empleado.getIdEmpleado() : 0;
            }
            List<GenericaJornada> listGJ = jornadaFacadeLocal.findByDateAndLiquidado(dDesde,
                    dHasta,
                    paramAreaUsr.getIdParamArea().getIdParamArea(),
                    Util.ID_LIQUIDADO, id_empleado);
            if (listGJ != null && !listGJ.isEmpty()) {
                MovilidadUtil.addErrorMessage("Entre las fechas seleccionadas, existen Jornadas liquidadas. No se permite realizar el procedimiento");
                return;
            }
            
            listGenericaJornada = jornadaFacadeLocal.findByDateAndLiquidado(dDesde,
                    dHasta,
                    paramAreaUsr.getIdParamArea().getIdParamArea(),
                    Util.ID_NO_LIQUIDADO, id_empleado);
            if (listGenericaJornada != null && listGenericaJornada.isEmpty()) {
                MovilidadUtil.addErrorMessage("No se encontraron registros entre las fechas indicadas.");
                reset();
                return;
            }
            List<GenericaJornadaHistorial> listGenericaJornadaHistorial = new ArrayList<>();
            for (GenericaJornada gj : listGenericaJornada) {
                listGenericaJornadaHistorial.add(getGenJorHisFromGenJor(gj));
            }
            //crear el historial
            for (GenericaJornadaHistorial gejh : listGenericaJornadaHistorial) {
                historialFacadeLocal.create(gejh);
            }
            //eliminar generica jornada
            jornadaFacadeLocal.eliminarJornadaInicial(paramAreaUsr.getIdParamArea().getIdParamArea(), dDesde, dHasta, id_empleado);
            jornadaFacadeLocal.eliminarJornada(paramAreaUsr.getIdParamArea().getIdParamArea(), dDesde, dHasta, id_empleado);
            MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
            reset();
        } catch (Exception e) {
            reset();
            MovilidadUtil.addErrorMessage("Error al realizar procedimiento");
        }
    }

    boolean validarDatos(Date desde, Date hasta) {
        try {
            if (MovilidadUtil.fechasIgualMenorMayor(hasta, desde, false) == 0) {
                MovilidadUtil.addErrorMessage("Fecha Hasta no puede ser inferior a Fecha Desde");
                return true;
            }
            return false;
        } catch (ParseException e) {
            return false;
        }
    }

    public void reset() {
        listGenericaJornada = new ArrayList<>();
        dDesde = new Date();
        dHasta = new Date();
        empleado = null;
    }

    public void onRowSelectEmpleado(SelectEvent event) {
        empleado = (Empleado) event.getObject();
        if (empleado != null) {
//            b_cont = true;
//            getSelected().setNombres(empleado.getNombres() + " " + empleado.getApellidos());
//            getSelected().setIdEmpleado(empleado);
//            MovilidadUtil.addSuccessMessage("Empleado valido");
        }
        PrimeFaces.current().executeScript("PF('empleDlg').hide();");
    }

    GenericaJornadaHistorial getGenJorHisFromGenJor(GenericaJornada gj) {
        GenericaJornadaHistorial gjh = new GenericaJornadaHistorial();
        gjh.setAutorizado(gj.getAutorizado());
        gjh.setCargada(gj.getCargada());
        gjh.setCompensatorio(gj.getCompensatorio());
        gjh.setCreado(gj.getCreado());
        gjh.setCreadoElimina(new Date());
        gjh.setDiurnas(gj.getDiurnas());
        gjh.setDominicalCompDiurnaExtra(gj.getDominicalCompDiurnaExtra());
        gjh.setDominicalCompDiurnas(gj.getDominicalCompDiurnas());
        gjh.setDominicalCompNocturnaExtra(gj.getDominicalCompNocturnaExtra());
        gjh.setDominicalCompNocturnas(gj.getDominicalCompNocturnas());
        gjh.setEstadoReg(gj.getEstadoReg());
        gjh.setEstadoRegElimina(0);
        gjh.setExtraDiurna(gj.getExtraDiurna());
        gjh.setExtraNocturna(gj.getExtraNocturna());
        gjh.setFecha(gj.getFecha());
        gjh.setFechaAutoriza(gj.getFechaAutoriza());
        gjh.setFechaGenera(gj.getFechaGenera());
        gjh.setFechaLiquida(gj.getFechaLiquida());
        gjh.setFestivoDiurno(gj.getFestivoDiurno());
        gjh.setFestivoExtraDiurno(gj.getFestivoExtraDiurno());
        gjh.setFestivoExtraNocturno(gj.getFestivoExtraNocturno());
        gjh.setFestivoNocturno(gj.getFestivoNocturno());
        gjh.setHfinTurno2(gj.getHfinTurno2());
        gjh.setHfinTurno3(gj.getHfinTurno3());
        gjh.setHiniTurno2(gj.getHiniTurno2());
        gjh.setHiniTurno3(gj.getHiniTurno3());
        gjh.setIdEmpleado(gj.getIdEmpleado() != null ? gj.getIdEmpleado().getIdEmpleado() : null);
        gjh.setIdGenericaJornadaMotivo(gj.getIdGenericaJornadaMotivo() != null ? gj.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo() : null);
        gjh.setIdGenericaJornadaTipo(gj.getIdGenericaJornadaTipo() != null ? gj.getIdGenericaJornadaTipo().getIdGenericaJornadaTipo() : null);
        gjh.setIdParamArea(gj.getIdParamArea() != null ? gj.getIdParamArea().getIdParamArea() : null);
        gjh.setLiquidado(gj.getLiquidado());
        gjh.setModificado(gj.getModificado());
        gjh.setNocturnas(gj.getNocturnas());
        gjh.setNominaBorrada(gj.getNominaBorrada());
        gjh.setObservaciones(gj.getObservaciones());
        gjh.setOrdenTrabajo(gj.getOrdenTrabajo());
        gjh.setPrgModificada(gj.getPrgModificada());
        gjh.setProductionTime(gj.getProductionTime());
        gjh.setProductionTimeReal(gj.getProductionTimeReal());
        gjh.setRealTimeDestiny(gj.getRealTimeDestiny());
        gjh.setRealTimeOrigin(gj.getTimeOrigin());
        gjh.setSercon(gj.getSercon());
        gjh.setTimeDestiny(gj.getTimeDestiny());
        gjh.setTimeOrigin(gj.getTimeOrigin());
        gjh.setTipoCalculo(gj.getTipoCalculo());
        gjh.setUserAutorizado(gj.getUserAutorizado());
        gjh.setUserGenera(gj.getUserGenera());
        gjh.setUserLiquida(gj.getUserLiquida());
        gjh.setUsername(gj.getUsername());
        gjh.setUserElimina(user.getUsername());
        gjh.setIdGenericaJornada(gj.getIdGenericaJornada());
        return gjh;
    }

    public List<GenericaJornada> getListGenericaJornada() {
        return listGenericaJornada;
    }

    public void setListGenericaJornada(List<GenericaJornada> listGenericaJornada) {
        this.listGenericaJornada = listGenericaJornada;
    }

    public Date getdDesde() {
        return dDesde;
    }

    public void setdDesde(Date dDesde) {
        this.dDesde = dDesde;
    }

    public Date getdHasta() {
        return dHasta;
    }

    public void setdHasta(Date dHasta) {
        this.dHasta = dHasta;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}
