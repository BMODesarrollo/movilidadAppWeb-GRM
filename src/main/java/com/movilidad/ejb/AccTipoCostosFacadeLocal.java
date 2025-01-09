/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoCostos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccTipoCostosFacadeLocal {

    void create(AccTipoCostos accTipoCostos);

    void edit(AccTipoCostos accTipoCostos);

    void remove(AccTipoCostos accTipoCostos);

    AccTipoCostos find(Object id);

    List<AccTipoCostos> findAll();

    List<AccTipoCostos> findRange(int[] range);

    int count();

    List<AccTipoCostos> estadoReg();

    List<AccTipoCostos> findByTipoCosto(int i_tipoCosto);

}
