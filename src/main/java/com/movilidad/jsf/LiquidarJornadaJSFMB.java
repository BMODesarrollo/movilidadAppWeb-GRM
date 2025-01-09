/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.aja.jornada.controller.calculator;
import static com.aja.jornada.util.JornadaUtil.calcular;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.ejb.PrgSerconDetFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.ParamFeriado;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgSerconDet;
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
import liquidadorjornada.Jornada;
import org.springframework.security.core.context.SecurityContextHolder;
import com.aja.jornada.util.ConfigJornada;

/**
 *
 * @author solucionesit
 */
@Named(value = "liquidarJornadaBean")
@ViewScoped
public class LiquidarJornadaJSFMB implements Serializable {

    /**
     * Creates a new instance of LiquidarJornadaJSFMB
     */
    public LiquidarJornadaJSFMB() {
    }

    @EJB
    private PrgSerconDetFacadeLocal prgSerconDetEJB;
    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @EJB
    private ParamFeriadoFacadeLocal paraFeEJB;

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEJB;
    @Inject
    private CalcularMasivoJSFManagedBean calcularMasivoBean;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private Map<String, ParamReporteHoras> mapaParamReporteHoras;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<PrgSerconDet> listPrgSerconDet;
    private String totalHorasAsignadas = "00:00:00";
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

