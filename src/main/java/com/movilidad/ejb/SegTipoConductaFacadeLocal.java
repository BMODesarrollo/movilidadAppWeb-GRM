/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegTipoConducta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegTipoConductaFacadeLocal {

    void create(SegTipoConducta segTipoConducta);

    void edit(SegTipoConducta segTipoConducta);

    void remove(SegTipoConducta segTipoConducta);

    SegTipoConducta find(Object id);

    List<SegTipoConducta> findAll();

    List<SegTipoConducta> findRange(int[] range);

    int count();

    List<SegTipoConducta> findAllByEstadoReg();

    SegTipoConducta findByNombre(String nombre, int idSegTipoConducta);
}
