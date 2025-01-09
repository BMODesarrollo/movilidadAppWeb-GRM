/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccVisualInforme;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccVisualInformeFacadeLocal {

    void create(AccVisualInforme accVisualInforme);

    void edit(AccVisualInforme accVisualInforme);

    void remove(AccVisualInforme accVisualInforme);

    AccVisualInforme find(Object id);

    List<AccVisualInforme> findAll();

    List<AccVisualInforme> findRange(int[] range);

    int count();

    List<AccVisualInforme> getAccVisualInformeOpe(int idAccInformeOpe);
}
