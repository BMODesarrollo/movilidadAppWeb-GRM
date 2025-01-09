/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTipoFacadeLocal;
import com.movilidad.ejb.GenericaTurnoJornadaDetFacadeLocal;
import com.movilidad.ejb.GenericaTurnoJornadaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.GenericaTurnoJornada;
import com.movilidad.model.GenericaTurnoJornadaDet;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "genTurnoJornadaMB")
@ViewScoped
public class GenericaTurnoJornadaJSFMB implements Serializable {

    @EJB
    private GenericaTurnoJornadaFacadeLocal turnoJornadaFacadeLocal;
    @EJB
    private GenericaJornadaTipoFacadeLocal tipoFacadeLocal;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private EmpleadoTipoCargoFacadeLocal emplTipoCargoEJB;
    @EJB
    private GenericaTurnoJornadaDetFacadeLocal genTurnoJornadaDetEJB;

    private List<GenericaTurnoJornada> listJornadaTurno;
    private List<GenericaJornadaTipo> listJornadaTipo;
    private List<EmpleadoTipoCargo> listCargos;
    private List<GenericaTurnoJornadaDet> listaDetalles;
    private GenericaTurnoJornada genericaTurnoJornada;
    private ParamAreaUsr paramAreaUsr;

    private HashMap<Integer, GenericaTurnoJornada> mapGenericaTurnoJornada;
    private HashMap<Integer, Integer> mapColaboradoresCargoHabil;
    private HashMap<Integer, Integer> mapColaboradoresCargoFeriado;
    private HashMap<Integer, Integer> mapTotalColaboradoresCargo;

    private Integer idGenericaJornadaTipo;
    private Integer idTotalEmpleadoArea;

    UserExtended user;

