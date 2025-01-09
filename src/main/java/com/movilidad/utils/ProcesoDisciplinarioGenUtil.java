package com.movilidad.utils;

import com.movilidad.model.Generica;
import com.movilidad.model.GenericaPdMaestro;
import com.movilidad.model.GenericaPdMaestroDetalle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Ballestas
 */
public class ProcesoDisciplinarioGenUtil {

    /**
     *
     * @param lstNovedades
     * @param user
     * @param pdMaestro
     * @return Método que devuelve la lista de detalles en base a las novedades
     * seleccionadas
     */
    public static List<GenericaPdMaestroDetalle> generarListaDetalle(List<Generica> lstNovedades, String user, GenericaPdMaestro pdMaestro) {
        List<GenericaPdMaestroDetalle> lista = new ArrayList<>();
        lstNovedades.forEach(item -> {
            GenericaPdMaestroDetalle nuevoDetalle = new GenericaPdMaestroDetalle();
            nuevoDetalle.setIdGenericaPdMaestro(pdMaestro);
            nuevoDetalle.setIdGenerica(item);
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
     * @param lstGenericaes
     * @return Mensaje de verificacion
     */
    public static String verificarExisteGenerica(List<GenericaPdMaestroDetalle> listaDetalles, List<Generica> lstGenericaes) {
        for (Generica novedad : lstGenericaes) {
            if (verificarExisteDetalle(listaDetalles, novedad.getIdGenerica())) {
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
    public static List<Generica> obtenerGenericaesSeleccionadas(List<GenericaPdMaestroDetalle> lista) {
        List<Generica> novedades = new ArrayList<>();

        lista.forEach(det -> {
            novedades.add(det.getIdGenerica());
        });

        return novedades;

    }

    private static boolean verificarExisteDetalle(List<GenericaPdMaestroDetalle> listaDetalles, Integer idGenerica) {
        return listaDetalles.stream().filter(det -> (det.getIdGenericaPdMaestroDetalle() != null)).anyMatch(det -> (det.getIdGenerica().getIdGenerica().equals(idGenerica)));
    }
}
