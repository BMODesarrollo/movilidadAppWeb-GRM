/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.aja.jornada.controller.GenericaJornadaFlexible;
import com.aja.jornada.controller.calculator;
import com.aja.jornada.model.GenericaJornadaLiqUtil;
import com.aja.jornada.util.JornadaUtil;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.GenericaJornadaMotivoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTipoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTokenFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaMotivo;
import com.movilidad.model.GenericaJornadaParam;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.GenericaJornadaToken;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.ParamFeriado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.TokenGeneratorUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import static com.aja.jornada.util.Util.getDiaSemana;
import com.movilidad.utils.UtilJornada;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author solucionesit
 */
@Named(value = "calcularMasivoBean")
@ViewScoped
public class CalcularMasivoJSFManagedBean implements Serializable {

    @EJB
    private GenericaJornadaFacadeLocal genJornadaEJB;
    @EJB
    private GenericaJornadaTipoFacadeLocal jornadaTEJB;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private GenericaJornadaMotivoFacadeLocal jornadaMotivoEJB;

    @EJB
    private ParamFeriadoFacadeLocal paraFeEJB;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @EJB
    private GenericaJornadaTokenFacadeLocal genJornadaTokenEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
   
    private GenericaJornadaParam genJornadaParam;
    private ParamAreaUsr pau;
    private boolean b_jornada_flexible = false;

    private Map<String, GenericaJornadaTipo> mapJornadaTipo;
    private Map<String, ParamFeriado> mapParamFeriado;
    String rol_user = "";
    String totalHorasAsignadas = calculator.hr_cero;
    String horasJornada;
    String horasExtrasDia;
    String horasMitadJornada;

    Date fechaDesde = MovilidadUtil.fechaHoy();
    Date fechaHasta = MovilidadUtil.fechaHoy();

    private List<GenericaJornada> genericaJornadaList;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private GenericaJornadaFlexible flexible;

    public GenericaJornadaFlexible getJornadaFlexible() {
        if (flexible == null) {
            flexible = new GenericaJornadaFlexible();
        }
        return flexible;
    }

