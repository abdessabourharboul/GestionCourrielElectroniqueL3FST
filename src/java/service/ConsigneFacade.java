/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Consigne;
import bean.Courrier;
import bean.User;
import controller.util.SessionUtil;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author asus
 */
@Stateless
public class ConsigneFacade extends AbstractFacade<Consigne> {

    @PersistenceContext(unitName = "ProjetFinEtudesPU")
    private EntityManager em;

    public List<Consigne> findConsignesByCourrier(Courrier courrier) {
        String req = "SELECT co FROM Consigne co WHERE co.courrier.id='" + courrier.getId() + "'";
        return em.createQuery(req).getResultList();
    }

    public void saveConsigne(Consigne consigne, Courrier selected) {
        User user = SessionUtil.getConnectedUser();
        consigne.setCourrier(selected);
        consigne.setUniteAdministrativeDepart(user.getUniteAdministrative());
        consigne.setDateConsigne(new Date());
        super.create(consigne);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsigneFacade() {
        super(Consigne.class);
    }

}
