/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmNovedadExcluir;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface PmNovedadExcluirFacadeLocal {

    void create(PmNovedadExcluir pmNovedadExcluir);

    void edit(PmNovedadExcluir pmNovedadExcluir);

    void remove(PmNovedadExcluir pmNovedadExcluir);

    PmNovedadExcluir find(Object id);

    List<PmNovedadExcluir> findAll();

    List<PmNovedadExcluir> findRange(int[] range);

    int count();

    List<PmNovedadExcluir> getAllActivo();

    PmNovedadExcluir getByIdNovedadTipoDet(int idDet, int idPmNovedadExcluir);

}
