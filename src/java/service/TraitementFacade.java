/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Consigne;
import bean.Courrier;
import bean.Traitement;
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
public class TraitementFacade extends AbstractFacade<Traitement> {

    @PersistenceContext(unitName = "ProjetFinEtudesPU")
    private EntityManager em;

    public List<Traitement> findTraitementsByCourrier(Courrier courrier) {
        String req = "SELECT co FROM Traitement co WHERE co.courrier.id='" + courrier.getId() + "'";
        return em.createQuery(req).getResultList();
    }

    public void saveTraitement(Traitement traitement, Courrier selected) {
        User user = SessionUtil.getConnectedUser();
        traitement.setCourrier(selected);
        traitement.setDateTraitement(new Date());
        traitement.setUniteTrait(user.getUniteAdministrative());
        super.create(traitement);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TraitementFacade() {
        super(Traitement.class);
    }

}