    @PostConstruct
    public void init() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            rol_user = auth.getAuthority();
        }
        //Objeto param area user 

    }

    public boolean jornadaFlexible() {
        return pau == null ? false : pau.getIdParamArea() == null
                ? false : pau.getIdParamArea().getJornadaFlexible() == null ? false
                : pau.getIdParamArea().getJornadaFlexible().equals(1);
    }

    /**
     * Creates a new instance of CalcularMasivoJSFManagedBean
     */
    public CalcularMasivoJSFManagedBean() {
        horasJornada = UtilJornada.getTotalHrsLaborales();
        horasExtrasDia = UtilJornada.getMaxHrsExtrasDia();
        horasMitadJornada = "04:00:00";//si se desea ser exacto este valor debería ser la mitad del valor parametrizado como tiempo jornada, es decir, horasJornada
    }

    /**
     * Responsable de cargar la data de jornadas para ser presentada en la vista
     * principal del modulo.
     *
     * La variables fechaDesde y fechaHasta, son seleccionadas por el usurio
     * desde la vista principla.
     *
     * Limpia los filtros del DataTable de la vista principal.
     *
     */
    public void cargarDatos() {
        calculator.reset();
        if (pau != null) {
            this.genericaJornadaList = genJornadaEJB.getByDate(fechaDesde, fechaHasta, pau.getIdParamArea().getIdParamArea());

        }
//        PrimeFaces.current().executeScript("PF('dtserconlist').clearFilters()");
    }

    /**
     * Valida que la horas sean todas positivas
     *
     * @param gj
     * @return true si las horas son negativas, flase si no.
     */
    public boolean validarHorasPositivas(GenericaJornada gj) {
        if (MovilidadUtil.toSecs(gj.getDiurnas()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getNocturnas()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getExtraDiurna()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getExtraNocturna()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getFestivoDiurno()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getFestivoNocturno()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) < 0) {
            return true;
        }
        if (MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) < 0) {
            return true;
        }
        return false;
    }

    public void cargarMapaTipoJornada() {
        mapJornadaTipo = new HashMap<>();
        List<GenericaJornadaTipo> findAllByArea = jornadaTEJB.findAllByArea(
                pau.getIdParamArea().getIdParamArea());
        findAllByArea.forEach((obj) -> {
            mapJornadaTipo.put(obj.getHiniT1() + "_" + obj.getHfinT1(), obj);
        });
    }

    /**
     * Método responsable de calcular jornadas por rango de fechas al invocar el
     * método cargarCalcularDato.
     *
     * Valida que las jornadas no esten liquidadas.
     *
     * @throws java.text.ParseException
     */
    public void cargarCalcularDatos() throws ParseException {
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(fechaDesde, 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        UtilJornada.cargarParametrosJar();
        genericaJornadaList = new ArrayList<>();
        b_jornada_flexible = false;
        if (pau != null) {
            genericaJornadaList = genJornadaEJB.getByDate(fechaDesde, fechaHasta, pau.getIdParamArea().getIdParamArea());
            b_jornada_flexible = jornadaFlexible();
        }
        if (b_jornada_flexible) {//flexible
            Map<String, List<GenericaJornada>> mapJornadasSemanas
                    = cargarMapSemanalDeJornadas(genericaJornadaList);
            for (Map.Entry<String, List<GenericaJornada>> entry : mapJornadasSemanas.entrySet()) {
                System.out.println("key SEMANA---------->" + entry.getKey());
                System.out.println("size SEMANA---------->" + entry.getValue().size());
                String[] listaFecha = entry.getKey().split("_");
                Date desde = Util.toDate(listaFecha[0]);
                Date hasta = Util.toDate(listaFecha[1]);
                List<GenericaJornadaLiqUtil> calculoOrdinarioJornadas = getJornadaFlexible().calculoOrdinarioJornadas(desde, hasta, 1, pau.getIdParamArea().getIdParamArea());
                System.out.println("calculoOrdinarioJornadas---------->" + calculoOrdinarioJornadas.size());
                genJornadaEJB.updatePrgSerconFromList(calculoOrdinarioJornadas, 1);
                List<GenericaJornadaLiqUtil> calculoJornadaFlexible = getJornadaFlexible().calculoJornadaFlexible(desde, hasta, 1, pau.getIdParamArea().getIdParamArea());
                System.out.println("calculoJornadaFlexible------------>" + calculoJornadaFlexible.size());
                genJornadaEJB.updatePrgSerconFromList(calculoJornadaFlexible, 1);
                List<GenericaJornadaLiqUtil> distribuirDomicalSinCompensatorio = getJornadaFlexible().distribuirDomicalSinCompensatorio(desde, hasta, pau.getIdParamArea().getIdParamArea());
                System.out.println("distribuirDomicalSinCompensatorio->" + distribuirDomicalSinCompensatorio.size());
                genJornadaEJB.updatePrgSerconFromList(distribuirDomicalSinCompensatorio, 1);

            }
        } else {
            cargarMapaTipoJornada();
            cargarMapParamFeriado();
            for (GenericaJornada gj : genericaJornadaList) {
                if (gj.getLiquidado() == null || (gj.getLiquidado() != null && gj.getLiquidado() == 0)
                        && gj.getNominaBorrada() == 0 && (gj.getAutorizado() == null || (gj.getAutorizado() != null && gj.getAutorizado() == 1))) {
                    String timeOrigin;
                    String timeDestiny;
                    boolean realTime = isRealTime(gj.getAutorizado(),
                            gj.getPrgModificada(), gj.getRealTimeOrigin());
                    if (realTime) {
                        timeOrigin = gj.getRealTimeOrigin();
                        timeDestiny = gj.getRealTimeDestiny();
                    } else {
                        timeOrigin = gj.getTimeOrigin();
                        timeDestiny = gj.getTimeDestiny();
                    }

                    boolean b_descanso = false;
                    GenericaJornadaTipo gjt = mapJornadaTipo.get(timeOrigin + "_" + timeDestiny);
                    gj.setIdGenericaJornadaTipo(gjt);
                    if (gjt != null) {
                        if (MovilidadUtil.toSecs(gjt.getDescanso()) > MovilidadUtil.toSecs(calculator.hr_cero)) {
                            b_descanso = true;
                        }
                    }
                    boolean b_jornadaExtra = false;
                    if ((MovilidadUtil.toSecs(timeDestiny)
                            - MovilidadUtil.toSecs(timeOrigin))
                            > MovilidadUtil.toSecs(horasJornada)) {

                        if (gj.getIdGenericaJornadaTipo() != null) {
                            if (((MovilidadUtil.toSecs(timeDestiny)
                                    - MovilidadUtil.toSecs(timeOrigin)
                                    - MovilidadUtil.toSecs(horasJornada)
                                    - MovilidadUtil.toSecs(gj.getIdGenericaJornadaTipo().getDescanso()))
                                    <= MovilidadUtil.toSecs(horasExtrasDia))) {
                                b_jornadaExtra = true;
                            }
                        } else {
                            if (((MovilidadUtil.toSecs(timeDestiny)
                                    - MovilidadUtil.toSecs(timeOrigin)
                                    - MovilidadUtil.toSecs(horasJornada)) <= MovilidadUtil.toSecs(horasExtrasDia))) {
                                b_jornadaExtra = true;
                            }
                        }
                    }
                    boolean flagTipoCalculo = gj.getIdGenericaJornadaTipo() != null && gj.getIdGenericaJornadaTipo().getTipoCalculo() == 1;

                    if (flagTipoCalculo) {
                        cargarCalcularDatoPorPartes(gj);
                    } else {
                        cargarCalcularDato(gj, 3);
                    }
                    if (validarHorasPositivas(gj)) {
                        MovilidadUtil.addErrorMessage("Error al calcular jornada"
                                + gj.getIdEmpleado().getIdentificacion()
                                + " - " + gj.getIdEmpleado().getNombres()
                                + " " + gj.getIdEmpleado().getApellidos());
                    } else {
                        if (!flagTipoCalculo && (b_jornadaExtra && b_descanso)) {
                            int festivaExtraNocturna = MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
                            int festivaExtraDiurna = MovilidadUtil.toSecs(gj.getFestivoExtraDiurno());
                            int extraNocturna = MovilidadUtil.toSecs(gj.getExtraNocturna());
                            int extraDiurna = MovilidadUtil.toSecs(gj.getExtraDiurna());
                            int descanso_ = MovilidadUtil.toSecs(gj.getIdGenericaJornadaTipo().getDescanso());

                            if (festivaExtraNocturna > 0) {
                                if (festivaExtraNocturna > descanso_) {
                                    festivaExtraNocturna = festivaExtraNocturna - descanso_;
                                    descanso_ = 0;
                                } else if (descanso_ > festivaExtraNocturna) {
                                    descanso_ = descanso_ - festivaExtraNocturna;
                                    festivaExtraNocturna = 0;
                                } else if (festivaExtraNocturna == descanso_) {
                                    festivaExtraNocturna = festivaExtraNocturna - descanso_;
                                    descanso_ = 0;
                                }
                            }
                            if (descanso_ > 0) {
                                if (festivaExtraDiurna > 0) {
                                    if (festivaExtraDiurna > descanso_) {
                                        festivaExtraDiurna = festivaExtraDiurna - descanso_;
                                        descanso_ = 0;
                                    } else if (descanso_ > festivaExtraDiurna) {
                                        descanso_ = descanso_ - festivaExtraDiurna;
                                        festivaExtraDiurna = 0;
                                    } else if (festivaExtraDiurna == descanso_) {
                                        festivaExtraDiurna = festivaExtraDiurna - descanso_;
                                        descanso_ = 0;
                                    }
                                }
                            }
                            if (descanso_ > 0) {
                                if (extraNocturna > 0) {
                                    if (extraNocturna > descanso_) {
                                        extraNocturna = extraNocturna - descanso_;
                                        descanso_ = 0;
                                    } else if (descanso_ > extraNocturna) {
                                        descanso_ = descanso_ - extraNocturna;
                                        extraNocturna = 0;
                                    } else if (extraNocturna == descanso_) {
                                        extraNocturna = extraNocturna - descanso_;
                                        descanso_ = 0;
                                    }
                                }
                            }
                            if (descanso_ > 0) {
                                if (extraDiurna > 0) {
                                    if (extraDiurna > descanso_) {
                                        extraDiurna = extraDiurna - descanso_;
                                        descanso_ = 0;
                                    } else if (descanso_ > extraDiurna) {
                                        descanso_ = descanso_ - extraDiurna;
                                        extraDiurna = 0;
                                    } else if (extraDiurna == descanso_) {
                                        extraDiurna = extraDiurna - descanso_;
                                        descanso_ = 0;
                                    }
                                }
                            }
                            gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(festivaExtraNocturna));
                            gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(festivaExtraDiurna));
                            gj.setExtraNocturna(MovilidadUtil.toTimeSec(extraNocturna));
                            gj.setExtraDiurna(MovilidadUtil.toTimeSec(extraDiurna));
                        }
//                psPersistir.setRealTimeDestiny(null);
//                psPersistir.setRealTimeOrigin(null);
//                psPersistir.setAutorizado(1);
//                psPersistir.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
//                psPersistir.setUserAutorizado(user.getUsername());
                        if (!rol_user.equals("ROLE_DIRGEN")) {
                            if (aprobarHorasFeriadas(gj)) {
                                int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(gj.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
                                if (genJornadaParam != null
                                        && genJornadaParam.getCtrlAprobarExtrasFeriadas() == 0
                                        && validacionDia == 0) {
                                    gj.setAutorizado(-1);
                                    gj.setFechaAutoriza(null);
                                    gj.setUserAutorizado("");
                                    if (genJornadaParam.getNotifica() == 1) {
                                        if (genJornadaParam.getEmails() != null) {
                                            notificar(gj);
                                        }
                                    }
                                }
                            }
                        }
                        gj.setDominicalCompDiurnas(null);
                        gj.setDominicalCompNocturnas(null);
                        gj.setDominicalCompDiurnaExtra(null);
                        gj.setDominicalCompNocturnaExtra(null);
//                    System.out.println("Tini->" + gj.getTimeOrigin());
//                    System.out.println("Tfin->" + gj.getTimeDestiny());
                        genJornadaEJB.edit(gj);
                        recalcularJornada(gj);
                    }
                }
            }
        }
        cargarDatos();

        MovilidadUtil.addSuccessMessage("Acción completada exitosamente");

    }

    public GenericaJornada cargarCalcularDatoPorPartes(GenericaJornada gj) {
        GenericaJornada onePart = null;
        GenericaJornada twoPart = null;

        //Calcular el total de horas ejecutadas.
        int totalHoras = MovilidadUtil.toSecs(gj.getTimeDestiny())
                - MovilidadUtil.toSecs(gj.getTimeOrigin())
                - MovilidadUtil.toSecs(gj.getIdGenericaJornadaTipo().getDescanso());
        /**
         * Calcular medio de la jornada de horas ejecutadas, para saber a que
         * hora termina el turno 1 y a que hora comienza el turno 2.
         */
        int mitadHoras = totalHoras / 2;
        String timeOrigin1 = gj.getTimeOrigin();
        String timeDestiny1;
        if (totalHoras > MovilidadUtil.toSecs(horasMitadJornada)) {
            timeDestiny1 = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(timeOrigin1) + MovilidadUtil.toSecs(horasMitadJornada));
        } else {
            timeDestiny1 = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(timeOrigin1) + mitadHoras);
        }
        String timeOrigin2 = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(timeDestiny1)
                + MovilidadUtil.toSecs(gj.getIdGenericaJornadaTipo().getDescanso()));
        String timeDestiny2 = gj.getTimeDestiny();
        if (gj.getAutorizado() != null && gj.getAutorizado() == 1) {

            //Calcular el total de horas ejecutadas.
            totalHoras = MovilidadUtil.toSecs(gj.getRealTimeDestiny())
                    - MovilidadUtil.toSecs(gj.getRealTimeOrigin())
                    - MovilidadUtil.toSecs(gj.getIdGenericaJornadaTipo().getDescanso());
            /**
             * Calcular medio de la jornada de horas ejecutadas, para saber a
             * que hora termina el turno 1 y a que hora comienza el turno 2.
             */
            mitadHoras = totalHoras / 2;
            timeOrigin1 = gj.getRealTimeOrigin();

            if (totalHoras > MovilidadUtil.toSecs(horasMitadJornada)) {
                timeDestiny1 = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(timeOrigin1)
                        + MovilidadUtil.toSecs(horasMitadJornada));
            } else {
                timeDestiny1 = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(timeOrigin1) + mitadHoras);
            }
            timeOrigin2 = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(timeDestiny1)
                    + MovilidadUtil.toSecs(gj.getIdGenericaJornadaTipo().getDescanso()));
            timeDestiny2 = gj.getRealTimeDestiny();

        }
