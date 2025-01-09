/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.OperacionKmTacografo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface OperacionKmTacografoFacadeLocal {

    void create(OperacionKmTacografo operacionKmTacografo);

    void edit(OperacionKmTacografo operacionKmTacografo);

    void remove(OperacionKmTacografo operacionKmTacografo);

    OperacionKmTacografo find(Object id);

    List<OperacionKmTacografo> findAll();

    List<OperacionKmTacografo> findRange(int[] range);

    int count();

    List<OperacionKmTacografo> findEstRegis();

    boolean fechaRegistro(Date d, int i);

    OperacionKmTacografo verificarKmInicial(int i);

    OperacionKmTacografo verificarKmInicialEditar(int i, int j);

}
