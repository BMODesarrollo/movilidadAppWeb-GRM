/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmNovedadExcluir;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmNovedadExcluirFacadeLocal {

    void create(GenericaPmNovedadExcluir genericaPmNovedadExcluir);

    void edit(GenericaPmNovedadExcluir genericaPmNovedadExcluir);

    void remove(GenericaPmNovedadExcluir genericaPmNovedadExcluir);

    GenericaPmNovedadExcluir find(Object id);

    List<GenericaPmNovedadExcluir> findAll();

    List<GenericaPmNovedadExcluir> findRange(int[] range);

    int count();

    List<GenericaPmNovedadExcluir> getByIdArea(int idArea);

    GenericaPmNovedadExcluir getByIdNovedadTipoDet(int idDet, int idGenericaPmNovedadExcluir);

}