//        System.out.println("Parte 1-->" + timeOrigin1 + " - " + timeDestiny1);
//        System.out.println("Parte 3-->" + timeOrigin2 + " - " + timeDestiny2);
        onePart = caluleOnePart(timeOrigin1, timeDestiny1, gj.getFecha());
        twoPart = caluleOnePart(timeOrigin2, timeDestiny2, gj.getFecha());

        totalHorasAsignadas = calculator.hr_cero;
        gj.setDiurnas(calculator.hr_cero);
        gj.setNocturnas(calculator.hr_cero);
        gj.setExtraDiurna(calculator.hr_cero);
        gj.setExtraNocturna(calculator.hr_cero);
        gj.setFestivoDiurno(calculator.hr_cero);
        gj.setFestivoNocturno(calculator.hr_cero);
        gj.setFestivoExtraDiurno(calculator.hr_cero);
        gj.setFestivoExtraNocturno(calculator.hr_cero);
        sumarPorPartes(gj, onePart, false);
        sumarPorPartes(gj, twoPart, MovilidadUtil.toSecs(timeOrigin2) > MovilidadUtil.toSecs(calculator.fin_dia));

        gj.setTipoCalculo(3);
        return gj;
    }

    public GenericaJornada sumarPorPartes(GenericaJornada gj, GenericaJornada gjModificado, boolean diaPaOtro) {
        int parteHorasLaborales;
        int parteHorasExtra;
        int totalAsignar;
        int maxHorasLaborales = MovilidadUtil.toSecs(UtilJornada.getTotalHrsLaborales());
        if (diaPaOtro) {
            totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getNocturnas());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                gj.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraNocturna()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                    gj.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getNocturnas()) + parteHorasLaborales));
                    totalHorasAsignadas = horasJornada;
                }
            } else {
                totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getNocturnas()));
                gj.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getNocturnas()) + MovilidadUtil.toSecs(gjModificado.getNocturnas())));
            }
            totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas)
                    + MovilidadUtil.toSecs(gjModificado.getDiurnas());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                gj.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraDiurna()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                    gj.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDiurnas()) + parteHorasLaborales));
                    totalHorasAsignadas = UtilJornada.getTotalHrsLaborales();
                }
            } else {
                totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getDiurnas()));
                gj.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDiurnas()) + MovilidadUtil.toSecs(gjModificado.getDiurnas())));
            }
            totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                    gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) + parteHorasLaborales));
                    totalHorasAsignadas = horasJornada;
                }
            } else {
                totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno()));
                gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno())));
            }

            totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                    gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) + parteHorasLaborales));
                    totalHorasAsignadas = horasJornada;
                }
            } else {
                totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno()));
                gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno())));
            }

            gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(gjModificado.getFestivoExtraNocturno())));
            gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(gjModificado.getFestivoExtraDiurno())));

            gj.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraNocturna()) + MovilidadUtil.toSecs(gjModificado.getExtraNocturna())));
            gj.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraDiurna()) + MovilidadUtil.toSecs(gjModificado.getExtraDiurna())));

        } else {
            totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas)
                    + MovilidadUtil.toSecs(gjModificado.getDiurnas());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                gj.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraDiurna()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                    gj.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDiurnas()) + parteHorasLaborales));
                    totalHorasAsignadas = UtilJornada.getTotalHrsLaborales();
                }
            } else {
                totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getDiurnas()));
                gj.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDiurnas()) + MovilidadUtil.toSecs(gjModificado.getDiurnas())));
            }
            totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getNocturnas());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                gj.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraNocturna()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                    gj.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getNocturnas()) + parteHorasLaborales));
                    totalHorasAsignadas = horasJornada;
                }
            } else {
                totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getNocturnas()));
                gj.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getNocturnas()) + MovilidadUtil.toSecs(gjModificado.getNocturnas())));
            }

            totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                    gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) + parteHorasLaborales));
                    totalHorasAsignadas = horasJornada;
                }
            } else {
                totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno()));
                gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno())));
            }

            totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar
                        - maxHorasLaborales;
                gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                    gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) + parteHorasLaborales));
                    totalHorasAsignadas = horasJornada;
                }
            } else {
                totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno()));
                gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno())));
            }

            gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(gjModificado.getFestivoExtraDiurno())));

            gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(gjModificado.getFestivoExtraNocturno())));

            gj.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraDiurna()) + MovilidadUtil.toSecs(gjModificado.getExtraDiurna())));

            gj.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraNocturna()) + MovilidadUtil.toSecs(gjModificado.getExtraNocturna())));
        }
        return gj;
    }

    public void cargarMapParamFeriado() {
        mapParamFeriado = new HashMap<>();
        List<ParamFeriado> listaParamFeriado = paraFeEJB.findAllByFechaMes(
                fechaDesde, MovilidadUtil.sumarDias(fechaHasta, 1));
        listaParamFeriado.forEach((obj) -> {
            mapParamFeriado.put(Util.dateFormat(obj.getFecha()), obj);
        });
    }

    public GenericaJornada caluleOnePart(String s_horaInicio, String s_horaFin, Date fechaParam) {
        calculator.reset();
        GenericaJornada parteJornada = new GenericaJornada();
        int ultimaHoraDia = MovilidadUtil.toSecs("24:00:00");
        int i_horaIniSec;
        int i_horaFinSec;

        i_horaIniSec = MovilidadUtil.toSecs(s_horaInicio);
        i_horaFinSec = MovilidadUtil.toSecs(s_horaFin);

        if (i_horaIniSec <= 0 && i_horaFinSec <= 0) {
            parteJornada.setDiurnas(liquidadorjornada.Jornada.getDiurnas());
            parteJornada.setNocturnas(liquidadorjornada.Jornada.getNocturnas());
            parteJornada.setExtraDiurna(liquidadorjornada.Jornada.getExtra_diurna());
            parteJornada.setExtraNocturna(liquidadorjornada.Jornada.getExtra_nocturna());
            parteJornada.setFestivoDiurno(liquidadorjornada.Jornada.getFestivo_diurno());
            parteJornada.setFestivoNocturno(liquidadorjornada.Jornada.getFestivo_nocturno());
            parteJornada.setFestivoExtraDiurno(liquidadorjornada.Jornada.getFestivo_extra_diurno());
            parteJornada.setFestivoExtraNocturno(liquidadorjornada.Jornada.getFestivo_extra_nocturno());
            return parteJornada;
        }

        if (i_horaIniSec > ultimaHoraDia) {
            Date fecha = MovilidadUtil.sumarDias(fechaParam, 1);
            ParamFeriado pf = mapParamFeriado.get(Util.dateFormat(fecha));
            if (pf == null && !MovilidadUtil.isSunday(fecha)) {
                JornadaUtil.calcular("H",
                        MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(s_horaInicio) - ultimaHoraDia),
                        "H",
                        MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(s_horaFin) - ultimaHoraDia), "");
            } else {
                JornadaUtil.calcular("F",
                        MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(s_horaInicio) - ultimaHoraDia),
                        "F",
                        MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(s_horaFin) - ultimaHoraDia), "");
            }
        } else {

            ParamFeriado pffHI = mapParamFeriado.get(Util.dateFormat(fechaParam));

            ParamFeriado pffHF = pffHI;
            Date fecha = fechaParam;
            if (i_horaFinSec > ultimaHoraDia) {
                fecha = MovilidadUtil.sumarDias(fechaParam, 1);
                pffHF = mapParamFeriado.get(Util.dateFormat(fecha));
            }
            if ((pffHI == null && !MovilidadUtil.isSunday(fechaParam)) && (pffHF == null && !MovilidadUtil.isSunday(fecha))) {
                JornadaUtil.calcular("H", s_horaInicio, "H", s_horaFin, "");
//                System.out.println("H;H->>" + s_horaInicio + " - " + timeDestiny);
            }
            if ((pffHI != null || MovilidadUtil.isSunday(fechaParam)) && (pffHF == null && !MovilidadUtil.isSunday(fecha))) {
                JornadaUtil.calcular("F", s_horaInicio, "H", s_horaFin, "");
//                System.out.println("F;H->>" + s_horaInicio + " - " + timeDestiny);
            }
            if ((pffHI != null || MovilidadUtil.isSunday(fechaParam)) && (pffHF != null || MovilidadUtil.isSunday(fecha))) {
                JornadaUtil.calcular("F", s_horaInicio, "F", s_horaFin, "");
//                System.out.println("F;F->>" + s_horaInicio + " - " + timeDestiny);

            }
            if ((pffHI == null && !MovilidadUtil.isSunday(fechaParam)) && (pffHF != null || MovilidadUtil.isSunday(fecha))) {
                JornadaUtil.calcular("H", s_horaInicio, "F", s_horaFin, "");
//                System.out.println("H;F->>" + s_horaInicio + " - " + timeDestiny);
            }
        }
        parteJornada.setDiurnas(calculator.getDiurnas());
        parteJornada.setNocturnas(calculator.getNocturnas());
        parteJornada.setExtraDiurna(calculator.getExtra_diurna());
        parteJornada.setExtraNocturna(calculator.getExtra_nocturna());
        parteJornada.setFestivoDiurno(calculator.getFestivo_diurno());
        parteJornada.setFestivoNocturno(calculator.getFestivo_nocturno());
        parteJornada.setFestivoExtraDiurno(calculator.getFestivo_extra_diurno());
        parteJornada.setFestivoExtraNocturno(calculator.getFestivo_extra_nocturno());
        return parteJornada;
    }

    /**
     * Método responsable de invocar al método calculaJ para calcular las horas
     * a liquidar(diurnas,nocturnas,extra diurna, extra nocturna, festivo
     * diurno, festivo nocturno, festivo extra diurno, festivo extra nocturno)
     * de una jornada de acuerdo a una hora inicio y hora fin de turno.
     *
     * Persiste la información en BD.
     *
     * Las jornadas o turnos que pasan por este método son almacenadas con el
     * identificador 2 en el atributo 'tipoCalculo', lo cual significa que el
     * tipo de calculo es automatico, hecho por rigel.
     *
     * Invoca al método calculaJ, responsable de calcular la jornada.
     *
     * @param jornada
     * @param opc
     * @param descanso
     * @return
     */
    @Transactional
    public GenericaJornada cargarCalcularDato(GenericaJornada jornada, int opc) {
        calculator.reset();
        int ultimaHoraDia = MovilidadUtil.toSecs("24:00:00");
        int horaIniSec;
        int horaFinSec;
        String timeOrigin;
        String timeDestiny;
        boolean realTime = isRealTime(jornada.getAutorizado(),
                jornada.getPrgModificada(), jornada.getRealTimeOrigin());
        if (realTime) {
            timeOrigin = jornada.getRealTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = jornada.getRealTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        } else {
            timeOrigin = jornada.getTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = jornada.getTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        }

//        if (opc == 0) {
//            ps.setRealTimeOrigin(ps.getTimeOrigin());
//            ps.setRealTimeDestiny(ps.getTimeDestiny());
//        } else 
//        if (opc == 3) {
//            ps.setRealTimeOrigin(ps.getTimeOrigin());
//            ps.setRealTimeDestiny(ps.getTimeDestiny());
//            ps.setAutorizado(1);
//        }
        if (timeOrigin != null && timeDestiny != null) {
            if (timeOrigin.equals(calculator.hr_cero) && timeDestiny.equals(calculator.hr_cero)) {
                jornada.setDiurnas(calculator.getDiurnas());
                jornada.setNocturnas(calculator.getNocturnas());
                jornada.setExtraDiurna(calculator.getExtra_diurna());
                jornada.setExtraNocturna(calculator.getExtra_nocturna());
                jornada.setFestivoDiurno(calculator.getFestivo_diurno());
                jornada.setFestivoNocturno(calculator.getFestivo_nocturno());
                jornada.setFestivoExtraDiurno(calculator.getFestivo_extra_diurno());
                jornada.setFestivoExtraNocturno(calculator.getFestivo_extra_nocturno());
                //Tipo de calculo automatico
                jornada.setTipoCalculo(2);
                if (opc == 1) {
                    genJornadaEJB.edit(jornada);
                } else if (opc == 0) {
//                    ps.setRealTimeOrigin(null);
//                    ps.setRealTimeDestiny(null);
//                    ps.setAutorizado(null);
                }
                return jornada;
            }
        } else {
            return jornada;
        }

//        if (ps.getAutorizado() != null && ps.getAutorizado() != 1) {
//            return ps;
//        }
//        if (timeOrigin != null && timeDestiny != null
//                && (ps.getAutorizado() != null && ps.getAutorizado() == 1)) {
//            timeOrigin = timeOrigin;
//            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
//            timeDestiny = timeDestiny;
//            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
//        } else {
//            return ps;
//        }
        if (horaIniSec > ultimaHoraDia) {
            Date fecha = MovilidadUtil.sumarDias(jornada.getFecha(), 1);
            ParamFeriado pf = mapParamFeriado.get(Util.dateFormat(fecha));
            if (pf == null && !MovilidadUtil.isSunday(fecha)) {
                JornadaUtil.calcular("H", timeOrigin, "H", timeDestiny, "");

            } else {
                JornadaUtil.calcular("F", timeOrigin, "F", timeDestiny, "");
            }
        } else {

            ParamFeriado pffHI = mapParamFeriado.get(Util.dateFormat(jornada.getFecha()));
            ParamFeriado pffHF = pffHI;
            Date fecha = jornada.getFecha();
            if (horaFinSec > ultimaHoraDia) {
                fecha = MovilidadUtil.sumarDias(jornada.getFecha(), 1);
                pffHF = mapParamFeriado.get(Util.dateFormat(fecha));
            }
            if ((pffHI == null && !MovilidadUtil.isSunday(jornada.getFecha())) && (pffHF == null && !MovilidadUtil.isSunday(fecha))) {
                JornadaUtil.calcular("H", timeOrigin, "H", timeDestiny, "");
//                System.out.println("H;H->>" + timeOrigin + " - " + timeDestiny);
            }
            if ((pffHI != null || MovilidadUtil.isSunday(jornada.getFecha())) && (pffHF == null && !MovilidadUtil.isSunday(fecha))) {
                JornadaUtil.calcular("F", timeOrigin, "H", timeDestiny, "");
//                System.out.println("F;H->>" + timeOrigin + " - " + timeDestiny);
            }
            if ((pffHI != null || MovilidadUtil.isSunday(jornada.getFecha())) && (pffHF != null || MovilidadUtil.isSunday(fecha))) {
                JornadaUtil.calcular("F", timeOrigin, "F", timeDestiny, "");
//                System.out.println("F;F->>" + timeOrigin + " - " + timeDestiny);

            }
            if ((pffHI == null && !MovilidadUtil.isSunday(jornada.getFecha())) && (pffHF != null || MovilidadUtil.isSunday(fecha))) {
                JornadaUtil.calcular("H", timeOrigin, "F", timeDestiny, "");
//                System.out.println("H;F->>" + timeOrigin + " - " + timeDestiny);
            }
        }
        jornada.setDiurnas(calculator.getDiurnas());
        jornada.setNocturnas(calculator.getNocturnas());
        jornada.setExtraDiurna(calculator.getExtra_diurna());
        jornada.setExtraNocturna(calculator.getExtra_nocturna());
        jornada.setFestivoDiurno(calculator.getFestivo_diurno());
        jornada.setFestivoNocturno(calculator.getFestivo_nocturno());
        jornada.setFestivoExtraDiurno(calculator.getFestivo_extra_diurno());
        jornada.setFestivoExtraNocturno(calculator.getFestivo_extra_nocturno());
//        system();
        //Tipo de calculo automatico
        jornada.setTipoCalculo(2);
        if (opc == 1) {
            genJornadaEJB.edit(jornada);
//        } else if (opc == 0) {
//            ps.setRealTimeOrigin(null);
//            ps.setRealTimeDestiny(null);
//            ps.setAutorizado(null);
//        } else if (opc == 3) {
//            ps.setRealTimeOrigin(null);
//            ps.setRealTimeDestiny(null);
//            ps.setAutorizado(null);
        }
        return jornada;
    }

    /**
     * Recalcula las jorndas con fechas de Sabado y Domingo identificando si el
     * empleado en cuestión ha descansado o no en al semana.
     *
     * @param gj Objeto GenericaJornada que contiene la jornada del empleado.
     */
    public void recalcularJornada(GenericaJornada gj) {
        // TODO code application logic here
        Date mondayNetxWeekend = null;
        Date fridayNetxWeekend = null;
        Date saturday = null;
        Date sunday = null;
        if (UtilJornada.tipoLiquidacion()) {
            if (MovilidadUtil.isSunday(gj.getFecha())) {
                saturday = getDiaSemana(gj.getFecha(), Calendar.SATURDAY);
                sunday = getDiaSemana(gj.getFecha(), Calendar.SUNDAY);
                mondayNetxWeekend = MovilidadUtil.sumarDias(sunday, 1);
                fridayNetxWeekend = getDiaSemana(mondayNetxWeekend, Calendar.FRIDAY);
            } else {
                mondayNetxWeekend = getDiaSemana(gj.getFecha(), Calendar.MONDAY);
                fridayNetxWeekend = getDiaSemana(gj.getFecha(), Calendar.FRIDAY);
                saturday = MovilidadUtil.sumarDias(mondayNetxWeekend, -2);
                sunday = MovilidadUtil.sumarDias(mondayNetxWeekend, -1);
            }
        } else {
            mondayNetxWeekend = getDiaSemana(gj.getFecha(), Calendar.MONDAY);
            fridayNetxWeekend = getDiaSemana(gj.getFecha(), Calendar.FRIDAY);
            saturday = getDiaSemana(gj.getFecha(), Calendar.SATURDAY);
            sunday = getDiaSemana(gj.getFecha(), Calendar.SUNDAY);
        }

//        System.out.println("mondayNetxWeekend->" + Util.dateFormat(mondayNetxWeekend));
//        System.out.println("fridayNetxWeekend->" + Util.dateFormat(fridayNetxWeekend));
//        System.out.println("saturday->" + Util.dateFormat(saturday));
//        System.out.println("sunday->" + Util.dateFormat(sunday));
//        int descanso = genJornadaEJB.descansoEnSemana(lunes.getTime(), domingo.getTime(), gj.getIdEmpleado().getIdEmpleado(), gj.getIdParamArea().getIdParamArea());
        //Valida si descanso entre lunes y viernes de la siguiente semana
        int descanso = genJornadaEJB.descansoEnSemana(mondayNetxWeekend, fridayNetxWeekend, gj.getIdEmpleado().getIdEmpleado(), gj.getIdParamArea().getIdParamArea());
        List<GenericaJornada> list = genJornadaEJB.getJornadasByDateAndEmpleado(saturday, sunday, gj.getIdEmpleado().getIdEmpleado());
        for (GenericaJornada g : list) {
            int resp_tiene_festivo = tieneFestivo(g);
            GenericaJornada gjPersistir = pasarHoras(g, descanso, resp_tiene_festivo);
            if (gjPersistir != null) {
                System.out.println("pasar Horas: DESCANSO->>" + descanso + " FESTIVO->>" + resp_tiene_festivo);
                System.out.println("fecha->>" + Util.dateFormat(gjPersistir.getFecha()));
                genJornadaEJB.edit(gjPersistir);
            }
        }
    }

    public Calendar getNextMonday(Calendar now) {
//        Calendar now = Calendar.getInstance();
        int weekday = now.get(Calendar.DAY_OF_WEEK);
        if (weekday != Calendar.MONDAY) {
            // calculate how much to add
            // the 2 is the difference between Saturday and Monday
            int days = (Calendar.SATURDAY - weekday + 2) % 7;
            now.add(Calendar.DAY_OF_YEAR, days);
        } else {
            now.add(Calendar.DAY_OF_YEAR, 1);
            weekday = now.get(Calendar.DAY_OF_WEEK);
            // the 2 is the difference between Saturday and Monday
            int days = (Calendar.SATURDAY - weekday + 2) % 7;
            now.add(Calendar.DAY_OF_YEAR, days);
        }
        return now;
    }

    public int tieneFestivo(GenericaJornada ps) {
        int ultimaHoraDia = MovilidadUtil.toSecs("24:00:00");
        int horaIniSec;
        int horaFinSec;
        String timeOrigin;
        String timeDestiny;

        if (ps.getAutorizado() != null && ps.getAutorizado() == 1) {
            if (ps.getRealTimeOrigin() != null) {
                timeOrigin = ps.getRealTimeOrigin();
                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
                timeDestiny = ps.getRealTimeDestiny();
                horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
            } else {
                timeOrigin = ps.getTimeOrigin();
                horaIniSec = MovilidadUtil.toSecs(timeOrigin);
                timeDestiny = ps.getTimeDestiny();
                horaFinSec = MovilidadUtil.toSecs(timeDestiny);
            }
        } else if (ps.getPrgModificada() != null && ps.getPrgModificada() == 1) {
            timeOrigin = ps.getRealTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = ps.getRealTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(ps.getRealTimeDestiny());
        } else {
            timeOrigin = ps.getTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = ps.getTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        }
        ParamFeriado pffHI = paraFeEJB.findByFecha(ps.getFecha());
        ParamFeriado pffHF = pffHI;
        Date fecha = ps.getFecha();
        if (horaFinSec > ultimaHoraDia) {
            fecha = MovilidadUtil.sumarDias(ps.getFecha(), 1);
            pffHF = paraFeEJB.findByFecha(fecha);
        }

//        Si ja jornada conmienza un dia festivo y termina el domingo
        if (pffHI != null && MovilidadUtil.isSunday(fecha)) {
            return 1;
        }
//        Si la jornada comineza un domingo y termina en un festivo
        if ((pffHF != null && MovilidadUtil.isSunday(ps.getFecha()))) {
            return 2;
        }
//        Si la jornada comienza o termina un dia domingo
        if (MovilidadUtil.isSunday(fecha) || MovilidadUtil.isSunday(ps.getFecha())) {
            return 3;
        }
        return 0;
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(1);
        NotificacionTemplate template = notificacionTemplateEjb.find(ConstantsUtil.TEMPLATEGENERICAJORNADA);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    public void notificar(GenericaJornada gj) {
        try {
            Map mapa = getMailParams();
            Map mailProperties = new HashMap();
            Empleado empl = gj.getIdEmpleado();
            GenericaJornadaMotivo motivo = jornadaMotivoEJB.find(gj.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo());
            mailProperties.put("identificacion", empl.getIdentificacion());
            mailProperties.put("nombre", empl.getNombres() + " " + empl.getApellidos());
            mailProperties.put("fecha", Util.dateFormat(gj.getFecha()));
            mailProperties.put("hora_ini_prg", gj.getTimeOrigin());
            mailProperties.put("hora_fin_prg", gj.getTimeDestiny());
            mailProperties.put("hora_ini_real", gj.getRealTimeOrigin());
            mailProperties.put("hora_fin_real", gj.getRealTimeDestiny());
            mailProperties.put("user_name", user.getUsername());
            mailProperties.put("motivo", motivo.getDescripcion());
            mailProperties.put("observacion", gj.getObservaciones());
//            System.out.println("Notificar...");
            mailProperties.put("url", generarTokenUrl(gj));
            String asunto = "MODIFICACIÓN JORNADA";
            String destinatarios = genJornadaParam.getEmails();
            SendMails.sendEmail(mapa, mailProperties, asunto, "", destinatarios, "Notificaciones RIGEL", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean aprobarHorasFeriadas(GenericaJornada gj) {
        int totalHorasExtrasNuevas = MovilidadUtil.toSecs(gj.getFestivoExtraDiurno())
                + MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
        if (totalHorasExtrasNuevas > 0) {
            return true;
        }
        return false;
    }

    public boolean isRealTime(Integer autizado, Integer prgModificada, String realTime) {
        if (autizado != null && autizado == 1) {
            return realTime != null;
        } else {
            return prgModificada != null && prgModificada == 1;
        }
    }

    /**
     * Pasa las horas calculadas(diurnas, nocturnas, extra diurna, extra
     * nocturna, festivo diurno, festivo nocturno, festivo extra diurna, festivo
     * extra nocturno) a los atributos de dominicales sin y con compesatorios
     * nocturno
     *
     * @param gj Objeto GenericaJornada que contiene la jornada del empleado a
     * gestionar.
     * @param descanso Variable int que trabaja con dos valores 0 y 1, el primer
     * valor indica que no hay descanso en la semana y el segundo valor indica
     * que si hay descanso en la semana
     * @param tieneFestivo indica si la jornada a trabajar tiene festivo en su
     * turno.
     * @return
     */
    public GenericaJornada pasarHoras(GenericaJornada gj, int descanso, int tieneFestivo) {

        int ultimaHoraDia = MovilidadUtil.toSecs("24:00:00");
        int horaIniSec;
        int horaFinSec;
        String timeOrigin;
        String timeDestiny;
        boolean realTime = isRealTime(gj.getAutorizado(),
                gj.getPrgModificada(), gj.getRealTimeOrigin());
        if (realTime) {
            timeOrigin = gj.getRealTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = gj.getRealTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        } else {
            timeOrigin = gj.getTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = gj.getTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        }
        if (horaIniSec == 0 && horaFinSec == 0) {
            return null;
        }

        if (descanso != 0) {
            if (gj.getDominicalCompDiurnas() != null
                    || gj.getDominicalCompNocturnas() != null
                    || gj.getDominicalCompDiurnaExtra() != null
                    || gj.getDominicalCompNocturnaExtra() != null) {
                return null;
            }
//            gj.setDominicalCompDiurnaExtra(hr_cero);
//            gj.setDominicalCompNocturnaExtra(hr_cero);
//            gj.setDominicalCompDiurnas(hr_cero);
//            gj.setDominicalCompNocturnas(hr_cero);
            int diurnas_dom = 0;
            int nocturnas_dom = 0;
            int diurnas_dom_extra = 0;
            int nocturnas_dom_extra = 0;

            //La jornada conmienza un festivo y termina en domingo
            if (tieneFestivo == 1) {
//                System.out.println("Caso 1 Y1SUS");

                if (MovilidadUtil.toSecs(timeDestiny) > MovilidadUtil.toSecs("30:00:00")) {
                    diurnas_dom = MovilidadUtil.toSecs(timeDestiny)
                            - MovilidadUtil.toSecs("30:00:00");
                    nocturnas_dom = MovilidadUtil.toSecs(timeDestiny)
                            - ultimaHoraDia - diurnas_dom;

                    if (MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) > 0) {
//                        if (diurnas_dom > MovilidadUtil.toSecs(gj.getFestivoExtraDiurno())) {
//                            diurnas_dom = diurnas_dom - MovilidadUtil.toSecs(gj.getFestivoExtraDiurno());
//                        } else {
//                            diurnas_dom = 0;
//                        }
                        gj.setDominicalCompDiurnaExtra(gj.getFestivoExtraDiurno());
                        gj.setFestivoExtraDiurno(calculator.hr_cero);

                    } else {
                        gj.setDominicalCompDiurnaExtra(calculator.hr_cero);
                    }
                    if (MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) > 0) {
//                        if (nocturnas_dom > MovilidadUtil.toSecs(gj.getFestivoExtraNocturno())) {
//                            nocturnas_dom = nocturnas_dom - MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
//                        } else {
//                            nocturnas_dom = 0;
//                        }
                        if (MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) > MovilidadUtil.toSecs("01:00:00")) { //???
                            int horasDominical = MovilidadUtil.toSecs(timeDestiny) - ultimaHoraDia;
                            if (horasDominical >= MovilidadUtil.toSecs(gj.getFestivoExtraNocturno())) {
                                gj.setDominicalCompNocturnaExtra(gj.getFestivoExtraNocturno());
                                gj.setFestivoExtraNocturno(calculator.hr_cero);
                            } else {
                                gj.setDominicalCompNocturnaExtra(MovilidadUtil.toTimeSec(horasDominical));
                                gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) - horasDominical));
                            }
                        } else {
                            gj.setDominicalCompNocturnaExtra(gj.getFestivoExtraNocturno());
                            gj.setFestivoExtraNocturno(calculator.hr_cero);
                        }

                    } else {
                        gj.setDominicalCompNocturnaExtra(calculator.hr_cero);
                    }
                    gj.setDominicalCompDiurnas(MovilidadUtil.toTimeSec(diurnas_dom));
                    gj.setDominicalCompNocturnas(MovilidadUtil.toTimeSec(nocturnas_dom));

                    gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) - diurnas_dom));
                    gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) - nocturnas_dom));
                } else {
                    nocturnas_dom = MovilidadUtil.toSecs(timeDestiny) - ultimaHoraDia;
                    if (MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) > 0) {
                        if (nocturnas_dom > MovilidadUtil.toSecs(gj.getFestivoExtraNocturno())) {
                            nocturnas_dom = nocturnas_dom - MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
                        } else {
                            nocturnas_dom = 0;
                        }
                        int horasFestivas = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin);
                        if (horasFestivas >= MovilidadUtil.toSecs(horasJornada)) {
//                            String horasExtrasComp = MovilidadUtil.toTimeSec(horasFestivas - MovilidadUtil.toSecs(horasJornada));
                            gj.setDominicalCompNocturnaExtra(gj.getFestivoExtraNocturno());
                            if (MovilidadUtil.toSecs(gj.getDominicalCompNocturnaExtra()) >= MovilidadUtil.toSecs(gj.getFestivoExtraNocturno())) {
                                gj.setFestivoExtraNocturno(calculator.hr_cero);
                            } else {
                                gj.setFestivoExtraNocturno(
                                        MovilidadUtil.toTimeSec(
                                                MovilidadUtil.toSecs(
                                                        gj.getFestivoExtraNocturno()
                                                ) - MovilidadUtil.toSecs(
                                                gj.getDominicalCompNocturnaExtra()
                                        )
                                        )
                                );
                            }

                        } else {
                            gj.setDominicalCompNocturnaExtra(gj.getFestivoExtraNocturno());
                            gj.setDominicalCompDiurnas(calculator.hr_cero);
                            gj.setDominicalCompNocturnas(MovilidadUtil.toTimeSec(nocturnas_dom));
                            gj.setFestivoExtraNocturno(calculator.hr_cero);
                            gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) - nocturnas_dom));

                        }
                    } else {
                        gj.setDominicalCompNocturnaExtra(calculator.hr_cero);
                        gj.setDominicalCompDiurnas(calculator.hr_cero);
                        gj.setDominicalCompNocturnas(MovilidadUtil.toTimeSec(nocturnas_dom));
                        gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) - nocturnas_dom));

                    }

                }
                return gj;
            }
            //La jornada conmienza un domingo y termina en festivo
            if (tieneFestivo == 2) {
//                System.out.println("Caso 2 Y1SUS");

//                System.out.println("Con Diurnas");
                if (MovilidadUtil.toSecs(timeOrigin) < MovilidadUtil.toSecs(calculator.getIni_nocturna())) {
                    diurnas_dom = MovilidadUtil.toSecs(calculator.getIni_nocturna()) - MovilidadUtil.toSecs(timeOrigin);
                    nocturnas_dom = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin) - diurnas_dom;
//                    System.out.println("diurnas_dom:->>" + MovilidadUtil.toTimeSec(diurnas_dom));
//                    System.out.println("nocturnas_dom:->>" + MovilidadUtil.toTimeSec(nocturnas_dom));

                    if (MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) > 0) {

                        int horasDominical = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin);

                        if (horasDominical >= MovilidadUtil.toSecs(horasJornada)) {
                            String horasExtrasComp = MovilidadUtil.toTimeSec(horasDominical - MovilidadUtil.toSecs(horasJornada));
                            gj.setDominicalCompNocturnaExtra(horasExtrasComp);
                            if (MovilidadUtil.toSecs(gj.getDominicalCompNocturnaExtra()) >= MovilidadUtil.toSecs(gj.getFestivoExtraNocturno())) {
                                gj.setFestivoExtraNocturno(calculator.hr_cero);
                            } else {
                                gj.setFestivoExtraNocturno(
                                        MovilidadUtil.toTimeSec(
                                                MovilidadUtil.toSecs(
                                                        gj.getFestivoExtraNocturno()
                                                ) - MovilidadUtil.toSecs(
                                                gj.getDominicalCompNocturnaExtra()
                                        )
                                        )
                                );
                            }
                            gj.setDominicalCompDiurnas(gj.getFestivoDiurno());
                            gj.setDominicalCompNocturnas(gj.getFestivoNocturno());
                            gj.setFestivoNocturno(calculator.hr_cero);
                            gj.setFestivoDiurno(calculator.hr_cero);
//                          
                        } else {
//                            System.out.println("Sin Horas extras dominicales");
                            gj.setDominicalCompNocturnaExtra(calculator.hr_cero);
                            gj.setDominicalCompDiurnaExtra(calculator.hr_cero);
                            gj.setDominicalCompDiurnas(MovilidadUtil.toTimeSec(diurnas_dom));
                            gj.setDominicalCompNocturnas(MovilidadUtil.toTimeSec(nocturnas_dom));
                            gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) - nocturnas_dom));
                            gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) - diurnas_dom));
