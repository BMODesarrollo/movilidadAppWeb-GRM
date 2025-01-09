/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NotificacionTemplate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class NotificacionTemplateFacade extends AbstractFacade<NotificacionTemplate> implements NotificacionTemplateFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificacionTemplateFacade() {
        super(NotificacionTemplate.class);
    }

    @Override
    public NotificacionTemplate findByTemplate(String template) {
        try {
            Query query = em.createNamedQuery("NotificacionTemplate.findByTemplate", NotificacionTemplate.class);
            query.setParameter("template", template);

            return (NotificacionTemplate) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
