package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuCategoria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Omar Beltrán
 */
@Stateless
public class PlaRecuCategoriaFacade extends AbstractFacade<PlaRecuCategoria> implements PlaRecuCategoriaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuCategoriaFacade() {
        super(PlaRecuCategoria.class);
    }
    
    @Override
    public List<PlaRecuCategoria> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_categoria WHERE estado_reg = ?1", PlaRecuCategoria.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Categoria");
            return null;
        }
    }
    
    @Override
    public List<PlaRecuCategoria> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_categoria WHERE estado_reg = 0", PlaRecuCategoria.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Categoria");
            return null;
        }
    }
    
    @Override
    public PlaRecuCategoria findByName(String categoriaName) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_categoria WHERE name = ?1 AND estado_reg = 0", PlaRecuCategoria.class);
            q.setParameter(1, categoriaName);
            return q.getResultList().isEmpty() ? null : (PlaRecuCategoria) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Categoria");
            return null;
        }
    }

}