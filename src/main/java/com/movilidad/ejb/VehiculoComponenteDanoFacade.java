/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoComponenteDano;
import com.movilidad.model.VehiculoComponenteGrupo;
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
public class VehiculoComponenteDanoFacade extends AbstractFacade<VehiculoComponenteDano> implements VehiculoComponenteDanoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoComponenteDanoFacade() {
        super(VehiculoComponenteDano.class);
    }

    @Override
    public List<VehiculoComponenteDano> findByCteGrupo(VehiculoComponenteGrupo componenteGrupo) {
        Query query = em.createQuery("SELECT vd FROM VehiculoComponenteDano vd "
                + "WHERE vd.idVehiculoComponenteGrupo = :idVehiculoComponenteGrupo")
                .setParameter("idVehiculoComponenteGrupo", componenteGrupo);
        return query.getResultList();
    }

    @Override
    public VehiculoComponenteDano findByIdCompGrupoAndIdVehiculoDano(int idComponenteGrupo, int idVehiculoDano, int idVehiculoComponenteDano) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    vehiculo_componente_dano\n"
                    + "WHERE\n"
                    + "    id_vehiculo_componente_grupo = ?1\n"
                    + "        AND id_vehiculo_dano = ?2\n"
                    + "        AND id_vehiculo_componente_dano <> ?3\n"
                    + "LIMIT 1;", VehiculoComponenteDano.class);
            q.setParameter(1, idComponenteGrupo);
            q.setParameter(2, idVehiculoDano);
            q.setParameter(3, idVehiculoComponenteDano);
            return (VehiculoComponenteDano) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