//                            if (MovilidadUtil.toSecs(gj.getFestivoNocturno()) > nocturnas_dom) {
//                                gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) - nocturnas_dom));
//                            } else {
//                                gj.setFestivoNocturno(hr_cero);
//                            }

                        }
                    } else {
//                        System.out.println("getDiurnas:->>" + gj.getDiurnas());
//                        System.out.println("getNocturnas:->>" + gj.getNocturnas());
//                        System.out.println("getExtraDiurna:->>" + gj.getExtraDiurna());
//                        System.out.println("getExtraNocturna:->>" + gj.getExtraNocturna());
//                        System.out.println("getFestivoDiurno:->>" + gj.getFestivoDiurno());
//                        System.out.println("getFestivoNocturno:->>" + gj.getFestivoNocturno());
//                        System.out.println("getFestivoExtraDiurno:->>" + gj.getFestivoExtraDiurno());
//                        System.out.println("getFestivoExtraNocturno:->>" + gj.getFestivoExtraNocturno());

                        gj.setDominicalCompNocturnaExtra(calculator.hr_cero);
                        gj.setDominicalCompDiurnaExtra(calculator.hr_cero);
                        gj.setDominicalCompDiurnas(gj.getFestivoDiurno());
                        gj.setFestivoDiurno(calculator.hr_cero);
                        gj.setDominicalCompNocturnas(MovilidadUtil.toTimeSec(nocturnas_dom));
//                        System.out.println("Resta:->>" + gj.getFestivoNocturno() + " - " + MovilidadUtil.toTimeSec(nocturnas_dom));
                        gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) - nocturnas_dom));
