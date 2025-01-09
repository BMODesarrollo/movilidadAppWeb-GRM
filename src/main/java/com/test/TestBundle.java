package com.test;

import com.movilidad.utils.MovilidadUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Carlos Ballestas
 */
public class TestBundle {

    static String diurnasTotales = "00:00:00";

    static String diurnas = "00:00:00";
    static String nocturnas = "00:00:00";
    static String extra_diurna = "00:00:00";
    static String extra_nocturna = "00:00:00";
    static String festivo_diurno = "00:00:00";
    static String festivo_nocturno = "00:00:00";
    static String festivo_extra_diurno = "00:00:00";
    static String festivo_extra_nocturno = "00:00:00";

    public static void main(String[] args) {
//        System.out.println("DOMINGO-->>2020/04/09 " + isSunday(new Date(2020, 4, 9)));
//        System.out.println("DOMINGO-->>2020/04/15 " + isSunday(new Date(2020, 4, 15)));
//        System.out.println("DOMINGO-->>2020/04/16 " + isSunday(new Date(2020, 4, 16)));

//        calcula("H", "19:00:00", "H", "29:00:00");
        calcula("H", "02:45:15", "H", "05:16:15");
//        calcula("F", "19:00:00", "F", "29:00:00");
//        calcula("F", "19:00:00", "H", "29:00:00");
//        System.out.println("Hora_Ini;HoraFin;Diurnas;Nocturnas;extra_diurna;extra_nocturna;festivo_diurno;festivo_nocturno;festivo_extra_diurno;festivo_extra_nocturno");
//
//        calcula("H", "12:00:00", "H", "22:00:00");
        System.out.println(diurnas + ";" + nocturnas + ';' + extra_diurna + ";" + extra_nocturna + ";" + festivo_extra_diurno + ";" + festivo_extra_nocturno);
////
        int festivaExtraNocturna = MovilidadUtil.toSecs(festivo_extra_nocturno);
        int festivaExtraDiurna = MovilidadUtil.toSecs(festivo_extra_diurno);
        int extraNocturna = MovilidadUtil.toSecs(extra_nocturna);
        int extraDiurna = MovilidadUtil.toSecs(extra_diurna);
        int descanso_ = MovilidadUtil.toSecs("00:00:00");

        if (festivaExtraNocturna
                > 0) {
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
        if (descanso_
                > 0) {
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
        if (descanso_
                > 0) {
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
        if (descanso_
                > 0) {
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
        extra_diurna = MovilidadUtil.toTimeSec(extraDiurna);
        extra_nocturna = MovilidadUtil.toTimeSec(extraNocturna);
        festivo_extra_diurno = MovilidadUtil.toTimeSec(festivaExtraDiurna);
        festivo_extra_nocturno = MovilidadUtil.toTimeSec(festivaExtraNocturna);

////        System.out.println(MovilidadUtil.toSecs("-1:00:00"));
        System.out.println("Diurnas                : " + diurnas);
        System.out.println("Nocturnas              : " + nocturnas);
        System.out.println("extra_diurna           : " + extra_diurna);
        System.out.println("extra_nocturna         : " + extra_nocturna);
        System.out.println("festivo_diurno         : " + festivo_diurno);
        System.out.println("festivo_nocturno       : " + festivo_nocturno);
        System.out.println("festivo_extra_diurno   : " + festivo_extra_diurno);
        System.out.println("festivo_extra_nocturno : " + festivo_extra_nocturno);
//        System.out.println("PRODUCTION TIME----->" + timeProductionNew("14:00:00","22:00:00")+"<-----PRODUCTION TIME");
//        System.out.println("PRODUCTION TIME----->" + timeProductionNew("22:00:00","30:00:00")+"<-----PRODUCTION TIME");
//        System.out.println("PRODUCTION TIME----->" + timeProductionNew("06:00:00","14:00:00")+"<-----PRODUCTION TIME");
//        System.out.println("PRODUCTION TIME----->" + timeProductionNew("22:00:00","06:00:00")+"<-----PRODUCTION TIME");

//        calcula("H", "04:51:30", "F", "13:27:45");
//        System.out.println("04:51:30" + ";" + "13:27:45" + ";" + diurnas + ";" + nocturnas + ";" + extra_diurna + ";" + extra_nocturna + ";" + festivo_extra_diurno + ";" + festivo_extra_nocturno);
    }

    public static boolean isSunday(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public static void reset() {
        diurnas = "00:00:00";
        nocturnas = "00:00:00";
        extra_diurna = "00:00:00";
        extra_nocturna = "00:00:00";
        festivo_diurno = "00:00:00";
        festivo_nocturno = "00:00:00";
        festivo_extra_diurno = "00:00:00";
        festivo_extra_nocturno = "00:00:00";
    }

    public static String timeProductionNew(String hi, String hf) {
        //Convertir a enteros las horas todos los turnos
        int turnoI1 = MovilidadUtil.toSecs(hi);
        int turnoF1 = MovilidadUtil.toSecs(hf);
        int turnoI2 = MovilidadUtil.toSecs("00:00:00");
        int turnoF2 = MovilidadUtil.toSecs("00:00:00");
        int turnoI3 = MovilidadUtil.toSecs("00:00:00");
        int turnoF3 = MovilidadUtil.toSecs("00:00:00");

        //Calcular el tiempo de produccion de todos los turnos
        int produccionTurno1 = turnoF1 - turnoI1;
        int produccionTurno2 = turnoF2 - turnoI2;
        int produccionTurno3 = turnoF3 - turnoI3;
        //Calcular el tiempo total de produccion
        int produccionTotal = produccionTurno1 + produccionTurno2 + produccionTurno3;

        //Retornar el tiempo todal de produccion en String
        if (produccionTotal == 0) {
            return "00:00:00";
        }
        return MovilidadUtil.toTimeSec(produccionTotal);
    }

    public static void calcula(String tipoDiaIni, String horaIni, String tipoDiaFin, String horaFin) {
        int caso = 0;
        boolean mismoDiaHabil = false;
        boolean mismoDiaFeriado = false;
        boolean habilFeriado = false;
        boolean feriadoHabil = false;
        boolean jornadaExtra = false;

        //<editor-fold defaultstate="collapsed" desc="Valida si hay jornada adicional">
        if ((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)) > MovilidadUtil.toSecs("08:00:00")) {
            if ((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)
                    - MovilidadUtil.toSecs("08:00:00")
                    - MovilidadUtil.toSecs("01:00:00")) <= MovilidadUtil.toSecs("02:00:00")) {
                jornadaExtra = true;
                System.out.println("* Jornada extra *");
            } else {
                System.out.println("* Solo se permiten hasta 2 horas extras diarias *");
                return;
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Valida si es cambio de tipo de día">
        if (tipoDiaIni.equals(tipoDiaFin)) {
            if (tipoDiaIni.equals("H")) {
                mismoDiaHabil = true;
            } else {
                mismoDiaFeriado = true;
            }
        } else {//
            if (tipoDiaIni.equals("H")) {
                habilFeriado = true;
            } else {
                feriadoHabil = true;
            }
        }
        //</editor-fold>

        // Caso 0, Jornada horaIni.equals("08:00:00") && horaFin.equals("17:00:00")
        //<editor-fold defaultstate="collapsed" desc="comment">
        if (horaIni.equals("08:00:00") && horaFin.equals("17:00:00")) {
            reset();
            if (mismoDiaHabil) {
                diurnas = "08:00:00";
                nocturnas = "00:00:00";
                extra_diurna = "00:00:00";
                extra_nocturna = "00:00:00";
                festivo_diurno = "00:00:00";
                festivo_nocturno = "00:00:00";
                festivo_extra_diurno = "00:00:00";
                festivo_extra_nocturno = "00:00:00";
            } else if (mismoDiaFeriado) {
                diurnas = "00:00:00";
                nocturnas = "00:00:00";
                extra_diurna = "00:00:00";
                extra_nocturna = "00:00:00";
                festivo_diurno = "08:00:00";
                festivo_nocturno = "00:00:00";
                festivo_extra_diurno = "00:00:00";
                festivo_extra_nocturno = "00:00:00";
            }
            return;
        }
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Nuevo Caso 1">
        if (MovilidadUtil.toSecs(horaIni) < MovilidadUtil.toSecs("06:00:00")) {
            System.out.println("Caso 1 Nuevo");
            if (mismoDiaHabil) {
                if (jornadaExtra) {
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("06:00:00") - MovilidadUtil.toSecs(horaIni)));
                    extra_diurna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)) - MovilidadUtil.toSecs("08:00:00"));
                    diurnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("08:00:00") - MovilidadUtil.toSecs(nocturnas)));
                } else {
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("06:00:00") - MovilidadUtil.toSecs(horaIni)));
                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni) - MovilidadUtil.toSecs(nocturnas));
                    if (MovilidadUtil.toSecs(horaFin) < MovilidadUtil.toSecs("06:00:00")) {
                        nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)));
                        diurnas = "00:00:00";
                    }
                }
            } else {
                if (jornadaExtra) {
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("06:00:00") - MovilidadUtil.toSecs(horaIni)));
                    festivo_extra_diurno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)) - MovilidadUtil.toSecs("08:00:00"));
                    festivo_diurno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("08:00:00") - MovilidadUtil.toSecs(festivo_nocturno)));
                } else {
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("06:00:00") - MovilidadUtil.toSecs(horaIni)));
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni) - MovilidadUtil.toSecs(festivo_nocturno));
                    if (MovilidadUtil.toSecs(horaFin) < MovilidadUtil.toSecs("06:00:00")) {
                        festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)));
                        festivo_diurno = "00:00:00";
                    }
                }
            }
            caso = 1;
            return;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Nuevo Caso 2">
        if (MovilidadUtil.toSecs(horaIni) >= MovilidadUtil.toSecs("06:00:00")
                && MovilidadUtil.toSecs(horaFin) < MovilidadUtil.toSecs("21:00:00")) {
            System.out.println("Caso 2 Nuevo");
            if (mismoDiaHabil) {
                if (jornadaExtra) {
                    diurnas = "08:00:00";
                    extra_diurna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)) - MovilidadUtil.toSecs("08:00:00"));
                } else {
                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni));
                }
            } else {
                if (jornadaExtra) {
                    festivo_diurno = "08:00:00";
                    festivo_extra_diurno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)) - MovilidadUtil.toSecs("08:00:00"));
                } else {
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni));
//                    festivo_extra_diurno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni)) - MovilidadUtil.toSecs("08:00:00"));
                }
            }
            caso = 2;
            return;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Nuevo Caso 3">
        if (MovilidadUtil.toSecs(horaIni) >= MovilidadUtil.toSecs("06:00:00")
                && MovilidadUtil.toSecs(horaFin) < MovilidadUtil.toSecs("24:00:00")) {
            System.out.println("Caso 3 Nuevo");
            if (mismoDiaHabil) {
                System.out.println("MISMO DIA");
                if (jornadaExtra) {
                    System.out.println("JORNADA EXTRA");
//                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
//                            - MovilidadUtil.toSecs(horaIni));
                    if (MovilidadUtil.toSecs(horaIni) <= MovilidadUtil.toSecs("06:00:00")) {
                        diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                                - MovilidadUtil.toSecs(horaIni));
                    } else if (MovilidadUtil.toSecs(horaIni) >= MovilidadUtil.toSecs("21:00:00")) {
                        diurnas = "00:00:00";
                    } else if (MovilidadUtil.toSecs(horaIni) < MovilidadUtil.toSecs("21:00:00")) {
                        diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                    }
                    if (MovilidadUtil.toSecs(diurnas) > MovilidadUtil.toSecs("08:00:00")) {
                        diurnas = "08:00:00";
                        extra_diurna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                                - MovilidadUtil.toSecs(diurnas)
                                - MovilidadUtil.toSecs(horaIni));
                        nocturnas = "00:00:00";
                        extra_nocturna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                                - MovilidadUtil.toSecs(horaIni)
                                - MovilidadUtil.toSecs(diurnas)
                                - MovilidadUtil.toSecs(extra_diurna));
                    } else {
                        nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("08:00:00")
                                - MovilidadUtil.toSecs(diurnas));
                        if ((MovilidadUtil.toSecs(diurnas) + MovilidadUtil.toSecs(nocturnas))
                                < MovilidadUtil.toSecs(horaFin)) {
                            extra_nocturna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                                    - MovilidadUtil.toSecs(horaIni)
                                    - MovilidadUtil.toSecs(diurnas)
                                    - MovilidadUtil.toSecs(nocturnas));
                        }
                    }
                } else {
                    if (MovilidadUtil.toSecs(horaIni) <= MovilidadUtil.toSecs("06:00:00")) {
                        diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                                - MovilidadUtil.toSecs(horaIni));
                    } else if (MovilidadUtil.toSecs(horaIni) >= MovilidadUtil.toSecs("21:00:00")) {
                        diurnas = "00:00:00";
                    } else if (MovilidadUtil.toSecs(horaIni) < MovilidadUtil.toSecs("21:00:00")) {
                        diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                    }
                    //                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));

                    if (MovilidadUtil.toSecs(diurnas) < MovilidadUtil.toSecs("08:00:00")) {
                        nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(diurnas) - MovilidadUtil.toSecs(horaIni)));
                    }
                }
            } else {
                if (jornadaExtra) {
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                            - MovilidadUtil.toSecs(horaIni));
                    if (MovilidadUtil.toSecs(festivo_diurno) > MovilidadUtil.toSecs("08:00:00")) {
                        festivo_diurno = "08:00:00";
                        festivo_extra_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                                - MovilidadUtil.toSecs(festivo_diurno)
                                - MovilidadUtil.toSecs(horaIni));
                        festivo_nocturno = "00:00:00";
                        festivo_extra_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                                - MovilidadUtil.toSecs(horaIni)
                                - MovilidadUtil.toSecs(festivo_diurno)
                                - MovilidadUtil.toSecs(festivo_extra_diurno));
                    } else {
                        festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("08:00:00")
                                - MovilidadUtil.toSecs(festivo_diurno));
                        if ((MovilidadUtil.toSecs(festivo_diurno) + MovilidadUtil.toSecs(festivo_nocturno))
                                < MovilidadUtil.toSecs(horaFin)) {
                            festivo_extra_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                                    - MovilidadUtil.toSecs(horaIni)
                                    - MovilidadUtil.toSecs(festivo_diurno)
                                    - MovilidadUtil.toSecs(festivo_nocturno));
                        }
                    }
                } else {
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                    if (MovilidadUtil.toSecs(festivo_diurno) < MovilidadUtil.toSecs("08:00:00")) {
                        festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(festivo_diurno) - MovilidadUtil.toSecs(horaIni)));
                    }
                }
            }
            caso = 3;
            return;
        }
        //</editor-fold>

        // Nuevo Caso 4, Jornada Diurna y Nocturna
        // Hora Inicial inferior a las 21:00:00 y hora Final inferior a las 30:00:00 DONE
        //<editor-fold defaultstate="collapsed" desc="Nuevo Caso 4">
        if (MovilidadUtil.toSecs(horaIni) <= MovilidadUtil.toSecs("21:00:00")
                && MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("21:00:00")
                && MovilidadUtil.toSecs(horaFin) <= MovilidadUtil.toSecs("30:00:00")) {
            System.out.println("Nuevo Caso 4");
            if (mismoDiaHabil) {
                if (jornadaExtra) {
                    if (((MovilidadUtil.toSecs("21:00:00")
                            - MovilidadUtil.toSecs(horaIni)))
                            > MovilidadUtil.toSecs("08:00:00")) {
                        System.out.println("!");
                        diurnas = ("08:00:00");
                        extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                                - MovilidadUtil.toSecs(horaIni))
                                - MovilidadUtil.toSecs("08:00:00"));
                        nocturnas = "00:00:00";
                    } else {
                        System.out.println("2");
                        diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                                - MovilidadUtil.toSecs(horaIni));

                        nocturnas = MovilidadUtil.toTimeSec(
                                MovilidadUtil.toSecs("08:00:00")
                                - MovilidadUtil.toSecs(diurnas));

                        extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                                - MovilidadUtil.toSecs(horaIni))
                                - MovilidadUtil.toSecs(diurnas)
                                - MovilidadUtil.toSecs(nocturnas));
                    }
                } else {
                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                    nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("21:00:00"));
                }
            } else if (mismoDiaFeriado) {
                if (jornadaExtra) {
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                            - MovilidadUtil.toSecs(horaIni));
                    festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00"));
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("21:00:00"))
                            - MovilidadUtil.toSecs(festivo_extra_nocturno));
                } else {
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                    festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("21:00:00"));
                }
            } else if (habilFeriado) {
                diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                        - MovilidadUtil.toSecs(horaIni));

                nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("08:00:00")
                        - MovilidadUtil.toSecs(diurnas));

                if (MovilidadUtil.toSecs(nocturnas)
                        > MovilidadUtil.toSecs("03:00:00")) {
                    nocturnas = "03:00:00";
                }

                if ((MovilidadUtil.toSecs(horaIni)
                        + MovilidadUtil.toSecs(diurnas)
                        + MovilidadUtil.toSecs(nocturnas))
                        <= MovilidadUtil.toSecs("24:00:00")
                        && (MovilidadUtil.toSecs(diurnas)
                        + MovilidadUtil.toSecs(nocturnas))
                        == MovilidadUtil.toSecs("08:00:00")) {
                    System.out.println("Si es menor");
                    extra_nocturna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("24:00:00")
                            - (MovilidadUtil.toSecs(horaIni)
                            + MovilidadUtil.toSecs(diurnas)
                            + MovilidadUtil.toSecs(nocturnas)));
                } else {

                }
                if (MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("24:00:00")) {
//                    festivo_nocturno = "03:00:00";
                    festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("24:00:00"));
                    if (jornadaExtra) {
                        if (((MovilidadUtil.toSecs(diurnas)
                                + MovilidadUtil.toSecs(nocturnas))
                                <= MovilidadUtil.toSecs("08:00:00"))) {
                            festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("08:00:00")
                                    - MovilidadUtil.toSecs(diurnas)
                                    - MovilidadUtil.toSecs(nocturnas));
                        } else {

                        }
                        festivo_extra_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                                - (MovilidadUtil.toSecs(horaIni)
                                + MovilidadUtil.toSecs(diurnas)
                                + MovilidadUtil.toSecs(nocturnas)
                                + MovilidadUtil.toSecs(festivo_nocturno)
                                + MovilidadUtil.toSecs(extra_nocturna)));
                    }
                } else {
                    nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("21:00:00"));
                }

