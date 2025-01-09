/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegRegistroArmamentoDoc;
import com.movilidad.utils.Util;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class SegRegistroArmamentoDocFacade extends AbstractFacade<SegRegistroArmamentoDoc> implements SegRegistroArmamentoDocFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegRegistroArmamentoDocFacade() {
        super(SegRegistroArmamentoDoc.class);
    }

    @Override
    public SegRegistroArmamentoDoc verificarRangoFechas(Date fecha, Integer idRegistroArmamamento, Integer idSegRegistroArmamentoDoc) {
        try {
            String sql = "SELECT * FROM seg_registro_armamento_doc \n"
                    + "where ?1 BETWEEN vigente_desde and vigente_hasta \n"
                    + "and activo = 1\n"
                    + "and id_seg_registro_armamento = ?2\n"
                    + "and id_seg_registro_armamento_doc <> ?3\n"
                    + "and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, SegRegistroArmamentoDoc.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idRegistroArmamamento);
            query.setParameter(3, idSegRegistroArmamentoDoc);

            return (SegRegistroArmamentoDoc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SegRegistroArmamentoDoc findByNumDoc(String numDoc, Integer idRegistroArmamamento) {
        try {
            String sql = "SELECT * FROM seg_registro_armamento_doc \n"
                    + "where\n"
                    + "id_seg_registro_armamento = ?1\n"
                    + "and numero_doc = ?2\n"
                    + "and estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, SegRegistroArmamentoDoc.class);
            query.setParameter(1, idRegistroArmamamento);
            query.setParameter(2, numDoc);

            return (SegRegistroArmamentoDoc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SegRegistroArmamentoDoc findByActivo(Integer idRegistroArmamento) {
        try {
            String sql = "SELECT * FROM seg_registro_armamento_doc \n"
                    + "where activo = 1\n"
                    + "and id_seg_registro_armamento = ?1\n"
                    + "and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, SegRegistroArmamentoDoc.class);
            query.setParameter(1, idRegistroArmamento);

            return (SegRegistroArmamentoDoc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