//                        System.out.println("Empleado:->>" + gj.getIdEmpleado().getIdentificacion());
//                        System.out.println("Jornada:->>" + timeOrigin + "-" + timeDestiny);
//                        System.out.println("Festivo Nocturno:->>" + gj.getFestivoNocturno());
//                        System.out.println("Fecha:->>" + Util.dateFormat(gj.getFecha()));
                        return gj;

                    }

                } else {
//                    System.out.println("Sin Diurnas");

                    nocturnas_dom = ultimaHoraDia - MovilidadUtil.toSecs(timeOrigin);
                    gj.setDominicalCompDiurnas(calculator.hr_cero);
                    gj.setDominicalCompNocturnas(MovilidadUtil.toTimeSec(nocturnas_dom));
                    gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) - nocturnas_dom));
                }
                return gj;
            }
            //La jornda comineza o termina un domingo y no tuvo festivos
            if (tieneFestivo == 3) {
//                System.out.println("Caso 3 Y1SUS");
                if (MovilidadUtil.toSecs(gj.getFestivoDiurno()) > 0
                        || MovilidadUtil.toSecs(gj.getFestivoNocturno()) > 0
                        || MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) > 0
                        || MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) > 0) {

                    gj.setDominicalCompDiurnas(gj.getFestivoDiurno());
                    gj.setDominicalCompNocturnas(gj.getFestivoNocturno());
                    gj.setDominicalCompNocturnaExtra(gj.getFestivoExtraNocturno());
                    gj.setDominicalCompDiurnaExtra(gj.getFestivoExtraDiurno());

                    gj.setFestivoDiurno(calculator.hr_cero);
                    gj.setFestivoNocturno(calculator.hr_cero);
                    gj.setFestivoExtraNocturno(calculator.hr_cero);
                    gj.setFestivoExtraDiurno(calculator.hr_cero);
//                    genJornadaEJB.edit(gj);
                }
                return gj;
            }
            if (tieneFestivo == 0) {
//                System.out.println("Caso 0 Y1SUS");

                gj.setDominicalCompDiurnaExtra(null);
                gj.setDominicalCompNocturnaExtra(null);
                gj.setDominicalCompDiurnas(null);
                gj.setDominicalCompNocturnas(null);
                return gj;
            }

        } else {
            if (gj.getDominicalCompDiurnas() != null) {
//                System.out.println("Pasó por aca-------->>");
                if (gj.getAutorizado() != null
                        && gj.getAutorizado() == 1
                        && gj.getNominaBorrada() == 0) {

                    if (MovilidadUtil.toSecs(gj.getRealTimeDestiny()) > 0) {
                        gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDominicalCompDiurnas()) + MovilidadUtil.toSecs(gj.getFestivoDiurno())));
                        gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDominicalCompNocturnas()) + MovilidadUtil.toSecs(gj.getFestivoNocturno())));
                        gj.setDominicalCompDiurnas(null);
                        gj.setDominicalCompNocturnas(null);
                        gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDominicalCompNocturnaExtra()) + MovilidadUtil.toSecs(gj.getFestivoExtraNocturno())));
                        gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDominicalCompDiurnaExtra()) + MovilidadUtil.toSecs(gj.getFestivoExtraDiurno())));
                        gj.setDominicalCompNocturnaExtra(null);
                        gj.setDominicalCompDiurnaExtra(null);
                    } else {
                        gj.setDominicalCompDiurnas(null);
                        gj.setDominicalCompNocturnas(null);
                        gj.setDominicalCompNocturnaExtra(null);
                        gj.setDominicalCompDiurnaExtra(null);
                    }

                } else if ((gj.getAutorizado() == null && gj.getNominaBorrada() == 0)
                        || (gj.getAutorizado() != null && gj.getAutorizado() == 0
                        && gj.getNominaBorrada() == 0)) {

                    if (MovilidadUtil.toSecs(gj.getTimeOrigin()) > 0) {
                        gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDominicalCompDiurnas()) + MovilidadUtil.toSecs(gj.getFestivoDiurno())));
                        gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDominicalCompNocturnas()) + MovilidadUtil.toSecs(gj.getFestivoNocturno())));
                        gj.setDominicalCompDiurnas(null);
                        gj.setDominicalCompNocturnas(null);
                        gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDominicalCompNocturnaExtra()) + MovilidadUtil.toSecs(gj.getFestivoExtraNocturno())));
                        gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDominicalCompDiurnaExtra()) + MovilidadUtil.toSecs(gj.getFestivoExtraDiurno())));
                        gj.setDominicalCompNocturnaExtra(null);
                        gj.setDominicalCompDiurnaExtra(null);
                    } else {
                        gj.setDominicalCompDiurnas(null);
                        gj.setDominicalCompNocturnas(null);
                        gj.setDominicalCompNocturnaExtra(null);
                        gj.setDominicalCompDiurnaExtra(null);
                    }
                }