//<editor-fold defaultstate="collapsed" desc="OldCode">
//                diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
//                if (MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("24:00:00")) {
//
//                    if ((MovilidadUtil.toSecs("08:00:00") - MovilidadUtil.toSecs(diurnas)) > 0) {
//                        nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("08:00:00")
//                                - MovilidadUtil.toSecs(diurnas));
//                        extra_nocturna = MovilidadUtil.toTimeSec(
//                                MovilidadUtil.toSecs("24:00:00")
//                                - (MovilidadUtil.toSecs(horaIni)
//                                + MovilidadUtil.toSecs(diurnas)
//                                + MovilidadUtil.toSecs(nocturnas)));
//                    } else {
//                        nocturnas = "00:00:00";
//                        extra_nocturna = MovilidadUtil.toTimeSec(
//                                MovilidadUtil.toSecs("24:00:00")
//                                - (MovilidadUtil.toSecs(horaIni)
//                                + MovilidadUtil.toSecs(diurnas)
//                                + MovilidadUtil.toSecs(nocturnas)));
////                    festivo_nocturno = "00:00:00";
//
//                    }
//                    if (jornadaExtra) {
//                        festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
//                                - MovilidadUtil.toSecs(horaIni))
//                                - MovilidadUtil.toSecs(diurnas)
//                                - MovilidadUtil.toSecs(nocturnas)
//                                - MovilidadUtil.toSecs(extra_nocturna)
//                        );
//                        festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
//                                - MovilidadUtil.toSecs("24:00:00")
//                                - MovilidadUtil.toSecs(festivo_extra_nocturno));
//                    }
//                } else {
//                    nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("21:00:00"));
//                }
//</editor-fold>
            } else if (feriadoHabil) {
                festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00")
                        - MovilidadUtil.toSecs(horaIni));

                festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("08:00:00")
                        - MovilidadUtil.toSecs(festivo_diurno));

                if (MovilidadUtil.toSecs(festivo_nocturno)
                        > MovilidadUtil.toSecs("03:00:00")) {
                    festivo_nocturno = "03:00:00";
                }

                if ((MovilidadUtil.toSecs(horaIni)
                        + MovilidadUtil.toSecs(festivo_diurno)
                        + MovilidadUtil.toSecs(festivo_nocturno))
                        <= MovilidadUtil.toSecs("24:00:00")
                        && (MovilidadUtil.toSecs(festivo_diurno)
                        + MovilidadUtil.toSecs(festivo_nocturno))
                        == MovilidadUtil.toSecs("08:00:00")) {
                    System.out.println("Si es menor");
                    festivo_extra_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("24:00:00")
                            - (MovilidadUtil.toSecs(horaIni)
                            + MovilidadUtil.toSecs(festivo_diurno)
                            + MovilidadUtil.toSecs(festivo_nocturno)));
                } else {

                }
                if (MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("24:00:00")) {
//                    festivo_nocturno = "03:00:00";
                    nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("24:00:00"));
                    if (jornadaExtra) {
                        if (((MovilidadUtil.toSecs(festivo_diurno)
                                + MovilidadUtil.toSecs(festivo_nocturno))
                                <= MovilidadUtil.toSecs("08:00:00"))) {
                            nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("08:00:00")
                                    - MovilidadUtil.toSecs(festivo_diurno)
                                    - MovilidadUtil.toSecs(festivo_nocturno));
                        } else {

                        }
                        extra_nocturna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                                - (MovilidadUtil.toSecs(horaIni)
                                + MovilidadUtil.toSecs(festivo_diurno)
                                + MovilidadUtil.toSecs(festivo_nocturno)
                                + MovilidadUtil.toSecs(nocturnas)
                                + MovilidadUtil.toSecs(festivo_extra_nocturno)));
                    }
                } else {
                    festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("21:00:00"));
                }
            }
            caso = 2;
            return;
        }
