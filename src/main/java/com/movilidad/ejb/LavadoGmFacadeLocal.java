/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoGm;
import com.movilidad.util.beans.CostoLavadoDTO;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface LavadoGmFacadeLocal {

    void create(LavadoGm lavadoGm);

    void edit(LavadoGm lavadoGm);

    void remove(LavadoGm lavadoGm);

    LavadoGm find(Object id);

    List<LavadoGm> findAll();

    List<LavadoGm> findRange(int[] range);

    int count();

    /**
     * Permite obtener los lavadoGm filtrando por unidad funcional, si la UF es
     * cero, retornará sin tener encuenta la unidad funcinal. Se filtra por
     * fechas.
     *
     * @param idGopUF Identificador GopUF
     * @param desde Date
     * @param hasta Date
     * @return list LavadoGM que no han sido calificados, filtra por unidad
     * funcional.
     */
    List<LavadoGm> findLavadoGMByFechaAndGopUF(int idGopUF, Date desde, Date hasta);

    List<LavadoGm> findLavadoByVehiculo(int idVehiculo, Date desde, Date hasta);

    List<LavadoGm> findLavadoByRange(Date desde, Date hasta, int idGopUF);

    /**
     * Permite consultar el costo total de los servicios de lavado que se
     * encuentren cerrados
     *
     * @param idGopUF Identificador GopUF
     * @param desde Date
     * @param hasta Date
     * @return List CostoLavadoDTO que no han sido calificados, filtra por
     * unidad funcional.
     */
    List<CostoLavadoDTO> findCostoLavadoCerrado(int idGopUF, Date desde, Date hasta);

}
