/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionDia;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class CableRevisionDiaFacade extends AbstractFacade<CableRevisionDia> implements CableRevisionDiaFacadeLocal {

	@PersistenceContext(unitName = "rigel")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CableRevisionDiaFacade() {
		super(CableRevisionDia.class);
	}

	@Override
	public CableRevisionDia findByFecha(Integer idCableEstacion, Date fecha) {
		try {
			String sql = "SELECT * FROM cable_revision_dia where fecha = ?1 "
					+ "and id_cable_estacion = ?2 "
					+ "and estado_reg = 0 LIMIT 1;";
			Query query = em.createNativeQuery(sql, CableRevisionDia.class);
			query.setParameter(1, Util.dateFormat(fecha));
			query.setParameter(2, idCableEstacion);

			return (CableRevisionDia) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<CableRevisionDia> findAllByEstadoReg() {
		try {
			Query query = em.createNamedQuery("CableRevisionDia.findByEstadoReg", CableRevisionDia.class);
			query.setParameter("estadoReg", 0);

			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<CableRevisionDia> findAllByDateRange(Date fechaDesde, Date fechaHasta) {
		try {
			String sql = "SELECT * FROM cable_revision_dia where fecha between ?1 and ?2 and estado_reg = 0;";
			Query query = em.createNativeQuery(sql, CableRevisionDia.class);
			query.setParameter(1, Util.dateFormat(fechaDesde));
			query.setParameter(2, Util.dateFormat(fechaHasta));

			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

}