    /**
     * Método encargado de liquidar por rango de fechas, para las jornada que
     * aplique se le setteará un '1' en el campo liquidado
     *
     * @param fechaDesde
     * @param fechaHasta
     */
    public void liquidar(Date fechaDesde, Date fechaHasta) {
        try {
            List<PrgSercon> listJornada = prgSerconEJB.getByDateSinLiquidar(fechaDesde, fechaHasta,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (!UtilJornada.tipoLiquidacion()) {
                listPrgSerconDet = getListPrgSerconDet();
                calcularMasivoBean.cargarMapParamFeriado();
                mapaParamReporteHoras = getMapaParamReporteHoras();
                this.prgSerconDetalleBuild(listJornada, user.getUsername());
            }

            prgSerconEJB.liquidarPorRangoFecha(fechaDesde, fechaHasta, user.getUsername(),
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (!UtilJornada.tipoLiquidacion()) {
                listPrgSerconDet.clear();
            }
            MovilidadUtil.addSuccessMessage("Jornada del " + Util.dateFormat(fechaDesde) + " hasta " + Util.dateFormat(fechaHasta) + "Liquidada Exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error inesperado al liquidar jornada, contacte al administrador");
        }
    }

    public PrgSercon caluleOnePartLiq(String s_horaInicio, String s_horaFin, Date fechaParam) {
        Jornada.reset();
        PrgSercon parteJornada = new PrgSercon();
        int ultimaHoraDia = MovilidadUtil.toSecs("24:00:00");
        int i_horaIniSec;
        int i_horaFinSec;

        i_horaIniSec = MovilidadUtil.toSecs(s_horaInicio);
        i_horaFinSec = MovilidadUtil.toSecs(s_horaFin);

        if (i_horaIniSec <= 0 && i_horaFinSec <= 0) {
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

        if (i_horaIniSec > ultimaHoraDia) {
            Date fecha = MovilidadUtil.sumarDias(fechaParam, 1);
            ParamFeriado pf = paraFeEJB.findByFecha(fecha);
            if (pf == null) {
                calcular("H", s_horaInicio, "H", s_horaFin, "");
            } else {
                calcular("F", s_horaInicio, "F", s_horaFin, "");
            }
        } else {

            ParamFeriado pffHI = paraFeEJB.findByFecha(fechaParam);
            ParamFeriado pffHF = pffHI;
            Date fecha = fechaParam;
            if (i_horaFinSec > ultimaHoraDia) {
                fecha = MovilidadUtil.sumarDias(fechaParam, 1);
                pffHF = paraFeEJB.findByFecha(fecha);
            }
            if (pffHI == null && pffHF == null) {
                calcular("H", s_horaInicio, "H", s_horaFin, "");
            }
            if (pffHI != null && pffHF == null) {
                calcular("F", s_horaInicio, "H", s_horaFin, "");
            }
            if (pffHI != null && pffHF != null) {
                calcular("F", s_horaInicio, "F", s_horaFin, "");
            }
            if (pffHI == null && pffHF != null) {
                calcular("H", s_horaInicio, "F", s_horaFin, "");
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

    public List<PrgSerconDet> getListPrgSerconDet() {
        if (listPrgSerconDet == null) {
            listPrgSerconDet = new ArrayList<>();
        }
        return listPrgSerconDet;
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

    public void prgSerconDetalleBuild(List<PrgSercon> list, String username) {
        mapaParamReporteHoras = getMapaParamReporteHoras();
        listPrgSerconDet = getListPrgSerconDet();
        list.forEach((PrgSercon obj) -> {
            /**
             * Validar si la jornada es por parte
             */
            salvarLiquidacionOriginal(obj);
            if (UtilJornada.esPorPartes(obj)) {
//                System.out.println("Jornada por partes" + obj.getIdPrgSercon());
                cargarCalcularDatoPorPartes(obj);
            } else {
//                System.out.println("Jornada No por partes" + obj.getIdPrgSercon());
                cargarCalcularDato(obj);
                generarDetallesJornada(obj, 1);

            }
        });
        prgSerconDetEJB.createList(this.listPrgSerconDet);

    }

    private void salvarLiquidacionOriginal(PrgSercon ps) {
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
     * @param ps
     * @return
     */
    public PrgSercon cargarCalcularDato(PrgSercon ps) {
        Jornada.reset();
        int ultimaHoraDia = MovilidadUtil.toSecs("24:00:00");
        int horaIniSec;
        int horaFinSec;
        String timeOrigin;
        String timeDestiny;
        if (UtilJornada.validarHorasParaCalcular(ps)) {
            timeOrigin = ps.getRealTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = ps.getRealTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        } else {
            timeOrigin = ps.getTimeOrigin();
            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
            timeDestiny = ps.getTimeDestiny();
            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
        }

        if (timeOrigin != null && timeDestiny != null) {
            if (timeOrigin.equals("00:00:00") && (timeDestiny.equals("00:00:00"))
                    || timeDestiny.equals("23:59:59")) {
                return ps;
            }
        } else {
            return ps;
        }

        if (horaIniSec > ultimaHoraDia) {
            Date fecha = MovilidadUtil.sumarDias(ps.getFecha(), 1);
            ParamFeriado pf = calcularMasivoBean.getMapParamFeriado().get(Util.dateFormat(fecha));
            if (pf == null) {
                calcular("H", timeOrigin, "H", timeDestiny, "");

            } else {
                calcular("F", timeOrigin, "F", timeDestiny, "");

            }
        } else {

            ParamFeriado pffHI = calcularMasivoBean.getMapParamFeriado().get(Util.dateFormat(ps.getFecha()));
            ParamFeriado pffHF = pffHI;
            Date fecha = ps.getFecha();
            if (horaFinSec > ultimaHoraDia) {
                fecha = MovilidadUtil.sumarDias(ps.getFecha(), 1);
                pffHF = calcularMasivoBean.getMapParamFeriado().get(Util.dateFormat(fecha));
            }
            if (pffHI == null && pffHF == null) {
                calcular("H", timeOrigin, "H", timeDestiny, "");
            }
            if (pffHI != null && pffHF == null) {
                calcular("F", timeOrigin, "H", timeDestiny, "");
            }
            if (pffHI != null && pffHF != null) {
                calcular("F", timeOrigin, "F", timeDestiny, "");
            }
            if (pffHI == null && pffHF != null) {
                calcular("H", timeOrigin, "F", timeDestiny, "");
            }
        }
        return ps;
    }

    public PrgSercon cargarCalcularDatoPorPartes(PrgSercon ps) {
        PrgSercon onePart = null;
        PrgSercon twoPart = null;
        PrgSercon threePart = null;
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
        onePart = caluleOnePartLiq(timeOrigin1, timeDestiny1, ps.getFecha());
        twoPart = caluleOnePartLiq(timeOrigin2, timeDestiny2, ps.getFecha());
        threePart = caluleOnePartLiq(timeOrigin3, timeDestiny3, ps.getFecha());

        totalHorasAsignadas = Jornada.hr_cero;
        ps.setDiurnas(Jornada.hr_cero);
        ps.setNocturnas(Jornada.hr_cero);
        ps.setExtraDiurna(Jornada.hr_cero);
        ps.setExtraNocturna(Jornada.hr_cero);
        ps.setFestivoDiurno(Jornada.hr_cero);
        ps.setFestivoNocturno(Jornada.hr_cero);
        ps.setFestivoExtraDiurno(Jornada.hr_cero);
        ps.setFestivoExtraNocturno(Jornada.hr_cero);
        sumarPorPartesLiq(ps, onePart, timeOrigin1, timeDestiny1, 1);
        sumarPorPartesLiq(ps, twoPart, timeOrigin2, timeDestiny2, 2);
        sumarPorPartesLiq(ps, threePart, timeOrigin3, timeDestiny3, 3);

        return ps;
    }

    public PrgSercon sumarPorPartesLiq(PrgSercon ps, PrgSercon psModificado, String horaIniParam, String horaFinParam, int parte) {
        int totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas)
                + MovilidadUtil.toSecs(psModificado.getDiurnas());
        int maxHorasLaborales = MovilidadUtil.toSecs(UtilJornada.getTotalHrsLaborales());
        int parteHorasLaborales;
        int parteHorasExtra;

        if (totalAsignar > maxHorasLaborales) {
//            System.out.println("CASO SUM 1");
            parteHorasExtra = totalAsignar - maxHorasLaborales;

            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_EXTRA_DIURNA),
                            ps,
                            MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                            horaFinParam, parte
                    )
            );
            ps.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraDiurna()) + parteHorasExtra));
            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
//                System.out.println("CASO SUM 1.1");

                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                /**
                 * Se valida que las horas originales
                 *
                 */
                listPrgSerconDet.add(
                        crearDetalleJornda(
                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                                ps,
                                horaIniParam,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra), parte
                        )
                );
                ps.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getDiurnas()) + parteHorasLaborales));
                totalHorasAsignadas = UtilJornada.getTotalHrsLaborales();
            }
        } else {
//            System.out.println("CASO SUM 2");
            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas)
                    + MovilidadUtil.toSecs(psModificado.getDiurnas()));
            ps.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getDiurnas())
                    + MovilidadUtil.toSecs(psModificado.getDiurnas())));
            if (MovilidadUtil.toSecs(psModificado.getDiurnas()) > 0) {
//                System.out.println("CASO SUM 2.1");

                //Se valida si la jordana va de nocturna a diurna
                if (MovilidadUtil.toSecs(horaIniParam) < MovilidadUtil.toSecs(UtilJornada.getIniDiurna())
                        && MovilidadUtil.toSecs(horaFinParam) > MovilidadUtil.toSecs(UtilJornada.getIniDiurna())) {
//                    System.out.println("CASO SUM 2.2");

                    listPrgSerconDet.add(crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                            ps,
                            UtilJornada.getIniDiurna(),
                            horaFinParam, parte
                    )
                    );
                } else //Se valida si la jordana va de diurna a nocturna
                if (MovilidadUtil.toSecs(horaIniParam) < MovilidadUtil.toSecs(UtilJornada.getIniNocturna())
                        && MovilidadUtil.toSecs(horaFinParam) > MovilidadUtil.toSecs(UtilJornada.getIniNocturna())) {
//                    System.out.println("CASO SUM 2.3");
                    listPrgSerconDet.add(crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                            ps,
                            horaIniParam,
                            UtilJornada.getIniNocturna(), parte
                    )
                    );
                } else {
//                    System.out.println("CASO SUM 2.4");

                    listPrgSerconDet.add(
                            crearDetalleJornda(
                                    MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                                    ps,
                                    horaIniParam,
                                    horaFinParam, parte
                            )
                    );
                }
            }
        }
        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getNocturnas());

        if (totalAsignar > maxHorasLaborales) {
//            System.out.println("CASO SUM 3");

            parteHorasExtra = totalAsignar - maxHorasLaborales;
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_EXTRA_NOCTURNA),
                            ps,
                            MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                            horaFinParam, parte
                    )
            );
            ps.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraNocturna()) + parteHorasExtra));
            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
