/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConsumoEnergiaEstado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface ConsumoEnergiaEstadoFacadeLocal {

	void create(ConsumoEnergiaEstado consumoEnergiaEstado);

	void edit(ConsumoEnergiaEstado consumoEnergiaEstado);

	void remove(ConsumoEnergiaEstado consumoEnergiaEstado);

	ConsumoEnergiaEstado find(Object id);

	ConsumoEnergiaEstado findByNombre(Integer idRegistro, String nombre);

	List<ConsumoEnergiaEstado> findAll();
	
	List<ConsumoEnergiaEstado> findAllByEstadoReg();

	List<ConsumoEnergiaEstado> findRange(int[] range);

	int count();

}
