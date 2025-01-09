/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstMatEquiMarca;
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
public class SstMatEquiMarcaFacade extends AbstractFacade<SstMatEquiMarca> implements SstMatEquiMarcaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstMatEquiMarcaFacade() {
        super(SstMatEquiMarca.class);
    }

    /**
     * Permite obtener la lista de objetos donde su atributo estado_reg sea
     * igual a 0 y identificador empresa sea el correspondiente
     *
     * @param idSstEmpresa identificador unico del objeto SstEmpresa
     * @return Lista de objetos SstMatEquiMarca, null en caso de error
     */
    @Override
    public List<SstMatEquiMarca> findAllEstadoReg(Integer idSstEmpresa) {
        try {
            String sql = "SELECT * "
                    + "FROM sst_mat_equi_marca "
                    + "WHERE id_sst_empresa = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, SstMatEquiMarca.class);
            q.setParameter(1, idSstEmpresa);
            return q.getResultList();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SstMatEquiMarca> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstMatEquiMarca.findByEstadoReg", SstMatEquiMarca.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
