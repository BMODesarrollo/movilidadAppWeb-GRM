/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import com.aja.jornada.controller.calculator;
import com.aja.jornada.util.ConfigJornada;
import com.movilidad.model.ConfigControlJornada;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.PrgSercon;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author solucionesit
 * @param <T>
 */
public class UtilJornada<T> implements Serializable {

//    private static String totalHorasAsignadas;
//
    /**
     * Método responsale de identificar si la jornada indicada por párametro es
     * una jornada por partes.
     *
     * @param ps
     * @return Valor boolean.
     */
    public static boolean esPorPartes(PrgSercon ps) {
        if (ps.getAutorizado() == null || (ps.getAutorizado() != null && ps.getAutorizado() == 0)) {
            if (MovilidadUtil.toSecs(ps.getHiniTurno2()) > 0) {
                return true;
            }
            if (MovilidadUtil.toSecs(ps.getHiniTurno3()) > 0) {
                return true;
            }
        } else {
            if (MovilidadUtil.toSecs(ps.getRealHiniTurno2()) > 0) {
                return true;
            }
            if (MovilidadUtil.toSecs(ps.getRealHiniTurno3()) > 0) {
                return true;
            }
        }
        return false;
    }

    public static void cargarParametrosJar() {
        ConfigJornada.setMax_hrs_extra_dia(getMaxHrsExtrasDia());
        ConfigJornada.setMax_hrs_extra_smnal(getMaxHrsExtrasSmnl());
        ConfigJornada.setTotal_Hrs_laborales(getTotalHrsLaborales());
        ConfigJornada.setIni_diurna(getIniDiurna());
        ConfigJornada.setIni_nocturna(getIniNocturna());
        ConfigJornada.setFin_nocturna(getFinNocturna());
        ConfigJornada.setCriterio_min_hora_extra(getCriterioMinHrsExtra());
        ConfigJornada.setMax_hrs_laborales_smnal(getMaxHrsLaboralesSmnl());
        ConfigJornada.setMax_hr_laborales_dia(getMaxHrsLaboralesDia());
        ConfigJornada.setMin_hr_laborales_dia(getMinHrsLaboralesDia());
        ConfigJornada.setTipo_liquidacion(getTipoLiquidacion());
    }

    public static boolean validarHorasParaCalcular(PrgSercon param) {
        if (param.getAutorizado() != null && param.getAutorizado() == 1) {
            return param.getRealTimeOrigin() != null;
        } else {
            return param.getPrgModificada() != null && param.getPrgModificada() == 1;
        }
    }
//
//    public static PrgSercon resetDetalleJornada(PrgSercon prgSercon) {
//        prgSercon.setDiurnas("00:00:00");
//        prgSercon.setNocturnas("00:00:00");
//        prgSercon.setExtraDiurna("00:00:00");
//        prgSercon.setExtraNocturna("00:00:00");
//        prgSercon.setFestivoDiurno("00:00:00");
//        prgSercon.setFestivoNocturno("00:00:00");
//        prgSercon.setFestivoExtraDiurno("00:00:00");
//        prgSercon.setFestivoExtraNocturno("00:00:00");
//        return prgSercon;
//    }
//
//    public static GenericaJornada resetDetalleJornada(GenericaJornada gj) {
//        gj.setDiurnas("00:00:00");
//        gj.setNocturnas("00:00:00");
//        gj.setExtraDiurna("00:00:00");
//        gj.setExtraNocturna("00:00:00");
//        gj.setFestivoDiurno("00:00:00");
//        gj.setFestivoNocturno("00:00:00");
//        gj.setFestivoExtraDiurno("00:00:00");
//        gj.setFestivoExtraNocturno("00:00:00");
//        return gj;
//    }
//
//    public static GenericaJornada cargarHorasLiquidadas(GenericaJornada gj) {
//        gj.setDiurnas(liquidadorjornada.Jornada.getDiurnas());
//        gj.setNocturnas(liquidadorjornada.Jornada.getNocturnas());
//        gj.setExtraDiurna(liquidadorjornada.Jornada.getExtra_diurna());
//        gj.setExtraNocturna(liquidadorjornada.Jornada.getExtra_nocturna());
//        gj.setFestivoDiurno(liquidadorjornada.Jornada.getFestivo_diurno());
//        gj.setFestivoNocturno(liquidadorjornada.Jornada.getFestivo_nocturno());
//        gj.setFestivoExtraDiurno(liquidadorjornada.Jornada.getFestivo_extra_diurno());
//        gj.setFestivoExtraNocturno(liquidadorjornada.Jornada.getFestivo_extra_nocturno());
//        return gj;
//    }
//
//    public static PrgSercon cargarHorasLiquidadas(PrgSercon ps) {
//        ps.setDiurnas(liquidadorjornada.Jornada.getDiurnas());
//        ps.setNocturnas(liquidadorjornada.Jornada.getNocturnas());
//        ps.setExtraDiurna(liquidadorjornada.Jornada.getExtra_diurna());
//        ps.setExtraNocturna(liquidadorjornada.Jornada.getExtra_nocturna());
//        ps.setFestivoDiurno(liquidadorjornada.Jornada.getFestivo_diurno());
//        ps.setFestivoNocturno(liquidadorjornada.Jornada.getFestivo_nocturno());
//        ps.setFestivoExtraDiurno(liquidadorjornada.Jornada.getFestivo_extra_diurno());
//        ps.setFestivoExtraNocturno(liquidadorjornada.Jornada.getFestivo_extra_nocturno());
//        return ps;
//    }
//

