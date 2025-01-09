/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.aja.jornada.controller.calculator;
import static com.aja.jornada.util.JornadaUtil.calcular;
import com.movilidad.ejb.GenericaJornadaDetFacadeLocal;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaDet;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.ParamFeriado;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.UtilJornada;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import liquidadorjornada.Jornada;
import org.springframework.security.core.context.SecurityContextHolder;
import com.aja.jornada.util.ConfigJornada;

/**
 *
 * @author solucionesit
 */
@Named(value = "liqGenJornadaBean")
@ViewScoped
public class LiquidarGenericaJornadaBean implements Serializable {

    /**
     * Creates a new instance of LiquidarGenericaJornadaBean
     */
    public LiquidarGenericaJornadaBean() {
    }

    @EJB
    private GenericaJornadaFacadeLocal genJornadaEJB;
    @EJB
    private GenericaJornadaDetFacadeLocal genJornadaDetEJB;

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEJB;

    @Inject
    private CalcularMasivoJSFManagedBean calcularMasivoBean;

    private List<GenericaJornadaDet> listGenJornadaDet;
    private String diurnas = Jornada.hr_cero;
    private String nocturnas = Jornada.hr_cero;
    private String extra_diurna = Jornada.hr_cero;
    private String extra_nocturna = Jornada.hr_cero;
    private String festivo_diurno = Jornada.hr_cero;
    private String festivo_nocturno = Jornada.hr_cero;
    private String festivo_extra_diurno = Jornada.hr_cero;
    private String festivo_extra_nocturno = Jornada.hr_cero;
    private String dominical_comp_diurno = Jornada.hr_cero;
    private String dominical_comp_nocturno = Jornada.hr_cero;
    private String dominical_comp_extra_diurno = Jornada.hr_cero;
    private String dominical_comp_extra_nocturno = Jornada.hr_cero;
    private String horasMitadJornada = "04:00:00";//si se desea ser exacto este valor debería ser la mitad del valor parametrizado como tiempo jornada, es decir, horasJornada
    private Map<String, ParamReporteHoras> mapaParamReporteHoras;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private ParamAreaUsr pau;

    public void liquidar() {
        liquidarTransactional();
        calcularMasivoBean.cargarDatos();
        MovilidadUtil.updateComponent("formLiqjornada:serconlistLiq");
    }

    public boolean jornadaFlexible() {
        return pau == null ? false : pau.getIdParamArea() == null
                ? false : pau.getIdParamArea().getJornadaFlexible() == null ? false
                : pau.getIdParamArea().getJornadaFlexible().equals(1);
    }

    /*
     * Método encargado de liquidar por rango de fechas, para las jornada que
     * aplique se le setteará un '1' en el campo liquidado.
     */
//    @Transactional
    private void liquidarTransactional() {
        try {
            if (!jornadaFlexible()) {
                List<GenericaJornada> listJornada = genJornadaEJB.getByDate(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta(), pau.getIdParamArea().getIdParamArea());
                listGenJornadaDet = getListGenJornadaDet();
                calcularMasivoBean.cargarMapParamFeriado();
                calcularMasivoBean.cargarMapaTipoJornada();
                getMapaParamReporteHoras();
                this.prgSerconDetalleBuild(listJornada, user.getUsername());
            }

            genJornadaEJB.liquidarPorRangoFecha(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta(), user.getUsername(), pau.getIdParamArea().getIdParamArea());
            MovilidadUtil.addSuccessMessage("Jornada del " + Util.dateFormat(calcularMasivoBean.getFechaDesde()) + " hasta " + Util.dateFormat(calcularMasivoBean.getFechaHasta()) + " Liquidada Exitosamente");

//            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error inesperado al liquidar jornada, contacte al administrador");
        }
    }

