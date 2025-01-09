/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaNovedadComponente;
import com.movilidad.model.NovedadDano;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julián Arévalo
 */
@Stateless
public class DanoFlotaNovedadComponenteFacade extends AbstractFacade<DanoFlotaNovedadComponente> implements DanoFlotaNovedadComponenteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DanoFlotaNovedadComponenteFacade() {
        super(DanoFlotaNovedadComponente.class);
    }

    @Override
    public List<DanoFlotaNovedadComponente> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    dano_flota_novedad_componente p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0 and p.activo = 1;", DanoFlotaNovedadComponente.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer findValueComponents(NovedadDano novedadDano) {
        try {
            Query q = em.createNativeQuery("SELECT SUM(p1.costo) as total\n"
                    + "FROM\n"
                    + "dano_flota_novedad_componente p\n"
                    + "INNER JOIN dano_flota_solucion_valor p1 on p.id_solucion_valor = p1.id_solucion_valor\n"
                    + "WHERE\n"
                    + "p.estado_reg = 0 and p.id_novedad_dano=?1 limit 1;"); // Especificar el tipo de resultado
            q.setParameter(1, novedadDano.getIdNovedadDano());

            Object result = q.getSingleResult();

            // Convertir el resultado a Integer de forma segura
            //return total;
            //System.out.println("TOTAL" + total);
            return result != null ? ((Number) result).intValue() : 0;
        } catch (NoResultException e) {
            // Manejar el caso donde no se encuentran resultados
            return 0; // O cualquier otro valor por defecto
        } catch (NonUniqueResultException e) {
            // Manejar el caso donde se encuentran múltiples resultados
            throw new RuntimeException("Se encontraron múltiples resultados", e);
        } catch (Exception e) {
            // Manejar otras excepciones
            throw new RuntimeException("Error al obtener el valor total", e);
        }
    }

    @Override
    public List<DanoFlotaNovedadComponente> findSolucionesByNovedad(Integer idNovedad) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "p.*\n"
                    + "FROM\n"
                    + "dano_flota_novedad_componente p\n"
                    + "WHERE\n"
                    + "p.estado_reg = 0 and id_novedad_dano=?1;", DanoFlotaNovedadComponente.class);
            q.setParameter(1, idNovedad);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
