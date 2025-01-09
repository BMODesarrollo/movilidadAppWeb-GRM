/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaComponenteGrupo;
import com.movilidad.model.DanoFlotaParamSeveridad;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julián Arévalo
 */
@Stateless
public class DanoFlotaComponenteGrupoFacade extends AbstractFacade<DanoFlotaComponenteGrupo> implements DanoFlotaComponenteGrupoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DanoFlotaComponenteGrupoFacade() {
        super(DanoFlotaComponenteGrupo.class);
    }

    @Override
    public List<DanoFlotaComponenteGrupo> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    dano_flota_componente_grupo p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0;", DanoFlotaComponenteGrupo.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
