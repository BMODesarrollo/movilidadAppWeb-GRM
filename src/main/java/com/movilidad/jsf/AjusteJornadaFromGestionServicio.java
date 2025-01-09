/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this templParaCambioate file, choose Tools | TemplParaCambioates
 * and open the templParaCambioate in the editor.
 */
package com.movilidad.jsf;

import com.aja.jornada.controller.JornadaFlexible;
import com.aja.jornada.dto.ErrorPrgSercon;
import com.aja.jornada.model.EmpleadoLiqUtil;
import com.aja.jornada.model.GopUnidadFuncionalLiqUtil;
import com.aja.jornada.model.PrgSerconLiqUtil;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.ConfigControlJornada;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTarea;
import com.movilidad.model.PrgTc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.UtilJornada;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.SortPrgTcByTimeOrigin;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import liquidadorjornada.Jornada;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "ajustJorndFromGestionServ")
@ViewScoped
public class AjusteJornadaFromGestionServicio implements Serializable {

    /**
     * Creates a new instance of AjusteJornadaFromGestionServicio
     */
    public AjusteJornadaFromGestionServicio() {
    }
    private PrgSercon prgSerconSelected;
    private PrgSercon prgSerconFound;

    private String horaIniTurno1Selected;
    private String horaFinTurno1Selected;
    private String horaIniTurno2Selected;
    private String horaFinTurno2Selected;
    private String horaIniTurno3Selected;
    private String horaFinTurno3Selected;
    private String horaIniTurno1Found;
    private String horaFinTurno1Found;
    private String horaIniTurno2Found;
    private String horaFinTurno2Found;
    private String horaIniTurno3Found;
    private String horaFinTurno3Found;
    private String observacion;
    private String totalHorasExtrasSmnal;
    private Integer idEmpleadoSelected;
    private Integer idEmpleadoFound;
    private Date fecha;
    private boolean desasignarOpeServNull = true;
    private boolean disponibleAlCambio = true;
    private boolean disponibleContingencia = false;

    private List<PrgTc> listServiciosOpSelected;
    private List<PrgTc> listServiciosOpFound;
    private List<PrgTc> listDesasignarOpServNull;
    private List<PrgTc> listPrgTcGestionOperador;
    private List<PrgTc> listDispAjustados;
    private List<PrgTc> listTareasDispFound;

    private boolean flagRestringirMaxHorasExtras;
    private boolean flagRestringirMaxTurnoSelected;
    private boolean flagRestringirMaxTurnoFound;
    private boolean flagRestringirMaxHrExtrasSmnal;
    private boolean flagAjusteFound;
    private boolean flagAjusteSelected;

    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @Inject
    private PrgSerconMotivoJSF prgSerconMotivoJSF;
    @Inject
    private LiqJornadaJSFManagedBean LiqJornadaJSFMB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private JornadaFlexible js;

    private JornadaFlexible getINSTANCEJS() {
        if (js == null) {
            js = new JornadaFlexible();
        }
        return js;
    }

    public boolean cargarPrgSerconFound() {
        prgSerconFound = prgSerconEJB.getByIdEmpleadoAndFecha(idEmpleadoFound, fecha);
        if (prgSerconFound == null) {
            return true;
        }
        return false;
    }

    public void cargarTotalHorasExtrasSmanal() {
        Calendar primerDiaSmana = Calendar.getInstance(MovilidadUtil.localeCO());
        Calendar ultimoDiaSmana = Calendar.getInstance(MovilidadUtil.localeCO());
        primerDiaSmana.setTime(fecha);
        ultimoDiaSmana.setTime(fecha);
        primerDiaSmana.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ultimoDiaSmana.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // Total de horas extras semanales.
        Date resp = prgSerconEJB.totalHorasExtrasByRangoFechaAndIdEmpleado(
                idEmpleadoFound, fecha, primerDiaSmana.getTime(), ultimoDiaSmana.getTime());
        totalHorasExtrasSmnal = resp == null ? Jornada.hr_cero : Util.dateToTimeHHMMSS(resp);
//        System.out.println("totalHorasExtrasSmnal->" + totalHorasExtrasSmnal);
    }

    public void cargarPrgSerconSelected() {
        prgSerconSelected = prgSerconEJB.getByIdEmpleadoAndFecha(idEmpleadoSelected, fecha);
    }

