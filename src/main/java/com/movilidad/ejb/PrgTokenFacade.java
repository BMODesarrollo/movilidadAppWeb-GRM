/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgToken;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class PrgTokenFacade extends AbstractFacade<PrgToken> implements PrgTokenFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgTokenFacade() {
        super(PrgToken.class);
    }

    @Override
    public List<PrgToken> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM prg_token WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, PrgToken.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgToken login(String token, Integer idEmpleado) {
        try {
            String sql = "SELECT\n"
                    + "	p.*\n"
                    + "FROM\n"
                    + "	prg_token p\n"
                    + "WHERE\n"
                    + "	p.solicitado = ?1\n"
                    + "	and p.id_empleado = ?2\n"
                    + "	AND p.token = ?3\n"
                    + "	AND p.activo = 0\n"
                    + "	AND p.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PrgToken.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(new Date())));
            query.setParameter(2, idEmpleado);
            query.setParameter(3, token);
            return (PrgToken) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgToken getValidarProcesoPorDia(Date dSolicitado, Integer idEmpleado) {
        try {
            String dSolici = Util.dateFormat(dSolicitado);
            String sql = "SELECT p.* "
                    + "FROM prg_token p "
                    + "WHERE p.solicitado = ?1 "
                    + "AND p.id_empleado = ?2 "
                    + "AND p.estado_reg = 0";
            Query q = em.createNativeQuery(sql, PrgToken.class);
            q.setParameter(1, dSolici);
            q.setParameter(2, idEmpleado);
            return (PrgToken) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

//    @Override
//    public PrgToken getTokenByEmpleado(Date dSolicitado, Integer idEmpleado, String token) {
//        try {
//            String dSolici = Util.dateFormat(dSolicitado);
//            String sql = "SELECT p.* "
//                    + "FROM prg_token p "
//                    + "WHERE p.solicitado = ?1 "
//                    + "AND p.id_empleado = ?2 "
//                    + "AND p.token = ?3 "
//                    + "AND p.activo = 0 "
//                    + "AND p.estado_reg = 0";
//            Query q = em.createNativeQuery(sql, PrgToken.class);
//            q.setParameter(1, dSolici);
//            q.setParameter(2, idEmpleado);
//            q.setParameter(3, token);
//            return (PrgToken) q.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