//</editor-fold>

        //Nuevo Caso 5, Jornada Diurna, Nocturna, Diurna
        // hora Inicial inferior a las 21:00:00 y hora Final mayor a 31:00:00 DONE
        //<editor-fold defaultstate="collapsed" desc="Nuevo caso 5"> 
        if (MovilidadUtil.toSecs(horaIni) <= MovilidadUtil.toSecs("21:00:00")
                && MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("21:00:00")
                && MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("30:00:00")) {
            System.out.println("Nuevo Caso 5");
            reset();
            if (mismoDiaHabil) {
                if (jornadaExtra) {
                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                    extra_diurna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("30:00:00"));
                    extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            - MovilidadUtil.toSecs(extra_diurna));
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("21:00:00"))
                            - MovilidadUtil.toSecs(extra_nocturna)
                            - MovilidadUtil.toSecs(extra_diurna));
                } else {
                    //No entrará por el rango de horas
                    System.out.println("No entrará por el rango de horas mismoHabil");
                    diurnas = MovilidadUtil.toTimeSec(
                            (MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni)));
                    nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("30:00:00") - MovilidadUtil.toSecs("21:00:00"));
                }
            } else if (mismoDiaFeriado) {
                if (jornadaExtra) {
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                    festivo_extra_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("30:00:00"));
                    festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            - MovilidadUtil.toSecs(festivo_extra_diurno));
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("21:00:00"))
                            - MovilidadUtil.toSecs(festivo_extra_nocturno)
                            - MovilidadUtil.toSecs(festivo_extra_diurno));
                } else {
                    System.out.println("No entrará por el rango de horas mismoFeriado");
                    festivo_diurno = MovilidadUtil.toTimeSec(
                            (MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni))
                            + (MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("30:00:00")));
                    festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("30:00:00") - MovilidadUtil.toSecs("21:00:00"));
                }//DONE
            } else if (habilFeriado) {
                diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                if (MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("24:00:00")) {
                    nocturnas = "03:00:00";
                    if (jornadaExtra) {
                        festivo_extra_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("30:00:00"));
                        festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni))
                                - MovilidadUtil.toSecs("08:00:00")
                                - MovilidadUtil.toSecs(festivo_extra_diurno));

                        festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                                - MovilidadUtil.toSecs("24:00:00"))
                                - MovilidadUtil.toSecs(festivo_extra_diurno)
                                - MovilidadUtil.toSecs(festivo_extra_nocturno));
                    }
                } else {
                    //No entra por el rango de horas
                    nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("21:00:00"));
                }
            } else if (feriadoHabil) {
                festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("21:00:00") - MovilidadUtil.toSecs(horaIni));
                festivo_nocturno = "03:00:00";
                if (jornadaExtra) {
                    extra_diurna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs("30:00:00"));
                    extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin) - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            - MovilidadUtil.toSecs(extra_diurna));

                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("24:00:00"))
                            - MovilidadUtil.toSecs(extra_diurna)
                            - MovilidadUtil.toSecs(extra_nocturna));
                }
            }
            caso = 3;
            return;
        }