//                System.out.println("CASO SUM 3.1");

                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                listPrgSerconDet.add(
                        crearDetalleJornda(
                                MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                                ps,
                                horaIniParam,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra), parte
                        )
                );
                ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) + parteHorasLaborales));
                totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
            }
        } else {
//            System.out.println("CASO SUM 4");
            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas)
                    + MovilidadUtil.toSecs(psModificado.getNocturnas()));
            ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas())
                    + MovilidadUtil.toSecs(psModificado.getNocturnas())));

            if (MovilidadUtil.toSecs(psModificado.getNocturnas()) > 0) {
//                System.out.println("CASO SUM 4.1");
                if (MovilidadUtil.toSecs(horaIniParam) < MovilidadUtil.toSecs(UtilJornada.getIniDiurna())
                        && MovilidadUtil.toSecs(horaFinParam) > MovilidadUtil.toSecs(UtilJornada.getIniDiurna())) {
                    listPrgSerconDet.add(crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                            ps,
                            horaIniParam,
                            UtilJornada.getIniDiurna(), parte
                    )
                    );
                } else if (MovilidadUtil.toSecs(horaIniParam) < MovilidadUtil.toSecs(UtilJornada.getIniNocturna())
                        && MovilidadUtil.toSecs(horaFinParam) > MovilidadUtil.toSecs(UtilJornada.getIniNocturna())) {
//                    System.out.println("CASO SUM 4.2");

                    listPrgSerconDet.add(crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                            ps,
                            UtilJornada.getIniNocturna(),
                            horaFinParam, parte
                    )
                    );
                } else {
//                    System.out.println("CASO SUM 4.3");
                    listPrgSerconDet.add(
                            crearDetalleJornda(
                                    MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                                    ps,
                                    horaIniParam,
                                    horaFinParam, parte
                            )
                    );
                }
            }
        }

        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoDiurno());

        if (totalAsignar > maxHorasLaborales) {
//            System.out.println("CASO SUM 5");
            parteHorasExtra = totalAsignar - maxHorasLaborales;
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO),
                            ps,
                            MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                            horaFinParam, parte
                    )
            );
            ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraDiurno())
                    + parteHorasExtra));
            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
