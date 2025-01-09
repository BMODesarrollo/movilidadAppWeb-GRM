/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgPrimeras;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface PrgPrimerasFacadeLocal {

    void create(PrgPrimeras prgPrimeras);

    void edit(PrgPrimeras prgPrimeras);

    void remove(PrgPrimeras prgPrimeras);

    PrgPrimeras find(Object id);

    List<PrgPrimeras> findAll();

    List<PrgPrimeras> findRange(int[] range);

    int count();

    /**
     * permite listar por unidad funcional y por fecha
     *
     * @param fecha Date
     * @param idGopUf int
     * @return
     */
    List<PrgPrimeras> findAllByGopUf(Date fecha, int idGopUf);

    /**
     * Ejecutar procediminto almacenado para obtener las primeras
     *
     * @param fecha Date
     * @param idGopUf
     */
    void ejecutarProcesoPrimeras(Date fecha, int idGopUf);

}