//</editor-fold>

        // Caso 4, Jornada Nocturna y Diurna
        //<editor-fold defaultstate="collapsed" desc="comment">
        if (MovilidadUtil.toSecs(horaIni) >= MovilidadUtil.toSecs("21:00:00")
                && MovilidadUtil.toSecs(horaIni) < MovilidadUtil.toSecs("24:00:00")
                && MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("30:00:00")) {
            System.out.println("Caso 4");
            reset();
            if (mismoDiaHabil) {
                //<editor-fold defaultstate="collapsed" desc="comment">
                if (jornadaExtra) {
                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
                    extra_diurna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
                    extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            - MovilidadUtil.toSecs(extra_diurna));
                    if (MovilidadUtil.toSecs(extra_nocturna) < 0) {
                        extra_nocturna = "00:00:00";
                        extra_diurna = "00:00:00";
                    }
                    if (diurnas.equals(extra_diurna)) {
                        diurnas = "00:00:00";
                    }
                    nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            //                            - MovilidadUtil.toSecs("21:00:00"))
                            - MovilidadUtil.toSecs(extra_nocturna)
                            - MovilidadUtil.toSecs(extra_diurna.equals("00:00:00") ? diurnas : extra_diurna));
                } else {
                    //No entra
                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
                    nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            //                            - MovilidadUtil.toSecs("21:00:00"))
                            - MovilidadUtil.toSecs(diurnas));
                }