    public GenericaTurnoJornadaJSFMB() {
    }

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        listJornadaTurno = new ArrayList<>();
        listJornadaTipo = null;
        genericaTurnoJornada = null;
        idGenericaJornadaTipo = null;
        mapGenericaTurnoJornada = new HashMap<>();
        mapColaboradoresCargoHabil = new HashMap<>();
        mapColaboradoresCargoFeriado = new HashMap<>();
        mapTotalColaboradoresCargo = new HashMap<>();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        List<Empleado> listEmp = empleadoFacadeLocal.getEmpledosByIdArea(paramAreaUsr.getIdParamArea().getIdParamArea(), 0);
        idTotalEmpleadoArea = listEmp != null ? listEmp.size() : 0;
    }

    public void preCreate() {
        genericaTurnoJornada = new GenericaTurnoJornada();
        cargarTiposJornada();
        PrimeFaces.current().executeScript("PF('turno_jornada_wv').show()");
    }

    public void cargarTiposJornada() {
        listJornadaTipo = tipoFacadeLocal.findAllByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
    }

    public List<GenericaTurnoJornadaDet> getGenericaTurnoJorDet(GenericaTurnoJornada genTurJor) {
        return genTurnoJornadaDetEJB.getByIdGenericaTurnoJornada(genTurJor.getIdGenericaTurnoJornada());
    }

    public void guardarDetalle() {
        if (validarNumColaboradoresArea()) {
            return;
        }
        if (validarNumColaboradoresCargo()) {
            return;
        }
        for (GenericaTurnoJornadaDet f : listaDetalles) {

            if ((f.getCantidadHabil() != null && f.getCantidadHabil() > 0) || (f.getCantidadFeriada() != null && f.getCantidadFeriada() > 0)) {
                f.setCreado(MovilidadUtil.fechaCompletaHoy());
                if (f.getIdGenericaTurnoJornadaDet() == null) {
                    genTurnoJornadaDetEJB.create(f);
                } else {
                    genTurnoJornadaDetEJB.edit(f);
                }
            }
        }
        MovilidadUtil.addSuccessMessage("Acción realizada exitosamente");
        PrimeFaces.current().executeScript("PF('adicionar_detalle_wv').hide()");

    }

    public void onAgregarDetalle(GenericaTurnoJornada genTurnoJornada) {
        HashMap<Integer, GenericaTurnoJornadaDet> mapa = new HashMap<>();
        listaDetalles = new ArrayList<>();
        List<GenericaTurnoJornadaDet> listaDetallesRemota = genTurnoJornadaDetEJB.getByIdGenericaTurnoJornada(genTurnoJornada.getIdGenericaTurnoJornada());
        for (GenericaTurnoJornadaDet det : listaDetallesRemota) {
            mapa.put(det.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), det);
        }
        listCargos = emplTipoCargoEJB.getCargosPorArea(paramAreaUsr.getIdParamArea().getIdParamArea());
        GenericaTurnoJornadaDet gtjd;
        for (EmpleadoTipoCargo etc : listCargos) {
            gtjd = new GenericaTurnoJornadaDet();
            gtjd.setIdEmpleadoTipoCargo(etc);
            gtjd.setIdGenericaTurnoJornada(genTurnoJornada);
            gtjd.setEstadoReg(0);
            gtjd.setUsername(user.getUsername());
            if (mapa.containsKey(etc.getIdEmpleadoTipoCargo())) {
                gtjd.setIdGenericaTurnoJornadaDet(mapa.get(etc.getIdEmpleadoTipoCargo()).getIdGenericaTurnoJornadaDet());
                gtjd.setCantidadHabil(mapa.get(etc.getIdEmpleadoTipoCargo()).getCantidadHabil());
                gtjd.setCantidadFeriada(mapa.get(etc.getIdEmpleadoTipoCargo()).getCantidadFeriada());
            }
            listaDetalles.add(gtjd);
        }
        cargarCantidadColaboradoresCargo(listaDetalles);
        PrimeFaces.current().executeScript("PF('adicionar_detalle_wv').show()");

    }

    public String esPar(int numero) {
        if (numero % 2 == 0) {
            return "background-color: #F3F6F9;";
        } else {
            return "background-color: gainsboro;";
        }
    }

    public void onGenericaJornadaTurno(GenericaTurnoJornada event) {
        idGenericaJornadaTipo = event.getIdGenericaJornadaTipo().getIdGenericaJornadaTipo();
        genericaTurnoJornada = event;
        cargarTiposJornada();
        PrimeFaces.current().executeScript("PF('turno_jornada_wv').show()");
    }

    public void guardar() {
        if (genericaTurnoJornada != null) {
            if (idGenericaJornadaTipo == null) {
                MovilidadUtil.addErrorMessage("Jornada Tipo es requerido");
                return;
            }
            cargarMap();
            if (mapGenericaTurnoJornada.containsKey(idGenericaJornadaTipo)) {
                MovilidadUtil.addErrorMessage("Jornada Tipo seleccionada se "
                        + "encuentra registrada para el area de "
                        + mapGenericaTurnoJornada.get(idGenericaJornadaTipo).getIdGenericaJornadaTipo().getIdParamArea().getArea().toUpperCase());
                return;
            }

            if (validarColaboradores()) {
                MovilidadUtil.addErrorMessage("Excede el máximo de colaboradores de su área");
                return;
            }
            if (genericaTurnoJornada.getColor() == null) {
                genericaTurnoJornada.setColor("ff0000");
            }
            Date d = new Date();
            genericaTurnoJornada.setIdGenericaJornadaTipo(new GenericaJornadaTipo(idGenericaJornadaTipo));
            genericaTurnoJornada.setUsername(user.getUsername());
            genericaTurnoJornada.setCreado(d);
            genericaTurnoJornada.setModificado(d);
            genericaTurnoJornada.setEstadoReg(0);
            turnoJornadaFacadeLocal.create(genericaTurnoJornada);
            MovilidadUtil.addSuccessMessage("Jornada Turno agregado correctamente");
//            reset();
            idGenericaJornadaTipo = null;
            genericaTurnoJornada = new GenericaTurnoJornada();
        }
    }

    public void actualizar() {
        if (genericaTurnoJornada != null) {
            if (idGenericaJornadaTipo == null) {
                MovilidadUtil.addErrorMessage("Jornada Tipo es requerido");
                return;
            }
            if (!idGenericaJornadaTipo.equals(genericaTurnoJornada.getIdGenericaJornadaTipo().getIdGenericaJornadaTipo())) {
                cargarMap();
                if (mapGenericaTurnoJornada.containsKey(idGenericaJornadaTipo)) {
                    MovilidadUtil.addErrorMessage("Jornada Tipo seleccionada se "
                            + "encuentra registrada para el area de "
                            + mapGenericaTurnoJornada.get(idGenericaJornadaTipo).getIdGenericaJornadaTipo().getIdParamArea().getArea().toUpperCase());
                    return;
                }
            }
            if (validarColaboradores()) {
                MovilidadUtil.addErrorMessage("Excede el máximo de colaboradores de su área");
                return;
            }
            if (genericaTurnoJornada.getColor() == null) {
                genericaTurnoJornada.setColor("ff0000");
            }
            genericaTurnoJornada.setIdGenericaJornadaTipo(new GenericaJornadaTipo(idGenericaJornadaTipo));
            Date d = new Date();
            genericaTurnoJornada.setModificado(d);
            turnoJornadaFacadeLocal.edit(genericaTurnoJornada);
            MovilidadUtil.addSuccessMessage("Jornada Turno actualizado correctamente");
            reset();
            PrimeFaces.current().executeScript("PF('turno_jornada_wv').hide()");

        }
    }

    boolean validarNumColaboradoresCargo() {
        for (GenericaTurnoJornadaDet f : listaDetalles) {
            int id = f.getIdGenericaTurnoJornadaDet() != null ? f.getIdGenericaTurnoJornadaDet() : 0;
            int size = empleadoFacadeLocal.getEmpledosByIdCargoActivos(f.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), 0).size();
            if (f.getCantidadHabil() != null && f.getCantidadHabil() > 0) {
                double sumCantidad = genTurnoJornadaDetEJB.getSumCantidad(1, f.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), id);
                sumCantidad = sumCantidad + f.getCantidadHabil();
                if (size < sumCantidad) {
                    MovilidadUtil.addErrorMessage("Cantidad Habil - Número de empleados ingresados, "
                            + "supera el número de empleados en el sistema "
                            + f.getIdEmpleadoTipoCargo().getNombreCargo().toUpperCase());
                    return true;
                }
            }
            if (f.getCantidadFeriada() != null && f.getCantidadFeriada() > 0) {
                double sumCantidad = genTurnoJornadaDetEJB.getSumCantidad(2, f.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), id);
                sumCantidad = sumCantidad + f.getCantidadFeriada();
                if (size < sumCantidad) {
                    MovilidadUtil.addErrorMessage("Cantidad Feriada - Número de empleados ingresados, "
                            + "supera el número de empleados en el sistema "
                            + f.getIdEmpleadoTipoCargo().getNombreCargo().toUpperCase());
                    return true;
                }
            }
        }
        return false;
    }

    boolean validarNumColaboradoresArea() {
        int contador = 0;
        GenericaTurnoJornada genTurJor = listaDetalles.get(0).getIdGenericaTurnoJornada();
        for (GenericaTurnoJornadaDet gtjd : listaDetalles) {
            if (gtjd.getCantidadHabil() != null) {
                contador = contador + gtjd.getCantidadHabil();
            }
        }
        if (contador > genTurJor.getDiaHabil()) {
            MovilidadUtil.addErrorMessage("Limite definido en la parametrización de turnos para días habiles,es excedido");
            return true;
        }
        contador = 0;
        for (GenericaTurnoJornadaDet gtjd : listaDetalles) {
            if (gtjd.getCantidadFeriada() != null) {
                contador = contador + gtjd.getCantidadFeriada();
            }
        }
        if (contador > genTurJor.getDiaFeriado()) {
            MovilidadUtil.addErrorMessage("Limite definido en la parametrización de turnos para días feriados,es excedido");
            return true;
        }
        return false;
    }

    boolean validarColaboradores() {
        int acumulador = 0;
        int suma;
        int id = genericaTurnoJornada.getIdGenericaTurnoJornada() != null ? genericaTurnoJornada.getIdGenericaTurnoJornada() : 0;
        List<GenericaTurnoJornada> findEstadoReg = turnoJornadaFacadeLocal.findEstadoReg(paramAreaUsr.getIdParamArea().getIdParamArea(), id);
        List<Empleado> empledos = empleadoFacadeLocal.getEmpledosByIdArea(paramAreaUsr.getIdParamArea().getIdParamArea(), 0);
        suma = genericaTurnoJornada.getDiaHabil();
        for (GenericaTurnoJornada gtj : findEstadoReg) {
            acumulador = acumulador + gtj.getDiaHabil();
        }
        acumulador = acumulador + suma;
        if (empledos != null && !empledos.isEmpty()) {
            if (empledos.size() >= acumulador) {
                return false;
            }
        }
        acumulador = 0;
        suma = genericaTurnoJornada.getDiaFeriado();
        for (GenericaTurnoJornada gtj : findEstadoReg) {
            acumulador = acumulador + gtj.getDiaFeriado();
        }
        acumulador = acumulador + suma;
        if (empledos != null && !empledos.isEmpty()) {
            if (empledos.size() >= acumulador) {
                return false;
            }
        }
        return true;
    }

    void cargarMap() {
        List<GenericaTurnoJornada> findEstadoReg = turnoJornadaFacadeLocal.findEstadoReg(paramAreaUsr.getIdParamArea().getIdParamArea(), 0);
        mapGenericaTurnoJornada.clear();
        for (GenericaTurnoJornada gtj : findEstadoReg) {
            mapGenericaTurnoJornada.put(gtj.getIdGenericaJornadaTipo().getIdGenericaJornadaTipo(), gtj);
        }
    }

    void reset() {
        listJornadaTipo = null;
        genericaTurnoJornada = null;
        idGenericaJornadaTipo = null;
    }

    void cargarCantidadColaboradoresCargo(List<GenericaTurnoJornadaDet> listaDetallesAux) {
        mapColaboradoresCargoHabil.clear();
        mapColaboradoresCargoFeriado.clear();
        mapTotalColaboradoresCargo.clear();
        for (GenericaTurnoJornadaDet gjtd : listaDetallesAux) {
            if (!mapTotalColaboradoresCargo.containsKey(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo())) {
                List<Empleado> listEmp = empleadoFacadeLocal.getEmpledosByIdCargoActivos(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), 0);
                if (listEmp != null) {
                    mapTotalColaboradoresCargo.put(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), listEmp.size());
                }
            }
        }
        List<GenericaTurnoJornada> findEstadoReg = turnoJornadaFacadeLocal.findEstadoReg(paramAreaUsr.getIdParamArea().getIdParamArea(), 0);
        for (GenericaTurnoJornada gtj : findEstadoReg) {
            List<GenericaTurnoJornadaDet> genericaTurnoJornadaDetList = gtj.getGenericaTurnoJornadaDetList();
            for (GenericaTurnoJornadaDet gjtd : genericaTurnoJornadaDetList) {
                if (gjtd.getCantidadHabil() != null) {
                    if (mapColaboradoresCargoHabil.containsKey(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo())) {
                        Integer sum = mapColaboradoresCargoHabil.get(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo());
                        mapColaboradoresCargoHabil.put(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), sum + gjtd.getCantidadHabil());
                    } else {
                        mapColaboradoresCargoHabil.put(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), gjtd.getCantidadHabil());
                    }
                }
                if (gjtd.getCantidadFeriada() != null) {
                    if (mapColaboradoresCargoFeriado.containsKey(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo())) {
                        Integer sum = mapColaboradoresCargoFeriado.get(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo());
                        mapColaboradoresCargoFeriado.put(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), sum + gjtd.getCantidadFeriada());
                    } else {
                        mapColaboradoresCargoFeriado.put(gjtd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), gjtd.getCantidadFeriada());
                    }
                }
            }
        }
    }

    public Integer getTotalColaboradoresHabilRegistrados() {
        int x = 0;
        for (GenericaTurnoJornada gtj : listJornadaTurno) {
            x = x + gtj.getDiaHabil();
        }
        return x;
    }

    public Integer getTotalColaboradoresFariadoRegistrados() {
        int x = 0;
        for (GenericaTurnoJornada gtj : listJornadaTurno) {
            x = x + gtj.getDiaFeriado();
        }
        return x;
    }

    public Integer getEmpleadosDisponiblesHabiles(Integer idCargo) {
        return mapColaboradoresCargoHabil.get(idCargo);
    }

    public Integer getEmpleadosDisponiblesFeriado(Integer idCargo) {
        return mapColaboradoresCargoFeriado.get(idCargo);
    }

    public Integer getEmpleadoTotalCargo(Integer idCargo) {
        return mapTotalColaboradoresCargo.get(idCargo);
    }

    public List<GenericaTurnoJornada> getListJornadaTurno() {
        listJornadaTurno = turnoJornadaFacadeLocal.findEstadoReg(paramAreaUsr.getIdParamArea().getIdParamArea(), 0);
        getTotalColaboradoresFariadoRegistrados();
        getTotalColaboradoresHabilRegistrados();
        return listJornadaTurno;
    }

    public void setListJornadaTurno(List<GenericaTurnoJornada> listJornadaTurno) {
        this.listJornadaTurno = listJornadaTurno;
    }

    public GenericaTurnoJornada getGenericaTurnoJornada() {
        return genericaTurnoJornada;
    }

    public void setGenericaTurnoJornada(GenericaTurnoJornada genericaTurnoJornada) {
        this.genericaTurnoJornada = genericaTurnoJornada;
    }

    public List<GenericaJornadaTipo> getListJornadaTipo() {
        return listJornadaTipo;
    }

    public void setListJornadaTipo(List<GenericaJornadaTipo> listJornadaTipo) {
        this.listJornadaTipo = listJornadaTipo;
    }

    public Integer getIdGenericaJornadaTipo() {
        return idGenericaJornadaTipo;
    }

    public void setIdGenericaJornadaTipo(Integer idGenericaJornadaTipo) {
        this.idGenericaJornadaTipo = idGenericaJornadaTipo;
    }

    public List<EmpleadoTipoCargo> getListCargos() {
        return listCargos;
    }

    public void setListCargos(List<EmpleadoTipoCargo> listCargos) {
        this.listCargos = listCargos;
    }

    public List<GenericaTurnoJornadaDet> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<GenericaTurnoJornadaDet> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public Integer getIdTotalEmpleadoArea() {
        return idTotalEmpleadoArea;
    }

    public void setIdTotalEmpleadoArea(Integer idTotalEmpleadoArea) {
        this.idTotalEmpleadoArea = idTotalEmpleadoArea;
    }

}
