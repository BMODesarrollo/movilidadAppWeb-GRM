/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DocumentoPorCargo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface DocumentoPorCargoFacadeLocal {

    void create(DocumentoPorCargo documentoPorCargo);

    void edit(DocumentoPorCargo documentoPorCargo);

    void remove(DocumentoPorCargo documentoPorCargo);

    DocumentoPorCargo find(Object id);

    List<DocumentoPorCargo> findAll();

    List<DocumentoPorCargo> findRange(int[] range);

    int count();

    DocumentoPorCargo findByIdTipoCargoAndIdTipoDocu(int idTipoCargo, int idTipoDocu, int idRegistro);

    List<DocumentoPorCargo> findAllActivos();
}