//</editor-fold>

            } else if (mismoDiaFeriado) {
                //<editor-fold defaultstate="collapsed" desc="comment">
                if (jornadaExtra) {
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
                    festivo_extra_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
                    festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            - MovilidadUtil.toSecs(festivo_extra_diurno));
                    if (MovilidadUtil.toSecs(festivo_extra_nocturno) < 0) {
                        festivo_extra_nocturno = "00:00:00";
                        festivo_extra_diurno = "00:00:00";
                    }
                    if (festivo_diurno.equals(festivo_extra_diurno)) {
                        festivo_diurno = "00:00:00";
                    }
                    festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            //                            - MovilidadUtil.toSecs("21:00:00"))
                            - MovilidadUtil.toSecs(festivo_extra_nocturno)
                            - MovilidadUtil.toSecs(festivo_extra_diurno.equals("00:00:00") ? festivo_diurno : festivo_extra_diurno));
                } else {
                    //No entra
                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
                    festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            //                            - MovilidadUtil.toSecs("21:00:00"))
                            - MovilidadUtil.toSecs(festivo_diurno));
                }
//</editor-fold>

            } else if (habilFeriado) {
                //<editor-fold defaultstate="collapsed" desc="comment">
                nocturnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("24:00:00")
                        - MovilidadUtil.toSecs(horaIni));

                if (jornadaExtra) {
                    System.out.println("Caso 4 - JornadaExtra habilFeriado");

                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
                    festivo_extra_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));

                    festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            - MovilidadUtil.toSecs(festivo_extra_diurno));

                    if (MovilidadUtil.toSecs(festivo_extra_nocturno) < 0) {
                        festivo_extra_nocturno = "00:00:00";
                        festivo_extra_diurno = "00:00:00";
                    }

                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs(nocturnas)
                            - MovilidadUtil.toSecs(festivo_diurno)
                            - MovilidadUtil.toSecs(festivo_extra_nocturno)
                            - MovilidadUtil.toSecs(festivo_extra_diurno));

                    if (festivo_nocturno.equals("06:00:00")) {
                        festivo_diurno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("08:00:00")
                                - MovilidadUtil.toSecs(nocturnas))
                                - MovilidadUtil.toSecs(festivo_nocturno));
                        festivo_extra_diurno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                                - MovilidadUtil.toSecs("30:00:00")
                                - MovilidadUtil.toSecs(festivo_diurno)));
                    }