//                System.out.println("CASO SUM 5.1");
                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                listPrgSerconDet.add(
                        crearDetalleJornda(
                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                                ps,
                                horaIniParam,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra), parte
                        )
                );
                ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoDiurno()) + parteHorasLaborales));
                totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
            }
        } else {
//            System.out.println("CASO SUM 6");
            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas)
                    + MovilidadUtil.toSecs(psModificado.getFestivoDiurno()));
            ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoDiurno())
                    + MovilidadUtil.toSecs(psModificado.getFestivoDiurno())));
            if (MovilidadUtil.toSecs(psModificado.getFestivoDiurno()) > 0) {
//                System.out.println("CASO SUM 6.1");
                if (MovilidadUtil.toSecs(horaIniParam) < MovilidadUtil.toSecs(UtilJornada.getIniDiurna())
                        && MovilidadUtil.toSecs(horaFinParam) > MovilidadUtil.toSecs(UtilJornada.getIniDiurna())) {
//                    System.out.println("CASO SUM 6.2");
                    listPrgSerconDet.add(crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                            ps,
                            UtilJornada.getIniDiurna(),
                            horaFinParam, parte
                    )
                    );
                } else if (MovilidadUtil.toSecs(horaIniParam) < MovilidadUtil.toSecs(UtilJornada.getIniNocturna())
                        && MovilidadUtil.toSecs(horaFinParam) > MovilidadUtil.toSecs(UtilJornada.getIniNocturna())) {
//                    System.out.println("CASO SUM 6.3");
                    listPrgSerconDet.add(crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                            ps,
                            horaIniParam,
                            UtilJornada.getIniNocturna(), parte
                    )
                    );
                } else {
//                    System.out.println("CASO SUM 6.4");
                    listPrgSerconDet.add(
                            crearDetalleJornda(
                                    MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                                    ps,
                                    horaIniParam,
                                    horaFinParam, parte
                            )
                    );
                }
            }
        }

        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoNocturno());

        if (totalAsignar > maxHorasLaborales) {
//            System.out.println("CASO SUM 7");
            parteHorasExtra = totalAsignar - maxHorasLaborales;
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO),
                            ps,
                            MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra),
                            horaFinParam, parte
                    )
            );
            ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraNocturno())
                    + parteHorasExtra));
            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
