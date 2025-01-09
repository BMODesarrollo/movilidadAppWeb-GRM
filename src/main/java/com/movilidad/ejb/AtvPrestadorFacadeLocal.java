/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvPrestador;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AtvPrestadorFacadeLocal {

    void create(AtvPrestador atvPrestador);

    void edit(AtvPrestador atvPrestador);

    void remove(AtvPrestador atvPrestador);

    AtvPrestador find(Object id);

    List<AtvPrestador> findAll();

    List<AtvPrestador> findRange(int[] range);

    int count();

    List<AtvPrestador> findAllByEstadoReg();

    AtvPrestador findByNombreAndRangoFecha(String nombre, Date desde, Date hasta, int idAtvPrestador);

    /**
     * Permite consultar un prestador por correo electronico y si se encuentra
     * activo
     *
     * @param correo
     * @param fecha 
     * @return
     */
    AtvPrestador findByCorreoAndActivo(String correo);
}