    public static boolean validarCriterioMinJornada(PrgSercon psParam) {
        int difPart1 = MovilidadUtil.toSecs(psParam.getRealTimeDestiny()) - MovilidadUtil.toSecs(psParam.getRealTimeOrigin());
        int difPart2 = MovilidadUtil.toSecs(psParam.getRealHfinTurno2()) - MovilidadUtil.toSecs(psParam.getRealHiniTurno2());
        int difPart3 = MovilidadUtil.toSecs(psParam.getRealHfinTurno3()) - MovilidadUtil.toSecs(psParam.getRealHiniTurno3());
        int totalProduccion = difPart1 + difPart2 + difPart3;
        int totalHorasExtras = (totalProduccion
                - getWorkTimeJornada(psParam.getSercon(), psParam.getWorkTime()));
        /**
         * Se valida si la jornada no cuenta con horas extras.
         */
        if (totalHorasExtras <= 0) {
            return true;
        }
        /**
         * Validar si el total de horas extras en mayor que el criterio minimo
         * para considerar la autorización de jornadas.
         */
        return (totalHorasExtras > MovilidadUtil.toSecs(getCriterioMinHrsExtra()));
    }

    public static boolean respetarMaxHorasExtrasDiarias() {
        try {
            return (SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.RESPETAR_MAX_HORAS_EXTRAS).getEstado().equals(ConstantsUtil.ON_INT));
        } catch (Exception e) {
            return true;
        }
    }
