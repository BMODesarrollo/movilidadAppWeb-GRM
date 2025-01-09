/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmNovedadIncluir;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface PmNovedadIncluirFacadeLocal {

    void create(PmNovedadIncluir pmNovedadIncluir);

    void edit(PmNovedadIncluir pmNovedadIncluir);

    void remove(PmNovedadIncluir pmNovedadIncluir);

    PmNovedadIncluir find(Object id);

    List<PmNovedadIncluir> findAll();

    List<PmNovedadIncluir> findRange(int[] range);

    int count();

    List<PmNovedadIncluir> getAllActivo();

    PmNovedadIncluir getByIdNovedadTipoDet(int idDet, int idPmNovedadIncluir);

}