//
                } else {
                    System.out.println("Caso 4 - JornadaExtra habilFeriado");

                    festivo_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
                    festivo_extra_diurno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));

                    festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            - MovilidadUtil.toSecs(festivo_extra_diurno));

                    if (MovilidadUtil.toSecs(festivo_extra_nocturno) < 0) {
                        festivo_extra_nocturno = "00:00:00";
                        festivo_extra_diurno = "00:00:00";
                    }

                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs(nocturnas)
                            - MovilidadUtil.toSecs(festivo_diurno)
                            - MovilidadUtil.toSecs(festivo_extra_nocturno)
                            - MovilidadUtil.toSecs(festivo_extra_diurno));
                }
//</editor-fold>

            } else if (feriadoHabil) {
                //<editor-fold defaultstate="collapsed" desc="comment">
//                festivo_nocturno
                festivo_nocturno = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs("24:00:00")
                        - MovilidadUtil.toSecs(horaIni));

                if (jornadaExtra) {
                    System.out.println("Caso 4 - JornadaExtra FeriadoHabil");
//                    diurnas
                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
//                    extra_diurna
                    extra_diurna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
//                    extra_nocturna
                    extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            //                            extra_diurna
                            - MovilidadUtil.toSecs(extra_diurna));

//                    extra_nocturna
                    if (MovilidadUtil.toSecs(extra_nocturna) < 0) {
//                    extra_nocturna
                        extra_nocturna = "00:00:00";
//                            extra_diurna
                        extra_diurna = "00:00:00";
                    }
