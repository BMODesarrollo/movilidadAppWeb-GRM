/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DocumentoPorCargo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class DocumentoPorCargoFacade extends AbstractFacade<DocumentoPorCargo> implements DocumentoPorCargoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentoPorCargoFacade() {
        super(DocumentoPorCargo.class);
    }

    @Override
    public DocumentoPorCargo findByIdTipoCargoAndIdTipoDocu(int idTipoCargo, int idTipoDocu, int idRegistro) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    dpc.*\n"
                    + "FROM\n"
                    + "    documento_por_cargo dpc\n"
                    + "WHERE\n"
                    + "    dpc.id_empleado_tipo_cargo = ?1\n"
                    + "        AND dpc.id_empleado_tipo_documento= ?2\n"
                    + "        AND dpc.id_documento_por_cargo <> ?3\n"
                    + "        AND dpc.estado_reg = 0;", DocumentoPorCargo.class);

            q.setParameter(1, idTipoCargo);
            q.setParameter(2, idTipoDocu);
            q.setParameter(3, idRegistro);
            return (DocumentoPorCargo) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DocumentoPorCargo> findAllActivos() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    dpc.*\n"
                + "FROM\n"
                + "    documento_por_cargo dpc\n"
                + "WHERE\n"
                + "    dpc.estado_reg = 0;", DocumentoPorCargo.class);

        return q.getResultList();

    }

}
