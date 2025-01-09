/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaToken;
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
public class GenericaTokenFacade extends AbstractFacade<GenericaToken> implements GenericaTokenFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaTokenFacade() {
        super(GenericaToken.class);
    }

    @Override
    public List<GenericaToken> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM generica_token WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, GenericaToken.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GenericaToken login(String token, Integer idEmpleado) {
        try {
            String sql = "SELECT\n"
                    + "	g.*\n"
                    + "FROM\n"
                    + "	generica_token g\n"
                    + "WHERE\n"
                    + "	g.solicitado = ?1\n"
                    + "	and g.id_empleado = ?2\n"
                    + "	AND g.token = ?3\n"
                    + "	AND g.activo = 0\n"
                    + "	AND g.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, GenericaToken.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(new Date())));
            query.setParameter(2, idEmpleado);
            query.setParameter(3, token);
            return (GenericaToken) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GenericaToken getValidarProcesoPorDia(Date dSolicitado, Integer idEmpleado) {
        try {
            String dSolici = Util.dateFormat(dSolicitado);
            String sql = "SELECT gt.* "
                    + "FROM generica_token gt "
                    + "WHERE gt.solicitado = ?1 "
                    + "AND gt.id_empleado = ?2 "
                    + "AND gt.estado_reg = 0";
            Query q = em.createNativeQuery(sql, GenericaToken.class);
            q.setParameter(1, dSolici);
            q.setParameter(2, idEmpleado);
            return (GenericaToken) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