    public void prgSerconDetalleBuild(List<GenericaJornada> list, String username) {
        list.forEach((GenericaJornada jornada) -> {
            salvarLiquidacionOriginal(jornada);
            String timeOrigin;
            String timeDestiny;
            boolean realTime = calcularMasivoBean.isRealTime(jornada.getAutorizado(), jornada.getPrgModificada(), jornada.getRealTimeOrigin());

            if (realTime) {
                timeOrigin = jornada.getRealTimeOrigin();
                timeDestiny = jornada.getRealTimeDestiny();
            } else {
                timeOrigin = jornada.getTimeOrigin();
                timeDestiny = jornada.getTimeDestiny();
            }
            GenericaJornadaTipo gjt = calcularMasivoBean.getMapJornadaTipo().get(timeOrigin + "_" + timeDestiny);
            jornada.setIdGenericaJornadaTipo(gjt);
            /**
             * Validar si la jornada es por parte
             */
            boolean flagTipoCalculo = jornada.getIdGenericaJornadaTipo() != null && jornada.getIdGenericaJornadaTipo().getTipoCalculo() == 1;
            if (flagTipoCalculo) {
//                System.out.println("Jornada por partes" + obj.getIdPrgSercon());
                cargarCalcularDatoPorPartes(jornada);
            } else {
//                System.out.println("Jornada No por partes" + obj.getIdPrgSercon());
                cargarCalcularDato(jornada);
                generarDetallesJornada(jornada);
            }
        });
        genJornadaDetEJB.createList(this.listGenJornadaDet);

    }

    private void salvarLiquidacionOriginal(GenericaJornada ps) {
        diurnas = ps.getDiurnas();
        nocturnas = ps.getNocturnas();
        extra_diurna = ps.getExtraDiurna();
        extra_nocturna = ps.getExtraNocturna();
        festivo_diurno = ps.getFestivoDiurno();
        festivo_nocturno = ps.getFestivoNocturno();
        festivo_extra_diurno = ps.getFestivoExtraDiurno();
        festivo_extra_nocturno = ps.getFestivoExtraNocturno();
        dominical_comp_diurno = ps.getDominicalCompDiurnas();
        dominical_comp_nocturno = ps.getDominicalCompNocturnas();
        dominical_comp_extra_diurno = ps.getDominicalCompDiurnaExtra();
        dominical_comp_extra_nocturno = ps.getDominicalCompNocturnaExtra();
    }

    public Map getMapaParamReporteHoras() {
        if (mapaParamReporteHoras == null) {
            List<ParamReporteHoras> listParamHoras = paramReporteHorasEJB.findAllActivos(0);
            mapaParamReporteHoras = new HashMap<>();
            //<editor-fold defaultstate="collapsed" desc="iteración lista ParamReporteHoras, para cargar Mapa.">
            listParamHoras.forEach((ph) -> {
                if (ph.getTipoHora().equals(Util.RPH_DIURNAS)) {
                    mapaParamReporteHoras.put(Util.RPH_DIURNAS, ph);
                }
                //Corresponde a recargo nocturno
                if (ph.getTipoHora().equals(Util.RPH_NOCTURNAS)) {
                    mapaParamReporteHoras.put(Util.RPH_NOCTURNAS, ph);
                }
                //Corresponde a recargo extra diurno
                if (ph.getTipoHora().equals(Util.RPH_EXTRA_DIURNA)) {
                    mapaParamReporteHoras.put(Util.RPH_EXTRA_DIURNA, ph);
                }
                //Corresponde a recargo extra nocturno
                if (ph.getTipoHora().equals(Util.RPH_EXTRA_NOCTURNA)) {
                    mapaParamReporteHoras.put(Util.RPH_EXTRA_NOCTURNA, ph);
                }
                //Corresponde a recargo festivo diurno
                if (ph.getTipoHora().equals(Util.RPH_FESTIVO_DIURNO)) {
                    mapaParamReporteHoras.put(Util.RPH_FESTIVO_DIURNO, ph);
                }
                //Corresponde a recargo festivo nocturno
                if (ph.getTipoHora().equals(Util.RPH_FESTIVO_NOCTURNO)) {
                    mapaParamReporteHoras.put(Util.RPH_FESTIVO_NOCTURNO, ph);
                }
                //Corresponde a recargo festivo extra diurno
                if (ph.getTipoHora().equals(Util.RPH_FESTIVO_EXTRA_DIURNO)) {
                    mapaParamReporteHoras.put(Util.RPH_FESTIVO_EXTRA_DIURNO, ph);
                }
                //Corresponde a recargo festivo extra nocturno
                if (ph.getTipoHora().equals(Util.RPH_FESTIVO_EXTRA_NOCTURNO)) {
                    mapaParamReporteHoras.put(Util.RPH_FESTIVO_EXTRA_NOCTURNO, ph);
                }
                //Corresponde a recargo festivo diurno con compensatorio dominical
                if (ph.getTipoHora().equals(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)) {
                    mapaParamReporteHoras.put(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO, ph);
                }
                //Corresponde a recargo festivo nocturno con compensatorio dominical
                if (ph.getTipoHora().equals(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)) {
                    mapaParamReporteHoras.put(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO, ph);
                }
                //Corresponde a recargo festivo extra diurno con compensatorio dominical
                if (ph.getTipoHora().equals(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO)) {
                    mapaParamReporteHoras.put(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO, ph);
                }
                //Corresponde a recargo festivo extra nocturno con compensatorio dominical
                if (ph.getTipoHora().equals(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO)) {
                    mapaParamReporteHoras.put(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO, ph);
                }
            });
            //</editor-fold>

        }
        return mapaParamReporteHoras;
    }

