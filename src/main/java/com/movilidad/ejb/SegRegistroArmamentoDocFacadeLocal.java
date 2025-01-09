/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegRegistroArmamentoDoc;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SegRegistroArmamentoDocFacadeLocal {

    void create(SegRegistroArmamentoDoc segRegistroArmamentoDoc);

    void edit(SegRegistroArmamentoDoc segRegistroArmamentoDoc);

    void remove(SegRegistroArmamentoDoc segRegistroArmamentoDoc);

    SegRegistroArmamentoDoc find(Object id);

    SegRegistroArmamentoDoc findByNumDoc(String numDoc, Integer idRegistroArmamamento);

    SegRegistroArmamentoDoc findByActivo(Integer idRegistroArmamento);

    SegRegistroArmamentoDoc verificarRangoFechas(Date fecha, Integer idRegistroArmamamento, Integer idSegRegistroArmamentoDoc);

    List<SegRegistroArmamentoDoc> findAll();

    List<SegRegistroArmamentoDoc> findRange(int[] range);

    int count();

}