//                System.out.println("CASO SUM 7.1");
                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
                listPrgSerconDet.add(
                        crearDetalleJornda(
                                MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                                ps,
                                horaIniParam,
                                MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam) - parteHorasExtra), parte
                        )
                );
                ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoNocturno())
                        + parteHorasLaborales));
                totalHorasAsignadas = ConfigJornada.getTotal_Hrs_laborales();
            }
        } else {
//            System.out.println("CASO SUM 8");
            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas)
                    + MovilidadUtil.toSecs(psModificado.getFestivoNocturno()));
            ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoNocturno())
                    + MovilidadUtil.toSecs(psModificado.getFestivoNocturno())));

            if (MovilidadUtil.toSecs(psModificado.getFestivoNocturno()) > 0) {
//                System.out.println("CASO SUM 8.1");
                if (MovilidadUtil.toSecs(horaIniParam) < MovilidadUtil.toSecs(UtilJornada.getIniDiurna())
                        && MovilidadUtil.toSecs(horaFinParam) > MovilidadUtil.toSecs(UtilJornada.getIniDiurna())) {
//                    System.out.println("CASO SUM 8.2");
                    listPrgSerconDet.add(crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                            ps,
                            horaIniParam,
                            UtilJornada.getIniDiurna(), parte
                    )
                    );
                } else if (MovilidadUtil.toSecs(horaIniParam) < MovilidadUtil.toSecs(UtilJornada.getIniNocturna())
                        && MovilidadUtil.toSecs(horaFinParam) > MovilidadUtil.toSecs(UtilJornada.getIniNocturna())) {
//                    System.out.println("CASO SUM 8.3");
                    listPrgSerconDet.add(crearDetalleJornda(MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                            ps,
                            UtilJornada.getIniNocturna(),
                            horaFinParam, parte
                    )
                    );
                } else {
//                    System.out.println("CASO SUM 8.4");
                    listPrgSerconDet.add(
                            crearDetalleJornda(
                                    MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                                    ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                                    : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                                    ps,
                                    horaIniParam,
                                    horaFinParam, parte
                            )
                    );
                }
            }
        }

        ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraDiurno())
                + MovilidadUtil.toSecs(psModificado.getFestivoExtraDiurno())));
        if (MovilidadUtil.toSecs(psModificado.getFestivoExtraDiurno()) > 0) {
//            System.out.println("CASO SUM 9");
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO),
                            ps,
                            MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam)
                                    - MovilidadUtil.toSecs(psModificado.getFestivoExtraDiurno())),
                            horaFinParam, parte
                    )
            );
        }

        ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraNocturno())
                + MovilidadUtil.toSecs(psModificado.getFestivoExtraNocturno())));
        if (MovilidadUtil.toSecs(psModificado.getFestivoExtraNocturno()) > 0) {
//            System.out.println("CASO SUM 10");
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO),
                            ps,
                            MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam)
                                    - MovilidadUtil.toSecs(psModificado.getFestivoExtraNocturno())),
                            horaFinParam, parte
                    )
            );
        }

        ps.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraDiurna())
                + MovilidadUtil.toSecs(psModificado.getExtraDiurna())));
        if (MovilidadUtil.toSecs(psModificado.getExtraDiurna()) > 0) {
//            System.out.println("CASO SUM 11");
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_EXTRA_DIURNA),
                            ps,
                            MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam)
                                    - MovilidadUtil.toSecs(psModificado.getExtraDiurna())),
                            horaFinParam, parte
                    )
            );
        }

        ps.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraNocturna())
                + MovilidadUtil.toSecs(psModificado.getExtraNocturna())));
        if (MovilidadUtil.toSecs(psModificado.getExtraNocturna()) > 0) {
//            System.out.println("CASO SUM 12");
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_EXTRA_NOCTURNA),
                            ps,
                            MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFinParam)
                                    - MovilidadUtil.toSecs(psModificado.getExtraNocturna())),
                            horaFinParam, parte
                    )
            );
        }
        return ps;
    }

    private List<PrgSerconDet> generarDetallesJornada(PrgSercon prgSerconParam, int parte) {
        //revisar el caso, al parecer es "jornada de oficina"
        //<editor-fold defaultstate="collapsed" desc="Aquí se recorre la lista de parametro de reporte de horas.">
        //Corresponde a recargo diurno
        if (hayJornada(Jornada.getDiurnas_time_origin(),
                Jornada.getDiurnas_time_destiny())) {
//            if (Jornada.getHoraIniParam().equals("08:00:00")
//                    && Jornada.getHoraFinParam().equals("17:00:00")) {
//                listPrgSerconDet.add(
//                        crearDetalleJornda(
//                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
//                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
//                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
//                                prgSerconParam,
//                                "08:00:00",
//                                "12:00:00", parte
//                        )
//                );
//                listPrgSerconDet.add(
//                        crearDetalleJornda(
//                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
//                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
//                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
//                                prgSerconParam,
//                                "13:00:00",
//                                "17:00:00", parte
//                        )
//                );
//            } else {
                listPrgSerconDet.add(
                        crearDetalleJornda(
                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_DIURNAS),
                                prgSerconParam,
                                Jornada.getDiurnas_time_origin(),
                                Jornada.getDiurnas_time_destiny(), parte
                        )
                );
//            }
        }
        //Corresponde a recargo nocturno
        if (hayJornada(Jornada.getNocturnas_time_origin(),
                Jornada.getNocturnas_time_destiny())) {
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_NOCTURNAS),
                            prgSerconParam,
                            Jornada.getNocturnas_time_origin(),
                            Jornada.getNocturnas_time_destiny(), parte
                    )
            );
        }
        //Corresponde a recargo extra diurno
        if (hayJornada(Jornada.getExtra_diurna_time_origin(),
                Jornada.getExtra_diurna_time_destiny())) {
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_EXTRA_DIURNA),
                            prgSerconParam,
                            Jornada.getExtra_diurna_time_origin(),
                            Jornada.getExtra_diurna_time_destiny(), parte
                    )
            );
        }
        //Corresponde a recargo extra nocturno
        if (hayJornada(Jornada.getExtra_nocturna_time_origin(),
                Jornada.getExtra_nocturna_time_destiny())) {
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_EXTRA_NOCTURNA),
                            prgSerconParam,
                            Jornada.getExtra_nocturna_time_origin(),
                            Jornada.getExtra_nocturna_time_destiny(), parte
                    )
            );
        }
        //Corresponde a recargo festivo diurno
        if (hayJornada(Jornada.getFestivo_diurno_time_origin(),
                Jornada.getFestivo_diurno_time_destiny())) {
//            if (Jornada.getHoraIniParam().equals("08:00:00")
//                    && Jornada.getHoraFinParam().equals("17:00:00")) {
//                listPrgSerconDet.add(
//                        crearDetalleJornda(
//                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
//                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
//                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
//                                prgSerconParam,
//                                "08:00:00",
//                                "12:00:00", parte
//                        )
//                );
//                listPrgSerconDet.add(
//                        crearDetalleJornda(
//                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
//                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
//                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
//                                prgSerconParam,
//                                "13:00:00",
//                                "17:00:00", parte
//                        )
//                );
//            } else {
                listPrgSerconDet.add(
                        crearDetalleJornda(
                                MovilidadUtil.toSecs(dominical_comp_diurno) > 0
                                ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)
                                : mapaParamReporteHoras.get(Util.RPH_FESTIVO_DIURNO),
                                prgSerconParam,
                                Jornada.getFestivo_diurno_time_origin(),
                                Jornada.getFestivo_diurno_time_destiny(), parte
                        )
                );
