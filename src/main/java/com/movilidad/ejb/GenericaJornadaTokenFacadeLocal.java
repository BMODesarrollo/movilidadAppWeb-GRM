/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaToken;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaJornadaTokenFacadeLocal {

    void create(GenericaJornadaToken genericaJorandaToken);

    void edit(GenericaJornadaToken genericaJorandaToken);

    void remove(GenericaJornadaToken genericaJorandaToken);

    GenericaJornadaToken find(Object id);

    List<GenericaJornadaToken> findAll();

    List<GenericaJornadaToken> findRange(int[] range);

    int count();
    
    GenericaJornadaToken findByToken(String token);
}
