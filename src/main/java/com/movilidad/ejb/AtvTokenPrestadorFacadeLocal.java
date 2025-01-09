/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvTokenPrestador;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AtvTokenPrestadorFacadeLocal {

    void create(AtvTokenPrestador atvTokenPrestador);

    void edit(AtvTokenPrestador atvTokenPrestador);

    void remove(AtvTokenPrestador atvTokenPrestador);

    AtvTokenPrestador find(Object id);

    List<AtvTokenPrestador> findAll();

    List<AtvTokenPrestador> findRange(int[] range);

    int count();

    /**
     * Permite consultar por token, si esta activo
     *
     * @param token
     * @return
     */
    AtvTokenPrestador findByTokenAndActivo(String token);

}