//            }
        }
        //Corresponde a recargo festivo nocturno
        if (hayJornada(Jornada.getFestivo_nocturno_time_origin(),
                Jornada.getFestivo_nocturno_time_destiny())) {
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            MovilidadUtil.toSecs(dominical_comp_nocturno) > 0
                            ? mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)
                            : mapaParamReporteHoras.get(Util.RPH_FESTIVO_NOCTURNO),
                            prgSerconParam,
                            Jornada.getFestivo_nocturno_time_origin(),
                            Jornada.getFestivo_nocturno_time_destiny(), parte
                    )
            );
        }
        //Corresponde a recargo festivo extra diurno
        if (hayJornada(Jornada.getFestivo_extra_diurno_time_origin(),
                Jornada.getFestivo_extra_diurno_time_destiny())) {
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_DIURNO),
                            prgSerconParam,
                            Jornada.getFestivo_extra_diurno_time_origin(),
                            Jornada.getFestivo_extra_diurno_time_destiny(), parte
                    )
            );
        }
        //Corresponde a recargo festivo extra nocturno
        if (hayJornada(Jornada.getFestivo_extra_nocturno_time_origin(),
                Jornada.getFestivo_extra_nocturno_time_destiny())) {
            listPrgSerconDet.add(
                    crearDetalleJornda(
                            mapaParamReporteHoras.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO),
                            prgSerconParam,
                            Jornada.getFestivo_extra_nocturno_time_origin(),
                            Jornada.getFestivo_extra_nocturno_time_destiny(), parte
                    )
            );
        }