//                    nocturnas
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            //                festivo_nocturno
                            - MovilidadUtil.toSecs(festivo_nocturno)
                            //                    diurnas
                            - MovilidadUtil.toSecs(diurnas)
                            //                    extra_nocturna
                            - MovilidadUtil.toSecs(extra_nocturna)
                            //                            extra_diurna
                            - MovilidadUtil.toSecs(extra_diurna));

//                    nocturnas
                    if (nocturnas.equals("06:00:00")) {
                        //                    diurnas
                        diurnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("08:00:00")
                                //                festivo_nocturno
                                - MovilidadUtil.toSecs(festivo_nocturno))
                                //                    nocturnas
                                - MovilidadUtil.toSecs(nocturnas));
                        //                            extra_diurna
                        extra_diurna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                                - MovilidadUtil.toSecs("30:00:00")
                                //                    diurnas
                                - MovilidadUtil.toSecs(diurnas)));
                    }
//
                } else {
                    System.out.println("Caso 4 - JornadaExtra habilFeriado");

//                    diurnas
                    diurnas = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
//                    extra_diurna
                    extra_diurna = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs("30:00:00"));
//                    extra_nocturna
                    extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            - MovilidadUtil.toSecs("08:00:00")
                            //                    extra_diurna
                            - MovilidadUtil.toSecs(extra_diurna));