//                genJornadaEJB.edit(gj);
                return gj;
            }
        }
        return null;
    }

    public String generarTokenUrl(GenericaJornada gj) {

        GenericaJornadaToken gjt = new GenericaJornadaToken();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        String ctxPath = request.getContextPath();
        url = url.replaceFirst(uri, "");
        url = url + ctxPath;
        String token = TokenGeneratorUtil.nextToken();
        url = url + "/genericaJornadaToken/genericaJornadaToken.jsf?pin=" + token;
        gjt.setActivo(0);
        gjt.setCreado(MovilidadUtil.fechaCompletaHoy());
        gjt.setEstadoReg(0);
        gjt.setIdGenericaJornada(gj);
        gjt.setToken(token);
        gjt.setUsername(user.getUsername());
        genJornadaTokenEJB.create(gjt);
        return url;
    }

    private Map<String, List<GenericaJornada>> cargarMapSemanalDeJornadas(List<GenericaJornada> jornadas) throws ParseException {
        jornadas.sort((d1, d2) -> d1.getFecha().compareTo(d2.getFecha()));
        Date fromDate = jornadas.get(0).getFecha();
        int indexLast = jornadas.size() - 1;
        Date toDate = jornadas.get(indexLast).getFecha();
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        int i = 1;
        Map<String, List<GenericaJornada>> map = new HashMap<>();
        while (!current.getTime().after(toDate)) {
            Date diaDomingo = MovilidadUtil.getDiaSemana(current.getTime(), Calendar.SUNDAY);
            String key = Util.dateFormat(current.getTime()).concat("_").concat(Util.dateFormat(diaDomingo));
            List<GenericaJornada> list = jornadas.stream()
                    .filter(x -> MovilidadUtil.betweenSinHora(x.getFecha(), current.getTime(), diaDomingo))
                    .collect(Collectors.toList());
            map.put(key, list);
            current.setTime(MovilidadUtil.sumarDias(diaDomingo, 1));
        }
        List<Map.Entry<String, List<GenericaJornada>>> list = new ArrayList<>(map.entrySet());

        // Invertir el orden de la lista
        Collections.reverse(list);
        Map<String, List<GenericaJornada>> reversedMap = new HashMap<>();
        for (Map.Entry<String, List<GenericaJornada>> entry : list) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        return reversedMap;
    }

    public String getRol_user() {
        return rol_user;
    }

    public void setRol_user(String rol_user) {
        this.rol_user = rol_user;
    }

    public GenericaJornadaParam getGenJornadaParam() {
        return genJornadaParam;
    }

    public void setGenJornadaParam(GenericaJornadaParam genJornadaParam) {
        this.genJornadaParam = genJornadaParam;
    }

    public List<GenericaJornada> getGenericaJornadaList() {
        return genericaJornadaList;
    }

    public void setGenericaJornadaList(List<GenericaJornada> genericaJornadaList) {
        this.genericaJornadaList = genericaJornadaList;
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

    public Map<String, GenericaJornadaTipo> getMapJornadaTipo() {
        return mapJornadaTipo;
    }

    public void setMapJornadaTipo(Map<String, GenericaJornadaTipo> mapJornadaTipo) {
        this.mapJornadaTipo = mapJornadaTipo;
    }

    public Map<String, ParamFeriado> getMapParamFeriado() {
        return mapParamFeriado;
    }

    public void setMapParamFeriado(Map<String, ParamFeriado> mapParamFeriado) {
        this.mapParamFeriado = mapParamFeriado;
    }

    public ParamAreaUsr getPau() {
        return pau;
    }

    public void setPau(ParamAreaUsr pau) {
        this.pau = pau;
    }

}
