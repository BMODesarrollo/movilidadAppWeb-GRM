/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.GenericaTurnoJornadaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.GenericaPrgJornada;
import com.movilidad.model.GenericaTurnoJornada;
import com.movilidad.model.GenericaTurnoJornadaDet;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.ParamFeriado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.component.timeline.Timeline;
import org.primefaces.component.timeline.TimelineUpdater;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "genTurnosJornadaCrearMB")
@ViewScoped
public class GenericaTurnosJornadaCrearJSFB implements Serializable {

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private GenericaJornadaFacadeLocal genJornadaEJB;
    @EJB
    private GenericaTurnoJornadaFacadeLocal genTurnoJorEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEJB;
    @EJB
    private ParamFeriadoFacadeLocal paramFeriadoEJB;

    public GenericaTurnosJornadaCrearJSFB() {
    }

    private String rol_user = "";
    private ParamAreaUsr pau;
    private Date fechaDesde = Util.getPrimerDiaMes(MovilidadUtil.fechaHoy());
    private Date fechaHasta = Util.getUltimoDiaMes(MovilidadUtil.fechaHoy());
    private List<GenericaJornada> listGenericaJornada = new ArrayList<>();
    private List<GenericaPrgJornada> listGenPrgJorada = new ArrayList<>();

    private TimelineModel model;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            rol_user = auth.getAuthority();
        }
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        consultar();
        timeLineAux();
    }

    public void consultar() {
        if (pau == null) {
            listGenericaJornada = new ArrayList<>();
        } else {
            listGenericaJornada = genJornadaEJB.getByDate(fechaDesde, fechaHasta, pau.getIdParamArea().getIdParamArea());
        }
        timeLine();
    }

    public Date fecha(int opc) {
        return MovilidadUtil.sumarDias(fechaHasta, 2);
    }

    public String nombreByGenericaJornada(GenericaPrgJornada gpj) {
        return gpj.getIdEmpleado().getIdentificacion() + " - " + gpj.getIdEmpleado().getNombres() + " " + gpj.getIdEmpleado().getApellidos();
    }

    public void timeLine() {
//        // create timeline model
//        model = new TimelineModel();
//        for (GenericaJornada gj : listGenericaJornada) {
//            String horaInicio = gj.getTimeOrigin();
//            String horaFin = gj.getTimeDestiny();
//            String title = gj.getTimeOrigin() + "-" + gj.getTimeDestiny();
//            String css = "css_" + getColor(gj);
//            if (MovilidadUtil.toSecs(horaInicio) == 0 && MovilidadUtil.toSecs(horaFin) == 0) {
//                horaInicio = "12:00:00";
//                horaFin = "24:00:00";
//                title = "DESCANSO";
//                css = "descanso";
//            }
//            String start = Util.dateFormat(gj.getFecha()) + " " + horaInicio;
//            String end = Util.dateFormat(gj.getFecha()) + " " + horaFin;
//            // create an event with content, fechaDesde / fechaHasta dates, editable flag, group name and custom style class
//            TimelineEvent event = new TimelineEvent(title, Util.dateTimeFormat(start), Util.dateTimeFormat(end), true, nombreByGenericaJornada(gj), css);
//            model.add(event);
//        }
    }

    public void timeLineAux() {
        // create timeline model
        model = new TimelineModel();
        simular1();
        for (GenericaPrgJornada gpj : listGenPrgJorada) {
            String horaInicio = gpj.getTimeOrigin();
            String horaFin = gpj.getTimeDestiny();
            String title = gpj.getTimeOrigin() + "-" + gpj.getTimeDestiny();
            String css = "css_" + getColor(gpj);
            if (MovilidadUtil.toSecs(horaInicio) == 0 && MovilidadUtil.toSecs(horaFin) == 0) {
                horaInicio = "12:00:00";
                horaFin = "24:00:00";
                title = "DESCANSO";
                css = "descanso";
            }
            String start = Util.dateFormat(gpj.getFecha()) + " " + horaInicio;
            String end = Util.dateFormat(gpj.getFecha()) + " " + horaFin;
            // create an event with content, fechaDesde / fechaHasta dates, editable flag, group name and custom style class
            TimelineEvent event = new TimelineEvent(gpj, Util.dateTimeFormat(start), Util.dateTimeFormat(end), true, nombreByGenericaJornada(gpj), css);
            model.add(event);
        }
    }

    public String getColor(GenericaPrgJornada gpj) {
        try {
            return gpj.getIdGenericaJornadaTipo().getGenericaTurnoJornadaList().get(0).getColor();
        } catch (Exception e) {
            return "";
        }
    }

    public void simular() {
        List<GenericaJornada> listGenJornada = genJornadaEJB.getUltimaJornadaMesByArea(fechaDesde, fechaHasta, pau.getIdParamArea().getIdParamArea());
        List<GenericaPrgJornada> listGenPrgJoradaHabil = new ArrayList<>();
        List<GenericaPrgJornada> listGenPrgJoradaFeriado = new ArrayList<>();
        GenericaPrgJornada genPrgJorada;
        List<GenericaTurnoJornada> listGenTurno = genTurnoJorEJB.findEstadoReg(pau.getIdParamArea().getIdParamArea(), 0);
        HashMap<Integer, List<Empleado>> mapEmpleadosHabil = new HashMap<>();
        HashMap<Integer, List<Empleado>> mapEmpleadosFeriado = new HashMap<>();
        HashMap<Integer, GenericaJornadaTipo> mapGenericaJornadaTipo = new HashMap<>();
        HashMap<Integer, GenericaJornada> mapGenericaJornadaHabil = new HashMap<>();
        HashMap<Integer, GenericaJornada> mapGenericaJornadaFeriado = new HashMap<>();
        for (GenericaJornada gj : listGenJornada) {
            mapGenericaJornadaTipo.put(gj.getIdGenericaJornadaTipo().getIdGenericaJornadaTipo(), gj.getIdGenericaJornadaTipo());
        }
        List<ParamFeriado> listDiasFeriados = paramFeriadoEJB.findAllByFechaMes(Util.getPrimerDiaMes(fechaDesde), Util.getUltimoDiaMes(fechaHasta));
        HashMap<String, ParamFeriado> mapDiaFeriados = new HashMap<>();
        for (ParamFeriado pf : listDiasFeriados) {
            mapDiaFeriados.put(Util.dateFormat(pf.getFecha()), pf);
        }
        for (GenericaJornada gj : listGenJornada) {
            if (!MovilidadUtil.isSunday(gj.getFecha()) || !mapDiaFeriados.containsKey(Util.dateFormat(gj.getFecha()))) {
                mapGenericaJornadaHabil.put(gj.getIdEmpleado().getIdEmpleado(), gj);
            }
        }
        for (GenericaJornada gj : listGenJornada) {
            if (MovilidadUtil.isSunday(gj.getFecha()) || mapDiaFeriados.containsKey(Util.dateFormat(gj.getFecha()))) {
                mapGenericaJornadaFeriado.put(gj.getIdEmpleado().getIdEmpleado(), gj);
            }
        }
        HashMap<Integer, List<Date>> mapDiaPorSemanas = Util.getMapDiaPorSemanas(fechaDesde);
        for (int y = 0; y < mapDiaPorSemanas.size(); y++) {
            for (Date d : mapDiaPorSemanas.get(y + 1)) {
                for (GenericaTurnoJornada gtj : listGenTurno) {
                    List<GenericaTurnoJornadaDet> listGenTurJorDet = gtj.getGenericaTurnoJornadaDetList();
                    for (GenericaTurnoJornadaDet gtjd : listGenTurJorDet) {
                        int id = gtjd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo();
                        List<Empleado> empledosByIdCargoActivos = empleadoEJB.getEmpledosByIdCargoActivos(id, 0);
                        mapEmpleadosHabil.put(id, empledosByIdCargoActivos);
                        mapEmpleadosFeriado.put(id, new ArrayList<>(empledosByIdCargoActivos));
                    }
                }
                for (GenericaTurnoJornada gtj : listGenTurno) {
                    List<GenericaTurnoJornadaDet> listGenTurJorDet = gtj.getGenericaTurnoJornadaDetList();
                    if (listGenTurJorDet != null) {
                        EmpleadoTipoCargo empTipoCargo;
                        for (GenericaTurnoJornadaDet gtjd : listGenTurJorDet) {
                            empTipoCargo = gtjd.getIdEmpleadoTipoCargo();
                            List<Empleado> listEmpleadoHabil, listEmpleadoFeriado;
                            if (empTipoCargo != null) {
                                listEmpleadoHabil = mapEmpleadosHabil.get(empTipoCargo.getIdEmpleadoTipoCargo());
                                listEmpleadoFeriado = mapEmpleadosFeriado.get(empTipoCargo.getIdEmpleadoTipoCargo());
                                Collections.shuffle(listEmpleadoHabil);
                                Collections.shuffle(listEmpleadoFeriado);
                                if (listEmpleadoHabil != null) {
                                    if (gtjd.getCantidadHabil() != null && gtjd.getCantidadHabil() > 0) {
                                        if (!MovilidadUtil.isSunday(d) && !mapDiaFeriados.containsKey(Util.dateFormat(d))) {
                                            for (int i = 0; i < gtjd.getCantidadHabil(); i++) {
                                                genPrgJorada = new GenericaPrgJornada();
                                                genPrgJorada.setIdEmpleado(listEmpleadoHabil.get(i));
                                                genPrgJorada.setIdGenericaJornadaTipo(gtj.getIdGenericaJornadaTipo());
                                                genPrgJorada.setIdParamArea(pau.getIdParamArea());
                                                genPrgJorada.setTimeOrigin(gtj.getIdGenericaJornadaTipo().getHiniT1());
                                                genPrgJorada.setTimeDestiny(gtj.getIdGenericaJornadaTipo().getHfinT1());
                                                genPrgJorada.setUsername(user.getUsername());
                                                genPrgJorada.setCreado(MovilidadUtil.fechaCompletaHoy());
                                                genPrgJorada.setModificado(MovilidadUtil.fechaCompletaHoy());
                                                genPrgJorada.setEstadoReg(0);
                                                genPrgJorada.setFecha(d);
                                                listGenPrgJoradaHabil.add(genPrgJorada);
                                            }
                                            listEmpleadoHabil.subList(0, gtjd.getCantidadHabil()).clear();
                                        }
                                    }
                                }
                                if (listEmpleadoFeriado != null) {
                                    if (gtjd.getCantidadFeriada() != null && gtjd.getCantidadFeriada() > 0) {
                                        if (MovilidadUtil.isSunday(d) || mapDiaFeriados.containsKey(Util.dateFormat(d))) {
                                            for (int i = 0; i < gtjd.getCantidadFeriada(); i++) {
                                                genPrgJorada = new GenericaPrgJornada();
                                                genPrgJorada.setIdEmpleado(listEmpleadoFeriado.get(i));
                                                genPrgJorada.setIdGenericaJornadaTipo(gtj.getIdGenericaJornadaTipo());
                                                genPrgJorada.setIdParamArea(pau.getIdParamArea());
                                                genPrgJorada.setTimeOrigin(gtj.getIdGenericaJornadaTipo().getHiniT1());
                                                genPrgJorada.setTimeDestiny(gtj.getIdGenericaJornadaTipo().getHfinT1());
                                                genPrgJorada.setUsername(user.getUsername());
                                                genPrgJorada.setCreado(MovilidadUtil.fechaCompletaHoy());
                                                genPrgJorada.setModificado(MovilidadUtil.fechaCompletaHoy());
                                                genPrgJorada.setEstadoReg(0);
                                                genPrgJorada.setFecha(d);
                                                listGenPrgJoradaFeriado.add(genPrgJorada);
                                            }
                                            listEmpleadoFeriado.subList(0, gtjd.getCantidadFeriada()).clear();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        listGenPrgJorada.addAll(listGenPrgJoradaFeriado);
        listGenPrgJorada.addAll(listGenPrgJoradaHabil);
    }

    public void simular1() {
        try {
            List<GenericaPrgJornada> listGenPrgJoradaHabil = new ArrayList<>();
            List<GenericaPrgJornada> listGenPrgJoradaFeriado = new ArrayList<>();
            GenericaPrgJornada genPrgJorada;
            List<GenericaTurnoJornada> listGenTurno = genTurnoJorEJB.findEstadoReg(pau.getIdParamArea().getIdParamArea(), 0);
            HashMap<Integer, List<Empleado>> mapEmpleadosHabil = new HashMap<>();
            HashMap<Integer, List<Empleado>> mapEmpleadosFeriado = new HashMap<>();
//            for (GenericaTurnoJornada gtj : listGenTurno) {
//                List<GenericaTurnoJornadaDet> listGenTurJorDet = gtj.getGenericaTurnoJornadaDetList();
//                for (GenericaTurnoJornadaDet gtjd : listGenTurJorDet) {
//                    int id = gtjd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo();
//                    List<Empleado> empledosByIdCargoActivos = empleadoEJB.getEmpledosByIdCargoActivos(id);
//                    mapEmpleadosHabil.put(id, empledosByIdCargoActivos);
//                    mapEmpleadosFeriado.put(id, new ArrayList<>(empledosByIdCargoActivos));
//                }
//            }
            HashMap<Integer, GenericaJornadaTipo> mapEmpleadosJornadaHabil = new HashMap<>();
            HashMap<Integer, GenericaJornadaTipo> mapEmpleadosJornadaFeriado = new HashMap<>();
            HashMap<Integer, List<Date>> mapDiaPorSemanas = Util.getMapDiaPorSemanas(fechaDesde);
            Date mes = fechaDesde;
            List<ParamFeriado> listDiasFeriados = paramFeriadoEJB.findAllByFechaMes(Util.getPrimerDiaMes(mes), Util.getUltimoDiaMes(mes));
            HashMap<String, ParamFeriado> mapDiaFeriados = new HashMap<>();
            for (ParamFeriado pf : listDiasFeriados) {
                mapDiaFeriados.put(Util.dateFormat(pf.getFecha()), pf);
            }
            int z = 0;
            int x = 0;
            for (int y = 0; y < mapDiaPorSemanas.size(); y++) {

                for (Date d : mapDiaPorSemanas.get(y + 1)) {
                    for (GenericaTurnoJornada gtj : listGenTurno) {
                        List<GenericaTurnoJornadaDet> listGenTurJorDet = gtj.getGenericaTurnoJornadaDetList();
                        for (GenericaTurnoJornadaDet gtjd : listGenTurJorDet) {
                            int id = gtjd.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo();
                            List<Empleado> empledosByIdCargoActivos = empleadoEJB.getEmpledosByIdCargoActivos(id, x);
                            mapEmpleadosHabil.put(id, empledosByIdCargoActivos);
                            mapEmpleadosFeriado.put(id, new ArrayList<>(empledosByIdCargoActivos));
                            if (y > z) {
                                if (x == 0) {
                                    x = 1;
                                } else {
                                    x = 0;
                                }
                                z++;
                            }
                        }
                    }
                    for (GenericaTurnoJornada gtj : listGenTurno) {
                        List<GenericaTurnoJornadaDet> listGenTurJorDet = gtj.getGenericaTurnoJornadaDetList();
                        if (listGenTurJorDet != null) {
                            EmpleadoTipoCargo empTipoCargo;
                            for (GenericaTurnoJornadaDet gtjd : listGenTurJorDet) {
                                empTipoCargo = gtjd.getIdEmpleadoTipoCargo();
                                List<Empleado> listEmpleadoHabil, listEmpleadoFeriado;
                                if (empTipoCargo != null) {
                                    listEmpleadoHabil = mapEmpleadosHabil.get(empTipoCargo.getIdEmpleadoTipoCargo());
                                    listEmpleadoFeriado = mapEmpleadosFeriado.get(empTipoCargo.getIdEmpleadoTipoCargo());
                                    if (y > z) {
                                        Collections.shuffle(listEmpleadoHabil);
                                        z++;
                                    }
                                    if (listEmpleadoHabil != null) {
                                        if (gtjd.getCantidadHabil() != null && gtjd.getCantidadHabil() > 0 && !MovilidadUtil.isSunday(d) && !mapDiaFeriados.containsKey(Util.dateFormat(d))) {
//                                            Collections.shuffle(listEmpleadoHabil);
                                            for (int i = 0; i < gtjd.getCantidadHabil(); i++) {
                                                if (y > z) {
                                                    Collections.shuffle(listEmpleadoHabil);
                                                    z++;
                                                }
                                                genPrgJorada = new GenericaPrgJornada();
                                                genPrgJorada.setIdEmpleado(listEmpleadoHabil.get(i));
                                                genPrgJorada.setIdGenericaJornadaTipo(gtj.getIdGenericaJornadaTipo());
                                                genPrgJorada.setIdParamArea(pau.getIdParamArea());
                                                genPrgJorada.setTimeOrigin(gtj.getIdGenericaJornadaTipo().getHiniT1());
                                                genPrgJorada.setTimeDestiny(gtj.getIdGenericaJornadaTipo().getHfinT1());
                                                genPrgJorada.setUsername(user.getUsername());
                                                genPrgJorada.setCreado(MovilidadUtil.fechaCompletaHoy());
                                                genPrgJorada.setModificado(MovilidadUtil.fechaCompletaHoy());
                                                genPrgJorada.setFecha(d);
                                                genPrgJorada.setEstadoReg(0);
                                                listGenPrgJoradaHabil.add(genPrgJorada);
                                                mapEmpleadosJornadaHabil.put(listEmpleadoHabil.get(i).getIdEmpleado(), gtj.getIdGenericaJornadaTipo());
                                            }
                                            listEmpleadoHabil.subList(0, gtjd.getCantidadHabil()).clear();
                                        }
                                    }
                                    if (listEmpleadoFeriado != null) {
                                        if (gtjd.getCantidadFeriada() != null && gtjd.getCantidadFeriada() > 0 && (MovilidadUtil.isSunday(d) || mapDiaFeriados.containsKey(Util.dateFormat(d)))) {
//                                            Collections.shuffle(listEmpleadoFeriado);
                                            for (int i = 0; i < gtjd.getCantidadFeriada(); i++) {
                                                genPrgJorada = new GenericaPrgJornada();
                                                genPrgJorada.setIdEmpleado(listEmpleadoFeriado.get(i));
                                                genPrgJorada.setIdGenericaJornadaTipo(gtj.getIdGenericaJornadaTipo());
                                                genPrgJorada.setIdParamArea(pau.getIdParamArea());
                                                genPrgJorada.setTimeOrigin(gtj.getIdGenericaJornadaTipo().getHiniT1());
                                                genPrgJorada.setTimeDestiny(gtj.getIdGenericaJornadaTipo().getHfinT1());
                                                genPrgJorada.setUsername(user.getUsername());
                                                genPrgJorada.setCreado(MovilidadUtil.fechaCompletaHoy());
                                                genPrgJorada.setModificado(MovilidadUtil.fechaCompletaHoy());
                                                genPrgJorada.setEstadoReg(0);
                                                genPrgJorada.setFecha(d);
                                                listGenPrgJoradaFeriado.add(genPrgJorada);
                                                mapEmpleadosJornadaFeriado.put(listEmpleadoFeriado.get(i).getIdEmpleado(), gtj.getIdGenericaJornadaTipo());
                                            }
                                            listEmpleadoFeriado.subList(0, gtjd.getCantidadFeriada()).clear();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            listGenPrgJorada.addAll(listGenPrgJoradaHabil);
            listGenPrgJorada.addAll(listGenPrgJoradaFeriado);
//            Date mes = fechaDesde;
//            List<ParamFeriado> listDiasFeriados = paramFeriadoEJB.findAllByFechaMes(Util.getPrimerDiaMes(mes), Util.getUltimoDiaMes(mes));
//            HashMap<String, ParamFeriado> mapDiaFeriados = new HashMap<>();
//            for (ParamFeriado pf : listDiasFeriados) {
//                mapDiaFeriados.put(Util.dateFormat(pf.getFecha()), pf);
//            }
//            HashMap<Integer, List<Date>> mapDiaPorSemanas = Util.getMapDiaPorSemanas(fechaDesde);
//            for (int y = 0; y < mapDiaPorSemanas.size(); y++) {
//                for (GenericaPrgJornada gpj : listGenPrgJoradaHabil) {
//                    for (Date d : mapDiaPorSemanas.get(y + 1)) {
//                        if (!MovilidadUtil.isSunday(d) && !mapDiaFeriados.containsKey(Util.dateFormat(d))) {
//                            listGenPrgJorada.add(setGenericaPrgJornada(gpj, d));
//                        }
//                    }
//                }
//
//            }
//            for (int y = 0; y < mapDiaPorSemanas.size(); y++) {
//                for (GenericaPrgJornada gpj : listGenPrgJoradaFeriado) {
//                    for (Date d : mapDiaPorSemanas.get(y + 1)) {
//                        if (MovilidadUtil.isSunday(d) || mapDiaFeriados.containsKey(Util.dateFormat(d))) {
//                            listGenPrgJorada.add(setGenericaPrgJornada(gpj, d));
//                        }
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    GenericaPrgJornada setGenericaPrgJornada(GenericaPrgJornada gpj, Date d
    ) {
        GenericaPrgJornada genPrgJor = new GenericaPrgJornada();
        genPrgJor.setIdEmpleado(gpj.getIdEmpleado());
        genPrgJor.setIdGenericaJornadaTipo(gpj.getIdGenericaJornadaTipo());
        genPrgJor.setIdParamArea(gpj.getIdParamArea());
        genPrgJor.setFecha(d);
        genPrgJor.setTimeOrigin(gpj.getIdGenericaJornadaTipo().getHiniT1());
        genPrgJor.setTimeDestiny(gpj.getIdGenericaJornadaTipo().getHfinT1());
        genPrgJor.setUsername(gpj.getUsername());
        genPrgJor.setCreado(gpj.getCreado());
        genPrgJor.setModificado(gpj.getModificado());
        genPrgJor.setEstadoReg(0);
        return genPrgJor;
    }

    String getLlaveFechaIdenticacion(GenericaPrgJornada gpj
    ) {
        return Util.dateFormat(gpj.getFecha()) + "-" + gpj.getIdEmpleado().getIdentificacion();
    }

    public TimelineModel getModel() {
        return model;
    }

    public Date getStart() {
        return fechaDesde;
    }

    public Date getEnd() {
        return fechaHasta;
    }

    public void onEdit(org.primefaces.event.timeline.TimelineModificationEvent event) {
        Timeline source = (Timeline) event.getSource();
        TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(source.getClientId());
        TimelineEvent timelineEvent = event.getTimelineEvent();
        int index = model.getIndex(timelineEvent);
        timelineEvent.setData("Changed");
        timelineUpdater.update(timelineEvent, index);
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<GenericaJornada> getListGenericaJornada() {
        return listGenericaJornada;
    }

    public void setListGenericaJornada(List<GenericaJornada> listGenericaJornada) {
        this.listGenericaJornada = listGenericaJornada;
    }

}