    public List<GenericaJornadaDet> getListGenJornadaDet() {
        if (listGenJornadaDet == null) {
            listGenJornadaDet = new ArrayList<>();
        }
        return listGenJornadaDet;
    }

    @Transactional
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
        onePart = calcularMasivoBean.caluleOnePart(timeOrigin1, timeDestiny1, gj.getFecha());
        twoPart = calcularMasivoBean.caluleOnePart(timeOrigin2, timeDestiny2, gj.getFecha());

        calcularMasivoBean.totalHorasAsignadas = "00:00:00";
        gj.setDiurnas(Jornada.hr_cero);
        gj.setNocturnas(Jornada.hr_cero);
        gj.setExtraDiurna(Jornada.hr_cero);
        gj.setExtraNocturna(Jornada.hr_cero);
        gj.setFestivoDiurno(Jornada.hr_cero);
        gj.setFestivoNocturno(Jornada.hr_cero);
        gj.setFestivoExtraDiurno(Jornada.hr_cero);
        gj.setFestivoExtraNocturno(Jornada.hr_cero);
        sumarPorPartes(gj, onePart, timeOrigin1, timeDestiny1, false);
        sumarPorPartes(gj, twoPart, timeOrigin2, timeDestiny2, MovilidadUtil.toSecs(timeOrigin2) > MovilidadUtil.toSecs(Jornada.fin_dia));