    /**
     * Develeve valor booleano para saber si van a renderizar los campos
     * referentes a la afectación de las jornadas de lso operadores desde los
     * servicios.
     *
     * @return
     */
    public boolean visible() {
        try {
            return SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING);
        } catch (Exception e) {
            return false;
        }
    }

    boolean validarSelected(PrgTc tc) {
        return listPrgTcGestionOperador.stream().anyMatch((obj)
                -> (obj.getIdPrgTc().equals(tc.getIdPrgTc())));
    }

    boolean validarUnSelected(PrgTc tc) {
        return listDesasignarOpServNull.stream().anyMatch((obj)
                -> (obj.getIdPrgTc().equals(tc.getIdPrgTc())));
    }

    void restasHoras(PrgTc obj) {
        /**
         * ------------------------------------------------------------- Validar
         * tiempo de inicio o fin del servicio esta dentro del tiempo de la
         * primeta parte del turno.
         * -------------------------------------------------------------
         */
        if (MovilidadUtil.horaBetween(obj.getTimeOrigin(),
                horaIniTurno1Selected, horaFinTurno1Selected)
                || MovilidadUtil.horaBetween(obj.getTimeDestiny(),
                        horaIniTurno1Selected, horaFinTurno1Selected)) {
            /**
             * Calcular el tiempo total del servicio en int.
             */
            int duracionTarea = MovilidadUtil.toSecs(obj.getTimeDestiny())
                    - MovilidadUtil.toSecs(obj.getTimeOrigin());
//            System.out.println("duracionTarea->" + duracionTarea);
            /**
             * validar si el tiempo talta del turno 1 es mayor que la duración
             * de tarea.
             */
            if ((MovilidadUtil.toSecs(horaFinTurno1Selected)
                    - MovilidadUtil.toSecs(horaIniTurno1Selected))
                    > duracionTarea) {
                /**
                 * la hora fin del turno 1 v a ser igual a la resta del fin de;
                 * turno menos la duracion total de la tarea.
                 */
                horaFinTurno1Selected = MovilidadUtil.toTimeSec(
                        MovilidadUtil.toSecs(horaFinTurno1Selected)
                        - duracionTarea);
            } else {
                /**
                 * el turno 1 pasa a ser cero.
                 */
                horaIniTurno1Selected = Jornada.hr_cero;
                horaFinTurno1Selected = Jornada.hr_cero;
                /**
                 * Calcular la diferencia entre el total de duracion de la tarea
                 * y el total de duracion del turno 2
                 */
                int diferenciaTurno2DuracionTarea = duracionTarea
                        - (MovilidadUtil.toSecs(horaFinTurno2Selected)
                        - MovilidadUtil.toSecs(horaIniTurno2Selected));

                /**
                 * validar si el tiempo talta del turno 2 es mayor que el tiempo
                 * de diferencia entre el total de duracion de la tarea y el
                 * total de duracion del turno 2
                 */
                if ((MovilidadUtil.toSecs(horaFinTurno2Selected)
                        - MovilidadUtil.toSecs(horaIniTurno2Selected))
                        > diferenciaTurno2DuracionTarea) {
                    horaIniTurno2Selected = MovilidadUtil.toTimeSec(
                            MovilidadUtil.toSecs(horaIniTurno2Selected)
                            + diferenciaTurno2DuracionTarea);
                } else {
                    /**
                     * el turno 2 pasa a ser cero.
                     */
                    horaIniTurno2Selected = Jornada.hr_cero;
                    horaFinTurno2Selected = Jornada.hr_cero;

                    int diferenciaTurno3DiferenciaAnt = diferenciaTurno2DuracionTarea
                            - (MovilidadUtil.toSecs(horaFinTurno2Selected)
                            - MovilidadUtil.toSecs(horaIniTurno2Selected));
                    if ((MovilidadUtil.toSecs(horaFinTurno3Selected)
                            - MovilidadUtil.toSecs(horaIniTurno3Selected))
                            > diferenciaTurno3DiferenciaAnt) {
                        horaIniTurno3Selected = MovilidadUtil.toTimeSec(
                                MovilidadUtil.toSecs(horaIniTurno3Selected)
                                + diferenciaTurno3DiferenciaAnt);
                    } else {
                        /**
                         * el turno 3 pasa a ser cero.
                         */
                        horaIniTurno3Selected = Jornada.hr_cero;
                        horaFinTurno3Selected = Jornada.hr_cero;
                    }
                }
            }
        }
        if (MovilidadUtil.horaBetween(obj.getTimeOrigin(), horaIniTurno2Selected, horaFinTurno2Selected)
                || MovilidadUtil.horaBetween(obj.getTimeDestiny(), horaIniTurno2Selected, horaFinTurno2Selected)) {

            int duracionTarea = MovilidadUtil.toSecs(obj.getTimeDestiny())
                    - MovilidadUtil.toSecs(obj.getTimeOrigin());

            /**
             * validar si el tiempo total del servicio es mayor que el tiempo de
             * diferencia entre el total de duracion de la tarea y el total de
             * duracion del turno 2
             */
            if ((MovilidadUtil.toSecs(horaFinTurno2Selected)
                    - MovilidadUtil.toSecs(horaIniTurno2Selected))
                    > duracionTarea) {
                horaIniTurno2Selected = MovilidadUtil.toTimeSec(
                        MovilidadUtil.toSecs(horaIniTurno2Selected)
                        + duracionTarea);
            } else {
                /**
                 * el turno 2 pasa a ser cero.
                 */
                horaIniTurno2Selected = Jornada.hr_cero;
                horaFinTurno2Selected = Jornada.hr_cero;

                int diferenciaTurno3DiferenciaAnt = duracionTarea
                        - (MovilidadUtil.toSecs(horaFinTurno2Selected)
                        - MovilidadUtil.toSecs(horaIniTurno2Selected));
                if ((MovilidadUtil.toSecs(horaFinTurno3Selected)
                        - MovilidadUtil.toSecs(horaIniTurno3Selected))
                        > diferenciaTurno3DiferenciaAnt) {
                    horaIniTurno3Selected = MovilidadUtil.toTimeSec(
                            MovilidadUtil.toSecs(horaIniTurno3Selected)
                            + diferenciaTurno3DiferenciaAnt);
                } else {
                    /**
                     * el turno 3 pasa a ser cero.
                     */
                    horaIniTurno3Selected = Jornada.hr_cero;
                    horaFinTurno3Selected = Jornada.hr_cero;
                }
            }
        }
        if (MovilidadUtil.horaBetween(obj.getTimeOrigin(), horaIniTurno3Selected, horaFinTurno3Selected)
                || MovilidadUtil.horaBetween(obj.getTimeDestiny(), horaIniTurno3Selected, horaFinTurno3Selected)) {

            int duracionTarea = MovilidadUtil.toSecs(obj.getTimeDestiny())
                    - MovilidadUtil.toSecs(obj.getTimeOrigin());
            if ((MovilidadUtil.toSecs(horaFinTurno3Selected)
                    - MovilidadUtil.toSecs(horaIniTurno3Selected))
                    > duracionTarea) {
                horaIniTurno3Selected = MovilidadUtil.toTimeSec(
                        MovilidadUtil.toSecs(horaIniTurno3Selected)
                        + duracionTarea);
            } else {
                /**
                 * el turno 3 pasa a ser cero.
                 */
                horaIniTurno3Selected = Jornada.hr_cero;
                horaFinTurno3Selected = Jornada.hr_cero;
            }
        }
    }

    void restasHorasII(PrgTc obj) {
        /**
         * ------------------------------------------------------------- Validar
         * tiempo de inicio o fin del servicio esta dentro del tiempo de la
         * primeta parte del turno.
         * -------------------------------------------------------------
         */
        if (MovilidadUtil.horaBetween(obj.getTimeOrigin(),
                horaIniTurno1Selected, horaFinTurno1Selected)
                || MovilidadUtil.horaBetween(obj.getTimeDestiny(),
                        horaIniTurno1Selected, horaFinTurno1Selected)) {
            horaIniTurno1Selected = obj.getTimeDestiny();
        }
        if (MovilidadUtil.horaBetween(obj.getTimeOrigin(),
                horaIniTurno2Selected, horaFinTurno2Selected)
                || MovilidadUtil.horaBetween(obj.getTimeDestiny(),
                        horaIniTurno2Selected, horaFinTurno2Selected)) {
            horaIniTurno2Selected = obj.getTimeDestiny();

        }
        if (MovilidadUtil.horaBetween(obj.getTimeOrigin(),
                horaIniTurno3Selected, horaFinTurno3Selected)
                || MovilidadUtil.horaBetween(obj.getTimeDestiny(),
                        horaIniTurno3Selected, horaFinTurno3Selected)) {
            horaIniTurno3Selected = obj.getTimeDestiny();

        }
    }

    void cargarListaServiciosOpSelected() {
        listServiciosOpSelected = prgTcEJB.getPrgTcByIdEmpleadoAndFechaAndIdGopUF(idEmpleadoSelected, fecha, 0);
    }

    void cargarListaServiciosOpFound() {
        listServiciosOpFound = prgTcEJB.getPrgTcByIdEmpleadoAndFechaAndIdGopUF(idEmpleadoFound, fecha, 0);
    }

    boolean validarParaAgregar(PrgTc obj) {
        if (disponibleAlCambio) {
//            System.out.println("1");
            return true;
        }
        if (validarSelected(obj)) {
//            System.out.println("2");
            return false;
        }
        if (desasignarOpeServNull && validarUnSelected(obj)) {
//            System.out.println("3");
            return false;
        }
//        System.out.println("4");
        return true;
    }

    boolean validarFoundParaAgregar(PrgTc obj) {
        if (disponibleAlCambio) {
//            System.out.println("1");
            return true;
        }
        if (validarSelected(obj)) {
//            System.out.println("2");
            return false;
        }
//        System.out.println("4");
        return true;
    }

    /**
     * Calcula la jornada del operador seleccionado desde lops servicios prgTc.
     */
    void recrearJornadaSelected() {
        horaIniTurno1Selected = Jornada.hr_cero;
        horaFinTurno1Selected = Jornada.hr_cero;
        horaIniTurno2Selected = Jornada.hr_cero;
        horaFinTurno2Selected = Jornada.hr_cero;
        horaIniTurno3Selected = Jornada.hr_cero;
        horaFinTurno3Selected = Jornada.hr_cero;
        flagRestringirMaxTurnoSelected = false;
        for (PrgTc obj : listServiciosOpSelected) {
            if (validarParaAgregar(obj)) {
                if (horaIniTurno1Selected.equals(Jornada.hr_cero)) {
                    horaIniTurno1Selected = obj.getTimeOrigin();
                }
                if (horaFinTurno1Selected.equals(Jornada.hr_cero)) {
                    horaFinTurno1Selected = obj.getTimeDestiny();
                } else {
                    if (horaFinTurno1Selected.equals(obj.getTimeOrigin())) {
                        horaFinTurno1Selected = obj.getTimeDestiny();
                    } else {
                        if (horaIniTurno2Selected.equals(Jornada.hr_cero)) {
                            horaIniTurno2Selected = obj.getTimeOrigin();
                        }
                        if (horaFinTurno2Selected.equals(Jornada.hr_cero)) {
                            horaFinTurno2Selected = obj.getTimeDestiny();
                        } else {
                            if (horaFinTurno2Selected.equals(obj.getTimeOrigin())) {
                                horaFinTurno2Selected = obj.getTimeDestiny();
                            } else {
                                if (horaIniTurno3Selected.equals(Jornada.hr_cero)) {
                                    horaIniTurno3Selected = obj.getTimeOrigin();
                                }
                                if (horaFinTurno3Selected.equals(Jornada.hr_cero)) {
                                    horaFinTurno3Selected = obj.getTimeDestiny();
                                } else {
                                    if (horaFinTurno3Selected.equals(obj.getTimeOrigin())) {
                                        horaFinTurno3Selected = obj.getTimeDestiny();
                                    } else {
                                        /**
                                         * El turno del operador tiene mas de 3
                                         * partes de trabajo.
                                         */
                                        MovilidadUtil.addAdvertenciaMessage(
                                                "No es posible ajustar esta jornada. Vaya a Control Jornada luego.");
                                        flagRestringirMaxTurnoSelected = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        validarTurnoCambioDia(horaFinTurno1Selected, horaIniTurno2Selected, horaFinTurno2Selected, horaIniTurno3Selected, prgSerconSelected.getIdEmpleado().getCodigoTm());

        MovilidadUtil.updateComponent(
                "formGestionPrgTc:tabViews:turnoEmplSelected");
    }

    /**
     * Calcula la jornada del operador seleccionado desde lops servicios prgTc.
     */
    void recrearJornadaFound(String msg) {
        resetHorasFound();
        flagRestringirMaxTurnoFound = false;
        for (PrgTc obj : listServiciosOpFound) {
            if (horaIniTurno1Found.equals(Jornada.hr_cero)) {
                horaIniTurno1Found = obj.getTimeOrigin();
            }
            if (horaFinTurno1Found.equals(Jornada.hr_cero)) {
                horaFinTurno1Found = obj.getTimeDestiny();
            } else {
                if (horaFinTurno1Found.equals(obj.getTimeOrigin())) {
                    horaFinTurno1Found = obj.getTimeDestiny();
                } else {
                    if (horaIniTurno2Found.equals(Jornada.hr_cero)) {
                        horaIniTurno2Found = obj.getTimeOrigin();
                    }
                    if (horaFinTurno2Found.equals(Jornada.hr_cero)) {
                        horaFinTurno2Found = obj.getTimeDestiny();
                    } else {
                        if (horaFinTurno2Found.equals(obj.getTimeOrigin())) {
                            horaFinTurno2Found = obj.getTimeDestiny();
                        } else {
                            if (horaIniTurno3Found.equals(Jornada.hr_cero)) {
                                horaIniTurno3Found = obj.getTimeOrigin();
                            }
                            if (horaFinTurno3Found.equals(Jornada.hr_cero)) {
                                horaFinTurno3Found = obj.getTimeDestiny();
                            } else {
                                if (horaFinTurno3Found.equals(obj.getTimeOrigin())) {
                                    horaFinTurno3Found = obj.getTimeDestiny();
                                } else {
                                    /**
                                     * El turno del operador tiene mas de 3
                                     * partes de trabajo.
                                     */
                                    MovilidadUtil.addAdvertenciaMessage("No es posible ajustar esta jornada. Vaya a Control Jornada luego.");
                                    flagRestringirMaxTurnoFound = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        MovilidadUtil.updateComponent(msg);
    }

    void ajusteJornadasSelected(List<PrgTc> listaDesasignacion,
            List<PrgTc> listaServSinSeleccion) {

        if (prgSerconSelected.getRealTimeOrigin() == null || prgSerconSelected.getAutorizado() == null
                || (prgSerconSelected.getAutorizado() != null && prgSerconSelected.getAutorizado() == 0)) {
            horaIniTurno1Selected = prgSerconSelected.getTimeOrigin();
            horaIniTurno1Selected = prgSerconSelected.getTimeDestiny();
            horaIniTurno1Selected = prgSerconSelected.getHiniTurno2() == null ? Jornada.hr_cero : prgSerconSelected.getHiniTurno2();
            horaIniTurno1Selected = prgSerconSelected.getHfinTurno2() == null ? Jornada.hr_cero : prgSerconSelected.getHfinTurno2();
            horaIniTurno1Selected = prgSerconSelected.getHiniTurno3() == null ? Jornada.hr_cero : prgSerconSelected.getHiniTurno3();
            horaIniTurno1Selected = prgSerconSelected.getHfinTurno3() == null ? Jornada.hr_cero : prgSerconSelected.getHfinTurno3();
        } else {
            horaIniTurno1Selected = prgSerconSelected.getRealTimeOrigin();
            horaIniTurno1Selected = prgSerconSelected.getRealTimeDestiny();
            horaIniTurno1Selected = prgSerconSelected.getRealHiniTurno2() == null ? Jornada.hr_cero : prgSerconSelected.getRealHiniTurno2();
            horaIniTurno1Selected = prgSerconSelected.getRealHfinTurno2() == null ? Jornada.hr_cero : prgSerconSelected.getRealHfinTurno2();
            horaIniTurno1Selected = prgSerconSelected.getRealHiniTurno3() == null ? Jornada.hr_cero : prgSerconSelected.getRealHiniTurno3();
            horaIniTurno1Selected = prgSerconSelected.getRealHfinTurno3() == null ? Jornada.hr_cero : prgSerconSelected.getRealHfinTurno3();
        }
        if (prgSerconSelected != null) {
            for (PrgTc obj : listaDesasignacion) {
                if (disponibleAlCambio) {
                    crearPrgTcDisponible(obj);
                } else {
                    restasHorasII(obj);
                }
            }
        }
        MovilidadUtil.updateComponent("formGestionPrgTc:tabViews:turnoEmplSelected");
    }

    void ajusteJornadaFoundFromNewServices(List<PrgTc> listaAsignacion, String msg) {
        listServiciosOpFound.addAll(listaAsignacion);
        listServiciosOpFound.sort(SortPrgTcByTimeOrigin.getSortPrgTcByTimeOrigin());
        recrearJornadaFound(msg);
    }

    int numeroParteJornadaFound() {
        int numPartes = 0;
        if (MovilidadUtil.toSecs(horaIniTurno1Found) > 0) {
            numPartes++;
        }
        if (MovilidadUtil.toSecs(horaIniTurno2Found) > 0) {
            numPartes++;
        }
        if (MovilidadUtil.toSecs(horaIniTurno3Found) > 0) {
            numPartes++;
        }
        return numPartes;
    }

    /**
     * Realiza el ajuste de las jornadas PrgSercon mediante de los servicios
     * asignados y desasignados.
     *
     * No se tiene en cuenta el el números de horas extras permitidas de acuerdo
     * a la legislación Laboral.
     *
     * @param listaAsignacion
     * @param listaDesasignacion
     */
    void ajusteJornadasFoundII(List<PrgTc> listaAsignacion, String msg) {
        int numPartes = numeroParteJornadaFound();
        listaAsignacion.sort(SortPrgTcByTimeOrigin.getSortPrgTcByTimeOrigin());
        flagRestringirMaxTurnoFound = false;
        flagRestringirMaxHorasExtras = false;
        flagAjusteFound = false;
        for (PrgTc obj : listaAsignacion) {
            /**
             * Jornada cero
             */
            if (horaIniTurno1Found.equals(Jornada.hr_cero)) {
                System.out.println("CASO-0");
                horaIniTurno1Found = obj.getTimeOrigin();
                horaFinTurno1Found = obj.getTimeDestiny();
            } else /**
             * El timeorigin esta dentro del rango del primer tunro pero
             * timeDestiny esta fuera del rango.
             */
            if (MovilidadUtil.horaBetween(obj.getTimeOrigin(), horaIniTurno1Found, horaFinTurno1Found)
                    && MovilidadUtil.toSecs(obj.getTimeDestiny()) > MovilidadUtil.toSecs(horaFinTurno1Found)) {
                /**
                 * El tiempo final del servicio es menor que el tiempo inicial
                 * del segundo turno.
                 */
                if (MovilidadUtil.toSecs(obj.getTimeDestiny())
                        < MovilidadUtil.toSecs(horaIniTurno2Found)) {
                    System.out.println("CASO-1");
                    flagAjusteFound = true;
                    horaFinTurno1Found = obj.getTimeDestiny();
                    /**
                     * Validar que la parte 2 de la jornada no sea CERO
                     */
                } else if (!horaFinTurno2Found.equals(Jornada.hr_cero)) {

                    if (MovilidadUtil.toSecs(obj.getTimeDestiny())
                            < MovilidadUtil.toSecs(horaFinTurno2Found)) {
                        horaFinTurno1Found = horaFinTurno2Found;
                        horaIniTurno2Found = horaIniTurno3Found;
                        horaFinTurno2Found = horaFinTurno3Found;
                        horaIniTurno3Found = Jornada.hr_cero;
                        horaFinTurno3Found = Jornada.hr_cero;
                        numPartes = 2;
                    } else /**
                     * El tiempo final del servicio es menor que el tiempo
                     * inicial del tercer turno.
                     */
                    if (MovilidadUtil.toSecs(obj.getTimeDestiny())
                            < MovilidadUtil.toSecs(horaIniTurno3Found)) {
                        System.out.println("CASO-2");
                        flagAjusteFound = true;
                        horaFinTurno2Found = obj.getTimeDestiny();
                        /**
                         * Validar que la parte 3 de la jornada no sea CERO
                         */
                    } else if (!horaFinTurno3Found.equals(Jornada.hr_cero)) {
                        System.out.println("CASO-3");
                        flagAjusteFound = true;
                        horaFinTurno1Found = obj.getTimeDestiny();
                        horaIniTurno2Found = Jornada.hr_cero;
                        horaFinTurno2Found = Jornada.hr_cero;
                        horaIniTurno3Found = Jornada.hr_cero;
                        horaFinTurno3Found = Jornada.hr_cero;
                        numPartes = 1;

                    } else {
                        System.out.println("CASO-4");
                        flagAjusteFound = true;
                        horaFinTurno1Found = obj.getTimeDestiny();
                        horaIniTurno2Found = Jornada.hr_cero;
                        horaFinTurno2Found = Jornada.hr_cero;
                        numPartes = 1;
                    }
                } else {
                    System.out.println("CASO-5");
                    flagAjusteFound = true;
                    horaFinTurno1Found = obj.getTimeDestiny();
                }
            } else /**
             * El tiempo inicio del servicio está antes del turno 1 y el tiempo
             * de destino está dentro del turno 1
             */
            if (MovilidadUtil.horaBetween(obj.getTimeDestiny(), horaIniTurno1Found, horaFinTurno1Found)
                    && MovilidadUtil.toSecs(obj.getTimeOrigin()) < MovilidadUtil.toSecs(horaIniTurno1Found)) {
                System.out.println("CASO-6");
                flagAjusteFound = true;
                horaIniTurno1Found = obj.getTimeOrigin();
            } else /**
             * El tiempo fin del servicio es mayor que el tiempo de fin del
             * turno 1 y el tiempo 2 es nulo o cero.
             */
            if (MovilidadUtil.toSecs(obj.getTimeOrigin()) > MovilidadUtil.toSecs(horaFinTurno1Found)
                    && horaIniTurno2Found.equals(Jornada.hr_cero)) {
                System.out.println("CASO-7");
                flagAjusteFound = true;
                numPartes++;
                horaIniTurno2Found = obj.getTimeOrigin();
                horaFinTurno2Found = obj.getTimeDestiny();
            } else /**
             * El timeorigin esta dentro del rango del segundo turno pero
             * timeDestiny esta fuera del rango.
             */
            if (MovilidadUtil.horaBetween(obj.getTimeOrigin(), horaIniTurno2Found, horaFinTurno2Found)
                    && MovilidadUtil.toSecs(obj.getTimeDestiny()) > MovilidadUtil.toSecs(horaFinTurno2Found)) {

                if (MovilidadUtil.toSecs(obj.getTimeDestiny())
                        < MovilidadUtil.toSecs(horaIniTurno3Found)) {
                    System.out.println("CASO-8");
                    flagAjusteFound = true;
                    horaFinTurno2Found = obj.getTimeDestiny();
                    /**
                     * Validar que la parte 3 de la jornada no sea CERO
                     */
                } else if (!horaFinTurno3Found.equals(Jornada.hr_cero)) {
                    System.out.println("CASO-9");
                    flagAjusteFound = true;
                    horaFinTurno2Found = obj.getTimeDestiny();
                    horaIniTurno3Found = Jornada.hr_cero;
                    horaFinTurno3Found = Jornada.hr_cero;
                    numPartes = 2;
                } else {
                    System.out.println("CASO-10");
                    flagAjusteFound = true;
                    horaFinTurno2Found = obj.getTimeDestiny();
                    numPartes = 2;
                    horaIniTurno3Found = Jornada.hr_cero;
                    horaFinTurno3Found = Jornada.hr_cero;
                }
            } else /**
             * El tiempo fin del servicio es mayor que el tiempo de fin del
             * turno 2 y el tiempo 3 es nulo o cero.
             */
            if (MovilidadUtil.toSecs(obj.getTimeOrigin()) > MovilidadUtil.toSecs(horaFinTurno2Found)
                    && !horaFinTurno2Found.equals(Jornada.hr_cero)
                    && horaIniTurno3Found.equals(Jornada.hr_cero)) {
                System.out.println("CASO-11");
                flagAjusteFound = true;
                numPartes++;
                horaIniTurno3Found = obj.getTimeOrigin();
                horaFinTurno3Found = obj.getTimeDestiny();

            } else /**
             * El tiempo inicio del servicio está antes del turno 2 y el tiempo
             * de destino está dentro del turno 2
             */
            if (MovilidadUtil.horaBetween(obj.getTimeDestiny(), horaIniTurno2Found, horaFinTurno2Found)
                    && MovilidadUtil.toSecs(obj.getTimeOrigin()) < MovilidadUtil.toSecs(horaIniTurno2Found)) {
                System.out.println("CASO-12");
                flagAjusteFound = true;
                horaIniTurno2Found = obj.getTimeOrigin();
            } else /**
             * El timeorigin esta dentro del rango del tercer turno pero
             * timeDestiny esta fuera del rango(despues).
             */
            if (MovilidadUtil.horaBetween(obj.getTimeOrigin(), horaIniTurno3Found, horaFinTurno3Found)
                    && MovilidadUtil.toSecs(obj.getTimeDestiny()) > MovilidadUtil.toSecs(horaFinTurno3Found)) {
                System.out.println("CASO-13");
                flagAjusteFound = true;
                horaFinTurno3Found = obj.getTimeDestiny();
            } else /**
             * El tiempo inicio del servicio está antes del turno 3 y el tiempo
             * de destino está dentro del turno 3
             */
            if (MovilidadUtil.horaBetween(obj.getTimeDestiny(), horaIniTurno3Found, horaFinTurno3Found)
                    && MovilidadUtil.toSecs(obj.getTimeOrigin()) < MovilidadUtil.toSecs(horaIniTurno3Found)) {
                System.out.println("CASO-14");
                flagAjusteFound = true;
                horaIniTurno3Found = obj.getTimeOrigin();
            } else if (MovilidadUtil.toSecs(obj.getTimeDestiny()) < MovilidadUtil.toSecs(horaIniTurno1Found)) {
                numPartes++;
                if (numPartes > 3) {
                    System.out.println("CASO-15-SUMA PARTE");
                    /**
                     * El turno del operador tiene mas de 3 partes de trabajo.
                     */
                    MovilidadUtil.addAdvertenciaMessage("No es posible ajustar esta jornada. Vaya a Control Jornada luego.");
                    flagRestringirMaxTurnoFound = true;
                } else {
                    System.out.println("CASO-16");
                    flagAjusteFound = true;
                    horaIniTurno3Found = horaIniTurno2Found;
                    horaFinTurno3Found = horaFinTurno2Found;
                    horaIniTurno2Found = horaIniTurno1Found;
                    horaFinTurno2Found = horaFinTurno1Found;
                    horaIniTurno1Found = obj.getTimeOrigin();
                    horaFinTurno1Found = obj.getTimeDestiny();

                }

            } else if (MovilidadUtil.toSecs(obj.getTimeOrigin()) > MovilidadUtil.toSecs(horaFinTurno1Found)
                    && MovilidadUtil.toSecs(obj.getTimeDestiny()) < MovilidadUtil.toSecs(horaIniTurno2Found)) {
                numPartes++;
                if (numPartes > 3) {
                    System.out.println("CASO-17-SUMA PARTE");
                    flagAjusteFound = true;
                    /**
                     * El turno del operador tiene mas de 3 partes de trabajo.
                     */
                    MovilidadUtil.addAdvertenciaMessage("No es posible ajustar esta jornada. Vaya a Control Jornada luego.");
                    flagRestringirMaxTurnoFound = true;
                } else {
                    System.out.println("CASO-18");
                    flagAjusteFound = true;
                    horaIniTurno3Found = horaIniTurno2Found;
                    horaFinTurno3Found = horaFinTurno2Found;
                    horaIniTurno2Found = obj.getTimeOrigin();
                    horaFinTurno2Found = obj.getTimeDestiny();
                }
            } else if (MovilidadUtil.toSecs(obj.getTimeOrigin()) > MovilidadUtil.toSecs(horaFinTurno2Found)
                    && MovilidadUtil.toSecs(obj.getTimeDestiny()) < MovilidadUtil.toSecs(horaIniTurno3Found)) {
                numPartes++;
                if (numPartes > 3) {
                    System.out.println("CASO-19-SUMA PARTE");
                    flagAjusteFound = true;
                    /**
                     * El turno del operador tiene mas de 3 partes de trabajo.
                     */
                    MovilidadUtil.addAdvertenciaMessage("No es posible ajustar esta jornada. Vaya a Control Jornada luego.");
                    flagRestringirMaxTurnoFound = true;
                } else {
                    System.out.println("CASO-20");
                    flagAjusteFound = true;
                    horaIniTurno3Found = obj.getTimeOrigin();
                    horaFinTurno3Found = obj.getTimeDestiny();
                }
            } else if (MovilidadUtil.toSecs(obj.getTimeOrigin()) > MovilidadUtil.toSecs(horaFinTurno3Found)
                    && !horaFinTurno3Found.equals(Jornada.hr_cero)) {
                numPartes++;
                if (numPartes > 3) {
                    System.out.println("CASO-21-SUMA PARTE");
                    flagAjusteFound = true;
                    /**
                     * El turno del operador tiene mas de 3 partes de trabajo.
                     */
                    MovilidadUtil.addAdvertenciaMessage("No es posible ajustar esta jornada. Vaya a Control Jornada luego.");
                    flagRestringirMaxTurnoFound = true;
                }
            }
        }
        if (numPartes > 3) {
            MovilidadUtil.addAdvertenciaMessage("numPartes->" + numPartes);
        }
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        if (UtilJornada.tipoLiquidacion()) {//Flexible
            PrgSercon ps = new PrgSercon();
            ps.setRealTimeOrigin(horaIniTurno1Found);
            ps.setRealTimeDestiny(horaFinTurno1Found);
            ps.setRealHiniTurno2(horaIniTurno2Found);
            ps.setRealHfinTurno2(horaFinTurno2Found);
            ps.setRealHiniTurno3(horaIniTurno3Found);
            ps.setRealHfinTurno3(horaFinTurno3Found);
            ps.setWorkTime(prgSerconFound.getWorkTime());
            ps.setSercon(prgSerconFound.getSercon());
            ps.setFecha(prgSerconFound.getFecha());
            validarHorasExtrasFlexible(ps);

        } else {
            int difPart1 = MovilidadUtil.toSecs(horaFinTurno1Found) - MovilidadUtil.toSecs(horaIniTurno1Found);
            int difPart2 = MovilidadUtil.toSecs(horaFinTurno2Found) - MovilidadUtil.toSecs(horaIniTurno2Found);
            int difPart3 = MovilidadUtil.toSecs(horaFinTurno3Found) - MovilidadUtil.toSecs(horaIniTurno3Found);
            int totalProduccion = difPart1 + difPart2 + difPart3;
            int totalHorasExtras = totalProduccion - MovilidadUtil.toSecs(UtilJornada.getTotalHrsLaborales());
            /**
             * Validar si el total de horas extras en mayor que el criterio
             * minimo para considerar horas extras.
             */
//        if (totalHorasExtras >= MovilidadUtil.toSecs(JornadaUtil.getCriterioMinHrsExtra())) {
            if (totalHorasExtras > MovilidadUtil.toSecs(UtilJornada.getMaxHrsExtrasDia())) {
                String mssg = "Se excedió el máximo de horas extras " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                        + MovilidadUtil.toTimeSec((totalProduccion - MovilidadUtil.toSecs(UtilJornada.getTotalHrsLaborales())));
                if (UtilJornada.respetarMaxHorasExtrasDiarias()) {
                    MovilidadUtil.addErrorMessage(mssg);
                    flagRestringirMaxHorasExtras = true;
                } else {
                    MovilidadUtil.addAdvertenciaMessage(mssg);
                }
            }
//        }
        }
        validarTurnoCambioDia(horaFinTurno1Found, horaIniTurno2Found, horaFinTurno2Found, horaIniTurno3Found, prgSerconFound.getIdEmpleado().getCodigoTm());
        MovilidadUtil.updateComponent(msg);
    }

    PrgTc crearPrgTcDisponible(PrgTc tc) {
        PrgTc tcDispo = new PrgTc();
        tcDispo.setIdPrgTcResumen(tc.getIdPrgTcResumen());
        tcDispo.setFecha(tc.getFecha());
        if (disponibleContingencia) {
            tcDispo.setSercon("DP_CONTINGC");
            try {
                tcDispo.setIdTaskType(new PrgTarea(Integer.parseInt(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_TAREA_DISPO_CONTINGENCIA))));
            } catch (NumberFormatException e) {
                MovilidadUtil.addAdvertenciaMessage("No existe configurada tarea para disponibles");
            }
        } else {
            tcDispo.setSercon("DP_PATIO");
            try {
                tcDispo.setIdTaskType(new PrgTarea(Integer.parseInt(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_TAREA_DISPO_PATIO))));
            } catch (NumberFormatException e) {
                MovilidadUtil.addAdvertenciaMessage("No existe configurada tarea para disponibles");
            }
        }

        tcDispo.setIdEmpleado(tc.getIdEmpleado());
        tcDispo.setObservaciones(observacion);
        tcDispo.setAmplitude(tc.getAmplitude());
        tcDispo.setWorkTime(tc.getWorkTime());
        tcDispo.setWorkPiece(tc.getWorkPiece());
        tcDispo.setFromStop(new PrgStopPoint(MovilidadUtil.patio()));
        tcDispo.setToStop(new PrgStopPoint(MovilidadUtil.patio()));
        tcDispo.setTimeOrigin(tc.getTimeOrigin());
        tcDispo.setTimeDestiny(tc.getTimeDestiny());
        tcDispo.setTaskDuration(tc.getTaskDuration());
        tcDispo.setIdGopUnidadFuncional(tc.getIdEmpleado()
                .getIdGopUnidadFuncional());
        tcDispo.setEstadoReg(0);
        tcDispo.setEstadoOperacion(0);
        tcDispo.setUsername(user.getUsername());
        tcDispo.setCreado(MovilidadUtil.fechaCompletaHoy());
        return tcDispo;
    }

    PrgTc crearPrgTcDisponibleConTarea(PrgTc tc, String timeOrigin,
            String timeDestiny) {
        PrgTc tcDispo = new PrgTc();
        tcDispo.setIdPrgTcResumen(tc.getIdPrgTcResumen());
        tcDispo.setFecha(tc.getFecha());
        tcDispo.setSercon(tc.getSercon());
        tcDispo.setIdEmpleado(tc.getIdEmpleado());
        tcDispo.setObservaciones(observacion);
        tcDispo.setAmplitude(tc.getAmplitude());
        tcDispo.setWorkTime(tc.getWorkTime());
        tcDispo.setWorkPiece(tc.getWorkPiece());
        tcDispo.setFromStop(tc.getFromStop());
        tcDispo.setToStop(tc.getToStop());
        tcDispo.setTimeOrigin(timeOrigin);
        tcDispo.setTimeDestiny(timeDestiny);
        tcDispo.setTaskDuration(tc.getTaskDuration());
        tcDispo.setIdTaskType(tc.getIdTaskType());
        tcDispo.setIdGopUnidadFuncional(tc.getIdEmpleado()
                .getIdGopUnidadFuncional());
        tcDispo.setEstadoReg(0);
        tcDispo.setEstadoOperacion(0);
        tcDispo.setUsername(user.getUsername());
        tcDispo.setCreado(MovilidadUtil.fechaCompletaHoy());
        return tcDispo;
    }

    void guardarAjusteJornadaFound(List<PrgTc> listservices) {
        if (prgSerconFound != null) {
            if (flagAjusteFound) {
                /**
                 * Set de tiempos reales para la jornada del operador
                 * consultado.
                 */
                prgSerconFound.setRealTimeOrigin(horaIniTurno1Found);
                prgSerconFound.setRealTimeDestiny(horaFinTurno1Found);
                prgSerconFound.setRealHiniTurno2(horaIniTurno2Found);
                prgSerconFound.setRealHfinTurno2(horaFinTurno2Found);
                prgSerconFound.setRealHiniTurno3(horaIniTurno3Found);
                prgSerconFound.setRealHfinTurno3(horaFinTurno3Found);
                prgSerconFound.setIdPrgSerconMotivo(prgSerconMotivoJSF.getPrgSerconMotivo());

                prgSerconFound.setUserGenera(user.getUsername());
                prgSerconFound.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
                prgSerconFound.setObservaciones(observacion);
                prgSerconFound.setPrgModificada(1);
                if (UtilJornada.validarCriterioMinJornada(prgSerconFound)) {
                    /**
                     * Datos para que la jornada quede para aprobar.
                     */
                    prgSerconFound.setUserAutorizado("");
                    prgSerconFound.setFechaAutoriza(null);
                    prgSerconFound.setAutorizado(-1);
                } else {
                    prgSerconFound.setUserAutorizado(user.getUsername());
                    prgSerconFound.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                    // (1) uno para autorizar, (0) cero para no autorizar
                    prgSerconFound.setAutorizado(1);
                    if (UtilJornada.esPorPartes(prgSerconFound)) {
                        LiqJornadaJSFMB.cargarCalcularDatoPorPartes(prgSerconFound);
                    } else {
                        LiqJornadaJSFMB.cargarCalcularDato(prgSerconFound);
                    }
                }
                prgSerconEJB.edit(prgSerconFound);

            }
        }
        recalcularDisponibilidad(listTareasDispFound, listservices);
        for (PrgTc obj : listDispAjustados) {
            obj.setObservaciones(observacion);
            prgTcEJB.create(obj);
        }
        for (PrgTc obj : listTareasDispFound) {
            if (obj.getEstadoOperacion().equals(ConstantsUtil.CODE_ELIMINADO_DISP_9)) {
                obj.setObservaciones(observacion);
                prgTcEJB.edit(obj);
            }
        }
    }

    void guardarAjusteJornada() {
        if (disponibleAlCambio) {
            for (PrgTc obj : listPrgTcGestionOperador) {
                obj.setIdEmpleado(prgSerconSelected.getIdEmpleado());
                prgTcEJB.create(crearPrgTcDisponible(obj));
            }
            if (desasignarOpeServNull) {
                for (PrgTc obj : listDesasignarOpServNull) {
                    obj.setIdEmpleado(prgSerconSelected.getIdEmpleado());
                    prgTcEJB.create(crearPrgTcDisponible(obj));
                }
            }
        }

        if (prgSerconSelected != null) {
            if (!disponibleAlCambio) {
                /**
                 * Set de tiempos reales para la jornada del operador
                 * seleccionado.
                 */
                prgSerconSelected.setRealTimeOrigin(horaIniTurno1Selected);
                prgSerconSelected.setRealTimeDestiny(horaFinTurno1Selected);
                prgSerconSelected.setRealHiniTurno2(horaIniTurno2Selected);
                prgSerconSelected.setRealHfinTurno2(horaFinTurno2Selected);
                prgSerconSelected.setRealHiniTurno3(horaIniTurno3Selected);
                prgSerconSelected.setRealHfinTurno3(horaFinTurno3Selected);
                prgSerconSelected.setIdPrgSerconMotivo(prgSerconMotivoJSF.getPrgSerconMotivo());
                prgSerconSelected.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
                prgSerconSelected.setUserGenera(user.getUsername());
                prgSerconSelected.setPrgModificada(1);

                prgSerconSelected.setObservaciones(observacion);
                if (UtilJornada.validarCriterioMinJornada(prgSerconSelected)) {
                    /**
                     * Datos para que la jornada quede para aprobar.
                     */
                    prgSerconSelected.setUserAutorizado("");
                    prgSerconSelected.setFechaAutoriza(null);
                    // (-1) estado Por Autorizar
                    prgSerconSelected.setAutorizado(-1);
                } else {
                    UtilJornada.cargarParametrosJar();
                    if (UtilJornada.tipoLiquidacion()) {
                        //calculo de jornada flexible para dominicales
                        if (MovilidadUtil.isSunday(prgSerconSelected.getFecha())) {
                            List<PrgSerconLiqUtil> preCagarHorasDominicales = getINSTANCEJS().preCagarHorasDominicales(prgSerconSelected.getFecha(),
                                    prgSerconSelected.getIdGopUnidadFuncional().getIdGopUnidadFuncional(),
                                    prgSerconSelected.getIdEmpleado().getIdEmpleado(), cargarObjetoParaJar(prgSerconSelected));
                            PrgSerconLiqUtil get = preCagarHorasDominicales.get(0);
                            setValueFromPrgSerconJar(get, prgSerconSelected);
                            prgSerconEJB.edit(prgSerconSelected);
                        } else {
                            //calculo de jornada flexible para días no dominicales
                            PrgSerconLiqUtil param = new PrgSerconLiqUtil();
                            param.setFecha(prgSerconSelected.getFecha());
                            param.setTimeOrigin(prgSerconSelected.getTimeOrigin());
                            param.setTimeDestiny(prgSerconSelected.getTimeDestiny());
                            param.setHiniTurno2(prgSerconSelected.getHiniTurno2());
                            param.setHfinTurno2(prgSerconSelected.getHfinTurno2());
                            param.setHiniTurno3(prgSerconSelected.getHiniTurno3());
                            param.setHfinTurno3(prgSerconSelected.getHfinTurno3());
                            param.setIdEmpleado(new EmpleadoLiqUtil(prgSerconSelected.getIdEmpleado().getIdEmpleado()));
                            param.setIdGopUnidadFuncional(new GopUnidadFuncionalLiqUtil(prgSerconSelected.getIdGopUnidadFuncional().getIdGopUnidadFuncional()));
                            param.setHfinTurno3(prgSerconSelected.getHfinTurno3());
                            param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(prgSerconSelected.getSercon(), prgSerconSelected.getWorkTime())));
                            ErrorPrgSercon errorPrgSercon
                                    = getINSTANCEJS().recalcularHrsExtrasSmnl(param);
                            List<PrgSerconLiqUtil> list = errorPrgSercon.getLista().stream()
                                    .filter(x -> !jornadaFechaDiferente(prgSerconSelected.getFecha(), x))
                                    .collect(Collectors.toList());
                            PrgSerconLiqUtil get = list.get(0);
                            setValueFromPrgSerconJar(get, prgSerconSelected);
                            prgSerconEJB.edit(prgSerconSelected);
                            list = errorPrgSercon.getLista().stream()
                                    .filter(x -> jornadaFechaDiferente(prgSerconSelected.getFecha(), x))
                                    .collect(Collectors.toList());
                            prgSerconEJB.updatePrgSerconFromList(list, 0);
                        }
                    } else {
                        prgSerconSelected.setUserAutorizado(user.getUsername());
                        prgSerconSelected.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                        // (1) uno para autorizar, (0) cero para no autorizar
                        prgSerconSelected.setAutorizado(1);
                        if (UtilJornada.esPorPartes(prgSerconSelected)) {
                            LiqJornadaJSFMB.cargarCalcularDatoPorPartes(prgSerconSelected);
                        } else {
                            LiqJornadaJSFMB.cargarCalcularDato(prgSerconSelected);
                        }
                    }
                }
                prgSerconEJB.edit(prgSerconSelected);
            }
        }
        if (prgSerconFound != null) {
            if (flagAjusteFound) {
                /**
                 * Set de tiempos reales para la jornada del operador
                 * consultado.
                 */
                prgSerconFound.setRealTimeOrigin(horaIniTurno1Found);
                prgSerconFound.setRealTimeDestiny(horaFinTurno1Found);
                prgSerconFound.setRealHiniTurno2(horaIniTurno2Found);
                prgSerconFound.setRealHfinTurno2(horaFinTurno2Found);
                prgSerconFound.setRealHiniTurno3(horaIniTurno3Found);
                prgSerconFound.setRealHfinTurno3(horaFinTurno3Found);
                prgSerconFound.setIdPrgSerconMotivo(prgSerconMotivoJSF.getPrgSerconMotivo());
                prgSerconFound.setPrgModificada(1);
                prgSerconFound.setObservaciones(observacion);
                prgSerconFound.setUserGenera(user.getUsername());
                prgSerconFound.setFechaGenera(MovilidadUtil.fechaCompletaHoy());

                if (UtilJornada.validarCriterioMinJornada(prgSerconFound)) {
                    /**
                     * Datos para que la jornada quede para aprobar.
                     */
                    prgSerconFound.setAutorizado(-1);
                    prgSerconFound.setUserAutorizado("");
                    prgSerconFound.setFechaAutoriza(null);
                } else {
                    prgSerconFound.setUserAutorizado(user.getUsername());
                    prgSerconFound.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                    // (1) uno para autorizar, (0) cero para no autorizar
                    prgSerconFound.setAutorizado(1);
                    if (UtilJornada.esPorPartes(prgSerconFound)) {
                        LiqJornadaJSFMB.cargarCalcularDatoPorPartes(prgSerconFound);
                    } else {
                        LiqJornadaJSFMB.cargarCalcularDato(prgSerconFound);
                    }
                }
                prgSerconEJB.edit(prgSerconFound);
            }
            recalcularDisponibilidad(listTareasDispFound, listPrgTcGestionOperador);
            for (PrgTc obj : listDispAjustados) {
                obj.setObservaciones(observacion);
                prgTcEJB.create(obj);
            }
            for (PrgTc obj : listTareasDispFound) {
                if (obj.getEstadoOperacion().equals(ConstantsUtil.CODE_ELIMINADO_DISP_9)) {
                    obj.setObservaciones(observacion);
                    prgTcEJB.edit(obj);
                }
            }
        }
    }

    void recalcularDisponibilidad(List<PrgTc> listDp, List<PrgTc> listServicios) {
        if (listDispAjustados == null) {
            listDispAjustados = new ArrayList<>();
        } else {
            listDispAjustados.clear();
        }
        listServicios.sort(SortPrgTcByTimeOrigin.getSortPrgTcByTimeOrigin());
        String dispoTimeOrigin = Jornada.hr_cero;
        String dispoTimeDestiny = Jornada.hr_cero;
        for (PrgTc disp : listDp) {
            dispoTimeOrigin = disp.getTimeOrigin();
            dispoTimeDestiny = disp.getTimeDestiny();
            System.out.println("dispoTimeOrigin-------------------------->" + dispoTimeOrigin);
            System.out.println("dispoTimeDestiny------------------------->" + dispoTimeDestiny);

            String timeOrigin = Jornada.hr_cero;
            String timeDestiny = Jornada.hr_cero;
            int ultimoPosicion = listServicios.size() - 1;
            for (PrgTc serv : listServicios) {
                System.out.println("serv.getTimeOrigin()----------------->" + serv.getTimeOrigin());
                System.out.println("serv.getTimeDestiny()---------------->" + serv.getTimeDestiny());

//                System.out.println("timeOrigin->" + serv.getTimeOrigin());
                if (MovilidadUtil.toSecs(serv.getTimeOrigin()) == MovilidadUtil.toSecs(dispoTimeOrigin)
                        && MovilidadUtil.toSecs(serv.getTimeDestiny()) == MovilidadUtil.toSecs(dispoTimeDestiny)) {
                    disp.setEstadoOperacion(ConstantsUtil.CODE_ELIMINADO_DISP_9);
                    System.out.println("IGUALES--------------------------");
                } else /**
                 * El tiempo de origen del servicio está dentro de la
                 * disponibilidad, pero el tiempo de destino esta fuera de la
                 * disponibilidad.
                 */
                if (MovilidadUtil.toSecs(serv.getTimeOrigin()) > MovilidadUtil.toSecs(dispoTimeOrigin)
                        && MovilidadUtil.toSecs(serv.getTimeDestiny()) > MovilidadUtil.toSecs(dispoTimeDestiny)
                        && MovilidadUtil.toSecs(serv.getTimeOrigin()) < MovilidadUtil.toSecs(dispoTimeDestiny)) {
//                    System.out.println("Caso-1->" + serv.getIdPrgTc() + "--" + disp.getIdPrgTc());
                    disp.setEstadoOperacion(ConstantsUtil.CODE_ELIMINADO_DISP_9);
                    int ultimoServicio = listServicios.indexOf(serv);
                    if (ultimoServicio == ultimoPosicion) {
                        listDispAjustados.add(crearPrgTcDisponibleConTarea(disp, dispoTimeOrigin, serv.getTimeOrigin()));
                    }

                    dispoTimeOrigin = serv.getTimeDestiny();
                    timeOrigin = serv.getTimeOrigin();
                    timeDestiny = serv.getTimeDestiny();
                    /**
                     * El tiempo de origen y destino del servicio estan dentro
                     * de la disponibilidad.
                     */
                } else if (MovilidadUtil.toSecs(serv.getTimeOrigin()) >= MovilidadUtil.toSecs(dispoTimeOrigin)
                        && MovilidadUtil.toSecs(serv.getTimeDestiny()) <= MovilidadUtil.toSecs(dispoTimeDestiny)) {
                    disp.setEstadoOperacion(ConstantsUtil.CODE_ELIMINADO_DISP_9);

                    if (MovilidadUtil.toSecs(serv.getTimeOrigin()) > MovilidadUtil.toSecs(dispoTimeOrigin)) {
//                        System.out.println("Caso-2->" + serv.getIdPrgTc() + "--" + disp.getIdPrgTc());
                        listDispAjustados.add(crearPrgTcDisponibleConTarea(disp, dispoTimeOrigin, serv.getTimeOrigin()));
                        dispoTimeOrigin = serv.getTimeDestiny();
                        int ultimoServicio = listServicios.indexOf(serv);
                        if (ultimoServicio == ultimoPosicion) {
                            listDispAjustados.add(crearPrgTcDisponibleConTarea(disp, serv.getTimeDestiny(), dispoTimeDestiny));
                        }
                    } else if (!timeDestiny.equals(serv.getTimeOrigin()) && !timeDestiny.equals(Jornada.hr_cero)) {
//                        System.out.println("Caso-3->" + serv.getIdPrgTc() + "--" + disp.getIdPrgTc());
                        listDispAjustados.add(crearPrgTcDisponibleConTarea(disp, timeDestiny, dispoTimeDestiny));
                        int ultimoServicio = listServicios.indexOf(serv);
                        if (ultimoServicio == ultimoPosicion) {
                            listDispAjustados.add(crearPrgTcDisponibleConTarea(disp, serv.getTimeDestiny(), dispoTimeDestiny));
                        }
                    } else {
                        int ultimoServicio = listServicios.indexOf(serv);
                        if (ultimoServicio == ultimoPosicion) {
                            listDispAjustados.add(crearPrgTcDisponibleConTarea(disp, serv.getTimeDestiny(), dispoTimeDestiny));
                        }
                    }
                    dispoTimeOrigin = serv.getTimeDestiny();
                    timeOrigin = serv.getTimeOrigin();
                    timeDestiny = serv.getTimeDestiny();
                    /**
                     * El tiempo de origen del servicio esta fuera de la
                     * disponibilidad pero el tiempo de destino esta dentro de
                     * la disponibilidad.
                     */
                } else if (MovilidadUtil.toSecs(serv.getTimeOrigin()) < MovilidadUtil.toSecs(dispoTimeOrigin)
                        && MovilidadUtil.toSecs(serv.getTimeDestiny()) < MovilidadUtil.toSecs(dispoTimeDestiny)
                        && MovilidadUtil.toSecs(serv.getTimeDestiny()) > MovilidadUtil.toSecs(dispoTimeOrigin)) {
                    disp.setEstadoOperacion(ConstantsUtil.CODE_ELIMINADO_DISP_9);
//                    System.out.println("Caso-4->" + serv.getIdPrgTc() + "--" + disp.getIdPrgTc());

                    int ultimoServicio = listServicios.indexOf(serv);
                    if (ultimoServicio == ultimoPosicion) {
                        listDispAjustados.add(crearPrgTcDisponibleConTarea(disp, serv.getTimeDestiny(), dispoTimeDestiny));
                    }
                    dispoTimeOrigin = serv.getTimeDestiny();
                    timeOrigin = serv.getTimeOrigin();
                    timeDestiny = serv.getTimeDestiny();
                }
            }
        }
//
//        listDispAjustados.stream().forEach(obj -> {
//            System.out.println("obj.getTimeOrigin()-->" + obj.getTimeOrigin());
//            System.out.println("obj.getTimeDestiny()->" + obj.getTimeDestiny());
//        });
    }

    void resetHorasFound() {
        horaIniTurno1Found = Jornada.hr_cero;
        horaFinTurno1Found = Jornada.hr_cero;
        horaIniTurno2Found = Jornada.hr_cero;
        horaFinTurno2Found = Jornada.hr_cero;
        horaIniTurno3Found = Jornada.hr_cero;
        horaFinTurno3Found = Jornada.hr_cero;
    }

    void validarMaxHorasExtrasSmanales() {
        flagRestringirMaxHrExtrasSmnal = false;
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        if (UtilJornada.tipoLiquidacion()) {//Flexible
            PrgSercon ps = new PrgSercon();
            ps.setRealTimeOrigin(horaIniTurno1Found);
            ps.setRealTimeDestiny(horaFinTurno1Found);
            ps.setRealHiniTurno2(horaIniTurno2Found);
            ps.setRealHfinTurno2(horaFinTurno2Found);
            ps.setRealHiniTurno3(horaIniTurno3Found);
            ps.setRealHfinTurno3(horaFinTurno3Found);
            ps.setWorkTime(prgSerconFound.getWorkTime());
            ps.setSercon(prgSerconFound.getSercon());
            ps.setFecha(prgSerconFound.getFecha());
            ps.setIdEmpleado(prgSerconFound.getIdEmpleado());
            ps.setIdGopUnidadFuncional(prgSerconFound.getIdGopUnidadFuncional());
            validarMaxHorasExtrasSmanalesFlexible(ps);

        } else {
            int difPart1 = MovilidadUtil.toSecs(horaFinTurno1Found) - MovilidadUtil.toSecs(horaIniTurno1Found);
            int difPart2 = MovilidadUtil.toSecs(horaFinTurno2Found) - MovilidadUtil.toSecs(horaIniTurno2Found);
            int difPart3 = MovilidadUtil.toSecs(horaFinTurno3Found) - MovilidadUtil.toSecs(horaIniTurno3Found);
            int totalProduccion = difPart1 + difPart2 + difPart3;
            int totalHorasExtras = (totalProduccion
                    - MovilidadUtil.toSecs(UtilJornada.getTotalHrsLaborales()));
            /**
             * Validar si el total de horas extras en mayor que el criterio
             * minimo para considerar horas extras.
             */
//        if (totalHorasExtras >= MovilidadUtil.toSecs(JornadaUtil.getCriterioMinHrsExtra())) {
            if ((MovilidadUtil.toSecs(totalHorasExtrasSmnal) + totalHorasExtras)
                    > MovilidadUtil.toSecs(UtilJornada.getMaxHrsExtrasSmnl())) {
                MovilidadUtil.addErrorMessage("Se excedió el máximo de horas extras semanales " + UtilJornada.getMaxHrsExtrasSmnl() + ". Encontrado "
                        + MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(totalHorasExtrasSmnal) + totalHorasExtras)));
                flagRestringirMaxHrExtrasSmnal = true;
            }
//        }
        }
    }

    /**
     *
     * @param horaFinTurno1Param
     * @param horaIniTurno2Param
     * @param horaFinTurno2Param
     * @param horaIniTurno3Param
     * @return
     */
    boolean validarTurnoCambioDia(String horaFinTurno1Param, String horaIniTurno2Param,
            String horaFinTurno2Param, String horaIniTurno3Param, Integer codigoTm) {
        if (MovilidadUtil.toSecs(horaIniTurno3Param) >= MovilidadUtil.toSecs(Jornada.fin_dia)) {
            if (MovilidadUtil.toSecs(horaIniTurno3Param) > MovilidadUtil.toSecs(horaFinTurno2Param)) {
                MovilidadUtil.addAdvertenciaMessage("No es posible ajustar esta jornada, Operado: " + codigoTm + ". Vaya a Control Jornada luego.");
                return true;
            }
        } else if (MovilidadUtil.toSecs(horaIniTurno2Param) >= MovilidadUtil.toSecs(Jornada.fin_dia)) {
            if (MovilidadUtil.toSecs(horaIniTurno2Param) > MovilidadUtil.toSecs(horaFinTurno1Param)) {
                MovilidadUtil.addAdvertenciaMessage("No es posible ajustar esta jornada, Operado: " + codigoTm + ". Vaya a Control Jornada luego.");
                return true;
            }
        }
        return false;
    }

    public boolean validarMaxHorasExtrasSmanalesFlexible() {
        if (flagAjusteFound) {
            /**
             * Set de tiempos reales para la jornada del operador consultado.
             */
            prgSerconFound.setRealTimeOrigin(horaIniTurno1Found);
            prgSerconFound.setRealTimeDestiny(horaFinTurno1Found);
            prgSerconFound.setRealHiniTurno2(horaIniTurno2Found);
            prgSerconFound.setRealHfinTurno2(horaFinTurno2Found);
            prgSerconFound.setRealHiniTurno3(horaIniTurno3Found);
            prgSerconFound.setRealHfinTurno3(horaFinTurno3Found);
        }
        return validarMaxHorasExtrasSmanalesFlexible(prgSerconFound);
    }

    boolean validarMaxHorasExtrasSmanalesFlexible(PrgSercon psLocal) {
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        ErrorPrgSercon validateMaxHrsSmnl = getINSTANCEJS().validateMaxHrsSmnl(psLocal.getFecha(),
                psLocal.getRealTimeOrigin(), psLocal.getRealTimeDestiny(),
                psLocal.getRealHiniTurno2(), psLocal.getRealHfinTurno2(),
                psLocal.getRealHiniTurno3(), psLocal.getRealHfinTurno3(),
                MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(psLocal.getSercon(), psLocal.getWorkTime())),
                psLocal.getIdEmpleado().getIdEmpleado(), psLocal.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        if (validateMaxHrsSmnl.isStatus()) {
            String msg = "Se excedió el máximo de horas extras semanales " + UtilJornada.getMaxHrsExtrasSmnl() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsSmnl.getHora());

            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasSmnlObj();
            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage(msg);
                flagRestringirMaxHrExtrasSmnal = true;
            } else {
                MovilidadUtil.addAdvertenciaMessage(msg);
            }
            return true;
        }
        return false;
    }

    public boolean validarHorasExtrasFlexible() {
        if (flagAjusteFound) {
            /**
             * Set de tiempos reales para la jornada del operador consultado.
             */
            prgSerconFound.setRealTimeOrigin(horaIniTurno1Found);
            prgSerconFound.setRealTimeDestiny(horaFinTurno1Found);
            prgSerconFound.setRealHiniTurno2(horaIniTurno2Found);
            prgSerconFound.setRealHfinTurno2(horaFinTurno2Found);
            prgSerconFound.setRealHiniTurno3(horaIniTurno3Found);
            prgSerconFound.setRealHfinTurno3(horaFinTurno3Found);
        }
        return validarHorasExtrasFlexible(prgSerconFound);
    }

    boolean validarHorasExtrasFlexible(PrgSercon psLocal) {
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        PrgSerconLiqUtil param = new PrgSerconLiqUtil();
        param.setFecha(psLocal.getFecha());
        param.setTimeOrigin(psLocal.getRealTimeOrigin());
        param.setTimeDestiny(psLocal.getRealTimeDestiny());
        param.setHiniTurno2(psLocal.getRealHiniTurno2());
        param.setHfinTurno2(psLocal.getRealHfinTurno2());
        param.setHiniTurno3(psLocal.getRealHiniTurno3());
        param.setHfinTurno3(psLocal.getRealHfinTurno3());
        param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(psLocal.getSercon(), psLocal.getWorkTime())));
        ErrorPrgSercon validateMaxHrsDia = getINSTANCEJS().validateMaxHrsDia(param);
        System.out.println("PASÓ POR ACÁ----------" + validateMaxHrsDia.isStatus());
        if (validateMaxHrsDia.isStatus()) {
            String msg = "Se excedió el máximo de horas extras diarias" + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsDia.getHora());
            System.out.println("msg" + msg);
            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage(msg);
                flagRestringirMaxHorasExtras = true;
            } else {
                MovilidadUtil.addAdvertenciaMessage(msg);
            }
            return true;
        }
        return false;
    }

    private void setValueFromPrgSerconJar(PrgSerconLiqUtil param1, PrgSercon param2) {
        param2.setDiurnas(param1.getDiurnas());
        param2.setNocturnas(param1.getNocturnas());
        param2.setExtraDiurna(param1.getExtraDiurna());
        param2.setExtraNocturna(param1.getExtraNocturna());
        param2.setFestivoDiurno(param1.getFestivoDiurno());
        param2.setFestivoNocturno(param1.getFestivoNocturno());
        param2.setFestivoExtraDiurno(param1.getFestivoExtraDiurno());
        param2.setFestivoExtraNocturno(param1.getFestivoExtraNocturno());
        param2.setDominicalCompDiurnas(param1.getDominicalCompDiurnas());
        param2.setDominicalCompNocturnas(param1.getDominicalCompNocturnas());
    }

    private PrgSerconLiqUtil cargarObjetoParaJar(PrgSercon ps) {
        String timeOrigin1 = ps.getTimeOrigin();
        String timeDestiny1 = ps.getTimeDestiny();
        String timeOrigin2 = ps.getHiniTurno2();
        String timeDestiny2 = ps.getHfinTurno2();
        String timeOrigin3 = ps.getHiniTurno3();
        String timeDestiny3 = ps.getHfinTurno3();
        if (ps.getAutorizado() != null && ps.getAutorizado() == 1) {
            timeOrigin1 = ps.getRealTimeOrigin();
            timeDestiny1 = ps.getRealTimeDestiny();
            timeOrigin2 = ps.getRealHiniTurno2();
            timeDestiny2 = ps.getRealHfinTurno2();
            timeOrigin3 = ps.getRealHiniTurno3();
            timeDestiny3 = ps.getRealHfinTurno3();
        }
        return new PrgSerconLiqUtil(timeOrigin1,
                timeDestiny1,
                timeOrigin2,
                timeDestiny2,
                timeOrigin3,
                timeDestiny3);
    }

    private boolean jornadaFechaDiferente(Date fecha, PrgSerconLiqUtil param) {
        return com.aja.jornada.util.Util.dateFormat(fecha) != com.aja.jornada.util.Util.dateFormat(param.getFecha());
    }

    public String getHoraIniTurno1Selected() {
        return horaIniTurno1Selected;
    }

    public void setHoraIniTurno1Selected(String horaIniTurno1Selected) {
        this.horaIniTurno1Selected = horaIniTurno1Selected;
    }

    public String getHoraFinTurno1Selected() {
        return horaFinTurno1Selected;
    }

    public void setHoraFinTurno1Selected(String horaFinTurno1Selected) {
        this.horaFinTurno1Selected = horaFinTurno1Selected;
    }

    public String getHoraIniTurno2Selected() {
        return horaIniTurno2Selected;
    }

    public void setHoraIniTurno2Selected(String horaIniTurno2Selected) {
        this.horaIniTurno2Selected = horaIniTurno2Selected;
    }

    public String getHoraFinTurno2Selected() {
        return horaFinTurno2Selected;
    }

    public void setHoraFinTurno2Selected(String horaFinTurno2Selected) {
        this.horaFinTurno2Selected = horaFinTurno2Selected;
    }

    public String getHoraIniTurno3Selected() {
        return horaIniTurno3Selected;
    }

    public void setHoraIniTurno3Selected(String horaIniTurno3Selected) {
        this.horaIniTurno3Selected = horaIniTurno3Selected;
    }

    public String getHoraFinTurno3Selected() {
        return horaFinTurno3Selected;
    }

    public void setHoraFinTurno3Selected(String horaFinTurno3Selected) {
        this.horaFinTurno3Selected = horaFinTurno3Selected;
    }

    public String getHoraIniTurno1Found() {
        return horaIniTurno1Found;
    }

    public void setHoraIniTurno1Found(String horaIniTurno1Found) {
        this.horaIniTurno1Found = horaIniTurno1Found;
    }

    public String getHoraFinTurno1Found() {
        return horaFinTurno1Found;
    }

    public void setHoraFinTurno1Found(String horaFinTurno1Found) {
        this.horaFinTurno1Found = horaFinTurno1Found;
    }

    public String getHoraIniTurno2Found() {
        return horaIniTurno2Found;
    }

    public void setHoraIniTurno2Found(String horaIniTurno2Found) {
        this.horaIniTurno2Found = horaIniTurno2Found;
    }

    public String getHoraFinTurno2Found() {
        return horaFinTurno2Found;
    }

    public void setHoraFinTurno2Found(String horaFinTurno2Found) {
        this.horaFinTurno2Found = horaFinTurno2Found;
    }

    public String getHoraIniTurno3Found() {
        return horaIniTurno3Found;
    }

    public void setHoraIniTurno3Found(String horaIniTurno3Found) {
        this.horaIniTurno3Found = horaIniTurno3Found;
    }

    public String getHoraFinTurno3Found() {
        return horaFinTurno3Found;
    }

    public void setHoraFinTurno3Found(String horaFinTurno3Found) {
        this.horaFinTurno3Found = horaFinTurno3Found;
    }

    public boolean isFlagRestringirMaxHorasExtras() {
        return flagRestringirMaxHorasExtras;
    }

    public void setFlagRestringirMaxHorasExtras(boolean flagRestringirMaxHorasExtras) {
        this.flagRestringirMaxHorasExtras = flagRestringirMaxHorasExtras;
    }

    public List<PrgTc> getListServiciosOpSelected() {
        return listServiciosOpSelected;
    }

    public void setListServiciosOpSelected(List<PrgTc> listServiciosOpSelected) {
        this.listServiciosOpSelected = listServiciosOpSelected;
    }

    public List<PrgTc> getListDesasignarOpServNull() {
        return listDesasignarOpServNull;
    }

    public void setListDesasignarOpServNull(List<PrgTc> listDesasignarOpServNull) {
        this.listDesasignarOpServNull = listDesasignarOpServNull;
    }

    public List<PrgTc> getListPrgTcGestionOperador() {
        return listPrgTcGestionOperador;
    }

    public void setListPrgTcGestionOperador(List<PrgTc> listPrgTcGestionOperador) {
        this.listPrgTcGestionOperador = listPrgTcGestionOperador;
    }

    public Integer getIdEmpleadoSelected() {
        return idEmpleadoSelected;
    }

    public void setIdEmpleadoSelected(Integer idEmpleadoSelected) {
        this.idEmpleadoSelected = idEmpleadoSelected;
    }

    public Integer getIdEmpleadoFound() {
        return idEmpleadoFound;
    }

    public void setIdEmpleadoFound(Integer idEmpleadoFound) {
        this.idEmpleadoFound = idEmpleadoFound;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isDesasignarOpeServNull() {
        return desasignarOpeServNull;
    }

    public void setDesasignarOpeServNull(boolean desasignarOpeServNull) {
        this.desasignarOpeServNull = desasignarOpeServNull;
    }

    public boolean isDisponibleAlCambio() {
        return disponibleAlCambio;
    }

    public void setDisponibleAlCambio(boolean disponibleAlCambio) {
        this.disponibleAlCambio = disponibleAlCambio;
    }

    public List<PrgTc> getListServiciosOpFound() {
        return listServiciosOpFound;
    }

    public void setListServiciosOpFound(List<PrgTc> listServiciosOpFound) {
        this.listServiciosOpFound = listServiciosOpFound;
    }

    public PrgSercon getPrgSerconSelected() {
        return prgSerconSelected;
    }

    public void setPrgSerconSelected(PrgSercon prgSerconSelected) {
        this.prgSerconSelected = prgSerconSelected;
    }

    public PrgSercon getPrgSerconFound() {
        return prgSerconFound;
    }

    public void setPrgSerconFound(PrgSercon prgSerconFound) {
        this.prgSerconFound = prgSerconFound;
    }

    public boolean isFlagRestringirMaxTurnoSelected() {
        return flagRestringirMaxTurnoSelected;
    }

    public void setFlagRestringirMaxTurnoSelected(boolean flagRestringirMaxTurnoSelected) {
        this.flagRestringirMaxTurnoSelected = flagRestringirMaxTurnoSelected;
    }

    public boolean isFlagRestringirMaxTurnoFound() {
        return flagRestringirMaxTurnoFound;
    }

    public void setFlagRestringirMaxTurnoFound(boolean flagRestringirMaxTurnoFound) {
        this.flagRestringirMaxTurnoFound = flagRestringirMaxTurnoFound;
    }

    public List<PrgTc> getListTareasDispFound() {
        return listTareasDispFound;
    }

    public void setListTareasDispFound(List<PrgTc> listTareasDispFound) {
        this.listTareasDispFound = listTareasDispFound;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isFlagRestringirMaxHrExtrasSmnal() {
        return flagRestringirMaxHrExtrasSmnal;
    }

    public void setFlagRestringirMaxHrExtrasSmnal(boolean flagRestringirMaxHrExtrasSmnal) {
        this.flagRestringirMaxHrExtrasSmnal = flagRestringirMaxHrExtrasSmnal;
    }

    public boolean isFlagAjusteFound() {
        return flagAjusteFound;
    }

    public void setFlagAjusteFound(boolean flagAjusteFound) {
        this.flagAjusteFound = flagAjusteFound;
    }

    public boolean isFlagAjusteSelected() {
        return flagAjusteSelected;
    }

    public void setFlagAjusteSelected(boolean flagAjusteSelected) {
        this.flagAjusteSelected = flagAjusteSelected;
    }

    public PrgSerconMotivoJSF getPrgSerconMotivoJSF() {
        return prgSerconMotivoJSF;
    }

    public void setPrgSerconMotivoJSF(PrgSerconMotivoJSF prgSerconMotivoJSF) {
        this.prgSerconMotivoJSF = prgSerconMotivoJSF;
    }

    public boolean isDisponibleContingencia() {
        return disponibleContingencia;
    }

    public void setDisponibleContingencia(boolean disponibleContingencia) {
        this.disponibleContingencia = disponibleContingencia;
    }

}
