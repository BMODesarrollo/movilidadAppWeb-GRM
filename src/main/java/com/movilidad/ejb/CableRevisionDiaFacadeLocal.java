/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionDia;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableRevisionDiaFacadeLocal {

	void create(CableRevisionDia cableRevisionDia);

	void edit(CableRevisionDia cableRevisionDia);

	void remove(CableRevisionDia cableRevisionDia);

	CableRevisionDia find(Object id);

	CableRevisionDia findByFecha(Integer idCableEstacion, Date fecha);

	List<CableRevisionDia> findAll();

	List<CableRevisionDia> findAllByDateRange(Date fechaDesde, Date fechaHasta);

	List<CableRevisionDia> findAllByEstadoReg();

	List<CableRevisionDia> findRange(int[] range);

	int count();

}
