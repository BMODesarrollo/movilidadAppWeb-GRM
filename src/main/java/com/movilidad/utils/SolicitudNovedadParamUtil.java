package com.movilidad.utils;

import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NovedadTipoDetalles;
import java.util.Map;

/**
 *
 * @author Carlos Ballestas
 */
public class SolicitudNovedadParamUtil {

    /*
     * Mensajes de error   
     */
    private static final String MSG_ERROR_CODIGO_PROCESO = "Debe seleccionar código de proceso";
    private static final String MSG_ERROR_NOVEDAD_CAMBIO = "Debe seleccionar la novedad correspondiente al cambio de turno";
    private static final String MSG_ERROR_NOVEDAD_PERMISO = "Debe seleccionar la novedad correspondiente a los permisos";
    private static final String MSG_ERROR_NOVEDAD_LICENCIA = "Debe seleccionar la novedad correspondiente a las licencias";
    private static final String MSG_ERROR_NOVEDAD_NO_FIRMA = "Debe seleccionar la novedad correspondiente a la NO firma de licencias";

    public static String validarDatos(NotificacionProcesos notificacionProcesos, NovedadTipoDetalles novedadCambio,
            NovedadTipoDetalles novedadPermiso, NovedadTipoDetalles novedadLicencia, NovedadTipoDetalles novedadNoFirma) {

        if (notificacionProcesos.getIdNotificacionProceso() == null) {
            return MSG_ERROR_CODIGO_PROCESO;
        }

        if (novedadCambio.getIdNovedadTipoDetalle() == null) {
            return MSG_ERROR_NOVEDAD_CAMBIO;
        }

        if (novedadPermiso.getIdNovedadTipoDetalle() == null) {
            return MSG_ERROR_NOVEDAD_PERMISO;
        }

        if (novedadLicencia.getIdNovedadTipoDetalle() == null) {
            return MSG_ERROR_NOVEDAD_LICENCIA;
        }

        if (novedadNoFirma.getIdNovedadTipoDetalle() == null) {
            return MSG_ERROR_NOVEDAD_NO_FIRMA;
        }

        return null;
    }

    public static boolean validarExisteRegistro(Map<String, NotificacionProcesos> map, String codigoProceso) {
        return map.containsKey(codigoProceso);
    }
}
