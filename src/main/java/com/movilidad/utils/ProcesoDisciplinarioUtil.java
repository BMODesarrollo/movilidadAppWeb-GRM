package com.movilidad.utils;

import com.movilidad.model.Novedad;
import com.movilidad.model.PdMaestro;
import com.movilidad.model.PdMaestroDetalle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Ballestas
 */
public class ProcesoDisciplinarioUtil {

    /**
     *
     * @param lstNovedades
     * @param user
     * @param pdMaestro
     * @return Método que devuelve la lista de detalles en base a las novedades
     * seleccionadas
     */
    public static List<PdMaestroDetalle> generarListaDetalle(List<Novedad> lstNovedades, String user, PdMaestro pdMaestro) {
        List<PdMaestroDetalle> lista = new ArrayList<>();
        lstNovedades.forEach(item -> {
            PdMaestroDetalle nuevoDetalle = new PdMaestroDetalle();
            nuevoDetalle.setIdPdMaestro(pdMaestro);
            nuevoDetalle.setIdNovedad(item);
            nuevoDetalle.setUsername(user);
            nuevoDetalle.setEstadoReg(0);
            nuevoDetalle.setCreado(MovilidadUtil.fechaCompletaHoy());
            nuevoDetalle.setFechaProceso(MovilidadUtil.fechaCompletaHoy());
            lista.add(nuevoDetalle);
        });
        return lista;
    }

    /**
     * Método que verifica si en la lista de detalles se encuentra alguna de las
     * novedades seleccionadas.
     *
     * @param listaDetalles
     * @param lstNovedades
     * @return Mensaje de verificacion
     */
    public static String verificarExisteNovedad(List<PdMaestroDetalle> listaDetalles, List<Novedad> lstNovedades) {
        for (Novedad novedad : lstNovedades) {
            if (verificarExisteDetalle(listaDetalles, novedad.getIdNovedad())) {
                return "SE encontraron novedades éxistentes en la lista de detalles";
            }
        }
        return null;
    }

    /**
     * Método que se encarga de devolver el listado de novedades seleccionas en
     * base a los detalles guardados.
     *
     * @param lista
     * @return Listado de novedades que se encuentran en los detalles
     */
    public static List<Novedad> obtenerNovedadesSeleccionadas(List<PdMaestroDetalle> lista) {
        List<Novedad> novedades = new ArrayList<>();

        lista.forEach(det -> {
            novedades.add(det.getIdNovedad());
        });

        return novedades;

    }

    private static boolean verificarExisteDetalle(List<PdMaestroDetalle> listaDetalles, Integer idNovedad) {
        return listaDetalles.stream().filter(det -> (det.getIdPdMaestroDetalle() != null)).anyMatch(det -> (det.getIdNovedad().getIdNovedad().equals(idNovedad)));
    }
}
