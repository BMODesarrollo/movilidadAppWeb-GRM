/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmNovedadIncluir;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmNovedadIncluirFacadeLocal {

    void create(GenericaPmNovedadIncluir genericaPmNovedadIncluir);

    void edit(GenericaPmNovedadIncluir genericaPmNovedadIncluir);

    void remove(GenericaPmNovedadIncluir genericaPmNovedadIncluir);

    GenericaPmNovedadIncluir find(Object id);

    List<GenericaPmNovedadIncluir> findAll();

    List<GenericaPmNovedadIncluir> findRange(int[] range);

    int count();

    List<GenericaPmNovedadIncluir> getByIdArea(int idArea);

    GenericaPmNovedadIncluir getByIdNovedadTipoDet(int idDet, int idGenericaPmNovedadIncluir);

}
