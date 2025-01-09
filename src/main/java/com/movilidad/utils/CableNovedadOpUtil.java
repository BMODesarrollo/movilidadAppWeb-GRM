package com.movilidad.utils;

import com.movilidad.model.CableNovedadOp;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos Ballestas
 */
public class CableNovedadOpUtil {

    /**
     * Realiza el envío de correo de la novedad generada a las partes
     * interesadas
     *
     * @param mailParams
     * @param mailProperties
     * @param cableNovedadOp
     */
    public static void notificar(Map mailParams, Map mailProperties, CableNovedadOp cableNovedadOp) {

        String subject = "Novedad " + cableNovedadOp.getIdCableEventoTipo().getNombre();
        String destinatarios = cableNovedadOp.getIdEmpleado() != null ? cableNovedadOp.getIdEmpleado().getEmailCorporativo() : "";

        if (cableNovedadOp.getIdCableEventoTipoDet().getIdNotificacionProcesos() != null) {
            if (destinatarios != null) {
                destinatarios = destinatarios + "," + cableNovedadOp.getIdCableEventoTipoDet().getIdNotificacionProcesos().getEmails();
            } else {
                destinatarios = cableNovedadOp.getIdCableEventoTipoDet().getIdNotificacionProcesos().getEmails();
            }
        }
        SendMails.sendEmail(mailParams, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    public static String calcularTiempoOperacionComercial(List<CableNovedadOp> lstNovedades) {

        String totalTimeOperacion;
        String tiempoParada = "";
        String tiempoReinicio = "";

        for (CableNovedadOp novedad : lstNovedades) {
            if (novedad.getIdCableEventoTipoDet().getClaseEvento() == 1) {
                tiempoReinicio = novedad.getTimeFinParada();
            } else if (novedad.getIdCableEventoTipoDet().getClaseEvento() == 2) {
                tiempoParada = novedad.getTimeIniParada();
            }
        }

        totalTimeOperacion = MovilidadUtil.diferenciaHoras(tiempoReinicio, tiempoParada);

        return totalTimeOperacion;
    }

    public static String calcularTiempoOperacionSistema(List<CableNovedadOp> lstNovedades) {
        BigDecimal b_HorometroTotalIni = Util.CERO;
        BigDecimal b_HorometroTotalFin = Util.CERO;

        for (CableNovedadOp novedad : lstNovedades) {
            if (novedad.getIdCableEventoTipoDet().getClaseEvento() == 1) {
                b_HorometroTotalIni = novedad.getHorometroTotal();
            } else if (novedad.getIdCableEventoTipoDet().getClaseEvento() == 2) {
                b_HorometroTotalFin = novedad.getHorometroTotal();
            }
        }

        return String.valueOf(b_HorometroTotalFin.subtract(b_HorometroTotalIni));
    }

    public static String calcularTiempoTotalParada(List<CableNovedadOp> lstNovedades) {

        String timeTotalParada = "00:00:00";

        for (CableNovedadOp novedad : lstNovedades) {
            if (novedad.getIdCableEventoTipoDet().getClaseEvento() != 1
                    && novedad.getIdCableEventoTipoDet().getClaseEvento() != 2) {
                timeTotalParada = MovilidadUtil.sumarHoraSrting(novedad.getTimeTotalParada(), timeTotalParada);
            }
        }

        return timeTotalParada;
    }

}