//        /**
//         * Compensatorio.
//         */
//        //Corresponde a recargo festivo diurno con compensatorio dominical
//        if (hayJornada(JornadaUtil.getFestivo_diurno_time_origin(),
//                JornadaUtil.getFestivo_diurno_time_destiny())) {
//            listPrgSerconDet.add(
//                    crearDetalleJornda(
//                            mapaParamReporteHoras.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO),
//                            prgSerconParam,
//                            JornadaUtil.getFestivo_diurno_time_origin(),
//                            JornadaUtil.getFestivo_diurno_time_destiny()
//                    )
//            );
//        }
////        Corresponde a recargo festivo nocturno con compensatorio dominical
//        if (hayJornada(JornadaUtil.getFestivo_nocturno_time_origin(),
//                JornadaUtil.getFestivo_nocturno_time_destiny())) {
//            listPrgSerconDet.add(
//                    crearDetalleJornda(
//                            mapaParamReporteHoras.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO),
//                            prgSerconParam,
//                            JornadaUtil.getFestivo_nocturno_time_origin(),
//                            JornadaUtil.getFestivo_nocturno_time_destiny()
//                    )
//            );
//        }
////        Corresponde a recargo festivo extra diurno con compensatorio dominical
//        if (hayJornada(JornadaUtil.getFestivo_extra_diurno_time_origin(),
//                JornadaUtil.getFestivo_extra_diurno_time_destiny())) {
//            listPrgSerconDet.add(
//                    crearDetalleJornda(
//                            mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO),
//                            prgSerconParam,
//                            JornadaUtil.getFestivo_extra_diurno_time_origin(),
//                            JornadaUtil.getFestivo_extra_diurno_time_destiny()
//                    )
//            );
//        }
////        Corresponde a recargo festivo extra nocturno con compensatorio dominical
//        if (hayJornada(JornadaUtil.getFestivo_extra_nocturno_time_origin(),
//                JornadaUtil.getFestivo_extra_nocturno_time_destiny())) {
//            listPrgSerconDet.add(
//                    crearDetalleJornda(
//                            mapaParamReporteHoras.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO),
//                            prgSerconParam,
//                            JornadaUtil.getFestivo_extra_nocturno_time_origin(),
//                            JornadaUtil.getFestivo_extra_nocturno_time_destiny()
//                    )
//            );
//        }
        //</editor-fold>

        return listPrgSerconDet;
    }

    private boolean hayJornada(String horaIni, String horaFin) {
        return MovilidadUtil.toSecs(horaIni) + MovilidadUtil.toSecs(horaFin) > 0;
    }

    private PrgSerconDet crearDetalleJornda(ParamReporteHoras paramReporte,
            PrgSercon objParam, String horaIni, String horaFin, int parte) {
//        System.out.println("parte----->" + parte);
//        System.out.println("ini----->" + horaIni);
//        System.out.println("fin----->" + horaFin);
        PrgSerconDet obj = new PrgSerconDet();
        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
        obj.setEstadoReg(0);
        obj.setUsername(user.getUsername());
        obj.setIdParamReporte(paramReporte);
        obj.setIdPrgSercon(objParam);
        obj.setTimeorigin(horaIni);
        obj.setTimedestiny(horaFin);
        obj.setParte(parte);
        String cantidad = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                - MovilidadUtil.toSecs(horaIni));
        obj.setCantidad(cantidad);

        return obj;
    }
}