        return gj;
    }

    public GenericaJornada sumarPorPartes(GenericaJornada gj, GenericaJornada gjModificado, String horaIniParam, String horaFinParam, boolean diaPaOtro) {
        int parteHorasLaborales;
        int parteHorasExtra = 0;
        int totalAsignar;
        int maxHorasLaborales = MovilidadUtil.toSecs(UtilJornada.getTotalHrsLaborales());
        if (diaPaOtro) {
            totalAsignar = MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getNocturnas());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;

                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_EXTRA_NOCTURNA),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraNocturna()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas);
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                    gj.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getNocturnas()) + parteHorasLaborales));
                    calcularMasivoBean.totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
                }
            } else {
                calcularMasivoBean.totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getNocturnas()));
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getNocturnas()) + MovilidadUtil.toSecs(gjModificado.getNocturnas())));
            }
            totalAsignar = MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas)
                    + MovilidadUtil.toSecs(gjModificado.getDiurnas());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_EXTRA_DIURNA),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraDiurna()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas);
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                    gj.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDiurnas()) + parteHorasLaborales));
                    calcularMasivoBean.totalHorasAsignadas = UtilJornada.getTotalHrsLaborales();
                }
            } else {
                calcularMasivoBean.totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getDiurnas()));
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDiurnas()) + MovilidadUtil.toSecs(gjModificado.getDiurnas())));
            }
            totalAsignar = MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar
                        - maxHorasLaborales;
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas);
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                    gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) + parteHorasLaborales));
                    calcularMasivoBean.totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
                }
            } else {
                calcularMasivoBean.totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno()));
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno())));
            }

            totalAsignar = MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas);
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                    gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) + parteHorasLaborales));
                    calcularMasivoBean.totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
                }
            } else {
                calcularMasivoBean.totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno()));
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno())));
            }

            gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(gjModificado.getFestivoExtraNocturno())));
            if (MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) > 0) {
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
            }
            gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(gjModificado.getFestivoExtraDiurno())));
            if (MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) > 0) {
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
            }
            gj.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraNocturna()) + MovilidadUtil.toSecs(gjModificado.getExtraNocturna())));
            if (MovilidadUtil.toSecs(gj.getExtraNocturna()) > 0) {
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_EXTRA_NOCTURNA),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
            }
            gj.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraDiurna()) + MovilidadUtil.toSecs(gjModificado.getExtraDiurna())));
            if (MovilidadUtil.toSecs(gj.getExtraDiurna()) > 0) {
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_EXTRA_DIURNA),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
            }

        } else {
            totalAsignar = MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas)
                    + MovilidadUtil.toSecs(gjModificado.getDiurnas());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_EXTRA_DIURNA),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraDiurna()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas);
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                    gj.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDiurnas()) + parteHorasLaborales));
                    calcularMasivoBean.totalHorasAsignadas = UtilJornada.getTotalHrsLaborales();
                }
            } else {
                calcularMasivoBean.totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getDiurnas()));
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getDiurnas()) + MovilidadUtil.toSecs(gjModificado.getDiurnas())));
            }
            totalAsignar = MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getNocturnas());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                if (MovilidadUtil.toSecs(gj.getExtraNocturna()) > 0) {
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_EXTRA_NOCTURNA),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                }
                gj.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraNocturna()) + parteHorasExtra));

                if (MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas);
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                    gj.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getNocturnas()) + parteHorasLaborales));
                    calcularMasivoBean.totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
                }
            } else {
                calcularMasivoBean.totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getNocturnas()));
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getNocturnas()) + MovilidadUtil.toSecs(gjModificado.getNocturnas())));
            }

            totalAsignar = MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar - maxHorasLaborales;
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas);
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                    gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) + parteHorasLaborales));
                    calcularMasivoBean.totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
                }
            } else {
                calcularMasivoBean.totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno()));
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoDiurno()) + MovilidadUtil.toSecs(gjModificado.getFestivoDiurno())));
            }

            totalAsignar = MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno());

            if (totalAsignar > maxHorasLaborales) {
                parteHorasExtra = totalAsignar
                        - maxHorasLaborales;
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) + parteHorasExtra));
                if (MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) < maxHorasLaborales) {
                    parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas);
                    listGenJornadaDet.add(
                            crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                                    gj,
                                    MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                    horaFinParam
                            )
                    );
                    gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) + parteHorasLaborales));
                    calcularMasivoBean.totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
                }
            } else {
                calcularMasivoBean.totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(calcularMasivoBean.totalHorasAsignadas) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno()));
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
                gj.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoNocturno()) + MovilidadUtil.toSecs(gjModificado.getFestivoNocturno())));
            }

            gj.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(gjModificado.getFestivoExtraDiurno())));
            if (MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) > 0) {
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
            }
            gj.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(gjModificado.getFestivoExtraNocturno())));
            if (MovilidadUtil.toSecs(gj.getFestivoExtraNocturno()) > 0) {
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
            }
            gj.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraDiurna()) + MovilidadUtil.toSecs(gjModificado.getExtraDiurna())));
            if (MovilidadUtil.toSecs(gj.getExtraDiurna()) > 0) {
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_EXTRA_DIURNA),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
            }
            gj.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(gj.getExtraNocturna()) + MovilidadUtil.toSecs(gjModificado.getExtraNocturna())));
            if (MovilidadUtil.toSecs(gj.getExtraNocturna()) > 0) {
                listGenJornadaDet.add(
                        crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_EXTRA_NOCTURNA),
                                gj,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                                horaFinParam
                        )
                );
            }
        }
        return gj;
    }

    private void generarDetallesJornada(GenericaJornada genJornadaParam) {
        //caso particular de una "jornada oficina", revisar al detalle el caso, dado que se cambia la liquidación 
        //<editor-fold defaultstate="collapsed" desc="Aquí se recorre la lista de parametro de reporte de horas.">
        //Corresponde a recargo diurno
        if (hayJornada(Jornada.getDiurnas_time_origin(),
                Jornada.getDiurnas_time_destiny())) {
//            if (Jornada.getHoraIniParam().equals("08:00:00")
//                    && Jornada.getHoraFinParam().equals("17:00:00")) {
//                listGenJornadaDet.add(
//                        crearDetalleJornda(
//                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
//                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
//                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
//                                genJornadaParam,
//                                "08:00:00",
//                                "12:00:00"
//                        )
//                );
//                listGenJornadaDet.add(
//                        crearDetalleJornda(
//                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
//                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
//                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
//                                genJornadaParam,
//                                "13:00:00",
//                                "17:00:00"
//                        )
//                );
//            } else {
                listGenJornadaDet.add(
                        crearDetalleJornda(
                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                                genJornadaParam,
                                Jornada.getDiurnas_time_origin(),
                                Jornada.getDiurnas_time_destiny()
                        )
                );
//            }
        }
        //Corresponde a recargo nocturno
        if (hayJornada(Jornada.getNocturnas_time_origin(),
                Jornada.getNocturnas_time_destiny())) {
            listGenJornadaDet.add(
                    crearDetalleJornda(
                            MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                            genJornadaParam,
                            Jornada.getNocturnas_time_origin(),
                            Jornada.getNocturnas_time_destiny()
                    )
            );
        }
        //Corresponde a recargo extra diurno
        if (hayJornada(Jornada.getExtra_diurna_time_origin(),
                Jornada.getExtra_diurna_time_destiny())) {
            listGenJornadaDet.add(
                    crearDetalleJornda(
                            MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_EXTRA_DIURNA),
                            genJornadaParam,
                            Jornada.getExtra_diurna_time_origin(),
                            Jornada.getExtra_diurna_time_destiny()
                    )
            );
        }
        //Corresponde a recargo extra nocturno
        if (hayJornada(Jornada.getExtra_nocturna_time_origin(),
                Jornada.getExtra_nocturna_time_destiny())) {
            listGenJornadaDet.add(
                    crearDetalleJornda(
                            MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_EXTRA_NOCTURNA),
                            genJornadaParam,
                            Jornada.getExtra_nocturna_time_origin(),
                            Jornada.getExtra_nocturna_time_destiny()
                    )
            );
        }
        //Corresponde a recargo festivo diurno
        if (hayJornada(Jornada.getFestivo_diurno_time_origin(),
                Jornada.getFestivo_diurno_time_destiny())) {
//            if (Jornada.getHoraIniParam().equals("08:00:00")
//                    && Jornada.getHoraFinParam().equals("17:00:00")) {
//                listGenJornadaDet.add(
//                        crearDetalleJornda(
//                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
//                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
//                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
//                                genJornadaParam,
//                                "08:00:00",
//                                "12:00:00"
//                        )
//                );
//                listGenJornadaDet.add(
//                        crearDetalleJornda(
//                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
//                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
//                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
//                                genJornadaParam,
//                                "13:00:00",
//                                "17:00:00"
//                        )
//                );
//            } else {
                listGenJornadaDet.add(
                        crearDetalleJornda(
                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                                genJornadaParam,
                                Jornada.getFestivo_diurno_time_origin(),
                                Jornada.getFestivo_diurno_time_destiny()
                        )
                );
//            }
        }
        //Corresponde a recargo festivo nocturno
        if (hayJornada(Jornada.getFestivo_nocturno_time_origin(),
                Jornada.getFestivo_nocturno_time_destiny())) {
            listGenJornadaDet.add(
                    crearDetalleJornda(
                            MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                            genJornadaParam,
                            Jornada.getFestivo_nocturno_time_origin(),
                            Jornada.getFestivo_nocturno_time_destiny()
                    )
            );
        }
        //Corresponde a recargo festivo extra diurno
        if (hayJornada(Jornada.getFestivo_extra_diurno_time_origin(),
                Jornada.getFestivo_extra_diurno_time_destiny())) {
            listGenJornadaDet.add(
                    crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_diurno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO),
                            genJornadaParam,
                            Jornada.getFestivo_extra_diurno_time_origin(),
                            Jornada.getFestivo_extra_diurno_time_destiny()
                    )
            );
        }
        //Corresponde a recargo festivo extra nocturno
        if (hayJornada(Jornada.getFestivo_extra_nocturno_time_origin(),
                Jornada.getFestivo_extra_nocturno_time_destiny())) {
            listGenJornadaDet.add(
                    crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_extra_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO),
                            genJornadaParam,
                            Jornada.getFestivo_extra_nocturno_time_origin(),
                            Jornada.getFestivo_extra_nocturno_time_destiny()
                    )
            );
        }
        //</editor-fold>

    }

    private boolean hayJornada(String horaIni, String horaFin) {
        return MovilidadUtil.toSecs(horaIni) + MovilidadUtil.toSecs(horaFin) > 0;
    }

    private GenericaJornadaDet crearDetalleJornda(ParamReporteHoras paramReporte,
            GenericaJornada objParam, String horaIni, String horaFin) {
        GenericaJornadaDet obj = new GenericaJornadaDet();
        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
        obj.setEstadoReg(0);
        obj.setUsername(user.getUsername());
        obj.setIdParamReporte(paramReporte);
        obj.setIdGenericaJornada(objParam);
        obj.setTimeorigin(horaIni);
        obj.setTimedestiny(horaFin);
        String cantidad = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                - MovilidadUtil.toSecs(horaIni));
        obj.setCantidad(cantidad);

        return obj;
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
     * @return
     */
    @Transactional
    public GenericaJornada cargarCalcularDato(GenericaJornada jornada) {
        Jornada.reset();
        int ultimaHoraDia = MovilidadUtil.toSecs("24:00:00");
        int horaIniSec;
        int horaFinSec;
        String timeOrigin;
        String timeDestiny;
        boolean realTime = calcularMasivoBean.isRealTime(jornada.getAutorizado(), jornada.getPrgModificada(), jornada.getRealTimeOrigin());
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

        if (timeOrigin != null && timeDestiny != null) {
            if (timeOrigin.equals("00:00:00") && timeDestiny.equals("00:00:00")) {
                jornada.setDiurnas(calculator.getDiurnas());
                jornada.setNocturnas(calculator.getNocturnas());
                jornada.setExtraDiurna(calculator.getExtra_diurna());
                jornada.setExtraNocturna(calculator.getExtra_nocturna());
                jornada.setFestivoDiurno(calculator.getFestivo_diurno());
                jornada.setFestivoNocturno(calculator.getFestivo_nocturno());
                jornada.setFestivoExtraDiurno(calculator.getFestivo_extra_diurno());
                jornada.setFestivoExtraNocturno(calculator.getFestivo_extra_nocturno());
                return jornada;
            }
        } else {
            return jornada;
        }

        if (horaIniSec > ultimaHoraDia) {
            Date fecha = MovilidadUtil.sumarDias(jornada.getFecha(), 1);
            ParamFeriado pf = calcularMasivoBean.getMapParamFeriado().get(Util.dateFormat(fecha));
            if (pf == null && !MovilidadUtil.isSunday(fecha)) {
                calcular("H", timeOrigin, "H", timeDestiny, "");
            } else {
                calcular("F", timeOrigin, "F", timeDestiny, "");
            }
        } else {

            ParamFeriado pffHI = calcularMasivoBean.getMapParamFeriado().get(Util.dateFormat(jornada.getFecha()));
            ParamFeriado pffHF = pffHI;
            Date fecha = jornada.getFecha();
            if (horaFinSec > ultimaHoraDia) {
                fecha = MovilidadUtil.sumarDias(jornada.getFecha(), 1);
                pffHF = calcularMasivoBean.getMapParamFeriado().get(Util.dateFormat(fecha));
            }
            if ((pffHI == null && !MovilidadUtil.isSunday(jornada.getFecha())) && (pffHF == null && !MovilidadUtil.isSunday(fecha))) {
                calcular("H", timeOrigin, "H", timeDestiny, "");
//                System.out.println("H;H->>" + timeOrigin + " - " + timeDestiny);
            }
            if ((pffHI != null || MovilidadUtil.isSunday(jornada.getFecha())) && (pffHF == null && !MovilidadUtil.isSunday(fecha))) {
                calcular("F", timeOrigin, "H", timeDestiny, "");
//                System.out.println("F;H->>" + timeOrigin + " - " + timeDestiny);
            }
            if ((pffHI != null || MovilidadUtil.isSunday(jornada.getFecha())) && (pffHF != null || MovilidadUtil.isSunday(fecha))) {
                calcular("F", timeOrigin, "F", timeDestiny, "");
//                System.out.println("F;F->>" + timeOrigin + " - " + timeDestiny);

            }
            if ((pffHI == null && !MovilidadUtil.isSunday(jornada.getFecha())) && (pffHF != null || MovilidadUtil.isSunday(fecha))) {
                calcular("H", timeOrigin, "F", timeDestiny, "");
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
        return jornada;
    }

    public ParamAreaUsr getPau() {
        return pau;
    }

    public void setPau(ParamAreaUsr pau) {
        this.pau = pau;
    }

    public CalcularMasivoJSFManagedBean getCalcularMasivoBean() {
        return calcularMasivoBean;
    }

    public void setCalcularMasivoBean(CalcularMasivoJSFManagedBean calcularMasivoBean) {
        this.calcularMasivoBean = calcularMasivoBean;
    }

}
