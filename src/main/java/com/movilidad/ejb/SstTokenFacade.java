/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstToken;
import com.movilidad.utils.Util;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class SstTokenFacade extends AbstractFacade<SstToken> implements SstTokenFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstTokenFacade() {
        super(SstToken.class);
    }

    /**
     * Permite obtener un objeto SstToken, por identificador SstEmpresa y Objeto
     * Date
     *
     * @param idSstEmp identificador SstEmpresa
     * @param d Objeto Util Date
     * @return Objeto SstToken, null en caso de error o no encontrar objeto
     * SstToken
     */
    @Override
    public SstToken findTokenByIdSstEmpresa(Integer idSstEmp, Date d) {
        try {
            String sql = "SELECT * "
                    + "FROM sst_token "
                    + "WHERE id_sst_empresa = ?1 "
                    + "AND solicitado = ?2 "
                    + "AND activo = 0 "
                    + "AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, SstToken.class);
            q.setParameter(1, idSstEmp);
            q.setParameter(2, Util.dateFormat(d));
            return (SstToken) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite obtener un objeto SstToken, por su atributo token y Objeto Date
     *
     * @param cToken atributo del objeto SstToken
     * @param d Objeto Util Date
     * @return Objeto SstToken, null en caso de error o no encontrar objeto
     * SstToken
     */
    @Override
    public SstToken findByToken(String cToken, Date d) {
        try {
            String sql = "SELECT * "
                    + "FROM sst_token "
                    + "WHERE token = ?1 "
                    + "AND solicitado = ?2 "
                    + "AND activo = 0 "
                    + "AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, SstToken.class);
            q.setParameter(1, cToken);
            q.setParameter(2, Util.dateFormat(d));
            return (SstToken) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