//                    extra_nocturna
                    if (MovilidadUtil.toSecs(extra_nocturna) < 0) {
//                    extra_nocturna
                        extra_nocturna = "00:00:00";
//                    extra_diurna
                        extra_diurna = "00:00:00";
                    }
//                    nocturnas
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni))
                            //                            festivo_nocturno
                            - MovilidadUtil.toSecs(festivo_nocturno)
                            //                    diurnas
                            - MovilidadUtil.toSecs(diurnas)
                            //                    extra_nocturna
                            - MovilidadUtil.toSecs(extra_nocturna)
                            //                    extra_diurna
                            - MovilidadUtil.toSecs(extra_diurna));
                }

            }
//</editor-fold>
            caso = 4;
        }
//</editor-fold>

        //Caso 6, Jornada Nocturna
        //<editor-fold defaultstate="collapsed" desc="comment">
        if (MovilidadUtil.toSecs(horaIni) >= MovilidadUtil.toSecs("21:00:00")
                && MovilidadUtil.toSecs(horaIni) < MovilidadUtil.toSecs("24:00:00")
                && MovilidadUtil.toSecs(horaFin) > MovilidadUtil.toSecs("24:00:00")
                && MovilidadUtil.toSecs(horaFin) <= MovilidadUtil.toSecs("30:00:00")) {
            System.out.println("Caso 6");
            reset();
            if (mismoDiaHabil) {
                if (jornadaExtra) {
                    nocturnas = "08:00:00";
                    extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            //                    diurnas
                            - MovilidadUtil.toSecs(nocturnas)));
                } else {
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)));
                }
            } else if (mismoDiaFeriado) {
                if (jornadaExtra) {
                    festivo_nocturno = "08:00:00";
                    festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            //                    diurnas
                            - MovilidadUtil.toSecs(festivo_nocturno)));
                } else {
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)));
                }
            } else if (habilFeriado) {
                if (jornadaExtra) {
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("24:00:00")
                            - MovilidadUtil.toSecs(horaIni)));
                    festivo_extra_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            - MovilidadUtil.toSecs("08:00:00")));
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(nocturnas)
                            - MovilidadUtil.toSecs(horaIni)
                            - MovilidadUtil.toSecs(festivo_extra_nocturno)));
                } else {
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("24:00:00")
                            - MovilidadUtil.toSecs(horaIni)));
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            - MovilidadUtil.toSecs(nocturnas)));

                }
            } else if (feriadoHabil) {
                if (jornadaExtra) {
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("24:00:00")
                            - MovilidadUtil.toSecs(horaIni)));
                    extra_nocturna = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            - MovilidadUtil.toSecs("08:00:00")));
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(festivo_nocturno)
                            - MovilidadUtil.toSecs(horaIni)
                            - MovilidadUtil.toSecs(extra_nocturna)));
                } else {
                    festivo_nocturno = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs("24:00:00")
                            - MovilidadUtil.toSecs(horaIni)));
                    nocturnas = MovilidadUtil.toTimeSec((MovilidadUtil.toSecs(horaFin)
                            - MovilidadUtil.toSecs(horaIni)
                            - MovilidadUtil.toSecs(festivo_nocturno)));

                }
            }
            caso = 6;
        }
//</editor-fold>

    }
}