//
//    public static void calcular(String tipoDiaIni, String horaIni, String tipoDiaFin, String horaFin, String totalHrsLaborales) {
//        setCongifEmpresaForJornadas(totalHrsLaborales);
//        liquidadorjornada.Jornada.calcular(tipoDiaIni, horaIni, tipoDiaFin, horaFin);
//    }
//
//    public static void setCongifEmpresaForJornadas(String totalHrsLaborales) {
//        liquidadorjornada.Jornada.setIni_diurna(SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.INI_DIURNA).getTiempo());
//        liquidadorjornada.Jornada.setIni_nocturna(SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.INI_NOCTURNA).getTiempo());
//        liquidadorjornada.Jornada.setFin_nocturna(SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.FIN_NOCTURNA).getTiempo());
//        if (MovilidadUtil.stringIsEmpty(totalHrsLaborales)) {
//            liquidadorjornada.Jornada.setHrs_laborales(SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.TOTAL_HRS_LABORALES).getTiempo());
//        } else {
//            liquidadorjornada.Jornada.setHrs_laborales(totalHrsLaborales);
//        }
//    }
//

    /**
     * Evalue que tipo de liquidación esta configurada.
     *
     * @return true Tipo de liquidación flexible, false tipo de liquidacion
     * normal
     */
    public static boolean tipoLiquidacion() {
        if (getTipoLiquidacionObj() == null) {
            return false;
        }
        return getTipoLiquidacion().equals(ConstantsUtil.ON_INT);
    }

    /**
     * Se encarga de validad si la fecha y hora de registro es mayor al la fecha
     * hora en que se termina la jornada
     *
     * @param fechaParam
     * @param HoraFinParam
     * @return True or False
     * @throws ParseException
     */
    public static boolean registroDespuesDeEjecutado(Date fechaParam, String HoraFinParam) throws ParseException {
        Date fechaHoraJornada = MovilidadUtil.converterToHour(fechaParam, HoraFinParam);
        Date fechaRegistro = MovilidadUtil.fechaCompletaHoy();
        return fechaHoraJornada.after(fechaRegistro);
    }

    /**
     * Se encarga de identificar y devolver la hora fin de la ultima parte de
     * turno registrada para horas reales.
     *
     * @param psParam
     * @return String formato hh:mm:ss
     */
    public static String ultimaHoraRealJornada(PrgSercon psParam) {
        if (MovilidadUtil.toSecs(psParam.getRealHfinTurno3()) > MovilidadUtil.toSecs(liquidadorjornada.Jornada.hr_cero)) {
            return psParam.getRealHfinTurno3();
        }
        if (MovilidadUtil.toSecs(psParam.getRealHfinTurno3()) > MovilidadUtil.toSecs(liquidadorjornada.Jornada.hr_cero)) {
            return psParam.getRealHfinTurno2();
        }
        return psParam.getRealTimeDestiny();
    }

    /**
     * Se encarga de identificar y devolver la hora fin de la ultima parte de
     * turno registrada para horas reales.
     *
     * @param jornada
     * @return String formato hh:mm:ss
     */
    public static String ultimaHoraRealJornada(GenericaJornada jornada) {
        String timeDesniny = jornada.getRealTimeDestiny();
        if (jornada.getIdGenericaJornadaTipo() != null) {
            if (jornada.getIdGenericaJornadaTipo().getTipoCalculo() == 0) {
                timeDesniny = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(timeDesniny) - MovilidadUtil.toSecs(jornada.getIdGenericaJornadaTipo().getDescanso()));
            }
        }
        return timeDesniny;
    }

    /*
     * Metodo encargado de devolver el worktime real o virtual segun el tipo de
     * jornada
     *
     */
    public static int getWorkTimeJornada(String sercon, String workTimeParam) {
        int workTime = MovilidadUtil.toSecs(SingletonConfigControlJornada
                .getMapConfigControlJornada()
                .get(ConstantsUtil.TOTAL_HRS_LABORALES)
                .getTiempo());
        if (tipoLiquidacion()) {//flexible
            workTime = MovilidadUtil.toSecs(workTimeParam);
            if (workTime == 0) {
                workTime = MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales());
            }
            if (workTimeJornadaVirtual(sercon)) {
                workTime = MovilidadUtil.toSecs(ConfigJornada.getTotal_Hrs_laborales());
            }
        }
        return workTime;
    }

    /*
     * Metodo encargado de determinar si una jornada de tipo ejemplo:
     * VAC,LNR,LR,IGN,AUS,DES,LMA,ARL
     *
     */
    public static boolean workTimeJornadaVirtual(String sercon) {
        if (sercon == null) {
            return false;
        }
        String[] conceptos = {"VAC", "LNR", "LR", "IGN", "AUS", "DES", "LMA", "ARL"};
        for (String item : conceptos) {
            if (sercon.toLowerCase().contains(item.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
//
//    /**
//     * Se encarga de identificar y devolver la hora fin de la ultima parte de
//     * turno registrada para horas programdas.
//     *
//     * @param psParam
//     * @return String formato hh:mm:ss
//     */
//    public static String ultimaHoraProgramadaJornada(PrgSercon psParam) {
//        if (MovilidadUtil.toSecs(psParam.getHfinTurno3()) > MovilidadUtil.toSecs(liquidadorjornada.Jornada.hr_cero)) {
//            return psParam.getHfinTurno3();
//        }
//        if (MovilidadUtil.toSecs(psParam.getHfinTurno3()) > MovilidadUtil.toSecs(liquidadorjornada.Jornada.hr_cero)) {
//            return psParam.getHfinTurno2();
//        }
//        return psParam.getTimeDestiny();
//    }
//
//    public static PrgSercon cargarCalcularDatoPorPartes(PrgSercon ps, Map<String, ParamFeriado> mapParamFeriado) {
//        PrgSercon onePart = null;
//        PrgSercon twoPart = null;
//        PrgSercon threePart = null;
//        String timeOrigin1 = ps.getTimeOrigin();
//        String timeDestiny1 = ps.getTimeDestiny();
//        String timeOrigin2 = ps.getHiniTurno2();
//        String timeDestiny2 = ps.getHfinTurno2();
//        String timeOrigin3 = ps.getHiniTurno3();
//        String timeDestiny3 = ps.getHfinTurno3();
//        if (ps.getAutorizado() != null && ps.getAutorizado() == 1) {
//            timeOrigin1 = ps.getRealTimeOrigin();
//            timeDestiny1 = ps.getRealTimeDestiny();
//            timeOrigin2 = ps.getRealHiniTurno2();
//            timeDestiny2 = ps.getRealHfinTurno2();
//            timeOrigin3 = ps.getRealHiniTurno3();
//            timeDestiny3 = ps.getRealHfinTurno3();
//        }
//        onePart = caluleOnePart(timeOrigin1, timeDestiny1, ps.getFecha(), ps.getWorkTime(), mapParamFeriado);
//        twoPart = caluleOnePart(timeOrigin2, timeDestiny2, ps.getFecha(), ps.getWorkTime(), mapParamFeriado);
//        threePart = caluleOnePart(timeOrigin3, timeDestiny3, ps.getFecha(), ps.getWorkTime(), mapParamFeriado);
//
//        totalHorasAsignadas = liquidadorjornada.Jornada.hr_cero;
//        ps = JornadaUtil.resetDetalleJornada(ps);
//        sumarPorPartes(ps, onePart);
//        sumarPorPartes(ps, twoPart);
//        sumarPorPartes(ps, threePart);
//
//        ps.setTipoCalculo(3);
//        return ps;
//    }
//
//    public static PrgSercon caluleOnePart(String s_horaInicio, String s_horaFin, Date fechaParam, String totalHorasLaborales, Map<String, ParamFeriado> mapParamFeriado) {
//        liquidadorjornada.Jornada.reset();
//        PrgSercon parteJornada = new PrgSercon();
//        int ultimaHoraDia = MovilidadUtil.toSecs(liquidadorjornada.Jornada.fin_dia);
//        int i_horaIniSec;
//        int i_horaFinSec;
//
//        i_horaIniSec = MovilidadUtil.toSecs(s_horaInicio);
//        i_horaFinSec = MovilidadUtil.toSecs(s_horaFin);
//
//        if (i_horaIniSec <= 0 && i_horaFinSec <= 0) {
//            return JornadaUtil.cargarHorasLiquidadas(parteJornada);
//        }
//
//        if (i_horaIniSec >= ultimaHoraDia) {
//            Date fecha = MovilidadUtil.sumarDias(fechaParam, 1);
////            ParamFeriado pf = paraFeEJB.findByFecha(fecha);
//            ParamFeriado pf = mapParamFeriado.get(Util.dateFormat(fecha));
//            if (pf == null) {
//                JornadaUtil.calcular("H", s_horaInicio, "H", s_horaFin, totalHorasLaborales);
//            } else {
//                JornadaUtil.calcular("F", s_horaInicio, "F", s_horaFin, totalHorasLaborales);
//            }
//        } else {
//
//            ParamFeriado pffHI = mapParamFeriado.get(Util.dateFormat(fechaParam));
//
//            ParamFeriado pffHF = pffHI;
//            Date fecha = fechaParam;
//            if (i_horaFinSec > ultimaHoraDia) {
//                fecha = MovilidadUtil.sumarDias(fechaParam, 1);
//                pffHF = mapParamFeriado.get(Util.dateFormat(fecha));
//
//            }
//            if (pffHI == null && pffHF == null) {
//                JornadaUtil.calcular("H", s_horaInicio, "H", s_horaFin, totalHorasLaborales);
//            }
//            if (pffHI != null && pffHF == null) {
//                JornadaUtil.calcular("F", s_horaInicio, "H", s_horaFin, totalHorasLaborales);
//            }
//            if (pffHI != null && pffHF != null) {
//                JornadaUtil.calcular("F", s_horaInicio, "F", s_horaFin, totalHorasLaborales);
//            }
//            if (pffHI == null && pffHF != null) {
//                JornadaUtil.calcular("H", s_horaInicio, "F", s_horaFin, totalHorasLaborales);
//            }
//        }
//
//        return JornadaUtil.cargarHorasLiquidadas(parteJornada);
//    }
//
//    public static PrgSercon sumarPorPartes(PrgSercon ps, PrgSercon psModificado) {
//        int totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas)
//                + MovilidadUtil.toSecs(psModificado.getDiurnas());
//        int maxHorasLaborales = MovilidadUtil.toSecs(ps.getWorkTime());
//        int parteHorasLaborales;
//        int parteHorasExtra;
//
//        if (totalAsignar > maxHorasLaborales) {
//            parteHorasExtra = totalAsignar - maxHorasLaborales;
//            ps.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraDiurna()) + parteHorasExtra));
//            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
//                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
//                ps.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getDiurnas()) + parteHorasLaborales));
//                totalHorasAsignadas = ps.getWorkTime();
//            }
//        } else {
//            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getDiurnas()));
//            ps.setDiurnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getDiurnas()) + MovilidadUtil.toSecs(psModificado.getDiurnas())));
//        }
//        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getNocturnas());
//
//        if (totalAsignar > maxHorasLaborales) {
//            parteHorasExtra = totalAsignar - maxHorasLaborales;
//            ps.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraNocturna()) + parteHorasExtra));
//            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
//                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
//                ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) + parteHorasLaborales));
//                totalHorasAsignadas = ps.getWorkTime();
//            }
//        } else {
//            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getNocturnas()));
//            ps.setNocturnas(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getNocturnas()) + MovilidadUtil.toSecs(psModificado.getNocturnas())));
//        }
//
//        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoDiurno());
//
//        if (totalAsignar > maxHorasLaborales) {
//            parteHorasExtra = totalAsignar - maxHorasLaborales;
//            ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraDiurno()) + parteHorasExtra));
//            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
//                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
//                ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoDiurno()) + parteHorasLaborales));
//                totalHorasAsignadas = ps.getWorkTime();
//            }
//        } else {
//            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoDiurno()));
//            ps.setFestivoDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoDiurno()) + MovilidadUtil.toSecs(psModificado.getFestivoDiurno())));
//        }
//
//        totalAsignar = MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoNocturno());
//
//        if (totalAsignar > maxHorasLaborales) {
//            parteHorasExtra = totalAsignar
//                    - maxHorasLaborales;
//            ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) + parteHorasExtra));
//            if (MovilidadUtil.toSecs(totalHorasAsignadas) < maxHorasLaborales) {
//                parteHorasLaborales = maxHorasLaborales - MovilidadUtil.toSecs(totalHorasAsignadas);
//                ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoNocturno()) + parteHorasLaborales));
//                totalHorasAsignadas = ps.getWorkTime();
//            }
//        } else {
//            totalHorasAsignadas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(totalHorasAsignadas) + MovilidadUtil.toSecs(psModificado.getFestivoNocturno()));
//            ps.setFestivoNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoNocturno()) + MovilidadUtil.toSecs(psModificado.getFestivoNocturno())));
//        }
//
//        ps.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(psModificado.getFestivoExtraDiurno())));
//
//        ps.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getFestivoExtraNocturno()) + MovilidadUtil.toSecs(psModificado.getFestivoExtraNocturno())));
//
//        ps.setExtraDiurna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraDiurna()) + MovilidadUtil.toSecs(psModificado.getExtraDiurna())));
//
//        ps.setExtraNocturna(MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(ps.getExtraNocturna()) + MovilidadUtil.toSecs(psModificado.getExtraNocturna())));
//        return ps;
//    }
//
//    /**
//     * Método responsable de invocar al método calculaJ para calcular las horas
//     * a liquidar(diurnas,nocturnas,extra diurna, extra nocturna, festivo
//     * diurno, festivo nocturno, festivo extra diurno, festivo extra nocturno)
//     * de una jornada de acuerdo a una hora inicio y hora fin de turno.
//     *
//     * Persiste la información en BD.
//     *
//     * Las jornadas o turnos que pasan por este método son almacenadas con el
//     * identificador 2 en el atributo 'tipoCalculo', lo cual significa que el
//     * tipo de calculo es automatico, hecho por rigel.
//     *
//     * Invoca al método calculaJ, responsable de calcular la jornada.
//     *
//     * @param ps
//     * @return
//     */
//    public static PrgSercon cargarCalcularDato(PrgSercon ps, Map<String, ParamFeriado> mapParamFeriado) {
//        liquidadorjornada.Jornada.reset();
//        int ultimaHoraDia = MovilidadUtil.toSecs(liquidadorjornada.Jornada.fin_dia);
//        int horaIniSec;
//        int horaFinSec;
//        String timeOrigin = ps.getTimeOrigin();
//        String timeDestiny = ps.getTimeDestiny();
//        horaIniSec = MovilidadUtil.toSecs(timeOrigin);
//        horaFinSec = MovilidadUtil.toSecs(timeDestiny);
//        String workTime = getWorKTimeFromJornada(ps);
//        if (ps.getAutorizado() != null && ps.getAutorizado() == 1) {
//            timeOrigin = ps.getRealTimeOrigin();
//            timeDestiny = ps.getRealTimeDestiny();
//            horaIniSec = MovilidadUtil.toSecs(timeOrigin);
//            horaFinSec = MovilidadUtil.toSecs(timeDestiny);
//        }
//
//        if (timeOrigin != null && timeDestiny != null) {
//            if (timeOrigin.equals(liquidadorjornada.Jornada.hr_cero) && timeDestiny.equals(liquidadorjornada.Jornada.hr_cero)) {
//
//                ps = JornadaUtil.cargarHorasLiquidadas(ps);
//                //Tipo de calculo automatico
//                ps.setTipoCalculo(2);
//                return ps;
//            }
//        } else {
//            return ps;
//        }
//
//        if (horaIniSec > ultimaHoraDia) {
//            Date fecha = MovilidadUtil.sumarDias(ps.getFecha(), 1);
////            ParamFeriado pf = paraFeEJB.findByFecha(fecha);
//            ParamFeriado pf = mapParamFeriado.get(Util.dateFormat(fecha));
//
////
//            if (pf == null) {
//                JornadaUtil.calcular("H", timeOrigin, "H", timeDestiny, workTime);
//
//            } else {
//                JornadaUtil.calcular("F", timeOrigin, "F", timeDestiny, workTime);
//
//            }
//        } else {
////            ParamFeriado pffHI = paraFeEJB.findByFecha(ps.getFecha());
//            ParamFeriado pffHI = mapParamFeriado.get(Util.dateFormat(ps.getFecha()));
//
//            ParamFeriado pffHF = pffHI;
//            Date fecha = ps.getFecha();
//            if (horaFinSec > ultimaHoraDia) {
//                fecha = MovilidadUtil.sumarDias(ps.getFecha(), 1);
////                pffHF = paraFeEJB.findByFecha(fecha);
//                pffHF = mapParamFeriado.get(Util.dateFormat(fecha));
//
//            }
//            if (pffHI == null && pffHF == null) {
//                JornadaUtil.calcular("H", timeOrigin, "H", timeDestiny, workTime);
//            }
//            if (pffHI != null && pffHF == null) {
//                JornadaUtil.calcular("F", timeOrigin, "H", timeDestiny, workTime);
//            }
//            if (pffHI != null && pffHF != null) {
//                JornadaUtil.calcular("F", timeOrigin, "F", timeDestiny, workTime);
//            }
//            if (pffHI == null && pffHF != null) {
//                JornadaUtil.calcular("H", timeOrigin, "F", timeDestiny, workTime);
//            }
//        }
//        ps = JornadaUtil.cargarHorasLiquidadas(ps);
//        //Tipo de calculo automatico
//        ps.setTipoCalculo(2);
//
//        return ps;
//    }
//
//    /**
//     * Calcular tiempo de produción para una nueva joranda.
//     *
//     * Se ayuda de la variable prgSerconNew, utlizada en la persistencia de una
//     * nueva jornada, para hacer el calculo.
//     *
//     * @return un valor string con el total de horas de producción.
//     */
//    public static String getTimeProduction(PrgSercon prgSercon) {
//        /**
//         * Convertir a enteros las horas todos los turnos.
//         */
//
//        int diurna = MovilidadUtil.toSecs(prgSercon.getDiurnas());
//        int nocturna = MovilidadUtil.toSecs(prgSercon.getNocturnas());
//        int extraDiurna = MovilidadUtil.toSecs(prgSercon.getExtraDiurna());
//        int extraNocturna = MovilidadUtil.toSecs(prgSercon.getExtraNocturna());
//        int festDiurno = MovilidadUtil.toSecs(prgSercon.getFestivoDiurno());
//        int festNocturno = MovilidadUtil.toSecs(prgSercon.getFestivoNocturno());
//        int festExtraDiurno = MovilidadUtil.toSecs(prgSercon.getFestivoExtraDiurno());
//        int festExtraNocturno = MovilidadUtil.toSecs(prgSercon.getFestivoExtraNocturno());
//        int domCompDiurna = MovilidadUtil.toSecs(prgSercon.getDominicalCompDiurnas());
//        int domCompNocturna = MovilidadUtil.toSecs(prgSercon.getDominicalCompNocturnas());
//        int domCompExtraDiurna = MovilidadUtil.toSecs(prgSercon.getDominicalCompDiurnaExtra());
//        int domCompExtraNocturna = MovilidadUtil.toSecs(prgSercon.getDominicalCompNocturnaExtra());
//
//        /**
//         * Calcular el tiempo total de produccion
//         */
//        int produccionTotal = diurna + nocturna + extraDiurna + extraNocturna
//                + festDiurno + festNocturno + festExtraDiurno + festExtraNocturno
//                + domCompDiurna + domCompNocturna + domCompExtraDiurna + domCompExtraNocturna;
//
//        /**
//         * Retornar el tiempo todal de produccion en String.
//         */
//        if (produccionTotal == 0) {
//            return liquidadorjornada.Jornada.hr_cero;
//        }
//        return MovilidadUtil.toTimeSec(produccionTotal);
//    }
//

    public static String getIniDiurna() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.INI_DIURNA).getTiempo();
    }

    public static String getIniNocturna() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.INI_NOCTURNA).getTiempo();
    }

    public static String getFinNocturna() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.FIN_NOCTURNA).getTiempo();
    }

    public static String getTotalHrsLaborales() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.TOTAL_HRS_LABORALES).getTiempo();
    }

    public static String getCriterioMinHrsExtra() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.CRITERIO_MIN_HORA_EXTRA).getTiempo();
    }

    public static String getMaxHrsExtrasDia() {
        return getMaxHrsExtrasDiaObj().getTiempo();
    }

    public static ConfigControlJornada getMaxHrsExtrasDiaObj() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.MAX_HRS_EXTRAS_DIA);
    }

    public static String getMaxHrsExtrasSmnl() {
        return getMaxHrsExtrasSmnlObj().getTiempo();
    }

    public static ConfigControlJornada getMaxHrsExtrasSmnlObj() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.MAX_HRS_EXTRAS_SMNAL);
    }

    public static String getMaxHrsLaboralesSmnl() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.MAX_HRS_LABORALES_SMNAL).getTiempo();
    }

    public static String getMaxHrsLaboralesDia() {
        return getMaxHrsLaboralesDiaObj().getTiempo();
    }

    public static ConfigControlJornada getMaxHrsLaboralesDiaObj() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.MAX_HRS_LABORALES_DIA);
    }

    public static String getMinHrsLaboralesDia() {
        return getMinHrsLaboralesDiaObj().getTiempo();
    }

    public static ConfigControlJornada getMinHrsLaboralesDiaObj() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.MIN_HRS_LABORALES_DIA);

    }

    public static Integer getTipoLiquidacion() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.TIPO_LIQUIDACION).getEstado();
    }

    public static ConfigControlJornada getTipoLiquidacionObj() {
        return SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.TIPO_LIQUIDACION);
    }

}
