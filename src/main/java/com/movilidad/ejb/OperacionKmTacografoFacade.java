/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.OperacionKmTacografo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class OperacionKmTacografoFacade extends AbstractFacade<OperacionKmTacografo> implements OperacionKmTacografoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperacionKmTacografoFacade() {
        super(OperacionKmTacografo.class);
    }

    @Override
    public List<OperacionKmTacografo> findEstRegis() {
        try {
            Query q = em.createQuery("SELECT o FROM OperacionKmTacografo o WHERE o.estadoReg = :estadoReg", OperacionKmTacografo.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean fechaRegistro(Date d, int i) {
        String c_aux = new SimpleDateFormat("yyyy/MM/dd").format(d);
        try {
            Query q = em.createNativeQuery("SELECT * FROM operacion_km_tacografo "
                    + "WHERE fecha = ? AND id_vehiculo = ? AND estado_reg = 0", OperacionKmTacografo.class);
            q.setParameter(1, "" + c_aux + "");
            q.setParameter(2, i);
            return q.getResultList().size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public OperacionKmTacografo verificarKmInicial(int i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM operacion_km_tacografo "
                    + "WHERE km_final = (SELECT MAX(km_final) FROM operacion_km_tacografo WHERE id_vehiculo = ? AND estado_reg = 0) "
                    + "AND estado_reg = 0", OperacionKmTacografo.class);
            q.setParameter(1, i);
            OperacionKmTacografo name = (OperacionKmTacografo) q.getSingleResult();
            return name;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OperacionKmTacografo verificarKmInicialEditar(int id_v, int id_reg) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM operacion_km_tacografo Where km_final = (SELECT MAX(km_final)"
                    + " FROM operacion_km_tacografo WHERE NOT id_operacion_km_tacografo = ?)"
                    + " AND id_vehiculo = ?;", OperacionKmTacografo.class);
            q.setParameter(1, id_reg);
            q.setParameter(2, id_v);
            OperacionKmTacografo name = (OperacionKmTacografo) q.getSingleResult();
            return name;
        } catch (Exception e) {
            return null;
        }
    }

}
